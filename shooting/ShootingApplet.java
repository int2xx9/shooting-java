package shooting;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.*;

public class ShootingApplet extends JApplet {
	JButton resumePauseButton;
	JButton leftButton;
	JButton rightButton;
	JButton shootButton;
	StatusPanel statusPanel;
	Shooting shooting;
	Player player;

	public void init() {
		setLayout(null);

		shooting = new Shooting();
		shooting.setBounds(5, 5, getWidth()-(5*2), getHeight()-50-5);
		add(shooting);

		player = new Player(shooting, shooting.getWidth()/2, shooting.getHeight()-60, 0, -1);

		int ctrlY = getHeight()-50;

		resumePauseButton = new JButton("äJén");
		resumePauseButton.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (shooting.isRunning()) {
					shooting.setPaused();
				} else {
					shooting.setRunning();
				}
			}
		});
		shooting.addShootingListener(new ShootingAdapter() {
			public void onGameResumed() {
				resumePauseButton.setText("àÍéûí‚é~");
			}
			public void onGamePaused() {
				resumePauseButton.setText("çƒäJ");
			}
		});
		resumePauseButton.setBounds(5, ctrlY+5, 100, 40);
		add(resumePauseButton);

		leftButton = new JButton("Å©");
		leftButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				player.setMovingX(-1);
			}

			public void mouseReleased(MouseEvent e) {
				player.setMovingX(0);
			}
		});
		leftButton.setBounds(110, ctrlY+5, 50, 40);
		add(leftButton);

		rightButton = new JButton("Å®");
		rightButton.addMouseListener(new MouseAdapter() {
			public void mousePressed(MouseEvent e) {
				player.setMovingX(1);
			}

			public void mouseReleased(MouseEvent e) {
				player.setMovingX(0);
			}
		});
		rightButton.setBounds(165, ctrlY+5, 50, 40);
		add(rightButton);

		shootButton = new JButton("Å™");
		shootButton.setBounds(220, ctrlY+5, 50, 40);
		add(shootButton);

		statusPanel = new StatusPanel();
		statusPanel.setBounds(275, ctrlY+5, getWidth()-275-5, 40);
		add(statusPanel);

		shooting.addPlayer(player);
		player.addPlayerListener(new PlayerAdapter() {
			public void scoreUpdated() {
				statusPanel.repaint();
			}
			public void damageUpdated() {
				statusPanel.repaint();
			}
		});
		shooting.addPlayer(new AutoPlayer(shooting, shooting.getWidth()/2, 60, 0, 1));
		/*
		shooting.addPlayer(new AutoPlayer(shooting, 30, 90, 0, 1));
		shooting.addPlayer(new AutoPlayer(shooting, shooting.getWidth()/2+40, 120, 0, 1));
		shooting.addPlayer(new AutoPlayer(shooting, shooting.getWidth()-30, 150, 0, 1));
		*/
	}

	class StatusPanel extends JPanel {
		public void paintComponent(Graphics g) {
			super.paintComponent(g);
			g.drawString("" + player.getHitPercent() + "%(" + player.getHitCount() + "/" + player.getHitSum() + ") damage:" + player.getDamage() + " score:" + player.getScore(), 0, 10);
		}
	}
}

