package agg.xt_basis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrType;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.DeclTuple;
import agg.util.XMLHelper;

/**
 * Implements the type of an edge graph object.
 * 
 * @author $Author: olga $
 * @version $Id: ArcTypeImpl.java,v 1.22 2010/11/06 18:34:59 olga Exp $
 */
public class ArcTypeImpl implements Type {

	String comment = "";
	
	/**
	 * the name of the type
	 */
	String itsStringRepr;

	/**
	 * the attributes of the type
	 */
	AttrType itsAttrType;

	/**
	 * an additional String for special informations. It will be saved together
	 * with {@link #itsStringRepr} as the name and can contain any information
	 * as string. It is used for example for the layout information of
	 * {@link agg.editor.EdType}.
	 */
	String additionalRepr;

	String imageFileName = "";
	
	/**
	 * a map to a vector of types. This map will only created for edge types. If
	 * there is an edge between sourceType and targetType
	 * edgeTypeGraphObjects.get(sourceType).contains(targetType) will return true.
	 */
	HashMap<Type, HashMap<Type, TypeGraphArc>> edgeTypeGraphObjects;

	/**
	 * this value will be true, 
	 * if a graph object inside of a type graph was defined.
	 */
	boolean typeGraphObjectDefined;

	String keyStr = null;
	
	
	protected ArcTypeImpl() {
		this.itsAttrType = null;
		this.itsStringRepr = "";
		this.additionalRepr = ":SOLID_LINE:java.awt.Color[r=0,g=0,b=0]::[EDGE]:";
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * Creates a new type with the given name. There is still not attributed type.
	 * 
	 * @param name
	 *            the name of the type
	 */
	protected ArcTypeImpl(String name) {
		this();
		this.itsStringRepr = name;
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * Creates a new type with the given attributes and the given name.
	 * 
	 * @param at
	 *            the declaration of the attributes
	 * @param name
	 *            the name of the type
	 */
	protected ArcTypeImpl(AttrType at, String name) {
		this();
		this.itsAttrType = at;
		this.itsStringRepr = name;
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * creates a new type with the given attributes and an empty name.
	 * 
	 * @param at
	 *            the declaration of the attributes
	 */
	protected ArcTypeImpl(AttrType at) {
		this();
		this.itsAttrType = at;
	}

	public void dispose() {
		this.itsAttrType = null;
		if (this.edgeTypeGraphObjects != null) {
			Iterator<HashMap<Type, TypeGraphArc>> 
			iter = this.edgeTypeGraphObjects.values().iterator();
			while (iter.hasNext()) {
				HashMap<Type, TypeGraphArc> map = iter.next();
				Iterator<TypeGraphArc> iter1 = map.values().iterator();
				while (iter1.hasNext()) {
					iter1.next().dispose();
				}
			}
			this.edgeTypeGraphObjects.clear();
			this.edgeTypeGraphObjects = null;
		}
		
		this.typeGraphObjectDefined = false;
	}
	
	public void finalize() {}
	
	public void createAttributeType() {
		this.itsAttrType = agg.attribute.impl.AttrTupleManager.getDefaultManager()
				.newType();
	}

	public void setAttributeType(final AttrType at) {
		this.itsAttrType = at;
	}

	public void removeAttributeType() {
		if (this.itsAttrType != null) {
			((DeclTuple)this.itsAttrType).dispose();
			this.itsAttrType = null;
		}
	}
	
	public boolean isAttrTypeEmpty() {
		return (this.getAttrType() == null
//					|| this.getAttrType().getNumberOfEntries() == 0
					);
	}
	
	public boolean hasAnyAttrMember() {
		return (this.getAttrType() != null
				&& this.getAttrType().getNumberOfEntries() != 0);
	}
	
	public boolean isNodeType() {
		return false;
	}

	public boolean isArcType() {
		return true;
	}

	/**
	 * Returns result string of <code>this.getStringRepr()+this.getAdditionalRepr()</code>
	 * 
	 * @see <code>getStringRepr()</code> and <code>getAdditionalRepr()</code>
	 */
	public String convertToKey() {
//		return itsStringRepr.concat("%").concat(additionalRepr);
		
		if (this.keyStr == null) {
			this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
		}
		return this.keyStr;
	}

	/**
	 * Adds those attribute members of the specified Type type which are not
	 * found in this type. A conflict can arise when a new member and an
	 * existing member have equal names but different types. In this case the
	 * name of the existing attribute member will be extended by "?" and then
	 * the new attribute member will be added.
	 */
	public void adaptTypeAttribute(final Type type) {
		if (type.getAttrType() == null)
			return;
		if (this.itsAttrType == null)
			this.itsAttrType = agg.attribute.impl.AttrTupleManager
					.getDefaultManager().newType();
		DeclTuple declTuple = (DeclTuple) this.itsAttrType;
		DeclTuple otherTuple = (DeclTuple) type.getAttrType();
		for (int i = 0; i < otherTuple.getSize(); i++) {
			DeclMember otherMem = (DeclMember) otherTuple.getMemberAt(i);
			if (otherMem.getHoldingTuple() != otherTuple)
				continue;
			String otherName = otherMem.getName();
			String otherType = otherMem.getTypeName();
			boolean nameFound = false;
			boolean conflict = false;
			DeclMember mem = null;
			for (int j = 0; j < declTuple.getSize(); j++) {
				mem = (DeclMember) declTuple.getMemberAt(j);
				if (mem.getHoldingTuple() != declTuple)
					continue;
				if (mem.getName().equals(otherName)) {
					nameFound = true;
					if (!mem.getTypeName().equals(otherType)) {
						conflict = true;
					} else
						mem = null;
					break;
				} 
				mem = null;
			}
			if (nameFound && conflict && mem != null
					&& (mem.getHoldingTuple() == declTuple)) {
				mem.setName(mem.getName() + "?");
				declTuple.addMember(
						agg.attribute.facade.impl.DefaultInformationFacade
								.self().getJavaHandler(), otherType, otherName);
			} else if (!nameFound) {
				declTuple.addMember(
						agg.attribute.facade.impl.DefaultInformationFacade
								.self().getJavaHandler(), otherType, otherName);
			}
		}
		return;
	}

	public void checkDoubleAttributeType() {
		if (this.itsAttrType == null)
			return;
		DeclTuple declTuple = (DeclTuple) this.itsAttrType;
		for (int i = 0; i < declTuple.getSize(); i++) {
			DeclMember memi = (DeclMember) declTuple.getMemberAt(i);
			String n = memi.getName();
//			String t = memi.getTypeName();
			boolean nameFound = false;
			boolean conflict = false;
			DeclMember memj = null;
			for (int j = i + 1; j < declTuple.getSize(); j++) {
				memj = (DeclMember) declTuple.getMemberAt(j);
				if (memj.getName().equals(n)) {
					nameFound = true;
					conflict = true;
				}
				if (nameFound && conflict) {
					memj.setName(memj.getName() + "?");
				}
				nameFound = false;
				conflict = false;
			}
		}
	}

	/**
	 * Returns TRUE if this type is equal to the type t.
	 */
	public boolean compareTo(final Type t) {
		if (!getStringRepr().equals(t.getStringRepr())) {
			return false;
		} 
		if (!getAdditionalRepr().equals(t.getAdditionalRepr())) {
			return false;
		} 
		if (this.itsAttrType != null) {
			if (t.getAttrType() == null
					|| ((DeclTuple) this.itsAttrType).getSize() 
							!= ((DeclTuple) t.getAttrType()).getSize()
					|| !((DeclTuple) this.itsAttrType).weakcompareTo(t.getAttrType())) {
				return false;
			} 
			return true;
		} 
		if (t.getAttrType() != null) {
			return false;
		}
		return true;	
	}

	/**
	 * Returns TRUE if this type is different to the type t. The Vector
	 * difference will contain all found differences between the types,
	 * otherwise it is empty. This method should be used sooner for information
	 * about differences of types.
	 */
	public boolean differentTo(final Type t, final Vector<String> difference) {
		String diff = "";
		if (!getStringRepr().equals(t.getStringRepr())) {
			diff = "Type name# " + getStringRepr() + " != " + t.getStringRepr();
			difference.add(diff);
		}
		if (!getAdditionalRepr().equals(t.getAdditionalRepr())) {
			diff = "Type graphical repr# " + getAdditionalRepr() + " != "
					+ t.getAdditionalRepr();
			difference.add(diff);
		}
		if (this.itsAttrType != null) {
			if (t.getAttrType() == null) {
				diff = "Attribute Type# " + "defined (is not null)" + " != "
						+ "not defined (is null)";
				difference.add(diff);
			} else if (((DeclTuple) this.itsAttrType).getSize() != ((DeclTuple) t
					.getAttrType()).getSize()) {
				diff = "Attr member count# "
						+ ((DeclTuple) this.itsAttrType).getSize() + " != "
						+ ((DeclTuple) t.getAttrType()).getSize();
				difference.add(diff);
			} else if (!this.itsAttrType.compareTo(t.getAttrType())) {
				DeclTuple dt1 = (DeclTuple) this.itsAttrType;
				DeclTuple dt2 = (DeclTuple) t.getAttrType();
				for (int i = 0; i < dt1.getSize(); i++) {
					DeclMember dm1 = (DeclMember) dt1.getMemberAt(i);
					DeclMember dm2 = (DeclMember) dt2.getMemberAt(i);
					if (!dm1.compareTo(dm2)) {
						diff = i + ". " + "Member decl(type:name)# "
								+ dm1.getTypeName() + ":" + dm1.getName()
								+ " != " + dm2.getTypeName() + ":"
								+ dm2.getName();
						difference.add(diff);
					}
				}
			}
		} else if (t.getAttrType() != null) {
			diff = "Attribute Type# " + "not defined (is null)" + " != "
					+ "defined (is not null)";
			difference.add(diff);
		}

		if (difference.isEmpty())
			return false;
		
		return true;
	}


	/**
	 * Returns the string representation. Mostly used as the name of a type.
	 */
	public final String getStringRepr() {
		return this.itsStringRepr;
	}

	/**
	 * Sets the string representation. Mostly used as the name of the type
	 */
	public final void setStringRepr(final String n) {
		this.itsStringRepr = n;
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/** Set textual comments for this type. */
	public void setTextualComment(final String text) {
		this.comment = text;
	}

	/** Return textual comments of this type. */
	public String getTextualComment() {
		return this.comment;
	}


	public Vector<String> checkDoubleAttributeName(final Type otherType) {
		Vector<String> v = new Vector<String>(5, 5);
		if (this.itsAttrType == null || otherType.getAttrType() == null)
			return v;

		DeclTuple myDecl = (DeclTuple) this.itsAttrType;
		DeclTuple otherDecl = (DeclTuple) otherType.getAttrType();
		for (int i = 0; i < otherDecl.getNumberOfEntries(); i++) {	
			DeclMember mem = (DeclMember) otherDecl.getMemberAt(i);
			if (myDecl.isLegalName(mem.getName()) > 0) {
				if (mem.getHoldingTuple() != myDecl.getMemberAt(mem.getName()).getHoldingTuple()) {
					v.add(otherDecl.getNameAsString(i));
				}
			}
		}
		return v;
	}

	/**
	 * Returns the associated attribute type.
	 */
	public final AttrType getAttrType() {
		return this.itsAttrType;
	}

	/**
	 * Returns the additional representation string
	 * 
	 * @see #setAdditionalRepr
	 */
	public String getAdditionalRepr() {
		return this.additionalRepr;
	}

	/**
	 * Set its additional graphical representation, 
	 * which is always saved together with its name. 
	 * Predefined minimal additional representation string
	 * of an Arc - ":SOLID_LINE:java.awt.Color[r=0,g=0,b=0]:[EDGE]:".
	 */
	public void setAdditionalRepr(final String repr) {
		if (repr.equals("EDGE") || repr.equals("[EDGE]")) {
			this.additionalRepr = ":SOLID_LINE:java.awt.Color[r=0,g=0,b=0]:[EDGE]:";
		} else {
			this.additionalRepr = repr;
		}
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	public void XwriteObject(XMLHelper h) {
		String n = getStringRepr();
//		System.out.println("TypeImpl.XwriteObject: " +getAdditionalRepr());
		if ((getAdditionalRepr() != null) && (!getAdditionalRepr().equals(""))) {
			n += ("%" + getAdditionalRepr());
		}
		
		if (n.indexOf("[EDGE]") >= 0) {
			h.openNewElem("EdgeType", this);
		} else {
			h.openNewElem("Type", this);
		}
		
		h.addAttr("name", n);

		if (!this.comment.equals("")) {
			h.addAttr("comment", this.comment);
		}		
		
		h.addAttr("abstract", String.valueOf(false));
		
		if (this.itsAttrType != null && this.itsAttrType.getNumberOfEntries() > 0) {
			h.addObject("", this.itsAttrType, true);
		}
		
		// multiplicity will be written in the Arc
		// object in the type graph
		h.close();
	}

	
	public void XreadObject(XMLHelper h) {
		if (h.isTag("NodeType", this) || h.isTag("EdgeType", this)
				|| h.isTag("Type", this)) {
			String n = h.readAttr("name");
//			n = XMLHelper.checkNameDueToSpecialCharacters(n);
//			System.out.println("TypeImpl.XreadObject: " +n);

			String str = h.readAttr("comment");
			if (!str.equals(""))
				this.comment = str.toString();

			h.readAttr("abstract");
			
			int i = n.indexOf('%');
			// set type name
			if (i != -1) {
				String test = n.substring(0, i);					
				test = XMLHelper.checkNameDueToSpecialCharacters(test);
				this.itsStringRepr = test; 
//				itsStringRepr = n.substring(0, i);
			}
			else {
				String test = XMLHelper.checkNameDueToSpecialCharacters(n);
				this.itsStringRepr = test; 
//				itsStringRepr = n;				
			}

			AttrType tmpAttr = agg.attribute.impl.AttrTupleManager
					.getDefaultManager().newType();
			h.enrichObject(tmpAttr);
			if (tmpAttr.getNumberOfEntries() != 0)
				this.itsAttrType = tmpAttr;
			else
				this.itsAttrType = null;

			if (i != -1) {
				String a = n.substring(i + 1);
				a = a.replaceAll("::", ":");							
				n = n.substring(0, i);
				setAdditionalRepr(a);
			}

			// NOTE:: multiplicity will be read in the TypeGraph

			h.close();
		}
	}

	
	/**
	 * internal function to convert a type into a string. If the type contains
	 * an empty string representation, this function will return "unnamed"
	 * otherwise the string representation of the type ({@link #getStringRepr()})
	 */
	public String getName() {
		String stringRepr = this.getStringRepr();
		if ("".equals(stringRepr)) {
			return "unnamed";
		}
		return stringRepr;
	}

	/**
	 * returns if the given GraphObject is valid typed as defined in the type
	 * graph. Before this can be checked, all edges and nodes of the type graph
	 * must be added to their types. The given object is not taken in account
	 * when this is its type.
	 * 
	 * @return null, if the graph object is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 * 
	 */
	public TypeError check(final GraphObject nodeOrArc, final int level) {
		if (level == TypeSet.DISABLED)
			return null;

		if (nodeOrArc instanceof Arc) {
			return check((Arc) nodeOrArc, level);
		} 
		throw new IllegalArgumentException(
					"parameter must be of Arc type.");
	}

	/**
	 * Returns true if at least one edge exists from the specified source type
	 * to any other type, otherwise false.
	 */
	public boolean hasTypeGraphArc(final Type sourceType) {
		if (this.edgeTypeGraphObjects != null) {
			Vector<Type> mySrcParents = sourceType.getAllParents();
			for (int i = 0; i < mySrcParents.size(); ++i) {
				Type mySrcType = mySrcParents.get(i);
				HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(mySrcType);
				if (targets != null)
					return true;
			}

		}
		return false;
	}

	/**
	 * Searches for a type that is the target type of this edge type with the
	 * specified source type. Returns a vector with all found target types,
	 * otherwise empty vector.
	 * 
	 */
	public Vector<Type> getTargetsOfArc(final Type sourceType) {
		final Vector<Type> v = new Vector<Type>();
		// try to find any edge between any pair of src-tar parents
		if (this.edgeTypeGraphObjects != null) {
			Vector<Type> mySrcParents = sourceType.getAllParents();
			for (int i = 0; i < mySrcParents.size(); ++i) {
				Type mySrcType =  mySrcParents.get(i);
				HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(mySrcType);
				if (targets == null)
					continue;
				Iterator<Type> iter = targets.keySet().iterator();
				while (iter.hasNext()) {
					Type t = iter.next();
					v.add(t);
					Vector<Type> childs = t.getChildren();
					for (int j=0; j<childs.size(); j++) {
						Type cht = childs.get(j);
						v.add(cht);
					}
				}
			}
		}
		return v;
	}

	public boolean isEdgeCreatable(final Type sourceType, final Type targetType, final int level) {
		// iterator for the parents of the src and target type of the current
		// arc
		Type mySrcType = sourceType;
		Type myTarType = targetType;

		// find out all parents of source and target
		Vector<Type> mySrcParents = sourceType.getAllParents();
		Vector<Type> myTarParents = targetType.getAllParents();

		TypeGraphArc subType = null;

		// try to find any edge between any pair of src/tar parents
		if (this.edgeTypeGraphObjects != null) {
			for (int i = 0; i < mySrcParents.size(); ++i) {
				mySrcType = mySrcParents.get(i);
				HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(mySrcType);
				if (targets == null)
					continue;

				for (int j = 0; j < myTarParents.size(); ++j) {
					myTarType = myTarParents.get(j);
					subType = targets.get(myTarType);
					if (subType != null)
						break;
				}
				if (subType != null)
					break;
			}
			//TODO  test
			if (subType == null) {
				for (int i = 0; i < myTarParents.size(); ++i) {
					myTarType = myTarParents.get(i);
					HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(myTarType);
					if (targets == null)
						continue;

					for (int j = 0; j < mySrcParents.size(); ++j) {
						mySrcType = mySrcParents.get(j);
						subType = targets.get(mySrcType);
						if (subType != null)
							break;
					}
					if (subType != null)
						break;
				}
			}
		}
		return (subType != null);
	}


	/**
	 * Returns null, if the specified arc is valid typed as defined in the type
	 * graph. Before this can be checked, all edges and nodes of the type graph
	 * must be added to theire types.
	 * 
	 * @return null, if the Arc is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 */
	public TypeError check(final Arc arc, final int level) {		
		if (this.edgeTypeGraphObjects != null) {
			// the source and target type of the current arc
			final Type sourceType = arc.getSource().getType();
			final Type targetType = arc.getTarget().getType();

			// find out all parents of source and target
			final Vector<Type> mySrcParents = sourceType.getAllParents();
			final Vector<Type> myTarParents = targetType.getAllParents();
			
			Type mySrcType = arc.getSource().getType();
			Type myTarType = arc.getTarget().getType();
			
			TypeGraphArc subType = null;

			// try to find any edge between any pair of src/tar parents
			for (int i = 0; i < mySrcParents.size(); ++i) {
				mySrcType = mySrcParents.get(i);
				HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(mySrcType);
				if (targets == null)
					continue;

				for (int j = 0; j < myTarParents.size(); ++j) {
					myTarType = myTarParents.get(j);
					subType = targets.get(myTarType);
					if (subType != null)
						break;
				}
				if (subType != null) {
					break;
				}
			}

			if (subType != null && subType.doesTypeGraphObjectExist()) {
				if (level > TypeSet.ENABLED) {
					int count = 0;
					int sourceMax = subType.getSourceMax();
					int targetMax = subType.getTargetMax();
					int sourceMin = subType.getSourceMin();
					int targetMin = subType.getTargetMin();
					if (targetMax != UNDEFINED) {
						// if multipl. defined, check if arc is possible
						count = ((Node)arc.getSource()).getNumberOfOutgoingArcsOfTypeToTargetType(this, myTarType);
						// if there are too many outgoing arcs
						if (count > targetMax) {
							String isOrAre = "is";
							if (targetMax != 1)
								isOrAre = "are";
							return new TypeError(TypeError.TO_MUCH_ARCS,
								"- Too many edges of type \"" + getName()
										+ "\" end at the node of type \""
										+ myTarType.getName() + "\".\nThere " //targetType.getName() 
										+ isOrAre + " only " + targetMax
										+ " allowed ( graph \""
										+ arc.getContext().getName() + "\" ).",
								arc, this);
						}
					}

					if (sourceMax != UNDEFINED) {
						// if multipl. defined, check if arc is possible
						count = ((Node)arc.getTarget()).getNumberOfIncomingArcsOfTypeFromSourceType(this, mySrcType);
						if (count > sourceMax) {
							String isOrAre = "is";
							if (sourceMax != 1)
								isOrAre = "are";
							return new TypeError(TypeError.TO_MUCH_ARCS,
								"- Too many edges of type \"" + getName()
										+ "\" start at the node of type \""
										+ mySrcType.getName() + "\".\nThere " //sourceType
										+ isOrAre + " only " + sourceMax
										+ " allowed ( graph \""
										+ arc.getContext().getName() + "\" ).",
								arc, this);
						}
					}

					if (level >= TypeSet.ENABLED_MAX_MIN) {
						if (targetMin > 0) {
							// if multipl. defined, check if arc is possible
							count = ((Node)arc.getSource()).getNumberOfOutgoingArcsOfTypeToTargetType(this, myTarType);
							// if there are too many outgoing arcs
							if (count < targetMin) {
								String isOrAre = "is";
								if (targetMin != 1)
									isOrAre = "are";
								return new TypeError(TypeError.TO_LESS_ARCS,
									"- Too few edges of type \"" + getName()
											+ "\" end at the node of type \""
											+ targetType.getName()
											+ "\".\nThere " + isOrAre
											+ " at least " + targetMin
											+ " required ( graph \""
											+ arc.getContext().getName()
											+ "\" ).", arc, this);
							}
						}

						if (sourceMin > 0) {
							// if multipl. defined, check if arc is possible
							count = ((Node)arc.getTarget()).getNumberOfIncomingArcsOfTypeFromSourceType(this, mySrcType);
							if (count < sourceMin) {
								String isOrAre = "is";
								if (sourceMin != 1)
									isOrAre = "are";
								return new TypeError(TypeError.TO_LESS_ARCS,
									"- Too few edges of type \"" + getName()
											+ "\" start at the node of type \""
											+ sourceType.getName()
											+ "\".\nThere " + isOrAre
											+ " at least " + sourceMin
											+ " required ( graph \""
											+ arc.getContext().getName()
											+ "\" ).", arc, this);
							}
						}
					}
				}
				return null;
			} 
			else if (level > TypeSet.ENABLED_INHERITANCE) {
				// no such source or target or there is no type graph object defined
				// for this combination
				return new TypeError(TypeError.NO_SUCH_TYPE,
					"- The type graph does not contain an edge type with name \""
							+ getName() + "\" \nbetween node type \""
							+ sourceType.getName() + "\" and \""
							+ targetType.getName() + "\""
							+"\n ( see graph:  "+arc.getContext().getName()+" ).", arc, this);
			}
		}
		return null;
	}

	public TypeError checkIfEdgeCreatable(final Node src, final Node tar, final int level) {
		return checkIfEdgeCreatable(null, src, tar, level);
	}

	public TypeError checkIfEdgeCreatable(final Graph g, final Node src, final Node tar, final int level) {
		if ((level == TypeSet.DISABLED) 
				|| (level == TypeSet.ENABLED_INHERITANCE)
				|| (level == TypeSet.ENABLED))
			return null;

		TypeError typeError = checkSourceMax(g, src, tar);
		if (typeError == null)
			typeError = checkTargetMax(g, src, tar);
		return typeError;
	}

	/*
	 * Source Max Multiplicity means how many ( at most ) nodes of the source node type
	 * are incoming into the target node. 
	 */
	public TypeError checkSourceMax(final Graph g, final Node src, final Node tar) {
		String graphName = "";
		if (g != null) {
			graphName = g.getName();
		}
		
		final Type sourceType = src.getType();
		final Type targetType = tar.getType();

		if (this.edgeTypeGraphObjects != null) {
			// check entry for source
			final HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(sourceType);
			if (targets != null) {
				final TypeGraphArc subType = targets.get(targetType);

				// search for the type graph object
				if ((subType != null) && (subType.doesTypeGraphObjectExist())) {
					int sourceMax = subType.getSourceMax();
					if (sourceMax != UNDEFINED) {
						int count = tar.getNumberOfIncomingArcs(this, sourceType);
						if (count + 1 > sourceMax) {
							String isOrAre = "is";
							if (sourceMax != 1)
								isOrAre = "are";
							return new TypeError(TypeError.TO_MUCH_ARCS,
									"Too many edges of type \"" + getName()
											+ "\" (would) start at the node of type \""
											+ src.getType().getName()
											+ "\" (green marked node).\nThere "
											+ isOrAre + " only " + sourceMax
											+ " allowed ( graph \""
											+ graphName
											+ "\" ).", tar, this);
						}
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Target Max Multiplicity means how many ( at most ) nodes of the target node type
	 * are outgoing from the source node. 
	 */
	public TypeError checkTargetMax(final Graph g, final Node src, final Node tar) {
		String graphName = "";
		if (g != null) {
			graphName = g.getName();
		}
		
		final Type sourceType = src.getType();
		final Type targetType = tar.getType();

		if (this.edgeTypeGraphObjects != null) {
			// check entry for source
			final HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects.get(sourceType);
			if (targets != null) {
				final TypeGraphArc subType = targets.get(targetType);

				// search for the type graph object
				if ((subType != null) && (subType.doesTypeGraphObjectExist())) {
					int targetMax = subType.getTargetMax();
					if (targetMax != UNDEFINED) {
						int count = src.getNumberOfOutgoingArcs(this, targetType); 
						if (count + 1 > targetMax) {
							String isOrAre = "is";
							if (targetMax != 1)
								isOrAre = "are";
							return new TypeError(TypeError.TO_MUCH_ARCS,
									"Too many edges of type \"" + getName()
											+ "\" (would) end at the node of type \""
											+ targetType.getName()
											+ "\" (green marked node).\nThere "
											+ isOrAre + " only " + targetMax
											+ " allowed ( graph \""
											+ graphName
											+ "\" ).", src, this);
						}
					}
					return null;
				}
			}
		}
		return null;
	}

	/**
	 * Add the given GraphObject of a type graph to this type. 
	 * The GraphObject nodeOrArc must be of this type:
	 * it is a Node if this is a node type,
	 * it is an Arc if this is an edge type. 
	 * In case of it is a node type and a node object inside of a type graph
	 * is already exist, it should to be removed first.
	 */
	public boolean addTypeGraphObject(final GraphObject arc) {
		if (arc instanceof Arc && arc.getContext().isTypeGraph()) {			
			Type sourceType = ((Arc) arc).getSource().getType();
			Type targetType = ((Arc) arc).getTarget().getType();
			TypeGraphArc subType = getTypeGraphArc(sourceType, targetType);
			if (subType.getArc() == null) {
				subType.addTypeGraphObject((Arc)arc);	
				this.typeGraphObjectDefined = true;
				return true;			
			} 			
		}
		return false;
	}

	/**
	 * Remove the given GraphObject from the type graph and from this type.
	 * Returns true if remove is done, otherwise false. To remove an GraphObject
	 * is not possible when the type graph check is activated.
	 */	
	public boolean removeTypeGraphObject(final GraphObject arc, final boolean forceToRemove) {
		if (arc == null 
				|| !arc.isArc()
				|| arc.getContext() == null
				|| !arc.getContext().isTypeGraph())
			return true;
		
		boolean allowedToRemove = false;
		if (arc.getContext().getTypeSet().getLevelOfTypeGraphCheck() 
				<= TypeSet.ENABLED_INHERITANCE) {
			allowedToRemove = true;
		} else {
			if (forceToRemove)
				allowedToRemove = true;
			else
				allowedToRemove = false;
		} 
		
		if (allowedToRemove) {
			if (this.edgeTypeGraphObjects == null) 
				return true;

			// get source and target
			Type sourceType = ((Arc) arc).getSource().getType();
			Type targetType = ((Arc) arc).getTarget().getType();

			HashMap<Type, TypeGraphArc> 
			targets = this.edgeTypeGraphObjects.get(sourceType);
			if (targets == null) {
				return true;
			}
			TypeGraphArc subType = targets.get(targetType);
			if (subType == null) {
				return true;
			}

			if (arc.getContext().getTypeSet().getLevelOfTypeGraphCheck() <= TypeSet.ENABLED_INHERITANCE) { //TypeSet.DISABLED) 
				subType.forceRemoveTypeGraphObject(); 
			}
			else if (forceToRemove) {
				subType.forceRemoveTypeGraphObject(); 
			}
			else if (!subType.removeTypeGraphObject())
				return false;
								
			// if the subtype doesn't contains a type graph object
			// or some using graph objects, we can destroy it
			targets.remove(targetType);
								
			// remove vector, if it is empty
			if (targets.isEmpty()) {
				this.edgeTypeGraphObjects.remove(sourceType);

				// remove HashMap if it is empty
				if (this.edgeTypeGraphObjects.isEmpty()) {
					this.edgeTypeGraphObjects = null;
					this.typeGraphObjectDefined = false;
				}
			}
			return true;				
		} 
		return false;
	}
	
	/**
	 * Remove the given GraphObject from the type graph and from this type.
	 * Returns true if remove is done, otherwise false. To remove an GraphObject
	 * is not possible when the type graph check is activated.
	 */
	public boolean removeTypeGraphObject(final GraphObject arc) {
		return removeTypeGraphObject(arc, false);
	}

	
	/**
	 * Set the min of the source multiplicity of an edge type to the value. The
	 * edge type is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public void setSourceMin(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setSourceMin(value);
	}

	/**
	 * Set the max of the source multiplicity of an edge type to the value. The
	 * edge type is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public void setSourceMax(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setSourceMax(value);
	}

	/**
	 * Set the min of the target multiplicity of an edge type to the value. The
	 * edge type is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public void setTargetMin(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setTargetMin(value);
	}

	/**
	 * Set the max of the target multiplicity of an edge type to the value. The
	 * edge type is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public void setTargetMax(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setTargetMax(value);
	}

	/**
	 * Return the min of the source multiplicity of an edge type. The edge type
	 * is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public int getSourceMin(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getSourceMin();
	}

	/**
	 * Return the max of the source multiplicity of an edge type. The edge type
	 * is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public int getSourceMax(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getSourceMax();
	}

	/**
	 * Return the min of the target multiplicity of an edge type. The edge type
	 * is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public int getTargetMin(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getTargetMin();
	}

	/**
	 * Return the max of the target multiplicity of an edge type. The edge type
	 * is defined by the node type sourceType and the node type
	 * targetType.
	 */
	public int getTargetMax(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getTargetMax();
	}

	public void setVisibityOfObjectsOfTypeGraphArc(final Type sourceType, final Type targetType, boolean vis) {
		TypeGraphArc tgarc = getTypeGraphArc(sourceType, targetType);
		if (tgarc != null)
			tgarc.setVisible(vis);
	}
	
	public boolean isObjectOfTypeGraphArcVisible(final Type sourceType, final Type targetType) {
		TypeGraphArc tgarc = getTypeGraphArc(sourceType, targetType);
		return (tgarc == null) || tgarc.isVisible();
	}
	
	public Arc getTypeGraphArcObject(final Type sourceType, final Type targetType) {
		TypeGraphArc tgarc = getTypeGraphArc(sourceType, targetType);
		if (tgarc != null) {
			return tgarc.getArc();
		}
		return null;
	}

	/**
	 * Returns the subtype object for this source and target combination. The
	 * subtype will be created, if it does not exist.
	 */
	public TypeGraphArc getTypeGraphArc(final Type sourceType, final Type targetType) {
		// iterator for the parents of the src and target type of the current
		// arc
		Type mySrcType = sourceType;
		Type myTarType = targetType;

		// find out all parents of source and target
		Vector<Type> mySrcParents = sourceType.getAllParents();
		Vector<Type> myTarParents = targetType.getAllParents();

		HashMap<Type, TypeGraphArc> targets = null;
		TypeGraphArc subType = null;

		// create Map if not def.
		if (this.edgeTypeGraphObjects == null) {
			this.edgeTypeGraphObjects = new HashMap<Type, HashMap<Type, TypeGraphArc>>();
			this.typeGraphObjectDefined = true;
		}

		// create HashMap for this sourceType if not def.
		for (int i = 0; i < mySrcParents.size(); ++i) {
			mySrcType = mySrcParents.get(i);
			targets = this.edgeTypeGraphObjects.get(mySrcType);
			if (targets != null) {
				for (int j = 0; j < myTarParents.size(); ++j) {
					myTarType = myTarParents.get(j);
					subType = targets.get(myTarType);
					if (subType != null) {
						return subType;
					}
				}
			}
		}
			
		targets = this.edgeTypeGraphObjects.get(sourceType);
		if (targets == null) {
			targets = new HashMap<Type, TypeGraphArc>();
			this.edgeTypeGraphObjects.put(sourceType, targets);
			subType = new TypeGraphArc();
			targets.put(targetType, subType);
		}
		
		if (subType == null) {
			subType = new TypeGraphArc();
			targets.put(targetType, subType);
		}
		return subType;
	}

	public TypeGraphArc getSimilarTypeGraphArc(final Type sourceType,
			final Type targetType) {
		Iterator<Type> sourceIter = this.edgeTypeGraphObjects.keySet().iterator();
		while (sourceIter.hasNext()) {
			Type srct = sourceIter.next();
			// if(getName().equals("s:i")){
			// System.out.println("type s:i source:: "+srct.getName()+" ::
			// "+sourceType.getName()+" "+targetType.getName());
			// }
			if (!srct.compareTo(sourceType))
				continue;

			HashMap<Type, TypeGraphArc> targetsMap = this.edgeTypeGraphObjects
					.get(srct);
			Iterator<Type> targetsIter = targetsMap.keySet().iterator();
			while (targetsIter.hasNext()) {
				Type tart = targetsIter.next();
				// if(getName().equals("s:i")){
				// System.out.println("type s:i target:: "+tart.getName()+" ::
				// "+sourceType.getName()+" "+targetType.getName());
				// }
				if (!tart.compareTo(targetType))
					continue;

				TypeGraphArc subType = targetsMap.get(tart);
				// if(getName().equals("s:i")){
				// System.out.println("type s:i TypeGraphArc: "+ subType);
				// }
				return subType;
			}
		}
		return null;
	}

	public boolean hasTypeGraphArc() {
		return (this.edgeTypeGraphObjects == null)?false:true;
	}

	public boolean hasTypeGraphArc(final Type sourceType, final Type targetType) {
		if (this.edgeTypeGraphObjects == null) {
			return false;
		}
		
		Type mySrcType = sourceType;
		Type myTarType = targetType;

		Vector<Type> mySrcParents = sourceType.getAllParents();
		Vector<Type> myTarParents = targetType.getAllParents();

		TypeGraphArc subType = null;

		// try to find any edge between any pair of src/tar parents
		for (int i = 0; i < mySrcParents.size(); ++i) {
			mySrcType = mySrcParents.get(i);
			HashMap<Type, TypeGraphArc> targets = this.edgeTypeGraphObjects
					.get(mySrcType);
			if (targets != null) {
				for (int j = 0; j < myTarParents.size(); ++j) {
					myTarType = myTarParents.get(j);
					subType = targets.get(myTarType);
					if (subType != null)
						break;
				}
				if (subType != null)
					break;
			}
		}
		
		return (subType != null);
	}

	public boolean hasTypeGraphArc(
			final GraphObject sourceType,
			final GraphObject targetType) {
		return hasTypeGraphArc(sourceType.getType(), targetType.getType());
	}

	public HashMap<Type, HashMap<Type, TypeGraphArc>> getArcTypeGraphObjects() {
		return this.edgeTypeGraphObjects;
	}

	public boolean compareTypeGraphArcs(final Type t) {
		if (this.edgeTypeGraphObjects == null
				&& ((ArcTypeImpl) t).getArcTypeGraphObjects() == null) {
			return true;
		}
		else if (this.edgeTypeGraphObjects != null
				&& ((ArcTypeImpl) t).getArcTypeGraphObjects() != null) {
			if (!this.edgeTypeGraphObjects.isEmpty()
					&& !((ArcTypeImpl) t).getArcTypeGraphObjects().isEmpty()) {
				Iterator<Type> sourceIter = this.edgeTypeGraphObjects.keySet().iterator();
				while (sourceIter.hasNext()) {
					Type srct = sourceIter.next();
					HashMap<Type, TypeGraphArc> targetsMap = this.edgeTypeGraphObjects
							.get(srct);
					Iterator<Type> targetsIter = targetsMap.keySet().iterator();
					while (targetsIter.hasNext()) {
						Type tart = targetsIter.next();
//						TypeGraphArc subType = targetsMap.get(tart);
						TypeGraphArc subType_t = ((ArcTypeImpl) t)
								.getSimilarTypeGraphArc(srct, tart);
						if (subType_t != null) {
							return true;
						}
					}
				}
			}
			return false;
		} else
			return false;
	}

	public boolean compareTypeGraphArcsMultiplicity(final Type t) {
		if (this.edgeTypeGraphObjects == null
				&& ((ArcTypeImpl) t).getArcTypeGraphObjects() == null) {
			return true;
		}
		else if (this.edgeTypeGraphObjects != null
				&& ((ArcTypeImpl) t).getArcTypeGraphObjects() != null) {
			if (!this.edgeTypeGraphObjects.isEmpty()
					&& !((ArcTypeImpl) t).getArcTypeGraphObjects().isEmpty()) {
				// System.out.println("Typeimpl.compareTypeGraphArcs ...");
				Iterator<Type> sourceIter = this.edgeTypeGraphObjects.keySet().iterator();
				while (sourceIter.hasNext()) {
					Type srct = sourceIter.next();
					HashMap<Type, TypeGraphArc> targetsMap = this.edgeTypeGraphObjects
							.get(srct);
					Iterator<Type> targetsIter = targetsMap.keySet().iterator();
					while (targetsIter.hasNext()) {
						Type tart = targetsIter.next();
						TypeGraphArc subType = targetsMap.get(tart);
						TypeGraphArc subType_t = ((ArcTypeImpl) t)
								.getSimilarTypeGraphArc(srct, tart);
						if (subType_t != null) {
							// System.out.println("Typeimpl.compareTypeGraphArcs
							// ... "+subType+" with "+subType_t);
							if (subType.getSourceMax() != subType_t
									.getSourceMax())
								return false;
							else if (subType.getSourceMin() != subType_t
									.getSourceMin())
								return false;
							else if (subType.getTargetMin() != subType_t
									.getTargetMin())
								return false;
							else if (subType.getTargetMax() != subType_t
									.getTargetMax())
								return false;
							else
								return true;
						}
					}
				}
			}
			return false;
		} else
			return false;
	}


	/**
	 * Disable type graph object of this type.
	 */
	public void disableTypeGraphObject() {
		if (this.edgeTypeGraphObjects != null) {
			Iterator<HashMap<Type, TypeGraphArc>> iter = this.edgeTypeGraphObjects.values().iterator();
			while (iter.hasNext()) {
				HashMap<Type, TypeGraphArc> actMap = iter.next();
				Iterator<TypeGraphArc> subIter = actMap.values().iterator();
				while (subIter.hasNext()) {
					TypeGraphArc subType = subIter.next();
	
					// remove the type graph object
					subType.forceRemoveTypeGraphObject();
				}
	
				if (actMap.isEmpty()) {
					// if there is no type graph arc with this source, we can
					// remove the Hash Map
					iter.remove();
				}
			}
			if (this.edgeTypeGraphObjects.isEmpty()) {
				this.edgeTypeGraphObjects = null;
				this.typeGraphObjectDefined = false;
			}
		} 
	}

	/**
	 * returns true, if there is at least one object in the type graph for this
	 * type.
	 */
	public boolean isTypeGraphObjectDefined() {
		return this.typeGraphObjectDefined;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agg.xt_basis.Type#checkIfRemovableFromSource(agg.xt_basis.Node,
	 *      agg.xt_basis.Arc, int)
	 */
	public TypeError checkIfRemovableFromSource(final GraphObject node, final Arc arc,
			final int level) {
		if (arc.getContext().isCompleteGraph()
				&& level == TypeSet.ENABLED_MAX_MIN)
			return checkSourceMin(node, arc, false, false);
		
		return null;
	}

	public TypeError checkIfRemovableFromSource(
			final GraphObject node, final Arc arc,
			boolean deleteSrc, boolean deleteTar,
			final int level) {
		if (arc.getContext().isCompleteGraph()
				&& level == TypeSet.ENABLED_MAX_MIN)
			return checkSourceMin(node, arc, deleteSrc, deleteTar);
		
		return null;
	}
	
	/*
	 * Source Max Multiplicity means how many ( at least ) nodes of the source node type
	 * are incoming into the target node. 
	 */
	private TypeError checkSourceMin(final GraphObject srcnode, final Arc arc,
			boolean deleteSrc, boolean deleteTar) {
//		System.out.println("TypeImpl.checkSourceMin(final GraphObject srcnode, final Arc arc)");

		int sourceMin = arc.getType().getSourceMin(this, arc.getTarget().getType());
		if (sourceMin != UNDEFINED) {
			int count = ((Node)arc.getTarget()).getNumberOfIncomingArcs(arc.getType(), srcnode.getType());
			if (count - 1 < sourceMin
					&& !deleteTar) {
				return new TypeError(TypeError.TO_LESS_ARCS,
						"Too few edges of type \"" + arc.getType().getName()
								+ "\"" + " (would) start at the source node  " + "\""
								+ arc.getSource().getType().getName()
								+ "\"." + "\nThere are at least " + sourceMin
								+ " needed ( graph \""
								+ srcnode.getContext().getName() + "\" ).",
						arc,
						this);
			}
		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see agg.xt_basis.Type#checkIfRemovableFromTarget(agg.xt_basis.Node,
	 *      agg.xt_basis.Arc, int)
	 */
	public TypeError checkIfRemovableFromTarget(final GraphObject node, final Arc arc,
			final int level) {
		
		if (arc.getContext().isCompleteGraph()
				&& level == TypeSet.ENABLED_MAX_MIN)
			return checkTargetMin(node, arc, false, false);
		
		return null;
	}
	
	public TypeError checkIfRemovableFromTarget(final GraphObject node, final Arc arc,
			boolean deleteSrc, boolean deleteTar,
			final int level) {
		
		if (arc.getContext().isCompleteGraph()
				&& level == TypeSet.ENABLED_MAX_MIN)
			return checkTargetMin(node, arc, deleteSrc, deleteTar);
		
		return null;
	}

	/*
	 * Target Min Multiplicity means how many (at least ) nodes of the target node type
	 * are outgoing from the source node. 
	 */
	private TypeError checkTargetMin(final GraphObject tarnode, final Arc arc,
			boolean deleteSrc, boolean deleteTar) {
//		System.out.println("TypeImpl.checkTargetMin(final GraphObject tarnode, final Arc arc)");
		
		int targetMin = arc.getType().getTargetMin(arc.getSource().getType(), this);
		if (targetMin != UNDEFINED) {
			int count = ((Node)arc.getSource()).getNumberOfOutgoingArcs(arc.getType(), tarnode.getType());
			if (count - 1 < targetMin
					&& !deleteSrc) {
				return new TypeError(TypeError.TO_LESS_ARCS,
						"Too few edges of type \"" + arc.getType().getName()
								+ "\"" + " (would) end at the target node  " + "\""
								+ arc.getTarget().getType().getName()
								+ "\"." + "\nThere are at least " + targetMin
								+ " needed ( graph \""
								+ tarnode.getContext().getName() + "\" ).",
						arc,
						this);
			}
		}
		return null;
	}

	/**
	 * @see agg.xt_basis.Type#isParentOf(agg.xt_basis.Type)
	 */
	public boolean isParentOf(Type t) {
		return t.compareTo(this);
	}

	/**
	 * @see agg.xt_basis.Type#isRelatedTo(agg.xt_basis.Type)
	 */
	public boolean isRelatedTo(Type t) {
		return t.compareTo(this);
	}

	
	
	
	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#addParent(agg.xt_basis.Type)
	 */
	public void addParent(Type t) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfNodeCreatable(agg.xt_basis.Graph, int)
	 */
	public TypeError checkIfNodeCreatable(Graph basisGraph,
			int levelOfTypeGraphCheck) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfRemovable(agg.xt_basis.Node, int)
	 */
	public TypeError checkIfRemovable(Node node, int level) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getAllChildren()
	 */
	public Vector<Type> getAllChildren() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getAllParents()
	 */
	public Vector<Type> getAllParents() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getChildren()
	 */
	public Vector<Type> getChildren() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getCommonParentWith(agg.xt_basis.Type)
	 */
	public List<Type> getCommonParentWith(Type t) {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getImageFilename()
	 */
	public String getImageFilename() {
		return "";
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getParent()
	 */
	public Type getParent() {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getParents()
	 */
	public Vector<Type> getParents() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getSourceMax()
	 */
	public int getSourceMax() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getSourceMin()
	 */
	public int getSourceMin() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTypeGraphNodeObject()
	 */
	public Node getTypeGraphNodeObject() {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasTypeGraphNode()
	 */
	public boolean hasTypeGraphNode() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isAbstract()
	 */
	public boolean isAbstract() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isChildOf(agg.xt_basis.Type)
	 */
	public boolean isChildOf(Type t) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isObjectOfTypeGraphNodeVisible()
	 */
	public boolean isObjectOfTypeGraphNodeVisible() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#removeParent(agg.xt_basis.Type)
	 */
	public void removeParent(Type t) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setAbstract(boolean)
	 */
	public void setAbstract(boolean b) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setImageFilename(java.lang.String)
	 */
	public void setImageFilename(String imageFilename) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setParent(agg.xt_basis.Type)
	 */
	public void setParent(Type t) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setSourceMax(int)
	 */
	public void setSourceMax(int value) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setSourceMin(int)
	 */
	public void setSourceMin(int value) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setVisibilityOfObjectsOfTypeGraphNode(boolean)
	 */
	public void setVisibilityOfObjectsOfTypeGraphNode(boolean vis) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasInheritedAttribute()
	 */
	public boolean hasInheritedAttribute() {
		return false;
	}


	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getMaxMultiplicityOfAllChildren()
	 */
	public int getMaxMultiplicityOfAllChildren() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getMinMultiplicityOfAllChildren()
	 */
	public int getMinMultiplicityOfAllChildren() {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isChildTypeGraphNodeUsed()
	 */
	public boolean isChildTypeGraphNodeUsed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isTypeGraphNodeUsed()
	 */
	public boolean isTypeGraphNodeUsed() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#addChild(agg.xt_basis.Type)
	 */
	public void addChild(Type t) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getOwnIncomingArcs()
	 */
	public Vector<Arc> getOwnIncomingArcs() {
		return new Vector<Arc>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getOwnIncomingArcTypes()
	 */
	public Vector<Type> getOwnIncomingArcTypes() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getOwnOutgoingArcTypes()
	 */
	public Vector<Type> getOwnOutgoingArcTypes() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getOwnOutgoingArcs()
	 */
	public Vector<Arc> getOwnOutgoingArcs() {
		return new Vector<Arc>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#removeChild(agg.xt_basis.Type)
	 */
	public void removeChild(Type t) {		
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTypeGraphNode()
	 */
	public TypeGraphNode getTypeGraphNode() {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getClan()
	 */
	public List<Type> getClan() {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasCommonParentWith(agg.xt_basis.Type)
	 */
	public boolean hasCommonParentWith(Type t) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isInClanOf(agg.xt_basis.Type)
	 */
	public boolean isInClanOf(Type t) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasChild()
	 */
	public boolean hasChild() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasParent()
	 */
	public boolean hasParent() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isParentAttrTypeEmpty()
	 */
	public boolean isParentAttrTypeEmpty() {
		return true;
	}

}
