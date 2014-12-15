package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lazer implements MainLoopJob, ShootingObject {
	public static final int WIDTH = 2, HEIGHT = 10;
	private Shooting shooting;
	private int x, y;
	private int sx, sy;

	public Lazer(Shooting shooting, int x, int y, int sx, int sy) {
		if (sy < 0) {
			// ã•ûŒü‚Éi‚Þê‡
			this.y = y-HEIGHT;
		} else {
			this.y = y;
		}
		this.x = x+WIDTH/2;
		this.sx = sx;
		this.sy = sy;
		this.shooting = shooting;
		System.out.println("x:" + this.x + " y:" + this.y);
	}

	public boolean isOutOfScreen() {
		return y+HEIGHT < 0 || y > shooting.getHeight();
	}

	public void runMainLoopJob() {
		x += sx;
		y += sy;
	}

	public void paintObject(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}
}

