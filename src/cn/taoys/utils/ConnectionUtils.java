package cn.taoys.utils;

import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.Properties;

import org.apache.log4j.Logger;

/**
 * Oracle数据库连接工具包
 * @param uname 用户账户
 * @param passwd 用户密码
 * @author wuqingjian
 *
 */
public class ConnectionUtils {
	
	private static Logger logger = Logger.getLogger(ConnectionUtils.class);
	//连接属性
	private static String driver;
	private static String url;
	private static String uname;
	private static String passwd;
	
	static {
		try{
			//在类加载时初始化连接属性
			Properties props = new Properties();
			//通过文件流将属性文件的信息读入到Properties对象中
			InputStream is = ConnectionUtils.class.getClassLoader().getResourceAsStream("db.properties");
			props.load(is);
			driver = props.getProperty("driver");
			url = props.getProperty("url");
			uname = props.getProperty("uname");
			passwd = props.getProperty("passwd");
			Class.forName(driver);
		}catch(Exception e){
			logger.error(e.getMessage());
			throw new RuntimeException(e);
		}
	}
	
	public static Connection getConnection() throws Exception{
		Connection con = DriverManager.getConnection(url,uname,passwd);
		return con;
	}
}













