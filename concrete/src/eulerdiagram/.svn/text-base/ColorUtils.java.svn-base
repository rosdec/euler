package eulerdiagram;

import java.awt.Color;
import java.util.ArrayList;

public  class ColorUtils {

	private static ArrayList<Color> colors= new ArrayList<Color>();
	
	private static void createColors() {
		colors.add(new Color( 255, 125, 125, 127));
		colors.add(new Color( 150, 255, 100, 127));
		colors.add(new Color( 0, 150, 250, 127));
		colors.add(new Color( 250, 150, 0, 127));
		colors.add(new Color( 200, 100, 255, 127));
		colors.add(new Color( 0, 128, 0, 127));
		colors.add(new Color( 0, 255, 255, 127));
		colors.add(new Color( 255, 255, 0, 127));
		colors.add(new Color( 255, 255, 0, 127));
		colors.add(new Color( 155, 100, 50, 127));
		colors.add(new Color( 55, 50, 150, 127));
		colors.add(new Color( 0, 100, 100, 127));
		colors.add(new Color( 0, 150, 250, 127));
		colors.add(new Color( 150, 200, 255, 127));
		colors.add(new Color( 0, 155, 0, 127));
	}
	
	public static Color getColor(){
		if (colors.size()==0)
			createColors();
		return colors.get((int) Math.floor(Math.random()*colors.size()));
	}
	
	
}
