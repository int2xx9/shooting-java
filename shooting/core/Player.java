package shooting.core;

import java.util.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// AIあるいは手動で操作を行うプレイヤーの基底クラス
public abstract class Player implements MainLoopJob, ShootingObject, KeyListener, LazerListener {
	/// 幅の取得
	/// @return 幅
	abstract public int getWidth();
	/// 高さの取得
	/// @return 高さ
	abstract public int getHeight();
	/// 最大ダメージの取得
	/// @return 最大ダメージ
	abstract public int getMaxDamage();

	/// 登録されているPlayerListenerのリスト
	private LinkedList<PlayerListener> listeners = new LinkedList<PlayerListener>();

	private Shooting shooting;	///< このPlayerに紐付けられているshooting
		/// 設定されているshootingの取得
		/// @return 設定されているshooting
		public Shooting getShooting() { return this.shooting; }
	private int damage;	///< 現在受けているダメージ
		/// ダメージの設定
		/// @param value 新しく設定するダメージ
		public void setDamage(int value) {
			this.damage = value;
			for (PlayerListener listener : listeners) {
				listener.damageUpdated();
			}
			if (this.damage >= getMaxDamage()) {
				for (PlayerListener listener : listeners) {
					listener.playerDestroyed();
				}
			}
		}
		/// ダメージの取得
		/// @return 現在のダメージ
		public int getDamage() { return this.damage; }
		/// 生存しているかどうか
		/// @return 生存している時true, それ以外false
		public boolean isAlive() { return this.damage < getMaxDamage(); }
		/// 破壊されたかどうか
		/// @return 破壊されている時true, それ以外false
		public boolean isDestroyed() { return this.damage >= getMaxDamage(); }
	private int score;	///< 現在のスコア
		/// スコアの設定
		/// @param value 新しく設定するスコア
		public void setScore(int value) {
			this.score = value;
			for (PlayerListener listener : listeners) {
				listener.scoreUpdated();
			}
		}
		/// スコアの取得
		/// @return 現在のスコア
		public int getScore() { return this.score; }
	private int combo;	///< 現在のコンボ数
		/// コンボ数の設定
		/// @param value 新しく設定するコンボ数
		public void setCombo(int value) {
			this.combo = value;
			for (PlayerListener listener : listeners) {
				listener.comboUpdated();
			}
		}
		/// コンボ数のインクリメント
		public void incrementCombo() { setCombo(getCombo() + 1); }
		/// コンボ数の取得
		/// @return 現在のコンボ数
		public int getCombo() { return this.combo; }
	private int hitCount;	///< 現在までに弾が当たった回数
	private int notHitCount;	///< 現在までに弾が外れた回数
		/// 弾が当たった回数の設定
		/// @param value 新しく設定する弾が当たった回数
		public void setHitCount(int value) {
			this.hitCount = value;
			for (PlayerListener listener : listeners) {
				listener.hitCountUpdated();
			}
		}
		/// 弾が外れた回数の設定
		/// @param value 新しく設定する弾がはずれた回数
		public void setNotHitCount(int value) {
			this.notHitCount = value;
			for (PlayerListener listener : listeners) {
				listener.notHitCountUpdated();
			}
		}
		/// 弾が当たった回数のインクリメント
		public void incrementHitCount() { setHitCount(getHitCount() + 1); }
		/// 弾が外れた回数のインクリメント
		public void incrementNotHitCount() { setNotHitCount(getNotHitCount() + 1); }
		/// 弾が当たった回数の取得
		/// @return 現在までに弾が当たった回数
		public int getHitCount() { return this.hitCount; }
		/// 弾が外れた回数の取得
		/// @return 現在までに弾がはずれた回数
		public int getNotHitCount() { return this.notHitCount; }
		/// 命中率の取得
		/// @return 命中率
		public int getHitPercent() {
			if (hitCount+notHitCount == 0) return 0;
			return (int)(((double)hitCount)/(hitCount+notHitCount)*100);
		}
		/// 弾がはずれた割合の取得
		/// @return 現在までに弾がはずれた割合(百分率)
		public int getNotHitPercent() {
			if (hitCount+notHitCount == 0) return 0;
			return (int)(((double)notHitCount)/(hitCount+notHitCount)*100);
		}
	private int initialX;	///< 最初に指定された左上X座標
	private int initialY;	///< 最初に指定された左上Y座標
	private int x;	///<左上X座標
	private int y;	///<左上Y座標
		/// 中心座標のXを設定
		/// @param value 新しく設定する中心X座標
		public void setCenterX(int value) { setX(value-this.getWidth()/2); }
		/// 中心座標のYを設定
		/// @param value 新しく設定する中心Y座標
		public void setCenterY(int value) { setY(value-this.getHeight()/2); }
		/// 左上座標のXを設定
		/// @param x 新しく設定する左上X座標
		public void setX(int x) {
			if (canMoveTo(x, this.y, getWidth(), getHeight())) {
				this.x = x;
			}
		}
		/// 左上座標のYを設定
		/// @param y 新しく設定する左上Y座標
		public void setY(int y) {
			if (canMoveTo(this.y, y, getWidth(), getHeight())) {
				this.y = y;
			}
		}
		/// 中心座標のXを取得
		/// @return 現在の中心X座標
		public int getCenterX() { return this.x-this.getWidth()/2; }
		/// 中心座標のYを取得
		/// @return 現在の中心Y座標
		public int getCenterY() { return this.y-this.getHeight()/2; }
		/// 左上座標のXを取得
		/// @return 現在の左上X座標
		public int getX() { return this.x; }
		/// 左上座標のYを取得
		/// @return 現在の左上Y座標
		public int getY() { return this.y; }
	private int sx;	///< X軸の弾を撃つ方向・速さ
	private int sy;	///< Y軸の弾を撃つ方向・速さ
		/// 弾を撃つ方向・速さのX座標を設定
		/// @param value X軸の弾を撃つ方向・速さを示した値
		public void setShootToX(int value) { this.sx = value; }
		/// 弾を撃つ方向・速さのY座標を設定
		/// @param value Y軸の弾を撃つ方向・速さを示した値
		public void setShootToY(int value) { this.sy = value; }
		/// 弾を撃つ方向・速さのX座標を取得
		/// @return X軸の弾を撃つ方向・速さを示した値
		public int getShootToX() { return this.sx; }
		/// 弾を撃つ方向・速さのY座標を取得
		/// @return Y軸の弾を撃つ方向・速さを示した値
		public int getShootToY() { return this.sy; }
	private int mx;	///< X軸の移動方向・量
	private int my;	///< Y軸の移動方向・量
		/// 移動の方向・量のX座標を設定
		/// @param value X軸の移動方向・量を表した値
		public void setMovingX(int value) { this.mx = value; }
		/// 移動の方向・量のY座標を設定
		/// @param value Y軸の移動方向・量を表した値
		public void setMovingY(int value) { this.my = value; }
		/// 移動の方向・量のX座標を取得
		/// @return X軸の移動方向・量を表した値
		public int getMovingX() { return this.mx; }
		/// 移動の方向・量のY座標を取得
		/// @return Y軸の移動方向・量を表した値
		public int getMovingY() { return this.my; }
	private int team;	///< チーム番号
		/// チーム番号の設定
		/// @param value 設定するチーム番号
		public void setTeam(int value) { this.team = value; }
		/// チーム番号の取得
		/// @return 設定されているチーム番号
		public int getTeam() { return this.team; }
	private Weapon weapon;	///< 紐付けられている武器
		private static final Weapon dummyWeapon = DummyWeapon.getInstance();
		/// 武器の設定
		/// @param value 設定する武器
		public void setWeapon(Weapon value) { this.weapon = value; }
		/// 武器の取得
		/// @return 設定されている武器
		/// @return 破壊済みであれば発射不可能なダミーのWeaponを返す
		public Weapon getWeapon() { return isAlive() ? this.weapon : dummyWeapon; }

	/// コンストラクタ
	/// @param shooting Shootingクラスのオブジェクト
	/// @param x 配置する左上からのX座標
	/// @param y 配置する左上からのY座標
	/// @param sx 発射の方向・速さのX座標
	/// @param sy 発射の方向・速さのY座標
	public Player(Shooting shooting, int team, int x, int y, int sx, int sy) {
		this.shooting = shooting;
		this.team = team;
		this.initialX = this.x = x;
		this.initialY = this.y = y;
		this.sx = sx; this.sy = sy;
		this.weapon = dummyWeapon;
	}

	/// 初期化
	public void initialize() {
		this.score = this.damage = this.combo =
			this.hitCount = this.notHitCount = 0;
		this.x = this.initialX;
		this.y = this.initialY;
		this.weapon.initialize();
	}

	/// PlayerListenerの登録
	/// @param listener PlayerListenerのオブジェクト
	public void addPlayerListener(PlayerListener listener) {
		listeners.add(listener);
	}

	/// 指定した位置に移動可能か
	/// @param x 左上X座標
	/// @param y 左上Y座標
	/// @param width 幅
	/// @param height 高さ
	/// @return 移動可能な場合true, 不可能な場合false
	public boolean canMoveTo(int x, int y, int width, int height) {
		if (x < 0 || y < 0) return false;
		if (x+width > shooting.getWidth() || y > shooting.getHeight()) return false;
		return true;
	}

	/// オブジェクトの描画
	/// @param g
	public void paintObject(Graphics g) {
		if (isAlive()) {
			g.setColor(Color.WHITE);
			g.fillRect(getX(), getY(), getWidth(), getHeight());

			if (shooting.isKeyseqOn()) {
				g.setColor(Color.RED);
				g.drawString((getWeapon().isCharged() ? "*" : "-") + damage + "/" + getMaxDamage(), getX()+5, getY()+5);
			}
		}
	}

	/// 当たり判定
	/// @param lazer 判定を行うLazerオブジェクト
	/// @return 当たっていた場合true, 外れていた場合false
	public boolean isHit(Lazer lazer) {
		if (isDestroyed()) return false;
		if (getTeam() == lazer.getPlayer().getTeam()) return false;
		if (lazer.getY()+lazer.getHeight() < getY()) return false;
		if (lazer.getY() > getY()+getHeight()) return false;
		if (lazer.getX()+lazer.getWidth() < getX()) return false;
		if (lazer.getX() > getX()+getWidth()) return false;
		return true;
	}

	/// 弾が自分に当たった
	/// @param lazer あたったLazerオブジェクト
	public void onHit(Lazer lazer) {
		setDamage(getDamage() + lazer.getDamage());
		setCombo(0);
	}

	/// 自分が撃った弾が敵に当たった
	public void lazerHit() {
		setCombo(getCombo() + 1);
		setScore(getScore() + 10 * getCombo());
		incrementHitCount();
	}

	/// 自分が撃った弾が敵に当たらなかった
	public void lazerNotHit() {
		setCombo(0);
		incrementNotHitCount();
	}

	public void keyPressed(KeyEvent e) {}
	public void keyReleased(KeyEvent e) {}
	public void keyTyped(KeyEvent e) {}
}

