package de.tub.tfs.henshin.tggeditor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.requests.CreationFactory;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TEdge;
import de.tub.tfs.henshin.tgg.TGGRule;
import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleGraph;


public class TGGModelCreationFactory implements CreationFactory {
	
	private Class<?> clazz;
	private EClass eClass;
	
	public TGGModelCreationFactory (Class<?> clazz) {
		this.clazz = clazz;
	}

	public TGGModelCreationFactory(Class<Node> clazz, EClass eClass) {
		this.clazz = clazz;
		this.eClass = eClass;
	}

	@Override
	public Object getNewObject() {
		if (clazz == TNode.class) {
			TNode node = TggFactory.eINSTANCE.createTNode();
			if (eClass != null) {
				node.setType(eClass);
			}
			return node;
		}
		if (clazz == TAttribute.class) {
			TAttribute node = TggFactory.eINSTANCE.createTAttribute();
		
			return node;
		}
		if (clazz == TEdge.class) {
			TEdge edge = TggFactory.eINSTANCE.createTEdge();
			
			return edge;
		}
		if (clazz == Node.class) {
			Node node = TggFactory.eINSTANCE.createTNode();
			if (eClass != null) {
				node.setType(eClass);
			}
			return node;
		}
		if (clazz == Attribute.class) {
			return TggFactory.eINSTANCE.createTAttribute();
		}
		if (clazz == Edge.class) {
			return TggFactory.eINSTANCE.createTEdge();
		}
		if (clazz == TGGRule.class) {
			TGGRule node = TggFactory.eINSTANCE.createTGGRule();
			
			return node;
		}
		if (clazz == TripleGraph.class) {
			return TggFactory.eINSTANCE.createTripleGraph();
		}
		if (clazz == Graph.class) {
			return TggFactory.eINSTANCE.createTripleGraph();
		}
		if (clazz == AttributeCondition.class) {
			return HenshinFactory.eINSTANCE.createAttributeCondition();
		}
		if (clazz == Rule.class) {
			return TggFactory.eINSTANCE.createTGGRule();
		}
		if (clazz == Mapping.class) {
			return HenshinFactory.eINSTANCE.createMapping();
		}
		if (clazz == Not.class){
			return HenshinFactory.eINSTANCE.createNot();
		}
		if (clazz == NestedCondition.class){
			return HenshinFactory.eINSTANCE.createNestedCondition();
		}
		if (clazz == Parameter.class) {
			Parameter parameter = HenshinFactory.eINSTANCE.createParameter();
			return parameter;
		}
		if (clazz == CritPair.class) {
			CritPair critPair = TggFactory.eINSTANCE.createCritPair();
			return critPair;
		}
		return null;
	}

	@Override
	public Object getObjectType() {
		return clazz;
	}

}
