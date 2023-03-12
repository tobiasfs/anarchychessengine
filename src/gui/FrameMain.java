package gui;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.InputEvent;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;

import javax.swing.JEditorPane;
import javax.swing.JFileChooser;
import javax.swing.JFormattedTextField;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JSplitPane;
import javax.swing.KeyStroke;
import javax.swing.ScrollPaneConstants;
import javax.swing.text.StyledEditorKit;

import game.Game;
import nlp.Language;
import nlp.SuperTolerantLexer;
import java.awt.Toolkit;

public class FrameMain {

	public JFrame frame;
	private JEditorPane editor_Description;
	Game game;

	public FrameMain() {
		game = new Game();
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setIconImage(Toolkit.getDefaultToolkit().getImage(FrameMain.class.getResource("/gui/icon.png")));
		frame.setBounds(100, 100, 803, 603);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));

		JMenuBar menuBar = new JMenuBar();
		frame.getContentPane().add(menuBar, BorderLayout.NORTH);

		JMenu mnFile = new JMenu("Game");
		menuBar.add(mnFile);

		JMenuItem mntmOpen = new JMenuItem("Open game description");
		mntmOpen.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_O, InputEvent.CTRL_DOWN_MASK));
		mntmOpen.addActionListener(new MntmOpenActionListener());
		mnFile.add(mntmOpen);

		JMenuItem mntmSave = new JMenuItem("Save game description");
		mntmSave.addActionListener(new MntmSaveActionListener());
		mntmSave.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_S, InputEvent.CTRL_DOWN_MASK));
		mnFile.add(mntmSave);

		JSeparator separator = new JSeparator();
		mnFile.add(separator);

		JMenuItem mntmQuit = new JMenuItem("Quit");
		mntmQuit.setAccelerator(KeyStroke.getKeyStroke(KeyEvent.VK_Q, InputEvent.CTRL_DOWN_MASK));
		mntmQuit.addActionListener(new MntmQuitActionListener());
		mnFile.add(mntmQuit);

		JSplitPane splitPane_GameText = new JSplitPane();
		frame.getContentPane().add(splitPane_GameText, BorderLayout.CENTER);
		splitPane_GameText.setResizeWeight(0.8);

		JPanel panel_Game = new JPanel();
		splitPane_GameText.setLeftComponent(panel_Game);
		panel_Game.setLayout(new BorderLayout(0, 0));

		JPanel panel_Own = new JPanel();
		panel_Own.setAlignmentY(Component.TOP_ALIGNMENT);
		panel_Game.add(panel_Own, BorderLayout.WEST);

		JScrollPane scrollPane_Game = new JScrollPane();
		panel_Game.add(scrollPane_Game, BorderLayout.CENTER);

		JPanel panel_Opponent = new JPanel();
		panel_Game.add(panel_Opponent, BorderLayout.EAST);

		JPanel panel_Description = new JPanel();
		splitPane_GameText.setRightComponent(panel_Description);
		panel_Description.setLayout(new BorderLayout(0, 0));

		JSplitPane splitPane_DescriptionDebug = new JSplitPane();
		splitPane_DescriptionDebug.setResizeWeight(0.8);
		splitPane_DescriptionDebug.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_Description.add(splitPane_DescriptionDebug);

		JScrollPane scrollPane_Text = new JScrollPane();
		scrollPane_Text.setHorizontalScrollBarPolicy(ScrollPaneConstants.HORIZONTAL_SCROLLBAR_NEVER);
		splitPane_DescriptionDebug.setLeftComponent(scrollPane_Text);

		editor_Description = new JEditorPane();
		editor_Description.addKeyListener(new Editor_DescriptionKeyListener());
		scrollPane_Text.setViewportView(editor_Description);
//		editor_Description.setContentType("text/plain");
		editor_Description.setEditorKit(new StyledEditorKit());

		JFormattedTextField formattedTextField_Debug = new JFormattedTextField();
		splitPane_DescriptionDebug.setRightComponent(formattedTextField_Debug);
	}

	private class MntmOpenActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(".", "games"));
			int returnVal = fc.showOpenDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					String contents = new String(Files.readAllBytes(file.toPath()), StandardCharsets.UTF_8);
					editor_Description.setText(contents);
					editor_Description.setCaretPosition(0);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}
			try {
				Language lang = new Language();
				lang.readDictionary(Paths.get(".", "language", "dictionary_en.txt").toFile());
				SuperTolerantLexer lex = new SuperTolerantLexer(lang);

				String contents = editor_Description.getText();
				lex.initLexing(contents);
				nlp.Token token = lex.nextToken();
				while (token != null) {
					System.out.println(token);
					token = lex.nextToken();
				}

			} catch (IOException e1) {
				e1.printStackTrace();
			}
		}
	}

	private class MntmSaveActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			String contents = editor_Description.getText();
			if (contents.isEmpty())
				return;
			final JFileChooser fc = new JFileChooser();
			fc.setCurrentDirectory(new File(".", "games"));
			int returnVal = fc.showSaveDialog(frame);
			if (returnVal == JFileChooser.APPROVE_OPTION) {
				File file = fc.getSelectedFile();
				try {
					Files.write(file.toPath(), contents.getBytes(StandardCharsets.UTF_8));
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			}

		}
	}

	private class MntmQuitActionListener implements ActionListener {
		public void actionPerformed(ActionEvent e) {
			frame.dispose();
		}
	}

	private class Editor_DescriptionKeyListener extends KeyAdapter {
		@Override
		public void keyTyped(KeyEvent e) {

		}
	}

}
