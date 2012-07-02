package agg.parser;

import java.util.EventObject;

//****************************************************************************+
/**
 * This event is a generall message from a parser.
 * 
 * @author $Author: olga $ Parser Group
 * @version $Id: ParserEvent.java,v 1.5 2010/09/23 08:25:00 olga Exp $
 */
public abstract class ParserEvent extends EventObject {

	public final static int PAIR_FINISHED = -1;

	public final static int FINISHED = -2;

	/**
	 * The message text from a event.
	 */
	protected String message;

	/**
	 * This constructor is inherited from <CODE>EventObject</CODE>.
	 * 
	 * @param source
	 *            The source of the event.
	 */
	public ParserEvent(Object source) {
		super(source);
	}

	// ************************************************************************+
	/**
	 * Returns the message of the event.
	 * 
	 * @return The message.
	 */
	public String getMessage() {
		return this.message;
	}

	// ************************************************************************+
	/**
	 * Sets the message for the event.
	 * 
	 * @param _message
	 *            The message.
	 */
	protected void setMessage(String _message) {
		this.message = _message;
	}
}

/*
 * End of ParserEvent.java
 * ---------------------------------------------------------------------- $Log:
 * ParserEvent.java,v $ Revision 1.2 2007/03/28 10:00:58 olga - extensive
 * changes of Node/Edge Type Editor, - first Undo implementation for graphs and
 * Node/edge Type editing and transformation, - new / reimplemented options for
 * layered transformation, for graph layouter - enable / disable for NACs, attr
 * conditions, formula - GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:57 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
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
