package net.mms_projects.tostream.ui.swt;

import java.util.LinkedHashMap;

import net.mms_projects.tostream.Settings;
import net.mms_projects.tostream.ToStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.ScrolledComposite;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.Text;

public class AdvancedSettings extends Dialog {

	protected Object result;
	protected Shell shell;
	protected Settings settings;
	private ScrolledComposite scrolledComposite;
	private Composite composite;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AdvancedSettings(Shell parent, Settings settings) {
		super(parent);

		this.settings = settings;

		setText(ToStream.getApplicationName() + " - Advanced settings");
	}

	private void buildForm() {
		LinkedHashMap<String, String> settingsArray = settings.getSettings();
		LinkedHashMap<String, Text> formElements = new LinkedHashMap<String, Text>();

		for (String key : settingsArray.keySet()) {
			final Label label = new Label(composite, SWT.NONE);
			label.setText(key);
			label.setLayoutData(new GridData(SWT.LEFT, SWT.CENTER, false,
					false, 1, 1));

			final Text text = new Text(composite, SWT.BORDER);
			text.setText(settingsArray.get(key));
			text.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false,
					1, 1));
			text.addModifyListener(new ModifyListener() {
				@Override
				public void modifyText(ModifyEvent event) {
					try {
						settings.set(label.getText(), text.getText());
					} catch (Exception e) {
					}
				}
			});
			formElements.put(key, text);
		}
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE
				| SWT.PRIMARY_MODAL);
		shell.setSize(700, 300);
		shell.setText(getText());
		shell.setLayout(new FillLayout(SWT.HORIZONTAL));

		scrolledComposite = new ScrolledComposite(shell, SWT.BORDER
				| SWT.V_SCROLL);
		scrolledComposite.setEnabled(true);
		scrolledComposite.setExpandVertical(true);
		scrolledComposite.setExpandHorizontal(true);
		scrolledComposite.setAlwaysShowScrollBars(true);

		composite = new Composite(scrolledComposite, SWT.NONE);
		composite.setLayout(new GridLayout(2, false));
		scrolledComposite.setContent(composite);

		buildForm();
		scrolledComposite.setMinSize(composite.computeSize(SWT.DEFAULT,
				SWT.DEFAULT));
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
