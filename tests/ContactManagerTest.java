package tests;

import static org.junit.Assert.*;

import java.time.LocalDate;
import java.time.Month;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import java.util.Set;

import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerTest {

	private static final Calendar TEST_DATE = new GregorianCalendar(2015, Month.JANUARY.getValue(), 1);
	private static final String TEST_MEETING_NOTES = "Some test meeting notes";
	
	private ContactManager cm = new ContactManagerImpl();

	/*
	 Add / get future and past meetings
	 */
	
	@Test
	public void getMeeting() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		Meeting meeting = cm.getMeeting(cm.addFutureMeeting(contactList, TEST_DATE ));
		boolean equalMeetings =  (meeting.getDate().equals(TEST_DATE) 
				&& meeting.getContacts().equals(contactList) ); 
		assertEquals(true, equalMeetings);
	}
	
	
	@Test
	public void getFutureMeeting() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		FutureMeeting meeting = cm.getFutureMeeting(cm.addFutureMeeting(contactList, TEST_DATE ));
		boolean equalMeetings =  (meeting.getDate().equals(TEST_DATE) 
				&& meeting.getContacts().equals(contactList) ); 
		assertEquals(true, equalMeetings);
	}
	
	


	@Test
	public void getPastMeeting() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		
		//get the first contact added
		Object[] contactArray = contactList.toArray();
		Contact contact = (Contact) contactArray[0]; 
		
		// get the only meeting from contact manager
		List<PastMeeting> meetings = cm.getPastMeetingList(contact);
		PastMeeting meeting = null;
		if (meetings.size() == 1){
			meetings.get(0); 
		}
		
		boolean equalMeetings =  (meeting.getDate().equals(TEST_DATE) 
				&& meeting.getContacts().equals(contactList) 
				&& meeting.getNotes().equals(TEST_MEETING_NOTES)); 
		assertEquals(true, equalMeetings);
	}
	
	@Test
	public void addFutureMeetingWithPastDate() {
		//to do add past meeting as future, expects illegal argument ex
	}
	
	@Test
	public void addFutureMeetingWithUnknownContact() {
		//to do add future meeting with unknown contact, expects illegal argument ex
	}
	
	@Test
	public void addPastMeetingWithEmptyContacts() {
		//to do add past meeting with empty set of contacts, expects illegal argument ex
	}
	
	@Test
	public void addPastMeetingWithUnknownContact() {
		//to do add past meeting with unknown contact, expects illegal argument ex
	}
	
	@Test
	public void addPastMeetingWithNullArgument() {
		//to do add past meeting with null parameter, expects null pointer ex
	}
	
	@Test
	public void getFutureMeetingWithPastId() {
		//to do get past meeting by id, expects illegal argument ex
	}
	
	@Test
	public void getPastMeetingWithFutureId() {
		//to do get future meeting by id, expects illegal argument ex
	}
	
	@Test
	public void getNullFromUnfoundMeeting() {
		//to do get null from unfound id
	}
	
	@Test
	public void getNullFromUnfoundFutureMeeting() {
		//to do get null from unfound id
	}
	
	@Test
	public void getNullFromUnfoundPastMeeting() {
		//to do get null from unfound id
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
	public void getPastMeetingNotesTrhowsIllegalStateIfFuture(){
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
	
	
	private Set<Contact> getContactList(int numContacts){
		Set<Contact> contactList = null;
		if (numContacts < 0 || numContacts > TestContacts.values().length )
			return contactList;
		TestContacts[] testContacts = TestContacts.values();
		for (int i = 0; i < numContacts; i++){
			contactList.add(new Contact(testContacts[i].toString(), testContacts[i].getNotes()));
		}
		
	}
	
}