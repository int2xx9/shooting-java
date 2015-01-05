package shooting.core;

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
		public void setHitCount(int value) {
			this.hitCount = value;
			for (PlayerListener listener : listeners) {
				listener.hitCountUpdated();
			}
		}
		public void setNotHitCount(int value) {
			this.notHitCount = value;
			for (PlayerListener listener : listeners) {
				listener.notHitCountUpdated();
			}
		}
		public void incrementHitCount() { setHitCount(getHitCount() + 1); }
		public void incrementNotHitCount() { setNotHitCount(getNotHitCount() + 1); }
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
	private int initialX, initialY;
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
		private static final Weapon dummyWeapon = DummyWeapon.getInstance();
		public void setWeapon(Weapon value) { this.weapon = value; }
		// ”j‰óÏ‚İ‚Å‚ ‚ê‚Î”­Ë•s‰Â”\‚Èƒ_ƒ~[‚ÌWeapon‚ğ•Ô‚·
		public Weapon getWeapon() { return isAlive() ? this.weapon : dummyWeapon; }

	public Player(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this.shooting = shooting;
		this.team = team;
		this.initialX = this.x = x;
		this.initialY = this.y = y;
		this.sx = sx; this.sy = sy;
		this.weapon = dummyWeapon;
	}

	public void initialize() {
		this.score = this.damage = this.combo =
			this.hitCount = this.notHitCount = 0;
		this.x = this.initialX;
		this.y = this.initialY;
		this.weapon.initialize();
	}

	public void addPlayerListener(PlayerListener listener) {
		listeners.add(listener);
	}

	// w’è‚µ‚½ˆÊ’u‚ÉˆÚ“®‰Â”\‚©
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

	// “–‚½‚è”»’è
	public boolean isHit(Lazer lazer) {
		if (isDestroyed()) return false;
		if (getTeam() == lazer.getPlayer().getTeam()) return false;
		if (lazer.getY()+lazer.getHeight() < getY()) return false;
		if (lazer.getY() > getY()+getHeight()) return false;
		if (lazer.getX()+lazer.getWidth() < getX()) return false;
		if (lazer.getX() > getX()+getWidth()) return false;
		return true;
	}

	// ’e‚ª©•ª‚É“–‚½‚Á‚½
	public void onHit(Lazer lazer) {
		setDamage(getDamage() + lazer.getDamage());
		setCombo(0);
	}

	// ©•ª‚ªŒ‚‚Á‚½’e‚ª“G‚É“–‚½‚Á‚½
	public void lazerHit() {
		setCombo(getCombo() + 1);
		setScore(getScore() + 10 * getCombo());
		incrementHitCount();
	}

	// ©•ª‚ªŒ‚‚Á‚½’e‚ª“G‚É“–‚½‚ç‚È‚©‚Á‚½
	public void lazerNotHit() {
		setCombo(0);
		incrementNotHitCount();
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

