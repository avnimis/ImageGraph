package week9;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;
import java.util.function.Function;

import javax.imageio.ImageIO;
import javax.swing.JFrame;
import javax.swing.WindowConstants;


public class UserMenu {

	public static ImageGraph ig; 
	public static ImageDisplay id; 
	public static BufferedImage img1;
	public static BufferedImage img2;
	public static int index; 
	public static Function<Pixel, Double> blue; 
	public static Function<Pixel, Double> energy; 
	
	
	
	
	public static void printMenu() {
		System.out.println("Please make a selection");
		System.out.println("B) Highlight the Bluest Column");
		System.out.println("E) Highlight Lowest Energy Column");
		System.out.println("D) Delete the last Highlighted Column" );
		System.out.println("U) Undo");
		System.out.println("Q) Quit");
	}
	
	
	
	public static void printResponse(String selection, ImageGraph pg) {
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

		
		switch(selection) {
		case "B": 
			blue = p -> ig.bluest(p);
			index = ig.findIndex(blue);
			ig.highlightSeam(index, true);
			img2 = ig.save();
			img2 = id.scale(img2, 40);
			id.reDraw(f,  img1,  img2);
			System.out.println("Bluest seam highlighted mreow");
			
			
			break;
		case "E":
			
			energy = p -> ig.energy(p);

			index = ig.findIndex(energy);
			ig.highlightSeam(index, false);
			img2 = ig.save();
			img2 = id.scale(img2, 40);
			id.reDraw(f,  img1,  img2);
			System.out.println("Lowest energy seam highlighted bark arf");
			
			
			break;
		case "D": 
			
			ig.removeSeam(index);
			img2 = ig.save();
			img2 = id.scale(img2, 40);
			id.reDraw(f,  img1,  img2);
			System.out.println("Last highlighted seam deleted neeiIIGHHhhh");
			
			
			break; 
		case "U":
			
			if(ig.undoDelete()) {
				img2 = ig.save();
				img2 = id.scale(img2, 40);
				id.reDraw(f,  img1,  img2);
				System.out.println("Deleted seam restored oink oink");
			} else {
				System.out.println("No deleted seams to restore mooo");
			}
			
			
			break;
		case "Q":
			
			
			break;
		default:
			System.out.println("That is not a valid option baaaa");
			break;
			
		}
	}
	
	
	public static void main(String[] args) throws IOException {
		
		boolean shouldQuit = false;
		
		ig = new ImageGraph("multi.png");
		id = new ImageDisplay();
		
		
		
		File originalFile1= new File("multi.png");
	    img1 = ImageIO.read(originalFile1);
	    img2 = ig.save();
		
	    
		
		JFrame f = new JFrame();
		f.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
	    
		
		// do this after everytime you save, before redrawing again
		img1 = id.scale(img1, 40);
		img2 = id.scale(img2, 40);
		id.reDraw(f, img1, img2);
	    
		
		Scanner scan = new Scanner(System.in);
		String choice = "";
		while(!shouldQuit) {
			printMenu();
			
			try {
				choice = scan.nextLine();
			} 
			
			catch (InputMismatchException e) {
				System.out.println("Input should be an letter squeak squeak");
				choice = "Q";
			}
			
			
			printResponse(choice, ig);
			if(choice.equals("Q")) {
				shouldQuit = true;
			}
		}
		scan.close();

		

	}
	
	
}
