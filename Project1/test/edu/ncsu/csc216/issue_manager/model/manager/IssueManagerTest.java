package edu.ncsu.csc216.issue_manager.model.manager;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Test the IssueManager class
 * @author yash
 *
 */
class IssueManagerTest {

	/**
	 * Test the getInstance() method
	 */
	@Test
	void testIssueManager() {
		IssueManager issueManager = IssueManager.getInstance();
		issueManager.createNewIssueList();
		issueManager.addIssueToList(Issue.IssueType.ENHANCEMENT, "Summary1", "Note 1");
		issueManager.addIssueToList(Issue.IssueType.BUG, "Summary2", "Note 2");
		
		Issue i1 = new Issue(1, Issue.IssueType.ENHANCEMENT, "Summary1", "Note 1");
		Issue i2 = issueManager.getIssueById(1);
		
		assertEquals(i1.getIssueId(), i2.getIssueId());
		
		Object [][] objectArr1 = issueManager.getIssueListAsArray();
		assertEquals(2, objectArr1.length);
		
		issueManager.addIssueToList(Issue.IssueType.BUG, "Summary3", "Note 3");
		Object [][] objectArr2 = issueManager.getIssueListAsArrayByIssueType("Bug");
		assertEquals(2, objectArr2.length);
		
		issueManager.deleteIssueById(1);		
		issueManager.saveIssuesToFile("test-files/ManagerSaveFile.txt");
		issueManager.loadIssuesFromFile("test-files/issue1.txt");
		Command c = new Command(CommandValue.ASSIGN, "Yash", Resolution.WORKSFORME, "Command note");
		issueManager.executeCommand(2, c);
				
	}
	
}
