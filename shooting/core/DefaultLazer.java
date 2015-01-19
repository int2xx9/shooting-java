package shooting.core;

/// shooting.coreパッケージの中でデフォルトで使用されるLazer
public class DefaultLazer extends Lazer {
	public static final int WIDTH = 2;	///< 幅
	public static final int HEIGHT = 10;	///< 高さ
	public static final int DAMAGE = 10;	///< 与えるダメージ
	/// 幅の取得
	/// @return 幅
	public int getWidth() { return WIDTH; }
	/// 高さの取得
	/// @return 高さ
	public int getHeight() { return HEIGHT; }
	/// 与えるダメージの取得
	/// @return 与えるダメージ
	public int getDamage() { return DAMAGE; }

	/// コンストラクタ
	/// @param x 生成するX座標の位置
	/// @param y 生成するY座標の位置
	/// @param sx X軸の発射する方向と速度
	/// @param sy Y軸の発射する方向と速度
	public DefaultLazer(Player player, int x, int y, int sx, int sy) {
		super(player, x, y, sx, sy*8);
	}
}

