package mocha;

import java.io.File;

import compiler.mocha.MochaCompiler;

public class InterpretTest {

	public static void main(String[] args){
		
		//MochaCompiler.compileToMug("testMug", new File("C:/Users/Krealutz/Desktop/Mocha Development/Test Files/test1.mocha"), new File("C:/Users/Krealutz/Desktop/Mocha Development/Test Files/test2.mocha"));
		
		MochaCompiler.decompileFromMugAndParse("testMug");
	}
}
