package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.Service;
import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.managers.ServiceManager;
import net.mms_projects.tostream.streaming_services.TwitchTv;

import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

public class StreamingSettings extends Composite {
	private Text settingRtmpUrl;
	private Text text;
	private Button txtGetKey;
	private Text text_1;
	private Text text_2;
	private Button txtAuto;
	private final Combo combo;
	private final Combo combo_1;
	private final Button btnAuto;

	/**
	 * Create the composite.
	 * 
	 * @param parent
	 * @param style
	 */
	public StreamingSettings(Composite parent, final Settings settings) {
		super(parent, SWT.NONE);

		final ServiceManager manager = new ServiceManager();

		final TwitchTv service = new TwitchTv();
		setLayout(new GridLayout(1, false));

		Group grpSelectYourStreaming = new Group(this, SWT.NONE);
		grpSelectYourStreaming.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 1, 1));
		grpSelectYourStreaming.setText("Select your streaming service:");
		grpSelectYourStreaming.setLayout(new GridLayout(1, false));

		Label lblAaaa = new Label(grpSelectYourStreaming, SWT.NONE);
		lblAaaa.setText("Streaming Service:");

		combo = new Combo(grpSelectYourStreaming, SWT.READ_ONLY);
		combo.setItems(manager.getItemNames());
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		combo.add("Custom");
		combo.select(combo.getItemCount() - 1);

		Group grpManuallyConfigureYour = new Group(this, SWT.NONE);
		grpManuallyConfigureYour.setLayoutData(new GridData(SWT.FILL, SWT.FILL,
				false, false, 1, 1));
		grpManuallyConfigureYour.setLayout(new GridLayout(2, false));
		grpManuallyConfigureYour
				.setText("Manually configure your stream link:");

		Label lblServer = new Label(grpManuallyConfigureYour, SWT.NONE);
		lblServer.setText("Server:");
		new Label(grpManuallyConfigureYour, SWT.NONE);

		combo_1 = new Combo(grpManuallyConfigureYour, SWT.READ_ONLY);
		combo_1.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					manager.getCurrentItem()
							.setCurrentServer(text.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				settingRtmpUrl.setText(manager.getCurrentItem()
						.getStreamUrl());
			}
		});
		combo_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));
		combo_1.setItems(service.getServerNames());
		combo_1.select(manager.getCurrentItem().getCurrentServerIndex(
				manager.getCurrentItem().getCurrentServerName()));

		btnAuto = new Button(grpManuallyConfigureYour, SWT.NONE);
		btnAuto.setLayoutData(new GridData(SWT.FILL, SWT.TOP, false, false, 1,
				1));
		btnAuto.setText("Auto");

		Label lblStreamKey = new Label(grpManuallyConfigureYour, SWT.NONE);
		lblStreamKey.setText("Stream Key:");
		new Label(grpManuallyConfigureYour, SWT.NONE);

		text = new Text(grpManuallyConfigureYour, SWT.BORDER);
		text.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				manager.getCurrentItem().setToken(text.getText());
				settingRtmpUrl.setText(manager.getCurrentItem()
						.getStreamUrl());
			}
		});
		text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));

		txtGetKey = new Button(grpManuallyConfigureYour, SWT.BORDER);
		txtGetKey.setText("Get Key");
		txtGetKey.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false, 1,
				1));

		Group grpAutomaticilly = new Group(this, SWT.NONE);
		grpAutomaticilly.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false,
				false, 1, 1));
		grpAutomaticilly.setLayout(new GridLayout(2, false));
		grpAutomaticilly.setText("Automatically configure stream:");

		Label lblUsername = new Label(grpAutomaticilly, SWT.NONE);
		lblUsername.setText("Username:");

		Label lblPassword = new Label(grpAutomaticilly, SWT.NONE);
		lblPassword.setText("Password:");

		text_1 = new Text(grpAutomaticilly, SWT.BORDER);
		text_1.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				manager.getCurrentItem().setUsername(text_1.getText());
			}
		});
		text_1.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		text_1.setEnabled(service.authMethod != Service.AUTH_METHOD_TOKEN);

		text_2 = new Text(grpAutomaticilly, SWT.BORDER);
		text_2.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent arg0) {
				manager.getCurrentItem().setPassword(text_2.getText());
			}
		});
		text_2.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1,
				1));
		text_2.setEnabled(service.authMethod != Service.AUTH_METHOD_TOKEN);
		new Label(grpAutomaticilly, SWT.NONE);

		txtAuto = new Button(grpAutomaticilly, SWT.BORDER);
		txtAuto.setText("Auto");
		txtAuto.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
				1, 1));

		Group grpRtmp = new Group(this, SWT.NONE);
		GridData gd_grpRtmp = new GridData(SWT.FILL, SWT.FILL, false, false, 1,
				1);
		gd_grpRtmp.heightHint = 60;
		grpRtmp.setLayoutData(gd_grpRtmp);
		grpRtmp.setLayout(new FillLayout(SWT.HORIZONTAL));
		grpRtmp.setText("RTMP URL:");

		settingRtmpUrl = new Text(grpRtmp, SWT.BORDER | SWT.WRAP | SWT.MULTI);

		Composite composite = new Composite(this, SWT.NONE);
		composite.setLayoutData(new GridData(SWT.CENTER, SWT.CENTER, false,
				false, 1, 1));
		composite.setLayout(new GridLayout(2, false));

		Button btnApply = new Button(composite, SWT.NONE);
		btnApply.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				try {
					settings.set(Settings.STREAM_URL, settingRtmpUrl.getText());
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
			}
		});
		btnApply.setText("Apply");

		Button btnReset = new Button(composite, SWT.NONE);
		btnReset.setText("Reset");
		if (service != null) {
			settingRtmpUrl.setEnabled(false);
		}

		combo.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				updateInterface(manager);
			}
		});

		updateInterface(manager);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}

	public void updateInterface(ServiceManager manager) {
		try {
			boolean manualSection;
			boolean autoSection;
			boolean urlSection;

			Service service;

			if (combo.getText() == "Custom") {
				manualSection = false;
				autoSection = false;
				urlSection = true;
			} else {
				manager.setCurrentItem(combo.getText());
				service = manager.getCurrentItem();

				manualSection = service.authMethod == Service.AUTH_METHOD_TOKEN;
				autoSection = service.authMethod == Service.AUTH_METHOD_USERNAME;
				urlSection = false;

				if (manualSection) {
					combo_1.setItems(service.getServerNames());
					combo_1.select(manager.getCurrentItem()
							.getCurrentServerIndex(
									manager.getCurrentItem()
											.getCurrentServerName()));
				}
			}

			combo_1.setEnabled(manualSection);
			btnAuto.setEnabled(manualSection);
			txtGetKey.setEnabled(manualSection);
			text.setEnabled(manualSection);

			// Username field
			text_1.setEnabled(autoSection);
			// Password field
			text_2.setEnabled(autoSection);
			txtAuto.setEnabled(autoSection);

			settingRtmpUrl.setEnabled(urlSection);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
