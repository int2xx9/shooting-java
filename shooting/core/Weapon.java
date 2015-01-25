package shooting.core;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;

/// Playerが使用するWeapon
public abstract class Weapon implements MainLoopJob {
	/// このWeaponは発射可能になることはないことを示す
	public static final int INTERVAL_INFINITY = -1;

	/// 発射可能になるまでの間隔の取得
	/// @return 発射可能になるまでの間隔
	abstract public int getInterval();
	/// LazerGeneratorの取得
	/// @return LazerGeneratorのオブジェクト
	abstract public LazerGenerator getGenerator(Player player);

	private int loopCount = 0;	///< 発射間隔を制御するためのカウンタ
	private Player player;	///< このWeaponに紐付けられているPlayer
	private boolean initialCharged;	///< chargedの初期値
	private boolean charged;	///< 発射可能かどうか
		/// 発射可能かどうか
		/// @return 発射可能な場合true, 不可能な場合false
		public boolean isCharged() { return this.charged; }

	/// コンストラクタ
	/// @param player Playerのオブジェクト
	public Weapon(Player player) {
		this(player, true);
	}

	/// コンストラクタ
	/// @param player Playerのオブジェクト
	/// @param charged chargedの状態をtrueにするかfalseにするか
	public Weapon(Player player, boolean charged) {
		this.player = player;
		this.initialCharged = charged;
		// INTERVAL_INFINITYの場合は意味がないのでメインループに登録しない
		if (getInterval() != INTERVAL_INFINITY) {
			this.player.getShooting().addMainLoopJob(this);
		}
		initialize();
	}

	/// 初期化
	public void initialize() {
		// INTERVAL_INFINITYでない場合のみ発射可能な状態で初期化する
		this.charged = this.initialCharged;
		this.loopCount = 0;
	}

	/// レーザの発射
	public void shoot() {
		if (player != null && player.getShooting().isRunning() && charged) {
			loopCount = 0;
			charged = false;
			Lazer lazer = null;
			if (player.getShootToY() < 0) {
				// 上方向
				lazer = getGenerator(player).generateLazer(
						player.getX()+player.getWidth()/2, player.getY(),
						player.getShootToX(), player.getShootToY());
			} else if (player.getShootToY() > 0) {
				// 下方向
				lazer = getGenerator(player).generateLazer(
						player.getX()+player.getWidth()/2, player.getY()+player.getHeight(),
						player.getShootToX(), player.getShootToY());
			}
			if (lazer != null) {
				player.getShooting().lazers.shoot(lazer);
			}
		}
	}

	/// メインループから呼ばれる処理
	///
	/// ここでは発射できるかどうかの制御を行っている
	public void runMainLoopJob() {
		if (!charged) {
			loopCount = (loopCount + 1) % getInterval();
			if (loopCount == 0) {
				charged = true;
			}
		}
	}
}

/// レーザの発射ができないダミーのWeapon
///
/// どのオブジェクトでも共通のものを使用するためシングルトンにしてある
class DummyWeapon extends Weapon {
	/// このクラスのインスタンス
	private static DummyWeapon instance = new DummyWeapon();
	/// 発射可能になるまでの間隔の取得
	///
	/// 発射可能になることはないのでWeapon.INTERVAL_INFINITYを返す
	/// @return Weapon.INTERVAL_INFINITY
	public int getInterval() { return Weapon.INTERVAL_INFINITY; }
	/// LazerGeneratorの取得
	/// DummyWeaponでは使用しないのでnullを返す
	/// @return null
	public LazerGenerator getGenerator(Player player) { return null; }
	/// コンストラクタ
	///
	/// 外部からインスタンス化できないようにprivateにしてある
	private DummyWeapon() { super(null); }
	/// DummyWeaponインスタンスの取得
	/// @return DummyWeaponのインスタンス
	static DummyWeapon getInstance() { return instance; }
}

