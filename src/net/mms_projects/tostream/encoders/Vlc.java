package net.mms_projects.tostream.encoders;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mms_projects.tostream.Encoder;
import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.Messages;
import net.mms_projects.tostream.OSValidator;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.managers.DeviceManager;
import net.mms_projects.tostream.managers.EncoderManager;

public class Vlc extends Encoder {

	private static final Pattern FRAME_PATTERN = Pattern.compile(
			"frame(\\s*)=(\\s+)(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern FRAME_RATE_PATTERN = Pattern.compile(
			"fps(\\s*)=(\\s+)(\\d+)", Pattern.CASE_INSENSITIVE);
	private static final Pattern BITRATE_PATTERN = Pattern.compile(
			"bitrate(\\s*)=(\\s+)(.*)kbits\\/s", Pattern.CASE_INSENSITIVE);
	protected String executableName;

	Process process;
	InputStream input;
	BufferedReader reader;

	public Vlc(EncoderManager manager, Settings settings) {
		super(manager, settings);

		if (OSValidator.isUnix()) {
			setExecutable("vlc");
		} else if (OSValidator.isWindows()) {
			setExecutable("vlc.exe");
		}
	}

	public List<String> compileSettings() {
		List<String> command = new ArrayList<String>();
		command.add(executableName);
		command.add("-vvv");
		
		command.add("screen://");
		
		Integer[] resolution = settings.getAsIntegerArray(Settings.RESOLUTION);
		Integer[] location = settings.getAsIntegerArray(Settings.LOCATION);
		
		if (resolution[0] % 2 == 1) {
			resolution[0] -= 1;
		}
		if (resolution[1] % 2 == 1) {
			resolution[1] -= 1;
		}
		
		command.add(":screen-fps=" + settings.get(Settings.FRAME_RATE));
		command.add("--screen-width=" + resolution[0]);
		command.add("--screen-height=" + resolution[1]);
		command.add("--screen-top=" + location[1]);
		command.add("--screen-left=" + location[0]);

		command.add("'#transcode{vcodec=FLV1,vb=" + settings.get(Settings.BITRATE) + ",acodec=mpga,ab=128k,samplerate=22050}:standard{access=file,dst=" + settings.get(Settings.STREAM_URL) + "}'");


		//command.add(settings.get(Settings.STREAM_URL));

		String cmd = "";
		for (String piece : command) {
			cmd += piece;
			cmd += " ";
		}
		System.out.println(cmd);

		return command;
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
							int frame = 0;
							double bitrate = 0;
							matcher = FRAME_RATE_PATTERN.matcher(line);
							if (matcher.find()) {
								framerate = Integer.parseInt(matcher.group(3));
							}
							matcher = FRAME_PATTERN.matcher(line);
							if (matcher.find()) {
								frame = Integer.parseInt(matcher.group(3));
							}
							matcher = BITRATE_PATTERN.matcher(line);
							if (matcher.find()) {
								bitrate = Double.parseDouble(matcher.group(3));
							}

							System.out.println(bitrate);

							for (EncoderOutputListener listener : getManager()
									.getListeners()) {
								if (framerate != -1) {
									listener.onStatusUpdate(frame, framerate,
											bitrate);
								}
								listener.onOutput(line + "\n");
							}
						}
					} catch (Exception e) {
						System.out
								.println("Error: '" + e.getClass() + "' thrown in the FfmpegWrapper."); //$NON-NLS-2$
					}
				}
				Thread.sleep(50);
			}
		} catch (InterruptedException e) {
			System.out.println("Child interrupted.");
		}
		System.out.println("Exiting child thread.");
	}

	public void setExecutable(String executable) {
		executableName = executable;
	}

	@Override
	public void startEncoder() throws IOException, Exception {
		if (isRunning()) {
			throw new Exception(Messages.getString("Ffmpeg.is-already-running"));
		}
		if (executableName.isEmpty()) {
			throw new Exception("The executable could not be found");
		}
		ProcessBuilder builder = new ProcessBuilder(compileSettings());
		builder.redirectErrorStream(true);
		try {
			process = builder.start();
			input = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));

			for (EncoderOutputListener listener : getManager().getListeners()) {
				listener.onStart();
			}
			super.startEncoder();
		} catch (IOException e) {
			throw e;
		}
	}

	@Override
	public void stopEncoder() throws Exception {
		if (!isRunning()) {
			throw new Exception(Messages.getString("Ffmpeg.is-not-running"));
		}
		input = null;
		reader = null;
		process.destroy();

		for (EncoderOutputListener listener : getManager().getListeners()) {
			listener.onStop();
		}
		super.stopEncoder();
	}

}
