/**
 * 
 */
package agg.gui.editor;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JDialog;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;


/**
 * The first button is predefined for YES_OPTION,
 * the second button - otherwise.
 * 
 * @author olga
 *
 */
public class DialogWithCheckBoxAndTwoButtons {

	public final static int YES_OPTION = 0;
	
	protected final JDialog dialog;
	
	protected final JButton button, button2;
	
	protected final JCheckBox checkBox;
	
	protected final JLabel contentText;
	
	protected int answer;
	
	public DialogWithCheckBoxAndTwoButtons(
								final JFrame parent,
								final String title, 
								final String text,
								final Object[] options,
								final Object option,
								final String textOfCheckBox) {
		
		this.dialog = new JDialog(parent, title);
		this.dialog.setModal(true);
		this.dialog.setLocationRelativeTo(parent);
		this.dialog.addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent evt) {
				DialogWithCheckBoxAndTwoButtons.this.dialog.setVisible(false);
			}
		});
		
		this.contentText = new JLabel("   "+text+"   ");
		
		this.button = new JButton("Option");
		this.button.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogWithCheckBoxAndTwoButtons.this.answer = 0;
				DialogWithCheckBoxAndTwoButtons.this.dialog.setVisible(false);
			}
		});
		
		this.button2 = new JButton("Option2");
		this.button2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				DialogWithCheckBoxAndTwoButtons.this.answer = 1;
				DialogWithCheckBoxAndTwoButtons.this.dialog.setVisible(false);
			}
		});
		
		if (options.length == 2) {			
			if (options[0] instanceof String)
				this.button.setText((String) options[0]);
			if (options[1] instanceof String)
				this.button2.setText((String) options[1]);
		}		
		
		if (option != null && (option instanceof String)) {
			if (((String)option).equals(this.button.getText()))
				this.answer = 0;
			else if (((String)option).equals(this.button2.getText()))
				this.answer = 1;
		}
		
		this.checkBox = new JCheckBox(textOfCheckBox, false);		
		this.checkBox.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (((JCheckBox) e.getSource()).isSelected()) {
					((JCheckBox) e.getSource()).setForeground(Color.RED);
				} else {
					((JCheckBox) e.getSource()).setForeground(Color.BLACK);
				}
			}
		});
		
		final JPanel content = new JPanel(new BorderLayout());
		initContent(content);
		
		this.dialog.getContentPane().add(content);
		this.dialog.validate();
		
		this.dialog.setSize(300, 300);
		this.dialog.pack();
	}
	
	private void initContent(final JPanel content) {
		final JPanel p = new JPanel(new GridLayout(4,1));
		p.add(new JLabel("          "));
		p.add(new JLabel("          "));
		p.add(this.contentText);
		p.add(new JLabel("          "));
		content.add(p, BorderLayout.NORTH);		
		
		final JPanel p1 = new JPanel();
		p1.add(this.button);
		p1.add(new JLabel("          "));
		p1.add(this.button2);
		content.add(p1, BorderLayout.CENTER);
		
		final JPanel p2 = new JPanel(new GridLayout(3,1));
		p2.add(new JLabel("          "));
		p2.add(this.checkBox);
		p2.add(new JLabel("          "));
		content.add(p2, BorderLayout.SOUTH);
	}
	
	public boolean isVisible() {
		return this.dialog.isVisible();
	}
	
	public void setVisible(boolean b) {
		this.dialog.setVisible(b);
	}
	
	public int getAnswer() {
		return this.answer;
	}
	
	public boolean isCheckSelected() {
		return this.checkBox.isSelected();
	}
}
