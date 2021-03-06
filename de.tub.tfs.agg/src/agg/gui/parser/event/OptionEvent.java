package agg.gui.parser.event;

import java.util.EventObject;

/**
 * These events are raisen from the option.
 */
@SuppressWarnings("serial")
public class OptionEvent extends EventObject {

	/**
	 * Creates a new event for the option.
	 * 
	 * @param source
	 *            The source of the event.
	 */
	public OptionEvent(Object source) {
		super(source);
	}
}
