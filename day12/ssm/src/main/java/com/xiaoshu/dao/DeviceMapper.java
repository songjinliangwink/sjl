package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Device;
import com.xiaoshu.vo.DeviceVo;

public interface DeviceMapper extends BaseMapper<Device> {
  
	List<DeviceVo> findAll(DeviceVo vo);

	List<Device> existUserWithUserName(String devicename);

	List<DeviceVo> tongji();
}