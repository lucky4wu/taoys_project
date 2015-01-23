package cn.taoys.utils;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.JProgressBar;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import cn.taoys.dao.PingDao;

public class PingTask extends SwingWorker<List<String>, String>{

	private static Logger logger = Logger.getLogger(PingTask.class);
	
	private String pingCommand;
	private String remoteNetAddress;
	private JTextArea textArea;
	private Integer pingTimes;
	private Integer timeOut = 1000;
	private String uname;
	private PingDao pingDao;
	private String toMail;
	private String fromMail;
	private String smtpHost;
	private boolean isDebug;
	private String fromMailPwd;
	private String subject;

	public PingTask(JTextArea textArea, String remoteNetAddress, Integer pingTimes, Integer timeOut, String uname, PingDao pingDao,
			String toMail, String fromMail, String smtpHost, boolean isDebug, String fromMailPwd, String subject){
		this.pingCommand = "ping " + remoteNetAddress  + " -n " + 1 ;
		this.remoteNetAddress = remoteNetAddress;
		this.textArea = textArea;
		this.pingTimes = pingTimes;
		this.timeOut = timeOut;
		this.uname = uname;
		this.pingDao = pingDao;
		this.toMail = toMail;
		this.fromMail = fromMail;
		this.smtpHost = smtpHost;
		this.isDebug = isDebug;
		this.fromMailPwd = fromMailPwd;
		this.subject = subject;
	}
	
	@Override
	protected List<String> doInBackground() throws Exception {
		CommandUtil cmdUtil = new CommandUtil();
		int count = 0;
		List<String> stdoutList = null;
		logger.debug("isCancelled()"+isCancelled());
		boolean flag = false;
		while(count < pingTimes && !isCancelled()){
			Integer exitVal = cmdUtil.executeCommand(pingCommand);
			logger.debug("ping task exitVal:"+exitVal);
			stdoutList = cmdUtil.getStdoutList();
			logger.debug("stdoutList.size()="+stdoutList.size());
			for(String outPub : stdoutList){
				logger.debug("publish string="+outPub);
				publish(outPub);
				
				Integer tmpCount = getRequestdTimeOut(outPub);
		    	if(tmpCount==0 && !flag ){
		    		//发送一封邮件提示
//		    		MailMsgSend mms = new MailMsgSend("wu_qingjian@126.com", "crazywu@126.com", "smtp.126.com", false, "iwnet0214");
		    		MailMsgSend mms = new MailMsgSend(toMail, fromMail, smtpHost, isDebug, fromMailPwd);
		    		String msgText = "远程网络地址为"+remoteNetAddress+"侦测到连接中断，请及时查看。本邮件由系统自动发出，请勿直接回复。谢谢！";
		    		mms.msgSend(subject, msgText);
		    		pingDao.save(remoteNetAddress, pingTimes, timeOut, uname);
		    		flag = true;
		    	}
		    	if(tmpCount==1 && flag){
		    		pingDao.save(remoteNetAddress, pingTimes, timeOut, uname);
		    		flag = false;
		    	}
		    	
			}
			count++;
			String minute = (pingTimes-count)*timeOut/1000/60+"";
			publish("大约剩余 "+ minute +"分钟...");
			setProgress(count*100/pingTimes);
			Thread.sleep(timeOut);
		}
		
		return stdoutList;
	}

	@Override
	protected void process(List<String> chunks){
		for(String line : chunks){
			logger.debug("process line ="+ line);
			textArea.append(line+ "\n");
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
	
	private static int getRequestdTimeOut(String line) {  
		Pattern pattern = Pattern.compile("无法访问目标主机",
				Pattern.CASE_INSENSITIVE);  
		Matcher matcher = pattern.matcher(line); 
		while (matcher.find()) {  
			return 1;  
		}  
		return 0; 
	}
	
	
	public static void main(String[] args) {
		final JProgressBar progressBar = new JProgressBar(0,100);
		JTextArea textArea = new JTextArea();
		String pingCommand = "ping -n 10 www.baidu.com";
		/*PingTask task = new PingTask(textArea, pingCommand);
		task.addPropertyChangeListener(new PropertyChangeListener() {
			
			@Override
			public void propertyChange(PropertyChangeEvent evt) {
				String propertyName = evt.getPropertyName();
				System.out.println("propertyName="+propertyName);
//				progressBar.setValue(n);
//				String evtVal = (String)evt.getNewValue();
//				System.out.println("evtVal:"+evtVal);
			}
		});
		task.execute();
		try {
			for(String get : task.get()){
				System.out.println("get:"+get);
			}
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ExecutionException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}*/
	}
	
}
