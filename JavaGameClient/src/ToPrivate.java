
// JavaObjClientView.java ObjecStram 기반 Client
//실질적인 채팅 창
import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.FileDialog;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.event.MouseWheelEvent;
import java.awt.event.MouseWheelListener;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.OutputStream;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.text.SimpleDateFormat;
import java.util.Date;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JPopupMenu;
import javax.swing.border.EmptyBorder;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JTextPane;
import javax.swing.DefaultListCellRenderer;
import javax.swing.DefaultListModel;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.SwingConstants;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.MenuItem;
import java.awt.PopupMenu;
import java.awt.Color;
import java.awt.Component;

import javax.swing.border.BevelBorder;
import javax.swing.border.LineBorder;
import javax.swing.JToggleButton;
import javax.swing.ListModel;
import javax.swing.ListSelectionModel;
import javax.swing.JList;
import javax.swing.JMenuItem;

import java.awt.Canvas;
import javax.swing.border.TitledBorder;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.text.BadLocationException;
import javax.swing.text.SimpleAttributeSet;
import javax.swing.text.StyleConstants;
import javax.swing.text.StyledDocument;

public class ToPrivate extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JPanel contentPane;
	private static JTextField txtInput;
	private String UserName;
	private JButton btnEmo;
	private JButton btnSend;
	private static final int BUF_LEN = 128; // Windows 처럼 BUF_LEN 을 정의
	private Socket socket; // 연결소켓
	private InputStream is;
	private OutputStream os;
	private DataInputStream dis;
	private DataOutputStream dos;

	private ObjectInputStream ois;
	private ObjectOutputStream oos;

	private JLabel lblUserName;
	private JScrollPane scrollPane;
	static JTextPane textArea;
	private JLabel notice;
	
	

	private JPopupMenu popup;

	static JList<String> talkList;
	private Frame frame;
	private FileDialog fd;
	private JButton imgBtn;

	JPanel panel;
	private JLabel lblMouseEvent;
	private Graphics gc;
	private int pen_size = 2; // minimum 2
	private static DefaultListModel Im;
	private String toUserName,fromUserName;
	static ImageIcon ori_icon;

	/**
	 * Create the frame.
	 * 
	 * @throws BadLocationException
	 */

	public ToPrivate(String toUserName, String fromUserName) {
		this.toUserName = toUserName;
		this.fromUserName= fromUserName;
		setTitle(toUserName);
		setResizable(false);
		
		setBounds(100, 100, 257, 468);
		contentPane = new JPanel();
		contentPane.setBackground(new Color(204, 255, 255));
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		scrollPane = new JScrollPane();
		// scrollPane.setBounds(12, 10, 266, 471);
		contentPane.add(scrollPane);

		textArea = new JTextPane();
		textArea.setEditable(true);
		textArea.setFont(new Font("굴림체", Font.PLAIN, 14));

		txtInput = new JTextField();
		txtInput.setBounds(63, 332, 124, 40);
		contentPane.add(txtInput);
		txtInput.setColumns(10);

		btnEmo = new JButton("🙂");
		btnEmo.setBounds(187, 331, 50, 40);
		btnEmo.setBorderPainted(false);
		btnEmo.setContentAreaFilled(false);
		// btnEmo.setFocusPainted(false);
		contentPane.add(btnEmo);
		Myaction2 actionEmoticon = new Myaction2();
		btnEmo.addActionListener(actionEmoticon);

		btnSend = new JButton("Send");
		btnSend.setBackground(new Color(255, 255, 204));
		btnSend.setFont(new Font("굴림", Font.PLAIN, 14));
		btnSend.setBounds(86, 380, 69, 40);
		contentPane.add(btnSend);

		lblUserName = new JLabel("Name");
		lblUserName.setBorder(new LineBorder(new Color(0, 0, 0)));
		lblUserName.setBackground(Color.WHITE);
		lblUserName.setFont(new Font("굴림", Font.BOLD, 14));
		lblUserName.setHorizontalAlignment(SwingConstants.CENTER);
		lblUserName.setBounds(12, 380, 62, 40);
		contentPane.add(lblUserName);
		setVisible(true);

		//AppendText("User " + username + " connecting " + ip_addr + " " + port_no);
		//UserName = username;
		lblUserName.setText(fromUserName);

		imgBtn = new JButton("+");
		imgBtn.setBackground(new Color(255, 255, 204));
		imgBtn.setFont(new Font("굴림", Font.PLAIN, 16));
		imgBtn.setBounds(12, 330, 50, 40);
		contentPane.add(imgBtn);

		JButton btnNewButton = new JButton("종 료");
		btnNewButton.setBackground(new Color(255, 255, 204));
		btnNewButton.setFont(new Font("굴림", Font.PLAIN, 14));
		btnNewButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ChatMsg msg = new ChatMsg(UserName, "400", "Bye");
				SendObject(msg);
				System.exit(0);
			}
		});
		btnNewButton.setBounds(167, 380, 69, 40);
		contentPane.add(btnNewButton);

		talkList = new JList();
		talkList.setSelectionMode(ListSelectionModel.SINGLE_INTERVAL_SELECTION);
		Im = new DefaultListModel();
		talkList.setFont(new Font("굴림체", Font.PLAIN, 14));
		talkList.setBounds(1, 88, 264, 382);
		

		contentPane.add(talkList);

		JScrollPane scrollPane_2 = new JScrollPane(talkList);
		scrollPane_2.setBounds(12, 20, 200, 300);
		contentPane.add(scrollPane_2);

		notice = new JLabel();
		notice.setBounds(12,0,264, 17);
		notice.setBackground(new Color(255, 255, 0));
		contentPane.add(notice);
		
		


		
		
		

		

		try {
			socket = new Socket("127.0.0.1", Integer.parseInt("30000"));
//			is = socket.getInputStream();
//			dis = new DataInputStream(is);
//			os = socket.getOutputStream();
//			dos = new DataOutputStream(os);

			oos = new ObjectOutputStream(socket.getOutputStream());
			oos.flush();
			ois = new ObjectInputStream(socket.getInputStream());

			// SendMessage("/login " + UserName);
			ChatMsg obcm = new ChatMsg(fromUserName, "100", "Hello");
			SendObject(obcm);
			ChatMsg obcm1= new ChatMsg(toUserName,"100","Hello");
			SendObject(obcm1);

			ListenNetwork net = new ListenNetwork();
			net.start();
			TextSendAction action = new TextSendAction();
			btnSend.addActionListener(action);
			txtInput.addActionListener(action);
			txtInput.requestFocus();
			ImageSendAction action2 = new ImageSendAction();
			imgBtn.addActionListener(action2);
			MyMouseEvent mouse = new MyMouseEvent();
			talkList.addMouseMotionListener(mouse);
			talkList.addMouseListener(mouse);
			MyMouseWheelEvent wheel = new MyMouseWheelEvent();
			talkList.addMouseWheelListener(wheel);

		} catch (NumberFormatException | IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			AppendText("connect error");
		}

	}

	// Server Message를 수신해서 화면에 표시
	class ListenNetwork extends Thread {
		public void run() {
			while (true) {
				try {
					SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
					Date time = new Date();
					String time1 = format.format(time);

					Object obcm = null;
					String msg = null;
					ChatMsg cm;
					try {
						obcm = ois.readObject();
					} catch (ClassNotFoundException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
						break;
					}
					if (obcm == null)
						break;
					if (obcm instanceof ChatMsg) {
						cm = (ChatMsg) obcm;
						msg = String.format("[%s]\n%s", cm.UserName, cm.data);
					} else
						continue;

					msg = msg + "\n" + time1 + "\n";

					switch (cm.code) {

					/*case "200": // chat message

						if (cm.UserName.equals(UserName))
							// AppendTextR(msg); // 내 메세지는 우측에
							AppendTalkListMsgR(msg);

						else
							AppendTalkListMsgL(msg);

						break;
					case "300": // Image 첨부
						if (cm.UserName.equals(UserName)) {
							AppendTalkListMsgR("[" + cm.UserName + "]");

						} else
							AppendTalkListMsgL("[" + cm.UserName + "]");
						AppendTalkListImg(cm.img);
						break;
						
					case "500": //emoticon
						AppendTalkListEmo(cm.img);
						break;
					
					case "600": // 공지
						SetNotice(msg);
						break;
					case "700": // list
						//AppendList(msg);
						break;
						*/
						case "800":
						AppendTalkListMsgL(msg);
						break;
					}
					
					
				} catch (IOException e) {
					AppendText("ois.readObject() error");
					try {
//						dos.close();
//						dis.close();
						ois.close();
						oos.close();
						socket.close();

						break;
					} catch (Exception ee) {
						break;
					} // catch문 끝
				} // 바깥 catch문끝

			}
		}
	}

	// Mouse Event 수신 처리
	public void DoMouseEvent(ChatMsg cm) {
		Color c;
		if (cm.UserName.matches(UserName)) // 본인 것은 이미 Local 로 그렸다.
			return;
		c = new Color(255, 0, 0); // 다른 사람 것은 Red
		gc.setColor(c);
		gc.fillOval(cm.mouse_e.getX() - pen_size / 2, cm.mouse_e.getY() - cm.pen_size / 2, cm.pen_size, cm.pen_size);
	}

	/*
	 * public void SendMouseEvent(MouseEvent e) { ChatMsg cm = new ChatMsg(UserName,
	 * "500", "MOUSE"); cm.mouse_e = e; cm.pen_size = pen_size; SendObject(cm);
	 * 
	 * }
	 */
	

	class MyMouseWheelEvent implements MouseWheelListener {
		@Override
		public void mouseWheelMoved(MouseWheelEvent e) {
			// TODO Auto-generated method stub
			if (e.getWheelRotation() < 0) { // 위로 올리는 경우 pen_size 증가
				if (pen_size < 20)
					pen_size++;
			} else {
				if (pen_size > 2)
					pen_size--;
			}
			// lblMouseEvent.setText("mouseWheelMoved Rotation=" + e.getWheelRotation()
			// + " pen_size = " + pen_size + " " + e.getX() + "," + e.getY());

		}

	}

	// Mouse Event Handler
	class MyMouseEvent implements MouseListener, MouseMotionListener {
		@Override
		public void mouseDragged(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mouseDragged " + e.getX() + "," +
			// e.getY());// 좌표출력가능
			/*
			 * Color c = new Color(0, 0, 255); gc.setColor(c); gc.fillOval(e.getX() -
			 * pen_size / 2, e.getY() - pen_size / 2, pen_size, pen_size);
			 */

			//SendMouseEvent(e);
		}

		@Override
		public void mouseMoved(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mouseMoved " + e.getX() + "," +
			// e.getY());
		}

		@Override
		public void mouseClicked(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mouseClicked " + e.getX() + "," +
			// e.getY());
			/*
			 * Color c = new Color(0, 0, 255); gc.setColor(c); gc.fillOval(e.getX() -
			 * pen_size / 2, e.getY() - pen_size / 2, pen_size, pen_size);
			 * SendMouseEvent(e);
			 */

			if (e.getButton() == MouseEvent.BUTTON3) {
				bigImage imageview = new bigImage(ori_icon);
				setVisible(true);
				imageview.setVisible(true);

			}
			if (e.getClickCount() == 2) {
				String noti = talkList.getSelectedValue().toString();
				mouseRightButton right = new mouseRightButton(noti, UserName);
				setVisible(true);
				right.setVisible(true);

			}
		}

		@Override
		public void mouseEntered(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mouseEntered " + e.getX() + "," +
			// e.getY());
			// panel.setBackground(Color.YELLOW);

		}

		@Override
		public void mouseExited(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mouseExited " + e.getX() + "," +
			// e.getY());
			// panel.setBackground(Color.CYAN);

		}

		@Override
		public void mousePressed(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mousePressed " + e.getX() + "," +
			// e.getY());

			

		}

		@Override
		public void mouseReleased(MouseEvent e) {
			// lblMouseEvent.setText(e.getButton() + " mouseReleased " + e.getX() + "," +
			// e.getY());
			// 드래그중 멈출시 보임

		}
	}

	// keyboard enter key 치면 서버로 전송
	class TextSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// Send button을 누르거나 메시지 입력하고 Enter key 치면
			if (e.getSource() == btnSend || e.getSource() == txtInput) {
				String msg = null;
				
				// msg = String.format("[%s] %s\n", UserName, txtInput.getText());
				AppendTalkListMsgL("["+fromUserName+"]"+txtInput.getText());
				msg ="/to "+toUserName+ " "+ txtInput.getText();
				SendMessage(msg);
				txtInput.setText(""); // 메세지를 보내고 나면 메세지 쓰는창을 비운다.
				txtInput.requestFocus(); // 메세지를 보내고 커서를 다시 텍스트 필드로 위치시킨다
				if (msg.contains("/exit")) // 종료 처리
					System.exit(0);
			}
		}
	}

	class ImageSendAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			// 액션 이벤트가 sendBtn일때 또는 textField 에세 Enter key 치면
			if (e.getSource() == imgBtn) {
				frame = new Frame("이미지첨부");
				fd = new FileDialog(frame, "이미지 선택", FileDialog.LOAD);
				// frame.setVisible(true);
				// fd.setDirectory(".\\");
				fd.setVisible(true);
				// System.out.println(fd.getDirectory() + fd.getFile());
				if (fd.getDirectory().length() > 0 && fd.getFile().length() > 0) {
					ChatMsg obcm = new ChatMsg(UserName, "300", "IMG");
					ImageIcon img = new ImageIcon(fd.getDirectory() + fd.getFile());
					obcm.img = img;
					SendObject(obcm);
				}
			}
		}
	}

	ImageIcon icon1 = new ImageIcon("src/icon1.jpg");
	
	public synchronized void AppendIcon(ImageIcon icon) {
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		textArea.setCaretPosition(len);
		textArea.insertIcon(icon);
	}

	
	public synchronized void AppendTalkListEmo(ImageIcon ori_icon) {
		Image ori_img = ori_icon.getImage();
		Image new_img;
		this.ori_icon = ori_icon;
		ImageIcon new_icon;
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 작으면 최대 가로 또는 세로 100 기준으로 확대시킨다.
		if (width<200 || height <200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 100;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 100;
				width = (int) (height * ratio);
			}
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			Im.addElement(new_icon);
		}

		talkList.setModel(Im);

	}
	
	public synchronized void AppendTalkListImg(ImageIcon ori_icon) {
		Image ori_img = ori_icon.getImage();
		Image new_img;
		this.ori_icon = ori_icon;
		ImageIcon new_icon;
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			Im.addElement(new_icon);
		}

		talkList.setModel(Im);

	}

	/*
	 * talkList.addListSelectionListener(new ListSelectionListener() {
	 * 
	 * @Override //리스트의 항목 선택시 자동 실행되는 메소드 : 콜백메소드
	 * 
	 * public void valueChanged(ListSelectionEvent e) {
	 * 
	 * //리스트에게 선택된 항목의 인덱스번호 얻어오기
	 * 
	 * int index = list.getSelectedIndex();
	 * 
	 * for(JLabel t : imgLabels) t.setVisible(false);
	 * 
	 * imgLabels[index].setVisible(true);
	 * 
	 * }
	 * 
	 * });
	 */

	public synchronized void AppendTalkListMsgR(String msg) {
		msg = msg.trim();
		Im.addElement(msg);
		/*
		 * talkList.setCellRenderer(new DefaultListCellRenderer() { public int
		 * getHorizaontalAlignment() { return RIGHT; } });
		 */
		int index = Im.getSize();
		// talkList(index).setForeground(Color.BLUE);

		talkList.setModel(Im);
		talkList.setSelectedIndex(index);
		talkList.setSelectionForeground(Color.BLUE);

		talkList.setCellRenderer(new DefaultListCellRenderer() {
			public int getHorizaontalAlignment() {
				return RIGHT;
			}
		});

	}

	public synchronized static void AppendTalkListMsgL(String msg) {
		msg = msg.trim();

		Im.addElement(msg);

		talkList.setModel(Im);
		talkList.setForeground(Color.BLACK);
		talkList.setAlignmentX(LEFT_ALIGNMENT);
	}

	// 화면에 출력
	public synchronized void AppendText(String msg) {
		// textArea.append(msg + "\n");
		// AppendIcon(icon1);
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		int len = textArea.getDocument().getLength();
		// 끝으로 이동
		// textArea.setCaretPosition(len);
		// textArea.replaceSelection(msg + "\n");

		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet left = new SimpleAttributeSet();
		StyleConstants.setAlignment(left, StyleConstants.ALIGN_LEFT);
		StyleConstants.setForeground(left, Color.BLACK);
		doc.setParagraphAttributes(doc.getLength(), 1, left, false);
		try {
			doc.insertString(doc.getLength(), msg + "\n", left);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	// 화면 우측에 출력
	public synchronized void AppendTextR(String msg) {
		msg = msg.trim(); // 앞뒤 blank와 \n을 제거한다.
		StyledDocument doc = textArea.getStyledDocument();
		SimpleAttributeSet right = new SimpleAttributeSet();
		StyleConstants.setAlignment(right, StyleConstants.ALIGN_RIGHT);
		StyleConstants.setForeground(right, Color.BLUE);
		doc.setParagraphAttributes(doc.getLength(), 1, right, false);

		try {
			doc.insertString(doc.getLength(), msg + "\n", right);
		} catch (BadLocationException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public synchronized void AppendImage(ImageIcon ori_icon) {
		int len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len); // place caret at the end (with no selection)
		Image ori_img = ori_icon.getImage();
		Image new_img;
		ImageIcon new_icon;
		int width, height;
		double ratio;
		width = ori_icon.getIconWidth();
		height = ori_icon.getIconHeight();
		// Image가 너무 크면 최대 가로 또는 세로 200 기준으로 축소시킨다.
		if (width > 200 || height > 200) {
			if (width > height) { // 가로 사진
				ratio = (double) height / width;
				width = 200;
				height = (int) (width * ratio);
			} else { // 세로 사진
				ratio = (double) width / height;
				height = 200;
				width = (int) (height * ratio);
			}
			new_img = ori_img.getScaledInstance(width, height, Image.SCALE_SMOOTH);
			new_icon = new ImageIcon(new_img);
			textArea.insertIcon(new_icon);

		} else {
			textArea.insertIcon(ori_icon);
			new_img = ori_img;
		}
		len = textArea.getDocument().getLength();
		textArea.setCaretPosition(len);
		textArea.replaceSelection("\n");
		// ImageViewAction viewaction = new ImageViewAction();
		// new_icon.addActionListener(viewaction); // 내부클래스로 액션 리스너를 상속받은 클래스로
		// gc.drawImage(ori_img, 0, 0, panel.getWidth(), panel.getHeight(), this);
	}

	// 공지 띄우기
	public void SetNotice(String noticeText) {
		String no="";
		String[] notiList = noticeText.split("\n");
		 
		for(int i=0;i<notiList.length-1;i++) {
			no +=notiList[i];
		}
		
		notice.setText(no);
		
	}

	// Windows 처럼 message 제외한 나머지 부분은 NULL 로 만들기 위한 함수
	public byte[] MakePacket(String msg) {
		byte[] packet = new byte[BUF_LEN];
		byte[] bb = null;
		int i;
		for (i = 0; i < BUF_LEN; i++)
			packet[i] = 0;
		try {
			bb = msg.getBytes("euc-kr");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
			System.exit(0);
		}
		for (i = 0; i < bb.length; i++)
			packet[i] = bb[i];
		return packet;
	}

	// Server에게 network으로 전송
	public synchronized void SendMessage(String msg) {
		try {
			// dos.writeUTF(msg);
//			byte[] bb;
//			bb = MakePacket(msg);
//			dos.write(bb, 0, bb.length);
			ChatMsg obcm = new ChatMsg(toUserName, "800", msg);
			oos.writeObject(obcm);
		} catch (IOException e) {
			// AppendText("dos.write() error");
			AppendText("oos.writeObject() error");
			try {
//				dos.close();
//				dis.close();
				ois.close();
				oos.close();
				socket.close();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
				System.exit(0);
			}
		}
	}

	public synchronized void SendObject(Object ob) { // 서버로 메세지를 보내는 메소드
		try {
			oos.writeObject(ob);
		} catch (IOException e) {
			// textArea.append("메세지 송신 에러!!\n");
			AppendText("SendObject Error");
		}
	}

	public static void getTxtInput(String emo) {
		txtInput.setText(emo);

	}

	class Myaction2 implements ActionListener // 내부클래스로 액션 이벤트 처리 클래스
	{
		@Override
		public void actionPerformed(ActionEvent e) {
			emoticon emo = new emoticon(UserName);
			emo.setVisible(true);
			setVisible(true);
		}
	}
	
}
