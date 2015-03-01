import javax.swing.JFrame;

public class Game {
	private static JFrame window;
	
	public static void main(String[] args) {
		window = new JFrame("HackExeter");
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.pack();
		window.setSize(400, 400);
		window.setResizable(false);
		window.setVisible(true);
	}
}