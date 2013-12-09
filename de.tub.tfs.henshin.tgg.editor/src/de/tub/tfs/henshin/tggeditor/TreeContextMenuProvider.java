package de.tub.tfs.henshin.tggeditor;

import org.eclipse.core.runtime.CoreException;
import org.eclipse.core.runtime.IConfigurationElement;
import org.eclipse.core.runtime.IExtension;
import org.eclipse.core.runtime.IExtensionPoint;
import org.eclipse.core.runtime.IExtensionRegistry;
import org.eclipse.core.runtime.Platform;
import org.eclipse.gef.EditPartViewer;
import org.eclipse.gef.ui.actions.GEFActionConstants;
import org.eclipse.jface.action.IMenuManager;

import de.tub.tfs.henshin.tggeditor.actions.AbstractTggActionFactory;
import de.tub.tfs.henshin.tggeditor.actions.EditAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateAttributeAction;
import de.tub.tfs.henshin.tggeditor.actions.create.graph.CreateGraphAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateAttributeConditonAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateNACAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateParameterAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreatePrototypeRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRecPrototypeRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.CreateRuleFolderAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateBTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateCCRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateCCRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRuleAction;
import de.tub.tfs.henshin.tggeditor.actions.create.rule.GenerateFTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteBTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteCCRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.execution.ExecuteFTRulesAction;
import de.tub.tfs.henshin.tggeditor.actions.exports.ExportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportCorrAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportEMFModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportInstanceModelActionWithDefaultValues;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportSourceAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.ImportTargetAction;
import de.tub.tfs.henshin.tggeditor.actions.imports.LoadReconstructXMLForSource;
import de.tub.tfs.henshin.tggeditor.actions.validate.CheckRuleConflictAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.GraphValidAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidAction;
import de.tub.tfs.henshin.tggeditor.actions.validate.RuleValidateAllRulesAction;
import de.tub.tfs.muvitor.ui.ContextMenuProviderWithActionRegistry;

public class TreeContextMenuProvider extends
		ContextMenuProviderWithActionRegistry {

	public TreeContextMenuProvider(EditPartViewer viewer) {
		super(viewer);
	}

	@Override
	public void buildContextMenu(IMenuManager menu) {
		String group = GEFActionConstants.GROUP_EDIT;
		dynamicAppendActionToGroup(menu, ImportEMFModelAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportSourceAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportTargetAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportCorrAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportInstanceModelAction.ID, group);
		dynamicAppendActionToGroup(menu, ImportInstanceModelActionWithDefaultValues.ID, group);
		dynamicAppendActionToGroup(menu, CreateGraphAction.ID, group);
		dynamicAppendActionToGroup(menu, LoadReconstructXMLForSource.ID, group);
		//dynamicAppendActionToGroup(menu, LoadXMLXSDmodel.ID, group);
		dynamicAppendActionToGroup(menu, CreateAttributeAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, CreatePrototypeRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateRecPrototypeRulesAction.ID, group);
		
		dynamicAppendActionToGroup(menu, CreateRuleFolderAction.ID, group);
		
		dynamicAppendActionToGroup(menu, CreateNACAction.ID, group);
		dynamicAppendActionToGroup(menu, GraphValidAction.ID, group);
		dynamicAppendActionToGroup(menu, RuleValidAction.ID, group);
		dynamicAppendActionToGroup(menu, CheckRuleConflictAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateParameterAction.ID, group);
		dynamicAppendActionToGroup(menu, CreateAttributeConditonAction.ID, group);
		dynamicAppendActionToGroup(menu, EditAttributeAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateFTRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateBTRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateCCRuleAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateFTRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateBTRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, GenerateCCRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, RuleValidateAllRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, ExecuteFTRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, ExecuteBTRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, ExecuteCCRulesAction.ID, group);
		dynamicAppendActionToGroup(menu, ExportInstanceModelAction.ID, group);

		IExtensionRegistry reg = Platform.getExtensionRegistry();
		IExtensionPoint ep = reg.getExtensionPoint("de.tub.tfs.henshin.tgg.editor.graph.actions");
		IExtension[] extensions = ep.getExtensions();
		for (int i = 0; i < extensions.length; i++) {
			IExtension ext = extensions[i];
			IConfigurationElement[] ce = 
					ext.getConfigurationElements();
			for (int j = 0; j < ce.length; j++) {

				try {
					AbstractTggActionFactory obj = (AbstractTggActionFactory) ce[j].createExecutableExtension("class");

					dynamicAppendActionToGroup(menu,obj.getActionID(),group);

				} catch (CoreException e) {
				}


			}
		}
		
	}
	
	
}
