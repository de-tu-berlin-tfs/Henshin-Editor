/**
 * 
 */
package de.tub.tfs.muvitor.ui;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.gef.ui.parts.ScrollingGraphicalViewer;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;

public class MultiDimensionalViewer<T extends EObject> extends ScrollingGraphicalViewer {

	
	protected int position = 0;
	public int getPosition() {
		return position;
	}
	public void setPosition(int position) {
		this.position = position;
	}
	/**
	 * 
	 */
	private final MultiDimensionalPage<T> multiDimensionalPage;
	/**
	 * A listener that notices when the mouse acts on this viewer.
	 */
	private final MouseListener mouseListener;

	public MultiDimensionalViewer(final MultiDimensionalPage<T> multiDimensionalPage) {
		this.multiDimensionalPage = multiDimensionalPage;
		mouseListener = new MouseAdapter() {
			@Override
			public void mouseDown(final MouseEvent e) {
				multiDimensionalPage.setCurrentViewer(MultiDimensionalViewer.this);
			}
		};
	}

	/**
	 * @return The page that hosts this graphical viewer.
	 */
	public MultiDimensionalPage<T> getHostPage() {
		return this.multiDimensionalPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalViewerImpl#hookControl()
	 */
	@Override
	protected void hookControl() {
		super.hookControl();
		super.getControl().addMouseListener(mouseListener);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see org.eclipse.gef.ui.parts.GraphicalViewerImpl#unhookControl()
	 */
	@Override
	protected void unhookControl() {
		super.getControl().removeMouseListener(mouseListener);
		super.unhookControl();
	}
}