package agg.gui.event;

import java.util.EventListener;

public interface LoadEventListener extends EventListener {

	/**
	 * LoadEvent occurred
	 */
	public void loadEventOccurred(LoadEvent e);
}
