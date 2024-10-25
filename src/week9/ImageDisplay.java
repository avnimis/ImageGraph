package week9;

import java.io.File;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.awt.Color;
import java.awt.image.BufferedImage;
import java.awt.*;
import javax.imageio.ImageIO;

import javax.swing.*;

public class ImageDisplay {
	
	
	public void reDraw(JFrame frame, BufferedImage oldImg, BufferedImage newImg) {
		frame.getContentPane().removeAll();
		frame.repaint();
		frame.getContentPane().setLayout(new FlowLayout());
	    frame.getContentPane().add(new JLabel(new ImageIcon(oldImg)));
	    frame.getContentPane().add(new JLabel(new ImageIcon(newImg)));
	    frame.pack();
	    frame.setVisible(true);
	}
	
	public BufferedImage scale(BufferedImage org, int factor) {
		
		int oldHeight = org.getHeight();
		int oldWidth = org.getWidth();
		
		int newHeight = oldHeight*factor;
		int newWidth = oldWidth*factor;
		
		BufferedImage newImg = new BufferedImage(newWidth, newHeight, BufferedImage.TYPE_INT_RGB);
		
		for(int row = 0; row < newHeight; row++) {
			for(int col = 0; col < newWidth; col++) {
				newImg.setRGB(col, row, org.getRGB(col/factor,row/factor));
			
			}
		
		}
	        		 

	    
		return newImg;		
	}
	
	
	public void printMenu() {
		System.out.println("Please make a selection");
		System.out.println("1.) Scale Images");
		System.out.println("2.) Flip Images");
		System.out.println("3.) Quit");
	}
	

	
   public static void main(String args[]) throws Exception{
	   
 
	  //read images
	  File originalFile1= new File("cool.png");
      BufferedImage img1 = ImageIO.read(originalFile1);
      
      File originalFile2= new File("multi.png");
      BufferedImage img2 = ImageIO.read(originalFile2);
     
      //create an instance of the class
      ImageDisplay im = new ImageDisplay();
      
      
      //Create a frame
      JFrame f = new JFrame();
      f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
      
      im.reDraw(f, img1, img2);
      
      
      System.out.println("Welcome. The two images have been loaded");
      
      //keep track if images have been flipped (right with left)
      boolean flip = false;
      
      //now create a user menu to put in choices
  
      boolean shouldQuit = false;
		
      Scanner scan = new Scanner(System.in);
      int choice = 0;
      int factor = 1;
      while(!shouldQuit) {
			im.printMenu();
			
			try {
				choice = scan.nextInt();
			} 
			
			catch (InputMismatchException e) {
				System.out.println("Input should be an int");
				choice = 3;
			}
			
			switch(choice) {
			case 1: 
				System.out.println("What factor would you like?");
				try {
					factor = scan.nextInt();
				}
				catch (InputMismatchException e) {
					System.out.println("Input should be an int");
				}
				
				img1 = im.scale(img1, factor);
				img2 = im.scale(img2, factor);
				im.reDraw(f, img1, img2);
				break;
			case 2:
				System.out.println("Flipping Images");
				if(flip) {
					im.reDraw(f, img1, img2);
					flip = false;
				}
				else {
					im.reDraw(f, img2, img1);
					flip = true;
				}
				break;
			case 3: 
				System.out.println("Thanks for playing");
				shouldQuit = true;
				break;
			default:
				System.out.println("That is not a valid option.");
				break;
				
			}
			
			

      
      }
    System.exit(0);
   }
}




	
	
	
	
