package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.Encoder;
import net.mms_projects.tostream.EncoderOutputListener;
import net.mms_projects.tostream.Messages;
import net.mms_projects.tostream.OSValidator;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.SettingsListener;
import net.mms_projects.tostream.ToStream;
import net.mms_projects.tostream.managers.DeviceManager;
import net.mms_projects.tostream.managers.EncoderManager;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.ShellAdapter;
import org.eclipse.swt.events.ShellEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
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
import org.eclipse.swt.widgets.TabFolder;
import org.eclipse.swt.widgets.TabItem;
import org.eclipse.swt.widgets.Text;

import swing2swt.layout.BorderLayout;

public class MainWindow extends Shell {

	Encoder ffmpegWrapper;
	private Text settingBitrate;
	private Text settingResolutionX;
	private Text settingResolutionY;
	private Text settingFramerate;
	private Text settingStreamUrl;

	private RecordingSelectionWindow regionSelectionWindow = new RecordingSelectionWindow(
			getDisplay());
	private Text settingLocationX;
	private Text settingLocationY;

	/**
	 * Create the shell.
	 * 
	 * @param display
	 * @param debugWindow
	 */
	public MainWindow(Display display, final EncoderManager encoderManager,
			final Settings settings, final DebugConsole debugWindow) {
		super(display, SWT.SHELL_TRIM);
		addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent arg0) {
				if (encoderManager.getCurrentItem().isRunning()) {
					try {
						encoderManager.getCurrentItem().stopEncoder();
					} catch (Exception e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		setLayout(new BorderLayout(0, 0));

		Menu menu = new Menu(this, SWT.BAR);
		setMenuBar(menu);

		MenuItem mntmFile = new MenuItem(menu, SWT.CASCADE);
		mntmFile.setText(Messages.getString("MainWindow.menuItemFile"));

		Menu menu_1 = new Menu(mntmFile);
		mntmFile.setMenu(menu_1);

		MenuItem mntmQuit = new MenuItem(menu_1, SWT.NONE);
		mntmQuit.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				close();
			}
		});
		mntmQuit.setText(Messages.getString("MainWindow.menuItemQuit"));

		MenuItem mntmHelp = new MenuItem(menu, SWT.CASCADE);
		mntmHelp.setText(Messages.getString("MainWindow.menuItemHelp"));

		Menu menu_2 = new Menu(mntmHelp);
		mntmHelp.setMenu(menu_2);

		final MenuItem mntmShowDebugconsole = new MenuItem(menu_2, SWT.CHECK);
		mntmShowDebugconsole.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				if (mntmShowDebugconsole.getSelection()) {
					debugWindow.open();
				} else {
					debugWindow.close();
				}
			}
		});
		mntmShowDebugconsole.setText(Messages
				.getString("MainWindow.showDebugConsole"));
		mntmShowDebugconsole.setSelection(debugWindow.getVisible());

		MenuItem mntmAdvancedSettings = new MenuItem(menu_2, SWT.NONE);
		mntmAdvancedSettings.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AdvancedSettings advancedSettings = new AdvancedSettings(
						arg0.display.getActiveShell(), settings);
				advancedSettings.open();
			}
		});
		mntmAdvancedSettings.setText(Messages
				.getString("MainWindow.menuItemAdvancedSettings"));

		MenuItem mntmSelectEncoder = new MenuItem(menu_2, SWT.NONE);
		mntmSelectEncoder.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				SwitchEncoder switcher = new SwitchEncoder(getShell(),
						encoderManager);
				switcher.open();
			}
		});
		mntmSelectEncoder.setText(Messages
				.getString("MainWindow.mntmSelectEncoder.text")); //$NON-NLS-1$

		new MenuItem(menu_2, SWT.SEPARATOR);

		MenuItem mntmAbout = new MenuItem(menu_2, SWT.NONE);
		mntmAbout.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				AboutDialog about = new AboutDialog(arg0.display
						.getActiveShell());
				about.open();
			}
		});
		mntmAbout.setText(Messages.getString("MainWindow.about")
				+ ToStream.getApplicationName());
		debugWindow.addShellListener(new ShellAdapter() {
			@Override
			public void shellClosed(ShellEvent arg0) {
				arg0.doit = false;
				debugWindow.setVisible(false);
				mntmShowDebugconsole.setSelection(false);
			}
		});

		TabFolder tabFolder = new TabFolder(this, SWT.NONE);

		TabItem tabStandard = new TabItem(tabFolder, SWT.NONE);
		tabStandard.setText(Messages.getString("MainWindow.tbtmNewItem.text")); //$NON-NLS-1$

		Composite compositeStandard = new Composite(tabFolder, SWT.NONE);
		tabStandard.setControl(compositeStandard);
		compositeStandard.setLayout(new GridLayout(2, false));

		Label labelVideoDevice = new Label(compositeStandard, SWT.NONE);
		labelVideoDevice.setText(Messages.getString("videoDevice"));

		final Combo settingVideoDevice = new Combo(compositeStandard,
				SWT.READ_ONLY);
		settingVideoDevice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		settingVideoDevice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DeviceManager.setVideoDevice(settingVideoDevice.getText(),
						settings);
			}
		});
		settingVideoDevice.setItems(DeviceManager.getVideoDevices());
		settingVideoDevice.select(DeviceManager.getVideoDeviceIndex(settings));

		Label labelAudioDevice = new Label(compositeStandard, SWT.NONE);
		labelAudioDevice.setText(Messages.getString("audioDevice"));

		final Combo settingAudioDevice = new Combo(compositeStandard,
				SWT.READ_ONLY);
		settingAudioDevice.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));
		settingAudioDevice.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				DeviceManager.setAudioDevice(settingAudioDevice.getText(),
						settings);
			}
		});
		settingAudioDevice.setItems(DeviceManager.getAudioDevices());
		settingAudioDevice.select(DeviceManager.getAudioDeviceIndex(settings));

		Label labelVideoEncodePreset = new Label(compositeStandard, SWT.NONE);
		labelVideoEncodePreset.setText("Video encode preset");

		Combo settingVideoEncodePreset = new Combo(compositeStandard,
				SWT.READ_ONLY);
		settingVideoEncodePreset.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		settingVideoEncodePreset.setItems(new String[] { "a", "a", "a" }); //$NON-NLS-2$ //$NON-NLS-3$

		Label labelVideoResolution = new Label(compositeStandard, SWT.NONE);
		labelVideoResolution.setText(Messages.getString("videoResolution"));

		Composite compositeVideoResolution = new Composite(compositeStandard,
				SWT.NONE);
		compositeVideoResolution.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		compositeVideoResolution.setLayout(new GridLayout(4, false));

		settingResolutionX = new Text(compositeVideoResolution, SWT.BORDER);
		settingResolutionX.setText(Integer.toString(settings
				.getAsIntegerArray(Settings.RESOLUTION)[0]));
		settingResolutionX.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				try {
					settings.videoResolution[0] = Integer
							.parseInt(settingResolutionX.getText());
				} catch (java.lang.NumberFormatException e) {
					settingResolutionX.setText("0");
				}
				try {
					settings.set(Settings.RESOLUTION, settings.videoResolution);
				} catch (Exception e) {
				}
			}
		});
		settingResolutionX.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent event) {
				switch (event.keyCode) {
				case SWT.BS: // Backspace
				case SWT.DEL: // Delete
				case SWT.HOME: // Home
				case SWT.END: // End
				case SWT.ARROW_LEFT: // Left arrow
				case SWT.ARROW_RIGHT: // Right arrow
					return;
				}

				if ((!Character.isDigit(event.character))
						&& (Character.getNumericValue(event.character) != -1)) {
					event.doit = false; // disallow the action
					System.out
							.println("'" + Character.getNumericValue(event.character) + "'"); //$NON-NLS-2$
				}
			}
		});
		settingResolutionX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Label labelX = new Label(compositeVideoResolution, SWT.NONE);
		labelX.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		labelX.setText("X");

		settingResolutionY = new Text(compositeVideoResolution, SWT.BORDER);
		settingResolutionY.setText(Integer.toString(settings
				.getAsIntegerArray(Settings.RESOLUTION)[1]));
		settingResolutionY.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				try {
					settings.videoResolution[1] = Integer
							.parseInt(settingResolutionY.getText());
				} catch (java.lang.NumberFormatException e) {
					settingResolutionY.setText("0");
				}
				try {
					settings.set(Settings.RESOLUTION, settings.videoResolution);
				} catch (Exception e) {
				}
			}
		});
		settingResolutionY.addVerifyListener(new VerifyListener() {
			@Override
			public void verifyText(final VerifyEvent event) {
				switch (event.keyCode) {
				case SWT.BS: // Backspace
				case SWT.DEL: // Delete
				case SWT.HOME: // Home
				case SWT.END: // End
				case SWT.ARROW_LEFT: // Left arrow
				case SWT.ARROW_RIGHT: // Right arrow
					return;
				}

				if ((!Character.isDigit(event.character))
						&& (Character.getNumericValue(event.character) != -1)) {
					event.doit = false; // disallow the action
					System.out.println(event.character);
				}
			}
		});
		settingResolutionY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				true, false, 1, 1));

		Button btnSelectRegion = new Button(compositeVideoResolution, SWT.NONE);
		btnSelectRegion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				regionSelectionWindow.setSize(
						settings.getAsIntegerArray(Settings.RESOLUTION)[0],
						settings.getAsIntegerArray(Settings.RESOLUTION)[1]);
				regionSelectionWindow.setLocation(
						settings.getAsIntegerArray(Settings.LOCATION)[0],
						settings.getAsIntegerArray(Settings.LOCATION)[1]);
				regionSelectionWindow.open();
			}
		});
		btnSelectRegion.setText("Select region");

		settingLocationX = new Text(compositeVideoResolution, SWT.BORDER);
		settingLocationX.setText(Integer.toString(settings
				.getAsIntegerArray(Settings.LOCATION)[0]));
		settingLocationX.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		settingLocationX.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				Integer[] location = settings
						.getAsIntegerArray(Settings.LOCATION);
				try {
					location[0] = Integer.parseInt(settingLocationX.getText());
				} catch (java.lang.NumberFormatException e) {
					settingLocationX.setText("0");
				}
				try {
					settings.set(Settings.LOCATION, location);
				} catch (Exception e) {
				}
			}
		});
		Label labelLocation = new Label(compositeVideoResolution, SWT.NONE);
		labelLocation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		labelLocation.setText(",");

		settingLocationY = new Text(compositeVideoResolution, SWT.BORDER);
		settingLocationY.setText(Integer.toString(settings
				.getAsIntegerArray(Settings.LOCATION)[1]));
		settingLocationY.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true,
				false, 1, 1));
		settingLocationY.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				Integer[] location = settings
						.getAsIntegerArray(Settings.LOCATION);
				try {
					location[1] = Integer.parseInt(settingLocationY.getText());
				} catch (java.lang.NumberFormatException e) {
					settingLocationY.setText("0");
				}
				try {
					settings.set(Settings.LOCATION, location);
				} catch (Exception e) {
				}
			}
		});

		new Label(compositeVideoResolution, SWT.NONE);

		Label labelVideoFrameRate = new Label(compositeStandard, SWT.NONE);
		labelVideoFrameRate.setText(Messages.getString("videoFrameRate"));

		settingFramerate = new Text(compositeStandard, SWT.BORDER);
		settingFramerate.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1));
		settingFramerate.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				try {
					settings.set(Settings.FRAME_RATE,
							settingFramerate.getText());
				} catch (Exception e) {
				}
			}
		});
		settingFramerate.setText(settings.get(Settings.FRAME_RATE));

		Label labelAudioBitrate = new Label(compositeStandard, SWT.NONE);
		labelAudioBitrate.setText(Messages.getString("audioBitrate"));

		new Label(compositeStandard, SWT.NONE);

		Label labelAudioChannels = new Label(compositeStandard, SWT.NONE);
		labelAudioChannels.setText(Messages.getString("audioChannels"));

		new Label(compositeStandard, SWT.NONE);

		Label labelStreamUrl = new Label(compositeStandard, SWT.NONE);
		labelStreamUrl.setText("Stream URL");

		settingStreamUrl = new Text(compositeStandard, SWT.BORDER);
		settingStreamUrl.setLayoutData(new GridData(SWT.FILL, SWT.CENTER,
				false, false, 1, 1));
		settingStreamUrl.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				try {
					settings.set(Settings.STREAM_URL,
							settingStreamUrl.getText());
				} catch (Exception e) {
				}
			}
		});

		TabItem tabAdvanced = new TabItem(tabFolder, SWT.NONE);
		tabAdvanced
				.setText(Messages.getString("MainWindow.tbtmNewItem.text_1"));

		Composite compositeAdvanced = new Composite(tabFolder, SWT.NONE);
		tabAdvanced.setControl(compositeAdvanced);
		compositeAdvanced.setLayout(new GridLayout(2, false));

		Label labelVideoBitrate = new Label(compositeAdvanced, SWT.NONE);
		labelVideoBitrate.setText(Messages.getString("videoBitrate"));

		settingBitrate = new Text(compositeAdvanced, SWT.BORDER);
		settingBitrate.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent event) {
				try {
					settings.set(Settings.BITRATE, settingBitrate.getText());
				} catch (Exception e) {
				}
			}
		});
		settingBitrate.setText(settings.get(Settings.BITRATE));

		TabItem tabStreamingSettings = new TabItem(tabFolder, SWT.NONE);
		tabStreamingSettings.setText(Messages
				.getString("MainWindow.tbtmStreamingSettings.text")); //$NON-NLS-1$

		Composite composite_2 = new StreamingSettings(tabFolder, settings);
		tabStreamingSettings.setControl(composite_2);

		Composite composite_1 = new Composite(this, SWT.NONE);
		composite_1.setLayoutData(BorderLayout.SOUTH);
		composite_1.setLayout(new GridLayout(1, false));

		Composite composite = new Composite(composite_1, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));

		final Button buttonStart = new Button(composite, SWT.NONE);
		buttonStart.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent event) {
				try {
					encoderManager.getCurrentItem().startEncoder();
				} catch (Exception error) {
					MessageBox msg = new MessageBox(new Shell());
					msg.setText("An erorr occured");
					msg.setMessage("Error while starting FFmpeg: "
							+ error.getMessage());
					msg.open();
				}
			}
		});
		buttonStart.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		buttonStart.setText("Start");

		final Button buttonStop = new Button(composite, SWT.NONE);
		buttonStop.setEnabled(false);
		buttonStop.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				try {
					encoderManager.getCurrentItem().stopEncoder();
				} catch (Exception error) {
					MessageBox msg = new MessageBox(new Shell());
					msg.setText("An erorr occured");
					msg.setMessage("Error while stopping FFmpeg: "
							+ error.getMessage());
					msg.open();
				}
			}
		});
		buttonStop.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, false,
				1, 1));
		buttonStop.setText("Stop");

		final Label labelStatus = new Label(composite_1, SWT.NONE);
		GridData gd_labelStatus = new GridData(SWT.FILL, SWT.CENTER, false, false, 1, 1);
		gd_labelStatus.widthHint = 367;
		labelStatus.setLayoutData(gd_labelStatus);
		labelStatus.setText("Please start to get the status");
		settings.addListener(Settings.BITRATE, new SettingsListener() {
			@Override
			public void settingSet(String value) {
				if (!OSValidator.isWindows()) {
					settingBitrate.setText(value);
				}
			}
		});

		settings.addListener(Settings.RESOLUTION, new SettingsListener() {
			@Override
			public void settingSet(String value) {
				String[] resolution = value.split(",");
				if (!OSValidator.isWindows()) {
					settingResolutionX.setText(resolution[0]);
					settingResolutionY.setText(resolution[1]);
				}
			}
		});

		settings.addListener(Settings.LOCATION, new SettingsListener() {
			@Override
			public void settingSet(String value) {
				String[] resolution = value.split(",");
				if (!OSValidator.isWindows()) {
					settingLocationX.setText(resolution[0]);
					settingLocationY.setText(resolution[1]);
				}
			}
		});
		settings.addListener(Settings.FRAME_RATE, new SettingsListener() {
			@Override
			public void settingSet(String value) {
				if (!OSValidator.isWindows()) {
					settingFramerate.setText(value);
				}
			}
		});
		if (settings.get(Settings.STREAM_URL) != null) {
			settingStreamUrl.setText(settings.get(Settings.STREAM_URL));
		}
		settings.addListener(Settings.STREAM_URL, new SettingsListener() {
			@Override
			public void settingSet(String value) {
				if (!OSValidator.isWindows()) {
					settingStreamUrl.setText(value);
				}
			}
		});

		encoderManager.addListener(new EncoderOutputListener() {
			@Override
			public void onStart() {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						buttonStop.setEnabled(true);
						buttonStart.setEnabled(false);

						regionSelectionWindow.close();
					}
				});
			}

			@Override
			public void onStatusUpdate(final int frame, final int framerate,
					final double bitrate) {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						labelStatus.setText("FPS: " + framerate + " - Frame: " //$NON-NLS-2$
								+ frame + " - Bitrate: " + bitrate + " kbit/s"); //$NON-NLS-2$
					}
				});
			}

			@Override
			public void onStop() {
				Display.getDefault().asyncExec(new Runnable() {
					@Override
					public void run() {
						buttonStop.setEnabled(false);
						buttonStart.setEnabled(true);
					}
				});
			}
		});

		regionSelectionWindow.addListener(new RecordingSelectionListener() {
			@Override
			public void selectionChanged(final Point location, final Point size) {
				Display.getDefault().asyncExec(new Runnable() {

					@Override
					public void run() {
						settingResolutionX.setText(Integer.toString(size.x));
						settingResolutionY.setText(Integer.toString(size.y));

						settingLocationX.setText(Integer.toString(location.x));
						settingLocationY.setText(Integer.toString(location.y));

						Integer[] resolution = { size.x, size.y };
						Integer[] locationArray = { location.x, location.y };
						try {
							settings.set(Settings.RESOLUTION, resolution);
							settings.set(Settings.LOCATION, locationArray);
						} catch (Exception e) {
						}
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
		setText(ToStream.getApplicationName());
		setSize(600, 600);

	}
}
