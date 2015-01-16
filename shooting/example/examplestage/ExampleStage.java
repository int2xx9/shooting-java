package shooting.example.examplestage;

import shooting.core.*;
import shooting.example.stage.*;

/// ステージのサンプル
public class ExampleStage extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public ExampleStage(Shooting shooting) { super(shooting); }

	/// ステージの名前を取得
	/// @return ステージの名前("Example Stage")
	public String getStageName() { return "Example Stage"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		enemies[0] = new ExampleAI(getShooting(), 1, getShooting().getWidth()/2, 60, 0, 1);
		return enemies;
	}
}

