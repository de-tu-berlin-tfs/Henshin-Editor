/**
 * 
 */
package de.tub.tfs.henshin.editor.util.flowcontrol;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Module;

import de.tub.tfs.henshin.editor.HenshinTreeEditor;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * @author nam
 * 
 */
public final class FlowControlUtil {
	/**
	 * The singleton instance.
	 */
	public static FlowControlUtil INSTANCE = new FlowControlUtil();

	/**
	 * Convenient method to get the root {@link FlowControlSystem flow control
	 * system}.
	 * 
	 * @param model
	 * @return
	 */
	public FlowControlSystem getFlowControlSystem(EObject model) {
		if (model.eContainer() != null || model instanceof FlowControlSystem
				|| model instanceof Module
				|| model instanceof LayoutSystem) {

			HenshinTreeEditor editor = (HenshinTreeEditor) IDUtil
					.getHostEditor(model);

			if (editor != null) {
				return editor.getModelRoot(FlowControlSystem.class);
			}
		}

		return null;
	}

	/**
	 * Private, since singleton class.
	 */
	private FlowControlUtil() {
	}
}
