package de.tub.tfs.henshin.tggeditor.editparts.tree.graphical;

import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.ecore.impl.ENotificationImpl;
import org.eclipse.emf.ecore.impl.EObjectImpl;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;

/**
 * A folder for graphs in the tree editor.
 */

public class GraphFolder extends EObjectImpl {
	private Module sys;
	private List<Graph> graphs;
	
	@Override
	public EClass eClass() {
		return HenshinPackage.Literals.MODULE;
	}
	
	@Override
	public Resource eResource() {
		// TODO Auto-generated method stub
		return sys.eResource();
	}
	
	@Override
	public Object eGet(EStructuralFeature eFeature, boolean resolve, boolean coreType)
	{
		if (eFeature.equals(HenshinPackage.Literals.MODULE__INSTANCES)){
			return sys.eGet(eFeature, resolve);
		}
		int featureID = eDerivedStructuralFeatureID(eFeature);
		if (featureID >= 0)
		{
			return eGet(featureID, resolve, coreType);
		}
		else
		{
			return eOpenGet(eFeature, resolve);
		}
	}
	public GraphFolder(Module sys){
		this.sys = sys;
		graphs = this.sys.getInstances();		
	}

	public List<Graph> getGraphs(){
		return this.graphs;
	}

	public void update() {
		graphs = this.sys.getInstances();
		eNotify(new ENotificationImpl(this, Notification.ADD, 0, null, null));
	}
	
}
