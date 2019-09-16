package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Add a new ship */
public class CreateShipCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public CreateShipCommand(GameWorld gw) {
		super("Create Ship");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Add a new ship.");
 		String msg = target.createShip();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
