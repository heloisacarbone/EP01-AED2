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
       tpeso customin - array contendo o peso de cada um dos vertices, é 
            inicializado com o valor de DBL_MAX em todas as suas posicoes 
            com excessao de v0 que recebe 0.
       tvertice antecessor - array contendo o antecessor de cada um dos vértices,
            é inicializado com o valor de -1, que é um correspondente a NULL nesse caso.
       int visitados - array que ajuda a validar quais vertices ja foram visitados,
            é inicializado com 0, com excessao da posicao de v0 que recebe 1, uma vez que ja foi visitado.
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
    Verifica se ainda existem vertices não visitados e retorna true 
    se existirem e false caso contrário.
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
    Retorna o índice do vertice que ainda nao foi visitado e possui o menor custo,
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
       tvertice antecessor - array contendo o antecessor de cada um dos vértices. 
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
    printf("---------------Dijkstra--------------\n");
    printf("VO: %d\n", v0);
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

Os campos serão separados por espacos ou tabulacoes.
Os nomes das ruas terao no maximo 50 caracteres e não conterão espacos.
Os números serao do tipo inteiro longo.

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
  Enfileira as ruas que fazem o caminho com custo minimo.
  Recebe como parametros: 
       tvertice vdest - Vertice de destino.
       char *nomesRuas - Array com a fila de ruas.
       int *countRuas - Numero de ruas na fila de ruas.
       tvertice *antecessor - Array de antecessores dos vertices.
       tgrafo *G - Grafo.
*/
void enfileirarRuasInvertidas(tvertice vdest, tapontador *nomesRuas, int *countRuas, tvertice *antecessor, tgrafo *G) {
    printf("-----------enfileirando----------\n");
    tvertice auxAtual = vdest;
    tvertice auxPred = antecessor[vdest];
    printf("AuxAtual: %d, AuxPred: %d\n", auxAtual, auxPred);
    tapontador aresta;
    while (auxPred != -1){
        aresta = BuscaAresta(auxPred, auxAtual, G);
        if (strcmp((nomesRuas[(*countRuas)]->nomerua), (aresta->nomerua)) != 0){
            *countRuas = (*countRuas) + 1;
            nomesRuas[*countRuas] = aresta;
            printf("nomeRua: %s\n", nomesRuas[*countRuas]->nomerua);
        }
        auxAtual = auxPred;
        auxPred = antecessor[auxAtual]; 
    }
}

/*
    Distancia entre o num de inicio e o num de destino em uma rua.
    Recebe como parametros:
        long nrorig - numero de origem
        long nrdest - numero de destino
 */ 
long Distancia(long nrorig, long nrdest) {
    if (nrorig > nrdest) 
        return nrorig - nrdest;
    else
        return nrdest - nrorig;
}

/* Dado um endereco de origem e um endereco de destino no grafo G,
   esta funcao chama o algoritmo de Dijkstra e escolhe a rota de menor
   custo entre os dois endereços.
   As ruas que compoem a melhor rota são impressas no arquivo apontado por fp.

   Argumentos:
     fp: ponteiro do arquivo de saida
     ruaorig, nrorig: rua/numero do endereco de origem
     ruadest, nrdest: rua/numero do endereco de destino
     G: grafo representando o mapa

  Esta funcao gravarah no arquivo de saida um bloco de linhas com a rota de
  menor custo entre o endereco de origem e o endereco de destino.
  A primeira linha do bloco bloco conterá os campos:
    Nome da rua de origem (string sem espaços e sem aspas, até 50 caracteres)
    Número de origem (inteiro longo)
    Nome da rua de destino (string sem espaços e sem aspas, até 50 caracteres)
    Número de destino (inteiro longo)
    Custo mínimo (double)
    Número de ruas distintas do endereço de origem ao endereço de destino (inteiro)
  As demais linhas do bloco conterão os nomes das ruas por onde a rota de menor
  custo deverá passar. Somente as ruas distintas no trajeto deverão ser exibidas
  (arestas consecutivas com mesmo nome de rua não devem ser listadas na rota final).
*/
void ImprimeMelhorRota (FILE *fp, char *ruaorig, long nrorig,
                                  char *ruadest, long nrdest, tgrafo *G) {

    tpeso customin0[G->num_vertices], customin1[G->num_vertices];
    tvertice antecessor0[G->num_vertices], antecessor1[G->num_vertices];
    tvertice vorig0, vorig1, vdest0, vdest1;
    tapontador aorig, adest;

    printf("------------------Imprime Melhor Rota------------------------\n");
    printf("Origem: %s, %ld\n", ruaorig, nrorig);
    printf("Destino: %s, %ld\n", ruadest, nrdest);
    
    BuscaArestaRua(&(*ruaorig), nrorig, &vorig0, &aorig, &(*G));
    vorig1 = aorig->vertice;
    printf("Vértice O0: %d, Vértice O1: %d\n", vorig0, vorig1);
    BuscaArestaRua(&(*ruadest), nrdest, &vdest0, &adest, &(*G));
    vdest1 = adest->vertice;
    printf("Vértice D0: %d, Vértice D1: %d\n", vdest0, vdest1);
    
    printf("Arestas O: %p, Vértice D: %p\n", aorig, adest);
    printf("--------------------------------\n");
    
    
    tpeso mindist = DBL_MAX;
    char *caso;
    
    if (aorig == adest) {
        printf("Caso A\n");
        caso = "A";
        mindist = Distancia(nrorig, nrdest);
    } else {
        
        Dijkstra(G, vorig0, &(*customin0), &(*antecessor0));
        Dijkstra(G, vorig1, &(*customin1), &(*antecessor1));
        
        printf("------------------------>Sai Dijkstra\n");
        int k;
        printf("customin0: ");
        for (k =0; k < G->num_vertices; k++) {
            printf("%f ", customin0[k]);
        }
        printf("\n");
        printf("antecessor0: ");
        for (k =0; k < G->num_vertices; k++) {
            printf("%d ", antecessor0[k]);
        }
        printf("\n");
        printf("customin1: ");
        for (k =0; k < G->num_vertices; k++) {
            printf("%f ", customin1[k]);
        }
        printf("\n");
        printf("antecessor1: ");
        for (k =0; k < G->num_vertices; k++) {
            printf("%d ", antecessor1[k]);
        }
        
        tpeso B = customin0[vdest0] + (double)Distancia(nrorig,aorig->nrini) + (double)Distancia(nrdest,adest->nrini);
        tpeso C = customin0[vdest1] + (double)Distancia(nrorig,aorig->nrini) + (double)Distancia(nrdest,adest->nrfim);
        tpeso D = customin1[vdest0] + (double)Distancia(nrorig,aorig->nrfim) + (double)Distancia(nrdest,adest->nrini);
        tpeso E = customin1[vdest1] + (double)Distancia(nrorig,aorig->nrfim) + (double)Distancia(nrdest,adest->nrfim);
        
        printf("\nB: %f, C: %f, D: %f, E: %f\n", B, C, D, E);
        
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
    
    printf("Escolhas: Caso - %s, MinDist: %f\n", caso, mindist);
    
    tapontador nomesRuas[G->num_vertices];
    int countRuas = 0;
    
    if (caso == "A") {
        printf("CASO A \n");
        nomesRuas[0] = aorig;
        countRuas = countRuas + 1;
    } else if (caso == "B") {        
        printf("CASO B \n");
        enfileirarRuasInvertidas(vdest0, nomesRuas, &countRuas, antecessor0, G);
    } else if (caso == "C") {
        printf("CASO C \n");
        enfileirarRuasInvertidas(vdest1, nomesRuas, &countRuas, antecessor0, G);
    } else if (caso == "D") {
        printf("CASO D \n");
        enfileirarRuasInvertidas(vdest0, nomesRuas, &countRuas, antecessor1, G);
    } else {
        printf("CASO E \n");
        enfileirarRuasInvertidas(vdest1, nomesRuas, &countRuas, antecessor1, G);
    }
    
   /* if (strcmp((nomesRuas[countRuas]->nomerua), (arestaO->nomerua)) != 0){
        countRuas = countRuas + 1;
        nomesRuas[countRuas] = aorig;
    }*/
    
    /*
    fprintf(fp,"%s %ld %s %ld %ld %d\n", ruaorig, nrorig, ruadest, nrdest, mindist, *countRuas);
    
    int i;
    
    for (i = 0; i < *countRuas; i++) {
        fprintf(fp, "%s\n", nomesRuas[i]);
    } */
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
