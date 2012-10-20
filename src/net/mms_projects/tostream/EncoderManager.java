package net.mms_projects.tostream;

import java.util.ArrayList;
import java.util.List;

public class EncoderManager {

	private List<EncoderOutputListener> listeners = new ArrayList<EncoderOutputListener>();
	private List<Encoder> encoders = new ArrayList<Encoder>();
	private int currentEncoder = 0;
	private Settings settings;

	public EncoderManager(Settings settings) {
		this.settings = settings;
	}

	public void addEncoder(Encoder encoder) {
		encoder.setDaemon(true);
		encoder.start();

		encoders.add(encoder);
	}

	public void addListener(EncoderOutputListener toAdd) {
		listeners.add(toAdd);
	}

	public Encoder getCurrentEncoder() {
		return encoders.get(currentEncoder);
	}

	public List<Encoder> getEncoders() {
		return encoders;
	}

	public List<EncoderOutputListener> getListeners() {
		return listeners;
	}

	public void setCurrentEncoder(String encoderClass) {
		for (int i = 0; i < encoders.size(); i++) {
			if (encoders.get(i).getClass().getName()
					.equalsIgnoreCase(encoderClass)) {
				currentEncoder = i;
			}
		}
	}

}
