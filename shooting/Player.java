package shooting;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Player implements MainLoopJob, ShootingObject, KeyListener, LazerListener {
	public static final int WIDTH = 25, HEIGHT = 25;
	public static final int MAX_DAMAGE = 50;

	private LinkedList<PlayerListener> listeners = new LinkedList<PlayerListener>();

	protected Shooting shooting;
	private int damage;	// ���݂̃_���[�W
	private int score;	// �X�R�A
	private int combo;	// �A���q�b�g��
	private int hitcnt, nothitcnt;	// ����������/�͂�������
	private int x, y;	// ���S�̍��W
	private int sx, sy;	// �e��������
	private int mx, my;	// �ړ����̕���
	private int team = 0;
	private Weapon weapon;

	// x, y�͒��S���W�Ȃ̂ō�����W�ɂ���
	public int getX() { return x-WIDTH/2; }
	public int getY() { return y-HEIGHT/2; }

	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }

	public void setCenterX(int x) { this.x = x; }
	public void setCenterY(int y) { this.y = y; }
	public int getCenterX() { return x; }
	public int getCenterY() { return y; }

	public int getDamage() { return damage; }
	public void setDamage(int value) {
		damage = value;
		for (PlayerListener listener : listeners) {
			listener.damageUpdated();
		}
		if (isDestroyed()) {
			for (PlayerListener listener : listeners) {
				listener.playerDestroyed();
			}
		}
	}
	public int getScore() { return score; }
	public void setScore(int value) {
		score = value;
		for (PlayerListener listener : listeners) {
			listener.scoreUpdated();
		}
	}
	public int getCombo() { return combo; }
	public void setCombo(int value) {
		combo = value;
		for (PlayerListener listener : listeners) {
			listener.comboUpdated();
		}
	}

	public int getHitCount() { return hitcnt; }
	public int getNotHitCount() { return nothitcnt; }
	public int getHitSum() { return hitcnt + nothitcnt; }
	public int getHitPercent() {
		int sum = getHitSum();
		if (sum == 0) return 0;
		return (int)(((double)hitcnt/sum)*100);
	}

	public void setShootToX(int sx) { this.sx = sx; }
	public void setShootToY(int sy) { this.sy = sy; }
	public int getShootToX() { return sx; }
	public int getShootToY() { return sy; }

	public void setMovingX(int mx) { this.mx = mx; }
	public void setMovingY(int my) { this.my = my; }
	public int getMovingX() { return mx; }
	public int getMovingY() { return my; }

	public void setTeam(int team) { this.team = team; }
	public int getTeam() { return team; }

	public boolean isAlive() { return damage < MAX_DAMAGE; }
	public boolean isDestroyed() { return damage >= MAX_DAMAGE; }

	public Weapon getWeapon() {
		if (isAlive()) {
			return weapon;
		} else {
			// ���ɔj�󂳂�Ă���̂Ń_�~�[��Weapon��Ԃ�
			return new Weapon(this.shooting, this) {
				public void shoot() {}
			};
		}
	}

	public boolean canMoveTo(int x, int y, int width, int height) {
		if (x < 0 || y < 0) return false;
		if (x+width > shooting.getWidth() || y > shooting.getHeight()) return false;
		return true;
	}

	public Player(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this.shooting = shooting;
		this.team = team;
		this.x = x; this.y = y;
		this.sx = sx; this.sy = sy;
		this.weapon = new Weapon(this.shooting, this);
	}

	public void addPlayerListener(PlayerListener listener) {
		listeners.add(listener);
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
		if (isAlive()) {
			g.setColor(Color.WHITE);
			g.fillRect(getX(), getY(), WIDTH, HEIGHT);

			if (shooting.isKeyseqOn()) {
				g.setColor(Color.RED);
				g.drawString(damage + "/" + MAX_DAMAGE, getX()+5, getY()+5);
			}
		}
	}
	
	// �����蔻��
	public boolean isHit(Lazer lazer) {
		if (isDestroyed()) return false;
		if (getTeam() == lazer.getPlayer().getTeam()) return false;
		if (lazer.getY()+lazer.getHeight() < getY()) return false;
		if (lazer.getY() > getY()+getHeight()) return false;
		if (lazer.getX()+lazer.getWidth() < getX()) return false;
		if (lazer.getX() > getX()+getWidth()) return false;
		return true;
	}

	// �G�̒e����������
	public void onHit(Lazer lazer) {
		setDamage(getDamage() + lazer.DAMAGE);
		setCombo(0);
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
			getWeapon().shoot();
		}
	}

	public void keyTyped(KeyEvent e) {}

	// �������������e����������
	public void lazerHit() {
		setCombo(getCombo() + 1);
		setScore(getScore() + 10 * getCombo());
		hitcnt++;
	}

	// �������������e����������
	public void lazerNotHit() {
		setCombo(0);
		nothitcnt++;
	}
}

class AutoPlayer extends Player {
	public static final int MAX_DAMAGE = 30;

	public AutoPlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setMovingX(1);
	}

	private static final int moveInterval = 5;
	private int moveIntervalCnt = 0;
	public void runMainLoopJob() {
		if ((int)(Math.random()*1000) == 0) {
			getWeapon().shoot();
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

interface PlayerListener {
	public void scoreUpdated();
	public void damageUpdated();
	public void comboUpdated();
	public void playerDestroyed();
}

class PlayerAdapter implements PlayerListener {
	public void scoreUpdated() {}
	public void damageUpdated() {}
	public void comboUpdated() {}
	public void playerDestroyed() {}
}

