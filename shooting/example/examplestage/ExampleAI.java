package shooting.example.examplestage;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shooting.core.*;

public class ExampleAI extends Player {
	public static final int WIDTH = 30, HEIGHT = 30;
	public static final int MAX_DAMAGE = 100;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getMaxDamage() { return MAX_DAMAGE; }

	public ExampleAI(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy*2);
		setWeapon(new ExampleWeapon(this));
		setMovingX(1);
	}

	private static final int moveInterval = 10;
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
}

