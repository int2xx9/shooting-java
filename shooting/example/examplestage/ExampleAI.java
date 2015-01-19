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

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param team �`�[���ԍ�
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public ExampleAI(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, null, team, x, y, sx, sy);
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
	}

	/// ���C�����[�v����Ă΂ꂽ�����x�Ɉ�x�������s����
	private static final int moveInterval = 10;
	/// moveInterval��Ɉ�x���s�𐧌䂷�邽�߂̃J�E���^
	private int moveIntervalCnt = 0;
	/// ���C�����[�v����Ă΂�鏈��
	/// �����ł͈ړ��ƒe�̔��ˏ������s���Ă���
	public void runMainLoopJob() {
		// 1/1000�̊m���Œe�𔭎˂���
		// (������Weapon�����ˉ\��ԂłȂ���Δ��˂��Ȃ�)
		if ((int)(Math.random()*1000) == 0) {
			getWeapon().shoot();
		}
		if (moveIntervalCnt == moveInterval) {
			// ����ȏ㍡�̌����֐i�߂Ȃ��Ȃ���������]������
			if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
				setMovingX(-getMovingX());
			}
			setX(getX()+getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}
}

