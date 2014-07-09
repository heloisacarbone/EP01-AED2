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
	
	public windowGrafo(Grafo grafo, Map<String, String> caminhos) {
		this.setLayout(null);
		Canvas canvas = new Canvas(){
			@Override
			public void paint(Graphics g) {
				
				super.paint(g);
				g.drawLine(0,0,100,100);
			}
		};
		super.setTitle("Teste tipo 2");
		canvas.setSize(500,500);
	
		
		
		
		super.add(canvas);
		
	
	}
}
