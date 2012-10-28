package net.mms_projects.tostream.input_devices;

import java.awt.Point;

import net.mms_projects.tostream.InputDevice;
import net.mms_projects.tostream.Settings;

public class Desktop extends InputDevice {
	
	private boolean cursorVisible = true;
	private Point location = new Point(0, 0);
	private Point resolution = new Point(0, 0);

	public Desktop(Settings settings) {
		super(settings);
		
		Integer[] resolution = settings.getAsIntegerArray(Settings.RESOLUTION);
		Integer[] location = settings.getAsIntegerArray(Settings.LOCATION);
		setLocation((int) location[0], (int) location[1]);
		setResolution((int) resolution[0], (int) resolution[1]);
		
		cursorVisible = settings.getAsBoolean("desktop-device.cursor-visible");
	}
	
	public boolean isCursorVisible() {
		return cursorVisible;
	}

	public void setCursorVisible(boolean cursorVisible) {
		this.cursorVisible = cursorVisible;
	}

	public Point getLocation() {
		return location;
	}

	public void setLocation(Point location) {
		this.location = location;
	}
	
	public void setLocation(int x, int y) {
		this.location = new Point(x, y);
	}

	public Point getResolution() {
		return resolution;
	}

	public void setResolution(Point resolution) {
		this.resolution = resolution;
	}
	
	public void setResolution(int x, int y) {
		this.resolution = new Point(x, y);
	}
	
	@Override
	public void save() {
		try {
			settings.set("desktop-device.cursor-visible", isCursorVisible());
			
			Integer[] resolutionArray = { resolution.x, resolution.y };
			Integer[] locationArray = { location.x, location.y };
			settings.set(Settings.RESOLUTION, resolutionArray);
			settings.set(Settings.LOCATION, locationArray);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
}
