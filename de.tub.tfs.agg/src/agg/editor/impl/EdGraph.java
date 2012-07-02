package agg.editor.impl;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.FontMetrics;
import java.awt.Graphics;
import java.awt.Point;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Observable;
import java.util.Observer;
import java.util.Vector;
import java.util.Hashtable;
import javax.swing.undo.*;

import agg.attribute.AttrMapping;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.ContextView;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.util.Change;
import agg.xt_basis.Arc;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphKind;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Morphism;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;
import agg.xt_basis.UndirectedGraph;
import agg.xt_basis.agt.KernelRule;
import agg.xt_basis.agt.MultiRule;
import agg.gui.editor.EditorConstants;
import agg.layout.evolutionary.EvolutionaryGraphLayout;
import agg.util.Pair;


/**
 * This class specifies a graph visualization of the class agg.xt_basis.Graph, where 
 * objects of class agg.editor.impl.EdNode visualize objects of class agg.xt_basis.Node, 
 * objects of class agg.editor.impl.EdArc visualize objects of class agg.xt_basis.Arc. 
 * 
 * @author $Author: olga $
 * @version $Id: EdGraph.java,v 1.134 2010/12/17 15:43:25 olga Exp $
 */
public class EdGraph implements XMLObject, Observer, StateEditable {

	protected Graph bGraph; // basis Graph
	protected EdGraGra eGra;
	protected EdTypeSet typeSet;

	protected Vector<EdNode> nodes; 
	protected Vector<EdArc> arcs; 
	private Hashtable<Node, EdNode> basisNode2node;
		
	protected  Vector<EdNode> visibleNodes;
	protected  Vector<EdArc> visibleArcs;	
	protected  Vector<EdNode> selectedNodes;
	protected  Vector<EdArc> selectedArcs;
	
	protected  Vector<GraphObject> changedObjects;
	protected Vector<EdArc> inheritanceArcs;		
	private Vector<EdGraphObject> newAfterTransformStep;	
	private EvolutionaryGraphLayout itsLayouter;	
	
	protected  EdNode selectedNode;
	protected  EdArc selectedArc;
	private EdType inheritanceType;
	protected  EdGraphObject pickedObj;
	private EdGraph gCopy;

	protected boolean editable; // allows to edit graph
	protected boolean isTG; // TRUE when this is a type graph
	private boolean isCPA; // TRUE when this is a CPA graph
	protected int criticalStyle;	
	protected int hidden; // is used and changed if this graph is a type graph	
	protected boolean visibilityChecked; // is used and changed if this graph is a complete graph

	protected boolean changed;
	private boolean isTransformChange;
	private boolean isGraphTransformed;	
	protected  String errMsg;
	protected  boolean straightenArcs;
//	private boolean attrVisible;
	
	// for Graph Layout
	protected  boolean hasDefaultLayout;
	protected  boolean externalLayouting;
	private Dimension gDim; 
	private int ggen;
	private boolean staticNodeXY;
	private double itsScale;
	private boolean firstDraw=true;
	protected boolean nodeNumberChanged; 
	private boolean nodeRemoved;
	
	// undo / redo edit actions
	protected EditUndoManager undoManager;
	protected StateEdit newEdit;
	protected Pair<String, Vector<?>> undoObj;

	
	/** Creates an empty graph layout. The used object is NULL. */
	public EdGraph() {
		init();
		this.typeSet = new EdTypeSet();
	}

	/**
	 * Creates an empty graph layout with a type set specified by the EdTypeSet
	 * types
	 */
	public EdGraph(EdTypeSet types) {
		init();
		this.typeSet = (types != null) ? types : new EdTypeSet();
	}

	/** Creates a graph layout with the used object specified by the Graph graph */
	public EdGraph(Graph graph) {
		init();
		
		if (graph == null) 
			this.typeSet = new EdTypeSet();
		else {
			this.bGraph = graph;
			this.bGraph.addObserver(this);
			this.typeSet = new EdTypeSet(this.bGraph.getTypeSet());
			if (this.bGraph instanceof agg.xt_basis.TypeGraph) 
				this.isTG = true;
			
			makeGraphObjects();
		} 
	}


	/**
	 * Creates a graph layout with the used object specified by the Graph graph
	 * and the type set specified by the EdTypeSet types
	 */
	public EdGraph(Graph graph, EdTypeSet types) {
		init();
		if (graph == null) {
			this.typeSet = (types != null) ? types : new EdTypeSet();
		}
		else {
			this.bGraph = graph;
			this.bGraph.addObserver(this);
			this.typeSet = types;
			if (this.bGraph instanceof agg.xt_basis.TypeGraph) 
				this.isTG = true;
			
			makeGraphObjects();
		}
	}

	/**
	 * Creates a graph layout with the used object specified by the Graph graph,
	 * the type set specified by the EdTypeSet types and visibility of
	 * attributes specified by the attrsVisible.
	 */
	public EdGraph(Graph graph, EdTypeSet types, boolean attrsVisible) {
		this(graph, types);
//		this.attrVisible = attrsVisible;		
	}

	private void init() {
		nodes = new Vector<EdNode>(); 
		arcs = new Vector<EdArc>(); 
		basisNode2node = new Hashtable<Node, EdNode>();
		itsLayouter = new EvolutionaryGraphLayout(100, null);
		
		editable = true;
		criticalStyle = 0;
		visibilityChecked = true;
		itsScale = 1.0;
		firstDraw = true;
		errMsg = "";
	}
	
	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.nodes.trimToSize();
		this.arcs.trimToSize();
		if (this.visibleArcs != null)
			this.visibleArcs.trimToSize();
		if (this.visibleNodes != null)
			this.visibleNodes.trimToSize();
		if (selectedArcs != null)
			this.selectedArcs.trimToSize();
		if (selectedNodes != null)
			this.selectedNodes.trimToSize();
		if (this.inheritanceArcs != null)
			this.inheritanceArcs.trimToSize();
		if (changedObjects != null)
			this.changedObjects.trimToSize();
	}
	
	public void dispose() {
		if (this.bGraph != null)
			this.bGraph.deleteObserver(this);

		if (this.visibleArcs != null)
			this.visibleArcs.clear();
		if (this.visibleNodes != null)
			this.visibleNodes.clear();
		
		if (this.selectedNodes != null)
			this.selectedNodes.clear();
		if (this.selectedArcs != null)
			this.selectedArcs.clear();
		this.selectedNode = null;
		this.selectedArc = null;
		this.pickedObj = null;		

		if (this.inheritanceArcs != null) {
			for (int i = 0; i < this.inheritanceArcs.size(); i++) {
				this.arcs.remove(this.inheritanceArcs.get(i));			
				this.inheritanceArcs.get(i).dispose();			
			}
			this.inheritanceArcs.clear();
		}
		this.inheritanceType = null;
		
		for (int i = 0; i < this.arcs.size(); i++) {
			this.arcs.get(i).dispose();
		}
		for (int i = 0; i < this.nodes.size(); i++) {
			this.nodes.get(i).dispose();
		}
		this.arcs.clear();
		this.nodes.clear();
		this.basisNode2node.clear();
		
		if (this.newAfterTransformStep != null)
			this.newAfterTransformStep.clear();
		if (changedObjects != null)
			this.changedObjects.clear();
		
		this.bGraph = null;		
		this.itsLayouter = null;
		this.typeSet = null;
		this.gCopy = null;		
		this.eGra = null;
				
		if (this.undoObj != null) {
			this.undoObj.second.clear();
		}
		if (this.newEdit != null) {
			this.newEdit.die();
		}
		this.undoManager = null;
	}

	public void finalize() {}

	private void addElement(EdGraphObject go, boolean addBasisGraphObject) {
		go.setContext(this);
		if (go.isNode()) {
			if (addBasisGraphObject) {
				this.bGraph.addNode(((EdNode) go).getBasisNode());				
			} 
			this.basisNode2node.put((Node) go.getBasisObject(), (EdNode) go);
			this.nodes.add((EdNode) go);
			
			go.markElementOfTypeGraph(this.isTG);
			this.typeSet.addTypeUser(go.getType(), go);
			
			if (this.visibleNodes != null && this.visibleNodes.size() > 0) {
				this.visibilityChecked = false;
			}			
		} else {			
			if (addBasisGraphObject) {
				this.bGraph.addArc(((EdArc) go).getBasisArc());
			}
			this.arcs.add((EdArc) go);
			
			go.markElementOfTypeGraph(this.isTG);
			this.typeSet.addTypeUser(go.getType(), go);
			
			if (this.visibleArcs != null && this.visibleArcs.size() > 0) {
				this.visibilityChecked = false;
			}
		}
	}

	private void removeElement(EdGraphObject go) {
		if (go.isNode()) {
			removeNodeElement((EdNode) go);
		} 
		else { // isArc
			removeArcElement((EdArc) go);
		}
	}

	private void removeNodeElement(EdNode go) {
		if (go.isSelected()) {
			if (selectedNodes != null)
				this.selectedNodes.remove(go);
			if (this.selectedNode == go) 
				this.selectedNode = null;
		}
		if (this.visibleNodes != null)
			this.visibleNodes.remove(go);			
		this.nodes.remove(go);
			
		this.typeSet.removeTypeUser(go.getType(), go);			
		this.basisNode2node.remove(go.getBasisObject());
		
		if (this.pickedObj == go) 
			this.pickedObj = null;
		
		if (this.newAfterTransformStep != null)
			this.newAfterTransformStep.remove(go);
		
		go.dispose();
		
		if (this.bGraph.getKind() == GraphKind.LHS) 
			this.updateArcKeys();
	}
	
	private void removeArcElement(EdArc go) {
		if (go.isSelected()) {
			if (this.selectedArcs != null)
				this.selectedArcs.remove(go);			
			if (this.selectedArc == go) 
				this.selectedArc = null;
		}
		if (this.visibleArcs != null)
			this.visibleArcs.remove(go);
		this.arcs.remove(go);
			
		this.typeSet.removeTypeUser(go.getType(), go);
		
		if (this.pickedObj == go) 
			this.pickedObj = null;
		
		if (this.newAfterTransformStep != null)
			this.newAfterTransformStep.remove(go);
		
		go.dispose();
		
		if (this.bGraph.getKind() == GraphKind.LHS) 
			this.updateArcKeys();
	}
	
	private void removeBadArc(EdArc go, EdType nType) {
		if (go.isSelected()) {	
			if (this.selectedArcs != null)
				this.selectedArcs.remove(go);			
			if (this.selectedArc == go) 
				this.selectedArc = null;
		}
		if (this.visibleArcs != null)
			this.visibleArcs.remove(go);
		this.arcs.remove(go);
			
		this.typeSet.removeArcTypeUser(go.getType(), go, nType);		
		
		if (this.pickedObj == go) 
			this.pickedObj = null;
			
		if (this.newAfterTransformStep != null)
			this.newAfterTransformStep.remove(go);
			
		go.dispose();
			
		if (this.bGraph.getKind() == GraphKind.LHS) 
				this.updateArcKeys();
	}
	
//	private void removeDirtyArc(EdGraphObject go) {
//		if (go.isArc()) {
//			if (go.isSelected()) {
//				this.selectedArcs.remove(go);			
//				if (this.selectedArc == go) 
//					this.selectedArc = null;
//			}
//			this.visibleArcs.remove(go);
//			this.arcs.remove(go);
//		}		
//		if (this.pickedObj == go) 
//			this.pickedObj = null;		
//		this.newAfterTransformStep.remove(go);		
//		((EdArc)go).dispose();
//	}
	
	
	@SuppressWarnings("unused")
	private void updateNodeKeys() {
		for (int i=0; i<nodes.size(); i++) {
			nodes.get(i).setMorphismMark(i+1);
		}
	}
	
	
	private void updateArcKeys() {
		for (int i=0; i<this.arcs.size(); i++) {
			this.arcs.get(i).setMorphismMark(this.nodes.size()+i+1);
		}
	}
	
	public UndoManager getUndoManager() {
		return this.undoManager;
	}

	public void setUndoManager(EditUndoManager anUndoManager) {
		this.undoManager = anUndoManager;
	}

	public void undoManagerLastEditDie() {
		if (this.undoManager != null)
			this.undoManager.lastEditDie();
	}

	boolean undoManagerAddEdit(String presentationName) {
		String pName = presentationName;
		if (this.isTransformChange) {
			pName = presentationName + " (Transformation)";
		}
		this.newEdit = new StateEdit(this, pName);
		return this.undoManager.addEdit(this.newEdit);
	}

	/*
	private Pair<String, Vector<EdGraphObject>> revertEdit(
			Pair<String, Vector<EdGraphObject>> anUndoObj) {
		String first = anUndoObj.first;
		String kind = "";
		Vector<EdGraphObject> gos = new Vector<EdGraphObject>();
		gos.addAll(anUndoObj.second);

		if (first.equals(EditUndoManager.CREATE_DELETE))
			kind = EditUndoManager.DELETE_CREATE;
		else if (first.equals(EditUndoManager.DELETE_CREATE))
			kind = EditUndoManager.CREATE_DELETE;
		else if (first.equals(EditUndoManager.SELECT_DESELECT))
			kind = EditUndoManager.DESELECT_SELECT;
		else if (first.equals(EditUndoManager.DESELECT_SELECT))
			kind = EditUndoManager.SELECT_DESELECT;
		else if (first.equals(EditUndoManager.MOVE_GOBACK))
			kind = EditUndoManager.MOVE_GOBACK;
		else if (first.equals(EditUndoManager.CHANGE_ATTRIBUTE))
			kind = EditUndoManager.CHANGE_ATTRIBUTE;
		else if (first.equals(EditUndoManager.CHANGE_MULTIPLICITY))
			kind = EditUndoManager.CHANGE_MULTIPLICITY;
		else if (first.equals(EditUndoManager.CHANGE_PARENT))
			kind = EditUndoManager.CHANGE_PARENT;
		else if (first.equals(EditUndoManager.CHANGE_TYPE))
			kind = EditUndoManager.CHANGE_TYPE;
		else if (first.equals(EditUndoManager.CHANGE))
			kind = EditUndoManager.CHANGE;
		else if (first.equals(EditUndoManager.TARGET_UNSET_SET))
			kind = EditUndoManager.TARGET_SET_UNSET;
		else if (first.equals(EditUndoManager.TARGET_SET_UNSET))
			kind = EditUndoManager.TARGET_UNSET_SET;
		else if (first.equals(EditUndoManager.SOURCE_UNSET_SET))
			kind = EditUndoManager.SOURCE_SET_UNSET;
		else if (first.equals(EditUndoManager.SOURCE_SET_UNSET))
			kind = EditUndoManager.SOURCE_UNSET_SET;
		
		if (!kind.equals("") && !gos.isEmpty()) {
			return new Pair<String, Vector<EdGraphObject>>(kind, gos);
		}
		return null;
	}
*/
	
	public void undoManagerEndEdit() {
		if (!this.editable 
				|| this.undoManager == null 
				|| !this.undoManager.isEnabled()
				|| this.undoObj == null) {
			return;
		}
		String addEditKind = this.undoObj.first;
		String kind = "";
		Vector<?> gos = (Vector<?>) this.undoObj.second.clone();
//	System.out.println("undoManagerEndEdit:: "+gos);
		if (addEditKind.equals(EditUndoManager.CREATE_DELETE))
			kind = EditUndoManager.DELETE_CREATE;
		else if (addEditKind.equals(EditUndoManager.DELETE_CREATE))
			kind = EditUndoManager.CREATE_DELETE;
		else if (addEditKind.equals(EditUndoManager.SELECT_DESELECT))
			kind = EditUndoManager.DESELECT_SELECT;
		else if (addEditKind.equals(EditUndoManager.DESELECT_SELECT))
			kind = EditUndoManager.SELECT_DESELECT;
		else if (addEditKind.equals(EditUndoManager.MOVE_GOBACK))
			kind = EditUndoManager.MOVE_GOBACK;
		else if (addEditKind.equals(EditUndoManager.CHANGE_ATTRIBUTE))
			kind = EditUndoManager.CHANGE_ATTRIBUTE;
		else if (addEditKind.equals(EditUndoManager.CHANGE_MULTIPLICITY))
			kind = EditUndoManager.CHANGE_MULTIPLICITY;
		else if (addEditKind.equals(EditUndoManager.CHANGE_PARENT))
			kind = EditUndoManager.CHANGE_PARENT;
		else if (addEditKind.equals(EditUndoManager.CHANGE_TYPE))
			kind = EditUndoManager.CHANGE_TYPE;
		else if (addEditKind.equals(EditUndoManager.CHANGE))
			kind = EditUndoManager.CHANGE;
		else if (addEditKind.equals(EditUndoManager.TARGET_UNSET_SET))
			kind = EditUndoManager.TARGET_SET_UNSET;
		else if (addEditKind.equals(EditUndoManager.TARGET_SET_UNSET))
			kind = EditUndoManager.TARGET_UNSET_SET;
		else if (addEditKind.equals(EditUndoManager.SOURCE_UNSET_SET))
			kind = EditUndoManager.SOURCE_SET_UNSET;
		else if (addEditKind.equals(EditUndoManager.SOURCE_SET_UNSET))
			kind = EditUndoManager.SOURCE_UNSET_SET;
		
//		System.out.println("EdGraph:: undoManagerEndEdit:: "+kind);
		if (!kind.equals("") && !gos.isEmpty()) {
			endEdit(gos, kind);
		}
	}

	private void endEdit(Vector<?> gos, String kind) {
//		System.out.println("EdGraph.endEdit:: kind: "+kind+" gos: "+gos);
		this.undoObj = new Pair<String, Vector<?>>(kind, gos);
		this.undoManager.end(this.newEdit);
	}

	protected void addEdit(Vector<?> gos, String kind, String presentation) {
		if (kind.equals("") || gos.isEmpty()) {
			return;
		}
		this.undoObj = new Pair<String, Vector<?>>(kind, gos);
		// System.out.println("EdGraph.addEdit:: kind: "+kind+" gos: "+gos);
		undoManagerAddEdit(presentation);
	}

	public void addCreatedToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<EdGraphObject> gos = new Vector<EdGraphObject>(1);
		gos.add(go);
		addEdit(gos, EditUndoManager.CREATE_DELETE, "Undo Creating");
	}

	public void addCreatedToUndo(Vector<EdGraphObject> gos) {		
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		addEdit(gos, EditUndoManager.CREATE_DELETE, "Undo Creating");
	}

	public void addDeletedToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<EdGraphObject> gos = new Vector<EdGraphObject>(1);
		gos.add(go);
		addEdit(gos, EditUndoManager.DELETE_CREATE, "Undo Deleting");
	}

	public void addDeletedToUndo(Vector<EdGraphObject> gos) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		addEdit(gos, EditUndoManager.DELETE_CREATE, "Undo Deleting");
	}

	public void addCommonDeletedToUndo(Vector<EdGraphObject> gos) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		
		addEdit(gos, EditUndoManager.DELETE_CREATE, "Undo Deleting");
	}

	public void addDeleteSelectedToUndo() {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>();
		if (this.selectedNodes != null) {
			vec.addAll(this.selectedNodes);
			for (int i = 0; i < this.selectedNodes.size(); i++) {
				EdNode n = this.selectedNodes.get(i);
				for (int j = 0; j < this.arcs.size(); j++) {
					EdArc a = this.arcs.elementAt(j);
					if (a.getTarget().equals(n)) {
						if (this.selectedArcs == null && !this.selectedArcs.contains(a))
							vec.add(a);
					} else if (a.getSource().equals(n)) {
						if (this.selectedArcs == null && !this.selectedArcs.contains(a))
							vec.add(a);
					}
				}
			}
		}
		if (this.selectedArcs != null) 
			vec.addAll(this.selectedArcs);
		
		if (vec.size() > 0)
			addEdit(vec, EditUndoManager.DELETE_CREATE, "Undo Deleting");
	}

//	public void addDeselectedToUndo(EdGraphObject go) {
//		if (undoManager == null || !undoManager.isEnabled() || !editable) {
//			return;
//		}
//		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(1);
//		vec.add(go);
//		addEdit(vec, EditUndoManager.DESELECT_SELECT, "Undo Deselecting");
//	}
//
//	public void addDeselectedToUndo() {
//		if (undoManager == null || !undoManager.isEnabled() || !editable) {
//			return;
//		}
//		Vector<EdGraphObject> 
//		gos = new Vector<EdGraphObject>(selectedNodes.size()+ selectedArcs.size());
//		gos.addAll(selectedNodes);
//		gos.addAll(selectedArcs);
//		addEdit(gos, EditUndoManager.DESELECT_SELECT, "Undo Deselecting");
//	}

	public void addChangedAttributeToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<EdGraphObject> gos = new Vector<EdGraphObject>();
		gos.add(go);
		addEdit(gos, EditUndoManager.CHANGE_ATTRIBUTE,
				"Undo Changing Attribute");
	}

	public void addChangedAttributeToUndo(Vector<EdGraphObject> gos) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		addEdit(gos, EditUndoManager.CHANGE_ATTRIBUTE,
				"Undo Changing Attribute");
	}

	public void addChangedTypeToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<String> gos = new Vector<String>();
		gos.add(String.valueOf(go.hashCode()));
		addEdit(gos, EditUndoManager.CHANGE_TYPE, "Undo Changing");
	}

	public void addChangedMultiplicityToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<String> vec = new Vector<String>();
		vec.add(String.valueOf(go.hashCode()));
		addEdit(vec, EditUndoManager.CHANGE_MULTIPLICITY,
				"Undo Changing Type Multiplicity");
	}

	public void addChangedParentToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		// go is a type graph node
		Vector<EdGraphObject> gos = new Vector<EdGraphObject>();
		gos.add(go);
//		gos.addAll(eGra.getGraphObjectsOfType(go.getType(), false));
		
//		Vector<String> vec = new Vector<String>();
//		for (int i=0; i<gos.size(); i++) {
//			vec.add(String.valueOf(gos.get(i).hashCode()));
//		}
//		gos.clear();
		
		addEdit(gos, EditUndoManager.CHANGE_PARENT,
				"Undo Changing Node Inheritance");
	}

	public void addUnsetTargetOfArcToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable
				|| !go.isArc()) {
			return;
		}
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(1);
		vec.add(go);
		addEdit(vec, EditUndoManager.TARGET_UNSET_SET, "Unset Target");
	}
	
	public void addSetTargetOfArcToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable
				|| !go.isArc()) {
			return;
		}
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(1);
		vec.add(go);
		addEdit(vec, EditUndoManager.TARGET_SET_UNSET, "Set Target");
	}
	
	public void addUnsetSourceOfArcToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable
				|| !go.isArc()) {
			return;
		}
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(1);
		vec.add(go);
		addEdit(vec, EditUndoManager.SOURCE_UNSET_SET, "Unset Source");
	}
	
	public void addSetSourceOfArcToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable
				|| !go.isArc()) {
			return;
		}
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(1);
		vec.add(go);
		addEdit(vec, EditUndoManager.SOURCE_SET_UNSET, "Set Source");
	}
	
	public void addMovedToUndo(EdGraphObject go) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(1);
		vec.add(go);
		addEdit(vec, EditUndoManager.MOVE_GOBACK, "Undo Moving");
	}

	public void addMovedToUndo(Vector<EdGraphObject> gos) {
		if (this.undoManager == null || !this.undoManager.isEnabled() || !this.editable) {
			return;
		}
		addEdit(gos, EditUndoManager.MOVE_GOBACK, "Undo Moving");
	}

	public void storeState(Hashtable<Object, Object> state) {
		if (this.undoObj == null 
				|| this.undoObj.first == null
				|| this.undoObj.second == null) {
			return;
		}

		String op = this.undoObj.first;
//		System.out.println("EdGraph.storeState...  "+op+"   "+undoObj.second);
		if (op.equals(EditUndoManager.DELETE_CREATE)) {
			Vector<?> vec = this.undoObj.second;			
//			System.out.println("EdGraph.storeState...  DELETE_CREATE  "+vec);
			Vector<Object> vecData = new Vector<Object>(vec.size());
			for (int i = 0; i < vec.size(); i++) {
				Object obj = vec.get(i);
				EdGraphObject go = null;
				if (obj instanceof EdGraphObject) {
					go = (EdGraphObject) obj;
				} else if (obj instanceof String) {					
					String hashCode = (String) obj;
					go = this.findGraphObject(hashCode);
				}
				if (go != null) {
					if (go.isNode()) {
						NodeReprData data = new NodeReprData((EdNode) go);
						data.storeState(state);
						vecData.add(data);
					} else {
						ArcReprData data = new ArcReprData((EdArc) go);
						data.storeState(state);
						vecData.add(data);
					} 
				}
			}
			this.undoObj.second.clear();
			this.undoObj.second = vecData;
		} else if (op.equals(EditUndoManager.CREATE_DELETE)
				|| op.equals(EditUndoManager.CHANGE_ATTRIBUTE)) {
			Vector<?> vec = this.undoObj.second;
//			System.out.println("EdGraph.storeState...  CREATE_DELETE / CHANGE_ATTRIBUTE  "+vec);
			Vector<String> vecData = new Vector<String>(vec.size());
			for (int i = 0; i < vec.size(); i++) {
				Object obj = vec.get(i);
				EdGraphObject go = null;
				if (obj instanceof String) {					
					String hashCode = (String) obj;
					go = this.findGraphObject(hashCode);
				} else if (obj instanceof EdGraphObject) {
					go = (EdGraphObject) obj;
				} else if (obj instanceof NodeReprData) {
					vecData.add(String.valueOf(((NodeReprData) obj).nodeHashCode));
				} else if (obj instanceof ArcReprData) {
					vecData.add(String.valueOf(((ArcReprData) obj).arcHashCode));	
				}
				
				if (go != null) {
					if (go.isNode()) {	
//						System.out.println("Node::  "+go.getType().getBasisType().getAdditionalRepr());
						((EdNode) go).storeState(state);
					}
					else {
//						System.out.println("Arc::  "+go.getType().getBasisType().getAdditionalRepr());
						((EdArc) go).storeState(state);
					}
					vecData.add(String.valueOf(go.hashCode()));
				}
			}
			this.undoObj.second.clear();
			this.undoObj.second = vecData;
			
		} else if (op.equals(EditUndoManager.TARGET_UNSET_SET)
					|| op.equals(EditUndoManager.SOURCE_UNSET_SET)) {
			Vector<?> vec = this.undoObj.second;			
//			System.out.println("EdGraph.storeState...  TARGET_UNSET  "+vec);
			Vector<Object> vecData = new Vector<Object>(vec.size());
			for (int i = 0; i < vec.size(); i++) {
				Object obj = vec.get(i);
				EdArc go = null;
				if (obj instanceof EdArc) {
					go = (EdArc) obj;
				} else if (obj instanceof String) {					
					String hashCode = (String) obj;
					go = (EdArc) this.findGraphObject(hashCode);
				}
				if (go != null) {				
					ArcReprData data = new ArcReprData(go);
					data.storeState(state);
					vecData.add(data);
				}
			}
			this.undoObj.second.clear();
			this.undoObj.second = vecData;
		} else if (op.equals(EditUndoManager.TARGET_SET_UNSET)
				|| op.equals(EditUndoManager.SOURCE_SET_UNSET)) {
			Vector<?> vec = this.undoObj.second;
//			System.out.println("EdGraph.storeState...  TARGET_SET  "+vec);
			Vector<String> vecData = new Vector<String>(vec.size());
			for (int i = 0; i < vec.size(); i++) {
				Object obj = vec.get(i);
				EdArc go = null;
				if (obj instanceof String) {					
					String hashCode = (String) obj;
					go = (EdArc) this.findGraphObject(hashCode);
				} else if (obj instanceof EdArc) {
					go = (EdArc) obj;
				} else if (obj instanceof ArcReprData) {
					vecData.add(String.valueOf(((ArcReprData) obj).arcHashCode));	
				}
				
				if (go != null) {
//					System.out.println("Arc::  "+go.getType().getBasisType().getAdditionalRepr());
					go.storeState(state);
					vecData.add(String.valueOf(go.hashCode()));
				}
			}
		
		} else {			
			Vector<?> vec = this.undoObj.second;
//			System.out.println("EdGraph.storeState::  "+op+"    "+vec);
			Vector<String> vecData = new Vector<String>(vec.size());
			for (int i = 0; i < vec.size(); i++) {
				Object obj = vec.get(i);
				EdGraphObject go = null;
				if (obj instanceof String) {
					go = this.findGraphObject((String) obj);
				}
				else if (obj instanceof EdGraphObject) {
					go = (EdGraphObject) obj;
				}
				if (go != null) {
					if (go.isNode()) {
						((EdNode) go).storeState(state);
					}
					else {
						((EdArc) go).storeState(state);
					}
					vecData.add(String.valueOf(go.hashCode()));
				}
			}
			this.undoObj.second.clear();
			this.undoObj.second = vecData;
		}
			
		state.put(this, this.undoObj);

		if (this.undoManager.isUndoEndOfTransformStepAllowed()) {
			this.undoManager.setUndoEndOfTransformStep();
			this.undoManager.setUndoEndOfTransformStepAllowed(false);
		}
	}

	public void restoreState(Hashtable<?, ?> state) {		
		if (state != null) {
			if (state.get(this) != null) {	
				try {
					restoreStateOfGraph(this, state);
				} catch (TypeException ex) {
					this.undoObj = (Pair<String, Vector<?>>) state.get(this);
					this.newEdit = new StateEdit(this, this.undoObj.first);
					this.undoManager.addEdit(this.newEdit);
				}
				return;
			}

			Enumeration<?> keys = state.keys();
			while (keys.hasMoreElements()) {
				Object key = keys.nextElement();
				if (key instanceof EdGraph) {
					if (((EdGraph) key) != this) {
						try {					
							restoreStateOfGraph(key, state);
						} catch (TypeException ex) {
							this.undoObj = (Pair<String, Vector<?>>) state.get(this);
							this.newEdit = new StateEdit(this, this.undoObj.first);
							this.undoManager.addEdit(this.newEdit);
						}	
					}
					break;
				} else if (key instanceof EdNode) {
					restoreStateOfNode(key, state);
				} else if (key instanceof EdArc) {
					restoreStateOfArc(key, state);
				}
			}
		}
	}

	private void restoreStateOfGraph(Object key, Hashtable<?, ?> state) throws TypeException {
		Object obj = state.get(key);
		if (obj == null || !(obj instanceof Pair)) {
			return;
		}
		String op = (String) ((Pair<?,?>) obj).first;
		Vector<?> vec = (Vector<?>) ((Pair<?,?>) obj).second;
//		System.out.println("\nEdGraph.restoreStateOfGraph... graph: "+this.getName()+"   "+op+" vec: "+vec);
		if (!vec.isEmpty()) {
			if (op.equals(EditUndoManager.CREATE_DELETE)) {			
				for (int i = 0; i < vec.size(); i++) {
					int tgCheckLevel = this.bGraph.getTypeSet()
							.getLevelOfTypeGraphCheck();
					if (tgCheckLevel == TypeSet.ENABLED_MAX_MIN) {
						this.bGraph.getTypeSet().setLevelOfTypeGraphCheck(
								TypeSet.ENABLED_MAX);
					}
//					System.out.println(vec.get(i));
					if (vec.get(i) instanceof String) {
						String hashCode = (String) vec.get(i);
						EdGraphObject o = this.findRestoredObject(hashCode);
						if (o != null) {
							try {
								propagateRemoveGraphObjectToMultiRule(o);
								deleteObj(o, true);						
							} catch (TypeException ex) {
								throw ex;
							}
						}
					} else if (vec.get(i) instanceof EdGraphObject) {
//						System.out.println("\nEdGraph.restoreStateOfGraph... graph: "+this.getName()+"  EdGraphObject ");
						EdGraphObject o = findRestoredObject((EdGraphObject) vec
								.get(i));
						if (o != null) {
							try {
								propagateRemoveGraphObjectToMultiRule(o);
								deleteObj(o, true);
							} catch (TypeException ex) {
								throw ex;
							}
						}
					} else if (vec.get(i) instanceof NodeReprData) {
//						System.out.println("\nEdGraph.restoreStateOfGraph... graph: "+this.getName()+"  NodeReprData ");
						NodeReprData data = (NodeReprData) vec.get(i);
						EdGraphObject o = this.findRestoredObject(data.nodeHC);
						if (o != null) {
							try {
								deleteObj(o, true);
								propagateRemoveGraphObjectToMultiRule(o);
							} catch (TypeException ex) {
								throw ex;
							}
						}
					} else if (vec.get(i) instanceof ArcReprData) {
//						System.out.println("\nEdGraph.restoreStateOfGraph... graph: "+this.getName()+"  ArcReprData ");
						ArcReprData data = (ArcReprData) vec.get(i);
						EdGraphObject o = this.findRestoredObject(data.arcHC);
						if (o != null) {
							try {
								deleteObj(o, true);
								propagateRemoveGraphObjectToMultiRule(o);
							} catch (TypeException ex) {
								throw ex;
							}
						}
					}
					this.bGraph.getTypeSet().setLevelOfTypeGraphCheck(tgCheckLevel);
				}
			} else if (op.equals(EditUndoManager.DELETE_CREATE)) {
//				System.out.println("EdGraph.restoreStateOfGraph:: EditUndoManager.DELETE_CREATE :  "+vec);
				final Vector<EdNode> restoredNodes = new Vector<EdNode>();
				
				for (int i = 0; i < vec.size(); i++) {
					Object data = vec.get(i);
//					if (data instanceof String) {
//						String hashCode = (String) data;
//						EdGraphObject o = this.findRestoredObject(hashCode);
//						
//					} else 
					if (data instanceof NodeReprData) {
//						System.out.println("\nEdGraph.restoreStateOfGraph... graph: "+this.getName()+"  NodeReprData ");
						((NodeReprData) data).restoreState(state);
						TypeReprData typedata = ((NodeReprData) data)
								.getNodeTypeReprData();
						if (this.typeSet.containsNodeType(typedata.getName(),
								typedata.getShape(), typedata.getColor(), typedata.hasFilledShape())) {
							EdNode go = ((NodeReprData) data)
									.createNodeFromNodeRepr(this);
							if (go != null) {
								go.setContext(this);
								if (this.bGraph.isTypeGraph()) {
									this.refreshInheritanceArcs();
								}
								go.refreshAttributeInstance();
								restoredNodes.add(go);
								go.getLNode().setFrozenByDefault(true);	
								
								propagateAddGraphObjectToMultiRule(go);
							}
						}
					} else if (data instanceof ArcReprData) {
						((ArcReprData) data).restoreState(state);
						TypeReprData typedata = ((ArcReprData) data)
								.getArcTypeReprData();					
						if (this.typeSet.containsArcType(typedata.getName(),
								typedata.getShape(), typedata.getColor())) {
							EdArc go = ((ArcReprData) data)
									.createArcFromArcRepr(this, restoredNodes);
							if (go != null) {
								go.setContext(this);
								go.refreshAttributeInstance();
								go.getLArc().setFrozenByDefault(true);
								
								propagateAddGraphObjectToMultiRule(go);
							}
						}
					}
				}
				restoredNodes.clear();
				
			} else if (op.equals(EditUndoManager.TARGET_SET_UNSET)
					|| op.equals(EditUndoManager.SOURCE_SET_UNSET)) {				
				for (int i = 0; i < vec.size(); i++) {
					int tgCheckLevel = this.bGraph.getTypeSet()
							.getLevelOfTypeGraphCheck();
					if (tgCheckLevel == TypeSet.ENABLED_MAX_MIN) {
						this.bGraph.getTypeSet().setLevelOfTypeGraphCheck(
								TypeSet.ENABLED_MAX);
					}
//					System.out.println(vec.get(i));
					if (vec.get(i) instanceof String) {
						String hashCode = (String) vec.get(i);
						EdArc o = (EdArc) this.findRestoredObject(hashCode);
						if (o != null) {
							try {
								delSelectedArc(o, false);
							} catch (TypeException ex) {
								throw ex;
							}
						}
					} else if (vec.get(i) instanceof EdGraphObject) {
						EdArc o = (EdArc) findRestoredObject((EdArc) vec.get(i));
						if (o != null) {
							try {
								delSelectedArc(o, false);
							} catch (TypeException ex) {
								throw ex;
							}
						}
					} else if (vec.get(i) instanceof ArcReprData) {
						ArcReprData data = (ArcReprData) vec.get(i);
						EdArc o = (EdArc) this.findRestoredObject(data.arcHC);
						if (o != null) {
							try {
								delSelectedArc(o, false);
							} catch (TypeException ex) {
								throw ex;
							}
						}
					}
					this.bGraph.getTypeSet().setLevelOfTypeGraphCheck(tgCheckLevel);
				}
				
			} else if (op.equals(EditUndoManager.TARGET_UNSET_SET)
					|| op.equals(EditUndoManager.SOURCE_UNSET_SET)) {
				
				final Vector<EdNode> restoredNodes = new Vector<EdNode>();
				
				for (int i = 0; i < vec.size(); i++) {
					Object data = vec.get(i);
//					if (data instanceof String) {
//						String hashCode = (String) data;
//						EdGraphObject o = this.findRestoredObject(hashCode);
//						
//					} else 
					if (data instanceof ArcReprData) {
						((ArcReprData) data).restoreState(state);
						TypeReprData typedata = ((ArcReprData) data)
								.getArcTypeReprData();					
						if (this.typeSet.containsArcType(typedata.getName(),
								typedata.getShape(), typedata.getColor())) {
							EdArc go = ((ArcReprData) data)
									.createArcFromArcRepr(this, restoredNodes);
							if (go != null) {
								go.setContext(this);
								go.refreshAttributeInstance();
								go.getLArc().setFrozenByDefault(true);
							}
						}
					}
				}
				restoredNodes.clear();
				
			} else if (op.equals(EditUndoManager.SELECT_DESELECT)) {
				for (int i = 0; i < vec.size(); i++) {
					String hashCode = (String) vec.get(i);
					EdGraphObject o = findRestoredObject(hashCode);
					if (o != null) {
						if (o.isNode()) {
							((EdNode) o).restoreState(state, hashCode);
							if (this.selectedNodes != null) {							
								if (o.isSelected()) {
									o.deselect();
									if (this.selectedNodes.contains(o))
										this.selectedNodes.remove(o);
								} else {
									if (this.selectedNodes.contains(o))
										this.selectedNodes.remove(o);
								}
							}
						} else {
							((EdArc) o).restoreState(state, hashCode);
							if (this.selectedArcs != null) {
								if (o.isSelected()) {
									o.deselect();
									if (this.selectedArcs.contains(o))
										this.selectedArcs.remove(o);
								} else {
									if (this.selectedArcs.contains(o))
										this.selectedArcs.remove(o);
								}
							}
						}
					}
				}
			} else if (op.equals(EditUndoManager.DESELECT_SELECT)) {
				for (int i = 0; i < vec.size(); i++) {
					String hashCode = (String) vec.get(i);
					EdGraphObject o = findRestoredObject(hashCode);
					if (o != null) {
						if (o.isNode()) {
							((EdNode) o).restoreState(state, hashCode);
							if (o.isSelected()) {
								if (!this.selectedNodes.contains(o))
									this.selectedNodes.add(((EdNode) o));
							} else {
								o.select();
								if (!this.selectedNodes.contains(o))
									this.selectedNodes.add(((EdNode) o));
							}
						} else {
							((EdArc) o).restoreState(state, hashCode);
							if (o.isSelected()) {
								if (!this.selectedArcs.contains(o))
									this.selectedArcs.add(((EdArc) o));
							} else {
								o.select();
								if (!this.selectedArcs.contains(o))
									this.selectedArcs.add(((EdArc) o));
							}
						}
					}
				}
			} else if (op.equals(EditUndoManager.MOVE_GOBACK)) {
//				System.out.println("EdGraph.restoreState:: EditUndoManager.MOVE_GOBACK  "+vec);
				for (int i = 0; i < vec.size(); i++) {
					String hashCode = (String) vec.get(i);
					EdGraphObject o = this.findRestoredObject(hashCode);
					if (o != null) {
						if (o.isNode()) {
							((EdNode) o).restoreState(state, hashCode);							
						} else {
							((EdArc) o).restoreState(state, hashCode);							
						}
					}
				}
			} else if (op.equals(EditUndoManager.CHANGE_ATTRIBUTE)) {
//				System.out.println("EdGraph.restoreStateOfGraph:: EditUndoManager.CHANGE_ATTRIBUTE "+vec);			 
				for (int i = 0; i < vec.size(); i++) {
					String hashCode = (String) vec.get(i);
					EdGraphObject o = this.findRestoredObject(hashCode);
					if (o != null) {
						if (o.isNode()) {
							((EdNode) o).restoreState(state, hashCode);
						}
						else {
							((EdArc) o).restoreState(state, hashCode);
						}
					}
				}
			} else if (op.equals(EditUndoManager.CHANGE_MULTIPLICITY)
					|| op.equals(EditUndoManager.CHANGE_TYPE)) {
				for (int i = 0; i < vec.size(); i++) {
					String hashCode = (String) vec.get(i);
					EdGraphObject o = this.findRestoredObject(hashCode);
					if (o != null) {						
						if (o.isNode())
							((EdNode) o).restoreState(state, hashCode);
						else
							((EdArc) o).restoreState(state, hashCode);						
					}
				}
			} else if (op.equals(EditUndoManager.CHANGE_PARENT)) {
				for (int i = 0; i < vec.size(); i++) {
					String hashCode = (String) vec.get(i);
					EdGraphObject o = this.findRestoredObject(hashCode);
					if (o != null) {						
						if (o.isNode()) {
							((EdNode) o).restoreState(state, hashCode);
							if (this.isTG) {
								this.refreshInheritanceArcs();
							}
						}
					} 
//					else if (((EdNode) go).getContext() != this) {
//						o = go.getContext().findRestoredObject(go);
//						if (o != null) { 
//							if (o != go)
//								((EdNode) o).restoreState(new NodeReprData(
//										(EdNode) go));
//
//							if (o.getContext().isTG)
//								o.getContext().refreshInheritanceArcs();
//						}
//					}
				}
			}
		} 
		else {
			this.undoManager.lastEditDie();
		}		
	}

	private void propagateAddGraphObjectToMultiRule(final EdGraphObject obj) {
		if (this.eGra != null && isSourceGraphOfGraphEmbedding()) {
			EdRuleScheme rs = this.eGra.getRuleScheme(this.bGraph);
			if (rs != null)
				rs.propagateAddGraphObjectToMultiRule(obj);
		}
	}
	
	private void propagateRemoveGraphObjectToMultiRule(final EdGraphObject obj) {
		if (this.eGra != null && isSourceGraphOfGraphEmbedding()) {
			EdRuleScheme rs = this.eGra.getRuleScheme(this.bGraph);
			if (rs != null)
				rs.propagateRemoveGraphObjectToMultiRule(obj);
		}
	}
	
	private void restoreStateOfNode(Object key, Hashtable<?, ?> state) {
//		 System.out.println("EdGraph.restoreStateOfNode:: "+key);
		if (key instanceof EdNode) {
			((EdNode) key).restoreState(state);
			EdGraphObject o = findRestoredObject(((EdNode) key));
			if (o != null) {
				if (o != (EdNode) key)
					((EdNode) o).restoreState(new NodeReprData((EdNode) key));
				if (this.selectedNodes != null) {
					if (o.isSelected()) {
						if (!this.selectedNodes.contains(o))
							this.selectedNodes.add(((EdNode) o));
					} else if (this.selectedNodes.contains(o))
						this.selectedNodes.remove(o);
				}
				if (this.isTG)
					this.refreshInheritanceArcs();
			} else if (((EdNode) key).getContext() != null
					&& ((EdNode) key).getContext() != this) {
				o = ((EdNode) key).getContext().findRestoredObject(
						((EdNode) key));

				if (o != null) {
					if (o != (EdNode) key)
						((EdNode) o)
								.restoreState(new NodeReprData((EdNode) key));
					if (o.getContext().isTG)
						o.getContext().refreshInheritanceArcs();
				}
			}
		}
	}

	private void restoreStateOfArc(Object key, Hashtable<?, ?> state) {
//		 System.out.println("EdGraph.restoreStateOfArc:: "+key);
		if (key instanceof EdArc) {
			((EdArc) key).restoreState(state);
			EdGraphObject o = findRestoredObject(((EdArc) key));
			if (o != null) {
				if (o != (EdArc) key)
					((EdArc) o).restoreState(new ArcReprData((EdArc) key));
				if (this.selectedArcs != null) {
					if (o.isSelected()) {
						if (!this.selectedArcs.contains(o))
							this.selectedArcs.add(((EdArc) o));
					} else if (this.selectedArcs.contains(o))
						this.selectedArcs.remove(o);
				}
			} else if (((EdArc) key).getContext() != null
					&& ((EdArc) key).getContext() != this) {
				o = ((EdArc) key).getContext()
						.findRestoredObject(((EdArc) key));

				if (o != null) {
					if (o != (EdArc) key)
						((EdArc) o).restoreState(new ArcReprData((EdArc) key));
				}
			}
		}
	}

	protected EdGraphObject findRestoredObject(EdGraphObject go) {
		if (go.isNode()) {
			for (int i = 0; i < this.nodes.size(); i++) {
				EdGraphObject o = this.nodes.get(i);
				if (o == go
						|| o.getContextUsage().contains(
								String.valueOf(go.hashCode())))
					return o;
			}
		} else if (go.isArc()) {
			for (int i = 0; i < this.arcs.size(); i++) {
				EdGraphObject o = this.arcs.get(i);
				if (o == go
						|| o.getContextUsage().contains(
								String.valueOf(go.hashCode())))
					return o;
			}
		}
		return null;
	}

	protected EdGraphObject findRestoredObject(String hashCode) {
		return findGraphObject(hashCode);
	}
	
	protected EdGraphObject findGraphObject(String hashCode) {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdGraphObject o = this.nodes.get(i);
			if (o.getContextUsage().contains(hashCode))
				return o;
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdGraphObject o = this.arcs.get(i);
			if (o.getContextUsage().contains(hashCode))
				return o;
		}
		return null;
	}
	
	

	protected EdGraphObject findNode(String hashCode) {		
		for (int i = 0; i < this.nodes.size(); i++) {
			EdGraphObject o = this.nodes.get(i);
			if (o.getContextUsage().contains(hashCode))
				return o;
		}
		return null;
	}
	
	protected EdGraphObject findArc(String hashCode) {
		for (int i = 0; i < this.arcs.size(); i++) {
			EdGraphObject o = this.arcs.get(i);
			if (o.getContextUsage().contains(hashCode))
				return o;
		}
		return null;
	}
	
	protected EdGraphObject findRestoredNode(String hashCode) {
		return findNode(hashCode);
	}
	
	protected EdGraphObject findRestoredArc(String hashCode) {
		return findArc(hashCode);
	}
	
	private void makeGraphObjects() {
		if (this.bGraph == null || this.bGraph.isEmpty()) {
			return;
		}
		// remove old layout nodes and arcs
		if (!this.nodes.isEmpty()) {
			disposeGraphObjects();
		}
		// create a new layout
		Iterator<?> elems = this.bGraph.getNodesSet().iterator();
		while (elems.hasNext()) {
			Node n = (Node) elems.next();
			newNode(n);
		}
		// create a new layout of arcs with default anchor
		elems = this.bGraph.getArcsSet().iterator();
		while (elems.hasNext()) {
			Arc a = (Arc) elems.next();
			newArc(a, this.basisNode2node.get((Node)a.getSource()), this.basisNode2node.get((Node)a.getTarget()));
		}
		// create inheritance arcs
		if (this.isTG)
			this.makeInheritanceArcs();
	}

	/**
	 * Creates layout nodes and edges of the new basis node and edges.
	 * The location of the new layout nodes is set to default.
	 */
	public void makeGraphObjectsOfNewBasisObjects(boolean selectnew) {
		if (this.bGraph == null || this.bGraph.isEmpty())
			return;
		Iterator<?> elems = this.bGraph.getNodesSet().iterator();
		while (elems.hasNext()) {
			Node bn = (Node) elems.next();
			if (this.findNode(bn) == null) {
				EdNode n = newNode(bn);
				if (selectnew)
					this.select(n);
			}
		}
		// create a new layout of arcs with default anchor
		elems = this.bGraph.getArcsSet().iterator();
		while (elems.hasNext()) {
			Arc ba = (Arc) elems.next();
			if (this.findArc(ba) == null) {
				EdArc a = newArc(ba);
				if (selectnew)
					this.select(a);
			}
		}
		// create inheritance arcs
		if (this.isTG)
			this.makeInheritanceArcs();
	}

	/** Gets my used object */
	public Graph getBasisGraph() {
		return this.bGraph;
	}

	public String getName() {
		if (this.bGraph != null)
			return this.bGraph.getName();
		
		return "unnamed";
	}

	/** Gets my gragra layout */
	public EdGraGra getGraGra() {
		return this.eGra;
	}

	/** Sets my gragra layout */
	public void setGraGra(EdGraGra egra) {
		if (egra == null) {
			return;
		}
		this.eGra = egra;
		if (this.eGra.getTypeSet() != null 
				&& this.eGra.getTypeSet() != this.typeSet) {
			setTypeSet(egra.getTypeSet());
		}
		if (this.isTG) {
			this.eGra.setChanged(true);
		}
	}

	/** Gets my type set */
	public EdTypeSet getTypeSet() {
		return this.typeSet;
	}

	/** Sets my type set to the set specified by the EdTypeSet types */
	public void setTypeSet(EdTypeSet types) {
		if (this.typeSet != null) {
			if (types != null) {
				for (int i = 0; i < this.typeSet.getNodeTypes().size(); i++) {
					EdType t = this.typeSet.getNodeTypes().elementAt(i);
					if (types.isNewType(types.getNodeTypes(), t))
						types.getNodeTypes().addElement(t);
				}
				for (int i = 0; i < this.typeSet.getArcTypes().size(); i++) {
					EdType t = this.typeSet.getArcTypes().elementAt(i);
					if (types.isNewType(types.getArcTypes(), t))
						types.getArcTypes().addElement(t);
				}
			}
		}
		this.typeSet = types;
		// TODO: insert type graph?
	}

	/** Returns my nodes and edges */
	public Vector<EdGraphObject> getGraphObjects() {
		Vector<EdGraphObject> result = new Vector<EdGraphObject>(this.nodes.size()
				+ this.arcs.size());
		result.addAll(this.nodes);
		result.addAll(this.arcs);
		return result;
	}

	/** Returns my nodes */
	public Vector<EdNode> getNodes() {
		return this.nodes;
	}

	/** Returns my arcs */
	public Vector<EdArc> getArcs() {
		return this.arcs;
	}

	public boolean isEmpty() {
		return this.nodes.isEmpty();
	}
	
	/** Allows to edit this graph depending on the value of parameter b. */
	public void setEditable(boolean b) {
		this.editable = b;
	}

	/* Determines whether this graph can be changed or not. */
	public boolean isEditable() {
		return this.editable;
	}

	public void setTransformChangeEnabled(boolean b) {
		this.isTransformChange = b;
		if (this.isTransformChange && (this.newAfterTransformStep != null))
			this.newAfterTransformStep.clear();
	}

	/** Tests whether the specified EdGraph is a graph */
	public boolean isGraph(EdGraph eg) {
		if (eg == null)
			return false;

		for (int i = 0; i < eg.arcs.size(); i++) {
			EdArc ea = eg.arcs.elementAt(i);
			if (!eg.nodes.contains(ea.getSource())
					|| !eg.nodes.contains(ea.getTarget()))
				return false;
		}
		return true;
	}

	/** Returns TRUE if this is a type graph */
	public boolean isTypeGraph() {
		return this.isTG;
	}

	public boolean isInheritanceType(EdType t) {
		return (this.inheritanceType == t);
	}
	
	/** Marks this as a type graph */
	public void markTypeGraph(boolean val) {
		this.isTG = val;
	}

	/**
	 * Returns TRUE if this is a graph of Critical Pair Analysis with conflicts
	 * and dependencies between rules.
	 */
	public boolean isCPAgraph() {
		return this.isCPA;
	}

	/**
	 * Set this graph to be a graph of Critical Pair Analysis with conflicts and
	 * dependencies between rules.
	 */
	public void setCPAgraph(boolean b) {
		this.isCPA = b;
	}

	/** Clears myself */
	public void clear() {
		this.disposeGraphObjects();
		this.basisNode2node.clear();
	}

	/** Clears my selection */
	public void clearSelected() {
		if (this.selectedArcs != null && this.selectedArcs.size() > 0) {
			for (int i = 0; i < this.selectedArcs.size(); i++) {
				EdArc ea = this.selectedArcs.elementAt(i);
				ea.setSelected(false);
			}
			this.selectedArcs.removeAllElements();
		}
		this.selectedArc = null;

		if (this.selectedNodes != null && this.selectedNodes.size() > 0) {
			for (int i = 0; i < this.selectedNodes.size(); i++) {
				EdNode en = this.selectedNodes.elementAt(i);
				en.setSelected(false);
			}
			this.selectedNodes.removeAllElements();
		}
		this.selectedNode = null;
	}

	// add EdNode

	/**
	 * Adds a new node layout to my nodes.
	 */
	public EdNode addNode(int x, int y, boolean visible) throws TypeException {
		
		if (this.typeSet.getSelectedNodeType() == null) {
			this.errMsg = "Any node type isn't selected!";
			return null;
		}

		// start memory test
//		System.gc();
//		long t1 = Runtime.getRuntime().freeMemory();
		
		try {
			EdNode eNode = new EdNode(this.bGraph, this.typeSet.getSelectedNodeType());	
			//		end memory test
	//		long t2 = Runtime.getRuntime().freeMemory();		
	//		System.out.println("EdGraph.addNode used memory: "+ (t1-t2) +"  "+t1+"   "+t2);
			this.addElement(eNode, false);
			eNode.setReps(x, y, visible, false);		
			eNode.setMorphismMark(this.nodes.size()+this.arcs.size());			
			return eNode;
		} catch (TypeException ex) {
			throw ex;
		}
	}

	/**
	 * Adds a new node layout to my nodes.
	 */
	public EdNode addNode(int x, int y, EdType eType, boolean visible)
			throws TypeException {
		
		try {	
			EdNode eNode = new EdNode(this.bGraph, eType);	
			this.addElement(eNode, false);
			eNode.setReps(x, y, visible, false);
			eNode.setMorphismMark(this.nodes.size()+this.arcs.size());			
			return eNode;
		} catch (TypeException ex) {
			throw ex;
		}
	}

	/**
	 * Adds a new node layout of the basis node specified by the basis Node
	 * bNode.
	 */
	private EdNode newNode(Node bNode) {		
		// find EdType
		EdType et = null;
		for (int i = 0; i < this.typeSet.getNodeTypes().size(); i++) {
			EdType t = this.typeSet.getNodeTypes().elementAt(i);
			if (t.getBasisType() == bNode.getType()) {
				et = t;
				break;
			}
		}		
		if (et == null) {
			et = this.typeSet.getNodeType(bNode.getType());		
			if (et == null) 
				et = this.typeSet.createNodeType(bNode.getType());
		}		
		if (et != null) {
			EdNode eNode = new EdNode(bNode, et);
			this.addElement(eNode, false);									
			eNode.setMorphismMark(this.nodes.size());
			return eNode;
		} 
		
		this.errMsg = "Creating node failed!";
		return null;
	}

	/**
	 * Adds a new node layout of the used object specified by the Node bNode
	 * using the type specified by the EdType et.
	 */
	public EdNode addNode(Node bNode, EdType et) {		
		EdNode eNode = new EdNode(bNode, et);		
		this.addElement(eNode, false);
		eNode.setMorphismMark(this.nodes.size()+this.arcs.size());		
		return eNode;
	}

	// add EdArc

	/**
	 * Adds a new arc layout to my arcs.
	 */
	public EdArc addArc(EdGraphObject from, EdGraphObject to, Point anchor,
			boolean directed) throws TypeException {
		
		if (this.typeSet.getSelectedArcType() == null) {
			this.errMsg = "Any edge type isn't selected!";
			throw new TypeException(this.errMsg);
//			return null;
		}

		//		start memory test
		//		System.gc();
		//		long t1 = Runtime.getRuntime().freeMemory();
		
		try {	
			EdArc eArc = new EdArc(this.bGraph, this.typeSet.getSelectedArcType(), from, to,
					anchor);			
	//		end memory test
	//		long t2 = Runtime.getRuntime().freeMemory();		
	//		System.out.println("EdGraph.addArc used memory: "+ (t1-t2) +"  "+t1+"   "+t2);
			this.addElement(eArc, false);			
			eArc.setReps(directed, true, false);
			this.resizeArc(eArc, 15);
			eArc.setMorphismMark(this.nodes.size() + this.arcs.size());			
			return eArc;
		} catch (TypeException ex) {
			throw ex;
		}
	}

	/**
	 * Adds a new arc layout to my arcs. using the type specified by the EdType
	 * eType.
	 */
	public EdArc addArc(EdType eType, EdGraphObject from, EdGraphObject to,
			Point anchor, boolean directed) throws TypeException {
		try {
			EdArc eArc = new EdArc(this.bGraph, eType, from, to, anchor);
			this.addElement(eArc, false);
			eArc.setReps(directed, true, false);
			eArc.setMorphismMark(this.nodes.size() + this.arcs.size());			
			return eArc;
		} catch (TypeException ex) {
			throw ex;
		}
	}

	/**
	 * Adds a new arc layout of the used object specified by the basis Arc bArc.
	 */
	private EdArc newArc(Arc bArc) {
		EdArc eArc = null;
		// find EdType
		EdType et = null;
		for (int i = 0; i < this.typeSet.getArcTypes().size(); i++) {
			EdType t = this.typeSet.getArcTypes().elementAt(i);
			if (t.getBasisType() == bArc.getType()) {
				et = t;
				break;
			}
		}
		if (et == null) {
			et = this.typeSet.getType(bArc.getType());
			if (et == null) 
				et = this.typeSet.createArcType(bArc.getType());
		}
		if (et != null) {
			// find src and tar as an EdGraphObject
			GraphObject bSrc = bArc.getSource();
			EdGraphObject eSrc = findNode(bSrc);
			GraphObject bTar = bArc.getTarget();
			EdGraphObject eTar = findNode(bTar);
			if (eSrc != null && eTar != null) {
				try {
					eArc = new EdArc(bArc, et, eSrc, eTar);
					this.addElement(eArc, false);				
					eArc.setMorphismMark(this.nodes.size() + this.arcs.size());
					return eArc;
				} catch (TypeException ex) {
					this.errMsg = "Creating edge failed! Source or target of this arc failed!";
					return null;
				}
			}
			this.errMsg = "Creating edge failed! Source or target of this arc failed!";
		} 
		else {
			this.errMsg = "Creating edge failed! Edge Type  <"
					+ bArc.getType().getName() + ">  is not found!";
		}

		return null;
	}

	private EdArc newArc(Arc bArc, EdGraphObject eSrc, EdGraphObject eTar) {
		EdArc eArc = null;
		// find EdType
		EdType et = null;
		for (int i = 0; i < this.typeSet.getArcTypes().size(); i++) {
			EdType t = this.typeSet.getArcTypes().elementAt(i);
			if (t.getBasisType() == bArc.getType()) {
				et = t;
				break;
			}
		}
		if (et == null) {
			et = this.typeSet.getArcType(bArc.getType());
			if (et == null) 
				et = this.typeSet.createArcType(bArc.getType());
		}
		if (et != null) {
			try {
				eArc = new EdArc(bArc, et, eSrc, eTar);
				this.addElement(eArc, false);				
				eArc.setMorphismMark(this.nodes.size() + this.arcs.size());
				return eArc;
			} catch (TypeException ex) {
				this.errMsg = "Creating edge failed! Source or target of this arc failed!";
				return null;
			}
		} 
		else {
			this.errMsg = "Creating edge failed! Edge Type  <"
					+ bArc.getType().getName() + ">  is not found!";
		}
		return null;
	}
	
	/**
	 * Adds a new inheritance arc layout of the used object specified by the Arc
	 * bArc to the arcs container specified by the Vector eArcs. The source and
	 * the target of the new arc have to be under my nodes . The Vector
	 * eArcs can be my arcs vector.
	 */
	public EdArc newInheritanceArc(final Arc bArc, final Vector<EdArc> eArcs) {
		if (!this.isTG) {
			this.errMsg = "This graph should be a type graph!";
			return null;
		}

		this.errMsg = "";
		if (this.inheritanceArcs == null)
			inheritanceArcs = new Vector<EdArc>();
		if (this.inheritanceType == null)
			this.inheritanceType = new EdType(bArc.getType(), EditorConstants.SOLID,
					new Color(0, 0, 0), "");

		GraphObject bSrc = bArc.getSource();
		GraphObject bTar = bArc.getTarget();

		EdGraphObject eSrc = findNode(bSrc);
		EdGraphObject eTar = findNode(bTar);
		if (eSrc != null && eTar != null) {
			try {
				EdArc eArc = new EdArc(bArc, this.inheritanceType, eSrc, eTar);
				eArc.setContext(this);
				eArc.setReps(true, true, false);
				eArc.markElementOfTypeGraph(true);
				this.inheritanceArcs.addElement(eArc);
				eArcs.addElement(eArc);
				return eArc;
			} catch(TypeException ex) {				
				return null;
			}
		} 
		return null;
	}
	
	public boolean deleteInheritanceRelation(EdNode child, EdNode parent) {
		if (this.isTG && (this.bGraph != null)
				&& (this.inheritanceArcs != null) && !this.inheritanceArcs.isEmpty()) {
			
			if (this.bGraph.getTypeSet()
					.removeInheritanceRelation(child.getBasisNode().getType(),
							parent.getBasisNode().getType())) {
				EdArc a = findInheritanceArc(child, parent);
				if (a != null) {
					this.inheritanceArcs.removeElement(a);
					this.arcs.removeElement(a);
					a.dispose();
					return true;
				}
			}
		}
		return false;
	}

	public boolean deleteAllInheritanceRelations(EdNode child) {
		if (this.isTG && (this.bGraph != null)
				&& (this.inheritanceArcs != null) && !this.inheritanceArcs.isEmpty()) {
			
			for (int i = 0; i < this.inheritanceArcs.size(); i++) {
				EdArc a = this.inheritanceArcs.elementAt(i);
				if (a.getSource() == child
						&& this.bGraph.getTypeSet().isInheritanceArc(a.getBasisArc())) {
					if (this.bGraph.getTypeSet().removeInheritanceRelation(
							child.getBasisNode().getType(),
							((EdNode) a.getTarget()).getBasisNode().getType())) {
						this.inheritanceArcs.removeElement(a);
						this.arcs.removeElement(a);
						a.dispose();
					}
				}
			}
		}
		return false;
	}

	/**
	 * Adds a new arc layout of the used object specified by the Arc bArc using
	 * the type specified by the EdType et.
	 */
	public EdArc addArc(Arc bArc, EdType et) throws TypeException {
		this.errMsg = "";
		EdArc eArc = null;
		// find src and tar as EdGraphObject
		GraphObject bSrc = bArc.getSource();
		GraphObject bTar = bArc.getTarget();

		EdGraphObject eSrc;
		EdGraphObject eTar;
		eSrc = findNode(bSrc);
		eTar = findNode(bTar);
		
		if (eSrc != null && eTar != null) {
			try {			
				eArc = new EdArc(bArc, et, eSrc, eTar);
				this.addElement(eArc, false);				
				eArc.setReps(bArc.isDirected(), true, false);
				eArc.setMorphismMark(this.nodes.size() + this.arcs.size());				
				return eArc;
			} catch(TypeException ex) {	
				this.errMsg = "Bad arc!";
				throw new TypeException(this.errMsg);
			}
		}
		this.errMsg = "Bad arc!";
		throw new TypeException(this.errMsg);
	}

	// find

	/** Gets the used object of the node layout specified by the EdNode eNode */
	public Node findBasisNode(EdNode eNode) {
		return eNode.getBasisNode();
	}

	/**
	 * Gets the node layout of the used object specified by the GraphObject
	 * bNode
	 */
	public EdNode findNode(GraphObject bNode) {
		if (bNode == null) 
			return null;
		 
		return this.basisNode2node.get(bNode);
	}

	public Vector<EdNode> getNodes(EdType t) {
		Vector<EdNode> v = new Vector<EdNode>();
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode o = this.nodes.elementAt(i);
			if (o.getType().compareTo(t))
				v.add(o);
		}
		return v;
	}

	public Vector<EdArc> getArcs(EdType t) {
		Vector<EdArc> v = new Vector<EdArc>();
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc o = this.arcs.elementAt(i);
			if (o.getType().compareTo(t))
				v.add(o);
		}
		return v;
	}

	private EdArc findInheritanceArc(EdNode src, EdNode tar) {
		if (this.isTG && (this.bGraph != null)
				&& (this.inheritanceArcs != null) && !this.inheritanceArcs.isEmpty()) {
			for (int i = 0; i < this.inheritanceArcs.size(); i++) {
					EdArc a = this.inheritanceArcs.elementAt(i);
					if (a.getSource() == src && a.getTarget() == tar)
						if (this.bGraph.getTypeSet().isInheritanceArc(a.getBasisArc()))
							return a;
			}
		}
		return null;
	}

	/** Gets the used object of the arc layout specified by the EdArc eArc */
	public Arc findBasisArc(EdArc eArc) {
		return eArc.getBasisArc();
	}

	/** Gets the arc layout of the used object specified by the GraphObject bArc */
	public EdArc findArc(GraphObject bArc) {
		if (bArc == null)
			return null;
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc eArc = this.arcs.elementAt(i);
			if (eArc.getBasisArc() == bArc)
				return eArc;
		}
		return null;
	}

	/*
	private EdArc findArc(GraphObject bArc, Vector<EdArc> eArcs) {
		if (bArc == null)
			return null;
		for (int i = 0; i < eArcs.size(); i++) {
			EdArc eArc = eArcs.elementAt(i);
			if (eArc.getBasisArc() != null 
					&& eArc.getBasisArc() == bArc) {
				return eArc;
			}
		}
		return null;
	}
*/
	
	public EdGraphObject findGraphObject(GraphObject obj) {
		if (obj != null) {
			if (obj.isNode()) {
				return findNode(obj);
			} 
			
			return findArc(obj);			
		} 
		return null;
				
	}

	// pick

	/** Gets an object on the position specifies by the int x, int y */
	public EdGraphObject getPicked(int x, int y) {
		this.pickedObj = this.getPickedNode(x, y);
		if (this.pickedObj == null) {
			this.pickedObj = this.getPickedArc(x, y);		
		}
		return this.pickedObj;
	}

	public EdGraphObject getPicked() {
		return this.pickedObj;
	}

	/** Gets the node layout on the position specifies by the int x, int y */
	public EdNode getPickedNode(int x, int y) {
		this.pickedObj = null;
		for (int i=this.nodes.size()-1; i>=0; i--) {
//		for (int i = 0; i<this.nodes.size(); i++) {
			EdNode en = this.nodes.elementAt(i);
			if (en.inside(x, y)) {
				this.pickedObj = en;
				return en;
			}
		}

		return null;
	}

	/** Gets the arc layout on the position specifies by the int x, int y */
	public EdArc getPickedArc(int x, int y) {
		this.pickedObj = null;
		for (int i=this.arcs.size()-1; i>=0; i--) {
//		for (int i = 0; i<this.arcs.size(); i++) {
			EdArc ea = this.arcs.elementAt(i);
			if (ea.inside(x, y)) {
				this.pickedObj = ea;
				return ea;
			}
		}
		return null;
	}

	public void setNodeOfAnimatedTypeToFront() {
		Vector<EdNode> list = new Vector<EdNode>();
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode eNode = this.nodes.get(i);
			if (eNode.getType().isAnimated()) {
				list.add(eNode);
			}			
		}
		for (int i = 0; i < list.size(); i++) {
			EdNode eNode = list.get(i);
			this.nodes.remove(eNode);
			this.nodes.add(eNode);
		}
		list.clear();
	}
	
	public void nodeToFront(EdNode go) {
		if (go != null) {
			this.nodes.remove(go);
			this.nodes.add(go);
		}
	}
	
	/**
	 * Gets the arc layout to the text on the position specifies by the int x,
	 * int y
	 */
	public EdArc getPickedTextOfArc(int x, int y, FontMetrics fm) {
//		for (int i=this.arcs.size()-1; i>=0; i--) {
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc ea = this.arcs.elementAt(i);
			if (ea.insideTextOfArc(x, y, fm)) {
				return ea;
			}
		}
		return null;
	}

	// move

	/**
	 * Moves the specified EdNode to the position computed by
	 * pickedNode.getX()+dx  and  pickedNode.getY()+dy
	 */
	public void moveNode(EdNode pickedNode, int dx, int dy) {		
//		System.out.println("EdGraph.moveNode(EdNode, int dx, int dy) "+dx+"    "+dy);
		if (pickedNode != null) {
			final Vector<EdArc> in = getIncomingArcs(pickedNode);
			final Vector<EdArc> out = getOutgoingArcs(pickedNode);
			pickedNode.x = pickedNode.x + dx;
			pickedNode.y = pickedNode.y + dy;
//			System.out.println(pickedNode.x+" , "+pickedNode.y);
			
			for (int i = 0; i < in.size(); i++) {
				final EdArc ea = in.elementAt(i);
//				if (pickedNode.isSelected() && ea.isSelected())
//					continue;
				// move the edge anchor, too
				if (ea.isLine() && ea.hasAnchor()) {
					 ea.setAnchor(new Point(ea.getAnchor().x+dx/2,
							 ea.getAnchor().y+dy/2));
				}
			}
			for (int i = 0; i < out.size(); i++) {
				final EdArc ea = out.elementAt(i);
//				if (pickedNode.isSelected() && ea.isSelected())
//					continue;
//				 move the edge anchor, too
				if (ea.isLine() && ea.hasAnchor()) {
					 ea.setAnchor(new Point(ea.getAnchor().x+dx/2,
							 ea.getAnchor().y+dy/2));
				}
				else if (!ea.isLine()) {// is Loop
					final Loop loop = ea.toLoop();
					loop.move(((EdNode) ea.getSource()).toRectangle(),
							Loop.CENTER, dx, dy);
					ea.setAnchor(Loop.UPPER_LEFT, new Point(loop.x, loop.y));
					ea.setWidth(loop.w);
					ea.setHeight(loop.h);
				}
			}
		}
	}

	public void moveNodeAndNotSelectedInOutArcs(EdNode pickedNode, int dx, int dy) {
//		 System.out.println("EdGraph.moveNodeAndNotSelectedInOutArcs(EdNode, int dx, int dy) ");
		if (pickedNode != null) {
			final Vector<EdArc> in = getIncomingArcs(pickedNode);
			final Vector<EdArc> out = getOutgoingArcs(pickedNode);
			pickedNode.x = pickedNode.x + dx;
			pickedNode.y = pickedNode.y + dy;

			for (int i = 0; i < in.size(); i++) {
				final EdArc ea = in.elementAt(i);
				if (ea.isSelected())
					continue;
				// move the edge anchor, too
				if (ea.isLine() && ea.hasAnchor()) {
					if (!ea.getSource().isSelected()) {
						ea.setAnchor(new Point(ea.getAnchor().x+dx/2,
								ea.getAnchor().y+dy/2));
					} else  if (!ea.moved) {
						ea.setAnchor(new Point(ea.getAnchor().x+dx,
								ea.getAnchor().y+dy));
						ea.moved = true;
					} else
						ea.moved = false;
				}
			}
			for (int i = 0; i < out.size(); i++) {
				final EdArc ea = out.elementAt(i);
				if (ea.isSelected())
					continue;
//				 move the edge anchor, too
				if (ea.isLine() && ea.hasAnchor()) {
					if (!ea.getTarget().isSelected()) {
						ea.setAnchor(new Point(ea.getAnchor().x+dx/2,
								ea.getAnchor().y+dy/2));
					} else if (!ea.moved) {
						ea.setAnchor(new Point(ea.getAnchor().x+dx,
								ea.getAnchor().y+dy));
						ea.moved = true;
					} else
						ea.moved = false;
				} 
				else if (!ea.isLine()) {// is Loop
					final Loop loop = ea.toLoop();
					loop.move(((EdNode) ea.getSource()).toRectangle(),
							Loop.CENTER, dx, dy);
					ea.setAnchor(Loop.UPPER_LEFT, new Point(loop.x, loop.y));
					ea.setWidth(loop.w);
					ea.setHeight(loop.h);
				}
			}
		}
	}

	/*
	 * Moves the specified EdNode to the position computed by
	 * pickedNode.getX()+dx  and  pickedNode.getY()+dy
	 */
	@SuppressWarnings("unused")
	private void moveNodeOnly(EdNode pickedNode, int dx, int dy) {
		if (pickedNode != null) {
			Vector<EdArc> in = getIncomingArcs(pickedNode);
			pickedNode.x = pickedNode.x + dx;
			pickedNode.y = pickedNode.y + dy;
			for (int i = 0; i < in.size(); i++) {
				EdArc ea = in.elementAt(i);
				// move loop-edge
				if (!ea.isLine()) {
					Loop loop = ea.toLoop();
					loop.move(((EdNode) ea.getSource()).toRectangle(),
							Loop.CENTER, dx, dy);
					ea.setAnchor(Loop.UPPER_LEFT, new Point(loop.x, loop.y));
					ea.setWidth(loop.w);
					ea.setHeight(loop.h);
				}
			}
		}
	}

	/**
	 * Moves the bend of the specified EdArc to the position computed by
	 * pickedArc.getX()+dx  and  pickedArc.getY()+dy
	 */
	public void moveArc(EdArc pickedArc, int dx, int dy) {
		if (pickedArc != null) {
			if (pickedArc.isLine()) {
				pickedArc.setAnchor(new Point(pickedArc.getAnchor().x + dx,
						pickedArc.getAnchor().y + dy));
			} else {
				Loop loop = pickedArc.toLoop();
				// if (pickedArc.getAnchorID() != Loop.CENTER)
				{
					loop.move(((EdNode) pickedArc.getSource()).toRectangle(),
							pickedArc.getAnchorID(), dx, dy);
					pickedArc.setAnchor(Loop.UPPER_LEFT, new Point(loop.x,
							loop.y));
					pickedArc.setWidth(loop.w);
					pickedArc.setHeight(loop.h);
				}
			}
		}
	}

	/**
	 * Moves the text of an arc specified by the EdArc pickedTextOfArc to the
	 * point specified by the int dx, int dy
	 */
	public void moveTextOfArc(EdArc pickedTextOfArc, int dx, int dy) {
		pickedTextOfArc.translateTextOffset(dx, dy);
	}

	public void moveObject(EdGraphObject go, int dx, int dy) {
		if (go instanceof EdNode) {
			moveNode((EdNode) go, dx, dy);
		} else if (go instanceof EdArc) {
			moveArc((EdArc) go, dx, dy);
		}
	}

	public void moveObjects(Vector<EdGraphObject> objects, int dx, int dy) {
		if (objects == null)
			return;
//		System.out.println("EdGraph.moveObjects(Vector<EdGraphObject>, int dx, int dy)");
		Enumeration<EdGraphObject> objs = objects.elements();
		while (objs.hasMoreElements()) {
			EdGraphObject go = objs.nextElement();
			if (go instanceof EdNode) {
				// moveNodeOnly((EdNode)go, dx, dy);
				moveNode((EdNode) go, dx, dy);
			} else if (go instanceof EdArc) {
				EdArc ea = (EdArc) go;
				moveArc(ea, dx, dy);
			}
		}
	}

	// select

	/** Returns selected node */
	public EdNode getSelectedNode() {
		return this.selectedNode;
	}

	/** Sets selected node to the node specified by the EdNode en */
	public void setSelectedNode(EdNode en) {
		if (this.nodes.contains(en)) {
			if (selectedNodes == null)
				selectedNodes = new Vector<EdNode>();
			if (!this.selectedNodes.contains(en)) {
				en.setSelected(true);
				this.selectedNodes.addElement(en);
			}
			this.selectedNode = en;
		}
	}

	/** Returns all selected nodes */
	public Vector<EdNode> getSelectedNodes() {
		return (this.selectedNodes != null) ? this.selectedNodes : new Vector<EdNode>(0);
	}

	/** Selects a node on the position specified by the int x, int y */
	public EdGraphObject selectNode(int x, int y) {
		EdNode selEdNode = null;
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode eNode = this.nodes.elementAt(i);
			if (eNode.inside(x, y)) {
				selEdNode = eNode;
				selEdNode.setSelected(true);
				if (selectedNodes == null)
					selectedNodes = new Vector<EdNode>();
				if (selEdNode.isSelected()) {
					this.selectedNodes.addElement(selEdNode);
					this.selectedNode = selEdNode;
				} else {
					this.selectedNodes.removeElement(selEdNode);
					if (this.selectedNode == selEdNode)
						this.selectedNode = null;
				}
				break;
			}
		}
		return selEdNode;
	}

	/** Returns selected arc */
	public EdArc getSelectedArc() {
		return this.selectedArc;
	}

	/** Sets selected arc to the arc specified by the EdArc ea */
	public void setSelectedArc(EdArc ea) {
		if (this.arcs.contains(ea)) {
			if (selectedArcs == null)
				selectedArcs = new Vector<EdArc>();
			if (!this.selectedArcs.contains(ea)) {
				ea.setSelected(true);
				this.selectedArcs.addElement(ea);
			}
			this.selectedArc = ea;
		}
	}

	/** Returns all selected arcs */
	public Vector<EdArc> getSelectedArcs() {
		return (this.selectedArcs != null) ? this.selectedArcs : new Vector<EdArc>(0);
	}

	/** Selects an arc on the position specified by the int x, int y */
	public EdGraphObject selectArc(int x, int y) {
		EdArc selEdArc = null;
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc eArc = this.arcs.elementAt(i);
			if (eArc.inside(x, y) || eArc.insideTextOfArc(x, y, null)) {
				selEdArc = eArc;
				selEdArc.setSelected(true);
				if (selectedArcs == null)
					selectedArcs = new Vector<EdArc>();
				if (selEdArc.isSelected()) {
					this.selectedArcs.addElement(selEdArc);
					this.selectedArc = selEdArc;
				} else {
					this.selectedArcs.removeElement(selEdArc);
					if (this.selectedArc == selEdArc)
						this.selectedArc = null;
				}
				break;
			}
		}
		return selEdArc;
	}

	/** Selects and returns an object in the position (x,y) */
	public EdGraphObject select(int x, int y) {
		EdGraphObject eObj = null;
		eObj = selectNode(x, y);
		if (eObj == null)
			eObj = selectArc(x, y);
		return eObj;
	}

	/** Selects an object. Returns true, if selected. */
	public void select(EdGraphObject obj) {
		if (obj != null) {
			obj.setSelected(true);
			if (obj.isNode()) {
				if (selectedNodes == null)
					selectedNodes = new Vector<EdNode>();
				this.selectedNodes.addElement((EdNode) obj);
			} else {
				if (selectedArcs == null)
					selectedArcs = new Vector<EdArc>();
				this.selectedArcs.addElement((EdArc) obj);
			}
		}
	}

	/** Selects all objects */
	public void selectAll() {
		synchronized (this) {
			if (selectedNodes == null)
				selectedNodes = new Vector<EdNode>();
			if (selectedArcs == null)
				selectedArcs = new Vector<EdArc>();
			deselectAll();
			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode eNode = this.nodes.elementAt(i);
				if (!eNode.isSelected()) {
					eNode.setSelected(true);
					this.selectedNodes.addElement(eNode);
				}
			}
			for (int j = 0; j < this.arcs.size(); j++) {
				EdArc eArc = this.arcs.elementAt(j);
				if (!eArc.isSelected()) {
					eArc.setSelected(true);
					this.selectedArcs.addElement(eArc);
				}
			}
		}
	}

	/** Selects nodes of selected type */
	public void selectObjectsOfSelectedNodeType() {
		EdType t = this.typeSet.getSelectedNodeType();
		selectObjectsOfType(t);
	}

	/** Selects edges of selected type */
	public void selectObjectsOfSelectedArcType() {
		EdType t = this.typeSet.getSelectedArcType();
		selectObjectsOfType(t);
	}

	/** Selects objects of type t */
	public void selectObjectsOfType(EdType t) {
		// System.out.println(">>> EdGraph.selectObjectsOfType: "+t.getName());
		synchronized (this) {
			this.clearSelected();
			if (selectedNodes == null)
				selectedNodes = new Vector<EdNode>();
			if (selectedArcs == null)
				selectedArcs = new Vector<EdArc>();

			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode eNode = this.nodes.elementAt(i);
				// System.out.println(eNode.getType()+" "+t);
				if (eNode.getType().equals(t)) {
					if (!this.selectedNodes.contains(eNode)) {
						eNode.setSelected(true);
						this.selectedNodes.addElement(eNode);
					}
				}
			}
			for (int j = 0; j < this.arcs.size(); j++) {
				EdArc eArc = this.arcs.elementAt(j);
				if (eArc.getType().equals(t)) {
					if (!this.selectedArcs.contains(eArc)) {
						eArc.setSelected(true);
						this.selectedArcs.addElement(eArc);
					}
				}
			}
		}
	}

	/** Deselects an object specified by the EdGraphObject ego */
	public void deselect(EdGraphObject ego) {
		if (ego != null) {
			ego.setSelected(false);
			if (ego.isNode() && this.selectedNodes != null)
				this.selectedNodes.removeElement(ego);
			else if (ego.isArc()  && this.selectedArcs != null)
				this.selectedArcs.removeElement(ego);
		}
	}

	/** Deselects all objects */
	public void deselectAll() {
		this.clearSelected();
	}

	/** Returns selected objects */
	public Vector<EdGraphObject> getSelectedObjs() {
		int i;
		Vector<EdGraphObject> sel = new Vector<EdGraphObject>();
		if (this.selectedNodes != null) {
			for (i = 0; i < this.selectedNodes.size(); i++) {
				EdNode en = this.selectedNodes.elementAt(i);
				sel.addElement(en);
			}
		}
		if (this.selectedArcs != null) {
			for (i = 0; i < this.selectedArcs.size(); i++) {
				EdArc ea = this.selectedArcs.elementAt(i);
				sel.addElement(ea);
			}
		}
		return sel;
	}

	/** Returns a copy of selected objects as an EdGraph */
	public EdGraph getSelectedAsGraph() {
		if (this.selectedNodes != null && !this.selectedNodes.isEmpty() && selectedArcsOK()) {		
	//		System.out.println("EdGraph.getSelectedAsGraphCopy    isTypeGraph: "+this.isTG);
			TypeSet ts = new TypeSet(this.typeSet.isArcDirected(), this.typeSet.isArcParallel());
			Graph bResult = BaseFactory.theFactory().createGraph(ts);
			java.util.HashMap<EdGraphObject,EdGraphObject> 
			hmap = new java.util.HashMap<EdGraphObject,EdGraphObject>();
			EdGraph result = new EdGraph(bResult, new EdTypeSet(bResult.getTypeSet()));
			Vector<EdNode> singleNodes = new Vector<EdNode>(this.selectedNodes.size());
			singleNodes.addAll(this.selectedNodes);
			if (this.selectedArcs != null) {
				for (int i = 0; i < this.selectedArcs.size(); i++) {
					EdArc ea = this.selectedArcs.elementAt(i);
					EdNode src = (EdNode)hmap.get(ea.getSource());
					if (src == null) {
						src = result.copyNode((EdNode) ea.getSource(), ea.getSource()
								.getX(), ea.getSource().getY());
						if (src != null) {
							hmap.put(ea.getSource(), src);
							// System.out.println(src);
							// showAttrs(src.getBasisNode().getAttribute());
						}
					}
					EdNode trg = src;
					if (!ea.getSource().equals(ea.getTarget())) {
						trg = (EdNode)hmap.get(ea.getTarget());
						if (trg == null) {
							trg = result.copyNode((EdNode) ea.getTarget(), ea
									.getTarget().getX(), ea.getTarget().getY());
							if (trg != null) {
								hmap.put(ea.getTarget(), trg);
								// System.out.println(trg);
								// showAttrs(trg.getBasisNode().getAttribute());
							}
						}
					}
					if (src != null && trg != null) {
		//				System.out.println("isInheritance:  "+ea.getBasisArc().isInheritance());
						EdArc copy = result.copyArc(ea, src, trg);
						// System.out.println(copy);
						// showAttrs(copy.getBasisArc().getAttribute());
						if (copy != null) {
							if (ea.getBasisArc().isInheritance()) {
								copy.setContextUsage("INHERITANCE");
							}
								
							copy.setSelected(true);
							if (!src.isSelected())
								src.setSelected(true);
							if (!trg.isSelected())
								trg.setSelected(true);
							singleNodes.removeElement(ea.getSource());
							singleNodes.removeElement(ea.getTarget());
						}		
					}
				}
			}
			for (int i = 0; i < singleNodes.size(); i++) {
				EdNode en = singleNodes.elementAt(i);
				EdNode copy = result.copyNode(en, en.getX(), en.getY());
				// System.out.println(copy);
				// showAttrs(copy.getBasisNode().getAttribute());
				if (copy != null)
					copy.setSelected(true);
			}
			hmap.clear();
			
			return result;
		}
		else
			return null;
	}

	/*
	 * This method is only useable if this graph is a type graph.
	 */
//	public boolean hasHiddenGraphObjects() {
//		return (this.hidden > 0);
//	}
	
	/**
	 * This method is only useable if this graph is a type graph.
	 */
	public void setVisibilityOfGraphObjectsOfType(EdGraphObject type, boolean vis) {
		if (this.isTG) {
			int oldhidden = this.hidden;
			if (type.isNode()) {
				// set visibility of the node type
//				((EdNode) type).getType().getBasisType().setVisibilityOfObjectsOfTypeGraphNode(vis);
				
				List<EdNode> list = new Vector<EdNode>();
				
				// set visibility of the node type and all its childs 
				for (int i = 0; i < this.nodes.size(); i++) { 
					EdNode n = this.nodes.get(i);
					if (type.getType().getBasisType().isParentOf(n.getType().getBasisType())) {
						n.getType().getBasisType().setVisibilityOfObjectsOfTypeGraphNode(vis);
						this.hidden = vis ? this.hidden-1 : this.hidden+1;
						list.add(n);
					}
				}
				// set visibility of all in- out- arcs 
				for (int j = 0; j < this.arcs.size(); j++) { 
					EdArc a = this.arcs.get(j);	
					if (list.contains(a.getSource())
							|| list.contains(a.getTarget())) {
						a.getType().getBasisType().setVisibityOfObjectsOfTypeGraphArc(
								a.getSource().getType().getBasisType(),
								a.getTarget().getType().getBasisType(),
								vis);
						this.hidden = vis ? this.hidden-1 : this.hidden+1;
					}							
				}
			} else {
				// set visibility of the edge type
				((EdArc) type).getType().getBasisType().setVisibityOfObjectsOfTypeGraphArc(
						((EdArc) type).getSource().getType().getBasisType(),
						((EdArc) type).getTarget().getType().getBasisType(),
						vis);
				this.hidden = vis ? this.hidden-1 : this.hidden+1;
			}
					
			// unset visibilityChecked of all complete graphs
			List<EdGraph> graphs = this.eGra.getGraphs();
			for (int i=0; i<graphs.size(); i++) {				
				graphs.get(i).visibilityChecked = (oldhidden == this.hidden);				
			}			
		}
	}
	
	/**
	 * States how to draw critical objects of CPA critical overlapping graphs:
	 * <code>EdGraphObject.CRITICAL_GREEN</code> or
	 * <code>EdGraphObject.CRITICAL_BLACK_BOLD</code>.
	 */
	public void setDrawingStyleOfCriticalObjects(int criticalStyle) {
		this.criticalStyle = criticalStyle;
	}
	
	
	public void setGraphToCopy(EdGraph g) {
		this.gCopy = g;
	}

	private void addToGraphEmbedding(final EdGraphObject sourceObject) {
		if (this.isSourceGraphOfGraphEmbedding()) {
			final EdRule kernelRule = this.eGra.getRule(this.bGraph);
			if (kernelRule != null) {
				final EdRuleScheme rs = this.eGra.getRuleScheme(kernelRule.getBasisRule());
				if (rs != null) {
					rs.propagateAddGraphObjectToMultiRule(sourceObject);
				}
			}
		}
	}
	
	/** Adds an EdGraph to this. */
	private void addGraph(EdGraph eg, int x, int y) {
		if (isGraph(eg) && (eg.getNodes().size() > 0)) {
			if (this.selectedNodes == null)
				this.selectedNodes = new Vector<EdNode>();
			if (this.selectedArcs == null)
				this.selectedArcs = new Vector<EdArc>();
			
			java.util.HashMap<EdGraphObject,EdGraphObject> 
			hmap = new java.util.HashMap<EdGraphObject,EdGraphObject>();
			Vector<EdNode> singleNodes = new Vector<EdNode>();
			// store all nodes to copy
			singleNodes.addAll(eg.getNodes());
			// do copy of arc by copy source, target nodes first
			for (int i = 0; i < eg.getArcs().size(); i++) {
				EdArc ea = eg.getArcs().elementAt(i);
				EdNode src = null;
				EdNode trg = null;
				
				src = (EdNode)hmap.get(ea.getSource());
				// copy source node
				if (src == null) {
					src = copyNode((EdNode) ea.getSource(), ea.getSource().getX(),
							ea.getSource().getY());
					if (src != null) {
						if (!src.isSelected()) {
							src.setSelected(true);
							this.selectedNodes.addElement(src);
						}
					}
					else if (this.isTG) {
						// find already existend source of the arc to copy in this TypeGraph
						src = getFirstNodeByTypeName(ea.getSource().getTypeName());	
					}
					if (src != null) {
						hmap.put(ea.getSource(), src);
					}
				}	
					
				trg = (EdNode)hmap.get(ea.getTarget());
				// copy target node
				if (trg == null) {
					trg = copyNode((EdNode) ea.getTarget(), ea.getTarget().getX(),
							ea.getTarget().getY());
					if (trg != null) {
						if (!trg.equals(src) && !trg.isSelected()) {
							trg.setSelected(true);
							this.selectedNodes.addElement(trg);
						}
					}
					else if (this.isTG) {
						// find already existend target of the arc to copy in this TypeGraph
						trg = getFirstNodeByTypeName(ea.getTarget().getTypeName());						
					}
					if (trg != null) {
						hmap.put(ea.getTarget(), trg);
					}
				}
				if (src != null && trg != null) {
					if (ea.getContextUsage().equals("INHERITANCE")) {
						// set inheritance relation
						this.getBasisGraph().getTypeSet().addInheritanceRelation(
								src.getBasisNode().getType(), 
								trg.getBasisNode().getType());
					} else {
						// copy arc
						EdArc copy = copyArc(ea, src, trg);
						if (copy != null) {
							if (!this.selectedNodes.contains(src)
									|| !this.selectedNodes.contains(trg)) {
								// do straighten arc copy
								if (copy.isLine()) {
									copy.setAnchor(null);
								}
							}
							copy.setSelected(true);
							this.selectedArcs.addElement(copy);
						}
						else if (this.isTG) {
							copy = getFirstArcByTypeName(ea.getTypeName(), src, trg);
						}
					}
				}
				
				// remove already copied nodes from store
				singleNodes.removeElement(ea.getSource());
				singleNodes.removeElement(ea.getTarget());
			}
			
			for (int i = 0; i < singleNodes.size(); i++) {
				EdNode en = singleNodes.elementAt(i);
				EdNode copy = copyNode(en, en.getX(), en.getY());
				if (copy != null) {
					copy.setSelected(true);
					this.selectedNodes.addElement(copy);
				}
				else if(this.isTG) {
					// find already existend source of the arc to copy in this TypeGraph
					copy = getFirstNodeByTypeName(en.getTypeName());	
				}
			}
	
			// berechne neue Positionen von Graphobjekten
			// finde die Mitte der Knoten
			Point p = findCenter(this.selectedNodes);
			int dx = 0;
			int dy = 0;
			for (int i = 0; i < this.selectedNodes.size(); i++) {
				EdNode en = this.selectedNodes.elementAt(i);
				dx = en.getX() - p.x;
				dy = en.getY() - p.y;
				int newX = x + dx;
				int newY = y + dy;
				en.setX(newX);
				en.setY(newY);
				addToGraphEmbedding(en);
			}
			for (int i = 0; i < this.selectedArcs.size(); i++) {
				EdArc ea = this.selectedArcs.elementAt(i);
				if (ea.isLine() && ea.hasAnchor()) {
					dx = ea.getAnchor().x - p.x;
					dy = ea.getAnchor().y - p.y;
					int newX = x + dx;
					int newY = y + dy;
					ea.setAnchor(new Point(newX, newY));
				}
				addToGraphEmbedding(ea);
			}
			
			// if this is TypeGraph
			refreshInheritanceArcs();
			
			this.typeSet.fireTypeChangedEvent();
		}
	}
	
	private EdNode getFirstNodeByTypeName(final String tname) {
		final EdType t = this.getTypeSet().getNodeTypeForName(tname);
		if (t != null) {
			final List<EdNode> tnodes = this.getNodes(t);
			if (!tnodes.isEmpty()) {
				return tnodes.get(0);
			}
		}
		return null;
	}
	
	private EdArc getFirstArcByTypeName(final String tname, final EdNode src, final EdNode tgt) {
		final EdType t = this.getTypeSet().getArcTypeForName(tname);
		if (t != null) {
			final Arc baseArc = this.getTypeSet().getBasisTypeSet().getTypeGraphArc(
					t.getBasisType(), 
					src.getBasisNode().getType(), 
					tgt.getBasisNode().getType());
			if (baseArc != null) {
				return this.findArc(baseArc);				
			}
		}
		return null;
	}
	
	public boolean hasChanged() {
		return this.changed;
	}

	public boolean adjustTypeObjectsMap() {
		if (this.typeSet.hasTypeKeyChanged()) {
			this.bGraph.updateTypeObjectsMap();
			this.typeSet.unsetTypeKeyChanged();
			return true;
		} 
		return false;
	}
	
	public boolean isNodeNumberChanged() {
		return this.nodeNumberChanged;
	}

	public boolean isNodeRemoved() {
		return this.nodeRemoved;
	}

	public void unsetNodeNumberChanged() {
		this.nodeNumberChanged = false;
		this.nodeRemoved = false;
	}

	/** Tests myself whether only one object is selected */
	public boolean hasOneSelection() {
		int s = 0;
		if (this.selectedNodes != null)
			s = this.selectedNodes.size();
		if (s == 0 && this.selectedArcs != null)
			s = this.selectedArcs.size();
			
		return (s == 1) ? true : false;
	}

	/** Returns TRUE if nothing is selected */
	private boolean nothingSelected() {
		if (this.selectedNodes != null &&  this.selectedNodes.size() > 0)
			return false;
		
		if (this.selectedArcs != null &&  this.selectedArcs.size() > 0)
			return false;
		
		return true;
	}

	/** Returns TRUE if something is selected */
	public boolean hasSelection() {
		return !nothingSelected();
	}

	public boolean hasAllSelected() {
		return (this.selectedNodes != null && this.selectedNodes.size() == this.nodes.size())
				&& (this.selectedArcs != null && this.selectedArcs.size() == this.arcs.size());
	}

	private boolean selectedArcsOK() {
		if (this.selectedArcs != null) {			
			for (int i = 0; i < this.selectedArcs.size(); i++) {
				EdArc selObj = this.selectedArcs.elementAt(i);
				if ((selectedNodes == null)
						|| !this.selectedNodes.contains(selObj.getSource())
						|| !this.selectedNodes.contains(selObj.getTarget()))
					return false;
			}
		}
		return true;
	}
	
	public boolean unsetCriticalGraphObjects() {
		boolean res = false;
		for (int i = 0; i < this.nodes.size(); i++) {
			if (this.nodes.get(i).getBasisNode().isCritical()) {
				this.nodes.get(i).getBasisNode().setCritical(false);
				res = true;
			}
		}
		for (int i = 0; i < this.arcs.size(); i++) { 
			if (this.arcs.get(i).getBasisArc().isCritical()) {
				this.arcs.get(i).getBasisArc().setCritical(false);
				res = true;
			}
		}
		return res;
	}
	
	// copy

	public void setContextUsageOfGraphObjToBasisHashCode() {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode go = this.nodes.get(i);
			go.getBasisNode().setContextUsage(go.getBasisNode().hashCode());
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc go = this.arcs.get(i);
			go.getBasisArc().setContextUsage(go.getBasisArc().hashCode());
		}
	}

	public void unsetContextUsageOfGraphObj() {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode go = this.nodes.get(i);
			go.getBasisNode().setContextUsage(-1);
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc go = this.arcs.get(i);
			go.getBasisArc().setContextUsage(-1);
		}
	}
	
	/** Creates a graph copy of itself. */
	public EdGraph copy() {		
		this.setContextUsageOfGraphObjToBasisHashCode();

		int tglevel = this.bGraph.getTypeSet()
				.getLevelOfTypeGraphCheck();
		if (tglevel == TypeSet.ENABLED_MAX_MIN)
			this.bGraph.getTypeSet().setLevelOfTypeGraphCheck(
					TypeSet.ENABLED_MAX);
		
		EdGraph clone = new EdGraph(BaseFactory.theFactory().createGraph(
				this.bGraph.getTypeSet(), this.bGraph.isCompleteGraph()), this.typeSet);
		
		clone.getBasisGraph().setName(this.bGraph.getName());
		if ((this.bGraph.getAttrContext() != null)
				&& ((ContextView) this.bGraph.getAttrContext())
						.getAllowedMapping() == AttrMapping.GRAPH_MAP) {
			agg.attribute.AttrContext aGraphContext = agg.attribute.impl.AttrTupleManager
					.getDefaultManager().newContext(
							agg.attribute.AttrMapping.GRAPH_MAP);
			clone.getBasisGraph().setAttrContext(
					agg.attribute.impl.AttrTupleManager.getDefaultManager()
							.newRightContext(aGraphContext));
		}

		Hashtable<EdNode, EdNode> table = new Hashtable<EdNode, EdNode>();
		// copy nodes
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode node = this.nodes.elementAt(i);
			try {
				Node n = clone.getBasisGraph().copyNode(node.getBasisNode());
				if (n != null) {
					EdNode cnode = clone.addNode(n, node.getType());
					cnode.setXY(node.getX(), node.getY());
					cnode.getLNode().setFrozenByDefault(
							node.getLNode().isFrozen());
					table.put(node, cnode);
				}
			} catch (TypeException e) {
				System.out.println("EdGraph.copy::  "+e.getLocalizedMessage());
//				e.printStackTrace();
			}
		}
		// copy arcs
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc arc = this.arcs.elementAt(i);
			EdNode src = (EdNode) arc.getSource();
			EdNode trg = (EdNode) arc.getTarget();
			EdNode csrc = table.get(src);
			EdNode ctrg = table.get(trg);
			if (csrc != null && ctrg != null) {
				try {
					Arc a = clone.getBasisGraph().copyArc(arc.getBasisArc(),
							csrc.getBasisNode(), ctrg.getBasisNode());
					if (a != null) {
						EdArc carc = clone.addArc(a, arc.getType());
						if (carc != null) {
							if (arc.isLine()) {
								if (arc.hasAnchor()) {
									carc.setAnchor(new Point(arc.getAnchor()));
								}
							} else {
								if (arc.hasAnchor()) {
									carc.setAnchor(Loop.UPPER_LEFT, new Point(arc
											.getAnchor()));
									carc.setWidth(arc.getWidthOfLoop());
									carc.setHeight(arc.getHeightOfLoop());
								}
							}
							carc.setTextOffset(arc.getTextOffset().x, arc
									.getTextOffset().y);
							carc.getLArc().setFrozenByDefault(
									arc.getLArc().isFrozenByDefault());
						}
					}
				} catch (TypeException e) {
					System.out.println("EdGraph.copy::  "+e.getLocalizedMessage());
	//				e.printStackTrace();
				}
			}
		}
	
		if (tglevel == TypeSet.ENABLED_MAX_MIN)
			this.getBasisGraph().getTypeSet().setLevelOfTypeGraphCheck(
					TypeSet.ENABLED_MAX_MIN);
		table.clear();
		table = null;
		return clone;
	}
	
	/**
	 * Disposes my nodes and edges. 
	 * The basis graph remains in keeping.
	 */
	private void disposeGraphObjects() {
		if (this.selectedNodes != null)
			this.selectedNodes.clear();
		if (this.selectedArcs != null)
			this.selectedArcs.clear();
		this.selectedNode = null;
		this.selectedArc = null;
		this.pickedObj = null;

		if (this.visibleArcs != null)
			this.visibleArcs.clear();
		if (this.visibleNodes != null)
			this.visibleNodes.clear();
		if (this.inheritanceArcs != null)
			this.inheritanceArcs.clear();
		if (changedObjects != null)
			this.changedObjects.clear();
		
		for (int i = 0; i < this.arcs.size(); i++) {
			this.arcs.get(i).dispose();
		}
		for (int i = 0; i < this.nodes.size(); i++) {
			this.nodes.get(i).dispose();
		}

		this.arcs.clear();
		this.nodes.clear();
		this.basisNode2node.clear();

		this.gCopy = null;
	}

	/**
	 * Returns true if this graph is the LHS (resp. RHS) of a KernelRule of 
	 * a RuleScheme and therefore is the source graph of
	 * the left (resp. right) graph embedding into a MultiRule of a RuleScheme.
	 * Otherwise returns false.
	 */
	public boolean isSourceGraphOfGraphEmbedding() {
		if (this.eGra != null) {
			final EdRule rule = this.eGra.getRule(this.bGraph);
			if (rule != null
					&& rule.getBasisRule() instanceof KernelRule) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if this graph is the LHS of a KernelRule of 
	 * a RuleScheme and therefore is the source graph of
	 * the left graph embedding into a MultiRule of a RuleScheme.
	 * Otherwise returns false.
	 */
	public boolean isSourceGraphOfGraphEmbeddingLeft() {
		if (this.eGra != null) {
			final EdRule rule = this.eGra.getRule(this.bGraph);
			if (rule != null
					&& rule.getBasisRule() instanceof KernelRule
					&& rule.getLeft() == this) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if this graph is the RHS of a KernelRule of 
	 * a RuleScheme and therefore is the source graph of
	 * the right graph embedding into a MultiRule of a RuleScheme.
	 * Otherwise returns false.
	 */
	public boolean isSourceGraphOfGraphEmbeddingRight() {
		if (this.eGra != null) {
			final EdRule rule = this.eGra.getRule(this.bGraph);
			if (rule != null
					&& rule.getBasisRule() instanceof KernelRule
					&& rule.getRight() == this) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * Returns true if this graph is the LHS (resp. RHS) of a MultiRule of 
	 * a RuleScheme and the specified graph object is the target object of
	 * the left (resp. right) graph embedding of the KernelRule of a RuleScheme.
	 * Otherwise returns false.
	 * 
	 * @param go a graph object
	 * @return true if go is a target object of a graph embedding, otherwise - false
	 */
	public boolean isTargetObjOfGraphEmbedding(final EdGraphObject go) {
		if (this.eGra != null) {
			final EdRule rule = this.eGra.getRule(this.bGraph);
			if (rule != null
					&& rule.getBasisRule() instanceof MultiRule) {
				if (((MultiRule)rule.getBasisRule()).isTargetOfEmbeddingLeft(go.getBasisObject())
						|| ((MultiRule)rule.getBasisRule()).isTargetOfEmbeddingRight(go.getBasisObject())) {
					return true;
				}
			}
		}
		return false;
	}
	
	/**
	 * Returns the object of the LHS (resp. RHS) of the KernelRule of 
	 * a RuleScheme if the specified graph object is the target object of
	 * the left (resp. right) graph embedding of the KernelRule of a RuleScheme.
	 * Otherwise returns false.
	 * THe specified object belongs to a MultiRule of a RuleScheme.
	 * 
	 * @param go a graph object
	 * @return a MultiRule graph object or null
	 */
	public EdGraphObject getSourceObjOfGraphEmbedding(final EdGraphObject tarObj) {
		final EdRule rule = this.eGra.getRule(this.bGraph);
		if (rule != null
				&& rule.getBasisRule() instanceof MultiRule) {
			if (((MultiRule)rule.getBasisRule()).isTargetOfEmbeddingLeft(tarObj.getBasisObject())) {
				GraphObject kern = ((MultiRule)rule.getBasisRule())
					.getEmbeddingLeft().getInverseImage(tarObj.getBasisObject()).nextElement();
				EdRuleScheme rs = this.eGra.getRuleScheme(this.bGraph);
				if (rs != null) {
					return rs.getKernelRule().getLeft().findGraphObject(kern);
				}
			}
			else if (((MultiRule)rule.getBasisRule()).isTargetOfEmbeddingRight(tarObj.getBasisObject())) {
				GraphObject kern = ((MultiRule)rule.getBasisRule())
					.getEmbeddingRight().getInverseImage(tarObj.getBasisObject()).nextElement();
				EdRuleScheme rs = this.eGra.getRuleScheme(this.bGraph);
				if (rs != null) {
					return rs.getKernelRule().getRight().findGraphObject(kern);
				}
			}
		}
		return null;
	}
	
	protected boolean deleteArc(
			final EdArc go,
			boolean addToUndo) {

		boolean done = true;
		try {
			if (addToUndo) {
				this.addDeletedToUndo(go);
				this.deleteObj(go, false);
				this.undoManagerEndEdit();
			} else
				this.deleteObj(go, false);
		} catch (TypeException e) {
			done = false;
			if (addToUndo)
				this.undoManagerLastEditDie();				
		}
		return done;
	}
	
	protected boolean deleteNode(
			final EdNode go,
			boolean addToUndo) {

		boolean done = true;		
		if (go.getBasisNode().getNumberOfArcs() != 0) {
			Iterator<Arc> edges = go.getBasisNode().getOutgoingArcsSet().iterator();
			while(edges.hasNext() && done) {
				EdArc arc = this.findArc(edges.next());
				if (arc != null) {
					if (this.typeSet.getBasisTypeSet().checkIfRemovable(arc.getBasisArc(), true, false) == null
							&& this.deleteArc(arc, addToUndo)) {
						edges = go.getBasisNode().getOutgoingArcsSet().iterator();
					} else {
						done = false;
					}
				}
			}
			edges = go.getBasisNode().getIncomingArcsSet().iterator();
			while(edges.hasNext() && done) {
				EdArc arc = this.findArc(edges.next());
				if (arc != null) {
					if (this.typeSet.getBasisTypeSet().checkIfRemovable(arc.getBasisArc(), false, true) == null
							&& this.deleteArc(arc, addToUndo)) {
						edges = go.getBasisNode().getIncomingArcsSet().iterator();
					}
					else {
						done = false;
					}
				}
			}
		}
		if (done) {
			try {
				if (addToUndo) {
					this.addDeletedToUndo(go);
					this.deleteObj(go, false);
					this.undoManagerEndEdit();
				} else
					this.deleteObj(go, false);
			} catch (TypeException e) {
				done = false;
				if (addToUndo)
					this.undoManagerLastEditDie();				
			}
		}
		return done;
	}
	
	protected List<EdGraphObject> getGraphObjectsOfType(final EdGraphObject tgo) {		
		List<EdGraphObject> list = new Vector<EdGraphObject>();
		if (tgo.isArc()) {
			for (int i=0; i<this.arcs.size(); i++) {
				EdArc go = this.arcs.get(i);
				if (tgo.getType() == go.getType()
						&& ((EdArc)tgo).getSource().getType().isParentOf(go.getSource().getType())
						&& ((EdArc)tgo).getTarget().getType().isParentOf(go.getTarget().getType())) {
					list.add(go);
				}
			}
		} else {
			for (int i=0; i<this.nodes.size(); i++) {
				EdNode go = this.nodes.get(i);
				if (tgo.getType() == go.getType()) {
					list.add(go);
				}
			}
		}
		return list;
	}
	
	protected List<EdGraphObject> getGraphObjectsOfType(final EdType t) {		
		List<EdGraphObject> list = new Vector<EdGraphObject>();
		if (t.isArcType()) {
			for (int i=0; i<this.arcs.size(); i++) {
				EdArc go = this.arcs.get(i);
				if (t == go.getType()) {
					list.add(go);
				}
			}
		} else {
			for (int i=0; i<this.nodes.size(); i++) {
				EdNode go = this.nodes.get(i);
				if (t == go.getType()) {
					list.add(go);
				}
			}
		}
		return list;
	}
	
	public boolean deleteGraphObjectsOfTypeFromGraph(
			final EdGraphObject tgo,
			boolean addToUndo) {
		
		boolean alldone = true;
		if (tgo.isArc()) {
			for (int i=0; i<this.arcs.size(); i++) {
				EdArc go = this.arcs.get(i);
				if (tgo.getType() == go.getType()
						&& ((EdArc)tgo).getSource().getType().isParentOf(go.getSource().getType())
						&& ((EdArc)tgo).getTarget().getType().isParentOf(go.getTarget().getType())) {
					if (this.deleteArc(go, addToUndo)) { 
						i--;				
					} else {
						alldone = false;
					}
				}
			}
		} else {
			for (int i=0; i<this.nodes.size(); i++) {
				EdNode go = this.nodes.get(i);
				if (tgo.getType() == go.getType()) {//(type.isParentOf(go.getType())) {
					if (this.deleteNode(go, addToUndo)) { 
						i--;				
					} else {
						alldone = false;
					}
				}
			}
		}
		return alldone;
	}
	
	public boolean deleteGraphObjectsOfTypeFromGraph(
			final EdType t,
			boolean addToUndo) {
		
		boolean alldone = true;
		if (t.isArcType()) {
			for (int i=0; i<this.arcs.size(); i++) {
				EdArc go = this.arcs.get(i);
				if (t == go.getType()) {
					if (this.deleteArc(go, addToUndo)) {
						i--;				
					} else {
						alldone = false;
					}
				}
			}
		} else {
			for (int i=0; i<this.nodes.size(); i++) {
				EdNode go = this.nodes.get(i);
				if (t == go.getType()) {//(type.isParentOf(go.getType())) {
					if (this.deleteNode(go, addToUndo)) { 
						i--;				
					} else {
						alldone = false;
					}
				}
			}
		}
		return alldone;
	}
	
	/**
	 * Deletes an arc layout for the used object specified by the Arc bArc. The
	 * used object will be deleted too.
	 */
	public void delArc(Arc bArc) throws TypeException {
		EdArc eArc = findArc(bArc);
		if (eArc != null)
			delSelectedArc(eArc);
	}

	/**
	 * Deletes a node layout for the used object specified by the Node bNode.
	 * The used object will be deleted too.
	 */
	public void delNode(Node bNode) throws TypeException {
		Vector<EdArc> inArcs = new Vector<EdArc>();
		Vector<EdArc> outArcs = new Vector<EdArc>();
		EdArc eArc = null;

		EdNode eNode = findNode(bNode);
		if (eNode != null) {
			for (int j = 0; j < this.arcs.size(); j++) {
				eArc = this.arcs.elementAt(j);
				if (eNode.equals(eArc.getSource()))
					outArcs.addElement(eArc);
				else if (eNode.equals(eArc.getTarget()) && eArc.isLine())
					inArcs.addElement(eArc);
			}
			if (!inArcs.isEmpty()) {
				for (int j = 0; j < inArcs.size(); j++) {
					eArc = inArcs.elementAt(j);
					
					removeElement(eArc);
				}
				inArcs.removeAllElements();
			}

			if (!outArcs.isEmpty()) {
				for (int j = 0; j < outArcs.size(); j++) {
					eArc = outArcs.elementAt(j);
					
					removeElement(eArc);
				}
				outArcs.removeAllElements();
			}

			delSelectedNode(eNode);
		}
	}

	/**
	 * Deletes a node layout specified by the EdNode eNode. The used object will
	 * be deleted too.
	 */
	public void delSelectedNode(EdNode eNode) throws TypeException {
		delSelectedNode(eNode, false);
	}

	public void delSelectedNode(EdNode eNode, boolean forceDelete)
			throws TypeException {
		boolean canDelete = true;
		EdType nType = eNode.getType(); 
		if (this.bGraph != null) {
			canDelete = false;
			try {
				eNode.removeFromAttributeViewObserver();
				this.bGraph.destroyNode(eNode.getBasisNode(), true, forceDelete);
				canDelete = true;
			} catch (TypeException e) {
				throw new TypeException(e.getTypeError());
			}
		}
		if (canDelete) {
			// check arcs of deleted nodes
			for (int i = 0; i < this.arcs.size(); i++) {
				EdArc a = this.arcs.get(i);
				if (!a.getBasisArc().isInheritance()
						&& a.getBasisArc().getContext() == null) {
					a.removeFromAttributeViewObserver();
					removeBadArc(a, nType);
					i--;
				}
			}

			removeElement(eNode);
		}
	}

	/**
	 * Deletes an arc layout specified by the EdArc eArc. The used object will
	 * be deleted too.
	 */
	public void delSelectedArc(EdArc eArc) throws TypeException {
		delSelectedArc(eArc, false);
	}

	public void delSelectedArc(EdArc eArc, boolean forceDelete)
			throws TypeException {
		boolean canDelete = true;
		if (this.bGraph != null) {
			canDelete = false;
			try {
				eArc.removeFromAttributeViewObserver();				
				this.bGraph.destroyArc(eArc.getBasisArc(), true, forceDelete);
				canDelete = true;			
			} catch (TypeException e) {
				throw new TypeException(e.getTypeError());
			}
		}
		if (canDelete)
			removeElement(eArc);
	}

	/** Deletes all selected nodes */
	public void deleteSelectedNodes() throws TypeException {
		if (this.selectedNodes != null)
			while (!this.selectedNodes.isEmpty()) {
				EdNode eNode = this.selectedNodes.get(0);
				delSelectedNode(eNode, false);
			}
	}

	/** Deletes all selected arcs */
	public void deleteSelectedArcs() throws TypeException {
		if (this.selectedArcs != null)
			while (!this.selectedArcs.isEmpty()) {
				EdArc eArc = this.selectedArcs.get(0);
				delSelectedArc(eArc, false);
			}
	}

	/** Deletes all selected objects (nodes and arcs) */
	public void deleteSelected() throws TypeException {
		int currentTypeGraphLevel = this.typeSet.getBasisTypeSet().getLevelOfTypeGraphCheck();
		if (this.selectedNodes != null && this.selectedNodes.size() == this.nodes.size()
				&& currentTypeGraphLevel > TypeSet.ENABLED)
			this.typeSet.getBasisTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED);
			
		deleteSelectedArcs();
			
		deleteSelectedNodes();
			
//		update();
			
		if (currentTypeGraphLevel > TypeSet.ENABLED)
			this.typeSet.getBasisTypeSet().setLevelOfTypeGraphCheck(currentTypeGraphLevel);
	}

	/** Deletes all objects (nodes and arcs) */
	public void deleteAll() throws TypeException {		
		selectAll();
		
		int currentTypeGraphLevel = this.typeSet.getBasisTypeSet().getLevelOfTypeGraphCheck();
		if (currentTypeGraphLevel > TypeSet.ENABLED)
			this.typeSet.getBasisTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED);
		
		deleteSelected();
		
		if (currentTypeGraphLevel > TypeSet.ENABLED)
			this.typeSet.getBasisTypeSet().setLevelOfTypeGraphCheck(currentTypeGraphLevel);
	}

	/** Deletes an object on the position specified by the int x, int y */
	public boolean deleteObj(int x, int y) throws TypeException {
		EdGraphObject obj = getPicked(x, y);
		if (obj != null) {
			if (obj.isNode()) {
				EdNode en = obj.getNode();
				delSelectedNode(en);
			} else {
				EdArc ea = obj.getArc();
				delSelectedArc(ea);
			}
//			update();
			return true;
		} 
		return false;
	}

	/** Deletes an layout object specified by the EdGraphObject ego 
	public void deleteObj(EdGraphObject ego) throws TypeException {
		if (ego == null)
			return;
		if (ego.isNode()) {
			delSelectedNode((EdNode) ego, false);
		} else {
			EdArc ea = ego.getArc();
			delSelectedArc(ea, false);
		}
//		update();
	}
	*/

	/** Deletes an layout object specified by the EdGraphObject ego */
	public void deleteObj(EdGraphObject ego, boolean forceDelete) throws TypeException {
		if (ego == null)
			return;
		if (ego.isNode()) {
			delSelectedNode((EdNode) ego, forceDelete);
		} else {
			EdArc ea = ego.getArc();
			delSelectedArc(ea, forceDelete);
		}
//		update();
	}

	public void forceDeleteObj(EdGraphObject ego) throws TypeException {
		if (ego == null)
			return;
		if (ego.isNode()) {
			delSelectedNode((EdNode)ego, true);
		} else  {
			delSelectedArc((EdArc)ego, true);
		}
//		update();
	}

	public void deleteObjects(EdGraphObject typeGraphObject)
			throws TypeException {
		if (typeGraphObject == null)
			return;
		// System.out.println("EdGraph.deleteObjects of type
		// :"+typeGraphObject.getType().getName()+" "+typeGraphObject+" "+this+"
		// "+bGraph.isCompleteGraph());

		if (typeGraphObject.isNode()) {
			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode en = this.nodes.elementAt(i);
				if (en.getType().equals(typeGraphObject.getType())) {
					// System.out.println("EdGraph.deleteObjects : node of type
					// :"+typeGraphObject.getType().getName());
					delSelectedNode(en);
					i--;
				}
			}
		} else {
			for (int i = 0; i < this.arcs.size(); i++) {
				EdArc ea = this.arcs.elementAt(i);
				if (ea.getType().equals(((EdArc) typeGraphObject).getType())
						&& ea.getSource().getType()
								.equals(
										((EdArc) typeGraphObject).getSource()
												.getType())
						&& ea.getTarget().getType()
								.equals(
										((EdArc) typeGraphObject).getTarget()
												.getType())) {
					// System.out.println("EdGraph.deleteObjects : arc of type
					// :"+typeGraphObject.getType().getName());
					delSelectedArc(ea);
					i--;
				}
			}
		}
		
//		update();
	}

	public void deleteObjects(EdType t) throws TypeException {
		synchronized (this) {
			if (t == null)
				return;
			// System.out.println("EdGraph.deleteObjects of type :"+t.getName()+"
			// "+t+" "+this+" "+bGraph.isCompleteGraph());
			boolean nodeType = false;
			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode en = this.nodes.elementAt(i);
				if (t.isParentOf(en.getType())) {
					// System.out.println("EdGraph.deleteObjects : node of type
					// :"+t.getName());
					nodeType = true;
					delSelectedNode(en);
					i--;
				} else
					break;
			}
			if (!nodeType) {
				for (int i = 0; i < this.arcs.size(); i++) {
					EdArc ea = this.arcs.elementAt(i);
					if (t.isParentOf(ea.getType())) {
						// System.out.println("EdGraph.deleteObjects : arc of type
						// :"+t.getName());
						delSelectedArc(ea);
						i--;
					} else
						break;
				}
			}
		}
	}

	// straight

	public boolean isStraightenArcsEnabled() {
		return this.straightenArcs;
	}
	
	public void setStraightenArcs(boolean b) {
		if (!this.straightenArcs && b) {
			straightAllArcs();
		}
		this.straightenArcs = b;
	}
	
	/** Straights all arcs */
	public void straightAllArcs() {
		synchronized (this) {
			for (int i = 0; i < this.arcs.size(); i++) {
				EdArc ea = this.arcs.elementAt(i);
				if (ea.isLine())
					ea.setAnchor(null);
			}
			return;
		}
	}

	/** Straights all selected arcs */
	public boolean straightSelectedArcs() {
		synchronized (this) {
			boolean moved = false;
			if (this.selectedArcs != null) {
				for (int i = 0; i < this.selectedArcs.size(); i++) {
					EdArc ea = this.selectedArcs.elementAt(i);
					if (ea.isLine() && ea.hasAnchor()) {
						ea.setAnchor(null);
						moved = true;
					}
				}
				if (moved) {
					this.straightenArcs = false;
				}
			}
			return moved;
		}
	}

	/** Straights an arc specified by the EdGraphObject ego */
	public boolean straightArc(EdGraphObject ego) {
		if (ego == null)
			return false;
		if (ego.isArc()) {
			EdArc ea = ego.getArc();
			if (ea.isLine() && ea.hasAnchor()) {
				ea.setAnchor(null);
				return true;
			}
		}
		return false;
	}

	// copy

	/**
	 * Copies selected objects to the position specified by x, y
	 */
	public void copySelected(int x, int y) {
		// System.out.println("EdGraph.copySelected... gCopy: "+gCopy );
		if (nothingSelected()) {
			if (this.gCopy != null) {
				// copy another graph into this graph
				addGraph(this.gCopy, x, y);
			} else {
				this.errMsg = "bad selection";
			}
			this.gCopy = null;
			return;
		}

		// Kopieren wenn Selektion OK ist.
		if (!selectedArcsOK()) {
			this.errMsg = "bad selection";
			return;
		}

		// copy selected objects
		int i;
		Vector<EdGraphObject> sel = new Vector<EdGraphObject>();
		Vector<EdGraphObject> selCopy = new Vector<EdGraphObject>();

		// Einen Vector mit selektierten Knoten und Kanten bilden.
		if (this.selectedNodes != null) {
			for (i = 0; i < this.selectedNodes.size(); i++) {
				EdNode en = this.selectedNodes.elementAt(i);
				sel.addElement(en);
			}
		}
		if (this.selectedArcs != null) {
			for (i = 0; i < this.selectedArcs.size(); i++) {
				EdArc ea = this.selectedArcs.elementAt(i);
				sel.addElement(ea);
			}
		}
		if (sel.isEmpty()) {
			this.errMsg = "bad selection";
			return;
		}

		Point p = findCenter(this.selectedNodes);
		int dx = x - p.x;
		int dy = y - p.y;
		for (i = 0; i < sel.size(); i++) {
			EdGraphObject go = sel.elementAt(i);
			// System.out.println("make copy of : "+go);
			EdGraphObject goCopy = copyGraphObject(go, go.getX()+dx, go.getY()+dy);
			if (goCopy != null) {
				addToGraphEmbedding(goCopy);
				selCopy.addElement(goCopy);
			}
		}

		// Alte Selektion loeschen.
		for (i = 0; i < this.selectedNodes.size(); i++) {
			this.selectedNodes.elementAt(i).setSelected(false);
			this.selectedNodes.elementAt(i).setCopy(null);
		}
		this.selectedNodes.removeAllElements();

		for (i = 0; i < this.selectedArcs.size(); i++) {
			this.selectedArcs.elementAt(i).setSelected(false);
			this.selectedArcs.elementAt(i).setCopy(null);
		}
		this.selectedArcs.removeAllElements();
		

		// Neue Selection setzen.
		for (i = 0; i < selCopy.size(); i++) {
			EdGraphObject eo = selCopy.elementAt(i);
			eo.setSelected(true);
			if (eo.isNode())
				this.selectedNodes.addElement(eo.getNode());
			else
				this.selectedArcs.addElement(eo.getArc());
		}
	}

	private EdGraphObject copyGraphObject(EdGraphObject srcObj, int x, int y) {
		int dx, dy;
		EdGraphObject goCopy = null;
		EdGraphObject src = null;
		EdGraphObject tar = null;

		if (srcObj.isNode() && srcObj.getCopy() == null) {
			// Knoten kopieren.
			goCopy = copyNode(srcObj.getNode(), x, y);
			srcObj.setCopy(goCopy);
		} else if (srcObj.isArc() && srcObj.getCopy() == null) {
			// Source fuer Kante kopieren oder setzen.
			if (srcObj.getArc().getSource().getCopy() == null) {
				src = copyNode((EdNode) srcObj.getArc().getSource(), x, y);
			} else
				src = srcObj.getArc().getSource().getCopy();

			// Target fuer Kante kopieren oder setzen.
			if (srcObj.getArc().getTarget().getCopy() == null) {
				tar = copyNode((EdNode) srcObj.getArc().getTarget(), x, y);
			} else
				tar = srcObj.getArc().getTarget().getCopy();

			// Kante selbst kopieren.
			if (src != null && tar != null) {
				dx = tar.getX() - srcObj.getArc().getTarget().getX();
				dy = tar.getY() - srcObj.getArc().getTarget().getY();
				goCopy = copyArc(srcObj.getArc(), src, tar, dx, dy);
				srcObj.setCopy(goCopy);
			}
		}
		return goCopy;
	}
/*
	private EdNode copyNode(EdNode en, int x, int y, Point p) {
		EdNode cn = null;
		if (bGraph != null) {
			Node bn = null;
			try {
				bn = bGraph.copyNode(en.getBasisNode());
				cn = new EdNode(bn, en.getType());
				cn.setContext(this);
				addElement(cn, false);
				cn.setMorphismMark(nodes.size());
				if (isTG)
					cn.markElementOfTypeGraph(true);
			} catch (TypeException e) {
			}
		} else {
			try {
				cn = addNode(x, y, en.getType(), true);
			} catch (TypeException e) {
			}
		}
		if (cn != null) {
			int dx = x - p.x;
			int dy = y - p.y;
			// wenn nur ein Object kopiert wurde --> p=(0,0);

			// Teste ob (x,y) im minus Bereich liegt oder groesser als max Wert
			int newX = en.getX() + dx;
			int newY = en.getY() + dy;

			if (newX <= 0)
				newX = newX - newX + cn.getWidth() * 2;
			if (newY <= 0)
				newY = newY - newY + cn.getHeight() * 2;

			if (newX >= GraphCanvas.MAX_XWIDTH)
				newX = GraphCanvas.MAX_XWIDTH - cn.getWidth();
			if (newY >= GraphCanvas.MAX_YHEIGHT)
				newY = GraphCanvas.MAX_YHEIGHT - cn.getHeight();
			cn.setReps(newX, newY, true, false);
			cn.getLNode().setFrozenByDefault(true); // en.getLNode().isFrozenAsDefault());
			// System.out.println("Copy obj: (x,y): "+cn.getX()+" "+cn.getY());
		}
		return cn;
	}
*/
	
	public EdNode copyNode(EdNode en, int x, int y) {
		EdNode cn = null;
		EdType t = this.typeSet.getNodeType(en.getType().getBasisType(), 
				en.getType().getName(), en.getType().getShape(), 
				en.getType().getColor(), en.getType().hasFilledShape());
		
		if (t != null) {
			this.typeSet.setSelectedNodeType(t);
			try {
				cn = addNode(x, y, t, true);
				cn.getBasisNode().copyAttributes(en.getBasisNode());
				cn.getLNode().setFrozenByDefault(true); // en.getLNode().isFrozenAsDefault());
			} catch (TypeException e) {
			}
		}
		
		return cn;
	}

	public EdArc copyArc(EdArc ea, EdGraphObject src, EdGraphObject tar) {
		EdArc ca = null;
		EdType t = this.typeSet.getArcType(ea.getType().getBasisType(), 
				ea.getType().getName(), 
				ea.getType().getShape(), 
				ea.getType().getColor(),
				ea.getType().hasFilledShape());
		if (t != null) {
			this.typeSet.setSelectedArcType(t);
			try {
				boolean directed = !(this.bGraph instanceof UndirectedGraph);
				ca = addArc(t, src, tar, null, directed);
				if (ca != null) {
					ca.getBasisArc().copyAttributes(ea.getBasisArc());
					if (ea.isLine()) {
						if (ea.hasAnchor()) {
							ca.setAnchor(new Point(ea.getX(), ea.getY()));
						}
					} else {
						if (ea.hasAnchor()) {
							ca.setAnchor(Loop.UPPER_LEFT, new Point(ea.getX(),
									ea.getY()));
							ca.setWidth(ea.getWidthOfLoop());
							ca.setHeight(ea.getHeightOfLoop());
						}
					}
					ca
							.setTextOffset(ea.getTextOffset().x, ea
									.getTextOffset().y);
					ca.setReps(ea.isDirected(), true, false);
					ca.getLArc().setFrozenByDefault(true); // ea.getLArc().isFrozenAsDefault());
				}

			} catch (TypeException e) {
			}
		}

		return ca;
	}

	private EdArc copyArc(EdArc ea, EdGraphObject src, EdGraphObject tar,
			int dx, int dy) {
		EdArc ca = null;
		if (this.bGraph != null) {
			Arc ba = null;
			try {
				ba = this.bGraph.copyArc(ea.getBasisArc(), (Node) src
						.getBasisObject(), (Node) tar.getBasisObject());
				ca = new EdArc(ba, ea.getType(), src, tar);
				this.addElement(ca, false);
				ca.setMorphismMark(this.nodes.size() + this.arcs.size());
			} catch (TypeException e) {}
		} 
		if (ca != null) {
			if (ea.isLine()) {
				if (ea.hasAnchor()) {
					ca.setAnchor(new Point(ea.getX() + dx, ea.getY() + dy));
				}
			} else {
				if (ea.hasAnchor()) {
					ca.setAnchor(Loop.UPPER_LEFT, new Point(ea.getX() + dx, ea
							.getY()
							+ dy));
					ca.setWidth(ea.getWidth());
					ca.setHeight(ea.getHeight());
				}
			}
			ca.setTextOffset(ea.getTextOffset().x, ea.getTextOffset().y);
			ca.setReps(ea.isDirected(), true, false);
			ca.getLArc().setFrozenByDefault(true); // ea.getLArc().isFrozenAsDefault());
		}
		return ca;
	}

	private Point findCenter(Vector<EdNode> v) {
		// v is Vector of EdGraphObject
		if (v == null)
			return new Point(0, 0);
		if (v.size() == 0)
			return new Point(0, 0);

		Vector<Point> points = new Vector<Point>();
		for (int i = 0; i < v.size(); i++) {
			EdGraphObject o = v.elementAt(i);
			if (o != null)
				points.addElement(new Point(o.getX(), o.getY()));
		}
		CenterOfPoints centerOfPoints = new CenterOfPoints(points);
		return centerOfPoints.getCenter();
	}

	public void setMorphismMarks(HashMap<?,?> marks, boolean all) {
		if (all)
			this.clearMarks();
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode n = this.nodes.elementAt(i);
			GraphObject go = n.getBasisNode();
			String m = (String) marks.get(go);
			if (m != null && m.length() > 0) {
				if (all || (!all && n.getMorphismMark().equals(""))) {
					n.addMorphismMark(m);
					marks.remove(go);
				}
			}
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc a = this.arcs.elementAt(i);
			GraphObject go = a.getBasisArc();
			String m = (String) marks.get(go);
			if (m != null && m.length() > 0) {
				if (all || (!all && a.getMorphismMark().equals(""))) {
					a.addMorphismMark(m);
					marks.remove(go);
				}
			}
		}
	}


	public void setCurrentLayoutToDefault(boolean b) {
		this.hasDefaultLayout = b;
		freezeLayout(b);
	}


	private void handleChangeEvent(int changeEvent, GraphObject obj1, GraphObject obj2) {
		if (changeEvent == Change.OBJECT_CREATED) {
			if (this.newAfterTransformStep == null)
				newAfterTransformStep = new Vector<EdGraphObject>();
			
			if (obj1.isNode()) {				
				EdNode newObj = newNode((Node) obj1);					
				this.addCreatedToUndo(newObj);
				this.undoManagerEndEdit();
				this.newAfterTransformStep.add(newObj);
				this.isGraphTransformed = true;
				this.nodeNumberChanged = true;				
			} else {
				EdArc newObj = newArc((Arc) obj1);
				this.addCreatedToUndo(newObj);
				this.undoManagerEndEdit();
				this.newAfterTransformStep.add(newObj);
				this.isGraphTransformed = true;
			}
		} else if (changeEvent == Change.WANT_DESTROY_OBJECT) {
			EdGraphObject go = this.findGraphObject(obj1);
			if (go != null) {
				this.addDeletedToUndo(go);
				this.typeSet.removeTypeUser(go.getType(), go);
			}
		} else if (changeEvent == Change.OBJECT_DESTROYED) {
			EdGraphObject go = this.findGraphObject(obj1);
			if (go != null) {
				if (go.isNode()) {
					this.nodeNumberChanged = true;
					this.nodeRemoved = true;
				}
				go.removeFromAttributeViewObserver();
				this.removeElement(go);
				this.undoManagerEndEdit();
				this.isGraphTransformed = true;
			}
		} else if (changeEvent == Change.TARGET_UNSET) {
			EdGraphObject go = this.findGraphObject(obj1);
//			System.out.println("Change.TARGET_UNSET:: "+obj1);
			if (go != null) {		
				this.addUnsetTargetOfArcToUndo(go);
				go.removeFromAttributeViewObserver();
//				this.removeDirtyArc(go);
				this.removeArcElement((EdArc) go);
				this.undoManagerEndEdit();
				this.isGraphTransformed = true;
//				System.out.println("Change.TARGET_UNSET:: edge target unset");
			}
		} else if (changeEvent == Change.TARGET_SET) {			
			EdGraphObject go = this.findGraphObject(obj1);
//			System.out.println("Change.TARGET_SET:: "+obj1);
			if (go == null) {
				EdArc newObj = newArc((Arc) obj1);
				this.addSetTargetOfArcToUndo(newObj);
				this.undoManagerEndEdit();
//				System.out.println("Change.TARGET_SET:: edge target set");
			} 
		} else if (changeEvent == Change.SOURCE_UNSET) {
			EdGraphObject go = this.findGraphObject(obj1);
//			System.out.println("Change.SOURCE_UNSET:: "+obj1);
			if (go != null) {		
				this.addUnsetSourceOfArcToUndo(go);
				go.removeFromAttributeViewObserver();
//				this.removeDirtyArc(go);
				this.removeArcElement((EdArc) go);
				this.undoManagerEndEdit();
				this.isGraphTransformed = true;
//				System.out.println("Change.SOURCE_UNSET:: edge source unset");
			}
		} else if (changeEvent == Change.SOURCE_SET) {			
			EdGraphObject go = this.findGraphObject(obj1);
//			System.out.println("Change.SOURCE_SET:: "+obj1);
			if (go == null) {
				EdArc newObj = newArc((Arc) obj1);
				this.addSetSourceOfArcToUndo(newObj);
				this.undoManagerEndEdit();
//				System.out.println("Change.SOURCE_SET:: edge source set");
			} 
		} else if (changeEvent == Change.WANT_MODIFY_OBJECT) {
			EdGraphObject go = this.findGraphObject(obj1);
			this.addChangedAttributeToUndo(go);
		} else if (changeEvent == Change.OBJECT_MODIFIED) {
			this.undoManagerEndEdit();
		} 
//		else if (changeEvent == Change.OBJECT_GLUED) {} 
	}

	protected boolean resetSourceTargetOfEdge(final EdArc go, final Arc arc) {
		boolean result = false;
		if (go.getSource().getBasisObject() != arc.getSource()) {
			EdNode src = this.findNode(arc.getSource());
			if (src != null) {
				go.setSource(src);
				result = true;
			}
		}
		if (go.getTarget().getBasisObject() != arc.getTarget()) {
			EdNode tar = this.findNode(arc.getTarget());
			if (tar != null) {
				go.setTarget(tar);
				result = true;
			}
		}
		return result;
	}
	
	
	/** Updates graph layout after observable basis Graph was changed*/
	public void update(final Observable o, final Object arg) {
		synchronized(this) {
		if (this.isTransformChange && this.bGraph != null && arg != null) {
//			if (changedObjects == null) 
//				changedObjects = new Vector<GraphObject>();
			
			// o : Graph / OrdinaryMorphism,
			// arg : Change
			GraphObject go = null;
			Change ch = (Change) arg;
			if ((ch.getEvent() == Change.OBJECT_CREATED)
					|| (ch.getEvent() == Change.WANT_DESTROY_OBJECT)
					|| (ch.getEvent() == Change.OBJECT_DESTROYED)
	//				|| (ch.getEvent() == Change.OBJECT_GLUED)
					|| (ch.getEvent() == Change.WANT_MODIFY_OBJECT)
	//				|| (ch.getEvent() == Change.MAPPING_ADDED)
	//				|| (ch.getEvent() == Change.MAPPING_REMOVED)
					|| (ch.getEvent() == Change.SOURCE_UNSET)
					|| (ch.getEvent() == Change.TARGET_UNSET)
					|| (ch.getEvent() == Change.SOURCE_SET)
					|| (ch.getEvent() == Change.TARGET_SET)) {
				if (ch.getItem() instanceof Node) {
					go = (GraphObject) ch.getItem();
					handleChangeEvent(ch.getEvent(), go, null);
				} else if (ch.getItem() instanceof Arc) {
					go = (GraphObject) ch.getItem();
					handleChangeEvent(ch.getEvent(), go, null);
				} else if (ch.getItem() instanceof Pair) {
					// if ch.getEvent() == Change.OBJECT_GLUED
					Pair<?,?> p = (Pair<?,?>) ch.getItem();
					Object obj1 = p.first;
					Object obj2 = p.second;
					if (obj1 instanceof GraphObject && obj2 instanceof GraphObject) {
						go = (GraphObject) obj1;
						handleChangeEvent(ch.getEvent(), (GraphObject) obj1,
								(GraphObject) obj2);
					}
				}
			} else if (ch.getEvent() == Change.OBJECT_MODIFIED) {
				if (ch.getItem() instanceof Pair) {
					Pair<?,?> p = (Pair<?,?>) ch.getItem();
					Object obj = p.first;
					if ((obj instanceof Node) && this.bGraph.isElement((Node) obj)) {
						go = (GraphObject) obj;
					} else if ((obj instanceof Arc) && this.bGraph.isElement((Arc) obj)) {
						go = (GraphObject) obj;
					}
					if (go != null) {
						handleChangeEvent(ch.getEvent(), (GraphObject) obj, null);
					}
				}
			}
	
			if (go != null) {
				if (changedObjects == null) 
					changedObjects = new Vector<GraphObject>();
				
				this.changed = true;
				if (!this.changedObjects.contains(go))
					this.changedObjects.addElement(go);
				if (this.eGra != null)
					this.eGra.setChanged(true);
			}
		}
		}
	}

	public Vector<EdGraphObject> getChangedGraphObjects() {
		if (this.changed && (changedObjects != null)) {
			Vector<EdGraphObject> v = new Vector<EdGraphObject>();
			for (int i = 0; i < this.changedObjects.size(); i++) {
				EdGraphObject go = findGraphObject(this.changedObjects.elementAt(i));
				if (go != null) {
					v.addElement(go);
				}
			}
			this.changed = false;
			this.changedObjects.removeAllElements();
			return v;
		} 
		return null;
	}

	public boolean hasDefaultLayout() {
		return this.hasDefaultLayout;
	}

	public void enableDefaultGraphLayout(boolean b) {
		this.externalLayouting = !b;
	}
	
//	public void updateBySelected() {
//		for(int i=0; i<this.nodes.size(); i++) {
//			EdNode n = this.nodes.get(i);
//			if(n.getBasisNode().selected)
//				this.select(n);
//		}
//		for(int i=0; i<this.arcs.size(); i++) {
//			EdArc a = this.arcs.get(i);
//			if(a.getBasisArc().selected)
//				this.select(a);
//		}
//	}

	/** Updates graph layout after renewed reading of the used Graph object */
	public void update() {
		updateGraph();
	}

	/** Updates graph layout after reading its Graph object */
	public void updateGraph(boolean attrsVisible) {
		if (this.bGraph == null)
			return;
		synchronized (this) {
//			this.attrVisible = attrsVisible;
			if (!this.hasDefaultLayout) {
				doDefaultEvolutionaryGraphLayout(20);
			} else
				doUpdateGraph(false);
		}
	}

	public void updateGraph(boolean attrsVisible, boolean selectNewObjects) {
		if (this.bGraph == null)
			return;
		synchronized (this) {
//			this.attrVisible = attrsVisible;
			if (!this.hasDefaultLayout) {
				doDefaultEvolutionaryGraphLayout(20);
				if (selectNewObjects && (this.newAfterTransformStep != null)) {
					for (int i = 0; i < this.newAfterTransformStep.size(); i++) {
						this.select(this.newAfterTransformStep.get(i));
					}
				}
			} else
				doUpdateGraph(selectNewObjects);
		}
	}

	/** Updates graph layout after renewed reading of the used Graph object */
	public void updateGraph() {
		if (this.bGraph == null)
			return;
		synchronized (this) {
//			System.out.println("updateGraph():  hasDefaultLayout: "+this.getName()+"   "+hasDefaultLayout);	
		if (!this.hasDefaultLayout) {
			doDefaultEvolutionaryGraphLayout(20);
		} else
			doUpdateGraph(false);
		}
	}

	private void doUpdateGraph(boolean selectNewObjects) {			
		synchronized (this) {
			if (this.isTransformChange && this.isGraphTransformed) {
				if (!this.externalLayouting) {
					doDefaultEvolutionaryGraphLayout(20);
				}
	
				if (selectNewObjects && (this.newAfterTransformStep != null)) {
					for (int i = 0; i < this.newAfterTransformStep.size(); i++)
						this.select(this.newAfterTransformStep.get(i));
				}
	
				this.isGraphTransformed = false;
			} else if (!this.isTransformChange) {
				if (!this.hasDefaultLayout) {
					doDefaultEvolutionaryGraphLayout(20);
				}
			}
		}
	}

	public void refreshInheritanceArcs() {
		synchronized (this) {
			if (this.isTG && (this.inheritanceArcs != null)) {
				for (int i = 0; i < this.inheritanceArcs.size(); i++) {
					this.arcs.remove(this.inheritanceArcs.get(i));
				}
				Vector<EdArc> oldInhArcs = new Vector<EdArc>(this.inheritanceArcs);
				this.inheritanceArcs.clear();
	
				Enumeration<Arc> bInhArcs = this.typeSet.getBasisTypeSet()
						.getInheritanceArcs().elements();
				while (bInhArcs.hasMoreElements()) {
					Arc bArc = bInhArcs.nextElement();
					boolean found = false;
					for (int k = 0; k < oldInhArcs.size(); k++) {
						EdArc currentArc = oldInhArcs.get(k);
						if (currentArc.getBasisArc() == bArc) {
							if (!this.inheritanceArcs.contains(currentArc)) {
								this.inheritanceArcs.add(currentArc);
								this.arcs.add(currentArc);
							}
							found = true;
							break;
						}
					}
					if (!found)
						newInheritanceArc(bArc, this.arcs);
				}
			}
		}
	}

	private void makeInheritanceArcs() {
		synchronized (this) {
		if (this.isTG) {
			Enumeration<Arc> bInhArcs = this.typeSet.getBasisTypeSet()
					.getInheritanceArcs().elements();
			while (bInhArcs.hasMoreElements()) {
				Arc bArc = bInhArcs.nextElement();
				if (this.findArc(bArc) == null) {
					newInheritanceArc(bArc, this.arcs);
				}
			}
		}
		}
	}

	public void synchronizeWithBasis(boolean selectnew) {
		synchronized (this) {
			// first basis nodes to this nodes
			final List<EdNode> list = new Vector<EdNode>(this.nodes.size());
			list.addAll(this.nodes);
			
			Iterator<Node> en = this.bGraph.getNodesSet().iterator();
			while (en.hasNext()) {
				Node bn = en.next();
				EdNode node = findNode(bn);
				if (node != null) {
					if (bn.getContext() == null) {
						this.removeElement(node);
					}
					list.remove(node);
				}
				else {
					EdNode n = newNode(bn);
					if (selectnew)
						this.select(n);
				}
			}
			// now this nodes to basis nodes
			for (int i=0; i<list.size(); i++) {
				EdNode node = list.get(i);
				if (node.getBasisNode().getContext() == null) {
					this.removeElement(node);
				}
			}
			list.clear();
			
			// first basis arcs to this arcs
			final List<EdArc> list1 = new Vector<EdArc>(this.arcs.size());
			list1.addAll(this.arcs);
			
			Iterator<Arc> ea = this.bGraph.getArcsSet().iterator();
			while (ea.hasNext()) {
				Arc ba = ea.next();
				EdArc arc = findArc(ba);
				if (arc != null) {
					if (ba.getContext() == null) {
						this.removeElement(arc);
					}
					list1.remove(arc);
				}
				else {
					EdArc a = newArc(ba);
					if (selectnew)
						this.select(a);
				}
			}
			// now this arcs to basis arcs
			for (int i=0; i<list1.size(); i++) {
				EdArc arc = list1.get(i);
				if (arc.getBasisArc().getContext() == null) {
					this.removeElement(arc);
				}
			}
			list1.clear();
			
			// create inheritance arcs
			if (this.isTG)
				this.makeInheritanceArcs();
		}
	}

	/**
	 * Updates graph layout considering morphisms specified by the
	 * OrdinaryMorphism m1, OrdinaryMorphism m2
	 */
	public void updateGraph(final OrdinaryMorphism m1,
										final OrdinaryMorphism m2) {
		if (m1 == null || m2 == null) {
			return;
		}
		synchronized (this) {
			EdGraph eImageGraph = this;
			eImageGraph.clearMarks();
			EdNode enI = null;
			EdArc eaI = null;
			EdGraph eOrigGraph = new EdGraph(m1.getOriginal());
			EdNode enO = null;
			EdArc eaO = null;
			eOrigGraph.clearMarks();
	
			// use morphism m1
			Enumeration<GraphObject> domain = m1.getDomain();
			while (domain.hasMoreElements()) {
				GraphObject bOrig = domain.nextElement();
				GraphObject bImage = m1.getImage(bOrig);
	
				enI = eImageGraph.findNode(bImage);
				if (enI != null) {
					if (enI.isMorphismMarkEmpty()) {
						enI.addMorphismMark(enI.getMyKey());
					}
					enO = eOrigGraph.findNode(bOrig);
					if (enO != null) {
						enO.addMorphismMark(enI.getMorphismMark());
					}
				} else {
					eaI = eImageGraph.findArc(bImage);
					if (eaI != null) {
						if (eaI.isMorphismMarkEmpty()) {
							eaI.addMorphismMark(eaI.getMyKey());
						}
						eaO = eOrigGraph.findArc(bOrig);
						if (eaO != null) {
							eaO.addMorphismMark(eaI.getMorphismMark());
						}
					}
				}
			}
	
			// use morphism m2
			domain = m2.getDomain();
			while (domain.hasMoreElements()) {
				GraphObject bOrig = domain.nextElement();
				GraphObject bImage = m2.getImage(bOrig);
	
				enI = eImageGraph.findNode(bImage);
				if (enI != null) {
					if (enI.isMorphismMarkEmpty()) {
						enI.addMorphismMark(enI.getMyKey());
					}
					enO = eOrigGraph.findNode(bOrig);
					if (enO != null) {
						enO.addMorphismMark(enI.getMorphismMark());
					}
				} else {
					eaI = eImageGraph.findArc(bImage);
					if (eaI != null) {
						if (eaI.isMorphismMarkEmpty()) {
							eaI.addMorphismMark(eaI.getMyKey());
						}
						eaO = eOrigGraph.findArc(bOrig);
						if (eaO != null) {
							eaO.addMorphismMark(eaI.getMorphismMark());
						}
					}
				}
			}
		}
	}

	/**
	 * Sets the layout from another EdGraph. The basis Graph of this and the
	 * specified EdGraph layout should be the same. The corresponding graph
	 * object is found by its basis object.
	 */
	public void setLayoutByBasisObject(EdGraph layout) {
		setLayoutFrom(layout, true, true);
	}

	/**
	 * Sets the layout from another EdGraph. * The basis Graph of this and the
	 * specified EdGraph layout should be the same. The corresponding graph
	 * object is found by its basis object.
	 */
	private void setLayoutFrom(EdGraph layout, boolean ofNodes, boolean ofArcs) {
		if (this.bGraph == layout.getBasisGraph()) {
			if (this.nodes.size() == 0)
				makeGraphObjects();
			if (ofNodes) {
				for (int i = 0; i < this.nodes.size(); i++) {
					EdNode n = this.nodes.get(i);
					EdNode ln = layout.findNode(n.getBasisNode());
					if (ln != null) {
						n.setX(ln.getX());
						n.setY(ln.getY());
						n.getLNode().setFrozenByDefault(true);
					}
				}
			}
			if (ofArcs) {
				for (int i = 0; i < this.arcs.size(); i++) {
					EdArc a = this.arcs.get(i);
					EdArc la = layout.findArc(a.getBasisArc());
					if (la != null) {
						if (la.isLine()) {
							if (la.hasAnchor())
								a.setAnchor(la.getAnchor());
							else
								a.setAnchor(null);
						} else {
							if (la.hasAnchor()) {
								a.setAnchor(Loop.UPPER_LEFT, new Point(la
										.getAnchor()));
								a.setWidth(la.getWidthOfLoop());
								a.setHeight(la.getHeightOfLoop());
							}
						}
						a.getLArc().setFrozenByDefault(true);
						a.setTextOffset(la.getTextOffset().x, la
								.getTextOffset().y);
					}
				}
			}
			this.hasDefaultLayout = true;
		} else {
			this.doDefaultEvolutionaryGraphLayout(20);
		}
	}


	/**
	 * Sets the layout from another EdGraph. The basis graphs may be different.
	 * The corresponding graph objects are found by its Type. It is suitable for
	 * a type graph especially,
	 */
	public void setLayoutByType(EdGraph layout) {
		
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode n = this.nodes.get(i);
			Vector<EdNode> other = layout.getNodes(n.getType());
			if (!other.isEmpty()) {
				EdNode nl = other.firstElement();
				n.setX(nl.getX());
				n.setY(nl.getY());
				n.getLNode().setFrozenByDefault(true);
			}
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc a = this.arcs.get(i);
			Vector<EdArc> other = layout.getArcs(a.getType());
			for (int j = 0; j < other.size(); j++) {
				EdArc al = other.get(j);
				if (a.getSource().getType().compareTo(al.getSource().getType())
						&& a.getTarget().getType().compareTo(
								al.getTarget().getType())) {
					if (al.isLine()) {
						if (al.hasAnchor())
							a.setAnchor(new Point(al.getAnchor().x, al
									.getAnchor().y));
						else
							a.setAnchor(null);
					} else if (al.hasAnchor()) {
						a.setAnchor(Loop.UPPER_LEFT, new Point(al.getAnchor()));
						a.setWidth(al.getWidthOfLoop());
						a.setHeight(al.getHeightOfLoop());
					}
					a.getLArc().setFrozenByDefault(true);
					a.setTextOffset(al.getTextOffset().x, al.getTextOffset().y);
				}
			}
		}
		
		this.hasDefaultLayout = true;
	}

	protected EdNode findNodeByBasisContextUsage(String basisHashCode) {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode n = this.nodes.get(i);
			if (n.getBasisNode().getContextUsage() == Integer.valueOf(basisHashCode).intValue()) {
				return n;
			}
		}
		return null;
	}
	
	protected EdArc findArcByBasisContextUsage(String basisHashCode) {
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc a = this.arcs.get(i);
			if (a.getBasisArc().getContextUsage() == Integer.valueOf(basisHashCode).intValue()) {
				return a;
			}
		}
		return null;
	}
	
	public void setLayoutByContextUsage(EdGraph layout, boolean ofNodesOnly) {
		for (int i = 0; i < layout.getNodes().size(); i++) {
			EdNode ln = layout.getNodes().get(i);
			EdNode n = this.findNodeByBasisContextUsage(String.valueOf(ln.getBasisNode().hashCode()));			
			if (n != null) {
				n.setX(ln.getX());
				n.setY(ln.getY());
				n.getLNode().setFrozenByDefault(true);
			}
		}
		
		this.hasDefaultLayout = true;
		if (ofNodesOnly) {
			return;
		}
		
		for (int i = 0; i < layout.getArcs().size(); i++) {
			EdArc la = layout.getArcs().get(i);
			EdArc a = this.findArcByBasisContextUsage(String.valueOf(la.getBasisArc().hashCode()));
			if (la.isLine()) {
				if (la.hasAnchor()) {
						a.setAnchor(new Point(la.getAnchor().x, 
											la.getAnchor().y));
				} else {
						a.setAnchor(null);
				}
			} else {
					a.setAnchor(Loop.UPPER_LEFT, new Point(la.getAnchor()));
					a.setWidth(la.getWidthOfLoop());
					a.setHeight(la.getHeightOfLoop());
			}
			a.getLArc().setFrozenByDefault(true);
			a.setTextOffset(la.getTextOffset().x,
						la.getTextOffset().y);		
		}
	}

	/**
	 * Sets the layout from another EdGraph. The basis graphs may be different.
	 * The corresponding graph objects are found by its index.
	 */
	public void setLayoutByIndex(EdGraph layout, boolean ofNodesOnly) {
		if (this.nodes.size() == 0)
			makeGraphObjects();
		for (int i = 0; i < this.nodes.size(); i++) {
			if (i < layout.getNodes().size()) {
				EdNode n = this.nodes.get(i);
				n.setX(layout.getNodes().get(i).getX());
				n.setY(layout.getNodes().get(i).getY());
				n.getLNode().setFrozenByDefault(true);
			}
		}
		
		this.hasDefaultLayout = true;
		if (ofNodesOnly)
			return;

		for (int i = 0; i < this.arcs.size(); i++) {
			if (i < layout.getArcs().size()) {
				EdArc a = this.arcs.get(i);
				EdArc other = layout.getArcs().get(i);
				if (other.isLine()) {
					if (other.hasAnchor())
						a.setAnchor(new Point(other.getAnchor().x, other
								.getAnchor().y));
					else
						a.setAnchor(null);
				} else if (other.hasAnchor()) {
					a.setAnchor(Loop.UPPER_LEFT, new Point(other.getAnchor()));
					a.setWidth(other.getWidthOfLoop());
					a.setHeight(other.getHeightOfLoop());
				}
				a.getLArc().setFrozenByDefault(true);
				a.setTextOffset(other.getTextOffset().x,
						other.getTextOffset().y);
			}
		}
	}

	/**
	 * Sets the layout from the specified EdGraph layout. The basis graph of the
	 * specified layout has to be the source graph and the basis graph of this
	 * EdGraph has to be the target graph of the specified OrdinaryMorphism om.
	 */
	public boolean updateLayoutByIsoMorphism(
			final OrdinaryMorphism isoCopyOfBaseGraph, final EdGraph layout) {
		// System.out.println("EdGraph.updateLayoutByIsoMorphism:: this:
		// "+bGraph.hashCode()+ " =? target:
		// "+isoCopyOfBaseGraph.getTarget().hashCode()+" source:
		// "+isoCopyOfBaseGraph.getSource().hashCode()+" =? layout:
		// "+layout.getBasisGraph().hashCode());
		if (layout.getBasisGraph() != isoCopyOfBaseGraph.getSource()
				|| this.bGraph != isoCopyOfBaseGraph.getTarget())
			return false;
		// System.out.println("EdGraph.updateLayoutByIsoMorphism:: bGraph:
		// "+bGraph.hashCode()+ " =? target:
		// "+isoCopyOfBaseGraph.getTarget().hashCode()+" source:
		// "+isoCopyOfBaseGraph.getSource().hashCode()+" =? layout:
		// "+layout.getBasisGraph().hashCode());
		synchronized (this) {
		for (int i = 0; i < layout.getNodes().size(); i++) {
			EdNode ln = layout.getNodes().elementAt(i);
			Node orig = ln.getBasisNode();
			GraphObject img = isoCopyOfBaseGraph.getImage(orig);
			EdNode en = this.findNode(img);
			if (en != null) {
				en.setX(ln.getX());
				en.setY(ln.getY());
			}
		}
		for (int i = 0; i < layout.getArcs().size(); i++) {
			EdArc la = layout.getArcs().elementAt(i);
			Arc orig = la.getBasisArc();
			GraphObject img = isoCopyOfBaseGraph.getImage(orig);
			EdArc ea = this.findArc(img);
			if (ea != null) {
				if (la.isLine()) {
					if (la.hasAnchor()) {
						ea.setAnchor(new Point(la.getX(), la.getY()));
					}
				} else {
					if (la.hasAnchor()) {
						ea.setAnchor(Loop.UPPER_LEFT, new Point(la.getX(), la
								.getY()));
						ea.setWidth(la.getWidth());
						ea.setHeight(la.getHeight());
					}
				}
				ea.setTextOffset(la.getTextOffset().x, la.getTextOffset().y);
				ea.getLArc().setFrozenByDefault(true);
			}
		}

		this.setCurrentLayoutToDefault(true);
		return true;
		}
	}

	/*
	 * Filters the layout for old objects. Layout from removed graphobjects will
	 * be removed
	 */
	public void filterLayout() {
		/* filter Arcs */
		/* check for removed basis arcs */
		for (int i = this.arcs.size() - 1; i >= 0; i--) {
			EdArc ea = this.arcs.elementAt(i);
			Arc a = findBasisArc(ea);
			if (a == null || (!a.isInheritance() && !this.bGraph.isElement(a))) {
				this.arcs.remove(ea);
				ea.dispose();
			}
		}

		/* now filter Nodes */
		/* check for removed basis nodes */
		for (int i = this.nodes.size() - 1; i >= 0; i--) {
			EdNode en = this.nodes.elementAt(i);
			Node n = findBasisNode(en);
			if (n == null || !this.bGraph.isElement(n)) {
				this.nodes.remove(en);
				this.basisNode2node.remove(en.getBasisObject());
				en.dispose();
			}
		}
	}

	public void setXYofNewNode(
			final EdRule rule, 
			final OrdinaryMorphism validmatch, 
			final OrdinaryMorphism comatch) {
		
		if (rule == null || validmatch == null || comatch == null
				|| !this.nodeNumberChanged) {
			return;
		}
		List<GraphObject> newobjs = rule.getBasisRule().getElementsToCreate();
		if (newobjs.size() > 0) {
			Node ruleimg = null;
			Node graphimg = null;
			EdNode enruleimg = null;
			EdNode engraphimg = null;
			for (int i=0; i<newobjs.size(); i++) {
				if (newobjs.get(i).isNode()) {
					final Node nodetocreate = (Node) newobjs.get(i);
					if (ruleimg == null
							&& !nodetocreate.getIncomingArcsSet().isEmpty()) {
						final Iterator<Arc> incomarcs = nodetocreate.getIncomingArcsSet().iterator();
						while (incomarcs.hasNext()) {
							final Arc arc = incomarcs.next();
							if (rule.getBasisRule().getInverseImage(arc.getSource()).hasMoreElements()) {
								ruleimg = (Node) arc.getSource();
								final GraphObject go = rule.getBasisRule().getInverseImage(arc.getSource()).nextElement();
								graphimg = (Node) validmatch.getImage(go);
								break;
							}
						}						
					}
					if (ruleimg == null
							&& !nodetocreate.getOutgoingArcsSet().isEmpty()) {
						final Iterator<Arc> outarcs = nodetocreate.getOutgoingArcsSet().iterator();
						while (outarcs.hasNext()) {
							final Arc arc = outarcs.next();
							if (rule.getBasisRule().getInverseImage(arc.getTarget()).hasMoreElements()) {
								ruleimg = (Node) arc.getTarget();
								final GraphObject go = rule.getBasisRule().getInverseImage(arc.getTarget()).nextElement();
								graphimg = (Node) validmatch.getImage(go);
								break;
							}
						}						
					}								
					if (ruleimg == null) {
						final Enumeration<GraphObject> ruledom = rule.getBasisRule().getDomain();
						while (ruledom.hasMoreElements()) {
							final GraphObject go = ruledom.nextElement();
							if (go.isNode() && !this.getTypeSet().getType(go.getType()).animated) {
								ruleimg = (Node) rule.getBasisRule().getImage(go);
								graphimg = (Node) validmatch.getImage(go);
								break;
							}
						}
					} else {					
						break;
					}
				}
			}
			
			for (int i=0; i<newobjs.size(); i++) {
				if (newobjs.get(i).isNode()) {
					Node nodeTocreate = (Node) newobjs.get(i);
					EdNode enodeTocreate = rule.getRight().findNode(nodeTocreate);	
					
					Node createdNode = (Node) comatch.getImage(nodeTocreate);
					EdNode createdEnode = this.findNode(createdNode);
					int posX = 0;
					int posY = 0;	
					
					if (createdEnode != null && enodeTocreate != null) {
						if (ruleimg != null) {
							enruleimg = rule.getRight().findNode(ruleimg);
							if (enruleimg != null) {								
								posX = enruleimg.getX() - enodeTocreate.getX();
								posY = enruleimg.getY() - enodeTocreate.getY();
							}
							
							engraphimg = this.findNode(graphimg);
							if (engraphimg != null) {								
								if ((engraphimg.getX() - posX) >= 0)
									posX = engraphimg.getX() - posX;
								else {
									posX = Math.abs(engraphimg.getX() - posX);
								}
								if ((engraphimg.getY() - posY) >= 0)
									posY = engraphimg.getY() - posY;
								else {
									posY = Math.abs(engraphimg.getY() - posY);
								}
							}
						} else {
							posX = enodeTocreate.getX();
							posY = enodeTocreate.getY();							
						}
						
						createdEnode.setX(posX);
						createdEnode.setY(posY);
						createdEnode.getLNode().setFrozenByDefault(this.staticNodeXY);
					}
				}
			}
		}
	}
	
	/**
	 * Select the graph objects which are images of the given morphism.
	 * If the rule parameter is not NULL, select the new created graph objects only.
	 */
	public void updateAlongMorph(Morphism coMorph, Rule r) {
		synchronized (this) {
		if (coMorph != null) {
			deselectAll();
			// select new objects along coMorph
			for (Enumeration<GraphObject> e = coMorph.getDomain(); e.hasMoreElements();) {
				GraphObject o = e.nextElement();
				GraphObject img = coMorph.getImage(o);
				if (r != null) {
					Enumeration<GraphObject> 
					inverse = r.getInverseImage(o);
					if (!inverse.hasMoreElements()) {
						EdGraphObject go = findGraphObject(img);
						if (go != null)
							select(go);
					}
				} else {
					EdGraphObject go = findGraphObject(img);
					if (go != null)
						select(go);
				}
			}
		}
		}
	}
	
	/**
	 * Select the graph objects which are images of the given morphism.
	 */
	public void updateAlongMorph(final Morphism m) {
	synchronized (this) {
		deselectAll();
		if (m != null) {
			// select objects along the match
			for (Enumeration<GraphObject> e = m.getDomain(); e.hasMoreElements();) {
				GraphObject o = e.nextElement();
				GraphObject img = m.getImage(o);
				EdGraphObject go = findGraphObject(img);
				if (go != null) {
					select(go);
				}
			}
		}
	}
	}


/*
	private EdGraphObject getOrigAfterStep(GraphObject image, Morphism coMorph,
			EdRule er) {
		if (image == null || coMorph == null || coMorph.getImage() == null
				|| er == null)
			return null;
		GraphObject orig = null;
		EdNode enOrig;
		EdArc eaOrig;
		if (coMorph.getImage().isElement(image)) {
			Enumeration<GraphObject> origs = coMorph.getInverseImage(image);
			if (origs.hasMoreElements())
				orig = origs.nextElement();
		}
		if (orig != null && er.getRight().getBasisGraph().isElement(orig)) {
			if (orig.isNode()) {
				enOrig = er.getRight().findNode(orig);
				return enOrig;
			} else {
				eaOrig = er.getRight().findArc(orig);
				return eaOrig;
			}
		}
		return null;
	}
*/
	
	public void updateSelection() {
		synchronized (this) {
			if (selectedNodes == null)
				selectedNodes = new Vector<EdNode>();
			else
				this.selectedNodes.removeAllElements();			
			if (selectedArcs == null)
				selectedArcs = new Vector<EdArc>();
			else
				this.selectedArcs.removeAllElements();
			
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode en = this.nodes.elementAt(i);
			if (en.isSelected())
				this.selectedNodes.addElement(en);
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc ea = this.arcs.elementAt(i);
			if (ea.isSelected())
				this.selectedArcs.addElement(ea);
		}
		}
	}

	/** Clears morphism marks */
	public void clearMarks() {
		synchronized (this) {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode en = this.nodes.elementAt(i);
			en.clearMorphismMark();
			en.setMorphismMark(i+1);
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc ea = this.arcs.elementAt(i);
			ea.clearMorphismMark();
			ea.setMorphismMark(this.nodes.size()+i+1);
		}
		}
	}

	/** Gets error message */
	public String getMsg() {
		if (this.errMsg == null)
			this.errMsg = "";
		return this.errMsg;
	}

	/** Returns current graph dimension */
	public Dimension getGraphDimension() {
		synchronized (this) {
			int maxX = 0;
			int maxY = 0;
			int offsetX  = 0;
			int offsetY = 0;
			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode elem = this.nodes.elementAt(i);
				offsetX = elem.getWidth()/2 +10;
				offsetY = elem.getHeight()/2 +10;
							
				if ((elem.getX() + offsetX) > maxX)
					maxX = elem.getX() + offsetX;
				if ((elem.getY() + offsetY) > maxY)
					maxY = elem.getY() + offsetY;
			}
			for (int i = 0; i < this.arcs.size(); i++) {
				EdArc elem = this.arcs.elementAt(i);
				offsetX = elem.getWidth()/2 +10;
				offsetY = elem.getHeight()/2 +10;
				
				if ((elem.getX() + offsetX) > maxX)
					maxX = elem.getX() + offsetX;
				if ((elem.getY() + offsetY) > maxY)
					maxY = elem.getY() + offsetY;
			}
			return new Dimension(maxX, maxY);
		}
	}

	/** Returns current graph dimension under considerring of scale */
	public Dimension getGraphDimension(double scale) {
		Dimension dim = getGraphDimension();
		return new Dimension((int) (scale * dim.width),
				(int) (scale * dim.height));
	}


	/* painting methods */
	
	public double getScale() {
		return this.itsScale;
	}
	
	public void applyScale(double scale) {
		for (int i = 0; i < this.nodes.size(); i++) {
			this.nodes.get(i).applyScale(scale);
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			this.arcs.get(i).applyScale(scale);
		}
		this.itsScale = scale;
	}


	/*
	 * Draws all nodes and arcs if parameter all is true otherwise only
	 * selected objects.
	 */
	/*
	private void drawGraphics(
			Graphics g,
			boolean all) {
		synchronized (this) {
			for (int i = 0; i < arcs.size(); i++) {
				EdArc a = arcs.elementAt(i);			
				
				if (isCPA)
					a.drawNameAttrOnly(g);
				else if (all || a.isSelected()) {
					a.setAttributeVisible(true);
					a.drawGraphic(g);
				}
			}
			for (int i = 0; i < nodes.size(); i++) {
				EdNode n = nodes.elementAt(i);
				if (isCPA)
					n.drawNameAttrOnly(g);
				else if (all || n.isSelected())
					n.drawGraphic(g);
			}
		}
	}
*/
	public void makeInitialUpdateOfNodes() {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode n = this.nodes.elementAt(i);
//			n.setAttributeVisible(true);
			if (this.isCPA) {
				n.updateNameAttrOnly(null);
			} else  {
				n.myUpdate(null);
			}
		}
	}
	
	/**
	 * Draws all nodes and arcs if parameter all is true otherwise only
	 * selected objects
	 * @deprecated
	 */
	public void drawGraphics(
				Graphics g,
				boolean all, 
				boolean attribueVisible) {		 
		synchronized (this) {
			for (int i = 0; i < this.arcs.size(); i++) {
				EdArc a = this.arcs.elementAt(i);
				a.setAttributeVisible(attribueVisible);
				
				if (this.isCPA) {
					a.drawNameAttrOnly(g);
				}
				else if (all || a.isSelected()) {
					a.drawGraphic(g);
				}
			}
			
			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode n = this.nodes.elementAt(i);
				n.setAttributeVisible(attribueVisible);
				if (this.isCPA) {
					n.drawNameAttrOnly(g);
				}
				else if (all || n.isSelected()) {
					n.drawGraphic(g);
				}
			}
		}
	}
	
	/**
	 * To use this method
	 * please make sure that the method <code>this.updateVisibility()</code>
	 * is called at least ones after the visibility of node resp. arc type object
	 * of the type graph was changed. 
	 * 
	 * @return list with visible nodes
	 */
	public List<EdNode> getVisibleNodes() {
		if (this.isTG || this.visibleNodes == null || this.visibleNodes.isEmpty())
			return this.nodes;
		
		return this.visibleNodes;
	}
	
	/**
	 * To use this method
	 * please make sure that the method <code>this.updateVisibility()</code>
	 * is called at least ones after the visibility of node resp. arc type object
	 * of the type graph was changed. 
	 * 
	 * @return list with visible edges
	 */
	public List<EdArc> getVisibleArcs() {
		if (this.isTG || this.visibleArcs == null || this.visibleArcs.isEmpty())
			return this.arcs;
		
		return this.visibleArcs;
	}
	
	/**
	 * This method is only useable if this graph is not a type graph.
	 */
	
	public void updateVisibility() {
		doUpdateVisibility(false);
	}
	
	private void doUpdateVisibility(boolean forceCheck) {		
		if (this.isTG) {
			this.visibilityChecked = true;
		}
		else {
			if (!this.visibilityChecked || forceCheck) {
				if (this.visibleNodes == null)
					visibleNodes = new Vector<EdNode>();
				else
					this.visibleNodes.clear();
				if (this.visibleArcs == null)
					visibleArcs = new Vector<EdArc>();				
				else
					this.visibleArcs.clear();
				
				for (int i = 0; i < this.nodes.size(); i++) {
					EdNode n = this.nodes.elementAt(i);
					if (n.isVisible()) {
						this.visibleNodes.add(n);
					}
				}
					
				for (int i = 0; i < this.arcs.size(); i++) {
					EdArc a = this.arcs.elementAt(i);
					if (a.isVisible()) {
						this.visibleArcs.add(a);
					}
				}	
			}
			this.visibilityChecked = true;
		}
	}
	
	public void forceVisibilityUpdate() {
		doUpdateVisibility(true);
	}
	
	/**
	 * Draws all nodes and arcs from the specified lists into the specified graphics 
	 * if the parameter all is true,
	 * otherwise draws selected objects, only.
	 */
	public void drawGraphics(
			final Graphics graphics,
			final List<EdNode> nodeList,
			final List<EdArc> arcList,
			final boolean all, 
			final boolean attributeVisible) {
		
		synchronized (this) {
			if (this.firstDraw) {
				// make test draw of node to set its real size (only ones!)
				this.firstDraw = false;
				final Graphics testGraphics = graphics.create();
				for (int i = 0; i < nodeList.size(); i++) {
					EdNode n = nodeList.get(i);
					n.setAttributeVisible(attributeVisible);
					if (this.isCPA) {
						n.drawNameAttrOnly(testGraphics);
					}
					else if (all || n.isSelected()) {
						n.drawGraphic(testGraphics);
					}
				}
			}
			
			for (int i = 0; i < arcList.size(); i++) {
				EdArc a = arcList.get(i);
				a.setAttributeVisible(attributeVisible);				
				if (this.isCPA) {
					a.drawNameAttrOnly(graphics);
				}
				else if (all || a.isSelected()) {				
					a.drawGraphic(graphics);
				}
			}
			
			for (int i = 0; i < nodeList.size(); i++) {
				EdNode n = nodeList.get(i);
				n.setAttributeVisible(attributeVisible);
				if (this.isCPA) {
					n.drawNameAttrOnly(graphics);
				}
				else if (all || n.isSelected()) {
					n.drawGraphic(graphics);
				}
			}
		}
	}
	
	/** Draws the graphic of the node n */
	public void drawNode(Graphics g, EdNode n) {
		n.drawGraphic(g);
	}

	/** Draws the graphic of the arc a */
	public void drawArc(Graphics g, EdArc a) {
		a.drawGraphic(g);
		((EdNode) a.getSource()).drawGraphic(g);
		((EdNode) a.getTarget()).drawGraphic(g);
	}

	/** Draws the graphic of the graph object o */
	public void drawObj(Graphics g, EdGraphObject o) {
		if (o.isNode())
			((EdNode) o).drawGraphic(g);
		else
			((EdArc) o).drawGraphic(g);
	}

	/** Draws the graphics of the nodes from vector v */
	public void drawNodes(Graphics g, Vector<EdNode> v) {
		synchronized (this) {
			if (v != null) {
				for (int j = 0; j < v.size(); j++) {
					EdNode n = v.elementAt(j);
					drawNode(g, n);
				}
			}
		}
	}

	/** Draws the graphics of the arcs from vector v */
	public void drawArcs(Graphics g, Vector<EdArc> v) {
		synchronized (this) {
			for (int j = 0; j < v.size(); j++) {
				EdArc a = v.elementAt(j);
				drawArc(g, a);
			}
		}
	}

	/** Draws the graphics of the selected graph objects */
	public void drawSelected(Graphics g) {
		synchronized (this) {
			if (this.selectedArcs != null) {
				for (int j = 0; j < this.selectedArcs.size(); j++) {
					EdArc a = this.selectedArcs.elementAt(j);
					a.drawGraphic(g);
					EdNode src = (EdNode) a.getSource();
					EdNode trg = (EdNode) a.getTarget();
					if (this.selectedNodes == null || !this.selectedNodes.contains(src))
						src.drawGraphic(g);
					if (this.selectedNodes == null || !this.selectedNodes.contains(trg))
						trg.drawGraphic(g);
				}
			}
			
			drawNodes(g, this.selectedNodes);
		}
	}

	/**
	 * Erases the graphic of the node n public void eraseOnlyNode(Graphics g,
	 * double scale, EdNode n) { n.eraseGraphic(g, scale); }
	 */

	/**
	 * Erases the graphic of the node n together with incoming and outgoing
	 * edges
	 */
	public void eraseNode(Graphics g, EdNode n) {
		eraseArcs(g, getIncomingArcs(n));
		eraseArcs(g, getOutgoingArcs(n));
		n.eraseGraphic(g);
	}

	/** Erases the graphics of the nodes from vector v */
	public void eraseNodes(Graphics g, Vector<EdNode> v) {
		synchronized (this) {
		for (int j = 0; j < v.size(); j++) {
			EdNode n = v.elementAt(j);
			eraseNode(g, n);
		}
		}
	}

	/** Erases the graphic of the edge a */
	public void eraseArc(Graphics g, EdArc a) {
		a.eraseGraphic(g);
		((EdNode) a.getSource()).drawGraphic(g);
		((EdNode) a.getTarget()).drawGraphic(g);
	}

	/** Erases the graphics of the edges from vector v */
	public void eraseArcs(Graphics g, Vector<EdArc> v) {
		synchronized (this) {
		for (int j = 0; j < v.size(); j++) {
			EdArc a = v.elementAt(j);
			eraseArc(g, a);
		}
		}
	}

	/** Erases the graphic of the graph object o */
	public void eraseObj(Graphics g, EdGraphObject o) {
		if (o.isNode())
			eraseNode(g, (EdNode) o);
		else
			eraseArc(g, (EdArc) o);
	}

	/** Erases the graphics of the selected graph objects */
	public void eraseSelected(Graphics g, boolean redraw) {
		synchronized (this) {
		if (this.selectedArcs != null) {
			for (int j = 0; j < this.selectedArcs.size(); j++) {
				EdArc a = this.selectedArcs.elementAt(j);
				if (redraw) {
					a.setSelected(false);
					drawArc(g, a);
				} else
					a.eraseGraphic(g);
				
				EdNode src = (EdNode) a.getSource();
				EdNode trg = (EdNode) a.getTarget();
				if (this.selectedNodes == null || !this.selectedNodes.contains(src))
					drawNode(g, src);
				if (this.selectedNodes == null || !this.selectedNodes.contains(trg))
					drawNode(g, trg);
			}
		}
		if (this.selectedNodes != null) {
			for (int j = 0; j < this.selectedNodes.size(); j++) {
				EdNode n = this.selectedNodes.elementAt(j);
				if (redraw) {
					n.setSelected(false);
					drawNode(g, n);
				} else
					n.eraseGraphic(g); // eraseNode(g, scale, n);
			}
		}
		}
	}

	public Vector<EdArc> getIncomingArcs(EdNode en) {
		synchronized (this) {
		Vector<EdArc> in = new Vector<EdArc>();
		for (int i = 0; i < this.arcs.size(); i++) {
			if (this.arcs.elementAt(i).getTarget() == en)
				in.addElement(this.arcs.elementAt(i));
		}
		return in;
		}
	}

	public Vector<EdArc> getOutgoingArcs(EdNode en) {
		synchronized (this) {
		Vector<EdArc> out = new Vector<EdArc>();
		for (int i = 0; i < this.arcs.size(); i++) {
			if (this.arcs.elementAt(i).getSource() == en)
				out.addElement(this.arcs.elementAt(i));
		}
		return out;
		}
	}

	public boolean containsIncomingOutgoingArcsAt(EdNode n) {
		if (!getIncomingArcs(n).isEmpty())
			return true;
		else if (!getOutgoingArcs(n).isEmpty())
			return true;
		else
			return false;
	}

	public boolean isUsingVariable(VarMember v) {
		return this.bGraph.isUsingVariable(v);
	}

	public void unsetAttributeValueWhereVariable() {
		this.bGraph.unsetAttributeValueWhereVariable();
	}

	public Vector<EdNode> getParentsOf(EdNode n) {
		Vector<EdNode> v = new Vector<EdNode>();
		if (this.inheritanceArcs != null) {
			for (int i = 0; i < this.inheritanceArcs.size(); i++) {
				EdArc a = this.inheritanceArcs.get(i);
				if (a.getSource() == n)
					v.add((EdNode) a.getTarget());
			}
		}
		return v;
	}

	public Vector<EdNode> getChildrenOf(EdNode n) {
		Vector<EdNode> v = new Vector<EdNode>();
		if (this.inheritanceArcs != null) {
			for (int i = 0; i < this.inheritanceArcs.size(); i++) {
				EdArc a = this.inheritanceArcs.get(i);
				if (a.getTarget() == n)
					v.add((EdNode) a.getSource());
			}
		}
		return v;
	}
	
	public void XwriteObject(XMLHelper h) {
		if (h.openObject(this.bGraph, this)) {
			
			if (this.bGraph.isCompleteGraph()) {
				if (this.ggen > 0) {
					h.addAttr("age", this.ggen);
				}
				if (this.straightenArcs) {
					h.addAttr("straightenArcs", "true");
				}
				if (this.staticNodeXY) {
					h.addAttr("staticNodePosition", "true");
				}
				if (this.undoManager != null 
						&& !this.undoManager.isEnabled()) {
					h.addAttr("undoEnabled", "false");
				}		
			}
			
			int j;
			for (j = 0; j < this.nodes.size(); j++) {
				EdNode n = this.nodes.elementAt(j);
				n.getLNode().setFrozenByDefault(true);
				h.addObject("", n, true);
			}
			for (j = 0; j < this.arcs.size(); j++) {
				EdArc a = this.arcs.elementAt(j);
				a.getLArc().setFrozenByDefault(true);
				h.addObject("", a, true);
			}
		}
	}

	public void XreadObject(XMLHelper h) {
		boolean hasLoadedLayout = false;
		h.peekObject(this.bGraph, this);
		
		if (this.bGraph.isCompleteGraph()) {			
			String age = h.readAttr("age");
			if (age != null && !age.equals("")) {
				try {
					this.ggen = (Integer.valueOf(age)).intValue();
				} catch (Exception ex) {}	
			}
			String straightenArcsStr = h.readAttr("straightenArcs");
			if (!straightenArcsStr.equals("")) {
				this.straightenArcs = Boolean.valueOf(straightenArcsStr).booleanValue();
			}
			String staticNodePositionStr = h.readAttr("staticNodePosition");
			if (!staticNodePositionStr.equals("")) {
				this.staticNodeXY = Boolean.valueOf(staticNodePositionStr).booleanValue();
			}
			
			String undoEnabledStr = h.readAttr("undoEnabled");
			if (!undoEnabledStr.equals("")) {
				this.eGra.enableUndoManager(Boolean.valueOf(undoEnabledStr).booleanValue());
			}
		}
		
		for (int j = 0; j < this.nodes.size(); j++) {
			EdNode n = this.nodes.elementAt(j);
			h.enrichObject(n);
			
			if (n.hasDefaultLayout) {
				hasLoadedLayout = true;
				n.getLNode().setFrozenByDefault(true);
			} 
		}
		for (int j = 0; j < this.arcs.size(); j++) {
			EdArc a = this.arcs.elementAt(j);
			h.enrichObject(a);
			
			a.getLArc().setFrozenByDefault(true);
		}

		if (hasLoadedLayout) {
			this.hasDefaultLayout = true;
		}
		else if (this.nodes.size() > 1) {
			if (this.isTG || this.bGraph.isCompleteGraph()) {
				if (this.nodes.size() <= 100) 
					layoutBasisGraph(new Dimension(800, 600));
				else 
					layoutBasisGraph(new Dimension(1000, 800));
			}
			else if (!this.isCPA) {
				if (this.nodes.size() <= 50) 
					layoutBasisGraph(new Dimension(400, 300));
				else if (this.nodes.size() <= 100) 
					layoutBasisGraph(new Dimension(600, 400));
				else 
					layoutBasisGraph(new Dimension(800, 600));
			}
			if (!this.bGraph.isCompleteGraph()) {
				this.staticNodeXY = true;
			}
			// in case of CPAGraph do nothing here
		} 
	}

	public void layoutBasisGraph(final Dimension maxdim) {
		if (this.hasDefaultLayout) {
			return;
		}
			
		this.nodeNumberChanged = true;
		setCurrentLayoutToDefault(false);
		getDefaultGraphLayouter().setEnabled(true);
				
		doDefaultEvolutionaryGraphLayout(this.itsLayouter,
							100, 10, maxdim);
		this.nodeNumberChanged = false;
	}
	
	// Methoden unten betreffen berechnung des Layouts

	public EvolutionaryGraphLayout getDefaultGraphLayouter() {
		return this.itsLayouter;
	}

	public boolean isStaticNodePositionEnabled() {
		return this.staticNodeXY;
	}
	
	public void setStaticNodePosition(boolean enable) {
		if (enable && !this.staticNodeXY) {
			for (int i = 0; i < this.nodes.size(); i++) {
				this.nodes.get(i).getLNode().setFrozen(true);
			}
		} else if (!enable && this.staticNodeXY) {
			for (int i = 0; i < this.nodes.size(); i++) {
				this.nodes.get(i).getLNode().setFrozen(false);
			}
		}
		
		this.staticNodeXY = enable;
	}

	public void enableStaticNodePosition() {
		if (!this.staticNodeXY) {
			for (int i = 0; i < this.nodes.size(); i++) {
				this.nodes.get(i).getLNode().setFrozen(true);
			}
			this.staticNodeXY = true;
		}
	}

	public void disableStaticNodePosition() {
		if (this.staticNodeXY) {
			for (int i = 0; i < this.nodes.size(); i++) {
				this.nodes.get(i).getLNode().setFrozen(false);
			}
			this.staticNodeXY = false;
		}
	}
	
	public void doDefaultEvolutionaryGraphLayout(int spaceBetweenParallelEdges) {
		if (this.nodes.isEmpty()) {
			this.makeGraphObjects();
			this.nodeNumberChanged = this.nodes.size() > 0;
		}
		
		doDefaultEvolutionaryGraphLayout(this.itsLayouter, 50,
				spaceBetweenParallelEdges);
	}

	public void forceDefaultEvolutionaryGraphLayout(int spaceBetweenParallelEdges) {
		if (this.nodes.isEmpty()) {
			this.makeGraphObjects();
		}
		
		this.nodeNumberChanged = true;
		doDefaultEvolutionaryGraphLayout(this.itsLayouter, 50,
				spaceBetweenParallelEdges);
	}
	
	public void doDefaultEvolutionaryGraphLayout(
			final EvolutionaryGraphLayout layouter, 
			int iters,
			int spaceBetweenParallelEdges) {
		
		doDefaultEvolutionaryGraphLayout(layouter, iters, spaceBetweenParallelEdges, null);
	}
	
	public void doDefaultEvolutionaryGraphLayout(
			final EvolutionaryGraphLayout layouter, 
			int iters,
			int spaceBetweenParallelEdges,
			final Dimension dim) {

		if (this.bGraph.isCompleteGraph() && !this.nodeNumberChanged) {
			return;
		}
		
		this.updateVisibility();		
		final List<EdNode> visiblenodes = this.getVisibleNodes();
		final List<EdArc> visiblearcs = new Vector<EdArc>(); //this.getVisibleArcs();

		if (visiblenodes.size() <= 1) {
			return;
		}
		
		this.updateNodePosEtoL(visiblenodes);

		if (dim == null) {
			layouter.setPanelSize(layouter.getNeededPanelSize(visiblenodes));
		} else {
			layouter.setPanelSize(dim);
		}

		int intersect = layouter.getNodeIntersect(visiblenodes, true);
		if (intersect > 0) {
			if (!layouter.isEnabled())
				layouter.setFrozenByDefault(true);
			else 
				layouter.setFrozenByDefault(false);
			layouter.makeRandomLayoutOfNodes(this, visiblenodes);
		} else if (!layouter.isEnabled())
			return;
		else {
			layouter.setFrozenByDefault(false);
			layouter.setUsePattern(false);
		}

		this.straightAllArcs();

		
		// set node ID
		for (int i = 0; i < visiblenodes.size(); i++) {
			EdNode n = visiblenodes.get(i);
			if (n.getNodeID() == -1) {
				// this.incLastNodeID();
				// n.setNodeID(this.getLastNodeID());
				n.setNodeID(n.hashCode());
			}
		}

		// neue Cluster der nodes berechnen
		for (int i = 0; i < visiblenodes.size(); i++) {
			EdNode n = visiblenodes.get(i);		
			// n.calculateCluster(layouter.getLayoutMetrics().getEpsilon(),this.getNodes());
			n.calculateCluster(100, visiblenodes); // default ist 200
		}

		if (!layouter.isEnabled())
			layouter.layoutGraph(this, visiblenodes, visiblearcs, iters, 1, 1);
		else
			layouter.layoutGraph(this, visiblenodes, visiblearcs, iters, 1, 50);

		// zentrieren ist noch nicht implementiert !
		if (layouter.isCentre())
			layouter.centreLayout(this, visiblenodes);

		if (spaceBetweenParallelEdges > 0)
			resolveArcOverlappings(spaceBetweenParallelEdges);

		this.refreshInheritanceArcs();

		// freezeLayout(true);
		this.hasDefaultLayout = true;
	}

	private void freezeLayout(boolean b) {
		for (int i = 0; i < this.nodes.size(); i++) {
			EdNode n = this.nodes.get(i);
			n.getLNode().setFrozenByDefault(b);
			n.getLNode().unsetOverlap();
		}
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc a = this.arcs.get(i);
			a.getLArc().setFrozenByDefault(b);
		}
		// storeXY();
	}


	public void resolveArcOverlappings(int space) {
		if (this.arcs.size() < 2 || this.straightenArcs) {
			return;
		}
		
		for (int j = 0; j < this.arcs.size(); j++) {
			EdArc a = this.arcs.elementAt(j);
			if (!a.hasAnchor())
				resizeArc(a, space);
		}
	}

	private void resizeArc(EdArc ea, int space) {
		if (ea.getBasisArc().isInheritance()
				|| ea.hasAnchor()
				|| this.straightenArcs) {
			return;
		}
		
		int step = space;
		if (step < 10)
			step = 10;
		int nb1 = 0;
		int nb2 = 0;
		for (int i = 0; i < this.arcs.size(); i++) {
			EdArc elem = this.arcs.elementAt(i);
			if (!elem.equals(ea)) {
				/*
				 * check overlapping of edges with the same source/target and
				 * target/source
				 */
				if (elem.isLine() && ea.isLine()) {
					/* edge is a line */
					if ((elem.getSource() == ea.getSource() && elem.getTarget() == ea
							.getTarget())
							|| (elem.getSource() == ea.getTarget() && elem
									.getTarget() == ea.getSource())) {

						Point p1 = new Point(elem.getSource().getX(), elem
								.getSource().getY());
						Point p2 = new Point(elem.getTarget().getX(), elem
								.getTarget().getY());
						Point p = new Point((p1.x + (p2.x - p1.x) / 2),
								(p1.y + (p2.y - p1.y) / 2));
						int aa = (p.x - p1.x) * (p.x - p1.x) + (p.y - p1.y)
								* (p.y - p1.y);
						int k0 = 2;
						try {
							int a = (int) Math.sqrt(aa);
							k0 = a / step;
						} catch (ArithmeticException aex) {
							System.out.println("EdGraph.resizeArc::  "+aex.getLocalizedMessage());
//							aex.printStackTrace();
						}
						if (k0 == 0)
							k0 = 2;
						if (!elem.hasAnchor() && !ea.hasAnchor()) {
							Point p3 = new Point((p.x + (p.y - p1.y) / k0),
									(p.y - (p.x - p1.x) / k0));
							if (p3.x <= 0)
								p3.x = 5;
							if (p3.y <= 0)
								p3.y = 5;
							ea.setAnchor(p3);
							if (elem.getSource() == ea.getSource()
									&& elem.getTarget() == ea.getTarget())
								nb1++;
							else if (elem.getSource() == ea.getTarget()
									&& elem.getTarget() == ea.getSource())
								nb2++;
						} else if (elem.hasAnchor() && !ea.hasAnchor())
							;
						else if (!elem.hasAnchor() && ea.hasAnchor())
							;
						else if (elem.getAnchor().x == ea.getAnchor().x
								&& elem.getAnchor().y == ea.getAnchor().y) {
							if (elem.getSource() == ea.getSource()
									&& elem.getTarget() == ea.getTarget()) {
								nb1++;
								Point p3 = new Point((p.x + nb1 * (p.y - p1.y)
										/ k0), (p.y - nb1 * (p.x - p1.x) / k0));
								if (p3.x <= 0)
									p3.x = 5;
								if (p3.y <= 0)
									p3.y = 5;
								ea.setAnchor(p3);
							} else if (elem.getSource() == ea.getTarget()
									&& elem.getTarget() == ea.getSource()) {
								nb2++;
								Point p3 = new Point((p.x + nb2 * (p.y - p1.y)
										/ k0), (p.y - nb2 * (p.x - p1.x) / k0));
								if (p3.x <= 0)
									p3.x = 5;
								if (p3.y <= 0)
									p3.y = 5;
								ea.setAnchor(p3);
							}
						}
					}
				} else if (!elem.isLine() && !ea.isLine()) {
					/* edge is a loop */
					if (elem.getSource().equals(ea.getSource())) {
						Loop loop = null;
						if (ea.getWidth() == ea.getHeight()
								&& ea.getHeight() == 0) {
							if (ea.getSource().isNode()) {
								int w1 = ea.getWidthOfLoop();
								int h1 = w1;
								int x1 = ea.getSource().getX()
										- ea.getSource().getNode().getWidth()
										/ 2 - w1 / 2 - w1 / 4;
								int y1 = ea.getSource().getY()
										- ea.getSource().getNode().getHeight()
										/ 2 - h1 / 2 - h1 / 4;
								loop = new Loop(x1, y1, w1, h1);
							}
						} else
							loop = ea.toLoop();
						if (loop != null) {
							if ((elem.getAnchor(Loop.UPPER_LEFT).x == loop
									.getAnchor(Loop.UPPER_LEFT).x && elem
									.getAnchor(Loop.UPPER_LEFT).y == loop
									.getAnchor(Loop.UPPER_LEFT).y)
									|| (elem.getWidth() == loop.w && elem
											.getHeight() == loop.h)) {
								ea.setAnchor(Loop.UPPER_LEFT, new Point(elem
										.getAnchor(Loop.UPPER_LEFT).x
										- step,
										elem.getAnchor(Loop.UPPER_LEFT).y
												- step));

								ea.setWidth(elem.getWidthOfLoop() + step);
								ea.setHeight(elem.getHeightOfLoop() + step);
							}
						}
					}
				}
			}
		}
	}


	// Ab hier neu hinzugefuegtes fuer den Layouter
	/**
	 * setzt die Koordinaten aller EdNodes von this auf die werte, die in der
	 * jeweiligen LayoutNode der EdNode als akt gespeichert ist
	 * 
	 */
	public void updateNodePosLtoE(final List<EdNode> nodelist) {
		for (int i = 0; i < nodelist.size(); i++) {
			EdNode e = nodelist.get(i);
			e.setXY(e.getLNode().getAkt().x, e.getLNode().getAkt().y);
			// System.out.println("L2E:: i: "+i+" x= "+e.getX()+" y="+e.getY());
		}
	}

	/**
	 * setzt die Koordinaten aller LayoutNodes die zu EdNodes von this gehoeren
	 * auf die Werte der Koordinaten, der jeweiligen EdNode
	 */
	public void updateNodePosEtoL(final List<EdNode> nodelist) {
		for (int i = 0; i < nodelist.size(); i++) {
			EdNode e = nodelist.get(i);
			e.getLNode().setAkt(new Point(e.getX(), e.getY()));
			// System.out.println("E2L:: i: "+i+" x= "+e.getLNode().getAkt().x+"
			// y="+e.getLNode().getAkt().y);
		}
	}

	public void updateLengthOfLayoutEdge(final List<EdArc> arclist, int l) {
		for (int i = 0; i < arclist.size(); i++) {
			EdArc e = arclist.get(i);
			e.getLArc().setAktLength(l);
			e.getLArc().setPrefLength(l);
		}
	}

	/**
	 * Returns max node dimension of its nodes.
	 */
	public Dimension getMaxNodeDim() {
		int w = 0, h = 0;
		EdNode e;
		for (int i = 0; i < this.nodes.size(); i++) {
			e = this.nodes.get(i);
			w = Math.max(w, e.getWidth());
			h = Math.max(h, e.getHeight());
		}
		Dimension d = new Dimension(w, h);
		return d;
	}

	/**
	 * Returns average node dimension of its nodes.
	 */
	public Dimension getAverageNodeDim() {
		float aw = 0, ah = 0;
		if (!this.nodes.isEmpty()) {
			for (int i = 0; i < this.nodes.size(); i++) {
				EdNode e = this.nodes.get(i);
				aw = aw + e.getWidth();
				ah = ah + e.getHeight();
			}
		
			aw = aw / this.nodes.size();
			ah = ah / this.nodes.size();
		}
		Dimension d = new Dimension((int) (aw + 1), (int) (ah + 1));
		return d;
	}

	public Dimension getAverageNodeDim(final List<EdNode> visiblenodes) {
		float aw = 0, ah = 0;
		if (!visiblenodes.isEmpty()) {
			for (int i = 0; i < visiblenodes.size(); i++) {
				EdNode e = visiblenodes.get(i);
				aw = aw + e.getWidth();
				ah = ah + e.getHeight();
			}
		
			aw = aw / visiblenodes.size();
			ah = ah / visiblenodes.size();
		}

		return new Dimension((int)aw, (int)ah);
	}
	
	public Dimension getGraphDim() {
		return this.gDim;
	}

	public void setGraphDim(Dimension d) {
		this.gDim = new Dimension(d);
	}

	/**
	 * Returns generation of this graph.
	 */
	public int getGraphGen() {
		return this.ggen;
	}

	/**
	 * Incriments the generation of this graph.
	 */
	public void incGraphGen() {
		this.ggen++;
		for (int i = 0; i < this.nodes.size(); i++) {
			this.nodes.get(i).getLNode().incAge();
		}
	}

	/*
	private int lastnodeid;
	private Vector<Integer> reservedNodeIDs;

	private boolean addReservedNodeID(int reservedID) {
		if (reservedNodeIDs == null)
			reservedNodeIDs = new Vector<Integer>();
		for (int i = 0; i < reservedNodeIDs.size(); i++) {
			if (reservedNodeIDs.get(i).intValue() == reservedID)
				return false;
		}
		reservedNodeIDs.add(Integer.valueOf(reservedID));
		return true;
	}
	
	private boolean isReservedNodeID(int nodeID) {
		if (this.reservedNodeIDs == null)
			return false;
		for (int i = 0; i < this.reservedNodeIDs.size(); i++) {
			if (this.reservedNodeIDs.get(i).intValue() == nodeID)
				return true;
		}
		return false;
	}

	public int getLastNodeID() {
		return this.lastnodeid;
	}

	public void incLastNodeID() {
		this.lastnodeid++;
		while (isReservedNodeID(this.lastnodeid))
			this.lastnodeid++;
	}
	*/
}
