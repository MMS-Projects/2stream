package net.mms_projects.tostream;

import java.awt.Dimension;
import java.awt.GraphicsDevice;
import java.awt.GraphicsEnvironment;
import java.awt.Toolkit;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FfmpegWrapper extends Thread {

    private static final Pattern FRAME_PATTERN = Pattern.compile(
        "frame(\\s*)=(\\s+)(\\d+)", Pattern.CASE_INSENSITIVE
    );
    private static final Pattern FRAME_RATE_PATTERN = Pattern.compile(
        "fps(\\s*)=(\\s+)(\\d+)", Pattern.CASE_INSENSITIVE
    );

	List<EncoderOutputListener> listeners = new ArrayList<EncoderOutputListener>();
	Process process;
	InputStream input;
	BufferedReader reader;
	Settings settings;

	public FfmpegWrapper(Settings settings) {
		super();
		
		this.settings = settings;
	}
	
	public void addListener(EncoderOutputListener toAdd) {
		listeners.add(toAdd);
	}

	public void startEncoder() throws IOException
	{
		ProcessBuilder builder = new ProcessBuilder(compileSettings());
		builder.redirectErrorStream(true);
		try {
			process = builder.start();
			input = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException e) {
			throw e;
		}
	}

	public void stopEncoder()
	{
		input  = null;
		reader = null;
		process.destroy();
	}

	@Override
	public void run() {
		try {
			while (true) {
				String line;
				if (reader != null) {
					try {
						while ((line = reader.readLine()) != null) {
							Matcher matcher;
							
							int framerate = -1;
							int frame     = 0;
							matcher = FRAME_RATE_PATTERN.matcher(line);
							if (matcher.find()) {
								framerate = Integer.parseInt(matcher.group(3));
							}
							matcher = FRAME_PATTERN.matcher(line);
							if (matcher.find()) {
								frame = Integer.parseInt(matcher.group(3));
							}
							
							for (EncoderOutputListener listener : listeners) {
								if (framerate != -1) {
									listener.onStatusUpdate(frame, framerate);
								}
								listener.onOutput(line + "\n");
							}
						}
					} catch (Exception e) {
						System.out.println("Error: '" + e.getClass() + "' thrown in the FfmpegWrapper.");
					}
				}
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			System.out.println("Child interrupted.");
		}
		System.out.println("Exiting child thread.");
	}
	
	public List<String> compileSettings()
	{
		List<String> command = new ArrayList<String>();
		command.add("ffmpeg");
		command.add("-y");
		command.add("-f");
		command.add("x11grab");
		
		if (settings.getAsInteger(Settings.FRAME_RATE) > 5) {
			command.add("-r");
			command.add(settings.get(Settings.FRAME_RATE));
		}
		
		Integer[] resolution = settings.getAsIntegerArray(Settings.RESOLUTION);
		Integer[] location = settings.getAsIntegerArray(Settings.LOCATION);
		
		command.add("-s");
		command.add(resolution[0] + "x" + resolution[1]);
		
		command.add("-i");
		command.add(":0.0+" + Integer.toString(location[0]) + "," + Integer.toString(location[1]));
		if (!settings.get(Settings.BITRATE).isEmpty()) {
			command.add("-b");
			command.add(settings.get(Settings.BITRATE));
			command.add("-minrate");
			command.add(settings.get(Settings.BITRATE));
			command.add("-maxrate");
			command.add(settings.get(Settings.BITRATE));
			command.add("-bufsize");
			command.add(settings.get(Settings.BUFFER_SIZE));
		}
		
		command.add("-vcodec");
		command.add("libx264");
		
		
		
		command.add("-s");
		command.add(resolution[0] + "x" + resolution[1]);
		
		command.add("-f");
		command.add("flv");
		
		command.add(settings.get(Settings.STREAM_URL));
		
		String cmd = "";
		for (String piece : command) {
			cmd += piece;
			cmd += " ";
		}
		System.out.println(cmd);
		
		return command;
	}

}
