/**
 * SelectionUtil.java
 * created on 28.03.2013 16:09:57
 */
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.ui.IViewPart;
import org.eclipse.ui.IViewReference;
import org.eclipse.ui.IWorkbench;
import org.eclipse.ui.IWorkbenchPage;
import org.eclipse.ui.PlatformUI;

import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalPage;
import de.tub.tfs.henshin.tggeditor.views.graphview.GraphicalView;

/**
 * @author huuloi
 */
public final class SelectionUtil {

	private SelectionUtil() {
		
	}
	
	
	/**
	 * @param graph
	 * 			the graph
	 * @return
	 * 			the view of the given graph
	 */
	public static GraphicalView getActiveGraphicalView(TripleGraph graph) {
		GraphicalView view = null;
		
		final IWorkbench workbench = PlatformUI.getWorkbench();
		final IWorkbenchPage page = workbench.getActiveWorkbenchWindow().getActivePage();
		
		if (page != null) {
//			TODO: check if the following codes work and use them instead of other implementation
//			IWorkbenchPart activePart = page.getActivePart();
//			if (activePart != null && activePart instanceof GraphicalView) {
//				GraphicalView viewer = (GraphicalView) activePart;
//				GraphicalPage graphicalPage = viewer.getPage();
//				if (EcoreUtil.equals(graph, graphicalPage.getCastedModel())) {
//					view = viewer;
//				}
//			}
			
			IViewReference[] viewRefs = page.getViewReferences();

			if (viewRefs != null) {
				for (IViewReference viewRef : viewRefs) {
					IViewPart viewPart = viewRef.getView(false);

					if (viewPart != null) {
						if (viewPart instanceof GraphicalView) {
							GraphicalView viewer = (GraphicalView) viewPart;
							GraphicalPage graphPage = viewer.getPage();
							if (graphPage.getCastedModel() == graph) {
								view = viewer;
							}
						}
					}
				}
			}
		}
		
		return view;
	}
	
}