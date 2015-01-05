package shooting.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Shooting extends JPanel {
	LazerCollection lazers = new LazerCollection();
	PlayerCollection players = new PlayerCollection();
		public void addPlayer(Player player) { players.addPlayer(player); }
		public void clearPlayers() { players.clear(); }
	//public Status status;
	LinkedList<ShootingListener> shootingListeners = new LinkedList<ShootingListener>();
	MainLoop mainLoop;
		public boolean isRunning() { return mainLoop.isRunning(); }
		public boolean isPaused() { return mainLoop.isPaused(); }
		public void setRunning() { mainLoop.setRunning(); }
		public void setPaused() { mainLoop.setPaused(); }
		public void addMainLoopJob(MainLoopJob job) { mainLoop.addJob(job); }
	private boolean isGameovered = false;
		public boolean isGameovered() { return isGameovered; }

	//private static final int[] keyseq = {38, 38, 40, 40, 37, 39, 37, 39, 66, 65};
	private static final int[] keyseq = {38, 38, 38};
	private int keyseq_cur = 0;
	private boolean keyseq_on = false;
	public boolean isKeyseqOn() { return keyseq_on; }

	public Shooting() {
		super();
		setBackground(Color.BLACK);
		mainLoop = new MainLoop();
		mainLoop.start();
		mainLoop.addJob(lazers);
		mainLoop.addJob(players);

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
								double damagePercent = (double)player.getDamage()/player.getMaxDamage();
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

	public void addShootingListener(ShootingListener listener) {
		shootingListeners.add(listener);
	}

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

	class MainLoop extends Thread {
		private LinkedList<MainLoopJob> jobs = new LinkedList<MainLoopJob>();
		private boolean isPaused = true;
			boolean isRunning() { return !isPaused; }
			boolean isPaused() { return isPaused; }

		void setRunning() {
			if (!isGameovered()) {
				isPaused = false;
				for (ShootingListener listener : shootingListeners) {
					listener.onGameResumed();
				}
			}
		}
		void setPaused() {
			isPaused = true;
			for (ShootingListener listener : shootingListeners) {
				listener.onGamePaused();
			}
		}

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
				try { Thread.sleep(1); } catch (InterruptedException e) {}
			}
		}
	}

	class LazerCollection implements MainLoopJob {
		// 頻繁な要素の追加・削除が予想されるため連結リストを使用する
		private LinkedList<Lazer> lazers = new LinkedList<Lazer>();

		synchronized void shoot(Lazer lazer) {
			lazers.add(lazer);
		}

		public synchronized void initialize() {
			lazers = new LinkedList<Lazer>();
		}

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

		public synchronized void paintObject(Graphics g) {
			for (Lazer lazer : lazers) {
				lazer.paintObject(g);
			}
		}

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

	class PlayerCollection implements MainLoopJob, KeyListener {
		// 頻繁に追加・削除せず参照する場合のほうが多いため動的配列を使用する
		private ArrayList<Player> players = new ArrayList<Player>();

		public Player[] getPlayers() {
			return this.players.toArray(new Player[this.players.size()]);
		}

		public void initialize() {
			for (Player player : players) {
				player.initialize();
			}
		}

		public void clear() {
			players = new ArrayList<Player>();
		}

		void addPlayer(Player player) {
			players.add(player);
		}

		public void runMainLoopJob() {
			for (Player player : players) {
				player.runMainLoopJob();
			}
		}

		public void paintObject(Graphics g) {
			for (Player player : players) {
				player.paintObject(g);
			}
		}

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

interface MainLoopJob {
	public void runMainLoopJob();
}

interface ShootingObject {
	public void paintObject(Graphics g);
	public boolean isHit(Lazer obj);
}

