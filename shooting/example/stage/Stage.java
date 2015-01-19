package shooting.example.stage;

import shooting.core.*;
import shooting.example.DocumentBaseImageLoader;

/// �X�e�[�W
public abstract class Stage {
	private Shooting shooting;	///< �X�e�[�W�ɕR�t����ꂽShooting�I�u�W�F�N�g
		/// �X�e�[�W�ɕR�t����ꂽshooting�I�u�W�F�N�g�̎擾
		/// @return �X�e�[�W�ɕR�t����ꂽShooting�I�u�W�F�N�g
		public Shooting getShooting() { return this.shooting; }

	/// DocumentBaseImageLoader�I�u�W�F�N�g
	private DocumentBaseImageLoader imgLoader = null;
		/// DocumentBaseImageLoader�̎擾
		/// @return DocumentBaseImageLoader�I�u�W�F�N�g
		public DocumentBaseImageLoader getImageLoader() { return this.imgLoader; }

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public Stage(Shooting shooting) {
		this.shooting = shooting;
	}

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	/// @param imgLoader DocumentBaseImageLoader�I�u�W�F�N�g
	public Stage(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		this.shooting = shooting;
		this.imgLoader = imgLoader;
	}

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O
	abstract public String getStageName();
	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	abstract public Player[] getEnemies();
}

