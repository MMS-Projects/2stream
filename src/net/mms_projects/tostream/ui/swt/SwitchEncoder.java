package net.mms_projects.tostream.ui.swt;

import java.util.List;

import net.mms_projects.tostream.Encoder;
import net.mms_projects.tostream.EncoderManager;

import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Combo;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;

public class SwitchEncoder extends Dialog {

	protected Object result;
	protected Shell shell;
	protected final EncoderManager manager;

	/**
	 * Create the dialog.
	 * @param parent
	 * @param style
	 */
	public SwitchEncoder(Shell parent, EncoderManager manager) {
		super(parent);
		
		this.manager = manager;
		
		setText("SWT Dialog");
	}

	/**
	 * Open the dialog.
	 * @return the result
	 */
	public Object open() {
		createContents(manager);
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

	/**
	 * Create contents of the dialog.
	 */
	private void createContents(final EncoderManager manager) {
		shell = new Shell(getParent(), SWT.BORDER | SWT.CLOSE | SWT.PRIMARY_MODAL);
		shell.setSize(450, 100);
		shell.setText(getText());
		shell.setLayout(new GridLayout(2, false));
		
		Label lblEncoder = new Label(shell, SWT.NONE);
		lblEncoder.setLayoutData(new GridData(SWT.RIGHT, SWT.CENTER, false, false, 1, 1));
		lblEncoder.setText("Encoder:");
		
		final Combo combo = new Combo(shell, SWT.NONE);
		combo.setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false, 1, 1));
		List<Encoder> encoders = manager.getEncoders();
		String[] encoderNames = new String[encoders.size()];
		for (int i = 0; i < encoders.size(); i++) {
			encoderNames[i] = encoders.get(i).getClass().getName();
		}
		combo.setItems(encoderNames);
		
		Button btnSave = new Button(shell, SWT.NONE);
		btnSave.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent arg0) {
				manager.setCurrentEncoder(combo.getText());
			}
		});
		btnSave.setText("Save");
		new Label(shell, SWT.NONE);

	}

}
