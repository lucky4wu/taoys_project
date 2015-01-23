package cn.taoys.ui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.FlowLayout;
import java.awt.GraphicsConfiguration;
import java.awt.GridLayout;
import java.awt.HeadlessException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.lang.reflect.InvocationTargetException;
import java.util.concurrent.ExecutionException;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.border.TitledBorder;

import org.apache.log4j.Logger;

import cn.taoys.entity.EntityContext;
import cn.taoys.service.NetworkService;
import cn.taoys.utils.PingTask;

public class NetworkFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = -1160576022577830141L;
	
	private static Logger logger = Logger.getLogger(NetworkFrame.class);
	
	private ClientContext clientContext;
	private JTextField netAddressField;
	private JTextArea console = new JTextArea();
	final JProgressBar progressBar = new JProgressBar(0,100);
	private JLabel message;
	private JTextField pingTimesField;
	private JTextField timeOutField;
	private PingTask task;
	
	
	
	public NetworkFrame()  {
	}

	public NetworkFrame(EntityContext context){
		init();
		setNetAddressField(context.getRemoteNetAddress());
		setPingTimesField(context.getPingTimes());
		setTimeOutField(context.getTimeOut());
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
		p.add(BorderLayout.NORTH, createProgressBarPane());
		p.add(BorderLayout.CENTER, createScrollPane());
		p.add(BorderLayout.SOUTH, createBackBtnPane());
		return p;
	}

	private JPanel createBackBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton backMenu = new JButton("backMenu");
		//JButton details = new JButton("显示最近的断点>>");
		JButton clear = new JButton("clear");
		p.add(backMenu);
		p.add(clear);
		//p.add(details);
		
		backMenu.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				clientContext.backMenu(NetworkFrame.this);
			}
		});
		clear.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				console.setText("");
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
//		console = new JTextArea();
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

	private JPanel createProgressBarPane() {
		JPanel p = new JPanel(new BorderLayout(6,0));
		
		p.add(BorderLayout.CENTER, progressBar);
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
		final JButton ping = new JButton("ping");
		JButton stop = new JButton("stop");
		
		p.add(ping);
		p.add(stop);
		
		
		ping.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
				task = clientContext.pingTask(task, console);
				
				task.addPropertyChangeListener(new PropertyChangeListener() {
					
					@Override
					public void propertyChange(PropertyChangeEvent evt) {
						String propertyName = evt.getPropertyName();
						logger.debug("propertyName="+propertyName);
						if("progress".equals(propertyName)){
							Integer evtVal = (Integer)evt.getNewValue();
							logger.debug("evtVal="+evtVal);
							progressBar.setValue(evtVal);
						}
						if("state".equals(propertyName)){
							if(task.isDone()){
								ping.setEnabled(true);
							}
						}
					}
				});
				
				task.execute();
				
				EventQueue.invokeLater(new Runnable(){
					@Override
					public void run() {
						ping.setEnabled(false);
					}
				});
			}
			
			
		});
		stop.addActionListener(new ActionListener(){
			@Override
			public void actionPerformed(ActionEvent e) {
//				clientContext.stopPing();
				if(!task.isCancelled() && !task.isDone()){
					boolean cancel = task.cancel(true);
					logger.debug("cancel="+cancel);
					EventQueue.invokeLater(new Runnable(){
						@Override
						public void run() {
							ping.setEnabled(true);
						}
					});
				}
			}
		});
		
		return p;
	}
	
	public void setNetAddressField(String netAddress) {
		this.netAddressField.setText(netAddress);
	}

	public void setPingTimesField(String pingTimesStr) {
		this.pingTimesField.setText(pingTimesStr);
	}

	public void setTimeOutField(String timeOutStr) {
		this.timeOutField.setText(timeOutStr);
	}

	public String getNetAddress(){
		return new String(netAddressField.getText());
	}
	public void updateConsole(String str){
		console.setText(str);
	}
	
	public JTextArea getConsole() {
		return console;
	}

	public void setConsole(JTextArea console) {
		this.console = console;
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
//		NetworkService networkService= new NetworkService();
		LoginFrame loginFrame = new LoginFrame();
		
		clientContext.setNetworkFrame(networkFrame);
		clientContext.setLoginFrame(loginFrame);
		networkFrame.setClientContext(clientContext);
//		clientContext.setNetworkService(networkService);
//		networkService.setNetworkFrame(networkFrame);
		
		networkFrame.setVisible(true);
		
		System.out.println("netaddress="+networkFrame.getNetAddress());
	}
}
