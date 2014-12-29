package shooting;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class AIPlayer extends Player {
	public static final int WIDTH = 25, HEIGHT = 25;
	public static final int MAX_DAMAGE = 30;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getMaxDamage() { return MAX_DAMAGE; }

	public AIPlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new DefaultWeapon(this));
		setMovingX(1);
	}

	private static final int moveInterval = 5;
	private int moveIntervalCnt = 0;
	public void runMainLoopJob() {
		if ((int)(Math.random()*1000) == 0) {
			getWeapon().shoot();
		}
		if (moveIntervalCnt == moveInterval) {
			if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
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

