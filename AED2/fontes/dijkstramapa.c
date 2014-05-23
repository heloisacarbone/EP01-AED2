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

#define DBL_MAX    
#define _DEBUG_

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
    
}

/* não é nem tvertice
void Relaxamento(tvertice u, tvertice *v, tpeso aresta) {
    if (v->peso > (u->peso + aresta)) {
        (*v)->peso = u->peso + aresta;
    }
} 

void InicializaFonteUnica(tgrafo *G, tvertice v0) {
    tapontador p  = G->ListaAdj;
    while (p != NULL) {
        if (p == v0) {
            p->peso = 0;
        } else {
            p->peso = DBL_MAX;
        }
        
        p = p->prox;
    }
} */


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
    fp = fopen(nomearq, "r");
    if (fp==NULL)
        return(0);

    if (fscanf(fp, "%d", qtreq)!=1)
        return(0);

    char auxOrigem[MAXSTRING];
    char auxDestino[MAXSTRING];
    
    for (i = 0; i < *qtreq ; i++) {
        if (fscanf(fp, "%s %ld %s %ld", auxOrigem, &(nrorigem[i]), auxDestino, &(nrdestino[i])) != 4) { // Verificar com prof
          fclose(fp);
          return(0);
        }

        strcpy(auxOrigem, RuaOrigem[i]); 
        strcpy(auxDestino, RuaDestino[i]); 
    }

    fclose(fp);
    return(1);
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

      #ifdef _DEBUG_
      printf("Requisicao: %s, %ld a %s,%ld\n", ruaorigem[idreq],nrorigem[idreq],
             ruadestino[idreq], nrdestino[idreq]);
      #endif
      fpout = fopen(argv[3], "at");
      ImprimeMelhorRota(fpout, ruaorigem[idreq], nrorigem[idreq],
                               ruadestino[idreq], nrdestino[idreq], &G);
      fclose(fpout);
    }

    LiberaGrafo(&G);
  }

  return(1);
}

