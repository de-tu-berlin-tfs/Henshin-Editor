/*******************************************************************************
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.delete;

import java.util.List;

import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.gef.commands.Command;

public class DeleteFoldercommand extends Command {

	private IndependentUnit model;

	public DeleteFoldercommand(IndependentUnit model) {
		this.model = model;
	}
	
	
	
	@Override
	public void execute() {
		model.getSubUnits().clear();
		((List)model.eContainer().eGet(model.eContainingFeature())).remove(model);		
		
	}

}
