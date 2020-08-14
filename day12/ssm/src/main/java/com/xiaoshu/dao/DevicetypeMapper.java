package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Devicetype;

public interface DevicetypeMapper extends BaseMapper<Devicetype> {
   

	void addType(Devicetype devicetype);
}