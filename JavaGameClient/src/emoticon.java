import javax.swing.border.EmptyBorder;

import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.Button;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Font;

public class emoticon extends JFrame {

	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private JButton emo01;
	private JButton emo02;
	private JButton emo03;
	private JButton emo04;
	private JButton emo05;
	private JButton emo06;
	private JButton emo07;
	private JButton emo08;
	private JButton emo09;
	private JButton emo10;
	private Socket socket;
	String ip_addr="127.0.0.1";
	String port_no="30000";
	private ObjectOutputStream oos;
	private String UserName;
	
	ImageIcon angry = new ImageIcon("emoticon/angry.png");
	ImageIcon bigsmile = new ImageIcon("emoticon/bigsmile.png");
	ImageIcon heart = new ImageIcon("emoticon/heart.png");
	ImageIcon heartface = new ImageIcon("emoticon/heartface.png");
	ImageIcon sad = new ImageIcon("emoticon/sad.png");
	ImageIcon smile = new ImageIcon("emoticon/smile.png");
	ImageIcon soccerball = new ImageIcon("emoticon/soccerball.png");
	ImageIcon sosad = new ImageIcon("emoticon/sosad.png");
	ImageIcon triheart = new ImageIcon("emoticon/triheart.png");
	ImageIcon heartfact = new ImageIcon("emoticon/heartfact.PNG");

	
	public emoticon(String UserName) {
		try {
			socket = new Socket(ip_addr, Integer.parseInt(port_no));
			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		setResizable(false);
		setBounds(500, 600, 263, 227);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(255, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);
		
		emo01 = new JButton("🙂");
		emo01.setBackground(new Color(255, 255, 204));
		emo01.setBounds(20, 20, 50, 50);
		contentPane.add(emo01);
		EmoticonAction actionEmo = new EmoticonAction();
		emo01.addActionListener(actionEmo);
		
		emo02 = new JButton("😍");
		emo02.setBackground(new Color(255, 255, 204));
		emo02.setBounds(70, 20, 50, 50);
		contentPane.add(emo02);
		emo02.addActionListener(actionEmo);

		
		emo03 = new JButton("💗");
		emo03.setBackground(new Color(255, 255, 204));
		emo03.setBounds(120, 20, 50, 50);
		contentPane.add(emo03);
		emo03.addActionListener(actionEmo);

		
		emo04 = new JButton("❣️");
		emo04.setBackground(new Color(255, 255, 204));
		emo04.setBounds(170, 20, 50, 50);
		contentPane.add(emo04);
		emo04.addActionListener(actionEmo);

		
		emo05 = new JButton("❤️");
		emo05.setBackground(new Color(255, 255, 204));
		emo05.setBounds(20, 70, 50, 50);
		contentPane.add(emo05);
		emo05.addActionListener(actionEmo);

		
		emo06 = new JButton("⚽");
		emo06.setBackground(new Color(255, 255, 204));
		emo06.setBounds(70, 70, 50, 50);
		contentPane.add(emo06);
		emo06.addActionListener(actionEmo);

		
		emo07 = new JButton("😠");
		emo07.setBackground(new Color(255, 255, 204));
		emo07.setBounds(120, 70, 50, 50);
		contentPane.add(emo07);
		emo07.addActionListener(actionEmo);

		
		emo08 = new JButton("😭");
		emo08.setBackground(new Color(255, 255, 204));
		emo08.setBounds(170, 70, 50, 50);
		contentPane.add(emo08);
		emo08.addActionListener(actionEmo);

		
		emo09 = new JButton("😢");
		emo09.setBackground(new Color(255, 255, 204));
		emo09.setBounds(20, 120, 50, 50);
		contentPane.add(emo09);
		emo09.addActionListener(actionEmo);

		
		emo10 = new JButton("😀");
		emo10.setBackground(new Color(255, 255, 204));
		emo10.setBounds(70, 120, 50, 50);
		contentPane.add(emo10);
		emo10.addActionListener(actionEmo);
		
	}
	
	
	
	class EmoticonAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			JButton btn =(JButton)e.getSource();
			
			ChatMsg obcm = new ChatMsg(UserName, "500", "EMO");
			switch(btn.getText()){
				case "😠":
					obcm.img = angry;
					break;
				case "😍":
					obcm.img = heartface;
					break;
				case "😭":
					obcm.img = sosad;
					break;
				case "😢":
					obcm.img = sad;
					break;
				case "⚽":
					obcm.img = soccerball;
					break;
				case "💗":
					obcm.img = triheart;
					break;
				case "🙂":
					obcm.img = smile;
					break;
				case "❤️":
					obcm.img = heart;
					break;
				case "❣️":
					obcm.img = heartfact;
					break;
				case "😀":
					obcm.img = bigsmile;
					break;
			}
			
			SendObject(obcm);
			
		}
		public void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
			try {
				oos.writeObject(ob);
				//System.out.println("SendObject Successful");
				
			} catch (IOException e) {
				
				System.out.println("SendObject Error");
			}
		}
		
	}


}
