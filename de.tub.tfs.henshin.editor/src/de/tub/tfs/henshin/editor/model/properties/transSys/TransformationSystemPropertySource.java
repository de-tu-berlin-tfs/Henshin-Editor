/**
 * 
 */
package de.tub.tfs.henshin.editor.model.properties.transSys;

import java.util.ArrayList;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.PriorityUnit;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.ui.views.properties.IPropertyDescriptor;
import org.eclipse.ui.views.properties.PropertyDescriptor;
import org.eclipse.ui.views.properties.TextPropertyDescriptor;

import de.tub.tfs.henshin.editor.interfaces.Messages;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.muvitor.properties.AbstractPropertySource;

/**
 * The Class TransformationSystemPropertySource.
 * 
 * @author Johann
 */
public class TransformationSystemPropertySource extends
		AbstractPropertySource<Module> {

	/**
	 * Konstruktor erhält Transformationssystem.
	 * 
	 * @param model
	 *            TransformationsSystem
	 */
	public TransformationSystemPropertySource(Module model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.properties.AbstractPropertySource#createPropertyDescriptors()
	 */
	@Override
	protected IPropertyDescriptor[] createPropertyDescriptors() {
		final ArrayList<IPropertyDescriptor> descriptorList = new ArrayList<IPropertyDescriptor>();
		int index = 0;
		TextPropertyDescriptor nameDescriptor = new TextPropertyDescriptor(index++,
				Messages.PROPERTY_NAME);
		nameDescriptor.setValidator(new NameEditValidator(getModel(),
				HenshinPackage.MODULE, getModel(), true));
		descriptorList.add(nameDescriptor);
		descriptorList.add(new TextPropertyDescriptor(index++, Messages.PROPERTY_DESCRIPTION));
		for (int i = 0; i < getModel().getImports().size(); i++) {
			descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_METAMODEL + (i + 1)));
		}
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_GRAPHS));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_RULES));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_CONDITIONAL_UNITS));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_INDEPENDENT_UNITS));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_LOOP_UNITS));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_PRIORITY_UNITS));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_SEQUENTIAL_UNITS));
		descriptorList.add(new PropertyDescriptor(index++, Messages.PROPERTY_NUMBER_OF_FLOW_DIAGRAMS));
		return descriptorList.toArray(new IPropertyDescriptor[] {});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#getPropertyValue(java
	 * .lang.Object)
	 */
	@Override
	public Object getPropertyValue(Object id) {
		if (id instanceof Integer) {
			switch ((Integer) id) {
			case 0:
				return getModel().getName();
			case 1:
				if (getModel().getDescription() == null)
					return new String();
				else
					return getModel().getDescription();
			}
			if (((Integer) id) - 2 < getModel().getImports().size()) {
				return getModel().getImports().get(((Integer) id) - 2)
						.getName();
			}
			
			int numberOfConditionalUnit = 0;
			int numberOfIndependentUnit = 0;
			int numberOfLoopUnit = 0;
			int numberOfPriorityUnit = 0;
			int numberOfSequentialUnit = 0;
			EList<Unit> transformationUnits = getModel().getUnits();
			for (Unit transformationUnit : transformationUnits) {
				if (transformationUnit instanceof ConditionalUnit) {
					numberOfConditionalUnit++;
				}
				if (transformationUnit instanceof IndependentUnit) {
					numberOfIndependentUnit++;
				}
				if (transformationUnit instanceof LoopUnit) {
					numberOfLoopUnit++;
				}
				if (transformationUnit instanceof PriorityUnit) {
					numberOfPriorityUnit++;
				}
				if (transformationUnit instanceof SequentialUnit) {
					numberOfSequentialUnit++;
				}
			}
			FlowControlSystem flowControlSystem = FlowControlUtil.INSTANCE.getFlowControlSystem((EObject) getModel());
			int numberOfFlowDiagram = flowControlSystem.getUnits().size();
			switch (((Integer) id) - getModel().getImports().size()) {
			case 2:
				return getModel().getInstances().size();
			case 3:
				return getModel().getUnits().size();
			case 4:
				return numberOfConditionalUnit;
			case 5:
				return numberOfIndependentUnit;
			case 6:
				return numberOfLoopUnit;
			case 7:
				return numberOfPriorityUnit;
			case 8:
				return numberOfSequentialUnit;
			case 9:
				return numberOfFlowDiagram;
			default:
				break;
			}

		}
		return null;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.ui.views.properties.IPropertySource#setPropertyValue(java
	 * .lang.Object, java.lang.Object)
	 */
	@Override
	public void setPropertyValue(Object id, Object value) {
		if (id instanceof Integer) {
			switch ((Integer) id) {
			case 0:
				getModel().setName((String) value);
				break;
			case 1:
				getModel().setDescription((String) value);
			}
		}

	}

}
