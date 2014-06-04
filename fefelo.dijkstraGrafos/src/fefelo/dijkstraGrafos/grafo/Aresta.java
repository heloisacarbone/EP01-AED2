package fefelo.dijkstraGrafos.grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;

public class Aresta {
	private Vertice v1;
	private Vertice v2;
	private int peso;
	
	public Aresta(Vertice v1, Vertice v2) {
		this.v1 = v1;
		this.v2 = v2;
		this.peso = calcpeso(v1, v2);
	}
	public int calcpeso(Vertice x, Vertice y){
		int n = (x.getX() - y.getX())^2;
		int m = (x.getY() - y.getY())^2;
		return math.sqrt(n+m);
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
