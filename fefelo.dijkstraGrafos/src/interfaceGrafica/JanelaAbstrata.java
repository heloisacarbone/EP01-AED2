package interfaceGrafica;


public abstract class JanelaAbstrata {
  
    protected JanelaImplementada janela;
 
    public JanelaAbstrata(JanelaImplementada j) {
        janela = j;
    }
 
    
    
    public void desenharJanela(String titulo, int tipo, double tempo, int numConjuntoVertices) {
        janela.desenharJanela(titulo, tipo, tempo, numConjuntoVertices);
    }
 
    public abstract void desenhar();
}