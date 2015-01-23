package shooting.example.examplestage;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import shooting.core.*;

/// AIのサンプル
public class ExampleAI extends Player {
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
		super(shooting, team, x, y, sx, sy*8);
		setImage(image);
		setWeapon(new ExampleWeapon(this));
		setMovingX(1);
	}

	/// メインループから呼ばれる処理
	///
	/// ここでは移動と弾の発射処理を行っている
	public void runMainLoopJob() {
		// 1/10の確率で弾を発射する
		// (ただしWeaponが発射可能状態でなければ発射しない)
		if ((int)(Math.random()*10) == 0) {
			getWeapon().shoot();
		}
		// これ以上今の向きへ進めなくなったら方向転換する
		if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
			setMovingX(-getMovingX());
		}
		setX(getX()+getMovingX());
	}
}

