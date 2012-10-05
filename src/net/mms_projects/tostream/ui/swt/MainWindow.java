package net.mms_projects.tostream.ui.swt;

import java.io.IOException;

import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.FfmpegWrapper;
import net.mms_projects.tostream.Settings;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
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
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.ModifyEvent;

public class MainWindow extends Shell {

	FfmpegWrapper ffmpegWrapper;
	private Text settingBitrate;
	private Text settingsResolutionX;
	private Text settingsResolutionY;
	private Text settingFramerate;

	/**
	 * Create the shell.
	 * @param display
	 */
	public MainWindow(Display display, final FfmpegWrapper ffmpegWrapper, final Settings settings) {
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
		lblVideoBitrate.setText("Video bitrate");

		settingBitrate = new Text(this, SWT.BORDER);
		settingBitrate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		settingBitrate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				try {
					settings.set(Settings.BITRATE, settingBitrate.getText());
				} catch (Exception e) {
				}
			}
		});
		settingBitrate.setText(settings.get(Settings.BITRATE));

		Label lblVideoEncodePreset = new Label(this, SWT.NONE);
		lblVideoEncodePreset.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblVideoEncodePreset.setText("Video encode preset");

		Combo settingVideoEncodePreset = new Combo(this, SWT.READ_ONLY);
		settingVideoEncodePreset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		settingVideoEncodePreset.setItems(new String[] {"a", "a", "a"});

		Label lblVideoResolution = new Label(this, SWT.NONE);
		lblVideoResolution.setText("Video resolution");

		Composite compositeVideoResolution = new Composite(this, SWT.NONE);
		compositeVideoResolution.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		compositeVideoResolution.setLayout(new GridLayout(3, false));

		settingsResolutionX = new Text(compositeVideoResolution, SWT.BORDER);
		settingsResolutionX.setText(Integer.toString(settings.getAsIntegerArray(Settings.RESOLUTION)[0]));
		settingsResolutionX.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				try {
					settings.videoResolution[0] = Integer.parseInt(settingsResolutionX.getText());
				}
				catch (java.lang.NumberFormatException e) {
					settingsResolutionX.setText("0");
				}
				try {
					settings.set(Settings.RESOLUTION, settings.videoResolution);
				} catch (Exception e) {
				}
			}
		});
		settingsResolutionX.addVerifyListener(new VerifyListener() {  
		    @Override  
		    public void verifyText(final VerifyEvent event) {  
		        switch (event.keyCode) {  
		            case SWT.BS:           // Backspace  
		            case SWT.DEL:          // Delete  
		            case SWT.HOME:         // Home  
		            case SWT.END:          // End  
		            case SWT.ARROW_LEFT:   // Left arrow  
		            case SWT.ARROW_RIGHT:  // Right arrow  
		                return;  
		        }  
		  
		        if (!Character.isDigit(event.character)) {  
		            event.doit = false;  // disallow the action  
		        }  
		    }  
		});
		settingsResolutionX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		Label lblX = new Label(compositeVideoResolution, SWT.NONE);
		lblX.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblX.setText("X");

		settingsResolutionY = new Text(compositeVideoResolution, SWT.BORDER);
		settingsResolutionY.setText(Integer.toString(settings.getAsIntegerArray(Settings.RESOLUTION)[1]));
		settingsResolutionY.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				try {
					settings.videoResolution[1] = Integer.parseInt(settingsResolutionY.getText());
				}
				catch (java.lang.NumberFormatException e) {
					settingsResolutionY.setText("0");
				}
				try {
					settings.set(Settings.RESOLUTION, settings.videoResolution);
				} catch (Exception e) {
				}
			}
		});
		settingsResolutionY.addVerifyListener(new VerifyListener() {  
		    @Override  
		    public void verifyText(final VerifyEvent event) {  
		        switch (event.keyCode) {  
		            case SWT.BS:           // Backspace  
		            case SWT.DEL:          // Delete  
		            case SWT.HOME:         // Home  
		            case SWT.END:          // End  
		            case SWT.ARROW_LEFT:   // Left arrow  
		            case SWT.ARROW_RIGHT:  // Right arrow  
		                return;  
		        }  
		  
		        if (!Character.isDigit(event.character)) {  
		            event.doit = false;  // disallow the action  
		        }  
		    }  
		});
		settingsResolutionY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		
		Label lblVideoFrameRate = new Label(this, SWT.NONE);
		lblVideoFrameRate.setText("Video frame rate");
		
		settingFramerate = new Text(this, SWT.BORDER);
		settingFramerate.addModifyListener(new ModifyListener() {
			public void modifyText(ModifyEvent event) {
				try {
					settings.set(Settings.FRAME_RATE, settingFramerate.getText());
				} catch (Exception e) {
				}
			}
		});
		settingFramerate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		settingFramerate.setText(settings.get(Settings.FRAME_RATE));

		Label lblAudioBitrate = new Label(this, SWT.NONE);
		lblAudioBitrate.setText("Audio bitrate");
		new Label(this, SWT.NONE);

		Label lblAudioChannels = new Label(this, SWT.NONE);
		lblAudioChannels.setText("Audio channels");
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
		btnNewButton.setText("Start");

		Button btnNewButton_1 = new Button(composite, SWT.NONE);
		btnNewButton_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				ffmpegWrapper.stopEncoder();
			}
		});
		btnNewButton_1.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false, 1, 1));
		btnNewButton_1.setText("Stop");
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
