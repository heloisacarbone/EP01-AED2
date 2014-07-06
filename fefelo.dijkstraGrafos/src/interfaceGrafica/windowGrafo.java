package interfaceGrafica;

import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.util.*;

import grafo.Grafo;

import javax.swing.*;

public class windowGrafo extends JFrame{
	private static Graphics g;
	
	
	public windowGrafo(Map<String, String> caminhos) {
		Canvas canvas = new Canvas();
		super.setTitle("Teste tipo 2");
		canvas.setSize(500,500);
	
		
		g = canvas.getGraphics();
		
		/* Não funciona 
		g.drawLine(0,0,100,100);
		
		canvas.paint(g);
		
		canvas.update(g);
		
		super.add(canvas);
		
	*/
	}
}
