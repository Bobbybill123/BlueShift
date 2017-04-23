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
		main.textAlign(PConstants.CENTER, PConstants.TOP);
		main.textSize(50);
		main.fill(buttons[0].base.getRGB());
		main.text(title, main.width/2, 0);
		float buttonHeight = ((main.height - 50)/buttons.length) - (buttons.length*5);
		for (int i = 0; i < buttons.length; i++) {
			buttons[i].draw(0, 50 + (buttonHeight + 5)*i, buttonHeight);
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
