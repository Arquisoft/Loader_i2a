package main.asw.user;

public class GeoCords {
	private double lat;
	private double lng;
	
	public GeoCords(double lat, double lng) {
		super();
		this.lat = lat;
		this.lng = lng;
	}

	public double getLat() {
		return lat;
	}

	public double getLng() {
		return lng;
	}

	@Override
	public String toString() {
		return "GeoCords [lat=" + lat + ", lng=" + lng + "]";
	}
	
	
}
