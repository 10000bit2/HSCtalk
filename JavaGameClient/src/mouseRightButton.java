import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JColorChooser;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.ArrayList;

import javax.swing.JTextField;

public class mouseRightButton extends JFrame {
	private JPanel contentPane;
	private JButton noticeButton;
	private ImageIcon new_icon;
	private JTextField textField;
	private String no="";
	private ObjectOutputStream oos;
	private String UserName;
	private Socket socket;
	String ip_addr="127.0.0.1";
	String port_no="30000";
	
	public mouseRightButton(String noti,String UserName) {
		
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		

		setResizable(false);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 254, 254);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		noticeButton = new JButton("공지로 띄우기");
		noticeButton.setBackground(new Color(0, 153, 255));
		noticeButton.setBounds(44, 126, 146, 33);
		contentPane.add(noticeButton);
		
		textField = new JTextField();
		textField.setBounds(12, 33, 216, 38);
		contentPane.add(textField);
		textField.setColumns(10);
		contentPane.add(textField);
		
		Action action = new Action();
		noticeButton.addActionListener(action);
		this.UserName= UserName;
		
		String[] notiList = noti.split("\n");
		 
		for(int i=1;i<notiList.length-1;i++) {
			no +=notiList[i];
		}
		textField.setText(no);
		
		
		
	}
	
	class Action implements ActionListener{
		
		@Override
		public void actionPerformed(ActionEvent e) {
			String noti = textField.getText();
			ChatMsg obcm = new ChatMsg(UserName, "600", noti);
			SendObject(obcm);
		}
		
	}
	public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
			
		} catch (IOException e) {
			
			System.out.println("SendObject Error");
		}
	}
}
