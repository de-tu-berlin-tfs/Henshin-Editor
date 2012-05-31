package agg.attribute.impl;

import java.util.Observable;

import agg.attribute.AttrEvent;
import agg.attribute.AttrVariableMember;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;

/**
 * Class for members of attribute instance tuples that are used as variables in
 * a context. This is an extension of ValueMember; it adds reference counting.
 * 
 * @author Boris Melamed
 * @version $Id: VarMember.java,v 1.17 2010/11/28 22:11:36 olga Exp $
 */
public class VarMember extends ValueMember implements AttrVariableMember {

	
	/** Marking left hand side (LHS) of a rule */
	public static final int LHS = 0;

	/** Marking right hand side (RHS) of a rule */
	public static final int RHS = 1;

	/** Marking NAC variable of a rule */
	public static final int NAC = 2;

	/** Marking PAC variable of a rule */
	public static final int PAC = 3;
	
	/** Marking GAC variable of a rule */
	public static final int GAC = 4;
	
	protected int refCnt = 0;

	protected boolean isIn;

	protected boolean isOut;

	private int mark; // LHS | RHS | NAC | PAC |GAC

	private boolean enabled = true;
	
	public static final long serialVersionUID = 3905403576345689583L;

	/**
	 * Creating a new instance with the specified type.
	 * 
	 * @param tuple
	 *            Instance tuple that this value is a member of.
	 * @param decl
	 *            Declaration for this member.
	 */
	public VarMember(VarTuple tuple, DeclMember decl) {
		super(tuple, decl);
		this.isIn = false;
		this.isOut = false;
		this.errorMsg = "";
		this.isTransient = false;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}

	/** Removes this member from its tuple. */
	public void delete() {
		getDeclaration().delete();
	}

	public void setEnabled(boolean b) {
		this.enabled = b;
		fireChanged(AttrEvent.MEMBER_DISABLED);
	}

	public boolean isEnabled() {
		return this.enabled;
	}
	
	public boolean isInputParameter() {
		return this.isIn;
	}

	public void setInputParameter(boolean in) {
		this.isIn = in;
	}

	public boolean isOutputParameter() {
		return this.isOut;
	}

	public void setOutputParameter(boolean out) {
		this.isOut = out;
	}

	public int getReferenceCount() {
		return this.refCnt;
	}

	public boolean isUnifiableWith(HandlerExpr srcExpr) {
		if (isEmpty() || getExpr().equals(srcExpr))
			return true;
		
		this.errorMsg = getExpr() + "  is not unifiable with  " + srcExpr;
		return false;
	}

	public boolean unifyWith(HandlerExpr srcExpr) {
		if (!isUnifiableWith(srcExpr)) 
			return false;
		
		if (isEmpty()) {
			if (srcExpr != null) {
				this.setExpr(srcExpr.getCopy());
				incrementCount();
			}
		}
		return true;
	}

	/** copy the contents of a single entry instance into another. */
	public void copy(ValueMember fromInstance) {
		super.copy(fromInstance);
		VarMember fromVar = (VarMember) fromInstance;
		this.refCnt = fromVar.refCnt;
		this.isIn = fromVar.isIn;
		this.isOut = fromVar.isOut;
		setTransient(fromVar.isTransient());
		setMark(fromVar.getMark());
	}

	public void undoUnification() {
		decrementCount();
	}

	public void setExprAsObject(Object value) throws AttrImplException {
		super.setExprAsObject(value);
		startCount();
	}

	public void setExprAsText(String exprText) throws AttrImplException {
		super.setExprAsText(exprText);
		startCount();
		// try initialize variable of attr. context
		if (getExpr() != null && getExpr().isComplex()
				&& (exprText.indexOf("new ") == 0)) {
			apply(getExpr());
		}
	}

	public void setExprAsText(String exprText, boolean initialize) throws AttrImplException {
		super.setExprAsText(exprText);
		startCount();
		// try initialize variable of attr. context
		if (getExpr() != null && getExpr().isComplex()
				&& initialize && (exprText.indexOf("new ") == 0)) {
			apply(getExpr());
		}
	}
	
	public void setExprAsEvaluatedText(String exprText) {
		super.setExprAsEvaluatedText(exprText);
		startCount();
	}

	public HandlerExpr getExpr() {
		return super.getExpr();
	}

	/** Test, if the expression evaluates to a constant. */
	public boolean isDefinite() {
		return (getEvaluationResult() != null);
	}

	/**
	 * The mark m defines a graph context of this variable . The graph context
	 * can be LHS, RHS, PAC, NAC.
	 */
	public void setMark(int m) {
		this.mark = m;
	}

	public int getMark() {
		return this.mark;
	}

	/** Implementation of the only Observer interface method. */
	public void update(Observable o, Object arg) {
	}


	protected HandlerExpr getEvaluationResult() {
//		logPrintln(VerboseControl.logTrace,
//				"VarMember:\n->getEvaluationResult()");
		if (!isValid() || getExpr() == null)
			return null;
		HandlerExpr ex = getExpr().getCopy();
		try {
			if (ex.isConstant())
				ex.evaluate(getContext());
			return ex;
		} catch (AttrHandlerException ex1) {
			return null;
		} finally {
//			logPrintln(VerboseControl.logTrace,
//					"VarMember:\n<-getEvaluationResult()");
		}
	}

	protected void startCount() {
		this.refCnt = 1;
	}

	protected void incrementCount() {
		this.refCnt++;
	}

	protected void decrementCount() {
		if (this.refCnt > 0)
			this.refCnt--;
		if (this.refCnt == 0) 
			this.expression = null;	
	}

	public String toString() {
		return "VarMember " + getExprAsText() + " refCnt: " + this.refCnt + " IN: "
				+ this.isIn + " OUT: " + this.isOut + " hash: " + hashCode();
	}
}
/*
 * $Log: VarMember.java,v $
 * Revision 1.17  2010/11/28 22:11:36  olga
 * new method
 *
 * Revision 1.16  2010/09/23 08:14:08  olga
 * tuning
 *
 * Revision 1.15  2010/06/09 11:08:43  olga
 * tuning
 *
 * Revision 1.14  2010/04/28 15:15:39  olga
 * tuning
 *
 * Revision 1.13  2010/03/08 15:37:42  olga
 * code optimizing
 *
 * Revision 1.12  2009/11/23 08:54:04  olga
 * new map kind: OBJECT_FLOW_MAP
 *
 * Revision 1.11  2009/06/30 09:50:23  olga
 * agg.xt_basis.GraphObject: added: setObjectName(String), getObjectName()
 * agg.xt_basis.Node, Arc: changed: save, load the object name
 * agg.editor.impl.EdGraphObject: changed: String getTypeString() - contains object name if set
 *
 * workaround of Applicability of Rule Sequences and Object Flow
 *
 * Revision 1.10  2008/05/19 09:19:32  olga
 * Applicability of Rule Sequence - reworking
 *
 * Revision 1.9  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.8  2007/09/24 09:42:33  olga
 * AGG transformation engine tuning
 *
 * Revision 1.7  2007/09/10 13:05:19  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.6 2007/06/13 08:33:09 olga Update: V161
 * 
 * Revision 1.5 2007/05/07 07:59:30 olga CSP: extentions of CSP variables
 * concept
 * 
 * Revision 1.4 2006/12/13 13:32:58 enrico reimplemented code
 * 
 * Revision 1.3 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.2 2006/01/16 09:36:43 olga Extended attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.7 2004/12/20 14:53:47 olga Changes because of matching
 * optimisation.
 * 
 * Revision 1.6 2004/06/09 11:32:54 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.5 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.4 2004/01/22 17:51:50 olga ...
 * 
 * Revision 1.3 2003/12/18 16:25:49 olga Tests.
 * 
 * Revision 1.2 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:02 olga Imported sources
 * 
 * Revision 1.10 2000/06/15 06:56:03 shultzke toString() verbessert
 * 
 * Revision 1.9 2000/06/05 14:08:00 shultzke Debugausgaben fuer V1.0.0b
 * geloescht
 * 
 * Revision 1.8 2000/04/05 12:09:30 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.7 2000/03/15 08:18:28 olga Die Aenderungen betraffen nur
 * serialVersionUID in einigen Files, um alte Beispiele zu laden. Noch zu
 * klaeren od wir die alte Beispiele am Leben erhalten wollen.
 * 
 * Revision 1.6 2000/03/03 11:40:56 shultzke Einige Zeilen Code aus einenader
 * gezogen, damit ich besser debuggen kann
 * 
 * Revision 1.5 1999/07/26 10:17:41 shultzke kopieren der in-out parameter aus
 * loneTuple konstruktor nach VarMember.copy() verschoben
 */
