package agg.attribute.impl;

import agg.attribute.gui.lang.AttrMessageLang;

/**
 * @version $Id: AttrImplException.java,v 1.3 2010/08/23 07:30:49 olga Exp $
 * @author $Author: olga $
 */
public class AttrImplException extends RuntimeException {

	static final long serialVersionUID = 1055116828321824266L;

	protected int msgCode = 0;

	public AttrImplException(int msgCode) {
		super();
		this.msgCode = msgCode;
	}

	public AttrImplException(int msgCode, String addMsg) {
		super(addMsg);
		this.msgCode = msgCode;
	}

	public AttrImplException(String addMsg) {
		super(addMsg);
	}

	public int getMsgCode() {
		return this.msgCode;
	}

	public String getMessage() {
		String msg1 = AttrMessageLang.textForCode(this.msgCode) + "\n";
		String msg2 = super.getMessage();
		if (msg2 != null)
			msg1 += "(" + msg2 + ")\n";
		return msg1;
	}
}
/*
 * $Log: AttrImplException.java,v $
 * Revision 1.3  2010/08/23 07:30:49  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:19  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:57 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.2 2002/09/23 12:23:54 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:01 olga Imported sources
 * 
 * Revision 1.5 2000/04/05 12:08:52 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
