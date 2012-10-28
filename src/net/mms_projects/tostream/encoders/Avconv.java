package net.mms_projects.tostream.encoders;

import net.mms_projects.tostream.OSValidator;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.managers.EncoderManager;
import net.mms_projects.tostream.managers.VideoDeviceManager;

public class Avconv extends Ffmpeg {

	public Avconv(EncoderManager encoderManager, Settings settings,
			VideoDeviceManager videoManager) {
		super(encoderManager, settings, videoManager);

		if (OSValidator.isUnix()) {
			setExecutable(settings.get(Settings.AVCONV_EXECUTABLE_LINUX));
		} else if (OSValidator.isWindows()) {
			setExecutable(settings.get(Settings.AVCONV_EXECUTABLE_WINDOWS));
		}
	}

}
