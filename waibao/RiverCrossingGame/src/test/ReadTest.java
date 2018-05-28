package test;

import javax.swing.JFrame;
import javax.swing.JPanel;

import scene.AllUnitInGame;
import scene.AllUnitInGame.SingleUnit;
import unit.ReadGameModelUnit;

public class ReadTest {
	private JFrame gameWindow = new JFrame();
	private JPanel jfxPanel = new JPanel();

	public ReadTest() {
		jfxPanel.setPreferredSize(new java.awt.Dimension(500, 500));
		gameWindow.setContentPane(jfxPanel);
		String allUnitStr = ReadGameModelUnit.readToString("Level1");
		AllUnitInGame allUnitInGame = AllUnitInGame.parse(allUnitStr);
		for (SingleUnit unit : allUnitInGame.getAllSingleUnit()) {
			jfxPanel.add(unit.button);
		}
		gameWindow.pack();
		gameWindow.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		gameWindow.setVisible(true);

	}

	public static void main(String[] args) {
		new ReadTest();
	}
}
