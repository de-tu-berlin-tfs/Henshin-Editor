//################################################################################

package agg.xt_basis;

/**
 * This Exception is thrown by methods of the class
 * <code>OrdinaryMorphism</code>, especially by <code>addMapping()</code>,
 * to indicate a violation of some morphism property. A more detailed
 * description of the violation cause will be given in the exception's detail
 * message.
 * 
 * @see agg.xt_basis.OrdinaryMorphism
 * @see agg.xt_basis.OrdinaryMorphism#addMapping
 */
public class MappingEvent extends BadMappingException {
	GraphObject obj, orig, image;

	/**
	 * Construct myself with the specified detail message and mapped/ unmapped
	 * object.
	 */
	public MappingEvent(String n, GraphObject o) {
		super(n);
		this.obj = o;
	}

	/**
	 * Construct myself with the specified detail message and original and image
	 * object of the mapping.
	 */
	public MappingEvent(String n, GraphObject o, GraphObject i) {
		super(n);
		this.orig = o;
		this.image = i;
	}

	public GraphObject getGraphObject() {
		return this.obj;
	}

	public GraphObject getOriginalObject() {
		return this.orig;
	}

	public GraphObject getImageObject() {
		return this.obj;
	}

}
