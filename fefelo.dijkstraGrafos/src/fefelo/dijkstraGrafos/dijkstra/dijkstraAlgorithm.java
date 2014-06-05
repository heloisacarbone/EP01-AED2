package fefelo.dijkstraGrafos.dijkstra;

public class dijkstraAlgorithm  extends Comparable<T>{
	
	private Vertice inicio;
	private Vertice fim;
	private List <Vertice> verticesremanescentes;
	private List <Vertice> adj;
	private Map <String, String> caminho = new hashMap<String, String>();
	private Vertice atual;

	public void inicio(Vertice x){	// Começo do caminho
		this.inicio = x;
	}
	public void fim(Vertice x){	// Fim do caminho
		this.fim = x;
	}
	
	public void dijkstraAlgorithm(grafo x){
		verticesremanescentes = x.getgrafo();		// Criando uma list para passar por todos os Vertices
		for(Vertice y: x.getgrafo()){			// Colocando todas as distancias em infinito
			x.setdist(Double.POSITIVE_INFINITY);
		}
		inicio.setdist(0);
		atual = inicio;
		
		while(!verticesremanescentes.isEmpty()){	// So termina qnd visitar todos os vertices
			for(Vertice n: atual.getadj()){		// Relaxamento dos vertices
				relax(atual, n);
				for(int p = 0; x.getgrafo().size() - 1; p++){	// não sei se precisa mas estou jogando os valores da dist atualizados no grafo original
					if(n.getZ().equals(x.getgrafo().get(p).getZ()){
						x.getgrafo().get(p).setdist(n.getdist());	
					}
				}
			}
			adj = atual.getadjlist();
			double menordist = 0;
			Vertice temp;
			while(!adj.isEmpty()){	// Escolher o proximo caminho pegando o menor caminho
				for(int p = 0; p < adj.size() - 1; p++){	// Escolhe o menor caminho dentre os adjacentes
					if(menordist<adj.get(p).getdist()){
						menordist = adj.get(p).getdist();
						temp = adj.get(p);
					}
				}
				for(int p = 0; p < verticesremanescentes.size() -1; p++){	// verifica se o adjacente escolhido ja não foi visitado
					if(temp.getZ().equals(verticesremanescentes.get(p).getZ()){
						adj.remove(temp);
						if(adj.isEmpty()){
							temp = verticesremanescentes.get(0);	// Caso algum Vertice fique perdido entre vertices que ja foram visitados
						}
					}else{
						adj.clear();
					}
					
				}
			}
			verticesremanescentes.remove(atual);
			atual = temp;
		}
	}
	
	public Map <String, String> mapa(grafo x){			// Retorna o mapa contendo o caminho a ser feito
		List <Vertice> minimap = new arrayList<Vertice>();
		minimap = x.getgrafo();
		String first = inicio.getZ();
		String second;
		while(!second.equals(fim.getZ())){
			for( int p = 0; minimap.size() -1; p++){
				if(first.equals(minimap.get(p).getZ())){
					List <Vertice> adj2 = minimap.get(p).getadjlist();
					double menor = 0;
					for(int p2 = 0; p2 < adj2.size(); p2++){
						if(menor < adj2.get(p2).getdist()){
							menor = adj2.get(p2).getdist();
							second = adj2.get(p2).getZ();
						}
					}
				}
			}
			caminho.put(first, second);
			first = second;
		}
		return caminho;
	}
	public double maxdistancegrafo(grafo x){			// Retorna a distância final do percurso
		for(int p = 0 < x.getgrafo.size() -1 ; p++){
			if(fim.getY().equals(x.getgrafo().get(p).getY()){
				return x.getgrafo().get(p).getdist();
			}
		}
			
	}
	
	
	private void relax(Vertice x, Vertice y){		// Calcula se existe um caminho menor para o vertice adjacente atual
		double z = x.getdist() + Aresta(x,y).getpeso();
		if(y.getdist() > z){
			y.setdist(z);
		}
		
	}

}
