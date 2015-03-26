package impl;

import interfaces.Contact;
import interfaces.Meeting;

import java.util.Calendar;
import java.util.Set;
/**
 * Requires id to be specified upon creation.
 */
public class MeetingImpl implements Meeting {
	
	private int id;
	private Calendar date;
	private Set<Contact> contacts;
	/**
	 * Constructor for meeting. <br />
	 * 
	 * @param id for identifying meeting
	 * @param date of meeting
	 * @param contacts associated with meeting
	 */
	public MeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		this.id = id;
		this.date = date;
		this.contacts = contacts;
	}
	
	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Calendar getDate() {
		return date;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public Set<Contact> getContacts() {
		return contacts;
	}
	

}
