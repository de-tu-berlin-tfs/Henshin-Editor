package tggeditor.editparts.tree;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.impl.EPackageImpl;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import tgg.TGG;
import tggeditor.editpolicies.ImportedModellEditPolicy;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;

/**
 * EditPart for imported models in the tree editor.
 */
public class ImportTreeEditPart extends AdapterTreeEditPart<EPackage> implements
IDirectEditPart{
	private TGG tgg;
	
	public ImportTreeEditPart(EPackage model, TGG tgg) {
		super(model);
		this.tgg = tgg;
	}
	
	@Override
	protected String getText() {
		if(getCastedModel() == null){
			return "";
		}				
		//String name;
		//getCastedModel().eResource().getResourceSet().getResources();
	
		if(tgg.getSource() != null && tgg.getSource().equals(getCastedModel()))
		 return "sourceModell";
		if(tgg.getTarget() != null && tgg.getTarget().equals(getCastedModel()))
		 	return	"targetModell";
		if(tgg.getCorresp() != null && tgg.getCorresp().equals(getCastedModel()))
			return "corrModell";
		if (((EPackage) getCastedModel()).getName() == null){
			return ((EPackageImpl)getCastedModel()).eProxyURI() + " not found!";
		}
		return ((EPackage) getCastedModel()).getName();
	}


	@Override
	public int getDirectEditFeatureID() {		
		return HenshinPackage.NAMED_ELEMENT;		// ????????????????
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	protected void notifyChanged(Notification notification) {
		// TODO Auto-generated method stub
		super.notifyChanged(notification);
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE, 
				new ImportedModellEditPolicy(tgg));

	}
	
	@Override
	protected Image getImage() {
		try {
			return IconUtil.getIcon("epackage16.png");
		} catch (Exception e) {
			return null;
		}
	}
	
}
