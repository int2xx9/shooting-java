package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// �X�e�[�W�̗�5
class Stage5 extends Stage {
	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	public Stage5(Shooting shooting) { super(shooting); }

	/// �R���X�g���N�^
	/// @param shooting �X�e�[�W�ɕR�t����Shooting�I�u�W�F�N�g
	/// @param imgLoader DocumentBaseImageLoader�I�u�W�F�N�g
	public Stage5(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// �X�e�[�W�̖��O���擾
	/// @return �X�e�[�W�̖��O("Stage 5")
	public String getStageName() { return "Stage 5"; }

	/// �X�e�[�W��̓G�̔z����擾
	/// @return �X�e�[�W��̓G�̔z��
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new Stage5AI(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2, 10, 0, 1);
		} else {
			enemies[0] = new Stage5AI(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
		}
		return enemies;
	}
}

/// �����ňړ��E���[�U�̔��˂��s��Player
class Stage5AI extends Player {
	public static final int DEFAULT_WIDTH = 25;	///< �f�t�H���g�̕�
	public static final int DEFAULT_HEIGHT = 25;	///< �f�t�H���g�̍���
	public static final int MAX_DAMAGE = 30;	///< �ő�_���[�W
	/// ���̎擾
	/// @return ��
	public int getWidth() { return getImage() != null ? getImage().getWidth(null) : DEFAULT_WIDTH; }
	/// �����̎擾
	/// @return ����
	public int getHeight() { return getImage() != null ? getImage().getHeight(null) : DEFAULT_HEIGHT; }
	/// �ő�_���[�W�̎擾
	/// @return �ő�_���[�W
	public int getMaxDamage() { return MAX_DAMAGE; }

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param team �`�[���ԍ�
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public Stage5AI(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this(shooting, null, team, x, y, sx, sy);
	}

	/// �R���X�g���N�^
	/// @param shooting Shooting�N���X�̃I�u�W�F�N�g
	/// @param image �@�̂̉摜
	/// @param team �`�[���ԍ�
	/// @param x �z�u���鍶�ォ���X���W
	/// @param y �z�u���鍶�ォ���Y���W
	/// @param sx ���˂̕����E������X���W
	/// @param sy ���˂̕����E������Y���W
	public Stage5AI(Shooting shooting, Image image, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setImage(image);
		setWeapon(new DefaultWeapon(this));
		setMovingX(2);
	}

	private int interval_counter = 0;	///< �v���C���[�֌������Đi�ޕp�x�̒����p�J�E���^
	private final int LOOP_INTERVAL = 180;	///< �v���C���[�֌������Đi�ފԊu
	/// ���C�����[�v����Ă΂�鏈��
	///
	/// �����ł͈ړ��ƃ��[�U�̔��ˏ������s���Ă���
	public void runMainLoopJob() {
		// 1/50�̊m���Ń��[�U�𔭎˂���
		// (������Weapon�����ˉ\��ԂłȂ���Δ��˂��Ȃ�)
		if ((int)(Math.random()*50) == 0) {
			getWeapon().shoot();
		}
		// ����ȏ㍡�̌����֐i�߂Ȃ��Ȃ���������]������
		if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
			setMovingX(-getMovingX());
		}
		setX(getX()+getMovingX());

		interval_counter = (interval_counter+1) % LOOP_INTERVAL;
		if (interval_counter == LOOP_INTERVAL-1) {
			if (getY() + 10 + getHeight() > getShooting().getHeight()-60) {
				for (Player player : getShooting().getPlayers()) {
					if (player.getTeam() != this.getTeam()) {
						player.setDamage(10000);
					}
				}
			}
			setY(getY()+10);
		}
	}
}

