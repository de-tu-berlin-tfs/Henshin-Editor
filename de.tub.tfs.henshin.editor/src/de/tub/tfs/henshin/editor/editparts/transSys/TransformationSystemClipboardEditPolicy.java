/**
 * TransformationSystemClipboardEditPolicy.java
 *
 * Created 22.01.2012 - 01:28:46
 */
package de.tub.tfs.henshin.editor.editparts.transSys;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy;
import de.tub.tfs.henshin.editor.editparts.PasteRequest;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.flowcontrol.FlowControlUtil;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.layout.Layout;

/**
 * @author nam
 * 
 */
public final class TransformationSystemClipboardEditPolicy extends
		ClipboardEditPolicy {

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy #canCopy()
	 */
	@Override
	protected boolean canCopy() {
		return false;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#canPaste(java
	 * .lang.Object)
	 */
	@Override
	protected boolean canPaste(Object o) {
		return o instanceof Unit || o instanceof FlowDiagram
				|| o instanceof Graph;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.henshin.editor.editparts.ClipboardEditPolicy#getPasteTarget
	 * (de.tub.tfs.henshin.editor.editparts.PasteRequest)
	 */
	@Override
	public EObject getPasteTarget(PasteRequest req) {
		Object o = req.getPastedObject();
		Module model = (Module) getHost()
				.getModel();

		if (o instanceof Layout) {
			return HenshinLayoutUtil.INSTANCE.getLayoutSystem(model);
		}

		if (o instanceof FlowDiagram) {
			return FlowControlUtil.INSTANCE.getFlowControlSystem(model);
		}

		return super.getPasteTarget(req);
	}
}