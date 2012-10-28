package net.mms_projects.tostream.ui.cli;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.ToStream;
import net.mms_projects.tostream.managers.EncoderManager;
import net.mms_projects.tostream.managers.VideoDeviceManager;
import net.mms_projects.tostream.ui.cli.commands.Quit;
import net.mms_projects.tostream.ui.cli.commands.Set;
import net.mms_projects.tostream.ui.cli.commands.Setup;
import net.mms_projects.tostream.ui.cli.commands.Start;
import net.mms_projects.tostream.ui.cli.commands.Stop;

public class CliInterface extends net.mms_projects.tostream.ui.InterfaceLoader {

	public CliInterface(EncoderManager encoderManager, Settings settings, VideoDeviceManager videoManager) {
		super(encoderManager, settings);

		ResourcePasser resources = new ResourcePasser();
		resources.encoderManager = encoderManager;
		resources.settings = settings;

		CommandManager commandManager = new CommandManager();
		commandManager.addItem(new Start());
		commandManager.addItem(new Stop());
		commandManager.addItem(new Set());
		commandManager.addItem(new Quit());
		commandManager.addItem(new Setup());

		resources.commandManager = commandManager;

		encoderManager.addListener(new EncoderOutputListener() {
			public void onStatusUpdate(final int frame, final int framerate) {
				System.out.println("FPS: " + framerate + " - Frame: " + frame);
			}
		});

		System.out.println("Running " + ToStream.getApplicationName()
				+ " version " + ToStream.getVersion());

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
