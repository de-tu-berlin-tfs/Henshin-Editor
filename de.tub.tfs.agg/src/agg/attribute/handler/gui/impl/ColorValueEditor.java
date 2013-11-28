package agg.attribute.handler.gui.impl;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.event.ActionEvent;

import javax.swing.AbstractAction;
import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JColorChooser;
import javax.swing.JLabel;
import javax.swing.JPanel;

import agg.attribute.handler.AttrHandler;
import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.gui.HandlerExprEditor;
import agg.attribute.impl.AttrSession;

// import java.util.EventObject;

/**
 * @version $Id: ColorValueEditor.java,v 1.3 2010/08/23 07:30:29 olga Exp $
 * @author $Author: olga $
 */
public class ColorValueEditor extends AbstractHandlerEditor implements
		HandlerExprEditor {

	Color editedColor = null;

	HandlerType editedType = null;

	AttrHandler handler = null;

	public ColorValueEditor(AttrHandler h) {
		super();
		this.handler = h;
	}

	/**
	 * Returns a graphical component for displaying the specified expr. The
	 * 'availableSpace' limit should be honoured, since this is a service for
	 * displaying the expr in a table cell. However, the renderer can contain
	 * tools (e.g. buttons) for invoking its larger custom renderer. Either
	 * 'type' or 'exprToRender' cannot be null.
	 */
	public Component getRendererComponent(HandlerType type,
			HandlerExpr exprToRender, Dimension availableSpace) {
		if (type == null)
			return null;
		if (type.getClazz() != Color.black.getClass())
			return null;
		Color col = (Color) exprToRender;
		JLabel label = createColorLabel(col);
		if (availableSpace != null)
			label.setPreferredSize(availableSpace);
		return label;
	}

	/**
	 * Returns a graphical component for editing the specified expr. The
	 * 'availableSpace' is a recommendation when the editor wishes to be
	 * operatable in a compact table cell and needs not be taken into account.
	 * Either 'type' or 'exprToEdit' cannot be null.
	 */
	public Component getEditorComponent(HandlerType type,
			HandlerExpr exprToEdit, Dimension availableSpace) {
		if (type == null)
			return null;
		this.editedType = type;
		if (type.getClazz() != Color.black.getClass())
			return null;
		Color col = (Color) exprToEdit;
		Box box = Box.createHorizontalBox();
		JButton editB = new JButton("Choose");
		editB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent ev) {
				ColorValueEditor.this.editedColor = JColorChooser.showDialog(null, "Choose-A-Color",
						ColorValueEditor.this.editedColor);
			}
		});
		JButton minusB = new JButton("-");
		editB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent ev) {
				ColorValueEditor.this.editedColor = ColorValueEditor.this.editedColor.darker();
			}
		});
		JButton plusB = new JButton("+");
		editB.addActionListener(new AbstractAction() {
			public void actionPerformed(ActionEvent ev) {
				ColorValueEditor.this.editedColor = ColorValueEditor.this.editedColor.brighter();
			}
		});
		box.add(createColorLabel(col));
		box.add(minusB);
		box.add(plusB);
		box.add(editB);
		JPanel mainP = new JPanel(new BorderLayout());
		mainP.add(box, BorderLayout.CENTER);
		if (availableSpace != null)
			mainP.setPreferredSize(availableSpace);
		return mainP;
	}

	protected JLabel createColorLabel(Color col) {
		String colorRGB = "null";
		if (col != null) {
			colorRGB = col.toString();
			/*
			 * colorRGB = ( "" + col.getRed() + "-" + col.getGreen() + "-" +
			 * col.getBlue());
			 */
		}

		JLabel label = new JLabel(colorRGB);
		if (col != null)
			label.setBackground(col);
		return label;
	}

	/** Returns the edited expression. */
	public HandlerExpr getEditedExpr() {
		if (this.editedColor == null)
			return null;
		HandlerExpr expr = null;
		try {
			expr = this.handler.newHandlerValue(this.editedType, this.editedColor);
		} catch (AttrHandlerException ex) {
			AttrSession.warn(this, "Couldn't create HandlerExpr", true);
		}
		return expr;
	}
}
/*
 * $Log: ColorValueEditor.java,v $
 * Revision 1.3  2010/08/23 07:30:29  olga
 * tuning
 *
 * Revision 1.2  2007/09/10 13:05:30  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:58 enrico ***
 * empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:04 olga Version with Eclipse
 * 
 * Revision 1.3 2003/03/05 18:24:26 komm sorted/optimized import statements
 * 
 * Revision 1.2 2002/09/23 12:23:52 komm added type graph in xt_basis, editor
 * and GUI
 * 
 * Revision 1.1.1.1 2002/07/11 12:16:59 olga Imported sources
 * 
 * Revision 1.6 2000/04/05 12:08:33 shultzke serialVersionUID aus V1.0.0
 * generiert
 * 
 */
