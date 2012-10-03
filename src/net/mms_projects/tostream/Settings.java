package net.mms_projects.tostream;

import java.awt.Rectangle;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Arrays;
import java.util.Iterator;
import java.util.Properties;

public class Settings extends Properties {

	public String bitrate = "250k";
	public String bufferSize = "1835k";
	public int framerate = 30;
	public Integer[] videoResolution = new Integer[2];

	public static final String BITRATE = "bitrate";
	public static final String BUFFER_SIZE = "bufferSize";
	public static final String FRAME_RATE = "frameRate";
	public static final String RESOLUTION = "resolution";

	public Settings() {
		super();

		defaults = new Properties();
		defaults.setProperty(Settings.BITRATE, "250k");
		defaults.setProperty(Settings.BUFFER_SIZE, "1835k");
		defaults.setProperty(Settings.FRAME_RATE, "30");
		defaults.setProperty(Settings.RESOLUTION, "800,600");
	}

	public String get(String key) {
		return getProperty(key);
	}

	public int getAsInteger(String key) {
		return Integer.parseInt(get(key));
	}

	public int[] getAsIntegerArray(String key) {
		String[] tokens = get(key).split(",");
		int[] array = new int[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			array[i] = Integer.parseInt(tokens[i]);
		}
		return array;
	}

	public void set(String key, String value) {
		setProperty(key, value);
		saveProperties();
	}

	public void set(String key, Integer value) {
		set(key, value.toString());
	}

	public void set(String key, Integer[] array) {
		String value = "";
		Iterator<Integer> itemIterator = Arrays.asList(array).iterator();
		Integer number;

		if (itemIterator.hasNext()) {
			number = itemIterator.next();
			if (number == null) {
				number = 0;
			}
			value += number.toString();
			while (itemIterator.hasNext()) {
				number = itemIterator.next();
				if (number == null) {
					number = 0;
				}
				value += "," + number.toString();
			}
		}
		set(key, value);
	}

	public void loadProperties() {
		BufferedInputStream stream;
		try {
			stream = new BufferedInputStream(new FileInputStream(
					getConfigDirectory() + "options.properties"));
			this.load(stream);
			stream.close();
		} catch (FileNotFoundException e) {
			// having no properties file is OK
		} catch (IOException e) {
			// something went wrong with the stream
			e.printStackTrace();
		}
	}

	public void saveProperties() {
		BufferedOutputStream stream;
		try {
			File file = new File(getConfigDirectory() + "options.properties");
			if (!file.exists()) {
				System.out.println("File not there");
				file.createNewFile();
			}
			stream = new BufferedOutputStream(new FileOutputStream(file));
			// TODO describe properties in comments
			String comments = "";
			store(stream, comments);
		} catch (FileNotFoundException e) {
			// we checked this first so this shouldn't occurs
		} catch (IOException e) {
			// something went wrong with the stream
			e.printStackTrace();
		}
	}

	public String getConfigDirectory() {
		return "." + System.getProperty("file.separator");
	}
}
