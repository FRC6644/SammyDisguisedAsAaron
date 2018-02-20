package org.usfirst.frc.team6644.robot.subsystems.drive;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Scanner;

public class History {
	/*
	 * Stuff for drive histories
	 * 
	 * Hopefully this is a really quick way of getting some sort of autonomous mode
	 * up if sensors end up taking too long.
	 * 
	 */
	private static ArrayList<double[]> driveHistory;
	private static int historyCount;
	private static boolean use;
	private static History instance;

	/**
	 * Returns an instance of history
	 * 
	 * @return instance of history
	 */
	public static History getInstance() {
		if (instance == null) {
			instance = new History();
		}
		return instance;
	}

	public History() {
		File custom = new File("custom" + File.separator);
		if (!custom.exists()) {
			custom.mkdir();
		}
		disable();
	}

	/**
	 * Clears drive history and starts a new one
	 */
	public void start() {
		driveHistory = new ArrayList<double[]>();
	}

	/**
	 * Records drive history to Hard Drive and increments the history counter
	 * 
	 * @throws IOException
	 */
	public void end() throws IOException {
		save(historyCount);
		historyCount++;
	}

	/**
	 * Removes the current history
	 */
	public void clear() {
		driveHistory = null;
	}

	/**
	 * Append data to current history in RAM. Do not leave running for long periods
	 * of time as the program will take all usable RAM.
	 * 
	 * @param data
	 *            Data to be appended to history.
	 */
	public void append(double data[]) {
		driveHistory.add(data);
	}

	/**
	 * Save current history to the hard disk. If the file already exists, it is
	 * overwritten.
	 * 
	 * @param historyNumber
	 *            Number of history to save
	 * @throws IOException
	 */
	public void save(int historyNumber) throws IOException {
		File historyFile = new File("custom" + File.separator + "driveHistory" + historyNumber + ".txt");
		if (historyFile.exists() && historyFile.isFile()) {
			historyFile.delete();
		}
		historyFile.createNewFile();

		FileWriter writer = new FileWriter(historyFile, true);
		for (int i = 0; i != driveHistory.size(); i++) {
			writer.write(Double.toString(driveHistory.get(i)[0]));
			writer.write(";");
			writer.write(Double.toString(driveHistory.get(i)[1]));
			writer.write(";");
		}

		clear();
		writer.close();
	}

	/**
	 * Load a history from the hard disk
	 * 
	 * @param historyNumber
	 *            Number of history to load from the hard disk
	 * @throws FileNotFoundException
	 */
	public void load(int historyNumber) throws FileNotFoundException {
		File dh = new File("custom" + File.separator + "driveHistory" + historyNumber + ".txt");
		Scanner scan = new Scanner(dh);
		scan.useDelimiter(";");
		while (scan.hasNext()) {
			append(new double[] { Double.parseDouble(scan.next()), Double.parseDouble(scan.next()) });
		}
		scan.close();
	}

	/**
	 * Reset the number of history files to zero and count the number of history
	 * files
	 */
	public void count() {
		File historyFile = new File("custom" + File.separator + "driveHistory0.txt");
		resetCount();
		while (historyFile.exists()) {
			historyCount++;
			historyFile = new File("custom" + File.separator + "driveHistory" + historyCount + ".txt");
		}
	}

	/**
	 * Resets the count of history files to zero. The are not actually deleted.
	 */
	public void resetCount() {
		historyCount = 0;
	}

	/**
	 * Sets the history to be actively in use
	 */
	public void enable() {
		use = true;
	}

	/**
	 * Sets the history to not actively be in use.
	 */
	public void disable() {
		use = false;
	}

	/**
	 * Returns whether the history is actively in use
	 * 
	 * @return the state of the history
	 */
	public boolean inUse() {
		return use;
	}
}
