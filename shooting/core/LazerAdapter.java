package shooting.core;

/// Lazer関係のイベントのアダプタ
public class LazerAdapter implements LazerListener {
	/// 撃ったレーザーが的中した
	public void lazerHit() {}
	/// 撃ったレーザーがはずれた
	/// (Playerクラスのオブジェクトにあたらないまま画面外までいった)
	public void lazerNotHit() {}
}

