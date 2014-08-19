package agg.util;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.PrintStream;

public class RedirectSystemOut {

	protected PrintStream out, err;

	protected FileOutputStream fos;

	protected File outFile;

	// protected ByteArrayOutputStream redirect;
	protected PrintStream redirectOut;

	protected boolean isOutput = true;

	public RedirectSystemOut() {
	}

	public void redirectToFile(String outFileName) {
		this.outFile = new File(outFileName);
		this.out = System.out;
		this.err = System.err;
		try {
			this.fos = new FileOutputStream(this.outFile);
			this.redirectOut = new PrintStream(this.fos);
			System.setOut(this.redirectOut);
			System.setErr(this.redirectOut);
		} catch (FileNotFoundException ex) {
			System.out.println("RedirectSystemOut   FAILED!");
		}
	}

	public void restoreOutputStream() {
		if (this.fos != null) {
			try {
				this.fos.close();
			} catch (IOException ex) {
			}
		}
		System.setOut(this.out);
		System.setErr(this.err);
		this.redirectOut = null;
	}

}
