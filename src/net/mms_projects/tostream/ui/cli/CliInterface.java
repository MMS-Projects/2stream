package net.mms_projects.tostream.ui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import org.eclipse.swt.widgets.Display;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.ToStream;
import net.mms_projects.tostream.ui.cli.commands.*;

public class CliInterface extends net.mms_projects.tostream.ui.InterfaceLoader {

	public CliInterface(FfmpegWrapper ffmpegWrapper, Settings settings) {
		super(ffmpegWrapper, settings);
		
		ResourcePasser resources = new ResourcePasser();
		resources.ffmpegWrapper = ffmpegWrapper;
		resources.settings = settings;
		
		CommandManager commandManager = new CommandManager();
		commandManager.addCommand(new Start());
		commandManager.addCommand(new Stop());
		commandManager.addCommand(new Set());
		commandManager.addCommand(new Quit());
		
		
		ffmpegWrapper.addListener(new EncoderOutputListener() {
			public void onStatusUpdate(final int frame, final int framerate) {
				System.out.println("FPS: " + framerate + " - Frame: "
						+ frame);
			}
		});

		System.out.println("Running " + ToStream.getApplicationName() + " version " + ToStream.getVersion());
		
		try {
			resources.loopRunning = true;
			while (resources.loopRunning) {
				BufferedReader br = new BufferedReader(new InputStreamReader(
						System.in));
				String str;
				System.out.print("> ");
				str = br.readLine().trim();
				String[] tokens = str.split(" ");
				System.out.println("input: '" + tokens[0] + "'");
				
				commandManager.executeCommand(tokens, resources);
			}
		} catch (IOException e) {
		}
	}

}
