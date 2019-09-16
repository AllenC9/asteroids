package gameCommands;

import com.codename1.ui.Command;
import com.codename1.ui.Dialog;
import com.codename1.ui.Display;
import com.codename1.ui.events.ActionEvent;
import com.mycompany.a3.MainForm;

/* Confirm player quit */
public class QuitGameCommand extends Command {
	
	/* Constructor */
	public QuitGameCommand() {
		super("Quit Game");
	}
	
	@Override
	public void actionPerformed(ActionEvent evt) {
	boolean quitConfirmed = Dialog.show("Confirm quit",
										"Are you sure you want to quit?",
										"Yes", "No");
	if (quitConfirmed)
		MainForm.openMainMenu();
	}
}
