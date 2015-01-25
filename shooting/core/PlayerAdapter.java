package shooting.core;

/// プレイヤー関係のイベントのアダプタ
public class PlayerAdapter implements PlayerListener {
	/// スコアが更新された
	public void scoreUpdated() {}
	/// ダメージが更新された
	public void damageUpdated() {}
	/// コンボ数が更新された
	public void comboUpdated() {}
	/// レーザが当たった回数が更新された
	public void hitCountUpdated() {}
	/// レーザが外れた回数が更新された
	public void notHitCountUpdated() {}
	/// 破壊された
	public void playerDestroyed() {}
}

