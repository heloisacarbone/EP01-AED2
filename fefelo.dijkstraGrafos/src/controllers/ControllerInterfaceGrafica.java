package controllers;

import grafo.Grafo;
import interfaceGrafica.windowGrafo;

import java.awt.Dimension;
import java.awt.Toolkit;
import java.util.Map;

import javax.swing.JFrame;

public class ControllerInterfaceGrafica {
	public static void createWindow(Grafo grafo, Map<String, String> caminho) {
		windowGrafo window = new windowGrafo(grafo, caminho);
		window.setPreferredSize(new Dimension(500,500));
		window.setMinimumSize(new Dimension(500,500));
		Dimension dim = Toolkit.getDefaultToolkit().getScreenSize();
		window.setLocation(dim.width/2-window.getSize().width/2, dim.height/2-window.getSize().height/2);
		window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		window.setVisible(true);
	}
}
