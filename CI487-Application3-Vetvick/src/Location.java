
public class Location {
	private final String name;
	private final String info;

	Location (String name, String info) {
		this.name = name;
		this.info = info;
	}
	
	public String getName () {
		return name;
	}
	
	public String getInfo() {
		return info;
	}
	
	@Override
	public String toString() {
		return String.format("(%s:%s)", name, info);
	}
}
