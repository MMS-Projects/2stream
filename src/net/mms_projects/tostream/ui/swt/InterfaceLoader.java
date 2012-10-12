package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.GC;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.ImageData;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Tray;
import org.eclipse.swt.widgets.TrayItem;

public class InterfaceLoader extends net.mms_projects.tostream.InterfaceLoader {

	public InterfaceLoader(FfmpegWrapper wrapperThread, Settings settings) {
		try {
			final Display display = Display.getDefault();

			final SplashScreen splashScreen = new SplashScreen(display);
			splashScreen.open();
			
			splashScreen.setProgress(10, "Opened the splashscreen");
			
			Tray tray = display.getSystemTray();
			final TrayItem item = new TrayItem(tray, SWT.NONE);
			
			splashScreen.setProgress(20, "Initialized the system tray");
			
			final SfiLoop sfiLoop = new SfiLoop();
			sfiLoop.setDaemon(true);
			sfiLoop.start();
			
			splashScreen.setProgress(30, "Initialized the SFI loop");

			wrapperThread.addListener(new EncoderOutputListener() {
				public void onStatusUpdate(final int frame, final int framerate) {
					Display.getDefault().asyncExec(new Runnable() {
						@Override
						public void run() {
							Image image = new Image(display, 32, 32);
							GC gc = new GC(image);
							Font font = gc.getFont();
							gc.setBackground(display
									.getSystemColor(SWT.COLOR_GRAY));
							gc.fillRectangle(image.getBounds());
							gc.drawString(
									Integer.toString(framerate),
									(image.getBounds().height - font.getFontData()[0]
											.getHeight()) / 2,
									(image.getBounds().width - font.getFontData()[0]
											.getHeight()) / 2);
							gc.dispose();
							item.setImage(image);
							image.dispose();
							
							sfiLoop.setFrameAmount(frame);
							sfiLoop.setFramerate(framerate);
						}
					});
				}
			});

			DebugConsole debugWindow = new DebugConsole(display, wrapperThread);
			debugWindow.setVisible(true);
			debugWindow.layout();
			
			splashScreen.setProgress(40, "Initialized the debug console");

			MainWindow shell = new MainWindow(display, wrapperThread, settings);
			shell.open();
			shell.layout();
			
			splashScreen.setProgress(50, "Opened the main window");
			
			splashScreen.close();
			
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
