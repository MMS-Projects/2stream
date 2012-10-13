package net.mms_projects.tostream.ui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Display;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.ToStream;

public class CliInterface extends net.mms_projects.tostream.ui.InterfaceLoader {

	public CliInterface(FfmpegWrapper ffmpegWrapper, Settings settings) {
		super(ffmpegWrapper, settings);
		
		ffmpegWrapper.addListener(new EncoderOutputListener() {
			public void onStatusUpdate(final int frame, final int framerate) {
				System.out.println("FPS: " + framerate + " - Frame: "
						+ frame);
			}
		});

		System.out.println("Running " + ToStream.getApplicationName() + " version " + ToStream.getVersion());
		
		try {
			while (true) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				String str;
				System.out.print("> ");
				str = br.readLine().trim();
				String[] tokens = str.split(" ");
				System.out.println("input: '" + tokens[0] + "'");
				if (tokens[0].contains("start")) {
					System.out.println("Starting FFmpeg...");
					try {
						ffmpegWrapper.startEncoder();
					} catch (Exception e) {
						System.out.println("Error trying to run FFmpeg: " + e.getMessage());
					}
				} else if (tokens[0].contains("stop")) {
					System.out.println("Stopping FFmpeg...");
					try {
						ffmpegWrapper.stopEncoder();
					} catch (Exception e) {
						System.out.println("Error trying to stop FFmpeg: " + e.getMessage());
					}
				} else if (tokens[0].contains("set")) {
					try {
						settings.set(tokens[1], tokens[2]);
						System.out.println("Set " + tokens[1] + " to: " + tokens[2]);
					} catch (Exception e) {
						System.out.println("An error occurred: " + e.getMessage());
					}
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
