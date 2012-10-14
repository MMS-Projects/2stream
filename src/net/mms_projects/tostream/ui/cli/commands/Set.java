package net.mms_projects.tostream.ui.cli.commands;

import net.mms_projects.tostream.ui.cli.Command;
import net.mms_projects.tostream.ui.cli.ResourcePasser;

public class Set extends Command {

	public Set() {
		command = "set";
	}
	
	@Override
	public boolean run(String[] args, ResourcePasser resources) {
		try {
			resources.settings.set(args[1], args[2]);
			System.out.println("Set " + args[1] + " to: " + args[2]);
		} catch (Exception e) {
			System.out.println("An error occurred: " + e.getMessage());
			return false;
		}
		return true;
	}

}
