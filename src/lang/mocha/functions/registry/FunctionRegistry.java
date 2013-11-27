package lang.mocha.functions.registry;

import java.util.ArrayList;

import lang.mocha.functions.MochaFunction;

public class FunctionRegistry {

	private static ArrayList<String> funcNames = new ArrayList<String>();
	private static ArrayList<MochaFunction> funcs = new ArrayList<MochaFunction>();
	
	public static void registerFunction(MochaFunction function){
		
		if(!funcNames.contains(function.getFunctionName()) && !funcs.contains(function)){
			
			funcNames.add(function.getFunctionName());
			funcs.add(function);
		}
	}
	
	public static ArrayList<String> getFunctionNames(){
		
		return funcNames;
	}
	
	public static ArrayList<MochaFunction> getFunctions(){
		
		return funcs;
	}
}
