package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class BGSound implements Runnable {
	private Media m;
	
	/* Constructor */
	public BGSound(String fileName) {
		InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
		try {
			if (fileName.endsWith(".wav"))
				m = MediaManager.createMedia(is, "audio/wav", this);
			else if (fileName.endsWith(".mp3"))
				m = MediaManager.createMedia(is, "audio/mp3", this);
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	/* Change volume level */
	public void changeVolume(int percentage) {
		m.setVolume(percentage);
	}
	
	/* Pause the sound file */
	public void pause() { m.pause(); }
	
	/* Play the sound file where last left off */
	public void play() { m.play(); }
	
	/* Return true if currently playing */
	public boolean isPlaying() {
		return m.isPlaying();
	}
	
	/* Invoked upon media completion */
	public void run() {
		m.setTime(0); // reset to beginning of sound file
		m.play();
	}
}
