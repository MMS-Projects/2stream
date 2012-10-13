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

			Tray tray = display.getSystemTray();
			final TrayItem item = new TrayItem(tray, SWT.NONE);
			
			final SfiLoop sfiLoop = new SfiLoop();
			sfiLoop.setDaemon(true);
			sfiLoop.start();

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

			MainWindow shell = new MainWindow(display, wrapperThread, settings, debugWindow);
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
