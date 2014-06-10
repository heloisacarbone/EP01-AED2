/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fefelo.dijkstraGrafos.dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import fefelo.dijkstraGrafos.grafo.Grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;


public class dijkstraAlgorithm{
	
	private Vertice inicio;
	private Vertice fim;
	private List <Vertice> verticesremanescentes;
    private List <Vertice> adj;
	private Map<String, String> caminho = new HashMap<String, String>();
	private Vertice atual;

	public void inicio(Vertice x){	// Começo do caminho
		this.inicio = x;
	}
	public void fim(Vertice x){	// Fim do caminho
		this.fim = x;
	}
	
	/**
	 * Inicializa a execução do algoritmo do dijkstra sobre um grafo
	 * 
	 * @param grafo
	 */
	public void dijkstraAlgorithm(Grafo grafo) {
		verticesremanescentes = grafo.getgrafo();		// Criando uma list para passar por todos os Vertices
		
		for(Vertice y: verticesremanescentes){			// Colocando todas as distancias em infinito
			y.setdist(Double.POSITIVE_INFINITY);
		}
		
		inicio.setdist(0);
		atual = inicio;
		
		while (!verticesremanescentes.isEmpty()) {
                                 
			System.out.print("tem adj! para o: ");
            System.out.println(atual.getZ());
            
            // So termina qnd visitar todos os vertices
			for(Vertice n: atual.getadjlist()){// Relaxamento dos vertices
				System.out.print("ele é o: ");
                System.out.println(n.getZ());
                
                relax(atual, n, grafo);
                          
                List <Vertice> aux = grafo.getgrafo();
             
				for(int p = 0; p < grafo.getgrafo().size() - 1; p++) {	// não sei se precisa mas estou jogando os valores da dist atualizados no grafo original
					Vertice aux2 = aux.get(p);
                    
					if(n.getZ().equals(aux2.getZ())){
                    	aux2.setdist(n.getdist());	
					}
				}
			}
			
			adj = atual.getadjlist();
                       
			double menordist = 0;
			Vertice temp = null;
			
			while(!adj.isEmpty()) {	// Escolher o proximo caminho pegando o menor caminho
				
				for(int p = 0; p < adj.size() - 1; p++) {	// Escolhe o menor caminho dentre os adjacentes
					if((menordist<adj.get(p).getdist()) && verticesremanescentes.contains(adj.get(p))) {
						menordist = adj.get(p).getdist();
						temp = adj.get(p);
                        System.out.println("entrei aqui");
					}
				}
                            
                //   System.out.println("Escolhido:"+temp.getZ());
                        
				for(int p = 0; p < verticesremanescentes.size() -1; p++){	// verifica se o adjacente escolhido ja não foi visitado
					if(temp.getZ().equals(verticesremanescentes.get(p).getZ()) ){
						System.out.print("aqui: ");	
                        verticesremanescentes.remove(atual);
                        adj.remove(temp);
						
                        if(adj.isEmpty()) {
                            System.out.print("n pode");	
                                                   
							temp = verticesremanescentes.get(0);	// Caso algum Vertice fique perdido entre vertices que ja foram visitados
						}
					}else{
						adj.clear();
					}
				}
			}
            //  System.out.println("temporario"+temp.getZ());
			verticesremanescentes.remove(atual);
            atual = temp;               
		}
	}
	
	/**
	 * Retorna o mapa contendo o caminho a ser feito.
	 * 
	 * @param grafo
	 * @return
	 */
	public Map<String, String> mapa(Grafo grafo) {			
		List<Vertice> minimap = new ArrayList<Vertice>();
		minimap = grafo.getgrafo();
		String first = inicio.getZ();
		String second = "";
		
		while(!second.equals(fim.getZ())){
			for( int p = 0; p < minimap.size() -1; p++) {
				
				if(first.equals(minimap.get(p).getZ())) {
					List <Vertice> adj2 = minimap.get(p).getadjlist();
					double menor = 0;
					
					for(int p2 = 0; p2 < adj2.size(); p2++) {
					
						if(menor < adj2.get(p2).getdist()) {
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
	
	/**Retorna a distância final do percurso
	 * 
	 * @param grafo
	 * @return
	 */
	public double maxdistancegrafo(Grafo grafo) {
            List<Vertice> auxDist = grafo.getgrafo();
            
		for(int p = 0; p < grafo.getgrafo().size() -1 ; p++){
                    Vertice auxVD = auxDist.get(p);
                    if(fim.getY()==(auxVD.getY())){
				return auxVD.getdist();
			}
		}
            return 0;
			
	}
	
	/**
	 * Faz o relaxamento dos vértices
	 * @param x
	 * @param y
	 * @param grafo
	 */
	private void relax(Vertice x, Vertice y, Grafo grafo){
		
		double z = x.getdist() + grafo.getPesoAresta(x, y);
		
		if(y.getdist() > z){
			y.setdist(z);
		}
		
	}

}
