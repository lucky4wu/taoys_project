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
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.UIManager;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;
import org.jvnet.substance.skin.SubstanceCremeLookAndFeel;


public class LoginFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 9209999140962803610L;
	
	private static Logger logger = Logger.getLogger(LoginFrame.class);
	
	private ClientContext clientContext;
	private JTextField nameField;
	private JPasswordField pwdField;
	private JLabel message;
	
	public void setClientContext(ClientContext clientContext){
		this.clientContext = clientContext;
	}
	public String getUname(){
		logger.debug("uname="+nameField.getText());
		return new String(nameField.getText());
	}
	public String getPassword(){
		char[] pwd = pwdField.getPassword();
		logger.debug("pwd="+pwd.toString());
		return new String(pwd);
	}

	public LoginFrame() {
		try {
			//UIManager.setLookAndFeel(UIManager.getCrossPlatformLookAndFeelClassName());
//			UIManager.setLookAndFeel(new SubstanceOfficeBlue2007LookAndFeel());
//			UIManager.setLookAndFeel(new SubstanceAutumnLookAndFeel());
//			UIManager.setLookAndFeel(new SubstanceBusinessBlueSteelLookAndFeel());
			UIManager.setLookAndFeel(new SubstanceCremeLookAndFeel());//淡蓝色
//			UIManager.setLookAndFeel(new SubstanceFindingNemoLookAndFeel());//背景海洋蓝
//			UIManager.setLookAndFeel(new SubstanceModerateLookAndFeel());//深蓝
//			java Swing修改皮肤的代码大致如下:
//				UIManager.setLookAndFeel(" ");
//				里面传的是一个String,表示LookAndFeel的实现子类的名称。
//
//				在Java中已经自带了几种，可以通过
//				UIManager.getInstalledLookAndFeels();方法获取
//
//				使用substance皮肤的话，
//				可以这样:
//				UIManager.setLookAndFeel("org.jvnet.substance.skin.SubstanceBusinessLookAndFeel");
//				注意substance里面包含了很多种外观，这只是其中一种，还有其他的 ，可以通过winrar去打开substance.jar,然后
//				到达 org.jvnet.substance.skin这一层，你可以看到很多名称以LookAndFeel结尾的class文件，你把传的string改下就可以了。
//				他里面有20多种，比较漂亮的。
		} catch (Exception e) {
			e.printStackTrace();
		}
		init();
	}
	
	private void init(){
		this.setTitle("登陆系统");
		JPanel contentPane = createContentPane();
		this.setContentPane(contentPane);
		this.setSize(270, 188);
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		this.addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				clientContext.exit(LoginFrame.this);
			}
		});
	}

	private JPanel createContentPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(8,16,8,16));
		p.add(BorderLayout.NORTH, new JLabel("网络在线侦测系统",JLabel.CENTER));
		p.add(BorderLayout.CENTER, createCenterPane());
		p.add(BorderLayout.SOUTH, createBtnPane());
		return p;
	}

	private JPanel createCenterPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(8, 0, 0, 0));
		p.add(BorderLayout.NORTH, createIdPwdPane());
		message = new JLabel("", JLabel.CENTER);
		p.add(BorderLayout.CENTER, message);
		return p;
	}

	private JPanel createIdPwdPane() {
		JPanel p = new JPanel(new GridLayout(2, 1, 0, 6));
		p.add(createNamePane());
		p.add(createPwdPane());
		return p;
	}
	
	private JPanel createNamePane(){
		JPanel p = new JPanel(new BorderLayout(6,0));
		p.add(BorderLayout.WEST, new JLabel("账户：", JLabel.CENTER));
		nameField = new JTextField();
		p.add(BorderLayout.CENTER, nameField);
		return p;
	}
	
	private JPanel createPwdPane(){
		JPanel p = new JPanel(new BorderLayout(6,0));
		p.add(BorderLayout.WEST, new JLabel("密码：",JLabel.CENTER));
		pwdField = new JPasswordField();
		pwdField.enableInputMethods(true);
		p.add(BorderLayout.CENTER, pwdField);
		return p;
	}

	private JPanel createBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		JButton login = new JButton("登陆");
		JButton cancel = new JButton("取消");
		
		getRootPane().setDefaultButton(login);
		
		p.add(login);
		p.add(cancel);
		
		login.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.login();
			}
		});
		cancel.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e){
				clientContext.exit(LoginFrame.this);
			}
		});
		return p;
	}
	
	public void showMessage(String message) {
		logger.debug("show message="+message);
		this.message.setText(message);
	}

	
}
