package shooting.core;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// ���[�U������ł���Player
public class ControllablePlayer extends Player {
	public static final int DEFAULT_WIDTH = 25;	///< �f�t�H���g�̕�
	public static final int DEFAULT_HEIGHT = 25;	///< �f�t�H���g�̍���
	public static final int MAX_DAMAGE = 50;	///< �ő�_���[�W
	/// ���̎擾
	/// @return ��
	public int getWidth() { return getImage() != null ? getImage().getWidth(null) : DEFAULT_WIDTH; }
	/// �����̎擾
	/// @return ����
	public int getHeight() { return getImage() != null ? getImage().getHeight(null) : DEFAULT_HEIGHT; }
	/// �ő�_���[�W�̎擾
	/// @return �ő�_���[�W
	public int getMaxDamage() { return MAX_DAMAGE; }

	/// �L�[�ݒ�
	public ControllablePlayerConfig keyConfig = new ControllablePlayerConfig();
	/// �L�[�ݒ�̎擾
	/// @return �L�[�ݒ�
	public ControllablePlayerConfig getKeyConfig() { return keyConfig; }

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param team �`�[���ԍ�
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public ControllablePlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
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
	public ControllablePlayer(Shooting shooting, Image image, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setImage(image);
		setWeapon(new DefaultWeapon(this));
	}

	/// ������
	public void initialize() {
		super.initialize();
		setMovingX(0);
		setMovingY(0);
	}

	/// ���C�����[�v����Ă΂ꂽ�����x�Ɉ�x�������s����
	private static final int moveInterval = 5;
	/// moveInterval��Ɉ�x���s�𐧌䂷�邽�߂̃J�E���^
	private int moveIntervalCnt = 0;
	/// ���C�����[�v����Ă΂�鏈��
	///
	/// �����ł͈ړ��������s���Ă���
	public void runMainLoopJob() {
		if (moveIntervalCnt == moveInterval) {
			setX(getX() + getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	/// �L�[�������ꂽ�ꍇ�̏���
	/// @param e KeyEvent�̃I�u�W�F�N�g
	public void keyPressed(KeyEvent e) {
		if (keyConfig.isMoveLeftKey(e.getKeyCode(), e.getKeyChar())) {
			setMovingX(-1);
		} else if (keyConfig.isMoveRightKey(e.getKeyCode(), e.getKeyChar())) {
			setMovingX(1);
		}
	}

	/// �L�[�������ꂽ�ꍇ�̏���
	/// @param e KeyEvent�̃I�u�W�F�N�g
	public void keyReleased(KeyEvent e) {
		// �ړ��֌W�̃L�[�������ꂽ�ꍇ�͗����ꂽ�L�[�ƈړ����̕������������ǂ������m�F���A
		// �����ł���ꍇ�݈̂ړ�����߂�
		if (keyConfig.isMoveLeftKey(e.getKeyCode(), e.getKeyChar()) && getMovingX() < 0) {
			setMovingX(0);
		} else if (keyConfig.isMoveRightKey(e.getKeyCode(), e.getKeyChar()) && getMovingX() > 0) {
			setMovingX(0);
		} else if (keyConfig.isShootKey(e.getKeyCode(), e.getKeyChar())) {
			getWeapon().shoot();
		}
	}
}

