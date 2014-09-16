package agg.attribute.parser;

import java.io.File;
import java.io.IOException;

import agg.attribute.AttrContext;
import agg.attribute.impl.ValueMember;

public class ExpressionParser {

	private static String classpath = "";

	private static boolean addMethodCall;

	private static String errStr = "";

	private static String expression;

	public final static boolean parse(String className,
			AttrContext attrContext, ValueMember member, String expr) {
		expression = expr;
		String exprStr = expr;
		String expr1 = checkStaticMethodCall(expr);
		if (expr1.equals(expr)) {
			expr1 = checkAddMethodCall(expr);
			if (!expr1.equals(expr)) {
				exprStr = expr1;
				addMethodCall = true;
			}
		} else
			exprStr = expr1;

		JavaClassCreation jcc = new JavaClassCreation();
		File file = jcc.createClass(className, attrContext, member, exprStr,
				addMethodCall);
		try {
			int c;
			StringBuffer fBuffer = new StringBuffer();
			Process p = Runtime.getRuntime().exec(
					"javac " + getClasspath() + file.getName());
			while ((c = p.getErrorStream().read()) != -1) {
				fBuffer.append((char) c);
			}
			System.out.println("Process Error Stream: \n" + fBuffer.toString());
			errStr = fBuffer.toString().trim();
			if (errStr.length() != 0 && !errStr.startsWith("Note:")) {
				return false;
			}
		} catch (IOException e) {
			errStr = e.getLocalizedMessage();
			e.printStackTrace();
		}
		expression = exprStr;
		return true;
	}

	public static String getError() {
		return errStr;
	}

	public static String getExpression() {
		return expression;
	}

	private static String getClasspath() {
		// System.out.println(System.getProperties());
		// System.out.println(System.getProperty("java.class.path"));
		classpath = System.getProperty("java.class.path");
		if (classpath.trim().length() != 0) {
			classpath = "-cp " + classpath + " ";
		}
		return classpath;
	}

	// check the form: $package.class$.static_method
	private static String checkStaticMethodCall(String aValue) {
		// System.out.println("checkStaticMethodCall: ");
		String result = aValue;
		if (aValue.indexOf("$") == 0) {
			int ind = aValue.substring(1).indexOf("$");
			if (ind > 0) {
				result = aValue.substring(ind + 1, aValue.length());
				String clstr = aValue.substring(1, ind + 1);
				// System.out.println("clstr: "+clstr);
				String tst = clstr.substring(clstr.lastIndexOf(".") + 1, clstr
						.length());
				// System.out.println("tst: "+tst);
				result = tst + aValue.substring(ind + 2, aValue.length());
				// System.out.println("result: "+result);
			}
		} else {
			String tst = aValue;
			String pname = null;
			String tmp = "";
			while (tst.indexOf(".") != -1) {
				// System.out.println("tst: "+tst);
				// System.out.println(tst.substring(0, tst.indexOf(".")));
				String next = tst.substring(0, tst.indexOf("."));
				Package p = Package.getPackage(tmp + next);
				if (p != null) {
					pname = p.getName();
					// System.out.println("pname: "+pname);
					tmp = p.getName();
				} else
					tmp = tmp + next;
				tmp = tmp + ".";
				// System.out.println("tmp: "+tmp);
				tst = tst.substring(tst.indexOf(".") + 1, tst.length());
			}
			if (pname != null) {
				result = aValue.replaceFirst(pname + ".", "");
				// System.out.println("result: "+result);
				/*
				 * String clstr = result.substring(0, result.indexOf("."));
				 * //System.out.println("clstr: "+clstr); try { Class c =
				 * Class.forName(pname+"."+clstr); return result; }catch
				 * (ClassNotFoundException ex) {}
				 */
			}
		}
		return result;
	}

	private static String checkAddMethodCall(String aValue) {
		String result = aValue.replaceFirst(";", ".");
		// System.out.println("isAddMethodCall: "+result);
		return result;
	}

}
