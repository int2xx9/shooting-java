package shooting.core;

/// ゲーム関係のイベントのリスナ
public interface ShootingListener {
	/// ゲームが初期化された
	public void onGameInitialized();
	/// ゲームが再開された
	public void onGameResumed();
	/// ゲームが一時停止された
	public void onGamePaused();
	/// ゲームが再読み込みされた
	public void onGameRestarted();
	/// ゲームオーバー
	public void onGameOvered();
}

