package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import shooting.example.examplestage.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.imageio.ImageIO;
import java.net.URL;

/// �A�v���b�g�N���X
public class ShootingApplet extends JApplet implements StageSelectListener {
	JButton resumePauseButton;	///< �ꎞ��~�E�ĊJ�{�^��
	JButton stageSelectButton;	///< �X�e�[�W�I���{�^��
	JButton leftButton;		///< ���ړ��{�^��
	JButton rightButton;	///< �E�ړ��{�^��
	JButton shootButton;	///< ���˃{�^��
	StageSelectPanel stageSelectPanel;	///< �X�e�[�W�I��p�p�l��
	StatusPanel statusPanel;	///< �X�R�A����\������p�l��
	JPanel currentPanel;		///< stageSelectPanel��shooting�̂������ݕ\������Ă���p�l��
	Shooting shooting;			///< �Q�[�����
	ControllablePlayer player;	///< ���@
	Player[] enemies;			///< �G�@

	/// �摜�ǂݍ��ݗp�N���X
	DocumentBaseImageLoader imgLoader = new DocumentBaseImageLoader(this);

	public void init() {
		setLayout(null);

		// �Q�[����ʂ̔z�u
		shooting = new Shooting();
		shooting.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(shooting);

		// ���@�̏���
		Image playerImage = imgLoader.getImageImmediately("k1_p3a.png");
		if (playerImage != null) {
			player = new ControllablePlayer(shooting, playerImage, 0,
					shooting.getWidth()/2-playerImage.getWidth(null)/2,
					shooting.getHeight()-60,
					0, -1);
		} else {
			player = new ControllablePlayer(shooting, playerImage, 0,
					shooting.getWidth()/2-ControllablePlayer.DEFAULT_WIDTH/2,
					shooting.getHeight()-60,
					0, -1);
		}
		player.getKeyConfig().setMoveLeftKey(37, 65535);	// ��
		player.getKeyConfig().setMoveRightKey(39, 65535);	// ��
		player.getKeyConfig().setShootKey(38, 65535);			// ��

		// �ړ��{�^���Ȃǂ̃Q�[����ʂ̉��ɔz�u����{�^����p�l����Y�ʒu�̃I�t�Z�b�g
		int ctrlY = getHeight()-50;

		// �J�n/�ĊJ/�ꎞ��~�{�^��
		resumePauseButton = new JButton("�J�n");
		resumePauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shooting.isRunning()) {
					// ���ɓ����Ă�����ꎞ��~
					shooting.setPaused();
				} else {
					// �����Ă��Ȃ���΍ĊJ
					// shooting���\������Ă��Ȃ����stageSelectPanel���
					// �t�H�[�J�X��shooting�Ɉړ�����
					if (!shooting.isVisible()) {
						stageSelectPanel.setVisible(false);
						shooting.setVisible(true);
						currentPanel = shooting;
						currentPanel.requestFocus();
					}
					shooting.setRunning();
				}
			}
		});
		shooting.addShootingListener(new ShootingAdapter() {
			public void onGameInitialized() {
				resumePauseButton.setText("�J�n");
			}
			public void onGameResumed() {
				resumePauseButton.setText("�ꎞ��~");
			}
			public void onGamePaused() {
				resumePauseButton.setText("�ĊJ");
			}
			public void onGameRestarted() {
				statusPanel.repaint();
			}
		});
		resumePauseButton.setBounds(5, ctrlY+5, 100, 40);
		resumePauseButton.addActionListener(new MoveFocusListener());
		add(resumePauseButton);

		// ���ړ��{�^��
		leftButton = new JButton("��");
		leftButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				player.setMovingX(-1);
			}

			public void mouseReleased(MouseEvent e) {
				player.setMovingX(0);
			}
		});
		leftButton.setBounds(110, ctrlY+25, 50, 20);
		leftButton.addActionListener(new MoveFocusListener());
		add(leftButton);

		// �E�ړ��{�^��
		rightButton = new JButton("��");
		rightButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				player.setMovingX(1);
			}

			public void mouseReleased(MouseEvent e) {
				player.setMovingX(0);
			}
		});
		rightButton.setBounds(160, ctrlY+25, 50, 20);
		rightButton.addActionListener(new MoveFocusListener());
		add(rightButton);

		// ���˃{�^��
		shootButton = new JButton("��");
		shootButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				if (shooting.isRunning()) {
					player.getWeapon().shoot();
				}
			}
		});
		shootButton.setBounds(110, ctrlY+5, 100, 20);
		shootButton.addActionListener(new MoveFocusListener());
		add(shootButton);

		// �X�R�A���̏���\������p�l��
		statusPanel = new StatusPanel();
		statusPanel.setBounds(215, ctrlY+5, getWidth()-215-5, 20);
		add(statusPanel);

		// �X�e�[�W�I���{�^��
		stageSelectButton = new JButton("�X�e�[�W�I��");
		stageSelectButton.setBounds(getWidth()-120-5, ctrlY+25, 120, 20);
		stageSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// �Q�[�����ꎞ��~������\���ɂ�����X�e�[�W�I����ʂ�\��
				shooting.setPaused();
				shooting.setVisible(false);
				stageSelectPanel.setVisible(true);
				currentPanel = stageSelectPanel;
				currentPanel.requestFocus();
			}
		});
		stageSelectButton.addActionListener(new MoveFocusListener());
		add(stageSelectButton);

		// shooting�Ɏ��@��Player�Ƃ��Ēǉ�
		shooting.addPlayer(player);
		// �X�R�A�����X�V���ꂽ�Ƃ�statusPanel���X�V����
		player.addPlayerListener(new PlayerAdapter() {
			public void scoreUpdated() {
				statusPanel.repaint();
			}
			public void damageUpdated() {
				statusPanel.repaint();
			}
			public void notHitCountUpdated() {
				statusPanel.repaint();
			}
		});

		// �X�e�[�W�I�����
		stageSelectPanel = new StageSelectPanel(shooting, this);
		stageSelectPanel.addStage(new Stage1(shooting, imgLoader));
		stageSelectPanel.addStage(new Stage2(shooting, imgLoader));
		stageSelectPanel.addStage(new ExampleStage(shooting, imgLoader));
		stageSelectPanel.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(stageSelectPanel);

		// shooting���\���ɂ���
		shooting.setVisible(false);
		currentPanel = stageSelectPanel;
		currentPanel.requestFocus();
	}

	/// �X�e�[�W�I����ʂŃX�e�[�W���I�΂ꂽ
	/// @param selectedStage �I�����ꂽ�X�e�[�W
	public void stageSelected(Stage selectedStage) {
		// Player��S�č폜�����@�ƑI�����ꂽ�X�e�[�W�̓G�@��ǉ�
		shooting.clearPlayers();
		shooting.addPlayer(player);
		for (Player player : selectedStage.getEnemies()) {
			shooting.addPlayer(player);
		}

		// �Q�[��������
		shooting.initializeGame();

		// shooting��\��
		stageSelectPanel.setVisible(false);
		shooting.setVisible(true);
		currentPanel = shooting;
		currentPanel.requestFocus();
	}

	/// �N���b�N�����Ƃ��Ƀt�H�[�J�X��currentPanel�Ɉړ����郊�X�i
	class MoveFocusListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			currentPanel.requestFocus();
		}
	}

	/// �X�R�A�Ȃǂ̏���\������p�l��
	/// �\��������͍��̂Ƃ���
	/// * ������
	/// * �������e������������
	/// * �������e���O�ꂽ��
	/// * ���݂̃_���[�W
	/// * �X�R�A
	class StatusPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("" + player.getHitPercent() + "%(" + player.getHitCount() + "/" + (player.getHitCount()+player.getNotHitCount()) + ") damage:" + player.getDamage() + " score:" + player.getScore(), 0, 10);
		}
	}
}

