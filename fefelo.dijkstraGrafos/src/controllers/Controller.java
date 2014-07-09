package controllers;

import file.*;
import grafo.*;
import dijkstra.*;

import java.io.*;
import java.util.*;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

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
	
	public static void outputXML(int type, Map<Integer, Map<String, String>> caminhos,
            Map<Integer, Double> maxdists) {

        List<String> listKey = new ArrayList<String>();
        List<String> listValue = new ArrayList<String>();
       
        Document doc = new Document ();
        Element principal = new Element("Dijkstra");
        doc.setRootElement(principal);
       
        for (int i = 0; i < caminhos.size(); i++) {
        	Element result = new Element("Resultado");
        	principal.addContent(result);
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
                Element vertices = new Element("Vertices");
                vertices.setText(Imprime);
                result.addContent(vertices);
                System.out.println(Imprime);
                Imprime = "";
                ltKey.remove();
            }



            Element distancia = new Element("distancia");
            int dist = maxdists.get(i).intValue() + 1;
            distancia.setText(String.valueOf(dist));
            result.addContent(distancia);
            
            XMLOutputter xmlOut = new XMLOutputter();
            OutputStream out;
            try {
                out = new FileOutputStream(new File("src/saidas/output.xml"));
                xmlOut.output(doc, out);
            } catch (FileNotFoundException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(Controller.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

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
		Document doc = null;
        SAXBuilder builder = new SAXBuilder();
        try {
            doc = builder.build(file);
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<Vertice> listaVertices = new ArrayList<Vertice>();
        List<Vertice> auxAdjacencia = new ArrayList<Vertice>();
        String descricao = null;
        int x = 0;
        int y = 0;
        Element dijkstra = doc.getRootElement(); // pega o elemento raiz do xml
        List<Element> filhos = dijkstra.getChildren(); // elementos filhos de dijkstra
        for (Element e : filhos) {
            descricao = e.getAttributeValue("descricao");
            System.out.println("Vertice: " + e.getAttributeValue("descricao"));
            x = Integer.parseInt(e.getChildText("x"));
            System.out.println("X: " + e.getChildText("x"));

            System.out.println("Y: " + e.getChildText("y"));
            y = Integer.parseInt(e.getChildText("y"));
            Vertice vertice = new Vertice(descricao, x, y);
            listaVertices.add(vertice);
            auxAdjacencia.add(vertice);

        }

        String auxAdj = null;
        for (Element e : filhos) {

            auxAdj = e.getChildText("adj"); // Todos os adjacentes ao vertice e
            String vertice = e.getAttributeValue("descricao");
            StringTokenizer values = new StringTokenizer(auxAdj);
            while (values.hasMoreTokens()) {
                String adj = values.nextToken();
                System.out.println("Adicionando o" + vertice + "adjacente a" + adj);
                controllSetAdj(listaVertices, adj, vertice);
                controllSetAdj(listaVertices, vertice, adj);
            }

        }

        String numVert = dijkstra.getAttributeValue("nVertices");
        int qtdVert = Integer.parseInt(numVert);
        int tipo = Integer.parseInt(dijkstra.getAttributeValue("tipo"));
        List<int[]> conjuntosBusca = new ArrayList<int[]>();
        int numVertType = 0;
        String origem = null;
        if (tipo == 1) {
            origem = dijkstra.getAttributeValue("origem");
        }
        if (tipo == 2 || tipo == 3) {
            numVertType = Integer.parseInt(dijkstra.getAttributeValue("nPares"));
            String pares = dijkstra.getAttributeValue("par");
            StringTokenizer values = new StringTokenizer(pares, ",");
            //retornar ex: 0 5
            while (values.hasMoreTokens()) {
                String parSeparado = values.nextToken();
                StringTokenizer vertSeparado = new StringTokenizer(parSeparado);
                // retorna o vertice sozinho ex 0 ou 5
                while (vertSeparado.hasMoreTokens()) {
                    int[] conjunto = new int[2];
                    conjunto[0] = Integer.parseInt(vertSeparado.nextToken());
                    conjunto[1] = Integer.parseInt(vertSeparado.nextToken());
                    conjuntosBusca.add(conjunto);

                }
            }
        }
        Grafo grafo = graphGenerator(qtdVert, listaVertices);

        initializeFactoryDijkstra(grafo, listaVertices, tipo, origem, numVertType, conjuntosBusca);

			
	}
         
    
	
 

}
