package shooting.core;

/// プレイヤー関係のイベントのリスナ
public interface PlayerListener {
	/// スコアが更新された
	public void scoreUpdated();
	/// ダメージが更新された
	public void damageUpdated();
	/// コンボ数が更新された
	public void comboUpdated();
	/// 弾が当たった回数が更新された
	public void hitCountUpdated();
	/// 弾が外れた回数が更新された
	public void notHitCountUpdated();
	/// 破壊された
	public void playerDestroyed();
}

