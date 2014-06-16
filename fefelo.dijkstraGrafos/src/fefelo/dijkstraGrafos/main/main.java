/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package fefelo.dijkstraGrafos.main;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringTokenizer;
import java.util.Iterator;
import java.util.HashMap;
import java.util.Map;
import fefelo.dijkstraGrafos.dijkstra.dijkstraAlgorithm;
import fefelo.dijkstraGrafos.grafo.Grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ListIterator;
import java.util.Set;

public class main {

    static String linha = null;
    static FileReader arq;
    static String file = "src/entradas/input.txt";

    public static void main(String[] args) {
        try {

            try {
            	System.out.println(args[0]);
                file = args[0];
            } catch (Exception ex) {
                System.out.println("Utilizando o arquivo padrão de testes, que se encontra no src/entradas/input, "
                        + "caso deseja inserir um arquivo diferente passe como argumento na hora de executar "
                        + "o programa");
                file = "src/entradas/input.txt";
            }

            arq = new FileReader(file);

            BufferedReader leitor = new BufferedReader(arq);
            linha = leitor.readLine(); //Le a  primeira linha 

            //leitura primeira linha 
            int qtdVert;
            dijkstraAlgorithm dijk = new dijkstraAlgorithm();
            Grafo grafo = new Grafo();
            StringTokenizer valores = new StringTokenizer(linha);
            String ln1 = valores.nextToken();  
            qtdVert = Integer.parseInt(ln1);
                      

            //fim da leitura da 1 linha
            int[] a = new int[3];
            int i = 0;

            List<Vertice> listaVertices = new ArrayList<Vertice>();
            linha = leitor.readLine(); //pula a segunda linha (em branco)            
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().length() == 0) {
                    break;
                } //para de ler quando encontra uma linha em branco
                StringTokenizer vCord = new StringTokenizer(linha);
                while (vCord.hasMoreTokens()) {

                    String aux = vCord.nextToken();
                
                    int cord = Integer.parseInt(aux);

                    a[i] = cord;

                    i++;
                }
                String v = String.valueOf(a[0]);
                Vertice vertice = new Vertice(v, a[1], a[2]);
                listaVertices.add(vertice);
               
                i = 0;
                // ->>>> Inicializa vertice: Vertice(a[0], a[1], a[2]);


                //grafo iniciado
            }
            grafo.startGrafo(listaVertices);

            

            List<Vertice> auxListVert = new ArrayList<Vertice>();
            auxListVert.addAll(listaVertices); //copia a lista de vertices para uma auxiliar
            int[] c = new int[2];
            int n = 0;
            while ((linha = leitor.readLine()) != null) {
                if (linha.trim().length() == 0) {
                    break;
                } //para de ler quando encontra uma linha em branco
                StringTokenizer adj = new StringTokenizer(linha);
                while (adj.hasMoreTokens()) {
                    String aux = adj.nextToken();
                    int cam = Integer.parseInt(aux);
                    c[n] = cam;
                    n++;
                }

                Iterator it = listaVertices.listIterator();
                String c0 = String.valueOf(c[0]);
                String c1 = String.valueOf(c[1]);

                while (it.hasNext()) {
                    Vertice vertice = (Vertice) it.next();
                    String desc = vertice.getZ();
                    Iterator it2 = listaVertices.listIterator();
                    Vertice compara = (Vertice) it2.next();
                    String descCompara = compara.getZ();
                    if (desc.equals(c0)) {   //enquanto o vertice da lista de vertices for diferente do que esta no input
                        while (!descCompara.equals(c1)) {
                            compara = (Vertice) it2.next();
                            descCompara = compara.getZ();
                            //sai do loop quando encontrar o c1, adjacente a c0

                        }
                        vertice.setadj(compara);
                    }

                }
                Iterator it1 = listaVertices.listIterator();
                while (it1.hasNext()) {
                    Vertice vertice = (Vertice) it1.next();
                    String desc = vertice.getZ();
                    Iterator it2 = listaVertices.listIterator();
                    Vertice compara = (Vertice) it2.next();
                    String descCompara = compara.getZ();
                    if (desc.equals(c1)) {   //enquanto o vertice da lista de vertices for diferente do que esta no input
                        while (!descCompara.equals(c0)) {
                            compara = (Vertice) it2.next();
                            descCompara = compara.getZ();
                            //sai do loop quando encontrar o c0, adjacente a c1

                        }
                        vertice.setadj(compara);
                    }

                }

             

                n = 0;
            }

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
            FileWriter out = new FileWriter(fileOut, true);
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

            leitor.close();
            arq.close();

        } catch (FileNotFoundException ex) {
            System.out.println("Arquivo não encontrado");
        } catch (IOException ex) {
            System.out.println("Erro");
        }



    }
}
    	
    	
    	/*1º parte: as quantidades de vertices e arestas será utilizado para auxiliar na criação do grafo 
    	(mas a quanidade de arestas eu considero meio irrelevante uma vez que eu inicializei em cada vertice uma lista de seus adjacentes
    	e o caluclo do peso eh feito diretamente no Relax)
    	
    	2º parte: Cada linha sera recebido 3 valores 
    		1 - Uma String (mesmo que seje um inteiro eu inicializei td como string) que é a descrição do Vertice
    		2 - um Int , A fefelo.dijkstraGrafos.dijkstra..grafordenada X do vertice
    		3 - Um Int,  A cordenada Y do vertice
    		Lembrar que o Vertice eh inicializado (String z, int x, int y);
    		
    	3º Parte: Aqui sera recebido em cada linha um possivel caminho (a parte mais complicada)
    		Cada Vertice possui uma list que recebe todos os vertices adjacentes, ou seja:
    			A cada linha sera recebido 2 vertices e em cada vertice será necessário adicionar seu respectivo adjacente
    				Ex: se receber os vertices 3 e 5, o Vertice 3 adciona na lista de adjacencia o Vertice 5 e vice versa
    				
    	4º Parte; E finalmente será recebido 2 Vertices sendo o fim e o começo do caminho, será necessario chamar uma sub-rotina do dijkstra pra ele recebe os
    		respectivos vertices 
    		
    	
    	
    	Final: Após terminar tudo acima, chame o metodo dijkstraAlgorithm(grafo x) Para ele calcular as distancias em relação 
    	ao inicio do percurso, em seguida use o mapa(grafo x) para retornar o mapa com o menor caminho e o maxdistancegrafo(grafo x)
    	para retornar a distancia total
    	*/