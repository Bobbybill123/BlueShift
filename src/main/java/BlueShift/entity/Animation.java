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
			if(entity != null) {
				images[i].resize((int) entity.getWidth(), ((int) entity.getHeight()));
			}
		}
	}

	private Animation(PImage[] images, int imageCount) {
		main = Main.instance;
		this.images = images;
		this.imageCount = imageCount;
	}

	public void animate(float x, float y) {
		frame = (frame + 1) % imageCount;
		displayCurr(x,y);
	}

	public void resize(int w, int h) {
		for (PImage image : images) {
			image.resize(w, h);
		}
	}

	public void setFrame(int frame) {
		this.frame = frame;
	}

	public Animation copy() {
		return new Animation(images, imageCount);
	}

	public void displayCurr(float x, float y) {
		display(x, y, frame);
	}

	public void display(float x, float y, int frame) {
		main.image(images[frame], x, y);
	}

	public float getWidth() {
		return images[0].width;
	}

	public float getHeight() {
		return images[0].height;
	}

	public int getImageCount() {
		return imageCount;
	}


}
