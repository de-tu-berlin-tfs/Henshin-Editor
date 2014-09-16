package agg.gui.treeview.nodedata;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JEditorPane;
import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.text.BadLocationException;
import javax.swing.text.DefaultEditorKit;
import javax.swing.text.PlainDocument;

import agg.cons.Formula;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;

public class GraGraTextualComment extends JDialog implements ActionListener {

	private Object src;

	private DefaultEditorKit kit;

	private PlainDocument document;

	// private HTMLEditorKit htmlkit;
	// private HTMLDocument htmldocument;
	private JEditorPane editor;

	private JButton closeButton;

	private JButton cancelButton;

	public GraGraTextualComment(JFrame parent, int locationX, int locationY,
			Object source) {
		super(parent);
		setTitle("Textual Comment");
		// setModal(true);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				close();
			}
		});

		this.src = source;

		this.editor = new JEditorPane();

		this.kit = (DefaultEditorKit) JEditorPane
				.createEditorKitForContentType("text/plain");
		this.document = (PlainDocument) this.kit.createDefaultDocument();

		// htmlkit = new HTMLEditorKit();
		// htmlkit.setAutoFormSubmission(true);
		// htmldocument = (HTMLDocument) htmlkit.createDefaultDocument();
		// htmlkit.install(this.editor);
		// this.editor.setEditorKit(htmlkit);

		setTextToEdit(this.src);

		JScrollPane scrollpane = new JScrollPane(this.editor);
		scrollpane.setPreferredSize(new Dimension(300, 100));

		JPanel panel = new JPanel(new BorderLayout());
		JPanel buttonPanel = new JPanel(new GridLayout(0, 2, 5, 5));
		this.closeButton = new JButton();
		this.closeButton.setActionCommand("close");
		this.closeButton.setText("Save and Close");
		this.closeButton.setToolTipText("Save text and close dialog.");
		this.closeButton.addActionListener(this);

		this.cancelButton = new JButton();
		this.cancelButton.setActionCommand("cancel");
		this.cancelButton.setText("Cancel");
		this.cancelButton.setToolTipText("Cancel text changes and close dialog.");
		this.cancelButton.addActionListener(this);

		buttonPanel.add(this.closeButton);
		buttonPanel.add(this.cancelButton);

		panel.add(scrollpane, BorderLayout.CENTER);
		panel.add(buttonPanel, BorderLayout.SOUTH);
		panel.revalidate();

		setContentPane(panel);
		setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		setLocation(locationX, locationY);
		validate();
		pack();
	}

	public void addActionListener(ActionListener l) {
		this.closeButton.addActionListener(l);
		this.cancelButton.addActionListener(l);
	}

	public void actionPerformed(ActionEvent e) {
		Object source = e.getSource();
		if (source == this.closeButton)
			close();
		else if (source == this.cancelButton)
			cancel();
	}

	private void accept() {
		// System.out.println(this.editor.getContentType());
		try {
			this.document.remove(0, this.document.getLength());
			this.document.insertString(0, this.editor.getText().toString(), null);
			// String s = this.document.getText(0, this.document.getLength());
			// System.out.println("this.document: "+s);
			setTextToSource(this.src, this.editor.getText().toString());
		} catch (BadLocationException ex) {
		}
		// System.out.println("this.editor : "+this.editor.getText());
	}

	/** Accept text and exit the dialog */
	public void close() {
		if (this.src != null)
			accept();
		setVisible(false);
		dispose();
	}

	public void cancel() {
		setVisible(false);
		dispose();
	}

	public void setText(String text) {
		this.editor.setText(text);
	}

	public String getText() {
		return this.editor.getText();
	}

	private void setTextToEdit(Object source) {
		if (source instanceof GraGra)
			this.editor.setText(((GraGra) source).getTextualComment().toString());
		else if (source instanceof Graph)
			this.editor.setText(((Graph) source).getTextualComment().toString());
		else if (source instanceof OrdinaryMorphism)
			this.editor.setText(((OrdinaryMorphism) source).getTextualComment()
					.toString());
		else if (source instanceof Formula)
			this.editor.setText(((Formula) source).getTextualComment().toString());
		else if (source instanceof agg.xt_basis.Type)
			this.editor.setText(((agg.xt_basis.Type)source).getTextualComment().toString());
		else
			this.editor.setText("");
	}

	private void setTextToSource(Object source, String text) {
		if (source instanceof GraGra)
			((GraGra) source).setTextualComment(text);
		else if (source instanceof Graph)
			((Graph) source).setTextualComment(text);
		else if (source instanceof OrdinaryMorphism)
			((OrdinaryMorphism) source).setTextualComment(text);
		else if (source instanceof Formula)
			((Formula) source).setTextualComment(text);
		else if (source instanceof agg.xt_basis.Type)
			((agg.xt_basis.Type) source).setTextualComment(text);
	}
}
