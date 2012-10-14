package net.mms_projects.tostream.ui.cli.commands;

import net.mms_projects.tostream.ui.cli.Command;
import net.mms_projects.tostream.ui.cli.ResourcePasser;

public class Start extends Command {

	public Start() {
		command = "start";
	}
	
	@Override
	public boolean run(String[] args, ResourcePasser resources) {
		System.out.println("Starting FFmpeg...");
		try {
			resources.ffmpegWrapper.startEncoder();
		} catch (Exception e) {
			System.out.println("Error trying to run FFmpeg: " + e.getMessage());
			return false;
		}
		return true;
	}

}
