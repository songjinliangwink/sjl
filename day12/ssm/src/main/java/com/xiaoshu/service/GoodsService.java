package com.xiaoshu.service;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.xiaoshu.dao.GoodsMapper;
import com.xiaoshu.dao.GoodstypeMapper;
import com.xiaoshu.entity.Goods;
import com.xiaoshu.entity.Goodstype;
import com.xiaoshu.vo.GoodsVo;

@Service
public class GoodsService {

	@Autowired
	GoodsMapper goodsMapper;
	
	@Autowired
	GoodstypeMapper goodstypeMapper;

	public List<Goodstype> findGoods() {
		// TODO Auto-generated method stub
		return goodstypeMapper.selectAll();
	}

	public PageInfo<GoodsVo> findUserPage(GoodsVo vo, Integer pageNum, Integer pageSize, String ordername,
			String order) {
		// TODO Auto-generated method stub
		PageHelper.startPage(pageNum, pageSize);
		List<GoodsVo> list = goodsMapper.findAll(vo);
		PageInfo<GoodsVo> info = new PageInfo<>(list);
		
		return info;
	}

	public void deleteUser(int parseInt) {
		// TODO Auto-generated method stub
		goodsMapper.deleteByPrimaryKey(parseInt);
	}

	public void addUser(Goods goods) {
		// TODO Auto-generated method stub
		goodsMapper.insert(goods);
	}

	public List<GoodsVo> tongji() {
		// TODO Auto-generated method stub
		return goodsMapper.tongji();
	}


}
