package net.mms_projects.tostream.input_devices;

import net.mms_projects.tostream.InputDevice;
import net.mms_projects.tostream.Settings;

public class VideoDevice extends InputDevice {

	public VideoDevice(Settings settings) {
		super(settings);
	}

	private String deviceFile = null;

	public String getDeviceFile() {
		return deviceFile;
	}

	public void setDeviceFile(String deviceFile) {
		this.deviceFile = deviceFile;
	}

}
