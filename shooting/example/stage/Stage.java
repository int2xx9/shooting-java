package shooting.example.stage;

import shooting.core.*;

/// �X�e�[�W
public abstract class Stage {
	private Shooting shooting;	///< �X�e�[�W�ɕR�t����ꂽShooting�I�u�W�F�N�g
		/// �X�e�[�W�ɕR�t����ꂽshooting�I�u�W�F�N�g�̎擾
		/// @return �X�e�[�W�ɕR�t����ꂽShooting�I�u�W�F�N�g
		public Shooting getShooting() { return this.shooting; }

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public Stage(Shooting shooting) {
		this.shooting = shooting;
	}

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O
	abstract public String getStageName();
	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	abstract public Player[] getEnemies();
}

