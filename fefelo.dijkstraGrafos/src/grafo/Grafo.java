/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package grafo;

import java.util.ArrayList;
import java.util.List;


public class Grafo {
	private List<Vertice> adjList = new ArrayList<Vertice>();


	public Grafo(List<Vertice> list) {		// Inicializa o Grafo com uma lista de vertices
            this.adjList.addAll(list);
	}
	public void addvertice(Vertice x){			// Adiciona um Vertice ao Grafo
		this.adjList.add(x);
	}
	public List<Vertice> getgrafo(){		// Retorna a lista com os vertices
		return this.adjList;
	}

	public double getPesoAresta(Vertice x, Vertice y) {	// Calcula o peso da aresta baseado nas fefelo.dijkstraGrafosrdenadas de cada vertice
		double n = Math.pow((x.getX() - y.getX()), 2);
		double m = Math.pow((x.getY() - y.getY()), 2);
		double z = Math.sqrt(n+m);
		return z;
	}
	public Vertice retornaVertice(String x){
		for(Vertice vertice: adjList){
			if(x.equals(vertice.getZ())){
				return vertice;
			}
		}Vertice y = null;
		return y;
	}


}