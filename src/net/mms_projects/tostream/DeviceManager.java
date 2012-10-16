package net.mms_projects.tostream;

import java.util.ArrayList;
import java.util.List;

public class DeviceManager {

	public static String getVideoDevice(Settings settings) {
		if (OSValidator.isUnix()) {
			return settings.get(Settings.VIDEO_DEVICE_LINUX);
		} else if (OSValidator.isWindows()) {
			return settings.get(Settings.VIDEO_DEVICE_WINDOWS);
		}
		return null;
	}
	
	public static String getAudioDevice(Settings settings) {
		if (OSValidator.isUnix()) {
			return null;
		} else if (OSValidator.isWindows()) {
			return settings.get("windowsAudio");
		}
		return null;
	}

	public static int getVideoDeviceIndex(String device) {
		String[] devices = getVideoDevices();
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].equalsIgnoreCase(device)) {
				return i;
			}
		}
		return -1;
	}

	public static int getVideoDeviceIndex(Settings settings) {
		return getVideoDeviceIndex(DeviceManager.getVideoDevice(settings));
	}

	public static String[] getVideoDevices() {
		if (OSValidator.isUnix()) {
			return new String[] { "x11grab", "/dev/video0" };
		} else if (OSValidator.isWindows()) {
			return new String[] { "None" };
		}
		return new String[] {};
	}

	public static void setVideoDevice(String device, Settings settings) {
		String setting = null;
		if (OSValidator.isUnix()) {
			setting = Settings.VIDEO_DEVICE_LINUX;
		} else if (OSValidator.isWindows()) {
			setting = Settings.VIDEO_DEVICE_WINDOWS;
		}
		try {
			settings.set(setting, device);
		} catch (Exception e) {
		}
	}

	public static ArrayList<String> buildDeviceString(String deviceVideo,
			String deviceAudio, Integer[] location) {
		ArrayList<String> command = new ArrayList<String>();
		if (OSValidator.isUnix()) {
			if (deviceVideo.equalsIgnoreCase("x11grab")) {
				command.add("-f");
				command.add("x11grab");

				command.add("-i");
				command.add(":0.0+" + Integer.toString(location[0]) + ","
						+ Integer.toString(location[1]));
			} else {
				command.add("-f");
				command.add("video4linux2");

				command.add("-i");
				command.add(deviceVideo);
			}
		} else if (OSValidator.isWindows()) {
			command.add("-f");
			command.add("dshow");

			command.add("-i");
			command.add("video=" + deviceVideo + ":audio=" + deviceAudio + "");
		}
		return command;
	}

	public static ArrayList<String> buildDeviceString(Settings settings,
			Integer[] location) {
		return buildDeviceString(DeviceManager.getVideoDevice(settings),
				DeviceManager.getAudioDevice(settings), location);
	}

}
