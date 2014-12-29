package shooting;

public class DefaultWeapon extends Weapon {
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

