package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TripleGraph;

public class Test {
	static boolean debug = true;
	private static void out(String output){
		if(debug){
		  System.out.println(output+"\n");
		}
	}
	
	private static void outGraphRepresentation(Graph graph){
		if (!debug) return;
		String r = "";
		if (graph.getRule()!=null){
			r+=">> Rule "+graph.getRule().getName()+" ";
			if (graph.isRhs()){
				r+="RHS-";
			}else{
				r+="LHS-";
			}
		}
		r+= "Graph "+graph.getName()+":  ";
		
		
		for(Node n : graph.getNodes()){
			r+="\n";
			String origin = "";
			if (graph.getRule()!=null){
				Node originNode = graph.getRule().getMappings().getOrigin(n);
				if(originNode!=null){
					origin+=originNode.getType().getName();
				}
				origin+= "-->";
			}
			r+="["+origin+n.getType().getName()+"; ";
			r+=""+((TNode)n).getMarkerType()+": Attributes="+getAttributesRepresentation(n)+"]";
		}
		out(r);
	}
	
	private static String getAttributesRepresentation(Node node){
		String r ="Attributes(";
		for (Attribute att : node.getAttributes()){
			r+= att.getType().getName()+"="+att.getValue();
			r+="|";
			r+=((TAttribute)att).getMarkerType();
			r+="; ";
		}
		return r+=")";
	}
	
	public static String outNodeRepresentation(
			org.eclipse.emf.henshin.model.Node n) {
		String r = "NODE: "+n.getType().getName()+"--";
		r+=getAttributesRepresentation(n);
		return r;
	}
	
	
	static void check(boolean condition, String output){
		if (!condition) throw new IllegalArgumentException(output);
	}
	public static void check(boolean condition){
		if (!condition) throw new IllegalArgumentException("problem");
	}
	
	public static void valid(Graph g){
		TripleGraph graph = (TripleGraph) g;
		int scx = graph.getDividerSC_X();
		int ctx = graph.getDividerCT_X();
		check(scx!=0 && ctx!=0, "dividers not set");
		
		for (Node n_ : graph.getNodes()){
			TNode n = (TNode)n_;
			check(n.getGraph()==graph);
			for (Edge e : n.getAllEdges()){
				check(e.getGraph()==graph);
				check(e.getSource().getGraph()==graph);
				check(e.getTarget().getGraph()==graph);
				check(e.getSource().getGraph()==e.getTarget().getGraph());
				check(graph.getNodes().contains(e.getTarget()));
				check(graph.getNodes().contains(e.getSource()));
			}
		}
		
		for (Edge e : graph.getEdges()){
			check(e.getGraph()==graph);
			check(e.getSource().getGraph()==graph);
			check(e.getTarget().getGraph()==graph);
			check(graph.getNodes().contains(e.getTarget()));
			check(graph.getNodes().contains(e.getSource()));
		}
	}
	
	public static void checkAttributes(Graph graph){
		for (Node node : graph.getNodes()){
			for (Attribute a : node.getAttributes()){
				if (a.getValue()==null) Test.check(false);
			}
		}
	}
	
	public static void checkMarks(Graph graph){
		String edgeMessage = "No mark found for Edge: ";
		String nodeMessage = "No mark found for Node: ";
//		for(Edge e : graph.getEdges()){
//			if (((TEdge)e).getMarkerType()==null){
//				Test.check(false, edgeMessage+e);
//			}
//		}
//		for(Node n : graph.getNodes()){
//			if (((TNode)n).getMarkerType()==null){
//				Test.check(false, nodeMessage+n);
//			}
//		}
	}
/*	
	public static void checkCopiedMarks(Graph graph1, Graph graph2){
		for (Node node1 : graph1.getNodes()){
			for (Node node2 : graph2.getNodes()){
				if (NodeUtil.isCopy(node1, node2, null)){
					Test.check(((TNode)node2).getMarkerType()==((TNode)node1).getMarkerType());
					for (Attribute a1 : node1.getAttributes()){
						for (Attribute a2 : node2.getAttributes()){
							if (AttributeUtil.isCopy(a1, a2)){
								Test.check(((TAttribute)a1).getMarkerType()==((TAttribute)a2).getMarkerType());
							}
						}
					}
				}
			}
		}
	}*/

	public static void checkMarks(Rule rule){
		checkMarks(rule.getRhs());
	}
	public static void valid(Rule r){
		valid(r.getLhs());
		valid(r.getRhs());
		
		for (Node nl : r.getLhs().getNodes()){
			if (null == r.getMappings().getImage(nl, r.getRhs())) check(false);
		}
	}

	
}
