package fefelo.dijkstraGrafos.grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;
import fefelo.dijkstraGrafos.grafo.Aresta;
import java.awt.List;

public class grafo {
	private List[] adjList;
	// Não sei direito o que usar, mas tem que ser um Array que tem como chave o Vertice e que leva a uma lista ligada de arestas adjacentes do vertice.
	private int num_vertices;
	
	public grafo(int num_vertices) {
		this.num_vertices = num_vertices;
		this.adjList = new List[num_vertices];
	}

}
