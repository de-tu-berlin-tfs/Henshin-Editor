package de.tub.tfs.henshin.tggeditor.editparts.graphical;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.draw2d.ChopboxAnchor;
import org.eclipse.draw2d.ConnectionAnchor;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.MouseEvent;
import org.eclipse.draw2d.MouseListener;
import org.eclipse.draw2d.geometry.Rectangle;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Formula;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.gef.ConnectionEditPart;
import org.eclipse.gef.EditPolicy;
import org.eclipse.gef.GraphicalEditPart;
import org.eclipse.gef.Request;
import org.eclipse.jface.viewers.ICellEditorValidator;

import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tgg.interpreter.util.RuleUtil;
//import de.tub.tfs.henshin.tgg.TNode;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeLayoutEditPolicy;
import de.tub.tfs.henshin.tggeditor.figures.NodeFigure;
import de.tub.tfs.henshin.tggeditor.util.GraphicalNodeUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart.IGraphicalDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart;

/**
 * The Class NodeEditPart.
 */
public class TNodeObjectEditPart extends AdapterGraphicalEditPart<TNode>
		implements org.eclipse.gef.NodeEditPart, IGraphicalDirectEditPart,
		MouseListener {

	/** The NAC or Rule mapping in NodeRuleEditPath */
	protected Mapping mapping; 
	
	/** Index to show mapping */
	protected int index = -1;
	
	/** The node, which is the model object */
	TNode node;
	
	/**
	 * Instantiates a new node edit part.
	 *
	 * @param model the model
	 */
	public TNodeObjectEditPart(TNode model) {
		super(model);
		node = (TNode) model;
		setNacMapping(model);
	}

	/**
	 * Iterates over all mappings and looks for mapping which have given model as image. If it's
	 * found it sets the mapping, registers it as adapter and refresh the index
	 * @param model the given model
	 */
	protected void setNacMapping(Node model) {
		if (getCastedModel().getGraph().eContainer() instanceof Formula) {
			Formula f = (Formula) getCastedModel().getGraph().eContainer();
			EList<Mapping> maps = ((NestedCondition)f).getMappings();
			for (Mapping m : maps) {
				if (m.getImage() == model) {
					this.mapping = m;
					registerAdapter(m);
					// Mapping numbers were different in RHS and NAC. So take the lhsnode,
					// not the original node (which is the rhsnode).
					//this.index = m.getOrigin().getGraph().getNodes().indexOf(m.getOrigin());
					Node rhsNode = RuleUtil.getRHSNode(m.getOrigin());
					this.index = rhsNode.getGraph().getNodes().indexOf(rhsNode);
					return;
				}
			}
		}
	}
	
	/**
	 * gets the current nac mapping
	 * @return current nac mapping
	 */
	public Mapping getNacMapping() {
		return this.mapping;
	}

	/**
	 * sets the nac mapping of model
	 * @param m the new nac mapping
	 */
	public void setNacMapping(Mapping m) {
		this.mapping = m;
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		if (!this.isActive())
			return;
		
		if (notification.getNotifier() instanceof Node) {
			int type = notification.getEventType();
			final Object newValue = notification.getNewValue();
			final Object oldValue = notification.getOldValue();				
			switch (type) {
				case Notification.SET:
				case Notification.UNSET:
					
					
					refreshVisuals();
					break;
				case Notification.ADD:
					if (newValue instanceof Mapping
							&& ((Mapping) newValue).getImage() == getModel()) {							
						this.mapping = (Mapping) newValue;
						if (this.index == -1) 
							this.index = this.mapping.getOrigin().getGraph().getNodes().indexOf(this.mapping.getOrigin());
						refreshFigureName();
						refreshVisuals();
						return;
					}
					break;
				case Notification.REMOVE:
					if (this.mapping == oldValue) {
						this.mapping = null;
						this.index = -1;
						refreshFigureName();
						refreshVisuals();
						return;
					}
					break;
			}

			final int featureId2 = notification.getFeatureID(HenshinPackage.class);
			switch (featureId2) {
			case HenshinPackage.NODE__NAME:
				updateValueDisplay("");
				refreshVisuals();
				break;
			case HenshinPackage.NODE__INCOMING:	
				refreshTargetConnections();
				refreshVisuals();
				break;				
			case HenshinPackage.NODE__OUTGOING:
				refreshSourceConnections();
				refreshVisuals();
				break;
			case HenshinPackage.NODE__TYPE:
				refreshVisuals();
				break;
			case HenshinPackage.NODE__ATTRIBUTES:
				refreshChildren();
				refreshVisuals();
				//((NodeFigure)getFigure()).updatePos();
			}
			
			if (notification.getNotifier() instanceof TNode) {
				final int featureId3 = notification
						.getFeatureID(TggPackage.class);
				switch (featureId3) {
				case TggPackage.TNODE__COMPONENT:
					refreshBG();
					break;
				case TggPackage.TNODE__MARKER_TYPE:
					refreshMarker();
					break;
				}
			}
		}
		
		if (notification.getNotifier() instanceof Mapping) {
			int featureId = notification.getFeatureID(HenshinPackage.class);
			switch (featureId) {
			case HenshinPackage.MAPPING__IMAGE:
				int type = notification.getEventType();
				final Object newValue = notification.getNewValue();
				final Object oldValue = notification.getOldValue();				
				switch (type) {
					case Notification.ADD:
						if (newValue instanceof Mapping
								&& ((Mapping) newValue).getImage() == getModel()) {							
							this.mapping = (Mapping) newValue;
							refreshFigureName();
							refreshVisuals();
						}
					break;
					case Notification.REMOVE:
						if (this.mapping == oldValue) {
							this.mapping = null;
							this.index = -1;
							refreshFigureName();
							refreshVisuals();
						}
					break;
				}
			}
		}
		
		//System.out.println("node update: " + ((System.nanoTime() - s) / 1000000) + " ms.");
	}

	/**
	 * refreshes the name display on figure of this model
	 */
	protected void refreshFigureName() {
		this.setName(this.getName());
	}
	
	/**
	 * Sets the name in visual figure
	 *
	 * @param name the new name
	 */
	public void setName(String name) {
		if (figure != null && figure instanceof NodeFigure) {
			((NodeFigure) figure).setName(name);
		}
	}
	
	/**
	 * Gets the name of model
	 * @return node name
	 */
	public String getName() {
		if (this.index == -1) 
			return getCastedModel().getName()+":"+(getCastedModel().getType() == null ? "null" : getCastedModel().getType().getName());
		else
			return "[" + this.index + "] " + getCastedModel().getName()+":"+(getCastedModel().getType() == null ? "null" : getCastedModel().getType().getName());
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {
		figure = new NodeFigure(getCastedModel());
		return figure;
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.NODE__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	public void mousePressed(MouseEvent me) {
	}

	@Override
	public void mouseReleased(MouseEvent me) {
	}

	@Override
	public void mouseDoubleClicked(MouseEvent me) {
	}

	@Override
	public Rectangle getValueLabelTextBounds() {
		return ((NodeFigure)getFigure()).getValueLabelTextBounds();
	}

	@Override
	public void updateValueDisplay(String value) {
		this.refreshFigureName();
	}
	
	@Override
	protected List<Edge> getModelSourceConnections() {
		return getCastedModel().getOutgoing();
	}
	
	@Override
	protected List<Edge> getModelTargetConnections() {
		return getCastedModel().getIncoming();
	}
	
	

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
		list.addAll(getCastedModel().getAttributes());
		return list;
	}
	
	@Override
	public IFigure getContentPane() {
		return ((NodeFigure)getFigure()).getAttributePane();
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(
			ConnectionEditPart connection) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getSourceConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	public ConnectionAnchor getTargetConnectionAnchor(Request request) {
		return new ChopboxAnchor(getFigure());
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.LAYOUT_ROLE, new NodeLayoutEditPolicy());
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new NodeComponentEditPolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new NodeGraphicalEditPolicy());
	}


	@Override
	protected void refreshVisuals() {
		if (node==null) return;
		NodeFigure figure = this.getNodeFigure();
		if(figure == null) return;
		final Rectangle bounds = new Rectangle(node.getX(),
				node.getY(),-1,-1);
		
		if (getFigure().getParent() != null){
			((GraphicalEditPart) getParent()).setLayoutConstraint(this,figure, bounds);
		}
		
		this.refreshFigureName();
		figure.updateMarker(); 
		figure.repaint();
		super.refreshVisuals();
	}

	
	protected void refreshBG() {
		if (node==null) return;
		NodeFigure figure = this.getNodeFigure();
		if(figure == null) return;
		figure.updateBG();
	}

	protected void refreshMarker() {
		if (node==null) return;
		NodeFigure figure = this.getNodeFigure();
		if(figure == null) return;
		figure.updateMarker();
	}

	
	/**
	 * gets the figure of model as NodeFigure
	 * @return the casted figure
	 */
	protected NodeFigure getNodeFigure(){
		return (NodeFigure) getFigure();
	}
	
	/**
	 * sets the number for mappings
	 * @param index the number for mappings
	 */
	public void setNumberForMapping(int index){
		this.index = index;
	}
	
	@Override
	public void setSelected(int selectionMode) {
		super.setSelected(selectionMode);
		// change color on node figure
		((NodeFigure)getFigure()).setSelectionMode(selectionMode);
	}
	
}
