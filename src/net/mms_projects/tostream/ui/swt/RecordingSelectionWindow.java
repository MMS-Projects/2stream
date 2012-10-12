package net.mms_projects.tostream.ui.swt;

import java.util.ArrayList;
import java.util.List;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Event;
import org.eclipse.swt.widgets.Listener;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.events.DragDetectListener;
import org.eclipse.swt.events.DragDetectEvent;
import org.eclipse.swt.events.MouseMoveListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.ControlAdapter;
import org.eclipse.swt.events.ControlEvent;
import org.eclipse.swt.graphics.Point;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.custom.StackLayout;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FormData;
import org.eclipse.swt.layout.FormAttachment;
import org.eclipse.swt.layout.RowLayout;
import swing2swt.layout.BorderLayout;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.events.DisposeListener;
import org.eclipse.swt.events.DisposeEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;

public class RecordingSelectionWindow extends Shell {

	private List<RecordingSelectionListener> listeners = new ArrayList<RecordingSelectionListener>();
	public boolean notifyListeners = true;
	
	public void addListener(RecordingSelectionListener listener) {
		listeners.add(listener);
	}
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			RecordingSelectionWindow shell = new RecordingSelectionWindow(
					display);
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

	/**
	 * Create the shell.
	 * @param display
	 */
	public RecordingSelectionWindow(Display display) {
		super(display, SWT.SHELL_TRIM);
		
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent arg0) {
				arg0.doit = false;
				close();
			}
		});
		setAlpha(200);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_GREEN));
		
		final RecordingSelectionWindow shell = this;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		StyledText styledText = new StyledText(this, SWT.READ_ONLY | SWT.WRAP);
		styledText.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		styledText.setBackground(this.getBackground());
		styledText.setText("1. Make sure the region you want to record is behind the green window\n2. Close the window. The region will be saved and used when recording.");
		
		addControlListener(new ControlAdapter() {
			public void event(ControlEvent arg0) {
				if (notifyListeners) {
					for (RecordingSelectionListener listener : listeners) {
						listener.selectionChanged(shell.getLocation(), shell.getSize());
					}
				}
			}
			@Override
			public void controlMoved(ControlEvent arg0) {
				event(arg0);
			}
			@Override
			public void controlResized(ControlEvent arg0) {
				event(arg0);
			}
		});
		
		createContents();
	}

	public void close() {
		setVisible(false);
	}
	
	@Override
	public void setVisible(boolean arg0) {
		notifyListeners = arg0;
		super.setVisible(arg0);
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(800, 600);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
