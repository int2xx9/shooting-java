package shooting.core;

/// shooting.core�p�b�P�[�W�̒��Ńf�t�H���g�Ŏg�p�����Lazer
public class DefaultLazer extends Lazer {
	public static final int WIDTH = 2;	///< ��
	public static final int HEIGHT = 10;	///< ����
	public static final int DAMAGE = 10;	///< �^����_���[�W
	/// ���̎擾
	/// @return ��
	public int getWidth() { return WIDTH; }
	/// �����̎擾
	/// @return ����
	public int getHeight() { return HEIGHT; }
	/// �^����_���[�W�̎擾
	/// @return �^����_���[�W
	public int getDamage() { return DAMAGE; }

	/// �R���X�g���N�^
	/// @param x ��������X���W�̈ʒu
	/// @param y ��������Y���W�̈ʒu
	/// @param sx X���̔��˂�������Ƒ��x
	/// @param sy Y���̔��˂�������Ƒ��x
	public DefaultLazer(Player player, int x, int y, int sx, int sy) {
		super(player, x, y, sx, sy*8);
	}
}

