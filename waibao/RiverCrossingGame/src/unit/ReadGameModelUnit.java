package unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ReadGameModelUnit {
	private static final String gameModlePath = System.getProperty("user.dir") + File.separator + "game_model"
			+ File.separator;

	public static String readToString(String level) {
		String encoding = "UTF-8";
		String filePath = gameModlePath + level + ".txt";
		return readStringFromFileByPath(encoding, filePath);
	}

	public static String readIntroduceStr() {
		String encoding = "UTF-8";
		String filePath = System.getProperty("user.dir") + File.separator + "introduce" + File.separator
				+ "introduce.html";
		return readStringFromFileByPath(encoding, filePath);
	}

	public static String readScoreStr() {
		String encoding = "UTF-8";
		String filePath = System.getProperty("user.dir") + File.separator + "score_board" + File.separator
				+ "scoreBoard.txt";
		return readStringFromFileByPath(encoding, filePath);
	}

	private static String readStringFromFileByPath(String encoding, String filePath) {
		File file = new File(filePath);
		Long filelength = file.length();
		byte[] filecontent = new byte[filelength.intValue()];
		try {
			FileInputStream in = new FileInputStream(file);
			in.read(filecontent);
			in.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		try {
			return new String(filecontent, encoding);
		} catch (UnsupportedEncodingException e) {
			System.err.println("The OS does not support " + encoding);
			e.printStackTrace();
			return null;
		}
	}
}
