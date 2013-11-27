package lang.mocha.utilclasses;

import interpreter.mocha.MochaInterpreter;

import java.util.ArrayList;

public class MochaMethod {

	private String name, code, vis, returnType;
	private boolean isStatic;
	private ArrayList<String> varNames = new ArrayList<String>();
	private ArrayList<MochaVariable> vars = new ArrayList<MochaVariable>();
	
	public MochaMethod(String name, String code, String returnType, String vis, boolean isStatic){
		
		this.name = name;
		this.code = code;
		this.returnType = returnType;
		this.vis = vis;
		this.isStatic = isStatic;
	}
	
	public void addVariable(MochaVariable variable) {

		if (!varNames.contains(variable.getName()) && !vars.contains(variable)) {

			varNames.add(variable.getName());
			vars.add(variable);
		}
	}
	
	public ArrayList<String> getVariableNames() {

		return varNames;
	}

	public ArrayList<MochaVariable> getVariables() {

		return vars;
	}
	
	public String getName(){
		return name;
	}
	
	public String getCode(){
		return code;
	}
	
	public String getReturnType(){
		
		return returnType;
	}
	
	public String getVisibility(){
		return vis;
	}
	
	public boolean isStatic(){
		return isStatic;
	}
	
	public void parse(){
		
		MochaInterpreter.currentMethod = this;
		
		MochaInterpreter.isParsingMethod = true;
		
		MochaInterpreter.parse(getCode());
		
		MochaInterpreter.isParsingMethod = false;
	}
}
