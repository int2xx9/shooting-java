package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Shooting extends JPanel {
	LazerCollection lazers = new LazerCollection();
	PlayerCollection players = new PlayerCollection();
	//public Status status;
	LinkedList<ShootingListener> shootingListeners = new LinkedList<ShootingListener>();
	MainLoop mainLoop;

	//private static final int[] keyseq = {38, 38, 40, 40, 37, 38, 37, 38, 65, 66};
	private static final int[] keyseq = {38, 38, 38};
	private int keyseq_cur = 0;
	private boolean keyseq_on = false;
	public boolean isKeyseqOn() { return keyseq_on; }

	public boolean isRunning() { return mainLoop.isRunning(); }
	public boolean isPaused() { return mainLoop.isPaused(); }
	public void setRunning() { mainLoop.setRunning(); }
	public void setPaused() { mainLoop.setPaused(); }
	public void addMainLoopJob(MainLoopJob job) { mainLoop.addJob(job); }

	public void addPlayer(Player player) { players.addPlayer(player); }

	public Shooting() {
		super();
		setBackground(Color.BLACK);
		mainLoop = new MainLoop();
		mainLoop.start();
		mainLoop.addJob(lazers);
		mainLoop.addJob(players);

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
				players.keyPressed(e);
			}

			public void keyReleased(KeyEvent e) {
				players.keyReleased(e);
			}

			public void keyTyped(KeyEvent e) {
				players.keyTyped(e);
			}
		});
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		players.paintObject(g);
		lazers.paintObject(g);
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
		private LinkedList<MainLoopJob> jobs;
		private boolean isPaused;

		boolean isRunning() { return !isPaused; }
		boolean isPaused() { return isPaused; }
		void setRunning() {
			isPaused = false;
			for (ShootingListener listener : shootingListeners) {
				listener.onGameResumed();
			}
		}
		void setPaused() {
			isPaused = true;
			for (ShootingListener listener : shootingListeners) {
				listener.onGamePaused();
			}
		}

		MainLoop() {
			isPaused = true;
			jobs = new LinkedList<MainLoopJob>();
		}

		void addJob(MainLoopJob job) {
			jobs.add(job);
		}

		public void run() {
			while (true) {
				if (!isPaused) {
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

interface ShootingListener {
	public void onGameResumed();
	public void onGamePaused();
	public void onGameOvered();
}

class ShootingAdapter implements ShootingListener {
	public void onGameResumed() {}
	public void onGamePaused() {}
	public void onGameOvered() {}
}

interface ShootingObject {
	public void paintObject(Graphics g);
	public boolean isHit(Lazer obj);
}

