package agg.attribute.parser;

import java.io.IOException;
import java.io.File;
import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.util.Vector;

import agg.attribute.AttrContext;
//import agg.attribute.AttrMember;
//import agg.attribute.AttrConditionMember;
//import agg.attribute.AttrConditionTuple;
//import agg.attribute.AttrManager;
//import agg.attribute.AttrMapping;
//import agg.attribute.AttrVariableMember;
//import agg.attribute.AttrVariableTuple;
//import agg.attribute.facade.InformationFacade;
//import agg.attribute.handler.AttrHandler;
//import agg.attribute.handler.HandlerExpr;
//import agg.attribute.handler.impl.javaExpr.JexHandler;
//import agg.attribute.impl.AttrTupleManager;
//import agg.attribute.impl.ContextView;
//import agg.attribute.impl.DeclMember;
//import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.ValueMember;
//import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.VarMember;

public class JavaClassCreation {

	private static String s1 = "public class ";

	private static String s2 = " {";

	private static String s3 = "}";

	private String name;

	private Vector<String> classPackages;

	private AttrContext attrContext;

	private ValueMember member;

	private String expr;

	public JavaClassCreation() {
		agg.attribute.facade.InformationFacade info = agg.attribute.facade.impl.DefaultInformationFacade
				.self();
		agg.attribute.handler.AttrHandler javaHandler = info.getJavaHandler();
		this.classPackages = ((agg.attribute.handler.impl.javaExpr.JexHandler) javaHandler)
				.getClassResolver().getPackages();
	}

	public File createClass(String className, AttrContext attrCntxt,
			ValueMember vmember, String vexpr, boolean addMethodCall) {
		this.attrContext = attrCntxt;
		this.member = vmember;
		this.expr = vexpr;
		this.name = className;

		boolean fileExists = true;
		int i = 0;
		while (fileExists) {
			File f = new File(this.name);
			f.deleteOnExit();
			if (f.exists()) {
				this.name = this.name + i;
				i++;
			} else
				fileExists = false;
		}

		final File file = new File(this.name + ".java");

		System.out.println("Output file:  " + file);
		String b = new String();
		b = b + createImports() + "\n";
		b = b + s1 + this.name + s2 + "\n";
		b = b + createFields() + "\n";
		if (!addMethodCall)
			b = b + createGetMethod() + "\n";
		else
			b = b + createAddMethod() + "\n";
		b = b + s3;
		System.out.println("\n" + b + "\n");

		try {
			FileOutputStream fos = new FileOutputStream(file);
//			byte buffer[] = new byte[1024 * 64];
			try {
				fos.write(b.getBytes());
				fos.close();
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		return file;
	}

	private String createImports() {
		String b = new String();
		for (int i = 0; i < this.classPackages.size(); i++) {
			String p = this.classPackages.get(i);
			b = b + "import " + p + ".*;\n";
		}
		return b;
	}

	private String createFields() {
		String b = "";
		VarTuple vars = (VarTuple) this.attrContext.getVariables();
		for (int i = 0; i < vars.getSize(); i++) {
			VarMember v = vars.getVarMemberAt(i);
			b = b + "private " + v.getDeclaration().getTypeName() + " "
					+ v.getName() + ";\n";
		}
		return b;
	}

	private String createGetMethod() {
		String b = new String();
		String typeName = this.member.getDeclaration().getTypeName();
		b = "public " + typeName + " get" + this.member.getName() + "(){ \n";
		b = b + "return " + this.expr + ";\n";
		b = b + "}\n";
		return b;
	}

	// @SuppressWarnings("unchecked")
	private String createAddMethod() {
		String b = new String();
		String typeName = "void";
		b = "public " + typeName + " addItem" + "(){ \n";
		b = b + this.expr + ";\n";
		b = b + "}\n";
		return b;
	}
}
