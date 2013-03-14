package de.tub.tfs.henshin.tggeditor.commands.setType;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.tgg.ImportedPackage;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TggFactory;
import de.tub.tfs.henshin.tgg.TripleComponent;

public class SetImportedPackageCommand extends Command {
	EPackage target;
	TGG tgg;
	EPackage oldTarget;
	private ImportedPackage impPackage;
	

	public SetImportedPackageCommand(EPackage target, TGG tgg, TripleComponent component) {
		this.target = target;
		this.tgg = tgg;
		this.impPackage = TggFactory.eINSTANCE.createImportedPackage();
		this.impPackage.setPackage(target);
		this.impPackage.setComponent(component);
	}

	@Override
	public boolean canExecute() {
		return this.target != null && this.tgg != null;
	}

	@Override
	public void execute() {
		tgg.getImportedPkgs().add(impPackage);
	}

	@Override
	public void undo() {
		tgg.getImportedPkgs().remove(impPackage);
		super.undo();
	}
	
	@Override
	public void redo() {
		tgg.getImportedPkgs().add(impPackage);
	}


	
	
	
	

}
