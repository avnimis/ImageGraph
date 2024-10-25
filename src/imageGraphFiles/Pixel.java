package imageGraphFiles;

import java.awt.Color;

import imageGraphFiles.ImageGraph.Direction;

public class Pixel {
	public Color color;
	public Color oldColor; 
	public Pixel left;
	public Pixel up;
	public Pixel right;
	public Pixel down;
	public Direction direction;
	
	/**
	 * Constructs a pixel
	 * @param c - the color 
	 * @param left - the pixel to the left
	 * @param up - the pixel above
	 * @param right - the pixel to the right
	 * @param down - the pixel below
	 */
	public Pixel(Color c, Pixel left, Pixel up, Pixel right, Pixel down) {
		this.color = c;
		this.left = left;
		this.up = up;
		this.right = right;
		this.down = down;
		this.direction = Direction.HERE;
		
		
		if(left != null) {
			left.right = this; //"this" is the literal object you are creating rn
		}
		if(up != null) {
			up.down = this;
		}
		if(right != null) {
			right.left = this;
		}
		if(down != null) {
			down.up = this;
		}
	}	
	
}
