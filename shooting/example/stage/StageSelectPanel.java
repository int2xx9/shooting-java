package shooting.example.stage;

import shooting.core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

/// ステージ選択画面
public class StageSelectPanel extends JPanel {
	/// 表示されるステージの一覧
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	/// ステージが選択されたとき呼ばれるリスナ
	private StageSelectListener listener;
	/// 現在選択しているステージの位置
	private int selectPosition = 0;

	/// コンストラクタ
	/// @param shooting 紐付けるshootingオブジェクト
	/// @param listener ステージが選択されたとき呼ばれるリスナ
	public StageSelectPanel(Shooting shooting, StageSelectListener listener) {
		this.listener = listener;

		setBackground(Color.BLACK);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 65535) {
					if (e.getKeyCode() == 38) {	// ↑
						// 最初(selectPosition==0)のステージが選択されていたら最後
						// そうでなければ1つ前のステージを選択した状態にする
						selectPosition =
							(selectPosition == 0)
								? (stages.size()-1)
								: (selectPosition-1);
					} else if (e.getKeyCode() == 40) {	// ↓
						selectPosition = (selectPosition + 1) % stages.size();
					}
				} else if (e.getKeyChar() == 10 && e.getKeyCode() == 10) {	// Enter
					// ステージを選択した
					StageSelectPanel.this.listener.stageSelected(stages.get(selectPosition));
				}
				repaint();
			}
		});
	}

	/// ステージの追加
	/// @param stage 追加するステージ
	public void addStage(Stage stage) {
		stages.add(stage);
	}

	/// 選択中のステージの取得
	/// @return 選択中のステージ
	public Stage getSelectedStage() {
		return stages.get(selectPosition);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);

		// 一番上に表示するStage Select
		g.setFont(new Font("Monospaced", Font.BOLD, 24));
		g.drawString("StageSelect", getWidth()/2-60, 32);

		g.setFont(new Font("Monospaced", Font.BOLD, 16));
		// 選択中のステージの表示
		g.drawString("-> " + stages.get(selectPosition).getStageName(), 10, 64);
		int currentLine = 1;
		// 選択中のステージより後に追加されたステージの名前を表示
		for (int i = selectPosition+1; i < stages.size(); i++, currentLine++) {
			g.drawString("   " + stages.get(i).getStageName(), 10, 64+16*currentLine);
		}
		// 選択中のステージより前に追加されたステージの名前を表示
		for (int i = 0; i < selectPosition; i++, currentLine++) {
			g.drawString("   " + stages.get(i).getStageName(), 10, 64+16*currentLine);
		}
	}
}

