package edu.ncsu.csc216.issue_manager.model.issue;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Test the methods in the Issue clas
 * @author yash
 *
 */
class IssueTest {

	/**Declared Command object to be initialized in each test*/
	private Issue i;
	/**Final ID for parameters for construction*/
	private static final int ID = 1;
	/**Final State for parameters for construction*/
	private static final String STATE = "New";
	/**Final Issue Type for parameters for construction*/
	private static final String IT_ENHANCEMENT = "Enhancement";
	/**Final Summary for parameters for construction*/
	private static final String SUMMARY = "Test summary.";
	/**Final Owner for parameters for construction*/
	private static final String OWNER = "Yash";
	/**ArrayList of String for the note field in Issue*/
	private ArrayList<String> notes = new ArrayList<String>();
	
	
	
	/**
	 * Test the smaller constructor in the Issue class
	 */
	@Test
	void testSmallIssueConstructor() { 
		
		Issue j = new Issue(ID, IssueType.BUG, SUMMARY, "Test note");
		System.out.println(j.getIssueId());
		System.out.println(j.getIssueType());
		System.out.println(j.getSummary());
		System.out.println(j.getNotes().get(0));

		i = assertDoesNotThrow(() -> new Issue(ID, IssueType.BUG, SUMMARY, "Test note"),
				"Should not throw an exception"); 
		
		assertAll("Issue",
				() -> assertEquals(ID, i.getIssueId(), "Incorrect ID"),
				() -> assertEquals("Bug", i.getIssueType(), "Incorrect Issue Type"),
				() -> assertEquals(SUMMARY, i.getSummary(), "Incorrect summary"),	
				() -> assertEquals("[New] Test note", i.getNotes().get(0), "Incorrect notes"));		
	}
	
	
	
	/**
	 * Test the larger constructor in the Issue class
	 */
	@Test
	void testLargeIssueConstructor() {
		i = assertDoesNotThrow(() -> new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, OWNER, false, Command.R_DUPLICATE, notes),
				"Should not throw an exception");
		assertAll("Issue",
				() -> assertEquals(ID, i.getIssueId()),
				() -> assertEquals(STATE, i.getStateName()),
				() -> assertEquals(IT_ENHANCEMENT, i.getIssueType()),
				() -> assertEquals(SUMMARY, i.getSummary()),
				() -> assertEquals(OWNER, i.getOwner()),
				() -> assertFalse(i.isConfirmed()),
				() -> assertEquals(Command.R_DUPLICATE, i.getResolution()),
				() -> assertEquals(notes, i.getNotes()));
		
		System.out.println(i.getNotesString());
	}
	
	/**
	 * Tests an issue by changing its state from New to Working
	 */
	@Test
	void testNewStateToWorkingState() {
		i = new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.ASSIGN, OWNER, null, "Command note");
		i.update(c);
		assertEquals(Issue.WORKING_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its state from New to Working
	 */
	@Test
	void testNewStateToWorkingStateFail() {
		i = new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		
		Exception e = assertThrows(IllegalArgumentException.class, 
				() -> i.update(new Command(CommandValue.ASSIGN, "", null, "Command note")));
		assertEquals("Invalid information.", e.getMessage());
	}
	
	/**
	 * Tests an issue by changing its state from New to Confirm
	 */
	@Test
	void testNewStateToConfirmState() {
		i = new Issue(ID, STATE, "Bug", SUMMARY, "", false, Command.R_FIXED, notes);
		Command c = new Command(CommandValue.CONFIRM, OWNER, null, "Command note");
		i.update(c);
		assertEquals(Issue.CONFIRMED_NAME, i.getStateName());
	}
	

	/**
	 * Tests an issue by changing its resolution to Fixed and state is Closed
	 */
	@Test
	void testNewStateByMakingResolutionFixed() {
		i = new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, "", false, "", notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.FIXED, "Command note");
		assertThrows(UnsupportedOperationException.class, () -> i.update(c));

	}
	
	/**
	 * Tests an issue by changing its resolution to Duplicate and state is Closed
	 */
	@Test
	void testNewStateByMakingResolutionDuplicate() {
		i = new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, "", false, "", notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.DUPLICATE, "Command note");
		i.update(c);
		assertEquals(Issue.CLOSED_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its resolution to WontFix and state is Closed
	 */
	@Test
	void testNewStateByMakingResolutionWontFix() {
		i = new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, "", false, "", notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.WONTFIX, "Command note");
		i.update(c);
		assertEquals(Issue.CLOSED_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its resolution to WorksForMe and state is Closed
	 */
	@Test
	void testNewStateByMakingResolutionWorksForMe() {
		i = new Issue(ID, STATE, IT_ENHANCEMENT, SUMMARY, "", false, "", notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.WORKSFORME, "Command note");
				assertThrows(UnsupportedOperationException.class, () -> i.update(c));

	}
	
	/**
	 * Tests an issue by changing its state from Working to Verifying
	 */
	@Test
	void testWorkingStateToVerifyingStateAndResolutionFixed() {
		i = new Issue(ID, "Working", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.FIXED, "Command note");
		i.update(c);
		assertEquals(Issue.VERIFYING_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its state from Working to Closed
	 */
	@Test
	void testWorkingStateToClosedState() {
		i = new Issue(ID, "Working", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.DUPLICATE, "Command note");
		i.update(c);
		assertEquals(Issue.CLOSED_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by throwing an exception as an Enhancement
	 */
	@Test
	void testWorkingStateEnhancementInvalid() {
		i = new Issue(ID, "Working", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.WORKSFORME, "Command note");
		assertThrows(UnsupportedOperationException.class, () -> i.update(c));
	}
	
	/*
	 * Tests an issue by throwing an exception as a Bug
	 */
	/*@Test
	void testWorkingStateBugInvalid() {
		i = new Issue(ID, "Working", "Bug", SUMMARY, "", false, Command.R_FIXED, notes);
		Command c = new Command(CommandValue.RESOLVE, "", Resolution.FIXED, "Command note");
		assertThrows(UnsupportedOperationException.class, () -> i.update(c));
	}*/
	
	/**
	 * Tests an issue by changing its state from Confirmed to Working
	 */
	@Test
	void testConfimredStateToWorkingState() {
		i = new Issue(ID, "Confirmed", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.ASSIGN, OWNER, Resolution.FIXED, "Command note");
		i.update(c);
		assertEquals(Issue.WORKING_NAME, i.getStateName());
	}
	
	/*
	 * Tests an issue by throwing an exception in the Confirmed state as an invalid owner and command
	 */
	/*@Test
	void testConfimredStateInvalidCommandAssign() {
		i = new Issue(ID, "Confirmed", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.ASSIGN, OWNER, Resolution.FIXED, "Command note");
		assertThrows(UnsupportedOperationException.class, () -> i.update(c));
	}*/
	
	/**
	 * Tests an issue by throwing an exception in the Confirmed state as an invalid owner and command
	 */
	@Test
	void testConfimredStateInvalidResolution() {
		i = new Issue(ID, "Confirmed", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.RESOLVE, OWNER, Resolution.WONTFIX, "Command note");
		i.update(c);
		assertEquals(Issue.CLOSED_NAME, i.getStateName());
	}
	/**
	 * Tests an issue by changing its state from Verifying to Closed
	 */
	@Test
	void testVerifyingStateToClosedState() {
		i = new Issue(ID, "Verifying", IT_ENHANCEMENT, SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.VERIFY, "", Resolution.FIXED, "Command note");
		i.update(c);
		assertEquals(Issue.CLOSED_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its state from Closed to Working
	 */
	@Test
	void testClosedStateToWorkingStateEnhancemnet() {
		i = new Issue(ID, "Closed", IT_ENHANCEMENT, SUMMARY, OWNER, false, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.REOPEN, OWNER, Resolution.FIXED, "Command note");
		i.update(c);
		assertEquals(Issue.WORKING_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its state from Closed to Verifying
	 */
	@Test
	void testClosedStateToWorkingStateBug() {
		i = new Issue(ID, "Closed", "Bug", SUMMARY, OWNER, true, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.REOPEN, OWNER, Resolution.FIXED, "Command note");
		i.update(c);
		assertEquals(Issue.WORKING_NAME, i.getStateName());
	}
	
	/**
	 * Tests an issue by changing its state from Closed to Confirmed
	 */
	@Test
	void testClosedStateToConfirmedState() {
		i = new Issue(ID, "Closed", "Bug", SUMMARY, "", true, Command.R_WONTFIX, notes);
		Command c = new Command(CommandValue.REOPEN, "", Resolution.FIXED, "Command note");
		i.update(c);
		assertEquals(Issue.CONFIRMED_NAME, i.getStateName());
	}
	
	@Test
	void testSample() {
		i = new Issue(ID, "New", "Bug", SUMMARY, "", false, Command.R_WONTFIX, notes);
		Command c1 = new Command(CommandValue.RESOLVE, "", Resolution.WORKSFORME, "Note One");
		i.update(c1);
		
		assertEquals(Issue.CLOSED_NAME, i.getStateName());
	}

}
