package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ShootingApplet extends JApplet implements StageSelectListener {
	JButton resumePauseButton;
	JButton stageSelectButton;
	JButton leftButton;
	JButton rightButton;
	JButton shootButton;
	StageSelectPanel stageSelectPanel;
	StatusPanel statusPanel;
	JPanel currentPanel;	// stageSelectPanelとstatusPanelのうち現在表示されているパネル
	Shooting shooting;
	ControllablePlayer player;
	Player[] enemies;

	public void init() {
		setLayout(null);

		shooting = new Shooting();
		shooting.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(shooting);

		player = new ControllablePlayer(shooting, 0, shooting.getWidth()/2, shooting.getHeight()-60, 0, -1);
		player.getKeyConfig().setMoveLeftKey(37, 65535);	// ←
		player.getKeyConfig().setMoveRightKey(39, 65535);	// →
		player.getKeyConfig().setShootKey(38, 65535);			// ↑

		int ctrlY = getHeight()-50;

		resumePauseButton = new JButton("開始");
		resumePauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shooting.isRunning()) {
					shooting.setPaused();
				} else {
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
				resumePauseButton.setText("開始");
			}
			public void onGameResumed() {
				resumePauseButton.setText("一時停止");
			}
			public void onGamePaused() {
				resumePauseButton.setText("再開");
			}
			public void onGameRestarted() {
				statusPanel.repaint();
			}
		});
		resumePauseButton.setBounds(5, ctrlY+5, 100, 40);
		resumePauseButton.addActionListener(new MoveFocusListener());
		add(resumePauseButton);

		leftButton = new JButton("←");
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

		rightButton = new JButton("→");
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

		shootButton = new JButton("↑");
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

		statusPanel = new StatusPanel();
		statusPanel.setBounds(215, ctrlY+5, getWidth()-215-5, 20);
		add(statusPanel);

		stageSelectButton = new JButton("ステージ選択");
		stageSelectButton.setBounds(getWidth()-120-5, ctrlY+25, 120, 20);
		stageSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				shooting.setPaused();
				shooting.setVisible(false);
				stageSelectPanel.setVisible(true);
				currentPanel = stageSelectPanel;
				currentPanel.requestFocus();
			}
		});
		stageSelectButton.addActionListener(new MoveFocusListener());
		add(stageSelectButton);

		shooting.addPlayer(player);
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

		shooting.requestFocus();
		stageSelectPanel = new StageSelectPanel(shooting, this);
		stageSelectPanel.addStage(new Stage1(shooting));
		stageSelectPanel.addStage(new Stage2(shooting));
		stageSelectPanel.addStage(new shooting.example.examplestage.ExampleStage(shooting));
		stageSelectPanel.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(stageSelectPanel);
		shooting.setVisible(false);
		currentPanel = stageSelectPanel;
		currentPanel.requestFocus();
	}

	public void stageSelected(Stage selectedStage) {
		shooting.clearPlayers();
		shooting.addPlayer(player);
		for (Player player : selectedStage.getEnemies()) {
			shooting.addPlayer(player);
		}
		shooting.initializeGame();
		stageSelectPanel.setVisible(false);
		shooting.setVisible(true);
		currentPanel = shooting;
		currentPanel.requestFocus();
	}

	class MoveFocusListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			currentPanel.requestFocus();
		}
	}

	class StatusPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("" + player.getHitPercent() + "%(" + player.getHitCount() + "/" + (player.getHitCount()+player.getNotHitCount()) + ") damage:" + player.getDamage() + " score:" + player.getScore(), 0, 10);
		}
	}
}

