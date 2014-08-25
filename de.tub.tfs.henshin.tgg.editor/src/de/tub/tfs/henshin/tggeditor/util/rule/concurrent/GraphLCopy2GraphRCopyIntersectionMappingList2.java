/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util.rule.concurrent;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

import de.tub.tfs.henshin.tggeditor.util.GraphicalRuleUtil;
import de.tub.tfs.henshin.tggeditor.util.rule.copy.Graph2GraphCopyMappingList;
/*
public class GraphLCopy2GraphRCopyIntersectionMappingList extends MappingListImpl{
	
	private static final long serialVersionUID = 902816361130151562L;
	
	private Graph2GraphCopyMappingList graphL2GraphCopy;
	private Graph2GraphCopyMappingList graphR2GraphCopy;
	private Graph graphL;
	private Graph graphR;
	private IntersectionHandler attributesValuesHandler;
	private Set<Edge> edges;
	//private Set<Node> nodes;

	public Graph2GraphCopyMappingList getGraphL2GraphLCopy() {
		return graphL2GraphCopy;
	}

	public Graph2GraphCopyMappingList getGraphR2GraphRCopy() {
		return graphR2GraphCopy;
	}

	public Graph getGraphL() {
		return graphL;
	}

	public Graph getGraphR() {
		return graphR;
	}
	
	public IntersectionHandler getIntersectingAttributesHandler(){
		return attributesValuesHandler;
	}

	public GraphLCopy2GraphRCopyIntersectionMappingList(Graph leftGraph, Graph rightGraph){
		super();
		this.graphL = leftGraph;
		this.graphR = rightGraph;
		this.graphL2GraphCopy = new Graph2GraphCopyMappingList(this.graphL);
		this.graphR2GraphCopy = new Graph2GraphCopyMappingList(this.graphR);
		this.attributesValuesHandler = new IntersectionHandler();
		this.edges = new HashSet<Edge>();
		//this.nodes = new HashSet<Node>();
	}
	
	public boolean addEdges(Edge left, Edge right){
		return (this.edges.add(left) && this.edges.add(right));
	}
	
	
	public boolean containsEdge(Edge edge){
		return (this.edges.contains(edge));
	}
	
	//return either isConsitently Present, is present but not consistent(oronly partially present)or isnot present at all (unknown)
	public Boolean isConsistent(Node origin, Node image){
		if (containsOrigin(origin) && containsImage(image)){
			return (getImage(origin, image.getGraph())==getOrigin(image));
		}else if (containsOrigin(origin) || containsImage(image)){
			return false;
		}else return null;
	}
	
	public boolean addIntersectingNodes(Node nodeL, Node nodeR){
		if (!intersectingGraphNodes(nodeL, nodeR)) return false;
		if (!this.attributesValuesHandler.addIntersectingNodes(nodeL, nodeR,  RuleUtil.getRHSNode(nodeR))) return false;
		if (this.add(nodeL, nodeR)==null) return false;
		return true;
	}
	
	public boolean intersectingGraphNodes(Node nodeL, Node nodeR){
		Boolean isConsistent = isConsistent(nodeL, nodeR);
		if (isConsistent!=null && !isConsistent)return false;
		if (!checkBijectivity(nodeL, nodeR)) return false;
		return this.attributesValuesHandler.intersectingGraphNodes(nodeL, nodeR);
	}
	
	private boolean checkBijectivity(
			Node iNodeGraphCopyL, 
			Node iNodeGraphCopyR){
		boolean inComingEdges = false;
		boolean nodesSwaped = false;
		IntersectionHandler iAttHandler = this.getIntersectingAttributesHandler();
		do{
			EList<Edge> edgesiNodeGraphCopyL = (inComingEdges ? iNodeGraphCopyL.getIncoming() : iNodeGraphCopyL.getOutgoing());
			for (Edge edgeiNodeGraphCopyL : edgesiNodeGraphCopyL){
				if (this.containsEdge(edgeiNodeGraphCopyL)) continue;
				Node iNodeGraphCopyLAssociate = (inComingEdges ? edgeiNodeGraphCopyL.getSource() : edgeiNodeGraphCopyL.getTarget());
				//skip each associated node that is not part of the intersection
				boolean partOfIntersection = (nodesSwaped ? this.containsImage(iNodeGraphCopyLAssociate) :
														    this.containsOrigin(iNodeGraphCopyLAssociate));
				if (!partOfIntersection) continue;
				boolean iNodeGraphCopyRAssociateFound = false;
				EList<Edge> edgesiNodeGraphCopyR = (inComingEdges ? iNodeGraphCopyR.getIncoming() : iNodeGraphCopyR.getOutgoing());
				for (Edge edgeiNodeGraphCopyR : edgesiNodeGraphCopyR){
					Node iNodeGraphCopyRAssociate = (inComingEdges ? edgeiNodeGraphCopyR.getSource() : edgeiNodeGraphCopyR.getTarget());
					boolean intersectingNodes = (nodesSwaped ? 
							iAttHandler.intersectingGraphNodes(iNodeGraphCopyRAssociate, iNodeGraphCopyLAssociate) :
							iAttHandler.intersectingGraphNodes(iNodeGraphCopyLAssociate, iNodeGraphCopyRAssociate));
					if (intersectingNodes){
						boolean consistent = (nodesSwaped ? 
								this.getImage(iNodeGraphCopyRAssociate)!=iNodeGraphCopyLAssociate 
								|| this.getOrigin(iNodeGraphCopyLAssociate)!=iNodeGraphCopyRAssociate :
								this.getImage(iNodeGraphCopyLAssociate)!=iNodeGraphCopyRAssociate 
								|| this.getOrigin(iNodeGraphCopyRAssociate)!=iNodeGraphCopyLAssociate);
						if (!consistent) continue;
						iNodeGraphCopyRAssociateFound = true;
					}
				}
				if (!iNodeGraphCopyRAssociateFound) {
					System.out.println("Nonbijective, edge missing in GraphR");
					return false;
				}
			}
			if (inComingEdges && nodesSwaped) break;
			//false -> true -> false -> true
			inComingEdges=!inComingEdges;
			//true -> false -> true
			if (!inComingEdges){//false -> true-> false
				Node tmp = iNodeGraphCopyL;
				iNodeGraphCopyL = iNodeGraphCopyR;
				iNodeGraphCopyR = tmp;
				//false -> true -> true
				nodesSwaped = true;
			}
		} while(true);
		return true;
	}
	
	
	/*
	public GraphCopyIntersectionMappings(GraphCopyIntersectionMappings intersection){
		super();
		this.graphL2GraphCopy = new Graph2GraphCopyMappingList(intersection.getGraphL());
		this.graphR2GraphCopy = new Graph2GraphCopyMappingList(intersection.getGraphR());
		this.graphL = intersection.getGraphL();
		this.graphR = intersection.getGraphR();
		for(Mapping m : intersection){
			Node source = intersection.getGraphL2GraphCopy().getOrigin(m.getOrigin());
			Node target = intersection.getGraphR2GraphCopy().getOrigin(m.getImage());
			Node sourceCopy = graphL2GraphCopy.getImage(source);
			Node targetCopy = graphR2GraphCopy.getImage(target);
			if (this.add(sourceCopy, targetCopy)==null) throw new IllegalArgumentException("Problem");
		}
	}*/

	/*
	private boolean valid(Mapping m){
		return graphL2GraphCopy.getGraphCopy().getNodes().contains(m.getOrigin()) && graphR2GraphCopy.getGraphCopy().getNodes().contains(m.getImage());
	}
	
	private boolean valid(Collection<? extends Mapping> c){
		for (Mapping m : c){
			if (!(graphL2GraphCopy.getGraphCopy().getNodes().contains(m.getOrigin()) && graphR2GraphCopy.getGraphCopy().getNodes().contains(m.getImage()))) return false;
		}
		return true;
	}
	*/
	/*
	@Override
	public boolean add(Mapping e) {
		boolean result = super.add(e);
		if (result && (e.getImage() instanceof Node) && (e.getOrigin() instanceof Node)){
			this.nodes.add((Node) e.getOrigin());
			this.nodes.add((Node) e.getImage());
		}
		return result;
	}
	
	@Override
	public Mapping add(Node origin, Node image) {
		Mapping result = super.add(origin, image);
		if (result!=null && (origin instanceof Node) && (image instanceof Node)){
			this.nodes.add(origin);
			this.nodes.add(image);
		}
		return result;
	}

/*
	@Override
	public void add(int index, Mapping element) {
		if (!valid(element)) return;
		super.add(index, element);
	}

	@Override
	public boolean addAll(Collection<? extends Mapping> c) {
		if (!valid(c))return false;
		return super.addAll(c);
	}

	@Override
	public boolean addAll(int index, Collection<? extends Mapping> c) {
		if (!valid(c))return false;
		return super.addAll(index, c);
	}

	@Override
	public Mapping set(int index, Mapping element) {
		if (!valid(element)) return element;
		return super.set(index, element);
	}

	
	@Override
	public void add(Edge origin, Edge image) {
		if (!(graphL2GraphCopy.getGraphCopy().getEdges().contains(origin) && graphR2GraphCopy.getGraphCopy().getEdges().contains(image))) return;
		super.add(origin, image);
	}
	
	@Override
	public Mapping add(Attribute origin, Attribute image) {
		boolean foundO = false;
		boolean foundI = false;
		for (Node nL : graphL2GraphCopy.getGraphCopy().getNodes()){
			for (Attribute aL : nL.getAttributes()){
				if (aL==origin) foundO=true;
				break;
			}
			if (foundO) break;
		}
		for (Node nR : graphR2GraphCopy.getGraphCopy().getNodes()){
			for (Attribute aR : nR.getAttributes()){
				if (aR==image) foundI=true;
				break;
			}
			if (foundI) break;
		}
		if (!(foundO && foundI)) return null;
		return super.add(origin, image);
	}

	@Override
	public <E extends GraphElement> void add(E origin, E image) {
		// TODO Auto-generated method stub
		super.add(origin, image);
	}
	
	public Node getImage(Node node){
		return super.getImage(node, graphR2GraphCopy.getGraphCopy());
	}

	public Edge getImage(Edge origin) {
		return super.getImage(origin, graphR2GraphCopy.getGraphCopy());
	}
*/
/*
	public Attribute getImage(Attribute origin) {
		return super.getImage(origin, graphR2GraphCopy.getGraphCopy());
	}

	public <E extends GraphElement> E getImage(E origin) {
		return super.getImage(origin, graphR2GraphCopy.getGraphCopy());
	}

	public boolean containsOrigin(Node node){
		return this.getImage(node)!=null;
	}
	
	public boolean containsImage(Node node){
		return this.getOrigin(node)!=null;
	}
	
	public String toString(){
		String r="Intersection Mapping: ";
		r+="[";
		for (Mapping m :this){
			r+=Test.outNodeRepresentation(m.getOrigin())+"-->"+Test.outNodeRepresentation(m.getImage())+", \n";
		}
		r+="]";
		return r;
	}

}
*/