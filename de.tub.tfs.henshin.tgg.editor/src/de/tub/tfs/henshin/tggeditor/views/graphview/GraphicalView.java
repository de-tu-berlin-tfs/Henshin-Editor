/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.views.graphview;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.jface.action.IToolBarManager;
import org.eclipse.ui.part.IPage;

import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBPpgDeltaBasedToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBPpgStateBasedToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBTRulesToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteCCRulesToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFPpgDeltaBasedToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFPpgStateBasedToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFTRulesToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteITRulesToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.RemoveMarkersToolBarAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.GraphValidToolBarAction;
import de.tub.tfs.muvitor.ui.MuvitorPageBookView;

public class GraphicalView extends MuvitorPageBookView {

	@Override
	protected String calculatePartName() {
		if(getModel().eContainer() instanceof NestedCondition){
			return "NAC: " + ((NamedElement) getModel()).getName();
		}else{
			return "Graph: " + ((NamedElement) getModel()).getName();
		}
	}

	@Override
	protected IPage createPageForModel(EObject forModel) {				
		GraphicalPage page = new GraphicalPage(this);
		IToolBarManager toolBar = getViewSite().getActionBars().getToolBarManager();
		toolBar.add(new GraphValidToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteFTRulesToolBarAction(this, (GraphicalPage) page));
		//NEW
		toolBar.add(new ExecuteITRulesToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteBTRulesToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteCCRulesToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new RemoveMarkersToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteFPpgStateBasedToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteBPpgStateBasedToolBarAction(this, (GraphicalPage) page));
		//NEW
		toolBar.add(new ExecuteFPpgDeltaBasedToolBarAction(this, (GraphicalPage) page));
		toolBar.add(new ExecuteBPpgDeltaBasedToolBarAction(this, (GraphicalPage) page));
		return page;
	}

}
