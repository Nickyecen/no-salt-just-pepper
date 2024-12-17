package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class TextRowTest {

	private static final String TEST_TEXT = "Some text";
	private static final String EMPTY_TEXT = "";

	@Test
	void testGetInfo() {
		TextRow textRow = new TextRow(TEST_TEXT, TEST_TEXT);

		assertEquals(textRow.getInfo(), TEST_TEXT);
	}

	@Test
	void testGetInfoEmpty() {
		TextRow textRow = new TextRow(TEST_TEXT, EMPTY_TEXT);

		assertEquals(textRow.getInfo(), EMPTY_TEXT);
	}

	@Test
	void testSetInfo() {
		TextRow textRow = new TextRow(TEST_TEXT, EMPTY_TEXT);
		textRow.setInfo(TEST_TEXT);

		assertEquals(textRow.getInfo(), TEST_TEXT);
	}

	@Test
	void testSetInfoEmpty() {
		TextRow textRow = new TextRow(TEST_TEXT, TEST_TEXT);
		textRow.setInfo(EMPTY_TEXT);

		assertEquals(textRow.getInfo(), EMPTY_TEXT);
	}
}
