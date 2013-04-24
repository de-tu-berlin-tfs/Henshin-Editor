package de.tub.tfs.muvitor.commands;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.gef.commands.Command;

/**
 * Document SimpleDeleteEObjectCommand
 * 
 * @author Tony
 * 
 */
public class SimpleDeleteEObjectCommand extends Command {

    final private EStructuralFeature containingFeature;

    final private EObject model;

    final private EObject parent;

    public SimpleDeleteEObjectCommand(final EObject model,
	    final EObject parent, final EStructuralFeature feature) {
	this.model = model;
	this.parent = parent;
	this.containingFeature = feature;
    }

    public SimpleDeleteEObjectCommand(final EObject model) {
	this(model, model.eContainer(), model.eContainingFeature());
    }

    @Override
    public boolean canExecute() {
	return model != null && parent != null;
    }

    @Override
    public void execute() {
	// see EcoreUtil.delete
    	int idx = -1;
		if (parent.eGet(containingFeature) instanceof List && (idx = ((List<?>) parent.eGet(containingFeature)).indexOf(model)) != -1 )
			((List<?>) parent.eGet(containingFeature)).remove(idx);
//		else parent.eGet(containingFeature)
    }

    /* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		// TODO Auto-generated method stub
		return super.canUndo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		// see EcoreUtil.delete
		if (parent.eGet(containingFeature) instanceof List)
			((List<?>) parent.eGet(containingFeature)).remove(model);
	}

	@SuppressWarnings("unchecked")
	@Override
	public void undo() {
		if (parent.eGet(containingFeature) instanceof List)
			((List<Object>) parent.eGet(containingFeature)).add(model);
	}
}
