package impl;

import java.io.File;
import java.io.IOException;
import java.util.Calendar;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NamedNodeMap;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerConfigurationException;
import javax.xml.transform.TransformerException;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.TransformerFactoryConfigurationError;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import interfaces.Contact;
import interfaces.ContactManager;
import interfaces.FutureMeeting;
import interfaces.Meeting;
import interfaces.PastMeeting;

public class ContactManagerImpl implements ContactManager {
	
	private static final String XML_DATA_FILENAME = "data.xml";	
	
	private Set<Contact> contacts;
	private Set<PastMeeting> pastMeetings;
	private Set<FutureMeeting> futureMeetings;
	private int currentMeetingId;
	private int currentContactId;
	
	public ContactManagerImpl(boolean restoreData) {
		setInitAttributes();
		File xmlData = new File(XML_DATA_FILENAME);
		if (restoreData && xmlData.exists()) restoreData(xmlData);		
	}
	
	public ContactManagerImpl(){
		setInitAttributes();
		File xmlData = new File(XML_DATA_FILENAME);
		if (xmlData.exists()) restoreData(xmlData);
	}
	
	private void setInitAttributes(){
		contacts = new HashSet<Contact>();
		pastMeetings = new HashSet<PastMeeting>();
		futureMeetings = new HashSet<FutureMeeting>();
		currentMeetingId = 0;
		currentContactId = 0;		
	}
	
	private boolean restoreData(File xmlData){
		
		DocumentBuilder builder = null;
		try {
			builder = DocumentBuilderFactory.newInstance().newDocumentBuilder();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Document doc = null;
		try {
			doc = builder.parse(xmlData);
		} catch (SAXException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
		NodeList contactNodes = doc.getElementsByTagName("contact");
		for (int i = 0; i < contactNodes.getLength(); i++){
			Node currentNode = contactNodes.item(i);
			NamedNodeMap attributes = currentNode.getAttributes();
			String id = attributes.getNamedItem("id").getNodeValue();
			String name = attributes.getNamedItem("name").getNodeValue();
			String notes = attributes.getNamedItem("notes").getNodeValue();
			contacts.add(new ContactImpl(Integer.parseInt(id), name, notes));
			currentContactId++;
			//System.out.println(id + ", " + name + ", " + notes);
		}
		
		NodeList pastMeetingNodes = doc.getElementsByTagName("past_meeting");
		for (int i = 0; i < pastMeetingNodes.getLength(); i++){
			Node currentNode = pastMeetingNodes.item(i);
			NamedNodeMap attributes = currentNode.getAttributes();
			
			String id = attributes.getNamedItem("id").getNodeValue();
			
			String dateInMs = attributes.getNamedItem("date").getNodeValue();
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(Long.parseLong(dateInMs));
			
			String[] contactList = attributes.getNamedItem("contact_id_list")
					.getNodeValue().split(", ");
			int[] contactIdList = new int[contactList.length];
			for (int j = 0; j < contactList.length; j++){
				contactIdList[j] = Integer.parseInt(contactList[j]);	
			}
	
			String notes = attributes.getNamedItem("notes").getNodeValue();
			
			pastMeetings.add(new PastMeetingImpl(Integer.parseInt(id), date, 
					getContacts(contactIdList), notes));
			currentMeetingId++;
		}
		
		NodeList futureMeetingNodes = doc.getElementsByTagName("future_meeting");
		for (int i = 0; i < futureMeetingNodes.getLength(); i++){
			Node currentNode = futureMeetingNodes.item(i);
			NamedNodeMap attributes = currentNode.getAttributes();
			
			String id = attributes.getNamedItem("id").getNodeValue();
			
			String dateInMs = attributes.getNamedItem("date").getNodeValue();
			Calendar date = Calendar.getInstance();
			date.setTimeInMillis(Long.parseLong(dateInMs));
			
			String[] contactList = attributes.getNamedItem("contact_id_list")
					.getNodeValue().split(", ");
			int[] contactIdList = new int[contactList.length];
			for (int j = 0; j < contactList.length; j++){
				contactIdList[j] = Integer.parseInt(contactList[j]);	
			}
				
			futureMeetings.add(new FutureMeetingImpl(Integer.parseInt(id), date, 
					getContacts(contactIdList)));
			currentMeetingId++;
		}
		
		return true;
	}

	@Override
	public int addFutureMeeting(Set<Contact> contacts, Calendar date) {
		if (contacts == null || date == null) 
			throw new NullPointerException("Null parameter!");
		if (contacts.isEmpty()) 
			throw new IllegalArgumentException("Must supply contacts!");
		if (date.before(Calendar.getInstance())) 
			throw new IllegalArgumentException("Date is in the past already!");
		
		for (Contact contact : contacts){
			if (!isInDb(contact)) 
				throw new IllegalArgumentException("Unknown Contact!");
		}
		futureMeetings.add(new FutureMeetingImpl(currentMeetingId, (Calendar) date.clone(), contacts));
		
		return currentMeetingId++;
	}

	@Override
	public PastMeeting getPastMeeting(int id) {
		for (PastMeeting meeting : pastMeetings){
			if (meeting.getId() == id) 
				return meeting;
		}
		for (FutureMeeting meeting : futureMeetings){
			if (meeting.getId() == id) 
				throw new IllegalArgumentException("Meeting ID represents future meeting!");
		}
		
		return null;
	}

	@Override
	public FutureMeeting getFutureMeeting(int id) {
		for (FutureMeeting meeting : futureMeetings){
			if (meeting.getId() == id) 
				return meeting;
		}
		for (PastMeeting meeting : pastMeetings){
			if (meeting.getId() == id)
				throw new IllegalArgumentException("Meeting ID represents past meeting!");
		}
		
		return null;
	}

	@Override
	public Meeting getMeeting(int id) {
		for (Meeting meeting : futureMeetings){
			if (meeting.getId() == id) 
				return meeting;
		}

		for (Meeting meeting : pastMeetings){
			if (meeting.getId() == id) 
				return meeting;
		}
		
		return null;
	}

	@Override
	public List<Meeting> getFutureMeetingList(Contact contact) {
		if (contact == null) 
			throw new IllegalArgumentException("Null contact provided!");
		if (!isInDb (contact)) 
			throw new IllegalArgumentException("Contact does not exist!");
		List<Meeting> meetings = new LinkedList<Meeting>();
		
		for (Meeting meeting : futureMeetings)
			for (Contact member : meeting.getContacts())
				if (member.getId() == contact.getId())
					meetings.add(meeting);
		
		return meetings.stream()
				.sorted((meeting, nextMeeting) -> meeting.getDate().compareTo(nextMeeting.getDate()))
				.collect(Collectors.toList());
	}

	@Override
	public List<Meeting> getFutureMeetingList(Calendar date) {
		if (date == null) 
			throw new IllegalArgumentException("Null date provided!");
		List<Meeting> meetings = new LinkedList<Meeting>();
		int year = date.get(Calendar.YEAR);
		int day = date.get(Calendar.DAY_OF_YEAR);
		
		for (Meeting meeting : futureMeetings){
			if (meeting.getDate().get(Calendar.YEAR) == year
					&& meeting.getDate().get(Calendar.DAY_OF_YEAR) == day)
				meetings.add(meeting);
		}
		for (Meeting meeting : pastMeetings){
			if (meeting.getDate().get(Calendar.YEAR) == year
					&& meeting.getDate().get(Calendar.DAY_OF_YEAR) == day)
				meetings.add(meeting);
		}
		
		return meetings.stream()
				.sorted((meeting, nextMeeting) -> meeting.getDate().compareTo(nextMeeting.getDate()))
				.collect(Collectors.toList());
	}

	@Override
	public List<PastMeeting> getPastMeetingList(Contact contact) {
		if (!isInDb (contact)) 
			throw new IllegalArgumentException("Contact does not exist!");
		List<PastMeeting> meetings = new LinkedList<PastMeeting>();
		
		for (PastMeeting meeting : pastMeetings){
			for (Contact member : meeting.getContacts()){
				if (member.getId() == contact.getId())
					meetings.add(meeting);
			}
		}
		
		return meetings.stream()
				.sorted((meeting, nextMeeting) -> meeting.getDate().compareTo(nextMeeting.getDate()))
				.collect(Collectors.toList());
	}

	@Override
	public void addNewPastMeeting(Set<Contact> contacts, Calendar date,
			String text) {
		if (contacts == null || date == null || text == null) 
			throw new NullPointerException("Null parameter!");
		if (contacts.isEmpty()) 
			throw new IllegalArgumentException("Must supply contacts!");
		
		for (Contact contact : contacts){
			if (!isInDb(contact)) 
				throw new IllegalArgumentException("Unknown Contact!");
		}
		
		pastMeetings.add(new PastMeetingImpl(currentMeetingId++, (Calendar) date.clone(), contacts, text));
	}

	private boolean isInDb(Contact contact) {
		for (Contact conFromDb : contacts){
			if (contact.getId() == conFromDb.getId()) return true;
		}
		
		return false;
	}

	@Override
	public void addMeetingNotes(int id, String text) {
		if (text == null) throw new NullPointerException("Notes are null!");		
		
		for (PastMeeting meeting : pastMeetings){
			if (meeting.getId() == id) {
				pastMeetings.add(new PastMeetingImpl(id, meeting.getDate(),
						meeting.getContacts(), String.format("%s\n%s", meeting.getNotes(), text)));
				pastMeetings.remove(meeting);
				return;
			}
		}
		for (FutureMeeting meeting : futureMeetings){
			if (meeting.getId() == id){
				if (meeting.getDate().after(Calendar.getInstance())) 
					throw new IllegalStateException("Meeting is still scheduled for the future!");
				pastMeetings.add(new PastMeetingImpl(id, meeting.getDate(), 
						meeting.getContacts(), text));
				futureMeetings.remove(meeting);
				return;
			}
		}
		
		throw new IllegalArgumentException("ID does not represent meeting!");		
	}

	@Override
	public void addNewContact(String name, String notes) {
		if (name == null || notes == null) 
			throw new NullPointerException("Neither name nor notes may be null!");	
		contacts.add(new ContactImpl(currentContactId++, name, notes));
	}

	@Override
	public Set<Contact> getContacts(int... ids) {
		Set<Contact> foundContacts = new HashSet<Contact>();
		
		for (Contact contact : contacts){
			for (int id : ids){
				if (contact.getId() == id) foundContacts.add(contact);
			}
		}
		
		if (foundContacts.isEmpty()) 
			throw new IllegalArgumentException("Id not found!");
		return foundContacts;
	}

	@Override
	public Set<Contact> getContacts(String name) {
		Set<Contact> foundContacts = new HashSet<Contact>();
		
		for (Contact contact : contacts){
			if (contact.getName().contains(name)) foundContacts.add(contact);
		}
		
		if (foundContacts.isEmpty()) 
			throw new IllegalArgumentException("Name not found!");
		return foundContacts;
	}

	@Override
	public void flush() {		
		DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
		DocumentBuilder builder = null;
		Document doc = null;
		
		try {
			builder = factory.newDocumentBuilder();
			doc = builder.newDocument();
		} catch (ParserConfigurationException e) {
			e.printStackTrace();
		}
		
		Element rootElem = doc.createElement("contact_manager_data");
		doc.appendChild(rootElem);
		
		Element contactsElem = doc.createElement("contacts");
		rootElem.appendChild(contactsElem);
		for (Contact contact : contacts){
			Element contactElem = doc.createElement("contact");
			contactsElem.appendChild(contactElem);
			contactElem.setAttribute("id", String.valueOf(contact.getId()));
			contactElem.setAttribute("name", contact.getName());
			contactElem.setAttribute("notes", contact.getNotes());
		}
		
		Element pastMeetingsElem = doc.createElement("past_meetings");
		rootElem.appendChild(pastMeetingsElem);
		for (PastMeeting meeting : pastMeetings){
			Element pastMeetingElem = doc.createElement("past_meeting");
			pastMeetingsElem.appendChild(pastMeetingElem);
			
			pastMeetingElem.setAttribute("id", String.valueOf(meeting.getId()));
			pastMeetingElem.setAttribute("date", String.valueOf(meeting.getDate().getTimeInMillis()));
			pastMeetingElem.setAttribute("notes", meeting.getNotes());
			
			String contactList = "";
			for (Contact contact : meeting.getContacts()){
				contactList += contact.getId() + ", ";
			}
			contactList = contactList.substring(0, contactList.length() - 2);
			pastMeetingElem.setAttribute("contact_id_list", contactList);			
		}
		
		Element futureMeetingsElem = doc.createElement("future_meetings");
		rootElem.appendChild(futureMeetingsElem);
		for (FutureMeeting meeting : futureMeetings){
			Element futureMeetingElem = doc.createElement("future_meeting");
			futureMeetingsElem.appendChild(futureMeetingElem);
			
			futureMeetingElem.setAttribute("id", String.valueOf(meeting.getId()));
			futureMeetingElem.setAttribute("date", String.valueOf(meeting.getDate().getTimeInMillis()));
			
			String contactList = "";
			for (Contact contact : meeting.getContacts()){
				contactList += contact.getId() + ", ";
			}
			contactList = contactList.substring(0, contactList.length() - 2);
			futureMeetingElem.setAttribute("contact_id_list", contactList);			
		}
		
		Transformer transformer = null;
		try {
			transformer = TransformerFactory.newInstance().newTransformer();
		} catch (TransformerConfigurationException e) {
			e.printStackTrace();
		} catch (TransformerFactoryConfigurationError e) {
			e.printStackTrace();
		}
		
		DOMSource src = new DOMSource(doc);
		StreamResult stream = new StreamResult(new File(XML_DATA_FILENAME));
		
		try {
			transformer.transform(src, stream);
		} catch (TransformerException e) {
			e.printStackTrace();
		}
		
	}
}