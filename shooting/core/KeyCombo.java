package shooting.core;

public class KeyCombo {
	private int keyCode;
		public int getKeyCode() { return keyCode; }
		public void setKeyCode(int value) { keyCode = value; }
	private char keyChar;
		public char getKeyChar() { return keyChar; }
		public void setKeyChar(char value) { keyChar= value; }

	public KeyCombo(int keyCode, char keyChar) {
		this.keyCode = keyCode;
		this.keyChar = keyChar;
	}

	public boolean isPressed(KeyCombo key) {
		return isPressed(key.getKeyCode(), key.getKeyChar());
	}

	public boolean isPressed(int keyCode, char keyChar) {
		return this.keyCode == keyCode && this.keyChar == keyChar;
	}
}

