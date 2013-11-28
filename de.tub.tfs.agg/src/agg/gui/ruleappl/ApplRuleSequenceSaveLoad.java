/**
 * 
 */
package agg.gui.ruleappl;


import java.io.File;

import javax.swing.JFileChooser;

import agg.editor.impl.EdGraGra;
import agg.gui.saveload.AGGFileFilter;
import agg.ruleappl.ApplRuleSequence;
import agg.util.XMLHelper;
import agg.util.XMLObject;


/**
 * @author olga
 *
 */
public class ApplRuleSequenceSaveLoad implements XMLObject {

	public ApplRuleSequence ars;
	public EdGraGra layout;
	public String dirName = "";
	public String fname = "";
	
	protected JFileChooser chooser;
	
	public ApplRuleSequenceSaveLoad() {}
	
	public ApplRuleSequenceSaveLoad(ApplRuleSequence applRuleSeqence, EdGraGra layoutGrammar) {
		this.ars = applRuleSeqence;
		this.layout = layoutGrammar;
	}

	public boolean save() {
		if (this.dirName.equals("")) {
			this.chooser = new JFileChooser(System.getProperty("user.dir"));
		} else {
			this.chooser = new JFileChooser(this.dirName);
		}
		this.chooser.setFileFilter(new AGGFileFilter("rsx", "AGG Files (.rsx)"));
		int returnVal = this.chooser.showSaveDialog(null);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fname = this.chooser.getSelectedFile().getName();				
			}
		}
		
		if (!"".equals(this.fname)) {
			if (!this.fname.endsWith(".rsx")) {
				this.fname = this.fname.concat(".rsx");
			}
			XMLHelper xmlh = new XMLHelper();
			xmlh.addTopObject(this);
			if (xmlh.save_to_xml(this.dirName + File.separator + this.fname)) {
				return true;
			} 
			return false;
		} 
		return false;
	}
	
	public void load(ApplRuleSequence applrulesequence) throws Exception {
		if (this.dirName.equals("")) {
			this.chooser = new JFileChooser(System.getProperty("user.dir"));
		} else {
			this.chooser = new JFileChooser(this.dirName);
		}
		this.chooser.setFileFilter(new AGGFileFilter("rsx", "AGG Files (.rsx)"));
		int returnVal = this.chooser.showOpenDialog(null);
		this.dirName = this.chooser.getCurrentDirectory().toString();
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			if (this.chooser.getSelectedFile() != null
					&& !this.chooser.getSelectedFile().getName().equals("")) {
				this.fname = this.chooser.getSelectedFile().getName();
			}
		}
		
		if (!"".equals(this.fname)) {
			this.ars = applrulesequence;
			this.ars.setGraGra(null);
			
			try {
				XMLHelper h = new XMLHelper();				
				this.ars.load(this.dirName + File.separator + this.fname, h);			
				this.layout = new EdGraGra(this.ars.getGraGra());
				h.enrichObject(this.layout);				
			} catch (Exception ex) {
				System.out.println("Loading File: "+this.fname+"   ERROR: "+ex.getMessage());
//				throw ex;
			}
		}	
	}
	
	public void XreadObject(XMLHelper h) {
		h.peekObject(this.ars, this);
		h.enrichObject(this.layout);
	}
	
	public void XwriteObject(XMLHelper h) {
//		System.out.println("### XwriteObject   ApplRuleSequenceSaveLoad "+layout+"   "+ars);
		h.addTopObject(this.ars);
		h.addObject("GraphTransformationSystem", this.layout, false);
	}
	
}
