package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextAreaTest {

	private static final String TEST_TEXT = "Some text";
	private static final String EMPTY_TEXT = "";

	@Test
	void testUndo() {
		TextArea textArea = new TextArea();
		textArea.append(TEST_TEXT);
		textArea.undo();

		assertEquals(textArea.getText(), EMPTY_TEXT);
	}

	@Test
	void testUndoNotPossible() {
		TextArea textArea = new TextArea();
		textArea.undo();

		assertEquals(textArea.getText(), EMPTY_TEXT);
	}

	@Test
	void testRedo() {
		TextArea textArea = new TextArea();
		textArea.append(TEST_TEXT);
		textArea.undo();
		textArea.redo();

		assertEquals(textArea.getText(), TEST_TEXT);
	}

	@Test
	void testRedoNotPossible() {
		TextArea textArea = new TextArea();
		textArea.redo();

		assertEquals(textArea.getText(), EMPTY_TEXT);
	}

	@Test
	void testClear() {
		TextArea textArea = new TextArea();
		textArea.append(TEST_TEXT);
		textArea.clear();

		assertEquals(textArea.getText(), EMPTY_TEXT);
	}

	@Test
	void testAppendAndGetText() {
		TextArea textArea = new TextArea();
		textArea.append(TEST_TEXT);

		assertEquals(textArea.getText(), TEST_TEXT);
	}

	@Test
	void testAppendAndGetTextEmpty() {
		TextArea textArea = new TextArea();
		textArea.append(EMPTY_TEXT);

		assertEquals(textArea.getText(), EMPTY_TEXT);
	}

	@Test
	void testGetTextEmpty() {
		TextArea textArea = new TextArea();

		assertEquals(textArea.getText(), EMPTY_TEXT);
	}
}
