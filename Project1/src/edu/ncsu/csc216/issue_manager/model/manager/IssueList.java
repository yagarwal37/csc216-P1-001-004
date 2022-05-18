package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Maintains an ArrayList of issues
 * 
 * @author yash
 *
 */
public class IssueList {

	/** ArrayList of issues */
	private ArrayList<Issue> issues;
	/** ID of the next issue added to the list */
	int counter;

	/**
	 * Constructor for the IssueList
	 */
	public IssueList() {
		issues = new ArrayList<Issue>();
		counter = 1;
	}

	/**
	 * Adds an Issue to the list
	 * 
	 * @param issueType enhancement or bug Issue
	 * @param summary   Summary of the issue
	 * @param note      notes about the issue
	 * @return counter the ID of the next instance
	 */
	public int addIssue(IssueType issueType, String summary, String note) {
		Issue current = new Issue(counter, issueType, summary, note);
		addIssue(current);
		return current.getIssueId();
	}

	/**
	 * Adds a collection of issues to the list
	 * 
	 * @param issueList List of the issues which will be added to
	 */
	public void addIssues(ArrayList<Issue> issueList) {
		issues = new ArrayList<Issue>();
		for (int i = 0; i < issueList.size(); i++) {
			addIssue(issueList.get(i));
		}
	}

	/**
	 * Adds a single issue to the List and checks for duplicates in issues
	 * 
	 * @param issue Issue object that is to be added
	 */
	private void addIssue(Issue issue) {
		boolean duplicate = false;
		for (int i = 0; i < issues.size(); i++) {
			Issue current = issues.get(i);
			if (current.getIssueId() == issue.getIssueId()) {
				duplicate = true;
				break;
			}
		}

		if (!duplicate) {
			if (issues.size() == 0) {
				issues.add(issue);
				counter++;
			} else {
				for (int i = 0; i < issues.size(); i++) {
					if (issue.getIssueId() < issues.get(i).getIssueId()) {
						issues.add(i, issue);
						counter++;
						break;
					} else if (i == issues.size() - 1) {
						issues.add(issue);
						counter++;
						break;
					}

				}

			}
		}

	}

	/**
	 * Retrieves the list of issues
	 * 
	 * @return the List of issues
	 */
	public ArrayList<Issue> getIssues() {
		return issues;
	}

	/**
	 * Gets the issues in the list based on a certain type
	 * 
	 * @param type Enhancement or bug type
	 * @return list list of issues of a certain type
	 * @throws IllegalArgumentException if type is null
	 */
	public ArrayList<Issue> getIssuesByType(String type) {
		ArrayList<Issue> temp = new ArrayList<Issue>();
		if (type == null) {
			throw new IllegalArgumentException();
		}
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).getIssueType().equals(type)) {
				temp.add(issues.get(i));
			}
		}
		return temp;
	}

	/**
	 * Gets the issues in the list based on a id
	 * 
	 * @param id int id of the issue
	 * @return issue Issue that is identified by id
	 */
	public Issue getIssueById(int id) {
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).getIssueId() == id) {
				return issues.get(i);
			}
		}
		return null;
	}

	/**
	 * Executes a command
	 * 
	 * @param id      id of the Issue in the list
	 * @param command Command that determines state
	 */
	public void executeCommand(int id, Command command) {
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).getIssueId() == id) {
				issues.get(i).update(command);
			}
		}
	}

	/**
	 * Deletes an issue based off its id
	 * 
	 * @param id id of the issue being deleted
	 */
	public void deleteIssueById(int id) {
		for (int i = 0; i < issues.size(); i++) {
			if (issues.get(i).getIssueId() == id) {
				issues.remove(i);
			}
		}
	}

}
