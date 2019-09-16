package com.mycompany.a3;

import java.io.IOException;
import java.io.InputStream;
import com.codename1.media.Media;
import com.codename1.media.MediaManager;
import com.codename1.ui.Display;

public class Sound {
	private Media m;
	
	/* Constructor */
	public Sound(String fileName) {
		InputStream is = Display.getInstance().getResourceAsStream(getClass(), "/"+fileName);
		try {
			m = MediaManager.createMedia(is, "audio/wav");
		} catch (IOException e) { e.printStackTrace(); }
	}
	
	/* Change volume level */
	public void changeVolume(int percentage) {
		m.setVolume(percentage);
	}
	
	/* Play the sound (from the beginning of file) */
	public void play() {
		m.setTime(0);
		m.play();
	}
}
