package agg.editor.impl;

//import java.util.Enumeration;
import java.util.Vector;
import java.util.Hashtable;
import java.awt.Point;
import javax.swing.undo.*;

import agg.util.Pair;
import agg.xt_basis.Arc;
//import agg.xt_basis.Node;
//import agg.xt_basis.Graph;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;

public class ArcReprData implements StateEditable {

//	protected String graphName; // for test only!!
	
	protected int typeHashCode;
	
	protected boolean elemOfTG;
	
	protected TypeReprData typeRepresentation;

	protected Hashtable<String, Pair<String,String>> attributes = new Hashtable<String, Pair<String,String>>();

	protected Point textOffset = new Point(0, 0);

	protected boolean hasAnchor;

	protected Point location;

	protected int loopW, loopH;
	
	protected String arcHC, fromHC, toHC; // hash code of arc, source and target node
	
	protected int key = this.hashCode();
   
	protected int arcHashCode;
	
	protected boolean frozen, frozenAsDefault;

	protected boolean restoreDone;
	
	public void storeState(Hashtable<Object, Object> state) {
		state.put(Integer.valueOf(this.key), this);
	}

	public void restoreState(Hashtable<?, ?> state) {
		ArcReprData data = (ArcReprData) state.get(Integer.valueOf(this.key));
		state.remove(Integer.valueOf(this.key));
		this.restoreDone = false;
		
		if (data != null) {
//			this.graphName = data.graphName;
			
			this.typeHashCode = data.typeHashCode;		
			this.typeRepresentation = data.typeRepresentation;
			this.elemOfTG = data.elemOfTG;
			this.attributes = data.attributes;
			this.textOffset = data.textOffset;
			this.loopW = data.loopW;
			this.loopH = data.loopH;
			this.hasAnchor = data.hasAnchor;
			this.location = data.location;
			this.arcHC = data.arcHC;
			this.fromHC = data.fromHC;
			this.toHC = data.toHC;		
			this.frozen = data.frozen;
			this.frozenAsDefault = data.frozenAsDefault;
			
			this.restoreDone = true;
		} 
	}
	
	public boolean hasRestored() {
		return this.restoreDone;
	}
	
	protected ArcReprData(EdArc a) {
		if (a.getBasisArc() == null) {
			return;
		}
		
//		this.graphName = a.getContext().getName();
		
		this.key = this.hashCode();
		this.arcHashCode = a.hashCode();
		this.typeHashCode = a.getType().hashCode();
		this.elemOfTG = a.isElementOfTypeGraph();
		
		if (a.isElementOfTypeGraph())
			this.typeRepresentation = new TypeReprData(a);
		else
			this.typeRepresentation = new TypeReprData(a.getType());
		
		this.arcHC = a.getContextUsage();
		if (a.getContextUsage().indexOf(String.valueOf(a.hashCode())) == -1)
			this.arcHC = String.valueOf(a.hashCode()) + ":"
					+ a.getContextUsage();
		
		this.fromHC = String.valueOf(a.getSource().hashCode());
		if (a.getContext() != null && a.getContext().isTargetObjOfGraphEmbedding(a.getSource())) {
			EdGraphObject kernObj = a.getContext().getSourceObjOfGraphEmbedding(a.getSource());
			if (kernObj != null) {
				this.fromHC = String.valueOf(kernObj.hashCode());
			}
		} 
		
		this.toHC = String.valueOf(a.getTarget().hashCode());
		if (a.getContext() != null && a.getContext().isTargetObjOfGraphEmbedding(a.getTarget())) {
			EdGraphObject kernObj = a.getContext().getSourceObjOfGraphEmbedding(a.getTarget());
			if (kernObj != null) {
				this.toHC = String.valueOf(kernObj.hashCode());
			}
		} 
		
		this.attributes = new Hashtable<String, Pair<String,String>>();
		if (a.getBasisObject().getAttribute() != null) {
			ValueTuple vt = (ValueTuple) a.getBasisObject().getAttribute();
			for (int i = 0; i < vt.getNumberOfEntries(); i++) {
				ValueMember vm = vt.getValueMemberAt(i);
				if(vm.getName() != null) {
					Pair<String,String> valPair = new Pair<String,String>(vm.getDeclaration().getTypeName(), "NULL");
					if (vm.getExpr() != null)
						valPair.second = vm.getExprAsText();
					this.attributes.put(vm.getName(), valPair);
				}
			}
		}

		this.hasAnchor = a.hasAnchor();
		if (a.hasAnchor()) {
			this.location = new Point(a.getAnchor());
		} else {
			this.location = new Point(a.getX(), a.getY());
		}

		this.textOffset = new Point(a.getTextOffset().x, a.getTextOffset().y);

		if (!a.isLine()) {
			this.loopW = a.getWidthOfLoop();
			this.loopH = a.getHeightOfLoop();
		}

		this.frozen = a.getLArc().isFrozen();
		this.frozenAsDefault = a.getLArc().isFrozenByDefault();
	}

	protected TypeReprData getArcTypeReprData() {
		return this.typeRepresentation;
	}

	protected void restoreArcFromArcRepr(EdArc a) {
		if (this.elemOfTG != a.isElementOfTypeGraph()) {
			return;
		}
		
		this.typeRepresentation.restoreTypeFromTypeRepr(a.getType());

		a.addContextUsage(this.arcHC);
		
		if (a.isElementOfTypeGraph()) {
			restoreMultiplicity(a, this.typeRepresentation);
		}
	
		if (!this.attributes.isEmpty()) {
			if (a.getBasisObject().getAttribute() != null) {
				Hashtable<String, Pair<String,String>> attrs = new Hashtable<String, Pair<String,String>>();
				attrs.putAll(this.attributes);
				restoreAttributes(attrs, a);
			}
		}

		a.setX(this.location.x);
		a.setY(this.location.y);
		if (this.hasAnchor)
			a.setAnchor(new Point(this.location.x, this.location.y));
		else
			a.setAnchor(null);

		a.setTextOffset(this.textOffset.x, this.textOffset.y);

		if (!a.isLine()) {
			a.setWidth(this.loopW);
			a.setHeight(this.loopH);
		}

		a.getLArc().setFrozen(this.frozen);
		a.getLArc().setFrozenByDefault(this.frozenAsDefault);
	}

	private EdType findArcType(EdGraph g, EdType t) {
		Vector<EdType> arcTypes = g.getTypeSet().getArcTypes();
		for (int i = 0; i < arcTypes.size(); i++) {
			EdType ti = arcTypes.get(i);
			if ((ti == t)
					|| (ti.getContextUsage().indexOf(
							String.valueOf(this.typeRepresentation
								.getTypeHashCode())) >= 0)) {
				return ti;
			}
		}
		return null;
	}

	
	private EdType findArcType(EdGraph g, int typeHC) {
		Vector<EdType> arcTypes = g.getTypeSet().getArcTypes();
		for (int i = 0; i < arcTypes.size(); i++) {
			EdType t = arcTypes.get(i);
			if (t.hashCode() == typeHC)
				return t;
			
			if (t.getContextUsage().indexOf(
						String.valueOf(this.typeRepresentation
								.getTypeHashCode())) >= 0) {
				return t;
			}
			
		}
		return null;
	}
	
	protected EdArc createArcFromArcRepr(EdGraph g, Vector<EdNode> restoredNodes) {		
		EdType type = findArcType(g, this.typeRepresentation.getTypeHashCode()); 
		if (type == null) {
			type = this.typeRepresentation.createTypeFromTypeRepr();
			type = findArcType(g, type);		
			if (type == null) 
				return null;
		}
		EdNode from = this.getNode(g, Integer.valueOf(this.fromHC).intValue());
		if (from == null) {
			from = (EdNode) g.findRestoredNode(this.fromHC);
		}
		EdNode to = this.getNode(g, Integer.valueOf(this.toHC).intValue());
		if (to == null) {
			to = (EdNode) g.findRestoredNode(this.toHC);
		}			
//		System.out.println("ArcReprData.createArcFromArcRepr::  from: "+from
//				 +"  to: "+to);

		if (from == null || to == null) {
			for (int i = 0; i < restoredNodes.size(); i++) {
				EdNode n = restoredNodes.get(i);
				if (n.getContextUsage().contains(this.fromHC)) {
					from = n;
				}
				if (n.getContextUsage().contains(this.toHC)) {
					to = n;
				}
			}
			if (from == null || to == null) {
				for (int i = 0; i < g.getNodes().size(); i++) {
					EdNode n = g.getNodes().get(i);
					if (from == null) {
						if (n.getContextUsage().contains(this.fromHC)) {
							from = n;
						}
					}
					if (to == null) {
						if (n.getContextUsage().contains(this.toHC)) {
							to = n;
						}
					}
				}
			}
//			System.out.println("ArcReprData.createArcFromArcRepr::  from: "+from
//					 +"  to: "+to);
		
			if (from == null || to == null) 
				return null;
		}
		EdArc a = null;
		try {
			Arc basis = g.getBasisGraph().createArc(type.getBasisType(),
					from.getBasisNode(), to.getBasisNode());

			a = g.addArc(basis, type);
			if (a == null) {
				return null;
			}
			
			a.addContextUsage(this.arcHC);

			if (a.isElementOfTypeGraph()) {
				restoreMultiplicity(a, this.typeRepresentation);
			}

			refreshAttributes(a);

			a.setX(this.location.x);
			a.setY(this.location.y);

			if (this.hasAnchor)
				a.setAnchor(new Point(this.location.x, this.location.y));

			a.setTextOffset(this.textOffset.x, this.textOffset.y);

			if (!a.isLine()) {
				a.setWidth(this.loopW);
				a.setHeight(this.loopH);
			}

			a.getLArc().setFrozen(this.frozen);
			a.getLArc().setFrozenByDefault(this.frozenAsDefault);
		} catch (TypeException ex) {}
		
		return a;
	}

	private void refreshAttributes(EdArc a) {
		if (!this.attributes.isEmpty()) {
			if (a.getBasisObject().getAttribute() == null)
				a.getBasisObject().createAttributeInstance();

			Hashtable<String, Pair<String,String>> attrs = new Hashtable<String, Pair<String,String>>();
			attrs.putAll(this.attributes);
			restoreAttributes(attrs, a);
		}
	}
	
	private EdNode getNode(EdGraph g, int hashCode) {
		Vector<EdNode> nodes = g.getNodes();
		for (int i=0; i<nodes.size(); i++) {
			EdNode n = nodes.get(i);
			if (n.hashCode() == hashCode) {
				return n;
			}
		}
		return null;
	}
	
	
	private void restoreMultiplicity(EdArc a, TypeReprData typedata) {
		Type t = a.getType().getBasisType();
		Type srct = a.getBasisArc().getSource().getType();
		Type tart = a.getBasisArc().getTarget().getType();
		t.setSourceMin(srct, tart, typedata.srcMinMultiplicity);
		t.setSourceMax(srct, tart, typedata.srcMaxMultiplicity);
		t.setTargetMin(srct, tart, typedata.tarMinMultiplicity);
		t.setTargetMax(srct, tart, typedata.tarMaxMultiplicity);
	}

	private void restoreAttributes(Hashtable<String, Pair<String,String>> attrs,
			EdArc a) {
		ValueTuple vt = (ValueTuple) a.getBasisObject().getAttribute();
		for (int i = 0; i < vt.getNumberOfEntries(); i++) {
			ValueMember vm = vt.getValueMemberAt(i);
			Pair<String,String> valPair = attrs.get(vm.getName());
			if (valPair != null && !valPair.isEmpty()) {
//				String tname = valPair.first;
				String expr = valPair.second;
				if (!a.isElementOfTypeGraph()) {
					if (expr != null) {
						if (vm.getExpr() == null) {
							if (!expr.equals("NULL")) {
								vm.setExprAsText(expr);
								vm.checkValidity();
							}
						} else if (expr.equals("NULL")) {
							vm.setExpr(null);
						} else {
							if (!vm.getExprAsText().equals(expr)) {
								vm.setExprAsText(expr);
								vm.checkValidity();
							}
						}
					}
				}
				attrs.remove(vm.getName());
			}
		}
	}

	
//	public void showData() {
//		System.out.println("Graph:  "+this.graphName+"   arc:  "+this.typeRepresentation.name);
//	}
}
