/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.util.rule.copy;

import java.util.HashSet;
import java.util.Set;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil.Copier;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.GraphElement;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

import de.tub.tfs.henshin.tggeditor.util.rule.concurrent.Test;

public class Graph2GraphCopyMappingList extends MappingListImpl{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private Graph graphSource;
	private Graph graphCopy;
	public Graph getGraphSource() {
		return graphSource;
	}
	public Graph getGraphCopy() {
		return graphCopy;
	}

	public Graph2GraphCopyMappingList(Graph graph) {
		super();
		this.graphSource = graph;
		//this.graphCopy = GraphUtil.copy(this.graphSource);
		boolean islhs = graph.isLhs();
		Rule rule = this.graphSource.getRule();
		Copier copier = new Copier();
		EObject result = copier.copy(rule);
		copier.copyReferences();
		Rule ruleCopy = (Rule) result;
		if (islhs){
			this.graphCopy = ruleCopy.getLhs();
		}else{
			this.graphCopy = ruleCopy.getRhs();
		}
		
		boolean found = false;
		//Set<Node> copiedNodes = new HashSet<Node>();
		for (Node nodeSource : this.graphSource.getNodes()) {
			for (Node nodeCopy : this.graphCopy.getNodes()) {
				if (isCopy(nodeSource, nodeCopy)) {
					super.add(nodeSource, nodeCopy);
					found =true;
				}
			}
			if (!found) throw new IllegalArgumentException("no copy node found");
		}
	}
	
	
	public static boolean isCopy(Node n1, Node n2){//, Set<Node>copiedNodes){
		//if (copiedNodes!=null && copiedNodes.contains(n2))return false;
		return !(n1==null || 
			n2==null || 
			n1 == n2 || 
			n1.getType()!=null && !n1.getType().equals(n2.getType()) ||
			n1.getGraph().getNodes().indexOf(n1)!=n2.getGraph().getNodes().indexOf(n2) ||
			n1.getIncoming().size()!=n2.getIncoming().size() ||
			n1.getOutgoing().size()!=n2.getOutgoing().size()
			);
		//boolean re = concurrentMatches(n1, n2, null, null);
		//if (!re) System.out.println("WEIRD: COPIED NODE IS NO LONGER SAME");
		//if (copiedNodes!=null && re) copiedNodes.add(n2);
		//return re;
	}
	
	public String toString(){
		String r="Graph copy of "+graphSource+" Mapping: ";
		r+="[";
		for (Mapping m :this){
			r+=Test.outNodeRepresentation(m.getOrigin())+"-->"+Test.outNodeRepresentation(m.getImage())+", \n";
		}
		r+="]";
		return r;
	}
	
	//NEW 
	public static Graph copy(Graph graph){
		Copier copier = new Copier();
		EObject g = copier.copy(graph);
		copier.copyReferences();
		return (Graph) g;
	}

	/**
	@Override
	public void move(int newPosition, Mapping object) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mapping move(int newPosition, int oldPosition) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean add(Mapping e) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void add(int index, Mapping element) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public boolean addAll(Collection<? extends Mapping> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addAll(int index, Collection<? extends Mapping> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void clear() {
		// TODO Auto-generated method stub
		
	}


**/
	/**
	@Override
	public boolean remove(Object o) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Mapping remove(int index) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean removeAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean retainAll(Collection<?> c) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public Mapping set(int index, Mapping element) {
		// TODO Auto-generated method stub
		return null;
	}

/**
	@Override
	public Mapping add(Node origin, Node image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(Edge origin, Edge image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mapping add(Attribute origin, Attribute image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends GraphElement> void add(E origin, E image) {
		// TODO Auto-generated method stub
		
	}
**/
	/**
	@Override
	public Mapping remove(Node origin, Node image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void remove(Edge origin, Edge image) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Mapping remove(Attribute origin, Attribute image) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public <E extends GraphElement> void remove(E origin, E image) {
		// TODO Auto-generated method stub
	}
**/
	public Node getImage(Node origin) {
		return super.getImage(origin, graphCopy);
	}

	public Edge getImage(Edge origin) {
		return super.getImage(origin, graphCopy);
	}

	public Attribute getImage(Attribute origin) {
		return super.getImage(origin, graphCopy);
	}

	public <E extends GraphElement> E getImage(E origin) {
		return super.getImage(origin, graphCopy);
	}

}
