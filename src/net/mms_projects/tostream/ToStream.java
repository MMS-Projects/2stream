package net.mms_projects.tostream;

import org.eclipse.swt.widgets.Display;

import net.mms_projects.tostream.ui.DebugConsole;
import net.mms_projects.tostream.ui.MainWindow;

public class ToStream {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			Display display = Display.getDefault();
			
			Settings settings = new Settings();
			
			FfmpegWrapper wrapperThread = new FfmpegWrapper();
			wrapperThread.setDaemon(true);
			wrapperThread.start();
			
			DebugConsole debugWindow = new DebugConsole(display, wrapperThread);
			debugWindow.setVisible(true);
			debugWindow.layout();
			
			
			
			MainWindow shell = new MainWindow(display, wrapperThread, settings);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

}
