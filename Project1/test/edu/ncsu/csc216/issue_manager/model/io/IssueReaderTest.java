package edu.ncsu.csc216.issue_manager.model.io;

import static org.junit.jupiter.api.Assertions.*;

import java.io.FileNotFoundException;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Test methods in the IssueReader class 
 * @author yash
 *
 */
class IssueReaderTest {
	
	/**Valid test file of Issues*/
	private final String validTestFile = "test-files/issue1.txt";
	/**First valid issue in test file*/
	private final String validIssue1 = "*1,New,Enhancement,Issue description,null,false,\n-[New] Note 1";
	/**Second valid issue in test file*/
	private final String validIssue2 = "*3,Confirmed,Bug,Issue description,null,true,\n"
			+ "-[New] Note 1\n"
			+ "-[Confirmed] Note 2\n"
			+ "that goes on a new line";
	/**Third valid issue in test file*/
	private final String validIssue3 = "*7,Working,Bug,Issue description,owner,true,\n"
			+ "-[New] Note 1\n"
			+ "-[Confirmed] Note 2\n"
			+ "-[Working] Note 3";
	/**Fourth valid issue in test file*/
	private final String validIssue4 = "*14,Verifying,Enhancement,Issue description,owner,false,Fixed\n"
			+ "-[New] Note 1\n"
			+ "-[Working] Note 2\n"
			+ "that goes on a new line\n"
			+ "-[Verifying] Note 3";
	/**Fifth valid issue in test file*/
	private final String validIssue5 = "*15,Closed,Enhancement,Issue description,owner,false,WontFix\n"
			+ "-[New] Note 1\n"
			+ "that goes on a new line\n"
			+ "-[Working] Note 2\n"
			+ "-[Verifying] Note 3\n"
			+ "-[Working] Note 4\n"
			+ "-[Closed] Note 6";
	
	/**
	 * Test readIssuesFromFile() method
	 * @throws FileNotFoundException throws exception if file cannot be found
	 */
	@Test
	void testReadIssuesFromFile() throws FileNotFoundException {
		ArrayList<Issue> issueList = IssueReader.readIssuesFromFile(validTestFile);
		
		assertEquals(validIssue1, issueList.get(0).toString());
		assertEquals(validIssue2, issueList.get(1).toString());
		assertEquals(validIssue3, issueList.get(2).toString());
		assertEquals(validIssue4, issueList.get(3).toString());
		assertEquals(validIssue5, issueList.get(4).toString());

	}

}
