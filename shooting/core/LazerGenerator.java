package shooting.core;

/// Lazerを生成するクラス
public abstract class LazerGenerator {
	private Player player;	///< 発射元となるPlayer
		/// 発射元となるPlayerの取得
		/// @return 発射元となるPlayer
		public Player getPlayer() { return this.player; }

	/// コンストラクタ
	/// @param player 発射元となるPlayer
	public LazerGenerator(Player player) {
		this.player = player;
	}

	/// Lazerの生成
	/// @param x 生成するX座標の位置
	/// @param y 生成するY座標の位置
	/// @param sx X軸の発射する方向と速度
	/// @param sy Y軸の発射する方向と速度
	/// @return Lazerオブジェクト
	abstract public Lazer generateLazer(int x, int y, int sx, int sy);
}

