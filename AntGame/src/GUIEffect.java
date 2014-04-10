

import org.newdawn.slick.Graphics;

public abstract class GUIEffect {
	
	private int x, y;
	//private int time; // time since creation in milliseconds
	//private int stage; // current effect "stage"
	//private int noOfStages; // number of stages the effect has
	//private int[] stagetime; // if msec exceeds stagetime[stage], stage increases
	private boolean active;
	private String filename;
	private int phase;
	
	public GUIEffect(int tx, int ty) {
		x=tx; y=ty;
		active=true;
	}
	
	public GUIEffect() {
		active=true;
	}
	
	public GUIEffect(String filename, int tx, int ty, int phase) {
		this.filename = filename;
		this.phase = phase;
		this.x=tx;
		this.y=ty;
		active=true;
	}
	
	public void destroy() {
		active=false;
	}
	
	public boolean isActive() { return active; }
	
	public abstract void render(Graphics g);
}
