import javax.swing.JFrame;

import javafx.embed.swing.JFXPanel;

public class GameArena {
	private JFrame window;
	private JFXPanel jfxPanel;

	public GameArena(int width, int height) {
		this(width, height, true);
	}

	public GameArena(int width, int height, boolean createWindow) {
		jfxPanel = new JFXPanel();
		jfxPanel.setPreferredSize(new java.awt.Dimension(width, height));

		if (createWindow) {
			window = new JFrame();
			window.setTitle("Let's Play!");
			window.setContentPane(jfxPanel);
			window.setResizable(false);
			window.pack();
			window.setVisible(true);
			window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		}
	}

	public JFXPanel getPanel() {
		return jfxPanel;
	}
}