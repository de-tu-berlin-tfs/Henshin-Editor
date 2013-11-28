package agg.xt_basis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

import agg.attribute.impl.ValueTuple;
import agg.util.Change;

public class UndirectedTypeGraph extends TypeGraph {

	public UndirectedTypeGraph(TypeSet aTypeSet) {
		super(aTypeSet);
		
	}

	
	/**
	 * Returns an error if the type multiplicity check failed after an edge of
	 * the specified type would be created, otherwise - null.
	 */
	public TypeError canCreateArc(
			final Type edgeType, 
			final Node source,
			final Node target, 
			int currentTypeGraphLevel) {
		
		TypeError error = this.itsTypes.canCreateArc(
				this, edgeType, source, target, currentTypeGraphLevel);
		if (error != null)
			error = this.itsTypes.canCreateArc(
						this, edgeType, target, source, currentTypeGraphLevel);

		return error;
	}
	
	/**
	 * Checks if the specified edge to create is allowed.
	 */
	public TypeError checkConnectValid(Type edgeType, Node source, Node target) {
		if (this.itsTypes.getTypeGraph() == null
				|| this.itsTypes.getLevelOfTypeGraphCheck() == TypeSet.DISABLED
				|| this.itsTypes.getLevelOfTypeGraphCheck() == TypeSet.ENABLED_INHERITANCE)
			return null;
		
		Arc typearc = this.itsTypes.getTypeGraphArc(edgeType, source.getType(), target.getType());
		if (typearc == null)
			typearc = this.itsTypes.getTypeGraphArc(edgeType, target.getType(), source.getType());
		
		if (typearc != null)
			return null;
		
		return new TypeError(TypeError.NO_SUCH_TYPE,
					"The edge of the type \"" + edgeType.getName()
							+ "\" is not allowed between node type \""
							+ source.getType().getName() + "\"  and  \""
							+ target.getType().getName() + "\".");
	}
	
	//???
	public TypeError checkNodeRequiresArc(final int actTypeGraphLevel) {
		if (this.itsTypes.getTypeGraph() == null
				|| actTypeGraphLevel != TypeSet.ENABLED_MAX_MIN)
			return null;
		
		Iterator<Node> iter = this.itsNodes.iterator();
		while (iter.hasNext()) {
			Node n = iter.next();			
			List<String> list = this.itsTypes.nodeRequiresArc(n);			
			if (list != null && !list.isEmpty()) {
				return new TypeError(TypeError.TO_LESS_ARCS,
						"Node type  " 
						+ "\""+n.getType().getName()+ "\" \n"
						+ "requires edge(s) of type: \n" 
						+ list.toString(), n.getType());
			}

		}
		return null;
	}
	
	/**
	 * Creates and add a new UndirectedArc of the specified type, source and target nodes,
	 *  which must be part of this graph.
	 */
	public Arc createArc(Type type, Node src, Node tar) throws TypeException {
		if (src == null || tar == null) {
			throw new TypeException("UndirectedGraph.createArc:: Cannot create an UndirectedArc of type : "+type.getStringRepr()+"   Source or target node is null!");
		} else if (!this.isNode(src) || !this.isNode(tar)) {
			throw new TypeException("UndirectedGraph.createArc:: Cannot create an UndirectedArc of type : "+type.getStringRepr()+"  Source or target does not belong to this graph!");
		}
		
		Type t = null;
		if (this.itsTypes.containsType(type))
			t = type;
		if (t == null) {
			t = this.itsTypes.getSimilarType(type);
			if (t == null)
				t = this.itsTypes.addType(type);		
		}

//		TypeError typeError = this.checkConnectValid(t, src, tar);
//		if (typeError != null) {
//			throw new TypeException(typeError);
//		}
		
		Arc anArc = newArc(t, src, tar);
		return anArc;
	}
	
	/**
	 * Creates a new UndirectedArc as a copy of the <code>orig</code>. 
	 * Only its type and attributes are copied, 
	 * the structural context (source, target) - is not.
	 * The specified source <code>src</code> and target <code>tar</code> objects 
	 * must be a part of this graph, but this is not checked here.
	 */
	public Arc copyArc(final Arc orig, final Node src, final Node tar) throws TypeException {
		UndirectedArc arc = null;
		try {
			arc = (UndirectedArc)this.createArc(orig.getType(), src, tar);
			if (arc != null) {
				arc.setObjectName(orig.getObjectName());			
				
				if (orig.getAttribute() != null) {
					arc.createAttributeInstance();
					((ValueTuple) arc.getAttribute()).copyEntries(orig
								.getAttribute());
				}				
			} else {
				throw new TypeException("Graph.copyArc:: Cannot create an UndirectedArc of type : "
						+orig.getType().getName());
			}	
		} catch (TypeException ex) {
			if (src != null && tar != null) {
				throw new TypeException("   "
					+orig.getType().getName()
					+" from  "+src.getType().getName()
					+" to  "+tar.getType().getName()
					+"   "+ex.getLocalizedMessage());
			} 
			throw new TypeException(ex.getLocalizedMessage());
			
		}
		return arc;	
	}
	
	/**
	 * Create a new Arc with given Type, source and target objects. Source and
	 * target object must be part of this graph.
	 */
	protected Arc newArc(final Type t, final Node src, final Node tar) throws TypeException {
		final UndirectedArc anArc = new UndirectedArc(t, src, tar, this);
		TypeError typeError = this.itsTypes.addTypeGraphObject(anArc);
		if (typeError != null) {
			anArc.dispose();
			throw new TypeException(typeError);
		}

		this.attributed = this.attributed || anArc.getAttribute() != null;		
		this.itsArcs.add(anArc);
		addArcToTypeObjectsMap(anArc);		
		this.changed = true;
		if(this.notificationRequired)
			propagateChange(new Change(Change.OBJECT_CREATED, anArc));
		return anArc;
	}
	
	
	protected Arc newArcFast(Type t, Node src, Node tar) {
		try {
			return this.newArc(t, src, tar);
		} catch (TypeException ex) {
			return null;}
	}
	
	/**
	 * Returns the type graph edge of the specified type <code>t</code>, with
	 * a source node of the specified type <code>source</code> and a target
	 * node of the specified type <code>target</code>, otherwise returns
	 * <code>null</code>.
	 */
	public Arc getTypeGraphArc(final Type t, final Type source, final Type target) {
		Iterator<Arc> arcs = this.itsArcs.iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (a.getType().compareTo(t)) {
				if (a.getSource().getType().isParentOf(source)
						&& a.getTarget().getType().isParentOf(target)) 
					return a;
				else if (a.getSource().getType().isParentOf(target)
						&& a.getTarget().getType().isParentOf(source)) 
					return a;
			}
		}
		return null;
	}
	
	protected void removeArc(final Arc a) {
		 if (a.getContext() == this) {
			// remove arc from its source / target
			((Node)a.getSource()).removeOut(a);
			((Node)a.getTarget()).removeOut(a);

			for (int i = 0; i < this.itsUsingMorphs.size(); i++) {
				this.itsUsingMorphs.get(i).removeMapping(a);
			}

			this.itsArcs.remove(a);						
			removeArcFromTypeObjectsMap(a);
			this.changed = true;
		}
	}
	
	protected void removeArcFromTypeObjectsMap(final Arc arc) {
		if (arc.getSource() != null
				&& arc.getTarget() != null) {
						
			if (arc.getSource().getType().hasParent()
					|| arc.getTarget().getType().hasParent()) {
											
				Vector<Type> srcParents = arc.getSource().getType().getAllParents();
				Vector<Type> tarParents = arc.getTarget().getType().getAllParents();
				
				for (int i = 0; i < srcParents.size(); ++i) {
					for (int j = 0; j < tarParents.size(); ++j) {
						String keystr = srcParents.get(i).convertToKey()
										+ arc.getType().convertToKey()
										+ tarParents.get(j).convertToKey();
						String keystr2 = tarParents.get(i).convertToKey()
										+ arc.getType().convertToKey()
										+ srcParents.get(j).convertToKey();
						
						HashSet<GraphObject> objSet = this.itsTypeObjectsMap.get(keystr);
						if (objSet == null) {
							// look for inverse arc key					
							objSet = this.itsTypeObjectsMap.get(keystr2);
						}
						if (objSet != null) {
							objSet.remove(arc);
						}
					}
				}
			} else {
				String keystr = arc.convertToKey();
				String keystr2 = ((UndirectedArc)arc).convertToInverseKey();
				HashSet<GraphObject> objSet = this.itsTypeObjectsMap.get(keystr);
				if (objSet == null) {
					// look for inverse arc key
					objSet = this.itsTypeObjectsMap.get(keystr2);
				}
				if (objSet != null) {
					objSet.remove(arc);
				}
			}
		}
	}
	
	protected void extendTypeObjectsMapByArc(final Arc arc) {		
		if (this.itsTypes.hasInheritance()
				&& arc.getSource().getType().hasParent()
						|| arc.getTarget().getType().hasParent()) {
			Vector<Type> srcParents = arc.getSource().getType().getAllParents();
			Vector<Type> tarParents = arc.getTarget().getType().getAllParents();			
			for (int i = 0; i < srcParents.size(); ++i) {				
				for (int j = 0; j < tarParents.size(); ++j) {					
					String keystr = srcParents.get(i).convertToKey()
								+ arc.getType().convertToKey()
								+ tarParents.get(j).convertToKey();
					String keystr2 = tarParents.get(i).convertToKey()
								+ arc.getType().convertToKey()
								+ srcParents.get(j).convertToKey();					
					HashSet<GraphObject> objSet = this.itsTypeObjectsMap.get(keystr);
					if (objSet == null) {
						// look for inverse arc key					
						objSet = this.itsTypeObjectsMap.get(keystr2);
					}
					if (objSet == null) {
						objSet = new LinkedHashSet<GraphObject>();
						this.itsTypeObjectsMap.put(keystr, objSet);
					}
					objSet.add(arc);
				}
			}
		} else {
			String keystr = arc.convertToKey();
			String keystr2 = ((UndirectedArc)arc).convertToInverseKey();
			HashSet<GraphObject> objSet = this.itsTypeObjectsMap.get(keystr);
			if (objSet == null) {
				// look for inverse arc key
				objSet = this.itsTypeObjectsMap.get(keystr2);
			}
			if (objSet == null) {
				objSet = new LinkedHashSet<GraphObject>();
				this.itsTypeObjectsMap.put(keystr, objSet);						
			} 
			objSet.add(arc);
		}						
	}
	
}
