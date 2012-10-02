package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;

import org.eclipse.swt.widgets.Display;

public class InterfaceLoader {

	public InterfaceLoader() {
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