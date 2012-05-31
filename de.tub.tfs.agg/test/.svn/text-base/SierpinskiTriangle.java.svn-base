


import java.util.Iterator;
import java.util.List;
import java.util.Vector;


import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.GraTraEvent;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Morphism;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.Graph;
import agg.xt_basis.Match;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;

public class SierpinskiTriangle extends SierpinskiTriangleBasic {
	
	private Rule rule;
	
	private Match match;
	
	private GraphObject startRuleNode;
	
	private GraphObject srcN1, srcN2, srcN3;
	
	private Arc srcE1, srcE2, srcE3;
	
	private GraphObject startGraphNode;
				
	public SierpinskiTriangle(String filename) {
		this(filename, "1");
	}
	
	public SierpinskiTriangle(String filename, String nn) {
		super(filename, nn);
	}

	
	public static void main(String[] args) {
		if (args.length == 0) {
			helpText();
		} else {
			handleInput(args);						
			new SierpinskiTriangle(fileName, String.valueOf(NN));			
		}		
	}

	public void graTraEventOccurred(GraTraEvent event) {}
	
	void addGraTraEventListener() {}
	
	void prepareTransform() {}
	
	void description() {
		String s = " -) rule set with a single rule only,"
			+"\n -) programmed rule application,"
			+"\n -) predefined match mapping";
		System.out.println(s);
	}
	
	void transform(int iterations) {		
		int vecSize = 3;
		for (int i=1; i<=iterations; i++) {
			vecSize = vecSize*3 -3;
		}		
		final Vector<GraphObject> targetObjects = new Vector<GraphObject>(vecSize);
		
		this.rule = getRule();		
		this.match = gragra.createMatch(this.rule);
						
		this.startRuleNode = getStartObject(this.rule.getLeft(), "1");
		this.srcN1 = this.startRuleNode;
		this.srcE1 = getOutEdge(this.srcN1, "1");
		this.srcN2 = this.srcE1.getTarget();
		this.srcE2 = getOutEdge(this.srcN2, "2");
		this.srcN3 = this.srcE2.getTarget();
		this.srcE3 = getOutEdge(this.srcN3, "3");
		
		this.startGraphNode = getStartObject(gragra.getGraph(), "1");
		
		fillTargetNodes(gragra.getGraph(), targetObjects);	
		
		String s = "";
		s = "Opening time: "+(System.currentTimeMillis()-this.startTime)+"ms";
		System.out.println(s);	
		
		if (this.writeLogFile) 
			writeTransformProtocol(s);
		
		for (int i=0; i<NN; i++) {
			long time0 = System.currentTimeMillis();
			
			Vector<GraphObject> createdNodes = makeMatchAndStep(this.match, targetObjects);
			
			targetObjects.addAll(createdNodes);	
			
			s = "\n "+(i+1)+". iteration: "
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
	
	private void fillTargetNodes(Graph g, final Vector<GraphObject> vec) {
		Iterator<Node> nodes = g.getNodesSet().iterator();
		while (nodes.hasNext()) {
			GraphObject go = nodes.next();
			if (getOutEdge(go, "1") != null)
				vec.add(go);
		}
		if (vec.get(0) != this.startGraphNode) {
			vec.remove(this.startGraphNode);
			vec.add(0, this.startGraphNode);
		}		
	}
	
	private List<GraphObject> makeStep(final Match m) {
		try {
			Morphism comatch = StaticStep.execute(m);	
			List<GraphObject> list = StaticStep.getCreatedNodes(m.getRule(), comatch);
			m.clear();
			((OrdinaryMorphism) comatch).dispose();
			return list;			
		} catch (TypeException ex) {}
		return new Vector<GraphObject>(0);
	}
	
	private Rule getRule() {
		if (!gragra.getListOfRules().isEmpty()) {
			return gragra.getListOfRules().get(0);
		}
		
		return null;		
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
			
	private Vector<GraphObject> makeMatchAndStep(
			final Match m,
			final Vector<GraphObject> targetNodes) {
					
		final Vector<GraphObject> createdNodes = new Vector<GraphObject>(targetNodes.size()*3);
//		System.out.println("createdNodes   allocated size: "+(targetNodes.size()*3) +"  capacity: "+  createdNodes.capacity());						
		for (int i=0; i<targetNodes.size(); i++) {	
//			final long time = System.currentTimeMillis();
			GraphObject tarN1 = targetNodes.get(i);			
			Arc tarE1 = getOutEdge(tarN1, "1");
			if (tarE1 != null) {
				GraphObject tarN2 = tarE1.getTarget();
				Arc tarE2 = getOutEdge(tarN2, "2");
				if (tarE2 != null) {
					GraphObject tarN3 = tarE2.getTarget();
					Arc tarE3 = getOutEdge(tarN3, "3");
					if (tarE3 != null) {
//						final long time = System.currentTimeMillis();
						try {							
							m.addMapping(this.srcN1, tarN1);
							m.addMapping(this.srcN2, tarN2);
							m.addMapping(this.srcN3, tarN3);
							m.addMapping(this.srcE1, tarE1);
							m.addMapping(this.srcE2, tarE2);
							m.addMapping(this.srcE3, tarE3);
//							System.out.println("mappings: "+m.getSize());
						} catch (BadMappingException ex) {
							System.out.println("MakeNextMatch: FAILED!");
							return createdNodes;
						}
						if (m.isTotal()/* && m.isValid()*/) {
							createdNodes.addAll(makeStep(m));
//							System.out.println(i+": Time for a Step: "+(System.currentTimeMillis()-time));
						}
					} else
						return createdNodes;
				} else
					return createdNodes;				
			} else {
				targetNodes.remove(i); 
				i--;				
			}
//			System.out.println("Time of Step: "+(System.currentTimeMillis()-time));
		}
//		System.out.println("createdNodes: "+createdNodes.size());
		return createdNodes;
	}
	
	
	
	
}
