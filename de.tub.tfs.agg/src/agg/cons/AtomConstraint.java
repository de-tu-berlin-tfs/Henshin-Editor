/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.cons;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;
import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrContext;
import agg.attribute.AttrInstanceMember;
import agg.attribute.AttrMapping;
import agg.attribute.AttrVariableTuple;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.TupleMapping;
import agg.attribute.impl.ContextView;

import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.Arc;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Type;

public class AtomConstraint extends OrdinaryMorphism 
implements Evaluable, XMLObject {

	private boolean valid;

	private int old_tick;

	private boolean old_val;

	private boolean evaluable;

	private String atomicName = "";

	private AtomConstraint parent;
	
	final private Vector<AtomConstraint> conclusions = new Vector<AtomConstraint>();
		
	private int indxOfValidConclusion = -1;

	private MorphCompletionStrategy strategy;

	final private Vector<GraphObject> failedObjs = new Vector<GraphObject>();
	
	/**
	 * Creates the parent object of a new atomic graph constraint with one conclusion.
	 * The parent is an empty morphism with premise as source and target
	 * and it is not visible and is not used for any edit and evaluation.
	 * The first conclusion morphism consists of premise and conclusion.
	 * It is available from the list of conclusions by <code>getConsclusions()</code>
	 * or by <code>getConsclusion(0)</code>.
	 * This constructor must be used only once. To add a new conclusion to this 
	 * atomic constraint the method
	 * <code>createNextConclusion(Graph)</code> must be use.
	 * 
	 * @param premise
	 * @param conclusion
	 * @param context
	 * @param name
	 */
	public AtomConstraint(final Graph premise, final Graph conclusion, 
			final AttrContext context,
			final String name) {
				
//		super(premise, conclusion, context);
		super(premise, premise, context);
		
		// the parent instance, hidden
		this.parent = this;		
		getOriginal().setAttrContext(getAttrManager().newLeftContext(context));
		getOriginal().setKind(GraphKind.PREMISE);	
				
		this.old_tick = -1;
		this.old_val = false;
		
		if ((name != null) && !name.equals("")) {
			this.atomicName = name;
			this.itsName = name;
		}
		
		// the first conclusion
		this.createNextConclusion(conclusion);
	}

	/*
	 * Creates next conclusion of already existent premise and given conclusion
	 * for the given parent atomic graph constraint.
	 * 
	 */
	private AtomConstraint(final Graph premise, final Graph conclusion, final AttrContext context,
			final String name, final AtomConstraint parent) {
		
		super(premise, conclusion, context);

		this.parent = parent;
				
		getImage().setAttrContext(getAttrManager().newRightContext(context));
		getImage().setKind(GraphKind.CONCLUSION);
		
		this.old_tick = -1;
		this.old_val = false;

		if ((name != null) && !name.equals("")) {
			this.atomicName = name;	
		}
	}
	
	public void dispose() {
		AtomConstraint atom = null;
		int i = this.conclusions.size()-1;
		while (!this.conclusions.isEmpty()) {
			atom = this.conclusions.remove(i);
			atom.dispose(false, true);
			i = this.conclusions.size()-1;
		}
		this.dispose(true, false);
	}
	
	public AtomConstraint getParent() {
		return this.parent;
	}
	
	public boolean isElement(Graph g) {
		for (int j = 0; j < this.conclusions.size(); j++) {
			final AtomConstraint atom = this.conclusions.elementAt(j);
			if (atom.getSource() == g || atom.getTarget() == g)
				return true;
		}
		return false;
	}

	public boolean isValid() {
		this.failedObjs.clear();
		
		this.valid = true;
		
		for (int j = 0; j < this.conclusions.size() && this.valid; j++) {
			AtomConstraint concl = this.conclusions.elementAt(j);
			this.valid = doIsValidCheck(concl);
		}
		
		return this.valid;
	}

	private boolean doIsValidCheck(final AtomConstraint concl) {
		// complete variables of the attr context:
		// that means: add used variable of the source graph (Premise)
		// to the attr context if it is not already contained.
		VarTuple lhsVars = (VarTuple) concl.getTarget().getAttrContext()
				.getVariables();
		concl.addToAttrContext(lhsVars);		

		if (!concl.isInjective() || !concl.isTotal()) {
			return false;
		}
		
		if (!doIsValidElemCheck(concl, concl.getOriginal().getNodesSet().iterator()))
			return false;
		
		if (!doIsValidElemCheck(concl, concl.getOriginal().getArcsSet().iterator()))
			return false;
		
		concl.markAttrConditions();
		// check attr context
		CondTuple conds = (CondTuple) concl.getAttrContext().getConditions();
		for (int i = 0; i < conds.getSize(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
			if (!cond.isValid()) {
				return false;
			}
		}
		return true;
	}
	
	private boolean doIsValidElemCheck(
			final AtomConstraint concl,
			final Iterator<?> elems) {
		/*
		 * elems are objects of the graph concl.getSource() 
		 * All attributes in elems may either be empty, constant,
		 * or a simple variable. Furthermore it's necessary, that if the
		 * source attr. contained a variable, that this attribute contains
		 * the same variable in the target node (i.e. the attribute part of
		 * the morphism is the identity).
		 */		
		while (elems.hasNext()) {
			GraphObject orig = (GraphObject) elems.next();
			if (orig.getAttribute() == null)
				continue;
			ValueTuple val = (ValueTuple) orig.getAttribute();			
			ValueTuple img_val = (ValueTuple) concl.getImage(orig).getAttribute();
			int n = val.getNumberOfEntries();
			for (int i = 0; i < n; i++) {
				if (val.isValueSetAt(i)) {
					ValueMember value = val.getValueMemberAt(i);
					HandlerExpr expr1 = value.getExpr();
					value = img_val.getValueMemberAt(i);
					HandlerExpr expr2 = value.getExpr();
					if (expr1 == expr2) // Both are unset or the similar
						continue;
					/*
					 * For reasons beyond me the equals() method fails for
					 * e.g. "x" and "x+1" thinking they are the same. Well,
					 * the reason is, that their AST.toString() method both
					 * returns "Expression", which is obviously equal. So I
					 * need to test the strings.
					 */
					if ((expr1 != null && expr2 != null)
							&& !expr1.toString().equals(expr2.toString())) {
						return false;
					}
					if (expr1 == null || expr1.isConstant()
							|| expr1.isVariable())
						continue;
					
					return false;
				} 
			} 
		} 
		return true;
	}
	
	public boolean isEvaluable() {
		return this.evaluable;
	}

	public void setMorphismCompletionStrategy(MorphCompletionStrategy s) {
		this.strategy = s;
		
		for (int i=0; i<this.conclusions.size(); i++) {
			this.conclusions.get(i).setMorphismCompletionStrategy(this.strategy);
		}
	}

	
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

	public boolean eval(java.lang.Object o) {
		return eval(o, -1);
	}

	public boolean eval(java.lang.Object o, int tick) {
		if (tick != -1 && tick == this.old_tick) {
			return this.old_val;
		}
		this.old_tick = tick;
		this.old_val = eval((Graph) o);
		return this.old_val;
	}

	public boolean eval(java.lang.Object o, boolean negation) {
		return eval(o, -1, negation);
	}

	public boolean eval(java.lang.Object o, int tick, boolean negation) {
		// System.out.println("AC.eval (negation) "+" tick : "+tick+ " this.old_tick:
		// "+this.old_tick);
		if (tick != -1 && tick == this.old_tick) {
			// System.out.println("AC.eval (negation) return this.old_val");
			return this.old_val;
		}
		this.old_tick = tick;
		this.old_val = eval((Graph) o, negation);
		return this.old_val;
	}

	private boolean eval(Graph g) {
		return eval(g, false);
	}

	private boolean eval(Graph g, boolean negation) {
//		System.out.println("AtomConstraint.eval(Graph):: "+getAtomicName()+"  negation: "+negation);

		this.failedObjs.clear();
		
		if (!this.valid || this.conclusions.isEmpty()) {
			return false;
		}
		
		Graph p = this.conclusions.get(0).getOriginal();
		if (!p.isEmpty() && g.isEmpty())
			return true;

		// this.strategy.showProperties();
		// for Premise
		final Completion_InjCSP strategy1 = new Completion_InjCSP(false); // do not randomize domain
		// for Conclusion
		final Completion_InjCSP strategy2 = new Completion_InjCSP(false); // do not randomize domain

		this.indxOfValidConclusion = -1;
		boolean result = true;
		
		BaseFactory bf = BaseFactory.theFactory();
		((AttrTupleManager) agg.attribute.impl.AttrTupleManager
				.getDefaultManager()).setVariableContext(false);
		this.adoptEntriesWhereEmpty();

		// do morphism s: P --> G as matchP
		AtomConstraint conclusion0 = this.conclusions.elementAt(0);
		OrdinaryMorphism s = bf.createMorphism(conclusion0.getOriginal(), g,
				true);
		OrdinaryMorphism matchP = bf.createMatchfromMorph(s, conclusion0
				.getAttrContext());
		
//		System.out.println("matchP : P --s--> G: "+matchP+" isTotal:  "+matchP.isTotal());
		// ((VarTuple)matchP.getAttrContext().getVariables()).showVariables();

		if ((matchP.getImage().getVariableNamesOfAttributes().size() != 0))
			((ContextView) matchP.getAttrContext()).setVariableContext(true);

		OrdinaryMorphism t = null, t2 = null, t2match = null;

		// For each morphism P --s--> G ...
		matchP.setCompletionStrategy(strategy1, true);
		
		while (matchP.nextCompletionWithConstantsChecking()) {
			// test output
//			this.showMorphismData(matchP);

			setEvaluable(matchP, matchP.getSource());
			result = false;

			/* try create t: C --> G of each conclusion */
			boolean allConclusionsOK = false;
			for (int i = 0; i < this.conclusions.size(); i++) {
				boolean conclusionOK = false;
				AtomConstraint atom = this.conclusions.elementAt(i);
				
				if (t == null) {
					t = matchP.completeDiagram(atom);
					if (t != null)
						t.setCompletionStrategy(strategy2, true);
				} else {
					// clear and reinit source graph of the t morphism
					t.clear();
					t.setSource(atom.getImage());
					t.getCompletionStrategy().initialize(t);
					if (!matchP.completeDiagram(atom, t)) {
						t = null;
					}
				}
				
				// there must be a C--t-->G, such that t o atom = s
				if (t != null) {
					t.adaptAttrContextValues(matchP.getAttrContext());
					
					Vector<String> varNames = t.getImage()
							.getVariableNamesOfAttributes();
					if ((varNames.size() != 0)) {
						((ContextView) t.getAttrContext())
								.setVariableContext(true);
					}
					// test output
//					this.showMorphismData(t);
//					((VarTuple) t.getAttrContext().getVariables()).showVariables();

					// now t is constructed on the part identically to s
					// so we only need to try to do it total if needed
					if (t.isTotal() || t.nextCompletionWithConstantsChecking()) {
						conclusionOK = true;
						if (t2 == null) {
							t2 = matchP.completeDiagram(atom);
						} else {
							// clear and reinit source graph of the t2 morphism
							t2.clear();
							t2.setSource(atom.getImage());
							if (!matchP.completeDiagram(atom, t2))
								t2 = null;
						}
						
						if (t2 != null) {
							// use attr. context of atom							
							if (t2match == null) {
								t2match = bf.createMatchfromMorph(t2, atom
										.getAttrContext());
								if (t2match != null) {
									t2match.setCompletionStrategy(strategy2,
											true);
								}
							} else {
								t2match.clear();
								if (bf.createMatchfromMorph(t2, t2match, atom
										.getAttrContext())) {
									t2match.getCompletionStrategy().initialize(
											t2match);
								} else
									t2match = null;
							}
							// System.out.println("t2match: "+t2match);

							if (t2match != null) {
								if ((t2match.getImage()
										.getVariableNamesOfAttributes().size() != 0))
									((ContextView) t2match.getAttrContext())
											.setVariableContext(true);
								
								AttrContext ac1 = t2match.getAttrContext();
								for (int k = 0; k < ac1.getConditions().getNumberOfEntries(); k++) {
									AttrInstanceMember am = (AttrInstanceMember) ac1
												.getConditions().getMemberAt(k);
									((CondMember) am).setMark(CondMember.LHS);
								}
								
								if (t2match.isTotal()
										|| t2match.nextCompletionWithConstantsChecking()) {
																		
									for (int k = 0;
											 k < ac1.getConditions().getNumberOfEntries(); 
											 k++) {
										AttrInstanceMember 
										am = (AttrInstanceMember) ac1.getConditions().getMemberAt(k);
										
										if (((CondMember) am).isDefinite()
												&& !((CondMember) am).isTrue()) {
											conclusionOK = false;
											break;
										}
									}
//									System.out.println("conclusionOK: "+conclusionOK);
									if (conclusionOK)
										this.indxOfValidConclusion = i;
									setEvaluable(t2match, t2match.getSource());
								} else
									conclusionOK = false;
							} else
								conclusionOK = false;

						} // if (t2 != null
						else
							conclusionOK = false;
					} // else if (t.nextCompletion())
					else
						conclusionOK = false;

				} // if(t != null
				else
					conclusionOK = false;
				
				allConclusionsOK = allConclusionsOK || conclusionOK;
				if (conclusionOK && !negation)
					break;
			} // for(int i=0; i<this.conclusions
			result = allConclusionsOK;
			if (!result) {
				fillFailedObjects(matchP);
				break;
			}
			// matchP.setCompletionStrategy(strategy1);
		} // while (matchP.nextCompletion
		
		unsetAllTransientAttrValuesOfOverlapGrah(matchP);
		matchP.dispose();
		matchP = null;
		bf.destroyMorphism(t);
		bf.destroyMorphism(t2);
		bf.destroyMorphism(t2match);
		t = null;
		t2 = null;
		t2match = null;
		
		return result;
	}

	private void fillFailedObjects(OrdinaryMorphism matchP) {
		Enumeration<GraphObject> codom = matchP.getCodomain();
		while (codom.hasMoreElements()) {
			this.failedObjs.add(codom.nextElement());
		}
	}
	
	public Enumeration<GraphObject> getFailedGraphObjects() {
		return this.failedObjs.elements();
	}
		
	public AtomConstraint createNextConclusion(final Graph img) {
		final AtomConstraint conclusion = new AtomConstraint(this.getOriginal(), img,
				agg.attribute.impl.AttrTupleManager.getDefaultManager()
						.newContext(AttrMapping.PLAIN_MAP), this.atomicName, this);
		
		// enrich attr context by variables from source graph	
		VarTuple lhsVars = (VarTuple) conclusion.getSource()
										.getAttrContext().getVariables();
		conclusion.addToAttrContext(lhsVars);
				
		conclusion.setName("Conclusion"+this.conclusions.size());
		
		this.conclusions.addElement(conclusion);
		
		return conclusion;
	}	
	
	public boolean destroyConclusion(final AtomConstraint conclusion) {
		if (this.conclusions.size() <= 1) {
			return false;
		} else if (this.conclusions.contains(conclusion)) {
			this.conclusions.removeElement(conclusion);
			conclusion.dispose();
			return true;
		} else		
			return false;
	}
	
	public boolean removeConclusion(AtomConstraint conclusion) {
		if (this.conclusions.size() <= 1) {
			return false;
		} else if (this.conclusions.contains(conclusion)) {
			this.conclusions.remove(conclusion);
			return true;
		} else
			return false;
	}
	
	public Enumeration<AtomConstraint> getConclusions() {
		return this.conclusions.elements();
	}

	public int getConclusionsSize() {
		return this.conclusions.size();
	}
	
	public AtomConstraint getConclusion(int indx) {
		if ((indx >= 0) && (indx < this.conclusions.size()))
			return this.conclusions.elementAt(indx);
		
		return null;
	}

	public AtomConstraint getValidConclusion() {
		if ((this.indxOfValidConclusion >= 0)
				&& (this.indxOfValidConclusion < this.conclusions.size()))
			return this.conclusions.elementAt(this.indxOfValidConclusion);
		
		return null;
	}

	public void setAtomicName(String n) {
		this.atomicName = n;
		if (this.parent == this)
			this.itsName = n;
	}

	public String getAtomicName() {
		return this.atomicName;
	}

	public boolean compareTo(AtomConstraint a) {
		if (!this.atomicName.equals(a.getAtomicName()))
			return false;
		
		Enumeration<AtomConstraint> e = a.getConclusions();
		Vector<AtomConstraint> another = new Vector<AtomConstraint>(10);
		while (e.hasMoreElements()) {
			another.add(e.nextElement());
		}
		if (this.conclusions.size() != another.size())
			return false;
		
		for (int i = 0; i < this.conclusions.size(); i++) {
			AtomConstraint c = this.conclusions.elementAt(i);
			for (int j = another.size() - 1; j >= 0; j--) {
				AtomConstraint c1 = another.elementAt(j);
				if (((OrdinaryMorphism)c).compareTo(c1)) {
					another.remove(c1);
					break;
				}
			}
		}
		if (another.size() != 0)
			return false;
		return true;
	}

	public void adoptEntriesWhereEmpty() {
		Enumeration<AtomConstraint> concls = this.getConclusions();
		while (concls.hasMoreElements()) {
			OrdinaryMorphism morph = concls.nextElement();
			Enumeration<GraphObject> e = morph.getDomain();
			while (e.hasMoreElements()) {
				GraphObject obj = e.nextElement();
				if (obj.getAttribute() == null)
					continue;

				GraphObject img = morph.getImage(obj);
				ContextView context = (ContextView) morph.getAttrContext();
				Vector<TupleMapping> mappings = context.getMappingsToTarget((ValueTuple) img
						.getAttribute());
				if (mappings != null) {
					mappings.elementAt(0)
							.adoptEntriesWhereEmpty((ValueTuple) obj
									.getAttribute(), (ValueTuple) img
									.getAttribute());
				}
			}
		}
	}

	public void createAttrInstanceWhereNeeded() {
		Enumeration<AtomConstraint> concls = this.getConclusions();
		// first conclusion: handle source and target graphs
		if (concls.hasMoreElements()) {
			OrdinaryMorphism morph = concls.nextElement();
			morph.getSource().createAttrInstanceWhereNeeded();
			morph.getTarget().createAttrInstanceWhereNeeded();
		}
		// all next conclusion: handle target graph
		while (concls.hasMoreElements()) {
			concls.nextElement().getTarget().createAttrInstanceWhereNeeded();
		}
	}
	
	public void createAttrInstanceOfTypeWhereNeeded(final Type t) {
		Enumeration<AtomConstraint> concls = this.getConclusions();
		// first conclusion: handle source and target graphs
		if (concls.hasMoreElements()) {
			OrdinaryMorphism morph = concls.nextElement();
			morph.getSource().createAttrInstanceOfTypeWhereNeeded(t);
			morph.getTarget().createAttrInstanceOfTypeWhereNeeded(t);
		}
		// all next conclusion: handle target graph
		while (concls.hasMoreElements()) {
			concls.nextElement().getTarget().createAttrInstanceOfTypeWhereNeeded(t);
		}
	}
	
	private void unsetAllTransientAttrValuesOfOverlapGrah(OrdinaryMorphism m) {
		// remove all transient variables in m.getImage()
		
		doUnsetAllTransientAttrValuesOfOverlapGrah(m.getImage().getNodesSet().iterator());
		doUnsetAllTransientAttrValuesOfOverlapGrah(m.getImage().getArcsSet().iterator());
		
	}

	private void doUnsetAllTransientAttrValuesOfOverlapGrah(final Iterator<?> elems) {
		// remove all transient variables
		while (elems.hasNext()) {
			GraphObject obj = (GraphObject) elems.next();
			if (obj.getAttribute() == null)
				continue;
			ValueTuple value = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < value.getNumberOfEntries(); i++) {
				ValueMember valuem = value.getValueMemberAt(i);
				if ((valuem.getExpr() != null) && valuem.getExpr().isVariable()
						&& valuem.isTransient()) {
					valuem.setExpr(null);
				}
			}
		}
	}
	
	public void refreshAttributed() {
		Enumeration<AtomConstraint> concls = this.getConclusions();
		// first conclusion: handle source and target graphs
		if (concls.hasMoreElements()) {
			OrdinaryMorphism morph = concls.nextElement();
			morph.getSource().refreshAttributed();
			morph.getTarget().refreshAttributed();
		}
		// all next conclusion: handle target graph
		while (concls.hasMoreElements()) {
			concls.nextElement().getTarget().refreshAttributed();
		}
	}
	
	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		super.trimToSize();
		for (int i = 0; i < this.conclusions.size(); i++) {
			this.conclusions.get(i).trimToSize();
		}
		this.conclusions.trimToSize();
		this.failedObjs.trimToSize();
	}
	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("Graphconstraint_Atomic", this);
		h.addAttr("name", getAtomicName());
		if (this.conclusions.size() > 0) {
			h.openSubTag("Premise");
			AtomConstraint atom = this.conclusions.elementAt(0);
			h.addObjectSub(atom.getOriginal());
			h.close();
			for (int i = 0; i < this.conclusions.size(); i++) {
				h.openSubTag("Conclusion");
				atom = this.conclusions.elementAt(i);
				h.addObjectSub(atom.getImage());
				atom.writeMorphism(h);

				AttrConditionTuple condt = atom.getAttrContext().getConditions();
				int num = condt.getNumberOfEntries();
				if (num > 0) {
					h.openSubTag("AttrCondition");
					h.addObject("", condt, true);
					h.close();
				}

				h.close();
			}
		}
		h.close();
	}

	public void XreadObject(XMLHelper h) {
		if (h.isTag("Graphconstraint_Atomic", this)) {
			setAtomicName(h.readAttr("name"));
			
			if (h.readSubTag("Premise")) {
				Graph orig = (Graph) h.getObject("", this.itsOrig, true);
				orig.setName("Premise of " + this.atomicName);
				h.close();			

				this.parent = this;
				
				// clear this.conclusions because one empty conclusion is created by default!
				this.conclusions.clear();
				
				Enumeration<?> en = h.getEnumeration("", null, true, "Conclusion");
				while (en.hasMoreElements()) {
					h.peekElement(en.nextElement());
					
					Graph target = BaseFactory.theFactory().createGraph(getSource().getTypeSet());
					AtomConstraint concl = createNextConclusion(target);
					h.getObject("", target, true);
					concl.readMorphism(h);
					target.setName("Conclusion of " + this.atomicName);
					if (h.readSubTag("AttrCondition")) {
						AttrConditionTuple
						condt = concl.getAttrContext().getConditions();
						if (condt != null)
							h.enrichObject(condt);
						h.close();
					}

					h.close();
				}

			} else {
				h.getObject("", getSource(), true);
				h.getObject("", getTarget(), true);
				readMorphism(h);
			}
			h.close();
		}
	}

	private void markAttrConditions() {
		markUsedVariables();
		
		final AttrVariableTuple avt = this.itsAttrContext.getVariables();
//		((VarTuple) avt).showVariables();
		final AttrConditionTuple act = this.itsAttrContext.getConditions();
		// check and mark the attr. conditions
		for (int k = 0; k < ((CondTuple) act).getSize(); k++) {
			final CondMember cm = ((CondTuple) act).getCondMemberAt(k);	
//			System.out.println(cm);
			cm.setMark(CondMember.LHS);
			final Vector<String> vars = cm.getAllVariables();
			if (!vars.isEmpty()) {
				for (int i=0; i<vars.size(); i++) {					
					final VarMember var = avt.getVarMemberAt(vars.get(i));
					if (var != null) {							
						if (var.getMark() == VarMember.RHS) {
							cm.setMark(CondMember.RHS);
							break;
						}					
					}
				}
			}
//			System.out.println(cm.getMark());
		}
	}
	
	private void markUsedVariables() {
		final AttrVariableTuple avt = this.itsAttrContext.getVariables();
		// mark used variables: RHS, LHS	
		for (Iterator<Node> e1 = getTarget().getNodesSet().iterator(); e1.hasNext();) {
			final GraphObject o = e1.next();
			if (o.getAttribute() == null)
				continue;
			final ValueTuple vt = (ValueTuple) o.getAttribute();
			for (int k = 0; k < vt.getSize(); k++) {
				final ValueMember vm = vt.getValueMemberAt(k);
				if (vm.isSet() && vm.getExpr().isVariable()) {
					final VarMember var = avt.getVarMemberAt(vm.getExprAsText());
					if (var != null)
						var.setMark(VarMember.RHS); // 1
				}
			}
		}
		for (Iterator<Arc> e2 = getTarget().getArcsSet().iterator(); e2.hasNext();) {
			final GraphObject o = e2.next();
			if (o.getAttribute() == null)
				continue;
			ValueTuple vt = (ValueTuple) o.getAttribute();
			for (int k = 0; k < vt.getSize(); k++) {
				ValueMember vm = vt.getValueMemberAt(k);
				if (vm.isSet() && vm.getExpr().isVariable()) {
					VarMember var = avt.getVarMemberAt(vm.getExprAsText());
					if (var != null)
						var.setMark(VarMember.RHS); // 1
				}
			}
		}
		
		for (Iterator<Node> e1 = getSource().getNodesSet().iterator(); e1.hasNext();) {
			final GraphObject o = e1.next();
			if (o.getAttribute() == null)
				continue;
			final ValueTuple vt = (ValueTuple) o.getAttribute();
			for (int k = 0; k < vt.getSize(); k++) {
				final ValueMember vm = vt.getValueMemberAt(k);
				if (vm.isSet() && vm.getExpr().isVariable()) {
					final VarMember var = avt.getVarMemberAt(vm.getExprAsText());
					if (var != null)
						var.setMark(VarMember.LHS); // 0
				}
			}
		}
		for (Iterator<Arc> e2 = getSource().getArcsSet().iterator(); e2.hasNext();) {
			final GraphObject o = e2.next();
			if (o.getAttribute() == null)
				continue;
			final ValueTuple vt = (ValueTuple) o.getAttribute();
			for (int k = 0; k < vt.getSize(); k++) {
				final ValueMember vm = vt.getValueMemberAt(k);
				if (vm.isSet() && vm.getExpr().isVariable()) {
					final VarMember var = avt.getVarMemberAt(vm.getExprAsText());
					if (var != null)
						var.setMark(VarMember.LHS); // 0
				}
			}
		}
//		((VarTuple) avt).showVariables();
	}

	/* (non-Javadoc)
	 * @see agg.cons.Evaluable#evalForall(java.lang.Object, int, boolean)
	 */
	public boolean evalForall(Object o, int tick) {
		// TODO Auto-generated method stub
		return false;
	}


	/*
	private void showMorphismData(final OrdinaryMorphism m) {
		System.out.println("Morphism data:  source graph, target graph, mappings::");
		System.out.println(m.getSource());
		System.out.println(m.getTarget());
		final Enumeration<GraphObject> dom = m.getDomain();
		while(dom.hasMoreElements()) {
			GraphObject go = dom.nextElement();
			System.out.println(go +" >>> "+m.getImage(go));
		}
	}
	*/
}
