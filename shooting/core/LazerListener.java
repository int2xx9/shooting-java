package shooting.core;

/// Lazer�֌W�̃C�x���g�̃��X�i
public interface LazerListener {
	/// ���������[�U�[���I������
	public void lazerHit();
	/// ���������[�U�[���͂��ꂽ
	/// (Player�N���X�̃I�u�W�F�N�g�ɂ�����Ȃ��܂܉�ʊO�܂ł�����)
	public void lazerNotHit();
}

