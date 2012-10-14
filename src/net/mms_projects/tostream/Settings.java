package net.mms_projects.tostream;

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

	public Integer[] videoResolution = new Integer[2];
	
	public Properties defaults = new Properties();

	public static final String BITRATE = "bitrate";
	public static final String BUFFER_SIZE = "bufferSize";
	public static final String FRAME_RATE = "frameRate";
	public static final String RESOLUTION = "resolution";
	public static final String LOCATION = "location";
	public static final String STREAM_URL = "streamUrl";

	public Settings() {
		super();

		defaults.setProperty(Settings.BITRATE, "250k");
		defaults.setProperty(Settings.BUFFER_SIZE, "1835k");
		defaults.setProperty(Settings.FRAME_RATE, "30");
		defaults.setProperty(Settings.RESOLUTION, "800,600");
		defaults.setProperty(Settings.LOCATION, "10,10");
		defaults.setProperty(Settings.STREAM_URL, "");
	}

	public String get(String key) {
		return getProperty(key);
	}

	public int getAsInteger(String key) {
		return Integer.parseInt(get(key));
	}

	public Integer[] getAsIntegerArray(String key) {
		String[] tokens = get(key).split(",");
		Integer[] array = new Integer[tokens.length];
		for (int i = 0; i < tokens.length; i++) {
			array[i] = Integer.parseInt(tokens[i]);
		}
		return array;
	}

	public void set(String key, String value) throws Exception {
		if (!defaults.containsKey(key)) {
			throw new Exception("Tried to set unknown setting");
		}
		setProperty(key, value);
		saveProperties();
	}

	public void set(String key, Integer value) throws Exception {
		set(key, value.toString());
	}

	public void set(String key, Integer[] array) throws Exception {
		String value = "";
		Iterator<Integer> itemIterator = Arrays.asList(array).iterator();
		
        for (int i = 0; i < array.length; i++) {
            value += array[i] + ",";
        }
        value = value.substring(0, s.length() - 1);
        
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
