package fefelo.dijkstraGrafos.grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;

public class Aresta {
	private Vertice v1;
	private Vertice v2;
	private int peso;
	
	public Aresta(Vertice v1, Vertice v2, int peso) {
		this.v1 = v1;
		this.v2 = v2;
		this.peso = peso;
	}

	public int getPeso() {
		return peso;
	}

	public void setPeso(int peso) {
		this.peso = peso;
	}

	public Vertice getV2() {
		return v2;
	}

	public void setV2(Vertice v2) {
		this.v2 = v2;
	}

	public Vertice getV1() {
		return v1;
	}

	public void setV1(Vertice v1) {
		this.v1 = v1;
	}

}