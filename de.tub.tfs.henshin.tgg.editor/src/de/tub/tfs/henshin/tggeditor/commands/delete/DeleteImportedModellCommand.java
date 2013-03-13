package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.ImportedPackage;
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
	private ImportedPackage impPackage;
	
	public DeleteImportedModellCommand(ImportedPackage impPackage, TGG tgg, Module module) {
		this.tgg = tgg;
		this.ePackage = impPackage.getPackage();
		this.impPackage = impPackage;
		this.module = module;
	}
	
	@Override
	public boolean canExecute() {
			return true;
	}
	
	@Override
	public void execute() {
		module.getImports().remove(ePackage);
		
		tgg.getImportedPkgs().remove(impPackage);
	}
	
	@Override
	public void undo() {
		module.getImports().add(ePackage);
		tgg.getImportedPkgs().add(impPackage);
		super.undo();
	}

}
