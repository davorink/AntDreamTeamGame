import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class GUIFlyingEffect extends GUIEffect {
	
	private int x, y;
	//private int stage; // current effect "stage"
	//private int noOfStages; // number of stages the effect has
	//private int[] stagetime; // if msec exceeds stagetime[stage], stage increases
	private float speed, radius, alpha;
	
	public GUIFlyingEffect(int xspeed) {
			speed = (float) (xspeed+Math.floor(Math.random()*xspeed*3));
			radius = (float) (2+Math.floor(Math.random()*6.5));
			alpha = (float) (Math.floor(Math.random()*100))/100;
			x = 0;
			y = (int)Math.floor(Math.random()*GUI.screenheight);
	}

	@Override
	public void render(Graphics g) {
		//System.out.println("rendering" + alpha);
		g.setColor(new Color(1f, 1f, 1f, alpha));
		g.fillOval(x-radius, y-radius, radius*2, radius*2);
		x+=speed;
		if(x>GUI.screenwidth) this.destroy();
	}

}
