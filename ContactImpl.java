/**
 * Implementation for Contact. <br />
 * Requires id to be specified in constructor.
 *
 * @author caleb
 *
 */
public class ContactImpl implements Contact {

	private int id;
	private String name;
	private String notes;
	/**
	 * Initializes new contact
	 * 
	 * @param id unique identifier
	 * @param name of contact
	 * @param notes associated with contact
	 */
	public ContactImpl(int id, String name, String notes) {
		this.id = id;
		this. name = name;
		this.notes = notes;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public int getId() {
		return id;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getName() {
		return name;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public String getNotes() {
		return notes;
	}

	/**
	 * {@inheritDoc} <br />
	 * Existing notes are not overwritten but new notes are instead added
	 * on a new line each time.
	 */
	@Override
	public void addNotes(String note) {
		notes = (notes.isEmpty()) ? note : String.format("%s\n%s", notes, note);	
	}

}
