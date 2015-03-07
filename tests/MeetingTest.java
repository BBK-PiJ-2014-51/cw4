package tests;

import static org.junit.Assert.*;
import impl.ContactImpl;
import impl.MeetingImpl;
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
	private static final String TEST_NOTES = "";
	private static final Calendar TEST_DATE = new GregorianCalendar(2015, 06, 15);
	private Meeting meeting;
	private Set<Contact> contacts;
	
	@Before
	public void buildUp(){
		
		int numContacts = 3;
		contacts = new HashSet<Contact>();
		for (int i = 0, ch = 'A'; i < numContacts; i++){
			contacts.add(new ContactImpl(i, "Contact " + ch++, TEST_NOTES + ch));
		}
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
		if (retrievedContacts == null){
			equalSets = false;
		} else{
			if (retrievedContacts.size() != contacts.size()) equalSets = false;
			for (Contact contact : contacts) {
				if (!retrievedContacts.contains(contact)) equalSets = false;
			}
		}
		assertEquals(true, equalSets);
	}
}