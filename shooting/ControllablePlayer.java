package shooting;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class ControllablePlayer extends Player {
	public static final int WIDTH = 25, HEIGHT = 25;
	public static final int MAX_DAMAGE = 50;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getMaxDamage() { return MAX_DAMAGE; }

	public ControllablePlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new DefaultWeapon(this));
	}

	private static final int moveInterval = 5;
	private int moveIntervalCnt = 0;
	public void runMainLoopJob() {
		if (moveIntervalCnt == moveInterval) {
			setX(getX() + getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	public void keyPressed(KeyEvent e) {
		if (e.getKeyChar() == 65535) {
			if (e.getKeyCode() == 37) {	// Å©
				setMovingX(-1);
			} else if (e.getKeyCode() == 39) {	// Å®
				setMovingX(1);
			}
		}
	}

	public void keyReleased(KeyEvent e) {
		if (e.getKeyChar() == 65535) {
			if (e.getKeyCode() == 37 && getMovingX()==-1) {	// Å©
				setMovingX(0);
			} else if (e.getKeyCode() == 39 && getMovingX()==1) {	// Å®
				setMovingX(0);
			}
		} else if (e.getKeyChar() == 32) {
			getWeapon().shoot();
		}
	}

	public void keyTyped(KeyEvent e) {}
}

