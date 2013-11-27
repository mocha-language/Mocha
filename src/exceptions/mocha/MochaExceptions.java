package exceptions.mocha;

public class MochaExceptions {

	public static void throwNew(String exceptionType, String exception, String... lines){
		
		System.err.println(exceptionType+"::"+exception);
		
		for(String line : lines){
			
			System.err.println("\t"+line);
		}
	}
}
