package tggeditor.commands;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Adapter;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EOperation;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;

import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tgg.TGGFactory;
import tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.analysis.AggInfo;
import de.tub.tfs.henshin.analysis.CriticalPair;

public class CheckConflictCommand extends Command {

	private Rule _firstRule;
	private Rule _secondRule;
	private TransformationSystem _trafo;
	private TGG layoutSystem;

	public CheckConflictCommand(Rule firstRule, Rule secondRule) {
		_firstRule = firstRule;
		_secondRule = secondRule;
		_trafo = _firstRule.getTransformationSystem();
		layoutSystem = NodeUtil.getLayoutSystem(_firstRule);
	}
	
	@Override
	public boolean canExecute() {
		return (_firstRule != null && _secondRule != null && _trafo != null);
	}
	
	
	@Override
	public void execute() {
		AggInfo aggInfo = new AggInfo(_trafo);
		aggInfo.isCritical(_firstRule, _secondRule);
		List<CriticalPair> critPairList = aggInfo.getConflictOverlappings(_firstRule, _secondRule);
		CriticalPair critPair = critPairList.get(0);
		System.out.println(critPairList.size());
		System.out.println(critPair.getOverlapping().getName());
		
		List<Mapping> mappingsOverToR1 = critPair.getMappingsOverlappingToRule1();
		List<Mapping> mappingsOverToR2 = critPair.getMappingsOverlappingToRule2();
		List<Mapping> mappingsR1ToR2 = critPair.getMappingsRule1ToRule2();
		Graph over = critPair.getOverlapping();
		
		changeToTGGGraph(over);
		
		Graph newOver = makeTGGGraph(over);
		
		_trafo.getInstances().add(over);
		_trafo.getInstances().add(newOver);
		_trafo.getRules().add(critPair.getRule1());
		_trafo.getRules().add(critPair.getRule2());
		
		System.out.println("Checking "+_firstRule.getName()+" with "+_secondRule.getName());
		super.execute();
	}
	
	private Graph makeTGGGraph(Graph over) {
		Graph newOver = HenshinFactory.eINSTANCE.createGraph();
		newOver.setName("Neu: "+over.getName());
		
		GraphLayout gL = TGGFactory.eINSTANCE.createGraphLayout();
		gL.setGraph(newOver);
		
		List<Mapping> oldToNewNodes = new ArrayList<Mapping>();
		
		for (Node n : over.getNodes()) {
			Node newNode = HenshinFactory.eINSTANCE.createNode();
			newNode.setName(n.getName());
			newNode.setType(n.getType());
			
			NodeLayout nL = TGGFactory.eINSTANCE.createNodeLayout();
			nL.setNode(newNode);
			
			newOver.getNodes().add(newNode);
			newNode.setGraph(newOver);
			layoutSystem.getNodelayouts().add(nL);
			
			Mapping m = HenshinFactory.eINSTANCE.createMapping();
			m.setOrigin(n);
			m.setImage(newNode);
			oldToNewNodes.add(m);
		}
		
		for (Edge e : over.getEdges()) {
			Edge newEdge = HenshinFactory.eINSTANCE.createEdge();
			newEdge.setType(e.getType());
			
			EdgeLayout eL = TGGFactory.eINSTANCE.createEdgeLayout();
			eL.setRhsedge(newEdge);
			
			for (Mapping m : oldToNewNodes) {
				if (m.getOrigin().getIncoming().contains(e)) {
					m.getImage().getIncoming().add(newEdge);
					newEdge.setTarget(m.getImage());
				}
				if (m.getOrigin().getOutgoing().contains(e)) {
					m.getImage().getOutgoing().add(newEdge);
					newEdge.setSource(m.getImage());
				}
			}
			
			newEdge.setGraph(newOver);
			newOver.getEdges().add(newEdge);
			layoutSystem.getEdgelayouts().add(eL);
			
		}
		
		layoutSystem.getGraphlayouts().add(gL);
		return newOver;
	}
	
	private void changeToTGGGraph(Graph graph) {
		//create NodeLayouts
		for (Node n : graph.getNodes()) {
			System.out.println("Node: "+n.getName()+" : "+n.getType().getName());
			NodeLayout nL = TGGFactory.eINSTANCE.createNodeLayout();
			nL.setNode(n);
			layoutSystem.getNodelayouts().add(nL);			
		}
		
		//create EdgeLayout
		for (Edge e : graph.getEdges()) {
			System.out.println("Edge: "+e.getType().getName());
			EdgeLayout eL = TGGFactory.eINSTANCE.createEdgeLayout();
			eL.setRhsedge(e);
			layoutSystem.getEdgelayouts().add(eL);
		}
				
		GraphLayout gL = TGGFactory.eINSTANCE.createGraphLayout();
		gL.setGraph(graph);
		layoutSystem.getGraphlayouts().add(gL);
		//set References ?? 
		
	}
}
