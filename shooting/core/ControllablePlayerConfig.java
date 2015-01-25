package shooting.core;

/// ControllablePlayer向けの設定
public class ControllablePlayerConfig {
	private KeyCombo moveLeftKey = null;	///< 左に移動するキー
		/// 左に移動するキーの取得
		/// @return 左に移動するキー
		public KeyCombo getMoveLeftKey() { return moveLeftKey; }
		/// 左に移動するキーの設定
		/// @param value 左に移動するキー
		public void setMoveLeftKey(KeyCombo value) { moveLeftKey = value; }
		/// 左に移動するキーの設定
		/// @param keyCode 左に移動するキーのkeyCode
		/// @param keyChar 左に移動するキーのkeyChar
		public void setMoveLeftKey(int keyCode, char keyChar) { moveLeftKey = new KeyCombo(keyCode, keyChar); }
		/// 左に移動するキーの設定
		/// @param keyCode 左に移動するキーのkeyCode
		/// @param keyChar 左に移動するキーのkeyChar
		public void setMoveLeftKey(int keyCode, int keyChar) { setMoveLeftKey(keyCode, (char)keyChar); }
		/// 左に移動するキーが押されているか
		/// @param key 左に移動するキー
		/// @return 押されている場合true, それ以外の場合false
		public boolean isMoveLeftKey(KeyCombo key) { return isMoveLeftKey(key.getKeyCode(), key.getKeyChar()); }
		/// 左に移動するキーが押されているか
		/// @param keyCode 左に移動するキーのkeyCode
		/// @param keyChar 左に移動するキーのkeyChar
		/// @return 押されている場合true, それ以外の場合false
		public boolean isMoveLeftKey(int keyCode, char keyChar) {
			return moveLeftKey != null && moveLeftKey.isPressed(keyCode, keyChar);
		}
	private KeyCombo moveRightKey = null;	///< 右に移動するキー
		/// 右に移動するキーの取得
		/// @return 右に移動するキー
		public KeyCombo getMoveRightKey() { return moveRightKey; }
		/// 右に移動するキーの設定
		/// @param value 右に移動するキー
		public void setMoveRightKey(KeyCombo value) { moveRightKey = value; }
		/// 右に移動するキーの設定
		/// @param keyCode 右に移動するキーのkeyCode
		/// @param keyChar 右に移動するキーのkeyChar
		public void setMoveRightKey(int keyCode, char keyChar) { moveRightKey = new KeyCombo(keyCode, keyChar); }
		/// 右に移動するキーの設定
		/// @param keyCode 右に移動するキーのkeyCode
		/// @param keyChar 右に移動するキーのkeyChar
		public void setMoveRightKey(int keyCode, int keyChar) { setMoveRightKey(keyCode, (char)keyChar); }
		/// 右に移動するキーが押されているか
		/// @param key 右に移動するキー
		/// @return 押されている場合true, それ以外の場合false
		public boolean isMoveRightKey(KeyCombo key) { return isMoveRightKey(key.getKeyCode(), key.getKeyChar()); }
		/// 右に移動するキーが押されているか
		/// @param keyCode 右に移動するキーのkeyCode
		/// @param keyChar 右に移動するキーのkeyChar
		/// @return 押されている場合true, それ以外の場合false
		public boolean isMoveRightKey(int keyCode, char keyChar) {
			return moveRightKey != null && moveRightKey.isPressed(keyCode, keyChar);
		}
	private KeyCombo shootKey = null;	///< レーザを発射するキー
		/// レーザを発射するキーの取得
		/// @return レーザを発射するキー
		public KeyCombo getShootKey() { return shootKey; }
		/// レーザを発射するキーの設定
		/// @param value レーザを発射するキー
		public void setShootKey(KeyCombo value) { shootKey = value; }
		/// レーザを発射するキーの設定
		/// @param keyCode レーザを発射するキーのkeyCode
		/// @param keyChar レーザを発射するキーのkeyChar
		public void setShootKey(int keyCode, char keyChar) { shootKey = new KeyCombo(keyCode, keyChar); }
		/// レーザを発射するキーの設定
		/// @param keyCode レーザを発射するキーのkeyCode
		/// @param keyChar レーザを発射するキーのkeyChar
		public void setShootKey(int keyCode, int keyChar) { setShootKey(keyCode, (char)keyChar); }
		/// レーザを発射するキーが押されているか
		/// @param key レーザを発射するキー
		/// @return 押されている場合true, それ以外の場合false
		public boolean isShootKey(KeyCombo key) { return isShootKey(key.getKeyCode(), key.getKeyChar()); }
		/// レーザを発射するキーが押されているか
		/// @param keyCode レーザを発射するキーのkeyCode
		/// @param keyChar レーザを発射するキーのkeyChar
		/// @return 押されている場合true, それ以外の場合false
		public boolean isShootKey(int keyCode, char keyChar) {
			return shootKey != null && shootKey.isPressed(keyCode, keyChar);
		}
}

