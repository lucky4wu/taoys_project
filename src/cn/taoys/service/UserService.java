package cn.taoys.service;

import cn.taoys.dao.UserDao;
import cn.taoys.entity.User;

public class UserService {

	private UserDao userDao;
	
	
	public User login(String uname,String passwd) throws NameOrPwdException,Exception {
		try {
			userDao.loadUserByUname(uname);//加载用户数据
		} catch (Exception e) {
			e.printStackTrace();
			throw e;
		}
		User user = userDao.findUserByUname(uname);
		if(user == null){
			throw new NameOrPwdException("用户名不存在!");
		}
		if(user.getPasswd().equals(passwd)){
			return user;
		}
		throw new NameOrPwdException("密码错误!");
	}

	public void setUserDao(UserDao userDao){
		this.userDao = userDao;
	}
}
