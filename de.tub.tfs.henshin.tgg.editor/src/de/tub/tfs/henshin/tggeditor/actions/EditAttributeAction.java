/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.actions;

import java.util.List;

import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.AttributeCondition;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;
import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

public class EditAttributeAction extends SelectionAction{

	public static final String ID ="de.tub.tfs.henshin.tggeditor.actions.EditAttributeAction";
	
	private Attribute attribute;
	private AttributeCondition attributeCondition;

	public EditAttributeAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Edit Attribute");
		setToolTipText("Multi Line Editor for Attributes");
	}

	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();
		if (selectedObjects.size() != 1) {
			return false;
		}
		Object selectedObject = selectedObjects.get(0);
				
		if ((selectedObject instanceof EditPart)) {
			EditPart editpart = (EditPart) selectedObject;
			if ((editpart.getModel() instanceof Attribute)) {
				attribute = (Attribute) editpart.getModel();
				attributeCondition = null;
				setText("Edit Attribute");
				return true;
			}
			if ((editpart.getModel() instanceof AttributeCondition)) {
				attributeCondition = (AttributeCondition) editpart.getModel();
				attribute = null;
				setText("Edit Attribute Conditions");
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		if (attribute != null){
			Shell shell = new Shell();
			TextDialog box = new TextDialog(shell,"Edit Attribute " + attribute.getType().getName(),"Value for Attribute "+ attribute.getType().getName(),attribute.getValue(),true);

			box.open();

			String text = box.getInputText();

			this.execute(new SetEObjectFeatureValueCommand(attribute, text, HenshinPackage.ATTRIBUTE__VALUE));
		} else if (attributeCondition != null){
			Shell shell = new Shell();
			TextDialog box = new TextDialog(shell,"Edit Attribute Condition ","Javascript Code for Attribute Condition:",attributeCondition.getConditionText(),true);

			box.open();

			String text = box.getInputText();

			this.execute(new SetEObjectFeatureValueCommand(attributeCondition, text, HenshinPackage.ATTRIBUTE_CONDITION__CONDITION_TEXT));
		}
	}

}
