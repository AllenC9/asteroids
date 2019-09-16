package objectGraphics;

import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.Graphics;
import com.codename1.ui.Transform;
import com.codename1.ui.geom.Point;

public class SpaceStationGraphic {
	private Transform myRotation, myTranslation, myScale;
	private int width, height;
	
	/* Constructor */
	public SpaceStationGraphic(int width, int height) {
		this.myRotation = Transform.makeIdentity();
		this.myTranslation = Transform.makeIdentity();
		this.myScale = Transform.makeIdentity();
		this.width = width;
		this.height = height;
	}
	
	/*  */
	public void rotate (float degrees) {
		myRotation.rotate((float)Math.toRadians(degrees),0,0);
	}
	
	/*  */
	public void translate (float tx, float ty) {
		myTranslation.translate(tx, ty);
	}
	
	/*  */
	public void scale(float sx, float sy) {
		myScale.scale(sx, sy);
	}
	
	/* This method applies the triangle’s LTs to the received Graphics object’s xform             */
	/* then uses this xform (with the additional transformations) to draw the triangle.           */
	/* Note that we pass getAbsoluteX() and getAbsoluteY() values of the container as pCmpRelScrn */
	public void draw(Graphics g, Point pCmpRelPrnt, Point pCmpRelScrn, boolean selected) {
		// append the triangle’s LTs to the xform in the Graphics object
		// but first move the drawing coordinates so that the local origin
		// coincides with the screen origin. After LTs are applied,
		// move the drawing coordinates back.
	 	Transform gXform = Transform.makeIdentity();
	 	
		g.getTransform(gXform);
		Transform gOrigXform = gXform.copy(); 
		gXform.translate(pCmpRelScrn.getX(), pCmpRelScrn.getY());
		gXform.translate(myTranslation.getTranslateX(), myTranslation.getTranslateY());
		gXform.concatenate(myRotation);
		gXform.scale(myScale.getScaleX(), myScale.getScaleY());
		gXform.translate(-pCmpRelScrn.getX(), -pCmpRelScrn.getY());
		g.setTransform(gXform);
		
		// 
		if (selected) g.setColor(ColorUtil.YELLOW);
		g.fillRoundRect(
				pCmpRelPrnt.getX()-50,
				pCmpRelPrnt.getY()-15,
				width, height, 10, 10);
		g.setTransform(gOrigXform);
	}
}
