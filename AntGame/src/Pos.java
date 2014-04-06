/**
 * Represents a position in the World.
 * @author D Kopic
 */
public class Pos {
	private int x;
	private int y;
	
	/**
	 * Constructor
	 * @param x X coordinate
	 * @param y Y coordinate
	 */
	public Pos(int x, int y){
		this.x = x;
		this.y = y;
	}
	
	/**
	 * Returns x coordinate.
	 */
	public int getX() {
		return x;
	}
	
	/**
	 * Set x coordinate.
	 * @param x
	 */
	public void setX(int x) {
		this.x = x;
	}
	
	/**
	 * Returns y coordinate.
	 */
	public int getY() {
		return y;
	}
	
	/**
	 * Set y coordinate.
	 * @param y
	 */
	public void setY(int y) {
		this.y = y;
	}
}
