/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.transformation_unit.tree;

import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.Request;
import org.eclipse.gef.RequestConstants;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.internal.ConditionalUnitPart;
import de.tub.tfs.henshin.editor.model.properties.transformation_unit.TransformationUnitPartPropertySource;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * The Class ConditionalUnitPartTreeEditPart.
 */
public class ConditionalUnitPartTreeEditPart extends
		AdapterTreeEditPart<ConditionalUnitPart> {

	/**
	 * Instantiates a new conditional unit part tree edit part.
	 * 
	 * @param model
	 *            the model
	 */
	public ConditionalUnitPartTreeEditPart(ConditionalUnitPart model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#getText()
	 */
	@Override
	protected String getText() {
		return getCastedModel().getName();
	}

	@Override
	public boolean understandsRequest(Request req) {
		return super.understandsRequest(req)
				&& RequestConstants.REQ_DIRECT_EDIT.equals(req);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@SuppressWarnings("rawtypes")
	@Override
	protected List getModelChildren() {
		List<EObject> list = new Vector<EObject>();
		Object object = getCastedModel().getModel().eGet(
				getCastedModel().getFeature());
		if (object != null) {
			list.add((EObject) object);
		}
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart#getGraphicalViewModel
	 * ()
	 */
	@Override
	protected EObject getGraphicalViewModel() {
		return getCastedModel().getModel();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new TransformationUnitPartPropertySource(getCastedModel());
	}
}
