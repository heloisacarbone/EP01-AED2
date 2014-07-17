package file;

import grafo.Grafo;
import grafo.Vertice;

import java.io.*;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;

import controllers.ControllerDijkstra;

public class Text implements FileInterface {	

	@Override
	public void initializeGraph(String file) {
		try {
			FileReader readArq = new FileReader(file);
			BufferedReader reader = new BufferedReader(readArq);
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
	
	                ControllerDijkstra.controllSetAdj(listaVertices, c0, c1);
	                ControllerDijkstra.controllSetAdj(listaVertices, c1, c0);			
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
			Grafo grafo = new Grafo(listaVertices);
	        
	        ControllerDijkstra.initializeFactoryDijkstra(grafo, listaVertices, type, verticeType1, numVertType, conjuntosBusca);
	        
	        //Desaclopar, não pode chamar o initializeFactory "!!!!!!! FUCK
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}



	@Override
	public void output(int type, Map<Integer, Map<String, String>> caminhos,
			Map<Integer, Double> maxdists) {
		// PS da para alterar para se escolher o nome da file
		try {
		List<String> listKey = new ArrayList<String>();
	    List<String> listValue = new ArrayList<String>();
	    
	    File fileOut = new File("src/saidas/output.txt");
		if (fileOut.exists()) {
            fileOut.delete();
        } else {
            fileOut.createNewFile();
        }
		
		FileWriter writeArq = new FileWriter(fileOut, true);
	    
	    PrintWriter output = new PrintWriter(writeArq);  
	   
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
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
	}
}