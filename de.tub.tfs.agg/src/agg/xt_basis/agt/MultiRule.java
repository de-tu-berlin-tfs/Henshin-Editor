/**
 * 
 */
package agg.xt_basis.agt;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;

import agg.attribute.impl.ContextView;
import agg.attribute.impl.TupleMapping;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.Change;
import agg.util.Pair;
import agg.util.XMLHelper;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;

/**
 * Multi rule is an extending rule of an interaction rule scheme. The kernel
 * rule of a rule scheme is embedded into multi rule. So the application of two
 * or more multi rules is synchronized by the kernel rule above this embedding.
 * 
 * @author olga
 * 
 */
public class MultiRule extends Rule implements Observer {

	private RuleScheme itsRuleScheme;

	/** embedded morphism left */
	private OrdinaryMorphism embeddingLeft;

	/** embedded morphism right */
	private OrdinaryMorphism embeddingRight;

	private final Hashtable<GraphObject, GraphObject> 
	kernel2objects = new Hashtable<GraphObject, GraphObject>();

	private final Hashtable<GraphObject, GraphObject> 
	objects2kernel = new Hashtable<GraphObject, GraphObject>();

	 private List<OrdinaryMorphism> shiftedApplConds = new Vector<OrdinaryMorphism>();

	// private boolean isChanged = false;

	/**
	 * Creates a multi rule with empty left and right graphs based on the
	 * specified type set.
	 */
	public MultiRule(TypeSet types) {
		super(types);

		this.itsName = "MultiRule";
	}

	/**
	 * Creates a multi rule based on the specified left and right embedding of
	 * the kernel rule. Target graph of the left embedding is the LHS, target
	 * graph of the right embedding is the RHS of the multi rule. The object
	 * mapping of the kernel rule builds partial mapping of the multi rule.
	 */
	public MultiRule(final Rule kernelRule,
			final OrdinaryMorphism embeddingLeft,
			final OrdinaryMorphism embeddingRight) {

		super(embeddingLeft.getTarget(), embeddingRight.getTarget());

		this.itsName = "MultiRule";

//		this.itsOrig.setAttrContext(getAttrManager().newLeftContext(
//				getAttrContext()));
//		this.itsImag.setAttrContext(getAttrManager().newRightContext(
//				getAttrContext()));

		this.embeddingLeft = embeddingLeft;
		this.embeddingRight = embeddingRight;
		
		this.applyEmbeddedRuleMapping(kernelRule);

		this.itsOrig.setKind(GraphKind.LHS);
		this.itsImag.setKind(GraphKind.RHS);

		kernelRule.getLeft().setKind(GraphKind.LHS);
		kernelRule.getRight().setKind(GraphKind.RHS);
		
		mapKernel2MultiObject();
	}

	/**
	 * Returns its full name : schemeName.ruleName
	 */
	public String getQualifiedName() {
		if (this.itsRuleScheme != null)
			return this.itsRuleScheme.getName().concat(".").concat(this.itsName);
		
		return this.itsName;
	}
	
	public void addShiftedKernelApplCond(OrdinaryMorphism cond, boolean pac) {
		this.shiftedApplConds.add(cond);
		if (pac) {
			this.itsPACs.add(0, cond);
		} else {
			this.itsNACs.add(0, cond);
		}
	}
	
	public void addShiftedKernelNestedApplCond(NestedApplCond cond) {
		this.shiftedApplConds.add(cond);
		this.itsACs.add(0, cond);
	}
	
	public void removeShiftedKernelApplConds() {
		for (int i=0; i<this.shiftedApplConds.size(); i++) {
			OrdinaryMorphism cond = this.shiftedApplConds.get(i);
			if (this.itsACs.contains(cond)) {
				this.destroyNestedAC(cond);
			}
			else if (this.itsPACs.contains(cond)) {
				this.destroyPAC(cond);
			} 
			else if (this.itsNACs.contains(cond)) {
				this.destroyNAC(cond);
			}
		}
		this.shiftedApplConds.clear();
	}
	
	public void removeShiftedKernelApplCond(OrdinaryMorphism cond, boolean pac) {
		if (this.shiftedApplConds.remove(cond)) {
			if (pac) {
				this.destroyPAC(cond);
			} else {
				this.destroyNAC(cond);
			}
		}
	}
	
	/**
	 * Returns a match which partially based on the match of the kernel rule.
	 */
	public Match getMatch(final Rule kernelRule) {
		if (this.itsMatch == null
				&& kernelRule == this.itsRuleScheme.getKernelRule()) {
			if (this.itsRuleScheme.getKernelRule().getMatch() != null) {
				this.itsMatch = BaseFactory.theFactory().createMatch(
						this,
						this.itsRuleScheme.getKernelRule().getMatch()
								.getTarget());
			}

			if (!setPartialMultiMatch(this.itsRuleScheme.getKernelRule()
					.getMatch())) {
				this.itsMatch.dispose();
				this.itsMatch = null;
			}
		}

		return this.itsMatch;
	}

	/** Create partial match based of the specified multi rule. */
	private boolean setPartialMultiMatch(final Match kernelMatch) {
		if (kernelMatch != null) {
			// set partial match taken from valid kernel match
			final Iterator<Node> e1 = kernelMatch.getSource().getNodesSet()
					.iterator();
			while (e1.hasNext()) {
				final GraphObject objLkern = e1.next();
				final GraphObject objLmulti = this.embeddingLeft
						.getImage(objLkern);
				final GraphObject imgKernMatch = kernelMatch.getImage(objLkern);
				if (objLmulti != null && imgKernMatch != null) {
					try {
						this.itsMatch.addMapping(objLmulti, imgKernMatch);
					} catch (BadMappingException ex) {
						return false;
					}
				}
			}
			final Iterator<Arc> e2 = kernelMatch.getSource().getArcsSet()
					.iterator();
			while (e2.hasNext()) {
				final GraphObject objLkern = e2.next();
				final GraphObject objLmulti = this.embeddingLeft
						.getImage(objLkern);
				final GraphObject imgKernMatch = kernelMatch.getImage(objLkern);
				if (objLmulti != null && imgKernMatch != null) {
					try {
						this.itsMatch.addMapping(objLmulti, imgKernMatch);
					} catch (BadMappingException ex) {
						return false;
					}
				}
			}
			this.itsMatch.adaptAttrContextValues(kernelMatch.getAttrContext());
			setTempInputParameter(kernelMatch);

			if (this.itsMatch.getSize() > 0) {
				this.itsMatch.setPartialMorphismCompletion(true);
			}
		}
		return true;
	}

	private void setTempInputParameter(final Match kernelMatch) {
//		VarTuple ruleVars = (VarTuple) this.getAttrContext().getVariables();		
		VarTuple kernVars = (VarTuple) kernelMatch.getAttrContext()
				.getVariables();
		VarTuple matchVars = (VarTuple) this.itsMatch.getAttrContext()
				.getVariables();
		for (int i = 0; i < kernVars.getNumberOfEntries(); i++) {
			VarMember kernVar = kernVars.getVarMemberAt(i);
			VarMember var = matchVars.getVarMemberAt(kernVar.getName());
			if (var != null
					&& kernVar.getDeclaration().getTypeName().equals(
							var.getDeclaration().getTypeName())) {
				if (kernVar.isSet()) {
					var.setExprAsText(kernVar.getExprAsText());
					if (!var.isInputParameter()) {
						var.setInputParameter(true);
					}
				}
			}
		}
	}

	/**
	 * @return true if the left embedding of the LHS of the kernel rule holds,
	 *         otherwise false.
	 */
	public boolean isLeftEmbeddingValid() {
		Iterator<?> kernelElems = this.embeddingLeft.getSource().getNodesSet()
				.iterator();
		while (kernelElems.hasNext()) {
			final GraphObject obj = (GraphObject) kernelElems.next();
			if (this.embeddingLeft.getImage(obj) == null) {
				return false;
			}
			final GraphObject img = this.embeddingLeft.getImage(obj);
			adoptEntriesWhereEmpty(this.embeddingLeft, obj, img);
		}
		if (kernelElems.hasNext())
			return false;

		kernelElems = this.embeddingLeft.getSource().getArcsSet().iterator();
		while (kernelElems.hasNext()) {
			final GraphObject obj = (GraphObject) kernelElems.next();
			if (this.embeddingLeft.getImage(obj) == null) {
				return false;
			}
			final GraphObject img = this.embeddingLeft.getImage(obj);
			adoptEntriesWhereEmpty(this.embeddingLeft, obj, img);
		}
		if (kernelElems.hasNext())
			return false;

		return true;
	}

	/**
	 * @return true if the right embedding of the RHS of the kernel rule holds,
	 *         otherwise false.
	 */
	public boolean isRightEmbeddingValid() {
		Iterator<?> kernelElems = this.itsRuleScheme.getKernelRule().getRight()
				.getNodesSet().iterator();
		while (kernelElems.hasNext()) {
			final GraphObject obj = (GraphObject) kernelElems.next();
			if (this.embeddingRight.getImage(obj) == null) {
				return false;
			}
			final GraphObject img = this.embeddingRight.getImage(obj);
			adoptEntriesWhereEmpty(this.embeddingRight, obj, img);
		}
		if (kernelElems.hasNext())
			return false;

		kernelElems = this.itsRuleScheme.getKernelRule().getRight()
				.getArcsSet().iterator();
		while (kernelElems.hasNext()) {
			final GraphObject obj = (GraphObject) kernelElems.next();
			if (this.embeddingRight.getImage(obj) == null) {
				return false;
			}
			final GraphObject img = this.embeddingRight.getImage(obj);
			adoptEntriesWhereEmpty(this.embeddingRight, obj, img);
		}
		if (kernelElems.hasNext())
			return false;
		return true;
	}

	/**
	 * 
	 * @return true if the rule morphism embedding of the kernel rule holds,
	 *         otherwise false.
	 */
	public boolean isMorphismEmbeddingValid() {
		final Enumeration<GraphObject> kernelDom = this.itsRuleScheme
				.getKernelRule().getDomain();
		while (kernelDom.hasMoreElements()) {
			final GraphObject goKern = kernelDom.nextElement();
			final GraphObject imgKern = this.itsRuleScheme.getKernelRule()
					.getImage(goKern);
			final GraphObject go = this.embeddingLeft.getImage(goKern);
			if (go == null) {
				return false;
			} else if (imgKern != null
					&& this.embeddingRight.getImage(imgKern) != this
							.getImage(go)) {
				return false;
			} else if (imgKern == null && this.getImage(go) != null) {
				return false;
			}
		}
		return true;
	}


	/**
	 * Set partial rule morphism mapping based on the specified kernel rule.
	 * 
	 * @param kernelRule
	 *            kernel rule of its rule scheme
	 * @return true if rule mapping set successfully, otherwise false.
	 */
	public boolean applyEmbeddedRuleMapping(final Rule kernelRule) {
		final Enumeration<GraphObject> kernelDom = kernelRule.getDomain();
		while (kernelDom.hasMoreElements()) {
			final GraphObject goKern = kernelDom.nextElement();
			final GraphObject imgKern = kernelRule.getImage(goKern);
			if (imgKern != null
					&& this.getImage(this.embeddingLeft.getImage(goKern)) != this.embeddingRight.getImage(imgKern)) {
				try {
					this.addPlainMapping(this.embeddingLeft.getImage(goKern), 
							this.embeddingRight.getImage(imgKern));
				} catch (BadMappingException ex) {
					return false;
				}
			}
		}
		return true;
	}

	private void mapKernel2MultiObject() {
		final Iterator<Node> nLeft = this.itsOrig.getNodesSet().iterator();
		while (nLeft.hasNext()) {
			GraphObject obj = nLeft.next();
			if (this.embeddingLeft.getInverseImage(obj).hasMoreElements()) {
				this.mapKernel2MultiObject(this.embeddingLeft.getInverseImage(
						obj).nextElement(), obj);
			}
		}
		final Iterator<Arc> aLeft = this.itsOrig.getArcsSet().iterator();
		while (aLeft.hasNext()) {
			GraphObject obj = aLeft.next();
			if (this.embeddingLeft.getInverseImage(obj).hasMoreElements()) {
				this.mapKernel2MultiObject(this.embeddingLeft.getInverseImage(
						obj).nextElement(), obj);
			}
		}

		final Iterator<Node> nRight = this.itsImag.getNodesSet().iterator();
		while (nRight.hasNext()) {
			GraphObject obj = nRight.next();
			if (this.embeddingRight.getInverseImage(obj).hasMoreElements()) {
				this.mapKernel2MultiObject(this.embeddingRight.getInverseImage(
						obj).nextElement(), obj);
			}
		}
		final Iterator<Arc> aRight = this.itsImag.getArcsSet().iterator();
		while (aRight.hasNext()) {
			GraphObject obj = aRight.next();
			if (this.embeddingRight.getInverseImage(obj).hasMoreElements()) {
				this.mapKernel2MultiObject(this.embeddingRight.getInverseImage(
						obj).nextElement(), obj);
			}
		}
	}

	/**
	 * Checks left, right and rule morphism embedding of the kernel rule.
	 */
	public boolean isReadyToTransform() {
		if (this.isLeftEmbeddingValid() 
				&& this.isRightEmbeddingValid()
				&& this.isMorphismEmbeddingValid()
				&& super.isReadyToTransform()) {
			return true;
		}
		return false;
	}

	/**
	 * Set the reference of its rule scheme.
	 * 
	 * @param rs
	 *            its rule scheme
	 */
	public void setRuleScheme(final RuleScheme rs) {
		this.itsRuleScheme = rs;
	}

	/**
	 * @return the reference of its rule scheme
	 */
	public RuleScheme getRuleScheme() {
		return this.itsRuleScheme;
	}

	/**
	 * Set the left embedding morphism with the source graph is the left graph
	 * of the kernel rule and the target graph is the left graph of this multi
	 * rule.
	 * 
	 * @param left
	 *            the left embedding morphism
	 */
	public void setEmbeddingLeft(final OrdinaryMorphism left) {
		this.embeddingLeft = left;
	}

	/**
	 * Set the right embedding morphism with the source graph is the right graph
	 * of the kernel rule and the target graph is the right graph of this multi
	 * rule.
	 * 
	 * @param left
	 *            the left embedding morphism
	 */
	public void setEmbeddingRight(final OrdinaryMorphism right) {
		this.embeddingRight = right;
	}

	/**
	 * @return its left embedding morphism with the source graph is the left
	 *         graph of the kernel rule and the target graph is the left graph
	 *         of this multi rule.
	 */
	public OrdinaryMorphism getEmbeddingLeft() {
		return this.embeddingLeft;
	}

	/**
	 * 
	 * @return its right embedding morphism with the source graph is the right
	 *         graph of the kernel rule and the target graph is the right graph
	 *         of this multi rule.
	 */
	public OrdinaryMorphism getEmbeddingRight() {
		return this.embeddingRight;
	}

	public void addEmbeddingLeft(final GraphObject kern, final GraphObject obj) {
		this.embeddingLeft.addMapping(kern, obj);
		this.kernel2objects.put(kern, obj);
		this.objects2kernel.put(obj,kern);
	}
	
	public void addEmbeddingRight(final GraphObject kern, final GraphObject obj) {
		this.embeddingRight.addMapping(kern, obj);
		this.kernel2objects.put(kern, obj);
		this.objects2kernel.put(obj,kern);
	}
	
	public void removeEmbeddingLeft(final GraphObject obj) {
		if (this.kernel2objects.get(obj) != null) {
			GraphObject obj2 = this.kernel2objects.get(obj);
			this.embeddingLeft.removeMappingFast(obj, true);
			this.kernel2objects.remove(obj);
			this.objects2kernel.remove(obj2);
		}
		else if (this.objects2kernel.get(obj) != null) {
			GraphObject obj2 = this.objects2kernel.get(obj);
			this.embeddingLeft.removeMappingFast(obj2, true);
			this.kernel2objects.remove(obj2);
			this.objects2kernel.remove(obj);
		}
	}
	
	public void removeEmbeddingRight(final GraphObject obj) {
		if (this.kernel2objects.get(obj) != null) {
			GraphObject obj2 = this.kernel2objects.get(obj);
			this.embeddingRight.removeMappingFast(obj, false);
			this.kernel2objects.remove(obj);
			this.objects2kernel.remove(obj2);
		}
		else if (this.objects2kernel.get(obj) != null) {
			GraphObject obj2 = this.objects2kernel.get(obj);
			this.embeddingRight.removeMappingFast(obj2, false);
			this.kernel2objects.remove(obj2);
			this.objects2kernel.remove(obj);
		}
	}
	
	public List<Node> getOwnNodesLeft() {
		Vector<Node> list = new Vector<Node>();
		Iterator<Node> nodes = this.itsOrig.getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node n = nodes.next();
			if (!this.embeddingLeft.getCodomainObjects().contains(n)) {
				list.add(n);
			}
		}
		return list;
	}
	
	public List<Node> getOwnNodesRight() {
		Vector<Node> list = new Vector<Node>();
		Iterator<Node> nodes = this.itsImag.getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node n = nodes.next();
			if (!this.embeddingRight.getCodomainObjects().contains(n)) {
				list.add(n);
			}
		}
		return list;
	}
	
	public List<Arc> getOwnArcsLeft() {
		Vector<Arc> list = new Vector<Arc>();
		Iterator<Arc> arcs = this.itsOrig.getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (!this.embeddingLeft.getCodomainObjects().contains(a)) {
				list.add(a);
			}
		}
		return list;
	}
	
	public List<Arc> getOwnArcsRight() {
		Vector<Arc> list = new Vector<Arc>();
		Iterator<Arc> arcs = this.itsImag.getArcsSet().iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (!this.embeddingRight.getCodomainObjects().contains(a)) {
				list.add(a);
			}
		}
		return list;
	}
	
	public void removeOwnMappings() {
		List<Arc> arcs = getOwnArcsLeft();
		for (int i=0; i<arcs.size(); i++) {
			this.removeMappingFast(arcs.get(i), true);
		}
		List<Node> nodes = getOwnNodesLeft();
		for (int i=0; i<nodes.size(); i++) {
			this.removeMappingFast(nodes.get(i), true);
		}
	}
	
	public void removeOwnNodesLeft() {
		List<Node> nodes = getOwnNodesLeft();
		for (int i=0; i<nodes.size(); i++) {
			try {
				this.itsOrig.destroyNode(nodes.get(i), false, true);
			} catch (TypeException ex) {}
		}
	}
	
	public void removeOwnNodesRight() {
		List<Node> nodes = getOwnNodesRight();
		for (int i=0; i<nodes.size(); i++) {
			try {
				this.itsImag.destroyNode(nodes.get(i), false, true);
			} catch (TypeException ex) {}
		}
	}
	
	public void removeOwnArcsLeft() {
		List<Arc> arcs = getOwnArcsLeft();
		for (int i=0; i<arcs.size(); i++) {
			try {
				this.itsOrig.destroyArc(arcs.get(i), false, true);
			} catch (TypeException ex) {}
		}
	}
	
	public void removeOwnArcsRight() {
		List<Arc> arcs = getOwnArcsRight();
		for (int i=0; i<arcs.size(); i++) {
			try {
				this.itsImag.destroyArc(arcs.get(i), false, true);
			} catch (TypeException ex) {}
		}
	}
	
	/**
	 * @param obj
	 *            an object of the LHS of the kernel rule
	 * @return true if the specified graph object belongs to the LHS of the
	 *         kernel rule
	 */
	public boolean isSourceOfEmbeddingLeft(final GraphObject obj) {
		return this.embeddingLeft.getImage(obj) != null;
	}

	/**
	 * @param obj
	 *            an object of the RHS of the kernel rule
	 * @return true if the specified graph object belongs to the RHS of the
	 *         kernel rule
	 */
	public boolean isSourceOfEmbeddingRight(final GraphObject obj) {
		return this.embeddingRight.getImage(obj) != null;
	}

	/**
	 * @param obj
	 *            an object of the LHS of this multi rule
	 * @return true if the specified graph object belongs to the LHS of this
	 *         multi rule and its source object of the left embedding belongs to
	 *         the LHS of the kernel rule.
	 */
	public boolean isTargetOfEmbeddingLeft(final GraphObject obj) {
		return this.embeddingLeft.getInverseImage(obj).hasMoreElements();
	}

	/**
	 * @param obj
	 *            an object of the RHS of this multi rule
	 * @return true if the specified graph object belongs to the RHS of this
	 *         multi rule and its source object of the right embedding belongs
	 *         to the RHS of the kernel rule.
	 */
	public boolean isTargetOfEmbeddingRight(final GraphObject obj) {
		return this.embeddingRight.getInverseImage(obj).hasMoreElements();
	}

	/** Set the left graph of the multi rule. */
	public final void setLeft(Graph left) {
		this.clear();
		this.itsOrig = left;
	}

	/** Set the right graph of the multi rule. */
	public final void setRight(Graph right) {
		this.clear();
		this.itsImag = right;
	}

	public void mapKernel2MultiObject(final GraphObject kernelObj,
			final GraphObject obj) {
		this.kernel2objects.put(kernelObj, obj);
		this.objects2kernel.put(obj, kernelObj);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public void update(Observable o, Object arg) {
//		System.out.println("MultiRule.update:   "+o+"    "+arg);
		GraphObject go = null;
		Graph graph = null;

		if (arg instanceof Change) {
			Change change = (Change) arg;
			if (change.getItem() instanceof GraphObject) {
				go = (GraphObject) change.getItem();
			} else if (change.getItem() instanceof Pair) {
				Pair<?, ?> p = (Pair) change.getItem();
				if (p.first instanceof GraphObject)
					go = (GraphObject) p.first;
			}

			if (o instanceof Graph) {
				if (this.itsRuleScheme.getKernelRule().getLeft() == o)
					graph = this.itsOrig;
				else if (this.itsRuleScheme.getKernelRule().getRight() == o)
					graph = this.itsImag;
			}

			if (go != null
					&& graph != null) {
				if (change.getEvent() == Change.OBJECT_MODIFIED) {
					GraphObject mgo = this.kernel2objects.get(go);
					if (mgo != null) {
						mgo.copyAttributes(go);
						// ((ValueTuple)mgo.getAttribute()).showValue();
					}
				}
			} 
		}
	}

	private void adoptEntriesWhereEmpty(final OrdinaryMorphism morph,
			final GraphObject from, final GraphObject to) {

		if (morph.getImage(from) != null && from.getAttribute() != null
				&& to.getAttribute() != null) {
			final ContextView context = (ContextView) morph.getAttrContext();
			Vector<TupleMapping> mappings = context
					.getMappingsToTarget((ValueTuple) to.getAttribute());
			if (mappings != null) {
				mappings.elementAt(0).adoptEntriesWhereEmpty(
						(ValueTuple) from.getAttribute(),
						(ValueTuple) to.getAttribute());
			}
		}
	}

	/**
	 * Save the multi rule.
	 * 
	 * @param h
	 *            AGG XML helper
	 */
	public void XwriteObject(XMLHelper h) {
		super.XwriteObject(h);

	}

	/**
	 * Load the multi rule.
	 * 
	 * @param h
	 *            AGG XML helper
	 */
	public void XreadObject(XMLHelper h) {
		super.XreadObject(h);

	}

}
