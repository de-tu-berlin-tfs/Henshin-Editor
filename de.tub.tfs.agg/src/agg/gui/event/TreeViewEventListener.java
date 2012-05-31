package agg.gui.event;

import java.util.EventListener;

public interface TreeViewEventListener extends EventListener {

	/**
	 * TreeViewEvent occurred
	 */
	public void treeViewEventOccurred(TreeViewEvent e);
}
