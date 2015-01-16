package shooting.core;

/// KeyEventで使用されるkeyCode, keyCharの組み合わせ
public class KeyCombo {
	private int keyCode;
		/// keyCodeの取得
		/// @return keyCode
		public int getKeyCode() { return keyCode; }
		/// keyCodeの設定
		/// @param value keyCode
		public void setKeyCode(int value) { keyCode = value; }
	private char keyChar;
		/// keyCharの取得
		/// @return keyChar
		public char getKeyChar() { return keyChar; }
		/// keyCharの設定
		/// @param value keyChar
		public void setKeyChar(char value) { keyChar= value; }

	/// コンストラクタ
	/// @param keyCode keyCode
	/// @param keyChar keyChar
	public KeyCombo(int keyCode, char keyChar) {
		this.keyCode = keyCode;
		this.keyChar = keyChar;
	}

	/// キーが押されているかどうか
	/// @param key 押されているキー
	/// @return 押されている場合true, それ以外の場合false
	public boolean isPressed(KeyCombo key) {
		return isPressed(key.getKeyCode(), key.getKeyChar());
	}

	/// キーが押されているかどうか
	/// @param keyCode 押されているキーのkeyCode
	/// @param keyChar 押されているキーのkeyChar
	/// @return 押されている場合true, それ以外の場合false
	public boolean isPressed(int keyCode, char keyChar) {
		return this.keyCode == keyCode && this.keyChar == keyChar;
	}
}

