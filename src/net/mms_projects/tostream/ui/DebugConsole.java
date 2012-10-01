package net.mms_projects.tostream.ui;

import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.EncoderOutputListener;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.FormLayout;
import org.eclipse.swt.layout.FillLayout;

public class DebugConsole extends Shell {
	
	FfmpegWrapper ffmpegWrapper;
	private Text text;
	
	/**
	 * Create the shell.
	 * @param display
	 */
	public DebugConsole(Display display, FfmpegWrapper ffmpegWrapper) {
		super(display, SWT.SHELL_TRIM);
		this.ffmpegWrapper = ffmpegWrapper;
		setLayout(new FillLayout(SWT.HORIZONTAL));
		
		final Text text = new Text(this, SWT.BORDER | SWT.MULTI);
		
		this.ffmpegWrapper.addListener(new EncoderOutputListener() {
		    @Override
		    public void onOutput(final String output) {
		        Display.getDefault().asyncExec(new Runnable() {
		        	public void run() {
		        		text.append(output);
		        	}
		        });
		    }
		});
		
		createContents();
	}

	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
