package file;

import grafo.Grafo;
import grafo.Vertice;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.jdom2.Document;
import org.jdom2.Element;
import org.jdom2.input.SAXBuilder;
import org.jdom2.output.XMLOutputter;

import controllers.ControllerDijkstra;

public class Xml implements FileInterface {

	public Xml() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void initializeGraph(String file) {
		System.out.println("TESTE");	
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
            ControllerDijkstra.controllSetAdj(listaVertices, adj, vertice);
            ControllerDijkstra.controllSetAdj(listaVertices, vertice, adj);
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
    
    Grafo grafo = new Grafo(listaVertices);
    ControllerDijkstra.initializeFactoryDijkstra(grafo, listaVertices, tipo, origem, numVertType, conjuntosBusca);
		
	}

	@Override
	public void output(int type, Map<Integer, Map<String, String>> caminhos,
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
                Logger.getLogger(ControllerDijkstra.class.getName()).log(Level.SEVERE, null, ex);
            } catch (IOException ex) {
                Logger.getLogger(ControllerDijkstra.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
		
	}

}
