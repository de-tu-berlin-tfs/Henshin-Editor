package agg.gui.termination;

import java.net.URL;

import agg.gui.help.HtmlPane;

public class HtmlPage {

	private HtmlPane html;

	/* The initial width and height of the frame */
	// private int WIDTH = 300;
	// private int HEIGHT = 300;
	private final String HELPPATH = "agg/gui/help/";

	public HtmlPage(String htmlFileName) {
		super();
		URL url = ClassLoader.getSystemClassLoader().getResource(this.HELPPATH + htmlFileName);
		if (url != null) {		
			this.html = new HtmlPane(url);
		} else {
			this.html = new HtmlPane("");
		}
	}

	public void setPage(String url) throws java.io.IOException {
		this.html.setPage(url);
	}

	public String toString() {
		return this.html.toString();
	}

	public HtmlPane getHTML() {
		return this.html;
	}
}
