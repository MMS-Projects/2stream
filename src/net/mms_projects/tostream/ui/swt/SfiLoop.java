package net.mms_projects.tostream.ui.swt;

import java.util.Date;

import javax.sound.sampled.LineUnavailableException;

import net.mms_projects.tostream.Tone;

/*
 * SFI
 * Sound based Frame rate Indicator
 */

public class SfiLoop extends Thread {

	private int frame;
	private int framerate = 30;
	private long soundDelay = 1000;
	
	public SfiLoop() {
		super();
		// TODO Auto-generated constructor stub
	}
	
	public void setFrameAmount(int frame) {
		this.frame = frame;
	}
	
	public void setFramerate(int framerate) {
		this.framerate = framerate;
	}
	
	@Override
	public void run() {
		int oldFrame = 0;
		long oldSoundTime = new Date().getTime();
		long oldSecondTime = new Date().getTime();
		while (true) {
			if ((new Date().getTime() - oldSoundTime) > soundDelay) {
				//System.out.println("Jup!");
				/*try {
					Tone.sound(800, 30, 1);
				} catch (LineUnavailableException e) {
				}*/
				oldSoundTime = new Date().getTime();
			}
			if ((new Date().getTime() - oldSecondTime) > 1000) {
				soundDelay = Math.round((double) 1 / framerate * 1000) * 30;
				//System.out.println(soundDelay);
				oldSecondTime = new Date().getTime();
			}
			try {
				Thread.sleep(5);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

}
