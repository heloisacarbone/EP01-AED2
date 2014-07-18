package interfaceGrafica;

public class JanelaT1 extends JanelaAbstrata {
    public JanelaT1(JanelaImplementada j) {
        super(j);
    }

    @Override
    public void desenhar() {
        System.out.println("DESENHANDO JANELA DO TIPO 1");
}
 
}