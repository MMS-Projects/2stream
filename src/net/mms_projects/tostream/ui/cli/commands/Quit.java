package net.mms_projects.tostream.ui.cli.commands;

import net.mms_projects.tostream.ui.cli.Command;
import net.mms_projects.tostream.ui.cli.ResourcePasser;

public class Quit extends Command {

	public Quit() {
		command = "quit";
	}
	
	@Override
	public boolean run(String[] args, ResourcePasser resources) {
		if (resources.ffmpegWrapper.running) {
			resources.commandManager.executeCommand("stop", resources);
		}
		System.out.println("Have a nice day!");
		resources.loopRunning = false;
		return true;
	}

}
