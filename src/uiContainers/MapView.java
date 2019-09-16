package uiContainers;

import java.util.Observable;
import java.util.Observer;
import com.codename1.charts.util.ColorUtil;
import com.codename1.ui.*;
import com.codename1.ui.geom.Point;
import com.codename1.ui.layouts.FlowLayout;
import com.mycompany.a3.*;

public class MapView extends Container implements Observer {
	private IGameWorld gw;
	private Label paused;
	
	/* Constructor */
	public MapView() {
		gw = null;
		paused = new Label("PAUSED");
		paused.getAllStyles().setFgColor(ColorUtil.WHITE);
		paused.getAllStyles().setFont(
				Font.createSystemFont(Font.FACE_SYSTEM,
									  Font.STYLE_BOLD,
									  Font.SIZE_LARGE));
		setLayout(new FlowLayout(Component.RIGHT));
	}
	
	/* Observable updated                          */
	/* code here to output current map information */
	public void update(Observable observable, Object data) {
		// place paused label is game is paused
		if (Game.isPaused()) this.addComponent(0, paused);
		else                 this.removeComponent(paused);
		// grab Game World data
		if (data instanceof IGameWorld) gw = (IGameWorld) data;
		this.repaint();
	}
	
	/* Iterate through each object and draw it */
	public void paintBackground(Graphics g) {
		super.paintBackground(g);
		if (gw == null) return;
		IIterator iterator = gw.getGameObjects().getIterator();
		while (iterator.hasNext())
			iterator.getNext().draw(g,
					new Point(getX(), getY()),
					new Point(getAbsoluteX(), getAbsoluteY()));
	}
	
	@Override
	public void pointerPressed(int x, int y) {
		Point pPtrRelPrnt = new Point(
				x - getParent().getAbsoluteX(),
				y - getParent().getAbsoluteY());
		Point pCmpRelPrnt = new Point(getX(), getY());
		IIterator iterator = gw.getGameObjects().getIterator();
		while (iterator.hasNext()) {
			ISelectable curObj = (ISelectable)iterator.getNext();
			if (curObj.contains(pPtrRelPrnt, pCmpRelPrnt))
				curObj.setSelected(true);
			else
				curObj.setSelected(false);
		}
		this.repaint();
	}
}
