package net.mms_projects.tostream.ui.cli.commands;

import net.mms_projects.tostream.ui.cli.Command;
import net.mms_projects.tostream.ui.cli.ResourcePasser;

public class Stop extends Command {

	public Stop() {
		command = "stop";
	}
	
	@Override
	public boolean run(String[] args, ResourcePasser resources) {
		System.out.println("Stopping FFmpeg...");
		try {
			resources.encoderManager.getCurrentEncoder().stopEncoder();
		} catch (Exception e) {
			System.out.println("Error trying to stop FFmpeg: " + e.getMessage());
			return false;
		}
		return true;
	}

}
