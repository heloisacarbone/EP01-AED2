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
import fefelo.dijkstraGrafos.grafo.Grafo;
import fefelo.dijkstraGrafos.grafo.Vertice;


public class main {

	public static void main(String[] args) {
		//Começa a ler arquivo
        String linha = null;  
         
         
        // instancia do arquivo  
        FileReader arq;  
        try {
            arq = new FileReader("src/exemplos/input.txt");
            //nao consegui arrumar um jeito de fazer sem usar o caminho exato preciso arrumar isso!
            BufferedReader leitor = new BufferedReader(arq);  
            linha = leitor.readLine(); //Le a  primeira linha 
            
            //List<Vertice> vertEare = new ArrayList<Vertice>();
              //leitura primeira linha 
            int qtdVert = 0;
            StringTokenizer valores = new StringTokenizer(linha);  
            String ln1 = valores.nextToken();  //Peguei apenas a qtd de vertice, pq de acordo com o felipe a de aresta é irrelevante
            qtdVert = Integer.parseInt(ln1);
	        System.out.println("======= qtd vertice ==========");
	        System.out.println(qtdVert);
	        System.out.println("==========fefelo.dijkstraGrafosrdenadas ========");
	            
                
                 //fim da leitura da 1 linha
            int[] a = new int[3];
            int i =0;
            List<Integer> auxCord = new ArrayList<Integer>();
            linha = leitor.readLine(); //pula a segunda linha (em branco)            
            while((linha = leitor.readLine()) != null){
                if(linha.trim().length() == 0){
                    break;
                } //para de ler quando encontra uma linha em branco
                StringTokenizer vCord = new StringTokenizer(linha);
                    while(vCord.hasMoreTokens()){
                            
                         String aux = vCord.nextToken();
                         System.out.println(aux);
                         int cord = Integer.parseInt(aux);
                         a[i] = cord;
                         i++;
                   }
                    System.out.println("Inicializa vertice: Vertice("+a[0]+","+ a[1]+","+a[2]+")");
                  i = 0;
                  // ->>>> Inicializa vertice: Vertice(a[0], a[1], a[2]);
                  //testado e funcionando, só precisa criar realmente o vertice
           }
             int[] c = new int[2];
             int n = 0;
             while((linha = leitor.readLine()) != null){
                if(linha.trim().length() == 0){
                    break;
                } //para de ler quando encontra uma linha em branco
                 StringTokenizer adj = new StringTokenizer(linha);
                    while(adj.hasMoreTokens()){
                       String aux = adj.nextToken();
                       int cam = Integer.parseInt(aux);
                       c[n] = cam;
                       n++;
                    }
                      System.out.println("Adjacencias: "+c[0]+" com "+c[1]);
                  
                n = 0;
             }
             
             //preciso fazer a parte de adjacencias e vertices
            
             
             //le o caminhoa ser feito
             int[] b = new int[2];
             int h = 0;
             StringTokenizer caminho = new StringTokenizer(linha);
             while((linha = leitor.readLine()) != null){
                StringTokenizer adj = new StringTokenizer(linha);
                    while(adj.hasMoreTokens()){
                       String aux = adj.nextToken();
                       int cam = Integer.parseInt(aux);
                       b[h] = cam;
                       h++;
                    }
                      System.out.println("Realizar caminho de "+b[0]+" a "+b[1]);
                  
                h = 0;
             }
             
            
            leitor.close();  
            arq.close();  
  
        } catch (FileNotFoundException ex){
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
		2 - um Int , A fefelo.dijkstraGrafosrdenada X do vertice
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
