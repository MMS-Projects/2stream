package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.managers.EncoderManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class DebugConsole extends Shell {

	private Text text;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 */
	public DebugConsole(Display display, EncoderManager encoderManager) {
		super(display, SWT.SHELL_TRIM);
		setLayout(new FillLayout(SWT.HORIZONTAL));

		final Text text = new Text(this, SWT.READ_ONLY | SWT.WRAP
				| SWT.V_SCROLL | SWT.MULTI);

		encoderManager.addListener(new EncoderOutputListener() {
			@Override
			public void onOutput(final String output) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						text.append(output);
					}
				});
			}
		});

		createContents();
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(600, 300);

	}
}
