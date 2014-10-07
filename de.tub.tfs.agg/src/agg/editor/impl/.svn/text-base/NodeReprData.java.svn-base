package agg.editor.impl;

import java.util.List;
import java.util.Vector;
import java.util.Hashtable;
import java.awt.Point;
import javax.swing.undo.*;

import agg.util.Pair;
import agg.xt_basis.Node;
import agg.xt_basis.Type;
import agg.xt_basis.TypeSet;
import agg.xt_basis.TypeGraph;
//import agg.xt_basis.TypeError;
import agg.xt_basis.TypeException;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.ValueMember;

public class NodeReprData implements StateEditable {

	protected int typeHashCode;

	protected TypeReprData typeRepresentation;

	protected Vector<String> parents; // names of direct parents only

	protected Vector<String> children; // names of its own children only

	protected boolean elemOfTG;

	protected Hashtable<String, Pair<String,String>> attributes;

	protected Point location;

	protected String nodeHC;

	protected int nodeHashCode;
	
	protected int key = this.hashCode();

	protected boolean frozen, frozenAsDefault;

	public void storeState(Hashtable<Object, Object> state) {
		state.put(Integer.valueOf(this.key), this);
	}

	public void restoreState(Hashtable<?, ?> state) {
		NodeReprData data = (NodeReprData) state.get(Integer.valueOf(this.key));
		state.remove(Integer.valueOf(this.key));

		if (data != null) {
			this.typeHashCode = data.typeHashCode;
		
			this.typeRepresentation = data.typeRepresentation;
			this.parents = data.parents;
			this.children = data.children;
			this.elemOfTG = data.elemOfTG;
			this.attributes = data.attributes;
			this.location = data.location;
			this.nodeHC = data.nodeHC;
			this.frozen = data.frozen;
			this.frozenAsDefault = data.frozenAsDefault;
		}
	}

	protected NodeReprData(EdNode n) {
		if (n.getBasisNode() == null) {
			return;
		}
		
		this.key = this.hashCode();
		this.nodeHashCode = n.hashCode();
		this.typeHashCode = n.getType().hashCode();
		this.elemOfTG = n.isElementOfTypeGraph();
		this.parents = new Vector<String>(5, 5);
		this.children = new Vector<String>(5, 5);
		
		if (n.isElementOfTypeGraph())
			this.typeRepresentation = new TypeReprData(n);
		else
			this.typeRepresentation = new TypeReprData(n.getType());

		this.nodeHC = n.getContextUsage();
		if (n.getContextUsage().indexOf(String.valueOf(n.hashCode())) == -1)
			this.nodeHC = String.valueOf(n.hashCode()) + ":"
					+ n.getContextUsage();
		// System.out.println("NodeReprData:: nodeHC = "+nodeHC);

		this.attributes = new Hashtable<String, Pair<String,String>>();
		if (n.getBasisObject().getAttribute() != null) {
			ValueTuple vt = (ValueTuple) n.getBasisObject().getAttribute();
			for (int i = 0; i < vt.getNumberOfEntries(); i++) {
				ValueMember vm = vt.getValueMemberAt(i);
				if(vm.getName() != null) {
					Pair<String,String> valPair = new Pair<String,String>(vm.getDeclaration().getTypeName(), "NULL");
					if (vm.getExpr() != null) {
						valPair.second = vm.getExprAsText();
					}
//					System.out.println("stored::  "+valPair.first+" , "+valPair.second);
					this.attributes.put(vm.getName(), valPair);
				}
			}
		}

		this.location = new Point(n.getX(), n.getY());
		
		if (n.isElementOfTypeGraph()) {
			Type btype = n.getType().getBasisType();
			for (int i = 0; i < btype.getParents().size(); i++) {
				this.parents.add(btype.getParents().get(i).getName());
			}
			for (int i = 0; i < btype.getChildren().size(); i++) {
				this.children.add(btype.getChildren().get(i).getName());
			}
			// System.out.println("parents: "+ parents);
			// System.out.println("children: "+children);
		}

		this.frozen = n.getLNode().isFrozen();
		this.frozenAsDefault = n.getLNode().isFrozenByDefault();
	}
	
	protected TypeReprData getNodeTypeReprData() {
		return this.typeRepresentation;
	}

	protected Vector<String> getParentName() {
		return this.parents;
	}

	protected Vector<String> getChildName() {
		return this.children;
	}

	protected void restoreNodeFromNodeRepr(EdNode n) {
		if (this.elemOfTG != n.isElementOfTypeGraph()) {
			return;
		}
		
		this.typeRepresentation.restoreTypeFromTypeRepr(n.getType());
		n.setContextUsage(this.nodeHC);

		if (n.isElementOfTypeGraph()) {
			restoreParentsAndChildren(n);
			restoreMultiplicity(n, this.typeRepresentation);
		}

		if (!this.attributes.isEmpty()) {
			if (n.getBasisObject().getAttribute() != null) {
				Hashtable<String, Pair<String,String>> attrs = new Hashtable<String, Pair<String,String>>();
				attrs.putAll(this.attributes);
				restoreAttributes(attrs, n);
			}
		}
		
		n.setX(this.location.x);
		n.setY(this.location.y);
			
		n.getLNode().setFrozen(this.frozen);
		n.getLNode().setFrozenByDefault(this.frozenAsDefault);
	}

		
	private EdType findNodeType(EdGraph g, int typeHC) {
		Vector<EdType> nodeTypes = g.getTypeSet().getNodeTypes();
		for (int i = 0; i < nodeTypes.size(); i++) {
			EdType t = nodeTypes.get(i);
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
	
	private void restoreParentsAndChildren(EdNode n) {
		// System.out.println("NodeReprData.restoreParentsAndChildren:: n:
		// "+n.isElementOfTypeGraph()+"
		// "+n.getBasisNode().getContext().isTypeGraph());
		if (n.getBasisNode().getContext().isTypeGraph()) { // n.isElementOfTypeGraph()){
			TypeGraph tg = (TypeGraph) n.getBasisNode().getContext();
			Type myType = n.getBasisNode().getType();

			// restore inheritance, first parents
			// System.out.println("NodeReprData.restoreParentsAndChildren::
			// parents: "+parents);
			if (this.parents.isEmpty()) {
				int lastTypeGraphCheck = tg.getTypeSet()
						.getLevelOfTypeGraphCheck();
				tg.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.DISABLED);
				Vector<Type> pars = myType.getParents();
				for (int i = 0; i < pars.size(); i++) {
					Type parType = pars.get(i);
//					boolean res = 
					tg.getTypeSet().removeInheritanceRelation(myType, parType);
				}
				tg.getTypeSet().setLevelOfTypeGraphCheck(lastTypeGraphCheck);
			} else {
				// first remove current parent
				Vector<Type> pars = myType.getParents();
				for (int i = 0; i < pars.size(); i++) {
					Type parType = pars.get(i);
					if (!this.parents.contains(parType.getName())) {
//						boolean res = 
						tg.getTypeSet().removeInheritanceRelation(myType, parType);
					}
				}
				// now reset parent
				for (int i = 0; i < this.parents.size(); i++) {
					String pn = this.parents.get(i);
					Type t = tg.getTypeSet().getTypeByName(pn);
					if (t == myType)
						continue;
					List<Node> vec = tg.getNodes(t);
					if (vec != null) {
						for (int j = 0; j < vec.size(); j++) {
							Node parNode = vec.get(j);
							if (parNode.getType().getName().equals(pn)) {
//								TypeError error = 
								tg.getTypeSet().addInheritanceRelation(myType,
												parNode.getType());
								// System.out.println("add parent : "+pn);
								break;
							}
						}
					}
				}
			}

			// now children
			// System.out.println("NodeReprData.restoreParentsAndChildren::
			// children: "+children);
			for (int i = 0; i < this.children.size(); i++) {
				String cn = this.children.get(i);
				Type t = tg.getTypeSet().getTypeByName(cn);
				if (t == myType)
					continue;
				List<Node> vec = tg.getNodes(t);
				if (vec != null) {
					for (int j = 0; j < vec.size(); j++) {
						Node childNode = vec.get(j);
						if (childNode.getType().getName().equals(cn)) {
//							TypeError error = 
							tg.getTypeSet().addInheritanceRelation(
											childNode.getType(), myType);
						}
					}
				}
			}
		}
	}

	private void restoreMultiplicity(EdNode n, TypeReprData typedata) {
		n.getBasisNode().getType()
				.setSourceMin(typedata.srcMinMultiplicity);
		n.getBasisNode().getType()
				.setSourceMax(typedata.srcMaxMultiplicity);
	}

	private EdType findNodeType(EdGraph g, EdType tmpType) {
		EdType t = tmpType;
		Vector<EdType> nodeTypes = g.getTypeSet().getNodeTypes();
		for (int i = 0; i < nodeTypes.size(); i++) {
			EdType ti = nodeTypes.get(i);
			if (ti == t)
				return t;
			
			if (ti.getContextUsage().indexOf(
						String.valueOf(this.typeRepresentation
								.getTypeHashCode())) >= 0) {
				return ti;
			}		
		}
		return null;
	}

	protected EdNode createNodeFromNodeRepr(EdGraph g) {
		EdType type = findNodeType(g, this.typeRepresentation.getTypeHashCode()); 
		if (type == null) {
			type = this.typeRepresentation.createTypeFromTypeRepr();
			type = findNodeType(g, type);
		}
		if (type == null) {
			return null;
		}
		
		if (this.elemOfTG != g.getBasisGraph().isTypeGraph()) {
			return null;
		}
		
		EdNode n = null;
		try {
			Node basis = g.getBasisGraph().createNode(type.getBasisType());
			n = g.addNode(basis, type);

			n.addContextUsage(this.nodeHC);

			if (n.isElementOfTypeGraph()) {
				restoreParentsAndChildren(n);
				restoreMultiplicity(n, this.typeRepresentation);
			}

			refreshAttributes(n);

			n.setX(this.location.x);
			n.setY(this.location.y);

			n.getLNode().setFrozen(this.frozen);
			n.getLNode().setFrozenByDefault(this.frozenAsDefault);			
		} catch (TypeException ex) {}
		
		return n;
	}

	private void refreshAttributes(EdNode n) {
		if (!this.attributes.isEmpty()) {
			if (n.getBasisObject().getAttribute() == null)
				n.getBasisObject().createAttributeInstance();

			Hashtable<String, Pair<String,String>> attrs = new Hashtable<String, Pair<String,String>>();
			attrs.putAll(this.attributes);
			restoreAttributes(attrs, n);
		}
	}

	private void restoreAttributes(Hashtable<String, Pair<String,String>> attrs,
			EdNode n) {
		ValueTuple vt = (ValueTuple) n.getBasisObject().getAttribute();
		for (int i = 0; i < vt.getNumberOfEntries(); i++) {
			ValueMember vm = vt.getValueMemberAt(i);
			Pair<String,String> valPair = attrs.get(vm.getName());
			if (valPair != null && !valPair.isEmpty()) {
//				System.out.println("restored::  "+valPair.first+" , "+valPair.second);
//				String tname = valPair.first;
				String expr = valPair.second;
				if (!n.isElementOfTypeGraph()) {
					if (expr != null) {
						if (vm.getExpr() == null) {
							if (!expr.equals("NULL")) {
								vm.setExprAsText(expr);
//								 System.out.println("NodeReprData.restoreAttributes::  "+ vm.getName()+"  =  "+(String)expr);
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
//		System.out.println(this.typeRepresentation.name);
//	}
}
