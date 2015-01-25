package cn.taoys.entity;

import java.io.Serializable;

public class PingRecord implements Serializable{

	private static final long serialVersionUID = 1085827368559142354L;

	private Integer id;
	private String uname;
	private String email;
	private String ipAddress;
	private String timeOut;
	private String pingTimes;
	private String createTime;
	private String status;//1、开始，2、结束
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getUname() {
		return uname;
	}
	public void setUname(String uname) {
		this.uname = uname;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getIpAddress() {
		return ipAddress;
	}
	public void setIpAddress(String ipAddress) {
		this.ipAddress = ipAddress;
	}
	public String getTimeOut() {
		return timeOut;
	}
	public void setTimeOut(String timeOut) {
		this.timeOut = timeOut;
	}
	public String getPingTimes() {
		return pingTimes;
	}
	public void setPingTimes(String pingTimes) {
		this.pingTimes = pingTimes;
	}
	public String getCreateTime() {
		return createTime;
	}
	public void setCreateTime(String createTime) {
		this.createTime = createTime;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	
	@Override
	public String toString(){
		return "ID："+id.toString()+" "+"用户名："+uname+" "
				+"IP地址："+ipAddress+" " + "次数："+pingTimes+" "
				+"超时间隔："+timeOut+" "+"创建时间："+createTime;
	}
	@Override
	public boolean equals(Object obj){
		if(obj==null)
			return false;
		if(this==obj)
			return true;
		if(obj instanceof PingRecord){
			PingRecord other = (PingRecord) obj;
			return this.id == other.id;
		}
		return false;
	}
	@Override
	public int hashCode(){
		return id;
	}
	
}
