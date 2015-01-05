package shooting.example.stage;

import shooting.core.*;

public abstract class Stage {
	private Shooting shooting;
		public Shooting getShooting() { return this.shooting; }

	public Stage(Shooting shooting) {
		this.shooting = shooting;
	}

	abstract public String getStageName();
	abstract public Player[] getEnemies();
}

