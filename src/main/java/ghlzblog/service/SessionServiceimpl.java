package ghlzblog.service;

import java.util.Date;
import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import ghlzblog.dao.SessionRepository;
import ghlzblog.dao.UserRepository;
import ghlzblog.po.Session;
import ghlzblog.po.User;

@Service
public class SessionServiceimpl implements SessionService{
	@Autowired
	private SessionRepository sessionRepository;
	@Autowired
	private UserRepository userRepository;
	@Override
	public String getsid() {
		String str="abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		  Random random1=new Random();
		  //指定字符串长度，拼接字符并toString
		  StringBuffer sb=new StringBuffer();
		  for (int i = 0; i < 10; i++) {
		 //获取指定长度的字符串中任意一个字符的索引值
		  int number=random1.nextInt(str.length());   
		  //根据索引值获取对应的字符
		  char charAt = str.charAt(number);
		      sb.append(charAt);
		  }
		  String str1 = new String(sb);
		  return str1;
	}
	@Override
	public void AddSession(Session session) {
		sessionRepository.saveAndFlush(session);
		return;
		
	}
	@Override
	public boolean isAvailable(String sid) {
		Session session = sessionRepository.findBySid(sid);
		if(session == null) {
			return false;
		}
		Long uid = session.getUid();
		User user = userRepository.findByUid(uid);
		if(user==null){
			return false;
		}
		Date begin=session.getTime();
		Date end = new Date();
		long between=(end.getTime()-begin.getTime())/1000;//除以1000是为了转换成秒
		long minute1=between%3600/60;
		if(minute1>=60)
			return false;
		return true;
	}
	@Override
	public void setSession(String sid) {
		Date nowDate = new Date();
		Session session = sessionRepository.findBySid(sid);
		session.setTime(nowDate);
		sessionRepository.saveAndFlush(session);
		return;
	}
	@Override
    public User getUser(String sid) {
		Session session = sessionRepository.findBySid(sid);
		Long uid = session.getUid();
		User user = userRepository.findByUid(uid);
		return user;
	}
	@Override
	public Session findBySid(String sid) {
		return sessionRepository.findBySid(sid);
	}
	@Override
	public void DeleteSession(Long uid) {
		Session session = sessionRepository.findByUid(uid);
		if(session!=null)
			sessionRepository.delete(session);
	}
}
