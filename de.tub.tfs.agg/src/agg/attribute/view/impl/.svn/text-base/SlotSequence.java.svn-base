package agg.attribute.view.impl;

import java.io.Serializable;
import java.util.Enumeration;
import java.util.Vector;

import agg.attribute.impl.AttrObject;
import agg.util.Disposable;

/**
 * @version $Id: SlotSequence.java,v 1.6 2010/09/23 08:15:32 olga Exp $
 * @author $Author: olga $
 */
public class SlotSequence extends AttrObject implements Disposable {

	protected Vector<Slot> slots = new Vector<Slot>(10, 10);

	static final long serialVersionUID = 3923744045591286915L;

	@SuppressWarnings("unused")
	private static transient int COUNTER = 0;

	public SlotSequence() {
		super();
		COUNTER++;
		// System.out.println("SlotSequence: Erzeuge Sequence #"+COUNTER);
	}

	protected void finalize() {
		COUNTER--;
		// System.out.println("SlotSequence: Loesche Sequence #"+COUNTER);
	}

	public void dispose() {
		System.out.println("SlotSequence.dispose  ");
	}

	public synchronized int getSize() {
		return this.slots.size();
	}

	public synchronized int getIndexAt(int slot) {
		// if((slot == -1) || (slot >= getSize())) return 0;
		if ((slot == -1) || (slot >= getSize()))
			return getSize() - 1;
		// System.out.println("SlotSequence::: "+slot+" "+getSize()+"
		// "+slots.size());
		return this.slots.elementAt(slot).getIndex();
	}

	public int getSlotForIndex(int index) {
		int in;
		int slot;
		Enumeration<Slot> en;
		for (en = this.slots.elements(), slot = 0; en.hasMoreElements(); slot++) {
			in = en.nextElement().getIndex();
			if (in == index)
				return slot;
		}
		return TupleFormat.HIDDEN;
	}

	public synchronized void addSlot(int index) {
		Slot newSlot = new Slot(index);
		this.slots.addElement(newSlot);
	}

	public void incrementAllGreaterThan(int index) {
		int in;
		Slot slot;

		for (Enumeration<Slot> en = this.slots.elements(); en.hasMoreElements();) {
			slot = en.nextElement();
			in = slot.getIndex();
			if (in > index)
				slot.setIndex(in + 1);
		}
	}

	public synchronized void deleteSlot(int slot) {
		if (slot >= getSize()) {
			warn("deleteSlot(): slot=" + slot + " >= size=" + getSize(), true);
			return;
		}
		this.slots.removeElementAt(slot);
	}

	public void decrementAllGreaterThan(int index) {
		int in;
		Slot slot;

		for (Enumeration<Slot> en = this.slots.elements(); en.hasMoreElements();) {
			slot = en.nextElement();
			in = slot.getIndex();
			if (in > index)
				slot.setIndex(in - 1);
		}
	}

	public void deleteSlotForIndex(int index) {
		int slot = getSlotForIndex(index);
		if (slot != TupleFormat.HIDDEN)
			deleteSlot(slot);
	}

	public void moveSlotInserting(int srcSlot, int destSlot) {		
		if (srcSlot == destSlot || srcSlot == destSlot - 1)
			return;
		int sSlot = srcSlot;
		Slot slotObj = this.slots.elementAt(sSlot);

		if (destSlot >= getSize()) {
			this.slots.addElement(slotObj);
		} else {
			this.slots.insertElementAt(slotObj, destSlot);
		}
		if (sSlot > destSlot)
			sSlot++;
		this.slots.removeElementAt(sSlot);
	}

	public String toString() {
		Slot slot;
		String log = null;
		try {
			log = "(";			
			for (int i = 0; i<this.slots.size(); i++) {
				slot = this.slots.get(i);
				log += slot.getIndex() + ",";
			}
		} catch (NullPointerException e) {
			log = "(???)";
		}
		return log;
	}

	/*
	 * private void readObject(ObjectInputStream in) throws IOException,
	 * ClassNotFoundException {
	 * AttrSession.logPrintln(AttrSession.logFileIO,"starte SlotSequence zu
	 * laden"); try{ in.defaultReadObject(); } catch(InvalidClassException ice){
	 * System.out.println("InvalidClassException seqeunce\n"); // SlotSequence s =
	 * (SlotSequence) in.readObject(); // s.addSlot(s.getSize()); //this.slots =
	 * s.slots; } }
	 */

	class Slot implements Serializable {
		protected int index;

		static final long serialVersionUID = -6821223290051933180L;

		public Slot(int index) {
			this.index = index;
		}

		public int getIndex() {
			return this.index;
		}

		public void setIndex(int index) {
			this.index = index;
			;
		}

		/*
		 * private void readObject(ObjectInputStream in) throws IOException,
		 * ClassNotFoundException {
		 * AttrSession.logPrintln(AttrSession.logFileIO,"starte Slots zu
		 * laden"); try{ in.defaultReadObject(); } catch(InvalidClassException
		 * ice){ System.out.println("InvalidClassException\n"); index = 0; } }
		 */
	}
}
/*
 * $Log: SlotSequence.java,v $
 * Revision 1.6  2010/09/23 08:15:32  olga
 * tuning
 *
 * Revision 1.5  2007/11/01 09:58:20  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.4  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.3  2007/09/10 13:05:50  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.2 2006/01/16 09:37:27 olga Extended
 * attr. setting
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:24:04 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:07 olga Imported sources
 * 
 * Revision 1.8 2000/06/15 06:58:03 shultzke toString() verbessert
 * 
 * Revision 1.7 2000/05/17 11:33:42 shultzke diverse Aenderungen. Version von
 * Olga wird erwartet
 * 
 * Revision 1.6 2000/04/05 12:11:29 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 * Revision 1.5 1999/10/05 09:05:37 earlgray *** empty log message ***
 */
