package ui;

import java.awt.GridLayout;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JSpinner;
import javax.swing.SpinnerNumberModel;

class SpinnerRow extends JPanel {
	private static final long serialVersionUID = 1L;
	
	private JLabel label;
	private JSpinner spinner;
	private SpinnerNumberModel model;

	SpinnerRow(String label, int minimum, int maximum, int stepSize, int defaultValue) {
		GridLayout layout = new GridLayout(1, 2);
		this.setLayout(layout);

		this.label = new JLabel(label);
		this.add(this.label);

		this.model = new SpinnerNumberModel();
		this.model.setMinimum(minimum);
		this.model.setMaximum(maximum);
		this.model.setStepSize(stepSize);
		this.model.setValue(defaultValue);

		this.spinner = new JSpinner(this.model);
		this.add(this.spinner);
	}
	
	SpinnerNumberModel getModel() {
		return model;
	}
	
	JSpinner getSpinner() {
		return spinner;
	}
}
