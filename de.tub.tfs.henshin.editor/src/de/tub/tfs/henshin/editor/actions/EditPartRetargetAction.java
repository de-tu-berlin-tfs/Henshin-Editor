/**
 * AbstractEdipartRetargetAction.java
 *
 * Created 19.12.2011 - 20:53:08
 */
package de.tub.tfs.henshin.editor.actions;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.gef.ui.actions.SelectionAction;
import org.eclipse.jface.action.IAction;

/**
 * @author nam
 * 
 */
public class EditPartRetargetAction extends SelectionAction {

	private IHandlersRegistry registry;

	private IAction defaultHandler;

	private List<IAction> handlers;

	/**
	 * @param reg
	 */
	public EditPartRetargetAction(IHandlersRegistry reg) {
		this(reg, "<unknown>");
	}

	/**
	 * @param reg
	 * @param id
	 */
	public EditPartRetargetAction(IHandlersRegistry reg, String id) {
		super(reg.getWorkbenchPart());

		this.registry = reg;
		this.handlers = new LinkedList<IAction>();

		setId(id);
	}

	/**
	 * @param defaultHandler
	 *            the defaultHandler to set
	 */
	public void setDefaultHandler(IAction defaultHandler) {
		this.defaultHandler = defaultHandler;

		if (defaultHandler != null) {
			defaultHandler.setId(getId() + "__default__");

			registry.registerHandler(defaultHandler);

			setText(defaultHandler.getText());
			setToolTipText(defaultHandler.getToolTipText());
			setDescription(defaultHandler.getDescription());
			setImageDescriptor(defaultHandler.getImageDescriptor());
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		for (IAction a : handlers) {
			a.run();
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		List<?> selection = getSelectedObjects();

		handlers.clear();

		for (Object o : selection) {
			if (o instanceof IHandlerTarget) {
				IAction targetHandler = registry.getHandler(getId(),
						((IHandlerTarget) o).getTargetModel());

				if (targetHandler != null && !handlers.contains(targetHandler)) {
					handlers.add(targetHandler);
				}
			}
		}

		if (handlers.isEmpty()) {
			handlers.add(defaultHandler);
		}

		for (IAction a : handlers) {
			if (!a.isEnabled()) {
				return false;
			}
		}

		return true;
	}
}
