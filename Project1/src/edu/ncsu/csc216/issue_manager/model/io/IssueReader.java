package edu.ncsu.csc216.issue_manager.model.io;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.NoSuchElementException;
import java.util.Scanner;

import edu.ncsu.csc216.issue_manager.model.issue.Issue;

/**
 * Reads a file to see the Issue objects to store them into an ArrayList of
 * Issues
 * 
 * @author yash
 *
 */
public class IssueReader {
	/**
	 * Reads Issues from a file and stores them in an ArrayList of Issues
	 * 
	 * @param fileName String name of the file that is being read
	 * @return issues ArrayList of the Issues read from the file
	 * @throws IllegalArgumentException If the file cannot be found or there is an
	 *                                  error during processing
	 */
	public static ArrayList<Issue> readIssuesFromFile(String fileName) {
		Scanner fileReader;
		try {
			fileReader = new Scanner(new FileInputStream(fileName));
		} catch (FileNotFoundException e) {
			throw new IllegalArgumentException("Unable to load file.");

		}
		ArrayList<Issue> issues = new ArrayList<Issue>();
		String fileString = "";
		while (fileReader.hasNextLine()) {
			fileString = fileString + fileReader.nextLine() + "\n";
		}
		Scanner fileStringReader = new Scanner(fileString);
		fileStringReader.useDelimiter("\\r?\\n?[*]");
			try{
				while (fileStringReader.hasNext()) {
				Issue issue = processIssue(fileStringReader.next());
				issues.add(issue);
				}
			} catch(IllegalArgumentException e) {
				fileReader.close();
				fileStringReader.close();
				throw new IllegalArgumentException();
			}
				

		fileReader.close();
		fileStringReader.close();
			return issues;
		
	}

	/**
	 * Process passed in issue to check for any problems or throw and exception if
	 * needed
	 * 
	 * @param issueString String name of the issue passed in
	 * @return issue the Issue if there are no Issues with the object that is passed in as a string
	 * @throws IllegalArgumentException if there is an problem with the Issue parameters
	 */
	private static Issue processIssue(String issueString) {
		Scanner scnr = new Scanner(issueString);
		scnr.useDelimiter(",");
		Issue issue = null;
		try {
			int id = scnr.nextInt();
			String state = scnr.next();
			String issueType = scnr.next();
			String summary = scnr.next();
			String owner = scnr.next();
			boolean confirmed = scnr.nextBoolean();
			String temp = scnr.next();

			Scanner tempScnr = new Scanner(temp);

			String resolution = tempScnr.nextLine();
			tempScnr.useDelimiter("\\r?\\n?[-]");

			ArrayList<String> notes = new ArrayList<String>();

			while (tempScnr.hasNext()) {
				String note = tempScnr.next();
				notes.add(note.replace("\r\n", ""));
			}

			issue = new Issue(id, state, issueType, summary, owner, confirmed, resolution, notes);

			tempScnr.close();

		} catch (NoSuchElementException e) {
			scnr.close();
			throw new IllegalArgumentException();
		}
		scnr.close();
		return issue;
	}
}
