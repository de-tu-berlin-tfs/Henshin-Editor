package agg.gui.saveload;

import java.util.Observer;
import java.util.Vector;

/**
 * This class reports the status of the load and save process. Therefore any
 * object which wants to know the status has to be registered (observer
 * pattern). As there is only one status for loading and saving the status is
 * static.
 * 
 * @author $Author: olga $
 * @version $Id: LoadSaveStatus.java,v 1.1 2008/10/29 09:04:11 olga Exp $
 */
public class LoadSaveStatus {

	/** This is the maximum which percentage can reach */
	private static int MAXIMUM = 100;

	/** This is the minimum where percentage start from */
	private static int MINIMUM = 0;

	/** This counts how much is done */
	private static int PERCENTAGE = MINIMUM;

	/** observes all its observers */
	private static Vector<Observer> OBSERVER = new Vector<Observer>();

	/** size of a small step */
	private static int SMALLSTEP = 1;

	/** size of a big step */
	private static int BIGSTEP = 2;

	/**
	 * fixes the direction if a step sums up or substracts 1 stands for summing
	 * up -1 stands for substraction
	 */
	private static int DIRECTION = 1;

	/**
	 * There must not be a instance of this class. So the constructor does
	 * nothing
	 */
	private LoadSaveStatus() {
	}

	public static void addObserver(Observer o) {
		OBSERVER.addElement(o);
	}

	/**
	 * Deletes an observer from the set of observers of this object.
	 * 
	 * @param o
	 *            the observer to be deleted.
	 */
	public static void deleteObserver(Observer o) {
		OBSERVER.removeElement(o);
	}

	/**
	 * If this object has changed, as indicated by the <code>hasChanged</code>
	 * method, then notify all of its observers and then call the
	 * <code>clearChanged</code> method to indicate that this object has no
	 * longer changed.
	 * <p>
	 * Each observer has its <code>update</code> method called with two
	 * arguments: this observable object and <code>null</code>. In other
	 * words, this method is equivalent to: <blockquote>
	 * 
	 * <pre>
	 * notifyOvservers(null)
	 * </pre>
	 * 
	 * </blockquote>
	 * 
	 * @see java.util.Observable#clearChanged()
	 * @see java.util.Observable#hasChanged()
	 * @see java.util.Observer#update(java.util.Observable, java.lang.Object)
	 */
	public static void notifyObservers() {
		for (int i = 0; i < countObservers(); i++) {
			Observer o = OBSERVER.elementAt(i);
			o.update(null, null);
		}
	}

	/**
	 * Clears the observer list so that this object no longer has any observers.
	 */
	public static void deleteObservers() {
		OBSERVER.removeAllElements();
	}

	// /**
	// * Tests if this object has changed.
	// *
	// * @return <code>true</code> if and only if the <code>setChanged</code>
	// * method has been called more recently than the
	// * <code>clearChanged</code> method on this object;
	// * <code>false</code> otherwise.
	// * @see java.util.Observable#clearChanged()
	// * @see java.util.Observable#setChanged()
	// */
	// public static boolean hasChanged() {
	// return OBSERVER.hasChanged();
	// }

	/**
	 * Returns the number of observers of this <tt>Observable</tt> object.
	 * 
	 * @return the number of observers of this object.
	 */
	public static int countObservers() {
		return OBSERVER.size();
	}

	/**
	 * sets a new maximum. This maximum must be greater or equal than the
	 * minimum. If this condition is violated the maximum will be reset to the
	 * old value. <br>
	 * If the current percentage is greater than the maximum the percentage will
	 * be adjust to the maximum.
	 */
	public static void setMaximum(int max) {
		if (max >= getMinimum())
			MAXIMUM = max;
		if (getValue() > getMaximum())
			setValue(getMaximum());
	}

	/** returns the current maximum */
	public static int getMaximum() {
		return MAXIMUM;
	}

	/**
	 * sets a new minimum. This minimum must be less or equal than the maximum.
	 * If this condition is violated the minumum will be reset to the old value.<br>
	 * If the current percentage is less than the minimum the percentage will be
	 * adjust to the minimum.
	 */
	public static void setMinimum(int min) {
		if (min <= getMaximum())
			MINIMUM = min;
		if (getValue() < getMinimum())
			setValue(getMinimum());
	}

	/** returns the current minimum */
	public static int getMinimum() {
		return MINIMUM;
	}

	/**
	 * sets the current percentage. This percentage must be less or equal than
	 * the maximum and must be greater or equal than the minimum. If this
	 * condition is violated the percentage will be reset to the old value.
	 */
	public static void setValue(int percent) {
		if (percent <= getMaximum() && percent >= getMinimum()) {
			PERCENTAGE = percent;
			LoadSaveStatus.notifyObservers();
		}
		// System.out.println(PERCENTAGE);
		// System.out.println(countObservers());
	}

	/** returns the current percentage */
	public static int getValue() {
		return PERCENTAGE;
	}

	/** sets the correct way to claculate the new result */
	private static void calcValue(int stepSize) {
		switch (DIRECTION) {
		case 1:
			if ((getValue() + stepSize) > getMaximum())
				DIRECTION = -1;
			break;
		case -1:
			if ((getValue() - stepSize) < getMinimum())
				DIRECTION = 1;
			break;
		default:
		}
		setValue(getValue() + (stepSize * DIRECTION));
	}

	/**
	 * does a small step. This means if a step will exceed the maximum a small
	 * step will substract from the current percentage. Vice versa if a step
	 * will exceed the minimum a small step will sum up. <br>
	 * By a small step the percentage will bounce between the minimum and the
	 * maximum.
	 */
	public static void smallStep() {
		calcValue(getSmallSize());
	}

	/**
	 * does a big step. This means if a step will exceed the maximum a big step
	 * will substract from the current percentage. Vice versa if a step will
	 * exceed the minimum a big step will sum up. <br>
	 * By a big step the percentage will bounce between the minimum and the
	 * maximum.
	 */
	public static void bigStep() {
		calcValue(getBigSize());
	}

	/**
	 * sets the step size of a small step. This size must be greater than zero
	 * and less or equal than the size of a big step. If not the new value won't
	 * be accepted.
	 */
	public static void setSmallSize(int small) {
		if (small > 0 && small <= getBigSize())
			SMALLSTEP = small;
	}

	/**
	 * sets the step size of a big step. This size must be greater or equal than
	 * the size of a small step and less or equal than the maximum. If not the
	 * new value won't be accepted.
	 */
	public static void setBigSize(int big) {
		if (big >= getSmallSize() && big <= getMaximum())
			BIGSTEP = big;
	}

	/** returns the size of a small step */
	public static int getSmallSize() {
		return SMALLSTEP;
	}

	/** returns the size of a big step */
	public static int getBigSize() {
		return BIGSTEP;
	}

	/** sets the percentage back to the minimum. */
	public static void reset() {
		setValue(getMinimum());
	}
}
// ======================================================================
// $Log: LoadSaveStatus.java,v $
// Revision 1.1  2008/10/29 09:04:11  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.2  2007/09/10 13:05:24  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.2 2003/03/05 18:24:18 komm
// sorted/optimized import statements
//
// Revision 1.1.1.1 2002/07/11 12:17:10 olga
// Imported sources
//
// Revision 1.1 2000/01/04 13:52:51 shultzke
// Progressbalken fuer das Laden und Speichern
// integriert.
//
