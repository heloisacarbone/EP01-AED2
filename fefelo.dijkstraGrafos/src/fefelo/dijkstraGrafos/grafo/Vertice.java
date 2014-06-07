/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fefelo.dijkstraGrafos.grafo;


import java.io.ObjectInputStream.GetField;
import java.util.*;

public final class Vertice {

	private int x;
	private int y;
	private String z; // facilitar dando um nome pra cada vertice
	private List<Vertice> adjacentes = new ArrayList<Vertice>();	// lista de vertices adjacentes
	private double dist;
	
	public Vertice(String z, int x, int y) {	// inicializa um vertice
		this.setX(x);
		this.setY(y);
		this.setZ(z);
	}

    Vertice() {
     }
	public String getZ(){				// retorna a descrição do vertice
		return z;
	}
	public String setZ(String z){			// adiciona a descrição do vertice
		this.z = z;
            return null;
	}

	public int getX() {				// Retorna a fefelo.dijkstraGrafosrdenada X do vertice
		return x;
	}

	public void setX(int x) {			// Adiciona a fefelo.dijkstraGrafosrdenada X do vertice
		this.x = x;
	}

	public int getY() {				// Retorna a fefelo.dijkstraGrafosrdenada Y do vertice
		return y;
	}

	public void setY(int y) {			// Adiciona a fefelo.dijkstraGrafosrdenada Y do vertice
		this.y = y;
	}
		
	public void setadj(Vertice x){			// Adiciona um vertice vizinho ao vertice atual
		this.adjacentes.add(x);
	}
	public Vertice getadj(int x){			// Retorna um vertice vizinho
		return adjacentes.get(x);
	}
	public List<Vertice> getadjlist(){		// Retorna todos os vertices vizinhos
		return adjacentes;
	}
	public int sizeadj(){				// Retorna a quantidade de vertices vizinhos
		return this.adjacentes.size();
	}
	public double getdist(){			// Retorna a distancia atual do vertice em relação ao inicio
		return dist;
	}
	public void setdist(double x){			// Adiciona uma distancia ao vertice atual em relação ao inicio
		this.dist = x;
	}

}