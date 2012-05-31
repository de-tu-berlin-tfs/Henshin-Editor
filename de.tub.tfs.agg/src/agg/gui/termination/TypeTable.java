package agg.gui.termination;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.Color;
import java.util.Vector;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

public class TypeTable extends JPanel {

	private int hght;

	public TypeTable(Vector<String> types, String title) {
		super(new BorderLayout());
		setBackground(Color.orange);
		setBorder(new TitledBorder(title));

		JPanel typePanel = new JPanel(new BorderLayout());
		JTable typeTable = new JTable(types.size(), 1);
		typeTable.setEnabled(false);
		for (int i = 0; i < types.size(); i++) {
			typeTable.setValueAt(types.elementAt(i), i, 0);
		}

		this.hght = getHeight(typeTable.getRowCount(), typeTable.getRowHeight());
		typeTable.doLayout();
		JScrollPane typeScrollPane = new JScrollPane(typeTable);
		typeScrollPane.setPreferredSize(new Dimension(200, this.hght));
		typePanel.add(typeScrollPane, BorderLayout.CENTER);
		add(typePanel, BorderLayout.CENTER);
		validate();
	}

	public Dimension getPreferredSize() {
		return new Dimension(200, this.hght);
	}

	public int getTableHeight() {
		return this.hght;
	}

	private int getHeight(int rowCount, int rowHeight) {
		int n = 10;
		int h = (rowCount + 3) * rowHeight;
		if (rowCount > n)
			h = (n + 2) * rowHeight;
		// else if(rowCount < 5)
		// h = 7*rowHeight;
		else if (rowCount == 0)
			h = 2 * rowHeight;
		return h;
	}
}
