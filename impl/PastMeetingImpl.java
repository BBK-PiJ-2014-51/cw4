package impl;

import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.PastMeeting;
/**
 * Implementation for PastMeeting based on MeetingImpl.<br />
 * 
 * @author caleb
 *
 */
public class PastMeetingImpl extends MeetingImpl implements PastMeeting {
	private String notes;
	
	/**
	 * Constructor for past meetings. 
	 *  
	 * @param id to identify meeting
	 * @param date meeting was held
	 * @param contacts present at meeting
	 * @param notes concerning meeting
	 */
	public PastMeetingImpl(int id, Calendar date,
			Set<Contact> contacts, String notes) {
		super(id, date, contacts);;
		this.notes = notes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNotes() {
		return notes;
	}
}