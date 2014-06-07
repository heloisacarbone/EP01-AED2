/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fefelo.dijkstraGrafos.grafo;


import fefelo.dijkstraGrafos.grafo.Vertice;

import java.util.ArrayList;
import java.util.List;

public class Grafo {
	private List<Vertice> adjList = new ArrayList<Vertice>();


	public void startGrafo(List<Vertice> list) {		// Inicializa o Grafo com uma lista de vertices
            this.adjList.addAll(list);
	}
	public void addvertice(Vertice x){			// Adiciona um Vertice ao Grafo
		this.adjList.add(x);
	}
	public List<Vertice> getgrafo(){		// Retorna a lista com os vertices
		return this.adjList;
	}
	
	public double getPesoAresta(Vertice x, Vertice y) {	// Calcula o peso da aresta baseado nas fefelo.dijkstraGrafosrdenadas de cada vertice
		int n = (x.getX() - y.getX())^2;
		int m = (x.getY() - y.getY())^2;
		return Math.sqrt(n+m);
	}
	

}