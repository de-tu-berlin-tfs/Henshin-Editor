package agg.attribute.impl;

import java.util.Enumeration;
import java.util.Vector;

import agg.attribute.AttrInstance;
import agg.attribute.AttrVariableTuple;
import agg.attribute.handler.AttrHandler;
import agg.util.XMLHelper;

/**
 * Adds the possibility of being shared. Needed as the container of variable
 * values inside a context core.
 * 
 * @author $Author: olga $
 * @version $Id: VarTuple.java,v 1.25 2010/11/28 22:11:36 olga Exp $
 */
public class VarTuple extends LoneTuple implements AttrVariableTuple {

	static final long serialVersionUID = 1133219076552845488L;

	/**
	 * A special value designating that the assignment was done in the parent
	 * context and this context's references don't count; such a value is
	 * permanent, it may not be changed by this value tuple.
	 */
	protected final int FIXED_VALUE = -1;

//	private static transient int COUNTER = 0;

	
	public VarTuple(AttrTupleManager manager, ContextView context,
			ValueTuple parent) {
		super(manager, context, parent);
		getContextView().setAllowVarDeclarations(true);
		getContextView().setAllowComplexExpr(true);

		if (this.parent != null) {
			if (this.getSize() == 0 && this.parent.getSize() > 0) {
				for (int i = 0; i < this.parent.getSize(); i++) {
					final VarMember m = (VarMember) this.parent.getMemberAt(i);
					if  (m.getDeclaration().getTypeName() == null
							|| m.getName() == null) {
						continue;
					}
					this.declare(m.getHandler(), m.getDeclaration()
							.getTypeName(), m.getName());
					if (m.isSet()) {
						((VarMember) this.getMemberAt(m.getName())).setExpr(m.getExpr());
						((VarMember) this.getMemberAt(m.getName())).setExprAsText(m.getExprAsText());
					}
				}
			}
			for (int i = 0; i < this.getSize(); i++) {
				final VarMember var = (VarMember) this.getMemberAt(i);
				final VarMember varp = (VarMember) this.parent.getMemberAt(var.getName());
				if (varp != null) {
					var.setInputParameter(varp.isInputParameter());
					var.setMark(varp.getMark());
					var.setTransient(varp.isTransient());
				}
			}
			getContextView().setVariableContext(
					parent.getContextView().isVariableContext());
		}

//		COUNTER++;
		this.errorMsg = "";
	}
	
	public void updateByParent() {
		if (this.parent != null) {	
			for (int i = 0; i < this.getSize(); i++) {
				final VarMember m = (VarMember) this.getMemberAt(i);
				if (this.parent.getMemberAt(m.getName()) == null) {
					this.getTupleType().deleteMemberAt(m.getName());
				} else {
					final VarMember pm = (VarMember) this.parent.getMemberAt(m.getName());
					m.setInputParameter(pm.isInputParameter());
					if (!m.isIn) {
						while (m.getReferenceCount() > 0) {
							m.undoUnification();
						}
					}
				}
			}
		}
	}
	
	public void propagateValueFromParent() {
		if (this.parent != null) {	
			for (int i = 0; i < this.parent.getSize(); i++) {
				final VarMember pm = (VarMember) this.parent.getMemberAt(i);				
				if (pm.isSet()) {
					final VarMember m = (VarMember) this.getMemberAt(pm.getName());
					m.setExpr(pm.getExpr());
					if (!m.isTransient)
						m.setTransient(pm.isTransient());
				}
			}			
		}
	}
	
	public void clear() {
		for (int i = 0; i < getSize(); i++) {
			this.deleteMemberAt(i);
		}
	}

	public void dispose() {
		super.dispose();
		if (this.type != null)
			this.type.dispose();
		if (getContextView() != null)
			setContextView(null);
	}

	protected void finalize() {
		super.finalize();
//		COUNTER--;
	}

	public void makeCopyOf(final VarTuple tuple) {
		for (int i = 0; i < tuple.getSize(); i++) {
			final VarMember m = (VarMember) tuple.getMemberAt(i);
//			System.out.println(m.getDeclaration().getTypeName()+"   "+m.getName());
			if  (m.getDeclaration().getTypeName() == null
					|| m.getName() == null) {
				continue;
			}
			this.declare(m.getHandler(), m.getDeclaration()
					.getTypeName(), m.getName());
			
			final VarMember var = (VarMember) this.getMemberAt(i);
			if (m.isSet()) {
				if (m.getExprAsText().indexOf("@") != -1) {
					var.setExpr(m.getExpr());
				} else
					var.setExprAsText(m.getExprAsText());
			}
			
			var.setInputParameter(m.isInputParameter());
			var.setMark(m.getMark());
			var.setTransient(m.isTransient());
		}
	}
	
	public String getErrorMsg() {
		return this.errorMsg;
	}

	protected ValueMember newMember(DeclMember decl) {
		if (decl == null)
			Thread.dumpStack();
		if (decl != null)
			return new VarMember(this, decl);
		
		return null;
	}

	protected String getLogEntry(int index) {
		return (super.getLogEntry(index)
				+ (getVarMemberAt(index).isInputParameter() ? " In " : " ") + (getVarMemberAt(
				index).isOutputParameter() ? " Out " : " "));
	}


	public VarMember getVarMemberAt(int index) {
		return (VarMember) getMemberAt(index);
	}

	public VarMember getVarMemberAt(String name) {
		return (VarMember) getMemberAt(name);
	}

	public boolean isDeclared(String name) {
		return getTupleType().containsName(name);
	}

	public boolean isDeclared(String typestr, String name) {
		if (getTupleType().containsName(name)) {
			final VarMember vm = (VarMember) getMemberAt(name);
			if (vm.getDeclaration().getTypeName().equals(typestr))
				return true;
			
			return false;
		} 
		return false;
	}

	public void declare(AttrHandler handler, String typestr, String name) {		
//		if (!getTupleType().isClassName(name)) 
		{
			getTupleType().addMember(handler, typestr, name);
		}
//		else {
//			throw new AttrImplException("Class name  " + name
//					+ "  cannot be used as a member name.");
//		}
	}

	public void deleteLeafDeclaration(String name) {
		final DeclTuple decl = getTupleType();
		if (decl.getParentInCharge(decl.getIndexForName(name)) == decl) {
			decl.deleteMemberAt(name);
		} else {
			throw new AttrImplException(
					"Can't delete a declaration which was made \nin a parent context.");
		}
	}

	public boolean isDeclared(Vector<String> varNames) {
		for (int i = 0; i < varNames.size(); i++) {
			if (!getTupleType().containsName(varNames.elementAt(i)))
				return false;
		}
		return true;
	}

	/** Checks if there is input parameter. */
	public boolean hasInputParameter() {
		int size = getSize();
		for (int i = 0; i < size; i++) {
			if (getVarMemberAt(i).isInputParameter())
				return true;
		}
		return false;
	}

	/**
	 * Checks if all input parameter are set. If there are no parameter this
	 * method returns true.
	 */
	public boolean areInputParametersSet() {
		int size = getSize();
		for (int i = 0; i < size; i++) {
			final VarMember vm = getVarMemberAt(i);
			if (vm.isInputParameter() && !vm.isSet())
				return false;
		}
		return true;
	}

	public void unsetInputParameters() {
		int size = getSize();
		for (int i = 0; i < size; i++) {
			final VarMember vm = getVarMemberAt(i);
			if (vm.isInputParameter() && vm.isSet()) {
				while (vm.getReferenceCount() > 0) {
					vm.undoUnification();
				}
			}
		}
	}

	/**
	 * Checks if all output parameter are set. If there are no parameter this
	 * method return true. 
	 */
	public boolean areOutputParametersSet(){ 
		int size = getSize(); 
		for (int i = 0; i<size; i++) {
			final VarMember vm = getVarMemberAt(i);
			if (vm.isOutputParameter() && !vm.isSet())
				return false; 
		}
		return true; 
	}

	public void unsetVariables() {
		int size = getSize();
		for (int i = 0; i < size; i++) {
			final VarMember vm = getVarMemberAt(i);
			if (vm.isSet()) {
				while (vm.getReferenceCount() > 0) {
					vm.undoUnification();
				}
			}
		}
	}

	public void unsetNotInputVariables() {
		int size = getSize();
		for (int i = 0; i < size; i++) {
			final VarMember vm = getVarMemberAt(i);
			if (!vm.isInputParameter() && vm.isSet()) {
				while (vm.getReferenceCount() > 0) {
					vm.undoUnification();
				}
			}
		}
	}

	/** Test, if all members can yield true or false. */
	public boolean isDefinite() {
		for (int i = 0; i < getSize(); i++) {
			final VarMember m = getVarMemberAt(i);

			// nicht definierte Variablen werden geloescht
			if (!m.isDefinite()) {
				final DeclTuple decl = ((ValueTuple) getParent()).getTupleType();
				if (m.getReferenceCount() == 0) { // is not used
					if (decl.getIndexForName(m.getName()) != -1) { // is
						// declared
						if (decl.getParentInCharge(decl.getIndexForName(m
								.getName())) == decl) {// is not set, then
							// delete
							decl.deleteMemberAt(m.getName());
							i--;
						} else
							return false;
					} else
						return false;
				} else
					return false;
			}
		}
		return true;
	}

	public Vector<String> getUndefiniteVariables() {
		final Vector<String> undefVars = new Vector<String>(2);
		for (int i = 0; i < getSize(); i++) {
			final VarMember m = getVarMemberAt(i);
			if (!m.isDefinite())
				undefVars.addElement(m.getName());
		}
		return undefVars;
	}

	public Vector<String> getVariableNames() {
		final Vector<String> names = new Vector<String>();
		for (int i = 0; i < getSize(); i++) {
			final VarMember v = getVarMemberAt(i);
			if ((v != null)) {
				String varName = v.getName();
				if (!names.contains(varName))
					names.addElement(varName);
			}
		}
		// System.out.println(names);
		return names;
	}

	public boolean compareTo(AttrInstance another) {
		VarTuple vt = (VarTuple) another;
		// compare tuple type
		if (!this.type.compareTo(vt.getTupleType()))
			return false;
		// compare member value
		int length = getSize();
		for (int i = 0; i < length; i++) {
			VarMember v = getVarMemberAt(i);
			VarMember v1 = vt.getVarMemberAt(i);
			if ((v.getExpr() == null) && (v1.getExpr() == null))
				;
			else if ((v.getExpr() == null) && (v1.getExpr() != null))
				return false;
			else if ((v.getExpr() != null) && (v1.getExpr() == null))
				return false;
			else if (!v.getExpr().equals(v1.getExpr()))
				return false;
		}
		return true;
	}

	public String toString() {
		String s = "VarTuple  hash: " + hashCode() + "  [\n";
		for (int i = 0; i < getSize(); i++) {
			VarMember v = getVarMemberAt(i);
			if (v != null) {
				s = s.concat(getVarMemberAt(i).getName() + ": "
						+ getVarMemberAt(i).toString());
				s = s.concat("\n");
			}
		}
		s = s.concat("\n ]");
		return s;
	}

	public void showVariables() {
		System.out.println("Attr. context variables:  "+this.context.core+ "     "+this.context);
		for (int i = 0; i < getSize(); i++) {
			VarMember v = getVarMemberAt(i);
			if (v != null) {
				String val = v.isSet()? v.getExprAsText(): "";
				System.out.println(v.getDeclaration().getTypeName() + " : "
						+ v.getName() + " : " +v + " : " + v.getMark()+ " : "+v.isTransient+" = "+val);
			}
		}
		System.out.println("================================");
	}

	public void XwriteObject(XMLHelper h) {
		int num = getSize();
		for (int i = 0; i < num; i++) {
			VarMember val = getVarMemberAt(i);
			if (val != null 
					&& val.getDecl().getTypeName() != null
					&& val.getName() != null
					&& !"".equals(val.getDecl().getTypeName())
					&& !"".equals(val.getName())) {
				h.openSubTag("Parameter");

				if (val.isSet()) {
					if (val.getExpr().isConstant()) {
						h.addAttr("value", val.getExpr().getValue().toString());
						/*
						 * h.openSubTag("Value");
						 * if(val.getDeclaration().getType().toString().equals("String"))
						 * h.addAttrValue("string", v); else
						 * h.addAttrValue(decl.getType().toString(), v);
						 * h.close();
						 */
					} else {
						h.addAttr("expr", val.getExpr().getString());
					}
				}
				h.addAttr("name", val.getName());
				h.addAttr("type", val.getDecl().getTypeName());
				boolean isin = val.isInputParameter();
				boolean isout = val.isOutputParameter();
				String inout = (isin && isout) ? "inout" : (isin) ? "input"
						: (isout) ? "output" : "";
				if (inout != "")
					h.addAttr("PTYPE", inout);
				h.close();
			}
		}
	}

	public void XreadObject(XMLHelper h) {
		Enumeration<?> en = h.getEnumeration("", null, true, "Parameter");
		while (en.hasMoreElements()) {
			h.peekElement(en.nextElement());
			String name = h.readAttr("name");
			if (!isDeclared(name)) {
				String typestr = h.readAttr("type");
				if (!"".equals(typestr)) {
					String handlerName = agg.attribute.handler.impl.javaExpr.JexHandler
							.getLabelName();
					AttrHandler handler = getAttrManager().getHandler(handlerName);
					declare(handler, typestr, name);
					VarMember var = getVarMemberAt(name);
					String inout = h.readAttr("PTYPE");
					boolean isin = false;
					boolean isout = false;
					if (inout.equals("inout")) {
						isin = isout = true;
					} else if (inout.equals("input"))
						isin = true;
					else if (inout.equals("output"))
						isout = true;
					var.setInputParameter(isin);
					var.setOutputParameter(isout);
	
					String value = h.readAttr("value");
					if ((value != null) && !value.equals("")) {
						// System.out.println("VarTuple.Xread: value: "+value);
						if (typestr.equals("String"))
							var.setExprAsText("\"" + value + "\"");
						else if (typestr.equals("Character") || typestr.equals("char"))
							var.setExprAsText("\'" + value.charAt(0) + "\'");
						else
							var.setExprAsText(value);
						var.checkValidity();
					} else {
						String expr = h.readAttr("expr");
						// System.out.println("VarTuple.Xread: expr: "+expr);
						if ((expr != null) && !expr.equals("")) {
							var.setExprAsText(expr);
							var.checkValidity();
						}
					}
				}
			}
			h.close();
		}
	}

}
/*
 * $Log: VarTuple.java,v $
 * Revision 1.25  2010/11/28 22:11:36  olga
 * new method
 *
 * Revision 1.24  2010/11/17 14:05:53  olga
 * tuning
 *
 * Revision 1.23  2010/09/23 08:14:08  olga
 * tuning
 *
 * Revision 1.22  2010/04/29 15:14:06  olga
 * tuning and tests
 *
 * Revision 1.21  2010/04/28 15:15:38  olga
 * tuning
 *
 * Revision 1.20  2010/04/27 12:29:41  olga
 * tuning
 *
 * Revision 1.19  2010/03/31 21:08:13  olga
 * tuning
 *
 * Revision 1.18  2010/03/19 14:45:22  olga
 * tuning
 *
 * Revision 1.17  2010/03/11 13:36:40  olga
 * bug fixed
 *
 * Revision 1.16  2010/03/08 15:37:42  olga
 * code optimizing
 *
 * Revision 1.15  2009/11/23 08:54:04  olga
 * new map kind: OBJECT_FLOW_MAP
 *
 * Revision 1.14  2009/10/12 08:27:59  olga
 * ApplOfRS-improved and bug fixed
 *
 * Revision 1.13  2009/10/05 08:52:14  olga
 * RSA check - bug fixed
 *
 * Revision 1.12  2009/07/13 07:26:20  olga
 * ARS: further development
 *
 * Revision 1.11  2009/03/12 10:57:44  olga
 * some changes in CPA of managing names of the attribute variables.
 *
 * Revision 1.10  2009/02/02 09:05:12  olga
 * New trafo option: Reset Graph, which can be used with the option -loop over layers-
 * when the user want to start a new loop at the start (not transformed) graph.
 * New trafo option: Graph Consistency check at the end of (layer) graph transformation.
 * Bug fixed  in copy start graph
 *
 * Revision 1.9  2008/02/25 08:44:48  olga
 * Extending of CPA: new class CriticalRulePairAtGraph to get critical
 * matches of two rules at a concret graph.
 *
 * Revision 1.8  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/24 09:42:33  olga
 * AGG transformation engine tuning
 *
 * Revision 1.6  2007/09/10 13:05:18  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2007/05/07 07:59:30 olga CSP: extentions
 * of CSP variables concept
 * 
 * Revision 1.4 2006/11/01 11:17:29 olga Optimized agg sources of CSP algorithm,
 * match usability, graph isomorphic copy, node/edge type multiplicity check for
 * injective rule and match
 * 
 * Revision 1.3 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.2 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.3 2005/07/11 14:23:08 olga Error fixed during matching with
 * variable
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.18 2005/01/03 13:14:43 olga Errors handling
 * 
 * Revision 1.17 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.16 2004/10/25 14:24:37 olga Fehlerbehandlung bei CPs und
 * Aenderungen im zusammenhang mit termination-Modul in AGG
 * 
 * Revision 1.15 2004/06/09 11:32:54 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.14 2004/05/13 17:54:09 olga Fehlerbehandlung
 * 
 * Revision 1.13 2004/04/28 12:46:38 olga test CSP
 * 
 * Revision 1.12 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.11 2004/03/01 15:47:55 olga Tests
 * 
 * Revision 1.10 2004/01/22 17:51:50 olga ...
 * 
 * Revision 1.9 2004/01/07 09:37:15 olga test
 * 
 * Revision 1.8 2003/12/18 16:25:49 olga Tests.
 * 
 * Revision 1.7 2003/07/10 17:41:56 olga Parameter
 * 
 * Revision 1.6 2003/07/10 14:03:26 olga Fehler bei XML Ausgabe fuer
 * char/Character eingebaut
 * 
 * Revision 1.5 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.4 2003/02/03 17:46:30 olga new method : compareTo(AttrInstance a)
 * 
 * Revision 1.3 2002/11/25 14:56:27 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.2 2002/09/19 16:23:56 olga Test an Output-Parameter. Noch geht es
 * nicht.
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.9 2000/12/07 14:23:35 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.8 2000/06/05 14:08:03 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.7 2000/04/05 12:09:32 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
