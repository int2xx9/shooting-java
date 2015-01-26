package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// �X�e�[�W�̗�3
/// ������ΐl��̗�
class Stage3 extends Stage {
	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public Stage3(Shooting shooting) { super(shooting); }

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	/// @param imgLoader DocumentBaseImageLoader�I�u�W�F�N�g
	public Stage3(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O("Stage 3")
	public String getStageName() { return "Stage 3"; }

	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	public Player[] getEnemies() {
		ControllablePlayer[] enemies = new ControllablePlayer[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new ControllablePlayer(getShooting(), image, 1,
					getShooting().getWidth()/2-image.getWidth(null)/2,
					10,
					0, 1);
		} else {
			enemies[0] = new ControllablePlayer(getShooting(), image, 1,
					getShooting().getWidth()/2-ControllablePlayer.DEFAULT_WIDTH/2,
					10,
					0, 1);
		}
		enemies[0].getKeyConfig().setMoveLeftKey(65, 97);	// a
		enemies[0].getKeyConfig().setMoveRightKey(68, 100);	// d
		enemies[0].getKeyConfig().setShootKey(83, 115);			// s
		return enemies;
	}
}
