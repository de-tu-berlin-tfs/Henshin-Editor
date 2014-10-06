package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

import de.tub.tfs.henshin.tggeditor.util.rule.copy.Graph2GraphCopyMappingList;

public class CoSpan {
	
	private Graph graphCoSpanL;
	private Graph graphCoSpanR;
	private Graph graphCoSpanC;
	private MappingList graphCoSpanL2graphCoSpanC;
	private MappingList graphCoSpanR2graphCoSpanC;


	public Graph getGraphCoSpanL() {
		return graphCoSpanL;
	}

	public Graph getGraphCoSpanR() {
		return graphCoSpanR;
	}

	public Graph getGraphCoSpanC() {
		return graphCoSpanC;
	}

	public MappingList getGraphCoSpanL2graphCoSpanC() {
		return graphCoSpanL2graphCoSpanC;
	}

	public MappingList getGraphCoSpanR2graphCoSpanC() {
		return graphCoSpanR2graphCoSpanC;
	}
	
	
	
	/*
	public CoSpan(CoSpan cs){
		this.graph2Copy = new Graph2Copy(cs.getGraphC());
		this.graphC = graph2Copy.getCopy();
		this.graphL2graphC = new MappingListImpl();
		this.graphR2graphC = new MappingListImpl();
		this.graphL = cs.getGraphL();
		this.graphR = cs.getGraphR();
		for (Mapping m : cs.getGraphL2GraphC()){
			graphL2graphC.add(m.getOrigin(), ((Graph2Copy)graphC).getImage(m.getImage()));
		}
		for (Mapping m : cs.getGraphR2GraphC()){
			graphR2graphC.add(m.getOrigin(), ((Graph2Copy)graphC).getImage(m.getImage()));
		}
	}*/
	private GraphIntersectionHandler intersectionHandler;
	
	public GraphIntersectionHandler getIntersectionHandler(){
		return this.intersectionHandler;
	}

	public CoSpan(GraphIntersectionHandler intersection){
		if (intersection==null) throw new IllegalArgumentException("null is not a valid CoSpan argument");
		this.intersectionHandler = intersection;
		this.graphCoSpanL = intersection.getGraphL();
		this.graphCoSpanR = intersection.getGraphR();
		this.graphCoSpanC = intersection.getGraphL2GraphLCopy().getGraphCopy();
		Graph2GraphCopyMappingList graphR2Copy = intersection.getGraphR2GraphRCopy();
		this.graphCoSpanL2graphCoSpanC = new MappingListImpl();
		Test.check(!intersection.getGraphL2GraphLCopy().isEmpty());
		Test.check(this.graphCoSpanL2graphCoSpanC.addAll(intersection.getGraphL2GraphLCopy()));
		
		Test.check(!intersection.getGraphL2GraphLCopy().isEmpty());
		this.graphCoSpanR2graphCoSpanC = new MappingListImpl();
		//this.graphCoSpanR2graphCoSpanC.addAll(intersection.getGraphR2GraphCopy());
		//merge graphCopies
		//first copy items from graphCopyR in GraphCopyL
		for (Mapping m : intersection){
			Node nodeCopyL = m.getOrigin();
			Node nodeCopyR = m.getImage();
			Test.check(nodeCopyL.getGraph()==intersection.getGraphL2GraphLCopy().getGraphCopy());
			Test.check(nodeCopyR.getGraph()==intersection.getGraphR2GraphRCopy().getGraphCopy());
		}
		
		while(graphR2Copy.getGraphCopy().getNodes().size()!=0){
			Node nodeCopyR = graphR2Copy.getGraphCopy().getNodes().get(0);
			Node nodeGraphR = graphR2Copy.getOrigin(nodeCopyR);
			Test.check(nodeGraphR!=null);
			nodeCopyR.setGraph(graphCoSpanC);
			Test.check(nodeCopyR.getGraph()==graphCoSpanC);
			Mapping m = this.graphCoSpanR2graphCoSpanC.add(nodeGraphR, nodeCopyR);
			Test.check(m.getImage().getGraph()==graphCoSpanC);
			//Test.check(graphCoSpanC.getNodes().add(nodeCopyR));
		}
		
		while(graphR2Copy.getGraphCopy().getEdges().size()!=0){
			Edge edgeCopyR = graphR2Copy.getGraphCopy().getEdges().get(0);
			edgeCopyR.setGraph(graphCoSpanC);
			//Test.check(graphCoSpanC.getEdges().add(edgeCopyR));
		}
		
		for (Mapping m : intersection){
			Node nodeCopyL = m.getOrigin();
			Node nodeCopyR = m.getImage();
			Test.check(nodeCopyL.getGraph()==graphCoSpanC);
			Test.check(nodeCopyR.getGraph()==graphCoSpanC);
			Node nodeR = graphCoSpanR2graphCoSpanC.getOrigin(nodeCopyR);
			Test.check(nodeCopyL!=null);
			Test.check(nodeCopyR!=null);
			Test.check(nodeR!=null);
			//Dont remove
			Test.check(graphCoSpanR2graphCoSpanC.remove(nodeR, nodeCopyR)!=null);
			Test.check(graphCoSpanR2graphCoSpanC.add(nodeR, nodeCopyL)!=null);
			while(nodeCopyR.getIncoming().size()!=0){
				Edge incomEdgeR = nodeCopyR.getIncoming().get(0);
				incomEdgeR.setGraph(graphCoSpanC);
				incomEdgeR.setTarget(nodeCopyL);
				Test.check(incomEdgeR.getTarget()==nodeCopyL);
				
			}
			while(nodeCopyR.getOutgoing().size()!=0){
				Edge outgoingEdgeR = nodeCopyR.getOutgoing().get(0);
				outgoingEdgeR.setGraph(graphCoSpanC);
				outgoingEdgeR.setSource(nodeCopyL);
				Test.check(outgoingEdgeR.getSource()==nodeCopyL);
			}
			graphCoSpanC.removeNode(nodeCopyR);
		}
		Test.check(!this.graphCoSpanL2graphCoSpanC.isEmpty());
		Test.check(!this.graphCoSpanR2graphCoSpanC.isEmpty());
		
		Test.valid(graphCoSpanC);
		Test.valid(graphCoSpanL);
		Test.valid(graphCoSpanR);
		for(Mapping m : getGraphCoSpanL2graphCoSpanC()){
			Test.check(m.getImage().getGraph()==graphCoSpanC);
		}
		
	}
}
