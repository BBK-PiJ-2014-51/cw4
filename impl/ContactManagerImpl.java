package impl;

import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

public class ContactManagerImpl implements ContactManager {
	private Set<Contact> contacts = new HashSet<Contact>();
	private Set<PastMeeting> pastMeetings = new HashSet<PastMeeting>();
	private Set<FutureMeeting> futureMeetings = new HashSet<FutureMeeting>();
	private int currentMeetingId = 0;
	private int currentContactId = 0;
	
	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (contacts == null || date == null) throw new NullPointerException("Null parameter!");
		if (contacts.isEmpty()) throw new IllegalArgumentException("Must supply contacts!");
		if (date.before(Calendar.getInstance())) throw new IllegalArgumentException("Date is in the past already!");
		for (Contact contact : contacts){
			if (!isInDb(contact)) throw new IllegalArgumentException("Unknown Contact!");
		}
		futureMeetings.add(new FutureMeetingImpl(currentMeetingId, date, contacts));
		return currentMeetingId++;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		for (PastMeeting meeting : pastMeetings){
			if (meeting.getId() == id) return meeting;
		}
		for (FutureMeeting meeting : futureMeetings){
			if (meeting.getId() == id) throw new IllegalArgumentException("Meeting ID represents future meeting!");
		}
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		for (FutureMeeting meeting : futureMeetings){
			if (meeting.getId() == id) return meeting;
		}
		for (PastMeeting meeting : pastMeetings){
			if (meeting.getId() == id)
				throw new IllegalArgumentException("Meeting ID represents past meeting!");
		}
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		for (Meeting meeting : futureMeetings){
			if (meeting.getId() == id) return meeting;
		}

		for (Meeting meeting : futureMeetings){
			if (meeting.getId() == id) return meeting;
		}
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (contact == null) throw new IllegalArgumentException("Null contact provided!");
		if (!isInDb (contact)) throw new IllegalArgumentException("Contact does not exist!");
		List<Meeting> meetings = new LinkedList<Meeting>();
		for (Meeting meeting : futureMeetings){
			for (Contact member : meeting.getContacts()){
				if (member.getId() == contact.getId()){
					meetings.add(meeting);
				}
			}
		}
		return meetings;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		if (date == null) throw new IllegalArgumentException("Null date provided!");
		List<Meeting> meetings = new LinkedList<Meeting>();
		for (Meeting meeting : futureMeetings){
			if (meeting.getDate().equals(date))
				meetings.add(meeting);
		}
		for (Meeting meeting : pastMeetings){
			if (meeting.getDate().equals(date))
				meetings.add(meeting);
		}
		return meetings;
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		if (!isInDb (contact)) throw new IllegalArgumentException("Contact does not exist!");
		List<PastMeeting> pastMeetings = new LinkedList<PastMeeting>();
		for (PastMeeting meeting : pastMeetings){
			for (Contact member : meeting.getContacts()){
				if (member.getName().equals(contact.getName()))
					pastMeetings.add(meeting);
			}
		}
		return pastMeetings;
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		if (contacts == null || date == null || text == null) throw new NullPointerException("Null parameter!");
		if (contacts.isEmpty()) throw new IllegalArgumentException("Must supply contacts!");
		for (Contact contact : contacts){
			if (!isInDb(contact)) throw new IllegalArgumentException("Unknown Contact!");
		}
		pastMeetings.add(new PastMeetingImpl(currentMeetingId++, date, contacts, text));
	}

	private boolean isInDb(Contact contact) {
		for (Contact conFromDb : contacts){
			if (contact.getId() == conFromDb.getId()) return true;
		}
		return false;
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		if (text == null) throw new NullPointerException("Notes are null!");		
		for (PastMeeting meeting : pastMeetings){
			if (meeting.getId() == id) {
				System.out.println("adding past meeting");
				pastMeetings.add(new PastMeetingImpl(id, meeting.getDate(),
						meeting.getContacts(), String.format("%s\n%s", meeting.getNotes(), text)));
				pastMeetings.remove(meeting);
				return;
			}
		}
		for (FutureMeeting meeting : futureMeetings){
			if (meeting.getId() == id){
				if (meeting.getDate().after(Calendar.getInstance())) 
					throw new IllegalStateException("Meeting is still scheduled for the future!");
				pastMeetings.add(new PastMeetingImpl(id, meeting.getDate(), 
						meeting.getContacts(), text));
				futureMeetings.remove(meeting);
				return;
			}
		}
		throw new IllegalArgumentException("ID does not represent meeting!");		
	}

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) throw new NullPointerException("Neither name nor notes may be null!");	
		contacts.add(new ContactImpl(currentContactId++, name, notes));
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> foundContacts = new HashSet<Contact>();
		for (Contact contact : contacts){
			for (int id : ids){
				if (contact.getId() == id) foundContacts.add(contact);
			}
		}
		if (foundContacts.isEmpty()) throw new IllegalArgumentException("Id not found!");
		return foundContacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		Set<Contact> foundContacts = new HashSet<Contact>();
		for (Contact contact : contacts){
			if (contact.getName().contains(name)) foundContacts.add(contact);
		}
		if (foundContacts.isEmpty()) throw new IllegalArgumentException("Name not found!");
		return foundContacts;
	}

	@Override
	public void flush() {
		// TODO Auto-generated method stub

	}

}
