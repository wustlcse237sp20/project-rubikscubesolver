package cube;

public class Facelet {

	Color color;
	String location;
	boolean displayLocation;
	
	public Facelet(Color color, String location, boolean displayLocation) {
		this.color = color;
		this.location = location;
		this.displayLocation = displayLocation;
	}
	
	public Facelet(Facelet facelet) {
		this.color = facelet.color;
		this.location = facelet.location;
		this.displayLocation = facelet.displayLocation;
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
	
	public void setDisplayLocation(boolean displayLocation) {
		this.displayLocation = displayLocation;
	}
	
	public boolean isDisplayLocation() {
		return this.displayLocation;
	}
	
	public String toString() {
		if(this.displayLocation){
			return this.location;
		}
		return this.color.toString();
	} 

}
