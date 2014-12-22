package shooting;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Player implements MainLoopJob, ShootingObject, KeyListener, LazerListener {
	abstract public int getWidth();
	abstract public int getHeight();
	abstract public int getMaxDamage();

	private LinkedList<PlayerListener> listeners = new LinkedList<PlayerListener>();

	private Shooting shooting;
		public Shooting getShooting() { return this.shooting; }
	private int damage;
		public void setDamage(int value) {
			this.damage = value;
			for (PlayerListener listener : listeners) {
				listener.damageUpdated();
			}
			if (this.damage >= getMaxDamage()) {
				for (PlayerListener listener : listeners) {
					listener.playerDestroyed();
				}
			}
		}
		public int getDamage() { return this.damage; }
		public boolean isAlive() { return this.damage < getMaxDamage(); }
		public boolean isDestroyed() { return this.damage >= getMaxDamage(); }
	private int score;
		public void setScore(int value) {
			this.score = value;
			for (PlayerListener listener : listeners) {
				listener.scoreUpdated();
			}
		}
		public int getScore() { return this.score; }
	private int combo;
		public void setCombo(int value) {
			this.combo = value;
			for (PlayerListener listener : listeners) {
				listener.comboUpdated();
			}
		}
		public void incrementCombo(int value) { setCombo(getCombo() + 1); }
		public int getCombo() { return this.combo; }
	private int hitCount, notHitCount;
		public void setHitCount(int value) { this.hitCount = value; }
		public void setNotHitCount(int value) { this.notHitCount = value; }
		public int getHitCount() { return this.hitCount; }
		public int getNotHitCount() { return this.notHitCount; }
		public int getHitPercent() {
			if (hitCount+notHitCount == 0) return 0;
			return (int)(((double)hitCount)/(hitCount+notHitCount)*100);
		}
		public int getNotHitPercent() {
			if (hitCount+notHitCount == 0) return 0;
			return (int)(((double)notHitCount)/(hitCount+notHitCount)*100);
		}
	private int x, y;
		public void setCenterX(int value) { setX(value-this.getWidth()/2); }
		public void setCenterY(int value) { setY(value-this.getHeight()/2); }
		public void setX(int x) {
			if (canMoveTo(x, this.y, getWidth(), getHeight())) {
				this.x = x;
			}
		}
		public void setY(int y) {
			if (canMoveTo(this.y, y, getWidth(), getHeight())) {
				this.y = y;
			}
		}
		public int getCenterX() { return this.x-this.getWidth()/2; }
		public int getCenterY() { return this.y-this.getHeight()/2; }
		public int getX() { return this.x; }
		public int getY() { return this.y; }
	private int sx, sy;
		public void setShootToX(int value) { this.sx = value; }
		public void setShootToY(int value) { this.sy = value; }
		public int getShootToX() { return this.sx; }
		public int getShootToY() { return this.sy; }
	private int mx, my;
		public void setMovingX(int value) { this.mx = value; }
		public void setMovingY(int value) { this.my = value; }
		public int getMovingX() { return this.mx; }
		public int getMovingY() { return this.my; }
	private int team;
		public void setTeam(int value) { this.team = value; }
		public int getTeam() { return this.team; }
	private Weapon weapon;
		private static final Weapon dummyWeapon = new DummyWeapon();
		public void setWeapon(Weapon value) { this.weapon = value; }
		// îjâÛçœÇ›Ç≈Ç†ÇÍÇŒî≠éÀïsâ¬î\Ç»É_É~Å[ÇÃWeaponÇï‘Ç∑
		public Weapon getWeapon() { return isAlive() ? this.weapon : dummyWeapon; }

	public Player(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this.shooting = shooting;
		this.team = team;
		this.x = x; this.y = y;
		this.sx = sx; this.sy = sy;
		this.weapon = dummyWeapon;
	}

	public void addPlayerListener(PlayerListener listener) {
		listeners.add(listener);
	}

	// éwíËÇµÇΩà íuÇ…à⁄ìÆâ¬î\Ç©
	public boolean canMoveTo(int x, int y, int width, int height) {
		if (x < 0 || y < 0) return false;
		if (x+width > shooting.getWidth() || y > shooting.getHeight()) return false;
		return true;
	}

	public void paintObject(Graphics g) {
		if (isAlive()) {
			g.setColor(Color.WHITE);
			g.fillRect(getX(), getY(), getWidth(), getHeight());

			if (shooting.isKeyseqOn()) {
				g.setColor(Color.RED);
				g.drawString(damage + "/" + getMaxDamage(), getX()+5, getY()+5);
			}
		}
	}

	// ìñÇΩÇËîªíË
	public boolean isHit(Lazer lazer) {
		if (isDestroyed()) return false;
		if (getTeam() == lazer.getPlayer().getTeam()) return false;
		if (lazer.getY()+lazer.getHeight() < getY()) return false;
		if (lazer.getY() > getY()+getHeight()) return false;
		if (lazer.getX()+lazer.getWidth() < getX()) return false;
		if (lazer.getX() > getX()+getWidth()) return false;
		return true;
	}

	// íeÇ™é©ï™Ç…ìñÇΩÇ¡ÇΩ
	public void onHit(Lazer lazer) {
		setDamage(getDamage() + lazer.DAMAGE);
		setCombo(0);
	}

	// é©ï™Ç™åÇÇ¡ÇΩíeÇ™ìGÇ…ìñÇΩÇ¡ÇΩ
	public void lazerHit() {
		setCombo(getCombo() + 1);
		setScore(getScore() + 10 * getCombo());
		hitCount++;
	}

	// é©ï™Ç™åÇÇ¡ÇΩíeÇ™ìGÇ…ìñÇΩÇÁÇ»Ç©Ç¡ÇΩ
	public void lazerNotHit() {
		setCombo(0);
		notHitCount++;
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

class ControlablePlayer extends Player {
	public static final int WIDTH = 25, HEIGHT = 25;
	public static final int MAX_DAMAGE = 50;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getMaxDamage() { return MAX_DAMAGE; }

	public ControlablePlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new Weapon(this));
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

class AutoPlayer extends Player {
	public static final int WIDTH = 25, HEIGHT = 25;
	public static final int MAX_DAMAGE = 30;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getMaxDamage() { return MAX_DAMAGE; }

	public AutoPlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new Weapon(this));
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

