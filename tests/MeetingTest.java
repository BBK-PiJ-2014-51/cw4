package tests;

import static org.junit.Assert.*;
import interfaces.Contact;
import interfaces.Meeting;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import org.junit.Before;
import org.junit.Test;

public class MeetingTest {
	private static final int TEST_ID = 33;
	private static final Calendar TEST_DATE = new GregorianCalendar(2015, 06, 15);
	private Meeting meeting;
	private Set<Contact> contacts;
	
	@Before
	public void buildUp(){
		contacts = new HashSet<Contact>();
		Contact contact = new ContactImpl(0, "Attendee A");
		contacts.add(contact);
		contact = new ContactImpl(1, "Attendee B");
		contacts.add(contact);
		contact = new ContactImpl(2, "Attendee C");
		contacts.add(contact);
		meeting = new MeetingImpl(TEST_ID, TEST_DATE, contacts);
	}

	@Test
	public void getId() {
		assertEquals(TEST_ID, meeting.getId());
	}
	
	@Test
	public void getDate() {
		assertEquals(true, TEST_DATE.equals(meeting.getDate()));
	}
	
	@Test
	public void getContacts() {
		Set<Contact> retrievedContacts = meeting.getContacts();
		boolean equalSets = true;
		if (retrievedContacts.size() != contacts.size()) equalSets = false;
		for (Contact contact : contacts) {
			if (!retrievedContacts.contains(contact)) equalSets = false;
		}
		assertEquals(true, equalSets);
	}
}