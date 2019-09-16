package uiContainers;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Component;
import com.codename1.ui.Container;
import com.codename1.ui.Font;
import com.codename1.ui.Graphics;
import com.codename1.ui.Label;
import com.codename1.ui.layouts.BoxLayout;
import com.codename1.ui.layouts.FlowLayout;
import com.codename1.ui.plaf.Border;

import gameCommands.TitleScreenCommand;

public class AboutScreen extends Container {
	private static AboutScreen aboutScreen;
	
	/* Constructor */
	private AboutScreen() {
		// set layout
		setLayout(new FlowLayout(Component.CENTER, Component.CENTER));
		Container text = new Container(BoxLayout.y());
		add(text);
		
		// text on screen
		Label lbl[] = new Label[5];
		lbl[0] = new Label("Asteroids (version 2)");
		lbl[1] = new Label("Developed by Allen Aquino");
		lbl[2] = new Label("Student at California State University, Sacramento");
		lbl[3] = new Label("CSC 133 - Object-Oriented Computer Graphics Programming");
		lbl[4] = new Label("Professor Doan Nguyen");
		for (Label l : lbl) {
			l.getAllStyles().setFont(Font.createSystemFont(Font.FACE_SYSTEM,
														   Font.STYLE_PLAIN,
														   Font.SIZE_LARGE));
			l.getAllStyles().setFgColor(ColorUtil.WHITE);
			l.getAllStyles().setAlignment(Component.CENTER);
		}
		text.addAll(lbl);
		
		// back button
		Button backBtn = new Button(TitleScreenCommand.getCommand());
		backBtn.setText("Back");
		backBtn.getAllStyles().setBorder(Border.createBevelRaised());
		backBtn.setPreferredSizeStr("150 30");
		backBtn.getAllStyles().setBgTransparency(120);
		backBtn.getAllStyles().setBgColor(ColorUtil.BLUE);
		backBtn.getPressedStyle().setBgTransparency(70);
		backBtn.getPressedStyle().setBgColor(ColorUtil.BLUE);
		text.add(FlowLayout.encloseCenterBottom(backBtn));
	}
	
	public static AboutScreen getScreen() {
		if (aboutScreen == null)
			aboutScreen = new AboutScreen();
		return aboutScreen;
	}
	
	@Override
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		g.setColor(ColorUtil.BLACK);
		g.fillRect(0, 0, getWidth(), getHeight());
	}
}
