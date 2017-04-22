package BlueShift.menu;

import BlueShift.Main;
import processing.core.PConstants;

import java.awt.*;

public class Button {
	private Main main;
	private Rectangle bounds;
	private String text;
	private Runnable onClick;
	private Color base;
	private Color darker;
	boolean mouseInside;

	public Button(String text, Color color, Runnable onClick) {
		main = Main.instance;
		this.text = text;
		this.onClick = onClick;
		this.base = color;
		darker = base.darker();
	}

	public void draw(float x, float y, float height) {
		bounds = new Rectangle((int) (x + height/10), (int) (y + height/10), ((int) (main.width - (height / 5))), (int) (4*(height/5)));
		main.fill(mouseInside?darker.getRGB():base.getRGB());
		main.rect(bounds.x, bounds.y, bounds.width, bounds.height, 20);
		main.textAlign(PConstants.CENTER, PConstants.CENTER);
		main.textSize(40);
		main.fill(255);
		main.text(text, bounds.x + (bounds.width/2), bounds.y + (bounds.height/2));
	}

	void mousePressed() {
		onClick.run();
	}


	boolean inside(float x, float y) {
		return bounds.contains(x,y);
	}
}
