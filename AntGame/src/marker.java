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
	public marker(){
		this.markers = new boolean[6];
	}
	public boolean[] getMarkers() {
		return markers;
	}
	public void setMarkers(boolean[] markers) {
		if(markers.length==6)this.markers = markers;
		else throw new Exception("Marker length is wrong");
	}
	
	public boolean getMarker(int index){
		if(index >= 0 && index <= 5) return markers[index];
		else throw new Exception("Marker out of bounds");
	}
	
	public void setMarker(int index, boolean newMarker){
		if(index >= 0 && index <= 5) markers[index] = newMarker;
		else throw new Exception("Marker out of bounds");
	}
	
	
}