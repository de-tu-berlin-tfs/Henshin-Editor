// $Id: NodeTypePropertyEditor.java,v 1.5 2010/09/23 08:24:01 olga Exp $

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
import javax.swing.JFileChooser;
import javax.swing.JOptionPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.JScrollPane;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.ButtonGroup;
import javax.swing.JEditorPane;
import javax.swing.event.ChangeListener;
import javax.swing.event.ChangeEvent;
import javax.swing.border.TitledBorder;
import javax.swing.undo.*;

import agg.editor.impl.EdType;
import agg.editor.impl.EditUndoManager;
import agg.editor.impl.TypeReprData;
import agg.gui.editor.EditorConstants;
import agg.gui.event.TypeEvent;
import agg.gui.saveload.AGGFileFilter;
import agg.util.Pair;
import agg.gui.animation.AnimationParam;
import agg.gui.animation.AnimationParamDialog;

public class NodeTypePropertyEditor extends JPanel implements ChangeListener,
		StateEditable {

	public NodeTypePropertyEditor(JFrame aggappl, TypeEditor typeEditor,
			TypePalette palette) {
		super(new BorderLayout());
		this.setBorder(new TitledBorder(" Node Type Properties "));

		this.applFrame = aggappl;
		this.typeEditor = typeEditor;
		this.palette = palette;

		this.colorChooser = new ColorChooserDialog();
		this.colorChooser.addChangeListener(this);

		this.fileChooser = new JFileChooser(System.getProperty("user.dir"));
		final AGGFileFilter filter = new AGGFileFilter();
		filter.addExtension("jpg");
		filter.addExtension("gif");
		filter.setDescription("JPG & GIF Images");
		this.fileChooser.setFileFilter(filter);

		this.dialog = new JDialog(this.applFrame, " Node Type Editor ");
		this.nameEditor = new JTextField(this.typeName);
		this.colorGroup = new ButtonGroup();
		this.moreColor = new JCheckBox("Other", null);
		this.shapeGroup = new ButtonGroup();
		this.filledCB = new JCheckBox("filled", null);
		this.animatedCB = new JCheckBox("animated", null);
		this.animationParam = new AnimationParam(0, 0, 10, 0);
		this.imageCB = new JCheckBox("Load", null);
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
		EdType type = this.typeEditor.getSelectedNodeType();
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
		// System.out.println("NodeTypePropertyEditor.undoManagerEndEdit
		// newEdit.end() BEGIN");
		String first = this.undoObj.first;
		String kind = "";
		Vector<TypeReprData> gos = new Vector<TypeReprData>(1);

			TypeReprData data = new TypeReprData(type);
			gos.add(data);

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
		// System.out.println("NodeTypePropertyEditor.undoManagerEndEdit
		// newEdit.end() DONE "+kind);
	}

	/**
	 * Implements the interface <EM>StateEditable</EM>. The type
	 * representation data <EM>TypeReprData</EM> is stored into <EM>state</EM>.
	 */
	public void storeState(Hashtable<Object, Object> state) {
		if (this.undoObj.first != null && this.undoObj.second != null) {
			// System.out.println("NodeTypePropertyEditor.storeState state:
			// "+state);

			state.put(String.valueOf(this.hashCode()), this.undoObj);
			// System.out.println("NodeTypePropertyEditor.storeState state:
			// "+state);

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
		// System.out.println("NodeTypePropertyEditor.restoreState state:
		// "+state);
		if (state == null)
			return;
		// System.out.println("NodeTypePropertyEditor.restoreState state:
		// "+state);
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
			for (int i = 0; i < this.typeEditor.getNodeTypes().size(); i++) {
				type = this.typeEditor.getNodeTypes().get(i);
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
			for (int i = 0; i < this.typeEditor.getNodeTypes().size(); i++) {
				type = this.typeEditor.getNodeTypes().get(i);
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
				if (type.isNodeType()) {
					if (this.typeEditor.deleteNodeType(type, false))
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
					.getBasisGraGra().createNodeType(false));
			// System.out.println(typeEditor.getGraGra().getTypeSet().getBasisTypeSet().getTypesVec());
			// System.out.println(type.getName()+" similar:
			// "+typeEditor.getGraGra().getTypeSet().getBasisTypeSet().getSimilarType(type.getBasisType()));
			this.typeEditor.addNodeType(type);
			this.palette.enableNodeTypeDeleteButton(true);
			this.palette.enableNodeTypeModifyButton(true);
		} else if (op.equals(EditUndoManager.COMMON_DELETE_CREATE)) {
			// deleting --> create
//			type = data.createTypeFromTypeRepr(typeEditor.getGraGra()
//					.getBasisGraGra().createType());
			type = data.createTypeFromTypeRepr(this.typeEditor.getGraGra()
					.getBasisGraGra().createNodeType(false));
			this.typeEditor.addNodeType(type);
			this.palette.enableNodeTypeDeleteButton(true);
			this.palette.enableNodeTypeModifyButton(true);
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

		final JPanel imagePanel = initImageAndAnimation();
		
		final JPanel commentPanel = initComment();
		final JPanel closePanel = initButtons();

		final JPanel p3 = new JPanel(new BorderLayout());
		p3.add(imagePanel, BorderLayout.NORTH);
		p3.add(commentPanel, BorderLayout.CENTER);
		p3.add(closePanel, BorderLayout.SOUTH);

		this.add(p1, BorderLayout.NORTH);
		this.add(p2, BorderLayout.CENTER);
		this.add(p3, BorderLayout.SOUTH);

		this.dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				// close();
				NodeTypePropertyEditor.this.dialog.setVisible(false);
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

		this.nameEditor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (addType(true)) {
					NodeTypePropertyEditor.this.modifyButton.setEnabled(true);
					NodeTypePropertyEditor.this.deleteButton.setEnabled(true);
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
					NodeTypePropertyEditor.this.nameEditor.setForeground(Color.black);
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
					NodeTypePropertyEditor.this.nameEditor.setForeground(Color.red);
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
					NodeTypePropertyEditor.this.nameEditor.setForeground(Color.orange);
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
					NodeTypePropertyEditor.this.nameEditor.setForeground(Color.blue);
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
					NodeTypePropertyEditor.this.nameEditor.setForeground(Color.pink);
				}
			}
		});
		panel.add(pink);

		this.colorGroup.add(this.moreColor);
		if (!this.colorGroup.isSelected(this.colorGroup.getSelection())) {
			this.moreColor.setSelected(true);
		} else {
			this.moreColor.setSelected(false);
		}
		this.moreColor.setForeground(Color.black);
		this.moreColor.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				NodeTypePropertyEditor.this.colorChooser.showColorDialog(NodeTypePropertyEditor.this.dialog, NodeTypePropertyEditor.this.location);
			}
		});

		panel.add(this.moreColor);
		return panel;
	}

	private JPanel initShapes() {
		final JPanel panel = new JPanel(new GridLayout(0, 1));
		panel.setBorder(new TitledBorder(" Shape "));

		final JCheckBox rect = new JCheckBox("Rectangle", null, true);
		this.shapeGroup.add(rect);
		if (this.typeShape == EditorConstants.RECT)
			rect.setSelected(true);
		else
			rect.setSelected(false);
		panel.add(rect);

		final JCheckBox roundrect = new JCheckBox("Roundrect", null);
		this.shapeGroup.add(roundrect);
		if (this.typeShape == EditorConstants.ROUNDRECT)
			roundrect.setSelected(true);
		else
			roundrect.setSelected(false);
		panel.add(roundrect);

		final JCheckBox oval = new JCheckBox("Oval", null);
		this.shapeGroup.add(oval);
		if (this.typeShape == EditorConstants.OVAL)
			oval.setSelected(true);
		else
			oval.setSelected(false);
		panel.add(oval);

		final JCheckBox circle = new JCheckBox("Circle", null);
		this.shapeGroup.add(circle);
		if (this.typeShape == EditorConstants.CIRCLE)
			circle.setSelected(true);
		else
			circle.setSelected(false);				
		panel.add(circle);
		
		panel.add(new JLabel("      "));
		panel.add(this.filledCB);
		
		return panel;
	}

	private JPanel initImageAndAnimation() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(" Image "));

		if (this.imageFileName.equals("")) {
			this.imageCB.setText("Load");
			this.imageCB.setSelected(false);
		} else {
			this.imageCB.setText(this.imageFileName);
			this.imageCB.setSelected(true);
		}
		this.imageCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (NodeTypePropertyEditor.this.imageCB.isSelected()) {
					String fname = loadImage();
					if (!fname.equals("")) {
						NodeTypePropertyEditor.this.imageCB.setText(fname);
					} else {
						NodeTypePropertyEditor.this.imageCB.setText("Load");
						NodeTypePropertyEditor.this.imageCB.setSelected(false);
					}
				} else {
					NodeTypePropertyEditor.this.imageCB.setText("Load");
					NodeTypePropertyEditor.this.imageCB.setSelected(false);
				}
			}
		});
		panel.add(this.imageCB, BorderLayout.WEST);
		
		this.animatedCB.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (NodeTypePropertyEditor.this.animatedCB.isSelected()) {
//					NodeTypePropertyEditor.this.animationDialog = new AnimationParamDialog(animationParam);
					NodeTypePropertyEditor.this.animationDialog = new AnimationParamDialog(
							NodeTypePropertyEditor.this.animationParam,
							NodeTypePropertyEditor.this.typeEditor.getSelectedNodeType().getBasisType(),
							NodeTypePropertyEditor.this.typeEditor.getTypeSet().getBasisTypeSet().getTypeGraph());
					
					NodeTypePropertyEditor.this.animationDialog.showParameterDialog(300, 300);
				}
			}
		});
		panel.add(this.animatedCB, BorderLayout.EAST);
		return panel;
	}

	private JPanel initComment() {
		final JPanel panel = new JPanel(new BorderLayout());
		panel.setBorder(new TitledBorder(" Comment "));

		// commentEditor = new JEditorPane();
		// final DefaultEditorKit kit = (DefaultEditorKit)
		// JEditorPane.createEditorKitForContentType("text/plain");
		// final PlainDocument document = (PlainDocument)
		// kit.createDefaultDocument();
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

		this.addButton.setActionCommand("add");
		this.addButton.setText("Add");
		this.addButton.setToolTipText("Add new type.");
		this.addButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				addType(false);
				NodeTypePropertyEditor.this.modifyButton.setEnabled(true);
				NodeTypePropertyEditor.this.deleteButton.setEnabled(true);
			}
		});

		this.modifyButton.setActionCommand("change");
		this.modifyButton.setText("Modify");
		this.modifyButton.setToolTipText("Modify type properties.");
		this.modifyButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				changeType();
			}
		});

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

		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Close");
		this.closeButton.setToolTipText("Accept type properties and close dialog.");
		this.closeButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				close();
			}
		});

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
		// System.out.println("modifyButton size: "+modifyButton.getWidth());
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
				if (!this.typeColor.equals(c)) {
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

		// shape filled with color
		if (this.filledShape != this.filledCB.isSelected()) {
			this.changed = true;
			this.filledShape = this.filledCB.isSelected();			
		}
		
		// animated shape
		if (this.animatedShape != this.animatedCB.isSelected()) {
			this.changed = true;
			this.animatedShape = this.animatedCB.isSelected();	
		}
		if (this.animatedCB.isSelected()) {
			if (this.animationDialog != null) { 						
				this.animationDialog.getAnimationParameter();
				if (this.animationDialog.isVisible())
					this.animationDialog.setVisible(false);
			}			
		}
		
		// resourcesPath && image file name - already set
		String imagefname = this.imageCB.getText();
		if (!imagefname.equals(this.imageFileName)) {
			if (this.imageFileName.length()==0 && imagefname.equals("Load")) {
			}
			else {
				this.imageFileName = imagefname;
				this.changed = true;
			}
//			System.out.println(imageFileName+"   changed: "+changed);
		}

		
		if (!this.typeComment.equals(this.commentEditor.getText())) {
			this.typeComment = this.commentEditor.getText();
			this.changed = true;
		}
	}

	protected boolean addType(boolean suppressWarning) {
		boolean result = false;
		accept();
		EdType t = this.typeEditor.addNodeType(this.typeName, this.typeColor, this.typeShape, this.filledShape,
				this.resourcesPath, this.imageFileName, this.typeComment, this.animatedShape);
						
		if (t == null) {
			if (!suppressWarning)
				JOptionPane.showMessageDialog(this.dialog, "Type already exists.");
		} else {
			// set animation parameter 
			if (t.isAnimated()) {
				resetAnimationParameterOfType(t);
				if (this.animationDialog != null && this.animationDialog.hasChanged()) {
					this.typeEditor.fireTypeEvent(new TypeEvent(this, TypeEvent.TYPE_ANIMATED_CHANGED));
					this.animationDialog.unsetChanged();
				}
			}
			result = true;
		}
		this.changed = false;
		return result;
	}

	protected void changeType() {
		accept();
		if (this.changed) {
			if (!this.typeEditor.changeSelectedNodeType(this.typeName, this.typeColor,
					this.typeShape, this.filledShape, 
					this.resourcesPath, this.imageFileName, this.typeComment, this.animatedShape)) {
				JOptionPane.showMessageDialog(this.dialog, "Type already exists.");
				this.changed = false;
				return;
			} 
		}
		
		EdType t = this.typeEditor.getSelectedNodeType();
		// set animation parameter 
		if (t.isAnimated()) {
			resetAnimationParameterOfType(t);
			if (this.animationDialog != null && this.animationDialog.hasChanged()) {
				this.typeEditor.fireTypeEvent(new TypeEvent(this, TypeEvent.TYPE_ANIMATED_CHANGED));
				this.animationDialog.unsetChanged();
			}
		}
		
		this.changed = false;
	}

	private void setAnimationParameter(AnimationParam p) {
		if (p != null) {
			this.animationParam.kind = p.kind;
			this.animationParam.step = p.step;
			this.animationParam.delay = p.delay;
			this.animationParam.plus = p.plus;
			this.animationParam.targetEdgeTypeName = p.targetEdgeTypeName;
		}		
	}
	
	private void resetAnimationParameterOfType(final EdType t) {
		t.animationParameter.kind = this.animationParam.kind;
		t.animationParameter.step = this.animationParam.step;
		t.animationParameter.delay = this.animationParam.delay;
		t.animationParameter.plus = this.animationParam.plus;
		t.animationParameter.targetEdgeTypeName = this.animationParam.targetEdgeTypeName;
	}
	
	protected void deleteType() {
		this.typeEditor.deleteSelectedNodeType(true);
	}

	protected void close() {
		accept();
		if (this.changed) {
			JOptionPane
					.showMessageDialog(this.dialog,
							"You have changed the type.\nPlease save it or cancel the entries.");
			return;
		}
		hideDialog();
	}

	protected void cancel() {
		hideDialog();
	}

	protected void hideDialog() {
		if (!this.palette.isNodeTypesEmpty()) {
			this.palette.enableNodeTypeModifyButton(true);
			this.palette.enableNodeTypeDeleteButton(true);
		} else {
			this.palette.enableNodeTypeModifyButton(false);
			this.palette.enableNodeTypeDeleteButton(false);
		}
		this.dialog.setVisible(false);
	}

	protected String loadImage() {
		String imageFile = "";
		int returnVal = this.fileChooser.showOpenDialog(this.dialog);
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.fileChooser.getSelectedFile() != null
					&& !this.fileChooser.getSelectedFile().getName().equals("")) {
				this.resourcesPath = this.fileChooser.getCurrentDirectory().toString();
				imageFile = this.fileChooser.getSelectedFile().getName();
			} else {
				this.resourcesPath = System.getProperty("user.dir");
				imageFile = "";
			}
		}
		return imageFile;
	}

	public void setSelectedTypeProperty(EdType t) {
		this.typeName = t.getName();
		this.typeColor = t.getColor();
		this.typeShape = t.getShape();
		this.filledShape = t.hasFilledShape();
		
		this.animatedShape = t.isAnimated();	
		this.setAnimationParameter(t.animationParameter);
		
		this.resourcesPath = t.getResourcesPath();
		this.imageFileName = t.getImageFileName();
		this.typeComment = t.getBasisType().getTextualComment();
		setTypeProperty();
		this.changed = false;
	}

	public void setSelectedTypeProperty(String tname, Color tcolor, int tshape, boolean tfilledshape,
			String tresourcespath, String timage, String tcomment, boolean tanimated) {
		this.typeName = tname;
		this.typeColor = tcolor;
		this.typeShape = tshape;
		this.filledShape = tfilledshape;
		this.animatedShape = tanimated;
		this.resourcesPath = tresourcespath;
		this.imageFileName = timage;
		this.typeComment = tcomment;
		setTypeProperty();
		this.changed = false;
	}

	public void setNewTypeDefaultProperty() {
		this.typeName = "";
		this.typeColor = Color.black;		
		this.typeShape = EditorConstants.RECT;
		this.filledShape = false;
		this.resourcesPath = "";
		this.imageFileName = "";
		this.typeComment = "";
		setTypeProperty();
		this.changed = false;
	}

	private void refreshTypeProperty(EdType t, int index) {
		this.typeName = t.getName();
		this.typeColor = t.getColor();
		this.typeShape = t.getShape();
		this.filledShape = t.hasFilledShape();
		
		this.animatedShape = t.isAnimated();
		this.setAnimationParameter(t.animationParameter);
		
		this.resourcesPath = t.getResourcesPath();
		this.imageFileName = t.getImageFileName();
		this.typeComment = t.getBasisType().getTextualComment();
		setTypeProperty();
		accept();
		JLabel l = this.typeEditor.makeNodeTypeLabel(t);
		this.palette.refreshNodeType(l, index);
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

		this.filledCB.setSelected(this.filledShape);
		
		this.animatedCB.setSelected(this.animatedShape);		
		if (this.animationDialog != null) {
			this.animationDialog.setVisible(false);
		} 
		
		if ((this.imageFileName.indexOf(".gif") != -1)
				|| (this.imageFileName.indexOf(".jpg") != -1)) {			
			this.imageCB.setText(this.imageFileName);
			this.imageCB.setSelected(true);
		} else {
			this.imageFileName = "";
			this.imageCB.setText("Load");
			this.imageCB.setSelected(false);
		}

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
		case EditorConstants.RECT:
			s = "Rectangle";
			break;
		case EditorConstants.ROUNDRECT:
			s = "Roundrect";
			break;
		case EditorConstants.OVAL:
			s = "Oval";
			break;
		case EditorConstants.CIRCLE:
			s = "Circle";
			break;
		default:
			break;
		}
		return s;
	}

	public void invoke() {
		invoke(100, 100);
	}

	public void invoke(int x, int y) {
		this.location = new Point(x, y);
		showPropertyDialog();
	}

	public String getImageFileName() {
		return this.imageFileName;
	}

	/**
	 * Sets the path of type resources (gif or jpg files) to the specified path
	 */
	public void setResourcesPath(String path) {
		this.resourcesPath = path;
	}

	/**
	 * Gets the path of type resources (gif or jpg files)
	 */
	public String getResourcesPath() {
		return this.resourcesPath;
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

	private int getTypeShape(String shape) {
		if (shape.equals("Rectangle"))
			return EditorConstants.RECT;
		else if (shape.equals("Circle"))
			return EditorConstants.CIRCLE;
		else if (shape.equals("Oval"))
			return EditorConstants.OVAL;
		else
			return EditorConstants.ROUNDRECT;
	}

	public static Icon getNodeTypeIcon(int shape, Color color, boolean filled) {
		Icon icon = null;
		switch (shape) {
		case EditorConstants.RECT:
			icon = (new agg.gui.icons.RectShapeIcon(color, filled));
			break;
		case EditorConstants.ROUNDRECT:
			icon = (new agg.gui.icons.RoundRectShapeIcon(color, filled));
			break;
		case EditorConstants.CIRCLE:
			icon = (new agg.gui.icons.CircleShapeIcon(color, filled));
			break;
		case EditorConstants.OVAL:
			icon = (new agg.gui.icons.OvalShapeIcon(color, filled));
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

	protected int undoID;

	private final JFrame applFrame;

	private final TypePalette palette;

	protected final TypeEditor typeEditor;

	private String typeName = "";

	private String typeComment = "";

	private Color typeColor = Color.black;

	protected int typeShape = EditorConstants.RECT;
	
	protected boolean filledShape, animatedShape;

	protected final AnimationParam animationParam;
	
	protected AnimationParamDialog animationDialog;
	
	private final JFileChooser fileChooser;

	private String resourcesPath = System.getProperty("user.dir");

	private String imageFileName = "";

	protected Point location;

	protected final ColorChooserDialog colorChooser;

	private final ButtonGroup colorGroup, shapeGroup;

	protected final JTextField nameEditor;

	private final JEditorPane commentEditor;

	protected final JButton addButton, modifyButton, deleteButton, closeButton,
			cancelButton;

	protected final JCheckBox moreColor, imageCB, filledCB, animatedCB;

	protected final JDialog dialog;

	protected boolean changed = false;
}
