// JavaObjClient.java
// ObjecStream 사용하는 채팅 Client

import java.awt.EventQueue;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.Color;

public class JavaGameClientMain extends JFrame {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	
	private JButton star;
	private JButton logo;
	private JTextField inputID;
	private JTextField inputpassword;
	private JTextField inputtitle;
	private JButton title;
	
	
	
	/**
	 * Launch the application.
	 */
	public static void main(String[] args) {
		EventQueue.invokeLater(new Runnable() {
			public void run() {
				try {
					JavaGameClientMain frame = new JavaGameClientMain();
					frame.setVisible(true);
				} catch (Exception e) {
					e.printStackTrace();
				}
			}
		});
	}

	/**
	 * Create the frame.
	 */
	public JavaGameClientMain() {
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 400, 400);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		star = new JButton("🌟");
		star.setBounds(183, 57, 44, 40);
		star.setBorderPainted(false);
		star.setContentAreaFilled(false);
		star.setFocusPainted(false);
		contentPane.add(star);
		
		logo = new JButton("HSC TALK");
		logo.setBounds(141, 107, 130, 30);
		logo.setBorderPainted(false);
		logo.setContentAreaFilled(false);
		logo.setFocusPainted(false);
		contentPane.add(logo);
		
		inputID = new JTextField();
		inputID.setColumns(10);
		inputID.setBounds(165, 151, 153, 30);
		contentPane.add(inputID);
		
		inputpassword = new JTextField();
		inputpassword.setColumns(10);
		inputpassword.setBounds(165, 191, 153, 30);
		contentPane.add(inputpassword);
		
		inputtitle = new JTextField();
		inputtitle.setColumns(10);
		inputtitle.setBounds(165, 231, 153, 30);
		contentPane.add(inputtitle);
		
		JButton ID = new JButton("ID");
		ID.setFocusPainted(false);
		ID.setContentAreaFilled(false);
		ID.setBorderPainted(false);
		ID.setBounds(74, 150, 81, 30);
		contentPane.add(ID);
		
		JButton password = new JButton("password");
		password.setFocusPainted(false);
		password.setContentAreaFilled(false);
		password.setBorderPainted(false);
		password.setBounds(58, 190, 97, 30);
		contentPane.add(password);
		
		title = new JButton("채팅방이름");
		title.setFocusPainted(false);
		title.setContentAreaFilled(false);
		title.setBorderPainted(false);
		title.setBounds(58, 230, 97, 30);
		contentPane.add(title);
		
		JButton btnConnect = new JButton("Connect");
		btnConnect.setBackground(new Color(255, 255, 204));
		btnConnect.setBounds(96, 288, 205, 38);
		contentPane.add(btnConnect);
		Myaction action = new Myaction();
		btnConnect.addActionListener(action);
	}
	
	class Myaction implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			String username = inputID.getText().trim();
			String ip_addr = "127.0.0.1";
			String port_no = "30000";
			String title = inputtitle.getText().trim();
			JavaGameClientView view = new JavaGameClientView(username, ip_addr, port_no, title);
			setVisible(false);
		}
	}
}


