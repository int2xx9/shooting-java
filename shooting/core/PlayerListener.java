package shooting.core;

/// �v���C���[�֌W�̃C�x���g�̃��X�i
public interface PlayerListener {
	/// �X�R�A���X�V���ꂽ
	public void scoreUpdated();
	/// �_���[�W���X�V���ꂽ
	public void damageUpdated();
	/// �R���{�����X�V���ꂽ
	public void comboUpdated();
	/// �e�����������񐔂��X�V���ꂽ
	public void hitCountUpdated();
	/// �e���O�ꂽ�񐔂��X�V���ꂽ
	public void notHitCountUpdated();
	/// �j�󂳂ꂽ
	public void playerDestroyed();
}

