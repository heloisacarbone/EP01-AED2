/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import grafo.Grafo;
import grafo.Vertice;


public class Dijkstra {

    private Vertice inicio;
    private Vertice fim;
    private List<Vertice> verticesremanescentes;
    private List<Vertice> adj;
    private Map<String, String> caminho = new HashMap<String, String>();
    private Vertice atual;

    public void inicio(Vertice x) {	// Começo do caminho
        this.inicio = x;
    }

    public void fim(Vertice x) {	// Fim do caminho
        this.fim = x;
    }
    
    public Vertice getInicio(){
    	return this.inicio;
    }
    
    public Vertice getFim(){
    	return this.fim;
    }

    /**
     * Inicializa a execução do algoritmo do dijkstra sobre um grafo
     *
     * @param grafo
     */
    public void dijkstraAlgorithm(Grafo grafo) {
        

    	verticesremanescentes = new ArrayList<Vertice>(grafo.getgrafo());// Criando uma list para passar por todos os Vertices

        for (Vertice y : verticesremanescentes) {			// Colocando todas as distancias em infinito
            y.setdist(Double.POSITIVE_INFINITY);
        }

        inicio.setdist(0);
        atual = inicio;
        verticesremanescentes.remove(atual);

        while ((!verticesremanescentes.isEmpty())) {

            // So termina qnd visitar todos os vertices

            for (Vertice n : atual.getadjlist()) {// Relaxamento dos vertices
                adj = atual.getadjlist();
                
                relax(atual, n, grafo);
                
                List<Vertice> aux = grafo.getgrafo();
                for (int p = 0; p < grafo.getgrafo().size() - 1; p++) {	// não sei se precisa mas estou jogando os valores da dist atualizados no grafo original
                    if (n.getZ().equals(aux.get(p).getZ())) {
                        aux.get(p).setdist(n.getdist());
                    }
                }
            }
            Vertice temp = null;
            while (temp == null) {	// Escolher o proximo caminho pegando o menor caminho
                double menordist = Double.MAX_VALUE;
                for (int p = 0; p < adj.size(); p++) { // Escolhe o menor caminho dentre os adjacentes
                    if ((menordist >= adj.get(p).getdist()) && verticesremanescentes.contains(adj.get(p))) {
                        menordist = adj.get(p).getdist();
                        temp = adj.get(p);
                    }
                }
                if(temp == null){	
				temp = verticesremanescentes.get(0);
                }
            }
            
            verticesremanescentes.remove(temp);
            atual = temp;
            
            if (verticesremanescentes.isEmpty()) {
                return;
            }
        }
     }

    public Map<String, String> mapa(Grafo grafo){
		Grafo minimap2 = new Grafo();
		minimap2 = grafo;

		String first = inicio.getZ();
		String second = "1";
		List<String> aux = new ArrayList<String>();
		aux.add(first);
		do{
			List<Vertice> minimap = new ArrayList(minimap2.getgrafo());
			while(!second.equals(fim.getZ()) && !first.equals(fim.getZ())){
				for( int p = 0; p < minimap.size(); p++) {
					if(first.equals(minimap.get(p).getZ())) {
						List <Vertice> adj2 = new ArrayList(minimap.get(p).getadjlist());
						double menor = Double.POSITIVE_INFINITY;
						if(adj2.size()>0){
							for(int p2 = 0; p2 < adj2.size(); p2++) {
								if(menor > adj2.get(p2).getdist() && !aux.contains((adj2.get(p2)).getZ())) {
									menor = adj2.get(p2).getdist();
									second = adj2.get(p2).getZ();
									}
							}
						aux.add(second);
						caminho.put(first, second);
						first = second;




						if(second.equals(fim.getZ())) break;

						}else{
							for(String string: aux){
								minimap2.retornaVertice(aux.get(aux.size()-2)).rva(minimap2.retornaVertice(string));
							}
							aux.remove(aux.size()-1);
							caminho.remove(aux.get(aux.size()-1));
							first = aux.get(aux.size()-1);
							second = "";
						}
					}
				}
			}
			double teste2 = minimap2.retornaVertice(aux.get(aux.size()-1)).getdist();
			double teste =  (minimap2.retornaVertice(aux.get(aux.size()-2)).getdist() + grafo.getPesoAresta(minimap2.retornaVertice(aux.get(aux.size()-2)), minimap2.retornaVertice(aux.get(aux.size()-1))));
			if(teste == teste2){
				return caminho;
				}
			for(String string: aux){
				minimap2.retornaVertice(aux.get(aux.size()-2)).rva(minimap2.retornaVertice(string));
			}
			aux.remove(aux.size()-1);
			caminho.remove(aux.get(aux.size()-1));
			first = aux.get(aux.size()-1);
			second = "";
		}while(aux.size()>1);
		return caminho;
	}

    /**
     * Retorna a distância final do percurso
     *
     * @param grafo
     * @return
     */
    public double maxdistancegrafo(Grafo grafo) {
        List<Vertice> auxDist = grafo.getgrafo();
        for (int p = 0; p < grafo.getgrafo().size() ; p++) {
            Vertice auxVD = auxDist.get(p);
            
            if (fim.getY() == (auxVD.getY())) {
                
                return auxVD.getdist();
            }
        }
        return 0;

    }

    private void relax(Vertice x, Vertice y, Grafo grafo) {
        double z;
       
        if (x.getdist() == Double.POSITIVE_INFINITY || x.getdist() == 0) {
            z = grafo.getPesoAresta(x, y);
            y.setdist(z);
        } else {
            z = x.getdist() + grafo.getPesoAresta(x, y);

            if (y.getdist() > z) {
                    y.setdist(z);
                    verticesremanescentes.add(y);
            }
        }
    }
}