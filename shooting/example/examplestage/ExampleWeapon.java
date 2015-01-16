package shooting.example.examplestage;

import shooting.core.*;

/// Weapon�̃T���v��
public class ExampleWeapon extends Weapon {
	/// LazerGenerator�̃T���v��
	class ExampleLazerGenerator extends LazerGenerator {
		/// �R���X�g���N�^
		/// @param player ���ˌ�Player
		public ExampleLazerGenerator(Player player) {
			super(player);
		}
		/// ExampleLazer�̐���
		/// @param x ��������X���W�̈ʒu
		/// @param y ��������Y���W�̈ʒu
		/// @param sx X���̔��˂�������Ƒ��x
		/// @param sy Y���̔��˂�������Ƒ��x
		/// @return Lazer�I�u�W�F�N�g
		public Lazer generateLazer(int x, int y, int sx, int sy) {
			return new ExampleLazer(getPlayer(), x, y, sx, sy);
		}
	};

	public static final int INTERVAL = 3000;	///< ���ˉ\�ɂȂ�܂ł̊Ԋu
	/// ���ˉ\�ɂȂ�܂ł̊Ԋu�̎擾
	/// @return ���ˉ\�ɂȂ�܂ł̊Ԋu
	public int getInterval() { return INTERVAL; }
	/// Generator�̎擾
	/// @param player ���ˌ��ƂȂ�Player
	/// @return Player�𔭎ˌ��Ƃ���LazerGenerator�̃I�u�W�F�N�g
	public LazerGenerator getGenerator(Player player) {
		return new ExampleLazerGenerator(player);
	}

	/// �R���X�g���N�^
	/// @param player ���ˌ��ƂȂ�Player
	public ExampleWeapon(Player player) {
		super(player);
	}
}

