package shooting.example.examplestage;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// ステージのサンプル
public class ExampleStage extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public ExampleStage(Shooting shooting) { super(shooting); }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	/// @param imgLoader DocumentBaseImageLoaderオブジェクト
	public ExampleStage(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// ステージの名前を取得
	/// @return ステージの名前("Example Stage")
	public String getStageName() { return "Example Stage"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new ExampleAI(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2, 10, 0, 1);
		} else {
			enemies[0] = new ExampleAI(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
		}
		return enemies;
	}
}

