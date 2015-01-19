package shooting.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// Weapon�����˂���Lazer
public abstract class Lazer implements MainLoopJob, ShootingObject {
	/// ���̎擾
	/// @return ��
	abstract public int getWidth();
	/// �����̎擾
	/// @return ����
	abstract public int getHeight();
	/// �^����_���[�W�̎擾
	/// @return �^����_���[�W
	abstract public int getDamage();

	private Player player;	///< �R�t�����Ă���Player
		/// �R�t�����Ă���Player�̎擾
		/// @return �R�t�����Ă���Player
		public Player getPlayer() { return this.player; }
	private int x;	///< X���W
	private int y;	///< Y���W
		/// X���W�̎擾
		/// @return X���W
		public int getX() { return this.x; }
		/// Y���W�̎擾
		/// @return Y���W
		public int getY() { return this.y; }
	private int sx;	///< X���̈ړ������Ɨ�
	private int sy;	///< Y���̈ړ������Ɨ�
		/// X���̈ړ������Ɨʂ��擾
		/// @return X���̈ړ������Ɨ�
		public int getShootingToX() { return this.sx; }
		/// Y���̈ړ������Ɨʂ��擾
		/// @return Y���̈ړ������Ɨ�
		public int getShootingToY() { return this.sy; }

	/// �R���X�g���N�^
	/// @param x ��������X���W�̈ʒu
	/// @param y ��������Y���W�̈ʒu
	/// @param sx X���̔��˂�������Ƒ��x
	/// @param sy Y���̔��˂�������Ƒ��x
	public Lazer(Player player, int x, int y, int sx, int sy) {
		this.player = player;
		this.x = x-getWidth()/2; this.y = y;
		this.sx = sx; this.sy = sy;
	}

	/// ��ʊO�ɑ��݂��邩�ǂ���
	/// @return ��ʊO�ɑ��݂����ꍇtrue, �����łȂ��ꍇfalse
	public boolean isOutOfScreen() {
		return y+getHeight() < 0 || y > player.getShooting().getHeight();
	}

	/// ���C�����[�v����Ă΂�鏈��
	///
	/// �����ł͈ړ�
	public void runMainLoopJob() {
		x += sx;
		y += sy;
	}

	/// �I�u�W�F�N�g�̕`��
	/// @param g
	public void paintObject(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, getWidth(), getHeight());
	}

	/// �����蔻��
	///
	/// �Ƃ肠����Lazer�͓����蔻��������Ȃ��̂ŏ��false��Ԃ�
	/// @return false
	public boolean isHit(Lazer lazer) {
		return false;
	}
}

