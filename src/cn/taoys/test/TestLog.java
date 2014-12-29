package cn.taoys.test;

import org.apache.log4j.Logger;

public class TestLog {

	private static Logger logger = Logger.getLogger(TestLog.class);
	
	public static void main(String[] args) {
		System.out.println("log begin...");
		String message = "message";
		logger.info(message);
		String debug = "debug";
		logger.debug(debug);
		String error = "error";
		logger.error(error);
	}
}
