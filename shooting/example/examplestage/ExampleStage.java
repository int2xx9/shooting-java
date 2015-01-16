package shooting.example.examplestage;

import shooting.core.*;
import shooting.example.stage.*;

/// �X�e�[�W�̃T���v��
public class ExampleStage extends Stage {
	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public ExampleStage(Shooting shooting) { super(shooting); }

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O("Example Stage")
	public String getStageName() { return "Example Stage"; }

	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new ExampleAI(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

