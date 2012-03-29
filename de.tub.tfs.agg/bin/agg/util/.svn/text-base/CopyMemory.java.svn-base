package agg.util;

import java.util.Hashtable;

/**
 * @author $Author: olga $
 * @version $Id: CopyMemory.java,v 1.2 2010/03/08 15:47:55 olga Exp $
 */
public class CopyMemory {
	private static Hashtable<Object, Object> MEMO1;

	private static Hashtable<Object, Object> MEMO2;

	public static void RESET(int n) // NEU
	{
		if (n == 1) {
			MEMO1 = null;
			return;
		}
		if (n == 2) {
			MEMO2 = null;
			return;
		} 
		System.out.println("CopyMemory.RESET:  error: illegal paramer!  n="
					+ n);
		return;
	}

	public static void UNSET() {
		if (MEMO1 != null) {
			MEMO1.clear();
			MEMO1 = null;
		}
		if (MEMO2 != null) {
			MEMO2.clear();
			MEMO2 = null;
		}
	}

	public static void INSTALL(int n) {
		if (n == 1) {
			if (MEMO1 == null) {
				MEMO1 = new Hashtable<Object, Object>();
				return;
			} 
			// System.out.println("CopyMemory: MEMO already exists!");
			return;
		}
		if (n == 2) {
			if (MEMO2 == null) {
				MEMO2 = new Hashtable<Object, Object>();
				return;
			} 
			// System.out.println("CopyMemory.INSTALL: MEMO already
			// exists!");
			return;
		} 
		System.out
					.println("CopyMemory.INSTALL:  error: illegal parameter! (n="
							+ n);
		return;
	}

	public static void STORE(Object one, Object two, int n) {
		if ((n == 1) && (MEMO1 != null)) {
			MEMO1.put(one, two);
			return;
		}
		if ((n == 2) && (MEMO2 != null)) {
			MEMO2.put(one, two);
			return;
		}
		System.out
					.println("CopyMemory.STORE:  error: illegal parameter or MEMO undefined!");
		return;
	}

	public static Object SELECT(Object o, int n) {
		if ((n == 1) && (MEMO1 != null)) {
			return (MEMO1.get(o));
		}
		if ((n == 2) && (MEMO2 != null)) {
			return (MEMO2.get(o));
		}
		System.out
					.println("CopyMemory.SELECT:  error: illegal parameter or MEMO undefined!");
		return null;
	}

	public static Hashtable<Object, Object> REQUEST(int n) {
		if ((n == 1) && (MEMO1 != null)) {
			return (MEMO1);
		}
		if ((n == 2) && (MEMO2 != null)) {
			return (MEMO2);
		}
		System.out
					.println("CopyMemory.REQUEST:  error: illegal parameter or MEMO undefined!");
		return null;
	}

}

// $Log: CopyMemory.java,v $
// Revision 1.2  2010/03/08 15:47:55  olga
// code optimizing
//
// Revision 1.1  2009/05/12 10:37:00  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.4  2007/11/01 09:58:16  olga
// Code refactoring: generic types- done
//
// Revision 1.3  2007/09/10 13:05:34  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.2 2005/10/10 08:05:16 olga
// Critical Pair GUI and CPA graph
//
// Revision 1.1 2005/08/25 11:56:54 enrico
// *** empty log message ***
//
// Revision 1.2 2005/06/20 13:37:03 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.1.1.1 2002/07/11 12:17:27 olga
// Imported sources
//
// Revision 1.5 2001/03/22 15:54:16 olga
// Testausgaben .
//
// Revision 1.4 2001/02/21 15:46:32 olga
// Beseitigung von Type Fehlern.
//
// Revision 1.3 1999/10/11 10:23:22 shultzke
// kleine Bugfixes
//
// Revision 1.2 1999/10/08 12:42:23 stefan
// -------------
// neu: RESET(n)
// -------------
//
// Revision 1.1 1999/10/04 13:59:32 stefan
// ------------------------------------------
// NEU! Wird benoetigt von den Methoden
// "graphcopy(n)" und "morphcopy()" in
// den Klassen Graph bzw. OrdinaryMorphism.
// Solche Kopiermethoden werden insbesondere
// im Projekt DISAGG (Kay A.) verwendet.
// -----------------------------------------
//

