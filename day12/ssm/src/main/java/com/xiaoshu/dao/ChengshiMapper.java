package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Chengshi;

public interface ChengshiMapper extends BaseMapper<Chengshi> {
   
	Chengshi findByChengshi(String aname);
}