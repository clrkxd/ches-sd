package mech;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.HashMap;

public class PaletteSwap {

	public static BufferedImage swap(
			BufferedImage image,
			HashMap<Integer, Integer> palette) {
		
		BufferedImage result = new BufferedImage(image.getWidth(), image.getHeight(), BufferedImage.TYPE_INT_ARGB);
		
		for (int x = 0; x < image.getWidth(); x++) {
			for (int y = 0; y < image.getHeight(); y++) {
				
				int pixel = image.getRGB(x, y);
				
				if (palette.containsKey(pixel)) {
					pixel = palette.get(pixel);
				}
				
				result.setRGB(x, y, pixel);
			}
		}
		
		return result;
	}
}
