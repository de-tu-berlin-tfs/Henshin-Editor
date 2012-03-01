/**
 * 
 */
package de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical;

import org.eclipse.draw2d.IFigure;
import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.GraphicalViewer;
import org.eclipse.jface.util.TransferDropTargetListener;
import org.eclipse.swt.dnd.DropTargetEvent;
import org.eclipse.swt.dnd.Transfer;

import de.tub.tfs.henshin.editor.figure.flow_diagram.ActivityFigure;
import de.tub.tfs.henshin.editor.util.ResourceUtil;
import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlPackage;
import de.tub.tfs.henshin.model.layout.FlowElementLayout;
import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;

/**
 * @author nam
 * 
 */
public class ActivityEditPart<T extends Activity> extends
		FlowElementEditPart<T> {

	/**
	 * @param model
	 */
	public ActivityEditPart(T model) {
		super(model);

		EObject content = model.getContent();

		if (content != null) {
			registerAdapter(content);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#hookCreateFigure()
	 */
	@Override
	protected IFigure hookCreateFigure() {
		ActivityFigure fig = new ActivityFigure();

		fig.setName(null);

		return fig;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditPart
	 * #notifyChanged(org.eclipse.emf.common.notify.Notification)
	 */
	@Override
	protected void notifyChanged(Notification notification) {
		int flowMsgId = notification.getFeatureID(FlowControlPackage.class);
		int msgType = notification.getEventType();

		switch (flowMsgId) {
		case FlowControlPackage.ACTIVITY__CONTENT:
			if (msgType == Notification.SET) {
				EObject newContentEObject = getCastedModel().getContent();

				if (newContentEObject != null) {
					registerAdapter(newContentEObject);
				}
			}
			break;

		default:
			break;
		}

		super.notifyChanged(notification);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * de.tub.tfs.muvitor.gef.editparts.AdapterGraphicalEditPart#performOpen()
	 */
	@Override
	protected void performOpen() {
		NamedElement content = getCastedModel().getContent();

		if (content != null) {
			MuvitorTreeEditor.showView(content);
		}
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.editparts.AbstractGraphicalEditPart#activate()
	 */
	@Override
	public void activate() {
		super.activate();

		GraphicalViewer viewer = (GraphicalViewer) getViewer();

		viewer.addDropTargetListener(new TransferDropTargetListener() {

			@Override
			public void dropAccept(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void drop(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragOver(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragOperationChanged(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragLeave(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public void dragEnter(DropTargetEvent event) {
				// TODO Auto-generated method stub

			}

			@Override
			public boolean isEnabled(DropTargetEvent event) {
				System.out.println("askdjflasjdfl;asjf;lja;slf");
				return true;
			}

			@Override
			public Transfer getTransfer() {
				// TODO Auto-generated method stub
				return null;
			}
		});
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see de.tub.tfs.henshin.editor.editparts.flow_diagram.graphical.
	 * FlowElementEditpart#layoutFigure(org.eclipse.draw2d.IFigure)
	 */
	@Override
	protected void layoutFigure(IFigure f) {
		super.layoutFigure(f);

		ActivityFigure fig = (ActivityFigure) f;
		NamedElement modelContent = getCastedModel().getContent();
		FlowElementLayout layoutModel = getLayoutModel();

		if (modelContent == null) {
			fig.setName(null);
			fig.setMapping(-1);
		} else if (layoutModel != null) {
			fig.setMapping(layoutModel.getMapId());
			fig.setName(modelContent.getName());

			if (modelContent instanceof Rule) {
				fig.setContentIcon(ResourceUtil.ICONS.RULE.img(25));
				fig.setToolTip("Activity \"" + modelContent.getName()
						+ "\".\nDouble click to open.");
			} else {
				fig.setContentIcon(ResourceUtil.ICONS.LINK.img(18));
				fig.setToolTip("Activity linked to \"" + modelContent.getName()
						+ "\".\nDouble click to open.");
			}
		}
	}
}
