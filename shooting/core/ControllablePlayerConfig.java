package shooting.core;

public class ControllablePlayerConfig {
	private KeyCombo moveLeftKey = null;
		public KeyCombo getMoveLeftKey() { return moveLeftKey; }
		public void setMoveLeftKey(KeyCombo value) { moveLeftKey = value; }
		public void setMoveLeftKey(int keyCode, char keyChar) { moveLeftKey = new KeyCombo(keyCode, keyChar); }
		public void setMoveLeftKey(int keyCode, int keyChar) { setMoveLeftKey(keyCode, (char)keyChar); }
		public boolean isMoveLeftKey(KeyCombo key) { return isMoveLeftKey(key.getKeyCode(), key.getKeyChar()); }
		public boolean isMoveLeftKey(int keyCode, char keyChar) {
			return moveLeftKey != null && moveLeftKey.isPressed(keyCode, keyChar);
		}
	private KeyCombo moveRightKey = null;
		public KeyCombo getMoveRightKey() { return moveRightKey; }
		public void setMoveRightKey(KeyCombo value) { moveRightKey = value; }
		public void setMoveRightKey(int keyCode, char keyChar) { moveRightKey = new KeyCombo(keyCode, keyChar); }
		public void setMoveRightKey(int keyCode, int keyChar) { setMoveRightKey(keyCode, (char)keyChar); }
		public boolean isMoveRightKey(KeyCombo key) { return isMoveRightKey(key.getKeyCode(), key.getKeyChar()); }
		public boolean isMoveRightKey(int keyCode, char keyChar) {
			return moveRightKey != null && moveRightKey.isPressed(keyCode, keyChar);
		}
	private KeyCombo shootKey = null;
		public KeyCombo getShootKey() { return shootKey; }
		public void setShootKey(KeyCombo value) { shootKey = value; }
		public void setShootKey(int keyCode, char keyChar) { shootKey = new KeyCombo(keyCode, keyChar); }
		public void setShootKey(int keyCode, int keyChar) { setShootKey(keyCode, (char)keyChar); }
		public boolean isShootKey(KeyCombo key) { return isShootKey(key.getKeyCode(), key.getKeyChar()); }
		public boolean isShootKey(int keyCode, char keyChar) {
			return shootKey != null && shootKey.isPressed(keyCode, keyChar);
		}
}

