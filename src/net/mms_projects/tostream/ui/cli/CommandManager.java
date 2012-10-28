package net.mms_projects.tostream.ui.cli;

import net.mms_projects.tostream.Manager;

public class CommandManager extends Manager<Command> {

	public void addItem(Command item) {
		super.addItem(item.command, item);
	}

	public void executeCommand(String command, ResourcePasser resources) {
		String[] args = { command };
		executeCommand(args, resources);
	}

	public void executeCommand(String[] args, ResourcePasser resources) {
		for (String commandKey : items.keySet()) {
			Command command = items.get(commandKey);
			if (args[0].equalsIgnoreCase(command.command)) {
				command.run(args, resources);
			}
		}
	}

}
