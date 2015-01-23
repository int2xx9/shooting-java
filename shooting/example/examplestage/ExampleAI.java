package shooting.example.examplestage;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shooting.core.*;

/// AI�̃T���v��
public class ExampleAI extends Player implements PlayerListener {
	public static final int DEFAULT_WIDTH = 30;	///< �f�t�H���g�̕�
	public static final int DEFAULT_HEIGHT = 30;	///< �f�t�H���g�̍���
	public static final int MAX_DAMAGE = 100;	///< �ő�_���[�W
	/// ���̎擾
	/// @return ��
	public int getWidth() { return getImage() != null ? getImage().getWidth(null) : DEFAULT_WIDTH; }
	/// �����̎擾
	/// @return ����
	public int getHeight() { return getImage() != null ? getImage().getHeight(null) : DEFAULT_HEIGHT; }
	/// �ő�_���[�W�̎擾
	/// @return �ő�_���[�W
	public int getMaxDamage() { return MAX_DAMAGE; }
	private Weapon subWeapon;	///< Player.weapon�Ƃ͕ʂ�Weapon

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param team �`�[���ԍ�
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public ExampleAI(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this(shooting, null, team, x, y, sx, sy);
	}

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param image �@�̂̉摜
	/// @param team �`�[���ԍ�
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public ExampleAI(Shooting shooting, Image image, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setImage(image);
		setWeapon(new ExampleWeapon(this));
		setMovingX(1);
		subWeapon = new DefaultWeapon(this);
	}

	/// ���C�����[�v����Ă΂�鏈��
	///
	/// �����ł͈ړ��ƒe�̔��ˏ������s���Ă���
	public void runMainLoopJob() {
		// subWeapon�͌��Ă鎞�͖��񌂂�
		subWeapon.shoot();
		if (getWeapon().isCharged()) {
			// Weapon���g�p�ł���ꍇ�A�G��AI����_����ʒu�ɂ��邩�𔻒f���Ă��猂��
			for (Player player : getShooting().getPlayers()) {
				if (player.getTeam() != this.getTeam()) {
					int shootxpos = this.getX() + this.getWidth()/2;
					if (player.getMovingX() > 0) {
						// �E�Ɉړ����Ă����ꍇ�̕␳
						shootxpos -= 50;
					} else if (player.getMovingX() < 0) {
						// ���Ɉړ����Ă����ꍇ�̕␳
						shootxpos += 50;
					}
					if (shootxpos >= player.getX() && shootxpos <= player.getX()+player.getWidth()) {
						getWeapon().shoot();
					}
				}
		}

		// 1/400�̊m���ŕ����]������
		if ((int)(Math.random()*400) == 0) {
			setMovingX(-getMovingX());
		}

		// ����ȏ㍡�̌����֐i�߂Ȃ��Ȃ���������]������
		if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
			setMovingX(-getMovingX());
		}
		setX(getX()+getMovingX());
	}

	/// �X�R�A���X�V���ꂽ
	public void scoreUpdated() {
	}

	/// �_���[�W���X�V���ꂽ
	public void damageUpdated() {
	}

	/// �R���{�����X�V���ꂽ
	public void comboUpdated() {
	}

	/// �e�����������񐔂��X�V���ꂽ
	public void hitCountUpdated() {
		// 1/10�̊m���ŋt�����Ɉړ�����
		if ((int)(Math.random()*10) == 0) {
			setMovingX(-getMovingX());
		}
	}

	/// �e���O�ꂽ�񐔂��X�V���ꂽ
	public void notHitCountUpdated() {
	}

	/// �j�󂳂ꂽ
	public void playerDestroyed() {
	}
}

