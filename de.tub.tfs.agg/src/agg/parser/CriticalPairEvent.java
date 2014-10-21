package agg.parser;

import agg.xt_basis.Rule;

//****************************************************************************+
/**
 * A message generated during critical pair analysis.
 * 
 * @author $Author: olga $ Parser Group
 * @version $Id: CriticalPairEvent.java,v 1.9 2010/12/20 20:05:36 olga Exp $
 */
@SuppressWarnings("serial")
public class CriticalPairEvent extends ParserMessageEvent {

	public static final int UNCRITICAL = 0;

	public static final int CRITICAL = 1;

	public static final int NON_RELEVANT = 2;

	public static final int SHOW_ENTRY = 3;

	public static final int HIDE_ENTRY = 4;

	public static final int REMOVE_RULE_ENTRY = 5;

	public static final int REMOVE_RELATION_ENTRY = 6;

	public static final int REMOVE_ENTRY = 7;
	
	public static final int REMOVE_ENTRIES = 71;

	public static final int NOT_COMPUTABLE = 8;
	
	public static final int NOT_COMPLETE_COMPUTABLE = 9;
	
	public static final int CONTINUE_COMPUTE = 10;
	
	/** contains the first rule of the actual pair */
	Rule first;

	/** contains the second rule of the actual pair */
	Rule second;

	/**
	 * Constructs a new object informing, that the state of the rule pair
	 * (first, second) has changed.
	 */
	public CriticalPairEvent(ExcludePairContainer source, Rule first,
			Rule second) {
		super(source);
		this.first = first;
		this.second = second;
		this.key = -1;
	}

	/**
	 * Constructs a new object informing, that the state of the rule pair
	 * (first, second) has changed.
	 */
	public CriticalPairEvent(ExcludePairContainer source, Rule first,
			Rule second, String message) {
		super(source, message);
		this.first = first;
		this.second = second;
		this.key = -1;
	}

	public CriticalPairEvent(ExcludePairContainer source, Rule first,
			Rule second, int key) {
		super(source, "");
		this.first = first;
		this.second = second;
		this.key = key;
	}

	public CriticalPairEvent(ExcludePairContainer source, Rule first,
			Rule second, int key, String message) {
		super(source, message);
		this.first = first;
		this.second = second;
		this.key = key;
	}

	/** returns the first rule of the changed pair */
	public Rule getFirstRule() {
		return this.first;
	}

	/** returns the second rule of the changed pair */
	public Rule getSecondRule() {
		return this.second;
	}

	public int getKey() {
		return this.key;
	}
}

/*
 * End of ParserEvent.java
 * ---------------------------------------------------------------------- $Log:
 * CriticalPairEvent.java,v $ Revision 1.3 2007/03/28 10:00:57 olga - extensive
 * changes of Node/Edge Type Editor, - first Undo implementation for graphs and
 * Node/edge Type editing and transformation, - new / reimplemented options for
 * layered transformation, for graph layouter - enable / disable for NACs, attr
 * conditions, formula - GUI tuning
 * 
 * Revision 1.2 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.3 2005/01/28 14:02:32 olga -Fehlerbehandlung beim Typgraph check
 * -Erweiterung CP GUI / CP Menu -Fehlerbehandlung mit identification option
 * -Fehlerbehandlung bei Rule PAC
 * 
 * Revision 1.2 2003/03/05 18:24:08 komm sorted/optimized import statements
 * 
 * Revision 1.1 2003/01/20 10:46:29 komm new events for new GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:42:52 olga Die Parser Version aus parser branch
 * wurde in Head uebernommen.
 * 
 * Revision 1.1.2.1 2001/01/28 13:14:57 shultzke API fertig
 * 
 * Revision 1.1 2000/06/13 08:57:33 shultzke Initial version, very alpha
 * 
 * Revision 1.11 1999/09/14 10:52:34 shultzke Kommentare hinzugefuegt
 * 
 * Revision 1.10 1999/09/08 17:36:24 shultzke Check fuer 3a implementiert und
 * etwas getestet
 * 
 * Revision 1.9 1999/07/11 09:22:43 shultzke *** empty log message ***
 * 
 * Revision 1.8 1999/07/04 18:48:09 shultzke Docu erneuert. Events
 * implementiert.
 * 
 * Revision 1.7 1999/06/30 21:24:11 shultzke added rcs key and tried to check in
 * remote
 */
