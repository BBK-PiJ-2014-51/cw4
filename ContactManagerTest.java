import static org.junit.Assert.assertEquals;
import org.junit.Test;

import java.time.Month;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

/**
 * Contact Manager Tests
 * @author caleb
 *
 * All test cases are named as such: Scope of test_Context tested_Expected Result<br />
 * e.g. computeTotal_nullParam_throwIllArgEx indicates testing a method named 
 * computeTotal with a null parameter, expecting an illegal argument exception. <br />
 * 
 * Tests are put in order of the method they test.<br />
 *  
 */
public class ContactManagerTest {

	private static final Calendar PAST_TEST_DATE = new GregorianCalendar(2015, Month.JANUARY.getValue(), 1, 12, 0);
	private static final Calendar FUTURE_TEST_DATE = new GregorianCalendar(2016, Month.JANUARY.getValue(), 1, 12, 0);
	private static final String TEST_MEETING_NOTES = "Some test meeting notes";
	
	private ContactManager cm = new ContactManagerImpl(false);

	/*
	 * getMeeting
	 */
	@Test
	public void getMeeting_validInput_returnMeeting() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		Meeting meeting = cm.getMeeting(cm.addFutureMeeting(contactList, FUTURE_TEST_DATE ));
		boolean equalMeetings =  (meeting.getDate().equals(FUTURE_TEST_DATE) 
				&& meeting.getContacts().equals(contactList) ); 
		assertEquals(true, equalMeetings);
	}
	
	@Test
	public void getMeeting_unfoundMeetingParam_returnNull() {
		loadTestContacts();
		Meeting unfoundMeeting = cm.getMeeting(1);
		Set<Contact> contacts = getContactList(TestContacts.values().length / 2);		
		Meeting foundMeeting = cm.getMeeting(cm.addFutureMeeting(contacts, FUTURE_TEST_DATE));
		assertEquals(true, unfoundMeeting == null && foundMeeting != null);
	}
	/*
	 * addFutureMeeting
	 */
	
	@Test (expected = IllegalArgumentException.class)
	public void addFutureMeeting_pastDateParam_throwIllArgEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		cm.addFutureMeeting(contactList, PAST_TEST_DATE );
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addFutureMeeting_unknownContactParam_throwIllArgEx() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		cm.addFutureMeeting(contactList, FUTURE_TEST_DATE );
	}
	
	/*
	 * getFutureMeeting
	 */
	@Test
	public void getFutureMeeting_validInput_returnMeeting() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		FutureMeeting meeting = cm.getFutureMeeting(cm.addFutureMeeting(contactList, FUTURE_TEST_DATE ));
		boolean equalMeetings =  (meeting.getDate().equals(FUTURE_TEST_DATE) 
				&& meeting.getContacts().equals(contactList) ); 
		assertEquals(true, equalMeetings);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getFutureMeeting_pastMeetingId_throwIllArgEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
		FutureMeeting meeting = cm.getFutureMeeting(0);
	}
	
	@Test
	public void getFutureMeeting_unfoundMeetingParam_returnNull() {
		loadTestContacts();
		FutureMeeting unfoundMeeting = cm.getFutureMeeting(1);
		Set<Contact> contacts = getContactList(TestContacts.values().length / 2);		
		FutureMeeting foundMeeting = cm.getFutureMeeting(cm.addFutureMeeting(contacts, FUTURE_TEST_DATE));
		assertEquals(true, unfoundMeeting == null && foundMeeting != null);
	}

	
	/*
	 * addNewPastMeeting
	 */
	@Test (expected = IllegalArgumentException.class)
	public void addNewPastMeeting_emptyContactParam_throwIllArgEx() {
		Set<Contact> contactList = new TreeSet<Contact>();
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addNewPastMeeting_unknownContactParam_throwIllArgEx() {
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addNewPastMeeting_nullFirstParam_throwNullPntrEx() {
		cm.addNewPastMeeting(null, PAST_TEST_DATE, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addNewPastMeeting_nullSecondParam_throwNullPntrEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, null, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addNewPastMeeting_nullThirdParam_throwNullPntrEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, null);
	}
	
	/*
	 * getPastMeeting
	 */
	@Test
	public void getPastMeeting_validInput_returnMeeting() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
	
		// get the only meeting from contact manager
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
		PastMeeting meeting = cm.getPastMeeting(0);
	
		boolean equalMeetings =  (meeting.getDate().equals(PAST_TEST_DATE) 
				&& meeting.getContacts().equals(contactList) 
				&& meeting.getNotes().equals(TEST_MEETING_NOTES)); 
		assertEquals(true, equalMeetings);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getPastMeeting_FutureIdParam_IllArgEx() {
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);		
		PastMeeting meeting = cm.getPastMeeting(cm.addFutureMeeting(contactList, FUTURE_TEST_DATE ));
	}
	
	@Test
	public void getPastMeeting_unfoundMeetingParam_returnNull() {
		loadTestContacts();
		PastMeeting unfoundMeeting = cm.getPastMeeting(1);
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		//get the first contact added
		Object[] contactArray = contactList.toArray();
		Contact firstContact = (Contact) contactArray[0]; 
		
		// get the only meeting from contact manager
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
		PastMeeting foundMeeting = cm.getPastMeeting(0);
		assertEquals(true, unfoundMeeting == null && foundMeeting != null);
	}
	/*
	 * getFutureMeetingList
	 */
	
	@Test
	public void getFutureMeetingList_validContactParam_returnList() {
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		Object[] contactsArray = contacts.toArray();
		Contact contact = (Contact) contactsArray[0];

		int numMeetings = 3;
		Set<Integer> meetingIds = new TreeSet<Integer>();
		for (int i = 0; i < numMeetings; i++){
			meetingIds.add(cm.addFutureMeeting(contacts, FUTURE_TEST_DATE));
		}

		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contact);
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
		
		Calendar addDate = FUTURE_TEST_DATE.getInstance();
		addDate.add(Calendar.DAY_OF_MONTH, 1);
		for (int i = 0; i < numMeetings; i++){
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
		Object[] contactsArray =  contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contact);
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
		Object[] contactsArray =  contacts.toArray(); // TODO shouldn't be needed
		Contact contact = (Contact) contactsArray[0];
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contact);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate()
					.after(returnedMeetings.get(i + 1).getDate())){
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
		Calendar addDate = (Calendar) FUTURE_TEST_DATE.clone();
		for (int i = 0; i < numMeetings; i++){		
			cm.addFutureMeeting(contacts, addDate);
			addDate = (Calendar) addDate.clone();
			addDate.add(Calendar.HOUR_OF_DAY, -2);
		}
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(FUTURE_TEST_DATE);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate()
					.after(returnedMeetings.get(i + 1).getDate())){
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
		Object[] contactsArray =  contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		List<Meeting> returnedMeetings = cm.getFutureMeetingList(contact);
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
			meetingIds.add(i);
			cm.addNewPastMeeting(contacts, PAST_TEST_DATE, TEST_MEETING_NOTES);
		}
		
		Calendar addDate = FUTURE_TEST_DATE;
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
		Set<Contact> contacts = getContactList(2);
		cm.getFutureMeetingList(contacts.iterator().next());
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
			if (returnedMeetings.get(i).getDate()
					.after(returnedMeetings.get(i + 1).getDate())){
				isChronological = false;
			}
		}
		assertEquals(true, isChronological);
	}
	
	/*
	 * getPastMeetingList
	 */
	
	@Test

	public void getPastMeetingList_validContactParam_returnList() {
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		
		int numMeetings = 3;
		Set<Integer> meetingIds = new TreeSet<Integer>();
		for (int i = 0; i < numMeetings; i++){
			cm.addNewPastMeeting(contacts, PAST_TEST_DATE, TEST_MEETING_NOTES);
		}
		Object[] contactsArray =  contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		contacts.remove(contact);
		for (int i = 0; i < numMeetings; i++){
			cm.addNewPastMeeting(contacts, PAST_TEST_DATE, TEST_MEETING_NOTES);
		}
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contact);
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
		Object[] contactsArray =  contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contact);
		assertEquals(true, returnedMeetings.isEmpty());
	}
	
	@Test
	public void getPastMeetingList_validContact_returnedChronologically(){
		loadTestContacts();
		Set<Contact> contacts = getContactList(5);
		int numMeetings = 3;
		Calendar addDate = (Calendar) PAST_TEST_DATE.clone();
		for (int i = 0; i < numMeetings; i++){
			cm.addNewPastMeeting(contacts, addDate, TEST_MEETING_NOTES);
			addDate = (Calendar) addDate.clone();
			addDate.add(Calendar.DAY_OF_MONTH, -1);
		}
		Object[] contactsArray =  contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contact);
		boolean isChronological = true;
		for (int i = 0; i < returnedMeetings.size() - 1; i++){
			if (returnedMeetings.get(i).getDate()
					.after(returnedMeetings.get(i + 1).getDate())){
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
		Object[] contactsArray = contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contact);
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
		Object[] contactsArray =  contacts.toArray();
		Contact contact = (Contact) contactsArray[0];
		List<PastMeeting> returnedMeetings = cm.getPastMeetingList(contact);
	}
	
	/*
	 * addMeetingNotes
	 */
	
	@Test
	public void addMeetingNotes_addNotesPastMeeting_getAddedNotesBack(){
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		// get the only meeting from contact manager
		cm.addNewPastMeeting(contactList, PAST_TEST_DATE, TEST_MEETING_NOTES);
		cm.addMeetingNotes(0, TEST_MEETING_NOTES);
		assertEquals(true, String.format("%s\n%s", TEST_MEETING_NOTES, TEST_MEETING_NOTES)
				.equals(cm.getPastMeeting(0).getNotes()));
	}
	
	@Test
	public void addMeetingNotes_addNotesFutureMeeting_getPastMeetingBack(){
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		Calendar now = Calendar.getInstance();
		now.add(Calendar.MILLISECOND, 10); //add a meeting slightly in the future
		int id = cm.addFutureMeeting(contactList, now);
		try {
			Thread.sleep(20); //wait a bit longer
		} catch (InterruptedException e) {
			e.printStackTrace();
		}
		cm.addMeetingNotes(id, TEST_MEETING_NOTES);
		PastMeeting meeting = cm.getPastMeeting(id);
		assertEquals(TEST_MEETING_NOTES, meeting.getNotes());
	}
	
	@Test (expected = NullPointerException.class)
	public void addMeetingNotes_nullNotesParam_throwNullPtrEx(){
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		int id = cm.addFutureMeeting(contactList, FUTURE_TEST_DATE);
		cm.addMeetingNotes(id, null);
	}
	
	
	@Test (expected = IllegalStateException.class)
	public void addMeetingNotes_dateInFuture_throwIllStateEx(){
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		int id = cm.addFutureMeeting(contactList, FUTURE_TEST_DATE);
		cm.addMeetingNotes(id, TEST_MEETING_NOTES);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void addMeetingNotes_meetingDoesntExist_throwsIllArgEx(){
		cm.addMeetingNotes(5, TEST_MEETING_NOTES);
	}
	
	/*
	 * addNewContact
	 */
	
	@Test (expected = NullPointerException.class)
	public void addNewContact_nullName_throwNullPtrEx(){
		cm.addNewContact(null, TEST_MEETING_NOTES);
	}
	
	@Test (expected = NullPointerException.class)
	public void addNewContact_nullNotes_throwNullPtrEx(){
		cm.addNewContact(TEST_MEETING_NOTES, null);
	}
	
	/*
	 * getContacts
	 */
	
	@Test
	public void getContacts_validIntIdVarArg_returnSet(){
		loadTestContacts();
		Set<Contact> contacts = cm.getContacts(0,1,2);
		assertEquals(3, contacts.size());
	} 
	@Test
	public void getContacts_commonNameSubstring_returnAsscContacts(){
		cm.addNewContact("Joe Johnson", TEST_MEETING_NOTES);
		cm.addNewContact("Joe Jacobson", TEST_MEETING_NOTES);
		Set<Contact> contacts = cm.getContacts("Joe");
		assertEquals (2, contacts.size());
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getContacts_idNotFound_IllArgEx(){
		Set<Contact> contacts = cm.getContacts(0,1,2);
	}
	
	@Test (expected = IllegalArgumentException.class)
	public void getContacts_nameNotFound_IllArgEx(){
		Set<Contact> contacts = cm.getContacts("Joe");
	}
	
	@Test
	public void getContacts_emptyString_returnAllContacts(){
		loadTestContacts();
		Set<Contact> contacts = cm.getContacts("");
		assertEquals(TestContacts.values().length, contacts.size());
	}
	
	/*
	 * flush
	 */
	
	@Test
	public void flush_addContacts_restoreNumContacts(){
		loadTestContacts();
		
		cm.flush();
		cm = new ContactManagerImpl(true);
		Set<Contact> contactList = getContactList(TestContacts.values().length);
		
		boolean contactsPresent = true;
		int hasChecked = 0;
		for (Contact contact : contactList){
			try{
				cm.getContacts(contact.getId());
			} catch (IllegalArgumentException ex){
				contactsPresent = false;
			}
			hasChecked++;
		}
		
		assertEquals(true, contactsPresent && hasChecked == TestContacts.values().length);
	}
	
	@Test
	public void flush_addPastMeetings_restoreNumPastMeetings(){
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		
		Calendar addDate = PAST_TEST_DATE;
		int numMeetings = 3;
		for (int i = 0; i < numMeetings; i++){
			addDate = (Calendar) addDate.clone();
			addDate.add(Calendar.DAY_OF_YEAR, 2);
			cm.addNewPastMeeting(contactList, addDate, TEST_MEETING_NOTES);			
		}
		
		cm.flush();
		cm = new ContactManagerImpl(true);
		
		boolean meetingsPresent = true;
		int hasChecked = 0;
		for (int i = 0; i < numMeetings; i++){
			try{			
				PastMeeting meeting = cm.getPastMeeting(i);
				if (meeting == null) meetingsPresent = false;
			} catch (IllegalArgumentException ex){
				meetingsPresent = false;
			}
			hasChecked++;
		}
		
		assertEquals(true, meetingsPresent && hasChecked == numMeetings);
	}
	
	@Test
	public void flush_addFutureMeetings_restoreNumFutureMeetings(){
		loadTestContacts();
		Set<Contact> contactList = getContactList(TestContacts.values().length / 2);
		
		Calendar addDate = FUTURE_TEST_DATE;
		int numMeetings = 3;		
		for (int i = 0; i < numMeetings; i++){
			addDate = (Calendar) addDate.clone();
			addDate.add(Calendar.DAY_OF_YEAR, 2);			
			cm.addFutureMeeting(contactList, addDate);			
		}
		
		cm.flush();
		cm = new ContactManagerImpl(true);
		
		boolean meetingsPresent = true;
		int hasChecked = 0;
		for (int i = 0; i < numMeetings; i++){
			try{			
				FutureMeeting meeting = cm.getFutureMeeting(i);
				if (meeting == null) meetingsPresent = false;
			} catch (IllegalArgumentException ex){
				meetingsPresent = false;
			}
			hasChecked++;
		}
		
		assertEquals(true, meetingsPresent && hasChecked == numMeetings);
	}
	
	/*
	 * Private helper methods
	 */
	
	/**
	 * loads some contact name and notes from TestContacts enum class into contact manager instance
	 */
	private void loadTestContacts() {
		TestContacts[] testContacts = TestContacts.values();
		
		for (int i = 0; i < testContacts.length; i++){
			cm.addNewContact(testContacts[i].toString(), testContacts[i].getNotes());
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
			contactList = new HashSet<Contact>();
			for (int i = 0; i < numContacts; i++){
				contactList.add(new ContactImpl(i, testContacts[i].toString(), testContacts[i].getNotes()));
			}
		}
		return contactList;
	}
	
}