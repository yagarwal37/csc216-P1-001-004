package edu.ncsu.csc216.issue_manager.model.manager;

import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.io.IssueReader;
import edu.ncsu.csc216.issue_manager.model.io.IssueWriter;
import edu.ncsu.csc216.issue_manager.model.issue.Issue;
import edu.ncsu.csc216.issue_manager.model.issue.Issue.IssueType;

/**
 * Controls the create and modification of possible issues in IssueLists
 * @author yash
 *
 */
public class IssueManager {

	/**Singleton instance of IssueManager*/
	private static IssueManager singleton;
	/**Instance of the issueList*/
	private static IssueList issueList;
	
	/**
	 * Constructor for the IssueManager object
	 */
	private IssueManager() {
		issueList = new IssueList();
	}
	
	/**
	 * Returns the current instance of IssueManger
	 * @return singleton IssueManager is returned
	 */
	public static IssueManager getInstance() {
		if(singleton == null) {
			singleton = new IssueManager();
		}
		return singleton;
	}
	
	/**
	 * Saves a list of issues to a file
	 * @param fileName name of file that is being saved
	 * @throws IllegalArgumentException if the file cannot be saved
	 */
	public void saveIssuesToFile(String fileName) {
		try {
			IssueWriter.writeIssuesToFile(fileName, issueList.getIssues());
		} catch(Exception e) {
			throw new IllegalArgumentException("File cannot be saved.");
		}
	}
	
	/**
	 * Loads a list of issues from a file
	 * @param fileName name of the file that is being loaded
	 * @throws IllegalArgumentException thrown if file is not able to be found
	 */
	public void loadIssuesFromFile(String fileName) {
		createNewIssueList();
		
		try {
			issueList.addIssues(IssueReader.readIssuesFromFile(fileName));
		} catch(Exception e) {
			throw new IllegalArgumentException("File not able to load issues.");
		}
	}
	
	/**
	 * Creates a new list of issues 
	 */
	public void createNewIssueList() {
		issueList = new IssueList();
	}
	
	/**
	 * Gets the list of Issues as a 2D array of Objects
	 * @return list 2D Object Array of Issues
	 */
	public Object[][] getIssueListAsArray(){
		Object [][] temp = new Object[issueList.getIssues().size()][4];
		
		for(int i = 0; i < issueList.getIssues().size(); i++) {
			temp[i][0] = issueList.getIssues().get(i).getIssueId();
			temp[i][1] = issueList.getIssues().get(i).getStateName();
			temp[i][2] = issueList.getIssues().get(i).getIssueType();
			temp[i][3] = issueList.getIssues().get(i).getSummary();

		}
		
		return temp;
	}
	
	/**
	 * Gets the list of Issues as a 2D array of Objects based on the type of issue
	 * @param type String name of the type of issue
	 * @return list 2D Object Array of Issues
	 */
	public Object[][] getIssueListAsArrayByIssueType(String type){
		ArrayList<Issue> tempList = issueList.getIssuesByType(type);
		Object [][] tempArray = new Object [tempList.size()][4];
		
		for(int i = 0; i < tempList.size(); i++) {
			tempArray[i][0] = tempList.get(i).getIssueId();
			tempArray[i][1] = tempList.get(i).getStateName(); 
			tempArray[i][2] = tempList.get(i).getIssueType();
			tempArray[i][3] = tempList.get(i).getSummary();

		}
		
		return tempArray;
	
	}
	
	/**
	 * Gets the Issue based on its ID
	 * @param id int id of the issue
	 * @return issue Issue returned based off id
	 */
	public Issue getIssueById(int id) {
		return issueList.getIssueById(id);
	}
	
	/**
	 * Executes the command to transition states
	 * @param id int id of the Issue
	 * @param command Command to transition between IssueStates
	 */
	public void executeCommand (int id, Command command) {
		issueList.executeCommand(id, command);
	}
	
	/**
	 * Deletes an Issue based of its ID
	 * @param id int id of the Issue
	 */
	public void deleteIssueById(int id) {
		issueList.deleteIssueById(id);
	}
	
	/**
	 * Adds the Issue to a list
	 * @param issueType type of Issue
	 * @param summary Summary of the issue
	 * @param note notes attached to the issue
	 */
	public void addIssueToList(IssueType issueType, String summary, String note) {
		issueList.addIssue(issueType, summary, note);
	}
}
