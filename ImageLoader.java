import java.awt.image.BufferedImage;
import java.io.File;

import javax.imageio.ImageIO;

public class ImageLoader {
	public static BufferedImage getImage(String fileName) {
		BufferedImage img = null;
		try {
			img = ImageIO.read(new File("bin/images/" + fileName + ".jpg"));
		} catch (Exception e) {}
		return img;
	}
}