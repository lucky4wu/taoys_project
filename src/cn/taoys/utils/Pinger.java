package cn.taoys.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.log4j.Logger;

import cn.taoys.dao.PingDao;
import cn.taoys.ui.NetworkFrame;

public class Pinger { 
	private static Logger logger = Logger.getLogger(Pinger.class);
	/** 
	 *  要ping的主机  
	 */ 
	private String remoteIpAddress;
	/** 
	 *  设置ping的次数  
	 */ 
	private final int pingTimes; 
	/** 
	  * 设置超时 
	  */ 
	private int timeOut; 
	/** 
	  * 构造函数 
	  * @param remoteIpAddress 
	  * @param pingTimes  
	  * @param timeOut 
	  */ 
	public Pinger(String remoteIpAddress, int pingTimes, int timeOut) {  
		super();  
		this.remoteIpAddress = remoteIpAddress;  
		this.pingTimes = pingTimes;  
		this.timeOut = timeOut; 
	}
	
	public String isSendEmail(){
		CommandUtil cmdUtil = new CommandUtil();
		String pingCommand = "ping " + remoteIpAddress + " -n " + pingTimes    + " -w " + timeOut;
		cmdUtil.executeCommand(pingCommand);
		printList(cmdUtil.getStdoutList());
		return "0";
	}
	public static void printList(List<String> list){
        for (String string : list) {
            System.out.println(string);
        }
    }
	/** 
	 *  测试是否能ping通  
	 *  @param server  
	 *  @param timeout  
	 *  @return  
	 */
	public boolean isReachable(NetworkFrame networkFrame, PingDao pingDao, String uname) {
	    BufferedReader in = null;  
	    Runtime r = Runtime.getRuntime();  // 将要执行的ping命令,此命令是windows格式的命令 
	    String pingCommand = "ping " + remoteIpAddress + " -n " + pingTimes    + " -w " + timeOut;  try {   // 执行命令并获取输出   
	    logger.debug(pingCommand);
	    Process p = r.exec(pingCommand);   
	    if (p == null) {    
	    	return false;   
	    }   
	    in = new BufferedReader(new InputStreamReader(p.getInputStream(), "gbk"));   // 逐行检查输出,计算类似出现=23ms TTL=62字样的次数   
	    int connectedCount = 0;   
	    String line = null;   
	    StringBuffer sb = new StringBuffer();
	    boolean flag = false;
	    while ((line = in.readLine()) != null) {  
	    	logger.debug("line="+line);
	    	sb.append(line).append("\n");
	    	Integer tmpCount = getCheckResult(line);
	    	if(tmpCount==0 && !flag ){
	    		pingDao.save(remoteIpAddress, pingTimes, timeOut, uname);
	    		flag = true;
	    	}
	    	if(tmpCount==1 && flag){
	    		pingDao.save(remoteIpAddress, pingTimes, timeOut, uname);
	    		flag = false;
	    	}
	    	connectedCount += tmpCount;   
	    }   // 如果出现类似=23ms TTL=62这样的字样,出现的次数=测试次数则返回真   
	    	networkFrame.updateConsole(sb.toString());
	    	return connectedCount == pingTimes;  
	    } catch (Exception ex) {   
	    	logger.error(ex.getMessage());
	    	return false;  
	    } finally {   
	    	try {    
	    		in.close();   
	    	} catch (IOException e) {
	    		logger.error(e.getMessage());
	    	}  
	    } 
	}

  	/**  
  	 * 若line含有=18ms TTL=16字样,说明已经ping通,返回1,否則返回0.   
  	 * @param line  * @return  
  	 */ 
	private static int getCheckResult(String line) {  
		// System.out.println("控制台输出的结果为:"+line);  
		Pattern pattern = Pattern.compile("(\\d+ms)(\\s+)(TTL=\\d+)",
				Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(line); 
		while (matcher.find()) {  
			return 1;  
		}  
		return 0; 
	}
	
	public static void main(String[] args) {  
		Pinger p = new Pinger("www.baidu.com", 10, 1000);  
		//System.out.println(p.isReachable()); 
	}
	
}