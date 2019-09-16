package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

public interface IDrawable {
	abstract void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);
}
