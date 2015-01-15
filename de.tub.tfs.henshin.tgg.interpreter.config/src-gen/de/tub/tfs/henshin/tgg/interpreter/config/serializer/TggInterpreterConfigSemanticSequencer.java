package de.tub.tfs.henshin.tgg.interpreter.config.serializer;

import com.google.inject.Inject;
import com.google.inject.Provider;
import de.tub.tfs.henshin.tgg.interpreter.config.services.TggInterpreterConfigGrammarAccess;
import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfig;
import de.tub.tfs.henshin.tgg.interpreter.config.tggInterpreterConfig.TggInterpreterConfigPackage;
import org.eclipse.emf.common.util.BasicEMap.Entry;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.xtext.serializer.acceptor.ISemanticSequenceAcceptor;
import org.eclipse.xtext.serializer.acceptor.SequenceFeeder;
import org.eclipse.xtext.serializer.diagnostic.ISemanticSequencerDiagnosticProvider;
import org.eclipse.xtext.serializer.diagnostic.ISerializationDiagnostic.Acceptor;
import org.eclipse.xtext.serializer.sequencer.AbstractDelegatingSemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.GenericSequencer;
import org.eclipse.xtext.serializer.sequencer.ISemanticNodeProvider.INodesForEObjectProvider;
import org.eclipse.xtext.serializer.sequencer.ISemanticSequencer;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService;
import org.eclipse.xtext.serializer.sequencer.ITransientValueService.ValueTransient;

@SuppressWarnings("all")
public class TggInterpreterConfigSemanticSequencer extends AbstractDelegatingSemanticSequencer {

	@Inject
	private TggInterpreterConfigGrammarAccess grammarAccess;
	
	public void createSequence(EObject context, EObject semanticObject) {
		if(semanticObject.eClass().getEPackage() == TggInterpreterConfigPackage.eINSTANCE) switch(semanticObject.eClass().getClassifierID()) {
			case TggInterpreterConfigPackage.PROCESSING_MAP_ENTRY:
				if(context == grammarAccess.getProcessingEntryRule()) {
					sequence_ProcessingEntry(context, (Entry<?, ?>) semanticObject); 
					return; 
				}
				else break;
			case TggInterpreterConfigPackage.TGG_INTERPRETER_CONFIG:
				if(context == grammarAccess.getTggInterpreterConfigRule()) {
					sequence_TggInterpreterConfig(context, (TggInterpreterConfig) semanticObject); 
					return; 
				}
				else break;
			}
		if (errorAcceptor != null) errorAcceptor.accept(diagnosticProvider.createInvalidContextOrTypeDiagnostic(semanticObject, context));
	}
	
	/**
	 * Constraint:
	 *     (key=keyValue value=ScriptOrValue)
	 */
	protected void sequence_ProcessingEntry(EObject context, Entry<?, ?> semanticObject) {
		if(errorAcceptor != null) {
			if(transientValues.isValueTransient((EObject)semanticObject, TggInterpreterConfigPackage.Literals.PROCESSING_MAP_ENTRY__KEY) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing((EObject)semanticObject, TggInterpreterConfigPackage.Literals.PROCESSING_MAP_ENTRY__KEY));
			if(transientValues.isValueTransient((EObject)semanticObject, TggInterpreterConfigPackage.Literals.PROCESSING_MAP_ENTRY__VALUE) == ValueTransient.YES)
				errorAcceptor.accept(diagnosticProvider.createFeatureValueMissing((EObject)semanticObject, TggInterpreterConfigPackage.Literals.PROCESSING_MAP_ENTRY__VALUE));
		}
		INodesForEObjectProvider nodes = createNodeProvider((EObject)semanticObject);
		SequenceFeeder feeder = createSequencerFeeder((EObject)semanticObject, nodes);
		feeder.accept(grammarAccess.getProcessingEntryAccess().getKeyKeyValueParserRuleCall_1_0(), semanticObject.getKey());
		feeder.accept(grammarAccess.getProcessingEntryAccess().getValueScriptOrValueParserRuleCall_5_0(), semanticObject.getValue());
		feeder.finish();
	}
	
	
	/**
	 * Constraint:
	 *     options+=ProcessingEntry*
	 */
	protected void sequence_TggInterpreterConfig(EObject context, TggInterpreterConfig semanticObject) {
		genericSequencer.createSequence(context, semanticObject);
	}
}
