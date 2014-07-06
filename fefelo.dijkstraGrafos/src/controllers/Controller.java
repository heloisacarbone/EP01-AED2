package controllers;

import file.*;
import grafo.*;
import dijkstra.*;

import java.io.*;
import java.util.*;

public class Controller {

	public static void initializeGraphTXT(String file) throws NumberFormatException, IOException {
		BufferedReader reader = Text.openReadFile(file);
		String line = "";
		
		int qtdVert = 0;
		int qtdAdj = 0;
		
		List<Vertice> listaVertices = new ArrayList<Vertice>();
		List<Vertice> auxListVert = new ArrayList<Vertice>(); //Copia do listaVertices
		
		int type = 0;
		String verticeType1 = null;
		int numVertType = 0;
		
		List<int[]> conjuntosBusca = new ArrayList<int[]>();
		
		int counter = 0;
		while ((line = reader.readLine()) != null) {
			StringTokenizer values = new StringTokenizer(line);
			if (counter == 0) {
				// Primeira linha do arquivo, quantidade de vértices e quantidade de adjacentes
				qtdVert = Integer.parseInt(values.nextToken()); 
				qtdAdj = Integer.parseInt(values.nextToken());
				
			} else if (counter >= 2 && counter <= (qtdVert+1)) {
				// Vertices com suas coordenadas são inceridos em um ArrayList
				int[] a = new int[3];
		        int i = 0;
	            while (values.hasMoreTokens()) {
	                 String aux = values.nextToken();
	                 int cord = Integer.parseInt(aux);
	                 a[i] = cord;
	                 i++;
	             }
	             String v = String.valueOf(a[0]);
	             Vertice vertice = new Vertice(v, a[1], a[2]);
	             listaVertices.add(vertice);
	             auxListVert.add(vertice);
	           
			} else if (counter >= (qtdVert+3) && counter <= (qtdVert+2+qtdAdj)) {
				// Lê os adjacentes
				
	            int[] a = new int[2];
	            int i = 0;
	            
                while (values.hasMoreTokens()) {
                    String aux = values.nextToken();
                    int caminho = Integer.parseInt(aux);
                    a[i] = caminho;
                    i++;
                }
         
                String c0 = String.valueOf(a[0]);
                String c1 = String.valueOf(a[1]);

                controllSetAdj(listaVertices, c0, c1);
                controllSetAdj(listaVertices, c1, c0);			
			} else if (counter == (qtdVert+4+qtdAdj)) {
				// quarta etapa
				type = Integer.parseInt(values.nextToken());
			} else if (type == 1 && counter == (qtdVert+5+qtdAdj)) {
				verticeType1 = values.nextToken();
				
			} else if ((type == 2 || type == 3) && counter == (qtdVert+5+qtdAdj)) {
				numVertType = Integer.parseInt(values.nextToken());
		
			} else if (counter >= (qtdVert+5+qtdAdj) && counter <= (qtdVert+5+qtdAdj+numVertType)) {
				int[] conjunto = new int[2];
				conjunto[0] = Integer.parseInt(values.nextToken());
				conjunto[1] = Integer.parseInt(values.nextToken());
				
				conjuntosBusca.add(conjunto);
			}
			counter++;
		}

		reader.close();
        Grafo grafo = graphGenerator(qtdVert, listaVertices);
        initializeFactoryDijkstra(grafo, listaVertices, type, verticeType1, numVertType, conjuntosBusca);
		
	}
	
	public static void outputTXT(int type, Map<Integer, Map<String, String>> caminhos, 
			Map<Integer, Double> maxdists) {
		List<String> listKey = new ArrayList<String>();
        List<String> listValue = new ArrayList<String>();
        PrintWriter output = Text.openWriteFile("src/saidas/output.txt");  
       
        for (int i = 0; i < caminhos.size(); i++) {
	        Iterator<String> itKey = caminhos.get(i).keySet().iterator();
	        Iterator<String> itVal = caminhos.get(i).values().iterator();
	        
	        while (itKey.hasNext()) {
	            Object k = itKey.next();
	            String key = String.valueOf(k);
	            listKey.add(key);
	        }
	        
	        while (itVal.hasNext()) {
	            Object v = itVal.next();
	            String val = String.valueOf(v);
	            listValue.add(val);
	        }
	        
	        output.println("=================================");
	        
	        ListIterator<String> ltKey = listKey.listIterator(listKey.size());
	        ListIterator<String> ltVal = listValue.listIterator(listValue.size());
	        String Imprime = "";
	        while (ltKey.hasPrevious()) {
	            String ltk = String.valueOf(ltKey.previous());
	            Imprime += (ltk + " ");
	            while (ltVal.hasPrevious()) {
	                String ltv = String.valueOf(ltVal.previous());
	                Imprime += (ltv);
	                ltVal.remove();
	                break;
	            }
	            output.println(Imprime);
	            Imprime = "";
	            ltKey.remove();
	        }
	        
	        output.println("");
	
	
	        output.println(maxdists.get(i).intValue() + 1);
	        output.print("=================================");
        }
        output.close();
        Text.closeFiles();
	}

	
	
	public static Grafo graphGenerator(int qtdVert, List<Vertice> listaVertices) {
        
        Grafo grafo = new Grafo();
        grafo.startGrafo(listaVertices);
        return grafo;
        
	}            
	
	public static void initializeFactoryDijkstra(Grafo grafo, List<Vertice> listaVertices, int type, String verticeType1, 
			int numConjuntoVertices, List<int[]> conjuntosBusca)  {
		
		// Acho que aqui vc chama o FACTORY e passa os dados para ele, mas não sei direito.
		Dijkstra dijk = new Dijkstra();
		
		if (type == 1) {
			// 1 Vértice acha o caminho minimo para todos
			
			
			
		} else if (type == 2 || type == 3) {
			// O que nós já temos feito
			
			Map<Integer, Map<String, String>> caminhos = new HashMap<Integer, Map<String, String>>();
			Map<Integer, Double> maxdists = new HashMap<Integer, Double>();
			if (numConjuntoVertices >= 1) {
				for (int i = 0; i < conjuntosBusca.size(); i++) {
					int[] conjunto = conjuntosBusca.get(i);
					String v0 = String.valueOf(conjunto[0]);
			        String v1 = String.valueOf(conjunto[1]);
			        
			        // inicializa dijkstra (Talvez possa ser um método separado)
			        for (Vertice v: listaVertices) {
			        	String auxDescription = v.getZ();
			            if (auxDescription.equals(v0)) {
			                dijk.inicio(v);
			            }
	
			            if (auxDescription.equals(v1)) {
			                dijk.fim(v);
			            }
			        }
			        
			        dijk.dijkstraAlgorithm(grafo); 
			        
			        Map<String, String> caminho = new HashMap<String, String>();
			        caminho = dijk.mapa(grafo);
			        caminhos.put(i, caminho);
			        
			        double maxdist = dijk.maxdistancegrafo(grafo);
			        maxdists.put(i, maxdist);
				}
				
				 if (type == 2) {
					System.out.println("Criando window");
					ControllerInterfaceGrafica.createWindow(grafo, caminhos.get(0));
				}
				
				outputTXT(type, caminhos, maxdists);	
		        
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
	
	
	// Temos que fazer um XML TBM 
	public static void initializeGraphXML(String file) {
	
			
	}
         
    
	
 

}
