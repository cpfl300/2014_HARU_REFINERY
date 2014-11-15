package refinery.model;

public class Section {
	
	private long id;
	private String major;
	private String minor;
	
	public Section() {
	}
	
	public Section(String minor) {
		this(0, null, minor);
	}

	public Section(long id) {
		this(id, null, null);
	}
	
	public Section(String major, String minor) {
		this(0, major, minor);
	}
	
	public Section (long id, String major, String minor) {
		this.id = id;
		this.major = major;
		this.minor = minor;
	}


	public long getId() {
		return id;
	}

	public void setId(long id) {
		this.id = id;
	}

	public String getMajor() {
		return major;
	}

	public void setMajor(String major) {
		this.major = major;
	}

	public String getMinor() {
		return minor;
	}

	public void setMinor(String minor) {
		this.minor = minor;
	}

	@Override
	public String toString() {
		return "Section [id=" + id + ", major=" + major + ", minor=" + minor + "]";
	}
	


}
