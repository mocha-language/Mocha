package interpreter.mocha;

import interpreter.mocha.io.MochaInputReader;

import java.io.File;

import lang.mocha.functions.registry.FunctionRegistry;
import lang.mocha.utilclasses.MochaClass;
import lang.mocha.utilclasses.MochaMethod;
import lang.mocha.utilclasses.MochaVariable;
import exceptions.mocha.MochaExceptions;

public class MochaInterpreter {

	public static MochaClass currentClass;
	public static MochaMethod currentMethod;
	public static boolean isParsingMethod;
	private static String codeLine = "";

	public static void parse(File file) {

		String[] codeLines = MochaInputReader.readFromFile(file);

		String code = "";

		for (int i = 0; i < codeLines.length; i++) {

			code += codeLines[i];
		}

		codeLine += code;
	}

	public static String getCode() {

		return codeLine;
	}

	public static void parse() {

		parse(codeLine);
	}

	public static void parse(String code) {

		String codeLine = code.trim();

		if (codeLine != null) {

			if (codeLine.startsWith("[")) {

				String fullDecl = findFullDeclaration(codeLine);

				parseDeclaration(fullDecl);

				String codeLeft = codeLine.replace("[" + fullDecl + "]", "")
						.trim();

				if (!codeLeft.contentEquals("") && codeLeft != null) {

					parse(codeLeft);
				} else {

					return;
				}

			} else if (codeLine.startsWith("(")) {

				String fullVal = findFullValue(codeLine);

				parse(fullVal);

			} else if (codeLine.startsWith("{")) {

			}
		}
	}

	private static String findFullDeclaration(String code) {

		int declCount = 0;

		char[] chars = code.toCharArray();

		for (int i = 0; i < chars.length; i++) {

			if (chars[i] == '\u005B') {
				declCount++;
			} else if (chars[i] == '\u005D') {
				declCount--;
			}

			if (declCount == 0) {

				return code.substring(1, i);
			}
		}

		MochaExceptions.throwNew("Declaration Exception",
				"Declarations must be closed.", code);

		return null;
	}

	private static void parseDeclaration(String declaration) {

		String[] parts = declaration.split(" ", 2);

		if (parts[0].contentEquals("def") && !isParsingMethod) {

			String[] declParts = declaration.split(" ", 4);

			if (declParts[2].contentEquals("class")) {

				if (declParts[3].contains("(")) {

					if (declParts[1].contentEquals("public")
							|| declParts[1].contentEquals("private")) {

						String className = declParts[3].substring(0,
								declParts[3].indexOf("("));

						if (className.contains(" ")) {

							className = className.substring(0,
									className.indexOf(" "));
						}

						String value = declParts[3].substring(
								declParts[3].indexOf("(")).trim();

						System.out.println(declParts[1].toUpperCase()
								+ " Class Found: " + className);

						String decl = "";

						for (int i = 0; i < declParts.length; i++) {

							decl += " " + declParts[i];
						}

						decl = decl.trim();

						MochaClass mc = new MochaClass(className, declParts[1]);

						currentClass = mc;

						System.out.println("TEST::" + value);

						parse(value);

					}
				} else {

					MochaExceptions.throwNew("Class Exception",
							"Classes must include a body.", "[" + declaration
									+ "]");
				}

			} else if (declParts[2].startsWith("var:")
					|| declParts[2].startsWith("~var:")) {

				if (declParts[1].contentEquals("public")
						|| declParts[1].contentEquals("private")) {

					String varName;

					if (declParts[3].contains("=")) {

						varName = declParts[3].substring(0,
								declParts[3].indexOf("="));
					} else {

						varName = declParts[3];
					}

					varName = varName.trim();

					String value;

					if (declParts[3].contains("=")) {

						value = declParts[3].substring(declParts[3]
								.indexOf("="));
					} else {

						value = null;
					}

					if (value != null)
						value = value.replaceFirst("=", "").trim();

					value = parseValue(value);

					boolean isStatic = false;

					if (declParts[2].startsWith("~")) {

						isStatic = true;
					}

					String decl = "";

					for (int i = 0; i < declParts.length; i++) {

						decl += " " + declParts[i];
					}

					decl = decl.trim();

					String typeString = declParts[2].substring(declParts[2]
							.indexOf(":") + 1);

					if (typeString != null && !typeString.contentEquals("")) {

						if (isStatic) {
							System.out.println(declParts[1].toUpperCase()
									+ " STATIC Variable Found: " + varName
									+ " of type " + typeString + " with value "
									+ value);
						} else {
							System.out.println(declParts[1].toUpperCase()
									+ " Variable Found: " + varName
									+ " of type " + typeString + " with value "
									+ value);
						}

						MochaVariable mv = new MochaVariable(varName,
								typeString, value, declParts[1], isStatic);

						if (isParsingMethod) {

							currentMethod.addVariable(mv);
						} else {

							currentClass.addVariable(mv);
						}

						System.out.println("TEST::" + value);

						if (declParts[3].contains("=")) {

							parse(value);
						}
					} else {

						MochaExceptions.throwNew("Variable Exception",
								"Variables must declare a type.", declaration);
					}
				} else {

					MochaExceptions
							.throwNew(
									"Variable Exception",
									"Variables must include a visibility as the second parameter in their declaration.",
									"[" + declaration + "]");
				}
			} else if (declParts[2].startsWith("function:")
					|| declParts[2].startsWith("~function:")) {

				if (declParts[3].contains("(")) {

					if (declParts[1].contentEquals("public")
							|| declParts[1].contentEquals("private")) {

						String funcName;

						if (declParts[3].contains("(")) {

							funcName = declParts[3].substring(0,
									declParts[3].indexOf("("));
						} else {

							funcName = declParts[3];
						}

						funcName = funcName.trim();

						String code = declParts[3].substring(
								declParts[3].indexOf("(")).trim();

						boolean isStatic = false;

						if (declParts[2].startsWith("~")) {

							isStatic = true;
						}

						String decl = "";

						for (int i = 0; i < declParts.length; i++) {

							decl += " " + declParts[i];
						}

						decl = decl.trim();

						String returnString = declParts[2]
								.substring(declParts[2].indexOf(":") + 1);

						if (returnString != null
								&& !returnString.contentEquals("")) {

							if (isStatic) {
								System.out.println(declParts[1].toUpperCase()
										+ " STATIC Function Found: " + funcName
										+ " with return type " + returnString);
							} else {
								System.out.println(declParts[1].toUpperCase()
										+ " Function Found: " + funcName
										+ " with return type " + returnString);
							}

							MochaMethod mf = new MochaMethod(funcName, code,
									returnString, declParts[1], isStatic);

							currentClass.addFunction(mf);

							System.out.println("TEST::" + code);

							if (funcName.contentEquals("main") && isStatic) {

								mf.parse();
							}
						} else {
							MochaExceptions.throwNew("Function Exception",
									"Functions must include a return type",
									declaration);
						}
					} else {

						MochaExceptions
								.throwNew(
										"Function Exception",
										"Functions must include a visibility as the second parameter in their declaration.",
										"[" + declaration + "]");
					}

				} else {

					MochaExceptions.throwNew("Function Exception",
							"Functions must include a body.", "[" + declaration
									+ "]");
				}
			} else {

				MochaExceptions.throwNew("Declaration Exception", declParts[2]
						+ " is not a valid declaration type.", declaration);
			}

		} else if (parts[0].contentEquals("def") && isParsingMethod) {

			String[] declParts = declaration.split(" ", 3);

			if (declParts[1].startsWith("var:")) {

				String varName;

				if (declParts[2].contains("=")) {

					varName = declParts[2].substring(0,
							declParts[2].indexOf("="));
				} else {

					varName = declParts[2];
				}

				varName = varName.trim();

				String value;

				if (declParts[2].contains("=")) {

					value = declParts[2].substring(declParts[2].indexOf("="));

				} else if (declParts[3].contains("=")) {

					value = declParts[3].substring(declParts[3].indexOf("="));

				} else {

					value = null;
				}

				if (value != null)
					value = value.replaceFirst("=", "").trim();

				value = parseValue(value);

				boolean isStatic = currentMethod.isStatic();

				String decl = "";

				for (int i = 0; i < declParts.length; i++) {

					decl += " " + declParts[i];
				}

				decl = decl.trim();

				String typeString = declParts[1].substring(declParts[1]
						.indexOf(":") + 1);

				if (typeString != null && !typeString.contentEquals("")) {

					System.out.println("IN-METHOD STATIC Variable Found: "
							+ varName + " of type " + typeString
							+ " with value " + value);

					MochaVariable mv = new MochaVariable(varName, typeString,
							value, "in-method", isStatic);

					currentMethod.addVariable(mv);

					System.out.println("TEST::" + value);

				} else {

					MochaExceptions.throwNew("Variable Exception",
							"Variables must declare a type.", declaration);
				}

			} else {

				MochaExceptions
						.throwNew(
								"Variable Exception",
								"Variables can only be declared without visibilities in a function.",
								declaration);
			}

		} else if (parts[0].contentEquals("set")) {

			String[] declParts = declaration.split(" ", 3);

			if (declParts[1].contentEquals("var")) {

				String varName;

				if (declParts[2].contains("=")) {

					varName = declParts[2].substring(0,
							declParts[2].indexOf("="));
				} else {

					varName = declParts[2];
				}

				varName = varName.trim();

				if (isVariableCallValid(varName)) {

					if (declParts[2].contains("=")) {

						String value;

						if (declParts[2].contains("=")) {

							value = declParts[2].substring(declParts[2]
									.indexOf("="));

						} else if (declParts[3].contains("=")) {

							value = declParts[3].substring(declParts[3]
									.indexOf("="));

						}

						value = declParts[2].substring(declParts[2]
								.indexOf("="));

						if (value != null)
							value = value.replaceFirst("=", "").trim();

						value = parseValue(value);

						System.out.println("Setting Variable: " + varName
								+ " with value " + value);

						setVariable(varName, value);

						System.out.println("TEST::" + value);

					} else {

						MochaExceptions
								.throwNew(
										"Variable Exception",
										"Variable set functions must contain a value to set.",
										declaration);
					}

				} else {

					MochaExceptions.throwNew("Variable Exception", varName
							+ " is not a known variable.", declaration);
				}
			}

		} else {
			MochaExceptions.throwNew("Declaration Exception",
					"A declaration must start with 'def' or 'set'.", "["
							+ declaration + "]");
		}
	}

	private static String parseValue(String value) {

		// TODO

		return value;
	}

	private static String findFullValue(String value) {

		int valCount = 0;

		char[] chars = value.toCharArray();

		for (int i = 0; i < chars.length; i++) {

			if (chars[i] == '(') {
				valCount++;
			} else if (chars[i] == ')') {
				valCount--;
			}

			if (valCount == 0) {

				return value.substring(1, i);
			}
		}

		MochaExceptions.throwNew("Declaration Value Exception",
				"Declaration values must be closed.", value);

		return null;
	}

	private static boolean isVariableCallValid(String variable) {

		if (isParsingMethod) {

			if (currentMethod.getVariableNames().contains(variable)
					|| currentClass.getVariableNames().contains(variable)) {

				return true;
			} else {

				return false;
			}
		} else {

			if (currentClass.getVariableNames().contains(variable)) {

				return true;
			} else {

				return false;
			}
		}
	}

	private static void setVariable(String variable, String value) {

		if (isParsingMethod) {

			if (currentMethod.getVariableNames().contains(variable)) {

				int loc = currentMethod.getVariableNames().indexOf(variable);

				currentMethod.getVariables().get(loc).setValue(value);

			} else if (currentClass.getVariableNames().contains(variable)) {

				int loc = currentClass.getVariableNames().indexOf(variable);

				currentClass.getVariables().get(loc).setValue(value);
			}
		} else {

			if (currentClass.getVariableNames().contains(variable)) {

				int loc = currentClass.getVariableNames().indexOf(variable);

				currentClass.getVariables().get(loc).setValue(value);
			}
		}
	}

	@SuppressWarnings("unused")
	// TODO Use
	private static boolean isFunctionCallValid(String function, String... args) {

		if (FunctionRegistry.getFunctionNames().contains(function)) {

			int loc = FunctionRegistry.getFunctionNames().indexOf(function);

			if (FunctionRegistry.getFunctions().get(loc).getParameterNumber() == args.length) {

				return true;
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	@SuppressWarnings("unused")
	// TODO Use
	private static void executeFunction(String function, String... args) {

		if (FunctionRegistry.getFunctionNames().contains(function)) {

			int loc = FunctionRegistry.getFunctionNames().indexOf(function);

			if (FunctionRegistry.getFunctions().get(loc).getParameterNumber() == args.length) {

				FunctionRegistry.getFunctions().get(loc).onExecute(args);
			}
		}
	}
}
