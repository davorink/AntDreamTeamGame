import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class GUITitleStarEffect extends GUIEffect {
	
	private int x=GUI.screenwidth/2, y=GUI.screenheight/2;
	private int time; // time since creation in frames (lol_
	//private int stage; // current effect "stage"
	//private int noOfStages; // number of stages the effect has
	//private int[] stagetime; // if msec exceeds stagetime[stage], stage increases
	private boolean active;
	private int life, origlife;
	private float speedX, speedY;
	
	public GUITitleStarEffect() {
			speedX = (int) ((-10)+Math.floor(Math.random()*21));
			speedY = (int) ((-10)+Math.floor(Math.random()*21));
			if(speedX==0 || speedY==0) this.destroy(); else this.active=true;
	}

	@Override
	public void render(Graphics g) {
		//System.out.println("rendering");
		g.setColor(new Color(1f, 1f, 1f, Math.min(1f,(float)life/20f)));
		for(int i=0; i<10; i++) {
			g.fillOval(x-4, y-4, 8, 8);
			x+=speedX;
			y+=speedY;
		}
		life++;
		if(x<0||x>GUI.screenwidth||y<0||y>GUI.screenheight) this.destroy();
	}

}
