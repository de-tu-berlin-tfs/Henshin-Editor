package agg.xt_basis.csp;

import java.util.BitSet;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Stack;
import java.util.Vector;

import agg.attribute.AttrVariableTuple;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.MorphCompletionStrategy;
import agg.xt_basis.NACStarMorphism;
import agg.xt_basis.OrdinaryMorphism;

//import com.objectspace.jgl.Deque;

/** 
 * Simple Backtracking implementation of morphism completion. 
 * 
 *  @deprecated not used anymore
 */
public class Completion_SimpleBT extends MorphCompletionStrategy {

	private boolean itsInjectiveFlag;

	private boolean initialized;

//	private Vector<GraphObject> domain;

	public Completion_SimpleBT(int i) {
		// no properties supported:
		super(new BitSet(1));
		this.itsInjectiveFlag = false;
		this.itsName = "Simple BT";
	}

	public Completion_SimpleBT(boolean injective, int i) {
		// no properties supported:
		super(new BitSet(1));
		this.itsInjectiveFlag = injective;
		this.itsName = "Simple BT";
	}

	public final void initialize(OrdinaryMorphism morph) {
		this.itsMorphism = morph;
		this.itsStack = new Stack<StackItem>();
//		this.itsObjectsToMap = new Deque();
		this.itsObjectsToMap = new Vector<GraphObject>();
		this.itsState = START;
//		itsPreviousCompletion = null;

		Iterator<?> iter = this.itsMorphism.getOriginal().getNodesSet().iterator();
		while (iter.hasNext()) {
			GraphObject obj = (GraphObject) iter.next();
			if (this.itsMorphism.getImage(obj) == null) {
//				this.itsObjectsToMap.pushBack(obj);
				this.itsObjectsToMap.add(obj);
			}
		}
		iter = this.itsMorphism.getOriginal().getArcsSet().iterator();
		while (iter.hasNext()) {
			GraphObject obj = (GraphObject) iter.next();
			if (this.itsMorphism.getImage(obj) == null) {
//				this.itsObjectsToMap.pushBack(obj);
				this.itsObjectsToMap.add(obj);
			}
		}
		this.initialized = true;
	}

	public void setPartialMorphism(OrdinaryMorphism morph) {
		if (!this.initialized)
			initialize(morph);
		else {
			this.itsMorphism = morph;
			this.itsStack.clear();
			this.itsObjectsToMap.clear();
			this.itsState = START;

			// System.out.println("Completion_SimpleBT.setPartialMorphism...");
			Iterator<?> iter = this.itsMorphism.getOriginal().getNodesSet().iterator();
			while (iter.hasNext()) {
				GraphObject obj = (GraphObject) iter.next();
				if (this.itsMorphism.getImage(obj) == null) {
//					this.itsObjectsToMap.pushBack(obj);
					this.itsObjectsToMap.add(obj);
				}
			}
			iter = this.itsMorphism.getOriginal().getArcsSet().iterator();
			while (iter.hasNext()) {
				GraphObject obj = (GraphObject) iter.next();
				if (this.itsMorphism.getImage(obj) == null) {
//					this.itsObjectsToMap.pushBack(obj);
					this.itsObjectsToMap.add(obj);
				}
			}
		}
	}

	public void resetTypeMap(Graph g) {
		refreshStack();
	}

	public void resetTypeMap(Hashtable<String, HashSet<GraphObject>> typeMap) {
		refreshStack();
	}

	public void resetVariableDomain(boolean instanceNull) {
		refreshStack();
	}

	private void refreshStack() {
		this.itsStack.clear();
		// domain.clear();
		// Enumeration en = this.itsMorphism.getTarget().getElements();
		// while(en.hasMoreElements()){
		// domain.add(en.nextElement());
		// }

		this.itsState = START;

		// this.itsObjectsToMap.clear();
		// Enumeration iter = this.itsMorphism.getOriginal().getElements();
		// while ( iter.hasMoreElements() ){
		// GraphObject obj = (GraphObject) iter.nextElement();
		// this.itsObjectsToMap.pushBack( obj );
		// }
		// System.out.println("Completion_SimpleBT.refreshStack()... DONE ");
	}

//	private void refreshObjectsMap() {
//		this.itsObjectsToMap.clear();
//		Iterator<?> iter = this.itsMorphism.getOriginal().getNodesSet().iterator();
//		while (iter.hasNext()) {
//			GraphObject obj = (GraphObject) iter.next();
//			this.itsObjectsToMap.pushBack(obj);
//		}
//		iter = this.itsMorphism.getOriginal().getArcsSet().iterator();
//		while (iter.hasNext()) {
//			GraphObject obj = (GraphObject) iter.next();
//			this.itsObjectsToMap.pushBack(obj);
//		}
//	}

	private final GraphObject nextMapping() {
		if (this.itsStack.empty()) {
			return null;
		}

		GraphObject image;
		GraphObject obj = this.itsStack.peek().object;
		Enumeration<?> iter = this.itsStack.peek().iter;
		// System.out.println("obj tp map : "+obj);
		while (iter.hasMoreElements()) {
			try {
				image = (GraphObject) iter.nextElement();
				// System.out.println("test image: "+image);
				Enumeration<GraphObject> e = this.itsMorphism.getInverseImage(image);
				GraphObject o = null;
				if (e.hasMoreElements())
					o = e.nextElement();
				// System.out.println("injective: "+this.itsInjectiveFlag+" inverse
				// image: "+o);
				if (!this.itsInjectiveFlag || (o == null)) {
					this.itsMorphism.addMapping(obj, image);
					if (this.itsMorphism.getImage(obj) != null) {
						// System.out.println("addMapping: "+ obj+" -->
						// "+this.itsMorphism.getImage(obj));
						return image;
					}
					continue;
				} 
				continue;
			} catch (BadMappingException exc) {
				continue;
			}
		}
		// System.out.println("Completion_SimpleBT: No mapping candidates!");
		return null;
	}

	public final void reset() {
	}

	public final boolean next(OrdinaryMorphism morph) {
		this.errorMsg = "";
		
		storeValueOfInputParameter(morph);

		if (morph != this.itsMorphism) {
			initialize(morph);
		}

		if (this.itsState == SUCCESS) {
			// savePreviousCompletion();
			this.itsState = MAP_NEXT;
		}

		GraphObject obj;
		while (true) {
			switch (this.itsState) {
			case START:
				if (this.itsMorphism.getOriginal().isEmpty())
					this.itsState = SUCCESS;
				else
					this.itsState = SELECT;
				break;

			case SELECT:
				obj = selectObjectToMap();
				if (obj == null)
					this.itsState = SUCCESS;
				else {
					this.itsStack.push(new StackItem(obj, getDomain() ) );
					this.itsState = MAP_NEXT;
				}
				break;

			case MAP_NEXT:
				obj = nextMapping();
				if (obj != null) {
					// remove the Mappings corresponding to the recent
					// backtracking steps:
					// Enumeration iter = this.itsObjectsToMap.elements();
					// for( itsPopNo; itsPopNo > 0; itsPopNo-- )
					// {
					// do_removeMapping( (GraphObject) iter.nextElement() );
					// }
					this.itsState = SELECT;
				} else {
					this.itsState = BACK;
				}
				restoreValueOfInputParameter(morph);
				
				break;

			case BACK:
				if (this.itsStack.size() > 1) {
					// this.itsObjectsToMap.pushFront( ((StackItem)
					// this.itsStack.pop()).object );
					// itsPopNo++;
					back();
					this.itsState = MAP_NEXT;
				} else {
					this.itsState = NO_MORE_COMPLETIONS;
				}
				break;

			case SUCCESS:
				if (!checkInputParameter(morph)) {
					this.itsState = MAP_NEXT;
					break;
				}
				if (!checkObjectsWithSameVariable(morph)) {
					this.itsState = MAP_NEXT;
					break;
				}
				if (!(morph instanceof NACStarMorphism)) {
					if (!checkAttrCondition(morph)) {
						this.itsState = MAP_NEXT;
						break;
					}
				}
				
				restoreValueOfInputParameter(morph);
				
				return true;

			case NO_MORE_COMPLETIONS:
				// if (itsPreviousCompletion != null)
				// restorePreviousCompletion();

				// refreshStack();
				// this.itsState = START;

				return false;

			default:
				// System.out.println("Completion_SimpleBT: Should have never
				// come here...");
			}
		}
	}

	private Enumeration<GraphObject> getDomain() {
		return this.itsMorphism.getImage().getElements();
		// return domain.elements();
	}

	private final void back() {
		GraphObject obj = this.itsStack.pop().object;
//		this.itsObjectsToMap.pushFront(obj);
		this.itsObjectsToMap.add(0, obj);
		if (this.itsMorphism.getImage(obj) != null)
			this.itsMorphism.removeMapping(obj);
	}

	private final GraphObject selectObjectToMap() {
		if (this.itsObjectsToMap.isEmpty())
			return null;
//		GraphObject obj = (GraphObject) this.itsObjectsToMap.popFront();
		GraphObject obj = this.itsObjectsToMap.get(0);
		this.itsObjectsToMap.remove(0);
		if (areReferencesMapped(obj))
			return obj;
		
//		this.itsObjectsToMap.pushBack(obj);
		this.itsObjectsToMap.add(obj);
		return selectObjectToMap();
	}

	private final boolean areReferencesMapped(GraphObject obj) {
		if (obj.isArc()) {
			return (this.itsMorphism.getImage(((Arc) obj).getSource()) != null && this.itsMorphism
					.getImage(((Arc) obj).getTarget()) != null);
		} 
		return true;
	}

	/*
	private final void savePreviousCompletion() {
		GraphObject obj;
		itsPreviousCompletion = new Vector<GraphObject>(2 * this.itsStack.size());
		for (int i = 0; i < this.itsStack.size(); i++) {
			obj = this.itsStack.elementAt(i).object;
			itsPreviousCompletion.addElement(obj);
			itsPreviousCompletion.addElement(this.itsMorphism.getImage(obj));
		}
	}

	private final void restorePreviousCompletion() {
		for (int i = 0; i < itsPreviousCompletion.size();) {
			this.itsMorphism.addMapping(itsPreviousCompletion.elementAt(i++),
					itsPreviousCompletion.elementAt(i++));
		}
	}
*/
	
	/*
	 * first save values of attr. context variables used as input parameters,
	 * otherwise its will be overwritten when next() done.
	 */
	private void storeValueOfInputParameter(OrdinaryMorphism morph) {
		this.mapInputParameter = new HashMap<Integer, String>(1);
		AttrVariableTuple avt = morph.getAttrContext().getVariables();
		if (avt != null) {
			int num = avt.getSize();
			for (int i = 0; i < num; i++) {
				VarMember var = avt.getVarMemberAt(i);
				if (var.isInputParameter()) {
					String val = var.getExprAsText();
					if (val != null) {
						Integer key = new Integer(i);
						this.mapInputParameter.put(key, val);
						// System.out.println("Store input param:
						// "+var.getName()+" :: " +var);
					}
				}
			}
		}
	}

	/*
	 * restore values of attr. context variables used as input parameters
	 */
	private void restoreValueOfInputParameter(OrdinaryMorphism morph) {
		if (morph.getAttrContext() == null) {
			return;
		}
		AttrVariableTuple avt = morph.getAttrContext().getVariables();
		if (!this.mapInputParameter.isEmpty()) {
			Iterator<Integer> iter = this.mapInputParameter.keySet().iterator();
			while (iter.hasNext()) {
				Integer key = iter.next();
				String val = this.mapInputParameter.get(key);
				VarMember var = avt.getVarMemberAt(key.intValue());
				String val1 = var.getExprAsText();
				if (!val.equals(val1)) {
					// System.out.println(((VarTuple)
					// avt).getTupleType().getNameAsString(key.intValue())+" ::
					// "+var);
					var.setExprAsText(val);
					((ValueMember) var).checkValidity();
					// System.out.println("INPUT PARAMETER RESET :");
					// System.out.println(((VarTuple)
					// avt).getTupleType().getNameAsString(key.intValue())+" ::
					// "+var);
				}
			}
		}
	}

	private boolean checkInputParameter(OrdinaryMorphism morph) {
		AttrVariableTuple avt = morph.getAttrContext().getVariables();
		if (!this.mapInputParameter.isEmpty()) {
			Iterator<Integer> iter = this.mapInputParameter.keySet().iterator();
			while (iter.hasNext()) {
				Integer key = iter.next();
				String val = this.mapInputParameter.get(key);
				VarMember var = avt.getVarMemberAt(key.intValue());
				if (var.isInputParameter()) {
					String val1 = var.getExprAsText();
					// System.out.println("InputParameter: "+var.getName()+"
					// "+var.getMark()+" "+val1);
					if (!(morph instanceof NACStarMorphism)) {
						if ((val1 != null) && (var.getMark() != VarMember.NAC)
								&& (var.getMark() != VarMember.PAC)
								&& !val.equals(val1)) {
							this.errorMsg = "Value of the input parameter  [ "
									+ var.getName() + " ] not found.";
							return false;
						}
					} else {
						if ((val1 != null) && (var.getMark() == VarMember.NAC)
								&& !val.equals(val1)) {
							this.errorMsg = "Value of the input parameter  [ "
									+ var.getName() + " ] not found.";
							return false;
						}
					}
				}
			}
		}
		return true;
	}

	private boolean checkObjectsWithSameVariable(OrdinaryMorphism morph) {
		// System.out.println("Completion_CSP.checkObjectsWithSameVariable");
		VarTuple variables = (VarTuple) morph.getAttrContext().getVariables();
		for (int i = 0; i < variables.getSize(); i++) {
			VarMember var = variables.getVarMemberAt(i);
			Vector<Pair<GraphObject, String>> v = new Vector<Pair<GraphObject, String>>();
			Iterator<?> iter = morph.getOriginal().getNodesSet().iterator();
			while (iter.hasNext()) {
				GraphObject orig = (GraphObject) iter.next();
				if (orig.getAttribute() == null)
					continue;
				ValueTuple origVal = (ValueTuple) orig.getAttribute();
				for (int k = 0; k < origVal.getSize(); k++) {
					ValueMember mem = origVal.getValueMemberAt(k);
					if (mem.isSet()
							&& mem.getExpr().isVariable()
							&& mem.getExprAsText().equals(var.getName())
							&& mem.getDeclaration().getTypeName().equals(
									var.getDeclaration().getTypeName())) {
						v
								.add(new Pair<GraphObject, String>(orig, mem
										.getName()));
						// System.out.println(var.getName()+" "+var);
					}
				}
			}
			iter = morph.getOriginal().getArcsSet().iterator();
			while (iter.hasNext()) {
				GraphObject orig = (GraphObject) iter.next();
				if (orig.getAttribute() == null)
					continue;
				ValueTuple origVal = (ValueTuple) orig.getAttribute();
				for (int k = 0; k < origVal.getSize(); k++) {
					ValueMember mem = origVal.getValueMemberAt(k);
					if (mem.isSet()
							&& mem.getExpr().isVariable()
							&& mem.getExprAsText().equals(var.getName())
							&& mem.getDeclaration().getTypeName().equals(
									var.getDeclaration().getTypeName())) {
						v
								.add(new Pair<GraphObject, String>(orig, mem
										.getName()));
						// System.out.println(var.getName()+" "+var);
					}
				}
			}
			if (v.size() > 1) {
				Pair<GraphObject, String> p = v.elementAt(0);
				GraphObject img = morph.getImage(p.first);
				ValueTuple val = (ValueTuple) img.getAttribute();
				ValueMember mem = val.getValueMemberAt(p.second);
				for (int j = 1; j < v.size(); j++) {
					Pair<GraphObject, String> pj = v.elementAt(j);
					GraphObject imgj = morph.getImage(pj.first);
					ValueTuple valj = (ValueTuple) imgj.getAttribute();
					ValueMember memj = valj.getValueMemberAt(pj.second);
					// System.out.println(mem.getExprAsText()+" == ?
					// "+memj.getExprAsText());
					if (!mem.getExprAsText().equals(memj.getExprAsText())) {
						this.errorMsg = "Attribute match is failed.";
						return false;
					}
				}
			}
		}
		return true;
	}

	private boolean checkAttrCondition(OrdinaryMorphism morph) {
		// System.out.println("Completion_SimpleBT.checkAttrCondition:: ");
		CondTuple conds = (CondTuple) morph.getAttrContext().getConditions();
		for (int i = 0; i < conds.getSize(); i++) {
			CondMember cond = conds.getCondMemberAt(i);
			if (!cond.isDefinite()) {
				// System.out.println("Completion_SimpleBT.checkAttrCondition::
				// "+cond.getExprAsText()+" NOT Definite --> TRUE");
				// ((VarTuple)morph.getAttrContext().getVariables()).showVariables();
			} else if (cond.isTrue()) {
				// System.out.println("Completion_SimpleBT.checkAttrCondition::
				// "+cond.getExprAsText()+" TRUE");
				// ((VarTuple)morph.getAttrContext().getVariables()).showVariables();
			} else {
				this.errorMsg = "Attribute condition  [ " + cond.getExprAsText()
						+ " ]  failed.";
				// System.out.println("Completion_SimpleBT : "+this.errorMsg);
				// ((VarTuple)morph.getAttrContext().getVariables()).showVariables();
				return false;
			}
		}
		return true;
	}

	public String getErrorMsg() {
		return this.errorMsg;
	}
	
	
	// ---- member variables ------------------------
	private OrdinaryMorphism itsMorphism;

//	private Deque itsObjectsToMap;
	private Vector<GraphObject> itsObjectsToMap;

	private Stack<StackItem> itsStack;

	private int itsState = START;

//	private Vector<GraphObject> itsPreviousCompletion;

	private HashMap<Integer, String> mapInputParameter = new HashMap<Integer, String>(
			1);

	private String errorMsg;

	// constants for morphism completion state machine:
	private final static int START = 1;

	private final static int SELECT = 2;

	private final static int MAP_NEXT = 3;

	private final static int BACK = 4;

	private final static int SUCCESS = 5;

	private final static int NO_MORE_COMPLETIONS = 6;

}

class StackItem {
	protected StackItem(GraphObject obj, Enumeration<GraphObject> iter) {
		this.object = obj;
		this.iter = iter;
	}

	protected Enumeration<GraphObject> iter;

	protected GraphObject object;
}
