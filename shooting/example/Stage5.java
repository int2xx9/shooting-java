package shooting.example;

import shooting.core.*;
import shooting.example.stage.*;
import java.awt.Image;
import shooting.example.DocumentBaseImageLoader;

/// ステージの例5
class Stage5 extends Stage {
	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage5(Shooting shooting) { super(shooting); }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	/// @param imgLoader DocumentBaseImageLoaderオブジェクト
	public Stage5(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		super(shooting, imgLoader);
	}

	/// ステージの名前を取得
	/// @return ステージの名前("Stage 5")
	public String getStageName() { return "Stage 5"; }

	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	public Player[] getEnemies() {
		Player[] enemies = new Player[1];
		Image image = getImageLoader().getImageImmediately("k1_p2a.png");
		if (image != null) {
			enemies[0] = new Stage5AI(getShooting(), image, 1, getShooting().getWidth()/2-image.getWidth(null)/2, 10, 0, 1);
		} else {
			enemies[0] = new Stage5AI(getShooting(), 1, getShooting().getWidth()/2, 10, 0, 1);
		}
		return enemies;
	}
}

/// 自動で移動・レーザの発射を行うPlayer
class Stage5AI extends Player {
	public static final int DEFAULT_WIDTH = 25;	///< デフォルトの幅
	public static final int DEFAULT_HEIGHT = 25;	///< デフォルトの高さ
	public static final int MAX_DAMAGE = 30;	///< 最大ダメージ
	/// 幅の取得
	/// @return 幅
	public int getWidth() { return getImage() != null ? getImage().getWidth(null) : DEFAULT_WIDTH; }
	/// 高さの取得
	/// @return 高さ
	public int getHeight() { return getImage() != null ? getImage().getHeight(null) : DEFAULT_HEIGHT; }
	/// 最大ダメージの取得
	/// @return 最大ダメージ
	public int getMaxDamage() { return MAX_DAMAGE; }

	/// コンストラクタ
	/// @param shooting Shootingクラスのオブジェクト
	/// @param team チーム番号
	/// @param x 配置する左上からのX座標
	/// @param y 配置する左上からのY座標
	/// @param sx 発射の方向・速さのX座標
	/// @param sy 発射の方向・速さのY座標
	public Stage5AI(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this(shooting, null, team, x, y, sx, sy);
	}

	/// コンストラクタ
	/// @param shooting Shootingクラスのオブジェクト
	/// @param image 機体の画像
	/// @param team チーム番号
	/// @param x 配置する左上からのX座標
	/// @param y 配置する左上からのY座標
	/// @param sx 発射の方向・速さのX座標
	/// @param sy 発射の方向・速さのY座標
	public Stage5AI(Shooting shooting, Image image, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setImage(image);
		setWeapon(new DefaultWeapon(this));
		setMovingX(2);
	}

	private int interval_counter = 0;	///< プレイヤーへ向かって進む頻度の調整用カウンタ
	private final int LOOP_INTERVAL = 180;	///< プレイヤーへ向かって進む間隔
	/// メインループから呼ばれる処理
	///
	/// ここでは移動とレーザの発射処理を行っている
	public void runMainLoopJob() {
		// 1/50の確率でレーザを発射する
		// (ただしWeaponが発射可能状態でなければ発射しない)
		if ((int)(Math.random()*50) == 0) {
			getWeapon().shoot();
		}
		// これ以上今の向きへ進めなくなったら方向転換する
		if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
			setMovingX(-getMovingX());
		}
		setX(getX()+getMovingX());

		interval_counter = (interval_counter+1) % LOOP_INTERVAL;
		if (interval_counter == LOOP_INTERVAL-1) {
			if (getY() + 10 + getHeight() > getShooting().getHeight()-60) {
				for (Player player : getShooting().getPlayers()) {
					if (player.getTeam() != this.getTeam()) {
						player.setDamage(10000);
					}
				}
			}
			setY(getY()+10);
		}
	}
}

