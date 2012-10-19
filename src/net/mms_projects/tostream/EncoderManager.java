package net.mms_projects.tostream;

import java.util.ArrayList;
import java.util.List;

public class EncoderManager {

	private List<EncoderOutputListener> listeners = new ArrayList<EncoderOutputListener>();
	private List<Encoder> encoders = new ArrayList<Encoder>();
	private int currentEncoder = 1;
	private Settings settings;
	
	public EncoderManager(Settings settings) {
		this.settings = settings;
	}
	
	public void addListener(EncoderOutputListener toAdd) {
		listeners.add(toAdd);
	}
	
	public void addEncoder(Encoder encoder) {
		encoder.setDaemon(true);
		encoder.start();
		
		encoders.add(encoder);
	}
	
	public Encoder getCurrentEncoder() {
		return encoders.get(currentEncoder);
	}
	
	public List<EncoderOutputListener> getListeners() {
		return listeners;
	}

}
