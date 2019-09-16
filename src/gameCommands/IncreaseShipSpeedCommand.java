package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Increase speed of the ship by a small amount */
public class IncreaseShipSpeedCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public IncreaseShipSpeedCommand(GameWorld gw) {
		super("Increase Ship Speed");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("test");
 		String msg = target.increaseShipSpeed();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
