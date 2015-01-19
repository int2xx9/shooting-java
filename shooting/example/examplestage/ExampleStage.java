package shooting.example.examplestage;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// �X�e�[�W�̃T���v��
public class ExampleStage extends Stage {
	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public ExampleStage(Shooting shooting) { super(shooting); }

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	/// @param imgLoader DocumentBaseImageLoader�I�u�W�F�N�g
	public ExampleStage(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O("Example Stage")
	public String getStageName() { return "Example Stage"; }

	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new ExampleAI(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2, 10, 0, 1);
		} else {
			enemies[0] = new ExampleAI(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
		}
		return enemies;
	}
}

