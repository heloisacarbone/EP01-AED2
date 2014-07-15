package controllers;

import grafo.Grafo;
import grafo.Vertice;
import interfaceGrafica.windowGrafo;

import java.util.Map;



public class ControllerInterfaceGrafica {
	public static void createWindow(Grafo grafo, Map<String, String> caminho, Vertice inicio, Vertice fim) {
		int numVertices = grafo.getgrafo().size();
		windowGrafo window = new windowGrafo(grafo.getgrafo(), caminho, numVertices*100, inicio, fim);
		
	}
}