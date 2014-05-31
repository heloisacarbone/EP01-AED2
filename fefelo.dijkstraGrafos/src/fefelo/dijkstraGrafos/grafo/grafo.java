package fefelo.dijkstraGrafos.grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;
import fefelo.dijkstraGrafos.grafo.Aresta;
import java.awt.List;

public class grafo {
	private List<Vertice> adjList = new arrayList<Vertice>();


	public startGrafo(List<Vertice> list) {
		this.adjList.addAll(list);
	}
	public addvertice(Vertice x){
		this.adjList.add(x);
	}
	public List<Vertice> getgrafo(){
		return this.adjlist;
	}
	public Vertice retornavertice(String x){
		
		for(int i = 0; i < getgrafo().size(); i++){
			if(x.equals(this.getgrafo().get(i).getZ();
			
				return this.getgrafo().get(i);
		}
		return null;
	}

}
