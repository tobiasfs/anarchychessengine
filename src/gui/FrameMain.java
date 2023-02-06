package gui;

import javax.swing.JFrame;
import javax.swing.JPanel;
import java.awt.BorderLayout;
import javax.swing.JSplitPane;
import javax.swing.JLabel;
import javax.swing.JScrollPane;
import javax.swing.JEditorPane;
import javax.swing.JFormattedTextField;

public class FrameMain {

	public JFrame frame;

	public FrameMain() {
		initialize();
	}

	private void initialize() {
		frame = new JFrame();
		frame.setBounds(100, 100, 450, 300);
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		frame.getContentPane().setLayout(new BorderLayout(0, 0));
		
		JLabel lblNewLabel = new JLabel("<game>");
		frame.getContentPane().add(lblNewLabel, BorderLayout.NORTH);
		
		JSplitPane splitPane_2 = new JSplitPane();
		frame.getContentPane().add(splitPane_2, BorderLayout.CENTER);
		splitPane_2.setResizeWeight(0.8);
		
		JPanel panel_2 = new JPanel();
		splitPane_2.setLeftComponent(panel_2);
		panel_2.setLayout(new BorderLayout(0, 0));
		
		JPanel panel_3 = new JPanel();
		panel_2.add(panel_3, BorderLayout.NORTH);
		
		JScrollPane scrollPane_1 = new JScrollPane();
		panel_2.add(scrollPane_1, BorderLayout.CENTER);
		
		JPanel panel_5 = new JPanel();
		panel_2.add(panel_5, BorderLayout.WEST);
		
		JPanel panel_1 = new JPanel();
		splitPane_2.setRightComponent(panel_1);
		panel_1.setLayout(new BorderLayout(0, 0));
		
		JSplitPane splitPane = new JSplitPane();
		splitPane.setResizeWeight(0.8);
		splitPane.setOrientation(JSplitPane.VERTICAL_SPLIT);
		panel_1.add(splitPane);
		
		JScrollPane scrollPane = new JScrollPane();
		splitPane.setLeftComponent(scrollPane);
		
		JEditorPane dtrpnHelloWorld = new JEditorPane();
		scrollPane.setViewportView(dtrpnHelloWorld);
		dtrpnHelloWorld.setText("Hello World");
		
		JFormattedTextField formattedTextField = new JFormattedTextField();
		splitPane.setRightComponent(formattedTextField);
	}

}
