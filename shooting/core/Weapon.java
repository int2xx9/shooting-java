package shooting.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// Player���g�p����Weapon
public abstract class Weapon implements MainLoopJob {
	/// ����Weapon�͔��ˉ\�ɂȂ邱�Ƃ͂Ȃ����Ƃ�����
	public static final int INTERVAL_INFINITY = -1;

	/// ���ˉ\�ɂȂ�܂ł̊Ԋu�̎擾
	/// @return ���ˉ\�ɂȂ�܂ł̊Ԋu
	abstract public int getInterval();
	/// LazerGenerator�̎擾
	/// @return LazerGenerator�̃I�u�W�F�N�g
	abstract public LazerGenerator getGenerator(Player player);

	private int loopCount = 0;	///< ���ˊԊu�𐧌䂷�邽�߂̃J�E���^
	private Player player;	///< ����Weapon�ɕR�t�����Ă���Player
	private boolean initialCharged;	///< charged�̏����l
	private boolean charged;	///< ���ˉ\���ǂ���
		/// ���ˉ\���ǂ���
		/// @return ���ˉ\�ȏꍇtrue, �s�\�ȏꍇfalse
		public boolean isCharged() { return this.charged; }

	/// �R���X�g���N�^
	/// @param player Player�̃I�u�W�F�N�g
	public Weapon(Player player) {
		this(player, true);
	}

	/// �R���X�g���N�^
	/// @param player Player�̃I�u�W�F�N�g
	/// @param charged charged�̏�Ԃ�true�ɂ��邩false�ɂ��邩
	public Weapon(Player player, boolean charged) {
		this.player = player;
		this.initialCharged = charged;
		// INTERVAL_INFINITY�̏ꍇ�͈Ӗ����Ȃ��̂Ń��C�����[�v�ɓo�^���Ȃ�
		if (getInterval() != INTERVAL_INFINITY) {
			this.player.getShooting().addMainLoopJob(this);
		}
		initialize();
	}

	/// ������
	public void initialize() {
		// INTERVAL_INFINITY�łȂ��ꍇ�̂ݔ��ˉ\�ȏ�Ԃŏ���������
		this.charged = this.initialCharged;
		this.loopCount = 0;
	}

	/// ���[�U�̔���
	public void shoot() {
		if (player != null && player.getShooting().isRunning() && charged) {
			loopCount = 0;
			charged = false;
			Lazer lazer = null;
			if (player.getShootToY() < 0) {
				// �����
				lazer = getGenerator(player).generateLazer(
						player.getX()+player.getWidth()/2, player.getY(),
						player.getShootToX(), player.getShootToY());
			} else if (player.getShootToY() > 0) {
				// ������
				lazer = getGenerator(player).generateLazer(
						player.getX()+player.getWidth()/2, player.getY()+player.getHeight(),
						player.getShootToX(), player.getShootToY());
			}
			if (lazer != null) {
				player.getShooting().lazers.shoot(lazer);
			}
		}
	}

	/// ���C�����[�v����Ă΂�鏈��
	///
	/// �����ł͔��˂ł��邩�ǂ����̐�����s���Ă���
	public void runMainLoopJob() {
		if (!charged) {
			loopCount = (loopCount + 1) % getInterval();
			if (loopCount == 0) {
				charged = true;
			}
		}
	}
}

/// ���[�U�̔��˂��ł��Ȃ��_�~�[��Weapon
///
/// �ǂ̃I�u�W�F�N�g�ł����ʂ̂��̂��g�p���邽�߃V���O���g���ɂ��Ă���
class DummyWeapon extends Weapon {
	/// ���̃N���X�̃C���X�^���X
	private static DummyWeapon instance = new DummyWeapon();
	/// ���ˉ\�ɂȂ�܂ł̊Ԋu�̎擾
	///
	/// ���ˉ\�ɂȂ邱�Ƃ͂Ȃ��̂�Weapon.INTERVAL_INFINITY��Ԃ�
	/// @return Weapon.INTERVAL_INFINITY
	public int getInterval() { return Weapon.INTERVAL_INFINITY; }
	/// LazerGenerator�̎擾
	/// DummyWeapon�ł͎g�p���Ȃ��̂�null��Ԃ�
	/// @return null
	public LazerGenerator getGenerator(Player player) { return null; }
	/// �R���X�g���N�^
	///
	/// �O������C���X�^���X���ł��Ȃ��悤��private�ɂ��Ă���
	private DummyWeapon() { super(null); }
	/// DummyWeapon�C���X�^���X�̎擾
	/// @return DummyWeapon�̃C���X�^���X
	static DummyWeapon getInstance() { return instance; }
}

