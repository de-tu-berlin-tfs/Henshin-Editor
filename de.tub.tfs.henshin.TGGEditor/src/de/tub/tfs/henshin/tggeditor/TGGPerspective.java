package de.tub.tfs.henshin.tggeditor;

import org.eclipse.ui.IFolderLayout;
import org.eclipse.ui.IPageLayout;
import org.eclipse.ui.IPerspectiveFactory;
import org.eclipse.ui.IPlaceholderFolderLayout;

import de.tub.tfs.muvitor.ui.MuvitorTreeEditor;
import de.tub.tfs.muvitor.ui.utils.MuvitorPerspective;

/**
 * This the default definition of the perspective for the RON editor.
 * {@link MuvitorTreeEditor} can automatically activate this perspective if it
 * is declared in the plugin.xml. See {@link MuvitorTreeEditor} for details.
 * 
 * @author Tony Modica
 */
public class TGGPerspective implements IPerspectiveFactory, MuvitorPerspective {
	
	public String perspectiveID="";
	
	/*
	 * (non-Javadoc)
	 * @see
	 * org.eclipse.ui.IPerspectiveFactory#createInitialLayout(org.eclipse.ui
	 * .IPageLayout)
	 */
	@Override
	public void createInitialLayout(final IPageLayout layout) {
		defineActions(layout);
		defineLayout(layout);
	}
	
	/**
	 * Defines the initial actions for a page.
	 * 
	 * @param layout
	 *            The layout we are filling
	 */
	private void defineActions(final IPageLayout layout) {
		// insert the id of your creation wizard here
		layout.addNewWizardShortcut("tggeditor.wizards.TGGFileCreationWizard"); 
		
		// Add "show views".
		// layout.addShowViewShortcut("de.tub.tfs.muvitor.CommonNavigator");
		layout.addShowViewShortcut(IPageLayout.ID_PROJECT_EXPLORER);
		layout.addShowViewShortcut(IPageLayout.ID_PROP_SHEET); // TODO: Property view is currently not loaded automatically within the TGG perspective (caused Nullpointer Exception)
		layout.addShowViewShortcut(IPageLayout.ID_PROBLEM_VIEW);
		layout.addShowViewShortcut("org.eclipse.pde.runtime.LogView");
	}
	
	/**
	 * Defines the initial perspective layout.
	 * 
	 * @param layout
	 *            The layout we are filling
	 */
	private void defineLayout(final IPageLayout layout) {
		
		final String editorArea = layout.getEditorArea();

		/*
		 * the suffix ":*" is needed for all MuvitorPageBookViews as a
		 * placeholder for the views' different secondary IDs
		 */


		final IFolderLayout bottomRight = layout.createFolder("bottomRight", IPageLayout.RIGHT,
				0.2f, editorArea);
		bottomRight.addView(IPageLayout.ID_PROP_SHEET);  
		bottomRight.addView("org.eclipse.pde.runtime.LogView");
		bottomRight.addView(IPageLayout.ID_PROBLEM_VIEW);

		
		 final IFolderLayout bottomLeft = layout.createFolder("bottomLeft", IPageLayout.BOTTOM,
				0.8f, editorArea);
		//bottomLeft.addView("de.tub.tfs.muvitor.CommonNavigator");
		 bottomLeft.addView(IPageLayout.ID_PROJECT_EXPLORER); // requested by property sheet


		 final IPlaceholderFolderLayout topRight = layout
		 .createPlaceholderFolder("topRight", IPageLayout.BOTTOM, 0.8f,
				 "bottomRight");
		
//		final IPlaceholderFolderLayout middleRight = layout.createPlaceholderFolder("middleRight",
//				IPageLayout.BOTTOM, 0.5f, "topRight");
		topRight.addPlaceholder(TreeEditor.GRAPH_VIEW_ID + ":*");
		topRight.addPlaceholder(TreeEditor.CRITICAL_PAIR_VIEW_ID + ":*");
		topRight.addPlaceholder(TreeEditor.RULE_VIEW_ID + ":*");
		

		
				
		
		
		

		 //			TreeEditor.setIDs();
		
	}
}