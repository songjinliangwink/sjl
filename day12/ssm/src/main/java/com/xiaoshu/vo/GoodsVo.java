package com.xiaoshu.vo;

import com.xiaoshu.entity.Goods;

public class GoodsVo  extends Goods{

	private String tname;
	
	private Integer num;

	public String getTname() {
		return tname;
	}

	public void setTname(String tname) {
		this.tname = tname;
	}

	public Integer getNum() {
		return num;
	}

	public void setNum(Integer num) {
		this.num = num;
	}
	
	
}
