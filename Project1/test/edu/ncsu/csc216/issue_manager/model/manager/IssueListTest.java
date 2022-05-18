package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Test the IssueList class
 * 
 * @author yash
 *
 */
class IssueListTest {

	/**
	 * Test the IssueList constructor
	 */
	@Test
	void testIssueList() {
		IssueList list = new IssueList();
		assertEquals(1, list.addIssue(IssueType.ENHANCEMENT, "Summary", "Note"));

	}

	/**
	 * Test the getIssueById() method in a valid way
	 */
	@Test
	void testGetIssueByIdValid() {
		IssueList list = new IssueList();
		list.addIssue(IssueType.ENHANCEMENT, "Summary", "Note");

		assertEquals(1, list.getIssueById(1).getIssueId());
	}

	/**
	 * Test the getIssueById() method in an invalid way
	 */
	@Test
	void testGetIssueByIdInvalid() {
		IssueList list = new IssueList();
		list.addIssue(IssueType.ENHANCEMENT, "Summary", "Note");

		assertNull(list.getIssueById(2));
	}

	/**
	 * Test the deleteIssueByID() method
	 */
	@Test
	void testDeleteIssueByID() {
		IssueList list = new IssueList();
		list.addIssue(IssueType.ENHANCEMENT, "Summary1", "Note1");
		list.addIssue(IssueType.BUG, "Summary2", "Note2");

		list.deleteIssueById(2);

		assertEquals(1, list.getIssues().size());
	}

	/**
	 * Test the getIssuesByType() method
	 */
	@Test
	void testGetIssuesByType() {
		IssueList list = new IssueList();
		list.addIssue(IssueType.BUG, "Summary1", "Note1");
		list.addIssue(IssueType.ENHANCEMENT, "Summary2", "Note2");

		Issue issue = new Issue(1, IssueType.BUG, "Summary1", "Note1");
		assertEquals(issue.toString(), list.getIssuesByType("Bug").get(0).toString());
	}

	/**
	 * Test the getAddIssue() method
	 */
	@Test
	void testAddIssues() {
		ArrayList<Issue> list1 = new ArrayList<Issue>();
		Issue issue = new Issue(1, IssueType.BUG, "Summary1", "Note1");
		list1.add(issue);

		IssueList list2 = new IssueList();
		list2.addIssues(list1);

		assertEquals(list1.get(0), list2.getIssueById(1));

	}
	
	/**
	 * Test the getExecuteCommand() method
	 */
	@Test
	void testExecuteCommand() {
		IssueList list = new IssueList();
		list.addIssue(IssueType.ENHANCEMENT, "Summary1", "Note1");
		
		Command c = new Command(CommandValue.ASSIGN, "Yash", Resolution.FIXED, "Command note");

		list.executeCommand(1, c);
		assertEquals("Working", list.getIssueById(1).getStateName());
	}
}
