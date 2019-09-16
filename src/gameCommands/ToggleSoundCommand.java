package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Toggle sound state */
public class ToggleSoundCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public ToggleSoundCommand(GameWorld gw) {
		super("Sound");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		System.out.println("~ Sound toggled");
		target.toggleSound();
	}
}
