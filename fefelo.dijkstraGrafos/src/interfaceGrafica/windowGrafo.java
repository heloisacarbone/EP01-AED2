package interfaceGrafica;

import java.awt.BasicStroke;
import java.awt.Canvas;
import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Toolkit;
import java.util.*;

import grafo.Vertice;

import javax.swing.*;

public class windowGrafo extends JFrame{	

	public windowGrafo(final List<Vertice> verticesGrafo, final Map<String, String> caminhos, 
			int size, final Vertice inicio, final Vertice fim, final int qtdGrafos) {
            
            VisualGrafo visual = new VisualGrafo(); 
            
		
		this.setLayout(null);
		Canvas canvas = new Canvas(){
			@Override
			public void paint(Graphics g) {
				super.paint(g);
				
				paintCaminhoMinimo(g, verticesGrafo, caminhos, inicio.getZ(), fim.getZ());
				
				Iterator<Vertice> it = verticesGrafo.iterator();
				while (it.hasNext()) {
		            Vertice v = it.next();
		            if (v.getZ() == inicio.getZ() || v.getZ() == fim.getZ()) {
		            	paintVertices(g, v, true);
		            } else {
		            	paintVertices(g, v, false);
		            }
			    }
				
			}
		};
		
		super.setTitle("Teste tipo 2");
		
		canvas.setSize(size,size/2);
	
		super.add(canvas);
		
		super.setPreferredSize(new Dimension(size,size/2));
		super.setMinimumSize(new Dimension(size,size/2));
		
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		super.setLocation(dim.width/2-super.getSize().width/2, dim.height/2-super.getSize().height/2);
		super.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		
                visual.getContentPane().add(canvas);
                visual.pack();
                visual.setVisible(true);
                
                
                
		
	}

	
	public void paintVertices(Graphics graphic, Vertice v, Boolean isPrinciple) {
		if (isPrinciple) {
			graphic.setColor(Color.RED);
		} else {
			graphic.setColor(Color.BLACK);
		}
		graphic.fillOval(v.getX()/20+10, v.getY()/20+10, 20, 20);
		graphic.drawString(v.getZ(), v.getX()/20+10, v.getY()/20+10);
	}
	
	public void paintCaminhoMinimo(Graphics graphic, List<Vertice> verticesGrafo, 
			Map<String, String> caminho, String inicio, String fim ) {
		
		String vertice0 = inicio;
		String vertice1 = caminho.get(inicio);
		
		while (vertice1 != fim) {
			printLine(graphic, verticesGrafo, vertice0, vertice1);
			vertice0 = vertice1;
			vertice1 = caminho.get(vertice0);
			
		}
		printLine(graphic, verticesGrafo, vertice0, vertice1);
			
	}
	
	public void printLine(Graphics graphic, List<Vertice> verticesGrafo, String v0, String v1) {
		graphic.setColor(Color.BLACK);
		int x0 = 0, y0 = 0 , x1 = 0, y1 = 0;
		Iterator<Vertice> it = verticesGrafo.iterator();
		while (it.hasNext()) {
            Vertice v = it.next();
            if (v.getZ() == v0) {
            	x0 = v.getX();
            	y0 = v.getY();
            }
            
            if (v.getZ() == v1) {
            	x1 = v.getX();
            	y1 = v.getY();
            }
	    }
		
		graphic.drawLine(x0/20+10, y0/20+15, x1/20+10, y1/20+15);
	
	}

}