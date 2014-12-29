package cn.taoys.main;

import cn.taoys.dao.PingDao;
import cn.taoys.dao.UserDao;
import cn.taoys.entity.EntityContext;
import cn.taoys.service.NetworkService;
import cn.taoys.service.UserService;
import cn.taoys.ui.ClientContext;
import cn.taoys.ui.LoginFrame;
import cn.taoys.ui.MenuFrame;
import cn.taoys.ui.NetworkFrame;
import cn.taoys.ui.TableFrame;

public class Main {

	public static void main(String[] args) {
		EntityContext context = new EntityContext();
		
		LoginFrame loginFrame = new LoginFrame();
		MenuFrame menuFrame = new MenuFrame();
		NetworkFrame networkFrame = new NetworkFrame();
		TableFrame tableFrame = new TableFrame();
		
		ClientContext clientContext = new ClientContext();
		
		UserDao userDao = new UserDao();
		PingDao pingDao = new PingDao();
		
		UserService userService = new UserService();
		NetworkService networkService= new NetworkService();
		
		
		networkService.setNetworkFrame(networkFrame);
		networkService.setPingDao(pingDao);
		userService.setUserDao(userDao);
		
		clientContext.setLoginFrame(loginFrame);
		clientContext.setMenuFrame(menuFrame);
		clientContext.setNetworkFrame(networkFrame);
		clientContext.setTableFrame(tableFrame);
		
		clientContext.setEntityContext(context);
		clientContext.setUserService(userService);
		clientContext.setNetworkService(networkService);
		
		loginFrame.setClientContext(clientContext);
		menuFrame.setClientContext(clientContext);
		networkFrame.setClientContext(clientContext);
		tableFrame.setClientContext(clientContext);
		
		clientContext.show();
	}

}
