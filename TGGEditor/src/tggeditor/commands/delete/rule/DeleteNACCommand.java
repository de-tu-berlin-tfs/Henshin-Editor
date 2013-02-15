package tggeditor.commands.delete.rule;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.henshin.model.And;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Not;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.CompoundCommand;

import tggeditor.commands.delete.DeleteGraphCommand;
import tggeditor.commands.delete.DeleteTGGGraphCommand;
import tggeditor.util.SendNotify;

/**
 * The class DeleteNACCommand deletes a NAC.
 * When executed it makes all the needed changes in the tree structure of 
 * the Formula in the lhs of the rule.
 */
public class DeleteNACCommand extends CompoundCommand {
	/**
	 * NAC
	 */
	private Not nc;
	/**
	 * Container of the NAC
	 */
	private And f;
	/**
	 * lhs which contains NACs.
	 */
	private Graph lhs;
	/**
	 * And to store the NAC before it's deletion.
	 */
	private And oldF;
	/**
	 * The boolean left indicates if the NAC stands left side under And.
	 */
	private boolean left;
	/**
	 * The Rule which has the NAC.
	 */
	private Rule rule;
	
	/**
	 * Constructor
	 * @param nac
	 */
	public DeleteNACCommand(Graph nac) {
		nc = (Not) ((NestedCondition)((EObject) nac).eContainer()).eContainer();
		add(new DeleteTGGGraphCommand(nac));
	}

	/**
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {
		return (nc != null);
	}

	/**
	 * Delete the NAC and change the tree structure of Formula under the lhs.
	 * The tree structure of the Formula consists of And and Not. 
	 * And can further consist of two Not or one Not and one And.
	 * The case differentiation is:
	 * 1. NAC stands direct under the lhs
	 * 2. NAC stands under And under the lhs
	 * 3. NAC stands under And under And
	 * In each cases it's also considered if the NAC stands left or right.
	 */
	@Override
	public void execute() {
		if(nc.eContainer() instanceof Graph){
			lhs = (Graph) nc.eContainer();
			((Graph) nc.eContainer()).setFormula(null);
			SendNotify.sendRemoveFormulaNotify(lhs.eContainer(), nc);
		}else if(nc.eContainer() instanceof And){		
			f = (And) nc.eContainer();
			if(f.getLeft().equals(nc)){				
				if(f.eContainer() instanceof Graph){
					lhs = (Graph) f.eContainer();
					rule = (Rule) ((Graph)f.eContainer()).eContainer();
					((Graph) f.eContainer()).setFormula(f.getRight());
				}else if(f.eContainer() instanceof And){
					if(((And) f.eContainer()).getRight().equals(f)){
						oldF = (And) f.eContainer();
						left = false;
						((And)f.eContainer()).setRight(f.getRight());	
					}else if(((And) f.eContainer()).getLeft().equals(f)){
						oldF = (And) f.eContainer();
						left = true;
						((And)f.eContainer()).setLeft(f.getRight());						
					}
					rule = getRule(oldF);	
				}
			}else if(f.getRight().equals(nc)){
				if(f.eContainer() instanceof Graph){
					lhs = (Graph) f.eContainer();
					rule = (Rule) ((Graph) f.eContainer()).eContainer();//
					((Graph) f.eContainer()).setFormula(f.getLeft());
				}else if(f.eContainer() instanceof And){
					if(((And) f.eContainer()).getRight().equals(f)){
						oldF = (And) f.eContainer();
						left = false;
						((And)f.eContainer()).setRight(f.getLeft());						
					}else if(((And) f.eContainer()).getLeft().equals(f)){
						oldF = (And) f.eContainer();						
						left = true;
						((And)f.eContainer()).setLeft(f.getLeft());						
					}
					rule = getRule(oldF);
				}			
			}
			SendNotify.sendRemoveFormulaNotify((EObject) rule, nc);
		}	
		super.execute();
	}

	@Override
	public boolean canUndo() {
		return true;
		//return super.canUndo();
	}

	/**
	 * @see org.eclipse.gef.commands.Command#redo()
	 */
	@Override
	public void redo() {
		if(lhs != null && lhs.getFormula() != null){
			lhs.setFormula(null);
			SendNotify.sendRemoveFormulaNotify(lhs.eContainer(), nc);
		}
		else if(f != null) { 
			if (f instanceof And){		
				if(f.getLeft() != null && f.getLeft().equals(nc)){	
					if(lhs!= null && lhs instanceof Graph){	
						lhs.setFormula(f.getRight());
					}else if(oldF != null && oldF instanceof And){
						if(left == false){
							oldF.setRight(f.getRight());							
						}else if(left == true){
							oldF.setLeft(f.getRight());
						}
					}
				}else if(f.getRight() != null && f.getRight().equals(nc)){
					if(lhs!= null && lhs instanceof Graph){
						lhs.setFormula(f.getLeft());
					}else if(oldF != null && oldF instanceof And){
						if(left == false){
							oldF.setRight(f.getLeft());
						}else if(left == true){
							oldF.setLeft(f.getLeft());						
						}
					}				
				}
				SendNotify.sendRemoveFormulaNotify(rule, (EObject) nc);
			}			
		}
		super.redo();
	}

	/**
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		if(lhs != null && lhs.getFormula() ==null){
			lhs.setFormula(nc);
			SendNotify.sendAddFormulaNotify(lhs.eContainer(), nc);
		}else if(f != null) { 
			if (f instanceof And){		
				if(f.getLeft() != null && f.getLeft().equals(nc)){
					if(lhs!= null && lhs instanceof Graph){	
						f.setRight(lhs.getFormula());
						lhs.setFormula(f);
					}else if(oldF != null && oldF instanceof And){
						if(left == false){
							f.setRight(oldF.getRight());
							oldF.setRight(f);							
						}else if(left == true){
							f.setRight(oldF.getLeft());
							oldF.setLeft(f);
						}
					}
				}else if(f.getRight() != null && f.getRight().equals(nc)){
					if(lhs!= null && lhs instanceof Graph){
						f.setLeft(lhs.getFormula());
						lhs.setFormula(f);
					}else if(oldF != null && oldF instanceof And){
						if(left == false){
							f.setLeft(oldF.getRight());
							oldF.setRight(f);
						}else if(left == true){
							f.setLeft(oldF.getLeft());
							oldF.setLeft(f);						
						}
					}				
				}
				SendNotify.sendAddFormulaNotify(rule, (EObject) nc);
			}			
		}
		super.undo();
	}

	
	private Rule getRule(And and){
		Rule r = null;
		while(and.eContainer() instanceof And){
			and = (And) and.eContainer();
		}
		if(and.eContainer() instanceof Graph){
			r = (Rule) ((Graph) and.eContainer()).eContainer();
		}else{
			System.out.println("and.eContainer() ist not instanceof Graph" + and.getClass());
		}
		return r;
	}
	
}