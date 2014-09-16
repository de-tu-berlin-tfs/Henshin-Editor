/**
 * SimpleSetEFeatureCommand.java
 *
 * Created 18.12.2011 - 13:09:56
 */
package de.tub.tfs.henshin.editor.commands;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.commands.Command;

/**
 * An simple {@link Command command} to set an {@link EStructuralFeature feature
 * value} of an {@link EObject model object} by calling the
 * {@link EObject#eSet(EStructuralFeature, Object) generic setter}.
 * 
 * @author nam
 * 
 * @param <T>
 *            the type of the model object, must be an {@link EObject}.
 * @param <K>
 *            the type of the value object to be set, must be an {@link EObject}
 *            .
 * 
 */
public class SimpleSetEFeatureCommand<T extends EObject, K> extends Command {
	/**
	 * The model object with the {@link EStructuralFeature} to be set.
	 */
	private T model;

	/**
	 * The new feature value to set.
	 */
	private K value;

	/**
	 * The old feature value.
	 */
	private Object oldValue;

	/**
	 * The {@link EStructuralFeature} to be set.
	 */
	private EStructuralFeature feature;

	/**
	 * Constructs an {@link SimpleSetEFeatureCommand} with the given parameters.
	 * 
	 * @param model
	 *            the model object.
	 * @param value
	 *            the value to be set, can be <code>null</code>.
	 * @param feature
	 *            the {@link EStructuralFeature} to be set.
	 */
	public SimpleSetEFeatureCommand(T model, K value, EStructuralFeature feature) {
		super();

		this.model = model;
		this.value = value;
		this.feature = feature;

		if (model != null) {
			if (feature != null) {
				oldValue = model.eGet(feature);
			}
		}
	}

	/**
	 * @param model
	 * @param value
	 * @param featureId
	 */
	public SimpleSetEFeatureCommand(T model, K value, int featureId) {
		this(model, value, model.eClass().getEStructuralFeature(featureId));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return model != null && feature != null
				&& model.eClass().getEAllStructuralFeatures().contains(feature);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		model.eSet(feature, value);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		model.eSet(feature, oldValue);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return canExecute();
	}
}
