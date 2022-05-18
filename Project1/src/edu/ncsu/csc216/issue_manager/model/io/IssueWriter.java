package edu.ncsu.csc216.issue_manager.model.io;

import java.io.File;
import java.io.PrintStream;
import java.util.List;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Writes a list of Issue objects with user inputs 
 * @author yash
 *
 */
public class IssueWriter {
	
	/**
	 * Creates a list of Issues with a passed in String file name
	 * 
	 * @param fileName name of the file that is going to be written
	 * @param list list of the Issues that are going to be put into a file
	 * @throws IllegalArgumentException if the file is unable to be saved due to any errors
	 */
	public static void writeIssuesToFile(String fileName, List<Issue> list) throws IllegalArgumentException {
		try {
			PrintStream fileWriter = new PrintStream(new File(fileName));

			for (int i = 0; i < list.size(); i++) {
			    fileWriter.println(list.get(i).toString());
			}

			fileWriter.close();
		} catch(Exception e) {
			throw new IllegalArgumentException("Unable to save file.");
		}
	}
}
