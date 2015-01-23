package shooting.example.examplestage;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shooting.core.*;

/// AIのサンプル
public class ExampleAI extends Player implements PlayerListener {
	public static final int DEFAULT_WIDTH = 30;	///< デフォルトの幅
	public static final int DEFAULT_HEIGHT = 30;	///< デフォルトの高さ
	public static final int MAX_DAMAGE = 100;	///< 最大ダメージ
	/// 幅の取得
	/// @return 幅
	public int getWidth() { return getImage() != null ? getImage().getWidth(null) : DEFAULT_WIDTH; }
	/// 高さの取得
	/// @return 高さ
	public int getHeight() { return getImage() != null ? getImage().getHeight(null) : DEFAULT_HEIGHT; }
	/// 最大ダメージの取得
	/// @return 最大ダメージ
	public int getMaxDamage() { return MAX_DAMAGE; }
	private Weapon subWeapon;	///< Player.weaponとは別のWeapon

	/// コンストラクタ
	/// @param shooting Shootingクラスのオブジェクト
	/// @param team チーム番号
	/// @param x 配置する左上からのX座標
	/// @param y 配置する左上からのY座標
	/// @param sx 発射の方向・速さのX座標
	/// @param sy 発射の方向・速さのY座標
	public ExampleAI(Shooting shooting, int team, int x, int y, int sx, int sy) {
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
	public ExampleAI(Shooting shooting, Image image, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setImage(image);
		setWeapon(new ExampleWeapon(this));
		setMovingX(1);
		subWeapon = new DefaultWeapon(this);
	}

	/// メインループから呼ばれる処理
	///
	/// ここでは移動と弾の発射処理を行っている
	public void runMainLoopJob() {
		// subWeaponは撃てる時は毎回撃つ
		subWeapon.shoot();
		if (getWeapon().isCharged()) {
			// Weaponが使用できる場合、敵がAIから狙える位置にいるかを判断してから撃つ
			for (Player player : getShooting().getPlayers()) {
				if (player.getTeam() != this.getTeam()) {
					int shootxpos = this.getX() + this.getWidth()/2;
					if (player.getMovingX() > 0) {
						// 右に移動していた場合の補正
						shootxpos -= 50;
					} else if (player.getMovingX() < 0) {
						// ←に移動していた場合の補正
						shootxpos += 50;
					}
					if (shootxpos >= player.getX() && shootxpos <= player.getX()+player.getWidth()) {
						getWeapon().shoot();
					}
				}
		}

		// 1/400の確率で方向転換する
		if ((int)(Math.random()*400) == 0) {
			setMovingX(-getMovingX());
		}

		// これ以上今の向きへ進めなくなったら方向転換する
		if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
			setMovingX(-getMovingX());
		}
		setX(getX()+getMovingX());
	}

	/// スコアが更新された
	public void scoreUpdated() {
	}

	/// ダメージが更新された
	public void damageUpdated() {
	}

	/// コンボ数が更新された
	public void comboUpdated() {
	}

	/// 弾が当たった回数が更新された
	public void hitCountUpdated() {
		// 1/10の確率で逆方向に移動する
		if ((int)(Math.random()*10) == 0) {
			setMovingX(-getMovingX());
		}
	}

	/// 弾が外れた回数が更新された
	public void notHitCountUpdated() {
	}

	/// 破壊された
	public void playerDestroyed() {
	}
}

