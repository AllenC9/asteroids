package uiContainers;

import java.util.Observable;
import java.util.Observer;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.IGameWorld;

public class PointsView extends Container implements Observer {
	private Label scoreValueLabel, livesValueLabel, missilesValueLabel, soundValueLabel, timeValueLabel;
	
	/* Constructor */
	public PointsView() {
		// set layout and style
		this.setLayout(new FlowLayout(Component.CENTER));
		this.getAllStyles().setBgTransparency(255);
		this.getAllStyles().setBgColor(ColorUtil.WHITE);
		
		// instantiate text labels
		Label scoreTextLabel = new Label("Points: ");
		Label livesTextLabel = new Label("Lives: ");
		Label missilesTextLabel = new Label("Missiles: ");
		Label soundTextLabel = new Label("Sound: ");
		Label timeTextLabel = new Label("Time: ");
		
		// instantiate value values
		scoreValueLabel = new Label("XXX ");
		livesValueLabel = new Label("XXX ");
		missilesValueLabel = new Label("XXX ");
		soundValueLabel = new Label("XXX ");
		timeValueLabel = new Label("XX:XX ");
		
		// add all labels to container
		this.addAll(scoreTextLabel,    scoreValueLabel,
					livesTextLabel,    livesValueLabel,
					missilesTextLabel, missilesValueLabel,
					soundTextLabel,    soundValueLabel,
					timeTextLabel,     timeValueLabel
					);
	}
	
	/* Observable updated                                                        */
	/* Code here to update labels from data in the Observable (a GameWorldPROXY) */
	public void update(Observable observable, Object data) {
		// Cast data as IGameWorld
		IGameWorld gw = (IGameWorld) data;
		
		// update labels
		int score = gw.getScore(); // retrieve score
		scoreValueLabel.setText("" + (score > 99 ? "" : "0") + (score > 9 ? "" : "0") + score);
		int lives = gw.getLives(); // retrieve lives remaining
		livesValueLabel.setText("" + lives);
		int missiles = gw.getMissiles(); // retrieve missiles remaining
		missilesValueLabel.setText("" + (missiles > 9 ? "" : "0") + missiles);
		boolean soundOn = gw.isSoundON(); // retrieve sound value
		soundValueLabel.setText(soundOn == true ? "ON" : "OFF");
		int time = gw.getClock(); // retrieve clock time
		timeValueLabel.setText("" + (time/60 > 9 ? "" : "0") + (time/60) + ":"
							      + (time%60 > 9 ? "" : "0") + (time%60));
		this.repaint();
	}
}
