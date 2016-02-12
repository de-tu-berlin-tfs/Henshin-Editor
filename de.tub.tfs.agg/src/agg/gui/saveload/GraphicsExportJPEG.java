package agg.gui.saveload;

import java.io.File;
import java.io.FileOutputStream;
import javax.swing.JFileChooser;
import javax.swing.JComponent;
import java.awt.image.BufferedImage;
import java.awt.Graphics2D;
import java.awt.Component;


/*
import com.sun.image.codec.jpeg.JPEGCodec;
import com.sun.image.codec.jpeg.JPEGEncodeParam;
import com.sun.image.codec.jpeg.JPEGImageEncoder;
*/

/**
 * GraphExportJPG save a graph image into JPEG data stream and writes the JPEG
 * stream into a file.
 */
public class GraphicsExportJPEG {

	private JFileChooser folderchooser;

	private ExtensionFileFilter filterJPG;

	private String jpgPath = "";

	private Component parent;

	private float quality = 1.0f;

	private boolean cancelled = false;

	public GraphicsExportJPEG(Component parent) {
		super();
		this.parent = parent;
		/* create a file chooser */
		this.folderchooser = new JFileChooser(System.getProperty("user.dir"));
		/* create file filters */
		this.filterJPG = new AGGFileFilter("jpg", "JPEG Files (.jpg)");
		this.folderchooser.addChoosableFileFilter(this.filterJPG);
		/* set file filter */
		this.folderchooser.setFileFilter(this.filterJPG);
	}

	public void setDirectory(String directory) {
		this.jpgPath = directory;
		if (!this.jpgPath.equals("")) {
			this.folderchooser = new JFileChooser(this.jpgPath);
			this.folderchooser.addChoosableFileFilter(this.filterJPG);
			/* set file filter */
			this.folderchooser.setFileFilter(this.filterJPG);
		}
	}

	public String getDirectory() {
		return this.jpgPath;
	}

	public boolean isCancelled() {
		return this.cancelled;
	}

	public String getDirectoryForJPEGs(Component parentComp) {
		this.cancelled = false;
		this.folderchooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		int retval = this.folderchooser.showSaveDialog(parentComp);
		if (retval == JFileChooser.CANCEL_OPTION) {
			this.cancelled = true;
			// System.out.println("Directory name for saving JPEGs undefined.");
			return null;
		}
		if (retval == JFileChooser.APPROVE_OPTION)
			this.jpgPath = this.folderchooser.getSelectedFile().getAbsolutePath();
		// System.out.println("Directory name for saving JPEGs: "+this.jpgPath);
		return this.jpgPath;
	}

	public void setQuality(float q) {
		this.quality = q;
	}

	public boolean save(JComponent graphPanel) {
		int retval = this.folderchooser.showSaveDialog(this.parent);
		if (retval == JFileChooser.APPROVE_OPTION) {
			if (this.folderchooser.getSelectedFile().isDirectory())
				this.jpgPath = this.folderchooser.getSelectedFile().getName();
			else if (this.folderchooser.getSelectedFile().isFile()) {
				this.jpgPath = this.folderchooser.getSelectedFile().getParent();
				// System.out.println("path:
				// "+this.folderchooser.getSelectedFile().getPath()+" parent:
				// "+this.folderchooser.getSelectedFile().getParent());
			} else
				this.jpgPath = this.folderchooser.getSelectedFile().getParent();
		} else {
			this.jpgPath = null;
			return false;
		}

		if (this.jpgPath.equals(""))
			this.jpgPath = ".";

		String filename = this.folderchooser.getSelectedFile().getName();
		if (!filename.endsWith(".jpg"))
			filename = filename.concat(".jpg");

		// System.out.println("file name: "+this.jpgPath+File.separator+filename );
		String name = this.jpgPath + File.separator + filename;
		// File testFile = new File(name);
		// if(testFile.exists()){
		// Object[] options = { "Yes", "No" };
		// int answer = JOptionPane.showOptionDialog(null,
		// "File "+name+" exists!"
		// +"\nDo you want to overwrite it?",
		// "Save to JPEG",
		// JOptionPane.DEFAULT_OPTION,
		// JOptionPane.QUESTION_MESSAGE,
		// null, options, options[1]);
		// if (answer == 0){
		// paintToJpg(graphPanel, name);
		// }
		// }
		// else
		
		return paintToJpg(graphPanel, name);
	}

	public boolean save(JComponent graphPanel, String filename) {
		if (!filename.endsWith(".jpg")) {
			return paintToJpg(graphPanel, filename.concat(".jpg"));				
		}
		
		return paintToJpg(graphPanel, filename);		
	}

	private boolean paintToJpg(JComponent graphPanel, String filename) {
		// JPanel graphpanel = graphPanel;
		JComponent graphpanel = graphPanel;
		// write the image data into buffered image
		BufferedImage image = new BufferedImage(graphpanel.getWidth(),
				graphpanel.getHeight(), BufferedImage.TYPE_INT_RGB);
		Graphics2D imageg = image.createGraphics();
		graphpanel.paint(imageg);
		// write the BufferedImage into JPEG stream
		try {
			/*
			FileOutputStream fos = new FileOutputStream(new File(filename));
			JPEGImageEncoder encoder = JPEGCodec.createJPEGEncoder(fos);
//			encoder = JPEGCodec.createJPEGEncoder(fos, JPEGCodec.getDefaultJPEGEncodeParam(image));
			if (encoder != null) {
				JPEGEncodeParam param = encoder.getDefaultJPEGEncodeParam(image);
				param.setQuality(this.quality, false); // 0.0 - 1.0
				encoder.setJPEGEncodeParam(param);
				encoder.encode(image);
				fos.flush();
				fos.close();
				*/
				return true;
				/*
			}
			fos.flush();
			fos.close();
		*/
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return false;
	}

}