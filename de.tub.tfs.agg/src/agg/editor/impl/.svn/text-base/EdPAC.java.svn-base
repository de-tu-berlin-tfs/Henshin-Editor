package agg.editor.impl;

import java.util.Vector;
import java.util.Enumeration;

import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.GraphObject;

/**
 * An EdPAC specifies the PAC (Positive Application Condition of a rule) layout
 * for the used object as an object of the class agg.xt_basis.OrdinaryMorphism.
 * The class EdPAC is a subclass of the class EdGraph. The used object is a
 * morphism between the left hand side of the rule and the PAC graph. The
 * morphism mappings are shown through the same number.
 * 
 * @author $Author: olga $
 * @version $Id: EdPAC.java,v 1.7 2010/09/30 14:11:40 olga Exp $
 */
public class EdPAC extends EdGraph {

	OrdinaryMorphism morphism; // PAC morphism

	String name;

	transient EdRule itsRule;

	/** Creates an empty PAC layout */
	public EdPAC() {
		super(); // base graph is null
		this.name = "Unnamed_PAC";
	}

	/** Creates an empty PAC layout with specified type set */
	public EdPAC(EdTypeSet types) {
		super(types); // base graph is null
		this.name = "Unnamed_PAC";
	}

	/** Creates a PAC layout of the morphism specified by the OrdinaryMorphism m */
	public EdPAC(OrdinaryMorphism m) {
		super(m.getImage()); // base graph is m.getImage()
		this.morphism = m;
		this.name = getBasisGraph().getName();
		this.morphism.addObserver(this);
		getBasisGraph().addObserver(this);
	}

	/**
	 * Creates a PAC layout of the morphism specified by the OrdinaryMorphism m
	 * and with specified type set
	 */
	public EdPAC(OrdinaryMorphism m, EdTypeSet types) {
		super(m.getImage(), types); // base graph is m.getImage()
		this.morphism = m;
		this.name = getBasisGraph().getName();
		this.morphism.addObserver(this);
		getBasisGraph().addObserver(this);
	}

	public void dispose() {
		this.morphism.deleteObserver(this);
		super.dispose();
		this.itsRule = null;
		this.morphism = null;
//		System.out.println("EdNAC.dispose:: DONE  "+this.hashCode());
	}

	/** Sets the name of the NAC */
	public void setName(String str) {
		this.name = str;
		if (getBasisGraph() != null)
			getBasisGraph().setName(str);
		if (this.morphism != null)
			this.morphism.setName(str);
	}

	/** Returns the name of the PAC */
	public String getName() {
		return this.name;
	}

	/** Returns the rule I belonging to. */
	public EdRule getRule() {
		return this.itsRule;
	}

	/** Sets the rule I belonging to to a rule specified by the EdRule er */
	public void setRule(EdRule er) {
		this.itsRule = er;
	}

	/** Returns my this.morphism */
	public OrdinaryMorphism getMorphism() {
		return this.morphism;
	}

	/**
	 * Returns my graph object that is the image to the specified graph object
	 * which is an object of the LHS of my rule.
	 */
	public EdGraphObject getImage(EdGraphObject orig) {
		GraphObject im = this.morphism.getImage(orig.getBasisObject());
		return ((EdGraph) this).findGraphObject(im);
	}

	/**
	 * Returns a vector with graph objects of the LHS of my rule. The specified
	 * graph object belongs to this EdPAC graph and is the image object of the
	 * graph objects to return.
	 */
	public Vector<EdGraphObject> getOriginal(EdGraphObject image) {
		Vector<EdGraphObject> vec = new Vector<EdGraphObject>(2);
		Enumeration<GraphObject> en = this.morphism.getInverseImage(image.getBasisObject());
		while (en.hasMoreElements()) {
			GraphObject obj = en.nextElement();
			EdGraphObject go = this.itsRule.getLeft().findGraphObject(obj);
			if (go != null)
				vec.add(go);
		}
		return vec;
	}
}
