package ui;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class SpinnerRowTest {

	@Test
	void testGetNumber() {
		SpinnerRow spinnerRow = new SpinnerRow("", 0, 100, 1, 50);

		assertEquals(spinnerRow.getNumber(), 50);
	}
}
