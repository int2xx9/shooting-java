package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player implements MainLoopJob, ShootingObject, KeyListener {
	public static final int WIDTH = 50, HEIGHT = 50;

	protected Shooting shooting;
	private int remaining;	// 残機
	private int x, y;	// 中心の座標
	private int sx, sy;	// 弾を撃つ方向
	private int mx, my;	// 移動中の方向

	// x, yは中心座標なので左上座標にする
	public int getRealX() { return x-WIDTH/2; }
	public int getRealY() { return y-HEIGHT/2; }

	public void setX(int x) { this.x = x; }
	public void setY(int y) { this.y = y; }
	public int getX() { return x; }
	public int getY() { return y; }

	public int getRemaining() { return remaining; }
	public void setRemaining(int value) { remaining = value; }

	public void setShootToX(int sx) { this.sx = sx; }
	public void setShootToY(int sy) { this.sy = sy; }
	public int getShootToX() { return sx; }
	public int getShootToY() { return sy; }

	public void setMovingX(int mx) { this.mx = mx; }
	public void setMovingY(int my) { this.my = my; }
	public int getMovingX() { return mx; }
	public int getMovingY() { return my; }

	public boolean canMoveTo(int x, int y, int width, int height) {
		if (x < 0 || y < 0) return false;
		if (x+width > shooting.getWidth() || y > shooting.getHeight()) return false;
		return true;
	}

	public Player(Shooting shooting, int x, int y, int sx, int sy) {
		this.shooting = shooting;
		this.x = x; this.y = y;
		this.sx = sx; this.sy = sy;
	}

	private static final int moveInterval = 5;
	private int moveIntervalCnt = 0;
	public void runMainLoopJob() {
		if (moveIntervalCnt == moveInterval) {
			if (canMoveTo(getRealX()+mx, getRealY(), WIDTH, HEIGHT)) {
				x+=mx;
			}
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	public void paintObject(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(getRealX(), getRealY(), WIDTH, HEIGHT);
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 65535) {
			if (e.getKeyCode() == 37) {	// ←
				mx=-1;
			} else if (e.getKeyCode() == 39) {	// →
				mx=1;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 65535) {
			if (e.getKeyCode() == 37 && mx==-1) {	// ←
				mx=0;
			} else if (e.getKeyCode() == 39 && mx==1) {	// →
				mx=0;
			}
		} else if (e.getKeyChar() == 32) {
			if (sy < 0) {
				// 上方向
				shooting.lazers.shoot(
					new Lazer(shooting, getRealX()+WIDTH/2, getRealY(), sx, sy)
				);
			} else if (sy > 0) {
				// 下方向
				shooting.lazers.shoot(
					new Lazer(shooting, getRealX()+WIDTH/2, getRealY()+HEIGHT, sx, sy)
				);
			}
		}
	}

	public void keyTyped(KeyEvent e) {}
}

class AutoPlayer extends Player {
	public AutoPlayer(Shooting shooting, int x, int y, int sx, int sy) {
		super(shooting, x, y, sx, sy);
		setMovingX(1);
	}

	private static final int moveInterval = 5;
	private int moveIntervalCnt = 0;
	public void runMainLoopJob() {
		if ((int)(Math.random()*1000) == 0) {
			shooting.lazers.shoot(
				new Lazer(shooting, getRealX()+WIDTH/2, getRealY()+HEIGHT, getShootToX(), getShootToY())
			);
		}
		if (moveIntervalCnt == moveInterval) {
			if (!canMoveTo(getRealX()+getMovingX(), getRealY(), WIDTH, HEIGHT)) {
				setMovingX(-getMovingX());
			}
			setX(getX()+getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

