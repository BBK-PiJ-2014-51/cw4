package impl;

import interfaces.Contact;

public class ContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;
	
	public ContactImpl(int id, String name, String notes) {
		this.id = id;
		this. name = name;
		this.notes = notes;
	}

	@Override
	public int getId() {
		return id;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public String getNotes() {
		return notes;
	}

	@Override
	public void addNotes(String note) {
		notes = (notes.isEmpty()) ? note : String.format("%s\n%s", notes, note);	
	}

}
