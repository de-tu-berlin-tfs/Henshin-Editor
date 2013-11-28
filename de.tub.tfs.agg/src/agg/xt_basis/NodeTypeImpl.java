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
 * Implements the type of a node graph object.
 * 
 * @author $Author: olga $
 * @version $Id: NodeTypeImpl.java,v 1.19 2010/11/06 18:34:59 olga Exp $
 */
public class NodeTypeImpl implements Type {

	String comment = "";
	
	boolean isAbstract = false;

	/**
	 * the name of the type
	 */
	String itsStringRepr;

	/*
	 * the parent of the type
	 */
//	Type itsParent;

	/**
	 * the parents of the type
	 */
	final Vector<Type> itsParents = new Vector<Type>(1);

	final Vector<Type> itsChildren = new Vector<Type>(1);

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
	 * this value will be true, 
	 * if a graph object inside of a type graph was defined.
	 */
	boolean typeGraphObjectDefined;

	/** holds additional info about a type graph node */
	TypeGraphNode typeGraphNode;

	String keyStr = null;
	
	protected NodeTypeImpl() {
		this.itsAttrType = null;
		this.itsStringRepr = "";
		this.additionalRepr = ":RECT:java.awt.Color[r=0,g=0,b=0]::[NODE]:";
		
		this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	/**
	 * Creates a new type with the given name. There is still not attributed type.
	 * 
	 * @param name
	 *            the name of the type
	 */
	protected NodeTypeImpl(String name) {
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
	protected NodeTypeImpl(AttrType at, String name) {
		this(name);
		this.itsAttrType = at;
	}

	/**
	 * creates a new type with the given attributes and an empty name.
	 * 
	 * @param at
	 *            the declaration of the attributes
	 */
	protected NodeTypeImpl(AttrType at) {
		this();
		this.itsAttrType = at;
	}

	public void dispose() {
		this.itsAttrType = null;
		
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

		for (int i = 0; i < this.getParents().size(); i++) {
			NodeTypeImpl t = (NodeTypeImpl) this.getParents().get(i);
			if (t.getAttrType() == null)
				t.createAttributeType();
			((DeclTuple) this.itsAttrType).addParent((DeclTuple) t.getAttrType());
		}
	}

	public void setAttributeType(final AttrType at) {
		this.itsAttrType = at;
		if (this.itsAttrType != null) {
			for (int i = 0; i < this.getParents().size(); i++) {
				NodeTypeImpl t = (NodeTypeImpl) this.getParents().get(i);
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
	
	public boolean isAttrTypeEmpty() {
		return (this.getAttrType() == null
//					|| this.getAttrType().getNumberOfEntries() == 0
					);
	}
	
	public boolean isParentAttrTypeEmpty() {
		List<Type> list = this.getAllParents();
		for (int i = 1; i < list.size(); i++) {
			NodeTypeImpl t = (NodeTypeImpl) list.get(i);
			if (t.getAttrType() != null
//					&& t.getAttrType().getNumberOfEntries() != 0
					) {
				return false;
			}
		}
		return true;
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
	
	public boolean isNodeType() {
		return true;
	}

	public boolean isArcType() {
		return false;
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
		if (this.compareTo(t))
			return true;
		for (int i = 0; i < t.getParents().size(); i++) {
			Type tAncestor = t.getParents().get(i);
			if (this.isParentOf(tAncestor))
				return true;
		}
		return false;
	}

	/**
	 * Checks whether there is any relation between this type and the given one or not.
	 * Two types are related if they have at least one common ancestor.
	 */
	public boolean isRelatedTo(final Type t) {
		// multiple inheritance
		if (compareTo(t) || this.isParentOf(t)) {
			return true;
		}
		Vector<Type> allparents = getAllParents();
		// allparents.get(0) (this) is already checked above
		for (int i = 1; i < allparents.size(); i++) {
			Type oldestAncestor = allparents.get(i);
			if (oldestAncestor.isParentOf(t)) {
				return true;
			}
		}
		return false;
	}

	public List<Type> getClan() {
		return new Vector<Type>(getAllChildren());
	}
	
	/**
	 * Returns true when this node type is in inheritance clan of the defined
	 * node type t. The type t can be the parent or a child of its clan.
	 */
	public boolean isInClanOf(final Type t) {
		if (t.hasCommonParentWith(this)
				|| this.isChildOf(t))
			return true;
		
		return false;
	}

	public boolean hasCommonParentWith(final Type t) {
		return !getCommonParentWith(t).isEmpty();
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
		// multiple inheritance allowed
		Vector<Type> myAllParents = new Vector<Type>();
		myAllParents.add(this);
		for (int i = 0; i < this.itsParents.size(); i++) {
			Type currentAncestor = this.itsParents.get(i);
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
		Vector<Type> myAllChildren = new Vector<Type>();
		myAllChildren.add(this);
		for (int i = 0; i < this.getChildren().size(); i++) {
			Type ch = this.getChildren().get(i);
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
	
	public void showRelatives() {
		System.out.println("Type Relatives of  \"" + this.getName()
				+ "\"   size: " + this.itsParents.size());
		for (int i = 0; i < this.itsParents.size(); i++) {
			Type p = this.itsParents.get(i);
			System.out.print("\"" + p.getName() + "\", ");
		}
		System.out.println("\n***************************");
	}

	public void showAllRelatives() {
		Vector<Type> v = getAllParents();
		System.out.println("Type Relatives of  \"" + getName() + "\"   size: "
				+ v.size());
		for (int i = 0; i < v.size(); i++) {
			Type p = v.get(i);
			System.out.print("\"" + p.getName() + "\", ");
		}
		System.out.println("\n***************************");
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
				((NodeTypeImpl) t).createAttributeType();
			}
			if (this.itsAttrType != null) {
				DeclTuple myDeclTuple = (DeclTuple) this.itsAttrType;
				myDeclTuple.addParent((DeclTuple) t.getAttrType());
			}
			this.itsParents.add(t);
//			this.itsParent = t;

			((NodeTypeImpl) t).addChild(this);
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
			((NodeTypeImpl) t).removeChild(this);
			// showRelatives();
			if (this.itsAttrType != null) {
				DeclTuple myDeclTuple = (DeclTuple) this.itsAttrType;
				myDeclTuple.removeParent((DeclTuple) t.getAttrType());
			}
		}
//		if (!itsParents.isEmpty())
//			itsParent = itsParents.lastElement();
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
	 * of a Node - ":RECT:java.awt.Color[r=0,g=0,b=0]:[NODE]:".
	 */
	public void setAdditionalRepr(final String repr) {
		if (repr.equals("NODE") || repr.equals("[NODE]")) {
			this.additionalRepr = ":RECT:java.awt.Color[r=0,g=0,b=0]:[NODE]:";
		} else {
			this.additionalRepr = repr;
		}
		if (!this.keyStr.equals(this.itsStringRepr.concat("%").concat(this.additionalRepr)))
			this.keyStr = this.itsStringRepr.concat("%").concat(this.additionalRepr);
	}

	public void XwriteObject(XMLHelper h) {
		String n = getStringRepr();
//		System.out.println("TypeImpl.XwriteObject: AdditionalRepr:   " +this.additionalRepr);		
		if ((this.additionalRepr != null) && (!this.additionalRepr.equals(""))) {
			n += ("%" + this.additionalRepr);
		}
		int idx = n.indexOf("[NODE]");
		if (idx >= 0) {
			// all parents write first
			h.addEnumeration("", this.itsParents.elements(), true);

			if (this.imageFileName.length() > 0) {
				// insert the image filename into string n to save the additional type representation
				n = n.substring(0, idx).concat(this.imageFileName).concat(":").concat("[NODE]:"); 
			}
			h.openNewElem("NodeType", this);
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

		if (this.itsAttrType != null && this.itsAttrType.getNumberOfEntries() > 0) {
			h.addObject("", this.itsAttrType, true);
		}
		// multiplicity will be written in the Arc / Node
		// object in the type graph
		h.close();
	}

	
	public void XreadObject(XMLHelper h) {
		if (h.isTag("NodeType", this)
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
//			System.out.println("TypeImpl.XreadObject:  isAbstract: " +isAbstract);
			
			int i = n.indexOf('%');
			// set type name
			if (i != -1) {
				String test = n.substring(0, i);					
				test = XMLHelper.checkNameDueToSpecialCharacters(test);
				this.itsStringRepr = test; 
			}
			else {
				String test = XMLHelper.checkNameDueToSpecialCharacters(n);
				this.itsStringRepr = test; 				
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

	/*
	 * If an image file name (.jpg|.gif|.xpm) found, set local parameter this.imageFileName
	 * and returns a string without the image file name.
	 * Otherwise returns the same input string.
	 */
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
	 * Returns the name of a type or "unnamed" if the name is empty string.
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
	 * must be added to theire types. The given object will not tested if this
	 * is its type.
	 * 
	 * @return null, if the graphobject is valid typed otherwise a
	 *         {@link TypeError} if there was a mismatch
	 * 
	 */
	public TypeError check(final GraphObject node, final int level) {
		if (level == TypeSet.DISABLED)
			return null;

		if (node instanceof Node) {
			return check((Node) node, level);
		} 
		throw new IllegalArgumentException(
					"parameter must be of Node type.");
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
				List<Node> list = node.getContext().getNodes(node.getType());
				int count = list != null? list.size(): 0;
				//???				
				if(!node.getContext().isNode(node)) {
					count++; // a node is created and should be added to nodes of a graph
				}
				// if the level is set, check for max / min constraints
				if (level == TypeSet.ENABLED_MAX
						|| level == TypeSet.ENABLED_MAX_MIN) {
					int sourceMax = this.typeGraphNode.getSourceMax();
					if (sourceMax != UNDEFINED) {
						if ((count > sourceMax)) {
							return new TypeError(TypeError.TO_MUCH_NODES,
									"- Too many ("+count+") nodes of type \""
											+ getName()
											+ "\".\nThere are only "
											+ sourceMax + " allowed ( graph \""
											+ node.getContext().getName()
											+ "\" ).", node, this);
						}
					}
				}
				// check Min
				if (level == TypeSet.ENABLED_MAX_MIN) {
					int sourceMin = this.typeGraphNode.getSourceMin();
					if (sourceMin > 0) {
						if (count < sourceMin) {
							return new TypeError(TypeError.TO_LESS_NODES,
									"- Not enough ("+count+") nodes of type \""
											+ getName()
											+ "\".\nThere are at least "
											+ sourceMin + " needed ( graph \""
											+ node.getContext().getName()
											+ "\" ).", node, this);
						}
					}
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
	 * Add the given GraphObject of a type graph to this type. 
	 * The GraphObject nodeOrArc must be of this type:
	 * it is a Node if this is a node type,
	 * it is an Arc if this is an edge type. 
	 * In case of it is a node type and a node object inside of a type graph
	 * is already exist, it should to be removed first.
	 */
	public boolean addTypeGraphObject(final GraphObject node) {
		if (!node.getContext().isTypeGraph()) {
			return false;
		}
		
		if (node instanceof Node) {
			if (this.typeGraphNode == null)  
				this.typeGraphNode = new TypeGraphNode();
						
			// set type graph object of node type
			this.typeGraphNode.addTypeGraphObject((Node) node);
			this.typeGraphObjectDefined = true;
				
			return true;			
		} 
		return false;
	}

	/**
	 * Remove the given GraphObject from the type graph and from this type.
	 * Returns true if remove is done, otherwise false. To remove an GraphObject
	 * is not possible when the type graph check is activated.
	 */	
	public boolean removeTypeGraphObject(final GraphObject node, 
			final boolean forceToRemove) {
		if (node == null 
				|| !node.isNode()
				|| node.getContext() == null
				|| !node.getContext().isTypeGraph())
			return true;
		
		boolean allowedToRemove = false;
		if (node.getContext().getTypeSet().getLevelOfTypeGraphCheck() 
											<= TypeSet.ENABLED_INHERITANCE
				|| forceToRemove) {
			allowedToRemove = true;
		}
		else {
			allowedToRemove = false;
		} 
		
		if (allowedToRemove) {
			if (this.typeGraphNode == null) {
				return true;
			}
				
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
	 * Returns true if remove is done, otherwise false. To remove an GraphObject
	 * is not possible when the type graph check is activated.
	 */	
	public boolean removeTypeGraphObject(final GraphObject node) {
		return removeTypeGraphObject(node, false);
	}

	/**
	 * Disable type graph object of this type.
	 */
	public void disableTypeGraphObject() {
		if (this.typeGraphNode != null) {
			this.typeGraphNode.forceRemoveTypeGraphObject();
			this.typeGraphObjectDefined = false;
		}
	}
	
	
	/**
	 * Set the min of the multiplicity of a node type.
	 */
	public void setSourceMin(final int value) {
		if (this.typeGraphNode != null)
			this.typeGraphNode.setSourceMin(value);
	}

	/**
	 * Set the max of the multiplicity of a node type to the value. The node
	 * type is defined through the sourceType .
	 */
	public void setSourceMax(final int value) {
		if (this.typeGraphNode != null)
			this.typeGraphNode.setSourceMax(value);
	}

	/**
	 * Return the min of the multiplicity of a node type. The node type is
	 * defined through the sourceType.
	 */
	public int getSourceMin() {
		if (this.typeGraphNode != null)
			return this.typeGraphNode.getSourceMin();
		
		return -1;
	}

	/**
	 * Return the max of the multiplicity of a node type.
	 */
	public int getSourceMax() {
		if (this.typeGraphNode != null)
			return this.typeGraphNode.getSourceMax();
		
		return -1;
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
	 * Returns the type graph node for this node type. 
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
		return (this.typeGraphNode == null)? null: this.typeGraphNode.getNode();
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
				result.addAll(((NodeTypeImpl) myparents.get(i))
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
				result.addAll(((NodeTypeImpl) myparents.get(i))
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
				result.addAll(((NodeTypeImpl) myparents.get(i))
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
	public Vector<Arc> getIncomingArcs() {
		Vector<Arc> result = new Vector<Arc>();
		if (this.typeGraphNode != null
				&& this.typeGraphNode.getNode() != null) {
			result.addAll(this.typeGraphNode.getNode().getIncomingArcsSet());
			Vector<Type> myparents = this.getAllParents();
			for (int i = 0; i < myparents.size(); i++) {
				result.addAll(((NodeTypeImpl) myparents.get(i))
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


	/**
	 * The specified node must be an instance of my.
	 */
	public TypeError checkIfRemovable(final Node node, final int level) {
		if (node.getType() != this 
				|| level != TypeSet.ENABLED_MAX_MIN
				|| node.getContext() instanceof TypeGraph)
			return null;

		return checkMinMultiplicityOfNodeType(node);
	}

	/**
	 * The specified node must be an instance of my.
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
						"Not enough nodes of type \"" + getName()
								+ "\"" + " .\nThere are at least " + minValue
								+ " needed ( graph \""
								+ node.getContext().getName() + "\" ).", node,
						this);
			}
		}
		return null;
	}

	/**
	 * The specified Graph is not a type graph.
	 * Check the max multiplicity of this type because a new node should be created.
	 * Returns an error if this check failed, otherwise - null.
	 */
	public TypeError checkIfNodeCreatable(final Graph g, final int level) {
		if (level != TypeSet.ENABLED_MAX_MIN
				|| g instanceof TypeGraph)
			return null;

		return checkMaxMultiplicityOfNodeType(g);
	}
	
	/**
	 * The specified Graph is not a type graph.
	 * Check the max multiplicity of this type because a new node should be created.
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
						"Too many nodes of type \"" + getName()
								+ "\"" + " .\nThere are at most " + maxValue
								+ " allowed ( graph \""
								+ g.getName() + "\" ).", 
						this);
			}
		}
		return null;
	}

	
	
	
	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfRemovableFromSource(agg.xt_basis.GraphObject, agg.xt_basis.Arc, int)
	 */
	public TypeError checkIfRemovableFromSource(
			final GraphObject node, Arc arc,
			int level) {
		if (arc.getContext().isCompleteGraph()
				&& level == TypeSet.ENABLED_MAX_MIN)
			return checkSourceMin(node, arc, false, false);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfRemovableFromSource(agg.xt_basis.GraphObject, agg.xt_basis.Arc, boolean, boolean, int)
	 */
	public TypeError checkIfRemovableFromSource(final GraphObject node, final Arc arc,
			boolean deleteSrc, boolean deleteTar, int level) {
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
//		System.out.println("NodeTypeImpl.checkSourceMin(final GraphObject srcnode, final Arc arc)");

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
	
	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfRemovableFromTarget(agg.xt_basis.GraphObject, agg.xt_basis.Arc, int)
	 */
	public TypeError checkIfRemovableFromTarget(
			final GraphObject node, 
			final Arc arc,
			int level) {
		if (arc.getContext().isCompleteGraph()
				&& level == TypeSet.ENABLED_MAX_MIN)
			return checkTargetMin(node, arc, false, false);
		
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfRemovableFromTarget(agg.xt_basis.GraphObject, agg.xt_basis.Arc, boolean, boolean, int)
	 */
	public TypeError checkIfRemovableFromTarget(
			final GraphObject node, 
			final Arc arc,
			boolean deleteSrc, 
			boolean deleteTar, 
			int level) {
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
//		System.out.println("NodeTypeImpl.checkTargetMin(final GraphObject tarnode, final Arc arc)");
		
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
	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getSourceMax(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public int getSourceMax(Type sourceType, Type targetType) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getSourceMin(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public int getSourceMin(Type sourceType, Type targetType) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTargetMax(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public int getTargetMax(Type sourceType, Type targetType) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTargetMin(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public int getTargetMin(Type sourceType, Type targetType) {
		return 0;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTypeGraphArcObject(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public Arc getTypeGraphArcObject(Type sourceType, Type targetType) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasTypeGraphArc()
	 */
	public boolean hasTypeGraphArc() {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isObjectOfTypeGraphArcVisible(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public boolean isObjectOfTypeGraphArcVisible(Type sourceType,
			Type targetType) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setSourceMax(agg.xt_basis.Type, agg.xt_basis.Type, int)
	 */
	public void setSourceMax(Type sourceType, Type targetType, int value) {		
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setSourceMin(agg.xt_basis.Type, agg.xt_basis.Type, int)
	 */
	public void setSourceMin(Type sourceType, Type targetType, int value) {		
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setTargetMax(agg.xt_basis.Type, agg.xt_basis.Type, int)
	 */
	public void setTargetMax(Type sourceType, Type targetType, int value) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setTargetMin(agg.xt_basis.Type, agg.xt_basis.Type, int)
	 */
	public void setTargetMin(Type sourceType, Type targetType, int value) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#setVisibityOfObjectsOfTypeGraphArc(agg.xt_basis.Type, agg.xt_basis.Type, boolean)
	 */
	public void setVisibityOfObjectsOfTypeGraphArc(Type sourceType,
			Type targetType, boolean vis) {
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasTypeGraphArc(agg.xt_basis.GraphObject, agg.xt_basis.GraphObject)
	 */
	public boolean hasTypeGraphArc(GraphObject sourceType,
			GraphObject targetType) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasTypeGraphArc(agg.xt_basis.Type)
	 */
	public boolean hasTypeGraphArc(Type sourceType) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasTypeGraphArc(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public boolean hasTypeGraphArc(Type sourceType, Type targetType) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isEdgeCreatable(agg.xt_basis.Type, agg.xt_basis.Type, int)
	 */
	public boolean isEdgeCreatable(Type sourceType, Type targetType, int level) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTargetsOfArc(agg.xt_basis.Type)
	 */
	public Vector<Type> getTargetsOfArc(Type sourceType) {
		return new Vector<Type>(0);
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#isTypeGraphArcUsed(agg.xt_basis.Arc)
	 */
	public boolean isTypeGraphArcUsed(Arc arc) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getArcTypeGraphObjects()
	 */
	public HashMap<Type, HashMap<Type, TypeGraphArc>> getArcTypeGraphObjects() {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getSimilarTypeGraphArc(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public TypeGraphArc getSimilarTypeGraphArc(Type sourceType, Type targetType) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#getTypeGraphArc(agg.xt_basis.Type, agg.xt_basis.Type)
	 */
	public TypeGraphArc getTypeGraphArc(Type sourceType, Type targetType) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfEdgeCreatable(agg.xt_basis.Node, agg.xt_basis.Node, int)
	 */
	public TypeError checkIfEdgeCreatable(Node src, Node tar, int level) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkIfEdgeCreatable(agg.xt_basis.Graph, agg.xt_basis.Node, agg.xt_basis.Node, int)
	 */
	public TypeError checkIfEdgeCreatable(Graph g, Node src, Node tar, int level) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkSourceMax(agg.xt_basis.Graph, agg.xt_basis.Node, agg.xt_basis.Node)
	 */
	public TypeError checkSourceMax(Graph g, Node src, Node tar) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#checkTargetMax(agg.xt_basis.Graph, agg.xt_basis.Node, agg.xt_basis.Node)
	 */
	public TypeError checkTargetMax(Graph g, Node src, Node tar) {
		return null;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#compareTypeGraphArcs(agg.xt_basis.Type)
	 */
	public boolean compareTypeGraphArcs(Type t) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#compareTypeGraphArcsMultiplicity(agg.xt_basis.Type)
	 */
	public boolean compareTypeGraphArcsMultiplicity(Type t) {
		return false;
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasChild()
	 */
	public boolean hasChild() {
		return !this.itsChildren.isEmpty();
	}

	/* (non-Javadoc)
	 * @see agg.xt_basis.Type#hasParent()
	 */
	public boolean hasParent() {
		return !this.itsParents.isEmpty();
	}
	
	
}
