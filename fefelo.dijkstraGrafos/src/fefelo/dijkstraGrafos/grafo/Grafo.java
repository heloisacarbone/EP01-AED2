/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fefelo.dijkstraGrafos.grafo;


import fefelo.dijkstraGrafos.grafo.Vertice;
import fefelo.dijkstraGrafos.grafo.Aresta;

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
	public List getgrafo(){		// Retorna a lista com os vertices
		return this.adjList;
	}
	

}