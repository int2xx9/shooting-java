package shooting.core;

/// ゲーム関係のイベントのアダプタ
public class ShootingAdapter implements ShootingListener {
	/// ゲームが初期化された
	public void onGameInitialized() {}
	/// ゲームが再開された
	public void onGameResumed() {}
	/// ゲームが一時停止された
	public void onGamePaused() {}
	/// ゲームが再読み込みされた
	public void onGameRestarted() {}
	/// ゲームオーバー
	public void onGameOvered() {}
}

