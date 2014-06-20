package com.allthelucky.memo.persistence;

import java.util.List;
import java.util.Map;

import com.allthelucky.memo.domain.User;

public interface UserMapper {
	
	public void addUser(User user);

	public void udpateUser(User user);

	public List<User> checkUser(User user);

	public int getCount();

	public List<User> getUsers(Map<String, Integer> map);
	
}
