package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// �X�e�[�W�̗�2
class Stage2 extends Stage {
	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public Stage2(Shooting shooting) { super(shooting); }

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	/// @param imgLoader DocumentBaseImageLoader�I�u�W�F�N�g
	public Stage2(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O("Stage 2")
	public String getStageName() { return "Stage 2"; }

	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	public Player[] getEnemies() {
		Player[] enemies = new Player[4];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new AIPlayer(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2, 10, 0, 1);
			enemies[1] = new AIPlayer(getShooting(), image, 1, 30, 70, 0, 1);
			enemies[2] = new AIPlayer(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2+40, 130, 0, 1);
			enemies[3] = new AIPlayer(getShooting(), image, 1, getShooting().getWidth()-70, 180, 0, 1);
		} else {
			enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
			enemies[1] = new AIPlayer(getShooting(), 1, 30, 70, 0, 1);
			enemies[2] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2+40, 130, 0, 1);
			enemies[3] = new AIPlayer(getShooting(), 1, getShooting().getWidth()-70, 180, 0, 1);
		}
		return enemies;
	}
}

