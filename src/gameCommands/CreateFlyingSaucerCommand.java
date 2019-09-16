package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Add a flying saucer */
public class CreateFlyingSaucerCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public CreateFlyingSaucerCommand(GameWorld gw) {
		super("Create Flying Saucer");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Add a new flying saucer.");
		target.createFlyingSaucer();
	}
}
