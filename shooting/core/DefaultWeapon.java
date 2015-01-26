package shooting.core;

/// shooting.coreパッケージの中でデフォルトで使用されるWeapon
public class DefaultWeapon extends Weapon {
	/// DefaultLazer用Generator
	class DefaultLazerGenerator extends LazerGenerator {
		/// コンストラクタ
		/// @param player 発射元Player
		public DefaultLazerGenerator(Player player) {
			super(player);
		}
		/// Lazerの生成
		/// @param x 生成するX座標の位置
		/// @param y 生成するY座標の位置
		/// @param sx X軸の発射する方向と速度
		/// @param sy Y軸の発射する方向と速度
		/// @return Lazerオブジェクト
		public Lazer generateLazer(int x, int y, int sx, int sy) {
			return new DefaultLazer(getPlayer(), x, y, sx, sy);
		}
	};

	public static final int INTERVAL = 25;	///< 発射可能になるまでの間隔
	/// 発射可能になるまでの間隔の取得
	/// @return 発射可能になるまでの間隔
	public int getInterval() { return INTERVAL; }
	/// Generatorの取得
	/// @param player 発射元となるPlayer
	/// @return Playerを発射元とするLazerGeneratorのオブジェクト
	public LazerGenerator getGenerator(Player player) {
		return new DefaultLazerGenerator(player);
	}

	/// コンストラクタ
	/// @param player 発射元となるPlayer
	public DefaultWeapon(Player player) {
		super(player);
	}
}

