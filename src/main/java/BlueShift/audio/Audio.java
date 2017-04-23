package BlueShift.audio;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
	private AudioInputStream inputStream;
	private Clip clip;
	private boolean playing = false;
	public Audio(String filePath) {
		try {
			inputStream = AudioSystem.getAudioInputStream(new File(filePath));
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void playSound() {
		try {
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			clip.loop(Clip.LOOP_CONTINUOUSLY);
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopSound() {
		clip.close();
	}

	public boolean isPlaying() {
		return playing;
	}
}
