package cn.taoys.service;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

import org.apache.log4j.Logger;

import cn.taoys.dao.PingDao;
import cn.taoys.entity.PingRecord;
import cn.taoys.ui.NetworkFrame;
import cn.taoys.utils.Pinger;

public class NetworkService {

	private static Logger logger = Logger.getLogger(NetworkService.class);
	
	private NetworkFrame networkFrame;
	private PingDao pingDao;
	
	public void ping(String remoteIpAddress, Integer pingTimes, Integer timeOut, String uname) {
		Pinger p = new Pinger(remoteIpAddress, pingTimes, timeOut);
		boolean isReachable = p.isReachable(networkFrame, pingDao, uname);
		logger.info("isReachable="+isReachable);
		/*
		if(!isReachable){
			try {
				pingDao.save(remoteIpAddress, pingTimes, timeOut, uname);
			} catch (Exception e) {
				logger.error(e.getMessage());
			}
		}
		*/
	}
	
	public void stopPing() {
		Runtime runtime = Runtime.getRuntime(); 
		runtime.exit(1);
	}

	public List<PingRecord> findDetailsByUname(String uname) {
		List<PingRecord> prList = pingDao.findPingListByUname(uname);
		return prList;
	}
	
	public void ping(String netAddress) {
		Runtime runtime = Runtime.getRuntime(); // 获取当前程序的运行进对象
		Process process = null; // 声明处理类对象
		String line = null; // 返回行信息
		InputStream is = null; // 输入流
		InputStreamReader isr = null; // 字节流
		BufferedReader br = null;
		//String ip = "www.baidu.com";
		boolean res = false;// 结果
		try {
		    process = runtime.exec("ping " + netAddress); // PING
		    is = process.getInputStream(); // 实例化输入流
		    isr = new InputStreamReader(is, "gbk");// 把输入流转换成字节流
		    br = new BufferedReader(isr);// 从字节中读取文本
		    StringBuffer sb = new StringBuffer();
		    while ((line = br.readLine()) != null) {
		    	sb.append(line).append("\n");
		    	logger.debug("line="+line);
		    	if (line.contains("TTL")) {
		    		res = true;
		    		continue;
		    	}
		    }
		    networkFrame.updateConsole(sb.toString());
		    is.close();
		    isr.close();
		    br.close();
		    if (res) {
		    	System.out.println("ping 通  ...");
		    } else {
		    	System.out.println("ping 不通...");
		    }
		} catch (IOException e) {
		    System.out.println(e);
		    runtime.exit(1);
	    }
		
	}


	public void setNetworkFrame(NetworkFrame networkFrame) {
		this.networkFrame = networkFrame;
	}
	
	public void setPingDao(PingDao pingDao){
		this.pingDao = pingDao;
	}
	
	public static void main(String[] args) {
		NetworkService networkService = new NetworkService();
		String netAddress = "www.baidu.com";
		networkService.ping(netAddress);
	}

	


	

	
}
