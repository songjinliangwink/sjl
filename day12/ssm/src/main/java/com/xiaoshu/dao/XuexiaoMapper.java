package com.xiaoshu.dao;

import java.util.List;

import com.xiaoshu.base.dao.BaseMapper;
import com.xiaoshu.entity.Xuexiao;
import com.xiaoshu.vo.XuexiaoVo;

public interface XuexiaoMapper extends BaseMapper<Xuexiao> {
   
	List<XuexiaoVo> findAll(XuexiaoVo vo);


	List<Xuexiao> findByName(String schoolname);
}