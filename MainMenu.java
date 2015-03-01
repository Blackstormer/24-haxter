import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.image.BufferedImage;

import javax.imageio.ImageIO;
import javax.swing.BorderFactory;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

/**
 * JFrame for the main menu
 * @author joh.morach
 *
 */
public class MainMenu extends JFrame {
	// Default
	private static final long serialVersionUID = 1L;
	
	public MainMenu() {
		super("The Quest For Knowledge");
		
		ImagePanel bg = new ImagePanel(ImageLoader.getImage("mainmenu"));
		bg.setLayout(null);
		JButton start = new JButton();
		start.setBorder(BorderFactory.createEmptyBorder());
		start.setContentAreaFilled(false);
		start.setBounds(100, 550, 374, 158);
		
		class StartListener implements ActionListener {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				Game.init();
				closeFrame();
			}
		}
		start.addActionListener(new StartListener());
		start.setIcon(new ImageIcon(png()));
		bg.add(start);
		add(bg);
		
		setSize(1024, 768);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setResizable(false);
		setVisible(true);
	}
	
	private void closeFrame() {
		this.dispose();
	}
	
	private BufferedImage png() {
		BufferedImage img = null;
		try {
			//Reads image from specified file in correct location
			img = ImageIO.read(Game.class.getClass().getResource("/images/" + "startbutton" + ".png"));
		} catch (Exception e) {}
		return img;
	}
}