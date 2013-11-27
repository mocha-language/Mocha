package compiler.mocha;

import interpreter.mocha.MochaInterpreter;
import interpreter.mocha.io.MochaInputReader;

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class MochaCompiler {

	public static void compileToMug(String compileName, File... files) {

		try {

			new File("mocha-compiled").mkdirs();

			File mugFile = new File("mocha-compiled/" + compileName + ".mug");

			DataOutputStream dos = new DataOutputStream(new FileOutputStream(
					mugFile));

			String codeLine = "";

			for (File file : files) {

				String[] codeLines = MochaInputReader.readFromFile(file);

				String code = "";

				for (int i = 0; i < codeLines.length; i++) {

					code += codeLines[i];
				}

				codeLine += code;
			}

			dos.writeUTF(codeLine);

			dos.close();

		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static String decompileFromMug(String compileName) {

		try {

			DataInputStream ds = new DataInputStream(new FileInputStream(
					new File("mocha-compiled/" + compileName + ".mug")));

			String codeLine = ds.readUTF();

			ds.close();

			return codeLine;

		} catch (Exception e) {

			e.printStackTrace();
			
			return null;
		}
	}

	public static void decompileFromMugAndParse(String compileName) {

		try {

			DataInputStream ds = new DataInputStream(new FileInputStream(
					new File("mocha-compiled/" + compileName + ".mug")));

			String codeLine = ds.readUTF();

			ds.close();

			MochaInterpreter.parse(codeLine);

		} catch (Exception e) {

			e.printStackTrace();
		}
	}
}
