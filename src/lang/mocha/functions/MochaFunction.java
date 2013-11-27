package lang.mocha.functions;

public interface MochaFunction {

	String getFunctionName();
	
	int getParameterNumber();
	
	String[] getParameterTypes();
	
	void onExecute(String... args);
}
