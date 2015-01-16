package shooting.example.stage;

import shooting.core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/// �X�e�[�W�I�����
public class StageSelectPanel extends JPanel {
	/// �\�������X�e�[�W�̈ꗗ
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	/// �X�e�[�W���I�����ꂽ�Ƃ��Ă΂�郊�X�i
	private StageSelectListener listener;
	/// ���ݑI�����Ă���X�e�[�W�̈ʒu
	private int selectPosition = 0;

	/// �R���X�g���N�^
	/// @param shooting �R�t����shooting�I�u�W�F�N�g
	/// @param listener �X�e�[�W���I�����ꂽ�Ƃ��Ă΂�郊�X�i
	public StageSelectPanel(Shooting shooting, StageSelectListener listener) {
		this.listener = listener;

		setBackground(Color.BLACK);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 65535) {
					if (e.getKeyCode() == 38) {	// ��
						// �ŏ�(selectPosition==0)�̃X�e�[�W���I������Ă�����Ō�
						// �����łȂ����1�O�̃X�e�[�W��I��������Ԃɂ���
						selectPosition =
							(selectPosition == 0)
								? (stages.size()-1)
								: (selectPosition-1);
					} else if (e.getKeyCode() == 40) {	// ��
						selectPosition = (selectPosition + 1) % stages.size();
					}
				} else if (e.getKeyChar() == 10 && e.getKeyCode() == 10) {	// Enter
					// �X�e�[�W��I������
					StageSelectPanel.this.listener.stageSelected(stages.get(selectPosition));
				}
				repaint();
			}
		});
	}

	/// �X�e�[�W�̒ǉ�
	/// @param stage �ǉ�����X�e�[�W
	public void addStage(Stage stage) {
		stages.add(stage);
	}

	/// �I�𒆂̃X�e�[�W�̎擾
	/// @return �I�𒆂̃X�e�[�W
	public Stage getSelectedStage() {
		return stages.get(selectPosition);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);

		// ��ԏ�ɕ\������Stage Select
		g.setFont(new Font("Monospaced", Font.BOLD, 24));
		g.drawString("StageSelect", getWidth()/2-60, 32);

		g.setFont(new Font("Monospaced", Font.BOLD, 16));
		// �I�𒆂̃X�e�[�W�̕\��
		g.drawString("-> " + stages.get(selectPosition).getStageName(), 10, 64);
		int currentLine = 1;
		// �I�𒆂̃X�e�[�W����ɒǉ����ꂽ�X�e�[�W�̖��O��\��
		for (int i = selectPosition+1; i < stages.size(); i++, currentLine++) {
			g.drawString("   " + stages.get(i).getStageName(), 10, 64+16*currentLine);
		}
		// �I�𒆂̃X�e�[�W���O�ɒǉ����ꂽ�X�e�[�W�̖��O��\��
		for (int i = 0; i < selectPosition; i++, currentLine++) {
			g.drawString("   " + stages.get(i).getStageName(), 10, 64+16*currentLine);
		}
	}
}

