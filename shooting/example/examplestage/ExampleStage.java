package shooting.example.examplestage;

import shooting.core.*;
import shooting.example.stage.*;

public class ExampleStage extends Stage {
	public String getStageName() { return "Example Stage"; }

	public ExampleStage(Shooting shooting) { super(shooting); }
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new ExampleAI(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

