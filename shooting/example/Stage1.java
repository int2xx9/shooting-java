package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;

class Stage1 extends Stage {
	public String getStageName() { return "Stage 1"; }

	public Stage1(Shooting shooting) { super(shooting); }
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

