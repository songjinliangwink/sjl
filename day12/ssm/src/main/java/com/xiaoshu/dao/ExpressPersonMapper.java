package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.ExpressPerson;
import com.xiaoshu.vo.PersonVo;

public interface ExpressPersonMapper extends BaseMapper<ExpressPerson> {

	List<PersonVo> findAll(PersonVo vo);

	List<ExpressPerson> existUserWithUserName(String experssName);

	List<PersonVo> tongji();

	List<PersonVo> tongji1();
    
}