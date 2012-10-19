package net.mms_projects.tostream;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class Encoder extends Thread {

	protected List<EncoderOutputListener> listeners = new ArrayList<EncoderOutputListener>();
	protected Settings settings;
	
	private boolean running = false;
	
	public Encoder(Settings settings) {
		this.settings = settings;
		// TODO Auto-generated constructor stub
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
	
	public void addListener(EncoderOutputListener toAdd) {
		listeners.add(toAdd);
	}
	
	protected void setState(boolean running) {
		this.running = running;
	}

}
