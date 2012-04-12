/**
 * 
 */
package agg.xt_basis;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrContext;
import agg.cons.Evaluable;
import agg.cons.Formula;
import agg.util.XMLHelper;

/**
 * @author olga
 *
 */
public class NestedApplCond extends OrdinaryMorphism implements Evaluable {

	NestedApplCond itsParent;
	
	String varTag = "";
	
//	private boolean valid;	
//	private int old_tick;
//	private boolean old_val;

	private boolean evaluable=true;

	final List<NestedApplCond> itsACs = new Vector<NestedApplCond>(0, 1);
	
	String formulaStr = "true";
	Formula itsFormula = new Formula(true);
	
	OrdinaryMorphism relatedMorph;
		
	public boolean forall, exist;
	
	
	public NestedApplCond(final Graph orig, final Graph img, final AttrContext ac) {
		super(orig, img, ac);		
	}
	
	public void setParent(NestedApplCond ac) {
		this.itsParent = ac;
	}
	
	public NestedApplCond getParent() {
		return this.itsParent;
	}
	
	public void setName(String n) {
		this.itsName = n;
		this.itsFormula.setName(n.concat(".formula"));
	}
	
	public String getName() {
		return this.itsName;
	}
	
	public void clear() {
		for (int i=0; i<this.itsACs.size(); i++) {		
			this.itsACs.get(i).clear();
		}		
		super.clear();
	}
	
	public void dispose() {
		this.clear();
		this.itsACs.clear();
		super.dispose();
	}
	
	
	/**
	 * Checks dangling edges of this and its nested application conditions.
	 * Returns true if no dangling edge exists, otherwise false.
	 */
	public boolean isValid() {
		if (this.isEnabled()) {
			final Iterator<Node> objects = this.itsOrig.getNodesSet().iterator();
			while (objects.hasNext()) {
				final Node x = objects.next();
				if (this.getImage(x) == null) {
					final Node y = (Node) this.getImage(x);
					if (y != null
							&& x.getNumberOfArcs() != y.getNumberOfArcs()) {	
						this.setErrorMsg(this.getName()+"  -  General AC failed (dangling edge)");
//						this.setErrorMsg(this.getName()+"  -  General AC failed (dangling edge)");
						return false;
					}
				}
			}
			for (int i=0; i<this.itsACs.size(); i++) {
				NestedApplCond ac = this.itsACs.get(i);
				if (!ac.isValid()) {
//					this.setErrorMsg(this.getName()+":  "+ac.getName()+"  -  General AC failed (dangling edge)");
					return false;
				}
					
			}
		}
		return true;
	}
	
	/**
	 * Destroys validation results of this general application condition.
	 * The validation results correspond to its rule and the current match.
	 * They are temporary and should be destroyed after the current match is checked.
	 */
	public void disposeResults() {
		this.relatedMorph = null;
		
		for (int i=0; i<this.itsACs.size(); i++) {		
			this.itsACs.get(i).disposeResults();
		}
		if (this.itsCoMorph != null) {
			this.itsCoMorph.dispose();
			this.itsCoMorph = null;
		}
	}
	
	
	/**
	 * Creates and adds a new (nested) application condition.
	 * Note, because a new morphism is empty and the this.target graph is not, 
	 * it is not a morphism in terms of theory, 
	 * which demands an application condition to be a total morphism.
	 * 
	 * @return an empty morphism <code>ac</code> with
	 *         <code>ac.getOriginal() == this.getTarget()</code>.
	 */
	public NestedApplCond createNestedAC() {
		final NestedApplCond ac = new NestedApplCond(
				this.itsImag, 
				BaseFactory.theFactory().createGraph(this.itsImag.getTypeSet()), 
				this.itsImag.getAttrContext());
		ac.setParent(this);
		this.itsACs.add(ac);
		AttrContext acContext = ac.getAttrContext();
		ac.getImage().setAttrContext(acContext);
		ac.getImage().setKind(GraphKind.AC);
		return ac;
	}
	
	public boolean addNestedAC(final NestedApplCond cond) {
		if (cond.getSource() == this.itsImag
				&& !this.itsACs.contains(cond)) {
			this.itsACs.add(cond);
			cond.setParent(this);
			return true;
		}
		
		return false;
	}
	
	public boolean addNestedAC(int indx, final NestedApplCond ac) {
		if (ac.getSource() == this.itsImag
				&& !this.itsACs.contains(ac)) {
			ac.getTarget().setKind(GraphKind.AC);
			if (indx >= 0 && indx < this.itsACs.size())
				this.itsACs.add(indx, ac);
			else
				this.itsACs.add(ac);
			ac.setParent(this);
			return true;
		}
		
		return false;
	}
	
	public void removeNestedAC(final NestedApplCond ac) {
		if (this.itsACs.remove(ac)) {
			this.itsFormula.patchOutEvaluable(ac, true);
			this.refreshFormula(new Vector<Evaluable>(this.getEnabledACs()));
//			ac.getImage().dispose();
		}
	}
	
	
	public int getSizeOfNestedAC() {
		return this.itsACs.size();
	}
	
	public List<NestedApplCond> getNestedACs() {
		return this.itsACs;
	}
	
	public List<Evaluable> getEnabledGeneralACsAsEvaluable() {
		List<Evaluable> list = new Vector<Evaluable>(this.itsACs.size());
		for (int i = 0; i < this.itsACs.size(); i++) {
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled())
				list.add(ac);
		}
		return list;
	}
	
	public NestedApplCond getNestedACAt(int indx) {
		if (indx >= 0 && indx < this.itsACs.size())
			return this.itsACs.get(indx);
		
		return null;
	}
	
	public boolean setDefaultFormulaTrue() {
		final List<Evaluable> vars = new Vector<Evaluable>(this.itsACs.size());
		
		if (this.itsACs.size() == 0) {
			this.formulaStr = "true";
			return true;
		}		
		
		String tmp = "";
		int indx = -1;
		for (int i=0; i<this.itsACs.size(); i++) {
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled()) {
				indx++;
				vars.add(ac);								
				if (indx == 0)
					tmp = tmp.concat(String.valueOf(indx+1));
				else
					tmp = tmp.concat("&").concat(String.valueOf(indx+1));
//				ac.setVarTagInFormula(String.valueOf(indx+1));
			}
		}
		if ("".equals(tmp)) {
			this.formulaStr = "true";
			return true;
		}
		
		if (this.itsFormula.setFormula(vars, tmp)) {
			this.formulaStr = this.itsFormula.getAsString(vars);
//			System.out.println("NestedApplCond: "+this.getName()+"  default formula: "+this.formulaStr);
			return true;
		}
		return false;
	}
	
	public boolean setDefaultFormulaFalse() {
		final List<Evaluable> vars = new Vector<Evaluable>(this.itsACs.size());
		
		if (this.itsACs.size() == 0) {
			this.formulaStr = "true";
			return true;
		}		
		
		String tmp = "";
		int indx = -1;
		for (int i=0; i<this.itsACs.size(); i++) {
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled()) {
				indx++;
				vars.add(ac);								
				if (indx == 0)
					tmp = tmp.concat(String.valueOf(indx+1));
				else
					tmp = tmp.concat("&").concat(String.valueOf(indx+1));
//				ac.setVarTagInFormula(String.valueOf(indx+1));
			}
		}
		if ("".equals(tmp)) {
			this.formulaStr = "true";
			return true;
		} else {
			tmp ="!(".concat(tmp).concat(")");
		}
		
		if (this.itsFormula.setFormula(vars, tmp)) {
			this.formulaStr = this.itsFormula.getAsString(vars);
//			System.out.println("NestedApplCond: "+this.getName()+"  default formula: "+this.formulaStr);
			return true;
		}
		return false;
	}
	
	public boolean refreshFormula(final List<Evaluable> vars) {
		String bnf = this.formulaStr;
		if (this.itsFormula.setFormula(vars, bnf)) {
			this.formulaStr = this.itsFormula.getAsString(vars);
			this.setTextualComment("Formula: ".concat(this.formulaStr));
			return true;
		} else {
			this.formulaStr = "true";
		}
		return false;
	}
	
	public boolean setFormula(String formStr) {		
//		return this.setFormula(formStr, this.itsACs);
		return this.setFormula(formStr, this.getEnabledACs());
	}
	
	/**
	 * Set a boolean formula represented by the specified string
	 * above the nested application conditions.
	 * @param bnf
	 */
	public boolean setFormula(String formStr, final List<NestedApplCond> list) {
		if (formStr.equals("true")) {
			this.formulaStr = formStr;
			return true;
		}
		else if (formStr.equals("false")) {
			return this.setDefaultFormulaFalse();
		}
		
		int n = 0;	
		final List<Evaluable> vars = new Vector<Evaluable>(n);
		for (int i=0; i<list.size(); i++) {	
			NestedApplCond ac = list.get(i);
			if (ac.isEnabled()) {
				vars.add(ac);
				n++;
			}
		}
		
		if (n == 0) {
			this.formulaStr = "true";
			return true;
		}
					
		if (this.itsFormula.setFormula(vars, formStr)) {
			this.formulaStr = this.itsFormula.getAsString(vars);
			this.setTextualComment("Formula: ".concat(this.formulaStr));
			interpretForallOperator();
//			System.out.println("NestedApplCond: "+this.getName()+"   formula: "+this.formulaStr);
			return true;
		} else
			return false;
	}

	public void setFormula(Formula f) {
		this.itsFormula = f;
		this.formulaStr = this.itsFormula.getAsString(this.getEnabledGeneralACsAsEvaluable());
		this.setTextualComment("Formula: ".concat(this.formulaStr));
	}
	
	private void interpretForallOperator() {
		if (this.formulaStr.contains("A")) 
			this.forall = true;
		else
			this.forall = false;
	}
	
	
	public void setRelatedMorphism(final OrdinaryMorphism morph) {
		this.relatedMorph = morph;
	}
	
	public OrdinaryMorphism getRelatedMorphism() {
		return this.relatedMorph;
	}
			
	public boolean evaluate(final Graph g) {
		if (!this.enabled) {
			return true;
		} else if (this.relatedMorph == null || this.relatedMorph.getTarget() != g) {
			return false;
		}
		
		PACStarMorphism comorph = MatchHelper.createNestedACstar(this, this.relatedMorph);		
		boolean result = 
			(MatchHelper.checkGACStar(comorph, this, this.relatedMorph, false) != null);
		this.disposeResults();
		
		return result;
	}
	
	public void setCoMorphism(final OrdinaryMorphism aCoMorph) {
		this.itsCoMorph = aCoMorph;
	}
	
	public boolean eval(Object o) {
//		return eval(o, -1);
		return evaluate((Graph) o);
	}

	public boolean eval(Object o, int tick) {
		return evaluate((Graph) o);

//		if (tick != -1 && tick == old_tick) {
//			return old_val;
//		}
//		old_tick = tick;
//		old_val = evaluate((Graph) o);
//		return old_val;
	}

	public boolean eval(Object o, boolean negation) {
//		return eval(o);
		return evaluate((Graph) o);
	}

	public boolean eval(Object o, int tick, boolean negation) {
//		return eval(o);
		return evaluate((Graph) o);
	}
	
	public boolean isEvaluable() {
		return this.evaluable;
	}
	
	// not used now!
	/*
	private void setEvaluable(OrdinaryMorphism m, Graph g) {
		this.evaluable = true;
		
		VarTuple vars = (VarTuple) m.getAttrContext().getVariables();
		Vector<String> varnames = g.getVariableNamesOfAttributes();
		CondTuple conds = (CondTuple) m.getAttrContext().getConditions();
		
		for (int i = 0; i < conds.getSize() && this.evaluable; i++) {
			CondMember cond = conds.getCondMemberAt(i);
			if (!cond.isEvaluable(vars)) {
				Vector<String> condVars = cond.getAllVariables();
				for (int j = 0; j < condVars.size(); j++) {
					String n = condVars.elementAt(j);
					VarMember var = vars.getVarMemberAt(n);
					if ((!var.isSet() && varnames.contains(n))
//							|| (!var.isSet() && !varnames.contains(n))
							)
						this.evaluable = false;
				}
			}
		}
		
		// check constants
		if (this.evaluable)
			doCheckConstantOfImage(m, g.getNodesSet().iterator());
		if (this.evaluable)
			doCheckConstantOfImage(m, g.getArcsSet().iterator());		
	}
	
	private void doCheckConstantOfImage(
			final OrdinaryMorphism m, 
			final Iterator<?> elems) {
		
		while (elems.hasNext()) {
			GraphObject o = (GraphObject) elems.next();
			if (o.getAttribute() == null)
				continue;
			GraphObject img = m.getImage(o);
			ValueTuple val = (ValueTuple) o.getAttribute();
			for (int i = 0; i < val.getSize(); i++) {
				ValueMember valm = val.getValueMemberAt(i);
				if (valm.isSet() && valm.getExpr().isConstant()) {
					if (img != null) {
						ValueTuple valImg = (ValueTuple) img.getAttribute();
						ValueMember valmImg = valImg.getValueMemberAt(i);
						if (!(valmImg.isSet() && valmImg.getExpr().equals(
								valm.getExpr()))) {
							this.evaluable = false;
						}
					}
				}
			}
		}
	}
*/
	
	
	public String getFormulaStr() {
		return this.formulaStr;
	}
	
	public String getFormulaText() {
		return this.formulaStr;
	}
		
	public Formula getFormula() {
		return this.itsFormula;
	}
	
	public boolean evalFormulaAtGraph(final Object g) {
		return evalFormula((Graph ) g);
	}
	
	public boolean evalFormula(final Graph g) {
		if (this.itsACs.size() != 0) {
			if (this.itsCoMorph != null) {
				for (int i=0; i<this.itsACs.size(); i++) {
					NestedApplCond ac = this.itsACs.get(i);
					ac.setRelatedMorphism(this.itsCoMorph);
				}
	
				if (this.formulaStr.equals("true")) 
					this.setDefaultFormulaTrue();
				else if (this.formulaStr.equals("false")) 
					this.setDefaultFormulaFalse();
				
				return this.itsFormula.eval(g);
			}
			return false;
		}
		return true;
	}
	
	public List<NestedApplCond> getEnabledNestedACs() {
		final List<NestedApplCond> vars = new Vector<NestedApplCond>();
		for (int i=0; i<this.itsACs.size(); i++) {		
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled())
				vars.add(ac);
			vars.addAll(ac.getEnabledNestedACs());
		}
		return vars;
	}
	
	public List<NestedApplCond> getEnabledACs() {
		final List<NestedApplCond> vars = new Vector<NestedApplCond>();
		for (int i=0; i<this.itsACs.size(); i++) {		
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled())
				vars.add(ac);
		}
		return vars;
	}
	
	public List<String> getNameOfEnabledACs() {
		final List<String> vars = new Vector<String>();
		for (int i=0; i<this.itsACs.size(); i++) {		
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled())
				vars.add(ac.getName());
		}
		return vars;
	}
	
	public List<String> getNameOfEnabledNestedACs() {
		final List<String> vars = new Vector<String>();
		for (int i=0; i<this.itsACs.size(); i++) {		
			NestedApplCond ac = this.itsACs.get(i);
			if (ac.isEnabled())
				vars.add(ac.getName());
			vars.addAll(ac.getNameOfEnabledNestedACs());
		}
		return vars;
	}
	
	public List<String> getNameOfNestedACs() {
		final List<String> vars = new Vector<String>();
		for (int i=0; i<this.itsACs.size(); i++) {		
			NestedApplCond ac = this.itsACs.get(i);
			vars.add(ac.getName());
			vars.addAll(ac.getNameOfEnabledNestedACs());
		}
		return vars;
	}
	
	public void setVarTagInFormula(String n) {
		this.varTag = n;
	}
	
	public String getVarTagInFormula() {
		return this.varTag;
	}

	/* (non-Javadoc)
	 * @see agg.util.XMLObject#XreadObject(agg.util.XMLHelper)
	 */
	public void readNestedApplConds(XMLHelper h) {		
		if (h.readSubTag("ApplCondition")) {			
			while (h.readSubTag("NestedAC")) {
				String attrStr = h.readAttr("formula");
				if (!"".equals(attrStr))
					this.formulaStr = attrStr;
				else
					this.formulaStr = "true";
				this.setTextualComment("Formula: ".concat(this.formulaStr));
				
				boolean acEnabled = true;
				Object acattr_enabled = h.readAttr("enabled");
				if ((acattr_enabled != null)
						&& ((String) acattr_enabled).equals("false"))
					acEnabled = false;

				NestedApplCond ac = createNestedAC();
				ac.getTarget().setHelpInfo(this.getName());
				
				h.getObject("", ac.getTarget(), true);
				ac.readMorphism(h);
				
				ac.readNestedApplConds(h);
				
				h.close();
				ac.setEnabled(acEnabled);
				ac.getTarget().setHelpInfo("");
			}
			if (h.readSubTag("AttrCondition")) {
				AttrConditionTuple condt = getAttrContext().getConditions();
				if (condt != null)
					h.enrichObject(condt);
				h.close();
			}
			h.close();
			
			this.setFormula(this.formulaStr);
		}
	}

	/* (non-Javadoc)
	 * @see agg.util.XMLObject#XwriteObject(agg.util.XMLHelper)
	 */
	public void writeNestedApplConds(XMLHelper h) {
		// Attr context conditions
		AttrConditionTuple condt = this.getAttrContext().getConditions();
		int num = condt.getNumberOfEntries();
		// nested ACs
		Enumeration<OrdinaryMorphism> nested = (new Vector<OrdinaryMorphism>(this.itsACs)).elements();
		if (nested.hasMoreElements()) {
			h.openSubTag("ApplCondition");
			while (nested.hasMoreElements()) {
				OrdinaryMorphism m = nested.nextElement();
				m.getTarget().setKind(GraphKind.AC);
				h.openSubTag("NestedAC");
				
				if (!"".equals(this.formulaStr))
					h.addAttr("formula", this.formulaStr);
				if (!m.isEnabled())
					h.addAttr("enabled", "false");
				
				h.addObject("", m.getTarget(), true);
				m.writeMorphism(h);
				
				((NestedApplCond) m).writeNestedApplConds(h);
				
				h.close();
			}
			
			// Attr context conditions
			if (num > 0) {
				h.openSubTag("AttrCondition");
				h.addObject("", condt, true);
				h.close();
			}
			h.close();
		}		
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#evalForall(java.lang.Object, int, boolean)
	 */
	public boolean evalForall(Object o, int tick) {
		// TODO Auto-generated method stub
		return false;
	}
	
	
}
