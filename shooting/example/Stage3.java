package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// ステージの例3
/// 無理矢理対人戦の例
class Stage3 extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage3(Shooting shooting) { super(shooting); }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	/// @param imgLoader DocumentBaseImageLoaderオブジェクト
	public Stage3(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// ステージの名前を取得
	/// @return ステージの名前("Stage 3")
	public String getStageName() { return "Stage 3"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		ControllablePlayer[] enemies = new ControllablePlayer[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new ControllablePlayer(getShooting(), image, 1,
					getShooting().getWidth()/2-image.getWidth(null)/2,
					10,
					0, 1);
		} else {
			enemies[0] = new ControllablePlayer(getShooting(), image, 1,
					getShooting().getWidth()/2-ControllablePlayer.DEFAULT_WIDTH/2,
					10,
					0, 1);
		}
		enemies[0].getKeyConfig().setMoveLeftKey(65, 97);	// a
		enemies[0].getKeyConfig().setMoveRightKey(68, 100);	// d
		enemies[0].getKeyConfig().setShootKey(83, 115);			// s
		return enemies;
	}
}

