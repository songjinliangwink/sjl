package com.xiaoshu.dao;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.ExpressCompany;

public interface ExpressCompanyMapper extends BaseMapper<ExpressCompany> {

	ExpressCompany findCname(String name);

	void findAddCname(ExpressCompany company2);
   
}