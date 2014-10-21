package agg.gui.parser.event;

import java.util.EventListener;

/**
 * Listens for events from several options.
 */
public interface OptionListener extends EventListener {

	public void optionEventOccurred(OptionEvent e);

}
