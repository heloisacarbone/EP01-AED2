package file;

import java.io.*;

public class Text {
	private static FileReader readArq;
	private static FileWriter writeArq;
	
	public static BufferedReader openReadFile(String fileName) {
		try {
			readArq = new FileReader(fileName);
			BufferedReader leitor = new BufferedReader(readArq);
			return leitor;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	
	
	public static PrintWriter openWriteFile(String fileName) {
		try {
			File fileOut = new File(fileName);
			if (fileOut.exists()) {
	            fileOut.delete();
	        } else {
	            fileOut.createNewFile();
	        }
			writeArq = new FileWriter(fileOut, true);
	        PrintWriter printWriter = new PrintWriter(writeArq);
	        return printWriter;
		} catch (Exception ex) {
			ex.printStackTrace();
			return null;
		}
	}
	
	public static void closeFiles(){
		try {
			readArq.close();
			writeArq.close();
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}