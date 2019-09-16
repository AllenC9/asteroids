package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Form;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.events.ActionEvent;

import uiContainers.AboutScreen;

/* Open About Dialog Box */
public class AboutCommand extends Command {
	private static AboutCommand abtCmd;
	
	/* Constructor */
	private AboutCommand() {
		super("About");
	}
	
	public static AboutCommand getCommand() {
		if (abtCmd == null)
			abtCmd = new AboutCommand();
		return abtCmd;
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
		Component c = evt.getComponent();
		while (c.getComponentForm() == null) c = c.getParent();
		c.getComponentForm().replaceAndWait(
				c.getComponentForm().getContentPane().getComponentAt(0),
				AboutScreen.getScreen(),
				CommonTransitions.createFade(500));
	}
}
