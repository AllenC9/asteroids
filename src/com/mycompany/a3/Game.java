package com.mycompany.a3;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.CheckBox;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.Graphics;
import com.codename1.ui.Toolbar;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.util.UITimer;
import gameCommands.*;
import uiContainers.*;

public class Game extends Form implements Runnable, Observer {
	private Container main;
	private GameWorld gw;
	private ButtonPanel bp;
	private MapView mv;
	private PointsView pv;
	private CommandCollection cmds;
	private UITimer clock;
	private static ObservableBoolean paused;
	private static int mapHeight, mapWidth;
	
	/* Constructor */
	public Game() {
		main = getContentPane();
		main.setLayout(new BorderLayout());
		gw = new GameWorld();
		mv = new MapView();
		pv = new PointsView();
		// add observers to GameWorld
		gw.addObserver(mv);
		gw.addObserver(pv);
		// game state fields
		paused = new ObservableBoolean(false);
		clock = new UITimer(this);
		cmds = new CommandCollection();
		
		// set GUI components
		main.add(BorderLayout.NORTH, pv);
		main.add(BorderLayout.CENTER, mv);
		
		/* code here to create menus, create Command objects for each command,   */
		/* add commands to Command menu, create a control panel for the buttons, */
		/* add buttons to the control panel, add commands to the buttons,        */
		/* and add control panel, MapView panel, add PointsView to the form      */
		
		/* Command Objects */
		// in-game object creation commands
		CreateAsteroidCommand     createAsteroid     = new CreateAsteroidCommand(gw);
		CreateFlyingSaucerCommand createFlyingSaucer = new CreateFlyingSaucerCommand(gw);
		CreateSpaceStationCommand createSpaceStation = new CreateSpaceStationCommand(gw);
		CreateShipCommand         createShip         = new CreateShipCommand(gw);
		// ship control commands
		IncreaseShipSpeedCommand increaseShipSpeed = new IncreaseShipSpeedCommand(gw);
		DecreaseShipSpeedCommand decreaseShipSpeed = new DecreaseShipSpeedCommand(gw);
		TurnShipLeftCommand      turnShipLeft      = new TurnShipLeftCommand(gw);
		TurnShipRightCommand     turnShipRight     = new TurnShipRightCommand(gw);
		FireMissileCommand       fireMissile       = new FireMissileCommand(gw);
		ReloadMissilesCommand    reloadMissiles    = new ReloadMissilesCommand(gw);
		HyperspaceCommand        hyperspace        = new HyperspaceCommand(gw);
		// in game state modification commands
		ToggleSoundCommand    toggleSound  = new ToggleSoundCommand(gw);
		QuitGameCommand       quitGame     = new QuitGameCommand();
		RefuelMissilesCommand refuel       = new RefuelMissilesCommand(gw);
		PausePlayCommand      pauseGame    = new PausePlayCommand();
		
		/* Add cmds to a list */
		cmds.addAll(createAsteroid, createFlyingSaucer, createSpaceStation,
					createShip,		increaseShipSpeed,	decreaseShipSpeed,
					turnShipLeft,	turnShipRight,		fireMissile,
					reloadMissiles,	hyperspace);		
		
		// create pause button and add to map
		PauseButton pauseButton = new PauseButton(isPaused(), pauseGame);
		pauseButton.setText("");
		paused.addObserver(pauseButton);
		paused.addObserver(this);
		paused.addObserver(mv);
		mv.add(pauseButton);
		
		/* Key Bindings */
		addKeyListener('f', fireMissile); // F KEY
		addKeyListener('j', hyperspace);  // J KEY
		
		/* Toolbar */
		Toolbar myToolbar = new Toolbar();
		setToolbar(myToolbar);
		myToolbar.setTitle("Asteroid Game");
		// "File" Side Menu
		CheckBox checkSoundOn = new CheckBox("Sound");
		checkSoundOn.setSelected(true);
		checkSoundOn.setCommand(toggleSound);
		checkSoundOn.getAllStyles().setBgTransparency(255);
		checkSoundOn.getAllStyles().setBgColor(ColorUtil.WHITE);
		myToolbar.addComponentToSideMenu(checkSoundOn);
		myToolbar.addCommandToSideMenu(quitGame);
		// "Commands" Overflow Menu
		myToolbar.addCommandToOverflowMenu(createAsteroid);     // 'a' from assignment 1
		myToolbar.addCommandToOverflowMenu(createSpaceStation); // 'b' from assignment 1
		myToolbar.addCommandToOverflowMenu(createShip);         // 's' from assignment 1
		myToolbar.addCommandToOverflowMenu(reloadMissiles);     // 'n' from assignment 1
		myToolbar.addCommandToOverflowMenu(quitGame);           // 'q' from assignment 1

		show();
	}
	
	public void start() {
		show();
		mapHeight = mv.getHeight();
		mapWidth = mv.getWidth();
		gw.init();
		clock.schedule(1000, true, this);
	}

	/* Clock has ticked */
	public void run() {
		gw.gameClockTick();
	}
	
	/* Pause or Unpause */

	public void update(Observable observable, Object data) {
		if (isPaused()) { // pause game
			clock.cancel();
			gw.pause();
			cmds.disableCommands();
		}
		else { // resume game
			clock.schedule(1000, true, this);
			gw.resume();
			cmds.enableCommands();
		}
	}
	
	/* Override keyPressed to allow key */
	/* holding for some key bindings    */                 
	@Override
	public void keyPressed(int KeyCode) {
		switch (KeyCode) {
			case -91: case 'w': gw.increaseShipSpeed(); break;
			case -92: case 's': gw.decreaseShipSpeed(); break;
			case -93: case 'a': gw.turnShipLeft();      break;
			case -94: case 'd': gw.turnShipRight();     break;
			default: break;
		}
	}
	
	@Override
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		g.setColor(ColorUtil.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	/* ------------- STATIC METHODS ------------- */
	
	/* Switch pause state on/off */
	public static void pauseAndResume() {
		paused.setValue(!paused.getValue());
	}
	
	/* Return the height of the map view */
	public static int getMapHeight() {
		return mapHeight;
	}
	
	/* Return the width of the map view */
	public static int getMapWidth() {
		return mapWidth;
	}
	
	/* Return paused value */
	public static boolean isPaused() {
		return paused.getValue();
	}
}
