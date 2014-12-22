package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

public class Weapon implements MainLoopJob {
	public static final int INTERVAL = 500;
	private int loopCount = 0;
	private boolean charged = true;
	private Shooting shooting;
	private Player player;

	public boolean isCharged() { return charged; }

	public Weapon(Shooting shooting, Player player) {
		this.shooting = shooting;
		this.player = player;
		this.shooting.addMainLoopJob(this);
	}

	public void shoot() {
		if (charged) {
			loopCount = 0;
			charged = false;
			if (player.getShootToY() < 0) {
				// ã•ûŒü
				shooting.lazers.shoot(
					new Lazer(player,
						player.getX()+player.getWidth()/2, player.getY(),
						player.getShootToX(), player.getShootToY())
				);
			} else if (player.getShootToY() > 0) {
				// ‰º•ûŒü
				shooting.lazers.shoot(
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

