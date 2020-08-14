package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.github.pagehelper.util.StringUtil;
import com.xiaoshu.dao.DeviceMapper;
import com.xiaoshu.dao.DevicetypeMapper;
import com.xiaoshu.dao.UserMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.entity.Devicetype;
import com.xiaoshu.entity.User;
import com.xiaoshu.entity.UserExample;
import com.xiaoshu.entity.UserExample.Criteria;
import com.xiaoshu.vo.DeviceVo;

@Service
public class DeviceService {

	@Autowired
	DevicetypeMapper devicetypeMapper;
	
	@Autowired
	DeviceMapper deviceMapper;
	

	public List<Devicetype> findStatus() {
		// TODO Auto-generated method stub
		return devicetypeMapper.selectAll();
	}


	public PageInfo<DeviceVo> findUserPage(DeviceVo vo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<DeviceVo> list  = deviceMapper.findAll(vo);
		PageInfo<DeviceVo> info = new PageInfo<>(list);
		return info;
	}


	public Device existUserWithUserName(String devicename) {
		// TODO Auto-generated method stub
		List<Device> list  = deviceMapper.existUserWithUserName(devicename);
		return list.isEmpty()?null:list.get(0);
	}


	public void updateDevice(Device device) {
		// TODO Auto-generated method stub
		deviceMapper.updateByPrimaryKey(device);
	}


	public void addDevice(Device device) {
		// TODO Auto-generated method stub
		deviceMapper.insertSelective(device);
	}


	public List<DeviceVo> tongji() {
		// TODO Auto-generated method stub
		return deviceMapper.tongji();
	}


	public void addType(Devicetype devicetype) {
		// TODO Auto-generated method stub
		devicetypeMapper.addType(devicetype);
	}


	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		deviceMapper.deleteByPrimaryKey(parseInt);
	}


}
