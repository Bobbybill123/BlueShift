package BlueShift.audio;

import BlueShift.Main;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;

public class Audio {
	private AudioInputStream inputStream;
	private Clip clip;
	private String filePath;
	public Audio(String filePath) {
		try {
			inputStream = AudioSystem.getAudioInputStream(Main.instance.dataFile(filePath));
			this.filePath = filePath;
		} catch (IOException | UnsupportedAudioFileException e) {
			e.printStackTrace();
		}
	}

	public void playSound(boolean continuous) {
		try {
			clip = AudioSystem.getClip();
			clip.open(inputStream);
			if(continuous) {
				clip.loop(Clip.LOOP_CONTINUOUSLY);
			}
			clip.start();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public void stopSound() {
		clip.close();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o) return true;
		if (o == null || getClass() != o.getClass()) return false;

		Audio audio = (Audio) o;
		return this.filePath.equals(audio.filePath) || (inputStream != null ? inputStream.equals(audio.inputStream) : audio.inputStream == null) && (clip != null ? clip.equals(audio.clip) : audio.clip == null);
	}

	@Override
	public int hashCode() {
		int result = inputStream != null ? inputStream.hashCode() : 0;
		result = 31 * result + (clip != null ? clip.hashCode() : 0);
		return result;
	}
}
