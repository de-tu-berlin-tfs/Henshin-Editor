package de.tub.tfs.henshin.tggeditor.editparts.rule;


import java.util.ArrayList;

import org.eclipse.draw2d.ColorConstants;
import org.eclipse.draw2d.Figure;
import org.eclipse.draw2d.FlowLayout;
import org.eclipse.draw2d.IFigure;
import org.eclipse.draw2d.Label;
import org.eclipse.draw2d.MarginBorder;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Device;
import org.eclipse.swt.graphics.Font;

import de.tub.tfs.henshin.tgg.TAttribute;
import de.tub.tfs.henshin.tgg.TggPackage;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.AttributeEditPart;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.AttributeGraphicalEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.graphical.NodeGraphicalEditPolicy;
import de.tub.tfs.henshin.tggeditor.editpolicies.rule.RuleAttributeComponentEditPolicy;
import de.tub.tfs.henshin.tggeditor.util.AttributeUtil;
import de.tub.tfs.henshin.tggeditor.util.RuleUtil;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * The EdgeEditPart class of rules.
 */
public class RuleAttributeEditPart extends AttributeEditPart {

//	/** The text. */
//	private Label text = new Label("");
	


	private static final Color COLOR_MARKER_BG = new Color(null,232,250,238);

	/** The label container. */
	protected Figure labelContainer;

	/** The marker label for elements to be created */
	protected Label marker;
	
	/** The marker label elements to be translated */
	protected Label translatedMarker;

	/** The attribute of the RHS */
	protected Attribute rhsAttribute;

	
	/** The Constant Display. */
	static final Device Display = null;

	private static final Font TEXT_FONT = new Font(Display, "SansSerif", 8, SWT.NORMAL);

	private static final Font MARKER_FONT = new Font(Display, "SansSerif", 8, SWT.BOLD);

	private static final Font TRANSLATED_MARKER_FONT = new Font(Display, "SansSerif", 8, SWT.BOLD);

	/* (non-Javadoc)
	 * @see tggeditor.editparts.graphical.AttributeEditPart#createFigure()
	 */
	@Override
	protected IFigure createFigure() {

		labelContainer = new Figure();
		labelContainer.setLayoutManager(new FlowLayout());
		labelContainer.setBorder(new MarginBorder(0, 1, 0 , 1));
		setName();
		text.setFont(TEXT_FONT);
		labelContainer.add(text,0);
		return labelContainer;

	}


	
	protected Color markerBG_Color= COLOR_MARKER_BG;

	/**
	 * Instantiates a new rule edge edit part.
	 *
	 * @param model the model
	 */
	public RuleAttributeEditPart(Attribute model) {
		super(model);
		
		rhsAttribute = model;
		AttributeUtil.refreshIsMarked(rhsAttribute);

		marker = new Label(RuleUtil.NEW);
		marker.setTextAlignment(SWT.CENTER);
		marker.setOpaque(true);
		marker.setForegroundColor(ColorConstants.darkGreen);
		marker.setFont(MARKER_FONT);
		marker.setVisible(true);
		
		translatedMarker = new Label(RuleUtil.Translated);
		translatedMarker.setTextAlignment(SWT.CENTER);
		translatedMarker.setOpaque(true);
		translatedMarker.setForegroundColor(ColorConstants.blue);
		translatedMarker.setFont(TRANSLATED_MARKER_FONT);
		translatedMarker.setVisible(true);

		
		cleanUpRule();
		
		

//		layoutModel = AttributeUtil.getAttributeLayout(model);
//		if (layoutModel != null)
//			registerAdapter(layoutModel);
//		if (model.getNode() != null)
//			registerAdapter(NodeUtil.getNodeLayout(model.getNode()));
	}
	
	/* (non-Javadoc)
	 * @see muvitorkit.gef.editparts.AdapterGraphicalEditPart#performDirectEdit()
	 */
	@Override
	protected void performDirectEdit() {
		super.performDirectEdit();
	}

	private void cleanUpRule() {
		// remove attribute duplicates in LHS
		
		ArrayList<Attribute> lhsAttributesList = RuleUtil
				.getAllLHSAttributes(rhsAttribute);

		// remove duplicates
		while (lhsAttributesList.size() > 1) {
			Attribute lhsAttribute = lhsAttributesList.get(0);
			lhsAttributesList.remove(0);
			SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(
					lhsAttribute);
			cmd.execute();
		}			
		
			
		
		// remove lhs attribute, if rule creates the attribute
		if( ((TAttribute) rhsAttribute).getMarkerType()!=null
				&& ((TAttribute) rhsAttribute).getMarkerType().equals(RuleUtil.NEW)){
			if (lhsAttributesList.size()==1) 
			{
				Attribute lhsAttribute = lhsAttributesList.get(0);
				lhsAttributesList.remove(0);
				SimpleDeleteEObjectCommand cmd = new SimpleDeleteEObjectCommand(lhsAttribute);
				cmd.execute();					
			}
		}
		
		// update lhs attribute value, if it is inconsistent to the rhs attribute value
		updateLHSAttribute();
		
	}

	@Override
	public void notifyChanged(Notification notification) {
		if (notification.getNotifier() instanceof Attribute) {
			int featureId = notification.getFeatureID(HenshinPackage.class);		
			switch (featureId) {
			case -1:
				return;
			case HenshinPackage.ATTRIBUTE__TYPE:
			case HenshinPackage.ATTRIBUTE__VALUE:
				text.setText(getName());
				updateLHSAttribute();
			case TggPackage.TATTRIBUTE__MARKER_TYPE:
			// case HenshinPackage.ATTRIBUTE__MARKER_TYPE: // is always triggered by above case
				refreshVisuals();
				return;
			}
		}		
//		if (notification.getNotifier() instanceof AttributeLayout) {
//			int featureId = notification.getFeatureID(TggPackage.class);
//			switch (featureId) {
//			case TggPackage.ATTRIBUTE_LAYOUT__NEW:
//				layoutModel= AttributeUtil.getAttributeLayout(getCastedModel());
//				refreshVisuals();
//				return;
//			}
//		}
//		if (notification.getNotifier() instanceof NodeLayout) {
//			Node n = ((NodeLayout)notification.getNotifier()).getNode();
//			if (n == getCastedModel().getNode()) {
//				int featureId = notification.getFeatureID(TggPackage.class);
//				switch (featureId) {
//				case TggPackage.NODE_LAYOUT__NEW:
//					refreshVisuals();
//					return;
//				}
//			}
//		}
	}

	private void updateLHSAttribute() {
		// updates the lhs attribute value if the lhs attribute exists and its value differs from the rhs attribute value
		// if attribute is not created by the rule, then update the corresponding value in LHS as well
		if (((TAttribute) rhsAttribute).getMarkerType() == null 
				|| !((TAttribute) rhsAttribute).getMarkerType().equals(RuleUtil.NEW)) {
			Attribute lhsAttribute = RuleUtil.getLHSAttribute(rhsAttribute);
			if (lhsAttribute!=null
					// lhs attribute has a different value as the rhs attribute
					&& !(lhsAttribute.getValue().equals(rhsAttribute.getValue()))) {
				// update lhs attribute value to current value of rhs attribute
				lhsAttribute.setValue(rhsAttribute.getValue());			
			}
		}
		
	}

	@Override
	protected void refreshVisuals() {
		super.refreshVisuals();
		updateMarker();
	}


	@Override
	protected void updateMarker() {
		if (labelContainer!=null) {
			int lastPos = labelContainer.getChildren().size();
			// if attribute shall be marked, then add marker, if it is not
			// present
			if (((TAttribute) rhsAttribute).getMarkerType() != null) {

				if (((TAttribute) rhsAttribute).getMarkerType().equals(RuleUtil.NEW)) {
					if (!labelContainer.getChildren().contains(marker))
						labelContainer.add(marker, lastPos);
				} else if (((TAttribute) rhsAttribute).getMarkerType().equals(RuleUtil.Translated)) {
					if (!labelContainer.getChildren()
							.contains(translatedMarker))
						labelContainer.add(translatedMarker, lastPos);
				}

			}

			// if attribute shall be without marker, then remove marker
			else {
				if (labelContainer.getChildren().contains(marker)) 
					labelContainer.remove(marker);
				if (labelContainer.getChildren().contains(translatedMarker)) 
					labelContainer.remove(translatedMarker);
			}
		}
	}
	
//	protected boolean needTranslateFlag(AttributeLayout attributeLayout) {
//		if(attributeLayout == null) return false;
//		Boolean lhsTranslated = attributeLayout.getLhsTranslated();
//		Boolean rhsTranslated = attributeLayout.getRhsTranslated();
//		if(rhsTranslated != null && lhsTranslated != null && !rhsTranslated.equals(lhsTranslated)){
//			return true;
//		}
//		return false;
//	}
	
	@Override
	protected void performOpen() {
		// TODO Auto-generated method stub
		//super.performOpen();
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, new RuleAttributeComponentEditPolicy());
		installEditPolicy(EditPolicy.NODE_ROLE, new AttributeGraphicalEditPolicy());
	}

	
}
