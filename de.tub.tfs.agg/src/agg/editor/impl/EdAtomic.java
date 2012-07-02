/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.editor.impl;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import agg.cons.AtomConstraint;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.agt.MultiRule;
import agg.util.XMLHelper;
import agg.attribute.impl.CondTuple;

public class EdAtomic extends EdRule {

	protected OrdinaryMorphism morph;

	private Vector<EdAtomic> conclusions; 

	protected boolean isParent;

	protected EdAtomic parent;

	/**
	 * Creates the parent object of a new atomic graph constraint with one conclusion.
	 * The parent is an empty morphism with premise as source and target
	 * and it is not visible and is not used for any edit and evaluation.
	 * The first conclusion morphism consists of premise and conclusion.
	 * It is available from the list of conclusions by <code>getConsclusions()</code>
	 * or by <code>getConsclusion(0)</code>.
	 * This constructor must be used only once. To add a new conclusion to this 
	 * atomic constraint the methods
	 * <code>createNextConclusion(EdGraph)</code>,
	 * <code>createNextConclusion(String)</code>,
	 * <code>createNextConclusion(AtomConstraint,EdTypeSet,String,EdAtomic)</code>
	 * must be use.
	 *  
	 * @param a
	 * @param typeset
	 * @param name
	 */
	public EdAtomic(AtomConstraint a, EdTypeSet typeset, String name) {
		super(true);
		
		this.itsACs = null;
		this.itsNACs = null;
		this.itsPACs = null;
		
		this.typeSet = typeset;
		this.conclusions = new Vector<EdAtomic>();
				
		if (a == a.getParent()) {			
			this.parent = this;
			this.isParent = true;
			
			/* reset left and right graphs of my super class EdRule */
			this.eLeft = new EdGraph(a.getOriginal(), this.typeSet);
			this.eRight = this.eLeft;
//			eRight = new EdGraph(a.getImage(), typeSet);
			if (!name.equals("")) {
				this.eLeft.getBasisGraph().setName("Premise of " + name);
//				eRight.getBasisGraph().setName("Conclusion of " + name);
				a.setAtomicName(name);
			}
			this.morph = a;

			/* add conclusions */
			final Enumeration<AtomConstraint> e = a.getConclusions();
			while (e.hasMoreElements()) {
				final AtomConstraint c = e.nextElement();
				createNextConclusion(c);
			}
		} 
	}

	private EdAtomic(final AtomConstraint a, 
			final EdTypeSet typeset, 
			final String name, 
			final EdAtomic parent) {
		super(true);
		
		this.itsACs = null;
		this.itsNACs = null;
		this.itsPACs = null;
		
		this.typeSet = typeset;
		this.conclusions = new Vector<EdAtomic>(0);
							
		this.parent = parent;
		this.isParent = false;
			
		/* reset left and right graphs of my super class EdRule */
		this.eLeft = new EdGraph(a.getOriginal(), this.typeSet);
		this.eRight = new EdGraph(a.getImage(), this.typeSet);
		if (!name.equals("")) {
			this.eLeft.getBasisGraph().setName("Premise of " + name);
			this.eRight.getBasisGraph().setName("Conclusion of " + name);
			a.setAtomicName(name);
		}
		this.morph = a;
		this.morph.addObserver(this.eLeft);
		this.morph.addObserver(this.eRight);
	}
		
	private EdAtomic(final EdGraph orig,
			final EdGraph img, 
			final AtomConstraint a,
			final EdTypeSet typeset,
			final EdAtomic parent) {
		super(true);
		
		this.itsACs = null;
		this.itsNACs = null;
		this.itsPACs = null;
		
		this.typeSet = typeset;
		/* reset left and right graphs of my super class EdRule */
		this.eLeft = orig;
		this.eRight = img;
		this.morph = a;
		this.morph.addObserver(this.eLeft);
		this.morph.addObserver(this.eRight);

		this.conclusions = new Vector<EdAtomic>(0);
		
		this.parent = parent;
		this.isParent = false;
	}

	public void dispose() {
		if (this == this.parent) {
			while (this.conclusions.size() > 0) {
				EdAtomic conclusion = this.conclusions.remove(this.conclusions.size()-1);	
				if (conclusion.getMorphism() != null) {
					conclusion.getMorphism().deleteObserver(this.eLeft);
					conclusion.getMorphism().deleteObserver(this.eRight);
				}
				conclusion.getRight().dispose();
			}
			
			if (this.morph != null) {
				this.morph.deleteObserver(this.eLeft);
				this.morph.deleteObserver(this.eRight);
			}
	
			this.eLeft.dispose();
			this.eRight.dispose();
			
			this.eLeft = null;
			this.eRight = null;
			this.morph = null;
		}
	}
	
	public void finalize() {
//		System.out.println("EdAtomic.finalize()  called  "+this.hashCode());
	}
	
	public void trimToSize() {
		this.conclusions.trimToSize();
	}
	
	/** Allows to edit this graphical constraint. */
	public void setEditable(boolean b) {
		this.editable = b;
		if (!this.isParent) {
			this.eLeft.setEditable(b);
			this.eRight.setEditable(b);
		}
		else {
			for (int i=0; i<this.conclusions.size(); i++) {
				this.conclusions.get(i).setEditable(b);
			}
		}
	}
		
	public void setGraGra(EdGraGra egra) {
		this.eGra = egra;
		if (egra != null) {
			if (!this.isParent) {
				this.eLeft.setGraGra(egra);
				this.eRight.setGraGra(egra);
				this.typeSet = egra.getTypeSet();
			}
			else {
				for (int i=0; i<this.conclusions.size(); i++) {
					this.conclusions.get(i).setGraGra(this.eGra);
				}
			}
		}
	}
	
	public void setUndoManager(EditUndoManager anUndoManager) {
		if (!this.isParent) {
			this.undoManager = anUndoManager;
			this.eLeft.setUndoManager(anUndoManager);
			this.eRight.setUndoManager(anUndoManager);
		}
		else {
			for (int i=0; i<this.conclusions.size(); i++) {
				this.conclusions.get(i).setUndoManager(anUndoManager);
			}
		}
	}
	
	/** Sets my type set to the set specified by the EdTypeSet types */
	public void setTypeSet(EdTypeSet types) {
		this.typeSet = types;
		if (types != null) {
			this.eLeft.setTypeSet(types);
			this.eRight.setTypeSet(types);		
			this.typeSet = types;
		}
	}
	
	public void clear() {
		this.eLeft.clear();
		this.eRight.clear();
	}
	
	public OrdinaryMorphism getMorphism() {
		return this.morph;
	}

	public Rule getBasisRule() {
		return null;
	}

	/**
	 * Returns the name of my basis AtomConstraint or an empty string.
	 */
	public String getName() {
		if (this.morph != null)
			return ((AtomConstraint) this.morph).getName();
		
		return "";
	}

	public AtomConstraint getBasisAtomic() {
		return (AtomConstraint) this.morph;
	}

	public EdAtomic createNextConclusion(final String name) {
		if (this == this.parent) {
			final Graph g = BaseFactory.theFactory().createGraph(this.eLeft.getBasisGraph().getTypeSet());
			final EdGraph img = new EdGraph(g, this.eLeft.getTypeSet());
			
			final AtomConstraint a = this.parent.getBasisAtomic()
									.createNextConclusion(img.getBasisGraph());
			
			final EdAtomic conclusion = new EdAtomic(this.eLeft, img, a, 
											this.eLeft.getTypeSet(),
											this.parent);
			this.conclusions.addElement(conclusion);
			
			conclusion.getMorphism().setName(name);
			enrichConclusion(conclusion);
			
			return conclusion;
		}
		
		return null;
	}

	public EdAtomic createNextConclusion(final AtomConstraint a) {
		if (this == this.parent) {
			final EdGraph img = new EdGraph(a.getTarget(), this.eLeft.getTypeSet());
			
			final EdAtomic conclusion = new EdAtomic(this.eLeft, 
												img, 
												a, 
												this.eLeft.getTypeSet(),
												this);
			this.conclusions.add(conclusion);
			
			enrichConclusion(conclusion);
			
			return conclusion;
		}
		
		return null;
	}

	public EdAtomic createNextConclusion(final EdGraph img) {
		if (this == this.parent) {
			final AtomConstraint a = this.parent.getBasisAtomic()
								.createNextConclusion(img.getBasisGraph());
			final EdAtomic conclusion = new EdAtomic(a,
										this.eLeft.getTypeSet(), 
										"", 
										this.parent);
			this.conclusions.addElement(conclusion);
			
			enrichConclusion(conclusion);
			
			return conclusion;
		}
		
		return null;
	}


	private void enrichConclusion(final EdAtomic conclusion) {
		conclusion.setUndoManager(this.undoManager);
		conclusion.setGraGra(this.getGraGra());	
		
		if (this.getGraGra() != null) 
			this.getGraGra().setChanged(true);
	}
	
	/**
	 * Adds given conclusion to this atomic constraint.
	 * This atomic constraint must be a parent atomic.
	 * The given conclusion must be type compatible with 
	 * this atomic constraint.
	 * @param conclusion
	 * @return
	 */
	public EdAtomic addConclusion(EdAtomic conclusion) {
		if (this == this.parent) {
			conclusion.isParent = false;
			conclusion.parent = this.parent;
			this.conclusions.addElement(conclusion);
			
			enrichConclusion(conclusion);			
		}
		return conclusion;
	}

	public boolean destroyConclusion(EdAtomic conclusion) {
//		System.out.println("EdAtomic.destroyConclusion");
		if (this.conclusions.size() <= 1) {
			return false;
		}
		if (this.conclusions.contains(conclusion)) {
			this.conclusions.remove(conclusion);
			this.getBasisAtomic()
					.destroyConclusion(conclusion.getBasisAtomic());
			if (getGraGra() != null)
				getGraGra().setChanged(true);
			return true;
		} 
		return false;		
	}

	public boolean removeConclusion(EdAtomic conclusion) {
		if (this.conclusions.size() <= 1) {
			return false;
		}
		if (this.conclusions.contains(conclusion)) {		
			this.conclusions.remove(conclusion);
			this.getBasisAtomic().removeConclusion(conclusion.getBasisAtomic());
			if (getGraGra() != null)
				getGraGra().setChanged(true);
			return true;
		} 
		return false;
	}

	public void removeConclusions() {
		while (this.conclusions.size() > 1) {
			this.conclusions.remove(this.conclusions.size()-1);
		}
	}

	public Vector<EdAtomic> getConclusions() {
		return this.conclusions;
	}

	public EdAtomic getConclusion(int indx) {
		if ((indx >= 0) && (indx < this.conclusions.size()))
			return this.conclusions.elementAt(indx);
		
		return null;
	}

	public Vector<String> getAttrConditions() {
		Vector<String> conds = new Vector<String>(1);
		if (this.morph == null)
			return conds;
		
		CondTuple ct = (CondTuple) this.morph.getAttrContext().getConditions();
		for (int i = 0; i < ct.getSize(); i++) {
			conds.add(ct.getCondMemberAt(i).getExprAsText());
		}
		return conds;
	}

	public boolean isParent() {
		return this.isParent;
	}

	public EdAtomic getParent() {
		return this.parent;
	}

	public EdGraphObject getImage(EdGraphObject orig) {
		GraphObject im = this.morph.getImage(orig.getBasisObject());
		return this.eRight.findGraphObject(im);
	}

	public Vector<EdGraphObject> getOriginal(EdGraphObject image) {
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(2);
		Enumeration<GraphObject> en = this.morph.getInverseImage(image.getBasisObject());
		while (en.hasMoreElements()) {
			GraphObject or = en.nextElement();
			EdGraphObject go = this.eLeft.findGraphObject(or);
			if (go != null)
				vec.add(go);
		}
		return vec;
	}

	public void XwriteObject(XMLHelper h) {
		h.addObject("", this.eLeft, true);
		if (this.conclusions.size() > 0) {
			for (int i = 0; i < this.conclusions.size(); i++) {
				EdAtomic concl = this.conclusions.elementAt(i);
				EdGraph g = concl.getRight();
				h.addObject("", g, true);
			}
		}
	}

	public void XreadObject(XMLHelper h) {
		// System.out.println("EdAtomic.XreadObject... "+getName());

		h.enrichObject(this.eLeft);
		for (int i = 0; i < this.conclusions.size(); i++) {
			EdAtomic concl = this.conclusions.elementAt(i);
			h.enrichObject(concl.getRight());
		}

		updateRule();
	}

	public boolean deleteGraphObjectsOfType(
			final EdGraphObject tgo,
			boolean addToUndo) {
		
		List<EdGraphObject> list = this.eLeft.getGraphObjectsOfType(tgo);
		if (addToUndo) {
			for (int i=0; i<list.size(); i++) {
				EdGraphObject go = list.get(i);
				EdGraphObject rgo = this.eRight.findGraphObject(
						this.getBasisRule().getImage(go.getBasisObject()));			
				if (rgo != null) {			
					this.addDeletedMappingToUndo(go, rgo);
					this.undoManagerEndEdit();
				}
			}
		}
		
		boolean allDone = true;		
		if (!this.eLeft.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
			allDone = false;
		if (!this.eRight.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
			allDone = false;
		
		return allDone;
	}
	
	public boolean deleteGraphObjectsOfType(
			final EdType t,
			boolean addToUndo) {
		
		List<EdGraphObject> list = this.eLeft.getGraphObjectsOfType(t);
		if (addToUndo) {
			for (int i=0; i<list.size(); i++) {
				EdGraphObject go = list.get(i);
				if (this.getBasisRule().getRuleScheme() == null
						|| this.getBasisRule().getRuleScheme().getKernelRule() == this.bRule
						|| !((MultiRule)this.bRule).isTargetOfEmbeddingLeft(go.getBasisObject())) {
					EdGraphObject rgo = this.eRight.findGraphObject(
								this.getBasisRule().getImage(go.getBasisObject()));			
					if (rgo != null) {			
	//					this.propagateRemoveRuleMappingToMultiRule(go);
						
						this.addDeletedMappingToUndo(go, rgo);
						this.undoManagerEndEdit();
					}
				}
			}
		}
		boolean allDone = true;
		if (!this.eLeft.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
			allDone = false;
		if (!this.eRight.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
			allDone = false;
		
		return allDone;
	}
	
	public void setLayoutByIndexFrom(EdRule er, boolean inverse) {
		if (inverse) {
			this.eLeft.setLayoutByIndex(er.getRight(), true);
			this.eRight.setLayoutByIndex(er.getLeft(), true);
		} else {
			this.eLeft.setLayoutByIndex(er.getLeft(), true);
			this.eRight.setLayoutByIndex(er.getRight(), true);
		}
	}
	
	/** This method is not implemented for this class. */
	public boolean addNAC(EdNAC nac) {return false;}
	/** This method is not implemented for this class. */
	public EdNAC createNAC(OrdinaryMorphism nac) { return null; }
	/** This method is not implemented for this class. */
	public EdNAC createNAC(String nameStr, boolean isIdentic) { return null; }
	/** This method is not implemented for this class. */
	public void destroyNAC(EdNAC enac) {}
	/** This method is not implemented for this class. */
	protected EdGraphObject findGraphObjectOfNAC(String goHashCode) {return null;}
	/** This method is not implemented for this class. */
	protected EdGraphObject findRestoredObjectOfNAC(EdGraphObject go) {return null;}
	/** This method is not implemented for this class. */
	public Object getApplCondByImageGraph(Graph g) {return null;}
	/** This method is not implemented for this class. */
	public EdNAC getNAC(int index) { return null; }
	/** This method is not implemented for this class. */
	public EdNAC getNAC(OrdinaryMorphism morphism) { return null; }
	/** This method is not implemented for this class. */
	public EdNAC getNAC(String nacname) { return null; }
	/** This method is not implemented for this class. */
	public Vector<EdNAC> getNACs() { return null; }
	/** This method is not implemented for this class. */
	public boolean hasNACs() { return false; }
	/** This method is not implemented for this class. */
	public boolean removeNAC(EdNAC nac) { return false; }
	/** This method is not implemented for this class. */
	public void removeNACMapping(EdGraphObject leftObj) {}
	/** This method is not implemented for this class. */
	public void updateNACs() {}
	/** This method is not implemented for this class. */
	public boolean addPAC(EdPAC pac) { return false; }
	/** This method is not implemented for this class. */
	public EdPAC createPAC(OrdinaryMorphism pac) { return null; }
	/** This method is not implemented for this class. */
	public EdPAC createPAC(String nameStr, boolean isIdentic) { return null; }
	/** This method is not implemented for this class. */
	public void destroyPAC(EdPAC epac) {}
	/** This method is not implemented for this class. */
	protected EdGraphObject findGraphObjectOfPAC(String goHashCode) { return null; }
	/** This method is not implemented for this class. */
	protected EdGraphObject findRestoredObjectOfPAC(EdGraphObject go) { return null; }
	/** This method is not implemented for this class. */
	public EdPAC getPAC(int index) { return null; }
	/** This method is not implemented for this class. */
	public EdPAC getPAC(OrdinaryMorphism morphism) { return null; }
	/** This method is not implemented for this class. */
	public EdPAC getPAC(String pacname) { return null; }
	/** This method is not implemented for this class. */
	public Vector<EdPAC> getPACs() { return null; }
	/** This method is not implemented for this class. */
	public boolean hasPACs() { return false; }
	/** This method is not implemented for this class. */
	public boolean removePAC(EdPAC pac) { return false; }
	/** This method is not implemented for this class. */
	public void removePACMapping(EdGraphObject leftObj) {}
	/** This method is not implemented for this class. */
	public void updatePACs() {}
	/** This method is not implemented for this class. */
	public boolean addNestedAC(EdNestedApplCond ac) { return false; }
	/** This method is not implemented for this class. */
	public EdPAC createNestedAC(OrdinaryMorphism ac) { return null; }
	/** This method is not implemented for this class. */
	public EdPAC createNestedAC(String nameStr, boolean isIdentic) { return null; }
	/** This method is not implemented for this class. */
	public void destroyNestedAC(EdPAC ac) {}
	/** This method is not implemented for this class. */
	protected EdGraphObject findGraphObjectOfAC(String goHashCode) { return null; }
	/** This method is not implemented for this class. */
	protected EdGraphObject findRestoredObjectOfAC(EdGraphObject go) { return null; }
	/** This method is not implemented for this class. */
	public List<EdNestedApplCond> getEnabledACs() {return null; }
	/** This method is not implemented for this class. */
	public List<EdNestedApplCond> getEnabledNestedACs() { return null; }
	/** This method is not implemented for this class. */
	public EdPAC getNestedAC(int index) { return null; }
	/** This method is not implemented for this class. */
	public EdPAC getNestedAC(OrdinaryMorphism morphism) {return null; }
	/** This method is not implemented for this class. */
	public EdPAC getNestedAC(String acname) {return null; }
	/** This method is not implemented for this class. */
	public Vector<EdPAC> getNestedACs() { return null; }
	/** This method is not implemented for this class. */
	public boolean hasNestedACs() { return false; }
	/** This method is not implemented for this class. */
	public boolean removeNestedAC(EdPAC ac) { return false; }
	/** This method is not implemented for this class. */
	public void removeNestedACMapping(EdGraphObject leftObj) {}
	
}
