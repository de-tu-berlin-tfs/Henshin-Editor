/**
 * RuleCreationTool.java
 *
 * Created 20.01.2012 - 01:06:49
 */
package de.tub.tfs.henshin.editor.actions.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.tools.CreationTool;
import org.eclipse.swt.graphics.Image;

import de.tub.tfs.henshin.editor.commands.flow_diagram.CreateFlowElementCommand;
import de.tub.tfs.henshin.editor.commands.flow_diagram.SetActivityContentCommand;
import de.tub.tfs.henshin.editor.ui.dialog.ExtendedElementListSelectionDialog;
import de.tub.tfs.henshin.editor.ui.dialog.NamedElementLabelProvider;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;

/**
 * @author nam
 * 
 */
public class RuleCreationTool extends CreationTool {
	private Activity model;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.eclipse.gef.tools.AbstractTool#executeCommand(org.eclipse.gef.commands
	 * .Command)
	 */
	@Override
	protected void executeCommand(Command command) {
		EditPart part = getTargetEditPart();

		if (part != null) {
			if (model == null) {
				model = (Activity) ((CreateFlowElementCommand<?>) command)
						.getModel();
			}

			Module root = HenshinUtil.INSTANCE
					.getTransformationSystem((EObject) getTargetEditPart()
							.getModel());
			List<NamedElement> possibleContents = new ArrayList<NamedElement>(
					HenshinUtil.getRules(root));

			if (model.getContent() != null) {
				possibleContents.remove(model.getContent());
			}

			NamedElement content = new ExtendedElementListSelectionDialog<NamedElement>(
					null, new NamedElementLabelProvider(null) {
						@Override
						public Image getImage(Object element) {
							return ResourceUtil.ICONS.RULE.img(16);
						}
					},
					possibleContents.toArray(new NamedElement[possibleContents
							.size()]), "Content Selection",
					"Please choose a content object for the selected Activity:")
					.runSingle();

			if (content != null) {
				Command cmd = null;

				if (command instanceof CreateFlowElementCommand<?>) {
					CreateFlowElementCommand<?> createCmd = ((CreateFlowElementCommand<?>) command);

					cmd = command.chain(new SetActivityContentCommand(
							(Activity) createCmd.getModel(), createCmd
									.getLayoutModel(), content));
				} else {
					cmd = new SetActivityContentCommand(model, content);
				}

				super.executeCommand(cmd);
			}
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.tools.TargetingTool#getCommand()
	 */
	@Override
	protected Command getCommand() {
		EditPart target = getTargetEditPart();

		model = null;

		if (target != null) {
			Object model = target.getModel();

			if (model instanceof Activity
					&& !(model instanceof CompoundActivity)) {
				this.model = (Activity) model;

				return new SetActivityContentCommand(this.model, null);
			}
		}

		return super.getCommand();
	}
}
