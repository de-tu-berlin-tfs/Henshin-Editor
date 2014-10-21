package agg.attribute.impl;

import java.util.Enumeration;
import java.util.Vector;

import agg.attribute.AttrEvent;
import agg.attribute.AttrMember;
import agg.attribute.AttrObserver;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerType;
import agg.attribute.view.AttrViewSetting;
import agg.attribute.view.impl.OpenViewSetting;
import agg.util.XMLHelper;
import agg.util.XMLObject;

//import agg.util.Debug;

/**
 * @author $Author: olga $
 * @version $Id: DeclTuple.java,v 1.30 2010/11/28 22:11:36 olga Exp $
 */
@SuppressWarnings("serial")
public class DeclTuple extends TupleObject implements AttrType, AttrMsgCode,
		XMLObject {

	protected static AttrViewSetting fixedFormSetting;

	private OpenViewSetting view;

	/** Parent list of this type. All parent entries are "inherited". */
	final protected Vector<DeclTuple> parents = new Vector<DeclTuple>(5);

	final private Vector<DeclTuple> directParents = new Vector<DeclTuple>(5);

	protected boolean loneDeclaration = false;


	public DeclTuple(AttrTupleManager manager) {
		this(manager, null);
	}

	/**
	 * @param parent
	 *            For inheritance purposes.
	 */
	public DeclTuple(AttrTupleManager manager, DeclTuple parent) {
		super(manager, parent);
		this.view = null;
//		Thread.dumpStack();
	}

	public synchronized void dispose() {
		super.dispose();
		if (this.view != null) {
			this.view.removeFormat(this);
		}		
		this.view = null;
	}

	protected void finalize() {
		super.finalize();
	}

	// multiple inheritance
	/**
	 * Removes all parents.
	 */
	protected void removeParents() {
		for (int i = 0; i < this.parents.size(); i++) {
			this.parents.get(i).removeObserver(this);
		}
		this.parents.clear();
	}

	// multiple inheritance 
	protected Vector<DeclTuple> getParents() {
		return this.parents;
	}

	public Enumeration<DeclTuple> getAllParents() {
		return this.parents.elements();
	}
	
	/**
	 * @param index
	 *            specifies the position of my direct parent to return
	 * @return parent
	 */
	protected DeclTuple getParent(int index) {
		if (index < this.directParents.size())
			return this.directParents.get(index);
		
		return null;
	}

	public void setView(OpenViewSetting ovs) {
		this.view = ovs;
	}

	public OpenViewSetting getView() {
		return this.view;
	}

	/**
	 * Own size is added to the parents' size, recursively, so the result ist
	 * the total number of declarations known to this instance.
	 */
	public int getSize() {
		// return getParentSize() + rawGetSize();
		return getParentsSize() + rawGetSize(); 
	}

	// multiple inheritance 
	public int getParentSize(DeclTuple p) {
		if (this.parents.contains(p)) {
			return p.getSize();
		} 
		return 0;
	}

	// multiple inheritance 
	protected int getParentsSize() {
		int s = 0;
		if (this.parents.isEmpty())
			return 0;

		for (int i = 0; i < this.parents.size(); i++) {
			s = s + this.parents.get(i).rawGetSize(); // getSize();
		}
		return s;
	}

	public Vector<DeclTuple> getDirectParents() {
		return this.directParents;
	}

//	private void showParents(Vector<DeclTuple> parentList) {
//		if (parentList == parents)
//			System.out.println("DeclTuple:: All Relatives of  \""
//					+ this.hashCode() + "\"" + "  :  " + parentList.size());
//		else if (parentList == directParents)
//			System.out.println("DeclTuple:: Direct Relatives of  \""
//					+ this.hashCode() + "\"" + "  :  " + parentList.size());
//		for (int i = 0; i < parentList.size(); i++) {
//			TupleObject p = parentList.get(i);
//			System.out.print("\"" + p.hashCode() + "\", ");
//		}
//		System.out.println("\n***************************");
//	}

	/**
	 * Transforming a root index into the corresponding index of this leaf.
	 * 
	 * @param rootIndex
	 *            Relative to the root of the inheritance tree.
	 * @return Corresponding index value for this leaf.
	 */
	protected int toLeafIndex(int rootIndex) {
		// return rootIndex - getParentSize();
		return rootIndex - getParentsSize();
	}

	/**
	 * Obtaining the index relative to the root of the inheritance tree.
	 * 
	 * @param leafIndex
	 *            Refers to this leaf.
	 * @return Corresponding index value referring to the root.
	 */
	protected int toRootIndex(int leafIndex) {
		// return leafIndex + getParentSize();
		return leafIndex + getParentsSize();
	}

	/**
	 * Getting a member from the container of this child.
	 */
	protected AttrMember getLeafMemberAt(int rootIndex) {
		return rawGetMemberAt(toLeafIndex(rootIndex));
	}

	/**
	 * Convenience method for internal operations; works much like the generic
	 * getMemberAt( int index ), but returns the appropriate member type.
	 * 
	 * @see agg.attribute.impl.TupleObject#getMemberAt( int )
	 */
	protected DeclMember getDeclMemberAt(int index) {
		return (DeclMember) getMemberAt(index);
	}

	/**
	 * Convenience method for internal operations; works much like the generic
	 * getMemberAt( int index ), but returns the appropriate member type.
	 * 
	 * @see agg.attribute.impl.TupleObject#getMemberAt( String )
	 */
	protected DeclMember getDeclMemberAt(String name) {
		return (DeclMember) getMemberAt(name);
	}

	/**
	 * Subclasses use/override this method in order to create their own members
	 * of the appropriate type.
	 */
	protected DeclMember newMember() {
		return new DeclMember(this);
	}

	protected void deleteLeafMemberAt(int rootIndex){
		synchronized (this) {
			fireAttrChanged(AttrEvent.MEMBER_TO_DELETE, rootIndex);				
			rawDeleteMemberAt(toLeafIndex(rootIndex));
			fireAttrChanged(AttrEvent.MEMBER_DELETED, rootIndex);
		}
	}

	protected AttrViewSetting getForm() {
		return fixedFormSetting;
	}

	protected void setForm(AttrViewSetting formSetting) {
		fixedFormSetting = formSetting;
	}

	public DeclTuple getTupleType() {
		return this;
	}

	// Services for DeclMember

	/**
	 * Checking the validity of a name as a unique key for a member. Tests if
	 * 'name' is assigned to more than one member. If so, the method
	 * 'setNameValid( false )' is invoked on every such member. Otherwise,
	 * 'setNameValid( true )' is invoked on the member with this name if such a
	 * member exists. Called by DeclMember when changing the name.
	 */
	public void checkNameValidity(String name) {
		if (name == null)
			return;
		
//		if (isClassName(name)) {
//			getDeclMemberAt(name).setNameValid(false);
//			return;
//		}

		int first = -1;
		boolean valid = true;
		for (int i = 0; i < getSize(); i++) {
			// for( int i = 0; i<rawGetSize(); i++ ){ 

			if (getMemberAt(i) == null)
				continue;

			if (name.equals(getMemberAt(i).getName())) {
				// First time ?
				if (first == -1) {
					first = i;
				} else {
					// Now it's not the first time
//					valid = false;
					getDeclMemberAt(i).setNameValid(false);
				}
			}
		}
		if (first != -1)
			getDeclMemberAt(first).setNameValid(valid);
	}

	
	/**
	 * This method is only used in 
	 * <code>agg.attribute.gui.impl.TupleTableModel.setItem(Object,AttrMember,int,AttrTuple,int)</code>
	 * to check the name and the value of an attribute member.
	 * A Class name should not be used for the name and the value of an attribute member.
	 */
	public boolean isClassName(String name) {
		Boolean cache = ((AttrTupleManager)AttrTupleManager.getDefaultManager()).classNameLookupMap.get(name);
		if (cache != null) {
			return cache.booleanValue();
		}
		
		boolean isClass = false;
		try {
			Class.forName(name);
			isClass = true;
			if (cache == null) {
				((AttrTupleManager)AttrTupleManager.getDefaultManager()).classNameLookupMap.put(name, Boolean.TRUE);
			}
		} 
		catch (ClassNotFoundException ex) {}
		catch (NoClassDefFoundError ex1) {}
		if (!isClass) {
			// construct class name as package.class
			agg.attribute.handler.AttrHandler attrHandlers[] = agg.attribute.impl.AttrTupleManager
					.getDefaultManager().getHandlers();
			for (int h = 0; h < attrHandlers.length; h++) {
				agg.attribute.handler.AttrHandler attrh = attrHandlers[h];
				java.util.Vector<String> packs = ((agg.attribute.handler.impl.javaExpr.JexHandler) attrh)
						.getClassResolver().getPackages();
				
				for (int pi = 0; pi < packs.size(); pi++) {
					String pack = packs.get(pi);
					
					String test = name;
					if (!test.startsWith(pack))
						test = pack + "." + name;
					// check if class exists
					try {
						Class.forName(test);
						isClass = true;
						if (cache == null) {
							((AttrTupleManager)AttrTupleManager.getDefaultManager()).classNameLookupMap.put(test, Boolean.TRUE);
							((AttrTupleManager)AttrTupleManager.getDefaultManager()).classNameLookupMap.put(name, Boolean.TRUE);
						}
						break;
					} 
					catch (ClassNotFoundException ex) {}
					catch (NoClassDefFoundError ex1) {}
				}
				if (isClass)
					break;
			}
		}
		return isClass;
	}

	// AttrType interface implementation

	/** Querying if an entry is declared. */
	public boolean containsName(String name) {
		return getIndexForName(name) > -1;
	}

	public boolean containsMember(DeclMember m) {
		if (m.getHoldingTuple() == this)
			return true;
		
		return false;
	}

	public boolean isLeafMember(String name) {
		if (getLeafMemberAt(getIndexForName(name)) != null)
			return true;
		
		return false;
	}

	/** @return The ancestor that originated the member at 'index'. */
	public DeclTuple getParentInCharge(int index) {
		int s = 0;
		for (int i = 0; i < this.parents.size(); i++) {
			s = s + this.parents.get(i).rawGetSize();
			if (index < s)
				return this.parents.get(i);
		}
		return this;

		/*// old code
		 * TupleObject res = null; if( (index >= getParentsSize())) res = this;
		 * int pos = index; int prev = 0; for(int i=0; i<parents.size(); i++){
		 * TupleObject p = parents.get(i); // System.out.println("pos: "+pos);
		 * if(pos < ((DeclTuple)p).getSize()){ res = p.getParentInCharge(pos);
		 * break; } else { pos = pos-((DeclTuple)p).getSize(); } } return res;
		 */
		
	}

	public int getRelativeIndex(DeclTuple parent,int index){
		int s = index;
		for (int i = 0; i < this.parents.size(); i++) {
			if (s - this.parents.get(i).rawGetSize() < 0)
				return parent.toRootIndex(s);
			s -= this.parents.get(i).rawGetSize();
		}			
		return parent.toRootIndex(s);
	}
	
	public AttrMember getMemberAt(int index) {
		if (index == -1)
			return null;
		DeclTuple parent = getParentInCharge(index);
		return parent.getLeafMemberAt(getRelativeIndex(parent,index));
	}

	
	
	public AttrMember getMemberAt(String name) {
		return getMemberAt(getIndexForName(name));
	}

	public AttrTypeMember addMember() {
		synchronized (this) {
			DeclMember member = newMember();
			addMember(member);
			fireAttrChanged(AttrEvent.MEMBER_ADDED, getSize() - 1);
			return member;
		}
	}

	public AttrTypeMember addMember(AttrHandler handler, String type,
			String name) {
		synchronized (this) {
			if (handler == null || type == null)
				return null;

			DeclMember member = newMember();
			addMember(member);
			int newIndex = getSize() - 1;
			member.setName(name);
			member.retype(handler, type);
			fireAttrChanged(AttrEvent.MEMBER_ADDED, newIndex);
			return member;
		}
	}

	public AttrTypeMember addMember(int index, AttrHandler handler,
			String type, String name) {
		synchronized (this) {
			if (handler == null || type == null)
				return null;

			DeclMember member = newMember();
			addMember(index, member);
			member.setName(name);
			member.retype(handler, type);
			fireAttrChanged(AttrEvent.MEMBER_ADDED, index);
			return member;
		}
	}

	public DeclTuple getHoldingTupleOfMember(int index) {
		DeclMember mem = (DeclMember) rawGetMemberAt(index);
		DeclTuple holdTuple = (DeclTuple) mem.getHoldingTuple();
		return holdTuple;
	}
	
	public void deleteMemberAt(int index){
		getParentInCharge(index).deleteLeafMemberAt(index);
	}

	
	public boolean isOwnMemberAt(AttrViewSetting viewSetting, int slot) {
		int indx = ensureNonNull(viewSetting).convertSlotToIndex(this, slot);		
		if (getParentInCharge(indx) == this) {
			return true;
		} 
		return false;
	}
	
	public void deleteMemberAt(AttrViewSetting viewSetting, int slot) {
		deleteMemberAt(ensureNonNull(viewSetting)
					.convertSlotToIndex(this, slot));
	}

	public void deleteMemberAt(String name){
//		deleteMemberAt(getIndexForName(name));
		
		int index = getIndexForName(name);
		if (index >= 0)
			deleteMemberAt(index);		
	}
	
	public void XwriteObject(XMLHelper h) {
		int num = getNumberOfEntries();
		for (int i = 0; i < num; i++) {
			DeclMember mem = (DeclMember) getMemberAt(i);
			if (mem.getTypeName() != null && mem.getName() != null
					&& mem.getTypeName().length() != 0 && mem.getName().length() != 0) {
				h.addObject("", mem, true);
			}
		}
	}

	public void XreadObject(XMLHelper h) {
		String handlerName = agg.attribute.handler.impl.javaExpr.JexHandler
				.getLabelName();
		AttrHandler handler = getAttrManager().getHandler(handlerName);
		Enumeration<?> en = h.getEnumeration("", null, true, "AttrType");
		while (en.hasMoreElements()) {
			h.peekElement(en.nextElement());
			String t = h.readAttr("typename");
			String n = h.readAttr("attrname");
			String vis = h.readAttr("visible");
			if (t.length() != 0 && n.length() != 0) {
				AttrTypeMember mem = addMember(handler, t, n);
				if (vis.equals("true") || vis.equals(""))
					((DeclMember) mem).setVisible(true);
				else
					((DeclMember) mem).setVisible(false);
				
//				if (mem.getType() == null || mem.getTypeName().length() == 0) {
//					System.out
//							.println("DeclTuple.XreadObject: WARNING!"
//									+ "\nThe type of an attribute member is empty."
//									+ "\nPlease check attribute declarations"
//									+ "\nor check in AGG GUI the part of the attribute editor"
//									+ "\n<Customize/ Handler / Searched Packages>  "
//									+ "\nand append the missed package.");
//				}
//				if (mem.getName() == null || mem.getName().length() == 0) {
//					System.out.println("DeclTuple.XreadObject: WARNING!"
//							+ "\nThe name of an attribute member is empty."
//							+ "\nPlease check attribute declarations.");
//				}
				
				h.loadObject(mem);
			}
//			else if (t.length() == 0 && n.length() == 0) {
//				System.out.println("DeclTuple.XreadObject: WARNING!"
//						+ "\nAn attribute member could not be created, "
//						+ "\nbecause type and name of the member are empty.");
//			}
			
			h.close();
		}
	}

	/**
	 * Translation between number- and name-oriented access.
	 * 
	 * @return The corresponding index if the name is declared within the tuple,
	 *         -1 otherwise.
	 */
	public int getIndexForName(String name) {
		int size = getSize();
		String n;

		for (int i = 0; i < size; i++) {
			// n = getDeclMemberAt(i).getName();
			DeclMember dm = getDeclMemberAt(i);
			if (dm != null) {
				n = dm.getName();
				if (n != null && n.equals(name))
					return i;
			}
		}
		return -1;
	}

	/** Check if 'handler' says that it can make a type out of 'typeName'. */
	public int isLegalType(AttrHandler handler, String typeName) {
		int msgCode = OK;
		try {
			if (handler.newHandlerType(typeName) == null) {
				msgCode = NO_SUCH_TYPE;
			}
		} catch (AttrHandlerException ex) {
			msgCode = NO_SUCH_TYPE;
		}
		return msgCode;
	}

	/** 
	 * Check if 'text'is already defined as attribute name. 
	 * Returns an attribute status code as result of the attribute name check. 
	 * It is failed for the status code > 0.
	 * @see AttrMsgCode interface
	 */
	public int isLegalName(String text) {
		int msgCode = OK;
		if (containsName(text)) {
			msgCode = NAME_DUPLICATION;
		}
		return msgCode;
	}

	public boolean isDefined() {
		for (int i = 0; i < getSize(); i++) {
			if (getMemberAt(i) == null || !((DeclMember)getMemberAt(i)).isDefined() )
				return false;
		}
		return true;
	}
	
	/**
	 * Renaming a declaration.
	 * 
	 * @deprecated
	 * @see DeclMember
	 */
	public void renameMemberAt(int index, String name) {
		AttrTypeMember member = getDeclMemberAt(index);
		member.setName(name); // Member fires change event.
	}

	/**
	 * Changing the type of a declaration.
	 * 
	 * @deprecated
	 * @see DeclMember
	 */
	public void retypeMemberAt(int index, AttrHandler handler, String type) {
		AttrTypeMember member = getDeclMemberAt(index);
		member.setHandler(handler); // Member fires change event.
		member.setType(type); // Member fires change event.
	}

	/**
	 * Removing a declaration at 'index'.
	 * 
	 * @deprecated
	 * @see #deleteMemberAt
	 */
	public void removeEntryAt(int index) {
		deleteMemberAt(index);
	}

	// Begin of AttrType interface implementation.

	/***************************************************************************
	 * Getting the total number of attribute entries (lines); The retrieval
	 * index range is [0 .. (getNumberOfEntries() - 1)].
	 */
	public int getNumberOfEntries() {
		return getSize();
	}

	// Getting a simple representation of a type as String.
	public String getTypeAsString(int entryIndex) {
		// HandlerType type = getDeclMemberAt( entryIndex ).getType();
		HandlerType type = null;
		if (getDeclMemberAt(entryIndex) != null)
			type = getDeclMemberAt(entryIndex).getType();
		if (type == null)
			return "";
		return type.toString();
	}

	// Getting a simple representation of a name as String.
	public String getNameAsString(int entryIndex) {
		if (getDeclMemberAt(entryIndex) != null)
			return getDeclMemberAt(entryIndex).getName();
		
		return "";
	}

	// Getting a simple representation of a value as String.
	public String getValueAsString(int entryIndex) {
		return "";
	}

	// End of AttrType interface implementation.

	/** Overrides ChainedObserver: views must be notified before others. */
	public void addObserver(AttrObserver attrObs) {
		if (attrObs instanceof AttrViewSetting) {
			super.addObserverAtPos(attrObs, 0);
		} else {
			super.addObserver(attrObs);
		}
	}

	// Update methods.

	public void updateMemberAdded(TupleEvent e) {
		AttrTuple tuple = e.getSource();
		// //if( tuple != null && tuple == parent ){
		if (tuple != null && this.parents.contains(tuple)) {
			propagateEvent(e);
		}
	}

	public void updateMemberDeleted(TupleEvent e) {
		AttrTuple tuple = e.getSource();
		// //if( tuple != null && tuple == parent ){
		if (tuple != null && this.parents.contains(tuple)) {
			propagateEvent(e);
		}
	}

	public String toString() {
		String result = "\n-----------------------------------------------------\n";
		result += getClass().getName() + "@" + hashCode() + "\n";
		int size = getSize();
		if (0 < size)
			result += "Tuple enthaelt " + size + " Member";
		else
			result += "Tuple ist leer";
		result += "\n";
		for (int i = 0; i < size; i++)
			result += getMemberAt(i) + "\n";
		int resulti = 0;
		for (int i = 0; i < getSize(); i++)
			resulti += getMemberAt(i).hashCode();
		result += resulti + "\n";
		result += "-----------------------------------------------------";
		return result;
	}

	
	/**
	 * @deprecated
	 * @see #addMember
	 */
	public void addEntry(AttrHandler handler, String type, String name) {
		addMember(handler, type, name);
	}

	/**
	 * @deprecated
	 * @see #deleteMemberAt
	 */
	public void deleteEntry(String name) {
		deleteMemberAt(name);
	}

	/**
	 * Compares an attribute type with the current one
	 * 
	 * @param tuple
	 *            attribute type to be compared with.
	 */
	public boolean compareTo(AttrType tuple) {
		int size = getNumberOfEntries();
		int n = 0;
		boolean isEqual = this.getClass().equals(tuple.getClass());
		while (n < size && isEqual) {
			DeclMember mem = (DeclMember) getMemberAt(n);
			if (mem != null)
				isEqual = mem.compareTo((DeclMember) ((DeclTuple) tuple)
						.getMemberAt(mem.getName()));
			n++;
		}
		return isEqual;
	}

	/**
	 * Compares an attribute type with the current one
	 * 
	 * @param tuple
	 *            attribute type to be compared with.
	 */
	public boolean weakcompareTo(AttrType tuple) {
		int size = getNumberOfEntries();
		int n = 0;
		boolean isEqual = this.getClass().equals(tuple.getClass());
		while (n < size && isEqual) {
			DeclMember mem = (DeclMember) getMemberAt(n);
			if (mem != null)
				isEqual = mem.weakcompareTo((DeclMember) ((DeclTuple) tuple)
						.getMemberAt(mem.getName()));
			n++;
		}
		return isEqual;
	}
	
	/*
	 * Selecting a declaration by 'index'.
	 * 
	 * @deprecated
	 * @see #getMemberAt( int )
	 */
//	private DeclMember getEntryAt(int index) {
//		return (DeclMember) getMemberAt(index);
//	}

	/*
	 * Getting a declaration by the name, not the number.
	 * 
	 * @deprecated
	 * @see #getMemberAt( String )
	 */
//	private DeclMember getEntryAt(String name) {
//		return (DeclMember) getMemberAt(name);
//	}

	public void setParent(DeclTuple newParent) {
		// assignParent (newParent);
		addParent(newParent);

		Enumeration<AttrObserver> en = getObservers();
		while (en.hasMoreElements()) {
			AttrObserver obs = en.nextElement();
			if (obs instanceof ValueTuple) {
				ValueTuple currentValueTuple = (ValueTuple) obs;
				currentValueTuple.refreshParents();
				// fireAttrChanged( AttrEvent.GENERAL_CHANGE, 0);
			} else if (obs instanceof OpenViewSetting) {
				OpenViewSetting myOpenView = (OpenViewSetting) obs;
				myOpenView.resetTuple(this);
				// fireAttrChanged( AttrViewEvent.GENERAL_CHANGE, 0);
			}
		}
	}

	// multiple inheritance - olga
	public boolean addParent(DeclTuple p) {
		if (p != null
				&& !this.parents.contains(p)) {
			p.addObserver(this);
			this.parent = p;

			for (int i = 0; i < p.getParents().size(); i++) {
				DeclTuple pi = p.getParents().get(i);
				if (!this.parents.contains(pi)) {
					this.parents.add(pi);
					pi.addObserver(this);
				}
			}
			this.parents.add(p);
			this.directParents.add(p);

			refreshObservers();
			return true;
		}
		return false;
	}

	// multiple inheritance - olga
	/**
	 * Removes the specified parent from my parents.
	 */
	public void removeParent(DeclTuple p) {
		if (p != null) {
			if (this.parents.contains(p)) {
				if (this.directParents.contains(p))
					this.directParents.remove(p);

				boolean canremove = true;
				for (int i = 0; i < this.directParents.size(); i++) {
					DeclTuple pi = this.directParents.get(i);
					if (pi.getParents().contains(p)) {
						canremove = false;
						break;
					}
				}
				if (canremove) {
					p.removeObserver(this);
					this.parents.remove(p);
				}

				for (int i = 0; i < p.getParents().size(); i++) {
					DeclTuple pi = p.getParents().get(i);
					if (this.parents.contains(pi)) {
						// System.out.println("patrent "+pi.hashCode()+" found,
						// try to remove");
						canremove = true;
						for (int j = 0; j < this.directParents.size(); j++) {
							DeclTuple itspj = this.directParents.get(j);
							if (itspj.getParents().contains(pi)) {
								canremove = false;
								break;
							}
						}
						if (canremove) {
							pi.removeObserver(this);
							this.parents.remove(pi);
						}
					}
				}
				if (this.parent == p) {
					if (!this.directParents.isEmpty())
						this.parent = this.directParents.lastElement();
					else
						this.parent = null;
				}
			}
		}
		refreshObservers();
	}

	public void refreshParents() {
		refreshParentsAfterRemove();
	}

	public void refreshParentsAfterAdd() {
		for (int i = 0; i < this.directParents.size(); i++) {
			DeclTuple pi = this.directParents.get(i);
			for (int j = 0; j < pi.getParents().size(); j++) {
				DeclTuple pj = pi.getParents().get(j);
				int indx = this.parents.indexOf(pi);
				if (!this.parents.contains(pj)) {
					this.parents.add(indx, pj);
					pj.addObserver(this);
				} else {
					int indx1 = this.parents.indexOf(pj);
					if (indx1 > indx) {
						this.parents.remove(indx1);
						this.parents.add(indx, pj);
					}
				}
			}
		}
		refreshObservers();
	}

	public void refreshParentsAfterRemove() {
		removeParents();
		for (int i = 0; i < this.directParents.size(); i++) {
			DeclTuple pi = this.directParents.get(i);
			this.parents.add(pi);
			pi.addObserver(this);
		}
		for (int i = 0; i < this.directParents.size(); i++) {
			DeclTuple pi = this.directParents.get(i);
			for (int j = 0; j < pi.getParents().size(); j++) {
				DeclTuple pj = pi.getParents().get(j);
				if (!this.parents.contains(pj)) {
					int indx = this.parents.indexOf(pi);
					this.parents.add(indx, pj);
					pj.addObserver(this);
				}
			}
		}
		refreshObservers();
	}

	public void refreshObservers() {
		Enumeration<AttrObserver> en = getObservers();
		while (en.hasMoreElements()) {
			AttrObserver obs = en.nextElement();
			if (obs instanceof ValueTuple) {
				ValueTuple currentValueTuple = (ValueTuple) obs;
				currentValueTuple.refreshParents();
				fireAttrChanged(AttrEvent.GENERAL_CHANGE, 0);
			} else if (obs instanceof OpenViewSetting) {
				OpenViewSetting myOpenView = (OpenViewSetting) obs;
				myOpenView.resetTuple(this);
				fireAttrChanged(AttrEvent.GENERAL_CHANGE, 0);
			}
		}
	}

}

/*
 * $Log: DeclTuple.java,v $
 * Revision 1.30  2010/11/28 22:11:36  olga
 * new method
 *
 * Revision 1.29  2010/10/18 08:52:08  olga
 * Save-Load improved: only attributes with defined type and name accepted
 *
 * Revision 1.28  2010/08/25 00:32:17  olga
 * tuning
 *
 * Revision 1.27  2010/03/21 21:22:54  olga
 * tuning
 *
 * Revision 1.26  2010/03/19 14:45:22  olga
 * tuning
 *
 * Revision 1.25  2010/03/18 18:15:31  olga
 * isClassName - improved
 *
 * Revision 1.24  2010/03/17 21:37:37  olga
 * tuning
 *
 * Revision 1.23  2010/03/08 15:37:22  olga
 * code optimizing
 *
 * Revision 1.22  2010/01/31 16:42:03  olga
 * new method addMember(int, String)
 *
 * Revision 1.21  2009/11/23 08:52:42  olga
 * tuning
 *
 * Revision 1.20  2009/04/20 08:50:45  olga
 * CPA: bug fixed
 *
 * Revision 1.19  2008/10/15 07:51:22  olga
 * Delete attr. member of parent type : error message dialog to warn the user
 *
 * Revision 1.18  2008/09/22 13:12:14  olga
 * new AttrEvent: MEMBER_TO_DELETE
 *
 * Revision 1.17  2008/04/07 10:38:35  olga
 * bug fixed
 *
 * Revision 1.16  2008/04/07 09:36:50  olga
 * Code tuning: refactoring + profiling
 * Extension: CPA - two new options added
 *
 * Revision 1.15  2007/12/17 08:33:29  olga
 * Editing inheritance relations - bug fixed;
 * CPA: dependency of rules - bug fixed
 *
 * Revision 1.14  2007/11/19 08:48:39  olga
 * Some GUI usability mistakes fixed.
 * Default values in node/edge of a type graph implemented.
 * Code tuning.
 *
 * Revision 1.13  2007/11/05 09:18:17  olga
 * code tuning
 *
 * Revision 1.12  2007/11/01 09:58:13  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.11  2007/09/10 13:05:17  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.10 2007/04/19 14:50:04 olga Loading
 * grammar - tuning
 * 
 * Revision 1.9 2007/03/28 10:00:28 olga - extensive changes of Node/Edge Type
 * Editor, - first Undo implementation for graphs and Node/edge Type editing and
 * transformation, - new / reimplemented options for layered transformation, for
 * graph layouter - enable / disable for NACs, attr conditions, formula - GUI
 * tuning
 * 
 * Revision 1.8 2006/12/13 13:32:58 enrico reimplemented code
 * 
 * Revision 1.7 2006/08/16 11:41:16 olga edit mode tuning graph layout by node
 * type pattern FreezingAge extended
 * 
 * Revision 1.6 2006/08/14 16:02:17 olga Edit mode handling
 * 
 * Revision 1.5 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.4 2006/04/12 14:54:07 olga Restore attr. values of attr. type
 * observers after type graph imported.
 * 
 * Revision 1.3 2006/04/06 09:28:53 olga Tuning of Import Type Graph and Import
 * Graph
 * 
 * Revision 1.2 2006/04/03 08:57:50 olga New: Import Type Graph and some bugs
 * fixed
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.3 2005/07/11 09:30:20 olga This is test version AGG V1.2.8alfa .
 * What is new: - saving rule option <disabled> - setting trigger rule for layer -
 * display attr. conditions in gragra tree view - CPA algorithm <dependencies> -
 * creating and display CPA graph with conflicts and/or dependencies based on
 * (.cpx) file
 * 
 * Revision 1.2.2.1 2005/06/20 20:55:03 enrico ported changes from latest
 * inheritance version
 * 
 * Revision 1.2 2005/06/20 13:37:03 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.8 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.7 2004/04/28 12:46:38 olga test CSP
 * 
 * Revision 1.6 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.5 2003/07/17 17:20:16 olga Primitive Datentypen
 * 
 * Revision 1.4 2003/03/05 18:24:22 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/10/04 16:36:38 olga Es gibt noch Fehler unter Window
 * 
 * Revision 1.2 2002/09/23 12:23:57 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.13 2001/03/08 10:38:40 olga Testausgaben raus.
 * 
 * Revision 1.12 2000/12/07 14:23:35 matzmich XML-Kram Man beachte: xerces
 * (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird jetzt im CLASSPATH
 * benoetigt.
 * 
 * Revision 1.11 2000/04/05 12:09:12 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.10 1999/10/13 10:41:34 olga AttrType werden durch <compareTo>
 * verglichen.
 * 
 * Revision 1.9 1999/10/11 10:42:31 shultzke kleine Bugfixes
 * 
 * Revision 1.8 1999/10/11 09:35:41 olga *** empty log message ***
 * 
 * Revision 1.7 1999/10/05 08:33:29 shultzke SlotSequences werden zwar
 * geloescht, aber sofort wieder erzeugt
 * 
 * Revision 1.6 1999/09/27 16:16:39 olga dispose Methoden hinzugefuegt.
 * 
 * Revision 1.5 1999/09/06 13:39:01 shultzke ChainedObserver auf WeakReferences
 * umgestellt, samt serialUID
 */
