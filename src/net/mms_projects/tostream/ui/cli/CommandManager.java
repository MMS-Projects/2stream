package net.mms_projects.tostream.ui.cli;

import java.util.ArrayList;
import java.util.List;

import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;


public class CommandManager {

	private List<Command> commands = new ArrayList<Command>();
	
	public CommandManager() {
		// TODO Auto-generated constructor stub
	}
	
	public void executeCommand(String[] args, ResourcePasser resources) {
		for (Command command : commands) {
			if (args[0].equalsIgnoreCase(command.command)) {
				command.run(args, resources);
			}
		}
	}
	
	public void executeCommand(String command, ResourcePasser resources) {
		String[] args = {command};
		executeCommand(args, resources);
	}
	
	public void addCommand(Command command) {
		commands.add(command);
	}

}
