package net.mms_projects.tostream.encoders;

import net.mms_projects.tostream.OSValidator;
import net.mms_projects.tostream.Settings;

public class Avconv extends Ffmpeg {

	public Avconv(Settings settings) {
		super(settings);
		
		if (OSValidator.isUnix()) {
			setExecutable(settings.get(Settings.AVCONV_EXECUTABLE_LINUX));
		} else if (OSValidator.isWindows()) {
			setExecutable(settings.get(Settings.AVCONV_EXECUTABLE_WINDOWS));
		}
	}

}