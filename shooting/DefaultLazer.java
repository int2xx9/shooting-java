package shooting;

public class DefaultLazer extends Lazer {
	public static final int WIDTH = 2, HEIGHT = 10;
	public static final int DAMAGE = 10;
	public int getWidth() { return WIDTH; }
	public int getHeight() { return HEIGHT; }
	public int getDamage() { return DAMAGE; }

	public DefaultLazer(Player player, int x, int y, int sx, int sy) {
		super(player, x, y, sx, sy);
	}
}

