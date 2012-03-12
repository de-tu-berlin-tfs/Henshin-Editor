package agg.editor.impl;

import java.awt.Color;
import java.util.Vector;
import java.util.Hashtable;
import agg.xt_basis.Type;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.DeclMember;

public class TypeReprData {

	String name;

	String shape;

	String imageFileName;

	String red, green, blue;

	boolean filled;
	
	Vector<Vector<String>> attributes; // declaration: (name, type, hashCode)

	Vector<String> parents; // names of direct parents only

	Vector<String> children; // names of its own children only

	int srcMinMultiplicity, srcMaxMultiplicity, tarMinMultiplicity,
			tarMaxMultiplicity;

	int typeHC;

	boolean isAbstract;

	String contextUsage;

	String textualComment;

	public TypeReprData(EdType t) {
		this.parents = new Vector<String>(5, 5);
		this.children = new Vector<String>(5, 5);

		this.name = t.getName();
		this.shape = String.valueOf(t.getShape());
		this.filled = t.hasFilledShape();
		this.imageFileName = t.getImageFileName();
		this.red = (Integer.valueOf(t.getColor().getRed())).toString();
		this.green = (Integer.valueOf(t.getColor().getGreen())).toString();
		this.blue = (Integer.valueOf(t.getColor().getBlue())).toString();

		this.attributes = new Vector<Vector<String>>();
		DeclTuple dt = (DeclTuple) t.getBasisType().getAttrType();
		if (dt != null) {
			for (int i = 0; i < dt.getNumberOfEntries(); i++) {
				DeclMember dm = (DeclMember) dt.getMemberAt(i);
				if (dm.getHoldingTuple() == dt) {
					Vector<String> v = new Vector<String>(3);
					v.add(dm.getName());
					v.add(dm.getTypeName());
					v.add(String.valueOf(dm.hashCode()));
					this.attributes.add(v);
				}
			}
		}

		for (int i = 0; i < t.getBasisType().getParents().size(); i++) {
			this.parents.add(t.getBasisType().getParents().get(i).getName());
		}
		for (int i = 0; i < t.getBasisType().getChildren().size(); i++) {
			this.children.add(t.getBasisType().getChildren().get(i).getName());
		}

		this.typeHC = t.hashCode();

		this.contextUsage = ":" + String.valueOf(t.hashCode()) + ":"
				+ t.getContextUsage() + ":";
		this.contextUsage = this.contextUsage.replaceAll("::", ":");

		this.isAbstract = t.getBasisType().isAbstract();
		this.textualComment = t.getBasisType().getTextualComment();
	}

	public TypeReprData(EdNode typeNode) {
		this(typeNode.getType());
		this.srcMinMultiplicity = typeNode.getType().getBasisType()
				.getSourceMin();
		this.srcMaxMultiplicity = typeNode.getType().getBasisType()
				.getSourceMax();
		this.tarMinMultiplicity = this.srcMinMultiplicity;
		this.tarMaxMultiplicity = this.srcMaxMultiplicity;
	}

	public TypeReprData(EdArc edgetype) {
		this(edgetype.getType());
		Type t = edgetype.getBasisArc().getType();
		Type srct = edgetype.getBasisArc().getSource().getType();
		Type tart = edgetype.getBasisArc().getTarget().getType();
		this.srcMinMultiplicity = t.getSourceMin(srct, tart);
		this.srcMaxMultiplicity = t.getSourceMax(srct, tart);
		this.tarMinMultiplicity = t.getTargetMin(srct, tart);
		this.tarMaxMultiplicity = t.getTargetMax(srct, tart);
	}

	public void restoreTypeFromTypeRepr(EdType t) {
		t.setName(this.name);
		t.setColor(getColor());
		t.setShape((Integer.valueOf(this.shape)).intValue());
		t.setFilledShape(this.filled);
		t.setImageFileName(this.imageFileName);

		Type btype = t.getBasisType();
		restoreAttributes(btype);

		t.setContextUsage(this.contextUsage);
		t.getBasisType().setAbstract(this.isAbstract);
		t.getBasisType().setTextualComment(this.textualComment);
	}
	
	public EdType createTypeFromTypeRepr() {
		EdType t = new EdType(this.name, (Integer.valueOf(this.shape)).intValue(), getColor(), this.filled,
				this.imageFileName);
	
		// basis type is still NULL!

		t.setContextUsage(this.contextUsage);
		return t;
	}

	public EdType createTypeFromTypeRepr(Type basis) {
		EdType t = new EdType(this.name, (Integer.valueOf(this.shape)).intValue(), getColor(), this.filled,
				this.imageFileName, basis);
		restoreAttributes(basis);

		t.setContextUsage(this.contextUsage);
		t.getBasisType().setAbstract(this.isAbstract);
		t.getBasisType().setTextualComment(this.textualComment);
		return t;
	}

	public String getName() {
		return this.name;
	}

	public int getShape() {
		return (Integer.valueOf(this.shape)).intValue();
	}

	public Color getColor() {
		Color color = new Color((Integer.valueOf(this.red)).intValue(), (Integer.valueOf(
				this.green)).intValue(), (Integer.valueOf(this.blue)).intValue());
		return color;
	}

	public boolean hasFilledShape() {
		return this.filled;
	}
	
	public String getTypeContextUsage() {
		return this.contextUsage;
	}

	public int getTypeHashCode() {
		return (Integer.valueOf(this.typeHC)).intValue();
	}

	public void restoreAttributes(Type btype) {
		if (btype != null) {
			Vector<Vector<String>> attrs = new Vector<Vector<String>>();
			attrs.addAll(this.attributes);

			if (attrs.isEmpty()) {
				// delete its attr. decl. members
				if (btype.getAttrType() != null) {
					DeclTuple dt = (DeclTuple) btype.getAttrType();
					for (int i = 0; i < dt.getNumberOfEntries(); i++) {
						DeclMember dm = (DeclMember) dt.getMemberAt(i);
						if (dm.getHoldingTuple() == dt)
							dt.deleteMemberAt(dm.getName());
					}
				}
			} else {
				if (btype.getAttrType() == null)
					btype.createAttributeType();
				DeclTuple dt = (DeclTuple) btype.getAttrType();
				if (dt.getNumberOfEntries() == 0) {
					for (int i = 0; i < attrs.size(); i++) {
						Vector<String> v = attrs.get(i);
						String n = v.get(0);
						String tn = v.get(1);

						dt
								.addMember(
										agg.attribute.facade.impl.DefaultInformationFacade
												.self().getJavaHandler(), tn, n);
					}
				} else {
					Hashtable<String, Vector<String>> hc2dmvec = new Hashtable<String, Vector<String>>();
					for (int j = 0; j < attrs.size(); j++) {
						Vector<String> v = attrs.get(j);
						String hashCode = v.get(2);
						hc2dmvec.put(hashCode, v);
					}

					for (int i = 0; i < dt.getNumberOfEntries(); i++) {
						DeclMember dm = (DeclMember) dt.getMemberAt(i);
						if (dm.getHoldingTuple() == dt) {
							boolean found = false;
							// first search decl member by hash code string
							String hcStr = String.valueOf(dm.hashCode());
							Vector<String> v = hc2dmvec.get(hcStr);
							if (v != null) {
								String n = v.get(0);
								String tn = v.get(1);
								if (!dm.getName().equals(n))
									dm.setName(n);
								if (!dm.getTypeName().equals(tn))
									dm.setType(tn);
								found = true;
								attrs.remove(v);
							}
							if (!found) {
								// now search decl member by name
								for (int j = 0; j < attrs.size(); j++) {
									v = attrs.get(j);
									String n = v.get(0);
									String tn = v.get(1);
									if (n.equals(dm.getName())) {
										if (!dm.getTypeName().equals(tn))
											dm.setType(tn);
										attrs.remove(v);
										found = true;
										break;
									}
								}
								if (!found)
									dt.deleteMemberAt(i);
							}
						}
					}
					if (!attrs.isEmpty()) {
						for (int j = 0; j < attrs.size(); j++) {
							Vector<String> v = attrs.get(j);
							String n = v.get(0);
							String tn = v.get(1);
							int pos = this.attributes.indexOf(v);
							if (dt.getMemberAt(n) == null) {
								if (pos < dt.getNumberOfEntries())
									dt
											.addMember(
													pos,
													agg.attribute.facade.impl.DefaultInformationFacade
															.self()
															.getJavaHandler(),
													tn, n);
								else
									dt
											.addMember(
													agg.attribute.facade.impl.DefaultInformationFacade
															.self()
															.getJavaHandler(),
													tn, n);
							}
						}
					}
				}
			}
		}
	}

}
