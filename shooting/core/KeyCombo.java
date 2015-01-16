package shooting.core;

/// KeyEvent�Ŏg�p�����keyCode, keyChar�̑g�ݍ��킹
public class KeyCombo {
	private int keyCode;
		/// keyCode�̎擾
		/// @return keyCode
		public int getKeyCode() { return keyCode; }
		/// keyCode�̐ݒ�
		/// @param value keyCode
		public void setKeyCode(int value) { keyCode = value; }
	private char keyChar;
		/// keyChar�̎擾
		/// @return keyChar
		public char getKeyChar() { return keyChar; }
		/// keyChar�̐ݒ�
		/// @param value keyChar
		public void setKeyChar(char value) { keyChar= value; }

	/// �R���X�g���N�^
	/// @param keyCode keyCode
	/// @param keyChar keyChar
	public KeyCombo(int keyCode, char keyChar) {
		this.keyCode = keyCode;
		this.keyChar = keyChar;
	}

	/// �L�[��������Ă��邩�ǂ���
	/// @param key ������Ă���L�[
	/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
	public boolean isPressed(KeyCombo key) {
		return isPressed(key.getKeyCode(), key.getKeyChar());
	}

	/// �L�[��������Ă��邩�ǂ���
	/// @param keyCode ������Ă���L�[��keyCode
	/// @param keyChar ������Ă���L�[��keyChar
	/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
	public boolean isPressed(int keyCode, char keyChar) {
		return this.keyCode == keyCode && this.keyChar == keyChar;
	}
}

