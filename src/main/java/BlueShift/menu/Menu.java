package BlueShift.menu;

import BlueShift.Main;
import processing.core.PConstants;

public class Menu {
	private Main main;
	private String title;
	private Button[] buttons;
	public Menu(String title, Button... buttons) {
		main = Main.instance;
		this.title = title;
		this.buttons = buttons;
	}

	public void draw() {
		main.textAlign(PConstants.CENTER);
		float buttonHeight = (main.height/buttons.length) - (buttons.length*5);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].draw(0, (buttonHeight + 5)*i, buttonHeight);
		}
	}

	public void onClick(float mouseX, float mouseY) {
		for (Button button : buttons) {
			if(button.inside(mouseX, mouseY)) button.mousePressed();
		}
	}

	public void onMouseMove(float mouseX, float mouseY) {
		for (Button button : buttons) {
			button.mouseInside = button.inside(mouseX, mouseY);
		}
	}
}
