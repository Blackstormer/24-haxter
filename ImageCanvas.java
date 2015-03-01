import java.awt.Canvas;
import java.awt.Color;
import java.awt.Graphics;

public class ImageCanvas extends Canvas {
	private static final long serialVersionUID = 1L;
	
	@Override
	public void paint(Graphics g) {
		g.setColor(Color.red);
		g.drawRect(0, 0, 30, 30);
	}
}