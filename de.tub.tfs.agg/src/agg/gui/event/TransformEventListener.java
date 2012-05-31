package agg.gui.event;

import java.util.EventListener;

public interface TransformEventListener extends EventListener {

	/**
	 * Transformation event occurred
	 */
	public void transformEventOccurred(TransformEvent e);
}
