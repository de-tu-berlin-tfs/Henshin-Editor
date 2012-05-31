// $Id: EdType.java,v 1.23 2010/09/20 14:28:38 olga Exp $

package agg.editor.impl;

import java.awt.Color;
import java.util.List;
import java.util.Vector;

import agg.gui.animation.AnimationParam;
import agg.gui.editor.EditorConstants;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.Type;

/** An EdType specifies the layout type of an agg.xt_basis.Type object */
public class EdType implements XMLObject //, StateEditable
{

	public String name = "";

	public int shape = EditorConstants.RECT;

	public Color color = Color.black;

	public boolean filled;
	
	public String imageFileName = "";

	public String resourcesPath = System.getProperty("user.dir");

	public final AnimationParam animationParameter = new AnimationParam(0, 0, 10, 0);
	
	protected boolean iconable;	
	
	protected boolean animated;
	
	protected Type bType;

	protected String itsContextUsage;

	private boolean typeKeyChanged;
	
	private boolean attrTypeChanged;
	
	/**
	 * Creates a layout type specified by : String name, int shape, Color color
	 * for the used object bType. The bType is an instance of the class
	 * agg.xt_basis.Type
	 */
	public EdType(String name, int shape, Color color, String iconFileName,
			Type bType) {
		this.name = name;
		this.shape = shape;
		this.color = color;
		this.imageFileName = iconFileName;
		this.bType = bType;
		if (bType != null) {
			this.bType.setStringRepr(name);
			setAdditionalReprOfBasisType(this.shape, this.color, this.filled,
					this.imageFileName);
		}
		this.itsContextUsage = "";
	}
	
	public EdType(String name, int shape, Color color, boolean filledShape, String iconFileName,
			Type bType) {
		this.name = name;
		this.shape = shape;
		this.color = color;
		this.filled = filledShape;
		this.imageFileName = iconFileName;
		this.bType = bType;
		if (bType != null) {
			this.bType.setStringRepr(name);
			this.setAdditionalReprOfBasisType(this.shape, this.color, this.filled,
					this.imageFileName);
		}

		this.itsContextUsage = "";
	}
	/**
	 * Creates default layout type : the name is empty, the shape is a
	 * rectangle, the color is black, the used object is NULL
	 */
	public EdType() {
		this("", EditorConstants.RECT, Color.black, "", null);
	}

	/**
	 * Creates a layout type specified by String name, the shape is a rectangle,
	 * the color is black, the used object is NULL
	 */
	public EdType(String name) {
		this(name, EditorConstants.RECT, Color.black, "", null);
	}

	/**
	 * Creates a layout type specified by String name, int shape, Color color,
	 * the used object is NULL
	 */
	public EdType(String name, int shape, Color color, String iconFileName) {
		this(name, shape, color, iconFileName, null);
	}

	public EdType(String name, int shape, Color color, boolean filledShape, String iconFileName) {
		this(name, shape, color, filledShape, iconFileName, null);
	}
	
	/**
	 * Creates a layout type specified by String name for the used object bType
	 * of the class agg.xt_basis.Type; the shape is a rectangle, the color is
	 * black
	 */
	public EdType(String name, Type bType) {
		this(name, EditorConstants.RECT, Color.black, "", bType);
	}

	/**
	 * Creates default layout type for the used object bType of the class
	 * agg.xt_basis.Type
	 */
	public EdType(Type bType) {
		this(bType, EditorConstants.RECT, Color.black, "");
	}

	/**
	 * Creates a layout type specified by int shape, Color color for the used
	 * object bType of the class agg.xt_basis.Type (the name is defined in the
	 * bType)
	 */
	public EdType(Type bType, int shape, Color color, String iconFileName) {
		this((bType != null) ? bType.getStringRepr() : "", shape, color,
				iconFileName, bType);
	}

	public EdType(Type bType, int shape, Color color, boolean filledShape, String iconFileName) {
		this((bType != null) ? bType.getStringRepr() : "", shape, color, filledShape,
				iconFileName, bType);
	}
	
	public void dispose() {
		this.bType = null;
	}
	
	public void finalize() {
	}
	
	// /**
	// * Implements the interface <EM>StateEditable</EM>.
	// * The type representation data <EM>TypeReprData</EM>
	// * is stored into <EM>state</EM>.
	// */
	// public void storeState(Hashtable<Object,Object> state){
	// TypeReprData data = new TypeReprData(this);
	// state.put(this, data);
	// }
	//	
	// /**
	// * Implements the interface <EM>StateEditable</EM>.
	// * The type representation data <EM>TypeReprData</EM>
	// * is extracted out of <EM>state</EM>
	// * and applyed to this type.
	// */
	// public void restoreState(Hashtable<?,?> state){
	// TypeReprData data = (TypeReprData) state.get(this);
	// state.remove(this);
	// data.restoreTypeFromTypeRepr(this);
	// }


	public void setAttrTypeChanged(boolean b) {
		this.attrTypeChanged = true;
	}
	
	public boolean hasAttrTypeChanged() {
		return this.attrTypeChanged;
	}
	
	
	/** Gets the name of the layout type */
	public String getName() {
		return this.name;
	}

	/** Gets the shape of the layout type */
	public int getShape() {
		return this.shape;
	}

	/**
	 * Returns true when the graphic of the node type is filled
	 * or the line of the edge type is bold,
	 * otherwise - false.
	 */
	public boolean hasFilledShape() {
		return this.filled;
	}
	
	/** Gets the color of the layout type */
	public Color getColor() {
		return this.color;
	}

	/** Gets the image filename of the layout type */
	public String getImageFileName() {
		return this.imageFileName;
	}

	/** Gets the used object of the layout type */
	public Type getBasisType() {
		return this.bType;
	}

	/** Gets the name of the used object of the layout type */
	public String getTypeName() {
		if (this.bType != null)
			return this.bType.getStringRepr();
		
		return this.name;
	}

	public boolean isNodeType() {
		if (this.bType.getAdditionalRepr().indexOf(":[NODE]:") >= 0)
			return true;
		
		return false;
	}

	public boolean isArcType() {
		if (this.bType.getAdditionalRepr().indexOf(":[EDGE]:") >= 0)
			return true;
		
		return false;
	}


	/** Sets the name of the layout type and of the used object */
	public void setName(String nm) {
		this.name = nm;
		if (this.bType != null)
			this.bType.setStringRepr(this.name);
	}

	/** Sets the shape of the layout type */
	public void setShape(int sh) {
		this.shape = sh;
	}

	/**
	 * If the specified parameter is TRUE, the shape of this type will be filled with its color,
	 * otherwise only its border is colored.
	 */
	public void setFilledShape(boolean b) {
		this.filled = b;
	}
	
	/** Sets the color of the layout type */
	public void setColor(Color col) {
		this.color = col;
	}
	
	/** Sets the image filename of the layout type */
	public void setImageFileName(String fname) {
		this.imageFileName = fname;
	}

	/** Sets the used object of the layout type */
	public void setBasisType(Type btype) {
		this.bType = btype;
	}

	/**
	 * Sets additional graphical representation of my base type. 
	 * The features of additional representation are: 
	 * shape, color, image filename.
	 */
	public void setAdditionalReprOfBasisType() {
		setAdditionalReprOfBasisType(this.shape, this.color, this.filled, this.imageFileName);
	}

	/**
	 * Sets additional graphical representation of my base type.
	 * The features of additional representation are: 
	 * shape, color, filled shape, image filename.
	 * 
	 */
	public void setAdditionalReprOfBasisType(int sh, Color col) {
		this.setAdditionalReprOfBasisType(sh, col, this.filled, this.imageFileName);
	}


	
	/**
	 * Sets additional graphical representation of my base type. 
	 * The features of additional representation are: 
	 * shape, color, image filename.
	 */
	public void setAdditionalReprOfBasisType(int sh, Color col, String imgFilename) {
		this.setAdditionalReprOfBasisType(sh, col, this.filled, imgFilename);
	}

	/**
	 * Sets additional representation of my base type. 
	 * The features of additional representation are: 
	 * shape, color, filled shape, image filename.
	 */
	public void setAdditionalReprOfBasisType(int sh, Color col, 
			boolean filledShape, String imgFilename) {
		
		this.shape = sh;
		this.color = col;
		this.filled = filledShape;
		this.imageFileName = imgFilename;
		
		String shapeStr = "";
		String colorStr = this.color.toString();
		String markStr = "[]";
		switch (this.shape) {
		case EditorConstants.RECT:
			shapeStr = "RECT";
			markStr = "[NODE]";
			break;
		case EditorConstants.ROUNDRECT:
			shapeStr = "ROUNDRECT";
			markStr = "[NODE]";
			break;
		case EditorConstants.CIRCLE:
			shapeStr = "CIRCLE";
			markStr = "[NODE]";
			break;
		case EditorConstants.OVAL:
			shapeStr = "OVAL";
			markStr = "[NODE]";
			break;
		case EditorConstants.ICON:
			shapeStr = "ICON";
			markStr = "[NODE]";
			break;
		case EditorConstants.SOLID:
			shapeStr = "SOLID_LINE";
			markStr = "[EDGE]";
			break;
		case EditorConstants.DASH:
			shapeStr = "DASH_LINE";
			markStr = "[EDGE]";
			break;
		case EditorConstants.DOT:
			shapeStr = "DOT_LINE";
			markStr = "[EDGE]";
			break;
		default:
			shapeStr = "RECT";
			markStr = "[NODE]";
			break;
		}
		
		String filledStr = "";
		if (this.filled) {
			if ("[NODE]".equals(markStr)) 
				filledStr = "FILLED";
			else if ("[EDGE]".equals(markStr)) 
				filledStr = "BOLD";		
		}
		
		String addRepr = ":";
		addRepr = addRepr.concat(shapeStr).concat(":");
		addRepr = addRepr.concat(colorStr).concat(":");		
		if (!"".equals(filledStr)) {
			addRepr = addRepr.concat(filledStr).concat(":");
		}
		addRepr = addRepr.concat(markStr).concat(":");
		// additional representation in the basis type does not contain the image file name
		this.bType.setAdditionalRepr(addRepr);
		
		// store the image file name separately
		this.bType.setImageFilename(this.imageFileName);
	}
	
	/*
	 * Set the image file name in the basis type if it is not empty or different in this type.
	 */
	protected void enrichAdditionalRepr() {
		if (!this.imageFileName.equals("")
				&& this.bType.getImageFilename().indexOf(this.imageFileName) == -1) {
			this.bType.setImageFilename(this.imageFileName);
		}
	}
	
	/**
	 * Returns a list with five features of the additional graphical 
	 * representation of its base type: 
	 * a shape string (by default RECT), 
	 * a color string (by default java.awt.Color[r=0,g=0,b=0]), 
	 * a filled shape string (by default empty or FILLED resp. BOLD), 
	 * an image filename string (by default empty or place.jpg),
	 * a marking string ( [NODE] or [EDGE]).
	 */
	public List<String> getAdditionalReprOfBasisType() {
//		 System.out.println("EdType.getAdditionalReprOfBasisType() : "+this.bType.getAdditionalRepr());

		String addRepr = this.bType.getAdditionalRepr();
		
		String shapeStr = "";
		String colorStr = "";
		String filledStr = "";
		String imageFileNameStr = "";
		String markStr = "[]";
		
		String[] test = addRepr.split(":");
		for (int i=0; i<test.length; i++) {
			String testStr = test[i];
			if ((testStr.indexOf("RECT") != -1)
					|| (testStr.indexOf("ROUND") != -1)
					|| (testStr.indexOf("CIRCLE") != -1)
					|| (testStr.indexOf("OVAL") != -1)
					|| (testStr.indexOf("IMAGE") != -1)
					|| (testStr.indexOf("SOLID_LINE") != -1)
					|| (testStr.indexOf("DASH_LINE") != -1)
					|| (testStr.indexOf("DOT_LINE") != -1)) {
				shapeStr = testStr;
			} else if (testStr.indexOf("Color") != -1) {
				colorStr = testStr;
			} else if ((testStr.indexOf("FILLED") != -1)
					|| (testStr.indexOf("BOLD") != -1)) {
				filledStr = testStr;
			} else if ((testStr.indexOf(".jpg") != -1)
					|| (testStr.indexOf(".gif") != -1)
					|| (testStr.indexOf(".xpm") != -1)) {
				imageFileNameStr = testStr;
			} else if ((testStr.indexOf("[NODE]") != -1)
					|| (testStr.indexOf("[EDGE]") != -1)) {
				markStr = testStr;
			}			
		}
		
		this.imageFileName = this.bType.getImageFilename();
		
		final Vector<String> v = new Vector<String>(5);
		v.add(shapeStr);
		v.add(colorStr);
		v.add(filledStr);
		v.add(imageFileNameStr);
		v.add(markStr);
		
		return v;
	}

	public boolean hasOldAdditionalRepr() {
		if (this.bType == null)
			return false;
		if (!this.bType.getAdditionalRepr().equals("")) {
			// System.out.println(this.bType.getAdditionalRepr());
			if ((this.bType.getAdditionalRepr().indexOf("51:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("52:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("53:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("54:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("55:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("61:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("62:") != -1)
					|| (this.bType.getAdditionalRepr().indexOf("63:") != -1)) {
				// System.out.println("EdType.hasOldAdditionalRepr() true");
				return true;
			} 
			return false;
		} 
		return true;
	}

	/**
	 * Allows to show nodes like icons, if the node type has an icon
	 * representation.
	 */
	public void setIconable(boolean iconable) {
		this.iconable = iconable;
	}

	/**
	 * Returns TRUE if icon filename is not empty and icon has to be shown .
	 */
	public boolean isIconable() {
		if (!this.imageFileName.equals("") && this.iconable)
			return true;
		
		return false;
	}

	public void setAnimated(boolean b) {
		this.animated = b;		
	}
	
	public boolean isAnimated() {
		return this.animated;		
	}
	
	public int getAnimationKind() {
		return this.animationParameter.kind;
	}
	
	/**
	 * Sets the path of type resources (gif or jpg files) to the specified path
	 */
	public void setResourcesPath(String path) {
		this.resourcesPath = path;
	}

	/**
	 * Gets the path of type resources (gif or jpg files)
	 */
	public String getResourcesPath() {
		return this.resourcesPath;
	}

	public void setContextUsage(String context) {
		this.itsContextUsage = context;
	}

	public String getContextUsage() {
		return this.itsContextUsage;
	}

	/**
	 * Redefines this layout type.
	 * Returns TRUE if its name, shape, color or
	 * image filename was changed, otherwise - FALSE.
	 */
	public boolean redefineType(String newName, int newShape, Color newColor, boolean filledshape,
			String newImageFileName) {
//		System.out.println("EdType.redefineType: this.name, this.shape, this.color, image  "+newImageFileName+"  "+this.imageFileName );

		if (!this.name.equals(newName) 
				|| (this.shape != newShape)
				|| !this.color.equals(newColor)
				|| (this.filled != filledshape)) {
			this.typeKeyChanged = true;
		}
		
		if (this.typeKeyChanged 
				|| !this.imageFileName.equals(newImageFileName)) {			
			this.name = newName;
			this.shape = newShape;
			this.color = newColor;
			this.filled = filledshape;
			this.imageFileName = newImageFileName;
			
//			System.out.println("image  "+newImageFileName+"  "+this.imageFileName );
			
			if (this.bType != null) {
				this.bType.setStringRepr(this.name);
				setAdditionalReprOfBasisType(this.shape, this.color, filledshape,
						this.imageFileName);
			}
			return true;
		}
		return false;
	}

	public boolean hasTypeKeyChanged() {
		return this.typeKeyChanged;
	}
	
	public void setTypeKeyChanged(boolean b) {
		this.typeKeyChanged = b;
	}
	
	/**
	 * Redefines this layout type.
	 * Returns TRUE if its name, shape, color or
	 * image filename was changed, otherwise - FALSE.
	 * The comment is set in each case.
	 */
	public boolean redefineType(String newName, int newShape, Color newColor,
			String newImageFileName, String newComment) {

		boolean result = redefineType(newName, newShape, newColor, false,
				newImageFileName);
		if (this.bType != null)
			this.bType.setTextualComment(newComment);
		return result;
	}

	/**
	 * Redefines this layout type.
	 * Returns TRUE if its name, shape, color or
	 * image filename was changed, otherwise - FALSE.
	 * The comment is set in each case.
	 */
	public boolean redefineType(String newName, int newShape, Color newColor, boolean filledshape,
			String newImageFileName, String newComment) {

		boolean result = redefineType(newName, newShape, newColor, filledshape,
				newImageFileName);
		if (this.bType != null)
			this.bType.setTextualComment(newComment);
		return result;
	}
	
	/** Compares to the type specified by the EdType t */
	public boolean compareTo(EdType t) {
		if (this.bType.compareTo(t.getBasisType()))
			return true;
		
		return false;
	}

	/**
	 * Finds out if there is any relation between this type and the given one.
	 * Two types are related if they have one common ancestor.
	 */
	public boolean isParentOf(EdType t) {
		if (this.bType.isParentOf(t.getBasisType()))
			return true;
		
		return false;
	}

	public void XwriteObject(XMLHelper h) {
		if (h.openObject(this.bType, this)) {			
			if (this.animated) {
//				System.out.println(this.getName()+"::  "
//						+animationParameter.getKind()+"   "
//						+animationParameter.getStep()+"   "
//						+animationParameter.getDelay()+"   "
//						+animationParameter.getEndPlus()+"   "
//						+animationParameter.getTargetEdgeTypeName()
//				);

				h.addAttr("animated", "true");
				h.openSubTag("Animation");
				h.addAttr("kind", this.animationParameter.getKind());
				h.addAttr("step", this.animationParameter.getStep());
				h.addAttr("delay", this.animationParameter.getDelay());
				h.addAttr("plus", this.animationParameter.getEndPlus());
				h.addAttr("edge", this.animationParameter.getTargetEdgeTypeName());
				h.close();
			}
		}
	}
	
	public void XreadObject(XMLHelper h) {
		h.peekObject(this.bType, this);
		String animatedAttr = h.readAttr("animated");
		if ("true".equals(animatedAttr)) {
			this.setAnimated(true);
			if (h.readSubTag("Animation")) {
				String par1 = h.readAttr("delay");
				if (!"".equals(par1))
					this.animationParameter.setDelay(par1);
						
				String par2 = h.readAttr("kind");
				if (!"".equals(par2))
					this.animationParameter.setKind(par2);
				
				String par3 = h.readAttr("step");
				if (!"".equals(par3))				
					this.animationParameter.setStep(par3);
			
				String par4 = h.readAttr("plus");
				if (!"".equals(par4))				
					this.animationParameter.setEndPlus(par4);
				
				String par5 = h.readAttr("edge");
				if (!"".equals(par5))
					this.animationParameter.setTargetEdgeTypeName(par5);
				
//				System.out.println(this.getName()+"::  "+animationParameter.kind+"   "+animationParameter.step+"   "+animationParameter.delay+"   "+this.animationParameter.targetEdgeTypeName);
			}
		}
	}
	

}
