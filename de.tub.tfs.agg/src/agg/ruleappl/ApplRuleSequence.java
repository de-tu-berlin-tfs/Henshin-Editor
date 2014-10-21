/**
 * 
 */
package agg.ruleappl;

import java.io.File;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import agg.parser.CriticalPairOption;
import agg.util.Pair;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.Rule;

/**
 * @author olga
 *
 */
public class ApplRuleSequence implements XMLObject {
	
	private String dirName;
	
	private GraGra gragra;	
			
	private CriticalPairOption cpOption;
	
	private final List<RuleSequence> ruleSequences = new Vector<RuleSequence>();
				
//	private final List<Rule> concurrentRules = new Vector<Rule>();
			
	/**
	 * 
	 */
	public ApplRuleSequence(final CriticalPairOption cpOption) {
		this.cpOption = cpOption;
	}
	
	public void dispose() {
		clear();
		this.gragra = null;
	}
	
	public void clear() {
		this.ruleSequences.clear();
	}
	
	public void setGraGra(final GraGra gra) {
		this.gragra = gra;
		this.ruleSequences.clear();
	}
		
	public GraGra getGraGra() {
		return this.gragra;
	}
	
	public boolean isEmpty() {
		return this.ruleSequences.isEmpty();
	}
	
	public List<RuleSequence> getRuleSequences() {
		return this.ruleSequences;
	}
	
	public RuleSequence getRuleSequence(final int indx) {
		if (indx >= 0 && indx < this.ruleSequences.size()) {
			return this.ruleSequences.get(indx);
		} 
		return null;
	}
	
	public Pair<Boolean, String> getApplicabilityResult(final int indx) {
		Pair<Boolean, String> result = null;
		if (indx >= 0 && indx < this.ruleSequences.size()) {
			result = this.ruleSequences.get(indx).getApplicabilityResult();
		}
		return result;
	}
	
	public Pair<Boolean, String> getNonApplicabilityResult(final int indx) {
		Pair<Boolean, String> result = null;
		if (indx >= 0 && indx < this.ruleSequences.size()) {
			result = this.ruleSequences.get(indx).getNonApplicabilityResult();
		}
		return result;
	}
	
	public boolean removeResult(int indx) {
		if (indx >= 0 && indx < this.ruleSequences.size()) {
			this.ruleSequences.get(indx).uncheck();
			return true;
		}
		return false;
	}
			
//	private List<Rule> makeRuleList(final List<String> sequence) {
//		final List<Rule> ruleList = new Vector<Rule>();
//		for (int j=0; j<sequence.size(); j++) {
//			Rule rule = gragra.getRule(sequence.get(j));
//			ruleList.add(rule);
//		}
//		return ruleList;
//	}
	
	public void setRuleSequences(final List<RuleSequence> list) {
		this.ruleSequences.clear();
		for (int i=0; i<list.size(); i++) {
			this.ruleSequences.add(list.get(i));
		}
	}
	
	public void setRuleSequence(final RuleSequence sequence) {
		this.ruleSequences.clear();
		this.ruleSequences.add(sequence);
	}
	
	public RuleSequence addRuleSequence(final List<String> listOfRuleNames, final Graph graph) {
		if (this.gragra == null)
			return null;
		
		final RuleSequence rseq = new RuleSequence(
				this.gragra, 
				"RuleSequence",
				this.cpOption);
		rseq.setGraph(graph);
		for (int i=0; i<listOfRuleNames.size(); i++) {
			String rname = listOfRuleNames.get(i);
			Rule rule = this.gragra.getRule(rname);
			if (rule != null) {
				rseq.addRule(rule);
			} else {
				rseq.dispose();
				return null;
			}
		}
		return rseq;
	}
	
	public void addRuleSequence(final RuleSequence sequence) {
		this.ruleSequences.add(sequence);
	}
	
	public void addRuleSequenceAt(int indx, final RuleSequence sequence) {
			this.ruleSequences.add(indx, sequence);
	}
		
	public void setGraphOfRuleSequence(final int indx, final Graph g) {
		if (indx >= 0 && indx < this.ruleSequences.size()) {
			RuleSequence seq = this.ruleSequences.get(indx);
			seq.setGraph(g);
		}
	}
	
	public boolean removeRuleSequence(final int indx) {
		if (indx >= 0 && indx < this.ruleSequences.size()) {
			RuleSequence seq = this.ruleSequences.get(indx);
			this.ruleSequences.remove(indx);
			seq.dispose();
			return true;
		} 
		return false;
	}
		
	public RuleSequence copyRuleSequence(int seqIndx) {
		if (seqIndx >= 0 && seqIndx < this.ruleSequences.size()) {		
			RuleSequence seq = this.ruleSequences.get(seqIndx);
			RuleSequence seqCopy = seq.getCopy();
			seqIndx++;
			if (seqIndx < this.ruleSequences.size()) {
				this.ruleSequences.add(seqIndx, seqCopy);
			} else {
				this.ruleSequences.add(seqCopy);
			} 
			return seqCopy;
		} 
		return null;		
	}
	
	public void moveRuleSequence(int from, int to) {
		if (from >= 0 && from < this.ruleSequences.size()
				&& to >= 0 && to < this.ruleSequences.size()) {		
			moveSequence(from, to);
//			System.out.println(ruleSequences.get(to).getRuleNames());
		}
	}
	
	private void moveSequence(int from, int to) {
		RuleSequence seq = this.ruleSequences.get(from);
		this.ruleSequences.remove(from);
		this.ruleSequences.add(to, seq);
		
	}
	
	public void moveRuleInsideSequence(int seqIndx, int from, int to) {
		if (seqIndx >= 0 && seqIndx < this.ruleSequences.size()) {
			RuleSequence seq = this.ruleSequences.get(seqIndx);
			seq.moveRule(from, to);
		}
	}
	
	public boolean hasChecked(int seqIndx) {
		return this.ruleSequences.get(seqIndx).isChecked();
	}
		
	public Pair<Boolean, List<String>> getRuleResult(
			final int seqIndx,
			final int indx,
			final String ruleName, 
			final String criterion) {
		
		RuleSequence seq = this.ruleSequences.get(seqIndx);
		return seq.getRuleResult(indx, ruleName, criterion);
	}
	
	public boolean check(final int seqIndx) {
		if (seqIndx >= 0 && seqIndx < this.ruleSequences.size()) {
			RuleSequence seq = this.ruleSequences.get(seqIndx);
			return this.check(seq);
		} 
		return false;
	}
	
	public boolean check(final RuleSequence sequence) {
		boolean result = sequence.check();
				
//		sequence.saveConcurrentRules();
		
		return result;
	}
	
	public int getIndexOf(final RuleSequence sequence) {
		return this.ruleSequences.indexOf(sequence);
	}
	
	public void save(final String filename) {
		String rsx = ".rsx";
		String outfileName = "";
		if ("".equals(filename)) {
			outfileName = this.gragra.getFileName().concat("_out.rsx");
		} else {
			outfileName = filename;
			if (outfileName.indexOf(rsx) == -1) {
				outfileName = outfileName.concat(rsx);
			}
		}

		XMLHelper xmlh = new XMLHelper();
		xmlh.addTopObject(this);
		xmlh.save_to_xml(outfileName);
	}
	
	public GraGra load(final String filename) throws Exception {
		File f = new File(filename);
		if (f.exists()) {
			if (filename.endsWith(".rsx")) {				
				this.gragra = BaseFactory.theFactory().createGraGra(false);
				
				XMLHelper h = new XMLHelper();				
				if (h.read_from_xml(filename)) {
					h.getTopObject(this);
	
					if (f.getParent() == null) {
						this.dirName = "." + File.separator;
					} else {
						this.dirName = f.getParent() + File.separator;
					}
				} else {
					throw new Exception("File  \"" + filename  
							+ "\"  is not a  \".rsx\"  file!");
				}
			} else {
				throw new Exception("File  \"" + filename  
						+ "\"  is not a  \".rsx\"  file!");
			}			
		} else {
			throw new Exception("File  \"" + filename  
					+ "\"  doesn't exist!");
		}
		
		return this.gragra;
	}
	
	public GraGra load(final String filename, XMLHelper h) throws Exception {
		File f = new File(filename);
		if (f.exists()) {
			if (filename.endsWith(".rsx")) {				
				this.gragra = BaseFactory.theFactory().createGraGra(false);
								
				if (h.read_from_xml(filename)) {
					h.getTopObject(this);
	
					String fileName = f.getName();
					if (f.getParent() == null) {
						this.dirName = "." + File.separator;
					} else {
						this.dirName = f.getParent() + File.separator;
					}
					this.gragra.setDirName(this.dirName);
					this.gragra.setFileName(fileName);
				} else {
					throw new Exception("File  \"" + filename  
							+ "\"  is not a  \".rsx\"  file!");
				}
			} else {
				throw new Exception("File  \"" + filename  
						+ "\"  is not a  \".rsx\"  file!");
			}			
		} else {
			throw new Exception("File  \"" + filename  
					+ "\"  doesn't exist!");
		}
		
		return this.gragra;
	}
	
	public void XwriteObject(XMLHelper h) {
		h.openNewElem("RuleSequenceApplicability", this);
		h.addObject("GraGra", this.gragra, true);
		
		h.openSubTag("RuleSequences");
		for (int i=0; i<this.ruleSequences.size(); i++) {
			RuleSequence seq = this.ruleSequences.get(i);
			
			boolean result = seq.getApplicabilityResult().first.booleanValue();
			String text = seq.getApplicabilityResult().second;
			boolean result2 = seq.getNonApplicabilityResult().first.booleanValue();
			String text2 = seq.getNonApplicabilityResult().second;
	
			h.openSubTag("Sequence");
			h.addAttr("name", seq.getName());
			// save options
			h.openSubTag("ConcurrentRule");	
			if (seq.getDepthOfConcurrentRule() == -1) {
				h.addAttr("depth", "undefined");
			} else {
				h.addAttr("depth", String.valueOf(seq.getDepthOfConcurrentRule()));
			}
			h.addAttr("complete", String.valueOf(seq.getCompleteConcurrency()));
			h.addAttr("completecpa", String.valueOf(seq.getCompleteCPAOfConcurrency()));
			h.addAttr("ignoredanglingedge", String.valueOf(seq.getIgnoreDanglingEdgeOfDelNode()));
			h.close();
			// save main result
			h.openSubTag("Item");	
			h.addAttr("kind", "applicable");
			h.addAttr("result", String.valueOf(result));
			h.addAttr("criterion", text);
			h.close();			
			h.openSubTag("Item");		
			h.addAttr("kind", "nonapplicable");
			h.addAttr("result", String.valueOf(result2));
			h.addAttr("criterion", text2);
			h.close();
			// save graph reference
			if (seq.getGraph() != null) {
				h.openSubTag("Graph");
				h.addObject("id", seq.getGraph(), false);
				h.close();
			}
			// save each rule result
			Hashtable<String, Pair<Boolean, List<String>>> ruleRes = seq.getRuleResults();	
			for (int j=0; j<seq.getRules().size(); j++) {
				Rule rule = seq.getRules().get(j);
				String ruleName = rule.getName();
				h.openSubTag("Rule");
				h.addObject("id", rule, false);
				if (ruleRes != null) {					
					Enumeration<String> keys = ruleRes.keys();
					while (keys.hasMoreElements()) {
						String key = keys.nextElement();						
						if (key.indexOf(String.valueOf(j).concat(ruleName)) == 0) {						
							Pair<Boolean, List<String>> resultpair = ruleRes.get(key);
							if (resultpair != null) {
								h.openSubTag("Item");
								h.addAttr("result", String.valueOf(resultpair.first));
								h.addAttr("criterion", resultpair.second.get(0));
								h.addAttr("text", resultpair.second.get(1));
								h.close();	
							} 
						}
					}					
				} 
				else {
					h.addAttr("result", "true");
					h.addAttr("criterion", "undefined");
					h.addAttr("text", "undefined");
				}			
				
				h.close();
			}			
			h.close();
		}
		h.close();	
		
		h.close();
	}
	
	
	private RuleSequence getSequence(String name) {
		for (int i=0; i<this.ruleSequences.size(); i++) {
			if (this.ruleSequences.get(i).getName().equals(name)) {
				return this.ruleSequences.get(i);
			}
		}
		return null;
	}
	
	public void XreadObject(XMLHelper h) {		
		if (h.isTag("RuleSequenceApplicability", this)) {			
			h.getObject("", this.gragra, true);
						
			this.ruleSequences.clear();
			if (!this.gragra.getRuleSequences().isEmpty())
				this.setRuleSequences(this.gragra.getRuleSequences());
			
//			int i = -1;
			if (h.readSubTag("RuleSequences")) {
				while (h.readSubTag("Sequence")) {
					String strName = h.readAttr("name");				
//					i++;
					boolean newSequence = false;
					RuleSequence seq = this.getSequence(strName);
					if (seq == null) {
						seq = new RuleSequence(
									this.gragra, 
									"RuleSequence",
									this.cpOption);
						if (!strName.equals(""))
							seq.setName(strName);						
						this.ruleSequences.add(seq);
						newSequence = true;
					}
										
					if (h.readSubTag("ConcurrentRule")) {
						String depthstr = h.readAttr("depth");
						if ("undefined".equals(depthstr)) {
							seq.setDepthOfConcurrentRule(-1);
						} else {
							seq.setDepthOfConcurrentRule(Integer.valueOf(depthstr).intValue());
						}
						String completestr = h.readAttr("complete");
						seq.setCompleteConcurrency(Boolean.valueOf(completestr).booleanValue());
						String completecpastr = h.readAttr("completecpa");
						seq.setCompleteCPAOfConcurrency(Boolean.valueOf(completecpastr).booleanValue());
						String danglingedgestr = h.readAttr("ignoredanglingedge");
						seq.setIgnoreDanglingEdgeOfDelNode(Boolean.valueOf(danglingedgestr).booleanValue());
						h.close();
					}
					
					Pair<Boolean, Boolean> result = new Pair<Boolean, Boolean>(null, null);
					Pair<String, String> criterion = new Pair<String, String>(null, null);
					while (h.readSubTag("Item")) {
						String kind = h.readAttr("kind");
						String res = h.readAttr("result");
						String  str = h.readAttr("criterion");
						
						if ("applicable".equals(kind)) {
							result.first = Boolean.valueOf(res);
							criterion.first = str;
						} else 	if ("nonapplicable".equals(kind)) {	
							result.second = Boolean.valueOf(res);
							criterion.second = str;
						}												
						h.close();
					}
						
					if (newSequence) {
						if (h.readSubTag("Graph")) {
							Graph g = (Graph) h.getObject("id", null, false);
							if (g != null) {
								seq.setGraph(g);
							}
							h.close();
						}
					}
					
					int indx = -1;
					while (h.readSubTag("Rule")) {
						final Rule r = (Rule) h.getObject("id", null, false);
//						System.out.println(r);
						if (newSequence)
							seq.addRule(r);
						indx++;
						
						while (h.readSubTag("Item")) {	
							String criterionStr = h.readAttr("criterion");
							String res = h.readAttr("result");
							String text = h.readAttr("text");
							h.close();
//							System.out.println(indx+"  "+r.getName()+"  "+criterionStr+"   "+res+"  ((  "+text);
							seq.setRuleResult(indx, r.getName(), Boolean.parseBoolean(res), criterionStr, text);																					
						}							
						h.close();	
					}
//					System.out.println(seq.getRuleNames()+"\n");
					
					seq.setApplicabilityResult(result.first.booleanValue(), criterion.first);
					seq.setNonApplicabilityResult(result.second.booleanValue(), criterion.second);
					seq.checked = !"undefined".equals(criterion.first) && !"undefined".equals(criterion.second);
					
//					System.out.println(result.first.booleanValue()+"   "+ criterion.first);
//					System.out.println(result.second.booleanValue() +"   "+ criterion.second+"\n");
					
					h.close();
				}
				h.close();
			}
			h.close();			
		}		
	}
	
		
}
