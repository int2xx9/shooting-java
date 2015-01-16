package shooting.core;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// AI���邢�͎蓮�ő�����s���v���C���[�̊��N���X
public abstract class Player implements MainLoopJob, ShootingObject, KeyListener, LazerListener {
	/// ���̎擾
	/// @return ��
	abstract public int getWidth();
	/// �����̎擾
	/// @return ����
	abstract public int getHeight();
	/// �ő�_���[�W�̎擾
	/// @return �ő�_���[�W
	abstract public int getMaxDamage();

	/// �o�^����Ă���PlayerListener�̃��X�g
	private LinkedList<PlayerListener> listeners = new LinkedList<PlayerListener>();

	private Shooting shooting;	///< ����Player�ɕR�t�����Ă���shooting
		/// �ݒ肳��Ă���shooting�̎擾
		/// @return �ݒ肳��Ă���shooting
		public Shooting getShooting() { return this.shooting; }
	private int damage;	///< ���ݎ󂯂Ă���_���[�W
		/// �_���[�W�̐ݒ�
		/// @param value �V�����ݒ肷��_���[�W
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
		/// �_���[�W�̎擾
		/// @return ���݂̃_���[�W
		public int getDamage() { return this.damage; }
		/// �������Ă��邩�ǂ���
		/// @return �������Ă��鎞true, ����ȊOfalse
		public boolean isAlive() { return this.damage < getMaxDamage(); }
		/// �j�󂳂ꂽ���ǂ���
		/// @return �j�󂳂�Ă��鎞true, ����ȊOfalse
		public boolean isDestroyed() { return this.damage >= getMaxDamage(); }
	private int score;	///< ���݂̃X�R�A
		/// �X�R�A�̐ݒ�
		/// @param value �V�����ݒ肷��X�R�A
		public void setScore(int value) {
			this.score = value;
			for (PlayerListener listener : listeners) {
				listener.scoreUpdated();
			}
		}
		/// �X�R�A�̎擾
		/// @return ���݂̃X�R�A
		public int getScore() { return this.score; }
	private int combo;	///< ���݂̃R���{��
		/// �R���{���̐ݒ�
		/// @param value �V�����ݒ肷��R���{��
		public void setCombo(int value) {
			this.combo = value;
			for (PlayerListener listener : listeners) {
				listener.comboUpdated();
			}
		}
		/// �R���{���̃C���N�������g
		public void incrementCombo() { setCombo(getCombo() + 1); }
		/// �R���{���̎擾
		/// @return ���݂̃R���{��
		public int getCombo() { return this.combo; }
	private int hitCount;	///< ���݂܂łɒe������������
	private int notHitCount;	///< ���݂܂łɒe���O�ꂽ��
		/// �e�����������񐔂̐ݒ�
		/// @param value �V�����ݒ肷��e������������
		public void setHitCount(int value) {
			this.hitCount = value;
			for (PlayerListener listener : listeners) {
				listener.hitCountUpdated();
			}
		}
		/// �e���O�ꂽ�񐔂̐ݒ�
		/// @param value �V�����ݒ肷��e���͂��ꂽ��
		public void setNotHitCount(int value) {
			this.notHitCount = value;
			for (PlayerListener listener : listeners) {
				listener.notHitCountUpdated();
			}
		}
		/// �e�����������񐔂̃C���N�������g
		public void incrementHitCount() { setHitCount(getHitCount() + 1); }
		/// �e���O�ꂽ�񐔂̃C���N�������g
		public void incrementNotHitCount() { setNotHitCount(getNotHitCount() + 1); }
		/// �e�����������񐔂̎擾
		/// @return ���݂܂łɒe������������
		public int getHitCount() { return this.hitCount; }
		/// �e���O�ꂽ�񐔂̎擾
		/// @return ���݂܂łɒe���͂��ꂽ��
		public int getNotHitCount() { return this.notHitCount; }
		/// �������̎擾
		/// @return ������
		public int getHitPercent() {
			if (hitCount+notHitCount == 0) return 0;
			return (int)(((double)hitCount)/(hitCount+notHitCount)*100);
		}
		/// �e���͂��ꂽ�����̎擾
		/// @return ���݂܂łɒe���͂��ꂽ����(�S����)
		public int getNotHitPercent() {
			if (hitCount+notHitCount == 0) return 0;
			return (int)(((double)notHitCount)/(hitCount+notHitCount)*100);
		}
	private int initialX;	///< �ŏ��Ɏw�肳�ꂽ����X���W
	private int initialY;	///< �ŏ��Ɏw�肳�ꂽ����Y���W
	private int x;	///<����X���W
	private int y;	///<����Y���W
		/// ���S���W��X��ݒ�
		/// @param value �V�����ݒ肷�钆�SX���W
		public void setCenterX(int value) { setX(value-this.getWidth()/2); }
		/// ���S���W��Y��ݒ�
		/// @param value �V�����ݒ肷�钆�SY���W
		public void setCenterY(int value) { setY(value-this.getHeight()/2); }
		/// ������W��X��ݒ�
		/// @param x �V�����ݒ肷�鍶��X���W
		public void setX(int x) {
			if (canMoveTo(x, this.y, getWidth(), getHeight())) {
				this.x = x;
			}
		}
		/// ������W��Y��ݒ�
		/// @param y �V�����ݒ肷�鍶��Y���W
		public void setY(int y) {
			if (canMoveTo(this.y, y, getWidth(), getHeight())) {
				this.y = y;
			}
		}
		/// ���S���W��X���擾
		/// @return ���݂̒��SX���W
		public int getCenterX() { return this.x-this.getWidth()/2; }
		/// ���S���W��Y���擾
		/// @return ���݂̒��SY���W
		public int getCenterY() { return this.y-this.getHeight()/2; }
		/// ������W��X���擾
		/// @return ���݂̍���X���W
		public int getX() { return this.x; }
		/// ������W��Y���擾
		/// @return ���݂̍���Y���W
		public int getY() { return this.y; }
	private int sx;	///< X���̒e���������E����
	private int sy;	///< Y���̒e���������E����
		/// �e���������E������X���W��ݒ�
		/// @param value X���̒e���������E�������������l
		public void setShootToX(int value) { this.sx = value; }
		/// �e���������E������Y���W��ݒ�
		/// @param value Y���̒e���������E�������������l
		public void setShootToY(int value) { this.sy = value; }
		/// �e���������E������X���W���擾
		/// @return X���̒e���������E�������������l
		public int getShootToX() { return this.sx; }
		/// �e���������E������Y���W���擾
		/// @return Y���̒e���������E�������������l
		public int getShootToY() { return this.sy; }
	private int mx;	///< X���̈ړ������E��
	private int my;	///< Y���̈ړ������E��
		/// �ړ��̕����E�ʂ�X���W��ݒ�
		/// @param value X���̈ړ������E�ʂ�\�����l
		public void setMovingX(int value) { this.mx = value; }
		/// �ړ��̕����E�ʂ�Y���W��ݒ�
		/// @param value Y���̈ړ������E�ʂ�\�����l
		public void setMovingY(int value) { this.my = value; }
		/// �ړ��̕����E�ʂ�X���W���擾
		/// @return X���̈ړ������E�ʂ�\�����l
		public int getMovingX() { return this.mx; }
		/// �ړ��̕����E�ʂ�Y���W���擾
		/// @return Y���̈ړ������E�ʂ�\�����l
		public int getMovingY() { return this.my; }
	private int team;	///< �`�[���ԍ�
		/// �`�[���ԍ��̐ݒ�
		/// @param value �ݒ肷��`�[���ԍ�
		public void setTeam(int value) { this.team = value; }
		/// �`�[���ԍ��̎擾
		/// @return �ݒ肳��Ă���`�[���ԍ�
		public int getTeam() { return this.team; }
	private Weapon weapon;	///< �R�t�����Ă��镐��
		private static final Weapon dummyWeapon = DummyWeapon.getInstance();
		/// ����̐ݒ�
		/// @param value �ݒ肷�镐��
		public void setWeapon(Weapon value) { this.weapon = value; }
		/// ����̎擾
		/// @return �ݒ肳��Ă��镐��
		/// @return �j��ς݂ł���Δ��˕s�\�ȃ_�~�[��Weapon��Ԃ�
		public Weapon getWeapon() { return isAlive() ? this.weapon : dummyWeapon; }

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public Player(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this.shooting = shooting;
		this.team = team;
		this.initialX = this.x = x;
		this.initialY = this.y = y;
		this.sx = sx; this.sy = sy;
		this.weapon = dummyWeapon;
	}

	/// ������
	public void initialize() {
		this.score = this.damage = this.combo =
			this.hitCount = this.notHitCount = 0;
		this.x = this.initialX;
		this.y = this.initialY;
		this.weapon.initialize();
	}

	/// PlayerListener�̓o�^
	/// @param listener PlayerListener�̃I�u�W�F�N�g
	public void addPlayerListener(PlayerListener listener) {
		listeners.add(listener);
	}

	/// �w�肵���ʒu�Ɉړ��\��
	/// @param x ����X���W
	/// @param y ����Y���W
	/// @param width ��
	/// @param height ����
	/// @return �ړ��\�ȏꍇtrue, �s�\�ȏꍇfalse
	public boolean canMoveTo(int x, int y, int width, int height) {
		if (x < 0 || y < 0) return false;
		if (x+width > shooting.getWidth() || y > shooting.getHeight()) return false;
		return true;
	}

	/// �I�u�W�F�N�g�̕`��
	/// @param g
	public void paintObject(Graphics g) {
		if (isAlive()) {
			g.setColor(Color.WHITE);
			g.fillRect(getX(), getY(), getWidth(), getHeight());

			if (shooting.isKeyseqOn()) {
				g.setColor(Color.RED);
				g.drawString((getWeapon().isCharged() ? "*" : "-") + damage + "/" + getMaxDamage(), getX()+5, getY()+5);
			}
		}
	}

	/// �����蔻��
	/// @param lazer ������s��Lazer�I�u�W�F�N�g
	/// @return �������Ă����ꍇtrue, �O��Ă����ꍇfalse
	public boolean isHit(Lazer lazer) {
		if (isDestroyed()) return false;
		if (getTeam() == lazer.getPlayer().getTeam()) return false;
		if (lazer.getY()+lazer.getHeight() < getY()) return false;
		if (lazer.getY() > getY()+getHeight()) return false;
		if (lazer.getX()+lazer.getWidth() < getX()) return false;
		if (lazer.getX() > getX()+getWidth()) return false;
		return true;
	}

	/// �e�������ɓ�������
	/// @param lazer ��������Lazer�I�u�W�F�N�g
	public void onHit(Lazer lazer) {
		setDamage(getDamage() + lazer.getDamage());
		setCombo(0);
	}

	/// �������������e���G�ɓ�������
	public void lazerHit() {
		setCombo(getCombo() + 1);
		setScore(getScore() + 10 * getCombo());
		incrementHitCount();
	}

	/// �������������e���G�ɓ�����Ȃ�����
	public void lazerNotHit() {
		setCombo(0);
		incrementNotHitCount();
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

