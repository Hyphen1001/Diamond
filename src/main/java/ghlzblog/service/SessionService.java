package ghlzblog.service;

import ghlzblog.po.Session;
import ghlzblog.po.User;

public interface SessionService {
	String getsid();
	void AddSession(Session session);
	boolean isAvailable(String sid);
	void setSession(String sid);
	User getUser(String sid);
	Session findBySid(String sid);
	void DeleteSession(Long uid);
}
