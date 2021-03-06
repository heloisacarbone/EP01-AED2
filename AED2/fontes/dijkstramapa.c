/************************************
    ACH2024 - Algoritmos e Estruturas de Dados II
    Prof. Marcelo de Souza Lauretto

  Modulo: dijkstra.c
    Implementacao do Algoritmo de Dijkstra

  Renomear este modulo com o seguinte padrao:
    d<numerousp1>_<numerousp2>.c
  Exemplo:
    d8517199_8516570.c

*************************************/

#include<math.h>
#include<limits.h>

#include "grafosmapa.h"
#include "indheap.h"

#define DBL_MAX    1.7976931348623157E+308    
#define _DEBUG_

/* 
   Inicializa todos os ponteiros utilizados em Djikstra.
   Recebe como parametros: 
       int num_vertices - quantidade de vertices no Grafo.
       tvertice v0 - vertice que sera tomado como ponto inicial.
       tpeso customin - array contendo o peso de cada um dos vertices, � 
            inicializado com o valor de DBL_MAX em todas as suas posicoes 
            com excessao de v0 que recebe 0.
       tvertice antecessor - array contendo o antecessor de cada um dos v�rtices,
            � inicializado com o valor de -1, que � um correspondente a NULL nesse caso.
       int visitados - array que ajuda a validar quais vertices ja foram visitados,
            � inicializado com 0, com excessao da posicao de v0 que recebe 1, uma vez que ja foi visitado.
*/
void InicializaFonteUnica(int num_vertices, tvertice v0, 
                          tpeso *customin, tvertice *antecessor, int *visitados) {
    int i;
    for(i = 0; i < num_vertices; i++) {
        if (i == v0) { 
            customin[i] = 0;
        } else { 
            customin[i] = DBL_MAX;
        }
        visitados[i] = 0;
        antecessor[i] = -1;
    }
}

/*
    Verifica se ainda existem vertices n�o visitados e retorna true 
    se existirem e false caso contr�rio.
    Recebe como parametros: 
       int num_vertices - quantidade de vertices no Grafo.
       int visitados - array que ajuda a validar quais vertices ja foram visitados
*/ 
int TemMaisVisitas(int num_vertices, int *visitados) {
    int i;
    for(i = 0; i < num_vertices; i++) {
        if (visitados[i] == 0) {
            return 1;
        }
    }
    return 0;
}

/*
    Retorna o �ndice do vertice que ainda nao foi visitado e possui o menor custo,
    caso todos ja tenham sido visitados retorna -1.
    Recebe como parametros: 
       int num_vertices - quantidade de vertices no Grafo.
       int visitados - array que ajuda a validar quais vertices ja foram visitados.
       tpeso customin - array contendo o peso de cada um dos vertices.
*/ 
int ExtraiMinimo(int num_vertices, int *visitados, tpeso *customin) {
    int min = -1;
    tpeso aux_min = DBL_MAX;
    int i;
    for(i = 0; i < num_vertices; i++) {
        if (visitados[i] == 0) {
            if (aux_min > customin[i]) {
                aux_min = customin[i];
                min = i;
            }
        }
    }
    return min;
}

/*
  Faz o relaxamento dos vertices.
  Recebe como parametros: 
       tvertice v0 - Indice do vertice que esta analisando os outros.
       tapontador adj - Adjacente do v0.
       tpeso customin - array contendo o peso de cada um dos vertices.
       tvertice antecessor - array contendo o antecessor de cada um dos v�rtices. 
       tgrafo G - Grafo.
*/ 
void Relaxamento(tvertice v0, tapontador adj, 
                 tpeso *customin, tvertice *antecessor, tgrafo *G){
    
    tvertice vAdj;
    tpeso pAresta;
    long nrini, nrfim;
    char *nomerua[MAXSTRING];
    RecuperaAdj(v0, adj, &vAdj, &pAresta, &nrini, &nrfim, &(*nomerua), G);
    
    if (customin[vAdj] > (customin[v0] + pAresta)) {
        customin[vAdj] = (customin[v0] + pAresta);
        antecessor[vAdj] = v0;
    }
}

/*
  Algoritmo de Dijkstra
  Dado um grafo G e um vertice de origem v0, esta funcao implementa
  o algoritmo de Dijkstra para encontrar o caminho de menor custo de
  v0 ateh cada um dos demais vertices do grafo.
  A saida da funcao eh o vetor de custos minimos e o vetor de
  antecessores.

  Observacao: Assume-se que o espaco para os vetores customin e
  antecessor jah estao alocados.
*/
void Dijkstra(tgrafo *G, tvertice v0,
              tpeso *customin, tvertice *antecessor) {
    tapontador aux_adj = NULL;              
    
    /* Array que ajuda a saber quais vertices ja foram visitados */
    int visitados[G->num_vertices];
    
    InicializaFonteUnica(G->num_vertices, v0, customin, antecessor, &(*visitados));
    
    while(TemMaisVisitas(G->num_vertices, visitados) == 1) {
        int menor = ExtraiMinimo(G->num_vertices, visitados, customin);
      
        if (menor != -1) {
       
            visitados[menor] = 1;
    
            // Verifica se o vertice tem adjacentes 
           
            if (ListaAdjVazia(menor, G) != 1) {
                // Faz o relaxamento de todos os adjacentes
                aux_adj = PrimeiroAdj(menor, G);
                while (aux_adj != NULL) {
                    Relaxamento(menor, aux_adj, &(*customin), &(*antecessor), G);
                    aux_adj = ProxAdj(menor, aux_adj, G);
                } 
                
                aux_adj = NULL;
            }
        }
    }
}

/* Carrega o arquivo de requisicoes de rota, devolvendo o status
  (1=sucesso, 0=fracasso).
A primeira linha conterah o numero de requisicoes, e cada uma das demais linhas
conterah uma requisicao, formada pelo endereco de origem (rua e numero) e endereco
de destino (rua e numero). Mais especificamente, o lay-out serah:

n
ruaorigem_1 nrorigem_1 ruadestino_1 nrdestino_1
ruaorigem_2 nrorigem_2 ruadestino_2 nrdestino_2
...
ruaorigem_n nrorigem_n ruadestino_n nrdestino_n

Os campos ser�o separados por espacos ou tabulacoes.
Os nomes das ruas terao no maximo 50 caracteres e n�o conter�o espacos.
Os n�meros serao do tipo inteiro longo.

Exemplo de arquivo de requisicoes:

4
Conselheiro_Aguiar  177 Joao_Bley_Filho 124
Paschoal_Ardito 7   Marques_De_Sao_Vicente  79
Silvia  177 Silvia  24
Euclides_Miragaia   9   Marques_De_Sao_Vicente  84
*/

int CarregaRequisicoes(char *nomearq, int *qtreq,
                        char RuaOrigem[][MAXSTRING],
                        char RuaDestino[][MAXSTRING],
                        long *nrorigem, long *nrdestino) {
    
    FILE *fp;
    int i;
    fp = fopen(nomearq, "rt");
    if (fp == NULL)
        return(0);

    if (fscanf(fp, "%d", qtreq) != 1)
        return(0);

    char auxOrigem[MAXSTRING];
    char auxDestino[MAXSTRING];
    
    for (i = 0; i < *qtreq ; i++) {
        if (fscanf(fp, "%s %ld %s %ld", auxOrigem, &(nrorigem[i]), auxDestino, &(nrdestino[i])) != 4) { // Verificar com prof
          fclose(fp);
          return(0);
        }

        strcpy(RuaOrigem[i], auxOrigem);
        strcpy(RuaDestino[i], auxDestino); 
    }

    fclose(fp);
    return(1);
    
}


/*
  Checa se o array de ruas ja possui a rua a ser inserida, caso ja esteja 
  na lista retorna 1 (verdadeiro), caso contrario 0 (falso)
  Recebe como parametros: 
       char *ruas - Array que contem os nomes de ruas.
       int num_ruas - Numero de ruas que ja estao no array.
       char rua - Nome da rua a ser inserida no array.
*/ 
int checaRuaEnfileirada(char *ruas, int num_ruas, char rua) {
    int i;
    for (i = 0; i < num_ruas; i++) {
        if (ruas[i] == rua) {
            return 1;
        }
    }
    
    return 0;
}

/*
  Empilha as ruas que fazem o caminho com custo minimo.
  Recebe como parametros: 
       tvertice vdest - Vertice de destino.
       char *nomesRuas - Array com a fila de ruas.
       int *countRuas - Numero de ruas na fila de ruas.
       tvertice *antecessor - Array de antecessores dos vertices.
       tgrafo *G - Grafo.
*/
void empilhaRuas(tvertice vdest, tapontador *nomesRuas, int *countRuas, tvertice *antecessor, tgrafo *G) {
    tvertice auxAtual = vdest;
    tvertice auxPred = antecessor[vdest];
    tapontador aresta;
    
    while (auxPred != -1){
        aresta = BuscaAresta(auxPred, auxAtual, G);
        if (strcmp((nomesRuas[(*countRuas)]->nomerua), (aresta->nomerua)) != 0){
            *countRuas = (*countRuas) + 1;
            nomesRuas[*countRuas] = aresta;
        }
        auxAtual = auxPred;
        auxPred = antecessor[auxAtual]; 
    }
}

/*
    Distancia entre o num de inicio e o num de destino em uma rua.
    Recebe como parametros:
        tapontador aresta - aresta que possui os numeros
        long numero - numero de origem ou fim
        int posicao - 0 -> origem, 1 -> fim
 */ 
double Distancia (tapontador aresta, long* numero1, long* numero2){ 
	double distancia;
	double tamanhoRua;
    if (numero1 != NULL && numero2 != NULL) {
        if (aresta->nrini > aresta->nrfim){
            tamanhoRua = aresta->nrini - aresta->nrfim;
        }else{ 
            tamanhoRua = aresta->nrfim - aresta->nrini;
        }
        if(*numero1 > *numero2){
            distancia = *numero1 - *numero2;
        }else{
            distancia = *numero2 - *numero1;
        }
    } else if (numero1 != NULL) { 
        if (aresta->nrini > *numero1) {
            distancia = aresta->nrini - *numero1;
            tamanhoRua = aresta->nrini - aresta->nrfim;
        } else { 
            distancia = *numero1 - aresta->nrini;
            tamanhoRua = aresta->nrfim - aresta->nrini;
        }
    } else {
        if (aresta->nrfim > *numero2) {
            distancia = aresta->nrfim - *numero2;
            tamanhoRua = aresta->nrfim - aresta->nrini;
        } else {
            distancia = *numero2 - aresta->nrfim;
            tamanhoRua = aresta->nrini - aresta->nrfim;
        }
    }
    
	return (distancia * aresta->peso) / tamanhoRua;
}

/* Dado um endereco de origem e um endereco de destino no grafo G,
   esta funcao chama o algoritmo de Dijkstra e escolhe a rota de menor
   custo entre os dois endere�os.
   As ruas que compoem a melhor rota s�o impressas no arquivo apontado por fp.

   Argumentos:
     fp: ponteiro do arquivo de saida
     ruaorig, nrorig: rua/numero do endereco de origem
     ruadest, nrdest: rua/numero do endereco de destino
     G: grafo representando o mapa

  Esta funcao gravarah no arquivo de saida um bloco de linhas com a rota de
  menor custo entre o endereco de origem e o endereco de destino.
  A primeira linha do bloco bloco conter� os campos:
    Nome da rua de origem (string sem espa�os e sem aspas, at� 50 caracteres)
    N�mero de origem (inteiro longo)
    Nome da rua de destino (string sem espa�os e sem aspas, at� 50 caracteres)
    N�mero de destino (inteiro longo)
    Custo m�nimo (double)
    N�mero de ruas distintas do endere�o de origem ao endere�o de destino (inteiro)
  As demais linhas do bloco conter�o os nomes das ruas por onde a rota de menor
  custo dever� passar. Somente as ruas distintas no trajeto dever�o ser exibidas
  (arestas consecutivas com mesmo nome de rua n�o devem ser listadas na rota final).
*/
void ImprimeMelhorRota (FILE *fp, char *ruaorig, long nrorig,
                                  char *ruadest, long nrdest, tgrafo *G) {

    tpeso customin0[G->num_vertices], customin1[G->num_vertices];
    tvertice antecessor0[G->num_vertices], antecessor1[G->num_vertices];
    tvertice vorig0, vorig1, vdest0, vdest1;
    tapontador aorig, adest;
    
    BuscaArestaRua(&(*ruaorig), nrorig, &vorig0, &aorig, &(*G));
    vorig1 = aorig->vertice;
    BuscaArestaRua(&(*ruadest), nrdest, &vdest0, &adest, &(*G));
    vdest1 = adest->vertice;    
    
    tpeso mindist = DBL_MAX;
    char *caso;
    
    if (aorig == adest) {
        caso = "A";
        mindist = Distancia(aorig, &nrorig, &nrdest);
    } else {
        
        Dijkstra(G, vorig0, &(*customin0), &(*antecessor0));
        Dijkstra(G, vorig1, &(*customin1), &(*antecessor1));
        
        tpeso B = customin0[vdest0] + Distancia(aorig, &nrorig, NULL) + Distancia(adest, &nrdest, NULL);
        tpeso C = customin0[vdest1] + Distancia(aorig, &nrorig, NULL) + Distancia(adest, NULL, &nrdest);
        tpeso D = customin1[vdest0] + Distancia(aorig, NULL, &nrorig) + Distancia(adest, &nrdest, NULL);
        tpeso E = customin1[vdest1] + Distancia(aorig, NULL, &nrorig) + Distancia(adest, NULL, &nrdest);
        
        if (mindist > B) {
            caso = "B";
            mindist = B;
        } 
        if (mindist > C) {
            caso = "C";
            mindist = C;
        } 
        if (mindist > D) {
            caso = "D";
            mindist = D;
        } 
        if (mindist > E) {
            caso = "E";
            mindist = E;
        }
        
    }
    
    tapontador nomesRuas[G->num_vertices];
    int countRuas = 0;
	
    nomesRuas[1] = adest;
    countRuas = countRuas + 1;
	
    if (caso == "B") {     
        empilhaRuas(vdest0, nomesRuas, &countRuas, antecessor0, G);
    } else if (caso == "C") {
        empilhaRuas(vdest1, nomesRuas, &countRuas, antecessor0, G);
    } else if (caso == "D") {
        empilhaRuas(vdest0, nomesRuas, &countRuas, antecessor1, G);
    } else if (caso == "E"){
        empilhaRuas(vdest1, nomesRuas, &countRuas, antecessor1, G);
    }
    
    if (strcmp((nomesRuas[countRuas]->nomerua), (aorig->nomerua)) != 0){
        countRuas = countRuas + 1;
        nomesRuas[countRuas] = aorig;
    }
    
    
    fprintf(fp,"%s %ld %s %ld %lf %d\n", ruaorig, nrorig, ruadest, nrdest, mindist, countRuas);
	
    
    int i;
    
    for (i = countRuas; i > 0; i--) {
        fprintf(fp, "%s\n", nomesRuas[i]->nomerua);
    } 
	fprintf(fp, "\n");
}

/*
  Funcao principal do programa: Carrega o grafo, carrega e executa a lista
  de requisicoes e escreve as melhores rotas.
*/
int main(int argc, char *argv[]) {
  tgrafo G;
  int qtreq;  // quantidade de requisicoes de rota
  int idreq;
  char ruaorigem[100][MAXSTRING], ruadestino[100][MAXSTRING];
  long nrorigem[100], nrdestino[100];
  FILE *fpout;
  int tamcaminho;

  if (argc<4) {
    printf("GPS.EXE \nSintaxe: gps <arqmapa> <arqrequisicao> <arqsaida>\n\n");
    exit(0);
  }

  if (LeGrafo(argv[1], &G) &&
      CarregaRequisicoes(argv[2], &qtreq, ruaorigem, ruadestino,
                         nrorigem, nrdestino)) {

    fpout = fopen(argv[3], "wt");
    if (fpout==NULL)
       return(0);
    fprintf(fpout, "%d\n", qtreq);
    fclose(fpout);

    for (idreq=0; idreq<qtreq; idreq++) {

      //#ifdef _DEBUG_
      printf("Requisicao: %s, %ld a %s,%ld\n", ruaorigem[idreq],nrorigem[idreq],
             ruadestino[idreq], nrdestino[idreq]);
      //#endif
      fpout = fopen(argv[3], "at");
      ImprimeMelhorRota(fpout, ruaorigem[idreq], nrorigem[idreq],
                               ruadestino[idreq], nrdestino[idreq], &G);
      fclose(fpout);
    }

    LiberaGrafo(&G);
  }

  return(1);
}
