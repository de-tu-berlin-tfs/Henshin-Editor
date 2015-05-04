package de.tub.tfs.henshin.tggeditor.editparts.constraint;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.EditPolicy;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.NodeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.NodeGraphicalEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.constraint.NodeLayoutEditPolicy;

public class NodeEditPart extends TNodeObjectEditPart {

	public NodeEditPart(TNode model) {
		super((Object)model);
		setMappingNumber();
	}

	private void setMappingNumber() {
		if (getCastedModel().getGraph().eContainer() instanceof NestedCondition) {
			for (Mapping m : ((NestedCondition)getCastedModel().getGraph().eContainer()).getMappings()) {
				if (m.getImage() == getCastedModel()) {
					this.mapping = m;
					this.index = getCastedModel().getGraph().getNodes().indexOf(getCastedModel());
					return;
				}
			}
		} else {
			for (Mapping m : ((NestedCondition)getCastedModel().getGraph().getFormula()).getMappings()) {
				if (m.getOrigin() == getCastedModel()) {
					this.mapping = m;
					this.index = m.getImage().getGraph().getNodes().indexOf(m.getImage());
					return;
				}
			}
		}
	}
	
	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new NodeLayoutEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new NodeGraphicalEditPolicy());
	}
		
	@Override
	protected void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof Node) {
			int type = notification.getEventType();
			final Object newValue = notification.getNewValue();
			switch (type) {
				case Notification.ADD:
					if (newValue instanceof Mapping
							&& ((Mapping) newValue).getOrigin() == getModel()) {							
						this.mapping = (Mapping) newValue;
						if (this.index == -1) 
							this.index = this.mapping.getImage().getGraph().getNodes().indexOf(this.mapping.getImage());
						refreshFigureName();
						refreshVisuals();
						return;
					}
					if (newValue instanceof Mapping
							&& ((Mapping) newValue).getImage() == getModel()) {							
						this.mapping = (Mapping) newValue;
						if (this.index == -1) 
							this.index = this.mapping.getImage().getGraph().getNodes().indexOf(this.mapping.getImage());
						refreshFigureName();
						refreshVisuals();
						return;
					}
					break;
			}
		}
		super.notifyChanged(notification);
	}
}
