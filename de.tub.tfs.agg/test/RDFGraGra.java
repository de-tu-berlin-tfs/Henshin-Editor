/**
 * 
 */



import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrTypeMember;
import agg.attribute.impl.AttrTupleManager;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.util.Pair;
import agg.util.XMLHelper;
import agg.xt_basis.Arc;
import agg.xt_basis.GraGra;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Type;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;

/**
 * @author olga
 *
 */
public class RDFGraGra extends GraGra {

	Hashtable<String, Node> name2node = new Hashtable<String, Node>();
	
	Hashtable<String, List<Pair<String,String>>> relations = new Hashtable<String, List<Pair<String,String>>>();
	
	
	public RDFGraGra() {
		super();
	}
	
	public void XreadObject(XMLHelper h) {	
		// read the grammar from .ggx file
		if (h.isTag("GraphTransformationSystem", this)) {
			String subtag = h.readSubTag();
			if (subtag != null && subtag.equals("rdf:RDF")) {
				setName("RDF");
	
				loadXML(h);
				
				h.close();
			}
		}
	}
	
	private void loadXML(XMLHelper h) {				
		String rdf = h.readAttr("xmlns:rdf");	
		if ("".equals(rdf)) {
			return;
		}
		
		String xmlns_j_0 = h.readAttr("xmlns:j.0");
		String[] array = xmlns_j_0.split("/");
		String graname = array[array.length-1];
		if (!"".equals(graname))
			this.setName(graname);
		
		// is a TypeGraph needed?
//		this.typeSet.createTypeGraph();
		
		// read family
		boolean graphnameSet = false;
		while (h.readSubTag("rdf:Description")) {
			
			String about = h.readAttr("rdf:about");
			if (!"".equals(about)) {
				List<Pair<String, String>> rels = new Vector<Pair<String, String>>();
				
				array = about.split("/");
				
				// set graph name
				if (!graphnameSet) {
					if (!"".equals(array[2])) {
						this.getGraph().setName(array[2]);
						graphnameSet = true;
					}
				}
				
				String str= array[array.length-1];
				String name = str.trim().replace(' ', '_');			
				Node n = getNode(name);
	
				if (n!= null) {
					boolean readSubTag = true;
					while (readSubTag) {
						String subtag = h.readSubTag();
						if (subtag != null && subtag.startsWith("j.0:")) {
							String[] array1 = subtag.split(":");
							String str1 = array1[array1.length-1];
							String edgename = str1;
							
							boolean attr = false;
							if (str1.equals("equal")) {
								attr = true;
							} 
							
							String resource = h.readAttr("rdf:resource");
							String[] array2 = resource.split("/");								
							String str2 = array2[array2.length-1];
								
							if (attr) {
								String attrname = str2.trim().replace(' ', '_');
								addAttribute(attrname, n);
								
								edgename = "";
							}
							else {
								String nodename = str2.trim().replace(' ', '_');
								Node n1 = getNode(nodename);
									
								if (n1 != null) {
									rels.add(new Pair<String,String>(edgename, nodename));
								}
							}
							
							h.close();
						} else {
							readSubTag = false;
						}
					}
										
					this.relations.put(name, rels);					
				} 
			}
			h.close();
		}
		
		createEdges();
		
		if (this.getTypeGraph() != null)
			this.setLevelOfTypeGraphCheck(TypeSet.ENABLED);
	}

	private Node getNode(final String name) {
		if (name != null) {
			Node n = this.name2node.get(name);
			if (n == null) {
				Type type = this.typeSet.getTypeByName(name);
				if (type == null) {
					type = getTypeSet().createNodeType(false); // without attribute
					type.setStringRepr(name);
				}
				if (type != null) {
					boolean ok = true;
					if (this.getTypeGraph() != null) {
						ok = createTypeNode(type);
					}
					if (ok) {
						try {
							n = getGraph().createNode(type);
							this.name2node.put(name, n);								
							return n;
						} catch (TypeException ex) {}
					}
				}
			} else
				return n;
		} 
		return null;
	}
	
	private void createEdges() {
		Enumeration<String> names = this.relations.keys();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			Node n = this.name2node.get(name);
			if (n != null) {
				List<Pair<String,String>> rels = this.relations.get(name);
				for (int i=0; i<rels.size(); i++) {
					Pair<String,String> rel = rels.get(i);					
					Node n1 = this.name2node.get(rel.second);
					if (n1 != null) {
						createEdge(rel.first, n, n1);
					}
				}
			}
		}
	}
	
	private Arc createEdge(String name, Node src, Node tar) {
		Type type = getTypeSet().getTypeByName(name);
		if (type == null) {
			type = getTypeSet().createArcType(false); // without attribute
			type.setStringRepr(name);
		}
		if (type != null) {
			boolean ok = true;
			if (this.getTypeGraph() != null) {
				ok = createTypeEdge(type, src.getType(), tar.getType());
			}
			if (ok) {
				try {
					return getGraph().createArc(type, src, tar);
				} catch (TypeException ex) {}
			}
		}
		return null;
	}
	
	private void addAttribute(String attrname, GraphObject obj) {
		if (obj.getAttribute() == null) {
			
			obj.getType().createAttributeType();
			AttrTypeMember attrmem = obj.getType().getAttrType().addMember();
			attrmem.setName(attrname);
			attrmem.setType("String");
			attrmem.setHandler(AttrTupleManager.getDefaultManager().getHandlers()[0]);
			
			obj.createAttributeInstance();
			
			ValueMember vm = ((ValueTuple) obj.getAttribute()).getValueMemberAt(attrname);
			if (vm != null) {
				vm.setExprAsText("\"".concat(attrname).concat("\""));
//				((ValueTuple) obj.getAttribute()).showValue();
			}
		}
	}
	
	private boolean createTypeNode(Type type) {
		if (this.getTypeGraph() != null) {
			try {
				return this.typeSet.getTypeGraph().createNode(type) != null;
			} catch (TypeException ex) {}
		}	
		return false;
	}
	
	private boolean createTypeEdge(Type type, Type srcType, Type tarType) {
		List<Node> list1 = this.typeSet.getTypeGraph().getNodes(srcType);
		Node src = !list1.isEmpty()? list1.get(0): null;
		List<Node> list2 = this.typeSet.getTypeGraph().getNodes(tarType);
		Node tar = !list2.isEmpty()? list2.get(0): null;
		if (src != null && tar != null) {
			try {
				return this.typeSet.getTypeGraph().createArc(type, src, tar) != null;
			} catch (TypeException ex) {}
		}	
		return false;
	}
	
	
	@SuppressWarnings("unused")
	private void loadXML_OLD(XMLHelper h) {		
		
		String rdf = h.readAttr("xmlns:rdf");		
		String xmlns_j_0 = h.readAttr("xmlns:j.0");
		this.setName(xmlns_j_0);
		
		// make types		
		Type person = getTypeSet().createNodeType(true);
		person.setStringRepr("Person");
		((DeclTuple)person.getAttrType()).addMember(agg.attribute.facade.impl.DefaultInformationFacade
				.self().getJavaHandler(), "String", "name");
		
		Type parentOf = getTypeSet().createArcType(false);
		parentOf.setStringRepr("parentOf");
		
		Type childOf = getTypeSet().createArcType(false);
		childOf.setStringRepr("childOf");
		
		Type siblingOf = getTypeSet().createArcType(false);
		siblingOf.setStringRepr("siblingOf");
		
		Type spouseOf = getTypeSet().createArcType(false);
		spouseOf.setStringRepr("spouseOf");
		
		try {
			this.typeSet.createTypeGraph();
			Node p = this.typeSet.getTypeGraph().createNode(person);
			
			this.typeSet.getTypeGraph().createArc(parentOf, p, p);
			this.typeSet.getTypeGraph().createArc(childOf, p, p);
			this.typeSet.getTypeGraph().createArc(siblingOf, p, p);
			this.typeSet.getTypeGraph().createArc(spouseOf, p, p);
		} catch (TypeException ex) {}
		
		// read family
		boolean graphnameSet = false;
		while (h.readSubTag("rdf:Description")) {
			
			String about = h.readAttr("rdf:about");
			if (!"".equals(about)) {
				List<Pair<String, String>> rels = new Vector<Pair<String, String>>();
				
				String[] array = about.split("/");
				if (!graphnameSet) {
					if (!"".equals(array[2])) {
						this.getGraph().setName(array[2]);
						graphnameSet = true;
					}
				}
				int i = array.length-1;
				String name = array[i];
				Node n = this.name2node.get(name.trim().replace(' ', '_'));
				if (n == null) {
					try {
						n = getGraph().createNode(person);
						((ValueTuple) n.getAttribute()).getValueMemberAt("name")
						.setExprAsText("\"".concat(name).concat("\""));
	//					((ValueTuple) nP.getAttribute()).showValue();						
						this.name2node.put(name, n);					
					} catch (TypeException ex) {}
				}
				
				if (n!= null) {
					while (h.readSubTag("j.0:siblingOf")) {
						String resource = h.readAttr("rdf:resource");
						String[] array1 = resource.split("/");
						int i1 = array1.length-1;
						String name1 = array1[i1];
						Node n1 = this.name2node.get(name1);
						if (n1 == null) {
							try {
								n1 = getGraph().createNode(person);
								((ValueTuple) n1.getAttribute()).getValueMemberAt("name")
								.setExprAsText("\"".concat(name1).concat("\""));
	//							((ValueTuple) n.getAttribute()).showValue();
								this.name2node.put(name1, n1);
							} catch (TypeException ex) {}
						}
						if (n1 != null) {
							rels.add(new Pair<String,String>("siblingOf",name1));
						}
						h.close();
					}
					
					while (h.readSubTag("j.0:childOf")) {
						String resource = h.readAttr("rdf:resource");
						String[] array1 = resource.split("/");
						int i1 = array1.length-1;
						String name1 = array1[i1];
						Node n1 = this.name2node.get(name1);
						if (n1 == null) {
							try {
								n1 = getGraph().createNode(person);
								((ValueTuple) n1.getAttribute()).getValueMemberAt("name")
								.setExprAsText("\"".concat(name1).concat("\""));
	//							((ValueTuple) n.getAttribute()).showValue();
								this.name2node.put(name1, n1);
							} catch (TypeException ex) {}
						}
						if (n1 != null) {
							rels.add(new Pair<String,String>("childOf",name1));							
						}
						h.close();
					}
					
					while (h.readSubTag("j.0:parentOf")) {
						String resource = h.readAttr("rdf:resource");
						String[] array1 = resource.split("/");
						int i1 = array1.length-1;
						String name1 = array1[i1];
						Node n1 = this.name2node.get(name1);
						if (n1 == null) {
							try {
								n1 = getGraph().createNode(person);
								((ValueTuple) n1.getAttribute()).getValueMemberAt("name")
								.setExprAsText("\"".concat(name1).concat("\""));
	//							((ValueTuple) n.getAttribute()).showValue();
								this.name2node.put(name1, n1);
							} catch (TypeException ex) {}
						}
						if (n1 != null) {
							rels.add(new Pair<String,String>("parentOf",name1));							
						}
						h.close();
					}
					
					while (h.readSubTag("j.0:spouseOf")) {
						String resource = h.readAttr("rdf:resource");
						String[] array1 = resource.split("/");
						int i1 = array1.length-1;
						String name1 = array1[i1];
						Node n1 = this.name2node.get(name1);
						if (n1 == null) {
							try {
								n1 = getGraph().createNode(person);
								((ValueTuple) n1.getAttribute()).getValueMemberAt("name")
								.setExprAsText("\"".concat(name1).concat("\""));
	//							((ValueTuple) n.getAttribute()).showValue();
								this.name2node.put(name1, n1);
							} catch (TypeException ex) {}
						}
						if (n1 != null) {
							rels.add(new Pair<String,String>("spouseOf",name1));								
						}
						h.close();
					}
					
					this.relations.put(name, rels);					
				} 
			}
			h.close();
		}
		
		createEdges_OLD();
		
		this.setLevelOfTypeGraphCheck(TypeSet.ENABLED);
	}
	
	
	private void createEdges_OLD() {
		Enumeration<String> names = this.relations.keys();
		while (names.hasMoreElements()) {
			String person = names.nextElement();
			Node n = this.name2node.get(person);
			if (n != null) {
				List<Pair<String,String>> rels = this.relations.get(person);
				for (int i=0; i<rels.size(); i++) {
					Pair<String,String> rel = rels.get(i);
					Node n1 = this.name2node.get(rel.second);
					if (n1 != null) {
						Type et = getTypeSet().getTypeByName(rel.first);
						try {
							getGraph().createArc(et, n, n1);
						} catch (TypeException ex) {}
						
					}
				}
			}
		}
	}
	
}
