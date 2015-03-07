package tests;

import static org.junit.Assert.*;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Set;

import impl.ContactImpl;
import impl.FutureMeetingImpl;
import interfaces.Contact;
import interfaces.FutureMeeting;

import org.junit.Test;

public class FutureMeetingTest {
	private static final Calendar TEST_DATE = new GregorianCalendar(2015, 06, 15);
	private static final int TEST_ID = 5;
	private Set<Contact> contacts;
	private FutureMeeting meeting;
	
	@Test
	public void testConstructor() {
		int numContacts = 2;
		contacts = new HashSet<Contact>();
		for (int i = 0, ch = 'A'; i < numContacts; i++){
			contacts.add(new ContactImpl(i, "Contact " + ch++, ""));
		}
		meeting = new FutureMeetingImpl(TEST_ID, TEST_DATE, contacts);
		assertEquals(true, meeting.getId() == TEST_ID &&
			meeting.getDate().equals(TEST_DATE) &&
			meeting.getContacts().size() == contacts.size());
	}

}
