package net.mms_projects.tostream;

import java.io.IOException;

public class Encoder extends Thread {

	protected Settings settings;

	private boolean running = false;
	private EncoderManager manager;

	public Encoder(EncoderManager manager, Settings settings) {
		this.settings = settings;
		this.manager = manager;
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
