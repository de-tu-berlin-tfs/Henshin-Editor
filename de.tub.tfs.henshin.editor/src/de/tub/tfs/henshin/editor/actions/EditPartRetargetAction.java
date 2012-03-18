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

	private IAction enabledHandler;

	private String defaultId;

	private List<String> handlers;

	/**
	 * @param reg
	 * @param id
	 */
	public EditPartRetargetAction(IHandlersRegistry reg, String id) {
		super(reg.getWorkbenchPart());

		this.registry = reg;
		this.handlers = new LinkedList<String>();
		defaultId = null;
		
		setId(id);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.jface.action.Action#run()
	 */
	@Override
	public void run() {
		enabledHandler.run();
	}

	/**
	 * @param id
	 */
	public void registerHandler(String id) {
		handlers.add(id);
	}

	public void setDefaultHandler(IAction defaultHanler) {
		defaultId = getId() + "___default___";

		registry.registerHandler(defaultHanler, defaultId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.actions.WorkbenchPartAction#calculateEnabled()
	 */
	@Override
	protected boolean calculateEnabled() {
		if(defaultId != null){
			handlers.add(defaultId);
		}
		
		for (String id : handlers) {
			IAction handler = registry.getHandler(id);

			if (handler != null) {
				if (handler.isEnabled()) {
					enabledHandler = handler;

					transformTo(enabledHandler);

					break;
				}
			}
		}

		return enabledHandler != null;
	}

	/**
	 * @param handler
	 */
	private void transformTo(final IAction handler) {
		setId(handler.getId());
		setText(handler.getText());
		setToolTipText(handler.getToolTipText());
		setImageDescriptor(handler.getImageDescriptor());
	}
}
