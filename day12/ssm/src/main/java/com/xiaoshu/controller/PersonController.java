package com.xiaoshu.controller;

import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
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
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.ExpressCompany;
import com.xiaoshu.entity.ExpressPerson;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.User;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.PersonService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;
import com.xiaoshu.vo.PersonVo;

import redis.clients.jedis.Jedis;
import redis.clients.jedis.JedisPool;

@Controller
@RequestMapping("person")
public class PersonController extends LogController{
	static Logger logger = Logger.getLogger(PersonController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	PersonService personService;
	
	@Autowired
	JmsTemplate jmsTemplate;
	
	@Autowired
	ActiveMQQueue activeMQQueue;
	
	@Autowired
	JedisPool jedisPool;
	
	@RequestMapping("personIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("StatusList", personService.findStatus());
		return "person";
	}
	
	
	@RequestMapping(value="personList",method=RequestMethod.POST)
	public void userList(PersonVo vo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			
			ExpressPerson person = new ExpressPerson();
			
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<PersonVo> userList= personService.findUserPage(vo,pageNum,pageSize,ordername,order);
			
			
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
	public void reserveUser(HttpServletRequest request,final ExpressPerson expressPerson,HttpServletResponse response){
		Integer Id = expressPerson.getId();
		JSONObject result=new JSONObject();
		try {
			if (Id != null) {   // userId不为空 说明是修改
					expressPerson.setCreateTime(new Date());
					personService.update(expressPerson);
					
					jmsTemplate.send(activeMQQueue,new MessageCreator() {
						
						@Override
						public Message createMessage(Session session) throws JMSException {
							// TODO Auto-generated method stub
							ObjectMessage message = session.createObjectMessage(JSONObject.toJSONString(expressPerson).toString());
							return message;
						}
					});										
					result.put("success", true);
			}else {   // 添加
					expressPerson.setCreateTime(new Date());
					personService.add(expressPerson);
					result.put("success", true);				
			}
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("保存用户信息错误",e);
			result.put("success", true);
			result.put("errorMsg", "对不起，操作失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
		//导入
		@RequestMapping("importFile")
		public String importFile(MultipartFile file,HttpServletRequest request,HttpServletResponse response) throws Exception{
			JSONObject result=new JSONObject();
	
				XSSFWorkbook workbook = new XSSFWorkbook(file.getInputStream());
				XSSFSheet sheetAt = workbook.getSheetAt(0);
				int lastRowNum = sheetAt.getLastRowNum();
				for (int i = 1; i <= lastRowNum; i++) {
					XSSFRow row = sheetAt.getRow(i);
					ExpressPerson  e  = new ExpressPerson();
										
					String ename = row.getCell(0).getStringCellValue();
					e.setExperssName(ename);
					
					String sex = row.getCell(1).getStringCellValue();
					e.setSex(sex);
					
					String experssTrait = row.getCell(2).getStringCellValue();
					e.setExperssTrait(experssTrait);
					
					String experssTime = row.getCell(3).getStringCellValue();
					e.setExperssTime(TimeUtil.ParseTime(experssTime, "yyyy-MM-dd"));
					
					String name = row.getCell(4).getStringCellValue();
					
					ExpressCompany company =  personService.findCname(name);
					if(company!=null) {
						e.setExpressTypeId(company.getId());
					}else {
						ExpressCompany company2 = new ExpressCompany();
						company2.setCreateTime(new Date());
						company2.setExpressName(name);
						company2.setStatus(1);
						personService.addCname(company2);
						e.setExpressTypeId(company2.getId());

						Jedis jedis = jedisPool.getResource();
						
						jedis.set("expressStr", company2.getExpressName());
						
						System.out.println(jedis.get("expressStr"));
						
						jedis.lpush("expressList", company2.getId().toString());
						System.out.println(jedis.lpop("expressList"));
						
						jedis.rpush("expressList", company2.getExpressName());
						System.out.println(jedis.rpop("expressList"));
						
						jedis.hset("expressHash", company2.getId().toString(), company2.getExpressName());
						System.out.println(jedis.hget("expressHash", company2.getId().toString()));
						
					}
					personService.add(e);
					
					
					
				}
				workbook.close();
				return "redirect:personIndex.htm?menuid=14";
		
		}
	
		
		
		//根据查询条件导出
		@RequestMapping("export")
		public void export(PersonVo personVo,HttpServletRequest request,HttpServletResponse response){
			JSONObject result=new JSONObject();
			try {
				XSSFWorkbook workbook = new XSSFWorkbook();
				XSSFSheet sheet = workbook.createSheet();
				XSSFRow row = sheet.createRow(0);
				String[] str = {"编号","人员名字","性别","人员特点","入职时间","所属公司","创建时间"};
				for (int i = 0; i < str.length; i++) {
					row.createCell(i).setCellValue(str[i]);
				}
				List<PersonVo> list = personService.findExperssPersonAll(personVo);
				for (int i = 0; i < list.size(); i++) {
					XSSFRow createRow = sheet.createRow(i+1);
					ExpressPerson p = list.get(i);
					createRow.createCell(0).setCellValue(p.getId());
					createRow.createCell(1).setCellValue(p.getExperssName());
					createRow.createCell(2).setCellValue(p.getSex());
					createRow.createCell(3).setCellValue(p.getExperssTrait());
					createRow.createCell(4).setCellValue(TimeUtil.formatTime(p.getExperssTime(), "yyyy-MM-dd"));
					createRow.createCell(5).setCellValue(list.get(i).getCname());
					createRow.createCell(6).setCellValue(TimeUtil.formatTime(p.getCreateTime(), "yyyy-MM-dd HH:mm:ss"));
				}
				OutputStream outputStream = new FileOutputStream(new File("D://快递信息.xlsx"));
				workbook.write(outputStream);
				
				workbook.close();
				outputStream.close();				
				result.put("success", true);	
			} catch (Exception e) {
				e.printStackTrace();
				logger.error("删除用户信息错误",e);
				result.put("errorMsg", "对不起，删除失败");
			}
			WriterUtil.write(response, result.toString());
		}
	
	
	//柱状图
	@RequestMapping("tongji")
	public void tongji(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
			List<PersonVo> count  = personService.tongji();
			result.put("data", count);
			
		} catch (Exception e) {
			e.printStackTrace();
			logger.error("删除用户信息错误",e);
			result.put("errorMsg", "对不起，删除失败");
		}
		WriterUtil.write(response, result.toString());
	}
	
	//饼状图
	@RequestMapping("tongji1")
	public void tongji1(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			
			List<PersonVo> count  = personService.tongji1();
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
				userService.deleteUser(Integer.parseInt(id));
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
