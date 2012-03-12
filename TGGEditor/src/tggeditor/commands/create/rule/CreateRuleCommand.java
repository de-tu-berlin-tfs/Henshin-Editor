package tggeditor.commands.create.rule;

import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.gef.commands.Command;

/**
 * The class CreateRuleCommand creates a new rule for a transformationsystem.
 */
public class CreateRuleCommand extends Command {
	
	/** transformation system in which a rule is created */
	private TransformationSystem transSys;
	/** rule */
	private Rule rule;
	/** name of a rule to create */
//	private String name;
	/**
	 * the lhs graph
	 */
	private Graph lhs;
	/**
	 * the rhs graph
	 */
	private Graph rhs;
	


	/**
	 * the constructor
	 * @param transSys the transformationsystem
	 * @param name the name for the rule
	 */
	public CreateRuleCommand(TransformationSystem transSys, String name) {
		this.transSys = transSys;
		this.rule = HenshinFactory.eINSTANCE.createRule();
//		this.name = name;
//		this.rule.setActivated(true);
		this.rule.setName(name);
		this.lhs  = HenshinFactory.eINSTANCE.createGraph();
		this.rhs = HenshinFactory.eINSTANCE.createGraph();
		lhs.setName("lhs");
		rhs.setName("rhs");
		rule.setLhs(lhs);
		rule.setRhs(rhs);

	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canExecute()
	 */
	@Override
	public boolean canExecute() {		
		return transSys != null;
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#execute()
	 */
	@Override
	public void execute() {
		transSys.getRules().add(rule);		
	}


	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#undo()
	 */
	@Override
	public void undo() {
		EList<Rule> rules = transSys.getRules();
		int index = rules.indexOf(rule);
		rules.remove(index);
		super.undo();
	}

	/* (non-Javadoc)
	 * @see org.eclipse.gef.commands.Command#canUndo()
	 */
	@Override
	public boolean canUndo() {
		return transSys != null && rule != null;
	}

}
