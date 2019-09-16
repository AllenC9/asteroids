package uiContainers;

import java.util.ArrayList;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.layouts.BoxLayout;

public class ButtonPanel extends Container {
	private BoxLayout xAxis, yAxis;
	private ArrayList<Button> playButtons;
	private ArrayList<Button> pauseButtons;
	
	/* Constructor */
	public ButtonPanel() {
		// Button Panel is a vertical/horizontal stack of buttons
		this.xAxis = BoxLayout.x();
		this.yAxis = BoxLayout.y();
		this.playButtons  = new ArrayList<Button>();
		this.pauseButtons = new ArrayList<Button>();
		setLayout(yAxis);
		this.getAllStyles().setPadding(5,5,5,5);
	}
	
	/* Make panel horizontal */
	public void setHorizontal() {
		setLayout(xAxis);
	}
	
	/* Make panel vertical */
	public void setVertical() {
		setLayout(yAxis);
	}
	
	/* Add a variable number of buttons to the panel */
	public void addButtons(Button... btns) {
		// add buttons to list
		for (Button b : btns) {
			b.getDisabledStyle().setOpacity(90);
			playButtons.add(b);
		}
		// add to this container
		for (Button b : playButtons) this.add(b);
	}
	
	/* Add a variable number of buttons to the panel */
	public void addButtons(Command... cmds) {
		// add buttons to list
		for (Command command : cmds)
			playButtons.add(new Button(command));
		// add to this container
		for (Button b : playButtons) {
			b.getDisabledStyle().setOpacity(90);
			this.add(b);
		}
	}
	
	/* Add a button, shown on pause */
	public void addButtonForPause(Button btn) {
		btn.setHidden(true);
		pauseButtons.add(btn);
		this.add(btn);
	}
	
	/* Add a button, shown on pause */
	public void addButtonForPause(Command cmd) {
		Button b = new Button(cmd);
		b.setHidden(true);
		pauseButtons.add(b);
		this.add(b);
	}
	
	/* Enable buttons */
	public void enablePlayButtons() {
		for (Button b : playButtons)
			b.setEnabled(true);
		this.repaint();
	}
	
	/* Disable buttons */
	public void disablePlayButtons() {
		for (Button b : playButtons)
			b.setEnabled(false);
		this.repaint();
	}
	
	/* Enable buttons */
	public void showPauseButtons() {
		for (Button b : pauseButtons)
			b.setHidden(false);
		this.repaint();
	}
	
	/* Disable buttons */
	public void hidePauseButtons() {
		for (Button b : pauseButtons)
			b.setHidden(true);
		this.repaint();
	}
}
