package de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical;

import java.util.HashMap;
import java.util.List;
import java.util.Vector;

import org.eclipse.draw2d.ConnectionLayer;
import org.eclipse.draw2d.FanRouter;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.LineBorder;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.ParameterMapping;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.LayerConstants;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.editparts.transformation_unit.graphical.parameter.ParameterEditPart;
import de.tub.tfs.henshin.editor.figure.transformation_unit.TransformationUnitFigure;
import de.tub.tfs.henshin.editor.model.properties.transformation_unit.TransformationUnitPropertySource;
import de.tub.tfs.henshin.editor.ui.transformation_unit.TransUnitPage;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.TransformationUnitUtil;
import de.tub.tfs.henshin.editor.util.validator.NameEditValidator;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;
import de.tub.tfs.muvitor.ui.utils.SWTResourceManager;

/**
 * The Class TransformationUnitEditPart.
 * 
 * @param <T>
 *            the generic type
 */
public abstract class TransformationUnitEditPart<T extends TransformationUnit>
		extends AdapterGraphicalEditPart<T> implements IGraphicalDirectEditPart {

	/** The trans unit page. */
	protected final TransUnitPage transUnitPage;

	/** The parameter2 edit part. */
	private HashMap<Parameter, EditPart> parameter2EditPart;

	/** The sub edit part. */
	private TransformationUnitEditPart<?> subEditPart;

	/** The parameter2 sub edit part. */
	private HashMap<Parameter, EditPart> parameter2SubEditPart;

	/**
	 * Instantiates a new transformation unit edit part.
	 * 
	 * @param transUnitPage
	 *            the trans unit page
	 * @param model
	 *            the model
	 */
	public TransformationUnitEditPart(TransUnitPage transUnitPage, T model) {
		super(model);
		this.transUnitPage = transUnitPage;
		parameter2EditPart = new HashMap<Parameter, EditPart>();
		parameter2SubEditPart = new HashMap<Parameter, EditPart>();
		transUnitPage.getUnit2EditPart().put(getCastedModel(), this);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		TransformationUnitFigure figure = new TransformationUnitFigure(this,
				getCastedModel().getName(), 450, getImage());
		ConnectionLayer cLayer = (ConnectionLayer) getLayer(LayerConstants.CONNECTION_LAYER);
		cLayer.setAntialias(SWT.ON);
		FanRouter fRouter = new FanRouter();
		fRouter.setSeparation(20);
		cLayer.setConnectionRouter(fRouter);

		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE,
				new TransformationUnitXYLayoutEditPolicy());

	}

	/**
	 * Update figure.
	 */
	protected void updateFigure() {
		IFigure figure = getFigure();
		if (getSelected() == 0) { // not selected
			figure.setBorder(new LineBorder(1));
			figure.setForegroundColor(SWTResourceManager.getColor(0, 0, 0));
		} else { // selected
			figure.setBorder(new LineBorder(2));
			figure.setForegroundColor(SWTResourceManager.getColor(150, 0, 0));
		}

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#refreshVisuals()
	 */
	@Override
	protected void refreshVisuals() {
		updateFigure();
		getFigure().repaint();
		super.refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		updateFigure();
		super.activate();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.editparts.AbstractGraphicalEditPart#fireSelectionChanged
	 * ()
	 */
	@Override
	protected void fireSelectionChanged() {
		super.fireSelectionChanged();
		refreshVisuals();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * muvitorkit.gef.editparts.AdapterGraphicalEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.TRANSFORMATION_UNIT__PARAMETER_MAPPINGS:
			switch (notification.getEventType()) {
			case Notification.ADD:
			case Notification.ADD_MANY:
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				if (subEditPart != null) {
					showParameterMapping(subEditPart);
				}
				break;
			}
			return;
		}
		switch (notification.getEventType()) {
		case Notification.ADD:
		case Notification.ADD_MANY:
		case Notification.REMOVE:
		case Notification.REMOVE_MANY:
		case Notification.MOVE:
		case Notification.SET:
			refreshChildren();
			repaintChildren();
			refreshVisuals();
			break;
		}
	}

	/**
	 * Repaint children.
	 */
	private void repaintChildren() {
		for (Object e : getChildren()) {
			if (e instanceof NodeEditPart) {
				((NodeEditPart) e).getFigure().repaint();
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		transUnitPage.backView(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractTreeEditPart#getImage()
	 */
	/**
	 * Gets the image.
	 * 
	 * @return the image
	 */
	protected abstract Image getImage();

	/**
	 * Gets the parameter2 edit part.
	 * 
	 * @return the parameter2 edit part
	 */
	public synchronized HashMap<Parameter, EditPart> getParameter2EditPart() {
		return parameter2EditPart;
	}

	/**
	 * Show parameter mapping.
	 * 
	 * @param subEditPart
	 *            the sub edit part
	 */
	public void showParameterMapping(TransformationUnitEditPart<?> subEditPart) {
		hideParameterMapping();
		if (this.subEditPart != subEditPart) {
			parameter2SubEditPart = subEditPart.getParameter2EditPart();
			this.subEditPart = subEditPart;
		}
		for (int i = 0, n = getCastedModel().getParameterMappings().size(); i < n; i++) {
			ParameterMapping parameterMapping = getCastedModel()
					.getParameterMappings().get(i);
			if (parameter2SubEditPart.containsKey(parameterMapping.getSource())) {
				Color color = ColorUtil.getColor(i);
				((ParameterEditPart) parameter2EditPart.get(parameterMapping
						.getTarget())).setColor(color);
				((ParameterEditPart) parameter2SubEditPart.get(parameterMapping
						.getSource())).setColor(color);
			} else {
				if (parameter2SubEditPart.containsKey(parameterMapping
						.getTarget())) {
					Color color = ColorUtil.getColor(i);
					((ParameterEditPart) parameter2EditPart
							.get(parameterMapping.getSource())).setColor(color);
					((ParameterEditPart) parameter2SubEditPart
							.get(parameterMapping.getTarget())).setColor(color);
				}
			}

		}
	}

	/**
	 * Hide parameter mapping.
	 */
	public void hideParameterMapping() {
		for (EditPart editPart : parameter2EditPart.values()) {
			((ParameterEditPart) editPart).setColor(null);
		}
		if (subEditPart != null) {
			for (EditPart editPart : parameter2SubEditPart.values()) {
				((ParameterEditPart) editPart).setColor(null);
			}
			subEditPart = null;
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractEditPart#getModelChildren()
	 */
	@Override
	protected List<?> getModelChildren() {
		Vector<EObject> list = new Vector<EObject>(
				TransformationUnitUtil.getSubUnits(getCastedModel()));
		list.addAll(getCastedModel().getParameters());
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * getValueLabelTextBounds()
	 */
	@Override
	public Rectangle getValueLabelTextBounds() {
		return ((TransformationUnitFigure) getFigure())
				.getNameLabelTextBounds();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart#
	 * updateValueDisplay(java.lang.String)
	 */
	@Override
	public void updateValueDisplay(String value) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditFeatureID()
	 */
	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.TRANSFORMATION_UNIT__NAME;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return new NameEditValidator(
				HenshinUtil.INSTANCE.getTransformationSystem(getCastedModel()),
				HenshinPackage.TRANSFORMATION_SYSTEM__TRANSFORMATION_UNITS,
				getCastedModel(), true);
	}

	/**
	 * Sets the model activated.
	 * 
	 * @param activated
	 *            the activated
	 * @return true, if successful
	 */
	public boolean setModelActivated(boolean activated) {
		transUnitPage.setActivated(getCastedModel(), activated);
		return getCastedModel().isActivated();
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		return new TransformationUnitPropertySource(getCastedModel());
	}

}
