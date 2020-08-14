package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ExpressCompanyMapper;
import com.xiaoshu.dao.ExpressPersonMapper;
import com.xiaoshu.entity.ExpressCompany;
import com.xiaoshu.entity.ExpressPerson;
import com.xiaoshu.vo.PersonVo;

@Service
public class PersonService {

	@Autowired
	ExpressPersonMapper expressPersonMapper;
	
	@Autowired
	ExpressCompanyMapper companyMapper;

	public PageInfo<PersonVo> findUserPage(PersonVo vo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<PersonVo> list = expressPersonMapper.findAll(vo);
		PageInfo<PersonVo> info = new PageInfo<>(list);
		return info;
	}

	public List<ExpressCompany> findStatus() {
		// TODO Auto-generated method stub
		return companyMapper.selectAll();
	}

	public ExpressPerson existUserWithUserName(String experssName) {
		// TODO Auto-generated method stub
		List<ExpressPerson> list = expressPersonMapper.existUserWithUserName(experssName);
		return list.isEmpty()?null:list.get(0);
	}

	public void update(ExpressPerson expressPerson) {
		// TODO Auto-generated method stub
		expressPersonMapper.updateByPrimaryKey(expressPerson);
	}

	public void add(ExpressPerson expressPerson) {
		// TODO Auto-generated method stub
		expressPersonMapper.insert(expressPerson);
	}

	public List<PersonVo> tongji() {
		// TODO Auto-generated method stub
		return expressPersonMapper.tongji();
	}

	public List<PersonVo> tongji1() {
		// TODO Auto-generated method stub
		return expressPersonMapper.tongji1();
	}

	public ExpressCompany findCname(String name) {
		// TODO Auto-generated method stub
		return companyMapper.findCname(name);
	}

	public void addCname(ExpressCompany company2) {
		// TODO Auto-generated method stub
		companyMapper.findAddCname(company2);
	}

	public List<PersonVo> findExperssPersonAll(PersonVo personVo) {
		// TODO Auto-generated method stub
		return expressPersonMapper.findAll(personVo);
	}

	


}
