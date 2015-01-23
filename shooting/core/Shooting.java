package shooting.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/// ゲームのメインクラス
public class Shooting extends JPanel {
	/// ゲームに表示されているLazerのコレクション
	LazerCollection lazers = new LazerCollection();
	/// ゲームに参加しているPlayerのコレクション
	PlayerCollection players = new PlayerCollection();
		/// Playerの追加
		public void addPlayer(Player player) { players.addPlayer(player); }
		/// Playerの全削除
		public void clearPlayers() { players.clear(); }
		/// Playerの配列
		public Player[] getPlayers() { return players.getPlayers(); }
	/// ゲームに関するイベントのリスナのリスト
	LinkedList<ShootingListener> shootingListeners = new LinkedList<ShootingListener>();
	MainLoop mainLoop;	///< メインループ
		/// メインループが動作中かどうか
		/// @return 動作中の場合true, それ以外の場合false
		public boolean isRunning() { return mainLoop.isRunning(); }
		/// メインループが停止中かどうか
		/// @return 停止中の場合true, それ以外の場合false
		public boolean isPaused() { return mainLoop.isPaused(); }
		/// メインループの再開
		public void setRunning() { mainLoop.setRunning(); }
		/// メインループの停止
		public void setPaused() { mainLoop.setPaused(); }
		/// メインループに処理を追加
		public void addMainLoopJob(MainLoopJob job) { mainLoop.addJob(job); }

	private boolean isGameovered = false;	///< ゲームオーバーかどうか
		/// ゲームオーバーかどうか
		/// @return ゲームオーバーの場合true, それ以外の場合false
		public boolean isGameovered() { return isGameovered; }

	private static final int[] keyseq = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};
	private int keyseq_cur = 0;
	private boolean keyseq_on = false;
	public boolean isKeyseqOn() { return keyseq_on; }

	/// コンストラクタ
	public Shooting() {
		super();
		setBackground(Color.BLACK);
		mainLoop = new MainLoop();
		mainLoop.start();
		mainLoop.addJob(lazers);
		mainLoop.addJob(players);

		// ゲームオーバー判定の処理
		mainLoop.addJob(new MainLoopJob() {
			public void runMainLoopJob() {
				// 残りのチームが1つになったらpauseしてgameover
				LinkedList<Integer> teams = new LinkedList<Integer>();
				for (Player player : players.getPlayers()) {
					if (player.isAlive() && !teams.contains(player.getTeam())) {
						teams.add(player.getTeam());
					}
				}
				if (teams.size() <= 1) {
					mainLoop.setPaused();
					isGameovered = true;
					// 勝ったチームのスコアを計算
					if (teams.size() == 1) {
						for (Player player : players.getPlayers()) {
							if (player.getTeam() == teams.get(0)) {
								int score = player.getScore();
								double hitPercent = (double)player.getHitCount()/(player.getHitCount()+player.getNotHitCount());
								double damagePercent = 1.0-(player.getDamage()/player.getMaxDamage());
								score = (int)(score * (1+hitPercent) * (1+damagePercent));
								player.setScore(score);
							}
						}
					}
					for (ShootingListener listener : shootingListeners) {
						listener.onGameOvered();
					}
					repaint();
				}
			}
		});

		// 画面の更新の処理
		mainLoop.addJob(new MainLoopJob() {
			public void runMainLoopJob() {
				repaint();
				requestFocus();
			}
		});

		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (!keyseq_on) {
					if (e.getKeyCode() == keyseq[keyseq_cur]) {
						keyseq_cur++;
						if (keyseq_cur >= keyseq.length) {
							keyseq_on = true;
						}
					} else {
						keyseq_cur = 0;
					}
				}
				if (!isGameovered && isPaused()) {
					if (e.getKeyCode() == 40 && e.getKeyChar() == 65535) {	// ↓
						setRunning();
					}
				}
				if (isGameovered) {
					if (e.getKeyCode() == 82 && e.getKeyChar() == 114) {	// r
						// restart
						initializeGame();
						for (ShootingListener listener : shootingListeners) {
							listener.onGameRestarted();
						}
					}
				}
				players.keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				players.keyReleased(e);
			}

			public void keyTyped(KeyEvent e) {
				players.keyTyped(e);
			}
		});

		initializeGame();
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		players.paintObject(g);
		lazers.paintObject(g);

		if (isGameovered) {
			g.setColor(Color.RED);
			g.setFont(new Font("Monospaced", Font.BOLD, 16));
			g.drawString("gameover", getWidth()/2-30, getHeight()/2-8);
			g.setColor(Color.WHITE);
			g.setFont(new Font("Monospaced", Font.PLAIN, 12));
			g.drawString("press R to restart", getWidth()/2-50, getHeight()/2+8);
		}
	}

	/// 初期化
	public void initializeGame() {
		setPaused();
		this.isGameovered = false;
		this.keyseq_cur = 0;
		this.keyseq_on = false;
		players.initialize();
		lazers.initialize();
		for (ShootingListener listener : shootingListeners) {
			listener.onGameInitialized();
		}
		repaint();
	}

	/// ShootingListenerの登録
	/// @param listener 登録するリスナ
	public void addShootingListener(ShootingListener listener) {
		shootingListeners.add(listener);
	}

	/// lazerが当たっているShootingObjectの配列を取得
	/// @param lazer 飛んできたLazer
	/// @return lazerが当たっているShootingObjectの配列
	public ShootingObject[] getHitObjects(Lazer lazer) {
		ShootingObject[] player_objs = players.getHitObjects(lazer);
		ShootingObject[] lazer_objs = lazers.getHitObjects(lazer);
		ShootingObject[] objs = new ShootingObject[player_objs.length + lazer_objs.length];
		int i, j;
		for (i = j = 0; j < player_objs.length; i++, j++) {
			objs[i] = player_objs[j];
		}
		for (j = 0; j < lazer_objs.length; j++, i++) {
			objs[i] = lazer_objs[j];
		}
		return objs;
	}

	/// メインループ
	class MainLoop extends Thread {
		/// メインループで行う処理のリスト
		private LinkedList<MainLoopJob> jobs = new LinkedList<MainLoopJob>();
		private boolean isPaused = true;	///< メインループが停止中かどうか
			/// メインループが動作中か
			/// @return 動作中の場合true, それ以外false
			boolean isRunning() { return !isPaused; }
			/// メインループが停止中か
			/// @return 停止中の場合true, それ以外false
			boolean isPaused() { return isPaused; }

		/// メインループの再開
		void setRunning() {
			if (!isGameovered()) {
				isPaused = false;
				for (ShootingListener listener : shootingListeners) {
					listener.onGameResumed();
				}
			}
		}

		/// メインループの停止
		void setPaused() {
			isPaused = true;
			for (ShootingListener listener : shootingListeners) {
				listener.onGamePaused();
			}
		}

		/// メインループに処理を追加
		void addJob(MainLoopJob job) {
			jobs.add(job);
		}

		public void run() {
			while (true) {
				if (!isPaused) {
					LinkedList<MainLoopJob> jobs = new LinkedList<MainLoopJob>(this.jobs);
					for (MainLoopJob job : jobs) {
						job.runMainLoopJob();
					}
				}
				try { Thread.sleep(16); } catch (InterruptedException e) {}
			}
		}
	}

	/// ゲームに表示されているLazerのコレクション
	class LazerCollection implements MainLoopJob {
		/// ゲームに表示されているLazerを保持するリスト
		/// @note 頻繁な要素の追加・削除が予想されるため連結リストを使用する
		private LinkedList<Lazer> lazers = new LinkedList<Lazer>();

		/// Lazerの発射
		synchronized void shoot(Lazer lazer) {
			lazers.add(lazer);
		}

		/// 初期化
		public synchronized void initialize() {
			lazers = new LinkedList<Lazer>();
		}

		/// メインループから呼び出される処理
		///
		/// ここでは次の処理を行っている
		/// * 画面外に出ていったLazerの削除
		/// * 当たり判定呼び出し+当たっていたLazerの削除
		public synchronized void runMainLoopJob() {
			// Listはforeachしながらremoveできないので予めコピーしておく
			LinkedList<Lazer> work_lazers = new LinkedList<Lazer>(lazers);
			for (Lazer lazer : work_lazers) {
				lazer.runMainLoopJob();
				if (lazer.isOutOfScreen()) {
					lazer.getPlayer().lazerNotHit();
					lazers.remove(lazer);
				}
			}

			// 当たり判定
			LinkedList<Lazer> hit_lazers = new LinkedList<Lazer>();
			for (Lazer lazer : lazers) {
				ShootingObject[] hitobjs = Shooting.this.getHitObjects(lazer);
				if (hitobjs.length > 0) {
					//System.out.println("hit");
					for (ShootingObject obj : hitobjs) {
						if (obj instanceof Player) {
							((Player)obj).onHit(lazer);
							lazer.getPlayer().lazerHit();
						}
					}
					hit_lazers.add(lazer);
				}
			}
			// あたっていたものは削除
			for (Lazer lazer : hit_lazers) {
				lazers.remove(lazer);
			}
		}

		/// Lazerの描画
		/// @param g
		public synchronized void paintObject(Graphics g) {
			for (Lazer lazer : lazers) {
				lazer.paintObject(g);
			}
		}

		/// src_lazerが当たっているLazerの配列を取得
		/// @param src_lazer 飛んできたLazer
		/// @return src_lazerが当たっているLazerの配列
		public synchronized ShootingObject[] getHitObjects(Lazer src_lazer) {
			LinkedList<ShootingObject> objs = new LinkedList<ShootingObject>();
			for (Lazer lazer : lazers) {
				if (lazer.isHit(src_lazer)) {
					objs.add(lazer);
				}
			}
			return objs.toArray(new ShootingObject[objs.size()]);
		}
	}

	/// ゲームに参加しているPlayerのコレクション
	class PlayerCollection implements MainLoopJob, KeyListener {
		/// ゲームに参加しているPlayerを保持するリスト
		// @note 頻繁に追加・削除せず参照する場合のほうが多いため動的配列を使用する
		private ArrayList<Player> players = new ArrayList<Player>();

		/// 参加しているPlayerの配列取得
		/// @return 参加しているPlayerの配列
		public Player[] getPlayers() {
			return this.players.toArray(new Player[this.players.size()]);
		}

		/// 初期化
		public void initialize() {
			for (Player player : players) {
				player.initialize();
			}
		}

		/// 全Playerの削除
		public void clear() {
			players = new ArrayList<Player>();
		}

		/// Playerの追加
		/// @param player 追加するPlayer
		void addPlayer(Player player) {
			players.add(player);
		}

		/// メインループから呼ばれる処理
		public void runMainLoopJob() {
			for (Player player : players) {
				player.runMainLoopJob();
			}
		}

		/// Playerの描画
		public void paintObject(Graphics g) {
			for (Player player : players) {
				player.paintObject(g);
			}
		}

		/// lazerが当たっているLazerの配列を取得
		/// @param lazer 飛んできたLazer
		/// @return lazerが当たっているPlayerの配列
		public ShootingObject[] getHitObjects(Lazer lazer) {
			LinkedList<ShootingObject> objs = new LinkedList<ShootingObject>();
			for (Player player : players) {
				if (player.isHit(lazer)) {
					objs.add(player);
				}
			}

			ShootingObject[] ret_objs = new ShootingObject[objs.size()];
			objs.toArray(ret_objs);
			return ret_objs;
		}

		public void keyPressed(KeyEvent e) {
			for (Player player : players) {
				player.keyPressed(e);
			}
		}

		public void keyReleased(KeyEvent e) {
			for (Player player : players) {
				player.keyReleased(e);
			}
		}

		public void keyTyped(KeyEvent e) {
			for (Player player : players) {
				player.keyTyped(e);
			}
		}
	}
}

/// メインループに処理を登録する場合のインターフェース
interface MainLoopJob {
	/// メインループから呼ばれる処理
	public void runMainLoopJob();
}

/// ゲーム画面に表示されるオブジェクト用のインターフェース
interface ShootingObject {
	/// オブジェクトの描画
	/// @param g
	public void paintObject(Graphics g);
	/// 当たり判定
	/// @param obj 当たり判定を行うLazer
	/// @return 当たっている場合true, それ以外の場合false
	public boolean isHit(Lazer obj);
}

