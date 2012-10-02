package net.mms_projects.tostream;

import net.mms_projects.tostream.ui.cli.InterfaceLoader;

public class ToStream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		FfmpegWrapper wrapperThread = new FfmpegWrapper();
		wrapperThread.setDaemon(true);
		wrapperThread.start();
		
		InterfaceLoader uiLoader = new InterfaceLoader(wrapperThread);
	}

}
