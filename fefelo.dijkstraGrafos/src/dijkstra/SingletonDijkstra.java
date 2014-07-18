package dijkstra;


public class SingletonDijkstra {
        private static Dijkstra instance; 
        public SingletonDijkstra(){} 
        
        public static synchronized Dijkstra getInstance(){
            instance = new Dijkstra();       
            return instance;
        }
    
}