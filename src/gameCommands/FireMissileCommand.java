package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Fire a missile out the front of the ship */
public class FireMissileCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public FireMissileCommand(GameWorld gw) {
		super("Fire Missile");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Fire missile.");
 		String msg = target.fireMissile();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
