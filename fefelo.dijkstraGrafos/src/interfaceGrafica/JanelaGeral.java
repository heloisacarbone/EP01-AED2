package interfaceGrafica;

public class JanelaGeral implements JanelaImplementada {

    @Override
    public void desenharJanela(String titulo, int tipo, double tempo, int numConjuntoVertices) {
            VisualGeral visual = new VisualGeral();
            visual.setTitle(titulo);
            visual.Parametros(tipo, tempo, numConjuntoVertices);
            visual.setVisible(true);
            System.out.println("Criando window do "+ titulo);
    }
    
    
}