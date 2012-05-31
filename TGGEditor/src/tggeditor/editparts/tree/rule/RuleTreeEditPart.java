package tggeditor.editparts.tree.rule;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.common.notify.Notification;
import org.eclipse.emf.common.util.TreeIterator;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.EditPolicy;
import org.eclipse.jface.viewers.ICellEditorValidator;
import org.eclipse.swt.graphics.Image;

import tggeditor.TreeEditor;
import tggeditor.editpolicies.rule.RuleComponentEditPolicy;
import tggeditor.util.IconUtil;
import de.tub.tfs.muvitor.gef.directedit.IDirectEditPart;
import de.tub.tfs.muvitor.gef.editparts.AdapterTreeEditPart;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * TreeEditPart for rules.
 */
public class RuleTreeEditPart extends AdapterTreeEditPart<Rule> implements
		IDirectEditPart {

	public RuleTreeEditPart(Rule model) {
		super(model);
		registerAdapter(model.getLhs());
	}

	@Override
	protected String getText() {
		return getCastedModel().getName();
	}

	@Override
	protected List<EObject> getModelChildren() {
		List<EObject> list = new ArrayList<EObject>();
//		list.add(getCastedModel().getLhs());
		list.add(getCastedModel().getRhs());
		
		if(getCastedModel().getLhs().getFormula() != null)
		//list.add(((NestedCondition)getCastedModel().getLhs().getFormula()).getConclusion());
		list.addAll(getAllNACs());
		list.addAll(getCastedModel().getParameters());
		
		return list;
	}

	
	private List<EObject> getAllNACs() {
		Rule rule = (Rule)getModel();
		List<EObject> l = new ArrayList<EObject>();
		TreeIterator<EObject> iter = rule.getLhs().getFormula().eAllContents();
		while (iter.hasNext()) {
			EObject elem = iter.next();
			
			if (elem instanceof Graph) {
				l.add(elem);
			}
		}
		return l;
	}
	
	@Override
	protected void notifyChanged(Notification notification) {
		final int featureId = notification.getFeatureID(HenshinPackage.class);
		switch (featureId){
			case HenshinPackage.RULE__NAME:
				refreshVisuals();
			case HenshinPackage.RULE__RHS:
			case HenshinPackage.RULE__LHS:
			case HenshinPackage.RULE__MAPPINGS:
			case HenshinPackage.FORMULA:
			case HenshinPackage.RULE__PARAMETERS:
				refreshChildren();
			default:
				break; 
		}
		refreshVisuals();
		super.notifyChanged(notification);
	}

	@Override
	public int getDirectEditFeatureID() {
		return HenshinPackage.RULE__NAME;
	}

	@Override
	public ICellEditorValidator getDirectEditValidator() {
		return null;
	}

	@Override
	protected void createEditPolicies() {
		installEditPolicy(EditPolicy.COMPONENT_ROLE,
				new RuleComponentEditPolicy());
	}

	/**
	 * The methode openRuleView is called by the performOpen methode of NACTreeEditPart.
	 * It shows the NAC above and the rule below in the graphical editor.
	 * @param nac
	 */
	public void openRuleView(Graph nac){
		this.performOpen();
		TreeEditor editor = (TreeEditor) IDUtil.getHostEditor((Rule) getModel());
		editor.getRulePage((Rule) getModel()).setCurrentNac((NestedCondition) nac.eContainer());
	}
	
	@Override
	protected Image getImage() {
		try {	
			return IconUtil.getIcon("rule.png");
		} catch (Exception e) {
			return null;
		}		
	}	
}
