package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Load a new supply of missiles into the ship */
public class ReloadMissilesCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public ReloadMissilesCommand(GameWorld gw) {
		super("Reload Missiles");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Reload missiles.");
 		String msg = target.reloadMissiles();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
