package shooting.core;

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

	public ControllablePlayerConfig keyConfig = new ControllablePlayerConfig();
	public ControllablePlayerConfig getKeyConfig() { return keyConfig; }

	public ControllablePlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new DefaultWeapon(this));
	}

	public void initialize() {
		super.initialize();
		setMovingX(0);
		setMovingY(0);
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
		if (keyConfig.isMoveLeftKey(e.getKeyCode(), e.getKeyChar())) {
			setMovingX(-1);
		} else if (keyConfig.isMoveRightKey(e.getKeyCode(), e.getKeyChar())) {
			setMovingX(1);
		}
	}

	public void keyReleased(KeyEvent e) {
		if (keyConfig.isMoveLeftKey(e.getKeyCode(), e.getKeyChar()) && getMovingX() < 0) {
			setMovingX(0);
		} else if (keyConfig.isMoveRightKey(e.getKeyCode(), e.getKeyChar()) && getMovingX() > 0) {
			setMovingX(0);
		} else if (keyConfig.isShootKey(e.getKeyCode(), e.getKeyChar())) {
			getWeapon().shoot();
		}
	}

	public void keyTyped(KeyEvent e) {}
}

