package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;

/// ステージの例1
class Stage1 extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage1(Shooting shooting) { super(shooting); }

	/// ステージの名前を取得
	/// @return ステージの名前("Stage 1")
	public String getStageName() { return "Stage 1"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

