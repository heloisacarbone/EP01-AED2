package file;

import grafo.Grafo;

import java.util.Map;

public interface FileInterface {
	public void initializeGraph(String file);
	public void output(int type, Map<Integer, Map<String, String>> caminhos, Map<Integer, Double> maxdists);
}