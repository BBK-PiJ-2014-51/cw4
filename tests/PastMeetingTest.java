package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;
import java.util.TreeSet;

import impl.ContactImpl;
import impl.PastMeetingImpl;
import interfaces.Contact;
import interfaces.PastMeeting;

import org.junit.Before;
import org.junit.Test;

public class PastMeetingTest {
	
	private static int TEST_ID = 5;
	private static final String TEST_NOTES = "test notes";
	private static final Calendar TEST_DATE = new GregorianCalendar(2014, 06, 15);
	private Set<Contact> contacts;
	private PastMeeting pm;
	
	
	@Test
	public void getNotesAfterAddMethod() {
		int numContacts = 2;
		contacts = new HashSet<Contact>();
		for (int i = 0, ch = 'A'; i < numContacts; i++){
			contacts.add(new ContactImpl(i, "Contact " + ch++, TEST_NOTES + ch));
		}
		pm = new PastMeetingImpl(TEST_ID, TEST_DATE, contacts, TEST_NOTES);
		assertEquals(true, TEST_NOTES.equals(pm.getNotes()));
	}
	
}
