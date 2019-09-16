package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Turn left by a small amount */
public class TurnShipLeftCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public TurnShipLeftCommand(GameWorld gw) {
		super("Turn Ship Left");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
 		String msg = target.turnShipLeft();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
