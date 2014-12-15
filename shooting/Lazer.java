package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Lazer implements MainLoopJob, ShootingObject {
	public static final int WIDTH = 2, HEIGHT = 10;
	public static final int DAMAGE = 10;
	private Player player;
	private int x, y;
	private int sx, sy;

	public int getX() { return this.x; }
	public int getY() { return this.y; }
	public int getWidth() { return this.WIDTH; }
	public int getHeight() { return this.HEIGHT; }
	public Player getPlayer() { return player; }

	public Lazer(Player player, int x, int y, int sx, int sy) {
		if (sy < 0) {
			// ������ɐi�ޏꍇ
			this.y = y-HEIGHT;
		} else {
			this.y = y;
		}
		this.x = x+WIDTH/2;
		this.sx = sx;
		this.sy = sy;
		this.player = player;
		//System.out.println("x:" + this.x + " y:" + this.y);
	}

	public boolean isOutOfScreen() {
		return y+HEIGHT < 0 || y > player.shooting.getHeight();
	}

	public void runMainLoopJob() {
		x += sx;
		y += sy;
	}

	public void paintObject(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, WIDTH, HEIGHT);
	}

	public boolean isHit(Lazer lazer) {
		return false;
	}
}

interface LazerListener {
	// ���������[�U�[���I������
	public void lazerHit();
	// ���������[�U�[���͂��ꂽ
	// (Player�N���X�̃I�u�W�F�N�g�ɂ�����Ȃ��܂܉�ʊO�܂ł�����)
	public void lazerNotHit();
}

class LazerAdapter implements LazerListener {
	public void lazerHit() {}
	public void lazerNotHit() {}
}

