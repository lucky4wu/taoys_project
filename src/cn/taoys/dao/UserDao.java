package cn.taoys.dao;


import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;

import org.apache.log4j.Logger;

import cn.taoys.entity.User;
import cn.taoys.utils.ConnectionUtils;


/**
 * 用户数据读取到内存
 * @author wuqingjian
 *
 */
public class UserDao {

	private static Logger logger = Logger.getLogger(UserDao.class);
	
	private HashMap<String, User> users = new HashMap<String, User>();
	public User findUserByUname(String uname){
		return users.get(uname);
	}
	private static final String sql_findUserByUname = 
		"select * from taoys_user where uname =?";
	public void loadUserByUname(String uname) throws Exception{
		Connection con = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		try{
			con = ConnectionUtils.getConnection();
			stmt = con.prepareStatement(sql_findUserByUname);
			stmt.setString(1,uname);
			rs = stmt.executeQuery();
			if(rs.next()){
//				System.out.println(rs.getInt(1));
				User user = new User();
				user.setId(rs.getInt(1));
				user.setUname(rs.getString(2));
				user.setRealname(rs.getString(3));
				user.setPasswd(rs.getString(4));
				users.put(user.getUname(), user);
			}
		}catch(Exception e){
			logger.error(e.getMessage());
			throw e;
		}finally{
			if(rs != null)
				rs.close();
			if(stmt != null)
				stmt.close();
			if(con != null)
				con.close();
		}
	}
	public static void main(String[] args) throws Exception{
		UserDao userDao = new UserDao();
		userDao.loadUserByUname("taoyisong");
		System.out.println(userDao.users);
	}
}
