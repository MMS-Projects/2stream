package net.mms_projects.tostream.ui.swt;

import net.mms_projects.tostream.Messages;
import net.mms_projects.tostream.ToStream;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Dialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Shell;

public class AboutDialog extends Dialog {

	protected Object result;
	protected Shell shell;

	/**
	 * Create the dialog.
	 * 
	 * @param parent
	 * @param style
	 */
	public AboutDialog(Shell parent) {
		super(parent);
		setText("About " + ToStream.getApplicationName());
	}

	/**
	 * Create contents of the dialog.
	 */
	private void createContents() {
		shell = new Shell(getParent(), SWT.CLOSE | SWT.TITLE
				| SWT.PRIMARY_MODAL);
		shell.setSize(300, 80);
		shell.setText(getText());
		shell.setLayout(new GridLayout(2, false));

		StyledText infoApplicatioName = new StyledText(shell, SWT.NONE);
		infoApplicatioName.setEnabled(false);
		infoApplicatioName.setEditable(false);
		infoApplicatioName.setLayoutData(new GridData(SWT.FILL, SWT.FILL, true,
				true, 1, 1));
		infoApplicatioName.setText(ToStream.getApplicationName());
		infoApplicatioName.setBackground(shell.getBackground());
		new Label(shell, SWT.NONE);

		Label labelVersion = new Label(shell, SWT.NONE);
		labelVersion.setText(Messages.getString("about.version"));

		Label infoVersion = new Label(shell, SWT.NONE);
		infoVersion.setText(ToStream.getVersion());

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
