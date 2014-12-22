package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public abstract class Weapon implements MainLoopJob {
	public static final int INTERVAL_INFINITY = -1;

	abstract public int getInterval();
	abstract public LazerGenerator getGenerator(Player player);

	private int loopCount = 0;
	private Player player;
	private boolean charged = true;
		public boolean isCharged() { return this.charged; }

	public Weapon(Player player) {
		this.player = player;
		if (getInterval() != INTERVAL_INFINITY) {
			this.player.getShooting().addMainLoopJob(this);
		}
	}

	public void shoot() {
		if (player != null && charged) {
			loopCount = 0;
			charged = false;
			Lazer lazer = null;
			if (player.getShootToY() < 0) {
				// ã•ûŒü
				lazer = getGenerator(player).generateLazer(
						player.getX()+player.getWidth()/2, player.getY(),
						player.getShootToX(), player.getShootToY());
			} else if (player.getShootToY() > 0) {
				// ‰º•ûŒü
				lazer = getGenerator(player).generateLazer(
						player.getX()+player.getWidth()/2, player.getY()+player.getHeight(),
						player.getShootToX(), player.getShootToY());
			}
			if (lazer != null) {
				player.getShooting().lazers.shoot(lazer);
			}
		}
	}

	public void runMainLoopJob() {
		if (!charged) {
			loopCount = (loopCount + 1) % getInterval();
			if (loopCount == 0) {
				charged = true;
			}
		}
	}
}

class DefaultWeapon extends Weapon {
	class DefaultLazerGenerator extends LazerGenerator {
		public DefaultLazerGenerator(Player player) {
			super(player);
		}
		public Lazer generateLazer(int x, int y, int sx, int sy) {
			return new DefaultLazer(getPlayer(), x, y, sx, sy);
		}
	};

	public static final int INTERVAL = 500;
	public int getInterval() { return INTERVAL; }
	public LazerGenerator getGenerator(Player player) {
		return new DefaultLazerGenerator(player);
	}

	public DefaultWeapon(Player player) {
		super(player);
	}
}

class DummyWeapon extends Weapon {
	public int getInterval() { return Weapon.INTERVAL_INFINITY; }
	public LazerGenerator getGenerator(Player player) { return null; }
	public DummyWeapon() { super(null); }
}

abstract class LazerGenerator {
	private Player player;
		public Player getPlayer() { return this.player; }

	public LazerGenerator(Player player) {
		this.player = player;
	}

	abstract public Lazer generateLazer(int x, int y, int sx, int sy);
}

