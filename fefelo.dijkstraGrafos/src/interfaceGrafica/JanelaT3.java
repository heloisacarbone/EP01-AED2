package interfaceGrafica;

public class JanelaT3 extends JanelaAbstrata {
    public JanelaT3(JanelaImplementada j) {
        super(j);
    }
 
    @Override
    public void desenhar() {
        System.out.println("DESENHANDO JANELA DO TIPO 3");
}
 
}