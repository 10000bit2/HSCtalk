import javax.swing.border.EmptyBorder;
import javax.swing.*;
import java.awt.BorderLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.Writer;
import java.awt.Canvas;
import java.awt.Color;

public class javaClientSetting extends JFrame{

   private JPanel contentPane;
   private JColorChooser cc;
   
   public javaClientSetting() {
      setTitle("설정");
      
      setResizable(false);
      //setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
      setBounds(100, 100, 254, 321);
      contentPane = new JPanel();
      contentPane.setBackground(new Color(204, 255, 255));
      contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
      setContentPane(contentPane);
      contentPane.setLayout(null);
      
      
      JButton btn = new JButton("대화내용 내보내기");
      btn.setBackground(new Color(255, 255, 204));
      btn.setBounds(59, 28, 146, 33);
      contentPane.add(btn);
      ChatTextAction chataction = new ChatTextAction();
      btn.addActionListener(chataction);
      
      
      JButton btn_1 = new JButton("채팅방 배경색 변경");
      btn_1.setBackground(new Color(255, 255, 204));
      btn_1.setBounds(59, 94, 146, 33);
      contentPane.add(btn_1);
      BackgroundAction backaction = new BackgroundAction();
      btn_1.addActionListener(backaction);
   }
   class BackgroundAction implements ActionListener{
		@Override
		public void actionPerformed(ActionEvent e) {
			Color newColor = JColorChooser.showDialog(cc,"Color Chooser",javaClientSetting.this.getBackground());
			JavaGameClientView.talkList.setBackground(newColor);
			JavaGameClientView.talkList.repaint();
		}
		
	}
	class ChatTextAction implements ActionListener {
		@Override
		public void actionPerformed(ActionEvent e) {
			ListModel model = JavaGameClientView.talkList.getModel();
			Object o = null;
			for(int i=0; i<model.getSize(); i++) {
				o = model.getElementAt(i);
				File file = new File("text.txt");
				try {
					file.createNewFile();
					FileWriter fw = new FileWriter(file, true);
					//FileWriter fw = new FileWriter(file, true);
					BufferedWriter bw = new BufferedWriter(fw);
					
					bw.write(o.toString() + "\n\n");
					bw.close();
				} catch (IOException e1) {
					// TODO Auto-generated catch block
					e1.printStackTrace();
					System.out.println("fail create file");
				}
			}
		}
	}
	
}
