package com.mycompany.a3;

import java.util.ArrayList;
import com.codename1.ui.Command;

public class CommandCollection {
	private ArrayList<Command> cmds;
	
	/* Constructor */
	public CommandCollection() {
		cmds = new ArrayList<Command>();
	}
	
	/* Add an object to the collection */
	public void add(Command c) {
		cmds.add(c);
	}
	
	/* Add multiple objects to the collection */
	public void addAll(Command... commands) {
		for (Command c : commands)
			cmds.add(c);
	}
	
	/* Remove an object from the collection */
	public void remove(Command c) {
		cmds.remove(c);
	}
	
	/* Remove multiple objects */
	public void removeAll(Command... commands) {
		for (Command c : commands)
			cmds.remove(c);
	}
	
	/* Enable Commands */
	public void enableCommands() {
		for (Command c : cmds)
			c.setEnabled(true);
	}
	
	/* Disable Commands */
	public void disableCommands() {
		for (Command c : cmds)
			c.setEnabled(false);
	}
}
