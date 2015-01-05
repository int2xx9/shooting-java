package shooting.example.stage;

import java.io.*;
import java.util.*;
import shooting.core.*;

public abstract class Stage {
	private Shooting shooting;
		Shooting getShooting() { return this.shooting; }

	public Stage(Shooting shooting) {
		this.shooting = shooting;
	}

	abstract public String getStageName();
	abstract public Player[] getEnemies();
}

class Stage1 extends Stage {
	public String getStageName() { return "Stage 1"; }

	public Stage1(Shooting shooting) { super(shooting); }
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

class Stage2 extends Stage {
	public String getStageName() { return "Stage 2"; }

	public Stage2(Shooting shooting) { super(shooting); }
	public Player[] getEnemies() {
		Player[] enemies = new Player[4];
		enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		enemies[1] = new AIPlayer(getShooting(), 1, 30, 90, 0, 1);
		enemies[2] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2+40, 120, 0, 1);
		enemies[3] = new AIPlayer(getShooting(), 1, getShooting().getWidth()-30, 150, 0, 1);
		return enemies;
	}
}

