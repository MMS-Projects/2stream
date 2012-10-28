package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.InputDevice;
import net.mms_projects.tostream.input_devices.Desktop;
import net.mms_projects.tostream.ui.swt.video_settings.DesktopSettings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;

import swing2swt.layout.BorderLayout;

public class VideoSettings extends Dialog {

	protected Object result;
	protected Shell shell;
	private InputDevice device;
	private DesktopSettings composite;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public VideoSettings(Shell parent, InputDevice device) {
		super(parent);
		this.device = device;
		setText("SWT Dialog");
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE
				| SWT.PRIMARY_MODAL);
		shell.setSize(450, 300);
		shell.setText(getText());
		shell.setLayout(new BorderLayout(0, 0));

		composite = null;

		if (device instanceof Desktop) {
			composite = new DesktopSettings(shell, SWT.NONE, (Desktop) device);
		}

		Composite controls = new Composite(shell, SWT.NONE);
		controls.setLayoutData(BorderLayout.SOUTH);
		controls.setLayout(new GridLayout(1, false));

		Button btnApply = new Button(controls, SWT.NONE);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				composite.save();
				System.out.println(((Desktop) device).isCursorVisible());
			}
		});
		btnApply.setText("Apply");

	}

	/**
	 * Open the dialog.
	 * 
	 * @return the result
	 */
	public Object open() {
		createContents();
		shell.open();
		shell.layout();
		Display display = getParent().getDisplay();
		while (!shell.isDisposed()) {
			if (!display.readAndDispatch()) {
				display.sleep();
			}
		}
		return result;
	}
}
