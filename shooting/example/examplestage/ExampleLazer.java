package shooting.example.examplestage;

import shooting.core.*;

/// Lazer�̃T���v��
public class ExampleLazer extends Lazer {
	public static final int WIDTH = 10;	///< ��
	public static final int HEIGHT = 20;	///< ����
	public static final int DAMAGE = 30;	///< �^����_���[�W
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
	public ExampleLazer(Player player, int x, int y, int sx, int sy) {
		super(player, x, y, sx, sy);
	}
}

