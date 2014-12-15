package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player implements MainLoopJob, ShootingObject, KeyListener {
	public static final int WIDTH = 50, HEIGHT = 50;

	protected Shooting shooting;
	private int remaining;	// �c�@
	private int x, y;	// ���S�̍��W
	private int sx, sy;	// �e��������
	private int mx, my;	// �ړ����̕���

	// x, y�͒��S���W�Ȃ̂ō�����W�ɂ���
	public int getX() { return x-WIDTH/2; }
	public int getY() { return y-HEIGHT/2; }

	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }

	public void setCenterX(int x) { this.x = x; }
	public void setCenterY(int y) { this.y = y; }
	public int getCenterX() { return x; }
	public int getCenterY() { return y; }

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
			if (canMoveTo(getX()+mx, getY(), WIDTH, HEIGHT)) {
				x+=mx;
			}
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	public void paintObject(Graphics g) {
		g.setColor(Color.WHITE);
		g.fillRect(getX(), getY(), WIDTH, HEIGHT);
	}
	
	public boolean isHit(Lazer lazer) {
		if (lazer.getY()+lazer.getHeight() < getY()) return false;
		if (lazer.getY() > getY()+getHeight()) return false;
		if (lazer.getX()+lazer.getWidth() < getX()) return false;
		if (lazer.getX() > getX()+getWidth()) return false;
		return true;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 65535) {
			if (e.getKeyCode() == 37) {	// ��
				mx=-1;
			} else if (e.getKeyCode() == 39) {	// ��
				mx=1;
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 65535) {
			if (e.getKeyCode() == 37 && mx==-1) {	// ��
				mx=0;
			} else if (e.getKeyCode() == 39 && mx==1) {	// ��
				mx=0;
			}
		} else if (e.getKeyChar() == 32) {
			if (sy < 0) {
				// �����
				shooting.lazers.shoot(
					new Lazer(shooting, getX()+WIDTH/2, getY(), sx, sy)
				);
			} else if (sy > 0) {
				// ������
				shooting.lazers.shoot(
					new Lazer(shooting, getX()+WIDTH/2, getY()+HEIGHT, sx, sy)
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
				new Lazer(shooting, getX()+WIDTH/2, getY()+HEIGHT, getShootToX(), getShootToY())
			);
		}
		if (moveIntervalCnt == moveInterval) {
			if (!canMoveTo(getX()+getMovingX(), getY(), WIDTH, HEIGHT)) {
				setMovingX(-getMovingX());
			}
			setCenterX(getCenterX()+getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

