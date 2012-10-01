package net.mms_projects.tostream;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;
import java.util.ArrayList;

public class FfmpegWrapper extends Thread {

	List<EncoderOutputListener> listeners = new ArrayList<EncoderOutputListener>();
	Process process;
	InputStream input;
	BufferedReader reader;

	public void addListener(EncoderOutputListener toAdd) {
		listeners.add(toAdd);
	}

	public void startEncoder()
	{
		ProcessBuilder builder = new ProcessBuilder("avconv", "-y", "-f", "x11grab", "-i", ":0.0", "bla.flv");
		builder.redirectErrorStream(true);
		try {
			process = builder.start();
			input = process.getInputStream();
			reader = new BufferedReader(new InputStreamReader(input));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public void stopEncoder()
	{
		process.destroy();
	}

	@Override
	public void run() {
		try {
			while (true) {
				String line;
				try {
					if (reader != null) {
						while ((line = reader.readLine()) != null) {
							//System.out.println ("Stdout: " + line);
							System.out.println(parseOutput(line));
							for (EncoderOutputListener hl : listeners) {
								hl.onOutput(line + "\n");
							}
						}
					}
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//System.out.println("Child Thread: " + 1000);
				// Let the thread sleep for a while.
				
				Thread.sleep(500);
			}
		} catch (InterruptedException e) {
			System.out.println("Child interrupted.");
		}
		System.out.println("Exiting child thread.");
	}
	
	public String parseOutput(String output) {
		for (String piece : output.split(" ")) {
			
		}
		return "";
	}

}
