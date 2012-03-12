package agg.gui.saveload;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InvalidClassException;
import java.io.ObjectInputStream;
import java.io.OptionalDataException;
import java.io.StreamCorruptedException;
import java.util.Vector;
import javax.swing.JOptionPane;
import javax.swing.JFileChooser;
import javax.swing.JFrame;

import agg.editor.impl.EdGraGra;
import agg.gui.AGGAppl;
import agg.gui.ProgressBar;
import agg.gui.event.LoadEvent;
import agg.gui.event.LoadEventListener;
import agg.util.XMLHelper;
//import agg.xt_basis.BaseFactory;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;

/**
 * @version $Id: GraGraLoad.java,v 1.4 2010/09/23 08:22:04 olga Exp $
 * @author $Author: olga $
 */
public class GraGraLoad {

	final private Vector<LoadEventListener> loadListeners = new Vector<LoadEventListener>();

	public GraGraLoad(JFrame fr) {
		this(fr, "", "");
	}

	public GraGraLoad(JFrame fr, String dname, String fname) {
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
		
		/* set a file filter */
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

	public void load() {
		fireLoad(new LoadEvent(this, LoadEvent.LOAD, ""));
		this.gra = null;
		this.basis = null;
		int returnVal = this.chooser.showOpenDialog(this.applFrame);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fileName = this.chooser.getSelectedFile().getName();
				if (!this.dirName.endsWith(File.separator))
					this.dirName += File.separator;
				
				reload();
			} else
				fireLoad(new LoadEvent(this, LoadEvent.EMPTY_ERROR, ""));
		} else if (returnVal == JFileChooser.CANCEL_OPTION) {
			this.canceled = true;
		}		
	}
	
	public boolean isCanceled() {
		return this.canceled;
	}

	public void reload(String dirname, String filename) {
		this.dirName = dirname;
		this.fileName = filename;
		reload();
	}

	public void reload() {
		AGGAppl.showFileLoadLogo();

		if (!this.fileName.endsWith(".ggx")
				&& this.chooser.getFileFilter() == this.filterXML)
			this.fileName = this.fileName + ".ggx";

		if (this.fileName.endsWith(".ggx")
				|| this.chooser.getFileFilter() != this.filterXML) {
			
			File f = new File(this.dirName + this.fileName);
			if (f.exists()) {
				fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_BEGIN, ""));

				XMLHelper h = new XMLHelper();
				/*
				 * if(XMLHelper.hasGermanSpecialCh(this.fileName)){
				 * JOptionPane.showMessageDialog(null, "\t"+this.fileName +"\n Read
				 * file name exception occurred! " +"\n Maybe the German
				 * characters like ä, ö, ü, ß or space were used. " +"\n Please
				 * rename the file " +"\nand try again.", "Cannot load file",
				 * JOptionPane.WARNING_MESSAGE); this.gra = null; return; }
				 */

				
				if ((this.dirName.equals("") && h.read_from_xml(this.fileName))				
						|| h.read_from_xml(this.dirName + this.fileName)) {

					long time0 = System.currentTimeMillis();
					
					GraGra bgra = BaseFactory.theFactory().createGraGra(false);				
					h.getTopObject(bgra);
					
					this.gra = new EdGraGra(bgra);
					this.gra.setDirName(this.dirName);
					this.gra.setFileName(this.fileName);
					this.gra.getTypeSet().setResourcesPath(this.dirName);
					
					h.enrichObject(this.gra);
					
					System.out.println("Grammar  <" + this.gra.getName()
							+ ">  loaded in  "
							+ (System.currentTimeMillis() - time0) + "ms");
	
					fireLoad(new LoadEvent(this, LoadEvent.LOADED, this.dirName
							+ this.fileName));
					fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_FINISHED, ""));
				}
			} else {
				JOptionPane.showMessageDialog(null, "File  \"" + this.dirName
						+ this.fileName + "\"  does not exist!", "Cannot load file",
						JOptionPane.WARNING_MESSAGE);
				System.out.println("agg.gui.GraGraLoad:  File  \"" + this.dirName
						+ this.fileName + "\"  does not exist!");
			}
		} // if XML
		else {
			if (!this.dirName.equals("") && !this.fileName.equals("")) {
				fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_BEGIN, this.bar
						.getContentPanel(), ""));
				this.bar.start();

				int key = -1;
				this.addMsg = "";
				try {
					File f = new File(this.dirName + this.fileName);
					long datei = f.length();
					double multi = (datei + datei * 0.04) / 16000.0;
					LoadSaveStatus.setMaximum((int) (100 * multi));
					FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);
					this.gra = (EdGraGra) ois.readObject();
					this.gra.setDirName(this.dirName);
					this.gra.setFileName(this.fileName);
					this.gra.getTypeSet().setResourcesPath(this.dirName);
					if (!this.gra.getTypeSet().basisTypeReprComplete())
						this.gra.getTypeSet().setAdditionalReprOfBasisType();
					this.gra.update();

					fis.close();
					key = LoadEvent.LOADED;
				} // try
				catch (FileNotFoundException fnfx) {
					this.gra = null;
					key = LoadEvent.CLASS_NOT_FOUND_ERROR;
					this.addMsg = "";
				} catch (SecurityException sx) {
					this.gra = null;
					key = LoadEvent.SECURITY_ERROR;
					if (sx.getMessage() == null) {
						this.addMsg = "";
					} else {
						this.addMsg = sx.getLocalizedMessage();
					}
				} catch (StreamCorruptedException scx) {
					this.gra = null;
					key = LoadEvent.STREAM_ERROR;
				} catch (ClassNotFoundException cnfx) {
					this.gra = null;
					key = LoadEvent.CLASS_NOT_FOUND_ERROR;
					this.addMsg = cnfx.getLocalizedMessage();
				} catch (InvalidClassException icx) {
					this.gra = null;
					key = LoadEvent.INVALID_CLASS_ERROR;
					this.addMsg = icx.getLocalizedMessage();
				} catch (OptionalDataException odx) { // what kind exception
					// is this?
					this.gra = null;
					key = LoadEvent.DATA_ERROR;
					this.addMsg = odx.getLocalizedMessage();
				} catch (IOException iox) {
					this.gra = null;
					key = LoadEvent.IO_ERROR;
					if (iox.getLocalizedMessage() == null) {
						this.addMsg = "";
					} else {
						this.addMsg = iox.getLocalizedMessage();
					}
				} catch (StackOverflowError sox) {
					this.gra = null;
					key = LoadEvent.STACK_OVERFLOW_ERROR;
					if (sox.getLocalizedMessage() == null) {
						this.addMsg = "";
					} else {
						this.addMsg = sox.getLocalizedMessage();
					}
				} finally {
					fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_FINISHED,
							this.bar.getContentPanel(), ""));
					fireLoad(new LoadEvent(this, key, this.dirName + this.fileName,
							this.addMsg));
					this.bar.finish();
					this.bar.quit();
				}
			} // if (!this.dirName.equals("") && !this.fileName.equals("")
		} // else
	}

	public void loadBase() {
		fireLoad(new LoadEvent(this, LoadEvent.LOAD, ""));

		int returnVal = this.chooser.showOpenDialog(this.applFrame);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fileName = this.chooser.getSelectedFile().getName();
				if (!this.dirName.endsWith(File.separator))
					this.dirName += File.separator;
				reloadBase();
			} else
				fireLoad(new LoadEvent(this, LoadEvent.EMPTY_ERROR, ""));
		} else
			fireLoad(new LoadEvent(this, LoadEvent.EMPTY_ERROR, ""));
	}

	public void reloadBase() {
		AGGAppl.showFileLoadLogo();

		if (this.fileName.endsWith(".ggx")
				&& this.chooser.getFileFilter() == this.filterXML) {
			fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_BEGIN, ""));

			XMLHelper h = new XMLHelper();
			/*
			 * if(XMLHelper.hasGermanSpecialCh(this.fileName)){
			 * JOptionPane.showMessageDialog(null, "\t"+this.fileName +"\n Read file
			 * name exception occurred! " +"\n Maybe the German characters like
			 * ä, ö, ü, ß or space were used. " +"\n Please rename the file "
			 * +"\nand try again.", "Cannot load file",
			 * JOptionPane.WARNING_MESSAGE); this.gra = null; return; }
			 */
			if ((this.dirName.equals("") && h.read_from_xml(this.fileName))
					|| h.read_from_xml(this.dirName + this.fileName)) {
			
	//			this.basis = BaseFactory.theFactory().createGraGra();
				
				this.basis = new GraGra(false);			
				h.getTopObject(this.basis);
	
				fireLoad(new LoadEvent(this, LoadEvent.LOADED, this.dirName + this.fileName));
				// fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_FINISHED, ""));
			}
		} // if XML
		else {
			if (!this.dirName.equals("") && !this.fileName.equals("")) {
				fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_BEGIN, this.bar
						.getContentPanel(), ""));
				this.bar.start();

				int key = -1;
				this.addMsg = "";
				try {
					File f = new File(this.dirName + this.fileName);
					long datei = f.length();
					double multi = (datei + datei * 0.04) / 16000.0;
					LoadSaveStatus.setMaximum((int) (100 * multi));

					FileInputStream fis = new FileInputStream(f);
					ObjectInputStream ois = new ObjectInputStream(fis);
					this.basis = (GraGra) ois.readObject();

					fis.close();
					key = LoadEvent.LOADED;
				} catch (FileNotFoundException fnfx) {
					key = LoadEvent.CLASS_NOT_FOUND_ERROR;
					this.addMsg = "";
				} catch (SecurityException sx) {
					key = LoadEvent.SECURITY_ERROR;
					if (sx.getMessage() == null) {
						this.addMsg = "";
					} else {
						this.addMsg = sx.getLocalizedMessage();
					}
				} catch (StreamCorruptedException scx) {
					key = LoadEvent.STREAM_ERROR;
				} catch (ClassNotFoundException cnfx) {
					key = LoadEvent.CLASS_NOT_FOUND_ERROR;
					this.addMsg = cnfx.getLocalizedMessage();
				} catch (InvalidClassException icx) {
					key = LoadEvent.INVALID_CLASS_ERROR;
					this.addMsg = icx.getLocalizedMessage();
				} catch (OptionalDataException odx) {
					key = LoadEvent.DATA_ERROR;
					this.addMsg = odx.getLocalizedMessage();
				} catch (IOException iox) {
					key = LoadEvent.IO_ERROR;
					if (iox.getMessage() == null) {
						this.addMsg = "";
					} else {
						this.addMsg = iox.getLocalizedMessage();
					}
				} catch (StackOverflowError sox) {
					key = LoadEvent.STACK_OVERFLOW_ERROR;
					if (sox.getMessage() == null) {
						this.addMsg = "";
					} else {
						this.addMsg = sox.getLocalizedMessage();
					}
				} finally {
					fireLoad(new LoadEvent(this, LoadEvent.PROGRESS_FINISHED,
							this.bar.getContentPanel(), ""));
					fireLoad(new LoadEvent(this, key, this.dirName + this.fileName,
							this.addMsg));
					this.bar.finish();
					this.bar.quit();
				}
			}
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
			
			/* set a file filter */
			this.chooser.setFileFilter(this.filterXML);
		}
	}

	public EdGraGra getGraGra() {
		return this.gra;
	}

	public GraGra getBaseGraGra() {
		return this.basis;
	}

	public void setGraGra(EdGraGra gragra, String dirname, String filename) {
		this.gra = gragra;
		if ((dirname != null) && !dirname.equals("")) {
			if (dirname.endsWith(File.separator))
				this.dirName = dirname;
			else
				this.dirName = dirname.concat(File.separator);
		}
		this.fileName = filename;
	}

	public void setBaseGraGra(GraGra gragra) {
		this.basis = gragra;
	}

	public void setBaseGraGra(GraGra gragra, String dirname, String filename) {
		this.basis = gragra;
		if ((dirname != null) && !dirname.equals("")) {
			if (dirname.endsWith(File.separator))
				this.dirName = dirname;
			else
				this.dirName = dirname.concat(File.separator);
		}
		this.fileName = filename;
	}

	public void setFrame(JFrame f) {
		this.applFrame = f;
		if (this.bar != null)
			this.bar.setFrame(f);
	}

	public synchronized void addLoadEventListener(LoadEventListener l) {
		if (!this.loadListeners.contains(l))
			this.loadListeners.addElement(l);
	}

	public synchronized void removeLoadEventListener(LoadEventListener l) {
		if (this.loadListeners.contains(l))
			this.loadListeners.removeElement(l);
	}

	private void fireLoad(LoadEvent e) {
		for (int i = 0; i < this.loadListeners.size(); i++) {
			this.loadListeners.elementAt(i).loadEventOccurred(e);
		}
	}

	/* create a progress bar */
	private ProgressBar createProgressBar() {
		ProgressBar pbar = new ProgressBar("Save");
		// pbar.setFrame(this.applFrame);
		pbar.setLabel("Saving Files ...");
		pbar.setFinishText("Saving Files done");
		pbar.setToolTipText("Save Status");
		pbar.setFinishAppend(false);
		return pbar;
	}

	private ProgressBar bar;

	private JFrame applFrame;

	private JFileChooser chooser;
	 
	private boolean canceled;
	
	private ExtensionFileFilter filterXML; 

	private String addMsg;

	private EdGraGra gra;

	private GraGra basis;

	private String dirName = "";

	private String fileName = "";

}

// $Log: GraGraLoad.java,v $
// Revision 1.4  2010/09/23 08:22:04  olga
// tuning
//
// Revision 1.3  2010/01/28 19:33:01  olga
// reloadGraGra - bug fixed
//
// Revision 1.2  2009/05/12 10:36:59  olga
// CPA: bug fixed
// Applicability of Rule Seq. : bug fixed
//
// Revision 1.1  2008/10/29 09:04:11  olga
// new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
//
// Revision 1.15  2008/10/22 14:07:52  olga
// GUI, ARS and CPA  tuning
//
// Revision 1.14  2008/10/20 07:42:32  olga
// added : set priority of transformation engine to improve synchronization
// of trafo and graph visualization
//
// Revision 1.13  2008/10/02 16:40:55  olga
// - Reset host graph - bug fixed ,
// - improved mouse event handling,
// - Applicability of rule sequences:  save and load grammar : layout data will be saved
// and loaded, too
//
// Revision 1.12  2008/04/07 09:36:52  olga
// Code tuning: refactoring + profiling
// Extension: CPA - two new options added
//
// Revision 1.11  2007/09/24 09:42:35  olga
// AGG transformation engine tuning
//
// Revision 1.10  2007/09/10 13:05:22  olga
// In this update:
// - package xerces2.5.0 is not used anymore;
// - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
// - bugs fixed in:  usage of PACs in rules;  match completion;
// 	usage of static method calls in attr. conditions
// - graph editing: added some new features
//
// Revision 1.9 2007/07/09 08:00:26 olga
// GUI tuning
//
// Revision 1.8 2007/06/13 08:32:53 olga
// Update: V161
//
// Revision 1.7 2007/04/19 14:50:03 olga
// Loading grammar - tuning
//
// Revision 1.6 2007/04/19 07:52:39 olga
// Tuning of: Undo/Redo, Graph layouter, loading grammars
//
// Revision 1.5 2007/03/28 10:00:44 olga
// - extensive changes of Node/Edge Type Editor,
// - first Undo implementation for graphs and Node/edge Type editing and
// transformation,
// - new / reimplemented options for layered transformation, for graph layouter
// - enable / disable for NACs, attr conditions, formula
// - GUI tuning
//
// Revision 1.4 2006/11/15 09:00:32 olga
// Transform with input parameter : bug fixed
//
// Revision 1.3 2006/05/29 07:59:42 olga
// GUI, undo delete - tuning.
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
// Revision 1.12 2005/05/11 12:34:55 olga
// Error fixed: Transform options after an imported graph has been integrated in
// the current grammar as the host graph
//
// Revision 1.11 2005/03/03 13:48:42 olga
// - Match with NACs and attr. conditions with mixed variables - error corrected
// - save/load class packages written by user
// - PACs : creating T-equivalents - improved
// - save/load matches of the rules (only one match of a rule)
// - more friendly graph/rule editor GUI
// - more syntactical checks in attr. editor
//
// Revision 1.10 2004/05/19 15:41:34 olga
// Comments
//
// Revision 1.9 2003/04/14 08:21:10 olga
// Bei Save und Load ist Serializable Format rausgenommen.
//
// Revision 1.8 2003/03/20 13:35:17 olga
// Delete TypeGraph neu
//
// Revision 1.7 2003/03/17 15:33:28 olga
// GUI Aenderungen
//
// Revision 1.6 2003/03/05 18:24:20 komm
// sorted/optimized import statements
//
// Revision 1.5 2002/11/25 15:04:39 olga
// Arbeit an dem TypeEditor.
//
// Revision 1.4 2002/11/11 09:43:15 olga
// Fehler im TypeEditor beseitigt
//
// Revision 1.3 2002/10/02 18:30:48 olga
// XXX
//
// Revision 1.2 2002/09/05 16:16:53 olga
// Arbeit an GUI
//
// Revision 1.1.1.1 2002/07/11 12:17:10 olga
// Imported sources
//
// Revision 1.13 2001/07/30 13:16:14 olga
// Kleine GUI Korrektur.
//
// Revision 1.12 2001/03/14 17:31:45 olga
// Korrektur wegen Layout und XML
//
// Revision 1.11 2001/03/12 13:35:13 olga
// *** empty log message ***
//
// Revision 1.10 2001/03/08 11:00:04 olga
// Das ist Stand nach der AGG GUI Reimplementierung
// und Parser Anbindung.
//
// Revision 1.9 2000/12/21 10:53:55 olga
// XML save / load Format fuer basis gragra eingefuegt.
//
// Revision 1.8 2000/12/21 09:49:03 olga
// In dieser Version wurden XML und GUI Reimplementierung zusammen gefuehrt.
//
// Revision 1.7 2000/12/07 14:23:37 matzmich
// XML-Kram
// Man beachte: xerces (/home/tfs/gragra/AGG/LIB/Xerces/xerces.jar) wird
// jetzt im CLASSPATH benoetigt.
//
// Revision 1.6.4.3 2000/12/04 13:26:02 olga
// Erste Stufe der GUI Reimplementierung abgeschlossen:
// - AGGAppl.java optimiert
// - Print eingebaut (GraGraPrint.java)
// - GraGraTreeView.java, GraGraEditor.java optimiert
// - Event eingebaut
// - GraTra umgestellt
//
// Revision 1.6.4.2 2000/11/09 17:54:42 olga
// Fehlerbeseitigt im TypeEditor und bei den Kanten.
//
// Revision 1.6.4.1 2000/11/06 09:32:46 olga
// Erste Version fuer neue GUI (Branch reimpl)
//
// Revision 1.6 2000/07/19 12:32:00 olga
// Load Dialog geaendert.
//
// Revision 1.5 2000/01/04 13:52:09 shultzke
// Progressbalken fuer das Laden und Speichern
// integriert.
//
