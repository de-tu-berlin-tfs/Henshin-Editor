package agg.convert;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.Node;

import agg.xt_basis.Arc;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Type;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
//import agg.util.XMLHelper;

public class AGG2ColorGraph {

//	private static XMLHelper h;
//	private static GraGra gragra;
//	private static String fileName;

	
	/**
	 * Save an AGG graph in GraphColor format.
	 * 
	 */
	public AGG2ColorGraph() {}
	
	
	/**
	 * Export the host graph of the specified GraGra in GraphColor format.
	 * @param gra
	 * @param outFileName  is the full file name and must end with ".col" 
	 * @param nodetype
	 * @param edgetype
	 *
	public static void exportAGG2ColorGraph(
			final GraGra gra, 
			final String outFileName,
			final String nodetype,
			final String edgetype
				) {	
		exportAGG2ColorGraph(gra.getGraph(), outFileName, nodetype, edgetype);
	}
	*/
	 
	
	/**
	 * Export the host graph of the specified GraGra in GraphColor format.
	 * @param gra
	 * @param outFileName  is the full file name and must end with ".col" 
	 * @param nodetype
	 * @param edgetype
	 */
	public static void exportAGG2ColorGraph(
			final GraGra gra, 
			final String outFileName,
			final Type nodetype,
			final Type edgetype
				) {					
		exportAGG2ColorGraph(gra.getGraph(), outFileName, nodetype, edgetype);
	}
	
	/**
	 * Export the specified Graph in GraphColor format.
	 * @param graph
	 * @param outFileName  is the full file name and must end with ".col"
	 * @param nodeType
	 * @param edgeType
	 *
	public static void exportAGG2ColorGraph(
			final Graph graph, 
			final String outFileName,
			final String nodeType,
			final String edgeType) {
		
		final Type ntype = graph.getTypeSet().getTypeByName(nodeType);
		final Type etype = graph.getTypeSet().getTypeByName(edgeType);
		exportAGG2ColorGraph(graph, outFileName, ntype, etype);
	}
	*/
	
	
	/**
	 * Export the specified Graph in GraphColor format.
	 * @param graph
	 * @param outFileName  is the full file name and must end with ".col"
	 * @param nodeType
	 * @param edgeType
	 */
	public static void exportAGG2ColorGraph(
			final Graph graph, 
			final String outFileName,
			final Type nodeType,
			final Type edgeType) {
		
//		System.out.println(outFileName+"   "+nodeType+"   "+edgeType);	
		if (outFileName.endsWith(".col")) {
			boolean NODE_TYPE = false;
			boolean EDGE_TYPE = false;
			
			final Hashtable<GraphObject,GraphObject> 
			map = new Hashtable<GraphObject,GraphObject>();
			
			final List<Arc> edges = new Vector<Arc>();
			edges.addAll(graph.getArcsSet());
			
			final List<Node> nodes = new Vector<Node>();
			nodes.addAll(graph.getNodesSet());
						
			final File f = new File(outFileName);
			ByteArrayOutputStream baOut = null;
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				
				// write comment
				baOut = new ByteArrayOutputStream();				
				String commentPart1 = "c AGG graph (.ggx) ";
				String commentPart2 = "to GraphColor (.col)\n";
				String commentNodeType = "";
				String commentEdgeType = "";
				if (nodeType != null) {
					commentNodeType = "NODE_TYPE:"+nodeType.getName()+" ";
					NODE_TYPE = true;
				}	
				if (edgeType != null) {
					commentEdgeType = "EDGE_TYPE:"+edgeType.getName();
					EDGE_TYPE = true;
				}				
				
				String comment = commentPart1
									.concat(commentNodeType)
									.concat(commentEdgeType)
									.concat(commentPart2);
				
				if (NODE_TYPE) {
					nodes.clear();
					nodes.addAll(getNodes(graph, nodeType));
				}
				
				final List<Arc> edgeList = new Vector<Arc>();
				if (EDGE_TYPE) {
					if (NODE_TYPE) {				
						edgeList.addAll(getArcs(graph, nodeType, edgeType));
					} else {
						edgeList.addAll(getArcs(graph, null, edgeType));
					}
				} else if (NODE_TYPE) {
					edgeList.addAll(getArcs(graph, nodeType, null));
				} else {
					edgeList.addAll(edges);
				}
				
				edges.clear();				
				for (int i=0; i<edgeList.size(); i++) {
					final Arc a = edgeList.get(i);
					if (map.get(a.getSource()) != a.getTarget()
							&& map.get(a.getTarget()) != a.getSource()) {
						map.put(a.getSource(), a.getTarget());								
						edges.add(a);
					}
				}
				
				// put in out stream and file						
				baOut.write(comment.getBytes());					
				fos.write(baOut.toByteArray());					
				baOut.flush();	
				
				// write edge problem
				baOut = new ByteArrayOutputStream();
				String problem = "p edge ";
				problem = problem.concat(String.valueOf(nodes.size()));
				problem = problem.concat(" ");
				problem = problem.concat(String.valueOf(edges.size()));
				problem = problem.concat("\n");
				// put in out stream and file						
				baOut.write(problem.getBytes());					
				fos.write(baOut.toByteArray());					
				baOut.flush();
				
				for (int i=0; i<edges.size(); i++) {
					final Arc a = edges.get(i);				
					int src = nodes.indexOf(a.getSource())+1;
					int tar = nodes.indexOf(a.getTarget())+1;

					String str = "e ";
					str = str.concat(String.valueOf(src)).concat(" ");
					str = str.concat(String.valueOf(tar).concat("\n"));
							
					// write edge line				
					baOut = new ByteArrayOutputStream();
					baOut.write(str.getBytes());							
					fos.write(baOut.toByteArray());							
					baOut.flush();	
				}					
			} catch (IOException e) {}				
		}
	}

	private static List<Node> getNodes(final Graph graph, final Type nodeType) {
		final List<Node> list = new Vector<Node>();
		final Iterator<Node> all = graph.getNodesSet().iterator();
		while (all.hasNext()) {
			final Node n = all.next();
			if (n.getType() == nodeType) {
				list.add(n);
			}
		}
		return list;
	}
	
	private static  List<Arc> getArcs(final Graph graph, final Type nodeType, final Type edgeType) {
		final List<Arc> list = new Vector<Arc>();
		final Iterator<Arc> all = graph.getArcsSet().iterator();
		while (all.hasNext()) {
			final Arc a = all.next();
			if (a.getType() == edgeType) {
				if (nodeType != null) {
					if (a.getSource().getType() == nodeType
							&& a.getTarget().getType() == nodeType) {
						list.add(a);
					}
				}
			} else {
				if (a.getSource().getType() == nodeType
							&& a.getTarget().getType() == nodeType) {
					list.add(a);
				}
			}
		}
		return list;
	}
	
	/**
	 * Import a ColorGraph which is specified by String colorFileName
	 * into the specified Graph of the specified GraGra.
	 * @param gra
	 * @param graph
	 * @param colorFileName  is the full file name and must end with ".res"
	 */
	public static boolean importColorGraph2AGG(
			final GraGra gra, 
			final Graph graph, 
			final String colorFileName,
			final Type nodeType,
			final Type edgeType) {
		
//		System.out.println(colorFileName);	
		if (colorFileName.endsWith(".res")
				&& gra.isElement(graph)) {
					
			boolean result = false;
			int size = 0;
			
			final List<Node> nodes = new Vector<Node>();
			
			if (nodeType != null) {
				nodes.addAll(getNodes(graph, nodeType));
			} else {
				nodes.addAll(graph.getNodesSet());
			}
			
			FileInputStream fos = null;			
			byte b[] = new byte[2048];
			int count = 0;	
			int lineEnd = 0;
			String str = "";
			try {
				final File f = new File(colorFileName);
				fos = new FileInputStream(f);				
				while (count != -1 &&  lineEnd != -1) {
					count = fos.read(b);					
					if (count != -1) {
						String s = new String(b);
						final String[] array = s.split("CLRS");
						for (int i=0; i<array.length; i++) {
							array[i] = "CLRS"+array[i];
//							System.out.println(array[i]);
						}
						s = array[array.length-1];
//						System.out.println(s);
						while (lineEnd != -1) {
							lineEnd = s.indexOf("\n");	
							if (lineEnd != -1)  {
								str = s.substring(0, lineEnd);	
//								System.out.println(str);
								s = s.substring(lineEnd+1, s.length()-1);
							}
							if (str.startsWith("CLRS")) {
								continue;
							}
							
							while (str.charAt(0) == ' ') {
								str = str.substring(1, str.length());
							}

							String[] str_e = str.split("   ");
							
							for (int i=0; i<str_e.length; i++) {										
								String color = str_e[i].trim();
								int indx = i+size;
								if (indx < nodes.size()) {
									Node node = nodes.get(indx);
//									System.out.println(indx+"   "+node);
									if (node.getAttribute() != null) {
										ValueTuple val = (ValueTuple) node.getAttribute();
										ValueMember mem = val.getValueMemberAt("color");
										if (mem == null)
											mem = val.getValueMemberAt("Color");
										if (mem != null) {	
											result = true;
											if (mem.getDeclaration().getTypeName().equals("int")) {
												mem.setExprAsText(color);
											}
											else if (mem.getDeclaration().getTypeName().equals("String")
													|| mem.getDeclaration().getTypeName().equals("java.lang.String")) {
												mem.setExprAsObject(color);
											}
											
//											System.out.println(indx+"  color: "+mem.getExprAsText());
//											System.out.println("AGG2ColorGraph.importColorGraph2AGG::  color attribute set of node");
//											if (val.getValueMemberAt("name") != null) {
//												System.out.println(indx+"  name: "+val.getValueMemberAt("name").getExprAsText()
//														+"  color: "+mem.getExprAsText());
//											} 
//											else {
//												System.out.println(indx+"  color: "+mem.getExprAsText());
//											}
										} 
									}
								}
							}
							
							size = size + str_e.length;
						}
					}					
				}
			} catch (IOException e) {}
			
			return result;
		}
		
		return false;
	}

	
	
	/*
	private static void mainTEST(String[] args) {
		String vers = System.getProperty("java.version");
		if (vers.compareTo("1.5") < 0) {
			System.out.println("WARNING : Swing must be run with the "
					+ "1.5 version of the JVM.");
		}

		if (args.length == 1) {	
			fileName = args[0];
			if (fileName.indexOf(".ggx") > 0) {
				gragra = load(fileName);
				
				final AGG2ColorGraph test = new AGG2ColorGraph();
				
				exportAGG2ColorGraph(gragra, fileName.concat(".col"), null, null);
			}			
		}
		else if (args.length == 2) {
			fileName = args[0];
			String fileName2 = args[1];
			if (fileName.indexOf(".ggx") > 0) {
				gragra = load(fileName);
				
				if (fileName2.indexOf(".res") > 0) {
					final AGG2ColorGraph test = new AGG2ColorGraph();
				
					importColorGraph2AGG(gragra, gragra.getGraph(), fileName2, null, null);
					
					gragra.save(fileName);
				}
			}			
		}
		else {
			warning();
			return;
		}
	}
*/
	
	static void warning() {
		System.out
		.println("Usage unaliased: java -oss3m -Xmx1000m agg.convert.AGG2ColorGraph grammar.ggx ");
		System.out.println("Usage aliased:");
		System.out.println("agg2color grammar");
		System.out.println("Output file: grammar.ggx.col");
		
		System.out.println("other usage:");
		System.out
		.println("agg2color grammar.ggx colorResultImport.res ");
		System.out.println("Overwritten file: grammar.ggx");

	}

	/*
	private static GraGra load(String fName) {
		if (fName.endsWith(".ggx")) {
			h = new XMLHelper();
			if (h.read_from_xml(fName)) {
				// create a gragra
				GraGra gra =  new GraGra(false);
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			} else 
				return null;
		} else
			return null;
	}
*/
	
}
