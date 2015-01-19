package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// ステージの例1
class Stage1 extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage1(Shooting shooting) { super(shooting); }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	/// @param imgLoader DocumentBaseImageLoaderオブジェクト
	public Stage1(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// ステージの名前を取得
	/// @return ステージの名前("Stage 1")
	public String getStageName() { return "Stage 1"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new AIPlayer(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2, 10, 0, 1);
		} else {
			enemies[0] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
		}
		return enemies;
	}
}

