package week9;

import java.awt.Color;
import java.util.ArrayList;

public class Action {

	
	private int index;
	private ArrayList<Pixel> seam; 
	
	
	/**
	 * constructs an action
	 * @param index - tracks where the first pixel of the seam goes
	 * @param list - an arraylist of the pixels in the seam
	 */
	protected Action(int index, ArrayList<Pixel> list) {
		
		this.index = index; 
		this.seam = new ArrayList<>(); 

		for(int i = 0; i < list.size(); i++) {
			Pixel temp = list.get(i);
			Color tempColor = new Color(temp.color.getRGB()); 
			//Color tempColor = temp.color;
			Pixel p = new Pixel(
					temp.color,
					temp.left,
					temp.up, 
					temp.right,
					temp.down
					);
			p.direction = temp.direction; 
			p.oldColor = tempColor;
			this.seam.add(p);
		}
	}
	
	
	/**
	 * a getter method for the index
	 * @return - returns the index
	 */
	protected int getIndex() {
		return index;
	}
	
	
	/**
	 * a getter method for seam
	 * @return - returns the seam 
	 */
	protected ArrayList<Pixel> getSeam(){
		return seam; 
	}
	
	
	
	
}
