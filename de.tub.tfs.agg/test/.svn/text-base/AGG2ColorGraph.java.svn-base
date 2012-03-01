

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.Node;

import agg.xt_basis.Arc;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.util.XMLHelper;

public class AGG2ColorGraph {

	private static XMLHelper h;

	private static GraGra gragra;

	private static String fileName;
	
	private String colorFileName; 
	
	private int indx;

	private static String outputFileName;


	public AGG2ColorGraph(final String arg, final String  arg1, final String arg2) {
		if (arg != null && arg.endsWith(".ggx")) {
			fileName = arg;
		}
		if (arg1 != null) {
			this.indx = 0;
			try {
				Integer I = Integer.valueOf(arg1);
				this.indx = I.intValue();
			} catch (NumberFormatException ex) {}
		}		
		if (arg2 != null && arg2.endsWith(".res")) {
			this.colorFileName = arg2;
		}
		
		
		if (fileName != null) {
			System.out.println("File name:  " + fileName);
			/* load gragra */			
			gragra = load(fileName);
		}
		
		if (gragra != null) {
			if (this.colorFileName == null) {
				outputFileName = fileName.concat("-NodeEdge.col");
				saveAGGNodeEdge2ColorGraph(gragra, outputFileName);
				System.out.println("Output file:  " + outputFileName);
				
	//			outputFileName = filename.concat(".col");
	//			saveAGG2ColorGraph(gragra, outputFileName);
	//			System.out.println("Output file:  " + outputFileName);
				
	//			outputFileName = filename.concat(".aggcol");
	//			saveAGGColor(gragra, outputFileName);
	//			System.out.println("AGG color Output file:  " + outputFileName);
			}
			else {
				System.out.println("Import result from:  " + this.colorFileName);
				final Graph graph = gragra.getGraph(this.indx);
				importColorGraph2AGG(gragra, graph);
				gragra.save(fileName);
			}
		} 
		else
			System.out.println("Grammar:  " + fileName + "   FAILED!");
	}


	public static void main(String[] args) {
		String vers = System.getProperty("java.version");
		if (vers.compareTo("1.5") < 0) {
			System.out.println("WARNING : Swing must be run with the "
					+ "1.5 version of the JVM.");
		}

		if (args.length == 1) {		
			new AGG2ColorGraph(args[0], String.valueOf(0), null);
		}
		else if (args.length == 2) {
			try {
				Integer.valueOf(args[1]);
				new AGG2ColorGraph(args[0], args[1], null);
				
			} catch (NumberFormatException ex) {
				
				new AGG2ColorGraph(args[0], null, args[1]);
			}
		}
		else if (args.length == 3) {		
			new AGG2ColorGraph(args[0], args[1], args[2]);
		}
		else {
			warning();
			return;
		}
	}

	static void warning() {
		System.out
				.println("Usage unaliased: java -oss3m -Xmx1000m agg.xt_basis.AGG2ColorGraph grammar.ggx [graph_index]");
		System.out
		.println("Usage unaliased: java -oss3m -Xmx1000m agg.xt_basis.AGG2ColorGraph grammar.ggx [graph_index] color_graph.res");
		System.out.println("Usage aliased:");
		System.out.println("agg2color grammar.ggx [graph_index]");
		System.out.println("Output file: grammar.col");
		
		System.out.println("Import result:");
		System.out.println("agg2color grammar.ggx [graph_index] color_graph.res");
	}

	public GraGra load(String fName) {
		if (fName.endsWith(".ggx")) {
			h = new XMLHelper();
			if (h.read_from_xml(fName)) {
	
				// create a gragra
				GraGra gra =  new GraGra(false);
				h.getTopObject(gra);
				gra.setFileName(fName);
				return gra;
			} 
			return null;
		} 
		return null;
	}


	/**
	 * Save all nodes and edges in GraphColor format.
	 * @param gra
	 * @param outFileName must end with ".col"
	 */
	public void saveAGG2ColorGraph(GraGra gra, String outFileName) {	
		if (outputFileName.endsWith(".col")) {
			
			final List<Arc> edges = new Vector<Arc>();
			edges.addAll(gragra.getGraph(this.indx).getArcsSet());
			
			final List<Node> nodes = new Vector<Node>();
			nodes.addAll(gragra.getGraph(this.indx).getNodesSet());
						
			final File f = new File(outputFileName);
			ByteArrayOutputStream baOut = null;
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				
				// write comment
				baOut = new ByteArrayOutputStream();				
				String comment = "c AGG (.ggx) to Color Graph (.col)\n";
				// put in out stream and file						
				baOut.write(comment.getBytes());					
				fos.write(baOut.toByteArray());					
				baOut.flush();	
				
				// write problem
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

	/**
	 * Search the host graph of the grammar for subgraph with Node<--Edge-->Node structure,
	 * where each edge starts at node Edge and ends at node Node. 
	 * Save this subgraph in GraphColor format.
	 * @param gra
	 * @param outFileName must end with ".col"
	 */
	public void saveAGGNodeEdge2ColorGraph(GraGra gra, String outFileName) {	
		if (outputFileName.endsWith(".col")) {
			
			final List<Node> edges = new Vector<Node>();
			
			final List<Node> nodes = new Vector<Node>();
			nodes.addAll(gragra.getGraph(this.indx).getNodesSet());
			for (int i=0; i<nodes.size(); i++) {
				final Node n = nodes.get(i);
				if (n.getType().getName().equals("Node")) {
					
				}
				else if (n.getType().getName().equals("Edge")) {
					edges.add(n);
					nodes.remove(i);
					i--;
				}
				else {
					nodes.remove(i);
					i--;
				}
			}
			
			final File f = new File(outputFileName);
			ByteArrayOutputStream baOut = null;
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				
				// write comment
				baOut = new ByteArrayOutputStream();				
				String comment = "c AGG to Color Graph (.col)\n";
				// put in out stream and file						
				baOut.write(comment.getBytes());					
				fos.write(baOut.toByteArray());					
				baOut.flush();	
				
				// write problem
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
					final Node en = edges.get(i);
					
					Iterator<Arc> outs = en.getOutgoingArcs();
					if (outs.hasNext()) {
						final Arc a = outs.next();
						int index = nodes.indexOf(a.getTarget())+1;
																				
						while (outs.hasNext()) {
							final Arc aj = outs.next();
							int indxj = nodes.indexOf(aj.getTarget())+1;
							String str = "e ";
							str = str.concat(String.valueOf(index)).concat(" ");
							str = str.concat(String.valueOf(indxj).concat("\n"));
							
							// write edge line				
							baOut = new ByteArrayOutputStream();
							baOut.write(str.getBytes());							
							fos.write(baOut.toByteArray());							
							baOut.flush();							
						}
					}			
				}					
			} catch (IOException e) {}				
		}
	}
	
	/**
	 * Save color result which is computed by AGG grammar.
	 * @param gra
	 * @param outFileName  must end with ".aggcol"
	 */
	public void saveAGGColor(GraGra gra, String outFileName) {	
		if (outputFileName.endsWith(".aggcol")) {
			
			final List<Node> edges = new Vector<Node>();
			
			final List<Node> nodes = new Vector<Node>();
			nodes.addAll(gragra.getGraph(this.indx).getNodesSet());
			for (int i=0; i<nodes.size(); i++) {
				final Node n = nodes.get(i);
				if (n.getType().getName().equals("Node")) {
					
				}
				else if (n.getType().getName().equals("Edge")) {
					edges.add(n);
					nodes.remove(i);
					i--;
				}
				else {
					nodes.remove(i);
					i--;
				}
			}
			
			final File f = new File(outputFileName);
			ByteArrayOutputStream baOut = null;
			FileOutputStream fos = null;
			try {
				fos = new FileOutputStream(f);
				
				// write comment
				baOut = new ByteArrayOutputStream();				
				String comment = "c AGG Color\n";
				// put in out stream and file						
				baOut.write(comment.getBytes());					
				fos.write(baOut.toByteArray());					
				baOut.flush();	
				
				// write problem
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
				
				for (int i=0; i<nodes.size(); i++) {
					final Node n = nodes.get(i);
					ValueTuple val = (ValueTuple)n.getAttribute();
					String color = ((ValueMember)val.getMemberAt("color")).getExprAsText();
					color = color.concat(" ");
						
					// write color				
					baOut = new ByteArrayOutputStream();
					baOut.write(color.getBytes());							
					fos.write(baOut.toByteArray());							
					baOut.flush();															
				}					
			} catch (IOException e) {}				
		}

	}

	/**
	 * Import a ColorGraph which is specified by String colorFileName
	 * into the by index specified Graph of the specified GraGra.
	 * @param gra
	 * @param graph
	 * @param colorFileName  is the full file name and must end with ".res"
	 */
	private boolean importColorGraph2AGG(final GraGra gra, final Graph graph) {	
		if (this.colorFileName.endsWith(".res")) {
								
//			final List<Node> edges = new Vector<Node>();
			
			final List<Node> nodes = new Vector<Node>();
			nodes.addAll(graph.getNodesSet());
			
			for (int i=0; i<nodes.size(); i++) {
				final Node n = nodes.get(i);
				if (n.getType().getName().equals("Node")) {
					
				}
				else if (n.getType().getName().equals("Edge")) {
//					edges.add(n);
					nodes.remove(i);
					i--;
				}
				else {
					nodes.remove(i);
					i--;
				}
			}
					
			final File f = new File(this.colorFileName);
			FileInputStream fos = null;			
			byte b[] = new byte[1024];
			int count = 0;			
			
			try {
				fos = new FileInputStream(f);				
				while (count != -1) {
					count = fos.read(b);					
					if (count != -1) {
						String s = new String(b);
						
						int lineEnd = s.indexOf("\n");											
						while (lineEnd != -1) {
							String str = s.substring(0, lineEnd);								
							s = s.substring(lineEnd+1, s.length()-1);
							lineEnd = s.indexOf("\n");	
							
							if (lineEnd == -1) {								
								while (str.charAt(0) == ' ') {
									str = str.substring(1, str.length());
								}

								String[] str_e = str.split("   ");
								
								for (int i=0; i<str_e.length; i++) {										
									String color = str_e[i].trim();
									
									Node node = nodes.get(i);
									if (node.getAttribute() != null) {
										ValueTuple val = (ValueTuple) node.getAttribute();
										ValueMember mem = val.getValueMemberAt("color");
										if (mem == null)
											mem = val.getValueMemberAt("Color");
										if (mem != null)
											mem.setExprAsText(color);
									}
								}					
							}
						}
					}					
				}
				return true;
				
			} catch (IOException e) {}	
		}
		return false;
	}
	
}
