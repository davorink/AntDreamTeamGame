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
	 * @throws PosException If x or y negative
	 */
	public Pos(int x, int y) throws PosException{
		if(x<0 || y<0){
			throw new PosException("Position (x or y) can not be negative!");
		}
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
	 * @throws PosException  If negative x
	 */
	public void setX(int x) throws PosException {
		if(x<0){
			throw new PosException("Position (x) can not be negative!");
		}
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
	 * @throws PosException If negative y
	 */
	public void setY(int y) throws PosException {
		if(y<0){
			throw new PosException("Position (y) can not be negative!");
		}
		this.y = y;
	}
}

/**
 * Class WorldException is used to report exceptions in the World
 * @author D Kopic
 */
@SuppressWarnings("serial")
class PosException extends WorldException {
	public String msg;
    
    public PosException(String _msg){
        super(_msg);
        msg = _msg;
    }

}