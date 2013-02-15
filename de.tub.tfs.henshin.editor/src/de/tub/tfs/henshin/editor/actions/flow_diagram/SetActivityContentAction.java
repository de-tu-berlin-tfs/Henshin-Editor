/**
 * 
 */
package de.tub.tfs.henshin.editor.actions.flow_diagram;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.Action;
import org.eclipse.swt.graphics.Image;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.editor.ui.dialog.NamedElementLabelProvider;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;

/**
 * An {@link Action} to set the content of {@link Activity} model objects. A
 * content could hereby be a {@link Rule} or a {@link FlowDiagram} itself.
 * 
 * @author nam
 * 
 */
public class SetActivityContentAction extends SelectionAction {

	/**
	 * An unique id for this {@link Action}.
	 */
	public static final String ID = "de.tub.tfs.henshin.editor.actions.flow_diagram.SetActivityContentAction"; //$NON-NLS-1$

	/**
     * 
     */
	private Activity model;

	/**
     * 
     */
	Module root;

	/**
	 * @param part
	 */
	public SetActivityContentAction(IWorkbenchPart part) {
		super(part);

		setText("Set Content...");
		setId(ID);
		setDescription("Set the content of this Activity.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		if (selectedObjects.size() == 1) {
			Object selected = selectedObjects.get(0);

			if (selected instanceof EditPart) {
				Object model = ((EditPart) selected).getModel();

				if (model instanceof Activity
						&& !(model instanceof CompoundActivity)) {
					this.model = (Activity) model;

					Module rootModel = ModelUtil.getModelRoot(
							this.model, Module.class);

					this.root = rootModel;

					return true;
				}
			}
		}

		return false;
	}

	private EList<Unit> getRules(Module m) {
		EList<Unit> rules = new BasicEList<Unit>();
		for (Unit unit : m.getUnits()) {
			if (unit instanceof Rule) {
				rules.add((Rule) unit);
			}
		}
		return ECollections.unmodifiableEList(rules);
	}
	
	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		
		List<Unit> rules = getRules(root);
		FlowControlSystem flowRoot = FlowControlUtil.INSTANCE
				.getFlowControlSystem(root);
		List<FlowDiagram> diags = flowRoot.getUnits();
		List<NamedElement> possibleContents = new ArrayList<NamedElement>(rules);

		possibleContents.addAll(diags);

		if (model.getContent() != null) {
			possibleContents.remove(model.getContent());
		}

		possibleContents.remove(model.getDiagram());

		NamedElement content = new ExtendedElementListSelectionDialog<NamedElement>(
				getWorkbenchPart().getSite().getShell(),
				new NamedElementLabelProvider(null) {
					@Override
					public Image getImage(Object element) {
						if (element instanceof Rule) {
							return ResourceUtil.ICONS.RULE.img(16);
						}

						return ResourceUtil.ICONS.FLOW_DIAGRAM.img(16);
					}
				}, possibleContents.toArray(new NamedElement[possibleContents
						.size()]), "Content Selection",
				"Please choose a content object for the selected Activity:")
				.runSingle();

		if (content != null) {
			execute(new SetActivityContentCommand(model, content));
		}
	}
}
