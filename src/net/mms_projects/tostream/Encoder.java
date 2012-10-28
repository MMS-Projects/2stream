package net.mms_projects.tostream;

import java.io.IOException;

import net.mms_projects.tostream.managers.EncoderManager;
import net.mms_projects.tostream.managers.VideoDeviceManager;

public class Encoder extends Thread {

	protected Settings settings;
	protected VideoDeviceManager videoManager;

	private boolean running = false;
	private EncoderManager manager;

	public Encoder(EncoderManager manager, Settings settings,
			VideoDeviceManager videoManager) {
		this.settings = settings;
		this.manager = manager;
		this.videoManager = videoManager;
	}

	protected EncoderManager getManager() {
		return manager;
	}

	public boolean isRunning() {
		return running;
	}

	protected void setState(boolean running) {
		this.running = running;
	}

	public void startEncoder() throws IOException, Exception {
		setState(true);
	}

	public void stopEncoder() throws Exception {
		setState(false);
	}

}
