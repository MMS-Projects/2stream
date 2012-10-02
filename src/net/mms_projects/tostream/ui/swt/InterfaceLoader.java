package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;

import org.eclipse.swt.widgets.Display;

public class InterfaceLoader {

	public InterfaceLoader(FfmpegWrapper wrapperThread, Settings settings) {
		try {
			Display display = Display.getDefault();
			
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
