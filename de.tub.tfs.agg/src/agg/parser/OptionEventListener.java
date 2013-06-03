package agg.parser;

import java.util.EventListener;
import java.util.EventObject;

/**
 * Listens for events from several options.
 */
public interface OptionEventListener extends EventListener {

	public void optionEventOccurred(EventObject e);

}
