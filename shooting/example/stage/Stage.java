package shooting.example.stage;

import shooting.core.*;

/// ステージ
public abstract class Stage {
	private Shooting shooting;	///< ステージに紐付けられたShootingオブジェクト
		/// ステージに紐付けられたshootingオブジェクトの取得
		/// @return ステージに紐付けられたShootingオブジェクト
		public Shooting getShooting() { return this.shooting; }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage(Shooting shooting) {
		this.shooting = shooting;
	}

	/// ステージの名前を取得
	/// @return ステージの名前
	abstract public String getStageName();
	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	abstract public Player[] getEnemies();
}

