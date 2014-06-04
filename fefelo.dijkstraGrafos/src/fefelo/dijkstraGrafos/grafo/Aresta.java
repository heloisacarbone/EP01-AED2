package fefelo.dijkstraGrafos.grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;

public class Aresta {
	private Vertice v1;
	private Vertice v2;
	private double peso;
	
	public Aresta(Vertice v1, Vertice v2) {		// Inicializa uma aresta (Caminho entre 2 vertices)
		setV1(v1);
		setV2(v2);
		this.peso = calcpeso(v1, v2);
	}
	public double calcpeso(Vertice x, Vertice y){	// Calcula o peso da aresta baseado nas coordenadas de cada vertice
		int n = (x.getX() - y.getX())^2;
		int m = (x.getY() - y.getY())^2;
		return math.sqrt(n+m);
	}

	public int getPeso() {				// Retorna o peso da aresta
		return peso;
	}

	public Vertice getV2() {			// Retorna o Vertice 2
		return v2;
	}

	public void setV2(Vertice v2) {			// Adiciona o Vertice 2
		this.v2 = v2;
	}

	public Vertice getV1() {			// Retorna o Vertice 1
		return v1;
	}

	public void setV1(Vertice v1) {			// Retorna o Vertice 1
		this.v1 = v1;
	}

}
