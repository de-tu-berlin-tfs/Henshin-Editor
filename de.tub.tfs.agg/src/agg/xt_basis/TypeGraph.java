package agg.xt_basis;

import java.util.Enumeration;
import java.util.HashSet;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Vector;

import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.util.Change;
import agg.util.Pair;
import agg.util.XMLHelper;

public class TypeGraph extends Graph {


	/**
	 * Creates an empty type graph for the specified type set.
	 */
	public TypeGraph(TypeSet aTypeSet) {
		super(aTypeSet);
		this.completeGraph = false;
		this.kind = GraphKind.TG;
	}

	/**
	 * prepare this graph for garbage collection. so cut all connections to
	 * other objects and dispose all graph object contained.
	 */
	public void dispose() {	
		this.observer.removeAllElements();		
		this.itsTypes.setLevelOfTypeGraph(TypeSet.DISABLED);
		Iterator<?> iter = this.itsArcs.iterator();
		while (iter.hasNext()) {
			try {
				this.destroyArc((Arc)iter.next(), false, false);
				iter = this.itsArcs.iterator();
			} catch (TypeException e) {
				System.out.println("TypeGraph.dispose:: destroyArc  FAILED!:  "+e.getMessage());
			}
		}
		iter = this.itsNodes.iterator();
		while (iter.hasNext()) {
			try {
				this.destroyNode((Node)iter.next(), false, false);
				iter = this.itsNodes.iterator();
			} catch (TypeException e) {
				System.out.println("TypeGraph.dispose:: destroyNode  FAILED!:  "+e.getMessage());
			}
		}
		super.dispose();	
	}
	
	public void finalize() {
	}
	
		
	/**
	 * Only new type nodes are created for the node types inside of the
	 * specified types. The types should be a subset of the type set of this
	 * type graph.
	 * 
	 * @param types
	 *            is a subset of my type set.
	 */
	public void tryToExtendByTypeNodes(final Vector<Type> types) {
		for (int i = 0; i < types.size(); i++) {
			Type t = types.get(i);
			if (!this.itsTypes.containsType(t))
				continue;

			if (!this.getElementsOfType(t).hasMoreElements()) {
				if (t.isNodeType()) {
					try {
						createNode(t);
					} catch (TypeException ex) {
					}
				} else if (t.isArcType()) {

				}
			}
		}
	}

	private void refreshGraph() {
		Iterator<?> iter = this.itsArcs.iterator();
		while (iter.hasNext()) {
			Arc o = (Arc) iter.next();
			if (o.getType() == null
					|| o.getSource() == null
					|| o.getTarget() == null) {
				this.removeArc(o);
				iter = this.itsArcs.iterator();
			}					
		}
		iter = this.itsNodes.iterator();
		while (iter.hasNext()) {
			Node o = (Node)iter.next();
			if (o.getType() == null) {
				this.removeNode(o);
				iter = this.itsNodes.iterator();
			}					
		}
	}
	
	
	/**
	 * Adds a copy of the specified Graph g. The Graph g should be an instance
	 * of TypeGraph. The type graph check of this type graph should be disabled.
	 * 
	 * @param g
	 *            another TypeGraph instance
	 * @return true if a copy of the type graph g was added, otherwise false.
	 */
	public boolean addCopyOfGraph(final Graph g) {
		synchronized (this) {
			if (g instanceof TypeGraph) {
				boolean failed = false;
				if (this.itsTypes.getLevelOfTypeGraphCheck() == TypeSet.DISABLED) {
					final Hashtable<Node, Node> memo1 = new Hashtable<Node, Node>(g
							.getSize());
					Iterator<Node> vtxList = g.getNodesSet().iterator();
					while (vtxList.hasNext()) {
						Node vtxOrig = vtxList.next();
						Node vtxCopy = null;
						Type type = this.itsTypes.getSimilarType(vtxOrig.getType());
						if (type == null) {
							type = this.itsTypes.getTypeByName(vtxOrig.getType()
									.getName());
							if (type != null && !type.isNodeType())
								type = null;
						}
						if (type != null) { 
							if (type.getTypeGraphNodeObject() == null) {					
								try {
									vtxCopy = this.newNode(type);
									
									if (vtxCopy != null) {
										// add inheritance edge
										for (int i = 0; i < vtxOrig.getType()
												.getParents().size(); i++) {
											Type parentOrig = vtxOrig.getType()
													.getParents().get(i);
											Type parent = this.itsTypes
													.getSimilarType(parentOrig);
											if (parent == null) {
												parent = this.itsTypes
														.getTypeByName(parentOrig
																.getName());
												if (parent != null
														&& !parent.isNodeType())
													parent = null;
											}
											if (parent != null)
												this.itsTypes.addInheritanceRelation(type,
														parent);
										}
										vtxCopy.copyAttributes(vtxOrig);
										vtxCopy.setContextUsage(vtxOrig
												.getContextUsage());
			
										memo1.put(vtxOrig, vtxCopy);
										if(this.notificationRequired)
											propagateChange(new Change(
												Change.OBJECT_CREATED, vtxCopy));
									} 
								} catch (TypeException e) {
									// type node already exists
									Node node = this.itsTypes.getTypeGraphNode(type);
									memo1.put(vtxOrig, node);
								}
							}
							else {
								memo1.put(vtxOrig, type.getTypeGraphNodeObject());
							}
						}
					}// while
					Iterator<Arc> arcList = g.getArcsSet().iterator();
					while (arcList.hasNext()) {
						Arc arcOrig = arcList.next();
						Arc arcCopy = null;
						Type type = this.itsTypes.getSimilarType(arcOrig.getType());
						if (type == null) {
							type = this.itsTypes.getTypeByName(arcOrig.getType()
									.getName());
							if (type != null && !type.isArcType())
								type = null;
						}
						if (type != null) {
							Node source = (Node) arcOrig.getSource();
							Node target = (Node) arcOrig.getTarget();
							Node srcImg = memo1.get(source);
							Node tgtImg = memo1.get(target);
							if (type.getTypeGraphArcObject(srcImg.getType(), tgtImg.getType()) == null) {
								if (type.getName().equals("next")) {
									if (srcImg.getType().getName().equals("Activity")
											&& tgtImg.getType().getName().equals("Activity")) {
										System.out.println("Activity - next - Activity");
									}
									else if (srcImg.getType().getName().equals("BusinessActivity")
											&& tgtImg.getType().getName().equals("BusinessActivity")) {
										System.out.println("BusinessActivity - next - BusinessActivity");
									}
								}
								try {						
									arcCopy = this.newArc(type, srcImg, tgtImg);
									if (arcCopy != null) {
										arcCopy.copyAttributes(arcOrig);
										arcCopy.setContextUsage(arcOrig
												.getContextUsage());
			
										Type srctype = arcCopy.getSource().getType();
										Type tartype = arcCopy.getTarget().getType();
										// check source multiplicity
										int m1 = arcOrig.getType().getSourceMin(
												arcOrig.getSource().getType(),
												arcOrig.getTarget().getType());
										type.setSourceMin(srctype, tartype, m1);
										m1 = arcOrig.getType().getSourceMax(
												arcOrig.getSource().getType(),
												arcOrig.getTarget().getType());
										type.setSourceMax(srctype, tartype, m1);
										m1 = arcOrig.getType().getTargetMin(
												arcOrig.getSource().getType(),
												arcOrig.getTarget().getType());
										type.setTargetMin(srctype, tartype, m1);
										m1 = arcOrig.getType().getTargetMax(
												arcOrig.getSource().getType(),
												arcOrig.getTarget().getType());
										type.setTargetMax(srctype, tartype, m1);
										if(this.notificationRequired)
											propagateChange(new Change(
												Change.OBJECT_CREATED, arcCopy));
									}
								} catch (TypeException e) {}
							}
						}
					}// while
					memo1.clear();
					
					refreshGraph();
				} else {
					failed = true;
				}
			return !failed;
			} 
			return false;
		}
	}

	public boolean makeFromPlainGraph(final Graph g) {
		boolean failed = false;
		if (this.itsTypes.getLevelOfTypeGraphCheck() == TypeSet.DISABLED) {
			final Hashtable<Node, Node> memo1 = new Hashtable<Node, Node>(g
					.getSize());
			Iterator<Node> vtxList = g.getNodesSet().iterator();
			while (vtxList.hasNext()) {
				Node vtxOrig = vtxList.next();
				Node vtxCopy = null;
				Type type = this.itsTypes.getSimilarType(vtxOrig.getType());
				if (type == null) {
					type = this.itsTypes.getTypeByName(vtxOrig.getType()
							.getName());
					if (type != null && !type.isNodeType())
						type = null;
				}
				if (type != null) { 
					if (type.getTypeGraphNodeObject() == null) {					
						try {
							vtxCopy = this.newNode(type);
							
							if (vtxCopy != null) {
								// add inheritance edge
								for (int i = 0; i < vtxOrig.getType()
										.getParents().size(); i++) {
									Type parentOrig = vtxOrig.getType()
											.getParents().get(i);
									Type parent = this.itsTypes
											.getSimilarType(parentOrig);
									if (parent == null) {
										parent = this.itsTypes
												.getTypeByName(parentOrig
														.getName());
										if (parent != null
												&& !parent.isNodeType())
											parent = null;
									}
									if (parent != null)
										this.itsTypes.addInheritanceRelation(type,
												parent);
								}
								vtxCopy.copyAttributes(vtxOrig);
								vtxCopy.setContextUsage(vtxOrig
										.getContextUsage());
	
								memo1.put(vtxOrig, vtxCopy);
								if(this.notificationRequired)
									propagateChange(new Change(
										Change.OBJECT_CREATED, vtxCopy));
							} 
						} catch (TypeException e) {
							// type node already exists
							Node node = this.itsTypes.getTypeGraphNode(type);
							memo1.put(vtxOrig, node);
						}
					}
					else {
						memo1.put(vtxOrig, type.getTypeGraphNodeObject());
					}
				}
			}// while
			Iterator<Arc> arcList = g.getArcsSet().iterator();
			while (arcList.hasNext()) {
				Arc arcOrig = arcList.next();
				Arc arcCopy = null;
				Type type = this.itsTypes.getSimilarType(arcOrig.getType());
				if (type == null) {
					type = this.itsTypes.getTypeByName(arcOrig.getType()
							.getName());
					if (type != null && !type.isArcType())
						type = null;
				}
				if (type != null) {
					Node source = (Node) arcOrig.getSource();
					Node target = (Node) arcOrig.getTarget();
					Node srcImg = memo1.get(source);
					Node tgtImg = memo1.get(target);
					if (type.getTypeGraphArcObject(srcImg.getType(), tgtImg.getType()) == null) {
						try {						
							arcCopy = this.newArc(type, srcImg, tgtImg);
							if (arcCopy != null) {
								arcCopy.copyAttributes(arcOrig);
								arcCopy.setContextUsage(arcOrig
										.getContextUsage());
	
								Type srctype = arcCopy.getSource().getType();
								Type tartype = arcCopy.getTarget().getType();
								// check source multiplicity
								int m1 = arcOrig.getType().getSourceMin(
										arcOrig.getSource().getType(),
										arcOrig.getTarget().getType());
								type.setSourceMin(srctype, tartype, m1);
								m1 = arcOrig.getType().getSourceMax(
										arcOrig.getSource().getType(),
										arcOrig.getTarget().getType());
								type.setSourceMax(srctype, tartype, m1);
								m1 = arcOrig.getType().getTargetMin(
										arcOrig.getSource().getType(),
										arcOrig.getTarget().getType());
								type.setTargetMin(srctype, tartype, m1);
								m1 = arcOrig.getType().getTargetMax(
										arcOrig.getSource().getType(),
										arcOrig.getTarget().getType());
								type.setTargetMax(srctype, tartype, m1);
								if(this.notificationRequired)
									propagateChange(new Change(
										Change.OBJECT_CREATED, arcCopy));
							}
						} catch (TypeException e) {}
					}
				}
			}// while
			memo1.clear();
			
			refreshGraph();
		} else {
			failed = true;
		}
	return !failed;
}
	/**
	 * Adds a copy of the specified Graph g. The Graph g should be an instance
	 * of TypeGraph. The type graph check of this type graph should be disabled.
	 * 
	 * @param g
	 *            another TypeGraph instance
	 * @param disabledTypeGraphOnly
	 *            has always to be true
	 * @return true if a copy of the type graph g was added, otherwise false.
	 */
	public boolean addCopyOfGraph(final Graph g, final boolean disabledTypeGraph) {
		return this.addCopyOfGraph(g);
	}

	public Graph copyLight(final TypeSet typeSet) {
		synchronized (this) {
//			int tglevel = 
			typeSet.getLevelOfTypeGraphCheck();
			boolean failed = false;
			final Hashtable<Node, Node> memo1 = new Hashtable<Node, Node>(this
					.getSize());
			
			TypeGraph theCopy = typeSet.isArcDirected()? new TypeGraph(typeSet): new UndirectedTypeGraph(typeSet);
			Iterator<?> iter = this.itsNodes.iterator();
			while (!failed && iter.hasNext()) {
					Node vtxOrig = (Node)iter.next();
					Node vtxCopy = null;
					Type type = typeSet.getSimilarType(vtxOrig.getType());
					if (type == null) {
						type = typeSet.getTypeByName(vtxOrig.getType()
								.getName());
						if (type != null && !type.isNodeType())
							type = null;
					}
					if (type != null) {
						try {
							vtxCopy = theCopy.newNode(type);
							/** side effect! */
							if (vtxCopy != null) {
								if (vtxCopy.getAttribute() != null
										&& vtxOrig.getAttribute() != null)
									((ValueTuple) vtxCopy.getAttribute())
											.copyEntriesToSimilarMembers(vtxOrig
													.getAttribute());
								vtxCopy.setContextUsage(vtxOrig
										.getContextUsage());
								memo1.put(vtxOrig, vtxCopy);

							}
						} catch (TypeException e) {
							// e.printStackTrace();
							failed = true;
							theCopy.dispose();
						}
					}
				} 
			iter = this.itsArcs.iterator();
				while (!failed && iter.hasNext()) {
					Arc arcOrig = (Arc)iter.next();
					Arc arcCopy = null;
					Type type = typeSet.getSimilarType(arcOrig.getType());
					if (type == null) {
						type = typeSet.getTypeByName(arcOrig.getType()
								.getName());
						if (type != null && !type.isArcType())
							type = null;
					}
					if (type != null) {
						try {
							Node source = (Node) arcOrig.getSource();
							Node target = (Node) arcOrig.getTarget();
							Node srcImg = memo1.get(source);
							Node tgtImg = memo1.get(target);
							arcCopy = theCopy.createArc(type, srcImg, tgtImg);
							if (arcCopy != null) {
								if (arcCopy.getAttribute() != null
										&& arcOrig.getAttribute() != null)
									((ValueTuple) arcCopy.getAttribute())
											.copyEntriesToSimilarMembers(arcOrig
													.getAttribute());
								arcCopy.setContextUsage(arcOrig
										.getContextUsage());
							}
						} catch (TypeException e) {
							// e.printStackTrace();
							failed = true;
							theCopy.dispose();
						}
					}
				}
			memo1.clear();
			if (failed)
				return null;
			
			return (theCopy);
		}
	}

	/**
	 * Returns a copy of this type graph typed above the specified typeset. The
	 * specified typeset should be compatible to its typeset.
	 */
	public Graph copy(final TypeSet types) {
		return graphcopy(types);
	}

	private Graph graphcopy(final TypeSet typeSet) {		
		synchronized (this) {
			typeSet.getLevelOfTypeGraphCheck();
			boolean failed = false;
			Iterator<Arc> arcList = this.getArcsSet().iterator();
			Iterator<Node> vtxList = this.getNodesSet().iterator();
			TypeGraph theCopy = typeSet.isArcDirected()? new TypeGraph(typeSet): new UndirectedTypeGraph(typeSet);
			final Hashtable<Node, Node> memo1 = new Hashtable<Node, Node>(this
					.getSize());
			while (vtxList.hasNext() && !failed) {
				Node vtxOrig = vtxList.next();
				Node vtxCopy = null;
				try {
					Type type = typeSet.getSimilarType(vtxOrig.getType());
					if (type != null) {
						vtxCopy = theCopy.newNode(type);
						/** side effect! */
						if (vtxCopy != null) {
							vtxCopy.copyAttributes(vtxOrig);
							vtxCopy.setContextUsage(vtxOrig.getContextUsage());
							memo1.put(vtxOrig, vtxCopy);
						}
					}
				} catch (TypeException e) {
					// If this graph is checked, the copy should also be ok
					// so no Exception should happen.
					// e.printStackTrace();
					failed = true;
					theCopy.dispose();
				}
			}// while

			while (arcList.hasNext() && !failed) {
				Arc arcOrig = arcList.next();
				Arc arcCopy = null;
				try {
					Type type = typeSet.getSimilarType(arcOrig.getType());
					if (type != null) {
						Node source = (Node) arcOrig.getSource();
						Node target = (Node) arcOrig.getTarget();
						Node srcImg = memo1.get(source);
						Node tgtImg = memo1.get(target);
						arcCopy = theCopy.createArc(type, srcImg, tgtImg);
						if (arcCopy != null) {
							arcCopy.copyAttributes(arcOrig);
							arcCopy.setContextUsage(arcOrig.getContextUsage());
						}
					}
				} catch (TypeException e) {
					// If the given graph is well typed,
					// the resulting graph should be also well typed
					// e.printStackTrace();
					failed = true;
					theCopy.dispose();
				}
			}// while
			memo1.clear();
			if (failed)
				return null;
			
			return (theCopy);
		}
	}

	/**
	 * Makes a copy of this graph
	 * 
	 * @param orig2copy
	 *            this hashtable is used to store the pairs (original, copy),
	 *            where an original is a node or edge of this graph and a copy
	 *            is a copied node or edge of the graph copy.
	 * @return a copy of this graph or null if an error occured
	 */
	public Graph copy(final Hashtable<GraphObject, GraphObject> orig2copy) {
		return graphcopy(orig2copy);
	}

	/**
	 * Makes a copy of this graph
	 * 
	 * @param orig2copy
	 *            this hashtable is used to store the pairs (original, copy),
	 *            where an original is a node or edge of this graph and a copy
	 *            is a copied node or edge of the graph copy.
	 * @return a copy of this graph or null if an error occured
	 */
	protected Graph graphcopy(
			final Hashtable<GraphObject, GraphObject> orig2copy) {
		
		synchronized (this) {
			boolean failed = false;
			TypeGraph theCopy = new TypeGraph(getTypeSet());
			Iterator<?> iter = this.itsNodes.iterator(); 
			while (!failed && iter.hasNext()) {
					Node vtxOrig = (Node)iter.next();
					Node vtxCopy = null;
					Type type = this.itsTypes.getSimilarType(vtxOrig.getType());
					if (type != null) {
						try {
							vtxCopy = theCopy.newNode(type);
							/** side effect! */
							if (vtxCopy != null) {
								vtxCopy.copyAttributes(vtxOrig);
								vtxCopy.setContextUsage(vtxOrig
										.getContextUsage());
								orig2copy.put(vtxOrig, vtxCopy);
							}
						} catch (TypeException e) {
							// e.printStackTrace();
							failed = true;
							theCopy.dispose();
						}
					}
			}
			iter = this.itsArcs.iterator();
			while (!failed && iter.hasNext()) {
					Arc arcOrig = (Arc)iter.next();
					Type type = this.itsTypes.getSimilarType(arcOrig.getType());
					if (type != null) {
						try {
							Node source = (Node) arcOrig.getSource();
							Node target = (Node) arcOrig.getTarget();
							Node srcImg = (Node) orig2copy.get(source);
							Node tgtImg = (Node) orig2copy.get(target);
							Arc arcCopy = theCopy.createArc(type, srcImg,
									tgtImg);
							if (arcCopy != null) {
								arcCopy.copyAttributes(arcOrig);
								arcCopy.setContextUsage(arcOrig
										.getContextUsage());
								orig2copy.put(arcOrig, arcCopy);
							}
						} catch (TypeException e) {
							// e.printStackTrace();
							failed = true;
							theCopy.dispose();
						}
					}
			}
			if (failed)
				return null;
			
			return (theCopy);
		}
	}

	/**
	 * The method returns a flat copy of the graph itself.
	 */
	public Graph graphcopy() {
		synchronized (this) {
			boolean failed = false;
			final Hashtable<Node, Node> memo1 = new Hashtable<Node, Node>(this
					.getSize());
			TypeGraph theCopy = new TypeGraph(getTypeSet());
			
			Iterator<?> iter = this.itsNodes.iterator();
			while (!failed && iter.hasNext()) {
				Node vtxOrig = (Node)iter.next();
				Node vtxCopy = null;
				try {
					vtxCopy = theCopy.createNode(vtxOrig);
					/** side effect! */
					vtxCopy.setContextUsage(vtxOrig.getContextUsage());
					memo1.put(vtxOrig, vtxCopy);
				} catch (TypeException e) {
					// e.printStackTrace();
					failed = true;
					theCopy.dispose();
				}
			} 
				
			iter = this.itsArcs.iterator();
			while (!failed && iter.hasNext()) {
				Arc arcOrig = (Arc)iter.next();
				try {
					Node source = (Node) arcOrig.getSource();
					Node target = (Node) arcOrig.getTarget();
					Node srcImg = memo1.get(source);
					Node tgtImg = memo1.get(target);
					Arc arcCopy = theCopy.copyArc(arcOrig, srcImg, tgtImg);
					if (arcCopy != null)
						arcCopy.setContextUsage(arcOrig.getContextUsage());
				} catch (TypeException e) {
					// e.printStackTrace();
					failed = true;
					theCopy.dispose();
				}
		}

		memo1.clear();
		if (failed)
			return null;
		
		return (theCopy);
		}
	}

	/**
	 * Copy a graph The method returns a flat copy (without references) of the
	 * graph.
	 */
	public Graph copy() {
		return this.graphcopy();
	}

	/**
	 * THIS METHOD IS NOT IMPLEMENTED FOR THIS CLASS.
	 * 
	 * @return null
	 */
	public Graph graphcopy(TypeGraph g) {
		return null;
	}

	/**
	 * Adds the specified node to my nodes. The type of the specified node has
	 * to be in my type set.
	 */
	public void addNode(final Node n) {
		if (!this.itsNodes.contains(n)) {
			TypeError typeError = this.itsTypes.addTypeGraphObject(n);
			if (typeError != null) {
				n.dispose();
			} else {
				this.itsNodes.add(n);
				addNodeToTypeObjectsMap(n);
				
				if (n.getAttribute() != null) {
					((ValueTuple) n.getAttribute()).addObserver(n);
					this.attributed = true;
				}
				this.changed = true;
			}
		}
	}

	/***************************************************************************
	 * Create a new Node as a copy of <code>orig</code>. Only the type and
	 * the * attributes are copied, the structural context (incoming/outgoing
	 * arcs) is not. *
	 **************************************************************************/
	public Node copyNode(final Node orig) throws TypeException {
		Node aNode = createNode(orig.getType());
		if (aNode != null) {
			if (orig.getAttribute() != null && aNode.getAttribute() == null) {
				aNode.createAttributeInstance();
			}
		}
		return aNode;
	}

	protected void removeNode(final Node n) {
		if (this.itsNodes.contains(n)) {
			Iterator<Arc> anIter = n.getIncomingArcsSet().iterator();
			while (anIter.hasNext()) {
				Arc aNeighbor = anIter.next();
				removeArc(aNeighbor);
				anIter = n.getIncomingArcsSet().iterator();
			}
			anIter = n.getOutgoingArcsSet().iterator();
			while (anIter.hasNext()) {
				Arc aNeighbor = anIter.next();
				removeArc(aNeighbor);
				anIter = n.getOutgoingArcsSet().iterator();
			}
			this.itsNodes.remove(n);
			
//			n.getType().removeTypeUser(n);
			
			removeNodeFromTypeObjectsMap(n);
			this.changed = true;
		}
	}

	/**
	 * Adds the specified edge to my edges. The type of the specified edge has
	 * to be in my type set.
	 */
	public void addArc(final Arc a) {
		if (!this.itsArcs.contains(a)) {
			TypeError typeError = this.itsTypes.addTypeGraphObject(a);
			if (typeError != null) {
				a.dispose();
			} else {
				this.itsArcs.add(a);
				addArcToTypeObjectsMap(a);
				
				if (a.getAttribute() != null)
					((ValueTuple) a.getAttribute()).addObserver(a);
				this.changed = true;
			}
		}
	}

	protected void removeArc(final Arc a) {
		if (this.itsArcs.contains(a)) {
			((Node)a.getSource()).removeOut(a);
			((Node)a.getTarget()).removeIn(a);
			this.itsArcs.remove(a);
//			a.getType().removeTypeUser(a);
			removeArcFromTypeObjectsMap(a);
			this.changed = true;
		}
	}

	protected Node newNode(final Type t) throws TypeException {		
		final Node aNode = new Node(t, this);		
		TypeError typeError = this.itsTypes.addTypeGraphObject(aNode);
		if (typeError != null) {
			aNode.dispose();
			throw new TypeException(typeError);
		}

		this.attributed = aNode.getAttribute() != null;
		
		this.itsNodes.add(aNode);
		addNodeToTypeObjectsMap(aNode);
		
		this.changed = true;
		if(this.notificationRequired)
			propagateChange(new Change(Change.OBJECT_CREATED, aNode));
		return aNode;
	}

	/** Create a new Node with given Type. */
	public Node createNode(final Type type) throws TypeException {
		Type t = this.itsTypes.adoptClan(type);
		Node aNode = newNode(t);
		return aNode;
	}

	/** Create a new Node with given Type. 
	 *
	 *@see createNode(final Type type)
	 */
	public Node createTypeNode(final Type type) throws TypeException {
		return createNode(type);
	}
	
	/**
	 * Returns the type graph node of the specified type or <code>null</code>.
	 */
	public Node getTypeNode(final Type type) {
		return this.itsTypes.getTypeGraphNode(type);
	}
	
	/**
	 * @deprecated use the method <code>copyNode(Node orig)</code>
	 */
	public Node createNode(final Node orig) throws TypeException {
		Node aNode = createNode(orig.getType());
		if (aNode != null) {
			if (orig.getAttribute() != null && aNode.getAttribute() == null)
				aNode.createAttributeInstance();
		}
		return aNode;
	}

	/**
	 * Delete a Node. Dangling Arcs are deleted implicitly.
	 * 
	 * @throws TypeException
	 *             If the parameter forceDestroy is false and there are objects
	 *             of this node, the type exception will be thrown, otherwise
	 *             not.
	 */
	public synchronized void destroyNode(
			final Node node, 
			final boolean checkFirst,
			final boolean forceDestroy) throws TypeException {

		TypeError typeError = null;		
		if (forceDestroy
				|| this.itsTypes.getLevelOfTypeGraphCheck() <= TypeSet.DISABLED) {
			this.itsTypes.forceRemoveTypeGraphObject(node);
		} else {
			typeError = this.itsTypes.removeTypeGraphObject(node);
			if (!forceDestroy && (typeError != null)) {
				throw new TypeException(typeError);
			}
		}
		//TODO: move to the GUI Type Graph 
		boolean clanUsed = false; //this.itsTypes.isClanUsed(node.getType());
		if (clanUsed
				&& (this.itsTypes.getLevelOfTypeGraphCheck() != TypeSet.DISABLED)
				&& (this.itsTypes.getLevelOfTypeGraphCheck() != TypeSet.ENABLED_INHERITANCE)) {
			typeError = new TypeError(TypeError.TYPE_IS_IN_USE, "\nThe type \""
					+ node.getType().getName()
					+ "\" cannot be deleted from the type graph,"
					+ "\nbecause at least one graph object uses it."
					+ "\nPlease disable the type graph before delete a type.",
					node, node.getType());
			typeError.setContainingGraph(this);
		}
		if (!forceDestroy && (typeError != null))
			throw new TypeException(typeError);

		Type myType = node.getType();
		this.itsTypes.removeAllInheritanceRelations(myType);
		Iterator<Node> en = getNodesSet().iterator();
		while (en.hasNext()) {
			Node currentNode = en.next();
			Type currentType = currentNode.getType();
			for (int i = 0; i < currentType.getParents().size(); i++) {
				Type p = currentType.getParents().get(i);
				if (p == myType) {
					this.itsTypes.removeInheritanceRelation(currentType, p);
					if (currentNode.getAttribute() != null)
						((ValueTuple) currentNode.getAttribute())
									.refreshParents();
					break;
				}
			}
		}
		this.itsTypes.refreshInheritanceArcs();

		// destroy incoming/outgoing arcs
		Arc a;
		Iterator<Arc> iter = node.getIncomingArcsSet().iterator();
		while (iter.hasNext()) {
			a = iter.next();
			destroyArc(a, false, false);
			iter = node.getIncomingArcsSet().iterator();
		}
		iter = node.getOutgoingArcsSet().iterator();
		while (iter.hasNext()) {
			a = iter.next();
			destroyArc(a, false, false);
			iter = node.getOutgoingArcsSet().iterator();
		}

		if(this.notificationRequired)
			propagateChange(new Change(Change.WANT_DESTROY_OBJECT, node));

		this.itsNodes.remove(node);
		this.changed = true;
		if(this.notificationRequired)
			propagateChange(new Change(Change.OBJECT_DESTROYED, node));
		node.dispose();
	}

	/**
	 * Create a new Arc with given Type, source and target objects. Source and
	 * target object must be part of this graph.
	 */
	protected Arc newArc(final Type t, final Node src, final Node tar) throws TypeException {
		final Arc anArc = new Arc(t, src, tar, this);
		if (t.getAttrType() != null
				&& t.getAttrType().getNumberOfEntries() != 0)
			anArc.createAttributeInstance();
		
		TypeError typeError = this.itsTypes.addTypeGraphObject(anArc);
		if (typeError != null) {			
			anArc.dispose();
			throw new TypeException(typeError);
		}

		if (anArc.getAttribute() != null)
			this.attributed = true;
		
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
	 * Create a new Arc with given Type, source and target objects. Source and
	 * target object must be part of this graph.
	 */
	public Arc createArc(final Type type, final Node src, final Node tar) throws TypeException {
		if (src == null || tar == null)
			return null;
		if (!this.isElement(src) || !this.isElement(tar))
			return null;

		Type t = null;
		if (this.itsTypes.containsType(type))
			t = type;
		if (t == null) {
			t = this.itsTypes.getSimilarType(type);
			if (t == null)
				t = this.itsTypes.addType(type);
		}

		Arc anArc = newArc(t, src, tar);
		return anArc;
	}

	/**
	 * Create a new Arc with given Type, source and target objects. Source and
	 * target object must be part of this graph.
	 * 
	 * @see createArc(final Type type, final Node src, final Node tar)
	 */
	public Arc createTypeArc(final Type type, final Node src, final Node tar) throws TypeException {
		return this.createArc(type, src, tar);
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
			if (a.getType().compareTo(t)
					&& a.getSource().getType().isParentOf(source)
					&& a.getTarget().getType().isParentOf(target)) {
				return a;
			}
		}
		return null;
	}
	
	/* The TypeGraph already contains a similar edge 
	 * with the source node is a parent of the specified source
	 * and the target node is a parent of the specified target node type.
	 * Returns this edge, otherwise - null.
	 */
	public Arc getTypeGraphParentArc(final Type t, final Type source, final Type target) {
		Iterator<Arc> arcs = this.itsArcs.iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (a.getType().compareTo(t)
					&& a.getSource().getType().isParentOf(source)
					&& a.getTarget().getType().isParentOf(target)) {
				return a;
			}
		}
		return null;
	}
	
	/* The TypeGraph already contains a similar edge 
	 * with the source node is a child of the specified source
	 * and the target node is a child of the specified target node type.
	 * Returns this edge, otherwise - null.
	 */
	public Arc getTypeGraphChildArc(final Type t, final Type source, final Type target) {
		Iterator<Arc> arcs = this.itsArcs.iterator();
		while (arcs.hasNext()) {
			Arc a = arcs.next();
			if (a.getType().compareTo(t)
					&& a.getSource().getType().isChildOf(source)
					&& a.getTarget().getType().isChildOf(target)) {
				return a;
			}
		}
		return null;
	}
	
	
	/**
	 * Create a new Arc as a copy of <code>orig</code>. Only the type and the
	 * attributes are copied, the structural context (source, target) is not.
	 * Source and target object must be part of this graph.
	 */
	public Arc copyArc(final Arc orig, final Node src, final Node tar) throws TypeException {
		try {
			Arc anArc = createArc(orig.getType(), src, tar);
			if (anArc != null) {
				if (orig.getAttribute() != null && anArc.getAttribute() == null) {
					anArc.createAttributeInstance();
					this.attributed = true;
				}
			}
			return anArc;
		} catch (TypeException ex) {
				throw new TypeException("TypeGraph.copyArc:: Cannot create an Arc of type : "
						+orig.getType().getName()
						+" from  "+src.getType().getName()
						+" to  "+tar.getType().getName()
						+"   "+ex.getLocalizedMessage());
		}
	}

	/**
	 * The specified arc will be removed from this type graph.
	 * 
	 * @throws TypeException
	 *             If the parameter forceDestroy is false and there are objects
	 *             of this node, the type exception will be thrown, otherwise
	 *             not.
	 */
	public synchronized void destroyArc(final Arc arc, final boolean checkFirst,
			final boolean forceDestroy) throws TypeException {
		if (arc == null)
			return;

		TypeError typeError = null;
		if (forceDestroy
				|| this.itsTypes.getLevelOfTypeGraphCheck() <= TypeSet.DISABLED) {
			this.itsTypes.forceRemoveTypeGraphObject(arc);
		} else {
			typeError = this.itsTypes.removeTypeGraphObject(arc);
			if (!forceDestroy && (typeError != null)) {
				throw new TypeException(typeError);
			}
		}
		
		if(this.notificationRequired)
			propagateChange(new Change(Change.WANT_DESTROY_OBJECT, arc));

		this.itsArcs.remove(arc);
		arc.dispose();
		this.changed = true;
		
		propagateChange(new Change(Change.OBJECT_DESTROYED, arc));
	}

	public boolean contains(final Graph g) {
		boolean result = false;
		if (g.isEmpty()) {
			result = true;
		} else {
			if (this.getSize() >= g.getSize()) {
				final Hashtable<GraphObject, GraphObject> table = new Hashtable<GraphObject, GraphObject>();
				boolean found = true;
				Iterator<?> iterG = g.getArcsSet().iterator();
				while (found && iterG.hasNext()) {
					Arc elemi = (Arc)iterG.next();
					found = false;
					Iterator<Arc> iter = this.itsArcs.iterator();
					while (iter.hasNext()) {
						Arc elemj = iter.next();
						if (elemi.getType().compareTo(elemj.getType())) {
							if (elemi.getSource() == elemi.getTarget()
									&& elemj.getSource() == elemj.getTarget()) {
								if (elemi.getSource().compareTo(elemj.getSource())) {
									found = true;
									table.put(elemi, elemj);
									table.put(elemi.getSource(), elemj.getSource());
									break;
								}
							} else if (elemi.getSource() != elemi.getTarget()
									&& elemj.getSource() != elemj.getTarget()) {
								if (elemi.getSource().compareTo(elemj.getSource())
										&& elemi.getTarget().compareTo(elemj.getTarget())) {
									found = true;
									table.put(elemi, elemj);
									table.put(elemi.getSource(), elemj.getSource());
									table.put(elemi.getTarget(), elemj.getTarget());
									break;
								}
							}
						}
					}
				}
				
				iterG = g.getNodesSet().iterator();
				while (found && iterG.hasNext()) {
					Node elemi = (Node)iterG.next();
					found = false;
					Iterator<Node> iter = this.itsNodes.iterator();
					while (iter.hasNext()) {
						Node elemj = iter.next();
						if (table.get(elemi) == null) {
							if (elemi.compareTo(elemj)) {
								found = true;
								table.put(elemi, elemj);
								break;
							}
						}
					}
				}
				
				if (table.size() == g.getSize()) {
					result = true;
				}				
			} else {
				result = false;
			}			
		} 
		return result;
	}

	/**
	 * Adds the given Node to the list for type key = anObj.getType().convertToKey().
	 * 
	 */
	protected void addNodeToTypeObjectsMap(Node n) {
		String keystr = n.getType().convertToKey();
		HashSet<GraphObject> anObjVec = new LinkedHashSet<GraphObject>(1);
		anObjVec.add(n);
		this.itsTypeObjectsMap.put(keystr, anObjVec);		
	}
	
	/**
	 * Adds the given Arc to the list of type key.
	 * key = ((Arc) anObj).getSource().getType().convertToKey() 
	 * + anObj.getType().convertToKey() + ((Arc) anObj).getTarget().getType().convertToKey().
	 * Additionally, this Arc object is added to the keys of all children of its source  
	 * resp. target Node because of the inherited edge relation.
	 * 
	 */
	protected void addArcToTypeObjectsMap(Arc arc) {
		
		Vector<Type> mySrcChildren = arc.getSource().getType().getAllChildren();
		Vector<Type> myTarChildren = arc.getTarget().getType().getAllChildren();
		
		for (int i = 0; i < mySrcChildren.size(); ++i) {
			
			for (int j = 0; j < myTarChildren.size(); ++j) {
				
				String keystr = mySrcChildren.get(i).convertToKey()
							+ arc.getType().convertToKey()
							+ myTarChildren.get(j).convertToKey();
				
				HashSet<GraphObject> anObjVec = new LinkedHashSet<GraphObject>(1);
				anObjVec.add(arc);
				this.itsTypeObjectsMap.put(keystr, anObjVec);
			}
		}
	}
	
	/**
	 * Adds the given GraphObject to the list for type key.
	 * For a Node object then key = anObj.getType().convertToKey(),
	 * for an Arc object then key = ((Arc) anObj).getSource().getType().convertToKey() 
	 * + anObj.getType().convertToKey() + ((Arc) anObj).getTarget().getType().convertToKey().
	 * Additionally, this Arc object is added to the keys for all children of its source  
	 * resp. target Node because of the inherited edge relation.
	 * 
	 */
	protected void addToTypeObjectsMap(GraphObject anObj) {
		if (anObj.isNode()) {			
			this.addNodeToTypeObjectsMap((Node) anObj);
		} else {
			this.addArcToTypeObjectsMap((Arc) anObj);
		}
	}
	
	protected void removeNodeFromTypeObjectsMap(final Node anObj) {
		final String keystr = anObj.getType().convertToKey();
		this.itsTypeObjectsMap.remove(keystr);
	}
	
	protected void removeArcFromTypeObjectsMap(final Arc anObj) {
		if (anObj.getSource() != null
				&& anObj.getTarget() != null) {
			Vector<Type> mySrcChildren = anObj.getSource().getType().getAllChildren();
			Vector<Type> myTarChildren = anObj.getTarget().getType().getAllChildren();
			
			for (int i = 0; i < mySrcChildren.size(); ++i) {
				
				for (int j = 0; j < myTarChildren.size(); ++j) {
					
					String keystr = mySrcChildren.get(i).convertToKey()
							+ anObj.getType().convertToKey()
							+ myTarChildren.get(j).convertToKey();
					this.itsTypeObjectsMap.remove(keystr);					
				}
			}
		}
	}
	
	/**
	 * Extends the type-objects map by adding all inherited edges of the given node type
	 * as child type.
	 * 
	 */
	protected void extendTypeObjectsMap(final Type nodeType) {	
//		System.out.println("TypeGraph.extendTypeObjectsMap: ");
		Node parNode = null;
		List<Type> parents = nodeType.getAllParents();
		for (int i=0; i<parents.size(); i++) {
			final Type parType = parents.get(i);
			String typeKey = parType.convertToKey();
			if (this.itsTypeObjectsMap.get(typeKey) != null
					&& !this.itsTypeObjectsMap.get(typeKey).isEmpty())
				parNode = (Node)this.itsTypeObjectsMap.get(typeKey).iterator().next();
			if (parNode != null) {
				Iterator<Arc> parArcs = parNode.getOutgoingArcsSet().iterator();
				while (parArcs.hasNext()) {
					Arc a = parArcs.next();
					if (a.isInheritance())
						continue;
					String keystr = nodeType.convertToKey()
										.concat(a.getType().convertToKey())
										.concat(a.getTargetType().convertToKey());
					HashSet<GraphObject> anObjVec = new LinkedHashSet<GraphObject>(1);
					anObjVec.add(a);
					this.itsTypeObjectsMap.put(keystr, anObjVec);	
					
//					System.out.println("TypeGraph.extendTypeObjectsMap: "
//							+nodeType.getName()+" - "
//							+a.getType().getName()+" -> "
//							+a.getTargetType().getName());
				}
				parArcs = parNode.getIncomingArcsSet().iterator();
				while (parArcs.hasNext()) {
					Arc a = parArcs.next();
					if (a.isInheritance())
						continue;
					String keystr = a.getSourceType().convertToKey()
											.concat(a.getType().convertToKey())
											.concat(nodeType.convertToKey());
					HashSet<GraphObject> anObjVec = new LinkedHashSet<GraphObject>(1);
					anObjVec.add(a);
					this.itsTypeObjectsMap.put(keystr, anObjVec);
					
//					System.out.println("TypeGraph.extendTypeObjectsMap: "
//							+a.getSourceType().getName()+" - "
//							+a.getType().getName()+" -> "
//							+nodeType.getName());
				}
			}
		}
		
	}
	
	
	/**
	 * This method is not defined for this class.
	 */
	public boolean isUsingVariable(VarMember v) {
		return false;
	}

	/**
	 * This method is not defined for this class.
	 */
	public synchronized boolean glue(GraphObject keep, GraphObject glue) {
		return false;
	}

	/**
	 * This method is not defined for this class.
	 */
	public boolean isReadyForTransform() {
		return false;
	}

	/**
	 * This method is not defined for this class.
	 */
	public boolean isReadyForTransform(Vector<GraphObject> storeOfFailedObjs) {
		return false;
	}

	/**
	 * This method is not defined for this class.
	 */
	public OrdinaryMorphism isomorphicCopy() {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public OrdinaryMorphism isoToCopy() {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public OrdinaryMorphism isoToCopy(int n) {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public OrdinaryMorphism reverseIsomorphicCopy() {
		return null;
	}


	/**
	 * This method is not defined for this class.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(Graph g, boolean withIsomorphic) {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(Graph g, boolean disjunion,
			boolean withIsomorphic) {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(Graph g, int sizeOfInclusions,
			boolean withIsomorphic) {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> getOverlappings(Graph g, int sizeOfInclusions,
			boolean disjunion, boolean withIsomorphic) {
		return null;
	}

	
	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("Graph", this);
		h.addAttr("name", getName());

		if (!this.kind.equals(""))
			h.addAttr("kind", this.kind);
		
		if (!this.comment.equals(""))
			h.addAttr("comment", this.comment);

		if (!this.info.equals(""))
			h.addAttr("info", this.info);

		h.addIteration("", this.itsNodes.iterator(), true);
		// multiplicity will be written by node
		h.addIteration("", this.itsArcs.iterator(), true);
		// multiplicity will be written by arc
		h.close();
	}

	public void XreadObject(XMLHelper h) {
		if (h.isTag("Graph", this)) {
			String str = h.readAttr("name");
			setName(str.replaceAll(" ", ""));

			str = h.readAttr("comment");
			if (!str.equals(""))
				this.comment = str.toString();

			str = h.readAttr("kind");
			if (!str.equals(""))
				this.kind = str.toString();
			
			str = h.readAttr("info");
			if (!str.equals(""))
				this.info = str.toString();

			Enumeration<?> en = h.getEnumeration("", null, true, "Node");
			while (en.hasMoreElements()) {
				h.peekElement(en.nextElement());
				Type t = (Type) h.getObject("type", null, false);
				if (t != null) {
					Node n = null;
					try {
						n = newNode(t);
						n = (Node) h.loadObject(n);
					} catch (TypeException e) {
						// while loading the type check should be disabled,
						// so this Exception should never be thrown
						// e.printStackTrace();
						System.out.println("TypeGraph.XreadObject: cannot load a Node: "+e.getMessage());
					}

					// load multiplicity, if this is the type graph
					// read the multiplicities
					String m = h.readAttr("sourcemin");
					if (!"".equals(m)) {
						try {
							t.setSourceMin(Integer.parseInt(m));
						} catch (NumberFormatException e) {
							t.setSourceMin(Type.UNDEFINED);
						}
					} else
						t.setSourceMin(Type.UNDEFINED);
					m = h.readAttr("sourcemax");
					if (!"".equals(m)) {
						try {
							t.setSourceMax(Integer.parseInt(m));
						} catch (NumberFormatException e) {
							t.setSourceMax(Type.UNDEFINED);
						}
					} else
						t.setSourceMax(Type.UNDEFINED);
				}
				h.close();
			}
			en = h.getEnumeration("", null, true, "Edge");
			while (en.hasMoreElements()) {
				h.peekElement(en.nextElement());
				Type t = (Type) h.getObject("type", null, false);
				Node n1 = (Node) h.getObject("source", null, false);
				Node n2 = (Node) h.getObject("target", null, false);
				if (t != null && n1 != null && n2 != null) {
					Arc a = null;
					try {
						a = newArc(t, n1, n2);						
						a = (Arc) h.loadObject(a);
					} catch (TypeException e) {						
						// while loading the type check should be disabled,
						// so this Exception should never be thrown						
						System.out.println("TypeGraph.XreadObject: cannot load an Arc: "+e.getMessage());
//						e.printStackTrace();
					}

					// load multiplicity, if this is the type graph
					Type sourceType = n1.getType();
					Type targetType = n2.getType();

					// read the multiplicities
					String m = h.readAttr("sourcemin");
					if (!"".equals(m)) {
						try {
							t.setSourceMin(sourceType, targetType, Integer
									.parseInt(m));
						} catch (NumberFormatException e) {
							t.setSourceMin(sourceType, targetType, Type.UNDEFINED);
						}
					} else
						t.setSourceMin(sourceType, targetType, Type.UNDEFINED);
					m = h.readAttr("sourcemax");
					if (!"".equals(m)) {
						try {
							t.setSourceMax(sourceType, targetType, Integer
									.parseInt(m));
						} catch (NumberFormatException e) {
							t.setSourceMax(sourceType, targetType, Type.UNDEFINED);
						}
					} else
						t.setSourceMax(sourceType, targetType, Type.UNDEFINED);
					m = h.readAttr("targetmin");
					if (!"".equals(m)) {
						try {
							t.setTargetMin(sourceType, targetType, Integer
									.parseInt(m));
						} catch (NumberFormatException e) {
							t.setTargetMin(sourceType, targetType, Type.UNDEFINED);
						}
					} else
						t.setTargetMin(sourceType, targetType, Type.UNDEFINED);
					m = h.readAttr("targetmax");
					if (!"".equals(m)) {
						try {
							t.setTargetMax(sourceType, targetType, Integer
									.parseInt(m));
						} catch (NumberFormatException e) {
							t.setTargetMax(sourceType, targetType, Type.UNDEFINED);
						}
					} else
						t.setTargetMax(sourceType, targetType, Type.UNDEFINED);
				}
				h.close();
			}
			h.close();
		}
	}

	/**
	 * This method is not implemented for this class.
	 */
	public Vector<OrdinaryMorphism> generateAllSubgraphs(int sizeOfInclusions,
			boolean union, boolean withIsomorphic) {
		return null;
	}

	/**
	 * This method is not implemented for this class.
	 */
	public Vector<OrdinaryMorphism> generateAllSubgraphsWithInclusionsOfSize(
			int i, Vector<GraphObject> itsGOSet, Vector<OrdinaryMorphism> inclusions, boolean withIsomorphic) {
		return null;
	}

	/**
	 * Returns true.
	 */
	public boolean isTypeGraph() {
		return true;
	}

	/**
	 * Returns false.
	 */
	public boolean isCompleteGraph() {
		return false;
	}

	/**
	 * Returns false.
	 */
	public boolean isNacGraph() {
		return false;
	}

	/**
	 * This method is not defined for this class.
	 */
	public Vector<String> getVariableNamesOfAttributes() {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public Vector<VarMember> getSameVariablesOfAttributes() {
		return null;
	}

	/**
	 * This method is not defined for this class.
	 */
	public void unsetCriticalObjects() {
	}

	/**
	 * This method is not defined for this class.
	 */
	public void unsetTransientAttrValues() {
	}

	/**
	 * This method is not defined for this class.
	 */
	public Vector<Hashtable<GraphObject, GraphObject>> getPartialMorphismIntoSet(
			Vector<GraphObject> set) {
		return null;
	}

}
