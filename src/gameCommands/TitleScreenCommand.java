package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;

import uiContainers.TitleScreen;

public class TitleScreenCommand extends Command {
	private static TitleScreenCommand titleCmd;
	
	/* Constructor */
	private TitleScreenCommand() {
		super("Title Screen");
	}
	
	public static TitleScreenCommand getCommand() {
		if (titleCmd == null)
			titleCmd = new TitleScreenCommand();
		return titleCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Component c = evt.getComponent();
		while (c.getComponentForm() == null) c = c.getParent();
		c.getComponentForm().replaceAndWait(
				c.getComponentForm().getContentPane().getComponentAt(0),
				TitleScreen.getScreen(),
				CommonTransitions.createFade(500));
	}
}
