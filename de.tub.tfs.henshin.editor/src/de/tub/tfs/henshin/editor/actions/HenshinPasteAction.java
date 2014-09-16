/**
 * HenshinPasteAction.java
 *
 * Created 16.01.2012 - 16:24:45
 */
package de.tub.tfs.henshin.editor.actions;

import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;
import org.eclipse.gef.ui.actions.Clipboard;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.actions.ActionFactory;

import de.tub.tfs.henshin.editor.HenshinTreeEditor;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.editparts.PasteRequest.IPasteRule;
import de.tub.tfs.henshin.editor.util.JavaUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.layout.HenshinLayoutPackage;

/**
 * @author nam
 * 
 */
public class HenshinPasteAction extends SelectionAction {

	private EditPart target;

	/**
	 * @param part
	 */
	public HenshinPasteAction(IWorkbenchPart part) {
		super(part);

		setId(ActionFactory.PASTE.getId());
		setText("Paste");
		setDescription("Paste from clipboard");
		setToolTipText("Paste parts from the clipboard");
		setImageDescriptor(PlatformUI.getWorkbench().getSharedImages()
				.getImageDescriptor(ISharedImages.IMG_TOOL_PASTE));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		CompoundCommand pasteCommand = new CompoundCommand("Paste objects");
		Collection<?> clipBoardContent = (Collection<?>) Clipboard.getDefault()
				.getContents();
		Module root = ((HenshinTreeEditor) getWorkbenchPart())
				.getModelRoot(Module.class);

		Map<EClass, IPasteRule> pasteRules = new HashMap<EClass, PasteRequest.IPasteRule>();

		pasteRules.put(HenshinPackage.Literals.GRAPH,
				new NamedElementPasteRule(
						HenshinPackage.MODULE__INSTANCES, root));

		pasteRules.put(HenshinPackage.Literals.RULE, new NamedElementPasteRule(
				HenshinPackage.MODULE__UNITS, root));

		pasteRules
				.put(HenshinPackage.Literals.CONDITIONAL_UNIT,
						new NamedElementPasteRule(
								HenshinPackage.MODULE__UNITS,
								root));

		pasteRules
				.put(HenshinPackage.Literals.SEQUENTIAL_UNIT,
						new NamedElementPasteRule(
								HenshinPackage.MODULE__UNITS,
								root));

		pasteRules
				.put(HenshinPackage.Literals.PRIORITY_UNIT,
						new NamedElementPasteRule(
								HenshinPackage.MODULE__UNITS,
								root));

		pasteRules
				.put(HenshinPackage.Literals.INDEPENDENT_UNIT,
						new NamedElementPasteRule(
								HenshinPackage.MODULE__UNITS,
								root));

		pasteRules.put(FlowControlPackage.Literals.FLOW_DIAGRAM,
				new NamedElementPasteRule(
						FlowControlPackage.FLOW_CONTROL_SYSTEM__UNITS,
						FlowControlUtil.INSTANCE.getFlowControlSystem(root)));

		pasteRules.put(HenshinLayoutPackage.Literals.NODE_LAYOUT,
				new LayoutPasteRule());

		pasteRules.put(HenshinLayoutPackage.Literals.FLOW_ELEMENT_LAYOUT,
				new LayoutPasteRule());

		for (Object o : EcoreUtil.copyAll(clipBoardContent)) {
			PasteRequest req = new PasteRequest(o);

			req.setExtendedData(pasteRules);

			Command cmd = target.getCommand(req);

			if (cmd != null) {
				pasteCommand.add(cmd);
			}
		}

		execute(pasteCommand);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		target = null;

		if (selection.size() == 1) {
			Object selected = selection.get(0);

			if (selected instanceof EditPart) {
				EditPart part = (EditPart) selected;
				Object clipBoardContent = Clipboard.getDefault().getContents();

				if (clipBoardContent instanceof Collection<?>) {
					Collection<?> copies = (Collection<?>) clipBoardContent;

					if (JavaUtil.checkContentType(copies, EObject.class)) {
						for (Object o : copies) {
							if (!part.understandsRequest(new PasteRequest(o))) {
								return false;
							}
						}

						target = part;

						return true;
					}
				}
			}
		}

		return false;
	}
}
