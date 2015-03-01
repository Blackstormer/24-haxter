import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

import javax.swing.JComponent;

public class ImageCanvas extends JComponent {
	private static final long serialVersionUID = 1L;
	
	private String renderText = "";
	private int rgb = 0;
	private BufferedImage image = ImageLoader.getImage("1");
	
	public void setRenderText(String text) {
		this.renderText = text;
	}
	
	public void setRGB(int rgb) {
		this.rgb = rgb;
	}
	
	public void setBufferedImage(String name) {
		image = ImageLoader.getImage(name);
		update(this.getGraphics());
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.drawImage(image, 0, 0, null);
		g.setColor(new Color(rgb, rgb, rgb));
		g.setFont(Game.augustus30);
		TextWrap.wrap(renderText, new Color(rgb, rgb, rgb), Game.augustus30, g, 40);
	}
}