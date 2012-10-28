package net.mms_projects.tostream.managers;

import net.mms_projects.tostream.InputDevice;
import net.mms_projects.tostream.Manager;
import net.mms_projects.tostream.OSValidator;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.input_devices.Desktop;
import net.mms_projects.tostream.input_devices.VideoDevice;

public class VideoDeviceManager extends Manager<InputDevice> {

	public VideoDeviceManager(Settings settings) {
		addItem("desktop", new Desktop(settings));
		String[] devices = DeviceManager.getVideoDevices();
		for (String device : devices) {
			VideoDevice deviceObject = new VideoDevice(settings);
			deviceObject.setDeviceFile(device);
			addItem(device, deviceObject);
		}
	}

	public int getItemIndex(Settings settings) {
		return getItemIndex(getVideoDevice(settings));
	}
	
	public void setCurrentItem(String currentItem, Settings settings) throws Exception {
		setCurrentItem(currentItem);
		setVideoDevice(currentItem, settings);
	}

	public void setVideoDevice(String text, Settings settings) {
		String setting = null;
		if (OSValidator.isUnix()) {
			setting = Settings.VIDEO_DEVICE_LINUX;
		} else if (OSValidator.isWindows()) {
			setting = Settings.VIDEO_DEVICE_WINDOWS;
		}
		try {
			settings.set(setting, text);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public String getVideoDevice(Settings settings) {
		if (OSValidator.isUnix()) {
			return settings.get(Settings.VIDEO_DEVICE_LINUX);
		} else if (OSValidator.isWindows()) {
			return settings.get(Settings.VIDEO_DEVICE_WINDOWS);
		}
		return null;
	}

}
