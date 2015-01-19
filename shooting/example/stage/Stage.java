package shooting.example.stage;

import shooting.core.*;
import shooting.example.DocumentBaseImageLoader;

/// ステージ
public abstract class Stage {
	private Shooting shooting;	///< ステージに紐付けられたShootingオブジェクト
		/// ステージに紐付けられたshootingオブジェクトの取得
		/// @return ステージに紐付けられたShootingオブジェクト
		public Shooting getShooting() { return this.shooting; }

	/// DocumentBaseImageLoaderオブジェクト
	private DocumentBaseImageLoader imgLoader = null;
		/// DocumentBaseImageLoaderの取得
		/// @return DocumentBaseImageLoaderオブジェクト
		public DocumentBaseImageLoader getImageLoader() { return this.imgLoader; }

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	public Stage(Shooting shooting) {
		this.shooting = shooting;
	}

	/// コンストラクタ
	/// @param shooting ステージに紐付けるShootingオブジェクト
	/// @param imgLoader DocumentBaseImageLoaderオブジェクト
	public Stage(Shooting shooting, DocumentBaseImageLoader imgLoader) {
		this.shooting = shooting;
		this.imgLoader = imgLoader;
	}

	/// ステージの名前を取得
	/// @return ステージの名前
	abstract public String getStageName();
	/// ステージ上の敵の配列を取得
	/// @return ステージ上の敵の配列
	abstract public Player[] getEnemies();
}

