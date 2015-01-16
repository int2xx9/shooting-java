package shooting.example.examplestage;

import shooting.core.*;

/// Weaponのサンプル
public class ExampleWeapon extends Weapon {
	/// LazerGeneratorのサンプル
	class ExampleLazerGenerator extends LazerGenerator {
		/// コンストラクタ
		/// @param player 発射元Player
		public ExampleLazerGenerator(Player player) {
			super(player);
		}
		/// ExampleLazerの生成
		/// @param x 生成するX座標の位置
		/// @param y 生成するY座標の位置
		/// @param sx X軸の発射する方向と速度
		/// @param sy Y軸の発射する方向と速度
		/// @return Lazerオブジェクト
		public Lazer generateLazer(int x, int y, int sx, int sy) {
			return new ExampleLazer(getPlayer(), x, y, sx, sy);
		}
	};

	public static final int INTERVAL = 3000;	///< 発射可能になるまでの間隔
	/// 発射可能になるまでの間隔の取得
	/// @return 発射可能になるまでの間隔
	public int getInterval() { return INTERVAL; }
	/// Generatorの取得
	/// @param player 発射元となるPlayer
	/// @return Playerを発射元とするLazerGeneratorのオブジェクト
	public LazerGenerator getGenerator(Player player) {
		return new ExampleLazerGenerator(player);
	}

	/// コンストラクタ
	/// @param player 発射元となるPlayer
	public ExampleWeapon(Player player) {
		super(player);
	}
}

