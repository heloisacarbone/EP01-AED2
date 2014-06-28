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
		
		int type = 0;
		int numVertType = 0;
		List<Vertice> listaVertices = new ArrayList<Vertice>();
		List<Vertice> auxListVert = new ArrayList<Vertice>(); //Copia do listaVertices
		
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
                    int cam = Integer.parseInt(aux);
                    a[i] = cam;
                    i++;
                }
         
                String c0 = String.valueOf(a[0]);
                String c1 = String.valueOf(a[1]);

                controllSetAdj(listaVertices, c0, c1);
                controllSetAdj(listaVertices, c1, c0);			
			} else if (counter == (qtdVert+4+qtdAdj)) {
				// quarta etapa
				type = Integer.parseInt(values.nextToken());
			} else if (counter == (qtdVert+5+qtdAdj)) {
				numVertType = Integer.parseInt(values.nextToken());
			} else if (counter >= (qtdVert+6+qtdAdj) && counter <= (qtdVert+4+qtdAdj+numVertType)) {
				
			}
			counter++;
			
		}
		
   
        
        grafoGenerator(qtdVert, listaVertices);
		
	}

	public static void initializeGraphXML(String file) {

		
	}
	
	public static void grafoGenerator(int qtdVert, List<Vertice> listaVertices) {
        dijkstraAlgorithm dijk = new dijkstraAlgorithm();
        Grafo grafo = new Grafo();
        grafo.startGrafo(listaVertices);
        
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
            String desc = vertice.getZ();
            
            ListIterator<Vertice> it2 = listaVertices.listIterator();
            Vertice compara = (Vertice) it2.next();
            String descCompara = compara.getZ();
            if (desc.equals(v0)) {   
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
         
       
         

      
/*

         //le o caminhoa ser feito
         int[] b = new int[2];
         int h = 0;
         while ((linha = leitor.readLine()) != null) {
             StringTokenizer adj = new StringTokenizer(linha);
             while (adj.hasMoreTokens()) {
                 String aux = adj.nextToken();
                 int cam = Integer.parseInt(aux);
                 b[h] = cam;
                 h++;
             }
            

             h = 0;
         }

         String b0 = String.valueOf(b[0]);
         String b1 = String.valueOf(b[1]);
         for (Vertice vertex : listaVertices) {
             String a1 = vertex.getZ();
             if (a1.equals(b0)) {
                 dijk.inicio(vertex);
             }

             if (a1.equals(b1)) {
                 dijk.fim(vertex);
             }
         }


        


         List<String> listKey = new ArrayList<String>();
         List<String> listVal = new ArrayList<String>();
         dijk.dijkstraAlgorithm(grafo);
         Map<String, String> caminho2 = new HashMap<String, String>();
         caminho2 = dijk.mapa(grafo);
         double maxdist = dijk.maxdistancegrafo(grafo);

         File fileOut = new File("src/saidas/output.txt");
         System.out.println("Saida");
         Iterator itKey = caminho2.keySet().iterator();
         Iterator itVal = caminho2.values().iterator();
         while (itKey.hasNext()) {
             Object k = itKey.next();
             String key = String.valueOf(k);
             listKey.add(key);
         }
         while (itVal.hasNext()) {
             Object v = itVal.next();
             String val = String.valueOf(v);
             listVal.add(val);
         }
         if (fileOut.exists()) {
             fileOut.delete();
         } else {
             fileOut.createNewFile();
         }
         System.out.println("Pre");
         FileWriter out = new FileWriter(fileOut, true);
         System.out.println("FILEE");
         PrintWriter printWriter = new PrintWriter(out);
         printWriter.println("=================================");
         ListIterator ltKey = listKey.listIterator(listKey.size());
         ListIterator ltVal = listVal.listIterator(listVal.size());
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
             printWriter.println(Imprime);
             Imprime = "";
             ltKey.remove();
         }
         printWriter.println("");


         printWriter.println((int) maxdist + 1);
         printWriter.print("=================================");
         out.close(); // cria o arquivo 
         System.out.println("chegou aqui");
         leitor.close();
         arq.close();

     } catch (FileNotFoundException ex) {
         System.out.println("Arquivo não encontrado");
     } catch (IOException ex) {
         System.out.println(ex);
     }


*/
 

}
