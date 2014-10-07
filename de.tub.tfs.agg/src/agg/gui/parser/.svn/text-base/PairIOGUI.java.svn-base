package agg.gui.parser;

import java.io.File;
import java.util.Vector;

import javax.swing.JFileChooser;
import javax.swing.JFrame;

import agg.gui.cpa.ConflictsDependenciesContainerSaveLoad;
import agg.gui.saveload.ExtensionFileFilter;
import agg.parser.PairContainer;
import agg.parser.CriticalPair;
import agg.parser.ConflictsDependenciesContainer;
import agg.util.XMLHelper;

/**
 * This class controlls the load and save process. The choice of xml format for
 * saving needs some preparation for the load process. For the load process the
 * creation of an empty object of the correct type is needed.
 * 
 * @author $Author: olga $
 * @version $Id: PairIOGUI.java,v 1.14 2010/09/23 08:20:54 olga Exp $
 */
public class PairIOGUI {

	private JFrame frame;

	private JFileChooser chooser;

	private PairContainer pc;

	private ConflictsDependenciesContainer cdContainer;
	private ConflictsDependenciesContainerSaveLoad cdContainerSaveLoad;
	
	private String directory = "";

//	private String currentDir = "";

	private String fileName = "";

	private PairFileFilter filter;

	private boolean isSaved;

	private boolean combined = false;

	/**
	 * Creates a new object. The method decides if user interaction is needed.
	 * 
	 * @param parent
	 *            The parent frame for this gui.
	 */
	public PairIOGUI(JFrame parent) {
		this.frame = parent;
		this.pc = null;
		this.cdContainer = null;
		this.combined = false;
		this.chooser = new JFileChooser(System.getProperty("user.dir"));
		this.filter = new PairFileFilter();
		this.chooser.addChoosableFileFilter(this.filter);
		this.chooser.setFileFilter(this.filter);
	}

	/**
	 * Creates a new object. The method decides if user interaction is needed.
	 * 
	 * @param parent
	 *            The parent frame for this gui.
	 * @param pairs
	 *            The critical pair container will be loaded or saved.
	 */
	public PairIOGUI(JFrame parent, PairContainer pairs) {
		this.frame = parent;
		this.pc = pairs;
		this.cdContainer = null;
		this.combined = false;
		this.chooser = new JFileChooser(System.getProperty("user.dir"));
		this.filter = new PairFileFilter();
		this.chooser.addChoosableFileFilter(this.filter);
		this.chooser.setFileFilter(this.filter);
	}

	public PairIOGUI(JFrame parent, PairContainer pairs, String dname,
			String fname) {
		this.frame = parent;
		this.pc = pairs;
		this.cdContainer = null;
		this.combined = false;
		this.directory = dname;
		this.fileName = fname;
		// create a file chooser
		if (!this.directory.equals(""))
			this.chooser = new JFileChooser(this.directory);
		else
			this.chooser = new JFileChooser(System.getProperty("user.dir"));
		this.filter = new PairFileFilter();
		this.chooser.addChoosableFileFilter(this.filter);
		this.chooser.setFileFilter(this.filter);
	}

	public PairIOGUI(JFrame parent, ConflictsDependenciesContainer pairs) {
		this.frame = parent;
		this.cdContainer = pairs;
		this.pc = null;
		this.combined = true;
		this.chooser = new JFileChooser(System.getProperty("user.dir"));
		this.filter = new PairFileFilter();
		this.chooser.addChoosableFileFilter(this.filter);
		this.chooser.setFileFilter(this.filter);
	}

	public PairIOGUI(JFrame parent, ConflictsDependenciesContainer pairs,
						String dname, String fname) {
		this.frame = parent;
		this.cdContainer = pairs;
		this.pc = null;
		this.combined = true;
		this.directory = dname;
		this.fileName = fname;
		// create a file chooser
		if (!this.directory.equals(""))
			this.chooser = new JFileChooser(this.directory);
		else
			this.chooser = new JFileChooser(System.getProperty("user.dir"));
		this.filter = new PairFileFilter();
		this.chooser.addChoosableFileFilter(this.filter);
		this.chooser.setFileFilter(this.filter);
	}

	private Vector<String> getFileFilter(PairContainer pairs) {
		Vector<String> ff = new Vector<String>(2);
		ff.add(".cpx");
		ff.add("Conflict Pairs XML (.cpx)");
		if (pairs == null)
			return ff;
		if (pairs.getKindOfConflict() == CriticalPair.TRIGGER_DEPENDENCY) {
			ff.removeAllElements();
			ff.add(".dpx");
			ff.add("Dependency Pairs XML (.dpx)");
		}
		return ff;
	}

	private void resetFileFilter(PairContainer pairs) {
		this.chooser.removeChoosableFileFilter(this.filter);
		Vector<String> ff = getFileFilter(pairs);
		this.filter = new PairFileFilter(ff.get(0), ff.get(1));
		this.chooser.addChoosableFileFilter(this.filter);
		this.chooser.setFileFilter(this.filter);
	}

	public String getFileFilter() {
		return this.filter.getExtension();
	}

	public void setFileFilter(String ff) {
		if (!ff.equals(this.filter.getExtension())) {
			this.chooser.removeChoosableFileFilter(this.filter);
			String descr = "Conflicts Pairs XML (.cpx)";
			if (ff.equals(".dpx"))
				descr = "Dependency Pairs XML (.dpx)";
			this.filter = new PairFileFilter(ff, descr);
			this.chooser.addChoosableFileFilter(this.filter);
			this.chooser.setFileFilter(this.filter);
		}
	}

	public void setCriticalPairContainer(PairContainer pairs) {
		this.pc = pairs;
		this.combined = false;
		resetFileFilter(this.pc);
	}

	public void setCriticalPairContainer(ConflictsDependenciesContainer pairs) {
		this.pc = null;
		this.cdContainer = pairs;
		this.combined = true;		
		setFileFilter(".cpx");
	}

	public void setCriticalPairContainer(ConflictsDependenciesContainerSaveLoad pairs) {
		this.cdContainerSaveLoad = pairs;
		
		this.pc = null;
		this.cdContainer = null;
		this.combined = true;	
		setFileFilter(".cpx");		
	}
	
	/**
	 * This method provides a convenient way to get a graphical save interface.
	 * This interface lets the user choose the file easily.
	 */
	public void save() {
		this.isSaved = false;
		if (this.cdContainerSaveLoad == null
				&& this.cdContainer == null
				&& this.pc == null) {
			return;
		}
		
		int returnVal = this.chooser.showSaveDialog(this.frame);
		this.directory = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.directory = this.chooser.getCurrentDirectory().toString();
				this.fileName = this.chooser.getSelectedFile().getName();
				if (!this.directory.endsWith(File.separator))
					this.directory += File.separator;
				File f = new File(this.directory + this.fileName);
				if (!this.chooser.getFileFilter().accept(f)) {
					this.fileName += ((ExtensionFileFilter) this.chooser.getFileFilter())
							.getExtension();
				}
				save(this.directory, this.fileName);
//				if (this.isSaved) {
////					currentDir = directory;
//				}
			}
		}
	}

	/**
	 * This method controlls the process of saving. That is way a directory and
	 * a file name is needed.
	 * 
	 * @param dir
	 *            The destination directory of the file.
	 * @param file
	 *            The destination file name.
	 */
	public void save(String dir, String file) {
		XMLHelper xmlh = new XMLHelper();
		// file = XMLHelper.replaceGermanSpecialCh(file);
		
		if (this.combined) {
			if (this.cdContainerSaveLoad != null) {
				xmlh.addTopObject(this.cdContainerSaveLoad);
				xmlh.save_to_xml(dir + file);
				this.isSaved = true;
			}
			else if (this.cdContainer != null) {
				xmlh.addTopObject(this.cdContainer);
				xmlh.save_to_xml(dir + file);
				this.isSaved = true;
			} 
		} else if (this.pc != null) {
			xmlh.addTopObject(this.pc);
			xmlh.save_to_xml(dir + file);
			this.isSaved = true;
		} 
	}

	public boolean fileIsSaved() {
		return this.isSaved;
	}

	/**
	 * This method provides a convenient way to get a graphical load interface.
	 * This interface lets the user enter a file name easily.
	 * 
	 * @return The loaded object.
	 */
	public Object load() {
		if (this.pc == null)
			return null;

		Object result = null;
		int returnVal = this.chooser.showOpenDialog(this.frame);
		this.directory = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.directory = this.chooser.getCurrentDirectory().toString();
				this.fileName = this.chooser.getSelectedFile().getName();
				if (!this.directory.endsWith(File.separator))
					this.directory += File.separator;
				File f = new File(this.directory + this.fileName);
				if (!this.chooser.getFileFilter().accept(f)) {
					this.fileName += ((ExtensionFileFilter) this.chooser.getFileFilter())
							.getExtension();
				}
				result = load(this.directory, this.fileName);
//				currentDir = directory;
			}
		}
		return result;
	}

	public Object load(boolean combi) {
		if (!combi) {
			return load();
		}
		
		this.cdContainerSaveLoad = new ConflictsDependenciesContainerSaveLoad();
		
		Object result = null;
		int returnVal = this.chooser.showOpenDialog(this.frame);
		this.directory = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.directory = this.chooser.getCurrentDirectory().toString();
				this.fileName = this.chooser.getSelectedFile().getName();
				if (!this.directory.endsWith(File.separator))
					this.directory += File.separator;
				File f = new File(this.directory + this.fileName);
				if (!this.chooser.getFileFilter().accept(f))
					this.fileName += ((ExtensionFileFilter) this.chooser.getFileFilter())
							.getExtension();

				result = load(this.directory, this.fileName);
//				currentDir = directory;
			}
		}
		return result;
	}

	/**
	 * This method controlls the process of loading. Therefor a directory and a
	 * file name is needed.
	 * 
	 * @param dir
	 *            The destination directory of the file.
	 * @param file
	 *            The destination file name.
	 * @return The loaded object.
	 */
	public Object load(String dir, String file) {
		if (this.cdContainerSaveLoad == null 
				&& this.cdContainer == null 
				&& this.pc == null) {
			return null;
		}
		
		Object result = null;
		this.directory = dir;
		this.fileName = file;
		XMLHelper h = new XMLHelper();
		/*
		 * if(XMLHelper.hasGermanSpecialCh(fileName)){ System.out.println(" Read
		 * file name exception occurred! " +"\n Maybe the German characters like
		 * ä, ö, ü, ß or spase were used. " +"\n Please rename the file " +"\n
		 * and try again."); return null; }
		 */
		if (h.read_from_xml(this.directory + this.fileName)) {
		
			if (this.cdContainerSaveLoad != null) {
				this.combined = true;
				result = h.getTopObject(this.cdContainerSaveLoad);
			} 
			else if (this.cdContainer != null) {
				this.combined = true;
				result = h.getTopObject(this.cdContainer);
			} 
			else if (this.pc != null) {
				this.combined = false;
				result = h.getTopObject(this.pc);
			}
		}
		return result;
	}

	public Object reload(String fullFileName) {
		if (this.cdContainerSaveLoad == null 
				&& this.cdContainer == null 
				&& this.pc == null) {
			return null;
		}
		
		Object result = null;
		XMLHelper h = new XMLHelper();
		/*
		 * if(XMLHelper.hasGermanSpecialCh(fullFileName)){
		 * System.out.println("Read file name exception occurred! " +"\nMaybe
		 * the German characters like ä, ö, ü, ß or space were used. "
		 * +"\nPlease rename the file " +"\nand try again."); return null; }
		 */
		if (h.read_from_xml(fullFileName)) {
			
			if (this.cdContainerSaveLoad != null) {
				this.combined = true;
				result = h.getTopObject(this.cdContainerSaveLoad);
			} 
			else if (this.cdContainer != null) {
				this.combined = true;
				result = h.getTopObject(this.cdContainer);
			} else if (this.pc != null) {
				this.combined = false;
				result = h.getTopObject(this.pc);
			}
		}
		return result;
	}

	public String getFileName() {
		return this.fileName;
	}

	public void setDirectoryName(String dir) {
		this.directory = dir;
		this.chooser.setCurrentDirectory(new File(this.directory));
	}

	public void setDirectoryName(String dir, String file) {
		this.directory = dir;
		this.fileName = file;
		this.chooser.setCurrentDirectory(new File(this.directory));
		if (this.fileName.equals(""))
			this.chooser.setSelectedFile(null);
		
	}

	public String getDirectoryName() {
		return this.directory;
	}

	public boolean isCombined() {
		return this.combined;
	}

}
/*
 * $Log: PairIOGUI.java,v $
 * Revision 1.14  2010/09/23 08:20:54  olga
 * tuning
 *
 * Revision 1.13  2010/03/08 15:43:09  olga
 * code optimizing
 *
 * Revision 1.12  2009/05/12 10:37:02  olga
 * CPA: bug fixed
 * Applicability of Rule Seq. : bug fixed
 *
 * Revision 1.11  2009/03/26 10:10:57  olga
 * Save and load of CPA improved, XY layout if grammar graphs (not of overlap graphs)
 * is saved and loaded, too.
 * Remove the first conclusion of an atomic graph constraint - Bug fixed
 * File menu update after closing grammar - bug fixed
 *
 * Revision 1.10  2008/10/29 09:04:13  olga
 * new sub packages of the package agg.gui: typeeditor, editor, trafo, cpa, options, treeview, popupmenu, saveload
 *
 * Revision 1.9  2008/02/18 09:37:10  olga
 * - an extention of rule dependency check is implemented;
 * - some bugs fixed;
 * - editing of graphs improved
 *
 * Revision 1.8  2007/11/01 09:58:18  olga
 * Code refactoring: generic types- done
 *
 * Revision 1.7  2007/09/24 09:42:39  olga
 * AGG transformation engine tuning
 *
 * Revision 1.6  2007/09/10 13:05:45  olga
 * In this update:
 * - package xerces2.5.0 is not used anymore;
 * - class com.objectspace.jgl.Pair is replaced by the agg own generic class agg.util.Pair;
 * - bugs fixed in:  usage of PACs in rules;  match completion;
 * 	usage of static method calls in attr. conditions
 * - graph editing: added some new features
 * Revision 1.5 2005/12/21 14:48:46 olga GUI tuning
 * 
 * Revision 1.4 2005/10/24 09:04:49 olga GUI tuning
 * 
 * Revision 1.3 2005/10/10 08:05:16 olga Critical Pair GUI and CPA graph
 * 
 * Revision 1.2 2005/09/19 09:12:14 olga CPA GUI tuning
 * 
 * Revision 1.1 2005/08/25 11:56:55 enrico *** empty log message ***
 * 
 * Revision 1.2 2005/06/20 13:37:04 olga Up to now the version 1.2.8 will be
 * prepared.
 * 
 * Revision 1.1 2005/05/30 12:58:03 olga Version with Eclipse
 * 
 * Revision 1.6 2005/03/03 13:48:42 olga - Match with NACs and attr. conditions
 * with mixed variables - error corrected - save/load class packages written by
 * user - PACs : creating T-equivalents - improved - save/load matches of the
 * rules (only one match of a rule) - more friendly graph/rule editor GUI - more
 * syntactical checks in attr. editor
 * 
 * Revision 1.5 2004/09/20 12:52:06 olga Fehler bei loaden von CPs korregiert.
 * 
 * Revision 1.4 2004/01/22 17:50:52 olga tests
 * 
 * Revision 1.3 2003/03/05 18:24:09 komm sorted/optimized import statements
 * 
 * Revision 1.2 2003/02/24 13:27:32 olga Save / load path
 * 
 * Revision 1.1.1.1 2002/07/11 12:17:19 olga Imported sources
 * 
 * Revision 1.2 2001/03/08 11:02:46 olga Parser Anbindung gemacht. Stand nach
 * AGG GUI Reimplementierung. Stand nach der AGG GUI Reimplementierung.Das ist
 * Stand nach der AGG GUI Reimplementierung und Parser Anbindung.
 * 
 * Revision 1.1.2.3 2001/01/28 13:14:45 shultzke API fertig
 * 
 * Revision 1.1.2.2 2001/01/10 15:09:50 shultzke load and save fast fertig
 * 
 * Revision 1.1.2.1 2000/12/12 13:27:42 shultzke erste Versuche kritische Paare
 * mit XML abzuspeichern
 * 
 */
