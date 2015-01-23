package cn.taoys.entity;

import cn.taoys.utils.Config;

public class EntityContext {

	private Config config;
	
	public EntityContext(Config config){
		this.config = config;
	}
	
	public String getRemoteNetAddress(){
		return config.getString("DEFAULT_REMOTE_NET_ADDRESS");
	}
	
	public String getPingTimes(){
		return config.getString("DEFAULT_PING_TIMES");
	}
	
	public String getTimeOut(){
		return config.getString("DEFAULT_TIMEOUT");
	}
	
	public String getToMail(){
		return config.getString("TO_MAIL");
	}
	
	public String getFromMail(){
		return config.getString("FROM_MAIL");
	}
	
	public String getFromMailPwd(){
		return config.getString("FROM_MAIL_PWD");
	}
	
	public String getSmtpHost(){
		return config.getString("SMTP_HOST");
	}
	
	public boolean getIsDebug(){
		return Boolean.valueOf(config.getString("IS_DEBUG")).booleanValue();
	}
	
	public String getMailSubject(){
		return config.getString("MAIL_SUBJECT");
	}
}
