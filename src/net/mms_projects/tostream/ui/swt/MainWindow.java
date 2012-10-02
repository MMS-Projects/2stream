package net.mms_projects.tostream.ui.swt;

import java.io.IOException;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Menu;
import org.eclipse.swt.widgets.MenuItem;
import org.eclipse.swt.widgets.MessageBox;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class MainWindow extends Shell {

	FfmpegWrapper ffmpegWrapper;
	private Text text;
	private Text text_1;
	private Text text_2;

	/**
	 * Create the shell.
	 * @param display
	 */
	public MainWindow(Display display, final FfmpegWrapper ffmpegWrapper, Settings settings) {
		super(display, SWT.SHELL_TRIM);
		this.ffmpegWrapper = ffmpegWrapper;
		setLayout(new GridLayout(2, false));

		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText("File");

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.setText("Quit");

		Label lblVideoBitrate = new Label(this, SWT.NONE);
		lblVideoBitrate.setText("Video Bitrate");

		text = new Text(this, SWT.BORDER);
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		text.setText("2500");

		Label lblVideoEncodePreset = new Label(this, SWT.NONE);
		lblVideoEncodePreset.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVideoEncodePreset.setText("Video Encode Preset");

		Combo comboVideoEncodePreset = new Combo(this, SWT.READ_ONLY);
		comboVideoEncodePreset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		comboVideoEncodePreset.setItems(new String[] {"a", "a", "a"});

		Label lblVideoResolution = new Label(this, SWT.NONE);
		lblVideoResolution.setText("Video Resolution");

		Composite compositeVideoResolution = new Composite(this, SWT.NONE);
		compositeVideoResolution.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		compositeVideoResolution.setLayout(new GridLayout(3, false));

		text_1 = new Text(compositeVideoResolution, SWT.BORDER);
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblX = new Label(compositeVideoResolution, SWT.NONE);
		lblX.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblX.setText("X");

		text_2 = new Text(compositeVideoResolution, SWT.BORDER);
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblVideoFrameRate = new Label(this, SWT.NONE);
		lblVideoFrameRate.setText("Video Frame Rate");
		new Label(this, SWT.NONE);

		Label lblAudioBitrate = new Label(this, SWT.NONE);
		lblAudioBitrate.setText("Audio Bitrate");
		new Label(this, SWT.NONE);

		Label lblAudioChannels = new Label(this, SWT.NONE);
		lblAudioChannels.setText("Audio Channels");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		composite.setLayout(new GridLayout(2, false));

		Button btnNewButton = new Button(composite, SWT.NONE);
		btnNewButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					ffmpegWrapper.startEncoder();
				} catch (IOException e) {
					MessageBox msg = new MessageBox(new Shell());
					msg.setText("An erorr occured");
					msg.setMessage("Error while starting FFmpeg: " + e.getMessage());
					msg.open();
				}
			}
		});
		btnNewButton.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnNewButton.setText("New Button");

		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ffmpegWrapper.stopEncoder();
			}
		});
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnNewButton_1.setText("New Button");
		new Label(this, SWT.NONE);
		
		final Label lblStatus = new Label(this, SWT.NONE);
		lblStatus.setText("Please start to get the status");

		this.ffmpegWrapper.addListener(new EncoderOutputListener() {
			public void onStatusUpdate(final int frame, final int framerate) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						lblStatus.setText("FPS: " + framerate + " - Frame: " + frame);
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
		setText("2STREAM");
		setSize(450, 300);

	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
