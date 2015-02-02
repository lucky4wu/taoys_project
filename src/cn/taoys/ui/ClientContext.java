package cn.taoys.ui;

import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JOptionPane;
import javax.swing.JTextArea;
import javax.swing.SwingWorker;

import org.apache.log4j.Logger;

import cn.taoys.entity.EntityContext;
import cn.taoys.entity.PingRecord;
import cn.taoys.entity.User;
import cn.taoys.service.NameOrPwdException;
import cn.taoys.service.NetworkService;
import cn.taoys.service.UserService;
import cn.taoys.utils.PingTask;

public class ClientContext {
	
	private static Logger logger = Logger.getLogger(ClientContext.class);
	
	private EntityContext context;
	private LoginFrame loginFrame;
	private MenuFrame menuFrame;
	private NetworkFrame networkFrame;
	private MultiPingFrame multiPingFrame;
	private UserService userService;
	private NetworkService networkService;
	
	/**
	 * 显示软件的开始界面, 先显示登录界面 show()方法的运行, 必须依赖于 loginFrame变量引用 具体的界面对象,
	 * 这个界面对象必须依赖方法setLoginFrame() 注入, 注入这个界面对象实例!
	 **/
	public void show(){
		loginFrame.setVisible(true);
	}
	
	public void login() {
		try {
			String uname = loginFrame.getUname();
			String passwd = loginFrame.getPassword();
			User user = userService.login(uname, passwd);
			menuFrame.updateView(user);
			loginFrame.setVisible(false);
			menuFrame.setVisible(true);
		} catch (NameOrPwdException e) {
			logger.error(e.getMessage());
			loginFrame.showMessage("用户名/密码错误："+e.getMessage());
		} catch (Exception e) {
			logger.error(e.getMessage());
			loginFrame.showMessage("登陆失败："+e.getMessage());
		}
	}

	/**
	 * 退出系统控制逻辑
	 * @param source 代表从哪一个界面退出，是一个窗口的引用
	 */
	public void exit(JFrame source) {
		int val = JOptionPane.showConfirmDialog(source, "离开吗？");
		logger.debug("exit val="+val);
		if(val == JOptionPane.YES_OPTION){
			System.exit(0);//结束当前java进程
		}
	}
	public void logout(JFrame source) {
		int val = JOptionPane.showConfirmDialog(source, "注销当前用户吗？");
		logger.debug("logout val="+val);
		if(val == JOptionPane.YES_OPTION){
			menuFrame.setVisible(false);
			loginFrame.setVisible(true);
			//TODO 加入重置清空登陆界面数据的方法
		}
	}
	public void analysis() {
		menuFrame.setVisible(false);
		networkFrame.setVisible(true);
	}
	
	public PingTask pingTask(PingTask task, JTextArea console){
		String netAddress = networkFrame.getNetAddress();
		String pingTimesField = networkFrame.getPingTimes();
		String timeOutField = networkFrame.getTimeOut();
		if(netAddress == null || "".equals(netAddress)){
			networkFrame.showMessage("IP地址/域名不能为空");
		}else{
			networkFrame.showMessage("");
		}
		if(pingTimesField == null || "".equals(pingTimesField)){
			pingTimesField = "1";
		}
		if(timeOutField == null || "".equals(timeOutField)){
			timeOutField = "1000";
		}
		Integer pingTimes = Integer.parseInt(pingTimesField);
		Integer timeOut = Integer.parseInt(timeOutField);
		String uname = loginFrame.getUname();
		
//		String pingCommand = "ping " + netAddress + " -n " + pingTimes    + " -w " + timeOut;
		
		task = networkService.pingTask(task, console, netAddress, pingTimes, timeOut, uname);
		
		return task;
	}
	
	/**
	 * 
	 */
	public void ping() {
		try{
			String netAddress = networkFrame.getNetAddress();
			String pingTimesField = networkFrame.getPingTimes();
			String timeOutField = networkFrame.getTimeOut();
			if(netAddress == null || "".equals(netAddress)){
				networkFrame.showMessage("IP地址/域名不能为空");
			}else{
				networkFrame.showMessage("");
			}
			if(pingTimesField == null || "".equals(pingTimesField)){
				pingTimesField = "4";
			}
			if(timeOutField == null || "".equals(timeOutField)){
				timeOutField = "1000";
			}
			Integer pingTimes = Integer.parseInt(pingTimesField);
			Integer timeOut = Integer.parseInt(timeOutField);
			String uname = loginFrame.getUname();
			networkService.ping(netAddress, pingTimes, timeOut, uname);
		}catch(Exception e){
			logger.error(e);
			e.printStackTrace();
		}
		
	}
	public void stopPing() {
		networkService.stopPing();
		
	}
	
	/*public void details() {
		this.findPingListByUname();
		tableFrame.setVisible(true);
	}
	
	public void findPingListByUname() {
		String uname = loginFrame.getUname();
		List<PingRecord> prList = networkService.findDetailsByUname(uname);
		tableFrame.setPingList(prList);
	}
*/
	
	public void backMenu(JFrame source) {
		source.setVisible(false);
		menuFrame.setVisible(true);
	}

	public void setEntityContext(EntityContext context){
		this.context = context;
	}
	public void setLoginFrame(LoginFrame loginFrame){
		this.loginFrame = loginFrame;
	}
	public void setMenuFrame(MenuFrame menuFrame){
		this.menuFrame = menuFrame;
	}
	public void setNetworkFrame(NetworkFrame networkFrame){
		this.networkFrame = networkFrame;
	}
	public void setMultiPingFrame(MultiPingFrame multiPingFrame) {
		this.multiPingFrame = multiPingFrame;
	}

	public void setUserService(UserService userService){
		this.userService = userService;
	}
	public void setNetworkService(NetworkService networkService){
		this.networkService = networkService;
	}

	
	

	

	
}
