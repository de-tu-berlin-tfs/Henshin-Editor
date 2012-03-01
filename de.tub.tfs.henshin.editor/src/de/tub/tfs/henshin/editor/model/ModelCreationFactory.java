package de.tub.tfs.henshin.editor.model;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.gef.requests.CreationFactory;

/**
 * A factory for creating ModelCreation objects.
 */
public class ModelCreationFactory implements CreationFactory {

	/** The clazz. */
	private Class<?> clazz;

	/** The eclass. */
	private EClass eclass;

	/**
	 * Instantiates a new model creation factory.
	 * 
	 * @param clazz
	 *            the clazz
	 */
	public ModelCreationFactory(Class<?> clazz) {
		this.clazz = clazz;
	}

	/**
	 * Instantiates a new model creation factory.
	 * 
	 * @param clazz
	 *            the clazz
	 * @param eclass
	 *            the eclass
	 */
	public ModelCreationFactory(Class<?> clazz, EClass eclass) {
		this.clazz = clazz;
		this.eclass = eclass;
	}

	/**
	 * Factory Klasse liefert entsprechend der Instanzierung ein Objekt
	 * zur�ck.
	 * 
	 * @return liefert Objekt der Klasse clazz zur�ck
	 */
	@Override
	public Object getNewObject() {
		if (clazz == Node.class) {
			Node node = HenshinFactory.eINSTANCE.createNode();
			if (eclass != null) {
				node.setType(eclass);
			}
			return node;
		}
		if (clazz == Edge.class) {
			return HenshinFactory.eINSTANCE.createEdge();
		}
		if (clazz == Attribute.class) {
			return HenshinFactory.eINSTANCE.createAttribute();
		}
		if (clazz == Mapping.class) {
			return HenshinFactory.eINSTANCE.createMapping();
		}
		if (clazz == Rule.class) {
			return HenshinFactory.eINSTANCE.createRule();
		}
		if (clazz == SequentialUnit.class) {
			return HenshinFactory.eINSTANCE.createSequentialUnit();
		}
		if (clazz == PriorityUnit.class) {
			return HenshinFactory.eINSTANCE.createPriorityUnit();
		}
		if (clazz == IndependentUnit.class) {
			return HenshinFactory.eINSTANCE.createIndependentUnit();
		}
		if (clazz == ConditionalUnit.class) {
			return HenshinFactory.eINSTANCE.createConditionalUnit();
		}

		if (clazz == Parameter.class) {
			Parameter parameter = HenshinFactory.eINSTANCE.createParameter();
			return parameter;
		}
		if (clazz == ParameterMapping.class) {
			return HenshinFactory.eINSTANCE.createParameterMapping();
		}

		if (LoopUnit.class.equals(clazz)) {
			return HenshinFactory.eINSTANCE.createLoopUnit();
		}

		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.requests.CreationFactory#getObjectType()
	 */
	@Override
	public Object getObjectType() {
		return clazz;
	}

}
