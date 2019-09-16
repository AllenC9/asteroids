package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.GameWorld;

/* Tell the game world the "game clock" has ticked */
public class GameClockTickCommand extends Command {
	private GameWorld target;
	
	/* Constructor */
	public GameClockTickCommand(GameWorld gw) {
		super("Game Clock Tick");
		target = gw;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		if (!this.isEnabled()) return;
		System.out.println("~ Clock Tick.");
		target.gameClockTick();
	}
}
