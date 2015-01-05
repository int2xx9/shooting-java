package shooting.example.stage;

import shooting.core.*;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class StageSelectPanel extends JPanel {
	private ArrayList<Stage> stages = new ArrayList<Stage>();
	private StageSelectListener listener;
	private int selectPosition = 0;

	public StageSelectPanel(Shooting shooting, StageSelectListener listener) {
		stages.add(new Stage1(shooting));
		stages.add(new Stage2(shooting));

		this.listener = listener;

		setBackground(Color.BLACK);
		addKeyListener(new KeyAdapter() {
			public void keyPressed(KeyEvent e) {
				if (e.getKeyChar() == 65535) {
					if (e.getKeyCode() == 38) {	// ª
						selectPosition =
							(selectPosition == 0)
								? (stages.size()-1)
								: (selectPosition-1);
					} else if (e.getKeyCode() == 40) {	// «
						selectPosition = (selectPosition + 1) % stages.size();
					}
				} else if (e.getKeyChar() == 10 && e.getKeyCode() == 10) {
					StageSelectPanel.this.listener.stageSelected(stages.get(selectPosition));
				}
				repaint();
			}
		});
	}

	public Stage getSelectedStage() {
		return stages.get(selectPosition);
	}

	public void paintComponent(Graphics g) {
		super.paintComponent(g);
		g.setColor(Color.WHITE);

		g.setFont(new Font("Monospaced", Font.BOLD, 24));
		g.drawString("StageSelect", getWidth()/2-60, 32);

		g.setFont(new Font("Monospaced", Font.BOLD, 16));
		g.drawString("-> " + stages.get(selectPosition).getStageName(), 10, 64);
		int currentLine = 1;
		for (int i = selectPosition+1; i < stages.size(); i++, currentLine++) {
			g.drawString("   " + stages.get(i).getStageName(), 10, 64+16*currentLine);
		}
		for (int i = 0; i < selectPosition; i++, currentLine++) {
			g.drawString("   " + stages.get(i).getStageName(), 10, 64+16*currentLine);
		}
	}
}

