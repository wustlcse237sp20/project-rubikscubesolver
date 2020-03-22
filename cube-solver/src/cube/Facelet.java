package cube;

public class Facelet {

	Color color;
	String location;
	
	public Facelet(Color color, String location) {
		this.color = color;
		this.location = location;
		
	}
	
	public Color getColor() {
		return color;
	}

	public void setColor(Color color) {
		this.color = color;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}
	
	public String toString() {
		return this.location;
	}

}
