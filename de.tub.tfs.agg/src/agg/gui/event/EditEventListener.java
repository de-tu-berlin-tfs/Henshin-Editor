package agg.gui.event;

import java.util.EventListener;

public interface EditEventListener extends EventListener {

	/**
	 * EditEvent occurred
	 */
	public void editEventOccurred(EditEvent e);
}
