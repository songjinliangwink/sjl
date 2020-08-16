package com.xiaoshu.controller;

import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.log4j.Logger;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.config.util.ConfigUtil;
import com.xiaoshu.entity.Chengshi;
import com.xiaoshu.entity.Operation;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.Xuexiao;
import com.xiaoshu.service.OperationService;
import com.xiaoshu.service.RoleService;
import com.xiaoshu.service.UserService;
import com.xiaoshu.service.XueXiaoService;
import com.xiaoshu.util.StringUtil;
import com.xiaoshu.util.TimeUtil;
import com.xiaoshu.util.WriterUtil;
import com.xiaoshu.vo.XuexiaoVo;

@Controller
@RequestMapping("xuexiao")
public class XueXiaoController extends LogController{
	static Logger logger = Logger.getLogger(XueXiaoController.class);

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService ;
	
	@Autowired
	private OperationService operationService;
	
	@Autowired
	XueXiaoService xueXiaoService;
	
	
	@RequestMapping("xuexiaoIndex")
	public String index(HttpServletRequest request,Integer menuid) throws Exception{
		
		List<Operation> operationList = operationService.findOperationIdsByMenuid(menuid);
		request.setAttribute("operationList", operationList);
		request.setAttribute("ChengshiList", xueXiaoService.findChengshi());
		return "xuexiao";
	}
	
	
	@RequestMapping(value="xuexiaoList",method=RequestMethod.POST)
	public void userList(XuexiaoVo vo,HttpServletRequest request,HttpServletResponse response,String offset,String limit) throws Exception{
		try {
			Xuexiao xuexiao = new Xuexiao();
			
			String order = request.getParameter("order");
			String ordername = request.getParameter("ordername");
			
			
			Integer pageSize = StringUtil.isEmpty(limit)?ConfigUtil.getPageSize():Integer.parseInt(limit);
			Integer pageNum =  (Integer.parseInt(offset)/pageSize)+1;
			PageInfo<XuexiaoVo> userList= xueXiaoService.findUserPage(vo,pageNum,pageSize,ordername,order);
			
			
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
	public void reserveUser(HttpServletRequest request,Xuexiao xuexiao,HttpServletResponse response){
		Integer userId = xuexiao.getId();
		JSONObject result=new JSONObject();
		try {
			/*if (userId != null) {   // userId不为空 说明是修改
				User userName = userService.existUserWithUserName(user.getUsername());
				if(userName != null && userName.getUserid().compareTo(userId)==0){
					user.setUserid(userId);
					userService.updateUser(user);
					result.put("success", true);
				}else{
					result.put("success", true);
					result.put("errorMsg", "该用户名被使用");
				}
				
			}else*/ {   // 添加
				if(xueXiaoService.findByName(xuexiao.getSchoolname())==null){  // 没有重复可以添加
					xuexiao.setCreatetime(new Date());
					xueXiaoService.addXuexiao(xuexiao);
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
	
	
	@RequestMapping("import")
	public void importFile(HttpServletResponse response,MultipartFile file) {
		JSONObject result=new JSONObject();
		try {
			
			XSSFWorkbook wb = new XSSFWorkbook(file.getInputStream());
			
			XSSFSheet sheet = wb.getSheetAt(0);
			
			int lastRowNum = sheet.getLastRowNum();
						
			for (int i = 1; i <= lastRowNum; i++) {
				XSSFRow row = sheet.getRow(i);
				
				Xuexiao xx = new Xuexiao();
				
				int id = (int)row.getCell(0).getNumericCellValue();
				
				String schoolname = row.getCell(1).getStringCellValue();
				xx.setSchoolname(schoolname);
				
				row.getCell(3).setCellType(CellType.STRING);
				String phone = row.getCell(3).getStringCellValue();
				xx.setPhone(phone);
				
				String address = row.getCell(4).getStringCellValue();
				xx.setAddress(address);
				
				String createtime = row.getCell(6).getStringCellValue(); 
				xx.setCreatetime(TimeUtil.ParseTime(createtime, "yyyy-MM-dd"));
				
				String status = row.getCell(5).getStringCellValue();
				xx.setStatus(status);
				
				String aname = row.getCell(2).getStringCellValue();
				Chengshi chengshi = xueXiaoService.findByChengshi(aname);
				
				if(chengshi!=null) {
					xx.setAreaid(chengshi.getId());
					}else {
						xx.setAreaid(null);
					}
				
				xueXiaoService.addXuexiao(xx);
				}	
					
				wb.close();
				
				result.put("success", true);
			} catch (Exception e) {
			// TODO: handle exception
				e.printStackTrace();
				logger.error("导入分校信息错误",e);
				result.put("errorMsg", "导入分校信息错误");
		}
		WriterUtil.write(response, result.toString());
	}
	
	
	
	
	@RequestMapping("deleteUser")
	public void delUser(HttpServletRequest request,HttpServletResponse response){
		JSONObject result=new JSONObject();
		try {
			String[] ids=request.getParameter("ids").split(",");
			for (String id : ids) {
				xueXiaoService.deleteXuexiao(Integer.parseInt(id));
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