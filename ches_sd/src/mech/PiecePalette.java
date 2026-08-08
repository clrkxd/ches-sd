package mech;

import java.awt.Color;
import java.util.HashMap;

public class PiecePalette {

    public static HashMap<Integer, Integer> BLACK = new HashMap<>();

    static {
    	
    	// color
    	BLACK.put(
    		    new Color(232, 232, 232).getRGB(),   // #E8E8E8
    		    new Color(40, 40, 40).getRGB()
    		);

//    		BLACK.put(
//    		    new Color(200, 200, 200).getRGB(),   // #C8C8C8
//    		    new Color(80, 80, 80).getRGB()
//    		);
    	
    	
    	// outline
    	BLACK.put(
    		    new Color(0, 0, 0).getRGB(),
    		    new Color(232, 232, 232).getRGB()
    		);

    }
}