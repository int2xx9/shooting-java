package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// ステージの例4
class Stage4 extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage4(Shooting shooting) { super(shooting); }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	/// @param imgLoader DocumentBaseImageLoaderオブジェクト
	public Stage4(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// ステージの名前を取得
	/// @return ステージの名前("Stage 4")
	public String getStageName() { return "Stage 4"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[4];
		Image player_image = getImageLoader().getImageImmediately("k1_p3a.png");
		Image enemy_image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (player_image != null) {
			enemies[0] = new ControllablePlayer(getShooting(), player_image, 0,
					getShooting().getWidth()/2-player_image.getWidth(null)/2,
					getShooting().getHeight()-60-player_image.getHeight(null)-10,
					0, -1);
		} else {
			enemies[0] = new ControllablePlayer(getShooting(), player_image, 0,
					getShooting().getWidth()/2-ControllablePlayer.DEFAULT_WIDTH/2,
					getShooting().getHeight()-60-ControllablePlayer.DEFAULT_HEIGHT-10,
					0, -1);
		}
		((ControllablePlayer)enemies[0]).getKeyConfig().setMoveLeftKey(65, 97);	// a
		((ControllablePlayer)enemies[0]).getKeyConfig().setMoveRightKey(68, 100);	// d
		((ControllablePlayer)enemies[0]).getKeyConfig().setShootKey(83, 115);			// s

		if (enemy_image != null) {
			enemies[1] = new AIPlayer(getShooting(), enemy_image, 1, getShooting().getWidth()/2-enemy_image.getWidth(null)/2, 10, 0, 1);
			enemies[2] = new AIPlayer(getShooting(), enemy_image, 1, 30, 70, 0, 1);
			enemies[3] = new AIPlayer(getShooting(), enemy_image, 1, getShooting().getWidth()/2-enemy_image.getWidth(null)/2+40, 130, 0, 1);
		} else {
			enemies[1] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
			enemies[2] = new AIPlayer(getShooting(), 1, 30, 70, 0, 1);
			enemies[3] = new AIPlayer(getShooting(), 1, getShooting().getWidth()/2+40, 130, 0, 1);
		}
		return enemies;
	}
}

