package agg.editor.impl;

import java.util.BitSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.undo.*;

import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.attribute.impl.CondTuple;
import agg.gui.animation.NodeAnimation;
import agg.util.Pair;


/**
 * An EdRule specifies the rule layout for the used object of the class an
 * agg.xt_basis.Rule. The left and the right hand side and the NACs are objects
 * of the class EdGraph. The rule morphism and the NAC morphism are shown
 * through the same number.
 * 
 * @author $Author: olga $
 * @version $Id: EdRule.java,v 1.65 2010/11/11 17:21:34 olga Exp $
 */
public class EdRule implements XMLObject, StateEditable {

	protected Rule bRule;
	
	protected EdTypeSet typeSet;	
	protected EdGraph eLeft;
	protected EdGraph eRight;
	protected EdGraGra eGra;
	
	protected Vector<EdPAC> itsACs;	
	protected Vector<EdNAC> itsNACs;
	protected Vector<EdPAC> itsPACs;

	protected boolean badMapping;
	protected String errMsg;
	
	protected boolean animated;	
	protected int animationKind;	
	protected boolean editable; // allows to edit rule mappings
	
	// for undo/redo
	protected EditUndoManager undoManager;
	protected StateEdit newEdit;
	protected Pair<String, Vector<String>> undoObj;
	
	

	/** Creates an empty rule layout. The used object is NULL. */
	public EdRule() {
		init(new EdTypeSet());
	}

	protected EdRule(boolean empty) {
		if (!empty) {
			init(new EdTypeSet());
		}
		this.editable = true;
	}
	
	/**
	 * Creates an empty rule layout for the type set specified by the EdTypeSet
	 * types
	 */
	public EdRule(EdTypeSet types) {
		init(types);
	}

	private void init(EdTypeSet types) {
		this.bRule = null;
		this.typeSet = types;
		this.eLeft = new EdGraph(this.typeSet);
		this.eRight = new EdGraph(this.typeSet);		
		initLists();
	}
	
	private void initLists() {
		itsACs = new Vector<EdPAC>();
		itsNACs = new Vector<EdNAC>();
		itsPACs = new Vector<EdPAC>();
		errMsg = "";
	    animationKind = -1;		
		editable = true;
	}
	
	/**
	 * Creates a rule layout with the used object specified by the Rule rule
	 */
	public EdRule(Rule rule) {
		this.bRule = rule;
		this.typeSet = new EdTypeSet(this.bRule.getLeft().getTypeSet());		
		this.eLeft = new EdGraph(this.bRule.getLeft(), this.typeSet);
		this.eRight = new EdGraph(this.bRule.getRight(), this.typeSet);
		initLists();
		createApplConds();
	}

	/**
	 * Creates a rule layout with the used object specified by the Rule rule and
	 * the type set specified by the EdTypeSet types
	 */
	public EdRule(Rule rule, EdTypeSet types) {
		this.bRule = rule;
		this.typeSet = types;		
		this.eLeft = new EdGraph(this.bRule.getLeft(), this.typeSet);
		this.eRight = new EdGraph(this.bRule.getRight(), this.typeSet);
		initLists();
		createApplConds();
	}

	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.itsNACs.trimToSize();
		for (int i = 0; i < this.itsNACs.size(); i++) {
			this.itsNACs.elementAt(i).trimToSize();
		}
		this.itsPACs.trimToSize();
		for (int i = 0; i < this.itsPACs.size(); i++) {
			this.itsPACs.elementAt(i).trimToSize();
		}
		this.itsACs.trimToSize();
		for (int i = 0; i < this.itsACs.size(); i++) {
			this.itsACs.elementAt(i).trimToSize();
		}
	}
	
	/** Allows to edit this rule. */
	public void setEditable(boolean b) {
		this.editable = b;
		this.getLeft().setEditable(b);
		this.getRight().setEditable(b);
		// NACs
		for (int j = 0; j < this.getNACs().size(); j++) {
			EdNAC ac = this.getNACs().elementAt(j);
			ac.setEditable(b);
		}
		// PACs
		for (int j = 0; j < this.getPACs().size(); j++) {
			EdPAC ac = this.getPACs().elementAt(j);
			ac.setEditable(b);
		}
		// GACs
		for (int j = 0; j < this.getNestedACs().size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond) this.getNestedACs().elementAt(j);
			ac.setEditable(b);
		}
	}

	/* Determines whether the mappings can be changed or not. */
	public boolean isEditable() {
		return this.editable;
	}
	
	private void createApplConds() {
//		System.out.println(this.getName()+"::   GACs: "+this.bRule.getNestedACsList().size()
//				+"   NACs: "+this.bRule.getNACsList().size()
//				+"   PACs: "+this.bRule.getPACsList().size());
		final Enumeration<OrdinaryMorphism> acs = this.bRule.getNestedACs();
		while (acs.hasMoreElements()) {		
			final OrdinaryMorphism ac = acs.nextElement();		
			createNestedAC(ac);
		}
		
		final Enumeration<OrdinaryMorphism> nacs = this.bRule.getNACs();
		while (nacs.hasMoreElements()) {		
			final OrdinaryMorphism nac = nacs.nextElement();		
			createNAC(nac);
		}

		final Enumeration<OrdinaryMorphism> pacs = this.bRule.getPACs();
		while (pacs.hasMoreElements()) {		
			final OrdinaryMorphism pac = pacs.nextElement();		
			createPAC(pac);
		}
	}
	
	public void dispose() {				
		while (!this.itsNACs.isEmpty()) {
			this.itsNACs.remove(0).dispose();
		}
		
		while (!this.itsPACs.isEmpty()) {
			this.itsPACs.remove(0).dispose();
		} 		
		
		while (!this.itsACs.isEmpty()) {
			this.itsACs.remove(0).dispose();
		}
		
		this.eLeft.dispose();
		this.eRight.dispose();

		this.eLeft = null;
		this.eRight = null;
		this.bRule = null;
		this.typeSet = null;
		this.eGra = null;
		
		if (this.undoObj != null) {
			this.undoObj.second.clear();
		}
		if (this.newEdit != null) {
			this.newEdit.die();
		}
		this.undoManager = null;
		
//		System.out.println("EdRule.dispose:: DONE  "+this.hashCode());
	}

	public void finalize() {
//		System.out.println("EdRule.finalize()  called  "+this.hashCode());
	}

	public UndoManager getUndoManager() {
		return this.undoManager;
	}

	public void setUndoManager(EditUndoManager anUndoManager) {
		this.undoManager = anUndoManager;
		this.eLeft.setUndoManager(this.undoManager);
		this.eRight.setUndoManager(this.undoManager);
		
		for (int j = 0; j < this.getNACs().size(); j++) {
			this.getNACs().get(j).setUndoManager(this.undoManager);
		}
		for (int j = 0; j < this.getPACs().size(); j++) {
			this.getPACs().get(j).setUndoManager(this.undoManager);
		}
		for (int j = 0; j < this.getNestedACs().size(); j++) {
			this.getNestedACs().get(j).setUndoManager(this.undoManager);
		}
	}

	private boolean undoManagerAddEdit(String presentationName) {
		this.newEdit = new StateEdit(this, presentationName);
		return this.undoManager.addEdit(this.newEdit);
	}

	public void undoManagerEndEdit() {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.undoObj == null) {
			return;
		}
		String addEditKind = this.undoObj.first;
		String kind = "";
		Vector<String> gos = new Vector<String>(this.undoObj.second.size());
		gos.addAll(this.undoObj.second);

		if (addEditKind.equals(EditUndoManager.MAPPING_CREATE_DELETE))
			kind = EditUndoManager.MAPPING_DELETE_CREATE;
		else if (addEditKind.equals(EditUndoManager.MAPPING_DELETE_CREATE))
			kind = EditUndoManager.MAPPING_CREATE_DELETE;
		else if (addEditKind.equals(EditUndoManager.NAC_MAPPING_CREATE_DELETE))
			kind = EditUndoManager.NAC_MAPPING_DELETE_CREATE;
		else if (addEditKind.equals(EditUndoManager.NAC_MAPPING_DELETE_CREATE))
			kind = EditUndoManager.NAC_MAPPING_CREATE_DELETE;
		else if (addEditKind.equals(EditUndoManager.MATCH_MAPPING_CREATE_DELETE))
			kind = EditUndoManager.MATCH_MAPPING_DELETE_CREATE;
		else if (addEditKind.equals(EditUndoManager.MATCH_MAPPING_DELETE_CREATE))
			kind = EditUndoManager.MATCH_MAPPING_CREATE_DELETE;
		else if (addEditKind
				.equals(EditUndoManager.MATCH_COMPLETION_MAPPING_CREATE_DELETE)) {
			kind = EditUndoManager.MATCH_COMPLETION_MAPPING_DELETE_CREATE;
			gos = this.makeUndoObjectOfMatchCompletionMapping();
		} else if (addEditKind
				.equals(EditUndoManager.MATCH_COMPLETION_MAPPING_DELETE_CREATE)) {
			kind = EditUndoManager.MATCH_COMPLETION_MAPPING_CREATE_DELETE;
			gos = this.makeUndoObjectOfMatchCompletionMapping();
		}
		if (!kind.equals(""))
			endEdit(gos, kind);
	}

	private void endEdit(Vector<String> gos, String kind) {
		this.undoObj = new Pair<String, Vector<String>>(kind, gos);
		this.undoManager.end(this.newEdit);
	}

	public void undoManagerLastEditDie() {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()) {
			return;
		}
		this.undoManager.lastEditDie();
	}

	protected void addEdit(EdGraphObject src, EdGraphObject tar, String kind,
			String presentation) {
		Vector<String> v = new Vector<String>();
		v.add(String.valueOf(src.hashCode()));
		v.add(String.valueOf(tar.hashCode()));
		this.undoObj = new Pair<String, Vector<String>>(kind, v);
		undoManagerAddEdit(presentation);
	}

	public void addCreatedMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.getMorphism() == null) {
			return;
		}
		GraphObject oldImg = null;
		if (this instanceof EdAtomic) {
			oldImg = ((EdAtomic) this).getBasisAtomic().getImage(
					src.getBasisObject());
		} else {
			oldImg = this.bRule.getImage(src.getBasisObject());
		}
		if (oldImg != null) {
			EdGraphObject go = this.eRight.findGraphObject(oldImg);
			if (go != null)
				addDeletedMappingToUndo(src, go);
		}
		addEdit(src, tar, EditUndoManager.MAPPING_CREATE_DELETE,
				"Undo Create Rule Mapping");
	}

	public void addDeletedMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.getMorphism() == null) {
			return;
		}
		if (tar.isNode()) {
			OrdinaryMorphism morph = this.getMorphism(); 			
			addDeletedMappingOfInOutEdgesToUndo((EdNode) src, (EdNode) tar, 
					src.getContext(), tar.getContext(), morph,
					EditUndoManager.MAPPING_DELETE_CREATE,
					"Undo Delete Rule Mapping");			
		}
		addEdit(src, tar, EditUndoManager.MAPPING_DELETE_CREATE,
				"Undo Delete Rule Mapping");
	}
	
	public void addCreatedNACMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null) {
			return;
		}
		EdNAC nac = (EdNAC) tar.getContext();
		GraphObject oldImg = nac.getMorphism().getImage(src.getBasisObject());
		if (oldImg != null) {
			EdGraphObject go = nac.findGraphObject(oldImg);
			if (go != null) {
				addDeletedNACMappingToUndo(src, go);
			}
		}
		addEdit(src, tar, EditUndoManager.NAC_MAPPING_CREATE_DELETE,
				"Undo Create NAC Mapping");
	}

	public void addDeletedNACMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null) {
			return;
		}
		if (tar.isNode()) {
			addDeletedMappingOfInOutEdgesToUndo((EdNode) src, (EdNode) tar, src
					.getContext(), tar.getContext(), ((EdNAC) tar.getContext())
					.getMorphism(), EditUndoManager.NAC_MAPPING_DELETE_CREATE,
					"Undo Delete NAC Mapping");
		}

		addEdit(src, tar, EditUndoManager.NAC_MAPPING_DELETE_CREATE,
				"Undo Delete NAC Mapping");
	}

	public void addCreatedPACMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null) {
			return;
		}
		EdPAC pac = (EdPAC) tar.getContext();
		GraphObject oldImg = pac.getMorphism().getImage(src.getBasisObject());
		if (oldImg != null) {
			EdGraphObject go = pac.findGraphObject(oldImg);
			if (go != null) {
				addDeletedPACMappingToUndo(src, go);
			}
		}
		addEdit(src, tar, EditUndoManager.PAC_MAPPING_CREATE_DELETE,
				"Undo Create PAC Mapping");
	}

	public void addDeletedPACMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null) {
			return;
		}
		if (tar.isNode()) {
			addDeletedMappingOfInOutEdgesToUndo((EdNode) src, (EdNode) tar, src
					.getContext(), tar.getContext(), ((EdPAC) tar.getContext())
					.getMorphism(), EditUndoManager.PAC_MAPPING_DELETE_CREATE,
					"Undo Delete PAC Mapping");
		}
		addEdit(src, tar, EditUndoManager.PAC_MAPPING_DELETE_CREATE,
				"Undo Delete PAC Mapping");
	}

	public void addCreatedACMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null) {
			return;
		}
		EdPAC ac = (EdPAC) tar.getContext();
		GraphObject oldImg = ac.getMorphism().getImage(src.getBasisObject());
		if (oldImg != null) {
			EdGraphObject go = ac.findGraphObject(oldImg);
			if (go != null) {
				addDeletedACMappingToUndo(src, go);
			}
		}
		addEdit(src, tar, EditUndoManager.AC_MAPPING_CREATE_DELETE,
				"Undo Create AC Mapping");
	}

	public void addDeletedACMappingToUndo(EdGraphObject src, EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null) {
			return;
		}
		if (tar.isNode()) {
			addDeletedMappingOfInOutEdgesToUndo((EdNode) src, (EdNode) tar, src
					.getContext(), tar.getContext(), ((EdPAC) tar.getContext())
					.getMorphism(), EditUndoManager.AC_MAPPING_DELETE_CREATE,
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

	public void addCreatedMatchMappingToUndo(EdGraphObject src,
			EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null || this.bRule.getMatch() == null) {
			return;
		}
		GraphObject oldImg = this.bRule.getMatch().getImage(src.getBasisObject());
		if (oldImg != null) {
			EdGraphObject go = this.eGra.getGraphOf(this.bRule.getMatch().getImage())
					.findGraphObject(oldImg);
			if (go != null) {
				addDeletedMatchMappingToUndo(src, go);
			}
		}
		addEdit(src, tar, EditUndoManager.MATCH_MAPPING_CREATE_DELETE,
				"Undo Create Match Mapping");
	}

	public void addDeletedMatchMappingToUndo(EdGraphObject src,
			EdGraphObject tar) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null || this.bRule.getMatch() == null) {
			return;
		}
		addEdit(src, tar, EditUndoManager.MATCH_MAPPING_DELETE_CREATE,
				"Undo Delete Match Mapping");
	}

	public void addCreatedMatchMappingToUndo() {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null || this.bRule.getMatch() == null) {
			return;
		}
		String kind = EditUndoManager.MATCH_COMPLETION_MAPPING_CREATE_DELETE;
		Vector<String> v = makeUndoObjectOfMatchCompletionMapping();
		this.undoObj = new Pair<String, Vector<String>>(kind, v);
		undoManagerAddEdit("Undo Create Match Completion Mapping");
	}

	public void addDeletedMatchMappingToUndo() {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.eLeft.isEditable()
				|| this.bRule == null || this.bRule.getMatch() == null) {
			return;
		}
		String kind = EditUndoManager.MATCH_COMPLETION_MAPPING_DELETE_CREATE;
		Vector<String> v = makeUndoObjectOfMatchCompletionMapping();
		this.undoObj = new Pair<String, Vector<String>>(kind, v);
		undoManagerAddEdit("Undo Delete Match Completion Mapping");
	}

	/*
	 * get(i) - hashCode string of the source of mapping
	 * get(i+1) - hashCode string of the target of mapping
	 */
	private Vector<String> makeUndoObjectOfMatchCompletionMapping() {
		Vector<String> pairs = new Vector<String>();
		if (this.bRule == null || this.bRule.getMatch() == null) {
			return pairs;
		}
		EdGraph hostgraph = this.eGra.getGraphOf(this.bRule.getMatch().getImage());
		Vector<EdNode> vec = this.eLeft.getNodes();
		for (int i = 0; i < vec.size(); i++) {
			EdGraphObject lgo = vec.get(i);
			GraphObject img = this.bRule.getMatch().getImage(lgo.getBasisObject());
			if (img != null) {
				EdGraphObject ggo = hostgraph.findGraphObject(img);				
				pairs.add(String.valueOf(lgo.hashCode()));
				pairs.add(String.valueOf(ggo.hashCode()));
			} else {
				pairs.add(String.valueOf(lgo.hashCode()));
				pairs.add("");
			}
		}
		Vector<EdArc> vec1 = this.eLeft.getArcs();
		for (int i = 0; i < vec1.size(); i++) {
			EdGraphObject lgo = vec1.get(i);
			GraphObject img = this.bRule.getMatch().getImage(lgo.getBasisObject());
			if (img != null) {
				EdGraphObject ggo = hostgraph.findGraphObject(img);
				pairs.add(String.valueOf(lgo.hashCode()));
				pairs.add(String.valueOf(ggo.hashCode()));
			} else {
				pairs.add(String.valueOf(lgo.hashCode()));
				pairs.add("");
			}
		}
		return pairs;
	}

	public void storeState(Hashtable<Object, Object> state) {
		if (this.undoObj != null 
				&& this.undoObj.first != null 
				&& this.undoObj.second != null) {
			state.put(this, this.undoObj);
		}
	}

	public void restoreState(Hashtable<?, ?> state) {
		Object obj = state.get(this);
		if (obj == null || !(obj instanceof Pair)) {
			return;
		}
		
		String op = (String) ((Pair<?,?>) obj).first;

		// System.out.println("\nEdRule.restoreState... state:
		// "+state.hashCode()+ " "+op+" "+state.size());

		if (op.equals(EditUndoManager.MATCH_COMPLETION_MAPPING_CREATE_DELETE)) {
			// System.out.println("operation::
			// MATCH_COMPLETION_MAPPING_CREATE_DELETE");
			if (this.bRule.getMatch() == null) {
				this.eGra.getBasisGraGra().createMatch(this.bRule);
			}

			EdGraph hostGraph = this.eGra.getGraphOf(this.bRule.getMatch()
						.getTarget());
			if (hostGraph != null) {
				Vector<?> vec = (Vector<?>) ((Pair<?,?>) obj).second;
				for (int i = vec.size()-2; i >= 0; i=i-2) {	
					String lobjHC = (String) vec.get(i);
					EdGraphObject lobj = this.eLeft.findGraphObject(lobjHC);
					removeMatchMapping(lobj);
				}
				for (int i = 0; i < vec.size(); i=i+2) {
					String lobjHC = (String) vec.get(i);
					String imgHC = (String) vec.get(i+1);				
					EdGraphObject lobj = this.eLeft.findGraphObject(lobjHC);					
					// reset stored match mapping
					if (!"".equals(imgHC)) {
						EdGraphObject graphObj = hostGraph
									.findGraphObject(imgHC);
						if (graphObj != null && lobj != null) {
							this.interactMatch(lobj, graphObj);
						}
					}
				}
			}
		} else if (op
				.equals(EditUndoManager.MATCH_COMPLETION_MAPPING_DELETE_CREATE)) {
			// System.out.println("operation::
			// MATCH_COMPLETION_MAPPING_DELETE_CREATE");

			if (this.bRule.getMatch() == null) {
				this.eGra.getBasisGraGra().createMatch(this.bRule);
			}
			if (this.bRule.getMatch() != null) {
				EdGraph hostGraph = this.eGra.getGraphOf(this.bRule.getMatch()
						.getTarget());
				if (hostGraph != null) {
					Vector<?> vec = (Vector<?>) ((Pair<?,?>) obj).second;
					for (int i = vec.size()-2; i >= 0; i=i-2) {	
						String lobjHC = (String) vec.get(i);
						EdGraphObject lobj = this.eLeft.findGraphObject(lobjHC);
						removeMatchMapping(lobj);
					}
					for (int i = 0; i < vec.size(); i=i+2) {
						String lobjHC = (String) vec.get(i);
						String imgHC = (String) vec.get(i+1);
						EdGraphObject lobj = this.eLeft.findGraphObject(lobjHC);					
						// reset stored match mapping
						if (!"".equals(imgHC)) {					
							EdGraphObject graphObj = hostGraph
									.findGraphObject(imgHC);
							if (graphObj != null && lobj != null) {
								this.interactMatch(lobj, graphObj);
							}
						}
					}
				}
			}
		} else {
			Vector<?> vec = (Vector<?>) ((Pair<?,?>) obj).second;
			if (!vec.isEmpty() && vec.size() == 2) {
				String objHC = (String) vec.get(0);
				String imgHC = (String) vec.get(1);
				if (op.equals(EditUndoManager.MAPPING_CREATE_DELETE)) {	
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					this.propagateRemoveRuleMappingToMultiRule(lObj);
					this.removeRuleMapping(lObj);
				} else if (op.equals(EditUndoManager.MAPPING_DELETE_CREATE)) {
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					EdGraphObject rObj = this.eRight.findGraphObject(imgHC);
					if (lObj != null && rObj != null) {
						this.interactRule(lObj, rObj);
						this.propagateAddRuleMappingToMultiRule(lObj, rObj);
					}
				} else if (op.equals(EditUndoManager.NAC_MAPPING_CREATE_DELETE)) {
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					EdGraphObject nacObj = this.findGraphObjectOfNAC(imgHC);
					if (lObj != null && nacObj != null && nacObj.getContext() != null) {
						EdNAC nac = (EdNAC) nacObj.getContext();
						this.removeNACMapping(lObj, nac.getMorphism());
					}
				} else if (op.equals(EditUndoManager.NAC_MAPPING_DELETE_CREATE)) {
//					System.out.println("EdRule.restoreState: NAC_MAPPING_DELETE_CREATE   "+this.getName());
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					EdGraphObject nacObj = findGraphObjectOfNAC(imgHC);
//					System.out.println("EdRule.restoreState: NAC_MAPPING_DELETE_CREATE  "+lObj+"     "+nacObj);
					if (lObj != null && nacObj != null && nacObj.getContext() != null) {
						EdNAC nac = (EdNAC) nacObj.getContext();
						this.interactNAC(lObj, nacObj, nac.getMorphism());
					} 
				} else if (op.equals(EditUndoManager.PAC_MAPPING_CREATE_DELETE)) {
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					EdGraphObject pacObj = this.findGraphObjectOfPAC(imgHC);
					if (lObj != null && pacObj != null && pacObj.getContext() != null) {
						EdPAC pac = (EdPAC) pacObj.getContext();
						this.removePACMapping(lObj, pac.getMorphism());
					}
				} else if (op.equals(EditUndoManager.PAC_MAPPING_DELETE_CREATE)) {
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					EdGraphObject pacObj = this.findGraphObjectOfPAC(imgHC);
					if (lObj != null && pacObj != null && pacObj.getContext() != null) {
						EdPAC pac = (EdPAC) pacObj.getContext();
						this.interactPAC(lObj, pacObj, pac.getMorphism());
					}
				} else if (op.equals(EditUndoManager.AC_MAPPING_CREATE_DELETE)) {
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					if (lObj == null)
						lObj = this.findGraphObjectOfAC(objHC);
					EdGraphObject acObj = this.findGraphObjectOfAC(imgHC);
//					System.out.println("EdRule.restoreState: AC_MAPPING_CREATE_DELETE  "+lObj+"     "+acObj);
					if (lObj != null 
							&& acObj != null && acObj.getContext() != null) {
						EdNestedApplCond ac = (EdNestedApplCond) acObj.getContext();
						this.removeNestedACMapping(lObj, ac.getNestedMorphism());
					} 
				} else if (op.equals(EditUndoManager.AC_MAPPING_DELETE_CREATE)) {
					EdGraphObject lObj = this.eLeft.findGraphObject(objHC);
					if (lObj == null)
						lObj = this.findGraphObjectOfAC(objHC);
					EdGraphObject acObj = this.findGraphObjectOfAC(imgHC);
//					System.out.println("EdRule.restoreState: AC_MAPPING_DELETE_CREATE  "+lObj+"     "+acObj);
					if (lObj != null && lObj.getContext() != null
							&& acObj != null && acObj.getContext() != null) {
						EdNestedApplCond ac = (EdNestedApplCond) acObj.getContext();
						this.interactNestedAC(lObj, acObj, ac.getNestedMorphism());
					} 
				} else if (op
						.equals(EditUndoManager.MATCH_MAPPING_CREATE_DELETE)) {
					if (this.bRule.getMatch() != null) {
						EdGraphObject lobj = this.eLeft.findGraphObject(objHC);	
						this.removeMatchMapping(lobj);
					}
				} else if (op
						.equals(EditUndoManager.MATCH_MAPPING_DELETE_CREATE)) {
					if (this.bRule.getMatch() == null) {
						this.eGra.getBasisGraGra().createMatch(this.bRule);
					}
					EdGraph hostGraph = this.eGra.getGraphOf(this.bRule.getMatch()
								.getTarget());
					EdGraphObject leftObj = this.eLeft.findGraphObject(objHC);
					EdGraphObject graphObj = hostGraph
								.findGraphObject(imgHC);
					this.interactMatch(leftObj, graphObj);
				} 				
			}
		}
	}

	public void propagateAddRuleMappingToMultiRule(EdGraphObject lobj, EdGraphObject robj) {
		if (this.bRule instanceof KernelRule) {
			EdRuleScheme rs = this.eGra.getRuleScheme(this.bRule);
			if (rs != null) {					
				rs.propagateAddMappingToMultiRule(lobj, robj);					
			}
		}
	}

	public void propagateRemoveRuleMappingToMultiRule(EdGraphObject obj) {
		if (this.bRule instanceof KernelRule) {
			EdRuleScheme rs = this.eGra.getRuleScheme(this.bRule);
			if (rs != null) {	
				if (obj.getContext().getBasisGraph() == this.bRule.getLeft())
					rs.propagateRemoveMappingToMultiRule(obj, true);
				else if (obj.getContext().getBasisGraph() == this.bRule.getRight())
					rs.propagateRemoveMappingToMultiRule(obj, false);
			}
		}
	}
	
	protected EdGraphObject findRestoredObjectOfNAC(EdGraphObject go) {
		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC nac = this.itsNACs.get(i);
			EdGraphObject obj = nac.findRestoredObject(go);
			if (obj != null)
				return obj;
		}
		return null;
	}

	protected EdGraphObject findGraphObjectOfNAC(String goHashCode) {
		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC nac = this.itsNACs.get(i);
			EdGraphObject obj = nac.findGraphObject(goHashCode);
			if (obj != null)
				return obj;
		}
		return null;
	}
	
	protected EdGraphObject findRestoredObjectOfPAC(EdGraphObject go) {
		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC pac = this.itsPACs.get(i);
			EdGraphObject obj = pac.findRestoredObject(go);
			if (obj != null)
				return obj;
		}
		return null;
	}

	protected EdGraphObject findGraphObjectOfPAC(String goHashCode) {
		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC pac = this.itsPACs.get(i);
			EdGraphObject obj = pac.findGraphObject(goHashCode);
			if (obj != null)
				return obj;
		}
		return null;
	}
	
	protected EdGraphObject findRestoredObjectOfAC(EdGraphObject go) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = (EdNestedApplCond)this.itsACs.get(i);
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
			EdNestedApplCond ac = (EdNestedApplCond)this.itsACs.get(i);
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
	
	/** Sets my basis rule to null. */
	public void unsetBasisRule() {
		this.bRule = null;
	}

		
	/** Clears myself */
	public void clear() {
		this.eLeft.clear();
		this.eRight.clear();
		
		for (int i = 0; i < this.itsNACs.size(); i++) 
			this.itsNACs.elementAt(i).clear();
		this.itsNACs.clear();
		
		for (int i = 0; i < this.itsPACs.size(); i++)
			this.itsPACs.elementAt(i).clear();
		this.itsPACs.clear();
		
		for (int i = 0; i < this.itsACs.size(); i++)
			this.itsACs.elementAt(i).clear();
		this.itsACs.clear();
	}

	/**
	 * Returns the name of my basis Rule or an empty string.
	 */
	public String getName() {
		if (this.bRule != null)
			return this.bRule.getName();
		
		return "";
	}

	/** Gets my used object */
	public Rule getBasisRule() {
		return this.bRule;
	}

	/**
	 * Get the underlying morphism.
	 */
	public OrdinaryMorphism getMorphism() {
		if (this instanceof EdAtomic)
			return ((EdAtomic) this).getMorphism();
		
		return this.bRule;
	}

	/** Gets my left hand side */
	public EdGraph getLeft() {
		return this.eLeft;
	}

	/** Gets my right hand side */
	public EdGraph getRight() {
		return this.eRight;
	}

	/** Gets my gragra layout */
	public EdGraGra getGraGra() {
		return this.eGra;
	}

	/** Sets my gragra layout */
	public void setGraGra(EdGraGra egra) {
		this.eGra = egra;
		if (egra != null) {
			this.eLeft.setGraGra(egra);
			this.eRight.setGraGra(egra);
			this.typeSet = egra.getTypeSet();
			for (int i = 0; i < this.itsNACs.size(); i++) {
				EdNAC ac = this.itsNACs.elementAt(i);
				ac.setGraGra(egra);
				ac.setTypeSet(egra.getTypeSet());
			}
			for (int i = 0; i < this.itsPACs.size(); i++) {
				EdPAC ac = this.itsPACs.elementAt(i);
				ac.setGraGra(egra);
				ac.setTypeSet(egra.getTypeSet());
			}
			for (int i = 0; i < this.itsACs.size(); i++) {
				EdPAC ac = this.itsACs.elementAt(i);
				ac.setGraGra(egra);
				ac.setTypeSet(egra.getTypeSet());
			}
		}
	}

	/** Gets my type set */
	public EdTypeSet getTypeSet() {
		return this.typeSet;
	}

	/** Sets my type set to the set specified by the EdTypeSet types */
	public void setTypeSet(EdTypeSet types) {
		this.typeSet = types;
		if (types != null) {
			this.eLeft.setTypeSet(types);
			this.eRight.setTypeSet(types);
			for (int i = 0; i < this.itsNACs.size(); i++) {
				EdNAC ac = this.itsNACs.elementAt(i);
				ac.setTypeSet(types);
			}
			for (int i = 0; i < this.itsPACs.size(); i++) {
				EdPAC ac = this.itsPACs.elementAt(i);
				ac.setTypeSet(types);
			}
			for (int i = 0; i < this.itsACs.size(); i++) {
				EdPAC ac = this.itsACs.elementAt(i);
				ac.setTypeSet(types);
			}
			this.typeSet = types;
		}
	}

	public boolean isApplicable() {
		if (this.bRule != null)
			return this.bRule.isApplicable();
		
		return true;

	}

	public void setApplicable(boolean applicable) {
		if (this.bRule != null)
			this.bRule.setApplicable(applicable);
	}

	public void setAnimated(boolean b) {
		this.animated = b;
		if (this.animated) {
			this.animationKind = -1;
			
			// a new node will be created
			Vector<EdNode> nodes = this.eRight.getNodes();
			for (int i=0; i<nodes.size(); i++) {
				EdNode n = nodes.get(i);
				if (!this.bRule.getInverseImage(n.getBasisNode()).hasMoreElements()) {
					if (n.getType().isAnimated()) {
						this.animationKind = n.getType().getAnimationKind();					
						break;
					}
				}
			}
			if (this.animationKind == -1) {
				for (int i=0; i<nodes.size(); i++) {
					EdNode n = nodes.get(i);
					if (this.bRule.getInverseImage(n.getBasisNode()).hasMoreElements()) {
						if (n.getBasisNode().getOutgoingArcsSet().size() == 1) {
							Iterator<Arc> arcs = n.getBasisNode().getOutgoingArcsSet().iterator();
							if (!this.bRule.getInverseImage(arcs.next()).hasMoreElements()) {
								if (n.getType().isAnimated()) {
									this.animationKind = n.getType().getAnimationKind();					
									break;
								}
							}
						}
					}
				}
			}
			if (this.animationKind == -1) {
				// a node will jump
				this.animationKind = NodeAnimation.JUMP;
			}
			this.eGra.setAnimated(true);
		} else {
			this.animationKind = -1;
			if (this.eGra.isAnimated())
				this.eGra.resetAnimated(false);
		}
	}
	
	public int getAnimationKind() {
		return this.animationKind;
	}
	
	public boolean isAnimated() {
		return this.animated;		
	}
		
	public Vector<EdNode> getAbstractNodesOfRHSToCreate() {
		Vector<EdNode> result = new Vector<EdNode>();
		Vector<EdNode> nodes = this.eRight.getNodes();
		for (int i=0; i<nodes.size(); i++) {
			EdNode n = nodes.get(i);
			if (!this.bRule.getInverseImage(n.getBasisNode()).hasMoreElements()) {
				if (n.getType().getBasisType().isAbstract()) {
					result.add(n);
				}
			}
		}
		return result;
	}
	
	/** Gets the current match */
	public Match getMatch() {
		if (this.bRule != null)
			return this.bRule.getMatch();
	
		return null;
	}

	public EdGraphObject getImage(EdGraphObject orig) {
		GraphObject im = this.bRule.getImage(orig.getBasisObject());
		return this.eRight.findGraphObject(im);
	}

	public Vector<EdGraphObject> getOriginal(EdGraphObject image) {
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(2);
		Enumeration<GraphObject> en = this.bRule.getInverseImage(image.getBasisObject());
		while (en.hasMoreElements()) {
			GraphObject or = en.nextElement();
			EdGraphObject go = this.eLeft.findGraphObject(or);
			if (go != null)
				vec.add(go);
		}
		return vec;
	}

	public Vector<String> getAttrConditions() {
		Vector<String> conds = new Vector<String>(1);
		if (this.bRule == null)
			return conds;
		
		CondTuple ct = (CondTuple) this.bRule.getAttrContext().getConditions();
		for (int i = 0; i < ct.getSize(); i++) {
			conds.add(ct.getCondMemberAt(i).getExprAsText());
		}
		return conds;
	}

	public Object getApplCondByImageGraph(Graph g) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond cond = (EdNestedApplCond) this.itsACs.elementAt(i);
			if (cond.getMorphism().getImage() == g)
				return cond;
		}
		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC cond = this.itsPACs.elementAt(i);
			if (cond.getMorphism().getImage() == g)
				return cond;
		}
		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC cond = this.itsNACs.elementAt(i);
			if (cond.getMorphism().getImage() == g)
				return cond;
		}
		
		return null;
	}
	
	
	public boolean hasNACs() {
		if (!this.itsNACs.isEmpty())
			return true;
		
		return false;
	}

	/** Gets my NACs */
	public Vector<EdNAC> getNACs() {
		return this.itsNACs;
	}

	/** Gets my NAC specified by the index */
	public EdNAC getNAC(int index) {
		for (int i = 0; i < this.itsNACs.size(); i++) {
			if (i == index)
				return this.itsNACs.elementAt(i);
		}
		return null;
	}

	/** Gets my NAC specified by the name */
	public EdNAC getNAC(String nacname) {
		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC nac = this.itsNACs.elementAt(i);
			if (nac.getName().equals(nacname))
				return nac;
		}
		return null;
	}

	/** Gets my NAC specified by the morphism */
	public EdNAC getNAC(OrdinaryMorphism morphism) {
		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC nac = this.itsNACs.elementAt(i);
			if (nac.getMorphism() == morphism)
				return nac;
		}
		return null;
	}

	/** Creates a new NAC layout with name specified by the String nameStr. */
	public EdNAC createNAC(String nameStr, boolean isIdentic) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdNAC nac = new EdNAC(this.bRule.createNAC(), this.typeSet);
		nac.getBasisGraph().setName(nameStr);
		nac.setName(nameStr);
		nac.setRule(this);
		nac.setGraGra(this.eGra);
		nac.setEditable(this.eLeft.isEditable());
		nac.setUndoManager(this.undoManager);
		if (isIdentic && nac.isEditable())
			identicNAC(nac);
		this.itsNACs.addElement(nac);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return nac;
	}

	/**
	 * Creates a new NAC layout of the used object specified by the
	 * OrdinaryMorphism nac
	 */
	public EdNAC createNAC(OrdinaryMorphism nac) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdNAC eNAC = new EdNAC(nac, this.typeSet);
		eNAC.getBasisGraph().setName(nac.getName());
		eNAC.setName(nac.getName());
		eNAC.setRule(this);
		eNAC.setGraGra(this.eGra);
		eNAC.setUndoManager(this.undoManager);
		this.itsNACs.addElement(eNAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);

		return eNAC;
	}

	/**
	 * Creates a new NAC layout of a NAC object which is constructed
	 * from the RHS of this rule.
	 */
	public EdNAC createNACDuetoRHS(String nameStr) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdNAC eNAC = new EdNAC(this.bRule.createNAC(), this.typeSet);
		eNAC.getBasisGraph().setName(nameStr);
		eNAC.setName(nameStr);
		eNAC.setRule(this);
		eNAC.setGraGra(this.eGra);
		eNAC.setEditable(this.eLeft.isEditable());
		eNAC.setUndoManager(this.undoManager);	
		this.makeNACDuetoRHS(eNAC);
		this.itsNACs.addElement(eNAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);

		return eNAC;
	}
	
	public boolean addNAC(EdNAC nac) {
		if (this.bRule == null)
			return false;
		boolean sameTypes = nac.getTypeSet().getBasisTypeSet() == this.typeSet.getBasisTypeSet();
		if (sameTypes || nac.getTypeSet().getBasisTypeSet().compareTo(
				this.typeSet.getBasisTypeSet())) {
			if (this.bRule.addNAC(nac.getMorphism())) {
				if (!sameTypes) {
					this.bRule.getLeft().getTypeSet().adaptTypes(nac.getTypeSet().getBasisTypeSet(), false);
					this.typeSet.refreshTypes();
					// nac.update();
				}
				nac.setRule(this);
				nac.setGraGra(this.eGra);
				nac.setUndoManager(this.undoManager);
				this.itsNACs.addElement(nac);
				if (this.eGra != null)
					this.eGra.setChanged(true);
				return true;
			} 
		} 
		return false;
	}

	/**
	 * Destroys the specified EdNAC enac.
	 */
	public void destroyNAC(EdNAC enac) {
		if (this.bRule == null || enac.getMorphism() == null)
			return;
		enac.getMorphism().deleteObserver(enac);
		this.itsNACs.removeElement(enac);
		getBasisRule().destroyNAC(enac.getMorphism());
		if (this.eGra != null)
			this.eGra.setChanged(true);
	}

	/** Makes an identic NAC from NAC specified by the EdNAC enac */
	public void identicNAC(EdNAC enac) {
		OrdinaryMorphism morph = enac.getMorphism();
		// Remove all of my mappings.
		morph.clear();

		// Remove my image.
		morph.getImage().clear();

		// Remove my visible image;
		enac.clear();

		for (int i = 0; i < this.eLeft.getNodes().size(); i++) {
			EdNode en = this.eLeft.getNodes().elementAt(i);
			identicNode(en, enac, morph);
		}
		for (int j = 0; j < this.eLeft.getArcs().size(); j++) {
			EdArc ea = this.eLeft.getArcs().elementAt(j);
			identicArc(ea, enac, morph);
		}
		this.updateRule();
		this.updateNAC(enac);
	}

	public void addIdenticToNAC(Vector<EdGraphObject> graphObjects, EdNAC enac) {
		for (int i = 0; i < graphObjects.size(); i++) {
			EdGraphObject go = graphObjects.elementAt(i);
			if (go.isNode())
				identicNode((EdNode) go, enac, enac.getMorphism());
			else
				identicArc((EdArc) go, enac, enac.getMorphism());
		}
		this.update();
	}

	public EdGraphObject addIdenticToNAC(EdGraphObject graphObject, EdNAC enac) {
		EdGraphObject go;
		if (graphObject.isNode())
			go = identicNode((EdNode) graphObject, enac, enac.getMorphism());
		else
			go = identicArc((EdArc) graphObject, enac, enac.getMorphism());
		this.update();
		return go;
	}

	/** Rewrites the specified NAC by the copy of the RHS. */
	public void makeNACDuetoRHS(EdNAC enac) {
		OrdinaryMorphism morph = enac.getMorphism();
		// Remove all of my mappings.
		morph.clear();
		// Remove my image.
		morph.getImage().clear();
		// Remove my visible image;
		enac.clear();
		
		makeACDuetoRHS(enac, morph);
		this.updateRule();
		this.updateNAC(enac);
	}
	
	private void makeACDuetoRHS(final EdGraph enac, final OrdinaryMorphism morph) {	
		HashMap<EdNode,EdNode> map = new HashMap<EdNode,EdNode>();
		for (int i = 0; i < this.eRight.getNodes().size(); i++) {			
			EdNode enr = this.eRight.getNodes().elementAt(i);
			List<EdGraphObject> l = this.getOriginal(enr);
			if (!l.isEmpty()) {				
				EdNode en = identicNode((EdNode)l.get(0), enac, morph);
				en.setReps(enr.getX(), enr.getY(), enr.isVisible(), false);
				map.put(enr, en);
				for (int j=1; j<l.size(); j++) {
					EdNode enj = (EdNode)l.get(j);
					try {						
						this.addCreatedNACMappingToUndo(enj, en);
						morph.addMapping(enj.getBasisNode(), en.getBasisNode());
						this.undoManagerEndEdit();
					} catch (BadMappingException ex) {}
				}
			}
			else {
				try {
					Node bn = enac.getBasisGraph().copyNode(enr.getBasisNode());
					if (bn.getAttribute() != null)
						((agg.attribute.impl.ValueTuple)bn.getAttribute()).unsetValueAsExpr();
					EdNode en = enac.addNode(bn, enr.getType());
					en.setReps(enr.getX(), enr.getY(), enr.isVisible(), false);
					en.getLNode().setFrozenByDefault(true);
					enac.addCreatedToUndo(en);
					enac.undoManagerEndEdit();
					map.put(enr, en);
				} catch (TypeException e) {}
			}
		}
		for (int i = 0; i < this.eRight.getArcs().size(); i++) {
			EdArc ear = this.eRight.getArcs().elementAt(i);
			List<EdGraphObject> l = this.getOriginal(ear);
			if (!l.isEmpty()) {	
				EdArc ea = identicArc((EdArc)l.get(0), enac, morph);
				ea.setTextOffset(ear.getTextOffset().x, ear.getTextOffset().y);
				if (ear.isLine()) {
					if (ear.hasAnchor()) {
						ea.setAnchor(ear.getAnchor());
						ea.getLArc().setFrozenByDefault(true);
					}
				} else { // is Loop
					if (ear.hasAnchor()) {
						ea.setXY(ear.getX(), ear.getY());
						ea.setWidth(ear.getWidth());
						ea.setHeight(ear.getHeight());
					}
				}
				for (int j=1; j<l.size(); j++) {
					EdArc eaj = (EdArc)l.get(j);
					try {						
						this.addCreatedNACMappingToUndo(eaj, ea);
						morph.addMapping(eaj.getBasisArc(), ea.getBasisArc());
						this.undoManagerEndEdit();
					} catch (BadMappingException ex) {}
				}
			}
			else {
				try {
					Node bSrc = (Node)((EdNode)map.get(ear.getSource())).getBasisNode();
					Node bTar = (Node)((EdNode)map.get(ear.getTarget())).getBasisNode();
					Arc ba = enac.getBasisGraph().copyArc(ear.getBasisArc(), bSrc, bTar);
					if (ba.getAttribute() != null)
						((agg.attribute.impl.ValueTuple)ba.getAttribute()).unsetValueAsExpr();
					EdArc ea = enac.addArc(ba, ear.getType());
					ea.setReps(ear.isDirected(), ear.isVisible(), false);
					ea.setTextOffset(ear.getTextOffset().x, ear.getTextOffset().y);
					if (ear.isLine()) {
						if (ear.hasAnchor()) {
							ea.setAnchor(ear.getAnchor());
							ea.getLArc().setFrozenByDefault(true);
						}
					} else { // is Loop
						if (ear.hasAnchor()) {
							ea.setXY(ear.getX(), ear.getY());
							ea.setWidth(ear.getWidth());
							ea.setHeight(ear.getHeight());
						}
					}
					enac.addCreatedToUndo(ea);
					enac.undoManagerEndEdit();
				} catch (TypeException e) {}
			}
		}
		map.clear(); map = null;
	}
	
	// EdPAC

	public boolean hasPACs() {
		if (!this.itsPACs.isEmpty())
			return true;
		
		return false;
	}

	/** Gets my PACs */
	public Vector<EdPAC> getPACs() {
		return this.itsPACs;
	}

	/** Gets my PAC specified by the index */
	public EdPAC getPAC(int index) {
		for (int i = 0; i < this.itsPACs.size(); i++) {
			if (i == index)
				return this.itsPACs.elementAt(i);
		}
		return null;
	}

	/** Gets my PAC specified by the name */
	public EdPAC getPAC(String pacname) {
		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC pac = this.itsPACs.elementAt(i);
			if (pac.getName().equals(pacname))
				return pac;
		}
		return null;
	}

	/** Gets my PAC specified by the morphism */
	public EdPAC getPAC(OrdinaryMorphism morphism) {
		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC pac = this.itsPACs.elementAt(i);
			if (pac.getMorphism() == morphism)
				return pac;
		}
		return null;
	}

	/** Creates a new PAC layout with name specified by the String nameStr. */
	public EdPAC createPAC(String nameStr, boolean isIdentic) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdPAC ePAC = new EdPAC(this.bRule.createPAC(), this.typeSet);
		ePAC.getBasisGraph().setName(nameStr);
		ePAC.setName(nameStr);
		ePAC.setRule(this);
		ePAC.setGraGra(this.eGra);
		ePAC.setEditable(this.eLeft.isEditable());
		ePAC.setUndoManager(this.undoManager);
		if (isIdentic && ePAC.isEditable())
			identicPAC(ePAC);
		this.itsPACs.addElement(ePAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return ePAC;
	}

	/**
	 * Creates a new PAC layout of the used object specified by the
	 * OrdinaryMorphism pac
	 */
	public EdPAC createPAC(OrdinaryMorphism pac) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdPAC ePAC = new EdPAC(pac, this.typeSet);
		ePAC.getBasisGraph().setName(pac.getName());
		ePAC.setName(pac.getName());
		ePAC.setRule(this);
		ePAC.setGraGra(this.eGra);
		ePAC.setUndoManager(this.undoManager);
		this.itsPACs.addElement(ePAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return ePAC;
	}

	public boolean addPAC(EdPAC pac) {
		if (this.bRule == null)
			return false;
		
		if (pac.getTypeSet().getBasisTypeSet().compareTo(
				this.typeSet.getBasisTypeSet())) {
			if (this.bRule.addPAC(pac.getMorphism())) {
				Vector<Type> v = pac.getMorphism().getUsedTypes();
				this.bRule.getLeft().getTypeSet().adaptTypes(v.elements(), false);
				this.typeSet.refreshTypes();
				// pac.update();
				pac.setRule(this);
				pac.setGraGra(this.eGra);
				pac.setUndoManager(this.undoManager);
				this.itsPACs.addElement(pac);
				if (this.eGra != null)
					this.eGra.setChanged(true);
				return true;
			} 
			return false;
		} 
		return false;
	}

	/**
	 * Destroys the specified EdPAC epac.
	 */
	public void destroyPAC(EdPAC epac) {
		if (this.bRule == null || epac.getMorphism() == null)
			return;
		epac.getMorphism().deleteObserver(epac);
		this.itsPACs.removeElement(epac);
		getBasisRule().destroyPAC(epac.getMorphism());
		if (this.eGra != null)
			this.eGra.setChanged(true);
	}

	/** Makes an identic PAC from PAC specified by the EdPAC epac */
	public void identicPAC(EdPAC epac) {
		OrdinaryMorphism morph = epac.getMorphism();
		// Remove all of my mappings.
		morph.clear();

		// Remove my image.
		morph.getImage().clear();

		// Remove my visible image;
		epac.clear();

		for (int i = 0; i < this.eLeft.getNodes().size(); i++) {
			EdNode en = this.eLeft.getNodes().elementAt(i);
			identicNode(en, epac, morph);
		}
		for (int j = 0; j < this.eLeft.getArcs().size(); j++) {
			EdArc ea = this.eLeft.getArcs().elementAt(j);
			identicArc(ea, epac, morph);
		}
		this.updateRule();
		this.updatePAC(epac);
	}

	public void addIdenticToPAC(Vector<EdGraphObject> graphObjects, EdPAC epac) {
		for (int i = 0; i < graphObjects.size(); i++) {
			EdGraphObject go = graphObjects.elementAt(i);
			if (go.isNode())
				identicNode((EdNode) go, epac, epac.getMorphism());
			else
				identicArc((EdArc) go, epac, epac.getMorphism());
		}
		this.update();
	}

	public EdGraphObject addIdenticToPAC(EdGraphObject graphObject, EdPAC epac) {
		EdGraphObject go;
		if (graphObject.isNode())
			go = identicNode((EdNode) graphObject, epac, epac.getMorphism());
		else
			go = identicArc((EdArc) graphObject, epac, epac.getMorphism());
		this.update();
		return go;
	}

	// //
	// nested AC as EdPAC

	public boolean hasNestedACs() {
		if (!this.itsACs.isEmpty())
			return true;
		
		return false;
	}

	/** Gets my Nested ACs */
	public Vector<EdPAC> getNestedACs() {
		return this.itsACs;
	}

	public List<EdNestedApplCond> getEnabledACs() {
		List<EdNestedApplCond> list = new Vector<EdNestedApplCond>(this.itsACs.size());		
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = (EdNestedApplCond) this.itsACs.get(i);
			if (ac.getMorphism().isEnabled())
				list.add(ac);
		}
		return list;
	}
	
	public List<EdNestedApplCond> getEnabledNestedACs() {
		List<EdNestedApplCond> list = new Vector<EdNestedApplCond>(this.itsACs.size());
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdNestedApplCond ac = (EdNestedApplCond) this.itsACs.get(i);
			if (ac.getMorphism().isEnabled())
				list.add(ac);
			list.addAll(ac.getEnabledNestedACs());
		}
		return list;
	}
	
	/** Gets my Nested AC specified by the index */
	public EdPAC getNestedAC(int index) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			if (i == index)
				return this.itsACs.elementAt(i);
		}
		return null;
	}

	/** Gets my Nested AC specified by the name */
	public EdPAC getNestedAC(String acname) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdPAC ac = this.itsACs.elementAt(i);
			if (ac.getName().equals(acname))
				return ac;
			else {
				EdPAC ac1 = ((EdNestedApplCond)ac).getNestedAC(acname);
				if (ac1 != null)
					return ac1;
			}
		}
		return null;
	}

	/** Gets my Nested AC specified by the morphism */
	public EdPAC getNestedAC(OrdinaryMorphism morphism) {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdPAC ac = this.itsACs.elementAt(i);
			if (ac.getMorphism() == morphism)
				return ac;
		}
		return null;
	}

	/** Creates a new Nested AC layout with name specified by the String nameStr. */
	public EdPAC createNestedAC(String nameStr, boolean isIdentic) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdNestedApplCond eAC = new EdNestedApplCond(null, this.bRule.createNestedAC(), this.typeSet);
		eAC.setName(nameStr);
		eAC.setRule(this);
		eAC.setGraGra(this.eGra);
		eAC.setEditable(this.eLeft.isEditable());
		eAC.getBasisGraph().setKind(GraphKind.AC);
		eAC.setUndoManager(this.undoManager);
		eAC.setSourceGraph(this.eLeft);
		if (isIdentic && eAC.isEditable())
			identicNestedAC(eAC);
		this.itsACs.addElement(eAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return eAC;
	}

	/** Creates a new Nested AC layout with name specified by the String nameStr. */
	public EdPAC createGACDuetoRHS(String nameStr) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		EdNestedApplCond eAC = new EdNestedApplCond(null, this.bRule.createNestedAC(), this.typeSet);
		eAC.setName(nameStr);
		eAC.setRule(this);
		eAC.setGraGra(this.eGra);
		eAC.setEditable(this.eLeft.isEditable());
		eAC.getBasisGraph().setKind(GraphKind.AC);
		eAC.setUndoManager(this.undoManager);
		eAC.setSourceGraph(this.eLeft);
		this.makeGACDuetoRHS(eAC);
		this.itsACs.addElement(eAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return eAC;
	}
	
	/**
	 * Creates a new Nested AC layout of the used object specified by the
	 * OrdinaryMorphism ac
	 */
	public EdPAC createNestedAC(OrdinaryMorphism ac) {
		if (this.bRule == null
				|| !this.editable)
			return null;
		
		final EdNestedApplCond eAC = new EdNestedApplCond(null, ac, this.typeSet);
		eAC.getBasisGraph().setName(ac.getName());
		eAC.getBasisGraph().setKind(GraphKind.AC);
		eAC.setName(ac.getName());
		eAC.setRule(this);
		eAC.setGraGra(this.eGra);
		eAC.setUndoManager(this.undoManager);
		eAC.setSourceGraph(this.eLeft);
		this.itsACs.addElement(eAC);
		if (this.eGra != null)
			this.eGra.setChanged(true);
		return eAC;
	}

	/**
	 * Adds a new Nested AC layout
	 * @param ac
	 */
	public boolean addNestedAC(EdNestedApplCond ac) {
		if (this.bRule == null)
			return false;
		
		if (ac.getTypeSet().getBasisTypeSet().compareTo(
				this.typeSet.getBasisTypeSet())) {
			if (this.bRule.addNestedAC(ac.getMorphism())) {
				Vector<Type> v = ac.getMorphism().getUsedTypes();
				this.bRule.getLeft().getTypeSet().adaptTypes(v.elements(), false);
				this.typeSet.refreshTypes();
				ac.getBasisGraph().setKind(GraphKind.AC);
				ac.setRule(this);
				ac.setGraGra(this.eGra);
				ac.setUndoManager(this.undoManager);
				ac.setSourceGraph(this.eLeft);
				this.itsACs.addElement(ac);
				if (this.eGra != null)
					this.eGra.setChanged(true);
				return true;
			} 
			return false;
		} 
		return false;
	}

	/**
	 * Destroys the specified nested AC.
	 */
	public void destroyNestedAC(EdPAC ac) {
		if (this.bRule == null || ac.getMorphism() == null)
			return;
		ac.getMorphism().deleteObserver(ac);
		this.itsACs.removeElement(ac);
		getBasisRule().destroyNestedAC(ac.getMorphism());
		if (this.eGra != null)
			this.eGra.setChanged(true);
	}

	/**
	 * Adds morphism mapping of the objects specified by the EdGraphObject
	 * leftObj, EdGraphObject acObj to the nested AC specified by the
	 * OrdinaryMorphism morph
	 */
	public void interactNestedAC(EdGraphObject leftObj, EdGraphObject acObj,
			NestedApplCond morph) {
		if (this instanceof EdAtomic) return;
		
		this.badMapping = false;
		this.errMsg = "";
		if (!leftObj.hasSimilarType(acObj)) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!";
			return;
		}
		try {
			morph.addMapping(leftObj.getBasisObject(), acObj.getBasisObject());
		} catch (BadMappingException ex) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!\n"+ex.getMessage();
		}
	}
	
	/** Makes an identic nested AC. */
	public void identicNestedAC(EdNestedApplCond ac) {
		if (this instanceof EdAtomic) return;
		
		OrdinaryMorphism morph = ac.getMorphism();
		// Remove all of my mappings.
		morph.clear();

		// Remove my image.
		morph.getImage().clear();

		// Remove my visible image;
		ac.clear();

		EdGraph srcGraph = ac.getSource()==null? this.eLeft: ac.getSource();
		for (int i = 0; i < srcGraph.getNodes().size(); i++) {
			EdNode en = srcGraph.getNodes().elementAt(i);
			identicNode(en, ac, morph);
		}
		for (int j = 0; j < srcGraph.getArcs().size(); j++) {
			EdArc ea = srcGraph.getArcs().elementAt(j);
			identicArc(ea, ac, morph);
		}

		if (ac.getSource() == this.eLeft)
			this.updateRule();
		else
			;
		this.updateNestedAC(ac);
	}

	public void addIdenticToNestedAC(Vector<EdGraphObject> graphObjects, EdPAC ac) {
		for (int i = 0; i < graphObjects.size(); i++) {
			EdGraphObject go = graphObjects.elementAt(i);
			if (go.isNode())
				identicNode((EdNode) go, ac, ac.getMorphism());
			else
				identicArc((EdArc) go, ac, ac.getMorphism());
		}
		this.update();
	}

	public EdGraphObject addIdenticToNestedAC(EdGraphObject graphObject, EdPAC ac) {
		EdGraphObject go;
		if (graphObject.isNode())
			go = identicNode((EdNode) graphObject, ac, ac.getMorphism());
		else
			go = identicArc((EdArc) graphObject, ac, ac.getMorphism());
		this.update();
		return go;
	}

	/** Rewrites the specified general AC by the copy of the RHS. */
	public void makeGACDuetoRHS(EdNestedApplCond egac) {
		if (this instanceof EdAtomic
				|| egac.getSource() != this.eLeft) return;
		
		OrdinaryMorphism morph = egac.getMorphism();
		// Remove all of my mappings.
		morph.clear();
		// Remove my image.
		morph.getImage().clear();
		// Remove my visible image;
		egac.clear();

		makeACDuetoRHS(egac, morph);
		this.updateRule();
		this.updateNestedAC(egac);
	}
	
	public boolean removeNestedAC(EdPAC ac) {
		if (this instanceof EdAtomic) return false;

		this.itsACs.removeElement(ac);
		this.bRule.removeNestedAC(ac.getMorphism());
		return true;		
	}
	// //

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
				if (morph instanceof Rule)
					this.addCreatedMappingToUndo(en, cn);				
				else if (morph instanceof Match)
					this.addCreatedMatchMappingToUndo(en, cn);
				else if (eg.getBasisGraph().isNacGraph())
					this.addCreatedNACMappingToUndo(en, cn);
				else if (eg.getBasisGraph().isPacGraph())
					this.addCreatedPACMappingToUndo(en, cn);
				else if (eg.getBasisGraph().isApplCondGraph())
					this.addCreatedACMappingToUndo(en, cn);
				else // AtomConstraint
					this.addCreatedMappingToUndo(en, cn);
				
				morph.addMapping(en.getBasisNode(), bn);

				this.undoManagerEndEdit();
			} catch (BadMappingException ex) {
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
					if (morph instanceof Rule)
						this.addCreatedMappingToUndo(ea, ca);
					else if (morph instanceof Match)
						this.addCreatedMatchMappingToUndo(ea, ca);
					else if (eg.getBasisGraph().isNacGraph())
						this.addCreatedNACMappingToUndo(ea, ca);
					else if (eg.getBasisGraph().isPacGraph())
						this.addCreatedPACMappingToUndo(ea, ca);
					else if (eg.getBasisGraph().isApplCondGraph())
						this.addCreatedACMappingToUndo(ea, ca);
					else // AtomConstraint
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

	private EdNode identicNode(EdNode en) {
		this.badMapping = false;
		this.errMsg = "";

		EdRuleScheme rs = (this.bRule != null)? 
				this.getGraGra().getRuleScheme(this.bRule): null;
		
		EdNode cn = null;
		Node bn = null;
		try {
			bn = this.eRight.getBasisGraph().copyNode(en.getBasisNode());
		} catch (TypeException e) {
		}

		if (bn != null) {
			cn = this.eRight.addNode(bn, en.getType());
			cn.setReps(en.getX(), en.getY(), en.isVisible(), false);
			// cn.getLNode().setFrozen(true);
			cn.getLNode().setFrozenByDefault(true);

			this.eRight.addCreatedToUndo(cn);
			this.eRight.undoManagerEndEdit();
			
			OrdinaryMorphism morph = getMorphism();
			try {
				this.addCreatedMappingToUndo(en, cn);
				
				morph.addMapping(en.getBasisNode(), bn);
				
				this.undoManagerEndEdit();
				
				if (rs != null && this.bRule instanceof KernelRule) {
					rs.propagateAddGraphObjectToMultiRule(cn);
					this.propagateAddRuleMappingToMultiRule(en, cn);
				}
				
			} catch (BadMappingException ex) {
				this.badMapping = true;
				this.errMsg = ex.getMessage();
			}
		}
		return cn;
	}

	private EdArc identicArc(EdArc ea) {
		this.badMapping = false;
		this.errMsg = "";

		EdRuleScheme rs = (this.bRule != null)? 
				this.getGraGra().getRuleScheme(this.bRule): null;
		
		EdArc ca = null;
		Arc ba = null;

		OrdinaryMorphism morph = getMorphism();
		GraphObject bSrc = morph.getImage(ea.getBasisArc().getSource());
		GraphObject bTar = morph.getImage(ea.getBasisArc().getTarget());

		try {
			ba = this.eRight.getBasisGraph().copyArc(ea.getBasisArc(), (Node) bSrc,
					(Node) bTar);
		} catch (TypeException e) {
//			e.printStackTrace();
		}

		if (ba != null) {
			try {
				ca = this.eRight.addArc(ba, ea.getType());

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
	
				this.eRight.addCreatedToUndo(ca);
				this.eRight.undoManagerEndEdit();
	
				try {
					this.addCreatedMappingToUndo(ea, ca);
	
					morph.addMapping(ea.getBasisArc(), ba);
	
					this.undoManagerEndEdit();
					
					if (rs != null && this.bRule instanceof KernelRule) {
						rs.propagateAddGraphObjectToMultiRule(ca);
						this.propagateAddRuleMappingToMultiRule(ea, ca);
					}
					
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

	public boolean canMakeIdenticRule() {
		if (!this.getMorphism().isEmpty()){
			this.errMsg = "Rule morphism mappings should be removed before.";
			return false;
		}
		if (!this.eRight.getBasisGraph().isEmpty()) {
			this.errMsg = "RHS graph objects should be removed before.";
			return false;
		}
		if (this.bRule instanceof MultiRule
						&& !((MultiRule)this.bRule).getRuleScheme()
								.getKernelRule().getSource().isEmpty()) {
			this.errMsg = "Kernel rule of this RuleScheme should contain empty graphs.";		
			return false;			
		} 
		return true;
	}
	
	/** Creates identical rule morphism */
	public void identicRule() {
		if (this.eRight.isEditable()
				&& this.canMakeIdenticRule()) {
				
			addLeftToRight(this.eLeft.getNodes(), this.eLeft.getArcs());	
			this.updateRule();
		}
	}	
	
	private void addLeftToRight(final List<EdNode> nodes, final List<EdArc> arcs) {
		for (int i = 0; i < nodes.size(); i++) {
			EdNode en = nodes.get(i);			
			identicNode(en);
		}
		for (int j = 0; j < arcs.size(); j++) {
			EdArc ea = arcs.get(j);
			identicArc(ea);
		}
	}
	
	public void addIdenticToRule(Vector<EdGraphObject> graphObjects) {
		if (!this.eRight.isEditable())
			return;
		for (int i = 0; i < graphObjects.size(); i++) {
			EdGraphObject go = graphObjects.get(i);
			addIdentic(go);
		}
		this.updateRule();
	}

	public EdGraphObject addIdentic(EdGraphObject graphObject) {
		EdGraphObject go = null;
		if (graphObject.isNode())
			go = identicNode((EdNode) graphObject);
		else
			go = identicArc((EdArc) graphObject);
		return go;
	}

	public List<EdNode> getOwnNodesLeft() {
		Vector<EdNode> list = null;
		if (this.bRule instanceof MultiRule) {
			list = new Vector<EdNode>();
			Iterator<EdNode> nodes = this.eLeft.getNodes().iterator();
			while (nodes.hasNext()) {
				EdNode n = nodes.next();
				if (!((MultiRule)this.bRule).getEmbeddingLeft().getCodomainObjects().contains(n)) {
					list.add(n);
				}
			}
		} else {
			list = this.eLeft.getNodes();
		}
		
		return list;
	}
	
	public List<EdNode> getOwnNodesRight() {
		Vector<EdNode> list = null;
		if (this.bRule instanceof MultiRule) {
			list = new Vector<EdNode>();
			Iterator<EdNode> nodes = this.eRight.getNodes().iterator();
			while (nodes.hasNext()) {
				EdNode n = nodes.next();
				if (!((MultiRule)this.bRule).getEmbeddingRight().getCodomainObjects().contains(n)) {
					list.add(n);
				}
			}
		} else {
			list = this.eRight.getNodes();
		}
		
		return list;
	}
	
	public List<EdArc> getOwnArcsLeft() {
		Vector<EdArc> list = null;
		if (this.bRule instanceof MultiRule) {
			list = new Vector<EdArc>();
			Iterator<EdArc> arcs = this.eLeft.getArcs().iterator();
			while (arcs.hasNext()) {
				EdArc a = arcs.next();
				if (!((MultiRule)this.bRule).getEmbeddingLeft().getCodomainObjects().contains(a)) {
					list.add(a);
				}
			}
		} else {
			list = this.eLeft.getArcs();
		}
		
		return list;
	}
	
	public List<EdArc> getOwnArcsRight() {
		Vector<EdArc> list = null;
		if (this.bRule instanceof MultiRule) {
			list = new Vector<EdArc>();
			Iterator<EdArc> arcs = this.eRight.getArcs().iterator();
			while (arcs.hasNext()) {
				EdArc a = arcs.next();
				if (!((MultiRule)this.bRule).getEmbeddingRight().getCodomainObjects().contains(a)) {
					list.add(a);
				}
			}
		} else {
			list = this.eLeft.getArcs();
		}
		
		return list;
	}
	/**
	 * Adds morphism mapping of the objects specified by the EdGraphObject
	 * leftObj, EdGraphObject rightObj to the rule morphism
	 */
	public void interactRule(EdGraphObject leftObj, EdGraphObject rightObj) {
		if (!this.editable)
			return;
		
		this.badMapping = false;
		this.errMsg = "";

		if (!leftObj.hasSimilarType(rightObj)) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!";
			return;
		}

		OrdinaryMorphism morph = getMorphism();
		if (morph == null)
			return;

		try {
			morph.addMapping(leftObj.getBasisObject(), rightObj
					.getBasisObject());
			this.update();
		} catch (BadMappingException ex) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!\n" + ex.getMessage();
		}
//		System.out.println("EdRule.interactRule: "+errMsg);
	}

	/**
	 * Removes the morphism mapping of the object specified by the EdGraphObject
	 * leftObj
	 */
	public void removeRuleMapping(EdGraphObject leftObj) {
		if (!this.editable)
			return;
		
		OrdinaryMorphism morph = getMorphism();
		if (morph.getImage(leftObj.getBasisObject()) != null) {
			morph.removeMapping(leftObj.getBasisObject());
			this.update();
		}
	}

	public boolean removeRuleMapping(EdGraphObject leftObj,
			EdGraphObject rightObj) {
		if (leftObj == null || rightObj == null
				|| !this.editable)
			return false;
		
		OrdinaryMorphism morph = getMorphism();
		if (leftObj.isNode()) {
			if (morph.removeMapping((Node) leftObj.getBasisObject(),
					(Node) rightObj.getBasisObject())) {
				this.update();
				return true;
			}
		} else {
			if (morph.removeMapping((Arc) leftObj.getBasisObject(),
					(Arc) rightObj.getBasisObject())) {
				this.update();
				return true;
			}
		}
		return false;
	}

	/**
	 * Adds morphism mapping of the objects specified by the EdGraphObject
	 * leftObj, EdGraphObject nacObj to the NAC specified by the
	 * OrdinaryMorphism morph
	 */
	public void interactNAC(EdGraphObject leftObj, EdGraphObject nacObj,
			OrdinaryMorphism morph) {
		if (this instanceof EdAtomic) return;
		
		// System.out.println("EdRule.interactNAC:: "+leftObj+" "+nacObj);
		this.badMapping = false;
		this.errMsg = "";
		if (!leftObj.hasSimilarType(nacObj)) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!";
			return;
		}
//		if (morph.getImage(leftObj.getBasisObject()) == null) 
		{
			try {
				morph.addMapping(leftObj.getBasisObject(), nacObj.getBasisObject());				
				this.update();
			} catch (BadMappingException ex) {
				this.badMapping = true;
				this.errMsg = "Bad mapping!\n"+ex.getMessage();
			}
		}
//		else {
//			badMapping = true;
//			errMsg = "Bad mapping!\n Only injective NAC mapping allowed.";
//		}
	}
	
	/**
	 * Removes the morphism mapping of the LHS graph object specified by the
	 * EdGraphObject leftObj from the NAC morphism specified by the
	 * OrdinaryMorphism morph
	 */
	public void removeNACMapping(EdGraphObject leftObj, OrdinaryMorphism morph) {
		if ((this instanceof EdAtomic) || !this.editable) return;
		
		if (morph.getImage(leftObj.getBasisObject()) != null) {
			morph.removeMapping(leftObj.getBasisObject());
			this.update();
		}
	}

	/**
	 * Removes the morphism mapping of the LHS graph object specified by the
	 * EdGraphObject leftObj from all NACs.
	 */
	public void removeNACMapping(EdGraphObject leftObj) {
		if ((this instanceof EdAtomic)
				|| !this.editable)
			return;

		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC nac = this.itsNACs.get(i);
			OrdinaryMorphism morph = nac.getMorphism();
			if (morph.getImage(leftObj.getBasisObject()) != null) {
				morph.removeMapping(leftObj.getBasisObject());
			}
		}
		this.update();
	}

	public boolean removeNAC(EdNAC nac) {
		if (this instanceof EdAtomic) return false;
		
		if (this.bRule.removeNAC(nac.getMorphism())) {
			this.itsNACs.removeElement(nac);
			return true;
		} 
		return false;
	}

	/**
	 * Adds morphism mapping of the objects specified by the EdGraphObject
	 * leftObj, EdGraphObject pacObj to the PAC specified by the
	 * OrdinaryMorphism morph
	 */
	public void interactPAC(EdGraphObject leftObj, EdGraphObject pacObj,
			OrdinaryMorphism morph) {
		if (this instanceof EdAtomic) return;
		
		this.badMapping = false;
		this.errMsg = "";
		if (!leftObj.hasSimilarType(pacObj)) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!";
			return;
		}
		try {
			morph.addMapping(leftObj.getBasisObject(), pacObj.getBasisObject());
			this.update();
		} catch (BadMappingException ex) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!\n"+ex.getMessage();
		}
	}

	/**
	 * Removes the morphism mapping of the LHS graph object specified by the
	 * EdGraphObject leftObj from the PAC morphism specified by the
	 * OrdinaryMorphism morph
	 */
	public void removePACMapping(EdGraphObject leftObj, OrdinaryMorphism morph) {
		if ((this instanceof EdAtomic) || !this.editable) return;

		if (morph.getImage(leftObj.getBasisObject()) != null) {
			morph.removeMapping(leftObj.getBasisObject());
			this.update();
		}
	}

	public void removeNestedACMapping(EdGraphObject leftObj, NestedApplCond morph) {
		if ((this instanceof EdAtomic) || !this.editable) return;

		if (morph.getImage(leftObj.getBasisObject()) != null) {
			morph.removeMapping(leftObj.getBasisObject());
			this.update();
		}
	}
	
	/**
	 * Removes the morphism mapping of the LHS graph object specified by the
	 * EdGraphObject leftObj from all PACs.
	 */
	public void removePACMapping(EdGraphObject leftObj) {
		if ((this instanceof EdAtomic) || !this.editable) return;

		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC pac = this.itsPACs.get(i);
			OrdinaryMorphism morph = pac.getMorphism();
			if (morph.getImage(leftObj.getBasisObject()) != null) {
				morph.removeMapping(leftObj.getBasisObject());
			}
		}
		this.update();
	}

	public void removeNestedACMapping(EdGraphObject leftObj) {
		if ((this instanceof EdAtomic) || !this.editable) return;

		for (int i = 0; i < this.itsACs.size(); i++) {
			EdPAC pac = this.itsACs.get(i);
			OrdinaryMorphism morph = pac.getMorphism();
			if (morph.getImage(leftObj.getBasisObject()) != null) {
				morph.removeMapping(leftObj.getBasisObject());
			}
		}
		this.update();
	}
	
	public boolean removePAC(EdPAC pac) {
		if (this instanceof EdAtomic) return false;

		if (this.bRule.removePAC(pac.getMorphism())) {
			this.itsPACs.removeElement(pac);
			return true;
		} 
		return false;
	}

	/**
	 * Lets the transformation machine to complete a match specified by the
	 * Match m.
	 */
	public void autoMatch(Match m) {
		if (m == null) {
			this.errMsg = "Match is NULL.";
			return;
		}
		this.badMapping = false;
		this.errMsg = "";

		if (m.nextCompletion()) {
			if (m.isValid())
				updateMatch(m, this.eLeft, this.eGra.getGraph());
			else {
				autoMatch(m);
				this.badMapping = true;
				this.errMsg = m.getErrorMsg(); // this.ERROR_NOT_VALID;
			}
		} else {
			this.badMapping = true;
			this.errMsg = m.getErrorMsg(); // this.ERROR_NO_COMPLETION;
		}
	}

	/**
	 * Adds the morphism mapping of the objects specified by the EdGraphObject
	 * leftObj, EdGraphObject graphObj to the current match
	 */
	public void interactMatch(EdGraphObject leftObj, EdGraphObject graphObj) {
		if (this.bRule.getMatch() == null
				|| !this.editable)
			return;

		this.badMapping = false;
		this.errMsg = "";

		/* check type */
		if (leftObj.getType() != null && !leftObj.hasSimilarType(graphObj)) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!\nSource and target objects of the mapping have to have the same type.";
			return;
		}

		/* check if injective */
		MorphCompletionStrategy strategy = this.bRule.getMatch()
				.getCompletionStrategy();
		BitSet activebits = strategy.getProperties();
		if (activebits.get(0)) {
			Enumeration<GraphObject> origs = this.bRule.getMatch().getInverseImage(
					graphObj.getBasisObject());
			if (origs.hasMoreElements()) {
				this.badMapping = true;
				this.errMsg = "Bad mapping!\nOnly injective mappings are allowed.";
				return;
			}
		}

		/* add mapping */
		try {
			this.bRule.getMatch().addMapping(leftObj.getBasisObject(),
					graphObj.getBasisObject());
			this.bRule.getMatch().setPartialMorphismCompletion(true);
			if (this.eGra.getGraph() != null)
				this.updateMatch(this.bRule.getMatch(), this.eLeft, this.eGra.getGraph());
//			System.out.println("EdRule: "+leftObj.getBasisObject()+"   "+bRule.getMatch().getImage(leftObj.getBasisObject()));
		} catch (BadMappingException ex) {
			this.badMapping = true;
			this.errMsg = "Bad mapping!\n" + ex.getMessage();
//			System.out.println("EdRule: "+errMsg);
		}
	}

	/**
	 * Removes the match mapping of the object specified by the EdGraphObject
	 * leftObj
	 */
	public void removeMatchMapping(EdGraphObject leftObj) {
		if (this.bRule == null || this.bRule.getMatch() == null
				|| !this.editable) {
			return;
		}
		if (this.bRule.getMatch().getImage(leftObj.getBasisObject()) != null) {
			this.bRule.getMatch().removeMapping(leftObj.getBasisObject());
			this.bRule.getMatch().removeVariableValue(
					leftObj.getBasisObject().getAttribute());
			if (this.eGra != null) {
				updateMatch(this.bRule.getMatch(), this.eLeft, this.eGra.getGraph());
			}
			this.update();
		}
		if (this.bRule.getMatch().getSize() == 0) {
			this.bRule.getMatch().setPartialMorphismCompletion(false);
		}
	}

	/** Removes all the match mappings and destroys the current match object */
	public void destroyMatch() {
		if (this.bRule == null || this.bRule.getMatch() == null)
			return;
		this.bRule.getMatch().deleteObserver(this.eLeft);
		this.eGra.getBasisGraGra().destroyMatch(this.bRule.getMatch());
		if (this.eGra != null) {
			this.eGra.getGraph().clearMarks();

			for (int i = 0; i < this.eLeft.getNodes().size(); i++) {
				EdNode en = this.eLeft.getNodes().elementAt(i);
				updateOrig(en);
			}
			for (int j = 0; j < this.eLeft.getArcs().size(); j++) {
				EdArc ea = this.eLeft.getArcs().elementAt(j);
				updateOrig(ea);
			}
		}
	}

	/**
	 * Removes the mappings of the object specified by the EdGraphObject
	 * imageObj from morphism specified by the OrdinaryMorphism m
	 */
	public void removeMapping(EdGraphObject imageObj, OrdinaryMorphism m) {
		if (imageObj == null || m == null
				|| !this.editable)
			return;

		Enumeration<GraphObject> originals = m.getInverseImage(imageObj.getBasisObject());
		while (originals.hasMoreElements()) {
			GraphObject original = originals.nextElement();
			m.removeMapping(original);
			// m.resetCSPVariableDomainOf(original); // test only
			this.update();
		}
	}

	public boolean removeMapping(EdGraphObject origObj, EdGraphObject imageObj,
			OrdinaryMorphism m) {
		if (origObj == null || imageObj == null || m == null
				|| !this.editable)
			return false;
		
		if (origObj.isNode()) {
			if (m.removeMapping((Node) origObj.getBasisObject(),
					(Node) imageObj.getBasisObject())) {
				this.update();
				return true;
			}
		} else {
			if (m.removeMapping((Arc) origObj.getBasisObject(), (Arc) imageObj
					.getBasisObject())) {
				this.update();
				return true;
			}
		}
		return false;
	}

	/**
	 * Updates layout of this rule, its NACs, PACs and match (if it exists)
	 * after renewed reading of the basis rule object
	 */
	public void update() {
		if (this.bRule != null) {		
			updateRule();
			updateNACs();
			updatePACs();
			updateNestedACs();
			if (this.eGra != null)
				updateMatch(this.bRule.getMatch(), this.eLeft, this.eGra.getGraph());
		} 
		else if (this instanceof EdAtomic)
			updateRule();		
	}

	/**
	 * Updates layout of the LHS and RHS and rule morphism of this rule.
	 */
	public void updateRule() {
		this.eLeft.clearMarks();
		this.eRight.clearMarks();

		EdNode enL = null;
		EdNode enR = null;
		EdArc eaL = null;
		EdArc eaR = null;

		OrdinaryMorphism bm = getMorphism();
		if (bm == null)
			return;
		
		Enumeration<GraphObject> domain = bm.getDomain();
		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = bm.getImage(bOrig);

			enL = this.eLeft.findNode(bOrig);
			if (enL != null) {
				if (enL.isMorphismMarkEmpty())
					enL.addMorphismMark(enL.getMyKey());

				enR = this.eRight.findNode(bImage);
				if (enR != null)
					enR.addMorphismMark(enL.getMorphismMark());
				else
					enL.clearMorphismMark();
			}

			eaL = this.eLeft.findArc(bOrig);
			if (eaL != null) {
				if (eaL.isMorphismMarkEmpty())
					eaL.addMorphismMark(eaL.getMyKey());

				eaR = this.eRight.findArc(bImage);
				if (eaR != null)
					eaR.addMorphismMark(eaL.getMorphismMark());
				else
					eaL.clearMorphismMark();
			}
		}
	}

	/** Updates NAC layout after reading NAC graph objects */
	public void updateNAC(EdNAC nacgraph) {
		EdNode enL = null;
		EdNode enNAC = null;
		EdArc eaL = null;
		EdArc eaNAC = null;

		nacgraph.clearMarks();

		Enumeration<GraphObject> domain = nacgraph.getMorphism().getDomain();
		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = nacgraph.getMorphism().getImage(bOrig);

			enL = this.eLeft.findNode(bOrig);
			if (enL != null) {
				if (enL.isMorphismMarkEmpty())
					enL.addMorphismMark(enL.getMyKey());

				enNAC = nacgraph.findNode(bImage);
				if (enNAC != null) {
					enNAC.addMorphismMark(enL.getMorphismMark());
				}
			}

			eaL = this.eLeft.findArc(bOrig);
			if (eaL != null) {
				if (eaL.isMorphismMarkEmpty())
					eaL.addMorphismMark(eaL.getMyKey());

				eaNAC = nacgraph.findArc(bImage);
				if (eaNAC != null) {
					eaNAC.addMorphismMark(eaL.getMorphismMark());
				}
			}
		}
	}

	/** Updates NAC layout after reading NAC graph objects */
	public void updateNACs() {
		for (int i = 0; i < this.itsNACs.size(); i++) {
			EdNAC eNAC = this.itsNACs.elementAt(i);
			this.updateNAC(eNAC);
		}
	}

	/** Updates PAC layout after reading PAC graph objects */
	public void updatePAC(EdPAC pacgraph) {
		EdNode enL = null;
		EdNode enPAC = null;
		EdArc eaL = null;
		EdArc eaPAC = null;

		pacgraph.clearMarks();

		OrdinaryMorphism pacMorph = pacgraph.getMorphism();
		Enumeration<GraphObject> domain = pacMorph.getDomain();

		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = pacMorph.getImage(bOrig);

			enL = this.eLeft.findNode(bOrig);
			if (enL != null) {
				if (enL.isMorphismMarkEmpty())
					enL.addMorphismMark(enL.getMyKey());

				enPAC = pacgraph.findNode(bImage);
				if (enPAC != null) {
					enPAC.addMorphismMark(enL.getMorphismMark());
				}
			}

			eaL = this.eLeft.findArc(bOrig);
			if (eaL != null) {
				if (eaL.isMorphismMarkEmpty())
					eaL.addMorphismMark(eaL.getMyKey());

				eaPAC = pacgraph.findArc(bImage);
				if (eaPAC != null) {
					eaPAC.addMorphismMark(eaL.getMorphismMark());
				}
			}
		}
	}

	/** Updates PAC layout after reading PAC graph objects */
	public void updatePACs() {
		for (int i = 0; i < this.itsPACs.size(); i++) {
			EdPAC ePAC = this.itsPACs.elementAt(i);
			this.updatePAC(ePAC);
		}
	}

	public void updateNestedACs() {
		for (int i = 0; i < this.itsACs.size(); i++) {
			EdPAC ac = this.itsACs.get(i);
			this.updateNestedAC((EdNestedApplCond) ac);
			((EdNestedApplCond) ac).updateNestedACs();
		}
	}
	
	public void updateNestedAC(EdNestedApplCond ac) {
		EdNode enL = null;
		EdNode enAC = null;
		EdArc eaL = null;
		EdArc eaAC = null;

		if (ac.getSource().getBasisGraph().isApplCondGraph())
			ac.getSource().clearMarks();
		ac.clearMarks();

		Enumeration<GraphObject> domain = ac.getMorphism().getDomain();

		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = ac.getMorphism().getImage(bOrig);

			enL = ac.getSource().findNode(bOrig);
			if (enL != null) {
				if (enL.isMorphismMarkEmpty())
					enL.addMorphismMark(enL.getMyKey());

				enAC = ac.findNode(bImage);
				if (enAC != null) {
					enAC.addMorphismMark(enL.getMorphismMark());
				}
			} else {
				eaL = ac.getSource().findArc(bOrig);
				if (eaL != null) {
					if (eaL.isMorphismMarkEmpty())
						eaL.addMorphismMark(eaL.getMyKey());
	
					eaAC = ac.findArc(bImage);
					if (eaAC != null) {
						eaAC.addMorphismMark(eaL.getMorphismMark());
					}
				}
			}
		}
	}
	
	/**
	 * Update layout of the LHS of this rule after the specified EdGraphObject
	 * leftObj changed its morphism mapping.
	 */
	private void updateOrig(EdGraphObject leftObj) {
		boolean isOrig = false;

		OrdinaryMorphism bm = getMorphism();
		Enumeration<GraphObject> domain = bm.getDomain();
		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			if (leftObj.getBasisObject().equals(bOrig))
				isOrig = true;
		}
		if (!isOrig)
			leftObj.clearMorphismMark();
	}

	/**
	 * Updates layout of a match, if it exists.
	 */
	public void updateMatch() {
		if (this.eGra != null)
			updateMatch(this.bRule.getMatch(), this.eLeft, this.eGra.getGraph());
	}

	/**
	 * Updates layout of the specified match.
	 */
	private void updateMatch(Match m, EdGraph eOrigGraph, EdGraph eImageGraph) {
		if (eOrigGraph == null || eImageGraph == null)
			return;

		eImageGraph.clearMarks();
		if (m == null)
			return;

		EdNode enO = null;
		EdNode enI = null;
		EdArc eaO = null;
		EdArc eaI = null;

		Enumeration<GraphObject> domain = m.getDomain();
		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = m.getImage(bOrig);

			enO = eOrigGraph.findNode(bOrig);
			if (enO != null) {
				if (enO.isMorphismMarkEmpty())
					enO.addMorphismMark(enO.getMyKey());

				enI = eImageGraph.findNode(bImage);
				if (enI != null) {
					enI.addMorphismMark(enO.getMorphismMark());
				}
			} else {
				eaO = eOrigGraph.findArc(bOrig);
				if (eaO != null) {
					if (eaO.isMorphismMarkEmpty())
						eaO.addMorphismMark(eaO.getMyKey());

					eaI = eImageGraph.findArc(bImage);
					if (eaI != null)
						eaI.addMorphismMark(eaO.getMorphismMark());
				}
			}
		}
	}

	public void setMorphismMarks(HashMap<?,?> marks, EdNAC nacGraph) {
		this.eLeft.setMorphismMarks(marks, true);
		this.eRight.clearMarks();
		Enumeration<?> e = this.eLeft.getNodes().elements();
		while (e.hasMoreElements()) {
			EdNode o = (EdNode) e.nextElement();
			GraphObject img = this.bRule.getImage(o.getBasisNode());
			if (img != null) {
				EdGraphObject o1 = this.eRight.findGraphObject(img);
				if (o1 != null && !o.getMorphismMark().equals(""))
					o1.addMorphismMark(o.getMorphismMark());
			}
		}
		e = this.eLeft.getArcs().elements();
		while (e.hasMoreElements()) {
			EdArc o = (EdArc) e.nextElement();
			GraphObject img = this.bRule.getImage(o.getBasisArc());
			if (img != null) {
				EdGraphObject o1 = this.eRight.findGraphObject(img);
				if (o1 != null && !o.getMorphismMark().equals(""))
					o1.addMorphismMark(o.getMorphismMark());
			}
		}
		// set morphism mapping marks of the other objects of the RHS
		this.eRight.setMorphismMarks(marks, false);
		// reset nac morphism mapping marks only
		if (nacGraph != null) {
			nacGraph.clearMarks();
			OrdinaryMorphism nacMorph = nacGraph.getMorphism();
			e = nacMorph.getCodomain();
			while (e.hasMoreElements()) {
				GraphObject obj = (GraphObject) e.nextElement();
				if (nacMorph.getInverseImage(obj).hasMoreElements()) {
					GraphObject objL = nacMorph.getInverseImage(
							obj).nextElement();
					EdGraphObject goL = this.eLeft.findGraphObject(objL);
					EdGraphObject goN = nacGraph.findGraphObject(obj);
					goN.clearMorphismMark();
					goN.addMorphismMark(goL.getMorphismMark());
				}
			}
			nacGraph.setMorphismMarks(marks, false);
		}
	}

	/** Returns TRUE if the morphism mapping is failed */
	public boolean isBadMapping() {
		return this.badMapping;
	}

	/** Returns error message */
	public String getMsg() {
		if (this.errMsg == null)
			this.errMsg = "";
		return this.errMsg;
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
		for (int j=0; j<this.itsNACs.size(); j++) {
			EdNAC nac = this.itsNACs.get(j);
			if (!nac.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;				
		}

		for (int j=0; j<this.itsPACs.size(); j++) {
			EdPAC pac = this.itsPACs.get(j);
			if (!pac.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
		}
	
		for (int j=0; j<this.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond)this.itsACs.get(j);
			if (!ac.deleteGraphObjectsOfType(t, addToUndo))
				allDone = false;
		}
		
		if (!this.eLeft.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
			allDone = false;
		if (!this.eRight.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
			allDone = false;
		
		return allDone;
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
			
		for (int j=0; j<this.itsNACs.size(); j++) {
			EdNAC nac = this.itsNACs.get(j);
			if (addToUndo) {
				for (int i=0; i<list.size(); i++) {
					EdGraphObject go = list.get(i);
					EdGraphObject rgo = nac.findGraphObject(
								nac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.addDeletedNACMappingToUndo(go, rgo);
						this.undoManagerEndEdit();
					}
				}
			}
			if (!nac.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}

		for (int j=0; j<this.itsPACs.size(); j++) {
			EdPAC pac = this.itsPACs.get(j);
			if (addToUndo) {
				for (int i=0; i<list.size(); i++) {
					EdGraphObject go = list.get(i);
					EdGraphObject rgo = pac.findGraphObject(
								pac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.addDeletedPACMappingToUndo(go, rgo);
						this.undoManagerEndEdit();
					}
				}
			}
			if (!pac.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}
	
		for (int j=0; j<this.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond)this.itsACs.get(j);
			if (addToUndo) {
				for (int i=0; i<list.size(); i++) {
					EdGraphObject go = list.get(i);
					EdGraphObject rgo = ac.findGraphObject(
								ac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.addDeletedACMappingToUndo(go, rgo);
						this.undoManagerEndEdit();
					}
				}
				ac.storeMappingOfGraphObjectsOfType(tgo, ac);
			}
			if (!ac.deleteGraphObjectsOfType(tgo, addToUndo))
				allDone = false;
		}
		
		if (!this.eLeft.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
			allDone = false;
		if (!this.eRight.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
			allDone = false;
		
		return allDone;
	}
	
	public void setLayoutByIndexFrom(EdRule er) {
		setLayoutByIndexFrom(er, false);
	}
	
	public void setLayoutByIndexFrom(EdRule er, boolean inverse) {
		if (inverse) {
			this.eLeft.setLayoutByIndex(er.getRight(), true);
			this.eRight.setLayoutByIndex(er.getLeft(), true);
		} else {
			this.eLeft.setLayoutByIndex(er.getLeft(), true);
			this.eRight.setLayoutByIndex(er.getRight(), true);
		}
		
		if (this.itsACs.size() == er.getNestedACs().size()) {
			for (int n = 0; n < this.itsACs.size(); n++) {
				EdPAC ac = this.itsACs.get(n);
				ac.setLayoutByIndex(er.getNestedACs().get(n), true);
			}
		}
		else {
			for (int n = 0; n < this.itsACs.size(); n++) {
				EdPAC ac = this.itsACs.get(n);
				ac.setLayoutByIndex(this.getLeft(), true);
			}
		}
		if (this.itsNACs.size() == er.getNACs().size()) {
			for (int n = 0; n < this.itsNACs.size(); n++) {
				EdNAC ac = this.itsNACs.get(n);
				ac.setLayoutByIndex(er.getNACs().get(n), true);
			}
		}
		else {
			for (int n = 0; n < this.itsNACs.size(); n++) {
				EdNAC ac = this.itsNACs.get(n);
				ac.setLayoutByIndex(this.getLeft(), true);
			}
		}
		if (this.itsPACs.size() == er.getPACs().size()) {
			for (int n = 0; n < this.itsPACs.size(); n++) {
				EdPAC ac = this.itsPACs.get(n);
				ac.setLayoutByIndex(er.getPACs().get(n), true);
			}
		}
		else {
			for (int n = 0; n < this.itsPACs.size(); n++) {
				EdPAC ac = this.itsPACs.get(n);
				ac.setLayoutByIndex(this.getLeft(), true);
			}
		}
	}
	
	public void XwriteObject(XMLHelper h) {
		if (h.openObject(this.bRule, this)) {
			if (this.animated) {
				// refresh animation kind
				this.setAnimated(true);
			
				h.addAttr("animated", "true");
			}
		}
		
		h.addObject("", this.eLeft, true);
		h.addObject("", this.eRight, true);
		
		for (int j = 0; j < this.itsNACs.size(); j++) {
			h.addObject("", this.itsNACs.elementAt(j), true);
		}
		for (int j = 0; j < this.itsPACs.size(); j++) {
			h.addObject("", this.itsPACs.elementAt(j), true);
		}
		for (int j = 0; j < this.itsACs.size(); j++) {
			h.addObject("", this.itsACs.elementAt(j), true);
		}
	}

	public void XreadObject(XMLHelper h) {
		h.peekObject(this.bRule, this);
		String animatedAttr = h.readAttr("animated");
		if ("true".equals(animatedAttr)) {
			this.setAnimated(true);
		}
		
		h.enrichObject(this.eLeft);
		h.enrichObject(this.eRight);
		
		for (int j = 0; j < this.itsNACs.size(); j++) {
			EdNAC ac = this.itsNACs.elementAt(j);
			h.enrichObject(ac);
		}
		for (int j = 0; j < this.itsPACs.size(); j++) {
			EdPAC ac = this.itsPACs.elementAt(j);
			h.enrichObject(ac);
		}
		for (int j = 0; j < this.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond) this.itsACs.get(j);
			h.enrichObject(ac);
		}
		updateRule();
		updateNACs();
		updatePACs();
		updateNestedACs();
	}

}
// $Log: EdRule.java,v $
// Revision 1.65  2010/11/11 17:21:34  olga
// tuning
//
// Revision 1.64  2010/10/16 22:43:41  olga
// improved undo for RuleScheme graph objects
//
// Revision 1.63  2010/10/07 20:09:37  olga
// tuning
//
// Revision 1.62  2010/10/04 18:11:28  olga
// tests
//
// Revision 1.61  2010/09/30 22:19:26  olga
// improved
//
// Revision 1.60  2010/09/30 14:11:40  olga
// delete objects of especial type -  improved
//
// Revision 1.59  2010/09/27 22:44:22  olga
// improved
//
// Revision 1.58  2010/09/27 16:21:19  olga
// improved
//
// Revision 1.57  2010/09/19 16:22:37  olga
// tuning
//
// Revision 1.56  2010/08/09 14:01:29  olga
// extended by possibility to add a critical graph as NAC of the first or second rule
//
// Revision 1.55  2010/06/09 11:07:50  olga
// extended due to new NestedApplCond
//
// Revision 1.54  2010/04/27 10:32:56  olga
// compute parallel rule - improved
//
// Revision 1.53  2010/03/10 14:44:49  olga
// make identical rule - bug fixed
//
// Revision 1.52  2010/03/08 15:40:03  olga
// code optimizing
//
// Revision 1.51  2010/03/04 14:08:32  olga
// code optimizing
//
// Revision 1.50  2009/10/05 08:51:27  olga
// RSA check - bug fixed
//
// Revision 1.49  2009/06/02 12:39:19  olga
// Min Multiplicity check - bug fixed
//
// Revision 1.48  2009/05/28 13:18:24  olga
// Amalgamated graph transformation - development stage
//
// Revision 1.47  2009/05/12 10:37:00  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.46  2009/04/27 07:37:17  olga
// Copy and Paste TypeGraph- bug fixed
// CPA - dangling edge conflict when first produce second delete - extended
//
// Revision 1.45  2009/04/14 09:18:34  olga
// Edge Type Multiplicity check - bug fixed
//
// Revision 1.44  2009/03/04 13:06:10  olga
// New extension: Export/Import to/from ColorGraph
//
// Revision 1.43  2008/12/17 09:37:41  olga
// Import of TypeGraph from  grammar (.ggx) - bug fixed
//
// Revision 1.42  2008/11/06 08:45:36  olga
// Graph layout is extended by Zest Graph Layout ( eclipse zest plugin)
//
// Revision 1.41  2008/10/29 09:04:05  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.40  2008/09/25 08:02:51  olga
// improved graphics update during graph transformation
//
// Revision 1.39  2008/07/21 10:03:28  olga
// Code tuning
//
// Revision 1.38  2008/07/16 15:52:48  olga
// Import GXL file - bug fixed
//
// Revision 1.37  2008/07/14 07:35:47  olga
// Applicability of RS - new option added, more tuning
// Node animation - new animation parameter added,
// Undo edit manager - possibility to disable it when graph transformation
// because it costs much more time and memory
//
// Revision 1.36  2008/06/30 10:47:40  olga
// Applicability of Rule Sequence - tuning
// Node animation - first steps
//
// Revision 1.35  2008/04/21 09:32:19  olga
// Visualization of inheritance edge - bugs fixed
// Graph layout tuning
//
// Revision 1.34  2008/04/07 09:36:50  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.33  2008/02/18 09:37:11  olga
// - an extention of rule dependency check is implemented;
// - some bugs fixed;
// - editing of graphs improved
//
// Revision 1.32  2007/12/17 08:33:29  olga
// Editing inheritance relations - bug fixed;
// CPA: dependency of rules - bug fixed
//
// Revision 1.31  2007/11/28 08:31:42  olga
// Match next completion : error message tuning
//
// Revision 1.30  2007/11/26 10:59:23  olga
// Edge mapping - bug fixed: update of failed edge mapping if source and target not already mapped
//
// Revision 1.29  2007/11/14 08:53:43  olga
// code tuning
//
// Revision 1.28  2007/11/05 09:18:17  olga
// code tuning
//
// Revision 1.27  2007/11/01 09:58:12  olga
// Code refactoring: generic types- done
//
// Revision 1.26  2007/10/17 14:48:02  olga
// EdRule: bug fixed
// Code tuning
//
// Revision 1.25  2007/10/10 14:30:34  olga
// Enumeration typing
//
// Revision 1.24  2007/10/10 07:44:27  olga
// CPA: bug fixed
// GUI, AtomConstraint: bug fixed
//
// Revision 1.23  2007/09/10 13:05:17  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.22 2007/07/04 08:40:34 olga
// Undo/Redu of transformation step in Step by Step mode - tuning
// Magic Edge - tuning
//
// Revision 1.21 2007/07/02 08:27:31 olga
// Help docu update,
// Source tuning
//
// Revision 1.20 2007/06/28 15:46:18 olga
// Graph layouter,
// Type palette - select type,
// Magic arc - tuning
//
// Revision 1.19 2007/06/13 08:32:46 olga
// Update: V161
//
// Revision 1.18 2007/05/07 07:59:36 olga
// CSP: extentions of CSP variables concept
//
// Revision 1.17 2007/04/30 13:23:36 olga
// Update morphism mapping after adding an identic node/edge to RHS, NAC - bug
// fixed.
//
// Revision 1.16 2007/04/24 07:08:47 olga
// undo/redo match mappings
//
// Revision 1.15 2007/04/19 14:50:05 olga
// Loading grammar - tuning
//
// Revision 1.14 2007/04/19 07:52:34 olga
// Tuning of: Undo/Redo, Graph layouter, loading grammars
//
// Revision 1.13 2007/04/11 10:03:34 olga
// Undo, Redo tuning,
// Simple Parser- bug fixed
//
// Revision 1.12 2007/03/28 10:00:27 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.11 2007/02/19 09:11:00 olga
// Bug during loading file fixed.
// Type editor tuning
//
// Revision 1.10 2007/02/05 12:33:43 olga
// CPA: chengeAttribute conflict/dependency : attributes with constants
// bug fixed, but the critical pairs computation has still a gap.
//
// Revision 1.9 2007/01/11 10:21:19 olga
// Optimized Version 1.5.1beta , free for tests
//
// Revision 1.8 2006/11/01 11:17:29 olga
// Optimized agg sources of CSP algorithm, match usability,
// graph isomorphic copy,
// node/edge type multiplicity check for injective rule and match
//
// Revision 1.7 2006/08/02 09:00:57 olga
// Preliminary version 1.5.0 with
// - multiple node type inheritance,
// - new implemented evolutionary graph layouter for
// graph transformation sequences
//
// Revision 1.6 2006/05/08 08:24:12 olga
// Some extentions of GUI: - Undo Delete button of tool bar to undo deletions
// if grammar elements like rule, NAC, graph constraints;
// - the possibility to add a new graph to a grammar or a copy of the current
// host graph;
// - to set one or more layer for consistency constraints.
// Also some bugs fixed of matching and some optimizations of CSP algorithmus
// done.
//
// Revision 1.5 2006/04/06 09:28:53 olga
// Tuning of Import Type Graph and Import Graph
//
// Revision 1.4 2006/03/02 12:03:23 olga
// CPA: check host graph - done
//
// Revision 1.3 2006/03/01 09:55:46 olga
// - new CPA algorithm, new CPA GUI
//
// Revision 1.2 2005/09/08 16:25:02 olga
// Improved: editing attr. condition, importing graph, sorting node/edge types
//
// Revision 1.1 2005/08/25 11:56:56 enrico
// *** empty log message ***
//
// Revision 1.3 2005/07/11 09:30:20 olga
// This is test version AGG V1.2.8alfa .
// What is new:
// - saving rule option <disabled>
// - setting trigger rule for layer
// - display attr. conditions in gragra tree view
// - CPA algorithm <dependencies>
// - creating and display CPA graph with conflicts and/or dependencies
// based on (.cpx) file
//
// Revision 1.2.2.3 2005/08/01 10:08:47 enrico
// Bugfix for mapping selection
//
// Revision 1.2.2.2 2005/07/04 11:41:37 enrico
// basic support for inheritance
//
// Revision 1.2.2.1 2005/06/20 20:55:03 enrico
// ported changes from latest inheritance version
//
// Revision 1.2 2005/06/20 13:37:04 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.18 2005/03/03 13:48:42 olga
// - Match with NACs and attr. conditions with mixed variables - error corrected
// - save/load class packages written by user
// - PACs : creating T-equivalents - improved
// - save/load matches of the rules (only one match of a rule)
// - more friendly graph/rule editor GUI
// - more syntactical checks in attr. editor
//
// Revision 1.17 2004/11/15 11:24:45 olga
// Neue Optionen fuer Transformation;
// verbesserter default Graphlayout;
// Close GraGra mit Abfrage wenn was geaendert wurde statt Delete GraGra
//
// Revision 1.16 2004/10/25 14:24:37 olga
// Fehlerbehandlung bei CPs und Aenderungen im zusammenhang mit
// termination-Modul
// in AGG
//
// Revision 1.15 2004/05/26 16:17:40 olga
// Observer / observable
//
// Revision 1.14 2004/05/13 17:54:10 olga
// Fehlerbehandlung
//
// Revision 1.13 2003/12/18 16:26:23 olga
// Copy method and Layout
//
// Revision 1.12 2003/10/16 08:25:13 olga
// Copy rule implementiert
//
// Revision 1.11 2003/06/05 12:37:34 olga
// destroyNAC statt removeNAC
//
// Revision 1.10 2003/04/10 09:05:24 olga
// Aenderungen wegen serializable Ausgabe
//
// Revision 1.9 2003/03/06 14:47:59 olga
// neu: setEditable, isEditable
//
// Revision 1.8 2003/03/05 18:24:24 komm
// sorted/optimized import statements
//
// Revision 1.7 2003/01/15 11:36:50 olga
// Kleine Aenderung
//
// Revision 1.6 2002/11/25 15:03:51 olga
// Arbeit an den Typen.
//
// Revision 1.5 2002/09/30 10:31:32 komm
// added TypeException handling
//
// Revision 1.4 2002/09/23 12:24:06 komm
// added type graph in xt_basis, editor and GUI
//
// Revision 1.3 2002/09/19 16:21:23 olga
// Layout von Knoten geaendert.
//
// Revision 1.2 2002/09/05 16:15:04 olga
// nectCompletion tests
//
// Revision 1.1.1.1 2002/07/11 12:17:08 olga
// Imported sources
//
// Revision 1.14 2001/05/14 12:00:42 olga
// Graph Layout und Graphobject Layout optimiert.
//
// Revision 1.13 2001/03/22 15:49:18 olga
// Warnung Ausgabe audgebaut.
//
// Revision 1.12 2001/03/15 17:03:19 olga
// Layout korrektur.
//
// Revision 1.11 2001/03/15 11:51:55 olga
// Typefehler beseitigt.
//
// Revision 1.10 2001/03/14 17:31:28 olga
// Korrektur wegen Layout und XML
//
// Revision 1.9 2001/03/08 10:53:21 olga
// Das ist Stand nach der AGG GUI Reimplementierung.
//
// Revision 1.8 2000/12/21 09:48:53 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.7 2000/12/07 14:23:37 matzmich
// XML-Kram
// Man beachte: xerces (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird
// jetzt im CLASSPATH benoetigt.
//
// Revision 1.6.6.1 2000/11/06 09:32:32 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.6 2000/06/07 08:17:30 shultzke
// debug infos geloescht
//
// Revision 1.5 2000/06/05 10:19:21 olga
// Kleine Aenderung, dass weniger TypeSets und Types angelegt wereden.
//
// Revision 1.4 1999/10/11 10:24:11 shultzke
// kleine Bugfixes
//
