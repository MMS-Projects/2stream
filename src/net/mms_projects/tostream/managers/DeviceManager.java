package net.mms_projects.tostream.managers;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import net.mms_projects.tostream.Messages;
import net.mms_projects.tostream.OSValidator;
import net.mms_projects.tostream.Settings;

public class DeviceManager {

	private static String[] _windowsVideo = null;
	private static String[] _windowsAudio = null;
	private static String[] _linuxVideo = null;
	private static String[] _linuxAudio = null;

	private static String[] _getLinuxAudioDevices() {
		ArrayList<String> audioDevices = new ArrayList<String>();
		if (_linuxAudio == null) {
			try {
				Pattern deviceInfo = Pattern.compile("Name\\:",
						Pattern.CASE_INSENSITIVE);
				Pattern removeNaming = Pattern.compile("(.*)Name\\:\\s(.*)",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher;
				ProcessBuilder builder = new ProcessBuilder(new String[] {
						"pactl", "list", "sources" });
				builder.redirectErrorStream(true);
				Process process = builder.start();
				InputStream input = process.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
				String line;
				int type = -1;
				while ((line = reader.readLine()) != null) {
					matcher = deviceInfo.matcher(line);
					if (matcher.find()) {
						matcher = removeNaming.matcher(line);
						if (matcher.find()) {
							audioDevices.add(matcher.group(2));
						}
					}
				}
				audioDevices.add(0, Messages.getString("list.none"));
				_linuxAudio = new String[audioDevices.size()];
				for (int i = 0; i < audioDevices.size(); i++) {
					_linuxAudio[i] = audioDevices.get(i);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _linuxAudio;
	}

	private static String[] _getLinuxVideoDevices() {
		ArrayList<String> videoDevices = new ArrayList<String>();
		if (_linuxVideo == null) {
			try {
				Pattern videoDevice = Pattern.compile("video",
						Pattern.CASE_INSENSITIVE);
				Pattern removeNaming = Pattern.compile("(.*)Name\\:\\s(.*)",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher;
				ProcessBuilder builder = new ProcessBuilder(new String[] {
						"ls", "/dev/" });
				builder.redirectErrorStream(true);
				Process process = builder.start();
				InputStream input = process.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
				String line;
				int type = -1;
				while ((line = reader.readLine()) != null) {
					matcher = videoDevice.matcher(line);
					if (matcher.find()) {
						videoDevices.add("/dev/" + line);
					}
				}
				_linuxVideo = new String[videoDevices.size()];
				for (int i = 0; i < videoDevices.size(); i++) {
					_linuxVideo[i] = videoDevices.get(i);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return _linuxVideo;
	}

	private static String[][] _getWindowsDevices() {
		ArrayList<String> videoDevices = new ArrayList<String>();
		ArrayList<String> audioDevices = new ArrayList<String>();
		if ((_windowsVideo == null) || (_windowsAudio == null)) {
			try {
				Pattern dshowInfo = Pattern.compile(
						"dshow\\s\\@\\s(.*)\\]\\s(.*)",
						Pattern.CASE_INSENSITIVE);
				Pattern removeQuotes = Pattern.compile("\\\"(.*)\\\"",
						Pattern.CASE_INSENSITIVE);
				Matcher matcher;
				ProcessBuilder builder = new ProcessBuilder(new String[] {
						"ffmpeg.exe", "-f", "dshow", "-list_devices", "true",
						"-i", "dummy" });
				builder.redirectErrorStream(true);
				Process process = builder.start();
				InputStream input = process.getInputStream();
				BufferedReader reader = new BufferedReader(
						new InputStreamReader(input));
				String line;
				int type = -1;
				while ((line = reader.readLine()) != null) {
					matcher = dshowInfo.matcher(line);
					if (matcher.find()) {
						line = matcher.group(2).trim();
						matcher = removeQuotes.matcher(line);
						if (matcher.find()) {
							if (type == 1) {
								videoDevices.add(matcher.group(1));
							} else if (type == 2) {
								audioDevices.add(matcher.group(1));
							}
						} else {
							if (line.contains("video")) {
								type = 1;
							} else if (line.contains("audio")) {
								type = 2;
							}
						}
					}
				}
				_windowsVideo = new String[videoDevices.size()];
				for (int i = 0; i < videoDevices.size(); i++) {
					_windowsVideo[i] = videoDevices.get(i);
				}
				_windowsAudio = new String[audioDevices.size()];
				for (int i = 0; i < audioDevices.size(); i++) {
					_windowsAudio[i] = audioDevices.get(i);
				}
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		return new String[][] { _windowsVideo, _windowsAudio };
	}

	public static String getAudioDevice(Settings settings) {
		if (OSValidator.isUnix()) {
			return settings.get(Settings.AUDIO_DEVICE_LINUX);
		} else if (OSValidator.isWindows()) {
			return settings.get(Settings.AUDIO_DEVICE_WINDOWS);
		}
		return null;
	}

	public static int getAudioDeviceIndex(Settings settings) {
		return getAudioDeviceIndex(DeviceManager.getAudioDevice(settings));
	}

	public static int getAudioDeviceIndex(String device) {
		String[] devices = getAudioDevices();
		for (int i = 0; i < devices.length; i++) {
			if (devices[i].equalsIgnoreCase(device)) {
				return i;
			}
		}
		return -1;
	}

	public static String[] getAudioDevices() {
		if (OSValidator.isUnix()) {
			return DeviceManager._getLinuxAudioDevices();
		} else if (OSValidator.isWindows()) {
			return DeviceManager._getWindowsDevices()[1];
		}
		return new String[] {};
	}

	public static String getVideoDevice(Settings settings) {
		if (OSValidator.isUnix()) {
			return settings.get(Settings.VIDEO_DEVICE_LINUX);
		} else if (OSValidator.isWindows()) {
			return settings.get(Settings.VIDEO_DEVICE_WINDOWS);
		}
		return null;
	}

	public static int getVideoDeviceIndex(Settings settings) {
		return getVideoDeviceIndex(DeviceManager.getVideoDevice(settings));
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

	public static String[] getVideoDevices() {
		if (OSValidator.isUnix()) {
			return DeviceManager._getLinuxVideoDevices();
		} else if (OSValidator.isWindows()) {
			return DeviceManager._getWindowsDevices()[0];
		}
		return new String[] {};
	}

	public static void setAudioDevice(String device, Settings settings) {
		String setting = null;
		if (OSValidator.isUnix()) {
			setting = Settings.AUDIO_DEVICE_LINUX;
		} else if (OSValidator.isWindows()) {
			setting = Settings.AUDIO_DEVICE_WINDOWS;
		}
		try {
			settings.set(setting, device);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
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
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
