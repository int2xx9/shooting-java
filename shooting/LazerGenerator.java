package shooting;

public abstract class LazerGenerator {
	private Player player;
		public Player getPlayer() { return this.player; }

	public LazerGenerator(Player player) {
		this.player = player;
	}

	abstract public Lazer generateLazer(int x, int y, int sx, int sy);
}

