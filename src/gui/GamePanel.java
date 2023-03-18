package gui;

import java.awt.Graphics;
import java.awt.geom.AffineTransform;

import javax.swing.JPanel;

import game.Game;

@SuppressWarnings("serial")
public class GamePanel extends JPanel {

	private Game game;

	GamePanel(Game game) {
		this.game = game;
//		super.setPreferredSize(new Dimension(1000, 1000));
	}

	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		AffineTransform tr = new AffineTransform();

		game.Paint(g, tr);

	}

}
