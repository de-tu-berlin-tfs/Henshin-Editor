package agg.editor.impl;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.gui.editor.EditorConstants;
import agg.gui.event.TypeEvent;
import agg.gui.event.TypeEventListener;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeGraphArc;
import agg.xt_basis.TypeSet;
import agg.xt_basis.TypeGraph;
import agg.attribute.handler.AttrHandler;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.DeclMember;

/**
 * This class EdTypeSet specifies a set of layout types for typing nodes and
 * edges of graphs. The type is defined by a combination of name, shape and
 * color. The name is a <code>String</code>. The color is a
 * <code>Color</code>. The shape of the node cann be specified by the
 * constants: EditorConstans.RECT EditorConstans.CIRCLE EditorConstans.OVAL
 * EditorConstans.ROUNDRECT. The shape of the edges cann be specified by the
 * constants: EditorConstans.SOLID EditorConstans.DASH EditorConstans.DOT. The
 * default node type is the combination: (unnamed, EditorConstans.RECT,
 * Color.black). The default edge type is the combination: (unnamed,
 * EditorConstans.SOLID, Color.black).
 * 
 * @author $Author: olga $
 * @version $Id: EdTypeSet.java,v 1.49 2010/10/16 22:43:42 olga Exp $
 */
public class EdTypeSet {

	private final Vector<EdType> 
	nodeTypes = new Vector<EdType>();

	private final Vector<EdType> 
	arcTypes = new Vector<EdType>();
	
	private final Hashtable<EdType,List<EdGraphObject>> 
	nodeTypeUsers = new Hashtable<EdType,List<EdGraphObject>>();
	
	private final Hashtable<EdType,List<EdGraphObject>> 
	arcTypeUsers = new Hashtable<EdType,List<EdGraphObject>>();
	
	private final Hashtable<EdType,List<EdGraphObject>> 
	typeGraphNodeUsers = new Hashtable<EdType,List<EdGraphObject>>();
	
	private final Hashtable<EdType,Hashtable<TypeGraphArc,List<EdGraphObject>>> 
	typeGraphArcUsers = new Hashtable<EdType,Hashtable<TypeGraphArc,List<EdGraphObject>>>();

	
	private EdType selectedNodeType;

	private EdType selectedArcType;

	private EdType defaultNodeType;

	private EdType defaultArcType;

	private boolean iconable;

	private String resourcesPath = System.getProperty("user.dir");

	private boolean typeKeyChanged;
	
	private final Vector<TypeEventListener> 
	typeEventListeners = new Vector<TypeEventListener>();

	/**
	 * this EdGraph holds the layout for the type graph used here. If no type
	 * graph is defined in the underlaying TypeSet, this will also be null.
	 */
	private EdGraph edTypeGraph;

	/**
	 * this object manages the types in basical layer. It will be used here
	 * to create new Types and to manage the type graph
	 */
	private TypeSet bTypeSet;

	
	/** Creates an empty type set */
	public EdTypeSet() {}

	/**
	 * creates an type set initialized with the types of the given TypeSet.
	 */
	public EdTypeSet(TypeSet baseTypeSet) {
		this.bTypeSet = baseTypeSet;
		// extract all Types and set default layout values of them
		initTypesFromTypeSet();		
	}  

	/**
     * Trims the capacity of used vectors to be the vector's current
     * size.
     */
	public void trimToSize() {
		this.nodeTypes.trimToSize();
		this.arcTypes.trimToSize();
		this.typeEventListeners.trimToSize();
	}
	
	public void dispose() {
		this.typeEventListeners.clear();
					
		this.defaultNodeType = null;
		this.defaultArcType = null;
		this.selectedNodeType = null;
		this.selectedArcType = null;
		
		if (this.edTypeGraph != null) {
			this.edTypeGraph.dispose();
			this.edTypeGraph = null;
		}
		
		this.arcTypeUsers.clear();
		this.nodeTypeUsers.clear();
		this.typeGraphArcUsers.clear();
		this.typeGraphNodeUsers.clear();
		
		while (!this.nodeTypes.isEmpty()) {
			this.nodeTypes.get(0).dispose();
			this.nodeTypes.remove(0);
		}
		while (!this.arcTypes.isEmpty()) {
			this.arcTypes.get(0).dispose();
			this.arcTypes.remove(0);
		}
		
		this.bTypeSet = null;		
	}
	
	public void finalize() {}

	/**
	 * Returns the type manager used in the theoretical layer for the types
	 * herein
	 */
	public TypeSet getBasisTypeSet() {
		return this.bTypeSet;
	} 

	/**
	 * force this class to use the give type manager. All old types will be
	 * removed and the types of the new type manager will get the default layout
	 * (or the last used layout as defined in the additional string of the type
	 * object).
	 */
	public void setBasisTypeSet(TypeSet typeSet) {
		this.bTypeSet = typeSet;
		initTypesFromTypeSet();
	} 

	/**
	 * Returns the formated type graph. The returned object contains the layout
	 * and a link to the basic type graph.
	 */
	public EdGraph getTypeGraph() {
		return this.edTypeGraph;
	} 

	public boolean setTypeGraph(EdGraph typeGraphLayout) {
		if (typeGraphLayout != null) {
			if (this.bTypeSet.compareTo(typeGraphLayout.getTypeSet()
					.getBasisTypeSet())) {
				Vector<Type> v = typeGraphLayout.getBasisGraph().getUsedTypes();
				this.bTypeSet.adaptTypes(v.elements(), false);
				refreshTypes();
				this.edTypeGraph = typeGraphLayout;
				this.bTypeSet.setTypeGraph(this.edTypeGraph.getBasisGraph());
				this.edTypeGraph.update();
				return true;
			} 
			return false;
		} 
		return false;
	}

	/**
	 * Creates an empty type graph, if this EdTypeSet haven't got one before. A
	 * type graph in the theoretical layer and an EdGraph for the layout will be
	 * created.
	 * 
	 * Returns the created EdGraph with the layout informations and a link to
	 * the basic type graph
	 */
	public EdGraph createTypeGraph() {
		// calls TypeSet to create a basic type graph
		// and wrap a layout around
		Graph typeGraph = this.bTypeSet.getTypeGraph();
		if (typeGraph == null) {
			typeGraph = this.bTypeSet.createTypeGraph();
		} 

		// we must send a reference to this otherwise we get an endless
		// recursion
		this.edTypeGraph = new EdGraph(typeGraph, this);
		this.edTypeGraph.setCurrentLayoutToDefault(true);
		this.edTypeGraph.markTypeGraph(true);
		return this.edTypeGraph;
	} 

	/**
	 * The EdGraph for the layout of the type graph will be deleted..
	 */
	protected void removeTypeGraph() {
		this.edTypeGraph = null;
	}

	public void destroyTypeGraph() {
		this.edTypeGraph.dispose();
		this.edTypeGraph = null;
	}

	/** Creates and adds a new type to the type set. */
	public EdType createType(Type baseType) {
		if (baseType == null) {
			return null;
		}
		String addRepr = baseType.getAdditionalRepr();
		if (addRepr.equals("")) {
			baseType.setAdditionalRepr(
//					":RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:");
					":RECT:java.awt.Color[r=0,g=0,b=0]:[NODE]:");
		}

		/* get additional representation */
		Vector<String> v = this.getAdditionalReprOfBasisType(baseType);
//		System.out.println(baseType.getStringRepr()+"   "+v);
		
		String shapeStr = v.elementAt(0);
		String colorStr = v.elementAt(1);
		String filledStr = v.elementAt(2);
		String imageFileNameStr = v.elementAt(3);
		String markStr = v.elementAt(4);

		/* shape is an integer */
		int shape = -1;
		if (shapeStr.equals("RECT"))
			shape = EditorConstants.RECT;
		else if (shapeStr.equals("ROUNDRECT"))
			shape = EditorConstants.ROUNDRECT;
		else if (shapeStr.equals("CIRCLE"))
			shape = EditorConstants.CIRCLE;
		else if (shapeStr.equals("OVAL"))
			shape = EditorConstants.OVAL;
		else if (shapeStr.equals("SOLID_LINE"))
			shape = EditorConstants.SOLID;
		else if (shapeStr.equals("DASH_LINE"))
			shape = EditorConstants.DASH;
		else if (shapeStr.equals("DOT_LINE"))
			shape = EditorConstants.DOT;
		else
			shape = EditorConstants.RECT;

		/* color is a Color */
		String r_str = colorStr.substring(colorStr.indexOf("r=") + 2, colorStr
				.indexOf(",g="));
		String g_str = colorStr.substring(colorStr.indexOf("g=") + 2, colorStr
				.indexOf(",b="));
		String b_str = colorStr.substring(colorStr.indexOf("b=") + 2, colorStr
				.indexOf("]"));
		Color color = new Color((Integer.valueOf(r_str)).intValue(), (Integer.valueOf(
				g_str)).intValue(), (Integer.valueOf(b_str)).intValue());
		
		boolean filled = filledStr.equals("FILLED") || filledStr.equals("BOLD");
		
		/* create a new type */
		EdType eType = new EdType(baseType, shape, color, filled, imageFileNameStr);
		
		eType.setResourcesPath(this.resourcesPath);
//		eType.setImageFileName(imageFileNameStr);

		/* add a type to the node types or arc types */
		if (markStr.equals("[NODE]")) {
			eType.setIconable(this.iconable);
			addAlphabeticalSorted(eType, this.nodeTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.NODE_CREATED));
		} else if (markStr.equals("[EDGE]")) {
			addAlphabeticalSorted(eType, this.arcTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.ARC_CREATED));
		}

		return eType;
	} 

	/**
	 * Creates and adds a new node type to this type set
	 * only when any node type with the specified graphical features does not exist,
	 * otherwise returns null.
	 * 
	 * If the base type set exists a new base type is created, too.
	 * 
	 * @param name
	 *            the name of a new node type
	 * @param shape
	 *            a key for the shape of node types as defined in EditorConstants
	 * @param color
	 *            the color of a new node type
	 * @return a new node EdType or null
	 * @see EditorConstants
	 */
	public EdType createNodeType(String name, int shape, Color color) {
		Type bType = null;
		EdType eType = null;

		// if type is undeclared
		if (isNewType(this.nodeTypes, name, shape, color)) {
			if (this.bTypeSet != null) {
				// try to create using TypeSet
//				bType = this.bTypeSet.createType();
				bType = this.bTypeSet.createNodeType(false);
				// EdType constructor will also set the values
				// in bType
				eType = new EdType(name, shape, color, "", bType);
			} else {
				// create without basis type
				eType = new EdType(name, shape, color, "");
			}

			eType.setIconable(this.iconable);
		
			addAlphabeticalSorted(eType, this.nodeTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.NODE_CREATED));
		}
		// return null or the new type
		return eType;
	} 

	/**
	 * Creates and adds a new node type to this type set
	 * only when any node type with the specified graphical features does not exist,
	 * otherwise returns null.
	 *  
	 * If the base type set exists a new base type is created, too.
	 */
	public EdType createNodeType(String name, int shape, Color color, String iconFileName) {
		Type bType = null;
		EdType eType = null;

		// if type is undeclared
		if (isNewType(this.nodeTypes, name, shape, color)) {
			if (this.bTypeSet != null) {
				// try to create using TypeSet
//				bType = this.bTypeSet.createType();
				bType = this.bTypeSet.createNodeType(false);
				// EdType constructor will also set the values
				// of bType
				eType = new EdType(name, shape, color, iconFileName, bType);
			} else {
				// create without basis type
				eType = new EdType(name, shape, color, iconFileName);
			}

			eType.setIconable(this.iconable);

			addAlphabeticalSorted(eType, this.nodeTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.NODE_CREATED));
		}
		// return null or the new type
		return eType;
	}
	
	/**
	 * Creates and adds a new node type to this type set
	 * only when any node type with the specified graphical features does not exist,
	 * otherwise returns null.
	 * 
	 * If the base type set exists a new base type is created, too.
	 */
	public EdType createNodeType(String name, int shape, Color color, boolean filledShape, String iconFileName) {
		Type bType = null;
		EdType eType = null;

		// if type is undeclared
		if (isNewType(this.nodeTypes, name, shape, color, filledShape)) {
			if (this.bTypeSet != null) {
				// try to create using TypeSet
//				bType = this.bTypeSet.createType();
				bType = this.bTypeSet.createNodeType(false);
				// EdType constructor will also set the values
				// of bType
				eType = new EdType(name, shape, color, filledShape, iconFileName, bType);
			} else {
				// create without basis type
				eType = new EdType(name, shape, color, filledShape, iconFileName);
			}

			eType.setIconable(this.iconable);

			addAlphabeticalSorted(eType, this.nodeTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.NODE_CREATED));
		}
		// return null or the new type
		return eType;
	}
	
	/**
	 * Creates and adds a new node type to the type set.
	 */
	public EdType createNodeType(Type baseType) {
		if (baseType == null)
			return null;
		if (baseType.getAdditionalRepr().equals("")) {
			return createDefaultNodeType(baseType);
		}

		/* shape as String */
		String shapeStr = ""; 
		/* color as String */
		String colorStr = ""; 
		String filledStr = "";
		String imageFileNameStr = "";
		
		/* shape as int */
		int shape = -1; 
		
		Vector<String> v = getAdditionalReprOfBasisType(baseType);
		shapeStr = v.elementAt(0);		
		colorStr = v.elementAt(1);
		filledStr = v.elementAt(2);
		imageFileNameStr = v.elementAt(3);
		
		if (shapeStr.equals("RECT"))
			shape = EditorConstants.RECT;
		else if (shapeStr.equals("ROUNDRECT"))
			shape = EditorConstants.ROUNDRECT;
		else if (shapeStr.equals("CIRCLE"))
			shape = EditorConstants.CIRCLE;
		else if (shapeStr.equals("OVAL"))
			shape = EditorConstants.OVAL;
		else
			shape = EditorConstants.RECT;
		/* color */
		String r_str = colorStr.substring(colorStr.indexOf("r=") + 2, colorStr
				.indexOf(",g="));
		String g_str = colorStr.substring(colorStr.indexOf("g=") + 2, colorStr
				.indexOf(",b="));
		String b_str = colorStr.substring(colorStr.indexOf("b=") + 2, colorStr
				.indexOf("]"));
		Color color = new Color((Integer.valueOf(r_str)).intValue(), (Integer.valueOf(
				g_str)).intValue(), (Integer.valueOf(b_str)).intValue());
		
		boolean filled = filledStr.equals("FILLED");
		EdType eType = new EdType(baseType, shape, color, filled, imageFileNameStr);
		eType.setResourcesPath(this.resourcesPath);
//		eType.setImageFileName(imageFileNameStr);
		eType.setIconable(this.iconable);
		addAlphabeticalSorted(eType, this.nodeTypes);
		// fireTypeEvent(new TypeEvent(this, eType, TypeEvent.NODE_CREATED));
		return eType;
	} 

	/** Returns the default node type. */
	public EdType getDefaultNodeType() {
		return this.defaultNodeType;
	} 

	/**
	 * Creates and adds the default node type for the specified basis type.
	 */
	public EdType createDefaultNodeType(Type baseType) {
		if (baseType == null) {
			return this.defaultNodeType;
		}

		EdType eType = null;
		if (isNewType(this.nodeTypes, baseType.getStringRepr(),
				EditorConstants.RECT, Color.black)) {
			eType = new EdType(baseType, EditorConstants.RECT, Color.black, false, "");
			eType.setIconable(this.iconable);
			addAlphabeticalSorted(eType, this.nodeTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.NODE_CREATED));
		} else {
			eType = getType(this.nodeTypes, baseType.getStringRepr(),
					EditorConstants.RECT, Color.black, false);
		}
		return eType;
	} 

	/** Creates and adds a new node type to the type set. */
	public EdType createNodeType(Type baseType, String name, int shape,
			Color color) {
		if (isNewType(this.nodeTypes, name, shape, color, false)) {
			EdType eType = new EdType(name, shape, color, false, "", baseType);
			eType.setIconable(this.iconable);
			addAlphabeticalSorted(eType, this.nodeTypes);
			// fireTypeEvent(new TypeEvent(this, eType,
			// TypeEvent.NODE_CREATED));
			return eType;
		} 
		return null;
	}
	
	/** Creates and adds a new node type to the type set. */
	public EdType createNodeType(Type baseType, String name, int shape,
			Color color, String iconFileName) {
		if (isNewType(this.nodeTypes, name, shape, color, false)) {
			EdType eType = new EdType(name, shape, color, false, iconFileName, baseType);
			eType.setIconable(true); //this.iconable);
			addAlphabeticalSorted(eType, this.nodeTypes);
			// fireTypeEvent(new TypeEvent(this, eType,
			// TypeEvent.NODE_CREATED));
			return eType;
		} 
		return null;
	} 
	
	public EdType createNodeType(Type baseType, String name, int shape,
			Color color, boolean filledshape, String iconFileName) {
		if (isNewType(this.nodeTypes, name, shape, color, filledshape)) {
			EdType eType = new EdType(name, shape, color, filledshape, iconFileName, baseType);
			eType.setIconable(true); //this.iconable);
			addAlphabeticalSorted(eType, this.nodeTypes);
			// fireTypeEvent(new TypeEvent(this, eType,
			// TypeEvent.NODE_CREATED));
			return eType;
		} 
		return null;
	}

	/** Adds a node type. */
	public void addNodeType(EdType t) {
		t.setIconable(this.iconable);
		addAlphabeticalSorted(t, this.nodeTypes);
		fireTypeEvent(new TypeEvent(this, t, TypeEvent.NODE_CREATED));
	}

	public boolean containsNodeType(String name, int shape, Color color, boolean filledshape) {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.get(i);
			if (t.getName().equals(name) && t.getShape() == shape
					&& t.getColor().equals(color))
				return true;
		}
		return false;
	}

	protected EdType getNodeTypeByBasis(final Type baseType) {
		for (int i=0; i<this.nodeTypes.size(); i++) {
			final EdType t = this.nodeTypes.get(i);
			if (t.getBasisType() == baseType)
				return t;
		}
		return null;
	}
	
	protected EdType getArcTypeByBasis(final Type baseType) {
		for (int i=0; i<this.arcTypes.size(); i++) {
			final EdType t = this.arcTypes.get(i);
			if (t.getBasisType() == baseType)
				return t;
		}
		return null;
	}
	
	public EdType getNodeType(Type baseType, String name, int shape, Color color, boolean filledShape) {
		EdType t = null;
		Type bt = null;
		if (this.bTypeSet.containsType(baseType)) 
			bt = baseType;
		if (bt != null) {
			t = createNodeType(bt, name, shape, color, filledShape, "");
			// t is null, if nodeType already exists
		} else { // baseType does not exist
			// create nodeType and copy attribute type
			t = createNodeType(name, shape, color, filledShape, "");
			if (t != null) {
				DeclTuple decl = (DeclTuple) baseType.getAttrType();
				if (decl != null) {
					t.getBasisType().createAttributeType();
					AttrHandler javaHandler = agg.attribute.facade.impl.DefaultInformationFacade
							.self().getJavaHandler();
					int num = decl.getNumberOfEntries();
					for (int i = 0; i < num; i++) {
						DeclMember mem = (DeclMember) decl.getMemberAt(i);
						t.getBasisType().getAttrType().addMember(javaHandler,
								mem.getTypeName(), mem.getName());
					}
				}
			}
		}
		if (t == null) {
			t = getType(this.nodeTypes, name, shape, color, filledShape);
		}
		return t;
	}

	/**
	 * Creates and adds a new edge type to the type set.
	 */
	public EdType createArcType(String name, int shape, Color color, boolean filledShape) {
		Type bType = null;
		EdType eType = null;
		if (isNewType(this.arcTypes, name, shape, color, filledShape)) {
			if (this.bTypeSet != null) {
//				bType = this.bTypeSet.createType();
				bType = this.bTypeSet.createArcType(false);
				eType = new EdType(name, shape, color, filledShape, "", bType);
			} else {
				eType = new EdType(name, shape, color, filledShape, "");
			}
			addAlphabeticalSorted(eType, this.arcTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.ARC_CREATED));
		}
		return eType;
	} 

	public EdType createArcType(String name, int shape, Color color) {
		return createArcType(name, shape, color, false);
	} 
	
	/** Creates and adds a new edge type to the type set. */
	public EdType createArcType(Type baseType) {
		if (baseType == null) {
			return null;
		}

		if (baseType.getAdditionalRepr().equals("")) {
			return createDefaultArcType(baseType);
		}

		/* shape as String */
		String shapeStr = ""; 
		/* color as String */
		String colorStr = ""; 	
		String filledStr = "";
		
		/* shape as int */
		int shape = -1; 

		Vector<String> v = getAdditionalReprOfBasisType(baseType);
		shapeStr = v.elementAt(0);
		colorStr = v.elementAt(1);
		filledStr = v.elementAt(2);
		
		if (shapeStr.equals("SOLID_LINE"))
			shape = EditorConstants.SOLID;
		else if (shapeStr.equals("DASH_LINE"))
			shape = EditorConstants.DASH;
		else if (shapeStr.equals("DOT_LINE"))
			shape = EditorConstants.DOT;
		else
			shape = EditorConstants.SOLID;
		/* color */
		String r_str = colorStr.substring(colorStr.indexOf("r=") + 2, colorStr
				.indexOf(",g="));

		String g_str = colorStr.substring(colorStr.indexOf("g=") + 2, colorStr
				.indexOf(",b="));

		String b_str = colorStr.substring(colorStr.indexOf("b=") + 2, colorStr
				.indexOf("]"));

		Color color = new Color((Integer.valueOf(r_str)).intValue(), (Integer.valueOf(
				g_str)).intValue(), (Integer.valueOf(b_str)).intValue());
		
		boolean filled = filledStr.equals("BOLD");
		
		EdType eType = new EdType(baseType, shape, color, filled, "");
		
		addAlphabeticalSorted(eType, this.arcTypes);
//		fireTypeEvent(new TypeEvent(this, eType, TypeEvent.ARC_CREATED));
		
		return eType;
	} 

	/** Returns the default edge type. */
	public EdType getDefaultArcType() {
		return this.defaultArcType;
	} 

	/**
	 * Creates and adds the default edge type for the specified basis type.
	 */
	public EdType createDefaultArcType(Type baseType) {
		if (baseType == null) {
			return this.defaultArcType;
		}

		EdType eType = null;
		if (isNewType(this.arcTypes, baseType.getStringRepr(),
				EditorConstants.SOLID, Color.black)) {
			eType = new EdType(baseType, EditorConstants.SOLID, Color.black, false, "");
			// this.arcTypes.addElement(eType);
			addAlphabeticalSorted(eType, this.arcTypes);
			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.ARC_CREATED));
		} else {
			eType = getType(this.arcTypes, baseType.getStringRepr(),
					EditorConstants.SOLID, Color.black, false);
		}
		return eType;
	} 

	/** Creates and adds a new edge type to the type set. */
	public EdType createArcType(Type baseType, String name, int shape,
			Color color) {
		if (isNewType(this.arcTypes, name, shape, color, false)) {
			EdType eType = new EdType(name, shape, color, false, "", baseType);
			addAlphabeticalSorted(eType, this.arcTypes);
//			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.ARC_CREATED));
			return eType;
		} 
		return null;
	} 

	public EdType createArcType(Type baseType, String name, int shape,
			Color color, boolean bold) {
		if (isNewType(this.arcTypes, name, shape, color, bold)) {
			EdType eType = new EdType(name, shape, color, bold, "", baseType);
			addAlphabeticalSorted(eType, this.arcTypes);
//			fireTypeEvent(new TypeEvent(this, eType, TypeEvent.ARC_CREATED));
			return eType;
		} 
		return null;
			
	}
	
	/** Adds an edge type. */
	public void addArcType(EdType t) {
		addAlphabeticalSorted(t, this.arcTypes);
		
		fireTypeEvent(new TypeEvent(this, t, TypeEvent.ARC_CREATED));
	}

	public boolean containsArcType(String name, int shape, Color color) {
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType t = this.arcTypes.get(i);
			if (t.getName().equals(name) && t.getShape() == shape
					&& t.getColor().equals(color))
				return true;
		}
		return false;
	}

	public EdType getArcType(Type baseType, String name, int shape, Color color, boolean bold) {
		EdType t = null;
		Type bt = null;
		if (this.bTypeSet.containsType(baseType))
			bt = baseType;
		if (bt != null) {
			t = createArcType(bt, name, shape, color, bold);
			// t is null, if arcType already exists
		} else {// baseType does not exist
			// create arcType and copy attribute type
			t = createArcType(name, shape, color, bold);
			if (t != null) {
				DeclTuple decl = (DeclTuple) baseType.getAttrType();
				if (decl != null) {
					t.getBasisType().createAttributeType();
					AttrHandler javaHandler = agg.attribute.facade.impl.DefaultInformationFacade
							.self().getJavaHandler();
					int num = decl.getNumberOfEntries();
					for (int i = 0; i < num; i++) {
						DeclMember mem = (DeclMember) decl.getMemberAt(i);
						t.getBasisType().getAttrType().addMember(javaHandler,
								mem.getTypeName(), mem.getName());
					}
				}
			}
		}
		if (t == null) // das sollte eigentlich nicht vorkommen!
			t = getType(this.arcTypes, name, shape, color, bold);
		return t;
	}

	private int addAlphabeticalSorted(EdType type, Vector<EdType> types) {
		int indx = -1;
		String name = type.getName();
		if (types.isEmpty()) {
			types.add(type);
		} else {
			for (int i = 0; i < types.size(); i++) {
				EdType t = types.get(i);
				String n = t.getName();
				if (name.compareTo(n) < 0) {
					types.add(i, type);
					break;
				} else if (name.compareTo(n) == 0) {
					if ((i + 1) < types.size())
						types.add(i + 1, type);
					break;
				}
			}
			if (types.indexOf(type) == -1)
				types.add(type);
		}

		indx = types.indexOf(type);
		return indx;
	}

	/** Checks whether the type set is empty */
	public boolean isEmpty() {
		if (this.nodeTypes.isEmpty() && this.arcTypes.isEmpty()) {
			return true;
		} 
		return false;
	}

	public boolean hasTypeKeyChanged( ){
		return this.typeKeyChanged;
	}
	
	public void unsetTypeKeyChanged(){
		this.typeKeyChanged = false;	
		this.unsetTypeKeyChangedOfAllTypes();
	}
	
	/** Returns all node types. */
	public Vector<EdType> getNodeTypes() {
		return this.nodeTypes;
	} 

	/** Sets node types. */
	public void setNodeTypes(Vector<EdType> newNodeTypes) {
		this.nodeTypes.clear();
		this.nodeTypes.addAll(newNodeTypes);
	} 

	/** Returns all edge types. */
	public Vector<EdType> getArcTypes() {
		return this.arcTypes;
	} 

	/** Sets edge types. */
	public void setArcTypes(Vector<EdType> newArcTypes) {
		this.arcTypes.clear();
		this.arcTypes.addAll(newArcTypes);
	} 

	/**
	 * Returns currently selected node type. The selected type will be used for
	 * the typing of nodes.
	 */
	public EdType getSelectedNodeType() {
		return this.selectedNodeType;
	} 

	/** Selects a node type. */
	public void setSelectedNodeType(EdType et) {
		this.selectedNodeType = et;
	} 

	/**
	 * Returns currently selected edge type. The selected type will be used for
	 * the typing of edges.
	 */
	public EdType getSelectedArcType() {
		return this.selectedArcType;
	} 

	/** Selects an edge type. */
	public void setSelectedArcType(EdType et) {
		this.selectedArcType = et;
	}

/*
	private boolean isTypeUsedInGraph(Type t, Graph g) {
		for(Iterator<Node> iter = g.getNodesSet().iterator(); iter.hasNext();) {
			if (iter.next().getType().compareTo(t)) {
				return true;
			}
		}
		for(Iterator<Arc> iter = g.getArcsSet().iterator(); iter.hasNext();) {
			if (iter.next().getType().compareTo(t)) {
				return true;
			}
		}
		return false;
	}
*/
	
	/** Renames a layout type specified by the EdType aType, String newName */
	public void renameType(EdType aType, String newName) {
		aType.setName(newName);
	}

	/** Redefines a layout type specified by the EdType aType */
	public boolean redefineType(EdType aType, String newName, int newShape,
			Color newColor, boolean filled, String newImageFileName, String newComment) {
		aType.setTypeKeyChanged(false);
		if (aType.redefineType(newName, newShape, newColor, filled,
				newImageFileName, newComment)) {
			this.typeKeyChanged = aType.hasTypeKeyChanged();
			return true;
		}
		
		return false;
	}
	
	private void unsetTypeKeyChangedOfAllTypes() {
		for (int i=0; i<this.nodeTypes.size(); i++) {
			this.nodeTypes.get(i).setTypeKeyChanged(false);
		}
		for (int i=0; i<this.arcTypes.size(); i++) {
			this.arcTypes.get(i).setTypeKeyChanged(false);
		}
	}
	
	public void removeType(EdType aType) throws TypeException {
		if (aType.getAdditionalReprOfBasisType().contains("[NODE]")) {
			removeNodeType(aType);
		} else if (aType.getAdditionalReprOfBasisType().contains("[EDGE]")) {
			removeArcType(aType);
		}
	}

	/** Removes a node type specified by the EdType aType */
	public void removeNodeType(EdType aType) throws TypeException {
		if (!this.nodeTypes.contains(aType))
			throw new TypeException(
					"Cannot delete this type from the node type list! \nThe type:  <"
							+ aType.getName() + ">  is not a node type!");
		try {			
			this.bTypeSet.destroyType(aType.getBasisType());
			
			this.nodeTypes.removeElement(aType);
			this.removeNodeTypeUsers(aType);
			
		} catch (TypeException ex) {
			throw ex;
		}
	}

	/** Removes an edge type specified by the EdType aType */
	public void removeArcType(EdType aType) throws TypeException {
		if (!this.arcTypes.contains(aType))
			throw new TypeException(
					"Cannot delete this type from the edge type list! \nThe type:  <"
							+ aType.getName() + ">  is not an edge type!");

		try {			
			this.bTypeSet.destroyType(aType.getBasisType());
			
			this.removeArcTypeUsers(aType);
			this.arcTypes.removeElement(aType);
			
		} catch (TypeException ex) {
			throw ex;
		}
	}


	/**
	 * internal method to initialize types from a new type set. The types from
	 * the type manager will get here the default layout. If a type graph is
	 * defined it will also wraped with a layout.
	 */
	private void initTypesFromTypeSet() {
		this.nodeTypes.clear();
		this.arcTypes.clear();
		Enumeration<Type> types = this.bTypeSet.getTypes();
		while (types.hasMoreElements()) {
			Type t = types.nextElement();
			if (t.getStringRepr().equals("")
					&& t.getAdditionalRepr().equals("")) {
				// remove "empty" types
				try {
					this.bTypeSet.destroyType(t);
				} catch (TypeException e) {
					System.out.println("EdTypeSet: " + e.getMessage());
				}
			} else {
				this.createType(t);
			}
		}

		// check type graph
		Graph typeGraph = this.bTypeSet.getTypeGraph();
		if ((typeGraph != null)
				&& (this.edTypeGraph == null || typeGraph != this.edTypeGraph
						.getBasisGraph())) {
			this.edTypeGraph = new EdGraph(typeGraph, this);
			this.edTypeGraph.markTypeGraph(true);
		}
	}
	
	public void refreshAttrInstances() {
		for (int i=0; i<this.arcTypes.size(); i++) {
			EdType t = this.arcTypes.get(i);
			boolean typeHasAttrs = t.getBasisType().hasAnyAttrMember();
			if (t.hasAttrTypeChanged()) {
				List<EdGraphObject> list = this.getTypeUsers(t);
				for (int j=0; j<list.size(); j++) {
					EdArc go = (EdArc)list.get(j);
					if (go.getBasisArc() != null && go.getBasisArc().getType() != null) {
						if (typeHasAttrs) {
							if (go.getBasisArc().getAttribute() == null) {
								go.getBasisArc().createAttributeInstance();
							}
						} else {
							if (go.getBasisArc().getAttribute() != null) {
								go.getBasisArc().disposeAttributeInstance();
							}
						}
					} else {
						list.remove(j);
						j--;
					}
				}
			}
			t.setAttrTypeChanged(false);
			if (!typeHasAttrs) {
				t.getBasisType().removeAttributeType();
			}
		}
		for (int i=0; i<this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.get(i);
			boolean typeHasAttrs = t.getBasisType().hasAnyAttrMember();
			if (t.hasAttrTypeChanged()) {
				List<EdGraphObject> list = this.getTypeUsers(t);
				for (int j=0; j<list.size(); j++) {
					EdNode go = (EdNode)list.get(j);
					if (go.getBasisNode() != null && go.getBasisNode().getType() != null) {
						if (typeHasAttrs) {
							if (go.getBasisNode().getAttribute() == null) {
								go.getBasisNode().createAttributeInstance();
							}
						} else {
							if (go.getBasisNode().getAttribute() != null) {
								go.getBasisNode().disposeAttributeInstance();
							}
						}
					} else {
						list.remove(j);
						j--;
					}
				}
			}
			t.setAttrTypeChanged(false);
			if (!typeHasAttrs) {
				t.getBasisType().removeAttributeType();
			}
		}
	}
	
	public void refreshTypeUsersAfterAttrTypeChanged() {
		for (int i=0; i<this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.get(i);
			if (t.hasAttrTypeChanged()) {
				List<EdGraphObject> list = this.getTypeUsers(t);
				for (int j=0; j<list.size(); j++) {
					EdNode go = (EdNode)list.get(j);
					if (go.getBasisNode() != null) {
						if (go.getBasisNode().getAttribute() == null) {
							go.getBasisNode().createAttributeInstance();
						}
					} else {
						list.remove(j);
						j--;
					}
				}
			}
			t.setAttrTypeChanged(false);
		}
		for (int i=0; i<this.arcTypes.size(); i++) {
			EdType t = this.arcTypes.get(i);
			if (t.hasAttrTypeChanged()) {
				List<EdGraphObject> list = this.getTypeUsers(t);
				for (int j=0; j<list.size(); j++) {
					EdArc go = (EdArc)list.get(j);
					if (go.getBasisArc() != null) {
						if (go.getBasisArc().getAttribute() == null) {
							go.getBasisArc().createAttributeInstance();
						}
					} else {
						list.remove(j);
						j--;
					}
				}
			}
			t.setAttrTypeChanged(false);
		}
	}
	
	public void refreshTypes() {
		boolean hasChanged = false;
		Enumeration<Type> types = this.bTypeSet.getTypes();
		while (types.hasMoreElements()) {
			Type t = types.nextElement();
			if (getType(t) == null) {
				if (t.getAdditionalRepr().indexOf(":[NODE]:") >= 0) {
					createNodeType(t);
					hasChanged = true;
				} else if (t.getAdditionalRepr().indexOf(":[EDGE]:") >= 0) {
					createArcType(t);
					hasChanged = true;
				}
			}
		}
		if (hasChanged)
			fireTypeEvent(new TypeEvent(this, TypeEvent.REFRESH));
	}

	public void refreshTypes(boolean byNameOnly) {
		boolean hasChanged = false;
		Enumeration<Type> types = this.bTypeSet.getTypes();
		while (types.hasMoreElements()) {
			Type t = types.nextElement();
			EdType type = getType(t);
			if (type == null && byNameOnly)
				type = getTypeForName(t);
			if (type == null) {
				if (t.getAdditionalRepr().indexOf(":[NODE]:") >= 0) {
					createNodeType(t);
					hasChanged = true;
				} else if (t.getAdditionalRepr().indexOf(":[EDGE]:") >= 0) {
					createArcType(t);
					hasChanged = true;
				}
			}
		}
		if (hasChanged)
			fireTypeEvent(new TypeEvent(this, TypeEvent.REFRESH));
	}

	/**
	 * Checks whether a combination specified by : String name, int shape, Color
	 * color provides a new layout type under types specified by the Vector
	 * types.
	 */
	public boolean isNewType(Vector<EdType> types, String name, int shape, Color color) {
		boolean result = true;
		for (int i = 0; i < types.size(); i++) {
			EdType et = types.elementAt(i);
			if (et.name.equals(name)
					&& et.shape == shape
					&& et.color.equals(color)) {
				result = false;
				break;
			}
		}
		return result;
	}

	public boolean isNewType(Vector<EdType> types, String name, int shape, Color color, boolean filledShape) {
		boolean result = true;
		for (int i = 0; i < types.size(); i++) {
			EdType et = types.elementAt(i);						
			if (et.name.equals(name)
					&& et.shape == shape
					&& et.color.equals(color)
					&& et.filled == filledShape) {
				result = false;
				break;
			}			
		}
		return result;
	}
	
	/**
	 * Checks whether a combination specified by : String name, int shape, Color
	 * color provides a new layout type among types specified by the Vector
	 * types.
	 */
	public boolean isNewType(Vector<EdType> types, EdType t) {
		if (t == null)
			return false;

		boolean result = true;
		for (int i = 0; i < types.size(); i++) {
			EdType et = types.elementAt(i);
			if (et.name.equals(t.getBasisType().getStringRepr())
					&& et.shape == t.getShape()
					&& et.color.equals(t.getColor())) {			
				result = false;
				break;
			} 
		}
		return result;
	}

	/**
	 * Sets the additional layout representation: "shape:color" of the base type
	 * if it is not set 
	 */
	public void setAdditionalReprOfBasisType() {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.elementAt(i);
			if ((t.getBasisType().getAdditionalRepr() == null)
					|| (t.getBasisType().getAdditionalRepr().equals(""))
					|| t.hasOldAdditionalRepr())
				t.setAdditionalReprOfBasisType();
		}
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType t = this.arcTypes.elementAt(i);
			if ((t.getBasisType().getAdditionalRepr() == null)
					|| t.getBasisType().getAdditionalRepr().equals("")
					|| t.hasOldAdditionalRepr())
				t.setAdditionalReprOfBasisType();
		}
	}

	protected void enrichAdditionalReprOfNodeType() {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			this.nodeTypes.get(i).enrichAdditionalRepr();			
		}
	}
	
	/**
	 * Checks the settings of the layout in the base types. Returns FALSE if an
	 * error on the additional layout.
	 */
	public boolean basisTypeReprComplete() {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.elementAt(i);
			if ((t.getBasisType().getAdditionalRepr() == null)
					|| t.getBasisType().getAdditionalRepr().equals("")
					|| t.hasOldAdditionalRepr())
				return false;
		}
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType t = this.arcTypes.elementAt(i);
			if ((t.getBasisType().getAdditionalRepr() == null)
					|| (t.getBasisType().getAdditionalRepr().equals(""))
					|| t.hasOldAdditionalRepr())
				return false;
		}
		return true;
	}

	/**
	 * Checks whether a layout type specified by name, shape, color
	 * is under types specified by the Vector types and returns this type
	 * 
	 * @deprecated replaced by 
	 * 		getType(Vector<EdType> types, String name, int shape, Color color, boolean filledShape)
	 */
	public EdType getType(Vector<EdType> types, String name, int shape, Color color) {
		for (int i = 0; i < types.size(); i++) {
			EdType et = types.elementAt(i);
			if (et.name.equals(name) 
					&& et.shape == shape
					&& et.color.equals(color))
				return et;
		}
		return null;
	}

	/**
	 * Checks whether a layout type specified by name, shape, color and filledShape 
	 * is under types specified by the Vector types and returns this type
	 */
	public EdType getType(Vector<EdType> types, String name, int shape, Color color, boolean filledShape) {
		for (int i = 0; i < types.size(); i++) {
			EdType et = types.elementAt(i);
			if (et.name.equals(name) 
					&& et.shape == shape
					&& et.color.equals(color)
					&& et.filled == filledShape)
				return et;
		}
		return null;
	}
	
	public EdType getType(Type t) {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType et = this.nodeTypes.elementAt(i);
			if (et.getBasisType() == t || et.getBasisType().compareTo(t))
				return et;
		}
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType et = this.arcTypes.elementAt(i);
			if (et.getBasisType() == t || et.getBasisType().compareTo(t))
				return et;
		}
		return null;
	}

	public EdType getNodeType(Type t) {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType et = this.nodeTypes.elementAt(i);
			if (et.getBasisType().compareTo(t))
				return et;
		}
		return null;
	}

	public EdType getArcType(Type t) {
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType et = this.arcTypes.elementAt(i);
			if (et.getBasisType().compareTo(t))
				return et;
		}
		return null;
	}

	public EdType getTypeForName(Type t) {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType et = this.nodeTypes.elementAt(i);
			if (et.getBasisType().getName().equals(t.getName()))
				return et;
		}
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType et = this.arcTypes.elementAt(i);
			if (et.getBasisType().getName().equals(t.getName()))
				return et;
		}
		return null;
	}

	public EdType getTypeForName(String tname) {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType et = this.nodeTypes.elementAt(i);
			if (et.getName().equals(tname))
				return et;
		}
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType et = this.arcTypes.elementAt(i);
			if (et.getName().equals(tname))
				return et;
		}
		return null;
	}

	public EdType getNodeTypeForName(String tname) {
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType et = this.nodeTypes.elementAt(i);
			if (et.getName().equals(tname))
				return et;
		}
		return null;
	}
	
	public EdType getArcTypeForName(String tname) {
		for (int i = 0; i < this.arcTypes.size(); i++) {
			EdType et = this.arcTypes.elementAt(i);
			if (et.getName().equals(tname))
				return et;
		}
		return null;
	}
	
	/**
	 * Returns additional representation of a base type as a vector with
	 * elements of the String type. First element is a shape string, second - a
	 * color string, third - a marking string, wich can be "[NODE]", "[EDGE]" or
	 * "[]".
	 */
	public Vector<String> getAdditionalReprOfBasisType(Type baseType) {
		Vector<String> v = new Vector<String>();
		String addRepr = baseType.getAdditionalRepr();
		
		String shapeStr = "";
		String colorStr = "";
		String filledStr = "";
		String imageFileNameStr = "";

		String markStr = "[]";
		if (addRepr.equals("[NODE]") || addRepr.equals("[EDGE]")) {
			markStr = addRepr.toString();
			addRepr = "";
		}

		String[] test = addRepr.split(":");
		for (int i=0; i<test.length; i++) {
			String testStr = test[i];
			if ((testStr.indexOf("RECT") != -1)
					|| (testStr.indexOf("ROUND") != -1)
					|| (testStr.indexOf("CIRCLE") != -1)
					|| (testStr.indexOf("OVAL") != -1)
					|| (testStr.indexOf("IMAGE") != -1)
					|| (testStr.indexOf("SOLID_LINE") != -1)
					|| (testStr.indexOf("DASH_LINE") != -1)
					|| (testStr.indexOf("DOT_LINE") != -1)) {
				shapeStr = testStr;
			} else if (testStr.indexOf("Color") != -1) {
				colorStr = testStr;
			} else if ((testStr.indexOf("FILLED") != -1)
					|| (testStr.indexOf("BOLD") != -1)) {
				filledStr = testStr;
			} else if ((testStr.indexOf(".jpg") != -1)
					|| (testStr.indexOf(".gif") != -1)
					|| (testStr.indexOf(".xpm") != -1)) {
				imageFileNameStr = testStr;
			} else if ((testStr.indexOf("[NODE]") != -1)
					|| (testStr.indexOf("[EDGE]") != -1)) {
				markStr = testStr;
			}			
		}
		
		if (shapeStr.equals("")) {
			if (markStr.equals("[NODE]"))
				shapeStr = "RECT";
			else if (markStr.equals("[EDGE]"))
				shapeStr = "SOLID_LINE";
		}
		if (colorStr.equals(""))
			colorStr = "java.awt.Color[r=0,g=0,b=0]";

		imageFileNameStr = baseType.getImageFilename();
		
		v.add(shapeStr);
		v.add(colorStr);
		v.add(filledStr);
		v.add(imageFileNameStr);
		v.add(markStr);

		return v;
	}

	/**
	 * Allows to show nodes like icons, if the node type has an icon
	 * representation.
	 */
	public void setNodeIconable(boolean iconableNode) {
		this.iconable = iconableNode; // true;
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.elementAt(i);
			t.setIconable(iconableNode);
		}
	}

	/**
	 * Sets the path of type resources (gif or jpg files) to the specified path
	 */
	public void setResourcesPath(String path) {
		this.resourcesPath = path;
		for (int i = 0; i < this.nodeTypes.size(); i++) {
			EdType t = this.nodeTypes.elementAt(i);
			t.setResourcesPath(this.resourcesPath);
		}
	}

	/** Adds a new type event listener. */
	public synchronized void addTypeEventListener(TypeEventListener l) {
		if (!this.typeEventListeners.contains(l)) {
			this.typeEventListeners.addElement(l);
		}
	}

	/** Removes the type event listener. */
	public synchronized void removeTypeEventListener(TypeEventListener l) {
		if (this.typeEventListeners.contains(l)) {
			this.typeEventListeners.removeElement(l);
		}
	}

	public void fireTypeChangedEvent() {
		fireTypeEvent(new TypeEvent(this, TypeEvent.CHANGED));
	}

	/** Sends a type event to the all my listeners. */
	private void fireTypeEvent(TypeEvent e) {
		if (this.typeEventListeners != null) {
			Vector<TypeEventListener> listeners = this.typeEventListeners;
			for (int i = 0; i < listeners.size(); i++) {
				listeners.elementAt(i).typeEventOccurred(e);
			}
		}
	}

	public void setAttrTypeChanged(final Type t, boolean b) {
		this.getType(t).setAttrTypeChanged(b);
		
		List<Type> list = t.getChildren();
		for (int i=0; i<list.size(); i++) {
			Type ch = list.get(i);
			this.getType(ch).setAttrTypeChanged(b);
		}
	}
	
	public void addTypeUser(final EdType t, final EdGraphObject go) {
		if (t.isNodeType()) {
			addTypeUserToTypeContainer(t, go, this.nodeTypeUsers);
			if (!go.isElementOfTypeGraph())
				addTypeUserToTypeGraphNodeContainer(t, go, this.typeGraphNodeUsers);
		} else if (t.isArcType()) {
			addTypeUserToTypeContainer(t, go, this.arcTypeUsers);
			if (!go.isElementOfTypeGraph())
				addTypeUserToTypeGraphArcContainer(t, go, this.typeGraphArcUsers);
		}
	}
	
	private void addTypeUserToTypeContainer(
			final EdType t, final EdGraphObject go, 
			final Hashtable<EdType,List<EdGraphObject>> container) {
		
		List<EdGraphObject> list = container.get(t);
		if (list == null) {
			list = new Vector<EdGraphObject>();
			container.put(t, list);
		}
		list.add(go);
	}
	
	private void addTypeUserToTypeGraphNodeContainer(
			final EdType t, final EdGraphObject go, 
			final Hashtable<EdType,List<EdGraphObject>> container) {
		
		if (t.getBasisType().getTypeGraphNode() != null) {
			List<EdGraphObject> list = container.get(t);
			if (list == null) {
				list = new Vector<EdGraphObject>();
				container.put(t, list);
			}
			list.add(go);
		}
	}
	
	private void addTypeUserToTypeGraphArcContainer(
			final EdType t, final EdGraphObject go, 
			final Hashtable<EdType,Hashtable<TypeGraphArc,List<EdGraphObject>>> container) {
		
		TypeGraphArc tga = this.getTypeGraphArc(
									t.getBasisType(), 
									((Arc)go.getBasisObject()).getSourceType(), 
									((Arc)go.getBasisObject()).getTargetType());
				
		if (tga != null) {	
			Hashtable<TypeGraphArc,List<EdGraphObject>> tCntnr = container.get(t);
			if (tCntnr == null) {
				tCntnr = new Hashtable<TypeGraphArc,List<EdGraphObject>>();
				container.put(t, tCntnr);
			}			
			List<EdGraphObject> list = tCntnr.get(tga);
			if (list == null) {
				list = new Vector<EdGraphObject>();
				tCntnr.put(tga, list);
			}
			list.add(go);
		}
	}
	
	private TypeGraphArc getTypeGraphArc(Type t, Type src, Type tar) {
		if (this.bTypeSet.getTypeGraph() != null) {
			Arc typeArc = ((TypeGraph)this.bTypeSet.getTypeGraph()).getTypeGraphArc(t, src, tar);
			if (typeArc != null)
				return t.getTypeGraphArc(typeArc.getSourceType(),  typeArc.getTargetType());
		}
		return null;
	}
	
	public void removeTypeUser(final EdType t, final EdGraphObject go) {
		if (t.isNodeType()) {
			removeTypeUserFromTypeContainer(t, go, this.nodeTypeUsers);
			removeTypeUserFromTypeGraphNodeContainer(t, go, this.typeGraphNodeUsers);
		} else if (t.isArcType()) {						
			removeTypeUserFromTypeContainer(t, go, this.arcTypeUsers);			
			removeTypeUserFromTypeGraphArcContainer(t, go, this.typeGraphArcUsers);
		}
	}
	
	public void removeArcTypeUser(final EdType t, final EdGraphObject go, final EdType nType) {
		if (t.isArcType()) {						
			removeTypeUserFromTypeContainer(t, go, this.arcTypeUsers);			
			removeTypeUserFromTypeGraphArcContainer(t, go, this.typeGraphArcUsers, nType);
		}
	}
	
	private void removeTypeUserFromTypeContainer(
			final EdType t, final EdGraphObject go, 
			final Hashtable<EdType,List<EdGraphObject>> container) {
		
		List<EdGraphObject> list = container.get(t);
		if (list != null) {
			list.remove(go);
			if (list.isEmpty())
				container.remove(t);
		}
	}
	
	private void removeTypeUserFromTypeGraphNodeContainer(
			final EdType t, final EdGraphObject go, 
			final Hashtable<EdType,List<EdGraphObject>> container) {
		
		if (t.getBasisType().getTypeGraphNode() != null) {
			List<EdGraphObject> list = container.get(t);
			if (list != null) {
				list.remove(go);
				if (list.isEmpty())
					container.remove(t);
			}
		}
	}
	
	private void removeTypeUserFromTypeGraphArcContainer(
			final EdType t, 
			final EdGraphObject go, 
			final Hashtable<EdType,Hashtable<TypeGraphArc,List<EdGraphObject>>> container) {
		
//		if (t.getBasisType().hasTypeGraphArc(((EdArc)go).getSource().getBasisObject().getType(), 
//				((EdArc)go).getTarget().getBasisObject().getType())) 
		if (this.edTypeGraph != null) {	
			TypeGraphArc tga = this.getTypeGraphArc(
					t.getBasisType(),
					((EdArc)go).getSource().getBasisObject().getType(), 
					((EdArc)go).getTarget().getBasisObject().getType());
			
			if (tga != null) {
				Hashtable<TypeGraphArc,List<EdGraphObject>> tCntnr = container.get(t);
				if (tCntnr != null) {					
					List<EdGraphObject> list = tCntnr.get(tga);
					if (list != null) {
						list.remove(go);
						if (list.isEmpty())
							tCntnr.remove(tga);
					} 
				}
			}
		}
	}

	private void removeTypeUserFromTypeGraphArcContainer(
			final EdType t, 
			final EdGraphObject go, 
			final Hashtable<EdType,Hashtable<TypeGraphArc,List<EdGraphObject>>> container,
			final EdType nType) {
		
		Type srcT = (((EdArc)go).getSource().getBasisObject() == null
				|| ((EdArc)go).getSource().getBasisObject().getType() == null)?
						nType.getBasisType():((EdArc)go).getSource().getBasisObject().getType();
		Type tarT = (((EdArc)go).getTarget().getBasisObject() == null
				|| ((EdArc)go).getTarget().getBasisObject().getType() == null)? nType.getBasisType():
					((EdArc)go).getTarget().getBasisObject().getType();

//		if (t.getBasisType().hasTypeGraphArc(srcT, tarT)) 
		if (this.edTypeGraph != null) {
			TypeGraphArc tga = this.getTypeGraphArc(t.getBasisType(), srcT, tarT);
			if (tga != null) {
				Hashtable<TypeGraphArc,List<EdGraphObject>> tCntnr = container.get(t);
				if (tCntnr != null) {					
					List<EdGraphObject> list = tCntnr.get(tga);
					if (list != null) {
						list.remove(go);
						if (list.isEmpty())
							tCntnr.remove(tga);
					} 
				}
			}
		}
	}
	
	public boolean hasTypeUser(final EdType t) {
		if (t.isNodeType()) {
			return (this.nodeTypeUsers.get(t) == null
						|| this.nodeTypeUsers.get(t).isEmpty())? false: true;
		} else if (t.isArcType()) {
			return (this.arcTypeUsers.get(t) == null
					|| this.arcTypeUsers.get(t).isEmpty())? false: true;
		} else
			return false;
	}
	
	public List<EdGraphObject> getTypeUsers(final EdType t) {
		List<EdGraphObject> list = new Vector<EdGraphObject>();
		if (t.isNodeType()
				&& this.nodeTypeUsers.get(t) != null) {
			list = this.nodeTypeUsers.get(t);	
		} else if (t.isArcType()
				&& this.arcTypeUsers.get(t) != null) {
			list = this.arcTypeUsers.get(t);
		} 
		return list;
	}
	
	/**
	 * Search for the outgoing edges of the parent type and checks whether they
	 * are in use or not.
	 * 
	 * @param parentType
	 * @return a set with used types of inherit edges or an empty set
	 */
	public Vector<Type> getTypeOfInheritedArcsInUse(final Type childType, final Type parentType) {
		Vector<Type> result = new Vector<Type>();
		Vector<Arc> inhArcs = this.bTypeSet.getInheritedArcs(parentType);
		for (int i = 0; i < inhArcs.size(); i++) {
			Type arcType = inhArcs.get(i).getType();
			EdType t = this.getArcType(arcType);
			List<EdGraphObject> users = this.arcTypeUsers.get(t);
			for ( int j=0; j<users.size(); j++) {
				EdGraphObject go = users.get(j);
				if (((Arc)go.getBasisObject()).getSourceType().compareTo(childType)) {
					if (!result.contains(arcType))
						result.add(arcType);
				}
			}
		}
		return result;
	}
	
	public Vector<EdArc> getTypeArcOfInheritedArcsInUse(final Type childType, final Type parentType) {
		Vector<EdArc> result = new Vector<EdArc>();
		Vector<Arc> inhArcs = this.bTypeSet.getInheritedArcs(parentType);
		for (int i = 0; i < inhArcs.size(); i++) {
			Type arcType = inhArcs.get(i).getType();
			EdType t = this.getArcType(arcType);
			List<EdGraphObject> users = this.arcTypeUsers.get(t);
			for ( int j=0; j<users.size(); j++) {
				EdGraphObject go = users.get(j);
				if (((Arc)go.getBasisObject()).getSourceType().compareTo(childType)) {
					if (!result.contains(arcType))
						result.add((EdArc)go);
				}
			}
		}
		return result;
	}
	
	public boolean isTypeUsed(final EdType t) {
		if (this.hasTypeUser(t))
			return true;
		else if (this.isChildTypeUsed(t))
			return true;
		else
			return false;
	}

	private boolean isChildTypeUsed(final EdType t) {
		Vector<Type> allChildren = t.getBasisType().getAllChildren();
		if (!allChildren.isEmpty()) {
			for (int i = 1; i < allChildren.size(); i++) {
				Type ch = allChildren.get(i);
				if (this.hasTypeUser(this.getType(ch)))
					return true;
			}
		}
		return false;
	}
	
	public boolean isTypeGraphNodeUsed(final EdType t) {
		if (t.isNodeType() && t.getBasisType().getTypeGraphNode() != null) {
			List<EdGraphObject> list = this.typeGraphNodeUsers.get(t.getBasisType().getTypeGraphNode());
//			System.out.println(t.getTypeName()+"  used by:   "+list);
			if (list != null
					&& !list.isEmpty()) {
				if (list.size() == 1
						&& list.get(0).isElementOfTypeGraph()) {
					return false;
				} 
				return true;
			}
		} 
		return false;
	}
	
	public boolean isChildTypeGraphNodeUsed(final EdType t) {
		Vector<Type> allChildren = t.getBasisType().getAllChildren();
		if (!allChildren.isEmpty()) {
			for (int i = 1; i < allChildren.size(); i++) {
				Type ch = allChildren.get(i);
				EdType et = this.getNodeTypeByBasis(ch);
				if (et != null && this.isTypeGraphNodeUsed(et))
					return true;
			}
		}
		return false;
	}
	
	public boolean isTypeGraphArcUsed(final EdType t, final EdType src, final EdType tar) {
		if (t.isArcType() && this.edTypeGraph != null) {
			TypeGraphArc tga = this.getTypeGraphArc(t.getBasisType(), src.getBasisType(), tar.getBasisType());
			if (tga != null) {
				Hashtable<TypeGraphArc,List<EdGraphObject>> tCntnr = this.typeGraphArcUsers.get(t);	
				if (tCntnr != null) {
					List<EdGraphObject> list = tCntnr.get(tga);	
					if (list != null && !list.isEmpty()) {						
						if (list.size() == 1 && list.get(0).isElementOfTypeGraph()) {
							return false;
						} 
						return true;
					} 
				}
			}
		}
		return false;
	}
	
	public boolean isClanUsed(final Type t) {
		boolean used = false;
		List<Type> clan = this.bTypeSet.getClan(t);
		for (int i = 0; i < clan.size() && !used; i++) {
			Type ch = clan.get(i);
			EdType et = this.getNodeTypeByBasis(ch);
			if (et != null && this.isTypeGraphNodeUsed(et)) {
				used = true;
			}
		}
		return used;
	}
	
	public boolean isArcDirected() {
		return this.bTypeSet.isArcDirected();
	}
	
	public boolean isArcParallel() {
		return this.bTypeSet.isArcParallel();
	}
	
	private void removeNodeTypeUsers(EdType t) {
		if (this.nodeTypeUsers.get(t) != null) {
			this.nodeTypeUsers.get(t).clear();
			this.nodeTypeUsers.remove(t);
		}
		if (this.typeGraphNodeUsers.get(t) != null) {
			this.typeGraphNodeUsers.get(t).clear();
			this.typeGraphNodeUsers.remove(t);
		}
	}
	
	private void removeArcTypeUsers(EdType t) {
		if (this.arcTypeUsers.get(t) != null) {
			this.arcTypeUsers.get(t).clear();
			this.arcTypeUsers.remove(t);
		}
		if (this.typeGraphArcUsers.get(t) != null) {
			Iterator<List<EdGraphObject>> 
			iter = this.typeGraphArcUsers.get(t).values().iterator();
			while (iter.hasNext()) {
				iter.next().clear();
			}
			this.typeGraphArcUsers.remove(t);
		}
	}
}
