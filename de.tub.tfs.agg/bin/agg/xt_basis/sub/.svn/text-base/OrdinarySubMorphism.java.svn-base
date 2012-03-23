package agg.xt_basis.sub;

import java.util.Enumeration;
import java.util.Vector;

import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;

/**
 * An implementation of a submorphism. Original and image graphs of the
 * submorphism are allowed to be arbitrary subgraphs of the respective graphs of
 * the supermorphism. The definition of a submorphism demands that the
 * submorphism's embedding diagram is strongly commutative, i.e. whenever an
 * object x is mapped to an object y by the supermorphism and x is element of
 * the submorphism's original graph, it is mapped to y by the submorphism as
 * well. The implementation automatically maintains consistency to this
 * definition.
 * 
 * @deprecated not more supported
 */
public class OrdinarySubMorphism extends OrdinaryMorphism {
	private OrdinaryMorphism itsSuperMorph;

	private SubGraph itsSubOrig, itsSubImg;

//	private Vector<GraphObject> itsDomObjects;
//
//	private Vector<GraphObject> itsCodomObjects;

	// public static final long serialVersionUID = -7412165151535980690L;

	protected OrdinarySubMorphism(OrdinaryMorphism supermorph) {
		this(supermorph, null, null);
	}

	public OrdinarySubMorphism(OrdinaryMorphism supermorph, SubGraph orig,
			SubGraph img) {
		super(orig, img, supermorph.getAttrContext());
		this.itsSubOrig = orig;
		this.itsSubImg = img;
//		this.itsDomObjects = new Vector<GraphObject>();
//		this.itsCodomObjects = new Vector<GraphObject>();
		this.itsSuperMorph = supermorph;
		// itsSuperMorph.addObserver(this);
		// Make me commute for the given original and image graphs:
		GraphObject anObj;
		if (orig != null) {
			Enumeration<GraphObject> allOrigObjects = orig.getElements();
			while (allOrigObjects.hasMoreElements()) {
				anObj = allOrigObjects.nextElement();
				makeCommute(anObj);
			}
		}
	}

	/**
	 * <p>
	 * <b>Pre:</b> <code>getOriginal.isElement(obj)</code>.
	 */
	private final void makeCommute(GraphObject obj) {
		GraphObject anImg = this.itsSuperMorph.getImage(obj);
		if (anImg != null) {
			if (!this.itsSubImg.isElement(anImg)) {
				this.itsSubImg.addObject(anImg);
			}

			addMapping(obj, anImg);
		}
	}

	public final OrdinaryMorphism getSuperMorphism() {
		return this.itsSuperMorph;
	}

	/**
	 * Return the ALR graph which is the source graph of the morphism.
	 * <p>
	 * <b>Post:</b> <code>r.isGraph()</code>, where <code>r</code> is the
	 * return value.
	 */
	public Graph getOriginal() {
		return this.itsSubOrig;
	}

	/**
	 * Return the ALR graph which is the target graph of the morphism.
	 * <p>
	 * <b>Post:</b> <code>r.isGraph()</code>, where <code>r</code> is the
	 * return value.
	 */
	public Graph getImage() {
		return this.itsSubImg;
	}

	public Enumeration<GraphObject> getDomain()
	/***************************************************************************
	 * Return an Enumeration of the graphobjects out of my source * graph which
	 * are actually taking part in one of my mappings. * Enumeration elements
	 * are of type <code>GraphObject</code>.
	 * 
	 * @see agg.xt_basis.GraphObject.*
	 **************************************************************************/
	{
		// System.out.println("OrdinarySubMorphism.getDomain has elements: "
		// +itsDomObjects.elements().hasMoreElements());
		return this.itsDomObjects.elements();
	}

	public Enumeration<GraphObject> getCodomain()
	/***************************************************************************
	 * Return an Enumeration of the graph objects out of my target graph * which
	 * are actually taking part in one of my mappings. * Enumeration elements
	 * are of type <code>GraphObject</code>.
	 * 
	 * @see agg.xt_basis.GraphObject *
	 **************************************************************************/
	{
		return this.itsCodomObjects.elements();
	}

	public GraphObject getImage(GraphObject o)
	/***************************************************************************
	 * Return the image of the specified object.
	 * 
	 * @return <code>null</code> if the object is not in domain.*
	 **************************************************************************/
	{
		// System.out.println("OrdinarySubMorphism.getImage(GraphObject o) ");
		GraphObject result = null;
		int i = this.itsDomObjects.indexOf(o);
		if (i > -1)
			result = this.itsCodomObjects.elementAt(i);
		// System.out.println("result: "+result);
		return result;
	}

	public Enumeration<GraphObject> getInverseImage(GraphObject o)
	/***************************************************************************
	 * Return an Enumeration of the inverse images of the specified object. *
	 * Enumeration will be empty when the object is not in codomain. *
	 * Enumeration elements are of type <code>GraphObject</code>.
	 * 
	 * @see agg.xt_basis.GraphObject.*
	 **************************************************************************/
	{
		Vector<GraphObject> invImages = new Vector<GraphObject>();
		int i = 0;
		int index;
		while ((i < this.itsCodomObjects.size())
				&& ((index = this.itsCodomObjects.indexOf(o, i)) != -1)) {
			invImages.addElement(this.itsDomObjects.elementAt(index));
			// System.out.println("InversImageObj:
			// "+itsDomObjects.elementAt(index));
			i = index + 1;
		}
		return invImages.elements();
	}

	public void addMapping(GraphObject o, GraphObject i)
			throws BadMappingException {
		// System.out.println("OrdinarySubMorphism.addMapping");
		if (this.itsSuperMorph.getImage(o) != i) {
			this.itsSuperMorph.addMapping(o, i);
		}
		this.itsDomObjects.addElement(o);
		this.itsCodomObjects.addElement(i);
		// System.out.println(itsDomObjects.size()+" "+itsCodomObjects.size());
	}

	public void removeMapping(GraphObject o) {
		this.itsSuperMorph.removeMapping(o);
		this.itsDomObjects.remove(o);
		this.itsCodomObjects.remove(getImage(o));

		// the mapping is automatically removed from the submorphism
		// during update(), because it is a subtree of the supermorphism
	}

}
