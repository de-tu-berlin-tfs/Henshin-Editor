package agg.xt_basis;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrType;
import agg.util.XMLObject;

/**
 * Instances of this class are used for dynamic typing of graph objects. Each
 * type is associated with a name (also called "string representation"). Note
 * that two types with the same name need not be equal.
 * 
 * @version $Id: Type.java,v 1.27 2010/10/07 20:04:26 olga Exp $
 */
public interface Type extends XMLObject {

	public static final int UNDEFINED = -1;

	/**
	 * Returns the string representation. Mostly used as the name of the type.
	 */
	public String getStringRepr();

	public boolean hasParent();
	
	/**
	 * Returns the last direct parent.
	 */
	public Type getParent();

	public Vector<Type> getParents();

	public void removeChild(final Type t);
	
	/**
	 * Returns the name of the type.
	 */
	public String getName();

	/**
	 * Sets the string representation. Mostly used as the name of the type
	 */
	public void setStringRepr(String n);

	/**
	 * Sets the parent.
	 */
	public void setParent(Type t);

	public void addParent(Type t);

	public void removeParent(Type t);

	public boolean hasChild();
	
	/**
	 * Returns its direct children only.
	 */
	public Vector<Type> getChildren();

	/**
	 * Returns the associated attribute type.
	 */
	public AttrType getAttrType();

	/**
	 * compares the given type with this object.
	 * 
	 * @return true, if the given type has the same name, attributes and
	 *         additional string
	 */
	public boolean compareTo(Type t);

	/**
	 * set an additional graphical string, which is saved together with the name
	 * string representation. Here you can save additional information used in
	 * another layer. Predefined additional string: if the specified String repr
	 * is "NODE" or "[NODE]", then additionalRepr =
	 * ":RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:" , if the specified String
	 * repr is "EDGE" or "[EDGE]", then additionalRepr =
	 * ":SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:" . This format of
	 * additional type information is used for the graphical layout information
	 * of nodes and edges.
	 */

	public void setAdditionalRepr(String repr);

	/**
	 * returns the additional string
	 * 
	 * @see #setAdditionalRepr
	 */
	public String getAdditionalRepr();

	public void setImageFilename(String imageFilename);
	
	public String getImageFilename();
	
	public void setTextualComment(String comment);

	public String getTextualComment();
	
	public boolean isArcType();

	public boolean isNodeType();

	public boolean isAttrTypeEmpty();
	
	public boolean isParentAttrTypeEmpty();
	
	public boolean hasAnyAttrMember();
	
	public void removeAttributeType();
	
	public void setAbstract(boolean b);

	public boolean isAbstract();
	
	public List<Type> getClan();
	
	/**
	 * Returns true when this node type is in inheritance clan of the defined
	 * node type t. The type t can be a parent or a child of the clan.
	 */
	public boolean isInClanOf(final Type t);
	
	public boolean hasCommonParentWith(final Type t);
	
	/**
	 * compares the given type with this object and its ancestors
	 * 
	 * @return true, if the given type or one of its ancestors has the same
	 *         name, attributes and additional string
	 */
	public boolean isChildOf(Type t);

	/**
	 * compares the given type and its ancestors with this object
	 * 
	 * @return true, if the given type has the same name, attributes and
	 *         additional string as t or one of its ancestors
	 */
	public boolean isParentOf(Type t);

	/**
	 * Finds out if there is any relation between this type and the given one.
	 * Two types are related if they have one common ancestor.
	 */
	public boolean isRelatedTo(Type t);

	/**
	 * returns a list with all the parents of the current type and itself as
	 * first element
	 * 
	 * @return list of all parents
	 */
	public Vector<Type> getAllParents();

	public void addChild(final Type t);
	
	public List<Type> getCommonParentWith(final Type t);
	
	/**
	 * returns a list with all the children of the current type and itself as
	 * first element
	 * 
	 * @return list of all children
	 */
	public Vector<Type> getAllChildren();

	/**
	 * returns a string containing the name, all attributes and the additional
	 * string separated by ":".
	 */
	public String convertToKey();

	/**
	 * returns if the given GraphObject is valid typed as defined in the type
	 * graph. Before this can be checked, all edges and nodes of the type graph
	 * must be added to theire types. The given object will not tested if this
	 * is its type.
	 * 
	 * @param graphObject
	 *            the object to test
	 * @param level
	 *            a type graph check level, as defined in
	 *            {@link TypeSet#setLevelOfTypeGraphCheck}
	 * @return null, if the graphobject is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 */
	public TypeError check(GraphObject graphObject, int level);

	/**
	 * returns if the given node could be removed. This check makes only sense,
	 * if the minimum multiplicity check is activated.
	 * 
	 * @param node
	 *            the node which will be removed.
	 * @param level
	 *            the actual level. If not set to
	 *            {@link TypeSet#ENABLED_MAX_MIN} this method will do nothing.
	 * @return null, if the node will be valid typed even after removing the arc
	 *         otherwise a {@link TypeError} containing the possible fault.
	 */
	TypeError checkIfRemovable(Node node, int level);

	/**
	 * returns if the given arc could be removed from the given node so the node
	 * would be valid typed. This check makes only sense, if the minimum
	 * multiplicity check is activated.
	 * 
	 * @param node
	 *            the node which will be modified. This node has to be the
	 *            source of the arc and has to have this type.
	 * @param arc
	 *            the arc which will be removed
	 * @param level
	 *            the actual level. If not set to
	 *            {@link TypeSet#ENABLED_MAX_MIN} this method will do nothing.
	 * @return null, if the node will be valid typed even after removing the arc
	 *         otherwise a {@link TypeError} containing the possible fault.
	 */
	TypeError checkIfRemovableFromSource(GraphObject node, Arc arc, int level);
	
	TypeError checkIfRemovableFromSource(GraphObject node, Arc arc, boolean deleteSrc, boolean deleteTar, int level);

	/**
	 * returns if the given arc could be removed from the given node so the node
	 * would be valid typed. This check makes only sense, if the minimum
	 * multiplicity check is activated.
	 * 
	 * @param node
	 *            the node which will be modified. This node has to be the
	 *            target of the arc and has to have this type.
	 * @param arc
	 *            the arc which will be removed
	 * @param level
	 *            the actual level. If not set to
	 *            {@link TypeSet#ENABLED_MAX_MIN} this method will do nothing.
	 * @return null, if the node will be valid typed even after removing the arc
	 *         otherwise a {@link TypeError} containing the possible fault.
	 */
	TypeError checkIfRemovableFromTarget(final GraphObject node, final Arc arc, int level);

	
	TypeError checkIfRemovableFromTarget(final GraphObject node, final Arc arc, 
			boolean deleteSrc, boolean deleteTar, int level);
	
	/**
	 * Add the given GraphObject to this type. 
	 * The GraphObject is a node or an arc of a TypeGraph.
	 * Only one GraphObject of each type is allowed.
	 * 
	 * @return true, if the graph object could be added.
	 */
	public boolean addTypeGraphObject(GraphObject nodeOrArc);

	/**
	 * Remove the given GraphObject of the type graph from this type. The
	 * GraphObject must be added before. 
	 * Returns true if this type graph object is removed, otherwise false 
	 * (esp. if the type is in use and the type graph check is activated).
	 */
	public boolean removeTypeGraphObject(GraphObject nodeOrArc);

	public boolean removeTypeGraphObject(GraphObject nodeOrArc, boolean forceToRemove);
	
	/**
	 * returns true, if there is at least one object in the type graph for this
	 * type.
	 */
	public boolean isTypeGraphObjectDefined();

	/**
	 * returns a type graph node, if it is defined.
	 */
	public Node getTypeGraphNodeObject();

	public boolean hasTypeGraphNode();
	
	public void setVisibilityOfObjectsOfTypeGraphNode(boolean vis);
	
	public void setVisibityOfObjectsOfTypeGraphArc(final Type sourceType, final Type targetType, boolean vis);

	public boolean isObjectOfTypeGraphNodeVisible();
	
	public boolean isObjectOfTypeGraphArcVisible(final Type sourceType, final Type targetType);
	
	/**
	 * returns a type graph edge, if it is defined.
	 */
	public Arc getTypeGraphArcObject(Type sourceType, Type targetType);
	
	public boolean hasTypeGraphArc();
	
	/**
	 * returns a collection of defined graph type edges.
	 * 
	 * public HashMap getTypeGraphEdgeObjects();
	 */

	/**
	 * disable type graph object of this type.
	 */
	public void disableTypeGraphObject();


	public void setSourceMin(Type sourceType, Type targetType, int value);

	public void setSourceMax(Type sourceType, Type targetType, int value);

	public void setTargetMin(Type sourceType, Type targetType, int value);

	public void setTargetMax(Type sourceType, Type targetType, int value);

	public int getSourceMin(Type sourceType, Type targetType);

	public int getSourceMax(Type sourceType, Type targetType);

	public int getTargetMin(Type sourceType, Type targetType);

	public int getTargetMax(Type sourceType, Type targetType);

	public void setSourceMin(int value);

	public void setSourceMax(int value);

	public int getSourceMin();

	public int getSourceMax();

	public TypeError checkIfNodeCreatable(Graph basisGraph,
			int levelOfTypeGraphCheck);
	
	public boolean hasInheritedAttribute();
		
	public TypeGraphNode getTypeGraphNode();
			
	public void createAttributeType();
		
	public boolean hasTypeGraphArc(final Type sourceType);
	
	public boolean hasTypeGraphArc(final Type sourceType, final Type targetType);
	
	public boolean hasTypeGraphArc(final GraphObject sourceType, final GraphObject targetType);
	
	public boolean isEdgeCreatable(final Type sourceType, final Type targetType, final int level);
	
	public Vector<Type> getTargetsOfArc(final Type sourceType);
		
	public int getMaxMultiplicityOfAllChildren();
	
	public int getMinMultiplicityOfAllChildren();
	
	public HashMap<Type, HashMap<Type, TypeGraphArc>> getArcTypeGraphObjects();
	
	public TypeGraphArc getTypeGraphArc(final Type sourceType, final Type targetType);
	
	public TypeGraphArc getSimilarTypeGraphArc(final Type sourceType, final Type targetType);
	
	public List<Arc> getOwnIncomingArcs();
	
	public Vector<Type> getOwnIncomingArcTypes();
	
	public List<Arc> getOwnOutgoingArcs();
	
	public Vector<Type> getOwnOutgoingArcTypes();
	
	public void checkDoubleAttributeType();
	
	public void adaptTypeAttribute(final Type type);
	
	public TypeError checkIfEdgeCreatable(final Node src, final Node tar, final int level);
	
	public TypeError checkIfEdgeCreatable(final Graph g, final Node src, final Node tar, final int level);
	
	public TypeError checkSourceMax(final Graph g, final Node src, final Node tar);
	
	public TypeError checkTargetMax(final Graph g, final Node src, final Node tar);
	
	public boolean compareTypeGraphArcs(final Type t);
	
	public boolean compareTypeGraphArcsMultiplicity(final Type t);
	
	public void dispose();
	
}
