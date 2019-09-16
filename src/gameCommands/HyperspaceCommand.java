package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Jump through hyperspace */
public class HyperspaceCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public HyperspaceCommand(GameWorld gw) {
		super("Hyperspace");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Hyperspace.");
 		String msg = target.hyperspace();
 		if (msg != null)
 			System.out.println("Invalid command entered: " + msg);
	}
}
