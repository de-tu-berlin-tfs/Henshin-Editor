package agg.layout.evolutionary;

import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.Type;

/**
 * This class contains information about a layout pattern of a type. This
 * implementation considers layout patterns of the edge types only. The data of
 * a layout pattern are: name, type of a pattern such as <code>edge</code>,
 * <code>node</code> or <code>edge_length</code>, edge type which is
 * effected by this layout pattern. Currently, only <code>edge</code> and
 * <code>edge_length</code> layout patterns are implemented. The
 * <code>edge</code> layout patterns is specified by offset type which can be
 * <code>x</code> or <code>y</code>, offset value of x-offset: if
 * xOffset==1 then target of edge is right of source, if xOffset==-1 then target
 * of edge is left of source, if xOffset==0 then target of edge is equal to
 * source, offset value of y-offset: if yOffset==1 then target of edge is under
 * source, if yOffset==-1 then target of edge is above source, if yOffset==0
 * then target of edge is equal to source, The <code>edge_length</code> layout
 * patterns is specified by length size.
 */
public class LayoutPattern implements XMLObject {

	String name;

	String patternType; // "edge" or "node"

	Type type;

	int edgeLength;

	char offsetType; // x|y

	int xOffset, yOffset;

	boolean freezing;

	/**
	 * Initialize layout pattern of the specified edge type. The dada of this
	 * layout pattern are undefined.
	 */
	public LayoutPattern(Type t) {
		this.type = t;
	}

	/**
	 * Initialize layout pattern of the specified edge type.
	 * 
	 * @param name
	 *            name of the layout pattern
	 * @param pType
	 *            type of the layout pattern
	 * @param t
	 *            edge type which uses this layout pattern
	 * @param offsetType
	 *            offset type
	 * @param offset
	 *            offset value
	 */
	public LayoutPattern(String name, String pType, Type t, char offsetType,
			int offset) {
		this.name = name;
		this.patternType = pType;
		this.type = t;
		this.offsetType = offsetType;
		if (offsetType == 'x') {
			this.xOffset = offset;
			this.yOffset = 0;
		} else {
			this.xOffset = 0;
			this.yOffset = offset;
		}
		this.edgeLength = 0;
	}

	/**
	 * Initialize layout pattern of the specified edge type.
	 * 
	 * @param name
	 *            name of the layout pattern
	 * @param pType
	 *            type of the layout pattern
	 * @param t
	 *            edge type which uses this layout pattern
	 * @param edgelength
	 *            preferred length of an edge
	 */
	public LayoutPattern(String name, String pType, Type t, int edgelength) {
		if (pType.equals("edge")) {
			this.name = name;
			this.patternType = pType;
			this.type = t;
			this.offsetType = 'l';
			this.edgeLength = edgelength;
		}
	}

	/**
	 * Initialize layout pattern of the specified node type.
	 * 
	 * @param name
	 *            name of the layout pattern
	 * @param pType
	 *            type of the layout pattern
	 * @param t
	 *            node type which uses this layout pattern
	 * @param freezing
	 *            (X,Y) position of node type instances stays static or not
	 */
	public LayoutPattern(String name, String pType, Type t, boolean freezing) {
		if (pType.equals("node")) {
			this.name = name;
			this.patternType = pType;
			this.type = t;
			this.freezing = freezing;
			this.offsetType = 'f';
		}
	}

	public void dispose() {
		this.type = null;
	}
	
	public String getName() {
		return this.name;
	}

	public boolean isEdgePattern() {
		boolean ret = false;
		if (this.patternType.compareTo("edge") == 0) {
			ret = true;
		}
		return ret;
	}

	public boolean isNodePattern() {
		boolean ret = false;
		if (this.patternType.equals("node")) {
			ret = true;
		}
		return ret;
	}

	public boolean isFrozenNodePattern() {
		if (this.name.equals("frozen_node")) {
			return true;
		}
		return false;
	}

	public boolean isLengthPattern() {
		boolean ret = false;
		if (this.offsetType == 'l') {
			ret = true;
		}
		return ret;
	}

	public int getLength() {
		return this.edgeLength;
	}

	public boolean isXOffset() {
		boolean ret = false;
		if (this.offsetType == 'x') {
			ret = true;
		}
		return ret;
	}

	public boolean isYOffset() {
		boolean ret = false;
		if (this.offsetType == 'y') {
			ret = true;
		}
		return ret;
	}

	public int getOffset() {
		int ret = 0;
		if (this.offsetType == 'x') {
			ret = this.xOffset;
		} else if (this.offsetType == 'y') {
			ret = this.yOffset;
		} else if (this.offsetType == 'l') {
			ret = this.edgeLength;
		}
		return ret;
	}

	public Type getEffectedType() {
		return this.type;
	}

	public boolean isSimilarTo(LayoutPattern lp) {
		if (this.name.equals(lp.getName())
				&& ((this.isEdgePattern() && lp.isEdgePattern()) || (this
						.isNodePattern() && lp.isNodePattern()))) {
			if (this.getEffectedType() != null && lp.getEffectedType() != null) {
				if (this.getEffectedType().compareTo(lp.getEffectedType())) {
					if (this.isLengthPattern() && lp.isLengthPattern())
						return true;
					else if (this.isXOffset() && lp.isXOffset())
						return true;
					else if (this.isYOffset() && lp.isYOffset())
						return true;
					else if (this.isFrozenNodePattern()
							&& lp.isFrozenNodePattern())
						return true;
				}
			}
		}
		return false;
	}

	public void XwriteObject(XMLHelper h) {
		h.openNewElem("LayoutPattern", this);
		h.addAttr("name", this.name); // pattern name
		h.addAttr("patterntype", this.patternType); // pattern type
		h.addAttr("kind", String.valueOf(this.offsetType)); // offsetType
		if (this.offsetType == 'x')
			h.addAttr("value", String.valueOf(this.xOffset));
		else if (this.offsetType == 'y')
			h.addAttr("value", String.valueOf(this.yOffset));
		else if (this.offsetType == 'l')
			h.addAttr("value", String.valueOf(this.edgeLength));
		else if (this.offsetType == 'f')
			h.addAttr("value", String.valueOf("true"));
		h.close();
	}

	public void XreadObject(XMLHelper h) {
		if (h.isTag("LayoutPattern", this)) {
			this.name = h.readAttr("name");
			this.patternType = h.readAttr("patterntype");
			this.offsetType = h.readAttr("kind").charAt(0);
			if (this.offsetType == 'x')
				this.xOffset = Integer.parseInt(h.readAttr("value"));
			else if (this.offsetType == 'y')
				this.yOffset = Integer.parseInt(h.readAttr("value"));
			else if (this.offsetType == 'l')
				this.edgeLength = Integer.parseInt(h.readAttr("value"));
			else if (this.offsetType == 'f')
				this.freezing = Boolean.parseBoolean(h.readAttr("value"));
			h.close();
			// System.out.println(this.name+" "+this.patternType+"
			// "+this.offsetType+" "+this.xOffset+" "+this.yOffset+"
			// "+this.edgeLength);
		}
	}
}
