package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Weapon implements MainLoopJob {
	public static final int INTERVAL = 500;
	private int loopCount = 0;
	private boolean charged = true;
	private Player player;

	public boolean isCharged() { return charged; }

	public Weapon(Player player) {
		this.player = player;
		if (this.player != null) {
			this.player.getShooting().addMainLoopJob(this);
		}
	}

	public void shoot() {
		if (player != null && charged) {
			loopCount = 0;
			charged = false;
			if (player.getShootToY() < 0) {
				// ã•ûŒü
				player.getShooting().lazers.shoot(
					new Lazer(player,
						player.getX()+player.getWidth()/2, player.getY(),
						player.getShootToX(), player.getShootToY())
				);
			} else if (player.getShootToY() > 0) {
				// ‰º•ûŒü
				player.getShooting().lazers.shoot(
					new Lazer(player,
						player.getX()+player.getWidth()/2, player.getY()+player.getHeight(),
						player.getShootToX(), player.getShootToY())
				);
			}
		}
	}

	public void runMainLoopJob() {
		if (!charged) {
			loopCount = (loopCount + 1) % INTERVAL;
			if (loopCount == 0) {
				charged = true;
			}
		}
	}
}

class DummyWeapon extends Weapon {
	public DummyWeapon() { super(null); }
	public void shoot() {}
}

