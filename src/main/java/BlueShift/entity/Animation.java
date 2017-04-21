package BlueShift.entity;

import BlueShift.Main;
import processing.core.PImage;

public class Animation {
	private Main main;
	private PImage[] images;
	private int imageCount;
	private int frame;

	public Animation(Entity entity, String imagePrefix, int count) {
		main = Main.instance;
		imageCount = count;
		images = new PImage[imageCount];
		for (int i = 0; i < imageCount; i++) {
			int j = i + 1;
			String filename = imagePrefix + j + ".png";
			images[i] = main.loadImage(filename);
			images[i].resize((int) entity.getWidth(), ((int) entity.getHeight()));
		}
	}

	public void display(float xpos, float ypos) {
		frame = (frame+1) % imageCount;
		main.image(images[frame], xpos, ypos);
	}

	public float getWidth() {
		return images[0].width;
	}

	public float getHeight() {
		return images[0].height;
	}
}
