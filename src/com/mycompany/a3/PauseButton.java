package com.mycompany.a3;

import java.io.IOException;
import java.util.Observable;
import java.util.Observer;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Image;

public class PauseButton extends Button implements Observer {
	private boolean paused;
	private Image pauseIcon, playIcon;
	
	/* Constructor */
	public PauseButton(boolean isPaused) {
		super(); init(isPaused);
	}
	
	/* Constructor */
	public PauseButton(boolean isPaused, Command cmd) {
		super(cmd); init(isPaused);
	}
	
	/* Initialize icons */
	private void init(boolean isPaused) {
		try {
			pauseIcon = Image.createImage("/pauseIcon.png").scaled(40, 40);
			playIcon  = Image.createImage("/playIcon.png").scaled(40, 40);
		} catch (IOException e) { e.printStackTrace(); }
		// set icon
		if (paused) setIcon(playIcon);
		else        setIcon(pauseIcon);
	}
	
	/* Set Command */
	public void setCommand(Command cmd) {
		super.setCommand(cmd);
	}

	public void update(Observable observable, Object data) {
		paused = !paused;
		if (paused) setIcon(playIcon);
		else        setIcon(pauseIcon);
	}
}
