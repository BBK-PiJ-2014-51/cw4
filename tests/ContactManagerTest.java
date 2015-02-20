package tests;

import static org.junit.Assert.*;
import interfaces.ContactManager;

import org.junit.Before;
import org.junit.Test;

public class ContactManagerTest {

	private ContactManager cm = new ContactManagerImpl();

	/*
	 Add / get future and past meetings
	 */
	
	@Test
	public void getMeeting() {
		//to do add meeting and get it back
	}
	
	@Test
	public void getValidFutureMeeting() {
		//to do add meeting and get it back
	}
	
	@Test
	public void getValidPastMeeting() {
		//to do add meeting and get it back
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
}