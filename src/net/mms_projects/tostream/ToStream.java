package net.mms_projects.tostream;

import net.mms_projects.tostream.InterfaceLoader;

public class ToStream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.loadProperties();

		FfmpegWrapper wrapperThread = new FfmpegWrapper(settings);
		wrapperThread.setDaemon(true);
		wrapperThread.start();

		InterfaceLoader uiLoader;
		if (args.length != 0) {
			switch (args[0]) {
			case "cli":
				uiLoader = new net.mms_projects.tostream.ui.swt.InterfaceLoader(
						wrapperThread, settings);
				break;
			case "swt":
			default:
				uiLoader = new net.mms_projects.tostream.ui.swt.InterfaceLoader(
						wrapperThread, settings);
			}
		} else {
			uiLoader = new net.mms_projects.tostream.ui.swt.InterfaceLoader(
					wrapperThread, settings);
		}
	}

}
