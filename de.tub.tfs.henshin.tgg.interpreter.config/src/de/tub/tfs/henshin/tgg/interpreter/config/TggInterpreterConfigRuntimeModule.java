/*
 * generated by Xtext
 */
package de.tub.tfs.henshin.tgg.interpreter.config;

import org.eclipse.xtext.conversion.IValueConverterService;

/**
 * Use this class to register components to be used at runtime / without the Equinox extension registry.
 */
public class TggInterpreterConfigRuntimeModule extends AbstractTggInterpreterConfigRuntimeModule {
	@Override
	public Class<? extends IValueConverterService> bindIValueConverterService() {
		return SPELLValueConverterService.class;
	}
}
