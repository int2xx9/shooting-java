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

/// アプレットクラス
public class ShootingApplet extends JApplet implements StageSelectListener {
	JButton resumePauseButton;	///< 一時停止・再開ボタン
	JButton stageSelectButton;	///< ステージ選択ボタン
	JButton leftButton;		///< 左移動ボタン
	JButton rightButton;	///< 右移動ボタン
	JButton shootButton;	///< 発射ボタン
	StageSelectPanel stageSelectPanel;	///< ステージ選択用パネル
	StatusPanel statusPanel;	///< スコア等を表示するパネル
	JPanel currentPanel;		///< stageSelectPanelとshootingのうち現在表示されているパネル
	Shooting shooting;			///< ゲーム画面
	ControllablePlayer player;	///< 自機
	Player[] enemies;			///< 敵機

	/// 画像読み込み用クラス
	DocumentBaseImageLoader imgLoader = new DocumentBaseImageLoader(this);

	public void init() {
		setLayout(null);

		// ゲーム画面の配置
		shooting = new Shooting();
		shooting.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(shooting);

		// 自機の準備
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
		player.getKeyConfig().setMoveLeftKey(37, 65535);	// ←
		player.getKeyConfig().setMoveRightKey(39, 65535);	// →
		player.getKeyConfig().setShootKey(38, 65535);			// ↑

		// 移動ボタンなどのゲーム画面の下に配置するボタンやパネルのY位置のオフセット
		int ctrlY = getHeight()-50;

		// 開始/再開/一時停止ボタン
		resumePauseButton = new JButton("開始");
		resumePauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shooting.isRunning()) {
					// 既に動いていたら一時停止
					shooting.setPaused();
				} else {
					// 動いていなければ再開
					// shootingが表示されていなければstageSelectPanelを閉じ
					// フォーカスをshootingに移動する
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

		// 左移動ボタン
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

		// 右移動ボタン
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

		// 発射ボタン
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

		// スコア等の情報を表示するパネル
		statusPanel = new StatusPanel();
		statusPanel.setBounds(215, ctrlY+5, getWidth()-215-5, 20);
		add(statusPanel);

		// ステージ選択ボタン
		stageSelectButton = new JButton("ステージ選択");
		stageSelectButton.setBounds(getWidth()-120-5, ctrlY+25, 120, 20);
		stageSelectButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				// ゲームを一時停止させ非表示にした後ステージ選択画面を表示
				shooting.setPaused();
				shooting.setVisible(false);
				stageSelectPanel.setVisible(true);
				currentPanel = stageSelectPanel;
				currentPanel.requestFocus();
			}
		});
		stageSelectButton.addActionListener(new MoveFocusListener());
		add(stageSelectButton);

		// shootingに自機をPlayerとして追加
		shooting.addPlayer(player);
		// スコア等が更新されたときstatusPanelを更新する
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

		// ステージ選択画面
		stageSelectPanel = new StageSelectPanel(shooting, this);
		stageSelectPanel.addStage(new Stage1(shooting, imgLoader));
		stageSelectPanel.addStage(new Stage2(shooting, imgLoader));
		stageSelectPanel.addStage(new ExampleStage(shooting, imgLoader));
		stageSelectPanel.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(stageSelectPanel);

		// shootingを非表示にする
		shooting.setVisible(false);
		currentPanel = stageSelectPanel;
		currentPanel.requestFocus();
	}

	/// ステージ選択画面でステージが選ばれた
	/// @param selectedStage 選択されたステージ
	public void stageSelected(Stage selectedStage) {
		// Playerを全て削除し自機と選択されたステージの敵機を追加
		shooting.clearPlayers();
		shooting.addPlayer(player);
		for (Player player : selectedStage.getEnemies()) {
			shooting.addPlayer(player);
		}

		// ゲーム初期化
		shooting.initializeGame();

		// shootingを表示
		stageSelectPanel.setVisible(false);
		shooting.setVisible(true);
		currentPanel = shooting;
		currentPanel.requestFocus();
	}

	/// クリックしたときにフォーカスをcurrentPanelに移動するリスナ
	class MoveFocusListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			currentPanel.requestFocus();
		}
	}

	/// スコアなどの情報を表示するパネル
	/// 表示する情報は今のところ
	/// * 命中率
	/// * 撃った弾が当たった数
	/// * 撃った弾が外れた数
	/// * 現在のダメージ
	/// * スコア
	class StatusPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("" + player.getHitPercent() + "%(" + player.getHitCount() + "/" + (player.getHitCount()+player.getNotHitCount()) + ") damage:" + player.getDamage() + " score:" + player.getScore(), 0, 10);
		}
	}
}

