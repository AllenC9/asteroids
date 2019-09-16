package uiContainers;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Container;
import com.codename1.ui.Display;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.events.ActionEvent;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.BGSound;
import com.mycompany.a3.TitleButton;
import gameCommands.AboutCommand;
import gameCommands.OptionsCommand;
import gameCommands.QuitGameCommand;
import gameCommands.ScoreBoardCommand;
import gameCommands.StartGameCommand;

public class TitleScreen extends Container {
	private static TitleScreen titleScreen;
	private BGSound bgMusicTitle;
	
	/* Singleton */
	public static TitleScreen getScreen() {
		if (titleScreen == null)
			titleScreen = new TitleScreen();
		return titleScreen;
	}
	
	/* Constructor */
	private TitleScreen() {
		// initialize fields
		setLayout(new BorderLayout());
		bgMusicTitle = new BGSound("Magellanic Clouds.mp3");
		bgMusicTitle.changeVolume(5);
	
		// create title buttons
		TitleButton newGameBtn = new TitleButton(new StartGameCommand(bgMusicTitle));
		TitleButton scoreBtn   = new TitleButton(ScoreBoardCommand.getCommand());
		TitleButton optionsBtn = new TitleButton(OptionsCommand.getCommand());
		TitleButton aboutBtn   = new TitleButton(AboutCommand.getCommand());
		TitleButton exitBtn    = new TitleButton();
		exitBtn.setCommand(new Command("Exit") {
			public void actionPerformed(ActionEvent e) {
				Display.getInstance().exitApplication();
			}
		});
		
		// add components
		add(BorderLayout.WEST, FlowLayout.encloseMiddle(
				newGameBtn, scoreBtn, optionsBtn, aboutBtn, exitBtn));
		((BorderLayout)getLayout()).getWest().setPreferredW(400);
		bgMusicTitle.play();
	}
	
	@Override
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		g.setColor(ColorUtil.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
	
	@Override
	public void paintGlass(Graphics g) {
		super.paintGlass(g);
		g.scale(4, 3);
		g.setColor(ColorUtil.WHITE);
		g.setFont(Font.createSystemFont(Font.FACE_SYSTEM,
										Font.STYLE_PLAIN,
										Font.SIZE_LARGE));
		g.drawString("Asteroids v2", 20, 20);
		g.resetAffine();
	}
}
