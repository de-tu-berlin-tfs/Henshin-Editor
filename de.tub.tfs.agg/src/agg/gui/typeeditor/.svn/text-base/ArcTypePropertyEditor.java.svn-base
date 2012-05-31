package agg.gui.typeeditor;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;
import java.awt.Color;
import java.awt.Point;
import java.awt.Dimension;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.AbstractButton;
import javax.swing.Icon;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JOptionPane;
import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.border.TitledBorder;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.undo.*;

import agg.editor.impl.EdType;
import agg.editor.impl.EditUndoManager;
import agg.editor.impl.TypeReprData;
import agg.gui.editor.EditorConstants;
import agg.gui.event.TypeEvent;
import agg.util.Pair;

public class ArcTypePropertyEditor extends JPanel implements ChangeListener,
		StateEditable {

	public ArcTypePropertyEditor(JFrame aggappl, TypeEditor typeEditor,
			TypePalette palette) {
		super(new BorderLayout());
		this.setBorder(new TitledBorder(" Edge Type Properties "));

		this.applFrame = aggappl;
		this.typeEditor = typeEditor;
		this.palette = palette;

		this.colorChooser = new ColorChooserDialog();
		this.colorChooser.addChangeListener(this);

		this.dialog = new JDialog(this.applFrame, " Edge Type Editor ");

		this.nameEditor = new JTextField(this.typeName);
		this.colorGroup = new ButtonGroup();
		this.moreColor = new JCheckBox("Other", null);
		this.shapeGroup = new ButtonGroup();
		this.boldCB = new JCheckBox("bold", null);
		this.commentEditor = new JEditorPane();
		this.addButton = new JButton();
		this.modifyButton = new JButton();
		this.deleteButton = new JButton();
		this.closeButton = new JButton();
		this.cancelButton = new JButton();

		initComponents();
	}

	public UndoManager getUndoManager() {
		return this.undoManager;
	}

	public void setUndoManager(EditUndoManager anUndoManager) {
		this.undoManager = anUndoManager;
	}

	public boolean undoManagerAddEdit(String presentationName) {
		if (this.undoManager == null || !this.undoManager.isEnabled())
			return false;
		EdType type = this.typeEditor.getSelectedArcType();
		type.setContextUsage(String.valueOf(type.hashCode()));
		TypeReprData data = new TypeReprData(type);
		Vector<TypeReprData> vec = new Vector<TypeReprData>(1);
		vec.add(data);
		this.undoObj = new Pair<String, Vector<?>>(presentationName, vec);
		this.newEdit = new StateEdit(this, presentationName);
		return this.undoManager.addEdit(this.newEdit);
	}

	public void undoManagerEndEdit(EdType type) {
		if (this.undoObj == null 
				|| this.undoManager == null  || !this.undoManager.isEnabled()
				|| this.newEdit == null
				|| type == null)
			return;
		// System.out.println("ArcTypePropertyEditor.undoManagerEndEdit
		// newEdit.end() BEGIN type: "+type);
		String first = this.undoObj.first;
		String kind = "";
		Vector<TypeReprData> gos = new Vector<TypeReprData>(1);
		gos.add(new TypeReprData(type));

		if (first.equals(EditUndoManager.CREATE_DELETE))
			kind = EditUndoManager.DELETE_CREATE;
		else if (first.equals(EditUndoManager.DELETE_CREATE))
			kind = EditUndoManager.CREATE_DELETE;
		else if (first.equals(EditUndoManager.COMMON_DELETE_CREATE))
			kind = EditUndoManager.CREATE_DELETE;
		else if (first.equals(EditUndoManager.CHANGE))
			kind = EditUndoManager.CHANGE;

		this.undoObj = new Pair<String, Vector<?>>(kind, gos);
		this.undoManager.end(this.newEdit);
		// System.out.println("ArcTypePropertyEditor.undoManagerEndEdit
		// newEdit.end() DONE "+kind);
	}

	/**
	 * Implements the interface <EM>StateEditable</EM>. The type
	 * representation data <EM>TypeReprData</EM> is stored into <EM>state</EM>.
	 */
	public void storeState(Hashtable<Object, Object> state) {
		if (this.undoObj.first != null && this.undoObj.second != null) {
			state.put(String.valueOf(this.hashCode()), this.undoObj);
			if (this.undoObj.first.equals(EditUndoManager.COMMON_DELETE_CREATE))
				this.typeEditor.storeState(state);
		}
	}

	/**
	 * Implements the interface <EM>StateEditable</EM>. The type
	 * representation data <EM>TypeReprData</EM> is extracted out of <EM>state</EM>
	 * and applyed to this type.
	 */
	public void restoreState(Hashtable<?, ?> state) {
		// System.out.println("ArcTypePropertyEditor.restoreState state:
		// "+state);
		if (state == null)
			return;
		EdType type = null;
		Pair<?,?> obj = (Pair) state.get(String.valueOf(this.hashCode()));
		if (obj == null)
			return;
		if (obj.first == null || obj.second == null)
			return;

		String op = (String) (obj).first;
		Vector<?> vec = (Vector) (obj).second;
		TypeReprData data = (TypeReprData) vec.firstElement();

		if (op.equals(EditUndoManager.CHANGE)) {
			// changing
			int hc = data.getTypeHashCode();
			int index = -1;
			for (int i = 0; i < this.typeEditor.getArcTypes().size(); i++) {
				type = this.typeEditor.getArcTypes().get(i);
				if (type.hashCode() == hc) {
					index = i;
					break;
				} else if (type.getContextUsage().indexOf(
						":" + String.valueOf(hc) + ":") != -1) {
					index = i;
					break;
				} else
					type = null;
			}
			if (type != null) {
				data.restoreTypeFromTypeRepr(type);
				refreshTypeProperty(type, index);
			}
		} else if (op.equals(EditUndoManager.CREATE_DELETE)) {
			// creating --> delete
			int hc = data.getTypeHashCode();
			// System.out.println("EditUndoManager.CREATE_DELETE hc: "+hc);
			for (int i = 0; i < this.typeEditor.getArcTypes().size(); i++) {
				type = this.typeEditor.getArcTypes().get(i);
				// System.out.println(type.getName()+" "+ type.hashCode()+ "
				// "+type.getContextUsage());
				if (type.hashCode() == hc) {
					break;
				} else if (type.getContextUsage().indexOf(
						":" + String.valueOf(hc) + ":") != -1) {
					break;
				} else
					type = null;
			}
			if (type != null) {
				if (type.isArcType()) {
					if (this.typeEditor.deleteArcType(type, false))
						;
					else
						undoManagerAddEdit(EditUndoManager.CREATE_DELETE);
				}
			}
		} else if (op.equals(EditUndoManager.DELETE_CREATE)) {
			// deleting --> create
//			type = data.createTypeFromTypeRepr(typeEditor.getGraGra()
//					.getBasisGraGra().createType());
			type = data.createTypeFromTypeRepr(this.typeEditor.getGraGra()
					.getBasisGraGra().createArcType(false));
			this.typeEditor.addArcType(type);
			this.palette.enableArcTypeDeleteButton(true);
			this.palette.enableArcTypeModifyButton(true);
		} else if (op.equals(EditUndoManager.COMMON_DELETE_CREATE)) {
			// deleting --> create
//			type = data.createTypeFromTypeRepr(typeEditor.getGraGra()
//					.getBasisGraGra().createType());
			type = data.createTypeFromTypeRepr(this.typeEditor.getGraGra()
					.getBasisGraGra().createArcType(false));
			this.typeEditor.addArcType(type);
			this.palette.enableArcTypeDeleteButton(true);
			this.palette.enableArcTypeModifyButton(true);
			if (state.get(this.typeEditor) != null)
				this.typeEditor.restoreState(state);
		}
	}

	public TypeEditor getTypeEditor() {
		return this.typeEditor;
	}

	protected JDialog getDialog() {
		return this.dialog;
	}

	public boolean isVisible() {
		return this.dialog.isVisible();
	}

	private void showPropertyDialog() {
		if (!this.dialog.isVisible()) {
			if ((this.location.y+this.dialog.getHeight()+10) > Toolkit.getDefaultToolkit().getScreenSize().height) {
				this.location.y = Toolkit.getDefaultToolkit().getScreenSize().height-(this.dialog.getHeight()+10);
			}
			this.dialog.setLocation(this.location.x, this.location.y);
			this.dialog.setVisible(true);
		}
		this.changed = false;
	}

	private void initComponents() {
		final JPanel p1 = new JPanel(new BorderLayout());
		final JPanel namePanel = initName();
		p1.add(namePanel, BorderLayout.CENTER);

		final JPanel p2 = new JPanel(new GridLayout(1, 0, 5, 5));
		final JPanel colorPanel = initColors();
		final JPanel shapePanel = initShapes();
		p2.add(colorPanel);
		p2.add(shapePanel);

		final JPanel commentPanel = initComment();
		final JPanel closePanel = initButtons();

		final JPanel p3 = new JPanel(new BorderLayout());
		p3.add(commentPanel, BorderLayout.CENTER);
		p3.add(closePanel, BorderLayout.SOUTH);

		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);

		this.dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				// close();
				ArcTypePropertyEditor.this.dialog.setVisible(false);
			}
		});
		this.dialog.setContentPane(this);
		this.dialog.validate();
		this.dialog.pack();
		this.dialog.setAlwaysOnTop(true);
	}

	private JPanel initName() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(" Name "));

		
		this.nameEditor.setForeground(this.typeColor);
		this.nameEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addType(true)) {
					ArcTypePropertyEditor.this.modifyButton.setEnabled(true);
					ArcTypePropertyEditor.this.deleteButton.setEnabled(true);
				}
			}});
		this.nameEditor.setForeground(this.typeColor);
		final JScrollPane scrolltf = new JScrollPane(this.nameEditor);
		scrolltf.setPreferredSize(new Dimension(50, 40));
		panel.add(scrolltf, BorderLayout.CENTER);
		return panel;
	}

	private JPanel initColors() {
		final JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(" Color "));
		// colorGroup = new ButtonGroup();

		final JCheckBox black = new JCheckBox("Black", null, true);
		this.colorGroup.add(black);
		black.setForeground(Color.black);
		if (this.typeColor == Color.black)
			black.setSelected(true);
		else
			black.setSelected(false);
		black.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					ArcTypePropertyEditor.this.nameEditor.setForeground(Color.black);
				}
			}
		});
		panel.add(black);

		final JCheckBox red = new JCheckBox("Red", null);
		this.colorGroup.add(red);
		red.setForeground(Color.red);
		if (this.typeColor == Color.red)
			red.setSelected(true);
		else
			red.setSelected(false);
		red.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					ArcTypePropertyEditor.this.nameEditor.setForeground(Color.red);
				}
			}
		});
		panel.add(red);

		final JCheckBox orange = new JCheckBox("Orange", null);
		this.colorGroup.add(orange);
		orange.setForeground(Color.orange);
		if (this.typeColor == Color.orange)
			orange.setSelected(true);
		else
			orange.setSelected(false);
		orange.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					ArcTypePropertyEditor.this.nameEditor.setForeground(Color.orange);
				}
			}
		});
		panel.add(orange);

		final JCheckBox blue = new JCheckBox("Blue", null);
		this.colorGroup.add(blue);
		blue.setForeground(Color.blue);
		if (this.typeColor == Color.blue)
			blue.setSelected(true);
		else
			blue.setSelected(false);
		blue.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					ArcTypePropertyEditor.this.nameEditor.setForeground(Color.blue);
				}
			}
		});
		panel.add(blue);

		final JCheckBox pink = new JCheckBox("Pink", null);
		this.colorGroup.add(pink);
		pink.setForeground(Color.pink);
		if (this.typeColor == Color.pink)
			pink.setSelected(true);
		else
			pink.setSelected(false);
		pink.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					ArcTypePropertyEditor.this.nameEditor.setForeground(Color.pink);
				}
			}
		});
		panel.add(pink);

		// moreColor = new JCheckBox("Other", null);
		this.colorGroup.add(this.moreColor);
		if (!this.colorGroup.isSelected(this.colorGroup.getSelection()))
			this.moreColor.setSelected(true);
		else
			this.moreColor.setSelected(false);
		this.moreColor.setForeground(Color.black);
		this.moreColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				ArcTypePropertyEditor.this.colorChooser.showColorDialog(ArcTypePropertyEditor.this.dialog, ArcTypePropertyEditor.this.location);
			}
		});

		panel.add(this.moreColor);
		return panel;
	}

	private JPanel initShapes() {
		final JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(" Style "));
		// shapeGroup = new ButtonGroup();

		final JCheckBox solid = new JCheckBox("Solid", null, true);
		this.shapeGroup.add(solid);
		if (this.typeShape == EditorConstants.SOLID)
			solid.setSelected(true);
		else
			solid.setSelected(false);
		panel.add(solid);

		final JCheckBox dot = new JCheckBox("Dot", null);
		this.shapeGroup.add(dot);
		if (this.typeShape == EditorConstants.DOT)
			dot.setSelected(true);
		else
			dot.setSelected(false);
		panel.add(dot);

		final JCheckBox dash = new JCheckBox("Dash", null);
		this.shapeGroup.add(dash);
		if (this.typeShape == EditorConstants.DASH)
			dash.setSelected(true);
		else
			dash.setSelected(false);
		panel.add(dash);

		panel.add(new JLabel("      "));
		panel.add(this.boldCB);
		
		return panel;
	}

	private JPanel initComment() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(" Comment "));

		// commentEditor = new JEditorPane();
		// DefaultEditorKit kit = (DefaultEditorKit)
		// JEditorPane.createEditorKitForContentType("text/plain");
		// PlainDocument document = (PlainDocument) kit.createDefaultDocument();
		this.commentEditor.setText(this.typeComment);
		final JScrollPane scrollpane = new JScrollPane(this.commentEditor);
		scrollpane.setPreferredSize(new Dimension(200, 100));
		panel.add(scrollpane);
		return panel;
	}

	private JPanel initButtons() {
		final JPanel p = new JPanel(new GridLayout(2, 0, 10, 10));
		final JPanel p1 = new JPanel(new GridLayout(0, 3, 10, 10));
		final JPanel p2 = new JPanel(new GridLayout(0, 2, 10, 10));

		// addButton = new JButton();
		this.addButton.setActionCommand("add");
		this.addButton.setText("Add");
		this.addButton.setToolTipText("Add new type.");
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addType(false);
				ArcTypePropertyEditor.this.modifyButton.setEnabled(true);
				ArcTypePropertyEditor.this.deleteButton.setEnabled(true);
			}
		});

		// modifyButton = new JButton();
		this.modifyButton.setActionCommand("change");
		this.modifyButton.setText("Modify");
		this.modifyButton.setToolTipText("Modify type properties.");
		this.modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeType();
			}
		});

		// deleteButton = new JButton();
		this.deleteButton.setActionCommand("delete");
		this.deleteButton.setText("Delete");
		this.deleteButton.setToolTipText("Delete type.");
		this.deleteButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				deleteType();
			}
		});

		p1.add(this.addButton);
		p1.add(this.modifyButton);
		p1.add(this.deleteButton);

		// closeButton = new JButton();
		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.setToolTipText("Accept type properties and close dialog.");
		this.closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

		// cancelButton = new JButton();
		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.cancelButton.setToolTipText("Cancel changes and close dialog.");
		this.cancelButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				cancel();
			}
		});

		p2.add(this.closeButton);
		p2.add(this.cancelButton);

		p.add(p1);
		p.add(p2);
		return p;
	}

	public void enableChangeButton(boolean b) {
		this.modifyButton.setEnabled(b);
	}

	public void enableDeleteButton(boolean b) {
		this.deleteButton.setEnabled(b);
	}

	private void accept() {
		this.changed = false;
		if (!this.typeName.equals(this.nameEditor.getText())) {
			this.changed = true;
			this.typeName = this.nameEditor.getText().replaceAll(" ", "");
		}

		Enumeration<AbstractButton> en = this.colorGroup.getElements();
		while (en.hasMoreElements()) {
			JCheckBox cb = (JCheckBox) en.nextElement();
			if (cb.isSelected()) {
				Color c = getTypeColor(cb.getText());
				if (!this.typeColor.toString().equals(c.toString())) {
					this.changed = true;
					this.typeColor = c;
					this.nameEditor.setForeground(this.typeColor);
					break;
				}
			} else if (this.moreColor.isSelected()) {
				Color c = this.moreColor.getForeground();
				if (!this.typeColor.equals(c)) {
					this.changed = true;
					this.typeColor = c;
					this.nameEditor.setForeground(this.typeColor);
					break;
				}
			}
		}
		en = this.shapeGroup.getElements();
		while (en.hasMoreElements()) {
			JCheckBox cb = (JCheckBox) en.nextElement();
			if (cb.isSelected()) {
				int sh = getTypeShape(cb.getText());
				if (this.typeShape != sh) {
					this.changed = true;
					this.typeShape = sh;
					break;
				}
			}
		}
		
		// bold 
		if (this.boldStyle != this.boldCB.isSelected()) {
			this.changed = true;
			this.boldStyle = this.boldCB.isSelected();			
		}
		
		if (!this.typeComment.equals(this.commentEditor.getText())) {
			this.typeComment = this.commentEditor.getText();
			// changed = true;
		}
	}

	protected boolean addType(boolean suppressWarning) {
		boolean result = false;
		accept();
		EdType t = this.typeEditor.addArcType(this.typeName, this.typeColor, 
				this.typeShape, this.boldStyle, this.typeComment);
		if (t == null) {
			JOptionPane.showMessageDialog(this.dialog, "Type already exists.");
		} else {
			result = true;
		}
		this.changed = false;
		return result;
	}

	void changeType() {
		accept();
		if (!this.changed)
			; // JOptionPane.showMessageDialog(dialog, "Nothing to change.");
		else if (!this.typeEditor.changeSelectedArcType(this.typeName, this.typeColor,
				this.typeShape, this.boldStyle, this.typeComment))
			JOptionPane.showMessageDialog(this.dialog, "Type already exists.");
		else
			this.changed = false;
	}

	void deleteType() {
		this.typeEditor.deleteSelectedArcType(true);
	}

	void close() {
		accept();
		if (this.changed) {
			JOptionPane
					.showMessageDialog(this.dialog,
							"You have changed the type properties.\nPlease save or cancel the entries.");
			return;
		}
		hideDialog();
	}

	void cancel() {
		hideDialog();
	}

	void hideDialog() {
		if (!this.palette.isArcTypesEmpty()) {
			this.palette.enableArcTypeModifyButton(true);
			this.palette.enableArcTypeDeleteButton(true);
		} else {
			this.palette.enableArcTypeModifyButton(false);
			this.palette.enableArcTypeDeleteButton(false);
		}
		this.dialog.setVisible(false);
	}

	public void setSelectedTypeProperty(EdType t) {
		this.typeName = t.getName();
		this.typeColor = t.getColor();
		this.typeShape = t.getShape();
		this.boldStyle = t.hasFilledShape();
		this.typeComment = t.getBasisType().getTextualComment();
		setTypeProperty();
		this.changed = false;
	}

	public void setSelectedTypeProperty(String tname, Color tcolor, int tshape, boolean tboldstyle,
			String tcomment) {
		this.typeName = tname;
		this.typeColor = tcolor;
		this.typeShape = tshape;
		this.boldStyle = tboldstyle;
		this.typeComment = tcomment;
		setTypeProperty();
		this.changed = false;
	}

	public void setNewTypeDefaultProperty() {
		this.typeName = "";
		this.typeColor = Color.black;
		this.typeShape = EditorConstants.SOLID;
		this.typeComment = "";
		setTypeProperty();
		this.changed = false;
	}

	private void refreshTypeProperty(EdType t, int index) {
		this.typeName = t.getName();
		this.typeColor = t.getColor();
		this.typeShape = t.getShape();
		this.typeComment = t.getBasisType().getTextualComment();
		setTypeProperty();
		accept();
		JLabel l = this.typeEditor.makeArcTypeLabel(t);
		// palette.refreshArcType(l, index);
		this.palette.changeArcType(l, index);
		this.typeEditor.fireTypeEvent(new TypeEvent(this, t, index,
				TypeEvent.MODIFIED_CHANGED));
		this.changed = false;
	}

	private void setTypeProperty() {
		this.nameEditor.setText(this.typeName);
		this.nameEditor.setForeground(this.typeColor);

		String colorStr = getTypeColorStr(this.typeColor);
		Enumeration<AbstractButton> en = this.colorGroup.getElements();
		while (en.hasMoreElements()) {
			JCheckBox cb = (JCheckBox) en.nextElement();
			if (cb.getText().equals(colorStr)) {
				if (cb.getText().equals("Other")) {		
					this.moreColor.setForeground(this.typeColor);	
				}
				cb.setSelected(true);
				break;
			} 
		}

		String shapeStr = getTypeShapeStr(this.typeShape);
		en = this.shapeGroup.getElements();
		while (en.hasMoreElements()) {
			JCheckBox cb = (JCheckBox) en.nextElement();
			if (cb.getText().equals(shapeStr)) {
				cb.setSelected(true);
			}
		}

		this.boldCB.setSelected(this.boldStyle);
		
		this.commentEditor.setText(this.typeComment);
	}

	// implements ChangeListener
	public void stateChanged(ChangeEvent e) {
		Color newColor = this.colorChooser.getColor();
		this.moreColor.setForeground(newColor);
		this.nameEditor.setForeground(newColor);
	}

	public String getName() {
		return this.typeName;
	}

	public Color getSelectedColor() {
		return this.typeColor;
	}

	public int getSelectedShape() {
		return this.typeShape;
	}

	private String getTypeShapeStr(int sh) {
		String s = "";
		switch (sh) {
		case EditorConstants.SOLID:
			s = "Solid";
			break;
		case EditorConstants.DOT:
			s = "Dot";
			break;
		case EditorConstants.DASH:
			s = "Dash";
			break;
		default:
			break;
		}
		return s;
	}

	public void invoke() {
		this.location = new Point(100, 100);
		showPropertyDialog();
	}

	public void invoke(int x, int y) {
		this.location = new Point(x, y);
		showPropertyDialog();
	}

	private Color getTypeColor(String col) {
		if (col.equals("Black"))
			return Color.black;
		else if (col.equals("Red"))
			return Color.red;
		else if (col.equals("Blue"))
			return Color.blue;
		else if (col.equals("White"))
			return Color.white;
		else if (col.equals("Yellow"))
			return Color.yellow;
		else if (col.equals("Orange"))
			return Color.orange;
		else if (col.equals("Pink"))
			return Color.pink;
		else if (col.equals("Other"))
			return this.moreColor.getForeground();
		else
			return Color.black;
	}

	private String getTypeColorStr(Color col) {
		if (col.equals(Color.black))
			return "Black";
		else if (col.equals(Color.red))
			return "Red";
		else if (col.equals(Color.blue))
			return "Blue";
		else if (col.equals(Color.white))
			return "White";
		else if (col.equals(Color.yellow))
			return "Yellow";
		else if (col.equals(Color.orange))
			return "Orange";
		else if (col.equals(Color.pink))
			return "Pink";
		else
			return "Other";
	}

	int getTypeShape(String shape) {
		// first the edge shape
		if (shape.equals("Solid"))
			return EditorConstants.SOLID;
		else if (shape.equals("Dash"))
			return EditorConstants.DASH;
		else
			return EditorConstants.DOT;
	}

	public Icon getArcTypeIcon(int shape, Color color, boolean bold) {
		Icon icon = null;
		switch (shape) {
		case EditorConstants.SOLID:
			icon = (new agg.gui.icons.ColorSolidLineIcon(color, bold));
			break;
		case EditorConstants.DASH:
			icon = (new agg.gui.icons.ColorDashLineIcon(color, bold));
			break;
		case EditorConstants.DOT:
			icon = (new agg.gui.icons.ColorDotLineIcon(color, bold));
			break;
		default:
			break;
		}
		return icon;
	}

	private EditUndoManager undoManager;

	private StateEdit newEdit;

//	private TypeReprData typeReprData;

	private Pair<String, Vector<?>> undoObj;

//	private int undoID;

	private final JFrame applFrame;

	private final TypePalette palette;

	private final TypeEditor typeEditor;

	private String typeName = "";

	private String typeComment = "";

//	private JLabel typeIconLabel;

	private Color typeColor = Color.black;

	private int typeShape = EditorConstants.SOLID;

	protected boolean boldStyle;
	
	protected Point location;

	protected final ColorChooserDialog colorChooser;

	private final ButtonGroup colorGroup, shapeGroup;

	protected final JTextField nameEditor;

	private final JEditorPane commentEditor;

	protected final JButton addButton, modifyButton, deleteButton, closeButton,
			cancelButton;

	private final JCheckBox moreColor, boldCB;

	protected final JDialog dialog;

	boolean changed = false;
}
