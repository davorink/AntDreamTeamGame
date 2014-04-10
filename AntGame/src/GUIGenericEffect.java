

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class GUIGenericEffect extends GUIEffect {
	
	private int x, y;
	private int time; // time since creation in frames (lol_
	//private int stage; // current effect "stage"
	//private int noOfStages; // number of stages the effect has
	//private int[] stagetime; // if msec exceeds stagetime[stage], stage increases
	private boolean active;
	private int life, origlife;
	private int[] circleX, circleY, speedX, speedY;
	
	public GUIGenericEffect(int tx, int ty) {
		super(tx, ty);
		this.x = tx;
		this.y = ty;
		this.life = this.origlife = 60;
		this.circleX = new int[10];
		this.circleY = new int[10];
		this.speedX = new int[10];
		this.speedY = new int[10];
		for(int i=0; i<10; i++) {
			circleX[i] = circleY[i] = 0;
			speedX[i] = (int) ((-10)+Math.floor(Math.random()*21));
			speedY[i] = (int) ((-10)+Math.floor(Math.random()*21));
		}
	}

	@Override
	public void render(Graphics g) {
		//System.out.println("rendering");
		g.setColor(new Color(1f, 1f, 1f, (float)life/origlife));
		for(int i=0; i<10; i++) {
			g.fillOval(x+circleX[i]-4, y+circleY[i]-4, 8, 8);
			circleX[i]+=speedX[i];
			circleY[i]+=speedY[i];
		}
		life--;
		if(life<1) this.destroy();
	}

}
