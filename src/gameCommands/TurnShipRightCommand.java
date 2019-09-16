package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Turn right by a small amount */
public class TurnShipRightCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public TurnShipRightCommand(GameWorld gw) {
		super("Turn Ship Right");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
 		String msg = target.turnShipRight();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
