package shooting.core;

/// Lazer�𐶐�����N���X
public abstract class LazerGenerator {
	private Player player;	///< ���ˌ��ƂȂ�Player
		/// ���ˌ��ƂȂ�Player�̎擾
		/// @return ���ˌ��ƂȂ�Player
		public Player getPlayer() { return this.player; }

	/// �R���X�g���N�^
	/// @param player ���ˌ��ƂȂ�Player
	public LazerGenerator(Player player) {
		this.player = player;
	}

	/// Lazer�̐���
	/// @param x ��������X���W�̈ʒu
	/// @param y ��������Y���W�̈ʒu
	/// @param sx X���̔��˂�������Ƒ��x
	/// @param sy Y���̔��˂�������Ƒ��x
	/// @return Lazer�I�u�W�F�N�g
	abstract public Lazer generateLazer(int x, int y, int sx, int sy);
}

