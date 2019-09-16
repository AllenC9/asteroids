package com.mycompany.a3;

import com.codename1.ui.Graphics;
import com.codename1.ui.geom.Point;

/* This interface defines the services (methods) provided */
/* by an object which is “Selectable” on the screen       */
public interface ISelectable {
	// a way to mark an object as “selected” or not
	public void setSelected(boolean yesNo);
	// a way to test whether an object is selected
	public boolean isSelected();
	// a way to determine if a pointer is “in” an object
	// pPtrRelPrnt is pointer position relative to the parent origin
	// pCmpRelPrnt is the component position relative to the parent origin
	public boolean contains(Point pPtrRelPrnt, Point pCmpRelPrnt);
	// a way to “draw” the object that knows about drawing
	// different ways depending on “isSelected”
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn);
}
