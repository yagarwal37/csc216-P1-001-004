/**
 * 
 */
package edu.ncsu.csc216.issue_manager.model.command;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Tester file for the Command class
 * @author yash
 *
 */
class CommandTest {

	/**Declared Command object to be initialized in each test*/
	private Command command;
	/**
	 * Test construction of a Command object
	 */
	@Test
	void testCommand() {
		command = assertDoesNotThrow(() -> new Command(CommandValue.ASSIGN, "Yash", Resolution.DUPLICATE, "Test note"),
				"Should not throw an exception");
		assertAll("Command",
			() -> assertEquals(CommandValue.ASSIGN, command.getCommand(), "Incorrect command value"),
			() -> assertEquals("Yash", command.getOwnerId(), "Incorrect owner ID"),
			() -> assertEquals(Resolution.DUPLICATE, command.getResolution(), "Incorrect resolution"),
			() -> assertEquals("Test note", command.getNote(), "Incorrect note"));	
	}

	/**
	 * Tests the constructor for a null CommandValue to see if exception is thrown
	 * 
	 */
	@Test
	void testNullCommandValue() {	
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new Command(null, "Yash", Resolution.DUPLICATE, "Test note"));
		assertEquals("Invalid information.", e.getMessage());
	}
	
	/**
	 * Test the constructor for a null owner with ASSIGN to see if exception is thrown
	 */
	@Test
	void testAssignWithNullOwner() {
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new Command(CommandValue.ASSIGN, null, Resolution.DUPLICATE, "Test note"));
		assertEquals("Invalid information.", e.getMessage());
	}
	
	/**
	 * Test the constructor for a null resolution with RESOLVE to see if exception is thrown
	 */
	@Test
	void testResolveWithNullResolution() {
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new Command(CommandValue.RESOLVE, "Yash", null, "Test note"));
		assertEquals("Invalid information.", e.getMessage());
	}
	
	/**
	 * Test the constructor for a null note to see if exception is thrown
	 */
	@Test
	void testNullNote() {
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new Command(CommandValue.ASSIGN, "Yash", Resolution.DUPLICATE, null));
		assertEquals("Invalid information.", e.getMessage());
	}
	
	/**
	 * Test the constructor for a empty note to see if exception is thrown
	 */
	@Test
	void testEmptyNote() {
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> new Command(CommandValue.ASSIGN, "Yash", Resolution.DUPLICATE, ""));
		assertEquals("Invalid information.", e.getMessage());
	}

}
