package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Lazer implements MainLoopJob, ShootingObject {
	abstract public int getWidth();
	abstract public int getHeight();
	abstract public int getDamage();

	private Player player;
		public Player getPlayer() { return this.player; }
	private int x, y;
		public int getX() { return this.x; }
		public int getY() { return this.y; }
	private int sx, sy;
		public int getShootingToX() { return this.sx; }
		public int getShootingToY() { return this.sy; }

	public Lazer(Player player, int x, int y, int sx, int sy) {
		this.player = player;
		this.x = x+getWidth()/2; this.y = y;
		this.sx = sx; this.sy = sy;
	}

	public boolean isOutOfScreen() {
		return y+getHeight() < 0 || y > player.getShooting().getHeight();
	}

	public void runMainLoopJob() {
		x += sx;
		y += sy;
	}

	public void paintObject(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, getWidth(), getHeight());
	}

	public boolean isHit(Lazer lazer) {
		return false;
	}
}

class DefaultLazer extends Lazer {
	public static final int WIDTH = 2, HEIGHT = 10;
	public static final int DAMAGE = 10;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getDamage() { return DAMAGE; }

	public DefaultLazer(Player player, int x, int y, int sx, int sy) {
		super(player, x, y, sx, sy);
		//if (sy < 0) {
		//	// 上方向に進む場合
		//	this.y = y-HEIGHT;
		//} else {
		//	this.y = y;
		//}
	}
}

interface LazerListener {
	// 撃ったレーザーが的中した
	public void lazerHit();
	// 撃ったレーザーがはずれた
	// (Playerクラスのオブジェクトにあたらないまま画面外までいった)
	public void lazerNotHit();
}

class LazerAdapter implements LazerListener {
	public void lazerHit() {}
	public void lazerNotHit() {}
}

