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
 * The type of a graph object is here defined. Each graph object has a type.
 * Types can be used for nodes as well as for edges. Even if there is no
 * protection in the methods a node type should be used only for nodes and a
 * edge type should be only used for edges.
 * 
 * @deprecated  replaced by
 * 		<code>NodeTypeImpl</code> to create a node type for nodes of a graph
 * and  
 * 		<code>ArcTypeImpl</code> to create an edge type for edges of a graph
 * 
 * @author $Author: olga $
 * @version $Id: TypeImpl.java,v 1.87 2010/10/07 20:04:26 olga Exp $
 */
public class TypeImpl implements Type {

	String comment = "";

	boolean isAbstract = false; // should be in NodeTypeImpl

	/**
	 * the name of the type
	 */
	String itsStringRepr;

	/*
	 * the parent of the type
	 */
//	Type itsParent; // should be in NodeTypeImpl

	/**
	 * the parents of the type
	 */
	final Vector<Type> itsParents = new Vector<Type>(); // should be in NodeTypeImpl

	final Vector<Type> itsChildren = new Vector<Type>(); // should be in NodeTypeImpl

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
//	 should be in ArcTypeImpl

	/**
	 * this value will be true, 
	 * if a graphobject inside of a type graph was defined.
	 */
	boolean typeGraphObjectDefined; // should be in NodeTypeImpl, ArcTypeImpl

	/** holds additional infos about a node of type graph */
	TypeGraphNode typeGraphNode; // should be in NodeTypeImpl

	String keyStr = null;
	
	
	protected TypeImpl() {
		this.itsAttrType = null;
		this.itsStringRepr = "";
		this.additionalRepr = "";
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * Creates a new type with the given name. There is non-attributable type.
	 * 
	 * @param stringRepr
	 *            the name of the type
	 */
	protected TypeImpl(String stringRepr) {
		this();
		this.itsStringRepr = stringRepr;
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * Creates a new type with the given attributes and the given name.
	 * 
	 * @param at
	 *            the declaration of the attributes
	 * @param stringRepr
	 *            the name of the type
	 */
	protected TypeImpl(AttrType at, String stringRepr) {
		this();
		this.itsAttrType = at;
		this.itsStringRepr = stringRepr;
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * creates a new type with the given attributes and an empty name.
	 * 
	 * @param at
	 *            the declaration of the attributes
	 */
	protected TypeImpl(AttrType at) {
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
		
		this.itsChildren.clear();
		this.itsParents.clear();
//		this.itsParent = null;
		
		if (this.typeGraphNode != null) {
			this.typeGraphNode.dispose();
			this.typeGraphNode = null;
		}
		this.typeGraphObjectDefined = false;
	}
	
	public void finalize() {
	}
	
	public void createAttributeType() {
		this.itsAttrType = agg.attribute.impl.AttrTupleManager.getDefaultManager()
				.newType();
		// should be in NodeTypeImpl
		for (int i = 0; i < this.getParents().size(); i++) {
			Type t = this.getParents().get(i);
			if (t.getAttrType() == null)
				t.createAttributeType();
			((DeclTuple) this.itsAttrType).addParent((DeclTuple) t.getAttrType());
		}
	}

	public void setAttributeType(final AttrType at) {
		this.itsAttrType = at;
		if (this.itsAttrType != null) {
			for (int i = 0; i < this.getParents().size(); i++) {
				Type t = this.getParents().get(i);
				if (t.getAttrType() == null)
					t.createAttributeType();
				((DeclTuple) this.itsAttrType)
						.addParent((DeclTuple) t.getAttrType());
			}
		}
	}

	public void removeAttributeType() {
		if (this.itsAttrType != null) {
			((DeclTuple)this.itsAttrType).dispose();
			this.itsAttrType = null;
		}
	}
	
	public boolean isNodeType() {
		if (this.additionalRepr.indexOf("[NODE]") >= 0)
			return true;
		
		return false;
	}

	public boolean isAttrTypeEmpty() {
		return (this.getAttrType() == null
//					|| this.getAttrType().getNumberOfEntries() == 0
					);
	}
	
	public boolean hasAnyAttrMember() {
		List<Type> list = this.getAllParents();
		for (int i = 0; i < list.size(); i++) {
			NodeTypeImpl t = (NodeTypeImpl) list.get(i);
			if (t.getAttrType() != null
					&& t.getAttrType().getNumberOfEntries() != 0
					) {
				return true;
			}
		}
		return false;
	}
	
	public boolean isArcType() {
		if (this.additionalRepr.indexOf("[EDGE]") >= 0)
			return true;
		
		return false;
	}

	/**
	 * Returns result string of <code>this.getStringRepr()+this.getAdditionalRepr()</code>
	 * 
	 * @see <code>getStringRepr()</code> and <code>getAdditionalRepr()</code>
	 */
	public String convertToKey() {
//		return this.itsStringRepr.concat("%").concat(this.additionalRepr);
		
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
//			System.out.println(getAdditionalRepr()+"  !=  "+getAdditionalRepr());
			return false;
		} else if (this.itsAttrType != null) {
			if (t.getAttrType() == null) {
				return false;
			} else if (((DeclTuple) this.itsAttrType).getSize() != ((DeclTuple) t
						.getAttrType()).getSize())
				return false;
			else if (!this.itsAttrType.compareTo(t.getAttrType())) {
				return false;
			} else
				return true;
		} else if (t.getAttrType() != null)
			return false;
		else
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

	public void setAbstract(final boolean b) {
		this.isAbstract = b;
	}

	public boolean isAbstract() {
		return this.isAbstract;
	}

	/**
	 * compares the specified type with parents of this object
	 * 
	 * @return true, if the specified type has the same
	 *         name, attributes and additional string representation
	 *         like a parent of this object
	 */
	public boolean isChildOf(final Type t) {
		Vector<Type> allParents = this.getAllParents();		
		for (int i = 1; i < allParents.size(); i++) {
			if (allParents.get(i).compareTo(t))
				return true;
		}
		return false;
	}

	/**
	 * compares the given type and its ancestors with this object
	 * 
	 * @return true, if this type has the same name, attributes and additional
	 *         representation as the specified type t or one of its ancestors.
	 */
	public boolean isParentOf(final Type t) {
		// multiple inheritance
		Type tAncestor = t;
		if (tAncestor.compareTo(this))
			return true;
		for (int i = 0; i < t.getParents().size(); i++) {
			tAncestor = t.getParents().get(i);
			if (this.isParentOf(tAncestor))
				return true;
		}
		return false;
	}

	/**
	 * Finds out if there is any relation between this type and the given one.
	 * Two types are related if they have one common ancestor.
	 */
	public boolean isRelatedTo(final Type t) {
		// multiple inheritance
		if (compareTo(t) || this.isParentOf(t))
			return true;
		Vector<Type> allparents = getAllParents();
		for (int i = 0; i < allparents.size(); i++) {
			Type oldestAncestor = allparents.get(i);
			if (oldestAncestor.isParentOf(t)) {
				return true;
			}
		}
		return false;
	}

	public List<Type> getCommonParentWith(final Type t) {
		List<Type> list = new Vector<Type> ();
		Vector<Type> allparents = getAllParents();
		for (int i = 0; i < allparents.size(); i++) {
			Type anAncestor = allparents.get(i);
			if (anAncestor.isParentOf(t)) {
				list.add(anAncestor);
			}
		}
		return list;
	}
	
	/**
	 * returns a list with all the parents of the current type and itself as the
	 * first element
	 * 
	 * @return list of all parents
	 */
	public Vector<Type> getAllParents() {
		// multiple inheritance
		Vector<Type> myAllParents = new Vector<Type>();
		myAllParents.add(this);
		for (int i = 0; i < this.getParents().size(); i++) {
			Type currentAncestor = this.getParents().get(i);
			// myAllParents.add(currentAncestor);
			Vector<Type> moreParents = currentAncestor.getAllParents();
			for (int j = 0; j < moreParents.size(); j++) {
				Type p = moreParents.get(j);
				if (!myAllParents.contains(p))
					myAllParents.add(p);
			}
		}
		return myAllParents;
	}

	/**
	 * returns a list with all the children of the current type and itself as
	 * the first element
	 * 
	 * @return list of all children
	 */
	public Vector<Type> getAllChildren() {
		// multiple inheritance
		Vector<Type> myAllChildren = new Vector<Type>();
		myAllChildren.add(this);
		for (int i = 0; i < this.getChildren().size(); i++) {
			Type ch = this.getChildren().get(i);
			// myAllChildren.add(ch);
			Vector<Type> moreChildren = ch.getAllChildren();
			for (int j = 0; j < moreChildren.size(); j++) {
				Type p = moreChildren.get(j);
				if (!myAllChildren.contains(p))
					myAllChildren.add(p);
			}
		}
		return myAllChildren;
	}

	public int getMaxMultiplicityOfAllChildren() {
		if (this.typeGraphNode == null)
			return -1;
//		System.out.println("Parent: "+this.itsStringRepr);
		int count = -1;
		for (int i = 0; i < this.getChildren().size(); i++) {
			Type chi = this.getChildren().get(i);
//			System.out.println("Child_i: "+chi.getName());
			if (chi.getSourceMax() != Type.UNDEFINED)
				count+= chi.getSourceMax();
			Vector<Type> moreChildren = chi.getAllChildren();
			for (int j = 1; j < moreChildren.size(); j++) {
				Type chj = moreChildren.get(j);
//				System.out.println("Child_j: "+chj.getName());
				if (chj.getSourceMax() != Type.UNDEFINED)
					count+= chj.getSourceMax();
			}
		}
		if (count > -1)
			count++;
		return count;
	}
	
	public int getMinMultiplicityOfAllChildren() {
		if (this.typeGraphNode == null)
			return -1;
		
		int count = -1;
		for (int i = 0; i < this.getChildren().size(); i++) {
			Type chi = this.getChildren().get(i);
			if (chi.getSourceMin() != Type.UNDEFINED)
				count+= chi.getSourceMin();
			Vector<Type> moreChildren = chi.getAllChildren();
			for (int j = 1; j < moreChildren.size(); j++) {
				Type chj = moreChildren.get(j);
				if (chj.getSourceMin() != Type.UNDEFINED)
				count+= chj.getSourceMin();
			}
		}
		if (count > -1)
			count++;
		return count;
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

	/**
	 * Returns my last direct parent.
	 */
	public Type getParent() {
		return this.itsParents.isEmpty()? null: this.itsParents.lastElement();
	}

	/**
	 * Returns my all direct parents.
	 */
	public Vector<Type> getParents() {
		return this.itsParents;
	}

	/**
	 * Remove my direct parent and set it to a new parent t. Due to this method
	 * I can have only one direct parent.
	 */
	public void setParent(final Type t) {
		if (t == null && !this.itsParents.isEmpty()) {
			removeParent(this.itsParents.lastElement());
			return;
		}

		addParent(t);
	}

	/**
	 * Adds a new parent to my parent list.
	 */
	public void addParent(final Type t) {
		if (t != null && !this.itsParents.contains(t)) {
			if (t.getAttrType() != null && this.itsAttrType == null) {
				this.createAttributeType();
			} else if (t.getAttrType() == null && this.itsAttrType != null) {
				t.createAttributeType();
			}
			if (this.itsAttrType != null) {
				DeclTuple myDeclTuple = (DeclTuple) this.itsAttrType;
				myDeclTuple.addParent((DeclTuple) t.getAttrType());
			}
			this.itsParents.add(t);
//			itsParent = t;

			t.addChild(this);
			// showRelatives();
		}
	}

	public void addChild(final Type t) {
		if (!this.itsChildren.contains(t))
			this.itsChildren.add(t);
	}

	/**
	 * Returns its direct children only.
	 */
	public Vector<Type> getChildren() {
		return this.itsChildren;
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
	 * Removes this parent from my parent list.
	 */
	public void removeParent(final Type t) {
		if (t != null && this.itsParents.contains(t)) {
			this.itsParents.remove(t);
			t.removeChild(this);
			// showRelatives();
			if (this.itsAttrType != null) {
				DeclTuple myDeclTuple = (DeclTuple) this.itsAttrType;
				myDeclTuple.removeParent((DeclTuple) t.getAttrType());
			}
		}
//		if (!this.itsParents.isEmpty())
//			itsParent = this.itsParents.lastElement();
//		else
//			itsParent = null;
	}

	public void removeChild(final Type t) {
		this.itsChildren.remove(t);
	}

	public boolean hasInheritedAttribute() {
		for (int i = 0; i < this.getParents().size(); i++) {
			Type pi = this.getParents().get(i);
			if (pi.getAttrType() != null)
				return true;
			Vector<Type> moreParents = pi.getAllParents();
			for (int j = 0; j < moreParents.size(); j++) {
				Type pj = moreParents.get(j);
				if (pj.getAttrType() != null)
					return true;
			}
		}
		return false;
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

	public String getImageFilename() {
		return this.imageFileName;
	}
	
	public void setImageFilename(String imageFilename) {
		this.imageFileName = imageFilename;
	}
	
	/**
	 * Set its additional graphical representation, 
	 * which is always saved together with its name. 
	 * Predefined minimal additional representation string
	 * of a Node - ":RECT:java.awt.Color[r=0,g=0,b=0]:[NODE]:",
	 * of an Arc - ":SOLID_LINE:java.awt.Color[r=0,g=0,b=0]:[EDGE]:".
	 */
	public void setAdditionalRepr(final String repr) {
		if (repr.equals("NODE") || repr.equals("[NODE]")) {
			this.additionalRepr = ":RECT:java.awt.Color[r=0,g=0,b=0]:[NODE]:";
		} else if (repr.equals("EDGE") || repr.equals("[EDGE]")) {
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
		
		if (n.indexOf("[NODE]") >= 0) {
			// all parents write first
			h.addEnumeration("", this.itsParents.elements(), true);

			h.openNewElem("NodeType", this);
		} else if (n.indexOf("[EDGE]") >= 0) {
			h.openNewElem("EdgeType", this);
		} else {
			h.openNewElem("Type", this);
		}
		
		h.addAttr("name", n);

		if (!this.comment.equals("")) {
			h.addAttr("comment", this.comment);
		}		
		
		h.addAttr("abstract", String.valueOf(this.isAbstract));
		
		// multiple inheritance -olga
		if (n.indexOf("[NODE]") >= 0) {				
			for (int i = 0; i < this.itsParents.size(); i++) {
				h.openSubTag("Parent");
				h.addObject("pID", this.itsParents.get(i), false);
				h.close();
			}
		}

		if (this.itsAttrType != null) {
			h.addObject("", this.itsAttrType, true);
		}
		// multiplicity will be written in the Arc / Node
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

			this.isAbstract = false;
			String abs = h.readAttr("abstract");
			if (!"".equals(abs)) {
				this.isAbstract = Boolean.valueOf(abs).booleanValue();
			}
//			System.out.println("TypeImpl.XreadObject:  this.isAbstract: " +this.isAbstract);
			
			int i = n.indexOf('%');
			// set type name
			if (i != -1) {
				String test = n.substring(0, i);					
				test = XMLHelper.checkNameDueToSpecialCharacters(test);
				this.itsStringRepr = test; 
//				this.itsStringRepr = n.substring(0, i);
			}
			else {
				String test = XMLHelper.checkNameDueToSpecialCharacters(n);
				this.itsStringRepr = test; 
//				this.itsStringRepr = n;				
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
				
				if (n.indexOf("[NODE]") != -1) {
					// multiple inheritance - olga
					Type p = (Type) h.getObjectRef("parent", null);
					if (p != null) {
						addParent(p);
						int pi = 1;
						while (true) {
							p = (Type) h.getObjectRef("parent" + pi, null);
							if (p != null) {
								addParent(p);
								pi++;
							} else
								break;
						}
					} else {
						while (h.readSubTag("Parent")) {
							Type pi = (Type) h.getObjectRef("pID", null);
							if (pi != null) {
								addParent(pi);
							}
							h.close();
						}
					}
					
					a = extractImageFileName(a);					
				} 
				
				n = n.substring(0, i);
				setAdditionalRepr(a);
			}

			// NOTE:: multiplicity will be read in the TypeGraph

			// add parents of attributes
			if (this.itsAttrType != null && this.itsAttrType.getNumberOfEntries() != 0) {
				DeclTuple myDeclTuple = (DeclTuple) this.itsAttrType;
				for (int p = 0; p < this.itsParents.size(); p++) {
					Type t = this.itsParents.get(p);
					myDeclTuple.addParent((DeclTuple) t.getAttrType());
				}
			}
			h.close();
		}
	}

	private String extractImageFileName(String str) {
		String[] test = str.split(":");
		int indx = -1;
		for (int i=0; i<test.length; i++) {
			String s = test[i];
			if (s.indexOf(".jpg") >= 0
					|| s.indexOf(".gif")>= 0
					|| s.indexOf(".xpm")>= 0) {
				this.imageFileName = s;
				indx = i;
				break;
			}
		}
		String out = ":";
		if (indx != -1) {
			for (int i=0; i<test.length; i++) {
				if (i != indx && !"".equals(test[i])) {
					String s = test[i];
					out = out.concat(s);
					out = out.concat(":");
				}				
			}
		} else {
			out = str;
		}
		return out; 		
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

	private String getNameForType(final Type type) {
		String stringRepr = type.getStringRepr();
		if ("".equals(stringRepr)) {
			return "unnamed";
		}
		return stringRepr;
	}

	/**
	 * returns if the given GraphObject is valid typed as defined in the type
	 * graph. Before this can be checked, all edges and nodes of the type graph
	 * must be added to theire types. The given object will not tested if this
	 * is its type.
	 * 
	 * @return null, if the graphobject is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 * 
	 */
	public TypeError check(final GraphObject nodeOrArc, final int level) {
		if (level == TypeSet.DISABLED)
			return null;

		if (nodeOrArc instanceof Node) {
			return check((Node) nodeOrArc, level);
		} else if (nodeOrArc instanceof Arc) {
			return check((Arc) nodeOrArc, level);
		} else {
			throw new IllegalArgumentException(
					"parameter must be of type Node or Arc.");
		}
	}

	/**
	 * returns if the given Node is valid typed as defined in the type graph.
	 * Before this can be checked, all edges and nodes of the type graph must be
	 * added to their types.
	 * 
	 * @return null, if the given Node is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 */
	public TypeError check(final Node node, final int level) {
		if (this.typeGraphNode != null) {
			if (level >= TypeSet.ENABLED) {
				List<Node> list = node.getContext().getNodes(this);
				int count = list != null? list.size(): 0;
				if(!node.getContext().isNode(node)) {
					count++; // a node is created and should be added to nodes of a graph
				}
				// if the level is set, check for max / min constraints
				if (level == TypeSet.ENABLED_MAX
						|| level == TypeSet.ENABLED_MAX_MIN) {
					int sourceMax = this.typeGraphNode.getSourceMax();
					if (sourceMax != UNDEFINED) {
						if (count > sourceMax) {
							return new TypeError(TypeError.TO_MUCH_NODES,
									"- Too many ("+count+") nodes of type \""
											+ getNameForType(this)
											+ "\".\nThere are only "
											+ sourceMax + " allowed ( graph \""
											+ node.getContext().getName()
											+ "\" ).", node, this);
						}
					}
					// return checkMaxMultiplicityOfInOutArcs(node);
				}
				// check Min
				if (level == TypeSet.ENABLED_MAX_MIN) {
					int sourceMin = this.typeGraphNode.getSourceMin();
					if (sourceMin > 0) {
						if (count < sourceMin) {
							return new TypeError(TypeError.TO_LESS_NODES,
									"- Not enough ("+count+") nodes of type \""
											+ getNameForType(this)
											+ "\".\nThere are at least "
											+ sourceMin + " needed ( graph \""
											+ node.getContext().getName()
											+ "\" ).", node, this);
						}
					}
					// return checkMinMultiplicityOfInOutArcs(node);
				} // if ENABLED_MAX_MIN
				return null;
			} 
			return null;
		} else if (level > TypeSet.ENABLED_INHERITANCE) {
			return new TypeError(TypeError.NO_SUCH_TYPE,
					"The type graph does not contain a node type with name \""
							+ getName() + "\".", node, this);
		} else {
			return null;
		}
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
	 * Search for a type that is the target type of this edge type with the
	 * specified source type. Returns a vector with all found target types,
	 * otherwise empty vector.
	 * 
	 */
	public Vector<Type> getTargetsOfArc(final Type sourceType) {
		final Vector<Type> v = new Vector<Type>();
		// try to find any edge between any pair of src/tar parents
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
		}
		return (subType != null);
	}


	/**
	 * Returns null, if the specified arc is valid typed as defined in the type
	 * graph. Before this can be checked, all edges and nodes of the type graph
	 * must be added to their types.
	 * 
	 * @return null, if the Arc is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 */
	public TypeError check(final Arc arc, final int level) {
//		System.out.println("TypeImpl.check(Arc arc, int level)  "+arc.getType().getName());
		
		if (this.edgeTypeGraphObjects != null) {
			// the src and target type of the current arc
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
						count = ((Node)arc.getSource()).getNumberOfOutgoingArcs(this,
								myTarType);
//						count = arc.getTarget().getNumberOfIncomingArcs(this,
//								mySrcType);
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
						count = ((Node)arc.getTarget()).getNumberOfIncomingArcs(this,
								mySrcType);
//						count = arc.getSource().getNumberOfOutgoingArcs(this,
//								myTarType);
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
							count = ((Node)arc.getSource()).getNumberOfOutgoingArcs(this,
									myTarType);
//							count = arc.getTarget().getNumberOfIncomingArcs(this,
//								mySrcType);
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
							count = ((Node)arc.getTarget()).getNumberOfIncomingArcs(this,
									mySrcType);
//							count = arc.getSource().getNumberOfOutgoingArcs(this,
//									myTarType);
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
							+ getNameForType(sourceType) + "\" and \""
							+ getNameForType(targetType) + "\""
							+"\n ( see graph:  "+arc.getContext().getName()+" ).", arc, this);
			}
		}
		return null;
	}

	public TypeError checkIfEdgeCreatable(final Node src, final Node tar, final int level) {
		return checkIfEdgeCreatable(null, src, tar, level);
	}

	public TypeError checkIfEdgeCreatable(final Graph g, final Node src, final Node tar, final int level) {
		// System.out.println("TypeImpl.checkIfEdgeCreatable(Node src, Node tar,
		// int level)");
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
//		System.out.println("TypeImpl.checkTargetMax(final Graph g, final Node src, final Node tar)");
		
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
											+ g.getName()
											+ "\" ).", tar, this);
						}
					}
					return null;
				}
			}
		}
		return null;
	}

	/*
	 * Target Max Multiplicity means how many ( at most ) nodes of the target node type
	 * are outgoing from the source node. 
	 */
	public TypeError checkTargetMax(final Graph g, final Node src, final Node tar) {
//		System.out.println("TypeImpl.checkTargetMax(final Graph g, final Node src, final Node tar)");
		
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
											+ g.getName()
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
	public boolean addTypeGraphObject(final GraphObject nodeOrArc) {
		if (!nodeOrArc.getContext().isTypeGraph()) {
			return false;
		}
		
		if (nodeOrArc instanceof Arc) {
			// get src and target.
			Type sourceType = ((Arc) nodeOrArc).getSource().getType();
			Type targetType = ((Arc) nodeOrArc).getTarget().getType();

			// create a new subtype or get the former defined
			TypeGraphArc subType = getTypeGraphArc(sourceType, targetType);
			if (subType.getArc() == null) {
				subType.addTypeGraphObject((Arc)nodeOrArc);	
				this.typeGraphObjectDefined = true;
			} else {
				return false;
			}
			return true;
			
		} 
		if (this.typeGraphNode == null) {
			this.typeGraphNode = new TypeGraphNode();
		}			
		if (this.typeGraphNode.getNode() == null) {
			// set type graph object of node type
			this.typeGraphNode.addTypeGraphObject((Node) nodeOrArc);
			this.typeGraphObjectDefined = true;
		} else {
			return false;
		}
		return true;
	}

	/**
	 * Remove the given GraphObject from the type graph and from this type.
	 * Returns true if remove is done, otherwise false. To remove an GraphObject
	 * is not possible when the type graph check is activated.
	 */	
	public boolean removeTypeGraphObject(final GraphObject nodeOrArc, final boolean forceToRemove) {
		if (nodeOrArc == null 
				|| nodeOrArc.getContext() == null
				|| !nodeOrArc.getContext().isTypeGraph())
			return true;
		
		boolean allowedToRemove = false;
		if (nodeOrArc.getContext().getTypeSet().getLevelOfTypeGraphCheck() <= TypeSet.ENABLED_INHERITANCE) { //TypeSet.DISABLED) {
			allowedToRemove = true;
		} else if (nodeOrArc.isNode() && forceToRemove) {
			allowedToRemove = true;
		} else if (nodeOrArc.isArc()) {
			if (forceToRemove)
				allowedToRemove = true;
			else
				allowedToRemove = false;
		} 
		
		if (allowedToRemove) {
			if (nodeOrArc instanceof Arc) {
				if (this.edgeTypeGraphObjects == null) 
					return true;

				// get src and target
				Type sourceType = ((Arc) nodeOrArc).getSource().getType();
				Type targetType = ((Arc) nodeOrArc).getTarget().getType();

				HashMap<Type, TypeGraphArc> 
				targets = this.edgeTypeGraphObjects.get(sourceType);
				if (targets == null) {
					return true;
				}
				TypeGraphArc subType = targets.get(targetType);
				if (subType == null) {
					return true;
				}

				if (nodeOrArc.getContext().getTypeSet().getLevelOfTypeGraphCheck() <= TypeSet.ENABLED_INHERITANCE) { //TypeSet.DISABLED) 
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
			if (this.typeGraphNode == null) {
				return true;
			}
			// mark that a type node is not defined	
			if (forceToRemove) {
				this.typeGraphNode.forceRemoveTypeGraphObject();
			} else if (!this.typeGraphNode.removeTypeGraphObject()) {
				return false;
			}
				
			this.typeGraphObjectDefined = false;
			return true;					
		} 
		return false;
	}
	
	/**
	 * Remove the given GraphObject from the type graph and from this type.
	 * Returns true if remove is done, othewise false. To remove an GraphObject
	 * is not possible when the type graph check is activated.
	 */	
	public boolean removeTypeGraphObject(final GraphObject nodeOrArc) {
		return removeTypeGraphObject(nodeOrArc, false);
	}

	/**
	 * Remove type graph objects from this type.
	 * 
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

					// the sub type is now empty, so we can remove it
					subIter.remove();
				}

				if (actMap.isEmpty()) {
					// if there is no type graph arc with this source, we can
					// remove the Hash Map
					iter.remove();
				}
			}
		} 
		else if (this.typeGraphNode != null) {
			this.typeGraphNode.forceRemoveTypeGraphObject();
			this.typeGraphObjectDefined = false;
		}
	}



	/**
	 * Set the min of the source multiplicity of an edge type to the value. The
	 * edge type is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public void setSourceMin(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setSourceMin(value);
	}

	/**
	 * Set the max of the source multiplicity of an edge type to the value. The
	 * edge type is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public void setSourceMax(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setSourceMax(value);
	}

	/**
	 * Set the min of the target multiplicity of an edge type to the value. The
	 * edge type is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public void setTargetMin(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setTargetMin(value);
	}

	/**
	 * Set the max of the target multiplicity of an edge type to the value. The
	 * edge type is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public void setTargetMax(final Type sourceType, final Type targetType, final int value) {
		this.getTypeGraphArc(sourceType, targetType).setTargetMax(value);
	}

	/**
	 * Return the min of the source multiplicity of an edge type. The edge type
	 * is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public int getSourceMin(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getSourceMin();
	}

	/**
	 * Return the max of the source multiplicity of an edge type. The edge type
	 * is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public int getSourceMax(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getSourceMax();
	}

	/**
	 * Return the min of the target multiplicity of an edge type. The edge type
	 * is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public int getTargetMin(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getTargetMin();
	}

	/**
	 * Return the max of the target multiplicity of an edge type. The edge type
	 * is defined throught the node type sourceType and the node type
	 * targetType.
	 */
	public int getTargetMax(final Type sourceType, final Type targetType) {
		return this.getTypeGraphArc(sourceType, targetType).getTargetMax();
	}

	/**
	 * Set the min of the multiplicity of a node type to the value. The node
	 * type is defined throught the sourceType .
	 */
	public void setSourceMin(final int value) {
		if (this.typeGraphNode != null)
			this.typeGraphNode.setSourceMin(value);
	}

	/**
	 * Set the max of the multiplicity of a node type to the value. The node
	 * type is defined throught the sourceType .
	 */
	public void setSourceMax(final int value) {
		if (this.typeGraphNode != null)
			this.typeGraphNode.setSourceMax(value);
	}

	/**
	 * Return the min of the multiplicity of a node type. The node type is
	 * defined throught the sourceType.
	 */
	public int getSourceMin() {
		if (this.typeGraphNode != null)
			return this.typeGraphNode.getSourceMin();
		
		return -1;
	}

	/**
	 * Return the max of the multiplicity of a node type. The node type is
	 * defined throught the sourceType.
	 */
	public int getSourceMax() {
		if (this.typeGraphNode != null)
			return this.typeGraphNode.getSourceMax();
		
		return -1;
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
		Arc arc = null;
		TypeGraphArc tgarc = getTypeGraphArc(sourceType, targetType);
		if (tgarc != null) {
			arc = tgarc.getArc();
		}
		return arc;
	}

	/**
	 * Returns the subtype object for this source and target combination. The
	 * subtype will be created, if it not exists.
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
			if (targets == null)
				continue;
			for (int j = 0; j < myTarParents.size(); ++j) {
				myTarType = myTarParents.get(j);
				subType = targets.get(myTarType);
				if (subType != null) {
					return subType;
				}
			}
		}
			
		if (this.edgeTypeGraphObjects.get(sourceType) == null) {
			targets = new HashMap<Type, TypeGraphArc>();
			this.edgeTypeGraphObjects.put(sourceType, targets);
			subType = new TypeGraphArc();
			targets.put(targetType, subType);
		} else
			targets = this.edgeTypeGraphObjects.get(sourceType);
		
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
		if (subType == null)
			return false;
		return true;
	}

	public boolean hasTypeGraphArc(final GraphObject sourceType,
			final GraphObject targetType) {
		return hasTypeGraphArc(sourceType.getType(), targetType.getType());
	}

	public HashMap<Type, HashMap<Type, TypeGraphArc>> getArcTypeGraphObjects() {
		return this.edgeTypeGraphObjects;
	}

	public boolean compareTypeGraphArcs(final Type t) {
		if (this.edgeTypeGraphObjects == null
				&& t.getArcTypeGraphObjects() == null) {
			return true;
		}
		else if (this.edgeTypeGraphObjects != null
				&& t.getArcTypeGraphObjects() != null) {
			if (!this.edgeTypeGraphObjects.isEmpty()
					&& !t.getArcTypeGraphObjects().isEmpty()) {
				Iterator<Type> sourceIter = this.edgeTypeGraphObjects.keySet().iterator();
				while (sourceIter.hasNext()) {
					Type srct = sourceIter.next();
					HashMap<Type, TypeGraphArc> targetsMap = this.edgeTypeGraphObjects
							.get(srct);
					Iterator<Type> targetsIter = targetsMap.keySet().iterator();
					while (targetsIter.hasNext()) {
						Type tart = targetsIter.next();
//						TypeGraphArc subType = targetsMap.get(tart);
						TypeGraphArc subType_t = t.getSimilarTypeGraphArc(srct, tart);
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
				&& t.getArcTypeGraphObjects() == null) {
			return true;
		}
		else if (this.edgeTypeGraphObjects != null
				&& t.getArcTypeGraphObjects() != null) {
			if (!this.edgeTypeGraphObjects.isEmpty()
					&& !t.getArcTypeGraphObjects().isEmpty()) {
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
						TypeGraphArc subType_t = t.getSimilarTypeGraphArc(srct, tart);
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

	public void setVisibilityOfObjectsOfTypeGraphNode(boolean vis) {
		if (this.typeGraphNode != null) {
			this.typeGraphNode.setVisible(vis);
		}
	}
	
	public boolean isObjectOfTypeGraphNodeVisible() {
		return (this.typeGraphNode == null) || this.typeGraphNode.isVisible();
	}
	
	/**
	 * Returns the subtype object for this node type. 
	 */
	public TypeGraphNode getTypeGraphNode() {
		if (this.typeGraphNode == null)
			this.typeGraphNode = new TypeGraphNode();
		return this.typeGraphNode;
	}

	/**
	 * returns true, 
	 * if there is an object in the type graph for this type.
	 */
	public boolean hasTypeGraphNode() {
		if (this.typeGraphNode != null 
				&& this.typeGraphNode.getNode() != null)
			return true;
		
		return false;
	}

	/**
	 * returns the type graph node, if it is defined.
	 */
	public Node getTypeGraphNodeObject() {
		if (this.typeGraphNode != null)
			return this.typeGraphNode.getNode();
		
		return null;
	}

	
	/**
	 * Returns my own outgoing arc types on condition that a type graph is
	 * defined, otherwise returns an empty vector.
	 */
	public Vector<Type> getOwnOutgoingArcTypes() {
		Vector<Type> result = new Vector<Type>();
		if (this.typeGraphNode != null 
				&& this.typeGraphNode.getNode() != null){
			final Iterator<Arc> outs = this.typeGraphNode.getNode().getOutgoingArcs();
			while ( outs.hasNext()) {
				result.add(outs.next().getType());
			}
		}
		return result;
	}

	/**
	 * Returns all outgoing arc types (of my own and of my parents) on condition
	 * that a type graph is defined, otherwise returns an empty vector.
	 */
	public Vector<Type> getOutgoingArcTypes() {
		Vector<Type> result = new Vector<Type>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			result.addAll(this.getOwnOutgoingArcTypes());
			Vector<Type> myparents = this.getAllParents();
			for (int i = 0; i < myparents.size(); i++) {
				result.addAll(myparents.get(i)
						.getOwnOutgoingArcTypes());
			}
		}
		return result;
	}

	/**
	 * Returns my own outgoing arcs on condition that a type graph is defined,
	 * otherwise returns an empty vector.
	 */
	public List<Arc> getOwnOutgoingArcs() {
		Vector<Arc> result = new Vector<Arc>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			result.addAll(this.typeGraphNode.getNode().getOutgoingArcsSet());
		}
		return result;
	}

	/**
	 * Returns all outgoing arcs (of my own and of my parents) on condition that
	 * a type graph is defined, otherwise returns an empty vector.
	 */
	public Vector<Arc> getOutgoingArcs() {
		Vector<Arc> result = new Vector<Arc>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			result.addAll(this.typeGraphNode.getNode().getOutgoingArcsSet());
			Vector<Type> myparents = this.getAllParents();
			for (int i = 0; i < myparents.size(); i++) {
				result.addAll(myparents.get(i)
						.getOwnOutgoingArcs());
			}
		}
		return result;
	}

	/**
	 * Returns my own incoming arc types on condition that a type graph is
	 * defined, otherwise returns an empty vector.
	 */
	public Vector<Type> getOwnIncomingArcTypes() {
		final Vector<Type> result = new Vector<Type>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			final Iterator<Arc> arcs = this.typeGraphNode.getNode().getIncomingArcs();
			while (arcs.hasNext()) {
				result.add(arcs.next().getType());
			}
		}
		return result;
	}

	/**
	 * Returns all incoming arc types (of my own and of my parents) on condition
	 * that a type graph is defined, otherwise returns an empty vector.
	 */
	public Vector<Type> getIncomingArcTypes() {
		Vector<Type> result = new Vector<Type>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			result.addAll(this.getOwnIncomingArcTypes());
			Vector<Type> myparents = this.getAllParents();
			for (int i = 0; i < myparents.size(); i++) {
				result.addAll(myparents.get(i)
						.getOwnIncomingArcTypes());
			}
		}
		return result;
	}

	/**
	 * Returns my own incoming arcs on condition that a type graph is defined,
	 * otherwise returns an empty vector.
	 */
	public List<Arc> getOwnIncomingArcs() {
		Vector<Arc> result = new Vector<Arc>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null)
			result.addAll(this.typeGraphNode.getNode().getIncomingArcsSet());
		
		return result;
	}

	/**
	 * Returns all incoming arcs (of my own and of my parents) on condition that
	 * a type graph is defined, otherwise returns an empty vector.
	 */
	public List<Arc> getIncomingArcs() {
		Vector<Arc> result = new Vector<Arc>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			result.addAll(this.typeGraphNode.getNode().getIncomingArcsSet());
			Vector<Type> myparents = this.getAllParents();
			for (int i = 0; i < myparents.size(); i++) {
				result.addAll(myparents.get(i)
						.getOwnIncomingArcs());
			}
		}
		return result;
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
						"Too few edges of type \"" + getNameForType(arc.getType())
								+ "\"" + " (would) start at the source node  " + "\""
								+ getNameForType(arc.getSource().getType())
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
						"Too few edges of type \"" + getNameForType(arc.getType())
								+ "\"" + " (would) end at the target node  " + "\""
								+ getNameForType(arc.getTarget().getType())
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
	 * The spcified node must be an instance of my.
	 */
	public TypeError checkIfRemovable(final Node node, final int level) {
		if (node.getType() != this 
				|| level != TypeSet.ENABLED_MAX_MIN)
			return null;

		if (node.getContext() instanceof TypeGraph)
			return null;

		return checkMinMultiplicityOfNodeType(node);
	}

	/**
	 * The spcified node must be an instance of my.
	 */
	private TypeError checkMinMultiplicityOfNodeType(final Node node) {		
		if (this.typeGraphNode == null) {
			return null;
		}
		
		int minValue = this.typeGraphNode.getSourceMin();
		if (minValue != UNDEFINED) {
			List<Node> list = node.getContext().getNodes(this);
			int count = list != null? list.size(): 0;
			if (count - 1 < minValue) {
				return new TypeError(TypeError.TO_LESS_NODES,
						"Not enough nodes of type \"" + getNameForType(this)
								+ "\"" + " .\nThere are at least " + minValue
								+ " needed ( graph \""
								+ node.getContext().getName() + "\" ).", node,
						this);
			}
		}
		return null;
	}

	/**
	 * This type must be a node type.
	 * The specified Graph is not a type graph.
	 * Check the max maltiplicity of this type because a new node should be created.
	 * Returns an error if this check failed, otherwise - null.
	 */
	public TypeError checkIfNodeCreatable(final Graph g, final int level) {
		if (level != TypeSet.ENABLED_MAX_MIN)
			return null;

		if (g instanceof TypeGraph)
			return null;

		return checkMaxMultiplicityOfNodeType(g);
	}
	
	/**
	 * The spcified Graph is not a type graph.
	 * Check the max maltiplicity of this type because a new node should be created.
	 * Returns an error if this check failed, otherwise - null.
	 */
	private TypeError checkMaxMultiplicityOfNodeType(final Graph g) {				
		if (this.typeGraphNode == null) {
			return null;
		}
		
		int maxValue = this.typeGraphNode.getSourceMax();
		if (maxValue != UNDEFINED) {
			List<Node> list = g.getNodes(this);
			int count = list != null? list.size(): 0;
			if (count + 1 > maxValue) {
				return new TypeError(TypeError.TO_MUCH_NODES,
						"Too many nodes of type \"" + getNameForType(this)
								+ "\"" + " .\nThere are at most " + maxValue
								+ " allowed ( graph \""
								+ g.getName() + "\" ).", 
						this);
			}
		}
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getClan()
	 */
	public List<Type> getClan() {
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			return new Vector<Type>(getAllChildren());
		} 
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasCommonParentWith(agg.xt_basis.Type)
	 */
	public boolean hasCommonParentWith(Type t) {
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			return !getCommonParentWith(t).isEmpty();
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isInClanOf(agg.xt_basis.Type)
	 */
	public boolean isInClanOf(Type t) {
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			if (t.hasCommonParentWith(this)
					|| this.isChildOf(t))
				return true;
		}
		return false;
	}
	
	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasChild()
	 */
	public boolean hasChild() {
		return (this.isNodeType())? !this.itsChildren.isEmpty(): false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasParent()
	 */
	public boolean hasParent() {
		return (this.isNodeType())? !this.itsParents.isEmpty(): false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isParentAttrTypeEmpty()
	 */
	public boolean isParentAttrTypeEmpty() {
		if (this.isNodeType()) {
			List<Type> list = this.getAllParents();
			for (int i = 1; i < list.size(); i++) {
				NodeTypeImpl t = (NodeTypeImpl) list.get(i);
				if (t.getAttrType() != null
//						&& t.getAttrType().getNumberOfEntries() != 0
						) {
					return false;
				}
			}
		}
		return true;
	}

}
