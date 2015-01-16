package shooting.core;

/// shooting.core�p�b�P�[�W�̒��Ńf�t�H���g�Ŏg�p�����Weapon
public class DefaultWeapon extends Weapon {
	/// DefaultLazer�pGenerator
	class DefaultLazerGenerator extends LazerGenerator {
		/// �R���X�g���N�^
		/// @param player ���ˌ�Player
		public DefaultLazerGenerator(Player player) {
			super(player);
		}
		/// Lazer�̐���
		/// @param x ��������X���W�̈ʒu
		/// @param y ��������Y���W�̈ʒu
		/// @param sx X���̔��˂�������Ƒ��x
		/// @param sy Y���̔��˂�������Ƒ��x
		/// @return Lazer�I�u�W�F�N�g
		public Lazer generateLazer(int x, int y, int sx, int sy) {
			return new DefaultLazer(getPlayer(), x, y, sx, sy);
		}
	};

	public static final int INTERVAL = 500;	///< ���ˉ\�ɂȂ�܂ł̊Ԋu
	/// ���ˉ\�ɂȂ�܂ł̊Ԋu�̎擾
	/// @return ���ˉ\�ɂȂ�܂ł̊Ԋu
	public int getInterval() { return INTERVAL; }
	/// Generator�̎擾
	/// @param player ���ˌ��ƂȂ�Player
	/// @return Player�𔭎ˌ��Ƃ���LazerGenerator�̃I�u�W�F�N�g
	public LazerGenerator getGenerator(Player player) {
		return new DefaultLazerGenerator(player);
	}

	/// �R���X�g���N�^
	/// @param player ���ˌ��ƂȂ�Player
	public DefaultWeapon(Player player) {
		super(player);
	}
}

