package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

import impl.ContactManagerImpl;
import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerTest {

	private static final Calendar PAST_TEST_DATE = new GregorianCalendar(2015, Month.JANUARY.getValue(), 1);
	private static final Calendar FUTURE_TEST_DATE = new GregorianCalendar(2016, Month.JANUARY.getValue(), 1);
	private static final String TEST_MEETING_NOTES = "Some test meeting notes";
	
	private ContactManager cm = new ContactManagerImpl();

	/*
	 Add / get future and past meetings
	 */
	
	@Test
	public void addGetMeeting_validInput_returnMeeting() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		Meeting meeting = cm.getMeeting(cm.addFutureMeeting(contactList, FUTURE_TEST_DATE ));
		boolean equalMeetings =  (meeting.getDate().equals(FUTURE_TEST_DATE) 
				&& meeting.getContacts().equals(contactList) ); 
		assertEquals(true, equalMeetings);
	}
	
	@Test
	public void getMeeting_unfoundMeetingParam_ReturnNull() {
		Meeting meeting = cm.getMeeting(1);
		assertEquals(null, meeting);
	}
	
	@Test
	public void addGetFutureMeeting_validInput_returnMeeting() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		FutureMeeting meeting = cm.getFutureMeeting(cm.addFutureMeeting(contactList, FUTURE_TEST_DATE ));
		boolean equalMeetings =  (meeting.getDate().equals(FUTURE_TEST_DATE) 
				&& meeting.getContacts().equals(contactList) ); 
		assertEquals(true, equalMeetings);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addFutureMeeting_PastDateParam_ThrowIllArgEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		cm.addFutureMeeting(contactList, PAST_TEST_DATE );
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addFutureMeeting_UnknownContactParam_ThrowIllArgEx() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		cm.addFutureMeeting(contactList, FUTURE_TEST_DATE );
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getFutureMeeting_PastMeetingId_ThrowIllArgEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
		Object[] contactArray = contactList.toArray();
		Contact firstContact = (Contact) contactArray[0]; 
		int meetingId = cm.getPastMeetingList(firstContact).get(0).getId();
		FutureMeeting meeting = cm.getFutureMeeting(meetingId);
	}
	
	@Test
	public void getFutureMeeting_unfoundMeetingParam_ReturnNull() {
		FutureMeeting meeting = cm.getFutureMeeting(1);
		assertEquals(null, meeting);
	}

	@Test
	public void addGetPastMeeting_validInput_returnMeeting() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		//get the first contact added
		Object[] contactArray = contactList.toArray();
		Contact firstContact = (Contact) contactArray[0]; 
		
		// get the only meeting from contact manager
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
		int meetingId = cm.getPastMeetingList(firstContact).get(0).getId();
		PastMeeting meeting = cm.getPastMeeting(meetingId);
		
		boolean equalMeetings =  (meeting.getDate().equals(PAST_TEST_DATE) 
				&& meeting.getContacts().equals(contactList) 
				&& meeting.getNotes().equals(TEST_MEETING_NOTES)); 
		assertEquals(true, equalMeetings);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addPastMeeting_EmptyContactParam_ThrowIllArgEx() {
		Set<Contact> contactList = new TreeSet<Contact>();
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addPastMeeting_UnknownContactParam_ThrowIllArgEx() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addPastMeeting_NullFirstParam_ThrowNullPntrEx() {
		cm.addNewPastMeeting(null, PAST_TEST_DATE, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addPastMeeting_NullSecondParam_ThrowNullPntrEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, null, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addPastMeeting_NullThirdParam_ThrowNullPntrEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getPastMeeting_FutureIdParam_IllArgEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		PastMeeting meeting = cm.getPastMeeting(cm.addFutureMeeting(contactList, FUTURE_TEST_DATE ));
	}
	
	@Test
	public void getPastMeeting_unfoundMeetingParam_ReturnNull() {
		PastMeeting meeting = cm.getPastMeeting(1);
		assertEquals(null, meeting);
	}
	
	/*
	 * get meeting lists
	 */
	@Test
	public void getFutureMeetingListByContact() {
		//to do get list back
	}
	
	@Test
	public void getFutureMeetingListByDate() {
		//to do get list back
	}
	
	@Test
	public void getFutureMeetingListEmptyIfNone(){
		// get empty list back
	}
	
	@Test
	public void getFutureMeetingListIsChronological(){
		// make sure list is sorted
	}
	
	@Test
	public void getFutureMeetingListNoDuplicates(){
		
	}
	
	@Test
	public void getFutureMeetingListByNullContact(){
		
	}
	
	@Test
	public void getPastMeetingListByContact() {
		//to do get list back
	}
	
	@Test
	public void getPastMeetingListByDate() {
		//to do get list back
	}
	
	@Test
	public void getPastMeetingListEmptyIfNone(){
		// get empty list back
	}
	
	@Test
	public void getPastMeetingListIsChronological(){
		// make sure list is sorted
	}
	
	@Test
	public void getPastMeetingListNoDuplicates(){
		
	}
	
	@Test
	public void getPastMeetingListByNullContact(){
		
	}
	
	/*
	 * Contacts, notes, flush tests
	 */
	@Test
	public void getPastMeetingNotes(){
		//get notes back
	}
	
	@Test
	public void getPastMeetingNotesThorwsIllegalArguementIfNotExist(){
		//get notes back
	}
	
	@Test
	public void getPastMeetingNotesThrowsIllegalStateIfFuture(){
		//get notes back
	}
	
	@Test
	public void getPastMeetingNotesThrowsNpIfNull(){
		//get notes back
	}

	@Test
	public void getContactListByIds(){
		//get contact back
	}
	
	@Test
	public void addContactThrowsNpIfNull(){
		
	}
	
	@Test
	public void getContactListByName(){
		///get contacts back
	}
	
	@Test
	public void getContactListIllegalArgIfAnyNotFound(){
		
	}
	
	@Test
	public void flush(){
		//save and restore state
	}
	
	
	/*
	 * Private methods
	 */
	/**
	 * loads some contact name and notes from TestContacts enum class into contact manager instance
	 */
	private void loadTestContacts() {
		for (TestContacts contact : TestContacts.values()){
			cm.addNewContact(contact.toString(), contact.getNotes());
		}
	}
	
	/**
	 * Returns a set of contacts from test data
	 * 
	 * @param numContacts number of contacts to return
	 * @return Set of Contacts
	 */
	private Set<Contact> getContactList(int numContacts){
		Set<Contact> contactList = null;
		if (numContacts < 0 || numContacts > TestContacts.values().length )
			return contactList;
		TestContacts[] testContacts = TestContacts.values();
		for (int i = 0; i < numContacts; i++){
			contactList.add(new ContactImpl(testContacts[i].toString(), testContacts[i].getNotes()));
		}
		
	}
	
}