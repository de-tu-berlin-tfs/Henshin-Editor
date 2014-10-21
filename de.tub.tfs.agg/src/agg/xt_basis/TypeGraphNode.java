package agg.xt_basis;


/**
 * @version $Id: TypeGraphNode.java,v 1.15 2010/09/23 08:27:31 olga Exp $
 */
public class TypeGraphNode {
	/**
	 * true, if this node type is contained in the type graph
	 */
	private boolean typeGraphObjectDefined = false;

	/**
	 * its node object inside of a type graph
	 */
	private Node itsNode;
	
	private boolean visible = true;
	
	/**
	 * minimum number of nodes of this type
	 */
	private short sourceMin = Type.UNDEFINED;

	/**
	 * maximum number of nodes of this type
	 */
	private short sourceMax = Type.UNDEFINED;

	
	TypeGraphNode() {}

	
	public void dispose() {
		this.itsNode = null;
	}
	
	
	Node addTypeGraphObject(Node n) {
		this.itsNode = n;
		this.typeGraphObjectDefined = (n != null);		
		return this.itsNode;
	}

	boolean hasTypeGraphObject() {
		return this.typeGraphObjectDefined;
	}

	boolean removeTypeGraphObject() {
		this.typeGraphObjectDefined = false;
		this.itsNode = null;
		return true;
	}

	void forceRemoveTypeGraphObject() {
		this.typeGraphObjectDefined = false;
		this.itsNode = null;
	}

	public Node getNode()   {
		return this.itsNode;
	} 
	
	public void setVisible(boolean vis) {
		this.visible = vis;
	}
	
	public boolean isVisible() {
		return this.visible;		
	}
	
	public void setSourceMin(int value) {
		this.sourceMin = (short) value;
	}

	public void setSourceMax(int value) {
		this.sourceMax = (short) value;
	}

	public int getSourceMin() {
		return this.sourceMin;
	}

	public int getSourceMax() {
		return this.sourceMax;
	}


}
