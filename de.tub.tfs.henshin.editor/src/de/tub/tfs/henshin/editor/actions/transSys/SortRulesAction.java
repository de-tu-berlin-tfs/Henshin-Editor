package de.tub.tfs.henshin.editor.actions.transSys;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.EditPart;
import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.ui.IWorkbenchPart;

import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.layout.EContainerDescriptor;

public class SortRulesAction extends SelectionAction {

	/** The Constant ID. */
	public static final String ID = "henshineditor.actions.SortRuleAction";

	/** The graph. */
	private TransformationSystem transformationSystem;

	public SortRulesAction(IWorkbenchPart part) {
		super(part);
		setId(ID);
		setText("Sort rules by name");
		setImageDescriptor(ResourceUtil.ICONS.SORT.descr(16));
		setToolTipText("Sort rules by name.");
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selectedObjects = getSelectedObjects();

		transformationSystem = null;

		if (selectedObjects.size() == 1) {
			Object selectedObject = selectedObjects.get(0);

			if ((selectedObject instanceof EditPart)) {
				EditPart editpart = (EditPart) selectedObject;
				Object model = editpart.getModel();

				if (model instanceof TransformationSystem) {
					transformationSystem = (TransformationSystem) model;
				}

				else if (model instanceof EContainerDescriptor
						&& editpart.getAdapter(Rule.class) != null) {
					transformationSystem = (TransformationSystem) ((EContainerDescriptor) model)
							.getContainer();
				}
			}
		}

		return transformationSystem != null;
	}

	@Override
	public void run() {
		EList<Rule> rules = transformationSystem.getRules();
		Rule[] rulesArr = new Rule[0];
		rulesArr = rules.toArray(rulesArr);
		Arrays.sort(rulesArr, new Comparator<Rule>() {
			@Override
			public int compare(Rule o1, Rule o2) {
				// TODO Auto-generated method stub
				if (o1 == o2)
					return 0;
				if (o1 == null || o1.getName() == null)
					return -1;
				if (o2 == null || o2.getName() == null)
					return 1;
				return o1.getName().toLowerCase()
						.compareTo(o2.getName().toLowerCase());
			}
		});
		// transformationSystem.eSetDeliver(false);
		rules.clear();
		rules.addAll(Arrays.asList(rulesArr));
		// transformationSystem.eSetDeliver(true);

	}
}
