import java.awt.Color;
import java.awt.Font;
import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import javax.swing.JTextArea;
import javax.swing.JTextPane;

public class bigImage extends JFrame {
		private JPanel contentPane;
		
		public bigImage(ImageIcon ori_icon) {
			setResizable(false);
			//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
			setBounds(100, 100, 510, 531);
			contentPane = new JPanel();
			contentPane.setBackground(new Color(204, 255, 255));
			contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
			setContentPane(contentPane);
			contentPane.setLayout(null);
			
			JTextPane textArea = new JTextPane();
			textArea.setBounds(0, 0, 500, 500);
			textArea.setEditable(true);
			textArea.setFont(new Font("굴림체", Font.PLAIN, 14));
			contentPane.add(textArea);
			
			
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
			if (width > 500 || height > 500) {
				if (width > height) { // 가로 사진
					ratio = (double) height / width;
					width = 500;
					height = (int) (width * ratio);
				} else { // 세로 사진
					ratio = (double) width / height;
					height = 500;
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
		}
}
