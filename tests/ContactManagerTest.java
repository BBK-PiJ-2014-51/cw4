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

import impl.ContactImpl;
import impl.ContactManagerImpl;
import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

import org.junit.Before;
import org.junit.Test;
/**
 * Contact Manager Tests
 * @author caleb
 *
 * All test cases are named as such: <Scope of test>_<Context tested>_<Expected Result>
 * e.g. testing a method named computeTotal with a null parameter, expecting an illegal argument exception
 * could be named computeTotal_nullParam_throwIllArgEx
 */
public class ContactManagerTest {

	private static final Calendar PAST_TEST_DATE = new GregorianCalendar(2015, Month.JANUARY.getValue(), 1, 12, 0);
	private static final Calendar FUTURE_TEST_DATE = new GregorianCalendar(2016, Month.JANUARY.getValue(), 1, 12, 0);
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
	public void getFutureMeetingList_validContactParam_returnList() {
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		Set<Integer> meetingIds = new TreeSet<Integer>();
		for (int i = 0; i < numMeetings; i++){
			cm.addFutureMeeting(contacts, FUTURE_TEST_DATE);
		}
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		
		contacts.remove(contactsArray[0]);
		for (int i = 0; i < numMeetings; i++){
			cm.addFutureMeeting(contacts, FUTURE_TEST_DATE);
		}
		
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contactsArray[0]);
		Set<Integer> returnedIds = new TreeSet<Integer>();
		for (Meeting meeting : returnedMeetings){
			returnedIds.add(meeting.getId());
		}
		boolean equalSize = returnedIds.size() == meetingIds.size();
		boolean isSubset = returnedIds.containsAll(meetingIds);
		assertEquals(true, (equalSize && isSubset));
	}
	
	@Test
	public void getFutureMeetingList_validDateParam_returnCorrectSizeList() {
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		Set<Integer> meetingIds = new TreeSet<Integer>();
		for (int i = 0; i < numMeetings; i++){
			meetingIds.add(cm.addFutureMeeting(contacts, FUTURE_TEST_DATE));
		}
		
		Calendar addDate = FUTURE_TEST_DATE;
		for (int i = 0; i < numMeetings; i++){
			addDate.add(Calendar.DAY_OF_MONTH, 1);
			cm.addFutureMeeting(contacts, addDate);
		}
		
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(FUTURE_TEST_DATE);

		Set<Integer> returnedIds = new TreeSet<Integer>();
		for (Meeting meeting : returnedMeetings){
			returnedIds.add(meeting.getId());
		}
		boolean equalSize = returnedIds.size() == meetingIds.size();
		boolean isSubset = returnedIds.containsAll(meetingIds);
		assertEquals(true, (equalSize && isSubset));
	}
	
	@Test
	public void getFutureMeetingList_contactWithoutMeetingsParam_returnEmptyList(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(2);
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contactsArray[0]);
		assertEquals(true, returnedMeetings.isEmpty());
	}
	
	@Test
	public void getFutureMeetingList_dateWithoutMeetingsParam_returnEmptyList(){
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(FUTURE_TEST_DATE);
		assertEquals(true, returnedMeetings.isEmpty());
	}
	
	@Test
	public void getFutureMeetingList_validContact_returnedChronologically(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		int numMeetings = 3;
		Calendar addDate = FUTURE_TEST_DATE;
		for (int i = 0; i < numMeetings; i++){
			addDate.add(Calendar.DAY_OF_MONTH, 1);
			cm.addFutureMeeting(contacts, addDate);
		}
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contactsArray[0]);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate().after(returnedMeetings.get(i + 1).getDate())){
				isChronological = false;
			}
		}
		assertEquals(true, isChronological);
	}
	
	@Test
	public void getFutureMeetingList_validDate_returnedChronologically(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		int numMeetings = 3;
		Calendar addDate = FUTURE_TEST_DATE;
		for (int i = 0; i < numMeetings; i++){
			addDate.add(Calendar.HOUR_OF_DAY, 2);
			cm.addFutureMeeting(contacts, addDate);
		}
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(FUTURE_TEST_DATE);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate().after(returnedMeetings.get(i + 1).getDate())){
				isChronological = false;
			}
		}
		assertEquals(true, isChronological);
	}
	
	@Test
	public void getFutureMeetingList_getByDate_returnNoDuplicates(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		for (int i = 0; i < numMeetings; i++){
			cm.addFutureMeeting(contacts, FUTURE_TEST_DATE);
		}
		
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(FUTURE_TEST_DATE);
		Set<Integer> meetingIds = new TreeSet<Integer>();
		boolean hasDuplicates = false;
		for (Meeting meeting : returnedMeetings){
			int id = meeting.getId();
			if (meetingIds.contains(id)) hasDuplicates = true;
			else meetingIds.add(id);
		}
		assertEquals(false, hasDuplicates);
	}
	
	@Test
	public void getFutureMeetingList_getByContact_returnNoDuplicates(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		for (int i = 0; i < numMeetings; i++){
			cm.addFutureMeeting(contacts, FUTURE_TEST_DATE);
		}
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contactsArray[0]);
		Set<Integer> meetingIds = new TreeSet<Integer>();
		boolean hasDuplicates = false;
		for (Meeting meeting : returnedMeetings){
			int id = meeting.getId();
			if (meetingIds.contains(id)) hasDuplicates = true;
			else meetingIds.add(id);
		}
		assertEquals(false, hasDuplicates);
	}
	
	@Test
	public void getFutureMeetingList_pastDateParam_returnValidList(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		Set<Integer> meetingIds = new TreeSet<Integer>();
		for (int i = 0; i < numMeetings; i++){
			meetingIds.add(cm.addFutureMeeting(contacts, PAST_TEST_DATE));
		}
		
		Calendar addDate = PAST_TEST_DATE;
		for (int i = 0; i < numMeetings; i++){
			addDate.add(Calendar.DAY_OF_MONTH, 1);
			cm.addFutureMeeting(contacts, addDate);
		}
		
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(PAST_TEST_DATE);
		Set<Integer> returnedIds = new TreeSet<Integer>();
		for (Meeting meeting : returnedMeetings){
			returnedIds.add(meeting.getId());
		}
		boolean equalSize = returnedIds.size() == meetingIds.size();
		boolean isSubset = returnedIds.containsAll(meetingIds);
		assertEquals(true, (equalSize && isSubset));
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getFutureMeetingList_unknownContactParam_throwIllArgEx(){
		Set<Contact> contacts = getContactList(5);
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		cm.getFutureMeetingList(contactsArray[0]);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getFutureMeetingList_nullContactParam_throwIllArgEx(){
		loadTestContacts();
		Contact contact = null;
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contact);	
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getFutureMeetingList_nullDateParam_throwIllArgEx(){
		loadTestContacts();
		Calendar nullCal = null;
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(nullCal);
	}
	
	@Test
	public void getPastMeetingList_validContactParam_returnList() {
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		Set<Integer> meetingIds = new TreeSet<Integer>();
		for (int i = 0; i < numMeetings; i++){
			cm.addNewPastMeeting(contacts, PAST_TEST_DATE, TEST_MEETING_NOTES);
		}
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		
		contacts.remove(contactsArray[0]);
		for (int i = 0; i < numMeetings; i++){
			cm.addNewPastMeeting(contacts, PAST_TEST_DATE, TEST_MEETING_NOTES);
		}
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contactsArray[0]);
		Set<Integer> returnedIds = new TreeSet<Integer>();
		for (PastMeeting meeting : returnedMeetings){
			returnedIds.add(meeting.getId());
		}
		boolean equalSize = returnedIds.size() == meetingIds.size();
		boolean isSubset = returnedIds.containsAll(meetingIds);
		assertEquals(true, (equalSize && isSubset));
	}
	
	@Test
	public void getPastMeetingList_noMeetingsWithContactParam_returnEmptyList(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(2);
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contactsArray[0]);
		assertEquals(true, returnedMeetings.isEmpty());
	}
	
	@Test
	public void getPastMeetingList_validContact_returnedChronologically(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		int numMeetings = 3;
		Calendar addDate = PAST_TEST_DATE;
		for (int i = 0; i < numMeetings; i++){
			addDate.add(Calendar.DAY_OF_MONTH, 1);
			cm.addNewPastMeeting(contacts, addDate, TEST_MEETING_NOTES);
		}
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contactsArray[0]);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate().after(returnedMeetings.get(i + 1).getDate())){
				isChronological = false;
			}
		}
		assertEquals(true, isChronological);
	}
	
	@Test
	public void getFutureMeetingList_validPastDate_returnedChronologically(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		int numMeetings = 3;
		Calendar addDate = PAST_TEST_DATE;
		for (int i = 0; i < numMeetings; i++){
			addDate.add(Calendar.HOUR_OF_DAY, 2);
			cm.addNewPastMeeting(contacts, addDate, TEST_MEETING_NOTES);
		}
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(PAST_TEST_DATE);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate().after(returnedMeetings.get(i + 1).getDate())){
				isChronological = false;
			}
		}
		assertEquals(true, isChronological);
	}
	
	@Test
	public void getPastMeetingList_validContact_returnNoDuplicates(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		for (int i = 0; i < numMeetings; i++){
			cm.addNewPastMeeting(contacts, PAST_TEST_DATE, TEST_MEETING_NOTES);
		}
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contactsArray[0]);
		Set<Integer> meetingIds = new TreeSet<Integer>();
		boolean hasDuplicates = false;
		for (Meeting meeting : returnedMeetings){
			int id = meeting.getId();
			if (meetingIds.contains(id)) hasDuplicates = true;
			else meetingIds.add(id);
		}
		assertEquals(false, hasDuplicates);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getPastMeetingList_nullContactParam_throwIllArgEx(){
		cm.getPastMeetingList(null);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getPastMeetingList_unknownContactParam_throwIllArgEx(){
		Set<Contact> contacts = getContactList(2);
		Contact[] contactsArray = (Contact[]) contacts.toArray();
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contactsArray[0]);
	}
	
	/*
	 * Contacts, notes, flush tests
	 */
	@Test
	public void addMeetingNotes_addNotesPastMeeting_getAddedNotesBack(){
		loadTestContacts();
		
		fail();
	}
	
	@Test
	public void addMeetingNotes_addNotesFutureMeeting_getPastMeetingBack(){
		loadTestContacts();
		
		fail();
	}
	
	@Test
	public void addMeetingNotes_nullNotesParam_throwNullPointerEx(){
		loadTestContacts();
		
		fail();
	}
	
	@Test
	public void addMeetingNotes_emptyNotesParam_throwIllArgEx(){
		loadTestContacts();
		
		fail();
	}
	
	@Test
	public void addMeetingNotes_dateInFuture_throwIllStateEx(){
		loadTestContacts();
		
		fail();
	}
	
	@Test
	public void getPastMeetingNotesThorwsIllegalArguementIfNotExist(){
		//get notes back
		fail();
	}
	
	@Test
	public void getPastMeetingNotesThrowsIllegalStateIfFuture(){
		//get notes back
		fail();
	}
	
	@Test
	public void getPastMeetingNotesThrowsNpIfNull(){
		//get notes back
		fail();
	}

	@Test
	public void getContactListByIds(){
		//get contact back
		fail();
	}
	
	@Test
	public void addContactThrowsNpIfNull(){
		fail();
	}
	
	@Test
	public void getContactListByName(){
		///get contacts back
		fail();
	}
	
	@Test
	public void getContactListIllegalArgIfAnyNotFound(){
		fail();
	}
	
	@Test
	public void flush(){
		//save and restore state
		fail();
	}
	
	
	/*
	 * Private helper methods
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
		if (numContacts > 0 && numContacts <= TestContacts.values().length ){
			TestContacts[] testContacts = TestContacts.values();
			contactList = new TreeSet<Contact>();
			for (int i = 0; i < numContacts; i++){
				contactList.add(new ContactImpl(testContacts[i].toString(), testContacts[i].getNotes()));
			}
		}
		return contactList;
	}
	
}