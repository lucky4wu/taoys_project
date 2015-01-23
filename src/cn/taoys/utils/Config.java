package cn.taoys.utils;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

/**
 * 读取系统的配置文件
 * @author wuqingjian
 *
 */
public class Config {
	private Properties table = new Properties();
	
	public Config(String file){
		try{
			table.load(new FileInputStream(file));
		}catch(IOException e){
			e.printStackTrace();
			throw new RuntimeException(e);
		}
	}
	public int getInt(String key){
		return Integer.parseInt(table.getProperty(key));
	}
	public String getString(String key){
		return table.getProperty(key);
	}
	public double getDouble(String key){
		return Double.parseDouble(table.getProperty(key));
	}
	
	public static void main(String[] args) {
		Config config = new Config("client.properties");
		String DEFAULT_REMOTE_NET_ADDRESS = config.getString("DEFAULT_REMOTE_NET_ADDRESS");
		System.out.println(DEFAULT_REMOTE_NET_ADDRESS);
	}
}
