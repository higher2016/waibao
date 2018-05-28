package unit;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.UnsupportedEncodingException;

public class ReadGameModelUnit {
	private static final String gameModlePath = System.getProperty("user.dir") + File.separator + "game_model" + File.separator;
	private static final String scoreListPath = System.getProperty("user.dir") + File.separator + "score_board" + File.separator + "scoreBoard.txt";

	public static void appendInfoToFile(String info) {
		File file = new File(scoreListPath);
		clearInfoForFile(scoreListPath);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file, true);
			fileWriter.write(info);
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void clearInfoForFile(String fileName) {
		File file = new File(fileName);
		try {
			if (!file.exists()) {
				file.createNewFile();
			}
			FileWriter fileWriter = new FileWriter(file);
			fileWriter.write("");
			fileWriter.flush();
			fileWriter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static String readToString(String level) {
		String encoding = "UTF-8";
		String filePath = gameModlePath + level + ".txt";
		return readStringFromFileByPath(encoding, filePath);
	}

	public static String readIntroduceStr() {
		String encoding = "UTF-8";
		String filePath = System.getProperty("user.dir") + File.separator + "introduce" + File.separator + "introduce.html";
		return readStringFromFileByPath(encoding, filePath);
	}

	public static String readScoreStr() {
		String encoding = "UTF-8";
		String filePath = System.getProperty("user.dir") + File.separator + "score_board" + File.separator + "scoreBoard.txt";
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
