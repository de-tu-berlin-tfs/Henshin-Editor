package agg.attribute.parser.javaExpr;

import java.awt.Frame;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.Color;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.PrintStream;

import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.SymbolTable;

/**
 * @version $Id: Jex.java,v 1.18 2010/09/23 08:15:01 olga Exp $
 * @author $Author: olga $
 */
public class Jex implements ActionListener {

	static final long serialVersionUID = 1L;

	static public final int PARSE_ERROR = 0;

	static public final int IS_CONSTANT = 1;

	static public final int IS_VARIABLE = 2;

	static public final int IS_COMPLEX = 3;

	protected TextField typeTF;

	static protected JexParser parser;

	protected PrintStream out, err;

	protected ByteArrayOutputStream redirect;

	protected PrintStream redirectOut;

	protected boolean isOutput = false; // true;

	protected Object variableExpression;

	public Jex() {
	}

	/* Testing environment */

	public static void main(String args[]) {
		Jex me = new Jex();
		SimpleNode.setClassResolver(new ClassResolver());

		Frame frame = new Frame("Jex-Test");
		me.typeTF = new TextField("", 30);
		me.typeTF.setBackground(Color.WHITE);
		me.typeTF.addActionListener(me);
		frame.add(me.typeTF);
		frame.pack();
		frame.setVisible(true);
	}

	public void fullTest(String line) {
		try {
			test_interpret(line, null, null);
		} catch (AttrHandlerException ex1) {
			System.out.println(ex1.getMessage());
			ex1.printStackTrace();
		}
	}

	public void actionPerformed(ActionEvent e) {
		String line = this.typeTF.getText();
		if (line.compareTo("q") == 0) {
			System.exit(0);
		}
		fullTest(line);
	}

	protected int getExprProperty() {
		synchronized (JexParser.jjtree) {
		SimpleNode node = (SimpleNode) JexParser.jjtree.rootNode();
		int result = IS_COMPLEX;

		if (node.isConstantExpr()) {
			result = IS_CONSTANT;
		} else if (node.jjtGetNumChildren() == 1) {
			node = (SimpleNode) node.jjtGetChild(0);
			if (node.identifier.equals("PrimaryExpression")
					&& node.jjtGetNumChildren() == 1) {
				node = (SimpleNode) node.jjtGetChild(0);
				if (node.identifier.equals("Id")) {
					result = IS_VARIABLE;
				}
			}
		}
		return result;
		}
	}

	protected void newStdOutStream() {
		FileOutputStream fdOut = new FileOutputStream(FileDescriptor.out);
		System.setOut(new PrintStream(new BufferedOutputStream(fdOut, 128),
				true));
	}

	protected void newStdErrStream() {
		FileOutputStream fdErr = new FileOutputStream(FileDescriptor.err);
		System.setErr(new PrintStream(new BufferedOutputStream(fdErr, 128),
				true));
	}

	protected void antiRedirect() {
		newStdOutStream();
		newStdErrStream();
	}

	/**
	 * Swaps StdOut and StdErr to ByteStream and vice versa
	 * 
	 * @see #redirectToString
	 * @see #restoreOutputStream
	 */
	protected void swapPrintStream() {
		PrintStream swapOut = System.out;
		PrintStream swapErr = System.err;
		System.setOut(this.out);
		System.setErr(this.err);
		this.out = swapOut;
		this.err = swapErr;
	}

	protected void redirectToString() {
		this.out = System.out;
		this.err = System.err;
		this.redirect = new ByteArrayOutputStream();
		this.redirectOut = new PrintStream(this.redirect);
		System.setOut(this.redirectOut);
		System.setErr(this.redirectOut);
	}

	protected void restoreOutputStream() {
		if (this.redirect != null && this.redirectOut != null) {
			System.setOut(this.out);
			System.setErr(this.err);
			if (this.isOutput) {
				System.out.println(this.redirect.toString());
			}
			this.redirect = null;
			this.redirectOut = null;
		}
	}

	static public String addMessage(Exception ex) {
		String msg = ex.getMessage();
		if (msg == null || msg.equals("null")) {
			return "";
		} 
		return "\n  (" + msg + ")";
	}

	/* Services for JexHandler */

	public void parseOutputOn() {
		this.isOutput = true;
	}

	public void parseOutputOff() {
		this.isOutput = false;
	}

	public int parse(String text) throws AttrHandlerException {
		// swapPrintStream();
//		AttrSession.logPrintln(VerboseControl.logTrace, "Jex:\n->parse");
//		AttrSession.logPrintln(VerboseControl.logTrace, "Jex: text " + text);
//		// only Jex parser tracing
//		AttrSession.logPrintln(VerboseControl.logJexParser, "Jex:\n->parse");
//		AttrSession
//				.logPrintln(VerboseControl.logJexParser, "Jex: text " + text);
		// swapPrintStream();
		// redirectToString();
		try {
			return parse_(text);
		} catch (Exception ex1) {
			if (this.redirect != null)
				throw new AttrHandlerException(this.redirect.toString()
						+ addMessage(ex1));
			throw new AttrHandlerException(addMessage(ex1));
		} finally {
			// restoreOutputStream();
//			AttrSession.logPrintln(VerboseControl.logTrace, "Jex:\n<-parse");
			// only Jex parser tracing
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Jex:\n<-parse");
		}
	}

	protected String getPropertyText(int code) {
		synchronized (this) {
		if (code == IS_CONSTANT)
			return "Constant expression";
		if (code == IS_VARIABLE)
			return "Variable";
		if (code == IS_COMPLEX)
			return "Complex expression";
		return "Parse error";
		}
	}

	protected int parse_(String text) throws ParseError {
		// swapPrintStream();
//		AttrSession.logPrintln(VerboseControl.logTrace, "Jex:\n->\tparse_");
//		AttrSession.logPrintln(VerboseControl.logTrace, "Jex: text " + text);
//		// only Jex parser tracing
//		AttrSession.logPrintln(VerboseControl.logJexParser, "Jex:\n->\tparse_");
//		AttrSession
//				.logPrintln(VerboseControl.logJexParser, "Jex: text " + text);

		// swapPrintStream();
		int result = PARSE_ERROR;

		String line = text + " ";
		byte bytes[] = line.getBytes();

		java.io.ByteArrayInputStream stream = new java.io.ByteArrayInputStream(
				bytes);

		synchronized (JexParser.jjtree) {
//		if (parser == null) {
//			parser = new JexParser(stream);
//		} else 
		{
			JexParser.ReInit(stream);
			JexParser.jjtree.reset();
				
//			System.out.println("parse text: "+text+"    stack: "+SimpleNode.stack.size()+"   top: "+SimpleNode.top+"    :::: "+SimpleNode.stack.hashCode());
			SimpleNode.top = -1;
			SimpleNode.stack.clear();
//			System.out.println("parse text: "+text+"    stack: "+SimpleNode.stack.size()+"   top: "+SimpleNode.top+"    :::: "+SimpleNode.stack.hashCode());
		}
		try {
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Parsing ["
//					+ text + "] ...");
			JexParser.CompilationUnit();
			JexParser.jjtree.rootNode().dump("  ");
			result = getExprProperty();
			
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					" Expression property: " + getPropertyText(result));
//			// swapPrintStream();
//			AttrSession.logPrintln(VerboseControl.logTrace, "Jex:\n<-\tparse_");
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Jex:\n<-\tparse_");
			// swapPrintStream();
			return result;
		} catch (ParseError ex1) {
			throw ex1;
		}
		}
	}

	public void check(Node ast, Class<?> type, SymbolTable symtab)
			throws AttrHandlerException {
//		AttrSession.logPrintln(VerboseControl.logJexParser, "Jex.check:   "
//				+ ast + "   " + type);
		// redirectToString();
		try {
			SimpleNode.setSymbolTable(symtab);
			try {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Type-Checking ...");
				ast.checkContext();
				this.variableExpression = ast;
			} catch (ASTIdNotDeclaredException ex1) {
//				AttrSession
//						.logPrintln(VerboseControl.logJexParser, "Variable \""
//								+ ex1.getMessage() + "\" is not declared");
				
				throw ex1;
			} catch (ASTWrongTypeException ex2) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Wrong type.\n");
//				if (ex2.getExpected() != null) {
//					AttrSession.logPrintln(VerboseControl.logJexParser,
//							"Required signature: " + ex2.getExpected());
//				}
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Encountered: " + ex2.getFound());
				
				throw ex2;
			} catch (ASTMemberException ex3) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Exception:\n" + ex3.getMessage());
				
				throw ex3;
			} catch (Exception ex) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Exception:\n" + ex.getMessage());
				
				throw new RuntimeException(ex.getMessage());
			}

			if (type != null && type != Void.TYPE) {
				Class<?> resultType = ((SimpleNode) ast).getNodeClass();
				boolean assignable = isAssignable(type, resultType);
				if (assignable) {
//					AttrSession.logPrintln(VerboseControl.logJexParser,
//							"Types are assignable");
				} else {
					
//					AttrSession.logPrintln(VerboseControl.logJexParser,
//							"Wrong expression type.");
//					AttrSession.logPrintln(VerboseControl.logJexParser,
//							"Required: " + type.getName());
//					AttrSession.logPrintln(VerboseControl.logJexParser,
//							"Found: " + resultType.getName());
					// unerklaerliches Ereignis.
					new ASTWrongTypeException();
				}
			}
		} catch (Exception ex1) {
			if (this.redirect != null)
				throw new AttrHandlerException(this.redirect.toString());
			
			throw new AttrHandlerException(ex1.getMessage());
		} finally {
			// restoreOutputStream();
		}
	}

	public void check(String text, Class<?> type, SymbolTable symtab)
			throws AttrHandlerException {
		// redirectToString();
		try {
			check_(text, type, symtab);
		} catch (Exception ex1) {
			if (this.redirect != null) {
				// System.out.println("Jex.check:: 1) AttrHandlerException:
				// "+ex1.getMessage() );
				throw new AttrHandlerException(this.redirect.toString());
			} 
				// System.out.println("Jex.check:: 2) AttrHandlerException:
				// "+ex1.getMessage() );
			throw new AttrHandlerException(ex1.getMessage());
			
		} finally {
			// restoreOutputStream();
		}
	}

	public void check_(String text, Class<?> type, SymbolTable symtab)
			throws ParseError {

		parse_(text);
		SimpleNode.setSymbolTable(symtab);

		synchronized (JexParser.jjtree) {
		try {
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Type-Checking ...");
			JexParser.jjtree.rootNode().checkContext();
			JexParser.jjtree.rootNode().dump("  ");
		} catch (ASTIdNotDeclaredException ex1) {
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Variable \""
//					+ ex1.getMessage() + "\" is not declared");
			throw new ASTIdNotDeclaredException("Variable \""
					+ ex1.getMessage() + "\" is not declared");
		} catch (ASTWrongTypeException ex2) {
//			AttrSession
//					.logPrintln(VerboseControl.logJexParser, "Wrong type.\n");
//			if (ex2.getExpected() != null) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Required signature: " + ex2.getExpected());
//			}
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Encountered: "
//					+ ex2.getFound());
			
			throw new ASTWrongTypeException(
					"Wrong expression type.  Required signature: "
							+ ex2.getExpected() + "   Encountered: "
							+ ex2.getFound());
		} catch (ASTMemberException ex3) {
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Exception:\n"
//					+ ex3.getMessage());
			
			throw new ASTMemberException("Member Exception:  "
					+ ex3.getMessage());
		} catch (Exception ex) {
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Exception:\n"
//					+ ex.getMessage());
			throw new RuntimeException("Exception:  " + ex.getMessage());
		}

		if (type != null && type != Void.TYPE) {
			Class<?> resultType = ((SimpleNode) JexParser.jjtree.rootNode())
					.getNodeClass();
			if (!isAssignable(type, resultType)) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Wrong expression type.\n Required: " + type.getName()
//								+ "\n Found: " + resultType.getName());
				throw new ASTWrongTypeException(
						"Wrong expression type.  Required: " + type.getName()
								+ "  Found: " + resultType.getName());
			}
		}
		}
	}

	protected static Object refObj = new Object();

	protected boolean isAssignable(Class<?> to, Class<?> from) {
		// System.out.println("agg.parser.javaExpr.Jex.isAssignable
		// "+from.getName()+" to "+to.getName());
		if (to.isPrimitive() || from.isPrimitive()) {
			// System.out.println("check: to.isPrimitive() ||
			// from.isPrimitive()");
			if (to == Byte.TYPE || to == Short.TYPE || to == Integer.TYPE
					|| to == Long.TYPE) {
				if (from == Byte.TYPE || from == Short.TYPE
						|| from == Integer.TYPE || from == Long.TYPE) {
					return true;
				} 
				return false;
			} else if (to == Float.TYPE || to == Double.TYPE) {
				if (from == Float.TYPE || from == Double.TYPE) {
					return true;
				} 
				return false;
			} else
				return (to == from);
		} 
		
		return to.isAssignableFrom(from) || from.isInstance(refObj);
	}

	protected Object test_interpret(String text, Class<?> type, SymbolTable symtab)
			throws AttrHandlerException {
		Object result;

		// redirectToString();//
		try {
			result = interpret_(text, type, symtab);
		} catch (ParseError ex1) {
			if (this.redirect != null)
				throw new AttrHandlerException(this.redirect.toString()
						+ addMessage(ex1));
			
			throw new AttrHandlerException(addMessage(ex1));
		} finally {
			// restoreOutputStream();//
		}
		return result;
	}

	/* ******************************************************************************* */

	/**
	 * Interprets an expression.
	 */
	public Object interpret(Node ast, Class<?> type, SymbolTable symtab)
			throws AttrHandlerException {
//		AttrSession.logPrintln(VerboseControl.logTrace,
//				"Jex: \n->interpret(ast)");
//		AttrSession.logPrintln(VerboseControl.logJexParser,
//				"Jex: \n->interpret(ast)");
		try {
			Object result = null;

			check(ast, type, symtab);
			// redirectToString();
			try {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Evaluating ..." + ast);
				ast.interpret();
				result = ast.getRootResult();
				/* If here, success */
				return result;
			} catch (ASTIdNotDeclaredException ex1) {
//				AttrSession
//						.logPrintln(VerboseControl.logJexParser, "Variable \""
//								+ ex1.getMessage() + "\" is not declared");
				
				throw ex1;
			} catch (ASTMissingValueException ex2) {
				if (ast.getString().indexOf("==null") != -1) {
					result = new Boolean(true); // ast.getRootResult();
					return result;
				} 
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//							"Missing value for variable \"" + ex2.getMessage()
//									+ "\".");
				
				throw ex2;
				
			} catch (ASTMemberException ex3) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"ASTMemberException " + ex3.getMessage());
				
				throw ex3;
			} catch (Exception ex) {
//				AttrSession.logPrintln(VerboseControl.logJexParser,
//						"Exception:\n" + ex.getMessage());
				
				throw new RuntimeException(ex.getMessage());
			}
		} catch (Exception ex1) {
			// swapPrintStream();
//			AttrSession.logPrintln(VerboseControl.logTrace,
//					"Jex.interpret : AttrHandlerException geworfen");
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Jex.interpret: AttrHandlerException geworfen");
			// swapPrintStream();
//			if (this.redirect != null)
//				throw new AttrHandlerException(this.redirect.toString());
			
			throw new AttrHandlerException("AttrHandlerException  : "
						+ ex1.getMessage());
		} finally {
			// restoreOutputStream();
//			AttrSession.logPrintln(VerboseControl.logTrace,
//					"Jex: \n<-interpret(ast)");
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Jex: \n<-interpret(ast)");
		}
	}

	/**
	 * Interprets an expression.
	 * 
	 * @deprecated Strings are NOT sufficent to represent expressions
	 */
	public Object interpret(String text, Class<?> type, SymbolTable symtab)
			throws AttrHandlerException {
		
//		AttrSession.logPrintln(VerboseControl.logTrace, "Jex: \n->interpret()");
//		AttrSession.logPrintln(VerboseControl.logJexParser,
//				"Jex: \n->interpret()");
		// redirectToString();
		try {
			return interpret_(text, type, symtab);
		} catch (Exception ex1) {
			// swapPrintStream();
//			AttrSession.logPrintln(VerboseControl.logTrace,
//					"Jex: AttrHandlerException geworfen");
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Jex: AttrHandlerException geworfen");
			// swapPrintStream();
			if (this.redirect != null)
				throw new AttrHandlerException(this.redirect.toString());
			
			throw new AttrHandlerException(ex1.getMessage());
		} finally {
			// restoreOutputStream();
//			AttrSession.logPrintln(VerboseControl.logTrace,
//					"Jex: \n<-interpret()");
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Jex: \n<-interpret()");
		}
	}

	public Object interpret_(String text, Class<?> type, SymbolTable symtab)
			throws ParseError {
		// swapPrintStream();
		// AttrSession.logPrintln(VerboseControl.logTrace,"Jex:\n->interpret_");
		// AttrSession.logPrintln(VerboseControl.logJexParser,
		// "Jex:\n->interpret_");
		synchronized (JexParser.jjtree) {
		if (JexParser.jjtree.rootNode() != null)
			JexParser.jjtree.rootNode().dump("");
		// swapPrintStream();

		check_(text, type, symtab);

		try {
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Evaluating ...");
			JexParser.jjtree.rootNode().interpret();
			Object result = JexParser.jjtree.rootNode().getRootResult();
//			String resultString = result == null ? "null" : result.toString();			
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Result = "
//					+ resultString);
			// swapPrintStream();
			JexParser.jjtree.rootNode().dump("");
			
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Result = "
//					+ resultString);
			// swapPrintStream();
			/* If here, success */
			return result;
		} catch (ASTIdNotDeclaredException ex1) {
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Variable \""
//					+ ex1.getMessage() + "\" is not declared");
			throw ex1;
		} catch (ASTMissingValueException ex2) {
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Missing value for variable \"" + ex2.getMessage() + "\".");
			
			/*
			 * ContextView cv = (ContextView) symtab; VarMember vm =
			 * ((VarTuple)cv.getVariables()).getVarMemberAt("x");
			 * System.out.println(vm.getExprAsText());
			 * 
			 * if(parser.jjtree.rootNode().isVariable()){ swapPrintStream();
			 * System.out.println("eine Variable");
			 * parser.jjtree.rootNode().dump("->");
			 * parser.jjtree.rootNode().rewrite();
			 * System.out.println(parser.jjtree.rootNode().getClass());
			 * getAST().dump("ast: "); swapPrintStream(); return null; }else
			 */
			throw ex2;
		} catch (ASTMemberException ex3) {
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"ASTMemberException " + ex3.getMessage());
			throw ex3;
		} catch (Exception ex) {
//			AttrSession.logPrintln(VerboseControl.logJexParser, "Exception:\n"
//					+ ex.getMessage());
			throw new RuntimeException(ex.getMessage());
		} finally {
			// swapPrintStream();
//			AttrSession.logPrintln(VerboseControl.logTrace,
//					"Jex:\n<-interpret_");
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Jex:\n<-interpret_");
			// swapPrintStream();
		}
		}
	}

	/** Rewrites all variables */
	public void rewrite(Node ast, Class<?> type, SymbolTable symtab)
			throws AttrHandlerException {
		// redirectToString();
		try {
			check(ast, type, symtab);
		} catch (AttrHandlerException ahe) {
//			AttrSession.logPrintln(VerboseControl.logJexParser,
//					"Type-checking causes an error. Rewriting failed. "
//							+ ahe.getMessage());
			// restoreOutputStream();
			throw ahe;
		} finally {
			// restoreOutputStream();
		}
		ast.rewrite();

		if (ast.getError().length() != 0)
			throw new AttrHandlerException(ast.getError());
	}

	/** returns root node of the abstract syntax tree */
	public Node getAST() {
		return JexParser.jjtree.rootNode();
	}

	public Object getVariableExpression() {
		return this.variableExpression;
	}

}
/*
 * $Log: Jex.java,v $
 * Revision 1.18  2010/09/23 08:15:01  olga
 * tuning
 *
 * Revision 1.17  2010/07/29 10:09:20  olga
 * Array stack changed to Vector stack
 *
 * Revision 1.16  2010/04/28 15:16:13  olga
 * tuning
 *
 * Revision 1.15  2010/03/31 21:10:49  olga
 * tuning
 *
 * Revision 1.14  2010/03/18 18:17:01  olga
 * synchronized - added
 *
 * Revision 1.13  2010/03/08 15:38:21  olga
 * code optimizing
 *
 * Revision 1.12  2009/11/26 10:57:29  olga
 * tests
 *
 * Revision 1.11  2007/11/05 09:18:23  olga
 * code tuning
 *
 * Revision 1.10  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.9  2007/09/10 13:05:48  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.8 2007/03/28 10:01:14 olga - extensive changes
 * of Node/Edge Type Editor, - first Undo implementation for graphs and
 * Node/edge Type editing and transformation, - new / reimplemented options for
 * layered transformation, for graph layouter - enable / disable for NACs, attr
 * conditions, formula - GUI tuning
 * 
 * Revision 1.7 2006/12/13 13:32:58 enrico reimplemented code
 * 
 * Revision 1.6 2006/08/09 07:42:18 olga API docu
 * 
 * Revision 1.5 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.4 2006/04/03 08:57:50 olga New: Import Type Graph and some bugs
 * fixed
 * 
 * Revision 1.3 2006/03/01 09:55:47 olga - new CPA algorithm, new CPA GUI
 * 
 * Revision 1.2 2006/01/16 09:37:01 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:52 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:01 olga Version with Eclipse
 * 
 * Revision 1.9 2005/03/21 09:22:57 olga ...
 * 
 * Revision 1.8 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.7 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.6 2003/03/05 18:24:14 komm sorted/optimized import statements
 * 
 * Revision 1.5 2003/02/20 17:38:25 olga Tests
 * 
 * Revision 1.4 2003/01/15 11:36:00 olga Neue VerboseControl Konstante
 * :logJexParser zum Testen
 * 
 * Revision 1.3 2002/12/20 11:25:47 olga Tests
 * 
 * Revision 1.2 2002/11/25 14:56:36 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:04 olga Imported sources
 * 
 * Revision 1.11 2000/06/05 14:08:10 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.10 2000/05/24 10:03:10 olga Nur Testausgaben eingebaut bei der
 * Suche nach dem Fehler in DISAGG : Match Konstante auf Konstante
 * 
 * Revision 1.9 2000/05/17 11:33:38 shultzke diverse Aenderungen. Version von
 * Olga wird erwartet
 * 
 * Revision 1.8 2000/04/05 12:10:51 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.7 2000/03/14 10:59:40 shultzke Transformieren von Variablen auf
 * Variablen sollte jetzt funktionieren Ueber das Design der Copy-Methode des
 * abstrakten Syntaxbaumes sollte unbedingt diskutiert werden.
 * 
 * Revision 1.6 2000/03/03 11:32:45 shultzke Alphaversion von Variablen auf
 * Variablen matchen
 */
