package week9;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;
import java.util.Stack;
import java.util.function.Function;

import javax.imageio.ImageIO;

/**
 * A class that represents an image. 
 */
public class ImageGraph {

	
		// make pixels wrap around OR make the non existent pixels maximums 
		// could possible make it so the borders are never chosen for seems 
		private int width; 
		private int oldWidth;
		private int height; 
		private ArrayList<Pixel> bottomRow;
		private Stack<Action> actions; 
		
		public enum  Direction{
			HERE, ABOVE, ABOVERIGHT, ABOVELEFT, WRAP
		}
		
		
		/**
		 * Constructs an ImageGraph by taking in a file name
		 * @param fileName - the name of the file
		 */
		public ImageGraph(String fileName) {
		
			this.actions = new Stack<>(); 
			this.bottomRow = new ArrayList<>(); 
			
			
			try(Scanner scan = new Scanner(new File(fileName))) {
				File file = new File(fileName);
				BufferedImage oldImg = ImageIO.read(file);
				
				
				this.width = oldImg.getWidth(); 
				this.oldWidth = this.width; 
				this.height = oldImg.getHeight();
				
				BufferedImage imageFromFile = new BufferedImage(
						width, 
						height, 
						BufferedImage.TYPE_INT_RGB);
				
				
				
				int bottomRowIndex = imageFromFile.getHeight() - 1;
				int rightColIndex = imageFromFile.getWidth() - 1; 
				
				ArrayList<Pixel> currentRow = new ArrayList<>();
				ArrayList<Pixel> previousRow = new ArrayList<>(); 
			
			
			
				for(int row = bottomRowIndex ; row >= 0; row--) {
					for(int col = 0; col <= rightColIndex; col++) {
						
						int pixel = oldImg.getRGB(col, row);
						
						Color originalColor = new Color(pixel);
					
						Pixel currentPixel;
						Pixel left; 
						Pixel up; 
						Pixel right; 
						Pixel down; 

						if(col == 0) {
							left = null; 
						} else { 
							left = currentRow.get(col - 1);
						}
				
						up = null; 
				
						right = null;
				
						if(row == bottomRowIndex) {
							down = null; 
						} else {
							down = previousRow.get(col);
						}
						
						currentPixel = new Pixel(originalColor, left, up, right, down); 
						
						
						// edge cases
						if(col == rightColIndex) {
							currentPixel.right = new Pixel(originalColor, currentPixel, null, null, null);
							currentPixel.right.direction = Direction.WRAP;
						}
						if(col == 0) {
							currentPixel.left = new Pixel(originalColor, null, null, currentPixel, null);
							currentPixel.left.direction = Direction.WRAP;	
						}
						if(row == bottomRowIndex) {
							currentPixel.down = new Pixel(originalColor, null, currentPixel, null, null);
							currentPixel.down.direction = Direction.WRAP; 
						} 
						if(row == 0) {
							currentPixel.up = new Pixel(originalColor, null, null, null, currentPixel);
							currentPixel.up.direction = Direction.WRAP;	
						}
						
						// corner cases
						if(col == rightColIndex && row == bottomRowIndex) {
							Pixel temp = new Pixel(originalColor, null, null, null, null);
							currentPixel.down.right = temp; 
							currentPixel.right.down = temp; 
							currentPixel.down.right.direction = Direction.WRAP;
						}
						if(col == rightColIndex && row == 0) {
							Pixel temp =  new Pixel(originalColor, null, null, null, null);
							currentPixel.up.right = temp;
							currentPixel.right.up = temp; 
							currentPixel.up.right.direction = Direction.WRAP;
						}
						if(col == 0 && row == bottomRowIndex) {
							Pixel temp = new Pixel(originalColor, null, null, null, null);
							currentPixel.down.left = temp; 
							currentPixel.left.down = temp; 
							currentPixel.down.left.direction = Direction.WRAP;
						}
						if(col == 0 && row == 0) {
							Pixel temp = new Pixel(originalColor, null, null, null, null);
							currentPixel.up.left = temp; 
							currentPixel.left.up = temp; 
							currentPixel.up.left.direction = Direction.WRAP;
						}
						
						currentRow.add(currentPixel);
					}
				
					if(row == bottomRowIndex) {
						this.bottomRow = currentRow; 
					}
						previousRow = currentRow; 
						currentRow = new ArrayList<Pixel>();
				}
				
			} catch (Exception ex) {
				System.out.println("File not found");
				ex.printStackTrace();
					
			}
		}
		
		/**
		 * Constucts an ImageGraph by taking in an ArrayList of ArrayList of Colors
		 * @param image - the arraylist to be copied into a graph
		 */
		public ImageGraph(ArrayList<ArrayList<Color>> image) { 
			
			this.actions = new Stack<>(); 
			this.bottomRow = new ArrayList<>(); 	
			
			
			this.height = image.size(); 
			this.width = image.get(0).size(); 
			this.oldWidth = this.width; 
			
			int bottomRowIndex = height - 1;
			int rightColIndex = width - 1; 			
			
			ArrayList<Pixel> currentRow = new ArrayList<>();
			ArrayList<Pixel> previousRow = new ArrayList<>(); 
			
			
			for(int row = bottomRowIndex ; row >= 0; row--) {
				for(int col = 0; col <= rightColIndex; col++) {
					
					int pixel = image.get(row).get(col).getRGB(); 
					
					Color originalColor = new Color(pixel);
				
					Pixel currentPixel;
					Pixel left; 
					Pixel up; 
					Pixel right; 
					Pixel down; 

					if(col == 0) {
						left = null; 
					} else { 
						left = currentRow.get(col - 1);
					}
			
					up = null; 
			
					right = null;
			
					if(row == bottomRowIndex) {
						down = null; 
					} else {
						down = previousRow.get(col);
					}
					
					currentPixel = new Pixel(originalColor, left, up, right, down); 
			
					// edge cases
					if(col == rightColIndex) {
						currentPixel.right = new Pixel(originalColor, currentPixel, null, null, null);
						currentPixel.right.direction = Direction.WRAP;
					}
					if(col == 0) {
						currentPixel.left = new Pixel(originalColor, null, null, currentPixel, null);
						currentPixel.left.direction = Direction.WRAP;	
					}
					if(row == bottomRowIndex) {
						currentPixel.down = new Pixel(originalColor, null, currentPixel, null, null);
						currentPixel.down.direction = Direction.WRAP; 
					} 
					if(row == 0) {
						currentPixel.up = new Pixel(originalColor, null, null, null, currentPixel);
						currentPixel.up.direction = Direction.WRAP;	
					}
					
					// corner cases
					if(col == rightColIndex && row == bottomRowIndex) {
						Pixel temp = new Pixel(originalColor, null, null, null, null);
						currentPixel.down.right = temp; 
						currentPixel.right.down = temp; 
						currentPixel.down.right.direction = Direction.WRAP;
					}
					if(col == rightColIndex && row == 0) {
						Pixel temp =  new Pixel(originalColor, null, null, null, null);
						currentPixel.up.right = temp;
						currentPixel.right.up = temp; 
						currentPixel.up.right.direction = Direction.WRAP;
					}
					if(col == 0 && row == bottomRowIndex) {
						Pixel temp = new Pixel(originalColor, null, null, null, null);
						currentPixel.down.left = temp; 
						currentPixel.left.down = temp; 
						currentPixel.down.left.direction = Direction.WRAP;
					}
					if(col == 0 && row == 0) {
						Pixel temp = new Pixel(originalColor, null, null, null, null);
						currentPixel.up.left = temp; 
						currentPixel.left.up = temp; 
						currentPixel.up.left.direction = Direction.WRAP;
					}
					
					
					currentRow.add(currentPixel);
				}
				
				if(row == bottomRowIndex) {
					this.bottomRow = currentRow; 
						
				}
				
				previousRow = currentRow; 
				currentRow = new ArrayList<Pixel>();
			}
			
		}
		
		/**
		 * a getter method for the bottomRow
		 * @return - returns the bottomRow
		 */
		public ArrayList<Pixel> getBottomRow(){
			return bottomRow; 
		}
		
		/**
		 * calculates the average of the red blue and green of a pixel
		 * @param p - the pixel to be computed
		 * @return - the brightness
		 */
		private double br(Pixel p) {
			return (p.color.getBlue() + p.color.getRed() + p.color.getGreen()) / 3;
		}
		
		/**
		 * calculates the energy of a pixel
		 * @param p - the pixel to be computer
		 * @return - the energy
		 */
		public double energy(Pixel p) {
			
			
			double left = br(p.left);
			
			double right = br(p.right);
			
			double up = br(p.up);
			
			double down = br(p.down);
			
			double upright;
			if(p.right.up != null) {
				upright = br(p.right.up);
			} else {
				upright = up; 
			}
			
			double upleft;
			if(p.left.up != null) {
				upleft = br(p.left.up);
			} else {
				upleft = up; 
			}
			
			double downright;
			if(p.right.down != null) {
				downright = br(p.right.down);
			} else {
				downright = down; 
			}
			
			
			double downleft;
			if(p.left.down != null) {
				downleft = br(p.left.down);
			} else {
				downleft = down; 
			}
			
			
			double horE;
			double verE;
		
			
			horE = upleft + 2 * left + downleft - (upright + 2 * right + downright); 
			verE = upleft + 2 * up + upright - (downleft + 2 * down + downright);
		
			return Math.sqrt((horE * horE) + (verE * verE));
		}
	
		/**
		 * returns 255 - the blue to compensate for the generic findSeam function
		 * findSeam finds the min of values, so subtracting the blue from the maximum possible
		 * blue will return the most blue when finding the minimum
		 * @param p - the pixel to be computed 
		 * @return - the amount of blue
		 */
		public double bluest(Pixel p) {
			return 255 - p.color.getBlue(); 
		}
	
		/**
		 * finds the index on the bottomRow that starts the seam 
		 * recurrs on findSeam to define all pixels in the seam
		 * @param f - takes in either the bluest function or energy function, 
		 * depending on what the seam is being found on 
		 * @return - returns the index of the first pixel in the seam
		 */
		public int findIndex(Function<Pixel, Double> f) { 
			
			ArrayList<Pixel> seam = new ArrayList<>(); 
			
			
			int index = 0;
			double minValue = 1000; 
				
			for(int i = 0; i < bottomRow.size(); i++) {
				double tempValue = f.apply(bottomRow.get(i));
				
				
				if(tempValue < minValue) {
					
					minValue = tempValue; 
					index = i; 
				}
			}
			
			
			seam.add(bottomRow.get(index));
			
			findSeam(bottomRow.get(index), f, seam);
			
			
			Action tempAction = new Action(index, seam);
			actions.push(tempAction);
			return index; 
			
			
			
		}
		
		
		/**
		 * finds and redefines direction for all the pixels in the seam by comparing.
		 * adds all the pixels to an arrayList
		 * @param p - the previous pixel in the seam
		 * @param f - either blue or energy functions
		 * @param seam - the list saving the seam
		 */
		private void findSeam(Pixel p, Function<Pixel, Double> f, ArrayList<Pixel> seam) {
			
			if(p.up.direction == Direction.WRAP) {
				p.direction = Direction.HERE; 
				return;
			}
			
			
			double up = f.apply(p.up);
			
			double upRight; 
			if(p.up.right.direction != Direction.WRAP) {
				upRight = f.apply(p.up.right);
			} else {
				upRight = Integer.MAX_VALUE;
			}
			
			double upLeft;
			if(p.up.left.direction != Direction.WRAP) {
				upLeft = f.apply(p.up.left); 
			} else {
				upLeft = Integer.MAX_VALUE;
			}
			
			
			p.direction = Direction.ABOVE; 
			Pixel temp = p.up;
			
			
			if(upRight < up) {
				p.direction = Direction.ABOVERIGHT;
				temp = p.up.right; 
				
				if(upRight > upLeft) {
					p.direction = Direction.ABOVELEFT; 
					temp = p.up.left;
				}
				
			}
			
			if(upLeft < up) { 
				p.direction = Direction.ABOVELEFT; 
				temp = p.up.left; 
			}
			
			
			seam.add(temp);
			findSeam(temp, f, seam);
			
		}
		
		/**
		 * a helper function that recolors a pixel 
		 * @param isBlue - true for blue, false for red
		 * @param p - the pixel to recolor
		 */
		private void reColor(Boolean isBlue, Pixel p) {
			Color reColor; 
			
			if(isBlue) {
				reColor = new Color(0, 0, 255);
			} else {
				reColor = new Color(255, 0, 0);
			}
				
			p.color = reColor;
			
		}
		
		/**
		 * highlights a seam recursively
		 * @param index - the start of the seam
		 * @param isBlue - true for blue, false for red
		 */
		public void highlightSeam(int index, boolean isBlue) {
			
			Pixel iter1 = bottomRow.get(index);
			Pixel iter2; 
			 
			
			reColor(isBlue, iter1);
			
			if(iter1.direction == Direction.HERE) {
				return; 
			}
			
			
			if(iter1.direction == Direction.ABOVE) {
				iter2 = iter1.up;
			} else if (iter1.direction == Direction.ABOVERIGHT) {
				iter2 = iter1.up.right;
			} else {
				iter2 = iter1.up.left;
			}
			
			highlightSeam(iter1, iter2, isBlue);
			
			
		}
		
		/**
		 * helper function for highlight seam
		 * @param iter1 - the previous pixel 
		 * @param iter2 - the next pixel in the seam to be recolored
		 * @param isBlue - true for blue, false for red
		 */
		private void highlightSeam(Pixel iter1, Pixel iter2, boolean isBlue) {
			
			reColor(isBlue, iter2); 
			
			
			if(iter2.direction == Direction.HERE) {
				reColor(isBlue, iter2);
				return; 
			}
			
			
			iter1 = iter2; 
			
			if(iter2.direction == Direction.ABOVE) {
				iter2 = iter2.up;
			} else if (iter2.direction == Direction.ABOVERIGHT) {
				iter2 = iter2.up.right;
			} else {
				iter2 = iter2.up.left;
			}
			
			highlightSeam(iter1, iter2, isBlue);
			
			
		}
		
		/**
		 * a helper function that removes a pixel
		 * @param p - the pixel 
		 * @param d - the direction the pixel
		 */
		private void remove(Pixel p, Direction d) {
			
			
			
			p.right.left = p.left; 
			p.left.right = p.right;
			
			if(p.left.direction == Direction.WRAP) {
				Pixel temp = p.right;  
				temp.left = new Pixel(temp.color, null, null, temp, null);
				temp.left.direction = Direction.WRAP; 
			}
			if(p.right.direction == Direction.WRAP) {
				Pixel temp = p.left;
				temp.right = new Pixel(temp.color, temp, null, null, null);
				temp.right.direction = Direction.WRAP; 
			}
			
			
			if(d == Direction.ABOVELEFT) {
				p.left.up = p.up; 
				p.up.down = p.left;
			} else if(d == Direction.ABOVERIGHT) {
				p.right.up = p.up; 
				p.up.down = p.right; 
			}
			
			
		
		}
		
		/**
		 * removes a seam from the graph recursively
		 * @param index - the index starting pixel of the seam
		 */
		public void removeSeam(int index) {
			
			Pixel iter1 = bottomRow.get(index);
			Pixel iter2; 
			
			
			remove(iter1, iter1.direction);
			
			if(iter1.direction == Direction.WRAP) {
				return; 
			}
			
			
			bottomRow.remove(index);
			width-- ;
			
			if(iter1.direction == Direction.ABOVE) {
				iter2 = iter1.up;
			} else if (iter1.direction == Direction.ABOVERIGHT) {
				iter2 = iter1.up.right;
			} else {
				iter2 = iter1.up.left;
			}
			
			removeSeam(iter1, iter2);
			
		}	
		
		/**
		 * helper function for removeSeam
		 * @param iter1 - the previous pixel
		 * @param iter2 - the pixel to the removed
		 */
		private void removeSeam(Pixel iter1, Pixel iter2) {
			
			remove(iter2, iter2.direction);
			
			if(iter2.direction == Direction.HERE) {
				return; 
			}
			
			
			iter1 = iter2; 
			
			if(iter2.direction == Direction.ABOVE) {
				iter2 = iter2.up;
			} else if (iter2.direction == Direction.ABOVERIGHT) {
				iter2 = iter2.up.right;
			} else if (iter2.direction == Direction.ABOVELEFT){
				iter2 = iter2.up.left;
			}
			
			removeSeam(iter1, iter2);
			
			
		}
			
		
		/**
		 * saves the graph to a buffered image in a file named "images"
		 * @return - returns the buffered image
		 */
		public BufferedImage save() { 
			BufferedImage newImg = new BufferedImage(
					width, 
					height, 
					BufferedImage.TYPE_INT_RGB
					);
			
			Pixel p = bottomRow.get(width - 1);
			Pixel p2 = p;
			
			
			for(int y = height - 1; y >= 0; y--) {
				for(int x = width - 1; x >= 0; x--) {
					newImg.setRGB(x, y, p2.color.getRGB());
					
					
					p2 = p2.left; 
					
					
				}
				p = p.up;
				p2 = p;
			}
			
			File newFile = new File("images/image.png");
			try {
				ImageIO.write(newImg, "png", newFile);
			} catch (IOException e) {
				e.printStackTrace();
			}
			
			return newImg; 
		}
		
		/**
		 * undos the last remove seam
		 * @return - true if delted, false if stack is empty/no seams are deleted
		 */
		public boolean undoDelete() {
			
			if(actions.empty()) {
				return false;
			}
			
			if(oldWidth == width) {
				return false; 
			}
			
			
			width++;
			Action temp = actions.pop();
			int index = temp.getIndex(); 
			ArrayList<Pixel> seam = temp.getSeam();
			
			Pixel iter1 = seam.get(0); 
			iter1.color = iter1.oldColor;
			bottomRow.add(index, iter1);
			
			if(index != bottomRow.size() - 1) {
				bottomRow.get(index + 1).left = iter1; 
			}
			if(index != 0) {
				bottomRow.get(index - 1).right = iter1; 
			}
			
			
			if(iter1.left.direction == Direction.WRAP) {
				Pixel p = new Pixel(iter1.color, null, null, null, null);
				iter1.down.right = p; 
				iter1.right.down = p; 
				iter1.down.right.direction = Direction.WRAP; 
				
			} else if (iter1.left.direction == Direction.WRAP) {
				Pixel p = new Pixel(iter1.color, null, null, null, null);
				iter1.down.left = p; 
				iter1.left.down = p; 
				iter1.down.left.direction = Direction.WRAP;
			}
			
			
			for(int i = 1; i < seam.size(); i++) {
				
				Pixel iter2 = seam.get(i);
				iter2.color = iter2.oldColor;
				
				if(iter1.direction == Direction.ABOVELEFT) {
					
					iter1.up = iter1.left.up; 
					iter1.up.left.right = iter2; 
					iter1.up.left = iter2; 
					iter1.left.up = iter2; 
					
				} else if (iter1.direction == Direction.ABOVERIGHT) {
					iter1.up = iter1.right.up; 
					iter1.up.right.left = iter2; 
					iter1.up.right = iter2; 
					iter1.right.up = iter2; 
					
				} else if (iter1.direction == Direction.ABOVE) {
					iter1.up = iter2; 
					
					if(iter1.right.up != null) {
						iter1.right.up.left = iter2; 
					}
					if(iter1.left.up != null) {
						iter1.left.up.right = iter2; 	
					}
				}	
				
				
				if(iter2.left.direction == Direction.WRAP) {
					iter2.left = new Pixel(iter2.color, null, null, iter2, null);
					iter2.left.direction = Direction.WRAP; 
				} else if (iter2.left.direction == Direction.WRAP) {
					iter2.right = new Pixel(iter2.color, iter2, null, null, null);
					iter2.right.direction = Direction.WRAP;
				}
				
				
				iter1 = iter2; 	
				
			}
			
			return true; 
			
			
			
		}
		
		/**
		 * turns an imageGraph into a string representation for testing purposes  
		 * @return - a string representation 
		 */
		@Override
		public String toString() {
			
			StringBuilder sb = new StringBuilder();
			Pixel iter1 = bottomRow.get(0);
			Pixel iter2;
			
			while(iter1.up.direction != Direction.WRAP) {
				iter1 = iter1.up;
			}
			
			iter2 = iter1; 
			
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					
					sb.append("(" 
							+ iter2.color.getRed() + ", " 
							+ iter2.color.getGreen() + ", " 
							+ iter2.color.getBlue() + ")"
							);
					iter2 = iter2.right; 
				}
				
				sb.append(System.lineSeparator());
				iter1 = iter1.down; 
				iter2 = iter1; 
				
			}
			
			return sb.toString();
			
		}
		
		
		
		
	}




