package tggeditor.util.dialogs;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Vector;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.TableEditor;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseAdapter;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.graphics.Rectangle;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Control;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.TableItem;
import org.eclipse.swt.widgets.Text;

import tggeditor.util.validator.ExpressionValidator;

/**
 * This code was edited or generated using CloudGarden's Jigloo SWT/Swing GUI
 * Builder, which is free for non-commercial use. If Jigloo is being used
 * commercially (ie, by a corporation, company or business for any purpose
 * whatever) then you should purchase a license for each developer using Jigloo.
 * Please visit www.cloudgarden.com for details. Use of Jigloo implies
 * acceptance of these licensing terms. A COMMERCIAL LICENSE HAS NOT BEEN
 * PURCHASED FOR THIS MACHINE, SO JIGLOO OR THIS CODE CANNOT BE USED LEGALLY FOR
 * ANY CORPORATE OR COMMERCIAL PURPOSE.
 */
public class ParemetersValueDialog extends org.eclipse.swt.widgets.Dialog {

	/** The dialog shell. */
	private Shell dialogShell;
	
	/** The table1. */
	private Table table1;
	
	/** The table column1. */
	private TableColumn tableColumn1;
	
	/** The button1. */
	private Button button1;
	
	/** The table column2. */
	private TableColumn tableColumn2;
	
	/** The parameter2 expression validators. */
	private Map<String, List<ExpressionValidator>> parameter2ExpressionValidators;
	
	/** The parameter2 correct. */
	private Map<String, Boolean> parameter2Correct;
	
	/** The correct. */
	private final Boolean correct = new Boolean(true);
	
	/** The incorrect. */
	private final Boolean incorrect = new Boolean(false);
	
	/** The assigment. */
	private Map<String, Object> assigment;
	
	/** The parameters. */
	private List<String> parameters;
	
	/** The cancel. */
	private boolean cancel;

	/**
	 * Auto-generated main method to display this org.eclipse.swt.widgets.Dialog
	 * inside a new Shell.
	 *
	 * @param parent the parent
	 * @param style the style
	 * @param parameter2ExpressionValidators the parameter2 expression validators
	 */

	public ParemetersValueDialog(
			Shell parent,
			int style,
			Map<String, List<ExpressionValidator>> parameter2ExpressionValidators) {
		super(parent, style);
		this.parameter2ExpressionValidators = parameter2ExpressionValidators;
		this.assigment = new HashMap<String, Object>();
		this.parameters = new Vector<String>(parameter2ExpressionValidators
				.keySet());
		parameter2Correct = new HashMap<String, Boolean>();
		for (String par : parameter2ExpressionValidators.keySet()) {
			parameter2Correct.put(par, correct);
		}
		cancel = true;
	}

	/**
	 * Open.
	 */
	public void open() {
		try {
			Shell parent = getParent();
			dialogShell = new Shell(parent, SWT.DIALOG_TRIM
					| SWT.APPLICATION_MODAL);

			dialogShell.setLayout(new FormLayout());
			{
				button1 = new Button(dialogShell, SWT.PUSH | SWT.CENTER);
				FormData button1LData = new FormData();
				button1LData.left = new FormAttachment(0, 1000, 12);
				button1LData.top = new FormAttachment(0, 1000, 212);
				button1LData.width = 335;
				button1LData.height = 25;
				button1.setLayoutData(button1LData);
				button1.setText("Ok");
				if (parameter2Correct.values()
						.contains(incorrect)) {
					button1.setEnabled(false);
				} else {
					button1.setEnabled(true);
				}
				button1.addListener(SWT.Selection, new Listener() {

					@Override
					public void handleEvent(Event event) {
						cancel = false;
						dialogShell.close();
					}
				});
			}
			{
				FormData table1LData = new FormData();
				table1LData.left = new FormAttachment(0, 1000, 12);
				table1LData.top = new FormAttachment(0, 1000, 12);
				table1LData.width = 318;
				table1LData.height = 177;
				table1 = new Table(dialogShell, SWT.SINGLE | SWT.FULL_SELECTION
						| SWT.V_SCROLL | SWT.H_SCROLL | SWT.VIRTUAL);
				table1.setLayoutData(table1LData);
				table1.setHeaderVisible(true);
				table1.setLinesVisible(true);
				{
					tableColumn1 = new TableColumn(table1, SWT.LEFT);
					tableColumn1.setText("Parameter");
					tableColumn1.setWidth(100);
				}
				{
					tableColumn2 = new TableColumn(table1, SWT.LEFT);
					tableColumn2.setText("Wert");
					tableColumn2.setWidth(230);
				}
				for (String par : parameters) {
					TableItem tableItem = new TableItem(table1, SWT.NONE);
					tableItem.setText(0, par);
					tableItem.setText(1, "");
				}
				final TableEditor editor = new TableEditor(table1);
				editor.horizontalAlignment = SWT.LEFT;
				editor.grabHorizontal = true;
				table1.addMouseListener(new MouseAdapter() {
					public void mouseDown(MouseEvent event) {
						// Dispose any existing editor
						Control old = editor.getEditor();
						if (old != null)
							old.dispose();

						// Determine where the mouse was clicked
						Point pt = new Point(event.x, event.y);

						// Determine which row was selected
						final TableItem item = table1.getItem(pt);
						if (item != null) {
							// Determine which column was selected
							int column = -1;
							for (int i = 0, n = table1.getColumnCount(); i < n; i++) {
								Rectangle rect = item.getBounds(i);
								if (rect.contains(pt)) {
									// This is the selected column
									column = i;
									break;
								}
							}
							// Column 2 holds dropdowns
							if (column == 1) {
								// Create the Text object for our editor
								final Text text = new Text(table1, SWT.NONE);
								text.setForeground(item.getForeground());

								// Transfer any text from the cell to the Text
								// control,
								// set the color to match this row, select the
								// text,
								// and set focus to the control
								text.setText(item.getText(column));
								text.setForeground(item.getForeground());
								text.selectAll();
								text.setFocus();

								// Recalculate the minimum width for the editor
								editor.minimumWidth = text.getBounds().width;

								// Set the control into the editor
								editor.setEditor(text, item, column);

								// Add a handler to transfer the text back to
								// the cell
								// any time it's modified
								final int col = column;
								text.addModifyListener(new ModifyListener() {

									@Override
									public void modifyText(ModifyEvent e) {
										String par = parameters.get(table1
												.indexOf(item));
										text.setToolTipText("");
										Boolean isCorrect = correct;
										if (!text.getText().isEmpty()) {
											
											String newStringValue = new String(text
													.getText());
											Object newObject = null;
											assigment.put(par, newStringValue);
											for (ExpressionValidator eV : parameter2ExpressionValidators
													.get(par)) {
												newObject = getCurrentObject(
														newStringValue,
														newObject, eV);
												String s = eV
														.isValid(assigment);
												if (s != null) {
													isCorrect = incorrect;
													text.setToolTipText(s);
													break;
												}
											}
											if (!(newObject instanceof String) && newObject!=null){
												assigment.put(par, newObject);
											}
										}
										else{
											assigment.remove(par);
										}
										parameter2Correct.put(par, isCorrect);
										if (parameter2Correct.values()
												.contains(incorrect)) {
											button1.setEnabled(false);
										} else {
											button1.setEnabled(true);
										}
										item.setText(col, text.getText());
									}

									/**
									 * @param newStringValue
									 * @param currentObject
									 * @param validator
									 * @return
									 */
									protected Object getCurrentObject(
											String newStringValue,
											Object currentObject,
											ExpressionValidator validator) {
										Object object = validator.getObject(newStringValue);
										if (currentObject==null){
											currentObject=object;
										}
										else{
											if (object.getClass().isAssignableFrom(currentObject.getClass())){
												currentObject = object;
											}
											else{
												if (!currentObject.getClass().isAssignableFrom(object.getClass())){
													currentObject=newStringValue;
												}
											}
										}
										return currentObject;
									}
								});
							}
						}
					}
				});

			}
			dialogShell.layout();
			dialogShell.setSize(370, 270);
			Rectangle shellBounds = getParent().getBounds();
			Point dialogSize = dialogShell.getSize();
			dialogShell.setLocation(shellBounds.x
					+ (shellBounds.width - dialogSize.x) / 2, shellBounds.y
					+ (shellBounds.height - dialogSize.y) / 2);
			dialogShell.open();
			Display display = dialogShell.getDisplay();
			while (!dialogShell.isDisposed()) {
				if (!display.readAndDispatch())
					display.sleep();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Gets the assigment.
	 *
	 * @return the assigment
	 */
	public Map<String, Object> getAssigment() {
		return assigment;
	}

	/**
	 * Checks if is cancel.
	 *
	 * @return the cancel
	 */
	public boolean isCancel() {
		return cancel;
	}
}
