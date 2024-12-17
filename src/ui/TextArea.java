package ui;

import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.undo.UndoManager;

class TextArea extends JScrollPane {
	private static final long serialVersionUID = 1L;
	
	private JTextArea textArea;
	private UndoManager undoManager;

	TextArea() {
		this.textArea = new JTextArea();
		this.textArea.setLineWrap(true);
		this.textArea.setWrapStyleWord(true);
		this.setViewportView(this.textArea);

		this.undoManager = new UndoManager();
		this.textArea.getDocument().addUndoableEditListener(this.undoManager);
	}

	void undo() {
		if(this.undoManager.canUndo())
			this.undoManager.undo();
	}

	void redo() {
		if(this.undoManager.canRedo())
			this.undoManager.redo();
	}

	void clear() {
		this.textArea.setText("");
	}

	void append(String line) {
		this.textArea.append(line);
	}
	
	String getText() {
		return this.textArea.getText();
	}
}
