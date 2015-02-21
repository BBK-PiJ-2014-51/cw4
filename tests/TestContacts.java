package tests;

public enum TestContacts {
	JOHN("Here are John's notes."),
	KEVIN("Here are Kevin's notes."),
	RACHEL("Here are Rachel's notes."),
	BETH("Here are Beth's notes."),
	SAM("Here are Sam's notes."),
	ROGER("Here are Roger's notes."),
	ROSE("Here are Rose's notes."),
	JENNIFER("Here are Jennifer's notes.");
	
	private String notes;
	
	private TestContacts(String notes){
		this.notes = notes;
	}
	
	public String getNotes(){
		return notes;
	}
}
