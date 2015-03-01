import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

public class TextWrap {
	public static void wrap(String text, Color c, Font f, Graphics g, int maxChars) {
		g.setColor(c);
		g.setFont(f);
		String line = "";
		int numLines = 0;
		for (String s : text.split(" ")) {
			if (line.length() + s.length() > maxChars) {
				g.drawString(line, 50, 50 + 45 * numLines);
				line = s;
				numLines++;
			} else {
				line += " " + s;
			}
		}
		g.drawString(line, 50, 50 + 45 * (numLines));
	}
}