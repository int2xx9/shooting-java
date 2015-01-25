package shooting.core;

/// ControllablePlayer�����̐ݒ�
public class ControllablePlayerConfig {
	private KeyCombo moveLeftKey = null;	///< ���Ɉړ�����L�[
		/// ���Ɉړ�����L�[�̎擾
		/// @return ���Ɉړ�����L�[
		public KeyCombo getMoveLeftKey() { return moveLeftKey; }
		/// ���Ɉړ�����L�[�̐ݒ�
		/// @param value ���Ɉړ�����L�[
		public void setMoveLeftKey(KeyCombo value) { moveLeftKey = value; }
		/// ���Ɉړ�����L�[�̐ݒ�
		/// @param keyCode ���Ɉړ�����L�[��keyCode
		/// @param keyChar ���Ɉړ�����L�[��keyChar
		public void setMoveLeftKey(int keyCode, char keyChar) { moveLeftKey = new KeyCombo(keyCode, keyChar); }
		/// ���Ɉړ�����L�[�̐ݒ�
		/// @param keyCode ���Ɉړ�����L�[��keyCode
		/// @param keyChar ���Ɉړ�����L�[��keyChar
		public void setMoveLeftKey(int keyCode, int keyChar) { setMoveLeftKey(keyCode, (char)keyChar); }
		/// ���Ɉړ�����L�[��������Ă��邩
		/// @param key ���Ɉړ�����L�[
		/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
		public boolean isMoveLeftKey(KeyCombo key) { return isMoveLeftKey(key.getKeyCode(), key.getKeyChar()); }
		/// ���Ɉړ�����L�[��������Ă��邩
		/// @param keyCode ���Ɉړ�����L�[��keyCode
		/// @param keyChar ���Ɉړ�����L�[��keyChar
		/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
		public boolean isMoveLeftKey(int keyCode, char keyChar) {
			return moveLeftKey != null && moveLeftKey.isPressed(keyCode, keyChar);
		}
	private KeyCombo moveRightKey = null;	///< �E�Ɉړ�����L�[
		/// �E�Ɉړ�����L�[�̎擾
		/// @return �E�Ɉړ�����L�[
		public KeyCombo getMoveRightKey() { return moveRightKey; }
		/// �E�Ɉړ�����L�[�̐ݒ�
		/// @param value �E�Ɉړ�����L�[
		public void setMoveRightKey(KeyCombo value) { moveRightKey = value; }
		/// �E�Ɉړ�����L�[�̐ݒ�
		/// @param keyCode �E�Ɉړ�����L�[��keyCode
		/// @param keyChar �E�Ɉړ�����L�[��keyChar
		public void setMoveRightKey(int keyCode, char keyChar) { moveRightKey = new KeyCombo(keyCode, keyChar); }
		/// �E�Ɉړ�����L�[�̐ݒ�
		/// @param keyCode �E�Ɉړ�����L�[��keyCode
		/// @param keyChar �E�Ɉړ�����L�[��keyChar
		public void setMoveRightKey(int keyCode, int keyChar) { setMoveRightKey(keyCode, (char)keyChar); }
		/// �E�Ɉړ�����L�[��������Ă��邩
		/// @param key �E�Ɉړ�����L�[
		/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
		public boolean isMoveRightKey(KeyCombo key) { return isMoveRightKey(key.getKeyCode(), key.getKeyChar()); }
		/// �E�Ɉړ�����L�[��������Ă��邩
		/// @param keyCode �E�Ɉړ�����L�[��keyCode
		/// @param keyChar �E�Ɉړ�����L�[��keyChar
		/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
		public boolean isMoveRightKey(int keyCode, char keyChar) {
			return moveRightKey != null && moveRightKey.isPressed(keyCode, keyChar);
		}
	private KeyCombo shootKey = null;	///< ���[�U�𔭎˂���L�[
		/// ���[�U�𔭎˂���L�[�̎擾
		/// @return ���[�U�𔭎˂���L�[
		public KeyCombo getShootKey() { return shootKey; }
		/// ���[�U�𔭎˂���L�[�̐ݒ�
		/// @param value ���[�U�𔭎˂���L�[
		public void setShootKey(KeyCombo value) { shootKey = value; }
		/// ���[�U�𔭎˂���L�[�̐ݒ�
		/// @param keyCode ���[�U�𔭎˂���L�[��keyCode
		/// @param keyChar ���[�U�𔭎˂���L�[��keyChar
		public void setShootKey(int keyCode, char keyChar) { shootKey = new KeyCombo(keyCode, keyChar); }
		/// ���[�U�𔭎˂���L�[�̐ݒ�
		/// @param keyCode ���[�U�𔭎˂���L�[��keyCode
		/// @param keyChar ���[�U�𔭎˂���L�[��keyChar
		public void setShootKey(int keyCode, int keyChar) { setShootKey(keyCode, (char)keyChar); }
		/// ���[�U�𔭎˂���L�[��������Ă��邩
		/// @param key ���[�U�𔭎˂���L�[
		/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
		public boolean isShootKey(KeyCombo key) { return isShootKey(key.getKeyCode(), key.getKeyChar()); }
		/// ���[�U�𔭎˂���L�[��������Ă��邩
		/// @param keyCode ���[�U�𔭎˂���L�[��keyCode
		/// @param keyChar ���[�U�𔭎˂���L�[��keyChar
		/// @return ������Ă���ꍇtrue, ����ȊO�̏ꍇfalse
		public boolean isShootKey(int keyCode, char keyChar) {
			return shootKey != null && shootKey.isPressed(keyCode, keyChar);
		}
}

