package agg.gui.saveload;

import java.io.File;
import java.util.Vector;

import javax.swing.filechooser.FileFilter;

/**
 * @version $Id: ExtensionFileFilter.java,v 1.2 2010/09/20 14:29:09 olga Exp $
 * @author $Author: olga $
 */
public abstract class ExtensionFileFilter extends FileFilter {

	protected Vector<String> extensions;

	protected String extension;

	protected String description;

	/**
	 * Creates a new file filter.
	 */
	public ExtensionFileFilter() {
		this.extension = "";
		this.description = "";
		this.extensions = new Vector<String>(2);
	}

	/**
	 * Creates a new file filter that accepts the given file type.
	 */
	public ExtensionFileFilter(String extension, String description) {
		this();
		this.extension = extension;
		this.description = description;
		this.extensions.addElement(extension);
	}

	/**
	 * Return true if this file should be shown in the directory pane, false if
	 * it shouldn't.
	 * 
	 * @see javax.swing.filechooser.FileFilter#accept
	 */
	public boolean accept(File f) {
		if (f != null) {
			if (f.isDirectory()) {
				return true;
			}
			for (int i = 0; i < this.extensions.size(); i++) {
				String ext = this.extensions.elementAt(i);
				if (f.getName().toLowerCase().endsWith(ext))
					return true;
			}
			// return f.getName().toLowerCase().endsWith(extension);
		}
		return false;
	}

	/** Adds a new file extension. */
	public void addExtension(String ext) {
		this.extensions.addElement(ext);
	}

	/**
	 * Returns the human readable description of this filter.
	 */
	public String getDescription() {
		return this.description;
	}

	/**
	 * Sets the human readable description of this filter.
	 */
	public void setDescription(String description) {
		this.description = description;
	}

	/** Returns the file extension */
	public String getExtension() {
		return this.extension;
	}

	/** Returns file extensions */
	public String getExtensions() {
		return this.extensions.toString();
	}
}
/*
 * $Log: ExtensionFileFilter.java,v $
 * Revision 1.2  2010/09/20 14:29:09  olga
 * tuning
 *
 * Revision 1.1  2008/10/29 09:04:11  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.2  2007/09/10 13:05:25  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.1 2005/08/25 11:56:53 enrico
 * *** empty log message ***
 * 
 * Revision 1.1 2005/05/30 12:58:02 olga Version with Eclipse
 * 
 * Revision 1.3 2004/04/15 10:49:47 olga Kommentare
 * 
 * Revision 1.2 2003/03/05 18:24:16 komm sorted/optimized import statements
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:10 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:00:02 olga Das ist Stand nach der AGG GUI
 * Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.4.2 2000/07/26 11:04:26 shultzke FileFilter hinzugefuegt
 * 
 */
