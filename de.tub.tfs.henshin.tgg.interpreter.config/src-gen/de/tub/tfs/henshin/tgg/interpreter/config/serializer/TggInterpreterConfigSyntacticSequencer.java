package de.tub.tfs.henshin.tgg.interpreter.config.serializer;

import com.google.inject.Inject;
import de.tub.tfs.henshin.tgg.interpreter.config.services.TggInterpreterConfigGrammarAccess;
import java.util.List;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.IGrammarAccess;
import org.eclipse.xtext.RuleCall;
import org.eclipse.xtext.nodemodel.INode;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.AbstractElementAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.GroupAlias;
import org.eclipse.xtext.serializer.analysis.GrammarAlias.TokenAlias;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynNavigable;
import org.eclipse.xtext.serializer.analysis.ISyntacticSequencerPDAProvider.ISynTransition;
import org.eclipse.xtext.serializer.sequencer.AbstractSyntacticSequencer;

@SuppressWarnings("all")
public class TggInterpreterConfigSyntacticSequencer extends AbstractSyntacticSequencer {

	protected TggInterpreterConfigGrammarAccess grammarAccess;
	protected AbstractElementAlias match_ProcessingEntry_CommaKeyword_7_q;
	protected AbstractElementAlias match_ProcessingEntry_NLTerminalRuleCall_0_q;
	protected AbstractElementAlias match_ProcessingEntry_NLTerminalRuleCall_4_q;
	protected AbstractElementAlias match_ProcessingEntry_NLTerminalRuleCall_6_q;
	protected AbstractElementAlias match_TggInterpreterConfig_NLTerminalRuleCall_0_q;
	protected AbstractElementAlias match_TggInterpreterConfig___NLTerminalRuleCall_1_3_AdditionalOptionsKeyword_1_0_EqualsSignKeyword_1_1__q;
	
	@Inject
	protected void init(IGrammarAccess access) {
		grammarAccess = (TggInterpreterConfigGrammarAccess) access;
		match_ProcessingEntry_CommaKeyword_7_q = new TokenAlias(false, true, grammarAccess.getProcessingEntryAccess().getCommaKeyword_7());
		match_ProcessingEntry_NLTerminalRuleCall_0_q = new TokenAlias(false, true, grammarAccess.getProcessingEntryAccess().getNLTerminalRuleCall_0());
		match_ProcessingEntry_NLTerminalRuleCall_4_q = new TokenAlias(false, true, grammarAccess.getProcessingEntryAccess().getNLTerminalRuleCall_4());
		match_ProcessingEntry_NLTerminalRuleCall_6_q = new TokenAlias(false, true, grammarAccess.getProcessingEntryAccess().getNLTerminalRuleCall_6());
		match_TggInterpreterConfig_NLTerminalRuleCall_0_q = new TokenAlias(false, true, grammarAccess.getTggInterpreterConfigAccess().getNLTerminalRuleCall_0());
		match_TggInterpreterConfig___NLTerminalRuleCall_1_3_AdditionalOptionsKeyword_1_0_EqualsSignKeyword_1_1__q = new GroupAlias(false, true, new TokenAlias(false, false, grammarAccess.getTggInterpreterConfigAccess().getNLTerminalRuleCall_1_3()), new TokenAlias(false, false, grammarAccess.getTggInterpreterConfigAccess().getAdditionalOptionsKeyword_1_0()), new TokenAlias(false, false, grammarAccess.getTggInterpreterConfigAccess().getEqualsSignKeyword_1_1()));
	}
	
	@Override
	protected String getUnassignedRuleCallToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if(ruleCall.getRule() == grammarAccess.getNLRule())
			return getNLToken(semanticObject, ruleCall, node);
		return "";
	}
	
	/**
	 * terminal NL :         ('\r'|'\n')+;
	 */
	protected String getNLToken(EObject semanticObject, RuleCall ruleCall, INode node) {
		if (node != null)
			return getTokenText(node);
		return "\r";
	}
	
	@Override
	protected void emitUnassignedTokens(EObject semanticObject, ISynTransition transition, INode fromNode, INode toNode) {
		if (transition.getAmbiguousSyntaxes().isEmpty()) return;
		List<INode> transitionNodes = collectNodes(fromNode, toNode);
		for (AbstractElementAlias syntax : transition.getAmbiguousSyntaxes()) {
			List<INode> syntaxNodes = getNodesFor(transitionNodes, syntax);
			if(match_ProcessingEntry_CommaKeyword_7_q.equals(syntax))
				emit_ProcessingEntry_CommaKeyword_7_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_ProcessingEntry_NLTerminalRuleCall_0_q.equals(syntax))
				emit_ProcessingEntry_NLTerminalRuleCall_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_ProcessingEntry_NLTerminalRuleCall_4_q.equals(syntax))
				emit_ProcessingEntry_NLTerminalRuleCall_4_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_ProcessingEntry_NLTerminalRuleCall_6_q.equals(syntax))
				emit_ProcessingEntry_NLTerminalRuleCall_6_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_TggInterpreterConfig_NLTerminalRuleCall_0_q.equals(syntax))
				emit_TggInterpreterConfig_NLTerminalRuleCall_0_q(semanticObject, getLastNavigableState(), syntaxNodes);
			else if(match_TggInterpreterConfig___NLTerminalRuleCall_1_3_AdditionalOptionsKeyword_1_0_EqualsSignKeyword_1_1__q.equals(syntax))
				emit_TggInterpreterConfig___NLTerminalRuleCall_1_3_AdditionalOptionsKeyword_1_0_EqualsSignKeyword_1_1__q(semanticObject, getLastNavigableState(), syntaxNodes);
			else acceptNodes(getLastNavigableState(), syntaxNodes);
		}
	}

	/**
	 * Syntax:
	 *     ','?
	 */
	protected void emit_ProcessingEntry_CommaKeyword_7_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     NL?
	 */
	protected void emit_ProcessingEntry_NLTerminalRuleCall_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     NL?
	 */
	protected void emit_ProcessingEntry_NLTerminalRuleCall_4_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     NL?
	 */
	protected void emit_ProcessingEntry_NLTerminalRuleCall_6_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     NL?
	 */
	protected void emit_TggInterpreterConfig_NLTerminalRuleCall_0_q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
	/**
	 * Syntax:
	 *     (NL 'AdditionalOptions' '=')?
	 */
	protected void emit_TggInterpreterConfig___NLTerminalRuleCall_1_3_AdditionalOptionsKeyword_1_0_EqualsSignKeyword_1_1__q(EObject semanticObject, ISynNavigable transition, List<INode> nodes) {
		acceptNodes(transition, nodes);
	}
	
}
