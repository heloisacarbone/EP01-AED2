package fefelo.dijkstraGrafos.grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;
import fefelo.dijkstraGrafos.grafo.Aresta;
import java.awt.List;

public class grafo {
	private List<Vertice> adjList = new arrayList<Vertice>();


	public startGrafo(List<Vertice> list) {		// Inicializa o Grafo com uma lista de vertices
		this.adjList.addAll(list);
	}
	public addvertice(Vertice x){			// Adiciona um Vertice ao Grafo
		this.adjList.add(x);
	}
	public List<Vertice> getgrafo(){		// Retorna a lista com os vertices
		return this.adjlist;
	}
	public Vertice retornavertice(String x){		// Retorna um vertice em especifico
		
		for(int i = 0; i < getgrafo().size(); i++){
			if(x.equals(this.getgrafo().get(i).getZ();
			
				return this.getgrafo().get(i);
		}
		return null;
	}

}
