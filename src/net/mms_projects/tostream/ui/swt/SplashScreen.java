package net.mms_projects.tostream.ui.swt;

import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Monitor;
import org.eclipse.swt.widgets.Shell;
import org.eclipse.swt.widgets.ProgressBar;
import org.eclipse.swt.layout.FillLayout;
import org.eclipse.wb.swt.SWTResourceManager;
import org.eclipse.swt.custom.StyledText;
import org.eclipse.swt.graphics.Font;
import org.eclipse.swt.graphics.FontData;
import org.eclipse.swt.graphics.Rectangle;

public class SplashScreen extends Shell {

	ProgressBar progressBar;
	StyledText textStatus;
	
	/**
	 * Launch the application.
	 * @param args
	 */
	public static void main(String args[]) {
		try {
			Display display = Display.getDefault();
			SplashScreen shell = new SplashScreen(display);
			shell.open();
			shell.layout();
			while (!shell.isDisposed()) {
				if (!display.readAndDispatch()) {
					display.sleep();
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**
	 * Create the shell.
	 * @param display
	 */
	public SplashScreen(Display display) {
		super(display, SWT.NO_TRIM);
		setBackground(SWTResourceManager.getColor(SWT.COLOR_DARK_RED));
		setLayout(null);
		
		progressBar = new ProgressBar(this, SWT.NONE);
		progressBar.setBackground(getBackground());
		progressBar.setBounds(10, 196, 430, 69);
		
		StyledText textLogo = new StyledText(this, SWT.NONE);
		textLogo.setForeground(SWTResourceManager.getColor(SWT.COLOR_WHITE));
		FontData[] fD = textLogo.getFont().getFontData();
		fD[0].setHeight(25);
		textLogo.setFont(new Font(display, fD[0]));
		textLogo.setText("2STREAM");
		textLogo.setAlwaysShowScrollBars(false);
		textLogo.setDoubleClickEnabled(false);
		textLogo.setEnabled(false);
		textLogo.setEditable(false);
		textLogo.setBackground(getBackground());
		textLogo.setBounds(10, 10, 430, 180);
		
		textStatus = new StyledText(this, SWT.NONE);
		textStatus.setBackground(getBackground());
		textStatus.setEditable(false);
		textStatus.setEnabled(false);
		textStatus.setBounds(10, 271, 430, 19);
		
		createContents();
	}

	public void setProgress(int progress) {
		progressBar.setSelection(progress);
	}
	
	public void setProgress(int progress, String text) {
		progressBar.setSelection(progress);
		textStatus.setText(text);
	}
	
	public void centerShell() {
		
	}
	
	/**
	 * Create contents of the shell.
	 */
	protected void createContents() {
		setText("SWT Application");
		setSize(450, 300);

		//center the dialog screen to the monitor
		Monitor primary = Display.getDefault().getPrimaryMonitor ();
		Rectangle monitor = primary.getBounds();
		int x = monitor.x + (monitor.width - getBounds().width) / 2;
		int y = monitor.y + (monitor.height - getBounds().height) / 2;
		setLocation(x, y);
	}

	@Override
	protected void checkSubclass() {
		// Disable the check that prevents subclassing of SWT components
	}
}
