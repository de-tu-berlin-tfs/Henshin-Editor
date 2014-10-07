/**
 * 
 */
package agg.layout.evolutionary;

import java.awt.Point;

import agg.editor.impl.EdArc;
import agg.editor.impl.EdNode;
import agg.util.XMLHelper;
import agg.util.XMLObject;

/**
 * This class contains information about an edge (connection), such as actual
 * and preferred length, atractive force and some more, that are used by
 * Springembedder Layout.
 */
public class LayoutArc implements XMLObject {

	private EdArc eArc;

	private int prefLngth;

	private int aktLngth;

	private int xlngth, ylngth;

	private int force;

	private Point srcpos, tarpos;

	private int used;

	private boolean frozen;

	private boolean frozenAsDefault;

	/**
	 * Initialize the layout information of the specified EdArc by default
	 * values.
	 */
	public LayoutArc(EdArc e) {
		this.eArc = e;
		this.prefLngth = 200; // 150;
		this.aktLngth = 200; // 150;
		this.force = 10;
		this.used = 0;
	}

	/**
	 * Initialize the layout information of the specified EdArc by specified
	 * parameters.
	 * 
	 * @param e
	 *            edge which will get this layout
	 * @param prefL
	 *            preferred length
	 * @param aktL
	 *            actual length
	 * @param f
	 *            atractive force
	 */
	public LayoutArc(EdArc e, int prefL, int aktL, int f) {
		this.eArc = e;
		this.prefLngth = prefL;
		this.aktLngth = aktL;
		this.force = f;
	}

	public void dispose() {
		this.eArc = null;
	}
	
	public EdArc getEdArc() {
		return this.eArc;
	}

	public void setPrefLength(int p) {
		this.prefLngth = p;
	}

	public void setAktLength(int a) {
		this.aktLngth = a;
	}

	public void setForce(int f) {
		this.force = f;
	}

	public int getPrefLength() {
		return this.prefLngth;
	}

	public int getAktLength() {
		return this.aktLngth;
	}

	public int getForce() {
		return this.force;
	}

	public void setSourcepos(Point p) {
		this.srcpos = new Point(p);
	}

	public void setTargetpos(Point p) {
		this.tarpos = new Point(p);
	}

	public Point getSourcepos() {
		return this.srcpos;
	}

	public Point getTargetpos() {
		return this.tarpos;
	}

	public int getXLength() {
		return this.xlngth;
	}

	public int getYLength() {
		return this.ylngth;
	}

	/*
	 * The source position is the middle point of the source node, the target
	 * position is the middle point of the target node of its edge.
	 */
	public void calcSourceTargetpos() {
		LayoutNode snode = ((EdNode) this.getEdArc().getSource()).getLNode();
		LayoutNode tnode = ((EdNode) this.getEdArc().getTarget()).getLNode();
		this.setSourcepos(new Point(snode.getAkt().x
				+ (snode.getEdNode().getWidth() / 2), snode.getAkt().y
				+ (snode.getEdNode().getHeight() / 2)));
		this.setTargetpos(new Point(tnode.getAkt().x
				+ (tnode.getEdNode().getWidth() / 2), tnode.getAkt().y
				+ (tnode.getEdNode().getHeight() / 2)));
	}

	/*
	 * Calculate the actual length of its edge that starts at source position
	 * and finishes at target position.
	 */
	public void calcAktLength() {
		this.calcSourceTargetpos();
		this.xlngth = Math.abs(this.srcpos.x - this.tarpos.x);
		this.ylngth = Math.abs(this.srcpos.y - this.tarpos.y);
		this.aktLngth = (int) Math.sqrt((this.xlngth * this.xlngth) + (this.ylngth * this.ylngth));
	}

	public int getUsed() {
		return this.used;
	}

	public void incUsed() {
		this.used++;
	}

	public void resetUsed() {
		this.used = 0;
	}

	public void setFrozen(boolean b) {
		this.frozen = b;
	}

	public boolean isFrozen() {
		return this.frozen;
	}

	public void setFrozenByDefault(boolean b) {
		this.frozenAsDefault = b;
	}

	public boolean isFrozenByDefault() {
		return this.frozenAsDefault;
	}

	public void XwriteObject(XMLHelper h) {
		if (h.openObject(this.eArc.getBasisArc(), this)) {
			h.openSubTag("additionalLayout");
			h.addAttr("preflength", this.prefLngth);
			h.addAttr("aktlength", this.aktLngth);
			h.addAttr("force", this.force);
			h.close();
			h.close();
		}

	}

	public void XreadObject(XMLHelper h) {
		String s;
		h.peekObject(this.eArc.getBasisArc(), this);
		if (h.readSubTag("additionalLayout")) {
			s = h.readAttr("preflength");
			if (s.length() == 0) {
				this.prefLngth = 200;
			} else {
				this.prefLngth = Integer.parseInt(s);
			}
			s = h.readAttr("aktlength");
			if (s.length() == 0) {
				this.aktLngth = 200;
			} else {
				this.aktLngth = Integer.parseInt(s);
			}
			s = h.readAttr("force");
			if (s.length() == 0) {
				this.force = 10;
			} else {
				this.force = Integer.parseInt(s);
			}
			h.close();
		}
		h.close();
	}
}
