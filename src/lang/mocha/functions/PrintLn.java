package lang.mocha.functions;

public class PrintLn implements MochaFunction{

	@Override
	public String getFunctionName() {
		
		return "println";
	}

	@Override
	public void onExecute(String... args) {
			
		System.out.println(args[0]);
	}

	@Override
	public int getParameterNumber() {
		
		return 1;
	}

	@Override
	public String[] getParameterTypes() {
		
		return new String[]{"any"};
	}
}
