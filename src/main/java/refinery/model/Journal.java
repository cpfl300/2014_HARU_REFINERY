package refinery.model;

public class Journal {
	
	private long id;
	private String name;
	private String section;
	
	public Journal() {
	}

	public Journal(String name) {
		this(0, name, null);
	}
	
	public Journal(long id) {
		this(id, null, null);
	}

	public Journal(String name, String section) {
		this(0, name, section);
	}
	
	public Journal(long id, String name, String section) {
		this.id = id;
		this.name = name;
		this.section = section;
	}
	

	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getSection() {
		return section;
	}

	public void setSection(String section) {
		this.section = section;
	}

	@Override
	public String toString() {
		return "Journal [id=" + id + ", name=" + name + ", section=" + section + "]";
	}
	
	
	
}
