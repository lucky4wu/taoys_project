package cn.taoys.ui;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;

import org.apache.log4j.Logger;

import cn.taoys.entity.User;

public class MenuFrame extends JFrame{

	/**
	 * 
	 */
	private static final long serialVersionUID = 2049023359755100227L;

	private static Logger logger = Logger.getLogger(MenuFrame.class);
	
	private ClientContext clientContext;
	private JLabel info;
	
	public MenuFrame(){
		init();
	}
	private void init(){
		setTitle("网络在线分析系统->主菜单");
		setSize(540, 380);
		setContentPane(createContentPane());
		setLocationRelativeTo(null);
		
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		addWindowListener(new WindowAdapter(){
			public void windowClosing(WindowEvent e){
				clientContext.exit(MenuFrame.this);
			}
		});
	}
	
	private JPanel createContentPane(){
		JPanel p = new JPanel(new BorderLayout());
		ImageIcon icon = new ImageIcon(this.getClass().getResource("001.jpg"));
		p.add(BorderLayout.NORTH, new JLabel(icon, JLabel.CENTER));
		p.add(BorderLayout.CENTER, createCenterPane());
		p.add(BorderLayout.SOUTH, createBottonPane());
		return p;
	}
	
	private JPanel createBottonPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.EAST,new JLabel("版权所有 @created by wu_qingjian@126.com"));
		return p;
	}
	private JPanel createCenterPane() {
		JPanel p = new JPanel(new BorderLayout());
		p.add(BorderLayout.NORTH,upsidePane());
		p.add(BorderLayout.CENTER,menuBtnPane());
		return p;
	}
	private JPanel menuBtnPane() {
		JPanel p = new JPanel(new FlowLayout());
		p.setBorder(new EmptyBorder(24, 0, 0, 0));
		JButton analysis = createImgBtn("message.png", "在线分析");
		JButton exit = createImgBtn("exit.png", "离开");
		p.add(analysis);
		p.add(exit);
		
		getRootPane().setDefaultButton(analysis);
		
		analysis.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.analysis();
			}
		});
		exit.addActionListener(new ActionListener(){
			public void actionPerformed(ActionEvent e) {
		        clientContext.exit(MenuFrame.this);
			}
		});
		return p;
	}
	private JButton createImgBtn(String img, String txt) {
		ImageIcon icon = new ImageIcon(this.getClass().getResource(img));
		JButton button = new JButton(txt,icon);
		//垂直文本位置对齐位置
		button.setVerticalTextPosition(JButton.BOTTOM);
		//水平文本对齐位置
		button.setHorizontalTextPosition(JButton.CENTER);
		return button;
	}
	private JPanel upsidePane() {
		JPanel p = new JPanel(new BorderLayout());
		p.setBorder(new EmptyBorder(0, 32, 0, 32));
		info = new JLabel("欢迎：xxx", JLabel.LEFT);
		p.add(BorderLayout.WEST, info);
		JButton logout = new JButton("注销");
		
		logout.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				clientContext.logout(MenuFrame.this);
			}
		});
		p.add(BorderLayout.EAST, logout);
		return p;
	}
	public void setClientContext(ClientContext clientContext){
		this.clientContext = clientContext;
	}
	public void updateView(User user){
		logger.debug(user);
		info.setText(user+" ,您好！");
	}
}
