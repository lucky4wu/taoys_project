package cn.taoys.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import cn.taoys.service.NetworkService;

public class NetworkFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1160576022577830141L;
	private ClientContext clientContext;
	private JTextField netAddressField;
	private JTextArea console;
	private JLabel message;
	private JTextField pingTimesField;
	private JTextField timeOutField;
	
	public NetworkFrame(){
		init();
	}
	
	private void init() {
		setSize(600,400);
		setTitle("网络在线分析系统->网络监听...");
		setContentPane(createContentPane());
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				clientContext.exit(NetworkFrame.this);
			}
		});
	}

	private JPanel createContentPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH, createTopPane());
		p.add(BorderLayout.CENTER, createCenterPane());
		return p;
	}

	private JPanel createCenterPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER, createScrollPane());
		p.add(BorderLayout.SOUTH, createBackBtnPane());
		return p;
	}

	private JPanel createBackBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton backMenu = new JButton("返回");
		//JButton details = new JButton("显示最近的断点>>");
		p.add(backMenu);
		//p.add(details);
		
		backMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.backMenu(NetworkFrame.this);
			}
		});
		/*details.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.details();
			}
		});*/
		return p;
	}

	private JScrollPane createScrollPane() {
		JScrollPane p = new JScrollPane();
		p.setBorder(new TitledBorder("Console"));
		console = new JTextArea();
		console.setText("  ");
		console.setLineWrap(true);
		console.setEditable(false);
		p.getViewport().add(console);
		return p;
	}

	private JPanel createTopPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.CENTER, createTextPane());
		p.add(BorderLayout.EAST, createBtnPane());
		message = new JLabel("", JLabel.CENTER);
		p.add(BorderLayout.SOUTH, message);
		return p;
	}

	private JPanel createTextPane() {
		JPanel p = new JPanel(new BorderLayout(6,0));
		p.add(BorderLayout.WEST, new JLabel("IP地址/域名：PING ", JLabel.CENTER));
		netAddressField = new JTextField();
		p.add(BorderLayout.CENTER, netAddressField);
		p.add(BorderLayout.SOUTH, createOptionPane());
		return p;
	}

	
	
	private JPanel createOptionPane() {
		JPanel p = new JPanel(new GridLayout(1,4,0,6));
		JLabel pingTimes = new JLabel("次数：", JLabel.CENTER);
		JLabel timeOut = new JLabel("超时间隔(ms)：", JLabel.CENTER);
		pingTimesField = new JTextField();
		timeOutField = new JTextField();
		
		p.add(pingTimes);
		p.add(pingTimesField);
		p.add(timeOut);
		p.add(timeOutField);
		
		return p;
	}

	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton ping = new JButton("ping");
		JButton stop = new JButton("stop");
		
		p.add(ping);
		p.add(stop);
		
		ping.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.ping();
			}
		});
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.stopPing();
			}
		});
		return p;
	}
	
	public String getNetAddress(){
		return new String(netAddressField.getText());
	}
	public void updateConsole(String str){
		console.setText(str);
	}
	public void showMessage(String message){
		this.message.setText(message);
	}
	public void setClientContext(ClientContext clientContext){
		this.clientContext = clientContext;
	}
	public String getPingTimes(){
		return new String(pingTimesField.getText());
	}
	public String getTimeOut(){
		return new String(timeOutField.getText());
	}
	
	public static void main(String[] args) {
		NetworkFrame networkFrame = new NetworkFrame();
		ClientContext clientContext = new ClientContext();
		NetworkService networkService= new NetworkService();
		LoginFrame loginFrame = new LoginFrame();
		
		clientContext.setNetworkFrame(networkFrame);
		clientContext.setLoginFrame(loginFrame);
		networkFrame.setClientContext(clientContext);
		clientContext.setNetworkService(networkService);
		networkService.setNetworkFrame(networkFrame);
		
		networkFrame.setVisible(true);
		
		System.out.println("netaddress="+networkFrame.getNetAddress());
	}
}
