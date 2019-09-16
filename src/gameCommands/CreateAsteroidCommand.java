package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Add a new asteroid */
public class CreateAsteroidCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public CreateAsteroidCommand(GameWorld gw) {
		super("Create Asteroid");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Add a new asteroid.");
		target.createAsteroid();
	}
}
