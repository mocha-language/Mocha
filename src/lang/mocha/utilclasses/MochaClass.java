package lang.mocha.utilclasses;

import java.util.ArrayList;

public class MochaClass {

	private String name, vis;
	private ArrayList<String> varNames = new ArrayList<String>();
	private ArrayList<MochaVariable> vars = new ArrayList<MochaVariable>();
	private ArrayList<String> funcNames = new ArrayList<String>();
	private ArrayList<MochaMethod> funcs = new ArrayList<MochaMethod>();

	public MochaClass(String name, String vis) {

		this.name = name;
		this.vis = vis;
	}

	public void addVariable(MochaVariable variable) {

		if (!varNames.contains(variable.getName()) && !vars.contains(variable)) {

			varNames.add(variable.getName());
			vars.add(variable);
		}
	}

	public void addFunction(MochaMethod function) {

		if (!funcNames.contains(function.getName())
				&& !funcs.contains(function)) {

			funcNames.add(function.getName());
			funcs.add(function);
		}
	}

	public ArrayList<String> getVariableNames() {

		return varNames;
	}

	public ArrayList<MochaVariable> getVariables() {

		return vars;
	}

	public ArrayList<String> getFunctionNames() {

		return funcNames;
	}

	public ArrayList<MochaMethod> getFunctions() {

		return funcs;
	}

	public String getName() {

		return name;
	}

	public String getVisibility() {

		return vis;
	}
}
