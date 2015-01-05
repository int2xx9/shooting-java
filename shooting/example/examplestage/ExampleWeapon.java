package shooting.example.examplestage;

import shooting.core.*;

public class ExampleWeapon extends Weapon {
	class ExampleLazerGenerator extends LazerGenerator {
		public ExampleLazerGenerator(Player player) {
			super(player);
		}
		public Lazer generateLazer(int x, int y, int sx, int sy) {
			return new ExampleLazer(getPlayer(), x, y, sx, sy);
		}
	};

	public static final int INTERVAL = 3000;
	public int getInterval() { return INTERVAL; }
	public LazerGenerator getGenerator(Player player) {
		return new ExampleLazerGenerator(player);
	}

	public ExampleWeapon(Player player) {
		super(player);
	}
}

