package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.ChengshiMapper;
import com.xiaoshu.dao.XuexiaoMapper;
import com.xiaoshu.entity.Chengshi;
import com.xiaoshu.entity.Xuexiao;
import com.xiaoshu.vo.XuexiaoVo;

@Service
public class XueXiaoService {

	@Autowired
	XuexiaoMapper xuexiaoMapper;
	
	@Autowired
	ChengshiMapper chengshiMapper;

	public PageInfo<XuexiaoVo> findUserPage(XuexiaoVo vo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<XuexiaoVo> list = xuexiaoMapper.findAll(vo);
		PageInfo<XuexiaoVo> info = new PageInfo<>(list);
		return info;
	}

	public Xuexiao findByName(String schoolname) {
		// TODO Auto-generated method stub
		List<Xuexiao> list = xuexiaoMapper.findByName(schoolname);
		return list.isEmpty()?null:list.get(0);
	}

	public void addXuexiao(Xuexiao xuexiao) {
		// TODO Auto-generated method stub
		xuexiaoMapper.insert(xuexiao);
	}

	public List<Chengshi> findChengshi() {
		// TODO Auto-generated method stub
		return chengshiMapper.selectAll();
	}

	public void deleteXuexiao(int id) {
		// TODO Auto-generated method stub
		xuexiaoMapper.deleteByPrimaryKey(id);
	}

	public Chengshi findByChengshi(String aname) {
		// TODO Auto-generated method stub
		return chengshiMapper.findByChengshi(aname);
	}


}
