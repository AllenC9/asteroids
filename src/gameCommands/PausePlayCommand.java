package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.Game;

public class PausePlayCommand extends Command {
	
	/* Constructor */
	public PausePlayCommand() {
		super("Pause");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println("~ Pause / Unpause.");
		Game.pauseAndResume();
	}
}
