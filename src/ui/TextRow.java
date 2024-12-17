package ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;

class TextRow extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel label;
	private JLabel info;

	TextRow(String label, String info) {
		GridLayout layout = new GridLayout(1, 2);
		this.setLayout(layout);

		this.label = new JLabel(label);
		this.add(this.label);

		this.info = new JLabel(info);
		this.add(this.info);
	}
	
	String getInfo() {
		return this.info.getText();
	}

	void setInfo(String info) {
		this.info.setText(info);
	}
}
