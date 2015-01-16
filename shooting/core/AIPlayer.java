package shooting.core;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// 自動で移動・弾の発射を行うPlayer
public class AIPlayer extends Player {
	public static final int WIDTH = 25;	///< 幅
	public static final int HEIGHT = 25;	///< 高さ
	public static final int MAX_DAMAGE = 30;	///< 最大ダメージ
	/// 幅の取得
	/// @return 幅
	public int getWidth() { return WIDTH; }
	/// 高さの取得
	/// @return 高さ
	public int getHeight() { return HEIGHT; }
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
	public AIPlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new DefaultWeapon(this));
		setMovingX(1);
	}

	/// メインループから呼ばれた時何度に一度処理を行うか
	private static final int moveInterval = 5;
	/// moveInterval回に一度実行を制御するためのカウンタ
	private int moveIntervalCnt = 0;
	/// メインループから呼ばれる処理
	/// ここでは移動と弾の発射処理を行っている
	public void runMainLoopJob() {
		// 1/1000の確率で弾を発射する
		// (ただしWeaponが発射可能状態でなければ発射しない)
		if ((int)(Math.random()*1000) == 0) {
			getWeapon().shoot();
		}
		if (moveIntervalCnt == moveInterval) {
			// これ以上今の向きへ進めなくなったら方向転換する
			if (!canMoveTo(getX()+getMovingX(), getY(), getWidth(), getHeight())) {
				setMovingX(-getMovingX());
			}
			setX(getX()+getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}
}

