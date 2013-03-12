package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.TGG;




public class DeleteImportedModellCommand extends Command {
/**
 * Instantiates a new delete imported model command
 * 
 */
	
	private TGG tgg;
	private EPackage ePackage;
	private int model;
	private Module module;
	
	public DeleteImportedModellCommand(EPackage ePackage, TGG tgg, Module module) {
		this.tgg = tgg;
		this.ePackage = ePackage;
		this.module = module;
	}
	
	@Override
	public boolean canExecute() {
		
//		// disable the Delete-Action if there is a node in a graph
//		if (!tgg.getNodelayouts().isEmpty()) {
//			return false;
//		}
		
		// enable a selected package, if it was imported before
		if (tgg.getCorresp()==ePackage) {
			model = 2;
			return true;
		}
		if (tgg.getTarget()==ePackage) {
			model = 3;
			return true;
		}
		if (tgg.getSource()==ePackage) {
			model = 1;
			return true;
		}
		return false;
	}
	
	@Override
	public void execute() {
		module.getImports().remove(ePackage);
		switch (model) {
		case 1:
			tgg.setSource(null);
			break;
		case 2:
			tgg.setCorresp(null);
			break;
		case 3:
			tgg.setTarget(null);
			break;
		}
		
		//trafo.getImports().remove(package);
	}
	
	@Override
	public void undo() {
		module.getImports().add(ePackage);
		switch (model) {
		case 1:
			tgg.setSource(ePackage);
			break;
		case 2:
			tgg.setCorresp(ePackage);
			break;
		case 3:
			tgg.setTarget(ePackage);
			break;
		}
		super.undo();
	}

}
