package net.mms_projects.tostream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encoder extends Thread {

	
	protected Settings settings;
	
	private boolean running = false;
	private EncoderManager manager;
	
	public Encoder(EncoderManager manager, Settings settings) {
		this.settings = settings;
		this.manager  = manager;
	}
	
	public boolean isRunning() {
		return running;
	}
	
	public void startEncoder() throws IOException, Exception {
		setState(true);
	}
	
	public void stopEncoder() throws Exception {
		setState(false);
	}
	
	protected void setState(boolean running) {
		this.running = running;
	}
	
	protected EncoderManager getManager() {
		return manager;
	}

}
