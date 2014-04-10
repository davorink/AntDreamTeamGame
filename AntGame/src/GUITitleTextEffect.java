import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GUITitleTextEffect extends GUIEffect {
	
	private boolean active;
	private int x, y, x2, y2, life, origlife, phase, origphase;
	private String filename;
	private Image image;
	
	public GUITitleTextEffect(String filename, int tx, int ty, int phase) {
		super(filename, tx, ty, phase);
		this.filename = filename;
		this.phase = phase;
		this.x=tx;
		this.y=ty;
		active=true;
		this.origphase = this.phase;
		this.life = this.origlife = 48;
		try {
			this.image = new Image(filename);
		} catch (SlickException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		x2 = x+image.getWidth();
		y2 = y+image.getHeight();
	}
	
	private float lifeScale(float number) {
		return (origlife-life)*(number/origlife);
	}

	@Override
	public void render(Graphics g) {
		//System.out.println(phase + " " + life);
		if(phase>0) phase--; else {
			if(life==0) { phase = 180; life = origlife; } else {
				g.drawImage(image, x-lifeScale(10), y-lifeScale(10), x2+lifeScale(10), y2+lifeScale(10), 0, 0, image.getWidth(), image.getHeight(), new Color(1f, 1f, 1f, (float)life/origlife));
				life--;
			}
		}
	}

}
