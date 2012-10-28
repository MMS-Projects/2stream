package net.mms_projects.tostream;

import java.util.Locale;

import net.mms_projects.tostream.encoders.Avconv;
import net.mms_projects.tostream.encoders.Ffmpeg;
import net.mms_projects.tostream.managers.EncoderManager;
import net.mms_projects.tostream.managers.VideoDeviceManager;
import net.mms_projects.tostream.ui.InterfaceLoader;
import net.mms_projects.tostream.ui.cli.CliInterface;
import net.mms_projects.tostream.ui.swt.SwtInterface;

public class ToStream {

	public static String getApplicationName() {
		return "2STREAM";
	}

	public static InterfaceLoader getInterface(String name,
			EncoderManager encoderManager, Settings settings,
			VideoDeviceManager videoManager) {
		if (name.equalsIgnoreCase("cli")) {
			return new CliInterface(encoderManager, settings, videoManager);
		}
		if (name.equalsIgnoreCase("swt")) {
			return new SwtInterface(encoderManager, settings, videoManager);
		}
		return null;
	}

	public static String getVersion() {
		String version = "";
		if (ToStream.class.getPackage().getSpecificationVersion() != null) {
			version += ToStream.class.getPackage().getSpecificationVersion();
		} else {
			version += "0.0.1";
		}

		if (ToStream.class.getPackage().getImplementationVersion() != null) {
			version += "-"
					+ ToStream.class.getPackage().getImplementationVersion();
		}
		return version;
	}

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Settings settings = new Settings();
		settings.loadProperties();

		if (settings.get(Settings.LANGUAGE) != null) {
			Locale locale = Locale.forLanguageTag(settings
					.get(Settings.LANGUAGE));
			Messages.setLocale(locale);
		} else {
			Messages.setLocale(new Locale("en", "US"));
		}

		VideoDeviceManager videoManager = new VideoDeviceManager(settings);

		try {
			videoManager.setCurrentItem(videoManager.getVideoDevice(settings));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		EncoderManager encoderManager = new EncoderManager(settings);
		encoderManager.addItem(new Ffmpeg(encoderManager, settings,
				videoManager));
		encoderManager.addItem(new Avconv(encoderManager, settings,
				videoManager));

		InterfaceLoader uiLoader;
		if (args.length != 0) {
			uiLoader = getInterface(args[0], encoderManager, settings,
					videoManager);
		} else {
			uiLoader = getInterface(settings.get(Settings.DEFAULT_INTERFACE),
					encoderManager, settings, videoManager);
		}
		if (uiLoader == null) {
			System.out.println("Unknown interface: " + args[0]);
		}
	}

}
