


import java.util.Iterator;

import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.Morphism;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;

public class SierpinskiTriangleRecursive1 extends SierpinskiTriangleBasic {
	
	private Rule rule1;	
	private Match match1;
	private GraphObject n1ofR1, n2ofR1, n3ofR1;
	private Arc a1ofR1, a2ofR1, a3ofR1;	
	private GraphObject startGraphNode;
		
	public SierpinskiTriangleRecursive1(String filename) {
		this(filename, "1");
	}
	
	public SierpinskiTriangleRecursive1(String filename, String nn) {
		super(filename, nn);		
	}

	public static void main(String[] args) {
		if (args.length == 0) {
			helpText();
		} else {
			handleInput(args);
			new SierpinskiTriangleRecursive1(fileName, String.valueOf(NN));
		}
	}

	public void graTraEventOccurred(GraTraEvent event) {}
	
	void addGraTraEventListener() {}
	
	void prepareTransform() {}
		
	void transform(int iterations) {			
		NN = iterations;
		
		this.rule1 = getRule();			
		this.match1 = gragra.createMatch(this.rule1);

		this.n1ofR1 = getStartObject(this.rule1.getLeft(), "1");
		this.a1ofR1 = getOutEdge(this.n1ofR1, "1");
		this.n2ofR1 = this.a1ofR1.getTarget();
		this.a2ofR1 = getOutEdge(this.n2ofR1, "2");
		this.n3ofR1 = this.a2ofR1.getTarget();
		this.a3ofR1 = getOutEdge(this.n3ofR1, "3");
				
		this.startGraphNode = getStartObject(gragra.getGraph(), "1");
			
		String s = "";
		s = "Opening time: "+(System.currentTimeMillis()-this.startTime)+"ms";
		System.out.println(s);
				
		if (this.writeLogFile) 
			writeTransformProtocol(s);
		
		int I=1;
		if (gragra.getGraph().getNodesCount() == 3) {
			long time0 = System.currentTimeMillis();
			applyRule1(this.match1, this.startGraphNode);
			
			s = "\n "+I+". iteration: "
			+"used time: "+(System.currentTimeMillis()-time0)+"ms"
//					+" *** Graph nodes: "+gragra.getGraph().getNodesCount()
//					+" *** edges: "+gragra.getGraph().getArcsCount()
					;						
			System.out.println(s);
			
			if (this.writeLogFile) 
				writeTransformProtocol(s);
			
			I++;
			NN--;
		} else {
			int max = gragra.getGraph().getArcsCount();
			int m = 3;
			while (m*3 <= max) {
				m = m*3;
				I++;
			}
		}
		
		for (int i=I; i<=NN+I-1; i++) {
			long time0 = System.currentTimeMillis();	
			
			applyTriangles1(this.startGraphNode);
			
			s = "\n "+i+". iteration: "
			+"used time: "+(System.currentTimeMillis()-time0)+"ms"
//					+" *** Graph nodes: "+gragra.getGraph().getNodesCount()
//					+" *** edges: "+gragra.getGraph().getArcsCount()
					;			
			System.out.println(s);	
			
			if (this.writeLogFile) 
				writeTransformProtocol(s);
		}
		
		s = "\nUsed time: "+(System.currentTimeMillis()-this.startTime)+"ms";
		System.out.println(s);
		
		if (this.writeLogFile) {
			writeTransformProtocol(s);
			closeTransformProtocol();
		}
	}
	
	private Rule getRule() {
		if (!gragra.getListOfRules().isEmpty()) {
			return gragra.getListOfRules().get(0);
		}
		
		return null;		
	}

	private boolean makeStep(final Match m) {
		try {
			Morphism comatch = StaticStep.execute(m);	
			m.clear();
			((OrdinaryMorphism) comatch).dispose();
			return true;			
		} catch (TypeException ex) {}
		return false;
	}
			
	private Arc getOutEdge(GraphObject go, String edgeTypeName) {
		Iterator<Arc> outArcs = ((Node)go).getOutgoingArcsSet().iterator();
		while (outArcs.hasNext()) {
			Arc arc = outArcs.next();
			String typeName = arc.getType().getName();
			if (typeName.equals(edgeTypeName)) {					
				return arc;
			}
		}
		return null;
	}
	
	/*
	private Arc getOutEdge(GraphObject src, String edgeTypeName, GraphObject tar) {
		Iterator<Arc> outArcs = ((Node)src).getOutgoingArcsSet().iterator();
		while (outArcs.hasNext()) {
			Arc arc = outArcs.next();
			String typeName = arc.getType().getName();
			if (typeName.equals(edgeTypeName) && arc.getTarget() == tar) {					
				return arc;
			}
		}
		return null;
	}
	*/
	
	private GraphObject getStartObject(Graph g, String edgeTypeName) {
		Iterator<Node> nodes = g.getNodesSet().iterator();
		while (nodes.hasNext()) {
			Node go = nodes.next();
			Iterator<Arc> outArcs = go.getOutgoingArcsSet().iterator();
			while (outArcs.hasNext()) {
				String typeName = outArcs.next().getType().getName();
				if (typeName.equals(edgeTypeName))
					return go;
			}
		}
		return null;
	}
			
	private GraphObject applyRule1(
			final Match m,
			final GraphObject rootNode) {								
//		final long time = System.currentTimeMillis();
		GraphObject n1 = rootNode;			
		Arc a1 = getOutEdge(n1, "1");
		GraphObject n2 = a1.getTarget();
		Arc a2 = getOutEdge(n2, "2");		
		GraphObject n3 = a2.getTarget();
		Arc a3 = getOutEdge(n3, "3");

		try {							
			m.addMapping(this.n1ofR1, n1);
			m.addMapping(this.n2ofR1, n2);
			m.addMapping(this.n3ofR1, n3);
			m.addMapping(this.a1ofR1, a1);
			m.addMapping(this.a2ofR1, a2);
			m.addMapping(this.a3ofR1, a3);
		} catch (BadMappingException ex) {
			System.out.println("applyRule1  FAILED!");
			return null;
		}
		if (m.isTotal()/* && m.isValid()*/) {
			if (makeStep(m)) {
				return n3;
			}
			System.out.println("applyRule1   FAILED!"); 
			return null;
		}		
		return null;
	}
		
	void applyTriangles1(GraphObject currentVertex){
		GraphObject leftArc = getOutEdge(currentVertex, "1");		
		if (leftArc == null)
			return;
		
		GraphObject left = ((Arc)leftArc).getTarget();
				
		GraphObject right = applyRule1(this.match1, currentVertex);
		if (right == null)
			return;	
		
		if (getOutEdge(left, "1") != null) {
			applyTriangles1(left);
		}
		
		if (getOutEdge(right, "1") != null) {
			applyTriangles1(right);
		}
    }			
	
	void description() {
		String s = " -) rule set with a single rule only,"
			+"\n -) recursive rule application,"
			+"\n -) predefined match mapping\n";
		System.out.println(s);
	}
}
