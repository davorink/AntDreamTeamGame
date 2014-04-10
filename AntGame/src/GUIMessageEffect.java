import org.newdawn.slick.Graphics;
import org.newdawn.slick.Color;

public class GUIMessageEffect extends GUIEffect {
	
	private boolean active;
	private String message;
	private int life, x, y;
	
	public GUIMessageEffect(String xmessage) {
		super();
		message=xmessage;
		this.x = (GUI.screenwidth/2)-((message.length()*9)/2);
		this.y = (int) (GUI.screenheight*0.75);
		this.life = 36;
	}

	@Override
	public void render(Graphics g) {
		//System.out.println("rendering");
		g.setColor(new Color(1f, 1f, 1f, (float)life/24));
		g.drawString(message, x, y);
		y-=2;
		life--;
		if(life<1) this.destroy();
	}

}