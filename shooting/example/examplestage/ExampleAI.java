package shooting.example.examplestage;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shooting.core.*;

/// AI�̃T���v��
public class ExampleAI extends Player {
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
	/// �����ł͈ړ��ƃ��[�U�̔��ˏ������s���Ă���
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
		}

		// 1/400�̊m���ŕ����]������
		if ((int)(Math.random()*400) == 0) {
			setMovingX(-getMovingX());
		}

		for (Lazer lazer : getShooting().getLazers()) {
			if (lazer.getPlayer().getTeam() != this.getTeam()) {
				// 100px�ȓ��ɓG��Lazer���߂Â��Ă��Ă���������悤�Ƃ���
				if (lazer.getY()-(getY()+getHeight()) <= 100) {
					if (lazer.getX() >= this.getX()-20 && lazer.getX()+lazer.getWidth() <= this.getX()+this.getWidth()+20) {
						setMovingX(lazer.getX() < this.getCenterX() ? 1 : -1);
					}
				}
			}
		}

		// ����ȏ㍡�̌����֐i�߂Ȃ��Ȃ���������]������
		if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
			setMovingX(-getMovingX());
		}
		setX(getX()+getMovingX());
	}
}

