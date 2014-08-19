package agg.xt_basis;

import java.util.Dictionary;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import agg.attribute.impl.TupleMapping;
import agg.xt_basis.colim.ALPHA_DIAGRAM;
import agg.xt_basis.colim.COLIM_DEFS;
import agg.xt_basis.colim.COLIM_VECTOR;
import agg.xt_basis.colim.INT_VECTOR;
import agg.xt_basis.colim.SET_DIAGRAM;


/**
 * This class allows for representation of general diagrams of graphs and for
 * computation of their colimit. It has capabilities for optional in-place
 * computation of the colimit object in one of the diagram nodes. Attributes are
 * ignored for colimit computation. The colimit computation itself is
 * implemented using the colimit library from Dietmar Wolz.
 */
public class ColimDiagram implements COLIM_DEFS {

	/**
	 * Construct myself to be an empty diagram where the colimit object is to be
	 * computed into the given Graph <code>result</code>. By adding
	 * <code>result</code> as an ordinary diagram node via
	 * <code>addNode</code> as well, in-place computation can be achieved.
	 * <p>
	 * <b>Pre:</b> <code>result.isGraph()</code>.
	 */
	public ColimDiagram(Graph result) {
		this.itsDiagram = new ALPHA_DIAGRAM();
		this.itsColimGraph = result;
		this.itsColimMorphisms = new Vector<OrdinaryMorphism>();
		this.itsGraphIndexMap = new Hashtable<Graph, Integer>(8);
		this.itsInplaceFlag = false;
	}

	/**
	 * Add a Graph as a node to the diagram.
	 * <p>
	 * <b>Pre:</b> <code>graph.isGraph()</code>.
	 */
	public void addNode(Graph graph) {
		// Objects of the graph (element type GraphObject):
		COLIM_VECTOR allObjects = new COLIM_VECTOR(32);
		// src, tar, abs references of each object (element type INT_VECTOR):
		COLIM_VECTOR allObjectsRefs = new COLIM_VECTOR(32);
		// Attributes/Type (?) of each object (element type COLIM_VECTOR of
		// Type):
		COLIM_VECTOR allObjectsAttrs = new COLIM_VECTOR(32);
		// Mapping of graph objects to their index in diagram representation
		// (maps GraphObject to Integer):
		Dictionary<GraphObject, Integer> anIndexMap = new Hashtable<GraphObject, Integer>(32);
		if (graph == this.itsColimGraph)
			this.itsInplaceFlag = true;

		// fill allObjects:
		fillObjects(allObjects, allObjectsRefs, allObjectsAttrs, anIndexMap,
									graph.getNodesSet().iterator());
		fillObjects(allObjects, allObjectsRefs, allObjectsAttrs, anIndexMap,
									graph.getArcsSet().iterator());
		
		/*
		 * // fill allObjects allObjects.ensureCapacity(graph.getSize());
		 * Enumeration anObjectIter = graph.getElements(); for (int i = 0;
		 * anObjectIter.hasMoreElements(); i++){ anObject = (GraphObject)
		 * anObjectIter.nextElement(); allObjects.push_back(anObject); } // fill
		 * allObjectsRefs: allObjectsRefs.ensureCapacity(allObjects.size()); for
		 * (int i = 0; i < allObjects.size(); i++){ GraphObject anObject = (GraphObject)
		 * allObjects.at(i); INT_VECTOR anObjectsRefs = new INT_VECTOR(2);
		 * 
		 * if (anObject.isNode()){ // set source and target references to
		 * undefined: anObjectsRefs.push_back(i); anObjectsRefs.push_back(i); }
		 * else{ anObjectsRefs.push_back(((Integer) anIndexMap.get(((Arc)
		 * anObject).getSource())).intValue());
		 * anObjectsRefs.push_back(((Integer) anIndexMap.get(((Arc)
		 * anObject).getTarget())).intValue()); }
		 * allObjectsRefs.push_back(anObjectsRefs);
		 * 
		 * COLIM_VECTOR anObjectsAttrs = new COLIM_VECTOR(1);
		 * anObjectsAttrs.push_back(anObject.getType());
		 * allObjectsAttrs.push_back(anObjectsAttrs); }
		 */
		
		allObjects.trimToSize();
		allObjectsRefs.trimToSize();
		allObjectsAttrs.trimToSize();
		
		int i = this.itsDiagram.insert_object(allObjects, allObjectsRefs,
				allObjectsAttrs, graph.getName());

		this.itsGraphIndexMap.put(graph, new Integer(i));
	}

	private void fillObjects(
			final COLIM_VECTOR allObjects,
			final COLIM_VECTOR allObjectsRefs,
			final COLIM_VECTOR allObjectsAttrs,
			final Dictionary<GraphObject, Integer> anIndexMap,
			final Iterator<?> anObjectIter) {
		// fill allObjects and allObjectsRefs
		int count = allObjects.size();
		for (int i = count; anObjectIter.hasNext(); i++) {
			GraphObject anObject = (GraphObject)anObjectIter.next();
			allObjects.push_back(anObject);
			anIndexMap.put(anObject, new Integer(i));

			INT_VECTOR anObjectsRefs = new INT_VECTOR(2);			
			if (anObject.isNode()) {
				// set source and target references to undefined:
				anObjectsRefs.push_back(i);
				anObjectsRefs.push_back(i);
				anObjectsRefs.trimToSize();
			} else {
				anObjectsRefs.push_back(anIndexMap.get(
						((Arc) anObject).getSource()).intValue());
				anObjectsRefs.push_back(anIndexMap.get(
						((Arc) anObject).getTarget()).intValue());
				anObjectsRefs.trimToSize();
			}
			allObjectsRefs.push_back(anObjectsRefs);

			COLIM_VECTOR anObjectsAttrs = new COLIM_VECTOR(1);
			anObjectsAttrs.push_back(anObject.getType());
			anObjectsAttrs.trimToSize();
			allObjectsAttrs.push_back(anObjectsAttrs);
		}	
	}
	
	/**
	 * Add an Morphism as an edge to the diagram.
	 * <p>
	 * <b>Pre:</b> <code>morph.getOriginal()</code> and
	 * <code>morph.getImage()</code> have been added to the diagram with
	 * <code>addNode()</code> before.
	 * 
	 * @see agg.xt_basis.Morphism#getOriginal()
	 * @see agg.xt_basis.Morphism#getImage()
	 * @see agg.xt_basis.ColimDiagram#addNode
	 */
	public void addEdge(Morphism morph) {
		Graph aSourceGraph = morph.getOriginal();
		Graph aTargetGraph = morph.getImage();

		GraphObject anObject = null;

		Dictionary<GraphObject, Integer> 
		aTargetIndexMap = new Hashtable<GraphObject, Integer>(32);
		// maps GraphObject to Integer
		int count = 0;
		Iterator<?> anObjectIter = aTargetGraph.getNodesSet().iterator();
		for (int i= count; anObjectIter.hasNext(); i++) {
			aTargetIndexMap.put((GraphObject) anObjectIter.next(),
					new Integer(i));
		}
		count = aTargetIndexMap.size();
		anObjectIter = aTargetGraph.getArcsSet().iterator();
		for (int i = count; anObjectIter.hasNext(); i++) {
			aTargetIndexMap.put((GraphObject) anObjectIter.next(),
					new Integer(i));
		}
		
		INT_VECTOR aMorphism = new INT_VECTOR(64);

		anObjectIter = aSourceGraph.getNodesSet().iterator();
		while (anObjectIter.hasNext()) {
			anObject = morph.getImage((GraphObject) anObjectIter.next());
			if (anObject != null) {
				if (aTargetIndexMap.get(anObject) != null)
					aMorphism.push_back(aTargetIndexMap.get(anObject)
							.intValue());
				else
					aMorphism.push_back(undefined);
			} else
				aMorphism.push_back(undefined);
		}
		anObjectIter = aSourceGraph.getArcsSet().iterator();
		while (anObjectIter.hasNext()) {
			anObject = morph.getImage((GraphObject) anObjectIter.next());
			if (anObject != null) {
				if (aTargetIndexMap.get(anObject) != null)
					aMorphism.push_back(aTargetIndexMap.get(anObject)
							.intValue());
				else
					aMorphism.push_back(undefined);
			} else
				aMorphism.push_back(undefined);
		}
		this.itsDiagram.insert_morphism(aMorphism, this.itsGraphIndexMap
				.get(aSourceGraph).intValue(), this.itsGraphIndexMap.get(
				aTargetGraph).intValue());
	}

	/**
	 * Perform the colimit computation for the diagram I'm representing. The
	 * Graph <code>result</code> which has been passed to my constructor
	 * becomes the colimit object, and the colimit morphisms requested by
	 * <code>requestEdge()</code> are built accordingly.
	 * 
	 * @see agg.xt_basis.ColimDiagram#requestEdge
	 */
	public final void computeColimit() throws TypeException {
		this.adoptEntriesWhereEmpty = false;
		COLIM_VECTOR items = this.itsDiagram.get_colimit_items_total();
		COLIM_VECTOR refs = this.itsDiagram.get_colimit_refs_total();
		// we don't actually use attrs
		COLIM_VECTOR attrs = this.itsDiagram.get_colimit_attrs_total();
		// this may throw a TypeException
		try {
			convertColimit(items, refs, attrs);
		} catch (TypeException ex) {
			throw ex;
		}
	}

	public final void computeColimit(boolean adoptEntries) throws TypeException {
		this.adoptEntriesWhereEmpty = adoptEntries;
		COLIM_VECTOR items = this.itsDiagram.get_colimit_items_total();
		COLIM_VECTOR refs = this.itsDiagram.get_colimit_refs_total();
		// we don't actually use attrs
		COLIM_VECTOR attrs = this.itsDiagram.get_colimit_attrs_total();
		// this may throw a TypeException
		try {
			convertColimit(items, refs, attrs);
		} catch (TypeException ex) {
			throw ex;
		}
	}

	/**
	 * Request the computation of the given empty morphism as a colimit
	 * morphism.
	 * <p>
	 * <b>Pre:</b>
	 * <ol>
	 * <li>The domain of <code>morph</code> is empty.
	 * <li><code>morph.getOriginal()</code> has been added to the diagram via
	 * <code>addNode()</code>.
	 * <li><code>morph.getImage()</code> is the <code>result</code> object
	 * that has been passed to my constructor.
	 * </ol>
	 * 
	 * @see agg.xt_basis.OrdinaryMorphism#getOriginal()
	 * @see agg.xt_basis.OrdinaryMorphism#getImage()
	 * @see agg.xt_basis.ColimDiagram#addNode
	 */
	public final void requestEdge(OrdinaryMorphism morph) {
		Graph aTargetGraph = morph.getImage();
		if (aTargetGraph == this.itsColimGraph) {
			this.itsColimMorphisms.add(morph);
		}
	}

	private final void convertColimit(COLIM_VECTOR items, COLIM_VECTOR refs,
			COLIM_VECTOR attrs) throws TypeException {
		SET_DIAGRAM anItemDiagram = this.itsDiagram.get_item_diagram();

		// The Vector "allColimItems" is computed following different
		// strategies in the two cases "inplace" and "diagram".
		// Once it has been built, it behaves like this:
		// for every index i into the colimit vector "items",
		// allColimItems.elementAt(i) will get us the corresponding
		// GraphObject in "itsColimGraph".
		Vector<Vector<GraphObject>> allColimItems = null;

		if (this.itsInplaceFlag) {
			// A Vector to represent the inverse relation of the implicit
			// morphism between "itsColimGraph" and the computed colimit
			// object represented by "items". Elements are of type
			// Vector of GraphObject.
			Vector<Vector<GraphObject>> allOrigs = new Vector<Vector<GraphObject>>(
					items.size());

			// initialize "allOrigs" with empty vectors:
			for (int i = 0; i < items.size(); i++) {
				allOrigs.addElement(new Vector<GraphObject>(2));
			}

			// loop over all GraphObjects in "itsColimGraph", computing
			// the inverse image Vector "allOrigs".
			// On the fly, delete GraphObjects that are not mapped.
			int anImgIndex;
			GraphObject anOrigObj;
			int aColimGraphIndex = this.itsGraphIndexMap.get(this.itsColimGraph)
					.intValue();
			int aFirstIndex = anItemDiagram.set_at_node(aColimGraphIndex).lower;
			int aLastIndex = anItemDiagram.set_at_node(aColimGraphIndex).upper;
			final Vector<Node> nodestodestroy = new Vector<Node>();
			for (int anObjIndex = aFirstIndex; anObjIndex <= aLastIndex; anObjIndex++) {
				anOrigObj = (GraphObject) anItemDiagram.get_element(anObjIndex);
				anImgIndex = anItemDiagram.get_colimit_pos(anObjIndex);
				if (anImgIndex == undefined) {
					if (anOrigObj.isNode()) {
						// undo here
//						itsColimGraph.destroyNode((Node) anOrigObj, false);
						// put nodes without img here and do destroy later
						nodestodestroy.add((Node) anOrigObj);
					} else {
						// undo here
						this.itsColimGraph.destroyArc((Arc) anOrigObj, false, false);
					}
				} else {
					// remained objects
					allOrigs.elementAt(anImgIndex).addElement(anOrigObj);
				}
			}
			// destroy nodes without img
			for (int d=0; d<nodestodestroy.size(); d++) {
				this.itsColimGraph.destroyNode(nodestodestroy.get(d), false, false);
			}
			// Now that we have computed the inverse image, we can determine
			// which objects are to be kept (only updated in their attributes),
			// which objects to merge (if we have non-injective
			// morphisms in the colimit diagram),
			// and which objects have to be created.
			// This is done in a loop over the elements of the vector
			// "items" that represents the colimit object:

			Vector<GraphObject> aCurOrigs; // The GraphObjects contained in
			// this vector
			// represent the inverse image of the currently
			// processed element of the colimit object

			INT_VECTOR anObjectsRefs;
			Type aType;
			Vector<GraphObject> aSrcOrigs, aTarOrigs; // similar to aCurOrigs
			boolean anObjectLeftToCreate; // flag if there's an Object left to			
			// create
			do {
				anObjectLeftToCreate = false;

				for (int i = 0; i < items.size(); i++) {
					aCurOrigs = allOrigs.elementAt(i);
					switch (aCurOrigs.size()) {
					case 0:
						// a new GraphObject has to be created:
						anObjectsRefs = (INT_VECTOR) refs.item(i);
						aSrcOrigs = allOrigs.elementAt(anObjectsRefs.item(0));
						aTarOrigs = allOrigs.elementAt(anObjectsRefs.item(1));
						GraphObject gobj = (GraphObject) items.item(i);
						aType = gobj.getType();
						if (gobj.getContext().getTypeSet() != this.itsColimGraph
								.getTypeSet())
							aType = this.itsColimGraph.getTypeSet().getSimilarType(
									gobj.getType());
						try {

							if (gobj.isNode()) {
								// create a node:
								Node n = this.itsColimGraph.createNode(aType);
								n.setContextUsage(gobj.getContextUsage());
								allOrigs.elementAt(i).addElement(n);
								this.createdNodes.add(n);
								// // undo here
							} else {
								// create an arc
								if ((aSrcOrigs.size() > 0)
										&& (aTarOrigs.size() > 0)) {
									// src and tar objects exist -
									Arc a = this.itsColimGraph.createArc(aType,
											(Node) aSrcOrigs.firstElement(),
											(Node) aTarOrigs.firstElement());
									allOrigs.elementAt(i).addElement(a);
									// undo here
									// if the new arc brokes the multiplicity
									// condition a TypeException will be thrown
								} else
									anObjectLeftToCreate = true;
							}

						} catch (TypeException ex) {
							throw (ex);
						}

						// Note: The vectors a***Origs contain more than
						// one element only if there is a non-injective
						// morphism in the colim diagram. In this case, we
						// have to merge all this elements into one. We
						// merge into the memory location of the first
						// element, thus we can mindlessly use
						// firstElement() on the vectors for abs, src, tar
						// parameters above. (The merging is done in the
						// switch default case.)
						// else anObjectLeftToCreate = true;

						break;

					case 1:
						// keep this object!");
						// the object does already exist.
						break;

					default:
						// merge....										
						for (int j = 1; j < aCurOrigs.size(); j++) {							
							this.itsColimGraph.glue(aCurOrigs.get(0), 
												aCurOrigs.get(j));							
						}
					}
				} // for
			} while (anObjectLeftToCreate);
			
			allColimItems = allOrigs;
		} else {
			System.out
					.println("agg.xt_basis.ColimDiagram:  Sorry, only inplace computation is supported yet");
			return;
		}

		// compute requested colimit morphisms:
		OrdinaryMorphism aColimMorph;
		GraphObject aSrc, aTar;
		Enumeration<OrdinaryMorphism> aMorphIter;
		int aSrcGraph, aTarPos;

		// Loop over all colimit morphisms:
		for (aMorphIter = this.itsColimMorphisms.elements(); aMorphIter
				.hasMoreElements();) {
			aColimMorph = aMorphIter.nextElement();
			aSrcGraph = this.itsGraphIndexMap.get(aColimMorph.getOriginal())
					.intValue();

			// Loop over all GraphObjects in source graph of morphism:
			for (int anObjIndex = anItemDiagram.set_at_node(aSrcGraph).lower; anObjIndex <= anItemDiagram
					.set_at_node(aSrcGraph).upper; anObjIndex++) {
				aTarPos = anItemDiagram.get_colimit_pos(anObjIndex);
				if (aTarPos != undefined) {
					aSrc = (GraphObject) anItemDiagram.get_element(anObjIndex);
					// aTar = (GraphObject) allColimItems.elementAt(aTarPos);
					aTar = (GraphObject) ((Vector) allColimItems
							.elementAt(aTarPos)).firstElement();
					try {
						aColimMorph.addMapping(aSrc, aTar);
					} catch (BadMappingException ex) {
						return;
					}
					if (this.adoptEntriesWhereEmpty)
						this.adoptEntriesWhereEmpty(aColimMorph, aSrc, aTar);
				}
			}
		}
	}

	private void adoptEntriesWhereEmpty(OrdinaryMorphism morph,
			GraphObject from, GraphObject to) {
		if (!morph.getImage().isAttributed()
				|| (from.getAttribute() == null)
				|| (to.getAttribute() == null))
			return;

		if (morph.getImage(from) != null) {
			agg.attribute.impl.ContextView context = (agg.attribute.impl.ContextView) morph
					.getAttrContext();
			Vector<TupleMapping> mappings = context
					.getMappingsToTarget((agg.attribute.impl.ValueTuple) to
							.getAttribute());
			if (mappings != null) {
				mappings.elementAt(0).adoptEntriesWhereEmpty(
								(agg.attribute.impl.ValueTuple) from
										.getAttribute(),
								(agg.attribute.impl.ValueTuple) to
										.getAttribute());
			}
		}
	}

	public Vector<GraphObject> getCreatedNodes() {		
		return this.createdNodes;
	}
	
	// ---- member variables -----------------------------------------
	/**
	 * The graph that becomes the resulting colimit graph.
	 */
	private Graph itsColimGraph;

	/**
	 * A mapping of graphs to the indices of their representing nodes in
	 * <code>itsDiagram</code>. Keys are of type <code>Graph</code>,
	 * values of type <code>Integer</code>.
	 */
	private Dictionary<Graph, Integer> itsGraphIndexMap;

	private ALPHA_DIAGRAM itsDiagram;

	private Vector<OrdinaryMorphism> itsColimMorphisms;

	private boolean itsInplaceFlag;

	private boolean adoptEntriesWhereEmpty = false;

	private final Vector<GraphObject> createdNodes = new Vector<GraphObject>();

}
