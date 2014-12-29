package cn.taoys.entity;

import java.io.Serializable;

/** 用户实体 
 *  @param id 用户序号
 *  @param uname 用户账户
 *  @param realname 用户姓名
 *  @param passwd 用户密码
 *  @param phone 用户手机
 *  @param email 用户电子邮件
 *  @author Wu
 * */
public class User implements Serializable{
	private static final long serialVersionUID = 922446085569210730L;
	private int id;
	private String uname;
	private String realname;
	private String passwd;
	private String phone;
	private String email;
	
	public User(){
		super();
	}
	public User(int id, String uname, String realname, String passwd, String phone, String email){
		super();
		this.id = id;
		this.uname = uname;
		this.realname = realname;
		this.passwd = passwd;
		this.phone = phone;
		this.email = email;
	}
	public int getId(){
		return id;
	}
	public void setId(int id){
		this.id = id;
	}
	public String getUname(){
		return uname;
	}
	public void setUname(String uname){
		this.uname = uname;
	}
	public String getRealname(){
		return realname;
	}
	public void setRealname(String realname){
		this.realname = realname;
	}
	public String getPasswd(){
		return passwd;
	}
	public void setPasswd(String passwd){
		this.passwd = passwd;
	}
	public String getPhone(){
		return phone;
	}
	public void setPhone(String phone){
		this.phone = phone;
	}
	public String getEmail(){
		return email;
	}
	public void setEmail(String email){
		this.email = email;
	}
	
	@Override
	public String toString(){
		return realname;
	}
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(this==obj)
			return true;
		if(obj instanceof User){
			User other = (User) obj;
			return this.id == other.id;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
}










