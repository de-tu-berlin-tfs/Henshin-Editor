package de.tub.tfs.muvitor.ui.utils;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.common.util.URI;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.util.BasicInternalEList;
import org.eclipse.emf.ecore.xmi.XMLHelper;
import org.eclipse.emf.ecore.xmi.impl.XMIHelperImpl;
import org.eclipse.emf.ecore.xmi.impl.XMIResourceImpl;

import de.tub.tfs.muvitor.ui.IDUtil;

public class FragmentResource extends XMIResourceImpl {

	public FragmentResource(URI uri) {
		super(uri);
	}
	
	@Override
	protected XMLHelper createXMLHelper() {
		// TODO Auto-generated method stub
		return new XMIHelperImpl(this){
			@Override
			public String getID(EObject obj) {
				if (!useUUIDs())
					return super.getID(obj);
				String id = IDUtil.getIDForModel(obj);
				return id;
			}
			
		};
	}

	protected BasicEList<EObject> contents;


	@Override
	public EList<EObject> getContents()
	{
		if (contents == null)
		{
			contents = new BasicInternalEList<EObject>(EObject.class);
		}
		return contents;
	}
	
	@Override
	protected boolean useUUIDs() {
		return true;
	}
	
	public void cleanUp() {
		getContents().clear();
		this.unload();
		
	}
	
}
