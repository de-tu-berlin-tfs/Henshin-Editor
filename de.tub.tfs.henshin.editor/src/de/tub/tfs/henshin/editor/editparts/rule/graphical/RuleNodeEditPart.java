/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.rule.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Color;
import org.eclipse.ui.views.properties.IPropertySource;

import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeEditPart;
import de.tub.tfs.henshin.editor.editparts.graph.graphical.NodeXYLayoutEditPolicy;
import de.tub.tfs.henshin.editor.editparts.rule.NodeRuleComponentEditPolicy;
import de.tub.tfs.henshin.editor.model.properties.rule.RuleNodePropertySource;
import de.tub.tfs.henshin.editor.util.ColorUtil;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.validator.RuleNodeNameEditorValidator;

/**
 * A {@link EditPart edit part} for {@link Node nodes} contained in {@link Rule
 * rules}.
 * 
 * @author Johann
 */
public class RuleNodeEditPart extends NodeEditPart {

	/**
	 * Instantiates a new node rule edit part.
	 * 
	 * @param model
	 *            Node to edit
	 */
	public RuleNodeEditPart(Node model) {
		super(model);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		figure = super.createFigure();
		figure.setBackgroundColor(ColorUtil.int2Color(getLayoutModel()
				.getColor()));
		setName(getName());
		return createMappingFigure(figure);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * henshineditor.editparts.graphs.NodeEditPart#notifyChanged(org.eclipse
	 * .emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		super.notifyChanged(notification);

		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId) {
		case HenshinPackage.MAPPING__IMAGE:
		case HenshinPackage.MAPPING__ORIGIN:
			int type = notification.getEventType();
			final Object newValue = notification.getNewValue();
			final Object oldValue = notification.getOldValue();
			Color layoutColor = ColorUtil
					.int2Color(getLayoutModel().getColor());

			switch (type) {
			case Notification.ADD:
			case Notification.ADD_MANY:
				if (newValue instanceof Mapping) {
					setName(getName());
				}
				break;
			case Notification.REMOVE:
			case Notification.REMOVE_MANY:
				if (oldValue instanceof Mapping) {
					setName(getName());
				}
				break;
			}
			break;

		case HenshinPackage.NODE:
			type = notification.getEventType();
			switch (type) {
			case Notification.SET:
				setName(getName());
			}
		}
		refresh();
	}

	/**
	 * Creates the mapping figure.
	 * 
	 * @param figure
	 *            the figure
	 * @return the IFigure
	 */
	private IFigure createMappingFigure(IFigure figure) {
		final Graph parentModel = (Graph) getCastedModel().eContainer();
		if (parentModel != null) {
			
				setName(getName());
				return figure;
		
		}
		return figure;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeEditPart#getName()
	 */
	@Override
	public String getName() {
		final int colorInt = getLayoutModel().getColor();
		final Graph g = getCastedModel().getGraph();
		String[] name = super.getName().split(":");

		if (g != null) {
			final Rule rule = g.getRule();

//			if (rule != null) {
//				if (rule.getParameterByName(name[0]) != null) {
//					name[0] = "${" + name[0] + "}";
//				}
//			}
		} else {
			return "";
		}

		final int mappingNumber = HenshinLayoutUtil.INSTANCE.getMappingNumber(g.getRule(),this.getCastedModel());
		if (mappingNumber != -1) {
			name[0] = "[" + mappingNumber + "]" + name[0];
		}
		

		return name[0] + ":" + name[1];
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see henshineditor.editparts.graphs.NodeEditPart#createEditPolicies()
	 */
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.NODE_ROLE,
				new RuleNodeGraphicalEditPartPolicy());

		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new NodeRuleComponentEditPolicy());

		if (getParent() instanceof LhsRhsEditPart) {
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new NodeRuleXYLayoutEditPolicy());
		} else {
			installEditPolicy(EditPolicy.LAYOUT_ROLE,
					new NodeXYLayoutEditPolicy());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.directedit.IDirectEditPart#getDirectEditValidator()
	 */
	@Override
	public ICellEditorValidator getDirectEditValidator() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(this.getCastedModel())){
			return new ICellEditorValidator() {
				
				@Override
				public String isValid(Object value) {
					// TODO Auto-generated method stub
					return "Editing not allowed!";
				}
			};
		}
		
		return new RuleNodeNameEditorValidator(getCastedModel());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.gef.editparts.AdapterTreeEditPart#createPropertySource()
	 */
	@Override
	protected IPropertySource createPropertySource() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(this.getCastedModel())){
			return null;
		}
		return new RuleNodePropertySource(getCastedModel());
	}
	
	@Override
	protected void performOpen() {
		// TODO Auto-generated method stub
		super.performOpen();
	}

	@Override
	public Rectangle getValueLabelTextBounds() {
		if (HenshinLayoutUtil.INSTANCE.hasOriginInKernelRule(this.getCastedModel())){
			return new Rectangle(super.getValueLabelTextBounds().x,super.getValueLabelTextBounds().y,0,0);
		}
		return super.getValueLabelTextBounds();
	}
}
