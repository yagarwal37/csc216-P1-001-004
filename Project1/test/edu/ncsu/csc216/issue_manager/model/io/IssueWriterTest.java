package edu.ncsu.csc216.issue_manager.model.io;


import static org.junit.Assert.fail;

import java.util.ArrayList;

import org.junit.jupiter.api.Test;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Test the IssueWriterTest class
 * @author yash
 *
 */
class IssueWriterTest {

	
	/**
	 * Test writeIssuesToFile() method
	 */
	@Test
	void testWriteIssuesToFile() {
		ArrayList<Issue> issueList = new ArrayList<Issue>();
		ArrayList<String> notes = new ArrayList<String>();
		notes.add("Test Note");
		issueList.add(new Issue(1, "New", "Bug", "Summary", "", false, null, notes));
		
		try {
			IssueWriter.writeIssuesToFile("test-files/issue_sample.txt", issueList);
		} catch(Exception e) {
			fail("Cannot write issues to file");
		}
		
	}

	

}
