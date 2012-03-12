package tggeditor.editpolicies;

import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.editpolicies.ComponentEditPolicy;
import org.eclipse.gef.requests.GroupRequest;

import tgg.TGG;
import tggeditor.commands.delete.DeleteImportedModellCommand;

public class ImportedModellEditPolicy extends ComponentEditPolicy {

	private TGG tgg;
	
	public ImportedModellEditPolicy(TGG tgg) {
		this.tgg = tgg;
	}
	
	@Override
	protected Command createDeleteCommand(GroupRequest deleteRequest) {
		TransformationSystem trafo = (TransformationSystem) getHost().getParent().getParent().getModel();
		return new DeleteImportedModellCommand((EPackage) getHost().getModel(), tgg, trafo);
		
	}

	
}
