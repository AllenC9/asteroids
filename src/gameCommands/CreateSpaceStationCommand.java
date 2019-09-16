package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Add a new blinking space station */
public class CreateSpaceStationCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public CreateSpaceStationCommand(GameWorld gw) {
		super("Create Space Station");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Add a new space station.");
		String msg = target.createSpaceStation();
		if (msg != null)
			System.out.println("Invalid command entered: " + msg);
	}
}
