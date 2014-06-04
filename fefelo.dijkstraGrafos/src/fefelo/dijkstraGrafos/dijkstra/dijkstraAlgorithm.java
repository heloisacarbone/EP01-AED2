package fefelo.dijkstraGrafos.dijkstra;

public class dijkstraAlgorithm  extends Comparable<T>{
	
	private Vertice inicio;
	private Vertice fim;
	private List <Vertice> verticesremanescentes;
	private List <Vertice> adj;
	private Map <String, String> caminho = new hashMap<String, String>();
	private Vertice atual;

	public void inicio(Vertice x){
		this.inicio = x;
	}
	public void fim(Vertice x){
		this.fim = x;
	}
	
	public void dijkstraAlgorithm(grafo x){
		verticesremanescentes = x.getgrafo();
		for(Vertice y: x.getgrafo()){
			x.setdist(Double.POSITIVE_INFINITY);
		}
		inicio.setdist(0);
		atual = inicio;
		
		while(!verticesremanescentes.isEmpty()){
			for(Vertice n: atual.getadj()){
				relax(atual, n);
				for(int p = 0; x.getgrafo().size() - 1; p++){
					if(n.getY().equals(x.getgrafo().get(p).getY()){
						x.getgrafo().get(p).setdist(n.getdist());	
					}
				}
			}
			adj = atual.getadjlist();
			double menordist = 0;
			Vertice temp;
			while(adj != null){
				for(int p = 0; p < adj.size() - 1; p++){
					if(menordist<adj.get(p).getdist()){
						menordist = adj.get(p).getdist();
						temp = adj.get(p);
					}
				}
				for(int p = 0; p < verticesremanescentes.size() -1; p++){
					if(temp.getY().equals(verticesremanescentes.get(p).getY()){
						adj.remove(temp);
					}else{
						adj.clear();
					}
					
				}
			}
			verticesremanescentes.remove(atual);
			atual = temp;
		}
	}
	
	public Map <String, String> mapa(grafo x){
		List <Vertice> minimap = new arrayList<Vertice>();
		minimap = x.getgrafo();
		String first = inicio.getY();
		String second;
		while(!second.equals(fim.getY())){
			for( int p = 0; minimap.size() -1; p++){
				if(first.equals(minimap.get(p).getY())){
					List <Vertice> adj2 = minimap.get(p).getadjlist();
					double menor = 0;
					for(int p2 = 0; p2 < adj2.size(); p2++){
						if(menor < adj2.get(p2).getdist()){
							menor = adj2.get(p2).getdist();
							second = adj2.get(p2).getY();
						}
					}
				}
			}
			caminho.put(first, second);
			first = second;
		}
		return caminho;
	}
	public double maxdistance(grafo x){
		for(int p = 0 < x.getgrafo.size() -1 ; p++){
			if(fim.getY().equals(x.getgrafo().get(p).getY()){
				return x.getgrafo().get(p).getdist();
			}
		}
			
	}
	
	
	private void relax(Vertice x, Vertice y){
		double z = x.getdist() + Aresta(x,y).getpeso();
		if(y.getdist() > z){
			y.setdist(z);
		}
		
	}

}
