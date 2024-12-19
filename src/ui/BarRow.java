package ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.SwingConstants;

class BarRow extends JPanel {
	private static final long serialVersionUID = 1L;

	private JLabel label;
	private JProgressBar bar;

	BarRow(String label, int minimum, int maximum, int defaultValue) {
		GridLayout layout = new GridLayout(1, 2);
		this.setLayout(layout);

		this.label = new JLabel(label);
		this.add(this.label);

		this.bar = new JProgressBar(SwingConstants.HORIZONTAL, minimum, maximum);
		this.bar.setValue(defaultValue);
		this.bar.setStringPainted(true);
		this.add(this.bar);
	}

	int getValue() {
		return this.bar.getValue();
	}

	void setValue(int value) {
		this.bar.setValue(value);
	}
}
