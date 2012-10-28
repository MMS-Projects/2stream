package net.mms_projects.tostream;

public class InputDevice {

	protected Settings settings;

	private String identifier;

	public static final int TYPE_DESKTOP = 1;
	public static final int TYPE_VIDEO_DEVICE = 2;

	public InputDevice(Settings settings) {
		this.settings = settings;
	}

	public String getIdentifier() {
		return identifier;
	}

	public void save() {

	}

	public void setIdentifier(String identifier) {
		this.identifier = identifier;
	}

}
