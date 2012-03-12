package agg.parser;

import java.util.Vector;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.Match;

//****************************************************************************+
/**
 * This a a container for matches. This container can check if a certain match
 * is in the container.
 * 
 * @author $Author: olga $ Parser Group
 * @version $Id: RuleInstances.java,v 1.3 2010/09/23 08:25:00 olga Exp $
 */
public class RuleInstances {

	/**
	 * set to store the rules and matches
	 */
	private Vector<Match> matches;

	/**
	 * Creates a new empty container.
	 */
	protected RuleInstances() {
		this.matches = new Vector<Match>();
	}

	// ----------------------------------------------------------------------+
	/**
	 * Adds a match to the container.
	 * 
	 * @param m
	 *            A match.
	 */
	protected void add(Match m) {
		if (!isIn(m))
			this.matches.addElement(m);
	}

	// ----------------------------------------------------------------------+
	/**
	 * Checks if a given match is in this container.
	 * 
	 * @param m
	 *            The match of this rule
	 * @return true if the match is in this container.
	 */
	protected boolean isIn(Match m) {
		for (int i = 0; i < this.matches.size(); i++) {
			Match n = this.matches.elementAt(i);
			if (m.getRule().equals(n.getRule())) {
				/*
				 * MorphismUtility mu = new MorphismUtility(bf,m);
				 * if(mu.isIsomorphicTo(n)) return true;
				 */
				if (m.isIsomorphicTo(n))
					return true;
			}
		}
		return false;
	}

	/**
	 * Clears some internal stuff.
	 */
	protected void finalize() {
		for (int i = 0; i < this.matches.size(); i++) {
			Match m = this.matches.elementAt(i);
			// System.out.println("RuleInstance: kille match "+m);
			BaseFactory.theFactory().destroyMatch(m);
		}
	}
}

/*
 * End of RuleInstances.java
 * ---------------------------------------------------------------------- $Log:
 * RuleInstances.java,v $ Revision 1.1 2005/08/25 11:56:57 enrico *** empty log
 * message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.2 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:24 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 10:44:56 olga Neue Files aus parser branch in Head
 * eingefuegt.
 * 
 * Revision 1.1.2.4 2001/01/28 13:14:58 shultzke API fertig
 * 
 * Revision 1.1.2.3 2001/01/01 21:24:33 shultzke alle Parser fertig inklusive
 * Layout
 * 
 * Revision 1.1.2.2 2000/11/28 09:54:50 shultzke stack aufgeraeumt nach parsing
 * 
 * Revision 1.1.2.1 2000/11/08 15:00:07 shultzke wichtiger teil des parsers
 * 
 * Revision 1.1 2000/06/13 08:58:21 shultzke Initial version, very alpha
 * 
 * Revision 1.11 2000/06/05 17:11:03 shultzke checkin fuer zu hause
 * 
 * Revision 1.10 1999/09/14 10:53:54 shultzke Kommentare hinzugefuegt. Total
 * veraltete Klassen geloechst. Debug ausgeschaltet Removed Files:
 * ParserErrorEventImpl.java ParserMessageEventImpl.java
 * 
 * Revision 1.9 1999/07/20 19:29:51 shultzke es wurden nur fuer javadoc einige
 * tags hizugfuegt
 * 
 * Revision 1.8 1999/07/20 11:58:19 shultzke bis auf 3a alles erledigt :-)
 * 
 * Revision 1.7 1999/07/20 10:06:00 shultzke halb-fertige aber uebersetzbare
 * Version des Parsers. RuleInstances mussten angepasst werden.
 * 
 * Revision 1.6 1999/07/11 09:22:50 shultzke *** empty log message ***
 * 
 * Revision 1.5 1999/06/30 21:24:19 shultzke added rcs key and tried to check in
 * remote
 */
