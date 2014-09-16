/*******************************************************************************
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.common.notify.Notification;

/**
 * The Interface HenshinNotification.
 */
public interface HenshinNotification extends Notification {

	/** The EXECUTED. */
	int EXECUTED = 11;
	
	/** The TRANSFORMATIO n_ undo. */
	int TRANSFORMATION_UNDO = 12;
	
	/** The TRANSFORMATIO n_ redo. */
	int TRANSFORMATION_REDO = 13;
	
	/** AND -> OR, OR -> AND */
	int BINARY_FORMULA_SWAP = 21;
}
