package de.tub.tfs.henshin.tggeditor.actions;

import java.util.ArrayList;
import java.util.List;
import java.util.AbstractMap.SimpleEntry;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.henshin.model.Attribute;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TRule;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateAttributeCommand;
import de.tub.tfs.henshin.tggeditor.dialogs.TextDialog;
import de.tub.tfs.henshin.tggeditor.editparts.graphical.TNodeObjectEditPart;
import de.tub.tfs.henshin.tggeditor.util.AttributeTypes;
import de.tub.tfs.henshin.tggeditor.util.NodeUtil;
import de.tub.tfs.henshin.tggeditor.util.dialogs.DialogUtil;
import de.tub.tfs.muvitor.commands.SetEObjectFeatureValueCommand;

public class EditAttributeAction extends SelectionAction{

	public static final String ID ="de.tub.tfs.henshin.tggeditor.actions.EditAttributeAction";
	
	private Attribute attribute;

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
			
				return true;
			}
		}
		return false;
	}
	
	@Override 
	public void run() {
		
		Shell shell = new Shell();
		TextDialog box = new TextDialog(shell,"Edit Attribute " + attribute.getType().getName(),"Value for Attribute "+ attribute.getType().getName(),attribute.getValue(),true);

		box.open();
		
		String text = box.getInputText();
		
		this.execute(new SetEObjectFeatureValueCommand(attribute, text, HenshinPackage.ATTRIBUTE__VALUE));
		
	}

}
