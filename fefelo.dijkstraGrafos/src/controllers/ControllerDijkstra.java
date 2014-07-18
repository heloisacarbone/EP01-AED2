package controllers;

import file.*;
import grafo.*;
import dijkstra.*;
import interfaceGrafica.*;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

public class ControllerDijkstra {       
	
	public static void initializeFactoryDijkstra(Grafo grafo, List<Vertice> listaVertices, int type, String inicioType1, 
			int numConjuntoVertices, List<int[]> conjuntosBusca)  {
		
		// Acho que aqui vc chama o FACTORY e passa os dados para ele, mas não sei direito.
		Dijkstra dijk = SingletonDijkstra.getInstance();
                Dijkstra dijk2 = null; //não pode usar o singleton nesse!
		for(Vertice vertice: grafo.getgrafo()){
			List <Vertice> noref = new ArrayList<Vertice>(vertice.getadjlist());
			dijk.apoio.put(vertice.getZ(), new ArrayList<Vertice>(noref));
		}
		if (type == 1) {
                    int contador = 0;
			Map<Integer, Map<String, String>> caminhos = new HashMap<Integer, Map<String, String>>();
			Map<Integer, Double> maxdists = new HashMap<Integer, Double>();

			// 1 Vértice acha o caminho minimo para todos
			for(Vertice vertice: grafo.getgrafo()){
                            contador++;
				if(vertice.getZ().equals(inicioType1)){
					dijk.inicio(vertice);
				}
			}
			dijk.dijkstraAlgorithm(grafo);

			int i = 0;
			for(Vertice vertice: grafo.getgrafo()){
				if(!vertice.getZ().equals(inicioType1)){
					dijk.fim(vertice);
					Map<String, String> caminho = new HashMap<String, String>(dijk.mapa(grafo));
					caminhos.put(i,caminho);
					double maxdist = dijk.maxdistancegrafo(grafo);
					maxdists.put(i, maxdist);
					i++;
				}
			}
			 FileContext fc = new FileContext();
				 fc.setFile(new Text());
				 fc.print(type, caminhos, maxdists);
				 fc.setFile(new Xml());
				 fc.print(type, caminhos, maxdists);
                         JanelaAbstrata janela = new JanelaT1(new JanelaGeral());
                         janela.desenhar();
                         janela.desenharJanela("Resultados Tipo 1", type, 00, contador);
                         
			
			
			
		} else if (type == 2 || type == 3) {
			// O que nós já temos feito
			
			Map<Integer, Map<String, String>> caminhos = new HashMap<Integer, Map<String, String>>();
			Map<Integer, Double> maxdists = new HashMap<Integer, Double>();
			if (numConjuntoVertices >= 1) {
				for (int i = 0; i < conjuntosBusca.size(); i++) {
                                    dijk2 = new Dijkstra(); // não pode usar o singleton aqui!!
                                    for(Vertice vertice: grafo.getgrafo()){
			List <Vertice> noref = new ArrayList<Vertice>(vertice.getadjlist());
			dijk2.apoio.put(vertice.getZ(), new ArrayList<Vertice>(noref));
		}
					int[] conjunto = conjuntosBusca.get(i);
					String v0 = String.valueOf(conjunto[0]);
			        String v1 = String.valueOf(conjunto[1]);
			        
			        // inicializa dijkstra (Talvez possa ser um método separado)
			        for (Vertice v: listaVertices) {
			        	String auxDescription = v.getZ();
			            if (auxDescription.equals(v0)) {
			                dijk2.inicio(v);
			            }
	
			            if (auxDescription.equals(v1)) {
			                dijk2.fim(v);
			            }
			        }
			        
			        dijk2.dijkstraAlgorithm(grafo); 
			        
			        Map<String, String> caminho = new HashMap<String, String>();
			        caminho = dijk2.mapa(grafo);
			        caminhos.put(i, caminho);
			        
			        double maxdist = dijk2.maxdistancegrafo(grafo);
			        maxdists.put(i, maxdist);
				}
				
				 if (type == 2) {
					System.out.println("Criando window tipo 2");
					ControllerInterfaceGrafica.createWindow(grafo, caminhos.get(0), dijk2.getInicio(), dijk2.getFim(), 1);
				}
                                 if(type == 3){
                                      JanelaAbstrata janela = new JanelaT3(new JanelaGeral());
                         
                         janela.desenhar();
                         janela.desenharJanela("Resultados Tipo 1", type, 00, numConjuntoVertices);                                      
                                        
                                        
                                 
                                 }
				
				 
				 // Imprimindo output
				 
				 FileContext fc = new FileContext();
				 fc.setFile(new Text());
				 fc.print(type, caminhos, maxdists);
				 fc.setFile(new Xml());
				 fc.print(type, caminhos, maxdists);
			
                                }
	
		}
	}
	
	/**
	 * Seta o adjacente v1 no v0.
	 * @param listaVertices
	 * @param v0
	 * @param v1
	 */
	public static void controllSetAdj(List<Vertice> listaVertices, String v0, String v1) {
		ListIterator<Vertice> it = listaVertices.listIterator();

        while (it.hasNext()) {
            Vertice vertice = (Vertice) it.next();
            String auxDescricao = vertice.getZ();
            
            ListIterator<Vertice> it2 = listaVertices.listIterator();
            Vertice compara = (Vertice) it2.next();
            String descCompara = compara.getZ();
            if (auxDescricao.equals(v0)) {   
            	//enquanto o vertice da lista de vertices for diferente do que esta no input
                while (!descCompara.equals(v1)) {
                    compara = (Vertice) it2.next();
                    descCompara = compara.getZ();
                    //sai do loop quando encontrar o c1, adjacente a c0
                }
                vertice.setadj(compara);
            }

        }
	}
	
         
    
	
 

}