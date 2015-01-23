package cn.taoys.main;

import org.apache.log4j.Logger;

import cn.taoys.dao.PingDao;
import cn.taoys.dao.UserDao;
import cn.taoys.entity.EntityContext;
import cn.taoys.service.NetworkService;
import cn.taoys.service.UserService;
import cn.taoys.ui.ClientContext;
import cn.taoys.ui.LoginFrame;
import cn.taoys.ui.MenuFrame;
import cn.taoys.ui.NetworkFrame;
import cn.taoys.utils.Config;

public class Main {

	private static Logger logger = Logger.getLogger(Main.class);
	
	public static void main(String[] args) {
		try{
			//创建所有对象,注入,解决依赖关系
			Config config = new Config("client.properties");
			EntityContext context = new EntityContext(config);
			
			LoginFrame loginFrame = new LoginFrame();
			MenuFrame menuFrame = new MenuFrame();
			NetworkFrame networkFrame = new NetworkFrame(context);
			
			ClientContext clientContext = new ClientContext();
			
			UserDao userDao = new UserDao();
			PingDao pingDao = new PingDao();
			
			UserService userService = new UserService();
			NetworkService networkService= new NetworkService(context);
			
			clientContext.setLoginFrame(loginFrame);
			clientContext.setMenuFrame(menuFrame);
			clientContext.setNetworkFrame(networkFrame);
			
			networkService.setPingDao(pingDao);
			userService.setUserDao(userDao);
			
			clientContext.setLoginFrame(loginFrame);
			clientContext.setMenuFrame(menuFrame);
			clientContext.setNetworkFrame(networkFrame);
			
			clientContext.setEntityContext(context);
			clientContext.setUserService(userService);
			clientContext.setNetworkService(networkService);
			
			loginFrame.setClientContext(clientContext);
			menuFrame.setClientContext(clientContext);
			networkFrame.setClientContext(clientContext);
			
			
			clientContext.show();
		}catch(RuntimeException re){
			logger.error(re);
		}catch(Exception e){
			logger.error(e);
		}
	}

}
