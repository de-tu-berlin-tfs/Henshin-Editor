package de.tub.tfs.henshin.tggeditor;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.requests.CreationFactory;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TGGFactory;


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
		if (clazz == Node.class) {
			Node node = HenshinFactory.eINSTANCE.createNode();
			if (eClass != null) {
				node.setType(eClass);
			}
			return node;
		}
		if (clazz == Node.class) {
			return HenshinFactory.eINSTANCE.createNode();
		}
		if (clazz == Attribute.class) {
			return HenshinFactory.eINSTANCE.createAttribute();
		}
		if (clazz == Edge.class) {
			return HenshinFactory.eINSTANCE.createEdge();
		}
		if (clazz == Rule.class) {
			return HenshinFactory.eINSTANCE.createRule();
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
			CritPair critPair = TGGFactory.eINSTANCE.createCritPair();
			return critPair;
		}
		return null;
	}

	@Override
	public Object getObjectType() {
		return clazz;
	}

}
