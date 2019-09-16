package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.BGSound;
import com.mycompany.a3.Game;

public class StartGameCommand extends Command {
	private BGSound bgMusic;
	
	/* Constructor */
	public StartGameCommand(BGSound bgMusic) {
		super("Start New Game");
		this.bgMusic = bgMusic;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		bgMusic.pause();
		new Game().start();
	}
}
