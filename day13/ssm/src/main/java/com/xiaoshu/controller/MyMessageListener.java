package com.xiaoshu.controller;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;

import com.alibaba.fastjson.JSONObject;
import com.xiaoshu.dao.MajorMapper;
import com.xiaoshu.entity.Major;

import redis.clients.jedis.Jedis;

public class MyMessageListener implements MessageListener{
	
	@Autowired
	private MajorMapper majorMapper;
	
	@Override
	public void onMessage(Message message) {
		// TODO Auto-generated method stub
		
		TextMessage textMessage=(TextMessage) message;
		try {
			String text = textMessage.getText();
			Major major = JSONObject.parseObject(text,Major.class);
			System.out.println("接受的消息++++++++++++"+major);
			
		
		} catch (JMSException e) {
			// TODO: handle exception
		}
	}
	
	

}
