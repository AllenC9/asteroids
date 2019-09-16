package uiContainers;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

import gameCommands.TitleScreenCommand;

public class ScoreBoardScreen extends Container {
private static ScoreBoardScreen scoreScreen;
	
	/* Constructor */
	private ScoreBoardScreen() {
		// set layout
		setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
		Container text = new Container(BoxLayout.y());
		add(text);
		
		//---------------
		Label l = new Label("Not yet implemented");
		l.getAllStyles().setFgColor(ColorUtil.WHITE);
		text.add(l);
		//---------------
		
		// back button
		Button backBtn = new Button(TitleScreenCommand.getCommand());
		backBtn.setText("Back");
		backBtn.getAllStyles().setBorder(Border.createBevelRaised());
		backBtn.setPreferredSizeStr("150 30");
		backBtn.getAllStyles().setBgTransparency(120);
		backBtn.getAllStyles().setBgColor(ColorUtil.BLUE);
		backBtn.getPressedStyle().setBgTransparency(70);
		backBtn.getPressedStyle().setBgColor(ColorUtil.BLUE);
		text.add(backBtn);
	}
	
	public static ScoreBoardScreen getScreen() {
		if (scoreScreen == null)
			scoreScreen = new ScoreBoardScreen();
		return scoreScreen;
	}
	
	@Override
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		g.setColor(ColorUtil.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
