package agg.util;

import java.io.FileOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.File;
import java.util.HashMap;
import java.util.Iterator;

import agg.attribute.AttrInstance;
import agg.xt_basis.Arc;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Morphism;
import agg.xt_basis.Node;

/**
 * This class provides methods for debugging output. There should be no runtime
 * overhead if <code>DEBUG</code> is set to <code>false</code> and inlining
 * is activated by using the "-O" switch for <code>javac</code>.
 * 
 * @author $Author: olga $
 * @version $Id: Debug.java,v 1.6 2010/03/04 14:13:00 olga Exp $
 */
public final class Debug {

	/** Don't make instances of this class */
	private Debug() {
		// ein privater Konstruktor verhindert das Erzeugen von Objekten
		// von dritten.
	}

	/**
	 * Set this flag to <code>false</code> to suppress all debug output. This
	 * flag is the master flag. It activates the output in general. It doesn't
	 * matter if any other flag is set to <code>true</code>.
	 */
	public static boolean DEBUG = false;

	/** Activates the debug output of the <code>findAbstraction</code> process */
	public static boolean ABSTRACTION = false;

	/** Activates the debug output for some parsing stuff */
	public static boolean PARSING = false;

	/**
	 * Will be used only for debug purposes to set current object hashcode
	 * (Object.hashCode()) of graph objects. A new attribute member String
	 * HASHCODE will be added to the attribute of a graph object and its value
	 * will be set to hashcode. The name "HASHCODE" is reserved for debugging.
	 * Do not use this name for an attribute member. This attribute member will
	 * be ignored during graph transformation. Do not save grammar if HASHCODE
	 * is true!
	 */
	public static boolean HASHCODE = false;

	public static boolean MATCH = false;

	public static boolean CSP_VAR = false;

	/**
	 * Write <code>msg</code> to standard output, succeeded by a newline
	 * character.
	 */
	public final static void println(String msg) {
		if (DEBUG) {
			System.out.println(msg);
		}
	}

	/** Write <code>msg</code> to standard output. */
	public final static void print(String msg) {
		if (DEBUG) {
			System.out.print(msg);
		}
	}

	/**
	 * Write <code>msg</code> to standard output, preceeded by the class name
	 * of <code>obj</code>, and succeeded by a newline character.
	 */
	public final static void println(String msg, Object obj) {
		if (DEBUG) {
			System.out.println(obj.getClass().getName() + ": " + msg);
		}
	}

	/**
	 * Write <code>msg</code> to standard output, preceeded by the class name
	 * of <code>obj</code>.
	 */
	public final static void print(String msg, Object obj) {
		if (DEBUG)
			System.out.print(obj.getClass().getName() + ": " + msg);
	}

	/**
	 * Writes <code>msg</code> to standard output, preceeded by the class name
	 * of <code>obj</code>, and succeeded by a newline character.
	 * Additionally it takes care if the debug <code>topic</code> is set.
	 */
	public final static void println(String msg, Object obj, boolean topic) {
		if (topic)
			// println(msg,obj);
			System.out.println(obj.getClass().getName() + ": " + msg);
	}

	/**
	 * Prints a graph with all nodes and edges
	 * 
	 * @param printGraph
	 *            The graph will be printed
	 * @param name
	 *            Just a short word so it is easier to identify the beginning
	 *            and end
	 * @param topic
	 *            takes care if the debug is set.
	 */
	public static void printlnGraph(Graph printGraph, String name, boolean topic) {
		if (topic)
			printlnGraph(printGraph, name);
	}

	private static HashMap<Object, Integer> hash = null;

	private static int new_id;

	private static void reset() {
		hash = new HashMap<Object, Integer>();
		new_id = 1;
	}

	private static int get_id(java.lang.Object o) {
		if (hash == null)
			return 0;
		Integer i = hash.get(o);
		if (i == null) {
			i = new Integer(new_id++);
			hash.put(o, i);
		}
		return i.intValue();
	}

	public static void printlnGraph(Graph g, String name) {
		printlnGraph2(g, name, false);
	}

	public static void printlnNode(GraphObject o) {
		System.out.print(o.getType().getStringRepr() + ": (");
		if (o.getAttribute() != null)
			printAttributes(o.getAttribute());
		System.out.println(")");
	}

	/**
	 * Prints a graph with all nodes and edges
	 * 
	 * @param agg.xt_basis.Graph#printGraph
	 *            The graph which will be printed
	 * @param name
	 *            Just a short word so it is easier to identify the beginning
	 *            and end
	 */
	private static void printlnGraph2(Graph printGraph, String name,
			boolean for_morph) {
		Iterator<Node> nodes = printGraph.getNodesSet().iterator();
		Node node;
		Arc arc;
		Node src, tar;

		System.out.println("--------------- Graph: " + name
				+ " beginns ---------------");
		if (!for_morph)
			System.out.println();

		while (nodes.hasNext()) {
			node = nodes.next();
			System.out.print(get_id(node) + " "
					+ node.getType().getStringRepr() + ": (");
			if (node.getAttribute() != null)
				printAttributes(node.getAttribute());
			System.out.println(") ");

			Iterator<Arc> incoming = node.getIncomingArcsSet().iterator();		
			System.out.print("  incoming: ");
			while (incoming.hasNext()) {
				arc = incoming.next();
				src = (Node) arc.getSource();
				System.out.print("(" + get_id(arc) + " "
							+ arc.getType().getStringRepr() + " <--  "
							+ get_id(src) + " " + src.getType().getStringRepr()
							+ "[ ");
				if (arc.getAttribute() != null)
					printAttributes(arc.getAttribute());
				System.out.print("]) ");
			}
			System.out.println();

			Iterator<Arc> outgoing = node.getOutgoingArcsSet().iterator();
			if (outgoing.hasNext()) {
				System.out.print("  outgoing: ");
				while (outgoing.hasNext()) {
					arc = outgoing.next();
					tar = (Node) arc.getTarget();
					System.out.print("(" + get_id(arc) + " "
							+ arc.getType().getStringRepr() + " --> "
							+ get_id(tar) + " " + tar.getType().getStringRepr()
							+ "[ ");
					if (arc.getAttribute() != null)
						printAttributes(arc.getAttribute());
					System.out.print("]) ");
				}
				System.out.println();
			}
		}

		if (!for_morph)
			System.out.println("--------------- Graph: " + name
					+ " ends ---------------\n");
	}

	private static void printAttributes(AttrInstance attribute) {
		for (int i = 0; i < attribute.getNumberOfEntries(); i++) {
			System.out.print(attribute.getNameAsString(i) + "=");
			System.out.print(attribute.getValueAsString(i) + " ");
		}
	}

	/**
	 * Prints a graph with all nodes and edges
	 * 
	 * @param morph
	 *            The morphism which will be printed
	 * @param name
	 *            Just a short word so it is easier to identify the beginning
	 *            and end
	 * @param topic
	 *            takes care if the debug is set.
	 */
	public static void printlnMorph(Morphism morph, String name, boolean topic) {
		if (topic)
			printlnMorph(morph, name);
	}

	/**
	 * Prints a graph with all nodes and edges
	 * 
	 * @param morph
	 *            The morphism which will be printed
	 * @param name
	 *            Just a short word so it is easier to identify the beginning
	 *            and end
	 */
	public static void printlnMorph(Morphism morph, String name) {
		Iterator<Node> nodes;
		Node node, mappedNode;
		Graph srcG = morph.getOriginal(), tarG = morph.getImage();

		reset();
		System.out.println("--------------- Morphism: " + name
				+ " beginns ---------------");
		printlnGraph2(srcG, "source graph", true);
		printlnGraph2(tarG, "target graph", true);
		System.out.println("  ---- mappings ----");

		System.out.println("Nodes: ");
		nodes = srcG.getNodesSet().iterator();
		while (nodes.hasNext()) {
			node = nodes.next();
			System.out.print("  source: " + get_id(node) + " "
					+ node.getType().getStringRepr() + ": (");
			if (node.getAttribute() != null)
				printAttributes(node.getAttribute());
			System.out.print(") --> ");
			mappedNode = (Node) morph.getImage(node);
			if (mappedNode != null) {
				System.out.print("target: " + get_id(mappedNode) + " "
						+ mappedNode.getType().getStringRepr() + ": (");
				if (mappedNode.getAttribute() != null)
					printAttributes(mappedNode.getAttribute());
				System.out.println(")");
			} else {
				System.out.println("no target");
			}
			// TODO: Morphismus fuer die Kanten ausgeben.
		}

		System.out.println("--------------- Morphism: " + name
				+ " ends ---------------\n");
		hash = null;
	}

	// test output in a file
	private static File f;

	private static FileOutputStream os;

	public static void openFile(String fname, String text) {
		// System.out.println("ExcludePair.openFile : "+fname);
		// System.out.println(text);
		f = new File(fname);
		try {
			os = new FileOutputStream(f);
			try {
				os.write(text.getBytes());
				os.write('\n');
				try {
					os.close();
				} catch (IOException ex) {
					ex.printStackTrace();
				}
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
	}

	public static void closeFile(String fname) {
		if (os == null)
			return;
		try {
			os.close();
		} catch (IOException ex) {
			ex.printStackTrace();
		}
		os = null;
		f = null;
	}

	public static void outInFile(String fname, String text) {
		f = new File(fname);
		try {
			os = new FileOutputStream(f, true);
		} catch (FileNotFoundException ex) {
			ex.printStackTrace();
		}
		if (os != null) {
			try {
				os.write(text.getBytes());
				os.write('\n');
			} catch (IOException ex) {
				ex.printStackTrace();
			}
		}
	}

}

// $Log: Debug.java,v $
// Revision 1.6  2010/03/04 14:13:00  olga
// code optimizing
//
// Revision 1.5  2007/11/01 09:58:20  olga
// Code refactoring: generic types- done
//
// Revision 1.4  2007/09/10 13:05:53  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.3 2007/04/11 10:03:36 olga
// Undo, Redo tuning,
// Simple Parser- bug fixed
//
// Revision 1.2 2006/12/13 13:33:06 enrico
// reimplemented code
//
// Revision 1.1 2005/08/25 11:57:00 enrico
// *** empty log message ***
//
// Revision 1.1 2005/05/30 12:58:04 olga
// Version with Eclipse
//
// Revision 1.7 2004/09/23 08:26:43 olga
// Fehler bei CPs weg, Debug output in file
//
// Revision 1.6 2004/04/28 12:46:38 olga
// test CSP
//
// Revision 1.5 2004/04/15 10:49:48 olga
// Kommentare
//
// Revision 1.4 2004/03/18 17:41:53 olga
//
// rrektur an CPs und XML converter
//
// Revision 1.3 2003/03/05 18:24:27 komm
// sorted/optimized import statements
//
// Revision 1.2 2002/11/11 10:41:00 komm
// Debug state changed
//
// Revision 1.1.1.1 2002/07/11 12:17:25 olga
// Imported sources
//
// Revision 1.7 2001/02/21 15:50:15 olga
// enderungen wegen den Parser.
//
// Revision 1.6 2000/06/15 07:02:15 shultzke
// *** empty log message ***
//
// Revision 1.5 2000/05/17 11:33:50 shultzke
// diverse Aenderungen. Version von Olga wird erwartet
//
// Revision 1.4 1999/10/11 10:23:30 shultzke
// kleine Bugfixes
//
// Revision 1.3 1999/06/28 16:08:42 shultzke
// Hoffentlich erzeigen wir eine uebersetzungsfaehige Version
//
// Revision 1.2 1998/01/04 21:25:24 mich
// DEBUG set to "false", so there should be no debug output activated.
//
// Revision 1.1 1997/12/29 16:30:06 mich
// Initial revision
//

