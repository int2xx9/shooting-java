package shooting.example.examplestage;

import shooting.core.*;

public class ExampleLazer extends Lazer {
	public static final int WIDTH = 10, HEIGHT = 20;
	public static final int DAMAGE = 30;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getDamage() { return DAMAGE; }

	public ExampleLazer(Player player, int x, int y, int sx, int sy) {
		super(player, x, y, sx, sy);
	}
}

