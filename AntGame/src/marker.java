/**
 * marker.java represents a chemical marker 
 * @author Kornelia Ratusznik
 * @version 31/3/2014
 */
public class marker {
	private boolean[]markers;
	public marker(boolean[]markers){
		if(markers.length==6)this.markers = markers;
		else throw new Exception("Marker length is wrong");
	}
	
	/**
	 * Construct an object.
	 */
	public marker(){
		this.markers = new boolean[6];
	}
	
	/**
	 * Get Markers.
	 * @return markers
	 */
	public boolean[] getMarkers() {
		return markers;
	}
	
	/**
	 * Set Markers.
	 * @param markers Boolean array of markers
	 */
	public void setMarkers(boolean[] markers) {
		if(markers.length==6)this.markers = markers;
		else throw new Exception("Marker length is wrong");
	}
	
	/**
	 * Get marker from a specific index.
	 * @param index Index to check
	 */
	public boolean getMarker(int index){
		if(index >= 0 && index <= 5) return markers[index];
		else throw new Exception("Marker out of bounds");
	}
	
	/**
	 * Set a marker at a specific index.
	 * @param index Index at which to insert new marker
	 * @param newMarker New boolean value of a marker
	 */
	public void setMarker(int index, boolean newMarker){
		if(index >= 0 && index <= 5) markers[index] = newMarker;
		else throw new Exception("Marker out of bounds");
	}
}