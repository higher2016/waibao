package scene;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.swing.ImageIcon;
import javax.swing.JButton;

import images.AllImages;
import scene.position.Position;

/**
 * 游戏中的所有像素块(bank1,bank2,plank1...)
 */
public class AllUnitInGame {
	private SingleUnit[][] allUnits = new SingleUnit[9][13];

	public SingleUnit winUnit;
	public SingleUnit startUnit;

	public Position winPosition;
	public Position startPosition;

	public List<SingleUnit> getAllSingleUnit() {
		List<SingleUnit> resList = new ArrayList<>();
		for (SingleUnit[] ss : allUnits) {
			resList.addAll(Arrays.asList(ss));
		}
		return resList;
	}

	public SingleUnit getUnitByPosition(Position position) {
		return allUnits[position.y][position.x];
	}

	public static AllUnitInGame parse(String allUnitStr) {
		AllUnitInGame allUnitInGameLevel = new AllUnitInGame();
		String[] singleUnitStrs = allUnitStr.split(System.getProperty("line.separator"));
		initStartAndWinPosition(allUnitInGameLevel, singleUnitStrs);
		initButton(allUnitInGameLevel, singleUnitStrs);
		return allUnitInGameLevel;
	}

	public SingleUnit getSingleUnit(JButton button) {
		for (SingleUnit[] units : allUnits) {
			for (SingleUnit unit : units) {
				if (button == unit.button) {
					return unit;
				}
			}
		}
		return null;
	}

	private static void initButton(AllUnitInGame allUnitInGameLevel, String[] singleUnitStrs) {
		for (int i = 2; i < singleUnitStrs.length; i++) {
			String[] singleUnitStrss = singleUnitStrs[i].split("#");
			for (String sin : singleUnitStrss) {
				String[] sins = sin.split(",");

				Position position = new Position(Integer.valueOf(sins[0]), Integer.valueOf(sins[1]));
				ImageIcon icon = AllImages.getImageIconBuyName(sins[2]);
				JButton button = new JButton();
				button.setIcon(icon);
				button.setBorderPainted(false);
				SingleUnit singleUnit = new SingleUnit(position, button, sins[2]);
				allUnitInGameLevel.allUnits[position.y][position.x] = singleUnit;
			}
		}
	}

	private static void initStartAndWinPosition(AllUnitInGame allUnitInGameLevel, String[] singleUnitStrs) {
		int startPositionX = Integer.valueOf(singleUnitStrs[0].split(",")[0]);
		int startPositionY = Integer.valueOf(singleUnitStrs[0].split(",")[1]);
		allUnitInGameLevel.startPosition = new Position(startPositionX, startPositionY);

		int winPositionX = Integer.valueOf(singleUnitStrs[1].split(",")[0]);
		int winPositionY = Integer.valueOf(singleUnitStrs[1].split(",")[1]);
		allUnitInGameLevel.winPosition = new Position(winPositionX, winPositionY);
	}

	public static final class SingleUnit {
		public final Position position;
		public final JButton button;
		public String imageName;

		public SingleUnit(Position position, JButton button, String imageName) {
			this.position = position;
			this.button = button;
			this.imageName = imageName;
		}

		public void setNewImageIcon(String newIamgeIconName) {
			ImageIcon icon = AllImages.getImageIconBuyName(newIamgeIconName);
			imageName = newIamgeIconName;
			button.setIcon(icon);
		}

		public boolean isSamePosition(Position position) {
			return this.position.isSamePosition(position);
		}

		public boolean isSamePosition(SingleUnit singleUnit) {
			return this.position.isSamePosition(singleUnit.position);
		}

		public String getImageName() {
			return imageName;
		}
	}
}
