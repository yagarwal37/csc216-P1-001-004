package edu.ncsu.csc216.issue_manager.model.command;

/**
 * Command object that encapsulates transitions between states of an Issue object.
 * Utilized by the State and Issue Class
 * @author yash
 *
 */
public class Command {
	
	/** Constant String for the Fixed Resolution*/
	public static final String R_FIXED = "Fixed";
	/** Constant String for the Duplicate Resolution*/
	public static final String R_DUPLICATE = "Duplicate";
	/** Constant String for the WontFix Resolution*/
	public static final String R_WONTFIX = "WontFix";
	/** Constant String for the WorksForMe Resolution*/
	public static final String R_WORKSFORME = "WorksForMe";
	/** CommandValue associated with Command*/
	private CommandValue c;
	/** String ID of the owner*/
	private String ownerId;
	/** Resolution associated with Command*/
	private Resolution resolution;
	/** Note attached to the Command*/
	private String note;
	
	/** 
	 * Constructor for the Command object
	 * @param c CommandValue of the Command to change state
	 * @param ownerId ID of the owner when Resolution is ASSIGN
	 * @param r Resolution of the Command 
	 * @param note String note tied to the command
	 */
	public Command(CommandValue c, String ownerId, Resolution r, String note) {
		if(c == null || c == CommandValue.ASSIGN &&
				(ownerId == null || "".equals(ownerId)) 
				|| c == CommandValue.RESOLVE && r == null || 
				"".equals(note) || note == null){
			throw new IllegalArgumentException("Invalid information.");
		}
		
		this.c = c;
		this.ownerId = ownerId;
		this.resolution = r;
		this.note = note;
	}
	
	/**
	 * Returns CommandValue c
	 * 
	 * @return c CommandValue of Command
	 */
	public CommandValue getCommand() {
		return c;
	}
	
	/**
	 * Returns ownerId
	 * 
	 * @return ownerId String ownerId of Command
	 */
	public String getOwnerId() {
		return ownerId;
	}
	
	/**
	 * Returns Resolution resolution
	 * 
	 * @return resolution Resolution of Command
	 */
	public Resolution getResolution() {
		return resolution;
	}
	
	/**
	 * Returns note
	 * 
	 * @return note String note of Command
	 */
	public String getNote() {
		return note;
	}
	
	/**
	 * 5 IssueStates that can be applied to an Issue object 
	 * 
	 * @author yash 
	 */
	public enum CommandValue { ASSIGN, CONFIRM, RESOLVE, VERIFY, REOPEN }

	/**
	 * 4 possible resolutions for an Issue object. Deter
	 * 
	 * @author yash
	 */
	public enum Resolution { FIXED, DUPLICATE, WONTFIX, WORKSFORME }
}
