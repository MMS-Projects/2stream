package net.mms_projects.tostream.ui.swt.video_settings;

import java.util.LinkedHashMap;

import net.mms_projects.tostream.Messages;
import net.mms_projects.tostream.input_devices.Desktop;
import net.mms_projects.tostream.ui.swt.RecordingSelectionListener;
import net.mms_projects.tostream.ui.swt.RecordingSelectionWindow;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.events.VerifyEvent;
import org.eclipse.swt.events.VerifyListener;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class DesktopSettings extends Composite {

	private RecordingSelectionWindow regionSelectionWindow = new RecordingSelectionWindow(
			getDisplay());
	private Desktop device;
	private Button btnShowCursor;
	private VerifyListener numberVerify = new VerifyListener() {
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
	};
	private Label resolutionName;
	private Text settingResolutionY;
	private Text settingLocationX;
	private Text settingLocationY;
	private Text settingResolutionX;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public DesktopSettings(Composite parent, int style, Desktop device) {
		super(parent, style);
		this.device = device;
		setLayout(new GridLayout(3, false));

		btnShowCursor = new Button(this, SWT.CHECK);
		btnShowCursor.setSelection(device.isCursorVisible());
		btnShowCursor.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				System.out.println(btnShowCursor.getSelection());
			}
		});
		btnShowCursor.setText("Show cursor");
		new Label(this, SWT.NONE);
		new Label(this, SWT.NONE);

		Label labelVideoResolution = new Label(this, SWT.NONE);
		labelVideoResolution.setLayoutData(new GridData(SWT.LEFT, SWT.TOP,
				false, false, 1, 1));
		labelVideoResolution.setText(Messages
				.getString("setting.video-resolution"));

		Composite compositeVideoResolution = new Composite(this, SWT.NONE);
		compositeVideoResolution.setLayout(new GridLayout(4, false));

		settingResolutionX = new Text(compositeVideoResolution, SWT.BORDER);
		GridData gd_settingResolutionX = new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 1, 1);
		gd_settingResolutionX.widthHint = 50;
		settingResolutionX.setLayoutData(gd_settingResolutionX);
		settingResolutionX.setText(Integer.toString(device.getResolution().x));
		settingResolutionX.addVerifyListener(numberVerify);

		Label labelX = new Label(compositeVideoResolution, SWT.NONE);
		labelX.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false,
				1, 1));
		labelX.setText("X");

		settingResolutionY = new Text(compositeVideoResolution, SWT.BORDER);
		GridData gd_settingResolutionY = new GridData(SWT.LEFT, SWT.CENTER,
				true, false, 1, 1);
		gd_settingResolutionY.widthHint = 50;
		settingResolutionY.setLayoutData(gd_settingResolutionY);
		settingResolutionY.setText(Integer.toString(device.getResolution().y));
		settingResolutionY.addVerifyListener(numberVerify);

		resolutionName = new Label(compositeVideoResolution, SWT.NONE);
		GridData gd_resolutionName = new GridData(SWT.LEFT, SWT.CENTER, false,
				false, 1, 1);
		gd_resolutionName.widthHint = 70;
		resolutionName.setLayoutData(gd_resolutionName);
		resolutionName.setText(Messages.getString("MainWindow.label.text")); //$NON-NLS-1$

		settingLocationX = new Text(compositeVideoResolution, SWT.BORDER);
		GridData gd_settingLocationX = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_settingLocationX.widthHint = 50;
		settingLocationX.setLayoutData(gd_settingLocationX);
		settingLocationX.setText(Integer.toString(device.getLocation().x));
		settingLocationX.addVerifyListener(numberVerify);
		Label labelLocation = new Label(compositeVideoResolution, SWT.NONE);
		labelLocation.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false,
				false, 1, 1));
		labelLocation.setText(",");

		settingLocationY = new Text(compositeVideoResolution, SWT.BORDER);
		GridData gd_settingLocationY = new GridData(SWT.LEFT, SWT.CENTER, true,
				false, 1, 1);
		gd_settingLocationY.widthHint = 50;
		settingLocationY.setLayoutData(gd_settingLocationY);
		settingLocationY.setText(Integer.toString(device.getLocation().y));
		new Label(compositeVideoResolution, SWT.NONE);
		settingLocationY.addVerifyListener(numberVerify);

		updateResolutionName();

		Button btnSelectRegion = new Button(this, SWT.NONE);
		btnSelectRegion.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				regionSelectionWindow.setSize(
						Integer.parseInt(settingResolutionX.getText()),
						Integer.parseInt(settingResolutionY.getText()));
				regionSelectionWindow.setLocation(
						Integer.parseInt(settingLocationX.getText()),
						Integer.parseInt(settingLocationY.getText()));
				regionSelectionWindow.open();
			}
		});
		btnSelectRegion.setText(Messages.getString("MainWindow.select-region"));

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
						updateResolutionName();
					}
				});
			}
		});
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void save() {
		device.setCursorVisible(btnShowCursor.getSelection());
		device.setResolution(Integer.parseInt(settingResolutionX.getText()),
				Integer.parseInt(settingResolutionY.getText()));
		device.setLocation(Integer.parseInt(settingLocationX.getText()),
				Integer.parseInt(settingLocationY.getText()));
		device.save();
	}

	protected void updateResolutionName() {
		Integer[] resolution = {
				Integer.parseInt(settingResolutionX.getText()),
				Integer.parseInt(settingResolutionY.getText()) };
		LinkedHashMap<String, Point> resolutions = new LinkedHashMap<String, Point>();
		resolutions.put("240p", new Point(426, 240));
		resolutions.put("320p", new Point(640, 360));
		resolutions.put("480p", new Point(854, 480));
		resolutions.put("720p HD", new Point(1280, 720));
		resolutions.put("1080p HD", new Point(1920, 1080));

		for (String name : resolutions.keySet()) {
			Point size = resolutions.get(name);
			int difference = Math.min(Math.abs(size.x - resolution[0]),
					Math.abs(size.y - resolution[1]));
			boolean good = true;
			if (Math.abs(size.x - resolution[0]) > 100) {
				good = false;
			}
			if (Math.abs(size.y - resolution[1]) > 100) {
				good = false;
			}
			if (good) {
				resolutionName.setText((difference != 0 ? "≈ " : "") + name);
			}
		}
	}

}
