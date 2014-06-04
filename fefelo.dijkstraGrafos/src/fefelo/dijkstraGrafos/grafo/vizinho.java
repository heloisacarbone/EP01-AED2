public class vizinho{
    private Vertice m = new Vertice();
    private Vertice n = new Vertice();
  
  public void vizinho(String x, String y){
    
    m = grafo.retornavertice(x);
    n = grafo.retornavertice(y);
    
    m.setadj(n);
    n.setadj(m);
  }
}
