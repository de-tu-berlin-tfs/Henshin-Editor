package agg.xt_basis;

import java.util.HashSet;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

import agg.attribute.impl.ValueTuple;
import agg.util.Change;

public class UndirectedGraph extends Graph {

	public UndirectedGraph(TypeSet aTypeSet) {
		this.itsTypes = aTypeSet;
	}
	
	/**
	 * Creates an empty graph with the specified TypeSet.
	 * 
	 * @param aTypeSet
	 *            the TypeSet to use
	 * @param completeGraph
	 *            true, to create a host graph
	 */
	public UndirectedGraph(TypeSet aTypeSet, boolean completeGraph) {
		super(aTypeSet, completeGraph);
	}
	
	
	/**
	 * Returns an error if the type multiplicity check failed after an edge of
	 * the specified type would be created, otherwise - null.
	 */
	public TypeError canCreateArc(
			final Type edgeType, 
			final Node src,
			final Node tar, 
			int currentTypeGraphLevel) {
		// check src->tar already exists
		TypeError error = this.itsTypes.canCreateArc(
				this, edgeType, src, tar, currentTypeGraphLevel);
		// check tar->src already exists
		if (error == null)
			error = this.itsTypes.canCreateArc(
				this, edgeType, tar, src, currentTypeGraphLevel);

		return error;
	}
	
	/**
	 * Checks if the specified edge to create is allowed.
	 */
	public TypeError checkConnectValid(Type edgeType, Node src, Node tar) {
		if (this.itsTypes.getTypeGraph() == null
				|| this.itsTypes.getLevelOfTypeGraphCheck() == TypeSet.DISABLED
				|| this.itsTypes.getLevelOfTypeGraphCheck() == TypeSet.ENABLED_INHERITANCE) {
			if (isParallelArcAllowed(edgeType, src, tar)) {
				return null;
			}

			return new TypeError(TypeError.NO_PARALLEL_ARC,
					"No parallel edges allowed");
		}
		
		Arc typearc = this.itsTypes.getTypeGraphArc(edgeType, src.getType(), tar.getType());
		if (typearc == null)
			typearc = this.itsTypes.getTypeGraphArc(edgeType, tar.getType(), src.getType());
		
		if (typearc != null) {
			if (isParallelArcAllowed(edgeType, src, tar)) {
				return null;
			}
			
			return new TypeError(TypeError.NO_PARALLEL_ARC,
						"No parallel edges allowed");
		}
		
		return new TypeError(TypeError.NO_SUCH_TYPE,
					"The edge of the type \"" + edgeType.getName()
							+ "\" is not allowed between node type \""
							+ src.getType().getName() + "\"  and  \""
							+ tar.getType().getName() + "\".");
	}
	
	public boolean isParallelArcAllowed(Type edgeType, Node src, Node tar) {
		if (this.itsTypes.isArcParallel()
				|| (src.getOutgoingArc(edgeType, tar) == null
						&& tar.getOutgoingArc(edgeType, src) == null))
			return true;
		else 
			return false;
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
			throw new TypeException("UndirectedGraph.createArc:: Cannot create an UndirectedArc of type : "+type.getStringRepr()+"  Source or target is not a Node!");
		}
		
		Type t = null;
		if (this.itsTypes.containsType(type))
			t = type;
		if (t == null) {
			t = this.itsTypes.getSimilarType(type);
			if (t == null)
				t = this.itsTypes.addType(type);
			if (t.getAdditionalRepr().indexOf("[EDGE]") == -1)
				t.setAdditionalRepr("[EDGE]");
		}

		TypeError typeError = this.checkConnectValid(t, src, tar);
		if (typeError != null) {
			throw new TypeException(typeError);
		}
		
		UndirectedArc anArc = new UndirectedArc(t, src, tar, this);
		
		// if this is not a type graph, so check this graph
		// against its type graph
		typeError = this.itsTypes.checkType(anArc, this.isCompleteGraph());
		if (typeError != null) {
			((Node)anArc.getSource()).removeOut(anArc);
			((Node)anArc.getTarget()).removeOut(anArc);
			throw new TypeException(typeError);
		}
				
		this.attributed = this.attributed || anArc.getAttribute() != null;		
		this.itsArcs.add(anArc);		
		addToTypeObjectsMap(anArc);				
		this.changed = true;		
		propagateChange(new Change(Change.OBJECT_CREATED, anArc));
		
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
	 * Creates and adds a new arc.
	 */
	protected Arc newArc(Type t, Node src, Node tar) throws TypeException {		
		TypeError typeError = this.checkConnectValid(t, src, tar);
		if (typeError != null) {
			throw new TypeException(typeError);
		}
		
		UndirectedArc anArc = new UndirectedArc(t, src, tar, this);
//		check for type mismatches, also multiplicity max of source and target
		typeError = this.itsTypes.checkType(anArc, this.isCompleteGraph());
		if (typeError != null) {
			((Node)anArc.getSource()).removeOut(anArc);
			((Node)anArc.getTarget()).removeOut(anArc);
			throw new TypeException(typeError);
		}
		
		this.attributed = this.attributed || anArc.getAttribute() != null;		
		this.itsArcs.add(anArc);		
		addToTypeObjectsMap(anArc);				
		this.changed = true;
		propagateChange(new Change(Change.OBJECT_CREATED, anArc));		
		
		return anArc;
	}

	protected Arc newArcFast(Type t, Node src, Node tar) {
//		long time = System.nanoTime();
		UndirectedArc anArc = new UndirectedArc(t, src, tar, this);		
		this.attributed = this.attributed || anArc.getAttribute() != null;		
		this.itsArcs.add(anArc);		
		addToTypeObjectsMap(anArc);				
		this.changed = true;
		propagateChange(new Change(Change.OBJECT_CREATED, anArc));		
//		System.out.println("Arc created  in: "+(System.nanoTime()-time)+"nano");
		return anArc;
	}

	/**
	 * Adds the specified edge to my edges. The type of the specified edge has
	 * to be in my type set.<br>
	 * The edge must be an instance of <code>UndirectedArc</code>.
	 */
	public void addArc(Arc anArc) {
		if (anArc instanceof UndirectedArc
				&& !this.itsArcs.contains(anArc)) {
			this.itsArcs.add(anArc);
			addToTypeObjectsMap(anArc);						
			this.attributed = this.attributed || anArc.getAttribute() != null;			
			this.changed = true;
		}
	}
	
	protected void addToTypeObjectsMap(GraphObject anObj) {
		if (anObj.isNode()) {	
			this.extendTypeObjectsMapByNode((Node)anObj);
		} else {
			this.extendTypeObjectsMapByArc((Arc)anObj);
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
					String keystr2 = tarParents.get(j).convertToKey()
								+ arc.getType().convertToKey()
								+ srcParents.get(i).convertToKey();					
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
						String keystr2 = tarParents.get(j).convertToKey()
										+ arc.getType().convertToKey()
										+ srcParents.get(i).convertToKey();
						
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
	
	/** Returns <code>true</code> if this graph uses the specified type. */
	public boolean isUsingType(GraphObject t) {
		if (t.isArc()) {
			boolean hasTypeGraphArc = this.getTypeSet().getTypeGraphArc(
					t.getType(), ((Arc) t).getSource().getType(), 
					((Arc) t).getTarget().getType()) != null ? true : false;
			
			Iterator<Arc> iter = this.itsArcs.iterator();
			while (iter.hasNext()) {
				Arc o = iter.next();
				if (hasTypeGraphArc) {
					if (o.getType().compareTo(t.getType())) {
						if (((o.getSource().getType().compareTo(((Arc) t)
									.getSource().getType())) || (o.getSource()
									.getType().isChildOf(((Arc) t).getSource()
									.getType())))
								&& ((o.getTarget().getType().compareTo(((Arc) t)
									.getTarget().getType())) || (o.getTarget()
									.getType().isChildOf(((Arc) t).getTarget()
									.getType())))) 
							return true;
						else if (((o.getTarget().getType().compareTo(((Arc) t)
									.getSource().getType())) || (o.getTarget()
									.getType().isChildOf(((Arc) t).getSource()
									.getType())))
								&& ((o.getSource().getType().compareTo(((Arc) t)
									.getTarget().getType())) || (o.getSource()
									.getType().isChildOf(((Arc) t).getTarget()
									.getType())))) 
							return true;							
					}
				} 
				else if (o.getType().compareTo(t.getType())) {
					return true;
				}
			}
			
		} else {
			while (this.itsNodes.iterator().hasNext()) {
				Node o = this.itsNodes.iterator().next();
				if (o.getType().compareTo(t.getType()))
					return true;
				else if (o.getType().isChildOf(t.getType()))
					return true;
			}
		}
		return false;
	}

}
