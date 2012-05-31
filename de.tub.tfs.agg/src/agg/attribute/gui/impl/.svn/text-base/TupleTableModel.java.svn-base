package agg.attribute.gui.impl;

import javax.swing.table.AbstractTableModel;
//import javax.swing.table.TableCellEditor;

import agg.attribute.AttrConditionTuple;
import agg.attribute.AttrEvent;
import agg.attribute.AttrInstance;
import agg.attribute.AttrInstanceMember;
import agg.attribute.AttrMember;
import agg.attribute.AttrTuple;
import agg.attribute.AttrType;
import agg.attribute.AttrTypeMember;
import agg.attribute.AttrVariableMember;
import agg.attribute.gui.AttrTupleEditor;
import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.impl.AttrSession;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.DeclTuple;
import agg.attribute.impl.DeclMember;
import agg.attribute.impl.VerboseControl;


/**
 * Table model for tuple editors. The following behavour can be customized by
 * method calls: which columns to display, their order, their titles, classes of
 * their content objects and if their objects are editable. When more specific
 * customization is needed, this class can be extended.
 * 
 * @version $Id: TupleTableModel.java,v 1.22 2010/11/28 22:08:46 olga Exp $
 * @author $Author: olga $
 */
public class TupleTableModel extends AbstractTableModel implements
 /*TableCellEditor, */TupleTableModelConstants {

	/**
	 * 
	 */
	private static final long serialVersionUID = 2201742847657275100L;

	// Property keys for internal array access. Must be [0,1,2,...]

	protected static final int COLUMN_INDEX = 0;

	protected static final int COLUMN_TITLE = 1;

	protected static final int COLUMN_CLASS = 2;

	protected static final int COLUMN_EDITABLE = 3;

	protected static final int N_COLUMN_PROPERTIES = 4;

	/**
	 * Decides if more rows can be interactively added to the tuple, by
	 * displaying an extra row at the table bottom.
	 */
	protected boolean isExtensible = false;

	/** Columns to display and their order. */
	protected int columnArray[] = { NAME, EXPR };

	/** Properties of each column with respect to the table. */
	protected Object columnData[][] = new Object[N_TUPLE_KEYS][N_COLUMN_PROPERTIES];

	/**
	 * Owning editor, serves e.g. for accessing of the edited tuple, the view
	 * setting or, the EditorManager when selecting a handler editor.
	 */
	protected AttrTupleEditor editor = null;

	/** Temporary trick. Later, the editor manager will be asked each time. */
	protected AttrHandler defaultHandler = null;

	protected int currentColumn;

	protected boolean valueChanged;

	/** Constructing with the owning editor. */
	public TupleTableModel(AttrTupleEditor editor) {
		super();
		this.editor = editor;
		String handlerName = agg.attribute.handler.impl.javaExpr.JexHandler
				.getLabelName();
		this.defaultHandler = editor.getAttrManager().getHandler(handlerName);
		initColumnProperties();
	}


	// Public methods for customizing the table model.
	

	/** Changing the table layout. */
	public void setColumnArray(int keys[]) {
		if (keys == null)
			this.columnArray = new int[0];
		else
			this.columnArray = keys;
		// Making all columns invisible.
		for (int i = 0; i < N_TUPLE_KEYS; i++) {
			this.columnData[i][COLUMN_INDEX] = new Integer(-1);
		}

		// Assigning column numbers to wanted columns.
		for (int i = 0; i < this.columnArray.length; i++) {
			this.columnData[this.columnArray[i]][COLUMN_INDEX] = new Integer(i);
		}
	}

	/**
	 * Setting if more rows can be interactively added to the tuple, by
	 * displaying an extra row at the table bottom.
	 */
	public void setExtensible(boolean b) {
		this.isExtensible = b;
	}

	/**
	 * Tests if more rows can be interactively added to the tuple, by displaying
	 * an extra row at the table bottom.
	 */
	public boolean isExtensible() {
		return this.isExtensible;
	}

	/** Changing a column title. */
	public void setColumnTitle(int key, String title) {
		setColumnProperty(key, COLUMN_TITLE, title);
	}

	/** Changing a column class. */
	public void setColumnClass(int key, Class<?> clazz) {
		setColumnProperty(key, COLUMN_CLASS, clazz);
	}

	/** Setting if a field at column is editable. */
	public void setColumnEditable(int key, boolean b) {
		setColumnProperty(key, COLUMN_EDITABLE, new Boolean(b));
	}

	/**
	 * Returns the member of 'tuple' at the specified row. Used internally and
	 * by the editor that uses this table model.
	 */
	public AttrMember getMember(AttrTuple tuple, int row) {
		return tuple.getMemberAt(this.editor.getViewSetting(), row);
	}

	/** Converting column index to item key. */
	public final int getItemKeyAt(int column) {
		return this.columnArray[column];
	}

	/** Converting item key to column index. */
	public final int getColumnAtKey(int key) {
		return ((Integer) this.columnData[key][COLUMN_INDEX]).intValue();
	}

	/**
	 * TupleEditor's AttrObserver interface implementation. notifies the table
	 * of the change. The default implementation is simply updating the whole
	 * table. For optimizing consider the data in the delivered event.
	 */
	public void attributeChanged(AttrEvent event) {
		if (event == null)
			this.valueChanged = false;
		else {
			// System.out.println("TupleTableModel.attributeChanged: at column:
			// "+currentColumn);
			this.fireTableDataChanged();
		}
	}

	//
	// Internal methods.

	/** Setting a column property. */
	protected void setColumnProperty(int key, int propertyKey, Object property) {
		this.columnData[key][propertyKey] = property;
	}

	/** Returns a column property. */
	protected Object getColumnProperty(int key, int propertyKey) {
		return this.columnData[key][propertyKey];
	}

	/**
	 * Returns the number of rows. If this is a model for an extendable table,
	 * makes the table one row higher than the number of its members. The last
	 * row is for adding of a new member. Otherwise returns the exact number of
	 * tuple members.
	 */
	public int getRowCount() {
		AttrTuple tuple = this.editor.getTuple();
		if (tuple == null)
			return 0;
		int nMembers = tuple.getNumberOfEntries(this.editor.getViewSetting());
		if (isExtensible())
			nMembers++;
		// System.out.println("TupleTableModel.getRowCount: "+ nMembers);
		return nMembers;
	}

	// Generic methods

	/** Default initialization, overwritten by calls to setColumn...()-methods. */
	protected void initColumnProperties() {
		Class<?> stringClass = "".getClass();
		Class<?> booleanClass = Boolean.TRUE.getClass();

		setColumnTitle(UNKNOWN, "???");
		setColumnClass(UNKNOWN, null);
		setColumnEditable(UNKNOWN, false);

		setColumnTitle(HANDLER, "Handler");
		setColumnClass(HANDLER, stringClass);
		setColumnEditable(HANDLER, false);

		setColumnTitle(TYPE, "Type");
		setColumnClass(TYPE, stringClass);
		setColumnEditable(TYPE, true);

		setColumnTitle(NAME, "Name");
		setColumnClass(NAME, stringClass);
		setColumnEditable(NAME, true);

		setColumnTitle(EXPR, "Expression");
		setColumnClass(EXPR, stringClass);
		setColumnEditable(EXPR, true);

		setColumnTitle(YIELDS, "Yields");
		setColumnClass(YIELDS, stringClass);
		setColumnEditable(YIELDS, false);

		setColumnTitle(CORRECTNESS, "OK");
		setColumnClass(CORRECTNESS, booleanClass);
		setColumnEditable(CORRECTNESS, false);

		setColumnTitle(IS_INPUT_PARAMETER, "In");
		setColumnClass(IS_INPUT_PARAMETER, booleanClass);
		setColumnEditable(IS_INPUT_PARAMETER, true);

		setColumnTitle(IS_OUTPUT_PARAMETER, "Out");
		setColumnClass(IS_OUTPUT_PARAMETER, booleanClass);
		setColumnEditable(IS_OUTPUT_PARAMETER, true);

		setColumnTitle(VISIBILITY, "Shown");
		setColumnClass(VISIBILITY, booleanClass);
		setColumnEditable(VISIBILITY, true);
	}

	/** Returns the item at 'key' of 'member'. */
	protected Object getItem(AttrMember member, int key, AttrTuple tuple,
			int row) {
		// AttrInstanceMember m = (AttrInstanceMember) member;

		if (member instanceof AttrTypeMember) {
			return getItemOfAttrTypeMember(member, key, tuple, row);
		} else if (member instanceof AttrInstanceMember) {

			AttrInstanceMember m = (AttrInstanceMember) member;
			switch (key) {
			case HANDLER:
				return m.getDeclaration().getHandler().getName();
			case TYPE:
				return m.getDeclaration().getTypeName();
			case NAME:
				return m.getDeclaration().getName();
			case VISIBILITY:
				return new Boolean(this.editor.getViewSetting()
						.isVisible(tuple, row));
			case EXPR:
				return m.getExprAsText();
			case CORRECTNESS:
				return new Boolean(m.isValid() && m.getDeclaration().isValid());
			case YIELDS:
				return new Boolean(m.isValid() && m.getDeclaration().isValid());
			case IS_INPUT_PARAMETER:
				return new Boolean(((AttrVariableMember) m).isInputParameter());
			case IS_OUTPUT_PARAMETER:
				return new Boolean(((AttrVariableMember) m).isOutputParameter());
			default:
				return null;
			}
		} else
			return null;
	}

	protected Object getItemOfAttrTypeMember(AttrMember member, int key,
			AttrTuple tuple, int row) {
		if (member instanceof AttrTypeMember) {
			AttrTypeMember m = (AttrTypeMember) member;

			switch (key) {
			case HANDLER:
				return m.getHandler().getName();
			case TYPE:
				return m.getTypeName();
			case NAME:
				return m.getName();
			case VISIBILITY:
				return new Boolean(this.editor.getViewSetting()
						.isVisible(tuple, row));
			case EXPR:
			case CORRECTNESS:
			case YIELDS:
			case IS_INPUT_PARAMETER:
			case IS_OUTPUT_PARAMETER:
			default:
				return null;
			}
		}
		return null;
	}

	/** Returns the item at 'key' of the new bottom row. */
	protected Object getItemOfNewRow(int key, AttrTuple tuple, int row) {
		switch (key) {
		// case HANDLER: return defaultHandler.getName();
		default:
			return null;
		}
	}

	/** Modifies the item at 'key' of 'member'. */
	protected void setItem(Object aValue, AttrMember member, int key,
			AttrTuple tuple, int row) {
		// System.out.println("TupleTableModel.setItem ");
		// AttrInstanceMember m = (AttrInstanceMember) member;

		if (member instanceof AttrTypeMember) {
			setItemOfAttrTypeMember(aValue, member, key, tuple, row);
		} else if (member instanceof AttrInstanceMember) {

			AttrInstanceMember m = (AttrInstanceMember) member;

			switch (key) {
			case HANDLER:
				AttrSession.stdoutPrintln(VerboseControl.logTrace, "Handler");
				m.getDeclaration().setHandler((AttrHandler) aValue);
				break;
			case TYPE:
				AttrSession.stdoutPrintln(VerboseControl.logTrace, "Type");
				if (!"".equals((String) aValue))
					m.getDeclaration().setType((String) aValue);
				break;
			case NAME:
				AttrSession.stdoutPrintln(VerboseControl.logTrace, "Name");
				if (!"".equals((String) aValue)) {
					if (((DeclTuple) ((DeclMember) m.getDeclaration())
								.getHoldingTuple()).isClassName((String) aValue))
						m.getDeclaration().setName("");
					else if (m.getDeclaration().getHoldingTuple().getMemberAt((String) aValue) == null)
						m.getDeclaration().setName((String) aValue);			
				}
				break;
			case VISIBILITY:
				AttrSession
						.stdoutPrintln(VerboseControl.logTrace, "Visibility");
				this.editor.getViewSetting().setVisibleAt(tuple,
						((Boolean) aValue).booleanValue(), row);
				break;
			case EXPR:
				// set expr iff the type and the name of the member are valid
				if (m.getDeclaration().isValid()) {
					AttrSession.stdoutPrintln(VerboseControl.logTrace, "Expr");
					
					HandlerExpr oldexpr = m.getExpr();
					
					((ValueMember) m).checkValidity(oldexpr);
					String olderrorMsg = ((ValueMember) m).getErrorMsg();
					
					String newText = (String) aValue;
					if (newText.equals("")) {
						m.setExprAsText(newText);
					} else if (((DeclTuple) ((DeclMember) m.getDeclaration())
									.getHoldingTuple()).isClassName(newText)) {
							m.setExprAsText("");
					} else if (oldexpr == null
									|| !newText.equals(oldexpr.toString())) {
						try {
							// check if aValue = $package.class$.static_method
							// or aValue = package.class.static_method
							// and cut to class.static_method
//							newText = AttrTupleManager.getDefaultManager().getStaticMethodCall((String) aValue);
							newText = this.getStaticMethodCall((String) aValue);											
							HandlerExpr expression = ((ValueMember) m).getHandler()
									.newHandlerExpr(
											((ValueMember) m).getDeclaration()
													.getType(), newText); // (String)aValue);
							((ValueMember) m).checkValidity(expression);
							
							if (m.isValid()) {
								m.setExprAsText(newText);
							}
							else if (oldexpr != null && olderrorMsg.length() == 0)	{
								m.setExprAsText(oldexpr.toString());
							}
							
						} catch (agg.attribute.handler.AttrHandlerException ex) {
	//						System.out.println(ex.getLocalizedMessage());
						}	
					}
				}
				break;
			case IS_INPUT_PARAMETER:
				AttrSession.stdoutPrintln(VerboseControl.logTrace,
						"Is_Input_Parameter");
				((AttrVariableMember) m).setInputParameter(((Boolean) aValue)
						.booleanValue());
				// System.out.println("ist input geklickt:"+m);
				break;
			case IS_OUTPUT_PARAMETER:
				AttrSession.stdoutPrintln(VerboseControl.logTrace,
						"Is_Out_Parameter");
				((AttrVariableMember) m).setOutputParameter(((Boolean) aValue)
						.booleanValue());
				break;
			default:
				;
			}
			AttrSession.logPrintln(VerboseControl.logTrace,
					"TupleTableModel:\n<-setItem");
		}
	}

	private void setItemOfAttrTypeMember(Object aValue, AttrMember member,
			int key, AttrTuple tuple, int row) {
		if (member instanceof AttrTypeMember) {
			AttrTypeMember m = (AttrTypeMember) member;

			switch (key) {
			case HANDLER:
				AttrSession.stdoutPrintln(VerboseControl.logTrace, "Handler");
				m.setHandler((AttrHandler) aValue);
				break;
			case TYPE:
				AttrSession.stdoutPrintln(VerboseControl.logTrace, "Type");
				m.setType((String) aValue);
				break;
			case NAME:
				AttrSession.stdoutPrintln(VerboseControl.logTrace, "Name");
				m.setName((String) aValue);
				break;
			case EXPR:
			case CORRECTNESS:
			case YIELDS:
			case IS_INPUT_PARAMETER:
			case IS_OUTPUT_PARAMETER:
			default:
				;
			}
			AttrSession.logPrintln(VerboseControl.logTrace,
					"TupleTableModel:\n<-setTypeItem");
		}
	}

	// check the form: $package.class$.static_method
	private String getStaticMethodCall(String aValue) {
		if (aValue.indexOf("$") == 0) {
			int ind = aValue.substring(1).indexOf("$");
			if (ind > 0) {
				String clstr = aValue.substring(1, ind + 1);
				try {
					Class.forName(clstr);
					String tst = clstr.substring(clstr.indexOf(".") + 1);
					while (tst.indexOf(".") != -1) {
						clstr = tst.concat("");
						tst = clstr.substring(clstr.indexOf(".") + 1);
					}
					clstr = tst.concat("");
					String result = clstr + aValue.substring(ind + 2);
					return result;
				} catch (ClassNotFoundException ex) {
					System.out.println("TupleTableModel: ClassNotFoundException: "+ex.getMessage());
				}
			}
		} else {
			try {
				if (aValue.indexOf(".") >= 0) {
					String clstr = aValue.substring(0, aValue.indexOf("."));
					Class.forName(clstr);
					return aValue;
				}
			} catch (ClassNotFoundException cex) {
//				System.out.println(cex.getMessage());
						
				String tst = aValue;
				String pack = null;
				String tmp = "";
				while (tst.indexOf(".") != -1) {
					String next = tst.substring(0, tst.indexOf("."));
					Package p = Package.getPackage(tmp + next);
					if (p != null) {
						pack = p.getName();
						tmp = p.getName();
					} else {
						tmp = tmp + next + ".";
					}
					tst = tst.substring(tst.indexOf(".") + 1, tst.length());
				}
				if (pack != null) {
					String result = aValue.replaceFirst(pack + ".", "");
					String clstr = result.substring(0, result.indexOf("."));
					try {
						Class.forName(pack + "." + clstr);
						return result;
					} catch (ClassNotFoundException ex) {
						System.out.println("TupleTableModel: ClassNotFoundException: "+ex.getMessage());
					}
				}
			}
		}
		return aValue;
	}

	/**
	 * Modifies the new bottom row. Involves adding a new member to 'tuple'.
	 */
	protected void setItemOfNewRow(Object aValue, int key, AttrTuple tuple,
			int row) {
//		System.out.println(
//				"TupleTableModel:\n->setItemOfNewRow\nnumber of entries: "
//						+ tuple.getNumberOfEntries());
		
		if (tuple instanceof AttrConditionTuple) {
			// System.out.println("TupleTableModel.setItemOfNewRow ::
			// addCondition: "+(String) aValue);
			((AttrConditionTuple) tuple).addCondition((String) aValue);
			return;
		} 
		if (tuple instanceof AttrInstance) {
			AttrTypeMember typeMember = ((AttrInstance) tuple).getType()
					.addMember();
			// addMember fuegt auf Instanzebene sofort einen Eintrag hinzu.
			// nach der Deklarierung durch addMember kann sofort mit dem neuen
			// Member gearbeitet
			// werden.
			typeMember.setHandler(this.defaultHandler);
			int lastIndex = tuple.getNumberOfEntries() - 1;
			AttrInstanceMember m = (AttrInstanceMember) tuple
					.getMemberAt(lastIndex);
			setItem(aValue, m, key, tuple, row);
			AttrSession.logPrintln(VerboseControl.logTrace,
					"TupleTableModel:\n<-setItemOfNewRow");
		}

		else if (tuple instanceof AttrType) {
			AttrTypeMember typeMember = ((AttrType) tuple).addMember();
			typeMember.setHandler(this.defaultHandler);
			int lastIndex = tuple.getNumberOfEntries() - 1;
			AttrTypeMember m = (AttrTypeMember) tuple.getMemberAt(lastIndex);
			setItem(aValue, m, key, tuple, row);
			AttrSession.logPrintln(VerboseControl.logTrace,
					"TupleTableModel:\n<-setItemOfNewRow");

		}
	}

	/** Returns the header column label for the specified key. */
	protected String getItemLabel(int key) {
		String label = (String) getColumnProperty(key, COLUMN_TITLE);
		// AttrSession.logPrintln("getItemLabel("+key+")="+label);
		return label;
	}

	/** Returns the class for an item key. */
	protected Class<?> getItemClass(int key) {
		Class<?> c = (Class<?>) getColumnProperty(key, COLUMN_CLASS);
		// AttrSession.logPrintln("getItemClass("+key+")="+c);
		return c;
	}

	/**
	 * Tests if the item at 'key' of the specified member is editable. Default
	 * implementation looks only at the key, ignoring 'member'. When needed,
	 * this behaviour can be overridden.
	 */
	protected boolean isItemEditable(AttrMember member, int key) {
		return ((Boolean) getColumnProperty(key, COLUMN_EDITABLE))
				.booleanValue();
	}

	/**
	 * Tests if the item at 'key' in the new bottom row is editable. Default
	 * implementation: same as other rows.
	 */
	protected boolean isNewRowEditable(int key) {
		return ((Boolean) getColumnProperty(key, COLUMN_EDITABLE))
				.booleanValue();
	}

	// TableModel interface implementation: dispatching to my specific
	// methods.

	/** TableModel interface implementation. */
	public final int getColumnCount() {
		return this.columnArray.length;
	}

	/** TableModel interface implementation: dispatching to my specific methods. */
	public final Object getValueAt(int row, int column) {
		AttrTuple tuple = this.editor.getTuple();
		if (tuple == null)
			return null;
		else if (row >= tuple.getNumberOfEntries())
			return getItemOfNewRow(getItemKeyAt(column), tuple, row);
		else
			return getItem(getMember(tuple, row), getItemKeyAt(column), tuple,
					row);
	}

	/** TableModel interface implementation: dispatching to my specific methods. */
	public final String getColumnName(int column) {
		return getItemLabel(getItemKeyAt(column));
	}

	/** Returns the number of a changed column. */
	public final int getChangedColumn() {
		return this.currentColumn;
	}

	/**
	 * Returns true if a new value was created or an already exist value was
	 * changed, otherwise false.
	 */
	public final boolean isColumnValueChanged() {
		return this.valueChanged;
	}

	/** TableModel interface implementation: dispatching to my specific methods. */
	public final boolean isCellEditable(int row, int column) {
		AttrTuple tuple = this.editor.getTuple();
		if (tuple == null) {
			return false;
		}
		else if (row >= tuple.getNumberOfEntries()) {
			return isNewRowEditable(getItemKeyAt(column));
		}
		else {
			return isItemEditable(getMember(tuple, row), getItemKeyAt(column));
		}
	}

	/** TableModel interface implementation: dispatching to my specific methods. */
	public final void setValueAt(Object aValue, int row, int column) {
		AttrTuple tuple = this.editor.getTuple();
		if (tuple == null) {
			return;
		}
		
		Object oldvalue = getValueAt(row, column);
		this.currentColumn = column;
		if (row >= tuple.getNumberOfEntries()) {
			this.valueChanged = true;
			setItemOfNewRow(aValue, getItemKeyAt(column), tuple, row);
		} else {
			if (oldvalue == null) {
				this.valueChanged = true;
			} else if (!oldvalue.equals(aValue)) {
				this.valueChanged = true;
			} else {
				this.valueChanged = false;
			}
			setItem(aValue, getMember(tuple, row), getItemKeyAt(column), tuple,
					row);
		}
		AttrSession.logPrintln(VerboseControl.logTrace,
				"TupleTableModel:\n<-setValueAt");
	}

	/** TableModel interface implementation: dispatching to my specific methods. */
	public final Class<?> getColumnClass(int column) {
		return getItemClass(getItemKeyAt(column));
	}
}
/*
 * $Log: TupleTableModel.java,v $
 * Revision 1.22  2010/11/28 22:08:46  olga
 * tuning
 *
 * Revision 1.21  2010/09/23 08:13:17  olga
 * tuning
 *
 * Revision 1.20  2010/03/22 09:41:54  olga
 * tuning
 *
 * Revision 1.19  2010/03/21 21:22:11  olga
 * edit attribute - improved
 *
 * Revision 1.18  2010/03/19 14:44:30  olga
 * isClassName(String) is only used in AGG GUI now
 *
 * Revision 1.17  2008/07/14 07:35:48  olga
 * Applicability of RS - new option added, more tuning
 * Node animation - new animation parameter added,
 * Undo edit manager - possibility to disable it when graph transformation
 * because it costs much more time and memory
 *
 * Revision 1.16  2008/01/14 09:01:26  olga
 * String.isEmpty() replaced by String.length()==0
 *
 * Revision 1.15  2008/01/14 08:46:42  olga
 * Bug fixed in using of CSP without Backjumping (CSP w/o BJ) match algorithm;
 * Bug fixed in using layered graph transformation with loop over layers.
 *
 * Revision 1.14  2007/11/19 08:48:39  olga
 * Some GUI usability mistakes fixed.
 * Default values in node/edge of a type graph implemented.
 * Code tuning.
 *
 * Revision 1.13  2007/11/05 09:18:19  olga
 * code tuning
 *
 * Revision 1.12  2007/11/01 09:58:17  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.11  2007/09/24 09:42:35  olga
 * AGG transformation engine tuning
 *
 * Revision 1.10  2007/09/13 14:58:59  olga
 * tests
 *
 * Revision 1.9  2007/09/10 13:05:29  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.8 2007/03/28 10:00:59 olga -
 * extensive changes of Node/Edge Type Editor, - first Undo implementation for
 * graphs and Node/edge Type editing and transformation, - new / reimplemented
 * options for layered transformation, for graph layouter - enable / disable for
 * NACs, attr conditions, formula - GUI tuning
 * 
 * Revision 1.7 2007/02/05 12:33:44 olga CPA: chengeAttribute
 * conflict/dependency : attributes with constants bug fixed, but the critical
 * pairs computation has still a gap.
 * 
 * Revision 1.6 2006/12/13 13:33:04 enrico reimplemented code
 * 
 * Revision 1.5 2006/08/02 09:00:57 olga Preliminary version 1.5.0 with -
 * multiple node type inheritance, - new implemented evolutionary graph layouter
 * for graph transformation sequences
 * 
 * Revision 1.4 2006/01/16 09:35:33 olga Extended attr. setting
 * 
 * Revision 1.3 2005/11/16 09:50:57 olga tests
 * 
 * Revision 1.2 2005/11/07 09:38:07 olga Null pointer during retype attr. member
 * fixed.
 * 
 * Revision 1.1 2005/08/25 11:56:58 enrico *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.8 2005/04/11 13:06:13 olga Errors during CPA are corrected.
 * 
 * Revision 1.7 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.6 2004/11/15 11:24:45 olga Neue Optionen fuer Transformation;
 * verbesserter default Graphlayout; Close GraGra mit Abfrage wenn was geaendert
 * wurde statt Delete GraGra
 * 
 * Revision 1.5 2004/06/09 11:32:53 olga Attribute-Eingebe/Bedingungen : NAC
 * kann jetzt eigene Variablen und Bedingungen haben. CP Berechnung korregiert.
 * 
 * Revision 1.4 2003/03/05 18:24:11 komm sorted/optimized import statements
 * 
 * Revision 1.3 2002/11/25 14:56:14 olga Der Fehler unter Windows 2000 im
 * AttributEditor ist endlich behoben. Es laeuft aber mit Java1.3.0 laeuft
 * endgueltig nicht. Also nicht Java1.3.0 benutzen!
 * 
 * Revision 1.2 2002/09/23 12:23:51 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:57 olga Imported sources
 * 
 * Revision 1.7 2000/04/05 12:08:01 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
