package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import uiContainers.ScoreBoardScreen;

public class ScoreBoardCommand extends Command {
	private static ScoreBoardCommand scoreCmd;

	/* Constructor */
	private ScoreBoardCommand() {
		super("Scoreboard");
	}
	
	public static ScoreBoardCommand getCommand() {
		if (scoreCmd == null)
			scoreCmd = new ScoreBoardCommand();
		return scoreCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Component c = evt.getComponent();
		while (c.getComponentForm() == null) c = c.getParent();
		c.getComponentForm().replaceAndWait(
				c.getComponentForm().getContentPane().getComponentAt(0),
				ScoreBoardScreen.getScreen(),
				CommonTransitions.createFade(500));
	}
}
