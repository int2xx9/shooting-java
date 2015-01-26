package shooting.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// Weaponが発射するLazer
public abstract class Lazer implements MainLoopJob, ShootingObject {
	/// 幅の取得
	/// @return 幅
	abstract public int getWidth();
	/// 高さの取得
	/// @return 高さ
	abstract public int getHeight();
	/// 与えるダメージの取得
	/// @return 与えるダメージ
	abstract public int getDamage();

	private Player player;	///< 紐付けられているPlayer
		/// 紐付けられているPlayerの取得
		/// @return 紐付けられているPlayer
		public Player getPlayer() { return this.player; }
	private int x;	///< X座標
	private int y;	///< Y座標
		/// X座標の取得
		/// @return X座標
		public int getX() { return this.x; }
		/// Y座標の取得
		/// @return Y座標
		public int getY() { return this.y; }
	private int sx;	///< X軸の移動方向と量
	private int sy;	///< Y軸の移動方向と量
		/// X軸の移動方向と量を取得
		/// @return X軸の移動方向と量
		public int getShootingToX() { return this.sx; }
		/// Y軸の移動方向と量を取得
		/// @return Y軸の移動方向と量
		public int getShootingToY() { return this.sy; }

	/// コンストラクタ
	/// @param x 生成するX座標の位置
	/// @param y 生成するY座標の位置
	/// @param sx X軸の発射する方向と速度
	/// @param sy Y軸の発射する方向と速度
	public Lazer(Player player, int x, int y, int sx, int sy) {
		this.player = player;
		this.x = x-getWidth()/2; this.y = y;
		this.sx = sx; this.sy = sy;
	}

	/// 画面外に存在するかどうか
	/// @return 画面外に存在した場合true, そうでない場合false
	public boolean isOutOfScreen() {
		return y+getHeight() < 0 || y > player.getShooting().getHeight();
	}

	/// メインループから呼ばれる処理
	///
	/// ここでは移動
	public void runMainLoopJob() {
		x += sx;
		y += sy;
	}

	/// オブジェクトの描画
	/// @param g
	public void paintObject(Graphics g) {
		g.setColor(Color.YELLOW);
		g.fillRect(x, y, getWidth(), getHeight());
	}

	/// 当たり判定
	///
	/// とりあえずLazerは当たり判定を持たないので常にfalseを返す
	/// @return false
	public boolean isHit(Lazer lazer) {
		return false;
	}
}

