package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.ObjectMessage;
import javax.jms.Session;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.activemq.command.ActiveMQQueue;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.DeviceService;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.WriterUtil;
import com.xiaoshu.vo.DeviceVo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("device")
public class DeviceController extends LogController{
	static Logger logger = Logger.getLogger(DeviceController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	DeviceService deviceService;
	
	@Autowired
	JedisPool jedisPool;
	
	@Autowired
	ActiveMQQueue activeMQQueue;
	
	@Autowired
	JmsTemplate JmsTemplate;
	
	
	@RequestMapping("deviceIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("DeviceStatus", deviceService.findStatus());
		return "device";
	}
	
	
	@RequestMapping(value="deviceList",method=RequestMethod.POST)
	public void userList(DeviceVo vo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Device device = new Device();
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<DeviceVo> userList= deviceService.findUserPage(vo,pageNum,pageSize,ordername,order);
			
			
			JSONObject jsonObj = new JSONObject();
			jsonObj.put("total",userList.getTotal() );
			jsonObj.put("rows", userList.getList());
	        WriterUtil.write(response,jsonObj.toString());
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("用户展示错误",e);
			throw e;
		}
	}
	
	
	// 新增或修改
	@RequestMapping("reserveUser")
	public void reserveUser(HttpServletRequest request,final Device device,HttpServletResponse response){
		Integer Id = device.getDeviceid();//获取到id
		JSONObject result=new JSONObject();
		try {
			if (Id != null) {   // userId不为空 说明是修改
				Device d = deviceService.existUserWithUserName(device.getDevicename());
				if(d==null){ //判断id是否为空,为空则添加,不为空则修改
					device.setCreatetime(new Date());
					deviceService.updateDevice(device);
					result.put("success", true);//返回前台
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else {   // 添加
				if(deviceService.existUserWithUserName(device.getDevicename())==null){  // 没有重复可以添加
					device.setCreatetime(new Date());
					deviceService.addDevice(device);
					
				
					JmsTemplate.send(activeMQQueue, new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {
							ObjectMessage message = session.createObjectMessage(JSONObject.toJSONString(device));
							
							return message;
						}
					});
					
					
					
					
					
					result.put("success", true);
				} else {
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	@RequestMapping("tongji")
	public void tongji(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
			List<DeviceVo> count = deviceService.tongji();
			
			result.put("data", count);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				deviceService.deleteUser(Integer.parseInt(id));
			}
			result.put("success", true);
			result.put("delNums", ids.length);
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//添加设备类别
	@RequestMapping("addDeviceType")
	public void addDeviceType(Devicetype devicetype,HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
			devicetype.setCreatetime(new Date());			
			deviceService.addType(devicetype);
				
			
			Jedis jedis = jedisPool.getResource();
			
			jedis.hset("devicetype", devicetype.getDevicetypeid().toString(), devicetype.toString());
			
			
			
			result.put("success", true);		
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("添加设备类别错误",e);
			result.put("errorMsg", "对不起，添加设备类别失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	@RequestMapping("editPassword")
	public void editPassword(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		String oldpassword = request.getParameter("oldpassword");
		String newpassword = request.getParameter("newpassword");
		HttpSession session = request.getSession();
		User currentUser = (User) session.getAttribute("currentUser");
		if(currentUser.getPassword().equals(oldpassword)){
			User user = new User();
			user.setUserid(currentUser.getUserid());
			user.setPassword(newpassword);
			try {
				userService.updateUser(user);
				currentUser.setPassword(newpassword);
				session.removeAttribute("currentUser"); 
				session.setAttribute("currentUser", currentUser);
				result.put("success", true);
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("修改密码错误",e);
				result.put("errorMsg", "对不起，修改密码失败");
			}
		}else{
			logger.error(currentUser.getUsername()+"修改密码时原密码输入错误！");
			result.put("errorMsg", "对不起，原密码输入错误！");
		}
		WriterUtil.write(response, result.toString());
	}
}
