package com.xiaoshu.service;

import java.util.List;

import javax.jms.Destination;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONObject;

import com.xiaoshu.dao.MajorMapper;

import com.xiaoshu.entity.Major;

import redis.clients.jedis.Jedis;


@Service
public class MajorService {

	@Autowired
	MajorMapper majorMapper;

	@Autowired
	private JmsTemplate jmsTemplate;
	@Autowired
	private  Destination queueTextDestination;
	
	public List<Major> findMajor() {
		// TODO Auto-generated method stub
		return majorMapper.selectAll();
	}

	
	
	//添加部门
	public void addMajor(final Major major) {
		// TODO Auto-generated method stub
		majorMapper.insert(major);
		
		//放到redis中
		Jedis jedis = new Jedis("127.0.0.1",6379);
		Major m = findzzz(major.getmName());
		jedis.set(m.getmId()+"", m.toString());
		
		jmsTemplate.send(queueTextDestination,new MessageCreator() {
			
			@Override
			public Message createMessage(Session session) throws JMSException {
				 String json = JSONObject.toJSONString(major);
				return session.createTextMessage(json);
			}
		});
	}

	public int findCidByMname(String mname) {
		Major major =new Major();
		major.setmName(mname);
		Major one = majorMapper.selectOne(major);
		return one.getmId();
	}

	public Major findzzz(String mName){
		Major major = new Major();
		major.setmName(mName);
		return majorMapper.selectOne(major);
		
	}

}
