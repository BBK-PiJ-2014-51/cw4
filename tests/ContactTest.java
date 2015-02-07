package tests;

import static org.junit.Assert.*;

import org.junit.Before;
import org.junit.Test;

import interfaces.Contact;

public class ContactTest {
	private static final int TEST_ID = 33;
	private static final String TEST_NAME = "John Smith";
	private static final String TEST_NOTES = "Here are some notes.";
	private Contact contact;
	
	@Before
	public void buildUp(){
		contact = new ContactImpl(TEST_ID, TEST_NAME, TEST_NOTES);
	}
	
	@Test
	public void getContactId() {
		assertEquals(TEST_ID, contact.getId());
	}
	
	@Test
	public void getContactName() {
		assertEquals(true, TEST_NAME.equals(contact.getName()));
	}
	
	@Test
	public void getContactNotes() {
		assertEquals(true, TEST_NOTES.equals(contact.getNotes()));
	}
	
	@Test
	public void addContactNotes() {
		contact = new ContactImpl(TEST_ID, TEST_NAME);
		contact.addNotes(TEST_NOTES);
		assertEquals(true, TEST_NOTES.equals(contact.getNotes()));
	}

}
