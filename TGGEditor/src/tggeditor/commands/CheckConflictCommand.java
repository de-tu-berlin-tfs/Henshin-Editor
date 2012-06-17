package tggeditor.commands;

import java.util.List;

import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.TransformationSystem;
import org.eclipse.emf.henshin.model.TransformationUnit;
import org.eclipse.gef.commands.Command;

import de.tub.tfs.henshin.analysis.AggInfo;
import de.tub.tfs.henshin.analysis.CriticalPair;

public class CheckConflictCommand extends Command {

	private Rule _firstRule;
	private Rule _secondRule;
	private TransformationSystem _trafo;

	public CheckConflictCommand(Rule firstRule, Rule secondRule) {
		_firstRule = firstRule;
		_secondRule = secondRule;
		_trafo = _firstRule.getTransformationSystem();
	}
	
	@Override
	public boolean canExecute() {
		return (_firstRule != null && _secondRule != null && _trafo != null);
	}
	
	
	@Override
	public void execute() {
		AggInfo aggInfo = new AggInfo(_trafo);
		aggInfo.isCritical(_firstRule, _secondRule);
		List<CriticalPair> critPairList = aggInfo.getConflictOverlappings(_firstRule, _secondRule);
		System.out.println(critPairList.get(0).getOverlapping().getName());
		System.out.println("Checking "+_firstRule.getName()+" with "+_secondRule.getName());
		super.execute();
	}
}
