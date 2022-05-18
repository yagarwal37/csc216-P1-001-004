package edu.ncsu.csc216.issue_manager.model.issue;

import java.util.ArrayList;

import edu.ncsu.csc216.issue_manager.model.command.Command;
import edu.ncsu.csc216.issue_manager.model.command.Command.CommandValue;
import edu.ncsu.csc216.issue_manager.model.command.Command.Resolution;

/**
 * Issue class which has the IssueState, NewState, WorkingState, VerifyingState,
 * ConfirmedState, and ClosedState inner classes Acts as a ticketing system for
 * enhancements or bugs for developers to adjust and testers to comment on
 * 
 * @author yash
 *
 */
public class Issue {

	/** Constant String for Enhancement issue type */
	public static final String I_ENHANCEMENT = "Enhancement";
	/** Constant String for Bug issue type */
	public static final String I_BUG = "Bug";
	/** Constant String for New state name */
	public static final String NEW_NAME = "New";
	/** Constant String for Working state name */
	public static final String WORKING_NAME = "Working";
	/** Constant String for Confirmed state name */
	public static final String CONFIRMED_NAME = "Confirmed";
	/** Constant String for Verifying state name */
	public static final String VERIFYING_NAME = "Verifying";
	/** Constant String for Closed state name */
	public static final String CLOSED_NAME = "Closed";

	/** Id for the issue */
	private int issueId;
	/** Type of issue - enhancement or bug */
	private IssueType issueType;
	/** Current state of issue */
	private IssueState state;
	/** Summary of issue */
	private String summary;
	/** User ID of the owner of the issue */
	private String owner;
	/** True if issue is confirmed */
	private boolean confirmed;
	/** Type of resolution */
	private Resolution resolution;
	/** ArrayList of notes */
	private ArrayList<String> notes;

	/** Final instance of the NewState inner class */
	private IssueState newState = new NewState();
	/** Final instance of the WorkingState inner class */
	private IssueState workingState = new WorkingState();
	/** Final instance of the ConfirmedState inner class */
	private IssueState confirmedState = new ConfirmedState();
	/** Final instance of the VerifyingState inner class */
	private IssueState verifyingState = new VerifyingState();
	/** Final instance of the ClosedState inner class */
	private IssueState closedState = new ClosedState();

	/**
	 * Constructs an Issue object
	 * 
	 * @param id        int id of the issue
	 * @param issueType String type of string
	 * @param summary   String summary of issue
	 * @param note      String notes on the issue
	 * @throws IllegalArgumentException if any of the parameters are null
	 */
	public Issue(int id, IssueType issueType, String summary, String note) {
		if (issueType == null || summary == null || note == null) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		setIssueId(id);
		this.issueType = issueType;
		setSummary(summary);

		this.owner = null;
		this.state = newState;
		this.resolution = null;
		this.confirmed = false;

		notes = new ArrayList<String>();
		addNote(note);
	}

	/**
	 * Constructs an Issue object
	 * 
	 * @param id         int id of the issue
	 * @param state      String state of the issue
	 * @param issueType  String type of string
	 * @param summary    String summary of issue
	 * @param owner      String owner of the issue
	 * @param confirmed  boolean if the issue is confirmed or not
	 * @param resolution String resolution state of the issue
	 * @param notes      ArrayList of Strings notes of the issue
	 */
	public Issue(int id, String state, String issueType, String summary, String owner, boolean confirmed,
			String resolution, ArrayList<String> notes) {
		setIssueId(id);
		setIssueType(issueType);
		setSummary(summary);
		setOwner(owner);
		setConfirmed(confirmed);
		setResolution(resolution);
		setNotes(notes);

		setState(state);
	}

	/**
	 * Sets the issue ID of the Issue object
	 * 
	 * @param issueId the issueId to set
	 * @throws IllegalArgumentException if issueId is less than 1
	 */
	private void setIssueId(int issueId) {
		if (issueId < 1) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		this.issueId = issueId;
	}

	/**
	 * Sets the state of the Issue object
	 * 
	 * @param state the state to set
	 * @throws IllegalArgumentException If state is null or blank
	 * @throws IllegalArgumentException If the Verifying or Closed state do not haev a resolution
	 * @throws IllegalArgumentException If the Working, New, Confirmed, or Verifying state do not have an owner
	 * @throws IllegalArgumentException If the Confirmed state has a resolution
	 * @throws IllegalArgumentException If the issue type is Enhancement and in the Closed state
	 * @throws IllegalArgumentException If the issue type is a Bug in the Working state and is not confirmed
	 * @throws IllegalArgumentException If the verifying state has a fixed resolution or Closed state does not have a resolution
	 * @throws IllegalArgumentException If the issue type is Enhancement which is confirmed

	 */
	private void setState(String state) {
		if (state == null || "".equals(state)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (state.equals(NEW_NAME)) {
			this.state = new NewState();
		} else if (state.equals(WORKING_NAME)) {
			this.state = new WorkingState();
		} else if (state.equals(CONFIRMED_NAME)) {
			this.state = new ConfirmedState();
		} else if (state.equals(VERIFYING_NAME)) {
			this.state = new VerifyingState();
		} else if (state.equals(CLOSED_NAME)) {
			this.state = new ClosedState();
		} 

		if (this.state.equals(verifyingState) && this.resolution == null
				|| this.state.equals(closedState) && this.resolution == null) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if ((this.state.equals(workingState) || this.state.equals(verifyingState))
				&& (owner == null || "".equals(owner))) {
			throw new IllegalArgumentException("Issue cannot be created.");
		} else if ((this.state.equals(newState) || this.state.equals(confirmedState))
				&& (owner == null || "".equals(owner))) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		if(this.state.equals(confirmedState) && resolution == Resolution.FIXED) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (issueType == IssueType.ENHANCEMENT && this.state.equals(closedState)
				|| issueType == IssueType.BUG && this.state.equals(workingState) && !confirmed) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (this.state.equals(verifyingState) && resolution == Resolution.FIXED
				|| this.state.equals(closedState) && resolution == null) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if (issueType == IssueType.ENHANCEMENT && confirmed) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
		
		
	}

	/**
	 * Sets the type of issue of the Issue object
	 * 
	 * @param issueType the issueType to set
	 * @throws IllegalArugmentException if issueType is null or empty
	 * @throws IllegalArugmentException if issueType is not a bug or enhancement 
	 */
	private void setIssueType(String issueType) {
		if (issueType == null || "".equals(issueType)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		if ("Enhancement".equals(issueType)) {
			this.issueType = IssueType.ENHANCEMENT;
		} else if ("Bug".equals(issueType)) {
			this.issueType = IssueType.BUG;
		} else {
			throw new IllegalArgumentException("Issue cannot be created.");
		}
	}

	/**
	 * Sets the summmary of the Issue object
	 * 
	 * @param summary the summary to set
	 * @throws IllegalArugmentException if summary is null or empty
	 */
	private void setSummary(String summary) {
		if (summary == null || "".equals(summary)) {
			throw new IllegalArgumentException("Issue cannot be created.");
		}

		this.summary = summary;
	}

	/**
	 * Sets the owner of the Issue object
	 * 
	 * @param owner the owner to set
	 */
	private void setOwner(String owner) {
		this.owner = owner;
	}

	/**
	 * Sets the confirmed field of the Issue object
	 * 
	 * @param confirmed the confirmed to set
	 */
	private void setConfirmed(boolean confirmed) {
		this.confirmed = confirmed;
	}

	/**
	 * Sets the resolution of the Issue object
	 * 
	 * @param resolution the resolution to set
	 */
	private void setResolution(String resolution) {

		if (resolution == null || "".equals(resolution)) {
			this.resolution = null;
		} else if (Command.R_FIXED.equals(resolution)) {
			this.resolution = Resolution.FIXED;
		} else if (Command.R_DUPLICATE.equals(resolution)) {
			this.resolution = Resolution.DUPLICATE;
		} else if (Command.R_WONTFIX.equals(resolution)) {
			this.resolution = Resolution.WONTFIX;
		} else if (Command.R_WORKSFORME.equals(resolution)) {
			this.resolution = Resolution.WORKSFORME;
		} 

	}

	/**
	 * Sets the notes of the Issue object
	 * 
	 * @param notes the notes to set
	 */
	private void setNotes(ArrayList<String> notes) {
		this.notes = notes;
	}

	/**
	 * Gets the issue ID of the Issue object
	 * 
	 * @return issueId ID of the issue
	 */
	public int getIssueId() {
		return issueId;
	}

	/**
	 * Gets the state of the Issue object
	 * 
	 * @return state state as a string
	 */
	public String getStateName() {
		return state.getStateName();
	}

	/**
	 * Gets the type of issue of the Issue object
	 * 
	 * @return issueType issueType as a string
	 */
	public String getIssueType() {
		if (issueType == IssueType.ENHANCEMENT) {
			return I_ENHANCEMENT;
		} else {
			return I_BUG;
		}
	}

	/**
	 * Gets the resolution of the Issue object
	 * 
	 * @return resolution resolution as a string
	 */
	public String getResolution() {
		if (this.resolution == Resolution.FIXED) {
			return Command.R_FIXED;
		} else if (this.resolution == Resolution.DUPLICATE) {
			return Command.R_DUPLICATE;
		} else if (this.resolution == Resolution.WONTFIX) {
			return Command.R_WONTFIX;
		} else if (this.resolution == Resolution.WORKSFORME) {
			return Command.R_WORKSFORME;
		} else {
			return null;
		}
	}

	/**
	 * Gets the owner of the Issue object
	 * 
	 * @return owner owner of the Issue
	 */
	public String getOwner() {
		if ("".equals(owner)) {
			return null;
		} else {
			return owner;
		}
	}

	/**
	 * Gets the summary of the Issue object
	 * 
	 * @return summary Summary is returned
	 */
	public String getSummary() {
		return summary;
	}

	/**
	 * Gets the notes list of the Issue object
	 * 
	 * @return notes The entire list of notes is returned
	 */
	public ArrayList<String> getNotes() {
		return notes;
	}

	/**
	 * Gets notes from the ArrayList as a String
	 * 
	 * @return note Note from the ArrayList as a String
	 */
	public String getNotesString() {
		ArrayList<String> tempList = getNotes();
		String tempNoteLine = "";

		for (int i = 0; i < tempList.size(); i++) {
			tempNoteLine = tempNoteLine + "-" + tempList.get(i).trim() + "\n";
		}

		return tempNoteLine;
	}

	/**
	 * Gets the confirmed state of the Issue object
	 * 
	 * @return confirmed Returns the confirmed state of the issue
	 */
	public boolean isConfirmed() {
		return confirmed;
	}

	/**
	 * Overrides the toString method of Object to output a String of the Issue
	 * fields
	 * 
	 * @return A string representation of all the contents of the Issue object
	 */
	@Override
	public String toString() {
		String tempOwner;
		if (getOwner() == null) {
			tempOwner = "null";
		} else {
			tempOwner = getOwner();
		}

		String tempRes;
		if (getResolution() == null) {
			tempRes = "";
		} else {
			tempRes = getResolution();
		}

		String tempNotes = getNotesString();
		tempNotes = tempNotes.substring(0, tempNotes.length() - 1);

		return "*" + getIssueId() + "," + getStateName() + "," + getIssueType() + "," + getSummary() + "," + tempOwner
				+ "," + String.valueOf(isConfirmed()) + "," + tempRes + "\n" + tempNotes;
	}

	/**
	 * Adds a note String to the notes ArrayList
	 * 
	 * @param note String note that is to be appended to the notes arraylist
	 * @throws IllegalArgumentException If the note is blank or null
	 */
	private void addNote(String note) {
		if ("".equals(note) || note == null) {
			throw new IllegalArgumentException("Issue cannot be created.");
		} else {
			String temp = "[" + this.getStateName() + "] " + note;
			notes.add(temp);
		}
	}

	/**
	 * Updates the issueState field
	 * 
	 * @param command Command used to update the issueState
	 * @throws UnsupportedOperationException if the state is not appropriate for the
	 *                                       current state
	 */
	public void update(Command command) {
		state.updateState(command);
	}

	/**
	 * IssueType defined the type of issue (Enhancement or Bug) in Issue object
	 * 
	 * @author yash
	 */
	public enum IssueType {
		/**Enhancement IssueType*/
		ENHANCEMENT, 
		/**Bug IssueType*/
		BUG
	}

	/**
	 * Interface for states in the Issue State Pattern. All concrete issue states
	 * must implement the IssueState interface. The IssueState interface should be a
	 * private interface of the Issue class.
	 * 
	 * @author Dr. Sarah Heckman (sarah_heckman@ncsu.edu)
	 */
	private interface IssueState {

		/**
		 * Update the Issue based on the given Command. An UnsupportedOperationException
		 * is throw if the Command is not a valid action for the given state.
		 * 
		 * @param command Command describing the action that will update the Issue's
		 *                state.
		 * @throws UnsupportedOperationException if the Command is not a valid action
		 *                                       for the given state.
		 */
		void updateState(Command command);

		/**
		 * Returns the name of the current state as a String.
		 * 
		 * @return the name of the current state as a String.
		 */
		String getStateName();

	}

	/**
	 * Inner class of Issue and interfaces the IssueState class The New State of the
	 * Issue object
	 * 
	 * @author yash
	 *
	 */
	public class NewState implements IssueState {

		/**
		 * Constructor for the NewState object
		 * 
		 */
		private NewState() {

		}

		/**
		 * Updates the issueState to Working, Confirmed, or closed
		 * 
		 * @param command Command used to update the issueState
		 * @throws UnsupportedOperationException if owner id is null when the command is Assign and an Enhancement
		 * @throws UnsupportedOperationException if the command is Resolve and the resolution is WorksForMe
		 * @throws UnsupportedOperationException if the resolution is Fixed
		 * @throws UnsupportedOperationException if command fails to update state
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ASSIGN && issueType == IssueType.ENHANCEMENT) {
				if (command.getOwnerId() == null || "".equals(command.getOwnerId())) {
					throw new UnsupportedOperationException("Invalid information.");
				} else {
					setOwner(command.getOwnerId());
					setState(WORKING_NAME);
					addNote(command.getNote());
				}
			} else if (command.getCommand() == CommandValue.CONFIRM && issueType == IssueType.BUG) {
				setConfirmed(true);
				setState(CONFIRMED_NAME);
				addNote(command.getNote());
			} else if (command.getCommand() == CommandValue.RESOLVE) {
				if (I_ENHANCEMENT.equals(getIssueType()) && command.getResolution() == Resolution.WORKSFORME) {
					throw new UnsupportedOperationException("Invalid information.");
				}
				if (command.getResolution() == Resolution.FIXED) {
					throw new UnsupportedOperationException("Invalid information.");
				}
				setState(CLOSED_NAME);
				resolution = command.getResolution();
				addNote(command.getNote());
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Return a String representation of the issueState New
		 * 
		 * @return String of the current issueState
		 */
		@Override
		public String getStateName() {
			return "New";
		}

	}

	/**
	 * Inner class of Issue and interfaces the IssueState class The Working State of
	 * the Issue object
	 * 
	 * @author yash
	 *
	 */
	public class WorkingState implements IssueState {

		/**
		 * Constructor for the WorkingState object
		 * 
		 */
		private WorkingState() {

		}

		/**
		 * Updates the issueState to Verifying or Closed
		 * 
		 * @param command Command used to update the issueState
		 * @throws UnsupportedOperationException if command fails to update state
		 */
		@Override
		public void updateState(Command command) {
			if (command.getResolution() == Resolution.FIXED && command.getCommand() == CommandValue.RESOLVE) {
				setState(VERIFYING_NAME);
				setResolution("Fixed");
				addNote(command.getNote());
			} else {

				if (I_ENHANCEMENT.equals(getIssueType()) && (command.getResolution() == Resolution.DUPLICATE
						|| command.getResolution() == Resolution.WONTFIX)) {
					setState(CLOSED_NAME);
					resolution = command.getResolution();
					addNote(command.getNote());

				} else if (I_BUG.equals(getIssueType()) && (command.getResolution() == Resolution.DUPLICATE
						|| command.getResolution() == Resolution.WONTFIX
						|| command.getResolution() == Resolution.WORKSFORME)) {
					setState(CLOSED_NAME);
					resolution = command.getResolution();
					addNote(command.getNote());
				} else {
					throw new UnsupportedOperationException("Invalid information.");

				}
			}

		}

		/**
		 * Return a String representation of the issueState Working
		 * 
		 * @return String of the current issueState
		 */
		@Override
		public String getStateName() {
			return "Working";
		}

	}

	/**
	 * Inner class of Issue and interfaces the IssueState class The Confirmed State
	 * of the Issue object
	 * 
	 * @author yash
	 *
	 */
	public class ConfirmedState implements IssueState {

		/**
		 * Constructor for the ConfirmedState object
		 * 
		 */
		private ConfirmedState() {

		}

		/**
		 * Updates the issueState to Working or Closed
		 * 
		 * @param command Command used to update the issueState
		 * @throws UnsupportedOperationException if the owner is null or blank when the command is Assign
		 * @throws UnsupportedOperationException if the command fails to update the state 
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.ASSIGN) {
				if (command.getOwnerId() == null || "".equals(command.getOwnerId())) {
					throw new UnsupportedOperationException("Invalid information.");
				} else {
					setOwner(command.getOwnerId());
					setState(WORKING_NAME);
					addNote(command.getNote());
				}
			} else if (command.getResolution() == Resolution.WONTFIX && command.getCommand() == CommandValue.RESOLVE) {
				setResolution("WontFix");
				setState(CLOSED_NAME);
				addNote(command.getNote());
			} 
			else {
				throw new UnsupportedOperationException("Invalid information.");
			}

		}

		/**
		 * Return a String representation of the issueState Confirmed
		 * 
		 * @return String of the current issueState
		 */
		@Override
		public String getStateName() {
			return "Confirmed";
		}

	}

	/**
	 * Inner class of Issue and interfaces the IssueState class The Verifying State
	 * of the Issue object
	 * 
	 * @author yash
	 *
	 */
	public class VerifyingState implements IssueState {

		/**
		 * Constructor for the NewState object
		 * 
		 */
		private VerifyingState() {

		}

		/**
		 * Updates the issueState to Working or Closed
		 * 
		 * @param command Command used to update the issueState
		 * @throws UnsupportedOperationException if the command fails to update the state
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.VERIFY) {
				setState(CLOSED_NAME);
				addNote(command.getNote());
			} else if (command.getCommand() == CommandValue.REOPEN) {
				setResolution(null);
				setState(WORKING_NAME);
				addNote(command.getNote());
			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Return a String representation of the issueState Verifying
		 * 
		 * @return String of the current issueState
		 */
		@Override
		public String getStateName() {
			return "Verifying";
		}

	}

	/**
	 * Inner class of Issue and interfaces the IssueState class The Closed State of
	 * the Issue object
	 * 
	 * @author yash
	 *
	 */
	public class ClosedState implements IssueState {

		/**
		 * Constructor for the ClosedState object
		 * 
		 */
		private ClosedState() {

		}

		/**
		 * Updates the issueState to Working, Confirmed, or New
		 * 
		 * @param command Command used to update the issueState
		 * @throws UnsupportedOperationException  if the commmand fails to update the state
		 */
		@Override
		public void updateState(Command command) {
			if (command.getCommand() == CommandValue.REOPEN) {
				setResolution(null);

				if (I_ENHANCEMENT.equals(getIssueType()) && getOwner() != null && !("".equals(getOwner()))) {
					setState(WORKING_NAME);
					addNote(command.getNote());
				} else if (I_BUG.equals(getIssueType()) && isConfirmed() && getOwner() != null
						&& !("".equals(getOwner()))) {
					setState(WORKING_NAME);
					addNote(command.getNote());
				} else if (I_BUG.equals(getIssueType()) && isConfirmed()
						&& (getOwner() == null || "".equals(getOwner()))) {
					setState(CONFIRMED_NAME);
					addNote(command.getNote());
				} else if (getOwner() == null || "".equals(owner)) {
					setState(NEW_NAME);
					addNote(command.getNote());
				}

			} else {
				throw new UnsupportedOperationException("Invalid information.");
			}
		}

		/**
		 * Return a String representation of the issueState Closed
		 * 
		 * @return String of the current issueState
		 */
		@Override
		public String getStateName() {
			return "Closed";
		}

	}
}