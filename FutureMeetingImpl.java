import java.util.Calendar;
import java.util.Set;

/**
 * Implementation for Future Meeting using MeetingImpl as superclass.
 * @author caleb
 *
 */
public class FutureMeetingImpl extends MeetingImpl implements FutureMeeting {
	/**
	 * Constructor which calls superclass meeting.<br />
	 * 
	 * @param id to identify meeting
	 * @param date meeting is to be held
	 * @param contacts scheduled for meeting
	 */
	public FutureMeetingImpl(int id, Calendar date, Set<Contact> contacts) {
		super(id, date, contacts);
	}

}
