package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

public class RefuelMissilesCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public RefuelMissilesCommand(GameWorld gw) {
		super("Refuel Missiles");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		target.refuelSelectedMissiles();
	}
}
