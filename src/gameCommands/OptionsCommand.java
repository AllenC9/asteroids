package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;
import uiContainers.OptionsScreen;

public class OptionsCommand extends Command {
	private static OptionsCommand optionsCmd;
	
	/* Constructor */
	private OptionsCommand() {
		super("Options");
	}
	
	public static OptionsCommand getCommand() {
		if (optionsCmd == null)
			optionsCmd = new OptionsCommand();
		return optionsCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Component c = evt.getComponent();
		while (c.getComponentForm() == null) c = c.getParent();
		c.getComponentForm().replaceAndWait(
				c.getComponentForm().getContentPane().getComponentAt(0),
				OptionsScreen.getScreen(),
				CommonTransitions.createFade(500));
	}
}
