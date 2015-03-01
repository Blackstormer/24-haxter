import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;

/**
 * Utility class for wrapping text over multiple lines
 * @author John Morach
 *
 */

public class TextWrap {
	/**
	 * Wraps the text displayed between stages
	 * @param text Text to wrap
	 * @param c Color of the text
	 * @param f Font of the text
	 * @param g Graphics object of the component
	 * @param maxChars Maximum number of characters per line
	 */
	public static void wrap(String text, Color c, Font f, Graphics g, int maxChars) {
		g.setColor(c);
		g.setFont(f);
		String line = "";
		int numLines = 0;
		for (String s : text.split(" ")) {
			// Checks if line length and length of string attempting to be added to line are greater than the maximum
			if (line.length() + s.length() > maxChars) {
				// Draws the current line at the correct height
				g.drawString(line, 50, 50 + 45 * numLines);
				line = s;
				numLines++;
			} else {
				line += " " + s;
			}
		}
		// Draws the last line
		g.drawString(line, 50, 50 + 45 * (numLines));
	}
}