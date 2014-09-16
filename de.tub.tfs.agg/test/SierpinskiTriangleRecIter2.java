


import java.util.Iterator;
import java.util.Vector;

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

public class SierpinskiTriangleRecIter2 extends SierpinskiTriangleBasic {

	private Rule rule1;	
	private Match match1;
	final private Vector<Vector<GraphObject>> matchVecForR1 = new Vector<Vector<GraphObject>>();
	private GraphObject n1ofR1, n2ofR1, n3ofR1;
	private Arc a1ofR1, a2ofR1, a3ofR1;

	private Rule rule2;
	private Match match2;
	final private Vector<Vector<GraphObject>> matchVecForR2 = new Vector<Vector<GraphObject>>();
	private GraphObject n1ofR2, n2ofR2, n3ofR2;
	private Arc a1ofR2, a2ofR2, a3ofR2;
	
	private GraphObject startGraphNode;
		

	public SierpinskiTriangleRecIter2(String filename) {
		this(filename, "1");
	}
	
	public SierpinskiTriangleRecIter2(String filename, String nn) {
		super(filename, nn);
	}


	public static void main(String[] args) {
		if (args.length == 0) {
			helpText();
		} else {
			handleInput(args);
			new SierpinskiTriangleRecIter2(fileName, String.valueOf(NN));
		}
	}

	public void graTraEventOccurred(GraTraEvent event) {}
	
	void addGraTraEventListener() {}
	
	void prepareTransform() {}

	void description() {
		String s = " -) Gabi's grammar with two rules,"
			+"\n -) iterative rule application"
			+"\n -) on recursive found matches"
			+"\n -) and predefined match mapping\n";
		System.out.println(s);
	}
	
	void transform(int iterations) {
		NN = iterations;
		
		this.rule1 = getRule("Apply1");			
		this.match1 = gragra.createMatch(this.rule1);

		this.n1ofR1 = getStartObject(this.rule1.getLeft(), "1");
		this.a1ofR1 = getOutEdge(this.n1ofR1, "1");
		this.n2ofR1 = this.a1ofR1.getTarget();
		this.a2ofR1 = getOutEdge(this.n1ofR1, "2");
		this.n3ofR1 = this.a2ofR1.getTarget();
		this.a3ofR1 = getOutEdge(this.n2ofR1, "3");
		
		this.rule2 = getRule("Apply2");			
		this.match2 = gragra.createMatch(this.rule2);

		this.n1ofR2 = getStartObject(this.rule2.getLeft(), "2");
		this.a1ofR2 = getOutEdge(this.n1ofR2, "2");
		this.n2ofR2 = this.a1ofR2.getTarget();
		this.a2ofR2 = getOutEdge(this.n1ofR2, "3");		
		this.n3ofR2 = this.a2ofR2.getTarget();
		this.a3ofR2 = getOutEdge(this.n3ofR2, "3");
		
		this.startGraphNode = getStartObject(gragra.getGraph(), "1");
			
		String s = "Opening time: "+(System.currentTimeMillis()-this.startTime)+"ms";
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
			// test: find matches recursive
			this.matchVecForR1.clear();
			this.matchVecForR2.clear();
			
			makeMatchesTriangles1(this.startGraphNode);
					
			long time0 = System.currentTimeMillis();
			// apply rules iterative
			if (applyRule1(this.match1, this.matchVecForR1)) {
				if (applyRule2(this.match2, this.matchVecForR2)) {
					
				} else {
					System.out.println("\n*** "+i+". iteration    FAILED!");
					break;
				}
			} else {
				System.out.println("\n*** "+i+". iteration    FAILED!");
				break;
			}
			
			s = "\n "+I+". iteration: "
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
		
	private boolean  makeStep(final Match m) {
		try {
			Morphism comatch = StaticStep.execute(m);	
			m.clear();
			((OrdinaryMorphism) comatch).dispose();
			return true;			
		} catch (TypeException ex) {}
		return false;
	}
	
	private Rule getRule(String name) {
		if (!gragra.getListOfRules().isEmpty()) {
			for (int i=0; i<gragra.getListOfRules().size(); i++) {
				if (gragra.getListOfRules().get(i).getName().equals(name))
					return gragra.getListOfRules().get(i);
			}
			return null;
		}
		
		return null;		
	}
	
	private Arc getOutEdge(GraphObject go, String edgeTypeName) {
		Iterator<Arc> outArcs = ((Node) go).getOutgoingArcsSet().iterator();
		while (outArcs.hasNext()) {
			Arc arc = outArcs.next();
			String typeName = arc.getType().getName();
			if (typeName.equals(edgeTypeName)) {					
				return arc;
			}
		}
		return null;
	}
	
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
	
	private boolean applyRule1(
			final Match m,
			final Vector<Vector<GraphObject>> matchVecForR) {
		
		int nn = 0;
		for (int i=0; i<matchVecForR.size(); i++) {
			Vector<GraphObject> objs = matchVecForR.get(i);
						
			try {							
				m.addMapping(this.n1ofR1, objs.get(0));
				m.addMapping(this.n2ofR1, objs.get(1));
				m.addMapping(this.n3ofR1, objs.get(2));
				m.addMapping(this.a1ofR1, objs.get(3));
				m.addMapping(this.a2ofR1, objs.get(4));
				m.addMapping(this.a3ofR1, objs.get(5));
			} catch (BadMappingException ex) {
				System.out.println("applyRule1: Apply1  FAILED!");
				return false;
			}

			if (m.isTotal()/* && m.isValid()*/) {			
				if (makeStep(m)) {
					nn++;
				} else {
					System.out.println("Apply1   FAILED!"); 
					return false;
				}
			}	
		}
		if (nn > 0 && nn == matchVecForR.size())
			return true;
		
		return false;
	}

	private boolean applyRule2(
			final Match m,
			final Vector<Vector<GraphObject>> matchVecForR) {

		int nn = 0;
		for (int i=0; i<matchVecForR.size(); i++) {
			Vector<GraphObject> objs = matchVecForR.get(i);
			try {							
				m.addMapping(this.n1ofR2, objs.get(0));
				m.addMapping(this.n2ofR2, objs.get(1));
				m.addMapping(this.n3ofR2, objs.get(2));
				m.addMapping(this.a1ofR2, objs.get(3));
				m.addMapping(this.a2ofR2, objs.get(4));
				m.addMapping(this.a3ofR2, objs.get(5));
			} catch (BadMappingException ex) {
				System.out.println("applyRule2: FAILED!");
				return false;
			}
			if (m.isTotal()/* && m.isValid()*/) {
				if (makeStep(m)) {
					nn++;
				} else {
					System.out.println("Apply2   FAILED!"); 
					return false;
				}
			}
		}		
		if (nn > 0 && nn == matchVecForR.size())
			return true;
		
		return false;
	}
	
	void makeMatchesTriangles1(GraphObject currentVertex){
		GraphObject rightArc = getOutEdge(currentVertex, "2");
		if (rightArc == null)
			return;
		
		GraphObject right = ((Arc)rightArc).getTarget();
		
		GraphObject leftArc = getOutEdge(currentVertex, "1");
		GraphObject left = ((Arc)leftArc).getTarget();
				
		makeMatchRule1(this.match1, currentVertex);
			
		if (getOutEdge(left, "1") != null) 
			makeMatchesTriangles1(left);
		else
			makeMatchesTriangles2(left);
		
		if (getOutEdge(right, "2") != null)
			makeMatchesTriangles2(right);
		
		
    }
	
	void makeMatchesTriangles2(GraphObject currentVertex){		
		GraphObject rightArc = getOutEdge(currentVertex, "2");
		if (rightArc == null)
			return;
		
		GraphObject right = ((Arc)rightArc).getTarget();
				
		GraphObject leftArc = getOutEdge(currentVertex, "3");
		GraphObject left = ((Arc)leftArc).getTarget();

		makeMatchRule2(this.match2, currentVertex);
		
		if (getOutEdge(right, "1") == null)
			makeMatchesTriangles2(right);
		else
			makeMatchesTriangles1(right);
		
		if (getOutEdge(left, "1") == null)
			makeMatchesTriangles2(left);
		else
			makeMatchesTriangles1(left);
    }
	
	private boolean makeMatchRule1(
			final Match m,
			final GraphObject rootNode) {
								
//		final long time = System.currentTimeMillis();
		GraphObject n1 = rootNode;			
		Arc a1 = getOutEdge(n1, "1");
		GraphObject n2 = a1.getTarget();
		Arc a2 = getOutEdge(n1, "2");		
		GraphObject n3 = a2.getTarget();
		Arc a3 = getOutEdge(n2, "3", n3);
		
		if (a3.getTarget() != n3) {
			System.out.println("applyRule1: Apply1  FAILED!  a3.getTarget() != n3");
			return false;
		}

		final Vector<GraphObject> vec = new Vector<GraphObject>(6);
		vec.add(n1);
		vec.add(n2);
		vec.add(n3);
		vec.add(a1);
		vec.add(a2);
		vec.add(a3);
		
		this.matchVecForR1.add(vec);
		
		return true;
	}
	
	private boolean makeMatchRule2(
			final Match m,
			final GraphObject rootNode) {
//		final long time = System.currentTimeMillis();		
		GraphObject n1 = rootNode;			
		Arc a1 = getOutEdge(n1, "2");
		GraphObject n2 = a1.getTarget();
		Arc a2 = getOutEdge(n1, "3");		
		GraphObject n3 = a2.getTarget();
		Arc a3 = getOutEdge(n3, "3", n2);
		if (n2 != a3.getTarget()) {
			System.out.println("Apply2   FAILED!  n2 != a3.getTarget()");
			return false;
		}

		final Vector<GraphObject> vec = new Vector<GraphObject>(6);
		vec.add(n1);
		vec.add(n2);
		vec.add(n3);
		vec.add(a1);
		vec.add(a2);
		vec.add(a3);
		
		this.matchVecForR2.add(vec);

		return true;
	}

	private boolean applyRule1(
			final Match m,
			final GraphObject rootNode) {
								
//		final long time = System.currentTimeMillis();
		GraphObject n1 = rootNode;			
		Arc a1 = getOutEdge(n1, "1");
		GraphObject n2 = a1.getTarget();
		Arc a2 = getOutEdge(n1, "2");		
		GraphObject n3 = a2.getTarget();
		Arc a3 = getOutEdge(n2, "3", n3);
		
		if (a3.getTarget() != n3) {
			System.out.println("applyRule1: Apply1  FAILED!  a3.getTarget() != n3");
			return false;
		}

		try {							
			m.addMapping(this.n1ofR1, n1);
			m.addMapping(this.n2ofR1, n2);
			m.addMapping(this.n3ofR1, n3);
			m.addMapping(this.a1ofR1, a1);
			m.addMapping(this.a2ofR1, a2);
			m.addMapping(this.a3ofR1, a3);
		} catch (BadMappingException ex) {
			System.out.println("applyRule1: Apply1  FAILED!");
			return false;
		}
//		final long time = System.currentTimeMillis();
		if (m.isTotal()/* && m.isValid()*/) {
			if (makeStep(m)) {
				return true;
			} 
			System.out.println("Apply1   FAILED!"); 
			return false;
		}		
		return false;
	}
	
	private boolean applyRule2(
			final Match m,
			final GraphObject rootNode) {
//		final long time = System.currentTimeMillis();		
		GraphObject n1 = rootNode;			
		Arc a1 = getOutEdge(n1, "2");
		GraphObject n2 = a1.getTarget();
		Arc a2 = getOutEdge(n1, "3");		
		GraphObject n3 = a2.getTarget();
		Arc a3 = getOutEdge(n3, "3", n2);
		if (n2 != a3.getTarget()) {
			System.out.println("Apply2   FAILED!  n2 != a3.getTarget()");
			return false;
		}

		try {							
			m.addMapping(this.n1ofR2, n1);
			m.addMapping(this.n2ofR2, n2);
			m.addMapping(this.n3ofR2, n3);
			m.addMapping(this.a1ofR2, a1);
			m.addMapping(this.a2ofR2, a2);
			m.addMapping(this.a3ofR2, a3);
		} catch (BadMappingException ex) {
			System.out.println("applyRule2: FAILED!");
			return false;
		}
		if (m.isTotal()/* && m.isValid()*/) {
			if (makeStep(m)) {
				return true;
			} 
			System.out.println("Apply2   FAILED!"); 
			return false;
		}
		return false;
	}
	
	
	void applyTriangles1(GraphObject currentVertex){
		GraphObject rightArc = getOutEdge(currentVertex, "2");
		if (rightArc == null)
			return;
		
		GraphObject right = ((Arc)rightArc).getTarget();
		
		GraphObject leftArc = getOutEdge(currentVertex, "1");
		GraphObject left = ((Arc)leftArc).getTarget();
				
		applyRule1(this.match1, currentVertex);
			
		if (getOutEdge(left, "1") != null) 
			applyTriangles1(left);
		else
			applyTriangles2(left);
		
		if (getOutEdge(right, "2") != null)
			applyTriangles2(right);
		
		
    }
	
	void applyTriangles2(GraphObject currentVertex){		
		GraphObject rightArc = getOutEdge(currentVertex, "2");
		if (rightArc == null)
			return;
		
		GraphObject right = ((Arc)rightArc).getTarget();
				
		GraphObject leftArc = getOutEdge(currentVertex, "3");
		GraphObject left = ((Arc)leftArc).getTarget();

		applyRule2(this.match2, currentVertex);
		
		if (getOutEdge(right, "1") == null)
			applyTriangles2(right);
		else
			applyTriangles1(right);
		
		if (getOutEdge(left, "1") == null)
			applyTriangles2(left);
		else
			applyTriangles1(left);
    }
	
}
