package fefelo.dijkstraGrafos.grafo;

import java.io.ObjectInputStream.GetField;

public class Vertice {

	private int x;
	private int y;
	private String z; // facilitar dando um nome pra cada vertice
	private List <Vertice> adjacentes = new arrayList<Vertice>;
	
	public Vertice(String z, int x, int y) {
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}
	public String getZ(){
		return z;
	}
	public String setZ(String z){
		this.z = z;
	}

	public int getX() {
		return x;
	}

	public void setX(int x) {
		this.x = x;
	}

	public int getY() {
		return y;
	}

	public void setY(int y) {
		this.y = y;
	}
		
	public void setadj(Vertice x){
		this.adjacentes.add(x);
	}

}
