/**
 * 
 */
package agg.editor.impl;

import java.util.Enumeration;
import java.util.List;
import java.util.Vector;

import agg.util.Pair;
import agg.util.XMLHelper;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;

/**
 * @author olga
 *
 */
public class EdNestedApplCond extends EdPAC {

	EdNestedApplCond itsParent;
	
	EdGraph itsSource;
	
	List<EdNestedApplCond> itsACs = new Vector<EdNestedApplCond>(0,1);
	
	boolean badMapping = false;

	String errMsg = "";
	
	public EdNestedApplCond(final EdNestedApplCond parent) {
		super();
		this.itsParent = parent;
	}
	
	public EdNestedApplCond(final EdNestedApplCond parent, EdTypeSet types) {
		super(types);
		this.itsParent = parent;
	}
	
	public EdNestedApplCond(final EdNestedApplCond parent, OrdinaryMorphism m) {
		super(m);
		this.itsParent = parent;
	}
	
	public EdNestedApplCond(final EdNestedApplCond parent, OrdinaryMorphism m, EdTypeSet types) {
		super(m, types);
		this.itsParent = parent;
		
		List<NestedApplCond> list = ((NestedApplCond)m).getNestedACs();
		for (int i=0; i<list.size(); i++) {
			this.createNestedAC(list.get(i));
			
		}
	}
	
	public void setUndoManager(EditUndoManager anUndoManager) {
		this.undoManager = anUndoManager;
		for (int j = 0; j < this.getNestedACs().size(); j++) {
			this.getNestedACs().get(j).setUndoManager(this.undoManager);
		}
	}
	
	void addEdit(EdGraphObject src, EdGraphObject tar, String kind,
			String presentation) {
		Vector<String> v = new Vector<String>();
		v.add(String.valueOf(src.hashCode()));
		v.add(String.valueOf(tar.hashCode()));
		this.undoObj = new Pair<String, Vector<?>>(kind, v);
		undoManagerAddEdit(presentation);
	}
	
	public void addCreatedMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.isEditable()
				|| this.morphism == null) {
			return;
		}
		
		GraphObject oldImg = this.morphism.getImage(src.getBasisObject());
		
		if (oldImg != null) {
			EdGraphObject go = this.findGraphObject(oldImg);
			if (go != null)
				addDeletedMappingToUndo(src, go);
		}
		addEdit(src, tar, EditUndoManager.AC_MAPPING_CREATE_DELETE,
				"Undo Create AC Mapping");
	}

	public void addDeletedMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.isEditable()
				|| this.morphism == null) {
			return;
		}
		if (tar.isNode()) {
			addDeletedMappingOfInOutEdgesToUndo((EdNode) src, (EdNode) tar, 
					src.getContext(), tar.getContext(), this.morphism,
					EditUndoManager.AC_MAPPING_DELETE_CREATE,
					"Undo Delete AC Mapping");			
		}

		addEdit(src, tar, EditUndoManager.AC_MAPPING_DELETE_CREATE,
				"Undo Delete AC Mapping");
	}
	
	private void addDeletedMappingOfInOutEdgesToUndo(EdNode orig, EdNode img,
			EdGraph origG, EdGraph imgG, OrdinaryMorphism morph, String kind,
			String msg) {
		Vector<EdArc> inArcs = origG.getIncomingArcs(orig);
		for (int i = 0; i < inArcs.size(); i++) {
			EdArc origEdArc = inArcs.get(i);
			GraphObject obj = morph.getImage(origEdArc.getBasisArc());
			if(obj != null) {
				Arc imgArc = (Arc) obj;		
				EdArc imgEdArc = imgG.findArc(imgArc);
				if (imgEdArc != null) {
					addEdit(origEdArc, imgEdArc, kind, msg);
				}
			}
		}
		Vector<EdArc> outArcs = origG.getOutgoingArcs(orig);
		for (int i = 0; i < outArcs.size(); i++) {
			EdArc origEdArc = outArcs.get(i);
			if (inArcs.contains(origEdArc))
				continue;
			GraphObject obj = morph.getImage(origEdArc.getBasisArc());
			if(obj != null) {
				Arc imgArc = (Arc) obj;
				EdArc imgEdArc = imgG.findArc(imgArc);
				if (imgEdArc != null) {
					addEdit(origEdArc, imgEdArc, kind, msg);
				}
			}
		}
	}
	
	/** Allows to edit this rule application condition. */
	public void setEditable(boolean b) {
		this.editable = b;
		for (int i=0; i<this.itsACs.size(); i++) {
			this.itsACs.get(i).setEditable(b);
		}
	}
	
	public void setRule(EdRule er) {
		super.setRule(er);
		
		for (int i=0; i<this.itsACs.size(); i++) {
			this.itsACs.get(i).setRule(er);
		}
	}
	
	public void setGraGra(EdGraGra egra) {
		if (egra == null) {
			return;
		}
		super.setGraGra(egra);
		for (int i=0; i<this.itsACs.size(); i++) {
			this.itsACs.get(i).setGraGra(egra);
		}
	}
	
	public void setSourceGraph(EdGraph g) {
		if (this.morphism.getSource() == g.getBasisGraph())
			this.itsSource = g;
	}
	
	public EdGraph getSource() {
		return this.itsSource;
	}
	
	public NestedApplCond getNestedMorphism() {
		return (NestedApplCond) this.morphism;
	}

	public void removeNestedAC(final EdNestedApplCond cond) {		
		if (this.itsACs.contains(cond)) {
			this.getNestedMorphism().removeNestedAC(cond.getNestedMorphism());
			this.itsACs.remove(cond);
		}
	}
	
	public boolean removeNestedACMapping(EdGraphObject leftObj, EdNestedApplCond ac) {
		if (!this.editable)
			return false;

		if (ac.getMorphism().getImage(leftObj.getBasisObject()) != null) {
			ac.getMorphism().removeMapping(leftObj.getBasisObject());
			this.updateNestedAC(ac);
			return true;
		}
		return false;
	}
	
	public List<EdNestedApplCond> getNestedACs() {
		return this.itsACs;
	}
	
	public List<EdNestedApplCond> getEnabledNestedACs() {
		List<EdNestedApplCond> list = new Vector<EdNestedApplCond>(this.itsACs.size());
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = this.itsACs.get(i);
			if (ac.getMorphism().isEnabled())
				list.add(ac);
			list.addAll(ac.getEnabledNestedACs());
		}
		return list;
	}
	
	public List<EdNestedApplCond> getEnabledACs() {
		List<EdNestedApplCond> list = new Vector<EdNestedApplCond>(this.itsACs.size());
//		list.add(this);
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = this.itsACs.get(i);
			if (ac.getMorphism().isEnabled())
				list.add(ac);
		}
		return list;
	}
	
	/** Gets my Nested AC specified by the name */
	public EdNestedApplCond getNestedAC(String acname) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = this.itsACs.get(i);
			if (ac.getName().equals(acname))
				return ac;
			else {
				EdNestedApplCond ac1 = ac.getNestedAC(acname);
				if (ac1 != null)
					return ac1;
			}
		}
		return null;
	}
	
	public EdNestedApplCond getNestedAC(int indx) {
		if (indx >= 0 && indx < this.itsACs.size())
			return this.itsACs.get(indx);
		
		return null;
	}
	
	public EdNestedApplCond getApplCondByImageGraph(final Graph g) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond cond = this.itsACs.get(i);
			if (cond.getMorphism().getImage() == g)
				return cond;
		}
		return null;
	}
	
	public int getSizeOfNestedACs() {
		return this.itsACs.size();
	}
	
	/** Creates a new Nested AC layout with name specified by the String nameStr. */
	public EdPAC createNestedAC(String nameStr, boolean isIdentic) {
		if (this.morphism == null
				|| !this.editable)
			return null;
		
		EdNestedApplCond eAC = new EdNestedApplCond(this,
				((NestedApplCond)this.morphism).createNestedAC(), this.typeSet);
		eAC.setName(nameStr);
		eAC.setRule(this.getRule());
		eAC.setGraGra(this.eGra);
		eAC.setEditable(this.isEditable());
		eAC.getBasisGraph().setKind(GraphKind.AC);
		eAC.setUndoManager(this.undoManager);
		eAC.setSourceGraph(this);
		if (isIdentic && eAC.isEditable())
			identicNestedAC(eAC);
		this.itsACs.add(eAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return eAC;
	}

	/**
	 * Creates a new Nested AC layout of the used object specified by the
	 * OrdinaryMorphism ac
	 */
	public EdPAC createNestedAC(OrdinaryMorphism ac) {
		if (this.morphism == null
				|| !this.editable)
			return null;
		
		EdNestedApplCond eAC = new EdNestedApplCond(this, ac, this.typeSet);
		eAC.getBasisGraph().setName(ac.getName());
		eAC.getBasisGraph().setKind(GraphKind.AC);
		eAC.setName(ac.getName());
		eAC.setRule(this.getRule());
		eAC.setGraGra(this.eGra);
		eAC.setUndoManager(this.undoManager);
		eAC.setSourceGraph(this);
		this.itsACs.add(eAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return eAC;
	}

	/**
	 * Adds a new Nested AC layout
	 * @param ac
	 */
	public boolean addNestedAC(EdNestedApplCond ac) {
		if (this.morphism == null)
			return false;
		
		if (ac.getTypeSet().getBasisTypeSet().compareTo(
				this.typeSet.getBasisTypeSet())) {
			if (((NestedApplCond)this.morphism)
					.addNestedAC((NestedApplCond)ac.getMorphism())) {
				Vector<Type> v = ac.getMorphism().getUsedTypes();
				this.morphism.getSource().getTypeSet().adaptTypes(v.elements(), false);
				this.typeSet.refreshTypes();
				ac.getBasisGraph().setKind(GraphKind.AC);
				ac.setRule(this.getRule());
				ac.setGraGra(this.eGra);
				ac.setUndoManager(this.undoManager);
				ac.setSourceGraph((EdGraph)this.getRule().getApplCondByImageGraph(ac.getMorphism().getSource()));
				ac.itsParent = this;
				this.itsACs.add(ac);
				if (this.eGra != null)
					this.eGra.setChanged(true);
				return true;
			} 
			return false;
		} 
		return false;
	}
	
	public EdNestedApplCond getParent() {
		return this.itsParent;
	}
	
	/** Makes an identic nested AC. */
	public void identicNestedAC(EdNestedApplCond ac) {
		OrdinaryMorphism morph = ac.getMorphism();
		// Remove all of my mappings.
		morph.clear();

		// Remove my image.
		morph.getImage().clear();

		// Remove my visible image;
		ac.clear();

		for (int i = 0; i < this.getNodes().size(); i++) {
			EdNode en = this.getNodes().elementAt(i);
			identicNode(en, ac, morph);
		}
		for (int j = 0; j < this.getArcs().size(); j++) {
			EdArc ea = this.getArcs().elementAt(j);
			identicArc(ea, ac, morph);
		}

		this.updateNestedAC(ac);
	}
	
	/**
	 * Sets the layout from another EdGraph. The basis graphs may be different.
	 * The corresponding graph objects are found by its index.
	 */
	public void setLayoutByIndex(EdGraph layout, boolean ofNodesOnly) {
		if (layout instanceof EdNestedApplCond) {
			super.setLayoutByIndex(layout, ofNodesOnly);
			
			if (this.itsACs.size() == ((EdNestedApplCond)layout).getNestedACs().size()) {
				for (int i=0; i<this.itsACs.size(); i++) {
					EdNestedApplCond c = this.itsACs.get(i);
					EdNestedApplCond c1 = ((EdNestedApplCond)layout).getNestedACs().get(i);
					if (c1 != null) {
						c.setLayoutByIndex(c1, ofNodesOnly);
					}
				}
			}
			else {
				for (int i=0; i<this.itsACs.size(); i++) {
					EdNestedApplCond c = this.itsACs.get(i);
					if (c.getParent() == null) 
						c.setLayoutByIndex(c.getRule().getLeft(), ofNodesOnly);
					else 
						c.setLayoutByIndex(c.getParent(), ofNodesOnly);
				}
			}
		}
		else
			super.setLayoutByIndex(layout, ofNodesOnly);
	}
	
	
	/** Updates GAC layout after reading GAC graph objects */
	public void updateNestedAC(EdNestedApplCond ac) {
		EdNode enL = null;
		EdNode enAC = null;
		EdArc eaL = null;
		EdArc eaAC = null;

		ac.clearMarks();

		Enumeration<GraphObject> domain = ac.getMorphism().getDomain();

		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = ac.getMorphism().getImage(bOrig);

			enL = this.findNode(bOrig);
			if (enL != null) {
				if (enL.isMorphismMarkEmpty())
					enL.addMorphismMark(enL.getMyKey());

				enAC = ac.findNode(bImage);
				if (enAC != null) 
					enAC.addMorphismMark(enL.getMorphismMark());
			}

			eaL = this.findArc(bOrig);
			if (eaL != null) {
				if (eaL.isMorphismMarkEmpty())
					eaL.addMorphismMark(eaL.getMyKey());

				eaAC = ac.findArc(bImage);
				if (eaAC != null)
					eaAC.addMorphismMark(eaL.getMorphismMark());
			}
		}
	}
	
	public void updateNestedACs() {		
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = this.itsACs.get(i);
			this.updateNestedAC(ac);
			ac.updateNestedACs();
		}	
	}
	
	private EdNode identicNode(EdNode en, EdGraph eg, OrdinaryMorphism morph) {
		this.badMapping = false;
		this.errMsg = "";

		EdNode cn = null;
		Node bn = null;
		try {
			bn = eg.getBasisGraph().copyNode(en.getBasisNode());
		} catch (TypeException e) {
		}
		if (bn != null) {
			cn = eg.addNode(bn, en.getType());
			cn.setReps(en.getX(), en.getY(), en.isVisible(), false);
			// cn.getLNode().setFrozen(true);
			cn.getLNode().setFrozenByDefault(true);

			eg.addCreatedToUndo(cn);
			eg.undoManagerEndEdit();

			try {
				this.addCreatedMappingToUndo(en, cn);
				
				morph.addMapping(en.getBasisNode(), bn);

				this.undoManagerEndEdit();
			} catch(BadMappingException ex) {
				this.badMapping = true;
				this.errMsg = ex.getMessage();
			}
		}
		return cn;
	}
	
	private EdArc identicArc(EdArc ea, EdGraph eg, OrdinaryMorphism morph) {
		this.badMapping = false;
		this.errMsg = "";

		EdArc ca = null;
		Arc ba = null;

		GraphObject bSrc = morph.getImage(ea.getBasisArc().getSource());
		GraphObject bTar = morph.getImage(ea.getBasisArc().getTarget());

		try {
			ba = eg.getBasisGraph().copyArc(ea.getBasisArc(), (Node) bSrc,
					(Node) bTar);
		} catch (TypeException e) {
			e.printStackTrace();
		}
		if (ba != null) {
			try {
				ca = eg.addArc(ba, ea.getType());
			
				ca.setReps(ea.isDirected(), ea.isVisible(), false);
				ca.setTextOffset(ea.getTextOffset().x, ea.getTextOffset().y);
				if (ea.isLine()) {
					if (ea.hasAnchor()) {
						ca.setAnchor(ea.getAnchor());
						ca.getLArc().setFrozenByDefault(true);
					}
				} else { // is Loop
					if (ea.hasAnchor()) {
						ca.setXY(ea.getX(), ea.getY());
						ca.setWidth(ea.getWidth());
						ca.setHeight(ea.getHeight());
					}
				}
	
				eg.addCreatedToUndo(ca);
				eg.undoManagerEndEdit();
	
				this.errMsg = "";
				try {
					this.addCreatedMappingToUndo(ea, ca);
					
					morph.addMapping(ea.getBasisArc(), ba);
	
					this.undoManagerEndEdit();
				} catch (BadMappingException ex) {
					this.badMapping = true;
					this.errMsg = ex.getMessage();
				}
			} catch (TypeException tex) {
				this.badMapping = true;
				this.errMsg = tex.getMessage();
			}
		}
		return ca;
	}
	
	public Vector<EdGraphObject> getOriginal(EdGraphObject image) {
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(2);
		Enumeration<GraphObject> en = this.morphism.getInverseImage(image.getBasisObject());
		while (en.hasMoreElements()) {
			GraphObject obj = en.nextElement();
			EdGraphObject go = null;
			if (this.itsParent == null) 
				go = this.itsRule.getLeft().findGraphObject(obj);
			else
				go = this.itsParent.findGraphObject(obj);
			if (go != null)
				vec.add(go);
			
		}
		return vec;
	}
	
	public boolean deleteGraphObjectsOfType(
			final EdType t,
			boolean addToUndo) {
		
		boolean alldone = true;		
		for (int n=0; n<this.itsACs.size(); n++) {
			EdNestedApplCond ac = this.itsACs.get(n);
			alldone = alldone && ac.deleteGraphObjectsOfType(t, addToUndo);			
		}
		alldone = alldone && this.deleteGraphObjectsOfTypeFromGraph(t, addToUndo);
		return alldone;
	}
	
	public boolean deleteGraphObjectsOfType(
			final EdGraphObject tgo,
			boolean addToUndo) {
		
		boolean alldone = true;
		for (int n=0; n<this.itsACs.size(); n++) {
			EdNestedApplCond ac = this.itsACs.get(n);
			alldone = alldone && ac.deleteGraphObjectsOfType(tgo, addToUndo);			
		}
		alldone = alldone && this.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo);
		return alldone;
	}
	
	protected List<EdGraphObject> getGraphObjectsOfType(
			final EdGraphObject tgo,
			final EdGraph g) {
		
		List<EdGraphObject> list = new Vector<EdGraphObject>();
		if (tgo.isArc()) {
			for (int i=0; i<g.arcs.size(); i++) {
				EdArc go = g.arcs.get(i);
				if (tgo.getType() == go.getType()
						&& ((EdArc)tgo).getSource().getType().isParentOf(go.getSource().getType())
						&& ((EdArc)tgo).getTarget().getType().isParentOf(go.getTarget().getType())) {
					list.add(go);
				}
			}
		} else {
			for (int i=0; i<g.nodes.size(); i++) {
				EdNode go = g.nodes.get(i);
				if (tgo.getType() == go.getType()) {
					list.add(go);
				}
			}
		}
		return list;
	}
	
	protected List<EdGraphObject> getGraphObjectsOfType(
			final EdType t,
			final EdGraph g) {
		
		List<EdGraphObject> list = new Vector<EdGraphObject>();
		if (t.isArcType()) {
			for (int i=0; i<g.arcs.size(); i++) {
				EdArc go = g.arcs.get(i);
				if (t == go.getType()) {
					list.add(go);
				}
			}
		} else {
			for (int i=0; i<g.nodes.size(); i++) {
				EdNode go = g.nodes.get(i);
				if (t == go.getType()) {
					list.add(go);
				}
			}
		}
		return list;
	}
	
	protected void storeMappingOfGraphObjectsOfType(
			final EdGraphObject tgo,
			final EdGraph src) {
		
		List<EdGraphObject> list = getGraphObjectsOfType(tgo, src);
		
		for (int n=0; n<this.itsACs.size(); n++) {
			EdNestedApplCond ac = this.itsACs.get(n);
//			ac.storeMappingOfGraphObjectsOfType(tgo, ac);
			
			for (int i=0; i<list.size(); i++) {
				EdGraphObject go = list.get(i);
				EdGraphObject rgo = ac.findGraphObject(
							ac.getMorphism().getImage(go.getBasisObject()));			
				if (rgo != null) {			
					ac.itsRule.addDeletedACMappingToUndo(go, rgo);
					ac.itsRule.undoManagerEndEdit();
				}
			}
			ac.storeMappingOfGraphObjectsOfType(tgo, ac);
		}
	}
	
	protected void storeMappingOfGraphObjectsOfType(
			final EdType t,
			final EdGraph src) {
		
		List<EdGraphObject> list = getGraphObjectsOfType(t, src);
		
		for (int n=0; n<this.itsACs.size(); n++) {
			EdNestedApplCond ac = this.itsACs.get(n);
//			ac.storeMappingOfGraphObjectsOfType(t, ac);
			
			for (int i=0; i<list.size(); i++) {
				EdGraphObject go = list.get(i);
				EdGraphObject rgo = ac.findGraphObject(
							ac.getMorphism().getImage(go.getBasisObject()));			
				if (rgo != null) {			
					ac.itsRule.addDeletedACMappingToUndo(go, rgo);
					ac.itsRule.undoManagerEndEdit();
				}
			}
			ac.storeMappingOfGraphObjectsOfType(t, ac);
		}
	}
	
	protected EdGraphObject findRestoredObjectOfAC(EdGraphObject go) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = this.itsACs.get(i);
			EdGraphObject obj = ac.findRestoredObject(go);
			if (obj != null) {
				return obj;
			} else {
				obj = ac.findRestoredObjectOfAC(go);
				if (obj != null) {
					return obj;
				}
			}
		}
		return null;
	}
	
	protected EdGraphObject findGraphObjectOfAC(String goHashCode) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = this.itsACs.get(i);
			EdGraphObject obj = ac.findGraphObject(goHashCode);
			if (obj != null) {
				return obj;
			} else {
				obj = ac.findGraphObjectOfAC(goHashCode);
				if (obj != null) {
					return obj;
				}
			}
		}
		return null;
	}
	
	public void XreadObject(XMLHelper h) {
		super.XreadObject(h);
		
		h.peekObject(this.bGraph, this);
		for (int j = 0; j < this.itsACs.size(); j++) {
			EdPAC ac = this.itsACs.get(j);
			h.enrichObject(ac);
		}		
	}
	
	public void XwriteObject(XMLHelper h) {
		super.XwriteObject(h);
		
		if (h.openObject(this.bGraph, this)) {
			for (int j = 0; j < this.itsACs.size(); j++) {
				h.addObject("", this.itsACs.get(j), true);
			}
		}
	}
	
}

