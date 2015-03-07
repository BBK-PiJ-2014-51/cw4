package impl;

import java.util.Calendar;
import java.util.Set;

import interfaces.Contact;
import interfaces.FutureMeeting;

public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {

	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		super(id, date, contacts);
	}

}
