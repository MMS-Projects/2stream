package net.mms_projects.tostream;

import net.mms_projects.tostream.ui.swt.InterfaceLoader;

public class ToStream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = new Settings();
		
		FfmpegWrapper wrapperThread = new FfmpegWrapper(settings);
		wrapperThread.setDaemon(true);
		wrapperThread.start();
		
		InterfaceLoader uiLoader = new InterfaceLoader(wrapperThread, settings);
	}

}
