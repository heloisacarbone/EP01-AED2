package file;

import java.io.*;

public class Text {
	
	public static BufferedReader openReadFile(String fileName) {
		try {
			FileReader arq = new FileReader(fileName);
			BufferedReader leitor = new BufferedReader(arq);
			return leitor;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public void openWriteFile(String fileName) {
		
	
	}

}
