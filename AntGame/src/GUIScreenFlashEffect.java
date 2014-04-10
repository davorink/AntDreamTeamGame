

import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class GUIScreenFlashEffect extends GUIEffect {
	
	private boolean active;
	private int life, origlife;
	
	public GUIScreenFlashEffect() {
		this.life = this.origlife = 6;
	}
	
	public GUIScreenFlashEffect(int xlife) {
		this.life = this.origlife = xlife;
	}

	@Override
	public void render(Graphics g) {
		//System.out.println("rendering");
		g.setColor(new Color(1f, 1f, 1f, (float)life/origlife));
			g.fillRect(0, 0, GUI.screenwidth, GUI.screenheight);
		life--;
		if(life<1) this.destroy();
	}

}
