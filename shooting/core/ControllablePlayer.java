package shooting.core;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// ユーザが操作できるPlayer
public class ControllablePlayer extends Player {
	public static final int WIDTH = 25;	///< 幅
	public static final int HEIGHT = 25;	///< 高さ
	public static final int MAX_DAMAGE = 50;	///< 最大ダメージ
	/// 幅の取得
	/// @return 幅
	public int getWidth() { return WIDTH; }
	/// 高さの取得
	/// @return 高さ
	public int getHeight() { return HEIGHT; }
	/// 最大ダメージの取得
	/// @return 最大ダメージ
	public int getMaxDamage() { return MAX_DAMAGE; }

	/// キー設定
	public ControllablePlayerConfig keyConfig = new ControllablePlayerConfig();
	/// キー設定の取得
	/// @return キー設定
	public ControllablePlayerConfig getKeyConfig() { return keyConfig; }

	/// コンストラクタ
	/// @param shooting Shootingクラスのオブジェクト
	/// @param team チーム番号
	/// @param x 配置する左上からのX座標
	/// @param y 配置する左上からのY座標
	/// @param sx 発射の方向・速さのX座標
	/// @param sy 発射の方向・速さのY座標
	public ControllablePlayer(Shooting shooting, int team, int x, int y, int sx, int sy) {
		super(shooting, team, x, y, sx, sy);
		setWeapon(new DefaultWeapon(this));
	}

	/// 初期化
	public void initialize() {
		super.initialize();
		setMovingX(0);
		setMovingY(0);
	}

	/// メインループから呼ばれた時何度に一度処理を行うか
	private static final int moveInterval = 5;
	/// moveInterval回に一度実行を制御するためのカウンタ
	private int moveIntervalCnt = 0;
	/// メインループから呼ばれる処理
	/// ここでは移動処理を行っている
	public void runMainLoopJob() {
		if (moveIntervalCnt == moveInterval) {
			setX(getX() + getMovingX());
			moveIntervalCnt = 0;
		}
		moveIntervalCnt++;
	}

	/// キーが押された場合の処理
	/// @param e KeyEventのオブジェクト
	public void keyPressed(KeyEvent e) {
		if (keyConfig.isMoveLeftKey(e.getKeyCode(), e.getKeyChar())) {
			setMovingX(-1);
		} else if (keyConfig.isMoveRightKey(e.getKeyCode(), e.getKeyChar())) {
			setMovingX(1);
		}
	}

	/// キーが離された場合の処理
	/// @param e KeyEventのオブジェクト
	public void keyReleased(KeyEvent e) {
		// 移動関係のキーが押された場合は離されたキーと移動中の方向が同じかどうかを確認し、
		// 同じである場合のみ移動をやめる
		if (keyConfig.isMoveLeftKey(e.getKeyCode(), e.getKeyChar()) && getMovingX() < 0) {
			setMovingX(0);
		} else if (keyConfig.isMoveRightKey(e.getKeyCode(), e.getKeyChar()) && getMovingX() > 0) {
			setMovingX(0);
		} else if (keyConfig.isShootKey(e.getKeyCode(), e.getKeyChar())) {
			getWeapon().shoot();
		}
	}
}

