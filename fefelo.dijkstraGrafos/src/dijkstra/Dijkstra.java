/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package dijkstra;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.imageio.IIOException;

import grafo.Grafo;
import grafo.Vertice;


public class Dijkstra {

    private Vertice inicio;
    private Vertice fim;
    private List<Vertice> verticesremanescentes;
    private List<Vertice> adj;
    private Map<String, String> caminho = new HashMap<String, String>();
    private Vertice atual;
    public Map <String, List<Vertice>> apoio = new HashMap<String, List<Vertice>>();

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
                break;
            }
        }
     }

    public Map<String, String> mapa(Grafo grafo){
		Grafo minimap2 = new Grafo(grafo.getgrafo());
		Double distParada = 0.0;
        double teste2 = Double.POSITIVE_INFINITY;
        List<Vertice> auxDist = grafo.getgrafo();
        for (int p = 0; p < grafo.getgrafo().size() ; p++) {
            Vertice auxVD = auxDist.get(p);
            
            if (fim.getZ().equals(auxVD.getZ())) {
                teste2 = auxVD.getdist();
            }
		}
        int intTeste2 = (int) teste2;
		
		String first = inicio.getZ();
		String second = "";
		List<String> aux = new ArrayList<String>();
		aux.add(first);
		if(inicio.getadjlist().contains(fim)){
            caminho.clear();
			caminho.put(inicio.getZ(), fim.getZ());
            return caminho;
            
        }
		do{
			
			while(!second.equals(fim.getZ()) && !first.equals(fim.getZ()) && !aux.contains(second)){
				List<Vertice> minimap = new ArrayList<Vertice>(minimap2.getgrafo());
				for( int p = 0; p < minimap.size(); p++) {
					if(first.equals(minimap.get(p).getZ())) {
						List <Vertice> adj2 = new ArrayList<Vertice>(minimap.get(p).getadjlist());
						double menor = Double.POSITIVE_INFINITY;
						if(adj2.size()>0){
							second = "po";
							String check = second;
							boolean change = false;
							for(int p2 = 0; p2 < adj2.size(); p2++) {
								Double fc = Double.POSITIVE_INFINITY;
								Double fc2 = minimap2.getPesoAresta(minimap.get(p),adj2.get(p2));
								if(fc2 != 0) fc = fc2;
								if(menor > fc && !aux.contains((adj2.get(p2)).getZ())) {
									menor = fc;
									second = adj2.get(p2).getZ();
									}
							}
							if(check.equals(second)) change = true;
							if(change == true){
								minimap2.retornaVertice(aux.get(aux.size()-2)).rva(minimap2.retornaVertice(aux.get(aux.size()-1)));
								Restaurar(minimap2.retornaVertice(aux.get(aux.size()-1)));
								distParada -= minimap2.getPesoAresta(minimap2.retornaVertice(aux.get(aux.size()-2)), minimap2.retornaVertice(aux.get(aux.size()-1)));
								aux.remove(aux.size()-1);
								caminho.remove(aux.get(aux.size()-1));
								first = aux.get(aux.size()-1);
								second = "";								
							}else{
								aux.add(second);
								caminho.put(first, second);
								first = second;
								distParada += minimap2.getPesoAresta(minimap2.retornaVertice(aux.get(aux.size()-2)), minimap2.retornaVertice(aux.get(aux.size()-1)));
							}
						if(second.equals(fim.getZ())) break;
						
						}else{
							try{	
								minimap2.retornaVertice(aux.get(aux.size()-2)).rva(minimap2.retornaVertice(aux.get(aux.size()-1)));
								Restaurar(minimap2.retornaVertice(aux.get(aux.size()-1)));
								distParada -= minimap2.getPesoAresta(minimap2.retornaVertice(aux.get(aux.size()-2)), minimap2.retornaVertice(aux.get(aux.size()-1)));
								aux.remove(aux.size()-1);
								caminho.remove(aux.get(aux.size()-1));
								first = aux.get(aux.size()-1);
								second = "";
							}catch(ArrayIndexOutOfBoundsException AIOOBException){
								System.out.println("Aux is Empty !!!");

								minimap2.retornaVertice(aux.get(aux.size()-1)).setadjlist(apoio.get(aux.get(aux.size()-1)));
								break;
							}finally{

							}
						}
					}
				}
//				int count = 0;
//				for(Vertice xz: grafo.getgrafo()){
//					if(aux.contains(xz.getZ())){
//						count ++;
//					}	
//				}
//				if(count == 1){
//					for(Vertice xz: minimap2.getgrafo()){
//						if(!xz.getZ().equals(inicio)){
//							xz.setadjlist(apoio.get(xz.getZ()));
//						}
//					}
//				}
			}
			int intDistParada = distParada.intValue();
            	if(intDistParada == intTeste2){
            		return caminho;
				}
            	second = "";
					if((aux.contains(fim.getZ()) && intDistParada != intTeste2)){
						minimap2.retornaVertice(aux.get(aux.size()-2)).rva(minimap2.retornaVertice(aux.get(aux.size()-1)));
						Restaurar(minimap2.retornaVertice(aux.get(aux.size()-1)));
						distParada -= minimap2.getPesoAresta(minimap2.retornaVertice(aux.get(aux.size()-2)), minimap2.retornaVertice(aux.get(aux.size()-1)));
						aux.remove(aux.size()-1);
						caminho.remove(aux.get(aux.size()-1));
						first = aux.get(aux.size()-1);
						second = "";
						
					}
					int count1 = minimap2.retornaVertice(first).getadjlist().size();
					int count2 = 0;
					for(Vertice k: minimap2.retornaVertice(first).getadjlist()){
						if(aux.contains(k.getZ())) count2 ++;
					}
					if(count2 == count1){
						minimap2.retornaVertice(aux.get(aux.size()-2)).rva(minimap2.retornaVertice(aux.get(aux.size()-1)));
						Restaurar(minimap2.retornaVertice(aux.get(aux.size()-1)));
						distParada -= minimap2.getPesoAresta(minimap2.retornaVertice(aux.get(aux.size()-2)), minimap2.retornaVertice(aux.get(aux.size()-1)));
						aux.remove(aux.size()-1);
						caminho.remove(aux.get(aux.size()-1));
						first = aux.get(aux.size()-1);
						second = "";
					}
					
		}while(aux.size()>0);
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
    private void Restaurar(Vertice vertice){
    	List <Vertice> rest = new ArrayList<Vertice>(apoio.get(vertice.getZ()));
    	for(Vertice vertice2: rest){
    		boolean equal = false;
    			for(Vertice vertice3: vertice.getadjlist()){
    				if(vertice2.getZ().equals(vertice3.getZ())){
    					equal = true;
    				}
    			}
				if (!equal){
					vertice.setadj(vertice2);
				}
    	}
   }
}