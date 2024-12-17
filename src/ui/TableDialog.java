package ui;

import javax.swing.JDialog;
import javax.swing.JScrollPane;
import javax.swing.JTable;

public class TableDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	JTable table;

	TableDialog(String title, int width, int height, Object[][] data, String[] columnNames) {

		this.setTitle(title);

		this.table = new JTable(data, columnNames);
		this.table.setEnabled(false);
		this.table.setFillsViewportHeight(true);
		this.table.setFocusable(false);
		this.table.setColumnSelectionAllowed(false);
		this.table.setRowSelectionAllowed(false);

		JScrollPane scrollPane = new JScrollPane(table);
		scrollPane.setFocusable(false);

		this.setSize(width, height);
		this.add(scrollPane);
	}

	void showDialog() {
		this.setVisible(true);
	}
}
