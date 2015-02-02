package cn.taoys.ui;

import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.ArrayList;
import java.util.List;

import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;

import org.apache.log4j.Logger;

import cn.taoys.entity.PingRecord;
import cn.taoys.service.NetworkService;

public class TableFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6555173320259148091L;
	private static Logger logger = Logger.getLogger(TableFrame.class);
	private ClientContext clientContext;
	
	public TableFrame(){
		init();
	}

	private void init() {
		setSize(600, 400);
		setTitle("网络在线分析系统->详情表格");
		setContentPane(createContentPane());
		setLocationRelativeTo(rootPane);
		
		//setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		/*addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e){
				clientContext.exit(TableFrame.this);
			}
		});*/
	}

	private JPanel createContentPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH, createSearchConditionPane());
		p.add(BorderLayout.CENTER, createCenterPane());
		return p;
	}

	private JPanel createCenterPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER, createTablePane());
		return p;
	}

	List<PingRecord> pingList = new ArrayList<PingRecord>();
	private JPanel createTablePane() {
		JPanel p = new JPanel(new GridLayout(10, 1, 0, 6));
		//pingList = clientContext.findPingListByUname();
		logger.debug("prList size="+pingList.size());
		if(pingList != null && pingList.size() > 0){
			Integer count = 0;
			for(PingRecord pr : pingList){
				JLabel prLabel = new JLabel(pr.toString(), JLabel.CENTER);
				p.add(prLabel);
				count++;
				if(count > 10){
					break;
				}
			}
		}
		return p;
	}

	private JPanel createSearchConditionPane() {
		JPanel p = new JPanel(new BorderLayout());
		return p;
	}

	public void setClientContext(ClientContext clientContext) {
		this.clientContext = clientContext;
	}
	public void setPingList(List<PingRecord> pingList) {
		this.pingList = pingList;
	}

	public static void main(String[] args) {
		TableFrame tableFrame = new TableFrame();
		ClientContext clientContext = new ClientContext();
//		NetworkService networkService = new NetworkService();
		NetworkFrame networkFrame = new NetworkFrame();
		LoginFrame loginFrame = new LoginFrame();
		
		tableFrame.setClientContext(clientContext);
//		networkService.setNetworkFrame(networkFrame);
//		clientContext.setNetworkService(networkService);
		clientContext.setLoginFrame(loginFrame);
//		clientContext.setTableFrame(tableFrame);
		
		tableFrame.setVisible(true);
	}
	
}
