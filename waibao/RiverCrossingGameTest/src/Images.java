import java.awt.Image;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;

public class Images {
	private int IMG_WIDTH = 50;
	private int IMG_HEIGHT = 50;
	private ImageIcon icon, icon2, icon3, icon4, icon5, icon6, icon7, icon8, icon9, icon10, icon11, icon12, icon13,
			icon14, icon15, icon16, water1, water2, water3, water4, bank1, bank2, plank1, plank2, plankMan1, plankMan2,
			stump1, stump2, stump3, stumpMan1, stumpMan2, stumpMan3;

	public void Water1() {
		icon = new ImageIcon(
				"RiverCrossingGraphics/water1.jpg");
		Image img = icon.getImage();
		Image newimg = img.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		water1 = new ImageIcon(newimg);
	}

	public static void main(String[] args) {
		Images im = new Images();
		im.Water1();
		JFrame j = new JFrame();
		JButton jb = new JButton();
		jb.setIcon(im.getWater1());
		j.add(jb);
		j.setVisible(true);
		j.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void Water2() {
		icon2 = new ImageIcon(
				"RiverCrossingGraphics/water2.jpg");
		Image img2 = icon2.getImage();
		Image newimg2 = img2.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		water2 = new ImageIcon(newimg2);
	}

	public void Water3() {
		icon3 = new ImageIcon(
				"RiverCrossingGraphics/water3.jpg");
		Image img3 = icon3.getImage();
		Image newimg3 = img3.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		water3 = new ImageIcon(newimg3);
	}

	public void Water4() {
		icon4 = new ImageIcon(
				"RiverCrossingGraphics/water4.jpg");
		Image img4 = icon4.getImage();
		Image newimg4 = img4.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		water4 = new ImageIcon(newimg4);
	}

	public void Bank1() {
		icon5 = new ImageIcon(
				"RiverCrossingGraphics/bank1.jpg");
		Image img5 = icon5.getImage();
		Image newimg5 = img5.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		bank1 = new ImageIcon(newimg5);
	}

	public void Bank2() {
		icon6 = new ImageIcon(
				"RiverCrossingGraphics/bank2.jpg");
		Image img6 = icon6.getImage();
		Image newimg6 = img6.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		bank2 = new ImageIcon(newimg6);
	}

	public void Plank1() {
		icon7 = new ImageIcon(
				"RiverCrossingGraphics/plank1.jpg");
		Image img7 = icon7.getImage();
		Image newimg7 = img7.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		plank1 = new ImageIcon(newimg7);
	}

	public void Plank2() {
		icon8 = new ImageIcon(
				"RiverCrossingGraphics/plank2.jpg");
		Image img8 = icon8.getImage();
		Image newimg8 = img8.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		plank2 = new ImageIcon(newimg8);
	}

	public void PlankMan1() {
		icon9 = new ImageIcon(
				"RiverCrossingGraphics/plank1_man.jpg");
		Image img9 = icon9.getImage();
		Image newimg9 = img9.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		plankMan1 = new ImageIcon(newimg9);
	}

	public void PlankMan2() {
		icon10 = new ImageIcon(
				"RiverCrossingGraphics/plank2_man.jpg");
		Image img10 = icon10.getImage();
		Image newimg10 = img10.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		plankMan2 = new ImageIcon(newimg10);
	}

	public void StumpMan1() {
		icon11 = new ImageIcon(
				"RiverCrossingGraphics/stump1_man.jpg");
		Image img11 = icon11.getImage();
		Image newimg11 = img11.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		stumpMan1 = new ImageIcon(newimg11);
	}

	public void StumpMan2() {
		icon12 = new ImageIcon(
				"RiverCrossingGraphics/stump2_man.jpg");
		Image img12 = icon12.getImage();
		Image newimg12 = img12.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		stumpMan2 = new ImageIcon(newimg12);
	}

	public void StumpMan3() {
		icon13 = new ImageIcon(
				"RiverCrossingGraphics/stump3_man.jpg");
		Image img13 = icon13.getImage();
		Image newimg13 = img13.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		stumpMan3 = new ImageIcon(newimg13);
	}

	public void Stump1() {
		icon14 = new ImageIcon(
				"RiverCrossingGraphics/stump1.jpg");
		Image img14 = icon14.getImage();
		Image newimg14 = img14.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		stump1 = new ImageIcon(newimg14);
	}

	public void Stump2() {
		icon15 = new ImageIcon(
				"RiverCrossingGraphics/stump2.jpg");
		Image img15 = icon15.getImage();
		Image newimg15 = img15.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		stump2 = new ImageIcon(newimg15);
	}

	public void Stump3() {
		icon16 = new ImageIcon(
				"RiverCrossingGraphics/stump3.jpg");
		Image img16 = icon16.getImage();
		Image newimg16 = img16.getScaledInstance(IMG_WIDTH, IMG_HEIGHT, java.awt.Image.SCALE_SMOOTH);
		stump3 = new ImageIcon(newimg16);
	}

	public ImageIcon getWater1() {
		return water1;
	}

	public ImageIcon getWater2() {
		return water2;
	}

	public ImageIcon getWater3() {
		return water3;
	}

	public ImageIcon getWater4() {
		return water4;
	}

	public ImageIcon getPlank1() {
		return plank1;
	}

	public ImageIcon getPlank2() {
		return plank2;
	}

	public ImageIcon getBank1() {
		return bank1;
	}

	public ImageIcon getBank2() {
		return bank2;
	}

	public ImageIcon getPlankMan1() {
		return plankMan1;
	}

	public ImageIcon getPlankMan2() {
		return plankMan2;
	}

	public ImageIcon getStumpMan1() {
		return stumpMan1;
	}

	public ImageIcon getStumpMan2() {
		return stumpMan2;
	}

	public ImageIcon getStumpMan3() {
		return stumpMan3;
	}

	public ImageIcon getStump1() {
		return stump1;
	}

	public ImageIcon getStump2() {
		return stump2;
	}

	public ImageIcon getStump3() {
		return stump3;
	}
}