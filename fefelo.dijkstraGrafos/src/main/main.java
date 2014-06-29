/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package main;

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

import grafo.*;
import controllers.*;

import java.io.File;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ListIterator;

import dijkstra.Dijkstra;

public class main {

    static String file = "src/entradas/input.txt";

    public static void main(String[] args) throws NumberFormatException, IOException {
    	
    	if (args.length != 0) {
    		file = args[0]; 
        }
    	
    	if (file.indexOf(".txt") != -1) {
    		try {
    			Controller.initializeGraphTXT(file);
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    		
    	} else if (file.indexOf(".xml") != -1) {
    		try {
    			Controller.initializeGraphXML(file);
    		} catch (Exception ex) {
    			ex.printStackTrace();
    		}
    	} else {
    		System.out.println("O arquivo inserido não é valido, é preciso que seja xml ou txt.");
    		System.exit(0);
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