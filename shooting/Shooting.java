package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class Shooting extends JPanel {
	LazerCollection lazers;
	PlayerCollection players;
	//public Status status;
	LinkedList<ShootingListener> shootingListeners;
	MainLoop mainLoop;

	public boolean isRunning() { return mainLoop.isRunning(); }
	public boolean isPaused() { return mainLoop.isPaused(); }
	public void setRunning() { mainLoop.setRunning(); }
	public void setPaused() { mainLoop.setPaused(); }
	public void addMainLoopJob(MainLoopJob job) { mainLoop.addJob(job); }

	public void addPlayer(Player player) { players.addPlayer(player); }

	public Shooting() {
		super();
		setBackground(Color.BLACK);
		shootingListeners = new LinkedList<ShootingListener>();
		lazers = new LazerCollection();
		players = new PlayerCollection();
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

	class LazerCollection implements MainLoopJob, ShootingObject {
		LinkedList<Lazer> lazers;
		LazerCollection() {
			lazers = new LinkedList<Lazer>();
		}

		void shoot(Lazer lazer) {
			lazers.add(lazer);
		}

		public void runMainLoopJob() {
			for (Lazer lazer : lazers) {
				lazer.runMainLoopJob();
			}

			// âÊñ äOÇ…Ç¢Ç¡ÇΩíeÇçÌèú
			LinkedList<Lazer> ooslazers = new LinkedList<Lazer>();
			for (Lazer lazer : lazers) {
				if (lazer.isOutOfScreen()) {
					ooslazers.add(lazer);
				}
			}
			for (Lazer lazer : ooslazers) {
				lazers.remove(lazer);
			}
		}

		public void paintObject(Graphics g) {
			for (Lazer lazer : lazers) {
				lazer.paintObject(g);
			}
		}
	}

	class PlayerCollection implements MainLoopJob, ShootingObject, KeyListener {
		ArrayList<Player> players;
		PlayerCollection() {
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
}

