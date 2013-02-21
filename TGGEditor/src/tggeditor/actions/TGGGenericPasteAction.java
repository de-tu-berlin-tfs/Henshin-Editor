/**
 * 
 */
package tggeditor.actions;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.resource.Resource;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.gef.commands.Command;
import org.eclipse.ui.IWorkbenchPart;

import tgg.EdgeLayout;
import tgg.GraphLayout;
import tgg.NodeLayout;
import tgg.TGG;
import tggeditor.PasteHenshinRules;
import tggeditor.util.ModelUtil;

/**
 * @author Johann
 * 
 */
public class TGGGenericPasteAction extends GenericPasteAction {

	/**
	 * @param part
	 */
	public TGGGenericPasteAction(IWorkbenchPart part) {
		super(part);
		registerPasteRule(HenshinPackage.Literals.RULE, new PasteHenshinRules());
		// default rule for named elements

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see muvitorkit.actions.GenericPasteAction#run()
	 */
	@Override
	public void run() {
//		if (targetModel instanceof Module) {
//			Collection<?> clipboardContents = (Collection<?>) Clipboard
//					.getDefault().getContents();
//			boolean hasRule=false;
//			boolean hasOther=false;
//			for (final Object copy : clipboardContents) {
//				if (copy instanceof Rule) {
//					hasRule=true;
//				}
//				else{
//					hasOther=true;
//				}
//			}
////			if (hasOther && hasRule){
////				return;
////			}
////			else{
//				if (hasRule){
//					List<Rule> rules=new ArrayList<Rule>();
//					for (final Object copy : clipboardContents) {
//						Copier c=new Copier();
//						rules.add((Rule) c.copy((EObject) copy));
//						c.copyReferences();
//					}
//
//					command = new PasteRuleCommand((Module)targetModel,
//							rules);
//				}
////			}
//		}
		PasteCommand command = ((PasteCommand)super.command);
		super.run();

		TGG layout = null;
		outer: for(Resource res :targetModel.eResource().getResourceSet().getResources()){
			for (EObject obj : res.getContents()){
				if (obj instanceof TGG){
					layout = (TGG) obj;
//					System.out.println("set TGG for layout");
					break outer;
				}
			}
		}

		if (layout == null)
			return;


		Collection<EObject> layoutElements = command.getFailedPastedElements();

		for (EObject eObject : layoutElements) {
			
			if (eObject instanceof GraphLayout) {
				for (Iterator<GraphLayout> iterator = layout.getGraphlayouts().iterator(); iterator.hasNext();) {
					GraphLayout graphLayout = iterator.next();
					if (graphLayout.getGraph().equals(((GraphLayout) eObject).getGraph())){
						if ((graphLayout.isIsSC() && ((GraphLayout) eObject).isIsSC())
								|| (!graphLayout.isIsSC() && !((GraphLayout) eObject).isIsSC())) {
							graphLayout.setDividerX(((GraphLayout) eObject).getDividerX());
							graphLayout.setMaxY(((GraphLayout) eObject).getMaxY());
						}
					}
				}
			}
			
			if (eObject instanceof NodeLayout){
				for (Iterator<NodeLayout> iterator = layout.getNodelayouts().iterator(); iterator
						.hasNext();) {
					NodeLayout nodeLayout = iterator.next();
					if (nodeLayout.getNode().equals(((NodeLayout) eObject).getNode())){
						nodeLayout.setRhsTranslated(((NodeLayout) eObject).getRhsTranslated());
						nodeLayout.setLhsTranslated(((NodeLayout) eObject).getLhsTranslated());
												
						nodeLayout.setX(((NodeLayout) eObject).getX());
						nodeLayout.setY(((NodeLayout) eObject).getY());
						nodeLayout.setNew(((NodeLayout) eObject).isNew());
						
						if (!nodeLayout.isNew()) {
							nodeLayout.setLhsnode(((NodeLayout) eObject).getLhsnode());	
						}
					}
				}
			}
			
			if (eObject instanceof EdgeLayout) {
				for (Iterator<EdgeLayout> iterator = layout.getEdgelayouts().iterator(); iterator
						.hasNext();) {
					EdgeLayout edgeLayout = iterator.next();
					System.out.println("hier");
					System.out.println(edgeLayout.getRhsedge() == null);
					if (edgeLayout.getRhsedge() != null && edgeLayout.getRhsedge().equals(((EdgeLayout) eObject).getRhsedge())){
						edgeLayout.setNew(((EdgeLayout) eObject).isNew());
						if (((EdgeLayout) eObject).getLhsTranslated() != null)
						edgeLayout.setLhsTranslated(((EdgeLayout) eObject).getLhsTranslated());
						if (((EdgeLayout) eObject).getRhsTranslated() != null)
						edgeLayout.setRhsTranslated(((EdgeLayout) eObject).getRhsTranslated());
						if (!edgeLayout.isNew()) {
							edgeLayout.setLhsedge(((EdgeLayout) eObject).getLhsedge());
						}
					}
				}
			}
		}
	}

	public class PasteRuleCommand extends Command {

		private Module targetModel;

		private List<Rule> clipboardContents;

		/**
		 * @param targetModel
		 * @param clipboardContents
		 */
		public PasteRuleCommand(Module targetModel,
				List<Rule> clipboardContents) {
			this.targetModel=targetModel;
			this.clipboardContents=clipboardContents;
		}

		/* (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#execute()
		 */
		@Override
		public void execute() {
			List<Rule> rules=new ArrayList<Rule>();
			Iterator<Rule> iter = clipboardContents.iterator();
			while(iter.hasNext()){
				Rule rule= iter.next();
				String newName=ModelUtil.getNewChildDistinctName(targetModel, HenshinPackage.MODULE__UNITS, "Copy_", "_"+rule.getName());
				rule.setName(newName);
				rules.add(rule);
			}
			targetModel.getUnits().addAll(rules);
		}

		/* (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#undo()
		 */
		@Override
		public void undo() {
			for (Rule copy : clipboardContents) {
				targetModel.getUnits().remove(copy);
			}
		}

		/* (non-Javadoc)
		 * @see org.eclipse.gef.commands.Command#redo()
		 */
		@Override
		public void redo() {
			for (Rule copy : clipboardContents) {
				targetModel.getUnits().add(copy);
			}
		}



	}
}
