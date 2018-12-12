package util;

import java.io.File;

public class FileChecker {
	private FileChecker() {
	}

	public static boolean exists(String filename) {
		return new File(filename).exists();
	}

	public static boolean existsFolder(String filename) {
		return new File(filename).isDirectory();
	}
}
