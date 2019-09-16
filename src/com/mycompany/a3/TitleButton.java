package com.mycompany.a3;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Button;
import com.codename1.ui.Command;
import com.codename1.ui.Component;
import com.codename1.ui.Font;
import com.codename1.ui.geom.Dimension;
import com.codename1.ui.plaf.Border;
import com.codename1.ui.plaf.Style;

public class TitleButton extends Button {
	
	/* Constructor */
	public TitleButton() {
		super(); init();
	}
	
	/* Constructor */
	public TitleButton(String label) {
		super(label); init();
	}
	
	/* Constructor */
	public TitleButton(Command cmd) {
		super(cmd); init();
	}
	
	/* Initialize object defaults */
	private void init() {
		setShiftText(20);
		// retrieve styles
		Style allStyles       = getAllStyles();
		Style unselectedStyle = getUnselectedStyle();
		Style selectedStyle   = getSelectedStyle();
		Style pressedStyle    = getPressedStyle();
		// set style defaults for title buttons
		allStyles.setBorder(Border.createBevelRaised());
		allStyles.setAlignment(Component.LEFT);
		allStyles.setFont(Font.createSystemFont(Font.FACE_SYSTEM,
												Font.STYLE_PLAIN,
												Font.SIZE_LARGE));
		//
		unselectedStyle.setBgTransparency(120);
		unselectedStyle.setBgColor(ColorUtil.BLUE);
		unselectedStyle.setPaddingTop(0);
		unselectedStyle.setPaddingBottom(0);
		//
		selectedStyle.setBgTransparency(120);
		selectedStyle.setBgColor(ColorUtil.BLUE);
		selectedStyle.setPaddingTop(2);
		selectedStyle.setPaddingBottom(2);
		//
		pressedStyle.setBgTransparency(70);
		pressedStyle.setBgColor(ColorUtil.BLUE);
	}
	
	@Override
	public Dimension calcPreferredSize() {
		return new Dimension(320,40);
	}
	
	@Override
	public void focusGained() {
		setPreferredW(350);
		setPreferredH(60);
		setShiftText(60);
		getParent().repaint();
	}
	
	@Override
	public void focusLost() {
		setPreferredW(320);
		setPreferredH(40);
		setShiftText(20);
		getParent().repaint();
	}
}
