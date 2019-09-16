package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Decrease speed of the ship by a small amount */
public class DecreaseShipSpeedCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public DecreaseShipSpeedCommand(GameWorld gw) {
		super("Decrease Ship Speed");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
 		String msg = target.decreaseShipSpeed();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
