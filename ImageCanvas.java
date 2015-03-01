import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.image.BufferedImage;

public class ImageCanvas extends Canvas {
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
	}
	
	@Override
	public void paint(Graphics g) {
		g.drawImage(image, 0, 0, null);
		g.setColor(new Color(rgb, rgb, rgb));
		g.drawString(renderText, 50, 50);
	}
}