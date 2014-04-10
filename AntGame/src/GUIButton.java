

import org.lwjgl.input.Mouse;
import org.newdawn.slick.Color;
import org.newdawn.slick.Graphics;

public class GUIButton {
	
	private int x, y, w, h; // position
	private String label; // label
	
	public GUIButton(int x, int y, String label) {
		this.x = x;
		this.y = y;
		this.label = label;
		this.w = 16+(label.length()*9);
		this.h=32;
	}
	
	public GUIButton(){}
	
	public void render(Graphics g) {
		int mouseX = Mouse.getX();
		int mouseY = GUI.screenheight - Mouse.getY();
		if((mouseX>=x&&mouseX<=x+w)&&(mouseY>=y&&mouseY<=y+h)) {
			g.setColor(new Color(0f, 1f, 1f, 1f));
		} else g.setColor(new Color(0f, 0f, 1f, 0.9f));
		g.fillRect(x, y, w, h);
		g.setColor(new Color(1f, 1f, 1f, 1f));
		g.drawString(label, x+8, y+8);
	}
	
	public boolean overButton() {
		int mouseX = Mouse.getX();
		int mouseY = GUI.screenheight - Mouse.getY();
		return ((mouseX>=x&&mouseX<=x+w)&&(mouseY>=y&&mouseY<=y+h));
	}

}
