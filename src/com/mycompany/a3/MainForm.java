package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Container;
import com.codename1.ui.Form;
import com.codename1.ui.animations.CommonTransitions;
import com.codename1.ui.layouts.BorderLayout;
import com.codename1.ui.plaf.Border;

import uiContainers.TitleScreen;

public class MainForm extends Form {
	private static Container main;

	/* Constructor */
	public MainForm() {
		setTransitionOutAnimator(CommonTransitions.createFade(2000));
		// initialize fields
		main = getContentPane();
		// set layout
		main.setLayout(new BorderLayout());
		main.add(BorderLayout.CENTER, TitleScreen.getScreen());
		getLayeredPane().getAllStyles().setBorder(
			Border.createRidgeBorder(15, ColorUtil.rgb(192,192,192)));
		// show form and play music
		show();
	}
	
	public static void openMainMenu() {
		main.replace(main.getComponentAt(0),
					 TitleScreen.getScreen(),
					 null);
		main.getComponentForm().show();
	}
}
