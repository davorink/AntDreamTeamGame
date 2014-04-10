

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;
import org.newdawn.slick.Image;
import org.newdawn.slick.SlickException;

public class GUIImageButton extends GUIButton {
	
	private int x, y, w, h; // position
	private Image base, over; // label
	
	public GUIImageButton(int x, int y, String filenamebase, String filenameover) throws SlickException {
		super();
		this.x = x;
		this.y = y;
		this.base = new Image(filenamebase);
		this.over = new Image(filenameover);
		this.w = base.getWidth();
		this.h= base.getHeight();
	}
	
	public void render(Graphics g) {
		int mouseX = Mouse.getX();
		int mouseY = GUI.screenheight - Mouse.getY();
		g.setColor(new Color(1f, 1f, 1f, 1f));
		if((mouseX>=x&&mouseX<=x+w)&&(mouseY>=y&&mouseY<=y+h)) {
			g.drawImage(over, x, y);
		} else g.drawImage(base, x, y);
		
	}
	
	public boolean overButton() {
		int mouseX = Mouse.getX();
		int mouseY = GUI.screenheight - Mouse.getY();
		return ((mouseX>=x&&mouseX<=x+w)&&(mouseY>=y&&mouseY<=y+h));
	}

}
