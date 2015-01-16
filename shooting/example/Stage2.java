package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;

/// ステージの例2
class Stage2 extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage2(Shooting shooting) { super(shooting); }

	/// ステージの名前を取得
	/// @return ステージの名前("Stage 2")
	public String getStageName() { return "Stage 2"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[4];
		enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		enemies[1] = new AIPlayer(getShooting(), 1, 30, 90, 0, 1);
		enemies[2] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2+40, 120, 0, 1);
		enemies[3] = new AIPlayer(getShooting(), 1, getShooting().getWidth()-30, 150, 0, 1);
		return enemies;
	}
}

