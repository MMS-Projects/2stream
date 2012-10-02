package net.mms_projects.tostream.ui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Display;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.FfmpegWrapper;

public class InterfaceLoader {

	public InterfaceLoader(FfmpegWrapper ffmpegWrapper) {
		ffmpegWrapper.addListener(new EncoderOutputListener() {
			public void onStatusUpdate(final int frame, final int framerate) {
				System.out.println("FPS: " + framerate + " - Frame: "
						+ frame);
			}
		});

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
					ffmpegWrapper.startEncoder();
				} else if (tokens[0].contains("stop")) {
					System.out.println("Stopping FFmpeg...");
					ffmpegWrapper.stopEncoder();
				}
			}
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
