package agg.gui.saveload;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JOptionPane;

import agg.editor.impl.EdGraGra;
import agg.gui.ProgressBar;
import agg.gui.event.SaveEvent;
import agg.gui.event.SaveEventListener;
import agg.util.XMLHelper;
import agg.xt_basis.GraGra;

/**
 * @version $Id: GraGraSave.java,v 1.5 2010/09/23 08:22:04 olga Exp $
 * @author $Author: olga $
 */
public class GraGraSave {

	public GraGraSave(JFrame fr) {
		this(fr, "", "");
	}

	public GraGraSave(JFrame fr, String dname, String fname) {
		this.saveListeners = new Vector<SaveEventListener>();
		this.applFrame = fr;
		this.dirName = dname;
		this.fileName = fname;

		/* create a file chooser */
		if (!this.dirName.equals(""))
			this.chooser = new JFileChooser(this.dirName);
		else
			this.chooser = new JFileChooser(System.getProperty("user.dir"));

		/* create file filters */
		this.filterXML = new AGGFileFilter("ggx", "AGG Files XML (.ggx)");
		this.chooser.addChoosableFileFilter(this.filterXML);
		/* set file filter */
		this.chooser.setFileFilter(this.filterXML);

		/* create a progress bar */
		this.bar = createProgressBar();
	}

	public javax.swing.filechooser.FileFilter getFileFilter() {
		return this.chooser.getFileFilter();
	}

	public void setFileFilter(javax.swing.filechooser.FileFilter filter) {
		this.chooser.setFileFilter(filter);
	}

	public void setExtensionFileFilter(ExtensionFileFilter filter) {
		this.chooser.setFileFilter(filter);
	}

	public boolean saveAs() {
		fireSave(new SaveEvent(this, SaveEvent.SAVE, ""));
		int returnVal = this.chooser.showSaveDialog(this.applFrame);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fileName = this.chooser.getSelectedFile().getName();
				return save();
			} else
				fireSave(new SaveEvent(this, SaveEvent.EMPTY_ERROR, ""));
		} else
			fireSave(new SaveEvent(this, SaveEvent.EMPTY_ERROR, ""));
		return false;
	}

	public boolean save() {
		if (this.gra == null) {
			fireSave(new SaveEvent(this, -1, "GraGra object is null"));
			return false;
		}

		fireSave(new SaveEvent(this, SaveEvent.SAVE, ""));
		if (this.dirName.equals(""))
			this.dirName = System.getProperty("user.dir");
		// System.out.println(File.pathSeparator+" "+File.separator);
		if (!this.dirName.endsWith(File.separator))
			this.dirName += File.separator;
		if (this.fileName.equals("")) {
			return saveAs();
		} else {
			if (this.chooser.getFileFilter() == this.filterXML) {
				if (!this.fileName.endsWith(".ggx"))
					this.fileName = this.fileName.concat(".ggx");
			}
			XMLHelper xmlh = new XMLHelper();
			// this.fileName = XMLHelper.replaceGermanSpecialCh(this.fileName);
			xmlh.addTopObject(this.gra);
			if (xmlh.save_to_xml(this.dirName + this.fileName)) {
	
				this.gra.setDirName(this.dirName);
				this.gra.setFileName(this.fileName);
				this.gra.getTypeSet().setResourcesPath(this.dirName);
				this.gra.setChanged(false);
	
				fireSave(new SaveEvent(this, SaveEvent.SAVED, this.dirName
								+ this.fileName));
			}
			else {
				fireSave(new SaveEvent(this, SaveEvent.IO_ERROR, "Write file Error!"
								, this.dirName + this.fileName));
				JOptionPane
				.showMessageDialog(this.applFrame,
						"Write file exception for the folder: "+this.dirName,
						"   IO File Error", JOptionPane.ERROR_MESSAGE);
				return false;
			}
			return true;	
		}		
	}

	public boolean saveAsBase() {
		// System.out.println(">>> GraGraSave.saveAsBase ");
		fireSave(new SaveEvent(this, SaveEvent.SAVE, ""));

		int returnVal = this.chooser.showSaveDialog(this.applFrame);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fileName = this.chooser.getSelectedFile().getName();
				return saveBase();
			} else
				fireSave(new SaveEvent(this, SaveEvent.EMPTY_ERROR, ""));
		} else
			fireSave(new SaveEvent(this, SaveEvent.EMPTY_ERROR, ""));
		return false;
	}

	public boolean saveBase() {
		if (this.basis == null) {
			return false;
		}

		if (this.dirName.equals(""))
			this.dirName = System.getProperty("user.dir");
		if (!this.dirName.endsWith(File.separator))
			this.dirName += File.separator;

		if (this.fileName.equals(""))
			return saveAsBase();
		else {
			if (this.chooser.getFileFilter() == this.filterXML) {
				if (!this.fileName.endsWith(".ggx"))
					this.fileName = this.fileName.concat(".ggx");
			}
//				if (this.fileName.endsWith(".ggx"))
				{
					XMLHelper xmlh = new XMLHelper();
					// this.fileName = XMLHelper.replaceGermanSpecialCh(this.fileName);
					xmlh.addTopObject(this.basis);
					if (xmlh.save_to_xml(this.dirName + this.fileName)) {
						fireSave(new SaveEvent(this, SaveEvent.SAVED, this.dirName
								+ this.fileName));
						return true;
					}
					else {
						fireSave(new SaveEvent(this, SaveEvent.IO_ERROR, "Write file Error!"
								, this.dirName + this.fileName));
						JOptionPane
						.showMessageDialog(this.applFrame,
								"Write file exception for the folder: "+this.dirName,
								"   IO File Error", JOptionPane.ERROR_MESSAGE);
						return false;
					}
				}
//			} // if XML
			
//			else if (this.chooser.getFileFilter() == filterAGG) {
//				// System.out.println("GraGraSave.save "+this.dirName+" "+this.fileName);
//				if (!this.fileName.endsWith(".agg"))
//					this.fileName = this.fileName.concat(".agg");
//			}
			/*
			fireSave(new SaveEvent(this, SaveEvent.PROGRESS_BEGIN, this.bar
					.getContentPanel(), ""));
			this.bar.start();

			File f = new File(this.dirName + this.fileName);
			FileOutputStream fos = null;
			ObjectOutputStream oos = null;

			int key = -1;
			try {
				fos = new FileOutputStream(f);
				oos = new ObjectOutputStream(fos);
				oos.writeObject(this.basis);
				oos.flush();
			} catch (IOException iox) {
				key = SaveEvent.IO_ERROR;
				if (iox.getMessage() == null) {
					addMsg = "";
				} else {
					addMsg = iox.getLocalizedMessage();
				}
			}

			finally {
				if (fos != null) {
					addMsg = "";
					try {
						fos.close();
						key = SaveEvent.SAVED;
					} catch (IOException ex) {
						key = SaveEvent.CLOSE_ERROR;
					}
				}
				fireSave(new SaveEvent(this, SaveEvent.PROGRESS_FINISHED, this.bar
						.getContentPanel(), ""));
				fireSave(new SaveEvent(this, key, this.dirName + this.fileName, addMsg));
				this.bar.finish();
				this.bar.quit();
			}
			*/
		}
	}

	public String getFileName() {
		return this.fileName;
	}

	public String getDirName() {
		return this.dirName;
	}

	public void setDirName(String directory) {
		if (!directory.equals("")) {
			this.dirName = directory;
			this.chooser = new JFileChooser(this.dirName);
			/* create file filters */
			this.filterXML = new AGGFileFilter("ggx", "AGG Files XML (.ggx)");
			this.chooser.addChoosableFileFilter(this.filterXML);
			/* set file filter */
			this.chooser.setFileFilter(this.filterXML);
		}
	}

	public void setGraGra(EdGraGra gragra) {
		this.gra = gragra;
	}

	public void setGraGra(EdGraGra gragra, String dirname, String filename) {
		this.gra = gragra;
		if ((dirname != null) && !dirname.equals(""))
			this.dirName = dirname;
		this.fileName = filename;
	}

	public void setBaseGraGra(GraGra gragra) {
		this.basis = gragra;
	}

	public void setBaseGraGra(GraGra gragra, String dirname, String filename) {
		// System.out.println(">>> GraGraSave.setBaseGraGra "+gragra);
		this.basis = gragra;
		if ((dirname != null) && !dirname.equals(""))
			this.dirName = dirname;
		this.dirName = dirname;
		this.fileName = filename;
	}

	public EdGraGra getGraGra() {
		return this.gra;
	}

	public GraGra getBaseGraGra() {
		return this.basis;
	}

	public void setFrame(JFrame f) {
		this.applFrame = f;
		if (this.bar != null)
			this.bar.setFrame(f);
	}

	public synchronized void addSaveEventListener(SaveEventListener l) {
		if (!this.saveListeners.contains(l))
			this.saveListeners.addElement(l);
	}

	public synchronized void removeSaveEventListener(SaveEventListener l) {
		if (this.saveListeners.contains(l))
			this.saveListeners.removeElement(l);
	}

	private void fireSave(SaveEvent e) {
		for (int i = 0; i < this.saveListeners.size(); i++) {
			this.saveListeners.elementAt(i).saveEventOccurred(e);
		}
	}

	/* create a progress bar */
	private ProgressBar createProgressBar() {
		ProgressBar pbar = new ProgressBar("Save");
		pbar.setFrame(this.applFrame);
		pbar.setLabel("Saving File ...");
		pbar.setFinishText("Saving  done");
		pbar.setToolTipText("Save Status");
		pbar.setFinishAppend(false);
		LoadSaveStatus.setMaximum(1000);
		return pbar;
	}

	private ProgressBar bar;

	private Vector<SaveEventListener> saveListeners;

	private JFrame applFrame;

	private JFileChooser chooser;

	private ExtensionFileFilter filterXML;

//	private ExtensionFileFilter filterAGG;

//	private String addMsg;

	private EdGraGra gra;

	private GraGra basis;

	private String dirName = "";

	private String fileName = "";

}
// $Log: GraGraSave.java,v $
// Revision 1.5  2010/09/23 08:22:04  olga
// tuning
//
// Revision 1.4  2010/03/08 15:43:56  olga
// code optimizing
//
// Revision 1.3  2009/06/30 09:50:27  olga
// agg.xt_basis.GraphObject: added: setObjectName(String), getObjectName()
// agg.xt_basis.Node, Arc: changed: save, load the object name
// agg.editor.impl.EdGraphObject: changed: String getTypeString() - contains object name if set
//
// workaround of Applicability of Rule Sequences and Object Flow
//
// Revision 1.2  2009/05/12 10:36:59  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.1  2008/10/29 09:04:11  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.5  2007/09/24 09:42:34  olga
// AGG transformation engine tuning
//
// Revision 1.4  2007/09/10 13:05:28  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.3 2007/06/13 08:32:53 olga
// Update: V161
//
// Revision 1.2 2005/12/21 14:49:05 olga
// GUI tuning
//
// Revision 1.1 2005/08/25 11:56:53 enrico
// *** empty log message ***
//
// Revision 1.2 2005/06/20 13:37:03 olga
// Up to now the version 1.2.8 will be prepared.
//
// Revision 1.1 2005/05/30 12:58:02 olga
// Version with Eclipse
//
// Revision 1.9 2005/04/11 13:06:13 olga
// Errors during CPA are corrected.
//
// Revision 1.8 2005/03/03 13:48:42 olga
// - Match with NACs and attr. conditions with mixed variables - error corrected
// - save/load class packages written by user
// - PACs : creating T-equivalents - improved
// - save/load matches of the rules (only one match of a rule)
// - more friendly graph/rule editor GUI
// - more syntactical checks in attr. editor
//
// Revision 1.7 2004/11/15 17:50:39 olga
// Jetzt das Kombinieren von Grammars moeglich.
//
// Revision 1.6 2003/04/14 08:21:10 olga
// Bei Save und Load ist Serializable Format rausgenommen.
//
// Revision 1.5 2003/03/20 13:35:18 olga
// Delete TypeGraph neu
//
// Revision 1.4 2003/03/05 18:24:17 komm
// sorted/optimized import statements
//
// Revision 1.3 2002/12/05 13:33:20 olga
// GUI Verbesserung.
//
// Revision 1.2 2002/09/05 16:16:53 olga
// Arbeit an GUI
//
// Revision 1.1.1.1 2002/07/11 12:17:10 olga
// Imported sources
//
// Revision 1.14 2001/09/24 16:39:35 olga
// Korrektur an LayerFunction und LayerGUI.
//
// Revision 1.13 2001/07/30 13:16:15 olga
// Kleine GUI Korrektur.
//
// Revision 1.12 2001/03/14 17:31:45 olga
// Korrektur wegen Layout und XML
//
// Revision 1.11 2001/03/08 11:00:05 olga
// Das ist Stand nach der AGG GUI Reimplementierung
// und Parser Anbindung.
//
// Revision 1.10 2000/12/21 10:53:56 olga
// XML save / load Format fuer basis gragra eingefuegt.
//
// Revision 1.9 2000/12/21 09:49:04 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.8 2000/12/07 14:23:37 matzmich
// XML-Kram
// Man beachte: xerces (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird
// jetzt im CLASSPATH benoetigt.
//
// Revision 1.7.4.3 2000/12/04 13:26:03 olga
// Erste Stufe der GUI Reimplementierung abgeschlossen:
// - AGGAppl.java optimiert
// - Print eingebaut (GraGraPrint.java)
// - GraGraTreeView.java, GraGraEditor.java optimiert
// - Event eingebaut
// - GraTra umgestellt
//
// Revision 1.7.4.2 2000/11/09 17:54:42 olga
// Fehlerbeseitigt im TypeEditor und bei den Kanten.
//
// Revision 1.7.4.1 2000/11/06 09:32:47 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.7 2000/07/19 12:31:22 olga
// Save Dialog geaendert.
//
// Revision 1.6 2000/01/04 13:52:18 shultzke
// Progressbalken fuer das Laden und Speichern
// integriert.
//
