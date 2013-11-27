package interpreter.mocha.io;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class MochaInputReader {

	public static String[] readFromFile(File file) {

		List<String> lines = new ArrayList<String>();

		try {
			
			Scanner sc = new Scanner(file);
			
			while(sc.hasNext()){
				
				lines.add(sc.nextLine().trim());
			}
			
			String[] linesRead = new String[lines.size()];
			
			for(int i = 0; i < lines.size();i++){
				
				linesRead[i] = lines.get(i);
			}
			
			return linesRead;
			
		} catch (Exception e) {
			
			e.printStackTrace();
			
			return null;
		}
	}
}
