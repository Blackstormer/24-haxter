import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

/**
 * Utility class for loading images
 * @author John Morach
 *
 */

public class ImageLoader {
	/**
	 * 
	 * @param fileName File name of the image (without file extension)
	 * @return BufferedImage of the image file
	 */
	public static BufferedImage getImage(String fileName) {
		BufferedImage img = null;
		try {
			//Reads image from specified file in correct location
			img = /*ImageIO.read(new File("bin/images/" + fileName + ".jpg"))*/
					ImageIO.read(Game.class.getClass().getResource("/images/" + fileName + ".jpg"));
		} catch (Exception e) {}
		return img;
	}
}