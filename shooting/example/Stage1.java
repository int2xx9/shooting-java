package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;

/// �X�e�[�W�̗�1
class Stage1 extends Stage {
	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public Stage1(Shooting shooting) { super(shooting); }

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O("Stage 1")
	public String getStageName() { return "Stage 1"; }

	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

