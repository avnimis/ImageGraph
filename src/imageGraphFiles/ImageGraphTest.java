package imageGraphFiles;

import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.*;

import java.awt.Color;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.function.Function;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class ImageGraphTest {

	public ImageGraph ig; 
	public ImageGraph ig2;
	public Pixel p1; 
	public Pixel p2; 
	public Function<Pixel, Double> blue;
	public Function<Pixel, Double> energy; 
	
	@BeforeEach
	public void init() {
		
		ArrayList<ArrayList<Color>> al1 = new ArrayList<>();
		al1.add(new ArrayList<Color>());
		al1.get(0).add(new Color(255,0,255));
		al1.get(0).add(new Color(0,255,0));
		al1.get(0).add(new Color(0,255,0));
		al1.add(new ArrayList<Color>());
		al1.get(1).add(new Color(0,255,0));
		al1.get(1).add(new Color(255,0,255));
		al1.get(1).add(new Color(0,255,0));
		al1.add(new ArrayList<Color>());
		al1.get(2).add(new Color(0,255,0));
		al1.get(2).add(new Color(0, 255,0));
		al1.get(2).add(new Color(255,0,255));
		
		ig = new ImageGraph(al1);
		
		ArrayList<ArrayList<Color>> al2 = new ArrayList<>(); 
		al2.add(new ArrayList<Color>());
		al2.get(0).add(new Color(0,255,0));
		al2.get(0).add(new Color(255,0,255));
		al2.get(0).add(new Color(0,255,0));
		al2.add(new ArrayList<Color>());
		al2.get(1).add(new Color(0,255,0));
		al2.get(1).add(new Color(255,0,255));
		al2.get(1).add(new Color(0,255,0));
		
		ig2 = new ImageGraph(al2);
		
		
		blue = p -> ig.bluest(p);
		energy = p -> ig.energy(p);
		
		p1 = ig.getBottomRow().get(0);
		p2 = ig.getBottomRow().get(1);
		
	}
	
	@Test 
	public void testEnergy() {
		
		assertEquals(ig.energy(p1), 120.208, 0.02);
		assertEquals(ig.energy(p2), 268.794, 0.2);
		
	}
	
	public void testBluest() {
		
		assertEquals(ig.bluest(p1), 0, 0);
		assertEquals(ig.bluest(p2), 0, 0);
		
	}
	
	
	@Test 
	public void testToString() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("(255, 0, 255)(0, 255, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(255, 0, 255)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 255, 0)(255, 0, 255)\n");
		
		
		StringBuilder sb2 = new StringBuilder();
		sb2.append("(0, 255, 0)(255, 0, 255)(0, 255, 0)\n"
				+ "(0, 255, 0)(255, 0, 255)(0, 255, 0)\n");
		
		assertEquals(ig.toString(), sb.toString());
		assertEquals(ig2.toString(), sb2.toString());
		
	}
	
	@Test
	public void testFindIndex() {
		
		assertEquals(ig.findIndex(blue), 2);
		assertEquals(ig.findIndex(energy), 0);
		
		
	}
	
	@Test 
	public void testHighlightSeam() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("(0, 0, 255)(0, 255, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 0, 255)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 255, 0)(255, 0, 255)\n"
				);
		
		int index = ig.findIndex(blue);
		ig.highlightSeam(index, true);
		assertEquals(ig.toString(), sb.toString());
		
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("(0, 0, 255)(255, 0, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 0, 255)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 255, 0)(255, 0, 255)\n");
		
		index = ig.findIndex(energy);
		ig.highlightSeam(index, false);
		assertEquals(ig.toString(), sb1.toString());
		
	}
	
	@Test
	public void testRemoveSeam() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("(0, 255, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 255, 0)\n"
				);
		
		int index = ig.findIndex(blue);
		ig.removeSeam(index);
		assertEquals(ig.toString(), sb.toString());
		
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("(0, 255, 0)\n"
				+ "(0, 255, 0)\n"
				+ "(0, 255, 0)\n");
		
		index = ig.findIndex(energy);
		ig.removeSeam(index);
		assertEquals(ig.toString(), sb1.toString());
		
		
	}
	
	
	@Test
	public void testUndoDelete() {
		
		StringBuilder sb = new StringBuilder();
		sb.append("(255, 0, 255)(0, 255, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(255, 0, 255)(0, 255, 0)\n"
				+ "(0, 255, 0)(0, 255, 0)(255, 0, 255)\n"
				);
		
		int index = ig.findIndex(blue);
		ig.removeSeam(index);
		ig.undoDelete();
		assertEquals(ig.toString(), sb.toString());
		
		
		StringBuilder sb1 = new StringBuilder();
		sb1.append("(255, 0, 255)(0, 255, 0)(0, 255, 0)\n"
				+ "(0, 255, 0)(255, 0, 255)(0, 255, 0)\n"
				+ "(0, 255, 0)(255, 0, 255)(255, 0, 255)\n");
		
		index = ig.findIndex(energy);
		ig.removeSeam(index);
		ig.undoDelete();
		assertEquals(ig.toString(), sb1.toString());
		
	}
	
	
	@Test
	public void testSave() {
		
		BufferedImage img = ig.save();
		
		assertEquals(img.getWidth(), 3);
		assertEquals(img.getHeight(), 3);
		
		
	}
	
	
}
