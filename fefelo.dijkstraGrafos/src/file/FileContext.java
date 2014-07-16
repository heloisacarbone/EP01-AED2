package file;

import java.util.Map;
// O main que vai chamar o initialize e o controller o print
public class FileContext {
	private FileInterface file = null;
	public void setFile(FileInterface file) {
		this.file = file;
	}
	
	public FileInterface getFile() {
		return this.file;
	}
	
	public void initialize(String file) {
		this.file.initializeGraph(file);	
	}
	
	public void print(int type, Map<Integer, Map<String, String>> caminhos, Map<Integer, Double> maxdists) {
		this.file.output(type, caminhos, maxdists);
	}
	
	
}