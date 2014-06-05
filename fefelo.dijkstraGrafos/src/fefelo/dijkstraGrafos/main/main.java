package fefelo.dijkstraGrafos.main;

public class main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub

	}
	
	
	
	/*1º parte: as quantidades de vertices e arestas será utilizado para auxiliar na criação do grafo 
	(mas a quanidade de arestas eu considero meio irrelevante uma vez que eu inicializei em cada vertice uma lista de seus adjacentes
	e o caluclo do peso eh feito diretamente no Relax)
	
	2º parte: Cada linha sera recebido 3 valores 
		1 - Uma String (mesmo que seje um inteiro eu inicializei td como string) que é a descrição do Vertice
		2 - um Int , A coordenada X do vertice
		3 - Um Int,  A cordenada Y do vertice
		Lembrar que o Vertice eh inicializado (String z, int x, int y);
		
	3º Parte: Aqui sera recebido em cada linha um possivel caminho (a parte mais complicada)
		Cada Vertice possui uma list que recebe todos os vertices adjacentes, ou seja:
			A cada linha sera recebido 2 vertices e em cada vertice será necessário adicionar seu respectivo adjacente
				Ex: se receber os vertices 3 e 5, o Vertice 3 adciona na lista de adjacencia o Vertice 5 e vice versa
				
	4º Parte; E finalmente será recebido 2 Vertices sendo o fim e o começo do caminho, será necessario chamar uma sub-rotina do dijkstra pra ele recebe os
		respectivos vertices 
	*/ 
}
