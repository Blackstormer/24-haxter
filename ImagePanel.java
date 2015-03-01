import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JPanel;

/**
 * JPanel with a background image
 * @author John Morach
 *
 */

public class ImagePanel extends JPanel {
	private static final long serialVersionUID = 1L;
	
	/**
	 * Background
	 */
	private BufferedImage image;
	
	public ImagePanel(BufferedImage image) {
		super();
		this.image = image;
	}
	
	public void setImage(BufferedImage image) {
		this.image = image;
	}
	
	/**
	 * Paints the background
	 */
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		if (image != null)
			g.drawImage(image, 0, 0, null);
	}
}