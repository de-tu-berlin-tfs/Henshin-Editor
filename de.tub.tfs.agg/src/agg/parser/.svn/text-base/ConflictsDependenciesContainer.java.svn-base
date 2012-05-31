package agg.parser;

import java.awt.Color;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Node;
import agg.xt_basis.Arc;
import agg.xt_basis.TypeException;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Rule;
import agg.xt_basis.Type;
import agg.editor.impl.EdGraph;
import agg.editor.impl.EdType;
import agg.parser.ExcludePairContainer.Entry;
import agg.util.XMLHelper;
import agg.util.XMLObject;
import agg.util.Pair;

public class ConflictsDependenciesContainer implements XMLObject {

	protected ExcludePairContainer epc;

	protected DependencyPairContainer dpc;

	protected LayeredExcludePairContainer lepc;

	protected LayeredDependencyPairContainer ldpc;

	protected boolean layered;

	protected PriorityExcludePairContainer pepc;

	protected PriorityDependencyPairContainer pdpc;

	protected boolean priority;

	protected GraGra pairsGrammar;
		
	protected Graph cpaBasisGraph;

	protected EdGraph cpaGraph;

	protected int count;

	protected final List<Pair<String,String>> cpaOptions = new Vector<Pair<String,String>>();
	
	
	
	public ConflictsDependenciesContainer() {
		this.cpaBasisGraph = null;
		this.cpaGraph = null;
	}

	public ConflictsDependenciesContainer(
			final PairContainer conflict,
			final PairContainer dependency) {
		
		this.layered = false;
		this.priority = false;

		this.count = 0;
		if (dependency instanceof LayeredDependencyPairContainer) {
			this.ldpc = (LayeredDependencyPairContainer) dependency;
			this.count++;
			this.layered = true;
			this.pairsGrammar = this.ldpc.getGrammar();
		} else if (dependency instanceof PriorityDependencyPairContainer) {
			this.pdpc = (PriorityDependencyPairContainer) dependency;
			this.count++;
			this.priority = true;
			this.pairsGrammar = this.pdpc.getGrammar();
		} else if (dependency instanceof DependencyPairContainer) {
			this.dpc = (DependencyPairContainer) dependency;
			this.count++;
			this.pairsGrammar = this.dpc.getGrammar();
		}

		this.layered = false;
		this.priority = false;
		if (conflict instanceof LayeredExcludePairContainer) {
			this.lepc = (LayeredExcludePairContainer) conflict;
			this.count++;
			this.layered = true;
			this.pairsGrammar = this.lepc.getGrammar();
		} else if (conflict instanceof PriorityExcludePairContainer) {
			this.pepc = (PriorityExcludePairContainer) conflict;
			this.count++;
			this.priority = true;
			this.pairsGrammar = this.pepc.getGrammar();
		} else if (conflict instanceof ExcludePairContainer) {
			this.epc = (ExcludePairContainer) conflict;
			this.count++;
			this.pairsGrammar = this.epc.getGrammar();
		}
	}

	public ConflictsDependenciesContainer(
			final PairContainer conflict,
			final PairContainer dependency, 
			final Graph conflictDependencyGraph) {		
		this(conflict, dependency);
		
		this.cpaBasisGraph = conflictDependencyGraph;
	}

	public ConflictsDependenciesContainer(
			final PairContainer conflict,
			final PairContainer dependency, 
			final EdGraph conflictDependencyGraph) {
		this(conflict, dependency);

		this.cpaGraph = conflictDependencyGraph;
		if (this.cpaGraph != null)
			this.cpaBasisGraph = conflictDependencyGraph.getBasisGraph();
	}

	public ExcludePairContainer getExcludePairContainer() {
		return this.epc;
	}

	public DependencyPairContainer getDependencyPairContainer() {
		return this.dpc;
	}

	public LayeredExcludePairContainer getLayeredExcludePairContainer() {
		return this.lepc;
	}

	public LayeredDependencyPairContainer getLayeredDependencyPairContainer() {
		return this.ldpc;
	}

	public PriorityExcludePairContainer getPriorityExcludePairContainer() {
		return this.pepc;
	}

	public PriorityDependencyPairContainer getPriorityDependencyPairContainer() {
		return this.pdpc;
	}

	public EdGraph getCPAGraph() {
		return this.cpaGraph;
	}

	public Graph getCPABasisGraph() {
		return this.cpaBasisGraph;
	}

	public boolean isPriority() {
		return this.priority;
	}

	public boolean isLayered() {
		return this.layered;
	}

	public GraGra getGrammar() {
		return this.pairsGrammar;
	}
	
	public int getContainerCount() {
		return this.count;
	}

	public List<Pair<String,String>> getLoadedCPAOptions() {
		return this.cpaOptions;
	}
	
	/**
	 * Writes the contents of this object to a file in a xml format.
	 * 
	 * @param h
	 *            A helper object for storing.
	 */
	public void XwriteObject(XMLHelper h) {				
		if (this.epc != null || this.dpc != null)
			writeCriticalPairs(h, this.epc, this.dpc, false);
		else if (this.lepc != null || this.ldpc != null)
			writeCriticalPairs(h, this.lepc, this.ldpc, true);
		else if (this.pepc != null || this.pdpc != null)
			writeCriticalPairs(h, this.pepc, this.pdpc, true);
		else
			System.out
					.println("ConflictsDependenciesContainer.XwriteObject  FAILED");
	}

	protected void writeCPAoptions(XMLHelper h, ExcludePairContainer pc ) {
		h.openSubTag("cpaOptions");
		boolean val = pc.complete;
		h.addAttr(CriticalPairOption.COMPLETE, Boolean.valueOf(val).toString());
		val = pc.consistent;
		h.addAttr(CriticalPairOption.CONSISTENT, Boolean.valueOf(val).toString());
		val = pc.strongAttrCheck;
		h.addAttr(CriticalPairOption.STRONG_ATTR_CHECK, Boolean.valueOf(val).toString());
		val = pc.reduceSameMatch;
		h.addAttr(CriticalPairOption.IGNORE_SAME_MATCH, Boolean.valueOf(val).toString());
		val = pc.ignoreIdenticalRules;
		h.addAttr(CriticalPairOption.IGNORE_SAME_RULE, Boolean.valueOf(val).toString());
		val = pc.reduce;
		h.addAttr(CriticalPairOption.ESSENTIAL, Boolean.valueOf(val).toString());
		val = pc.directStrctCnfl;
		h.addAttr(CriticalPairOption.DIRECTLY_STRICT_CONFLUENT, Boolean.valueOf(val).toString());
		val = pc.directStrctCnflUpToIso;
		h.addAttr(CriticalPairOption.DIRECTLY_STRICT_CONFLUENT_UPTOISO, Boolean.valueOf(val).toString());
		val = pc.namedObjectOnly;
		h.addAttr(CriticalPairOption.NAMED_OBJECT, Boolean.valueOf(val).toString());
		
		h.close();
	}
	
	protected List<Pair<String,String>> readCPAoptions(XMLHelper h) {
		final List<Pair<String,String>> list = new Vector<Pair<String,String>>();
		if (h.readSubTag("cpaOptions")) {
//			System.out.println("cpaOptions");
			Pair<String,String> p = new Pair<String,String> (
					CriticalPairOption.COMPLETE,
					h.readAttr(CriticalPairOption.COMPLETE));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.CONSISTENT,
					h.readAttr(CriticalPairOption.CONSISTENT));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.ESSENTIAL,
					h.readAttr(CriticalPairOption.ESSENTIAL));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);			
			p = new Pair<String,String> (
					CriticalPairOption.IGNORE_SAME_MATCH,
					h.readAttr(CriticalPairOption.IGNORE_SAME_MATCH));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.IGNORE_SAME_RULE,
					h.readAttr(CriticalPairOption.IGNORE_SAME_RULE));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.STRONG_ATTR_CHECK,
					h.readAttr(CriticalPairOption.STRONG_ATTR_CHECK));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.DIRECTLY_STRICT_CONFLUENT,
					h.readAttr(CriticalPairOption.DIRECTLY_STRICT_CONFLUENT));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.DIRECTLY_STRICT_CONFLUENT_UPTOISO,
					h.readAttr(CriticalPairOption.DIRECTLY_STRICT_CONFLUENT_UPTOISO));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			p = new Pair<String,String> (
					CriticalPairOption.NAMED_OBJECT,
					h.readAttr(CriticalPairOption.NAMED_OBJECT));
//			System.out.println(p.first+" , "+p.second);
			list.add(p);
			h.close();
		}
		return list;
	}
	
	protected boolean writeLayoutGrammar(XMLHelper h) {
		return true;
	}
	
	protected void readLayoutGrammar(XMLHelper h) {}
	
	protected boolean writeGrammar(XMLHelper h) {
		if (this.pairsGrammar != null) {
			h.addObject("GraGra", this.pairsGrammar, true);
			return true;
		} 
		return false;
	}
		
	protected void readGrammar(final XMLHelper h) {
		this.pairsGrammar = BaseFactory.theFactory().createGraGra();
		
		// loads the data in the predefined object
		h.getObject("", this.pairsGrammar, true);
		
		this.pairsGrammar.prepareRuleInfo();
		
		if (this.pairsGrammar.isLayered()) {
			this.layered = true;		
		}
		else if (this.pairsGrammar.trafoByPriority()){
			this.priority = true;	
		}
	}
	
	protected void writeRuleSet(XMLHelper h, final String tagname, final List<Rule> ruleSet) {
		h.openSubTag(tagname);
		h.addAttr("size", String.valueOf(ruleSet.size()));
		
		for (int i=0; i<ruleSet.size(); i++) {
			String ruleIDstr = h.getO2I(ruleSet.get(i));
			if (!ruleIDstr.equals("")) {
				h.addAttr("i".concat(String.valueOf(i)), ruleIDstr);
			}
		}
		h.close();
	}
	
	protected boolean readRuleSet(XMLHelper h, final String tagname, final List<Rule> ruleSet) {
		if (h.readSubTag(tagname)) {
			String sizeStr = h.readAttr("size");
			try {
				int size = Integer.valueOf(sizeStr).intValue();
				
				for (int i=0; i<size; i++) {
					String ruleID = h.readAttr("i".concat(String.valueOf(i)));
					Object obj = h.getI2O(ruleID);
					if (obj instanceof Rule) {
						ruleSet.add((Rule) obj);
					} else {
						h.close();
						return false;
					}
				}
				h.close();
				return true; 
				
			} catch (java.util.IllegalFormatException ex) { }
			h.close();
		}
		return false;
	}
	
	protected void writeCriticalPairs(XMLHelper h, PairContainer pc1,
			PairContainer pc2, boolean layer) {
		// System.out.println("criticalPairsWriteXML ... ");
		h.openNewElem("CriticalPairs", this);

		if (!writeGrammar(h)) {
			System.out.println("ConflictsDependenciesContainer.XwriteObject(XMLHelper h) :: "+
					"Cannot write critical pairs! Grammar is null.");
			return;
		}
		
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
		excludeContainer = null;
		Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
		conflictFreeContainer = null;
		Entry entry = null;
		boolean optionsWritten = false;
		
		if (pc1 != null) {
			writeCPAoptions(h, (ExcludePairContainer) pc1);
			optionsWritten = true;
						
			excludeContainer = ((ExcludePairContainer) pc1).getExcludeContainer();
			
			// write conflict container
			h.openSubTag("conflictContainer");
			h.addAttr("kind", "exclude");
			
			writeRuleSet(h, "RuleSet", pc1.getRules()); // columns of the table
			writeRuleSet(h, "RuleSet2", pc1.getRules2()); // rows of the table
			
			for (Enumeration<Rule> keys = excludeContainer.keys(); keys
					.hasMoreElements();) {
				Rule r1 = keys.nextElement();
				h.openSubTag("Rule");
				h.addObject("R1", r1, false);

				Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = excludeContainer.get(r1);
				for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
					Rule r2 = k2.nextElement();
					entry = ((ExcludePairContainer) pc1).getEntry(r1, r2);
					h.openSubTag("Rule");
					h.addObject("R2", r2, false);
					Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> p = secondPart.get(r2);
					Boolean b = p.first;
					h.addAttr("bool", b.toString());
					
//					h.addAttr("duIndx", String.valueOf(entry.duIndx));
//					h.addAttr("pfIndx", String.valueOf(entry.pfIndx));
//					h.addAttr("caIndx", String.valueOf(entry.caIndx));				
					h.addAttr("duIndx", entry.duIndxStr);
					h.addAttr("pfIndx", entry.pfIndxStr);
					h.addAttr("caIndx", entry.caIndxStr);
					
					if (b.booleanValue()) {
						Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
						v = p.second;
						for (int i = 0; i < v.size(); i++) {
							Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
							p2i = v.elementAt(i);
							Pair<OrdinaryMorphism, OrdinaryMorphism> p2 = p2i.first;
							h.openSubTag("Overlapping_Pair");
							OrdinaryMorphism first = p2.first;
							Graph overlapping = first.getImage();
							// add overlapping graph
							h.addObject("", overlapping, true);
							for (Iterator<Node> e = overlapping.getNodesSet().iterator(); e
									.hasNext();) {
								GraphObject o = e.next();
								if (o.isCritical()) {
									h.openSubTag("Critical");
									h.addObject("object", o, false);
									h.close();
								}
							}
							for (Iterator<Arc> e = overlapping.getArcsSet().iterator(); e
								.hasNext();) {
								GraphObject o = e.next();
								if (o.isCritical()) {
									h.openSubTag("Critical");
									h.addObject("object", o, false);
									h.close();
								}
							}
							writeOverlapMorphisms(h, r1, r2, p2i);

							h.close();
						}
					}
					h.close();
				}
				h.close();
			}
			h.close();

			// now write conflict free container
			conflictFreeContainer = ((ExcludePairContainer) pc1)
						.getConflictFreeContainer();
			if (conflictFreeContainer != null) {
				h.openSubTag("conflictFreeContainer");
				for (Enumeration<Rule> keys = excludeContainer.keys(); keys
							.hasMoreElements();) {
					Rule r1 = keys.nextElement();
					h.openSubTag("Rule");
					h.addObject("R1", r1, false);
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = conflictFreeContainer
								.get(r1);
					for (Enumeration<Rule> k2 = secondPart.keys(); k2
								.hasMoreElements();) {
						Rule r2 = k2.nextElement();
						entry = ((ExcludePairContainer) pc1).getEntry(r1, r2);
						h.openSubTag("Rule");
						h.addObject("R2", r2, false);
						Boolean b = secondPart.get(r2).first;
						h.addAttr("bool", b.toString());
						if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPUTABLE)
							h.addAttr("status", "not_computable");
						h.close();
					}
					h.close();
				}
				h.close();
			}			
		}

		excludeContainer = null;
		String kind = "trigger_dependency";
		if (pc2 != null) {
			
			if (!optionsWritten) {
				writeCPAoptions(h, (ExcludePairContainer) pc2);
				optionsWritten = true;
			}
			
			excludeContainer = ((ExcludePairContainer) pc2)
					.getExcludeContainer();
			
			if (((DependencyPairContainer) pc2).switchDependency)
				kind = "trigger_switch_dependency";
		
			// System.out.println(excludeContainer);
			// write dependency container
			h.openSubTag("dependencyContainer");			
			h.addAttr("kind", kind);
			
			writeRuleSet(h, "RuleSet", pc2.getRules()); // columns of the table
			writeRuleSet(h, "RuleSet2", pc2.getRules2()); // rows of the table
			
			for (Enumeration<Rule> keys = excludeContainer.keys(); keys
					.hasMoreElements();) {
				Rule r1 = keys.nextElement();
				h.openSubTag("Rule");
				h.addObject("R1", r1, false);
				Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = excludeContainer.get(r1);
				for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
					Rule r2 = k2.nextElement();
					entry = ((ExcludePairContainer) pc2).getEntry(r1, r2);
					h.openSubTag("Rule");
					h.addObject("R2", r2, false);
					Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> p = secondPart.get(r2);
					Boolean b = p.first;
					h.addAttr("bool", b.toString());
					
//					h.addAttr("duIndx", String.valueOf(entry.duIndx));
//					h.addAttr("pfIndx", String.valueOf(entry.pfIndx));
//					h.addAttr("caIndx", String.valueOf(entry.caIndx));
					h.addAttr("duIndx", entry.duIndxStr);
					h.addAttr("pfIndx", entry.pfIndxStr);
					h.addAttr("caIndx", entry.caIndxStr);
					
					if (b.booleanValue()) {
						Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> v = p.second;
						for (int i = 0; i < v.size(); i++) {
							Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> p2i = v.elementAt(i);
							Pair<OrdinaryMorphism, OrdinaryMorphism> p2 = p2i.first;
							h.openSubTag("Overlapping_Pair");
							OrdinaryMorphism first = p2.first;
							Graph overlapping = first.getImage();
							// add overlapping graph
							h.addObject("", overlapping, true);
							for (Iterator<Node> e = overlapping.getNodesSet().iterator(); e
									.hasNext();) {
								GraphObject o = e.next();
								if (o.isCritical()) {
									h.openSubTag("Critical");
									h.addObject("object", o, false);
									h.close();
								}
							}
							for (Iterator<Arc> e = overlapping.getArcsSet().iterator(); e.hasNext();) {
								GraphObject o = e.next();
								if (o.isCritical()) {
									h.openSubTag("Critical");
									h.addObject("object", o, false);
									h.close();
								}
							}
							writeOverlapMorphisms(h, r1, r2, p2i);

							h.close();
						}
					}
					h.close();
				}
				h.close();
			}
			h.close();

			// now write dependency free container
			conflictFreeContainer = ((ExcludePairContainer) pc2)
						.getConflictFreeContainer();
			if (conflictFreeContainer != null) {					
				h.openSubTag("dependencyFreeContainer");
				for (Enumeration<Rule> keys = excludeContainer.keys(); keys
							.hasMoreElements();) {
					Rule r1 = keys.nextElement();
					h.openSubTag("Rule");
					h.addObject("R1", r1, false);
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = conflictFreeContainer
								.get(r1);
					for (Enumeration<Rule> k2 = secondPart.keys(); k2
								.hasMoreElements();) {
						Rule r2 = k2.nextElement();
						entry = ((ExcludePairContainer) pc2).getEntry(r1, r2);
						h.openSubTag("Rule");
						h.addObject("R2", r2, false);
						Boolean b = secondPart.get(r2).first;
						h.addAttr("bool", b.toString());
						if (entry.getStatus() == ExcludePairContainer.Entry.NOT_COMPUTABLE)
							h.addAttr("status", "not_computable");
						h.close();
					}
					h.close();
				}
				h.close();
			}			
		}

		writeCPAGraph(h);

		h.close();
		
		writeLayoutGrammar(h);
	}
	
	protected void writeCPAGraph(XMLHelper h) {
		if (this.cpaBasisGraph != null) {
			h.openSubTag("ConflictDependencyGraph");
			h.openSubTag("Types");
			h.addEnumeration("", this.cpaBasisGraph.getTypeSet().getTypes(), true);
			h.close();
			h.addObject("", this.cpaBasisGraph, true);
			h.close();
		}
		
		if (this.cpaGraph != null) {
			//this.cpaGraph.XwriteObject(h);
			h.addObject("Graph", this.cpaGraph, false);
		}
	}

	/**
	 * Reads the contents of a xml file to restore this object.
	 * 
	 * @param h
	 *            A helper object for loading.
	 */
	@SuppressWarnings("unused")
	public void XreadObject(XMLHelper h) {
//		System.out.println("ConflictsDependencies.XreadObject ...");
		this.count = 0;
		this.cpaOptions.clear();
		
		if (h.isTag("CriticalPairs", this)) {
			Rule r1 = null;
			Rule r2 = null;
			int conflictKind = CriticalPair.CONFLICT;
			this.layered = false;
			this.priority = false;
			boolean b = false;
			boolean depsRead = false;
			Vector<String> tagnames = new Vector<String>(1);
			Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
			allOverlappings = null;
			
			// read GraGra which was saved with CPs :
			// this.pairsGrammar
			readGrammar(h);
			
			this.cpaOptions.addAll(readCPAoptions(h));		
			
			List<Rule> tmpList = null;
			List<Rule> tmpList2 = null;
			
			tagnames.add("conflictContainer");
			tagnames.add("conflictsContainer");
			tagnames.add("excludeContainer");
			if (h.readSubTag(tagnames)) {
				String kind = h.readAttr("kind");
				conflictKind = CriticalPair.CONFLICT;
				if (this.layered)
					this.lepc = (LayeredExcludePairContainer) ParserFactory
							.createEmptyCriticalPairs(this.pairsGrammar,
									CriticalPairOption.EXCLUDEONLY, true);
				else if (this.priority)
					this.pepc = (PriorityExcludePairContainer) ParserFactory
							.createEmptyCriticalPairs(this.pairsGrammar,
									CriticalPairOption.EXCLUDEONLY, false);
				else
					this.epc = (ExcludePairContainer) ParserFactory
							.createEmptyCriticalPairs(this.pairsGrammar,
									CriticalPairOption.EXCLUDEONLY, false);
				this.count++;
			} else {
				tagnames.clear();
				tagnames.add("dependenciesContainer");
				tagnames.add("dependencyContainer");
				if (h.readSubTag(tagnames)) {
					conflictKind = CriticalPair.TRIGGER_DEPENDENCY;
					boolean switchDependency = false;
					if (h.readAttr("kind").equals("trigger_switch_dependency")) {
						switchDependency = true;					
						conflictKind = CriticalPair.TRIGGER_SWITCH_DEPENDENCY;
					}
				
					if (this.layered) {
						this.ldpc = (LayeredDependencyPairContainer) ParserFactory
								.createEmptyCriticalPairs(this.pairsGrammar,
										conflictKind,
										true);
						this.ldpc.switchDependency = switchDependency;
					}
					else if (this.priority) {
						this.pdpc = (PriorityDependencyPairContainer) ParserFactory
								.createEmptyCriticalPairs(this.pairsGrammar,
										conflictKind,
										false);
						this.pdpc.switchDependency = switchDependency;
					}
					else {
						this.dpc = (DependencyPairContainer) ParserFactory
								.createEmptyCriticalPairs(this.pairsGrammar,
										conflictKind,
										false);
						this.dpc.enableSwitchDependency(switchDependency);
					}
					this.count++;
				}
			}

			if (conflictKind == CriticalPair.CONFLICT
					|| conflictKind == CriticalPair.TRIGGER_DEPENDENCY
					|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
				
				// read rule sets
				tmpList = new Vector<Rule>();
				tmpList2 = new Vector<Rule>();
				if (!readRuleSet(h, "RuleSet", tmpList))
					tmpList = null;
				if (!readRuleSet(h, "RuleSet2", tmpList2))
					tmpList2 = null;
				
				Enumeration<?> r1s = h.getEnumeration("", null, true, "Rule");
				if (!r1s.hasMoreElements())
					r1s = h.getEnumeration("", null, true, "Regel");

				while (r1s.hasMoreElements()) {
					h.peekElement(r1s.nextElement());
					// da ein referenziertes object geholt werden soll.
					// muss nur angegeben werden wie der Membername heisst.
					r1 = (Rule) h.getObject("R1", null, false);
					if (r1 == null)
						continue;
					
					Enumeration<?> r2s = h.getEnumeration("", null, true, "Rule");
					if (!r2s.hasMoreElements())
						r2s = h.getEnumeration("", null, true, "Regel");

					while (r2s.hasMoreElements()) {
						h.peekElement(r2s.nextElement());
						r2 = (Rule) h.getObject("R2", null, false);
						String bool = h.readAttr("bool");
						
						String duIndxStr = h.readAttr("duIndx");
						String pfIndxStr = h.readAttr("pfIndx");
						String caIndxStr = h.readAttr("caIndx");
						
						b = false;
						allOverlappings = null;
						if (bool.equals("true")) {
							b = true;
							allOverlappings = new Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>();
							Enumeration<?> overlappings = h.getEnumeration("",
									null, true, "Overlapping_Pair");
							while (overlappings.hasMoreElements()) {
								h.peekElement(overlappings.nextElement());
								// use the type set of the loaded grammar!
								Graph g = (Graph) h.getObject("", BaseFactory.theFactory().createGraph(
										this.pairsGrammar.getTypeSet()), true);
								while (h.readSubTag("Critical")) {
									GraphObject o = (GraphObject) h.getObject(
											"object", null, false);
									if (o != null)
										o.setCritical(true);
									h.close();
								}
								if (conflictKind == CriticalPair.CONFLICT) {
									/*
									 * OrdinaryMorphism first =
									 * BaseFactory.theFactory().createMorphism(r1.getLeft(),
									 * g); first.readMorphism(h);
									 * OrdinaryMorphism second =
									 * BaseFactory.theFactory().createMorphism(r2.getLeft(),
									 * g); second.readMorphism(h);
									 * allOverlappings.addElement(new
									 * Pair(first, second));
									 */
									Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
									p = readOverlappingMorphisms(h, r1, r2, g, conflictKind);
									allOverlappings.addElement(p);
								} else if (conflictKind == CriticalPair.TRIGGER_DEPENDENCY
										|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
									/*
									 * OrdinaryMorphism first =
									 * BaseFactory.theFactory().createMorphism(r1.getRight(),
									 * g); first.readMorphism(h);
									 * //System.out.println("ConflictDependCont.Xread::
									 * "+first.getSize()); OrdinaryMorphism
									 * second =
									 * BaseFactory.theFactory().createMorphism(r2.getLeft(),
									 * g); second.readMorphism(h);
									 * allOverlappings.addElement(new
									 * Pair(first, second));
									 */
									
									Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
									p = readOverlappingMorphisms(h, r1, r2, g, conflictKind);
									allOverlappings.addElement(p);
								}
								h.close();
							}
						}
						if (conflictKind == CriticalPair.CONFLICT) {
							Entry entry = null;
							if (this.layered) {
								this.lepc.addQuadruple(this.lepc.getExcludeContainer(),
										r1, r2, b, allOverlappings);
								entry = this.lepc.getEntry(r1, r2, true);
							}
							else if (this.priority) {
								this.pepc.addQuadruple(this.pepc.getExcludeContainer(),
										r1, r2, b, allOverlappings);
								entry = this.pepc.getEntry(r1, r2, true);
							}
							else {
								this.epc.addQuadruple(this.epc.getExcludeContainer(), r1,
										r2, b, allOverlappings);
								entry = this.epc.getEntry(r1, r2, true);
							}
							if (entry != null) {
								if (duIndxStr.indexOf(':')>=0)
									entry.setIndexOfDelUseProgress(duIndxStr);
								else
									entry.setIndexOfDelUseProgress(duIndxStr.concat(":"));
								if (pfIndxStr.indexOf(':')>=0)
									entry.setIndexOfProdForbidProgress(pfIndxStr);
								else
									entry.setIndexOfProdForbidProgress(pfIndxStr.concat(":"));
								if (caIndxStr.indexOf(':')>=0)
									entry.setIndexOfChangeAttrProgress(caIndxStr);
								else
									entry.setIndexOfChangeAttrProgress(caIndxStr.concat(":"));
							}
							
						} else if (conflictKind == CriticalPair.TRIGGER_DEPENDENCY
								|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
							Entry entry = null;
							if (this.layered)  {
								this.ldpc.addQuadruple(this.ldpc.getExcludeContainer(),
										r1, r2, b, allOverlappings);
								entry = this.ldpc.getEntry(r1, r2, true);
							}
							else if (this.priority) {
								this.pdpc.addQuadruple(this.pdpc.getExcludeContainer(),
										r1, r2, b, allOverlappings);
								entry = this.pdpc.getEntry(r1, r2, true);
							}
							else {
								this.dpc.addQuadruple(this.dpc.getExcludeContainer(), r1,
										r2, b, allOverlappings);
								entry = this.dpc.getEntry(r1, r2, true);
							}
							if (entry != null) {
								if (duIndxStr.indexOf(':')>=0)
									entry.setIndexOfDelUseProgress(duIndxStr);
								else
									entry.setIndexOfDelUseProgress(duIndxStr.concat(":"));
								if (pfIndxStr.indexOf(':')>=0)
									entry.setIndexOfProdForbidProgress(pfIndxStr);
								else
									entry.setIndexOfProdForbidProgress(pfIndxStr.concat(":"));
								if (caIndxStr.indexOf(':')>=0)
									entry.setIndexOfChangeAttrProgress(caIndxStr);
								else
									entry.setIndexOfChangeAttrProgress(caIndxStr.concat(":"));
							}
						}
						h.close();
					}
					h.close();
				}
				h.close();
			}

			tagnames.clear();
			tagnames.add("conflictFreeContainer");
			tagnames.add("dependencyFreeContainer");
			if (h.readSubTag(tagnames)) {
				
				Enumeration<?> r1s = h.getEnumeration("", null, true, "Rule");
				if (!r1s.hasMoreElements())
					r1s = h.getEnumeration("", null, true, "Regel");

				while (r1s.hasMoreElements()) {
					h.peekElement(r1s.nextElement());
					// da ein referenziertes object geholt werden soll.
					// muss nur angegeben werden wie der Membername heisst.
					r1 = (Rule) h.getObject("R1", null, false);
					
					Enumeration<?> r2s = h.getEnumeration("", null, true, "Rule");
					if (!r2s.hasMoreElements())
						r2s = h.getEnumeration("", null, true, "Regel");

					while (r2s.hasMoreElements()) {
						h.peekElement(r2s.nextElement());
						r2 = (Rule) h.getObject("R2", null, false);
						String bool = h.readAttr("bool");
						String status = h.readAttr("status");
						b = false;
						if (bool.equals("true"))
							b = true;

						if (conflictKind == CriticalPair.CONFLICT) {
							if (this.layered)
								this.lepc.addQuadruple(this.lepc
										.getConflictFreeContainer(), r1, r2, b,
										null);
							else if (this.priority)
								this.pepc.addQuadruple(this.pepc
										.getConflictFreeContainer(), r1, r2, b,
										null);
							else
								this.epc.addQuadruple(
										this.epc.getConflictFreeContainer(), r1, r2,
										b, null);
						} else if (conflictKind == CriticalPair.TRIGGER_DEPENDENCY
								|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
							ExcludePairContainer.Entry entry = null;
							if (this.layered) {
								this.ldpc.addQuadruple(this.ldpc
										.getConflictFreeContainer(), r1, r2, b,
										null);
								entry = this.ldpc.getEntry(r1, r2, true);
							}
							else if (this.priority) {
								this.pdpc.addQuadruple(this.pdpc
										.getConflictFreeContainer(), r1, r2, b,
										null);
								entry = this.pdpc.getEntry(r1, r2, true);
							}
							else {
								this.dpc.addQuadruple(
										this.dpc.getConflictFreeContainer(), r1, r2,
										b, null);
								entry = this.dpc.getEntry(r1, r2, true);
							}
							if (entry != null) {
								if (status.equals(String.valueOf("not_computable")))
									entry.setStatus(ExcludePairContainer.Entry.NOT_COMPUTABLE);
							}
						}

						if (!r1.isEnabled() || !r2.isEnabled()) {
							if (conflictKind == CriticalPair.CONFLICT) {
								if (this.layered)
									this.lepc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
								else if (this.priority)
									this.pepc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
								else
									this.epc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
							} else if (conflictKind == CriticalPair.TRIGGER_DEPENDENCY
									|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
								if (this.layered)
									this.ldpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
								else if (this.priority)
									this.pdpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
								else
									this.dpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
							}
						} else {
							if (r1.getLayer() != r2.getLayer()) {
								if (this.layered) {
									if (conflictKind == CriticalPair.CONFLICT)
										this.lepc.getEntry(r1, r2).state = ExcludePairContainer.Entry.NOT_RELATED;
									else if (conflictKind == CriticalPair.TRIGGER_DEPENDENCY
											|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
										this.ldpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.NOT_RELATED;
								}
							}
							if (r1.getPriority() != r2.getPriority()) {
								if (this.priority) {
									if (conflictKind == CriticalPair.CONFLICT)
										this.pepc.getEntry(r1, r2).state = ExcludePairContainer.Entry.NOT_RELATED;
									else if (conflictKind == CriticalPair.TRIGGER_DEPENDENCY
											|| conflictKind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY)
										this.pdpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.NOT_RELATED;
								}
							}
						}
						h.close();
					}
					h.close();
				}
				h.close();
			}

			tagnames.clear();
			tagnames.add("dependencyContainer");
			tagnames.add("dependenciesContainer");
			if (h.readSubTag(tagnames)) {
				if (!depsRead) {
					conflictKind = CriticalPair.TRIGGER_DEPENDENCY;
					boolean switchDependency = false;
					if (h.readAttr("kind").equals("trigger_switch_dependency")) {
						switchDependency = true;					
						conflictKind = CriticalPair.TRIGGER_SWITCH_DEPENDENCY;
					}
					
					if (this.layered)
						this.ldpc = (LayeredDependencyPairContainer) ParserFactory
								.createEmptyCriticalPairs(this.pairsGrammar,
										conflictKind,
										true);
					else if (this.priority)
						this.pdpc = (PriorityDependencyPairContainer) ParserFactory
								.createEmptyCriticalPairs(this.pairsGrammar,
										conflictKind,
										false);
					else
						this.dpc = (DependencyPairContainer) ParserFactory
								.createEmptyCriticalPairs(this.pairsGrammar,
										conflictKind,
										false);

					this.count++;

					// read rule sets
					tmpList = new Vector<Rule>();
					tmpList2 = new Vector<Rule>();
					if (!readRuleSet(h, "RuleSet", tmpList))
						tmpList = null;
					if (!readRuleSet(h, "RuleSet2", tmpList2))
						tmpList2 = null;
					
					Enumeration<?> r1s = h.getEnumeration("", null, true, "Rule");
					if (!r1s.hasMoreElements())
						r1s = h.getEnumeration("", null, true, "Regel");

					while (r1s.hasMoreElements()) {
						h.peekElement(r1s.nextElement());
						// da ein referenziertes object geholt werden soll.
						// muss nur angegeben werden wie der Membername heisst.
						r1 = (Rule) h.getObject("R1", null, false);
						Enumeration<?> 
						r2s = h.getEnumeration("", null, true, "Rule");
						if (!r2s.hasMoreElements())
							r2s = h.getEnumeration("", null, true, "Regel");

						while (r2s.hasMoreElements()) {
							h.peekElement(r2s.nextElement());
							r2 = (Rule) h.getObject("R2", null, false);
							String bool = h.readAttr("bool");
							b = false;
							allOverlappings = null;
							if (bool.equals("true")) {
								b = true;
								allOverlappings = new Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>();
								Enumeration<?> overlappings = h.getEnumeration("",
										null, true, "Overlapping_Pair");
								while (overlappings.hasMoreElements()) {
									h.peekElement(overlappings.nextElement());
									Graph g = (Graph) h.getObject("",
											new Graph(this.pairsGrammar.getTypeSet()),
											true);
									while (h.readSubTag("Critical")) {
										GraphObject o = (GraphObject) h
												.getObject("object", null,
														false);
										if (o != null)
											o.setCritical(true);
										h.close();
									}
									/*
									 * OrdinaryMorphism first =
									 * BaseFactory.theFactory().createMorphism(r1.getRight(),
									 * g); first.readMorphism(h);
									 * //System.out.println("ConflictDependCont.Xread::
									 * "+first.getSize()); OrdinaryMorphism
									 * second =
									 * BaseFactory.theFactory().createMorphism(r2.getLeft(),
									 * g); second.readMorphism(h);
									 * allOverlappings.addElement(new
									 * Pair(first, second));
									 */

									Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
									p = readOverlappingMorphisms(
											h, r1, r2, g, conflictKind);
									allOverlappings.addElement(p);

									h.close();
								}
							}
							if (this.layered)
								this.ldpc.addQuadruple(this.ldpc.getExcludeContainer(),
										r1, r2, b, allOverlappings);
							else if (this.priority)
								this.pdpc.addQuadruple(this.pdpc.getExcludeContainer(),
										r1, r2, b, allOverlappings);
							else
								this.dpc.addQuadruple(this.dpc.getExcludeContainer(), r1,
										r2, b, allOverlappings);
							h.close();
						}
						h.close();
					}
				}
				h.close();

				tagnames.clear();
				tagnames.add("conflictFreeContainer");
				tagnames.add("dependencyFreeContainer");
				if (h.readSubTag(tagnames)) {
					if (!depsRead) {
						Enumeration<?> 
						r1s1 = h.getEnumeration("", null, true, "Rule");
						if (!r1s1.hasMoreElements())
							r1s1 = h.getEnumeration("", null, true, "Regel");

						while (r1s1.hasMoreElements()) {
							h.peekElement(r1s1.nextElement());
							// da ein referenziertes object geholt werden soll.
							// muss nur angegeben werden wie der Membername
							// heisst.
							r1 = (Rule) h.getObject("R1", null, false);
							Enumeration<?> 
							r2s = h.getEnumeration("", null, true, "Rule");
							if (!r2s.hasMoreElements())
								r2s = h.getEnumeration("", null, true, "Regel");

							while (r2s.hasMoreElements()) {
								h.peekElement(r2s.nextElement());
								r2 = (Rule) h.getObject("R2", null, false);
								String bool = h.readAttr("bool");
								String status = h.readAttr("status");
								
								b = false;
								if (bool.equals("true"))
									b = true;
								ExcludePairContainer.Entry entry = null;
								
								if (this.layered) {
									this.ldpc.addQuadruple(this.ldpc
											.getConflictFreeContainer(), r1,
											r2, b, null);
									entry = this.ldpc.getEntry(r1, r2, true);
								}
								else if (this.priority) {
									this.pdpc.addQuadruple(this.pdpc
											.getConflictFreeContainer(), r1,
											r2, b, null);
									entry = this.pdpc.getEntry(r1, r2, true);
								}
								else {
									this.dpc.addQuadruple(this.dpc
											.getConflictFreeContainer(), r1,
											r2, b, null);
									entry = this.dpc.getEntry(r1, r2, true);
								}
								if (entry != null) {
									if (status.equals("not_computable"))
										entry.setStatus(ExcludePairContainer.Entry.NOT_COMPUTABLE);
								}
								
								if (!r1.isEnabled() || !r2.isEnabled()) {
									if (this.layered)
										this.ldpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
									else if (this.priority)
										this.pdpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
									else
										this.dpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.DISABLED;
								} else {
									if (r1.getLayer() != r2.getLayer()) {
										if (this.layered)
											this.ldpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.NOT_RELATED;
									}
									if (r1.getPriority() != r2.getPriority()) {
										if (this.priority)
											this.pdpc.getEntry(r1, r2).state = ExcludePairContainer.Entry.NOT_RELATED;
									}
								}
								h.close();
							}
							h.close();
						}
					}
					h.close();
				}
			}

			// read CPA rule graph
			readCPAGraph(h);

			// reset rule sets (rules and rules2) of container
			if (this.layered) {
				if (this.lepc != null)
					this.lepc.resetRules(tmpList, tmpList2);
				if (this.ldpc != null)
					this.ldpc.resetRules(tmpList, tmpList2);
			}
			else if (this.priority) {
				if (this.pepc != null)
					this.pepc.resetRules(tmpList, tmpList2);
				if (this.pdpc != null)
					this.pdpc.resetRules(tmpList, tmpList2);
			}
			else {
				if (this.epc != null)
					this.epc.resetRules(tmpList, tmpList2);
				if (this.dpc != null)
					this.dpc.resetRules(tmpList, tmpList2);
			}
			
			this.pairsGrammar.isReadyToTransform(true);
			
			readLayoutGrammar(h);
		}
	}

	protected void readCPAGraph(XMLHelper h) {
		this.cpaBasisGraph = new Graph();
		if (h.readSubTag("ConflictDependencyGraph")) {
			if (h.readSubTag("Types")) {
				Enumeration<?> en = h.getEnumeration("", null, true,
						"NodeType");
				while (en.hasMoreElements()) {
					h.peekElement(en.nextElement());
					Type t = this.cpaBasisGraph.getTypeSet().createNodeType(false);
					h.loadObject(t);
					h.close();
					if (t.getAdditionalRepr().equals(""))
						t.setAdditionalRepr("[NODE]");
				}

				en = h.getEnumeration("", null, true, "EdgeType");
				while (en.hasMoreElements()) {
					h.peekElement(en.nextElement());
					Type t = this.cpaBasisGraph.getTypeSet().createArcType(false);
					h.loadObject(t);
					h.close();
					if (t.getAdditionalRepr().equals(""))
						t.setAdditionalRepr("[EDGE]");
				}
				h.close();
			}

			h.getObject("", this.cpaBasisGraph, true);
			// improve old CPA Graph name
			String gn = this.cpaBasisGraph.getName();
			if (gn.contains("ofRules")) {
				this.cpaBasisGraph.setName("CPA_RuleGraph:Conflicts_(red)-Dependencies_(blue)");
			}
			
			this.cpaGraph = new EdGraph(this.cpaBasisGraph);
			this.cpaGraph.setCPAgraph(true);
			
			h.enrichObject(this.cpaGraph);
			h.close();

			Vector<EdType> cpaEdgeTypes = this.cpaGraph.getTypeSet().getArcTypes();
			for (int i = 0; i < cpaEdgeTypes.size(); i++) {
				EdType t = cpaEdgeTypes.get(i);
				if (t.getBasisType().getName().equals("c"))
					t.setColor(Color.RED);
				else if (t.getBasisType().getName().equals("d"))
					t.setColor(Color.BLUE);
				t.setAdditionalReprOfBasisType();
			}
		}	
	}
		
	protected void writeOverlapMorphisms(XMLHelper h, Rule r1, Rule r2,
			Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> overlapping) {

		Pair<OrdinaryMorphism, OrdinaryMorphism> p1 = overlapping.first;
		OrdinaryMorphism first = p1.first;
		
		// write first (left) overlap morphism
		h.openSubTag("Morphism");
		h.addAttr("name", first.getName());
		
		if (first.getTarget().getName().indexOf("deliver-delete-dependency")>=0
				&& first.getSource() == r2.getLeft()) {
			h.addAttr("source", "LHS_R2");
		}
		else if (first.getTarget().getName().indexOf("forbid-produce-dependency")>=0
				&& first.getSource() == r2.getRight()) {
			h.addAttr("source", "RHS_R2");
		}
		else if (first.getTarget().getName().indexOf("change-change-dependency")>=0
				&& first.getSource() == r2.getLeft()) {
			h.addAttr("source", "LHS_R2");
		}
		else if (first.getTarget().getName().indexOf("-switch-")>=0) { // old code
			if (first.getSource() == r2.getLeft())
				h.addAttr("source", "LHS_R2");
			else if (first.getSource() == r2.getRight())
				h.addAttr("source", "RHS_R2"); 
		} 
		else if (first.getTarget().getName().indexOf("produceEdge-deleteNode-")>=0) {
			if (first.getSource() == r2.getLeft())
				h.addAttr("source", "LHS_R2_1");
		}
		else {
			if (first.getSource() == r1.getLeft())
				h.addAttr("source", "LHS");
			else if (first.getSource() == r1.getRight())
				h.addAttr("source", "RHS");		
		}
		
		Enumeration<GraphObject> e = first.getDomain();
		while (e.hasMoreElements()) {
			GraphObject s = e.nextElement();
			h.openSubTag("Mapping");
			h.addObject("orig", s, false);
			h.addObject("image", first.getImage(s), false);
			h.close();
		}
		h.close();

		// write second (right) overlap morphism
		OrdinaryMorphism second = p1.second;
		Pair<OrdinaryMorphism, OrdinaryMorphism> p2 = overlapping.second;
		if (p2 == null) {			
			h.openSubTag("Morphism");
			h.addAttr("name", second.getName());
			
			if (second.getSource() == r2.getLeft()) 
				h.addAttr("source", "LHS");
			else if (second.getSource() == r1.getRight())
				h.addAttr("source", "RHS_R1");
			else if (second.getSource() == r1.getLeft()) 
				h.addAttr("source", "LHS_R1_2");
			
			e = second.getDomain();
			while (e.hasMoreElements()) {
				GraphObject s = e.nextElement();
				h.openSubTag("Mapping");
				h.addObject("orig", s, false);
				h.addObject("image", second.getImage(s), false);
				h.close();
			}
			h.close();
			
		} else {
			// handle PACs
			if (p2.second != null
					&& p2.first.getTarget() == p2.second.getSource()) {
//				OrdinaryMorphism morphL2iso = p2.first;
				OrdinaryMorphism morphL2PACiso = p2.second;

				h.openSubTag("Morphism");
				h.addAttr("name", second.getName());
								
				if (second.getSource() == r2.getLeft()) {
					h.addAttr("source", "PAC+LHS");
				}
				else if (second.getSource() == r1.getRight())  {
					h.addAttr("source", "PAC+RHS_R1");
				}
				
//				System.out.println("r2: "+r2.getName()+"   PACs: "+r2.getPACsList());
				final Vector<OrdinaryMorphism>
				pacs = new Vector<OrdinaryMorphism>(r2.getPACsList().size());
				
				final Hashtable<GraphObject, GraphObject> 
				pacgo2go = new Hashtable<GraphObject, GraphObject>();
				
				Iterator<Node> en = second.getTarget().getNodesSet().iterator();
				while (en.hasNext()) {
					GraphObject go = en.next();
					if (!second.getInverseImage(go).hasMoreElements()) {
						if (morphL2PACiso.getInverseImage(go).hasMoreElements()) {
							GraphObject o = morphL2PACiso
									.getInverseImage(go).nextElement();
							OrdinaryMorphism pac = getPAC(r2, o, pacgo2go);
							if (pac != null) {
								if (!pacs.contains(pac)) {
									pacs.add(pac);
								}
							}
						}
					} else {
						GraphObject s = second
								.getInverseImage(go).nextElement();
						h.openSubTag("Mapping");
						h.addObject("orig", s, false);
						h.addObject("image", go, false);
						h.close();
					}
				}
				Iterator<Arc> ea = second.getTarget().getArcsSet().iterator();
				while (ea.hasNext()) {
					GraphObject go = ea.next();
					if (!second.getInverseImage(go).hasMoreElements()) {
						if (morphL2PACiso.getInverseImage(go).hasMoreElements()) {
							GraphObject o = morphL2PACiso
									.getInverseImage(go).nextElement();
							OrdinaryMorphism pac = getPAC(r2, o, pacgo2go);
							if (pac != null) {
								if (!pacs.contains(pac)) {
									pacs.add(pac);
								}
							}
						}
					} else {
						GraphObject s = second
								.getInverseImage(go).nextElement();
						h.openSubTag("Mapping");
						h.addObject("orig", s, false);
						h.addObject("image", go, false);
						h.close();
					}
				}
				
				for (int i = 0; i < pacs.size(); i++) {
					OrdinaryMorphism pac = pacs.get(i);
					en = pac.getTarget().getNodesSet().iterator();
					while (en.hasNext()) {
						GraphObject s = en.next();
						if (pacgo2go.get(s) == null)
							continue;
						GraphObject t = morphL2PACiso.getImage(pacgo2go.get(s));
						// GraphObject s belongs to a PAC
						// GraphObject t belongs to the overlapgraph
						if (t != null) {
							h.openSubTag("Mapping");
							h.addAttr("pacname", pac.getName());
							h.addObject("orig", s, false);
							h.addObject("image", t, false);
							h.close();
						}
					}
					ea = pac.getTarget().getArcsSet().iterator();
					while (ea.hasNext()) {
						GraphObject s = ea.next();
						if (pacgo2go.get(s) == null)
							continue;
						GraphObject t = morphL2PACiso.getImage(pacgo2go.get(s));
						// GraphObject s belongs to a PAC
						// GraphObject t belongs to the overlapgraph
						if (t != null) {
							h.openSubTag("Mapping");
							h.addAttr("pacname", pac.getName());
							h.addObject("orig", s, false);
							h.addObject("image", t, false);
							h.close();
						}
					}
				}
				h.close();
			} else {
				// here handle NAC
				OrdinaryMorphism morphL2iso = p2.first;
				OrdinaryMorphism morphNACiso = p2.second;
								
				h.openSubTag("Morphism");
				h.addAttr("name", second.getName());
				
				if (morphL2iso.getSource() == r1.getRight()
						|| morphL2iso.getSource() == r2.getRight())
					h.addAttr("source", "NAC+RHS");
				else
					h.addAttr("source", "NAC+LHS");
				
				// second.target is N2 = NAC+L2
				e = second.getDomain();
				while (e.hasMoreElements()) {
					GraphObject src = e.nextElement();
					GraphObject t = second.getImage(src);
					GraphObject s = null;
					if (morphL2iso.getInverseImage(src).hasMoreElements())
						s = morphL2iso.getInverseImage(src)
								.nextElement();
					else if (morphNACiso.getInverseImage(src).hasMoreElements())
						s = morphNACiso.getInverseImage(src)
								.nextElement();
					if (s != null) {
						h.openSubTag("Mapping");
						h.addObject("orig", s, false);
						h.addObject("image", t, false);
						h.close();
					}
				}
				h.close();
			}
		}
	}

	private OrdinaryMorphism getPAC(
			final Rule r, 
			final GraphObject goOfPAC,
			final Hashtable<GraphObject, GraphObject> pacgo2go) {
				 
//		System.out.println("getPAC::  "+ goOfPAC.getContextUsage());
		final List<OrdinaryMorphism> pacs = r.getPACsList();
		for (int l=0; l<pacs.size(); l++) {
			final OrdinaryMorphism pac = pacs.get(l);		
			Iterator<?> elems = pac.getTarget().getNodesSet().iterator();
			while (elems.hasNext()) {
				GraphObject go = (GraphObject) elems.next();
				if (goOfPAC.getContextUsage() == go.hashCode()) {
					pacgo2go.put(go, goOfPAC);
//					System.out.println("PAC: "+pac.getName() +"  FOUND  ");
					return pac;
				}
			}
			elems = pac.getTarget().getArcsSet().iterator();
			while (elems.hasNext()) {
				GraphObject go = (GraphObject) elems.next();
				if (goOfPAC.getContextUsage() == go.hashCode()) {
					pacgo2go.put(go, goOfPAC);
					return pac;
				}
			}
		}
		return null;
	}

	protected Pair<OrdinaryMorphism, OrdinaryMorphism> readOldOverlappingMorphisms(XMLHelper h, Rule r1, Rule r2,
			String firstName, Graph overlapGraph, int kind) {
		OrdinaryMorphism first = null;
		OrdinaryMorphism second = null;
		if (kind == CriticalPair.CONFLICT) {
			first = BaseFactory.theFactory().createMorphism(
					r1.getLeft(), overlapGraph);
			second = BaseFactory.theFactory().createMorphism(
					r2.getLeft(), overlapGraph);
		}
		else if (kind == CriticalPair.TRIGGER_DEPENDENCY 
				|| kind == CriticalPair.TRIGGER_SWITCH_DEPENDENCY) {
			if (overlapGraph.getName().indexOf("-switch-")>=0) {
				first = BaseFactory.theFactory().createMorphism(
						r2.getLeft(), overlapGraph);

				second = BaseFactory.theFactory().createMorphism(
						r1.getRight(), overlapGraph);
			}
			else {			
				first = BaseFactory.theFactory().createMorphism(
						r1.getRight(), overlapGraph);
					
				second = BaseFactory.theFactory().createMorphism(
						r2.getLeft(), overlapGraph);
			}
		}
		
		if (first != null) {
			first.setName(firstName.replaceAll(" ", ""));
			while (h.readSubTag("Mapping")) {
				GraphObject o = (GraphObject) h.getObject("orig", null, false);
				GraphObject i = (GraphObject) h.getObject("image", null, false);
				if (o != null && i != null) {
					try {
						first.addMapping(o, i);
					} catch (BadMappingException ex) {}
				}
				h.close();
			}
		}
		h.close();
		
		if (h.readSubTag("Morphism")) {
			if (second != null) {
				String name = h.readAttr("name");
				second.setName(name.replaceAll(" ", ""));
				while (h.readSubTag("Mapping")) {
					GraphObject o = (GraphObject) h.getObject("orig", null, false);
					GraphObject i = (GraphObject) h.getObject("image", null, false);
					if (o != null && i != null) {
						try {
							second.addMapping(o, i);
						} catch (BadMappingException ex) {}
					}
					h.close();
				}
			}
			h.close();
		}
		return new Pair<OrdinaryMorphism, OrdinaryMorphism>(first, second);
	}

	protected Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
	readOverlappingMorphisms(XMLHelper h, Rule r1,
			Rule r2, Graph overlapGraph, int kind) {
//		System.out.println("ConflictsDependenciesContainer.readOverlappingMorphisms...");
		// read first overlap morphism
		OrdinaryMorphism first = null;
		OrdinaryMorphism second = null;
		Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
		result = null;
		Pair<OrdinaryMorphism, OrdinaryMorphism> p = null, p1 = null, p2 = null;
		
//		 read first overlap morphism
		if (h.readSubTag("Morphism")) {
			String name = h.readAttr("name");
			String source = h.readAttr("source");
			if (source.equals("")) {
				p = readOldOverlappingMorphisms(h, r1, r2, name, overlapGraph,
						kind);
				return new Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>(p, null);
			}
			
			if (source.equals("LHS"))
				first = BaseFactory.theFactory().createMorphism(r1.getLeft(),
						overlapGraph);
			else if (source.equals("RHS"))
				first = BaseFactory.theFactory().createMorphism(r1.getRight(),
						overlapGraph);
			else if (source.equals("LHS_R2"))
				first = BaseFactory.theFactory().createMorphism(r2.getLeft(),
						overlapGraph);
			else if (source.equals("RHS_R2"))
				first = BaseFactory.theFactory().createMorphism(r2.getRight(),
						overlapGraph);
			else if (source.equals("LHS_R2_1"))
				first = BaseFactory.theFactory().createMorphism(r2.getLeft(),
						overlapGraph);
			
			if (first != null) {
				first.setName(name.replaceAll(" ", ""));
				while (h.readSubTag("Mapping")) {
					GraphObject o = (GraphObject) h.getObject("orig", null, false);
					GraphObject i = (GraphObject) h.getObject("image", null, false);
					if (o != null && i != null) {
						try {
							first.addMapping(o, i);
						} catch (BadMappingException ex) {
							System.out.println("ConflictDependencyContainer.readOverlappingMorphisms:: (second) "+ex.getLocalizedMessage());
						}						
					}
					h.close();
				}
			}
			h.close();
		}
		
		// read second overlap morphism
		if (h.readSubTag("Morphism")) {
			OrdinaryMorphism morphL2iso = null;
			OrdinaryMorphism morphNACiso = null;
			OrdinaryMorphism morphL2PACiso = null;
			final Hashtable<GraphObject, GraphObject> 
			orig2copy = new Hashtable<GraphObject, GraphObject>();

			String name = h.readAttr("name");
			String source = h.readAttr("source");
			if (source.equals("LHS")) {
				second = BaseFactory.theFactory().createMorphism(r2.getLeft(),
						overlapGraph);
			}
			else if (source.equals("RHS_R1")) {
				second = BaseFactory.theFactory().createMorphism(r1.getRight(),
						overlapGraph);
			}
			else if (source.equals("LHS_R1_2")) {
				second = BaseFactory.theFactory().createMorphism(r1.getLeft(),
						overlapGraph);
			}
			else if (source.equals("NAC+LHS")) {
				OrdinaryMorphism nac = null;
				
				if (overlapGraph.getName().indexOf("forbid-switch-")>=0
						|| overlapGraph.getName().indexOf("forbid-produce-dependency")>=0) {
					final List<OrdinaryMorphism> nacs = r1.getNACsList();
					for (int i=0; i<nacs.size(); i++) {
						OrdinaryMorphism n = nacs.get(i);
						if (overlapGraph.getHelpInfoAboutNAC().indexOf(n.getName()) != -1) {
							nac = n;
							break;
						}
					}
					Pair<OrdinaryMorphism, OrdinaryMorphism> 
					NAC_RHS = BaseFactory.theFactory().extendRightGraphByNAC(r1, nac);
					if (NAC_RHS != null) {
						morphL2iso = NAC_RHS.first;					
						morphNACiso = NAC_RHS.second;
					}
				} else {
					final List<OrdinaryMorphism> nacs = r2.getNACsList();
					for (int i=0; i<nacs.size(); i++) {
						OrdinaryMorphism n = nacs.get(i);					
						if (overlapGraph.getHelpInfoAboutNAC().indexOf(n.getName()) != -1) {
							nac = n;
							break;
						}
					}
					Pair<OrdinaryMorphism, OrdinaryMorphism> 
					NAC_LHS = BaseFactory.theFactory().extendLeftGraphByNAC(r2, nac);
					if (NAC_LHS != null) {
						morphL2iso = NAC_LHS.first;					
						morphNACiso = NAC_LHS.second;	
					}
				}
				if (morphL2iso != null) {
					Graph N2 = morphL2iso.getTarget();
					second = BaseFactory.theFactory().createMorphism(N2,
							overlapGraph);
				}
			} else if (source.equals("NAC+RHS")) {
				OrdinaryMorphism nac = null;

				if (overlapGraph.getName().indexOf("forbid-switch-")>=0
						|| overlapGraph.getName().indexOf("forbid-produce-dependency")>=0) {
					
					for (Enumeration<OrdinaryMorphism> e = r1.getNACs(); e.hasMoreElements();) {
						OrdinaryMorphism n = e.nextElement();
						if (overlapGraph.getHelpInfoAboutNAC().indexOf(n.getName()) != -1) {
							nac = n;
							break;
						}
					}
					Pair<OrdinaryMorphism, OrdinaryMorphism> 
					NAC_RHS = BaseFactory.theFactory().extendRightGraphByNAC(r1, nac);
					if (NAC_RHS != null) {
						morphL2iso = NAC_RHS.first;					
						morphNACiso = NAC_RHS.second;
					}
				} 
				else {
					for (Enumeration<OrdinaryMorphism> e = r2.getNACs(); e.hasMoreElements();) {
						OrdinaryMorphism n = e.nextElement();
						if (overlapGraph.getHelpInfoAboutNAC().indexOf(n.getName()) != -1) {
							nac = n;
							break;
						}
					}
					Pair<OrdinaryMorphism, OrdinaryMorphism> 
					NAC_LHS = BaseFactory.theFactory().extendLeftGraphByNAC(r2, nac);
					if (NAC_LHS != null) {
						morphL2iso = NAC_LHS.first;					
						morphNACiso = NAC_LHS.second;
					}
				}
				if (morphL2iso != null) {
					Graph N2 = morphL2iso.getTarget();
					second = BaseFactory.theFactory().createMorphism(N2,
							overlapGraph);
				}
			} 
			
			else if (source.equals("PAC+LHS")) {
				morphL2iso = r2.getLeft().isomorphicCopy();
				if (morphL2iso != null) {
					morphL2PACiso = BaseFactory.theFactory().createMorphism(
										morphL2iso.getTarget(), overlapGraph);
				}
				// NOTE: r2.getLeft() == morphL2iso.getSource()
				second = BaseFactory.theFactory().createMorphism(r2.getLeft(),
						overlapGraph);
			} 
			else if (source.equals("PAC+RHS_R1")) {
//				System.out.println("PAC+RHS_R1");
				morphL2iso = r1.getRight().isomorphicCopy();
				if (morphL2iso != null) {
					morphL2PACiso = BaseFactory.theFactory().createMorphism(
										morphL2iso.getTarget(), overlapGraph);
				}
				// NOTE: r1.getRight() == morphL2iso.getSource()
				second = BaseFactory.theFactory().createMorphism(r1.getRight(),
						overlapGraph);
			}
			
			if (second != null) {
				second.setName(name.replaceAll(" ", ""));
				while (h.readSubTag("Mapping")) {
					String pacname = h.readAttr("pacname");
					GraphObject o = (GraphObject) h.getObject("orig", null,
							false);
					GraphObject i = (GraphObject) h.getObject("image", null,
							false);
					if (o != null && i != null) {
						if (source.equals("LHS")
								|| source.equals("RHS_R1")
								|| source.equals("LHS_R1_2")) {
							second.addMapping(o, i);
						}
						else if (source.equals("NAC+LHS")) {
							GraphObject s = null;
							if (morphL2iso != null)
								s = morphL2iso.getImage(o);
							if (s == null && morphNACiso != null)
								s = morphNACiso.getImage(o);
							if (s != null) {
								try {
									second.addMapping(s, i);
								} catch (BadMappingException ex) {
									System.out.println("ConflictDependencyContainer.readOverlappingMorphisms:: (second) "+ex.getLocalizedMessage());
								}
							}
						} else if (source.equals("NAC+RHS")) {
							GraphObject s = null;
							if (morphL2iso != null)
								s = morphL2iso.getImage(o);
							if (s == null && morphNACiso != null)
								s = morphNACiso.getImage(o);
							if (s != null) {
								try {
									second.addMapping(s, i);
								} catch (BadMappingException ex) {
									System.out.println("ConflictDependencyContainer.readOverlappingMorphisms:: (second) "+ex.getLocalizedMessage());
								}
							}
						} 
						
						else if (source.equals("PAC+LHS")) {
							if (pacname == null || pacname.length() == 0) {
								try {
									second.addMapping(o, i);
								} catch (BadMappingException ex) {
									System.out.println("ConflictDependencyContainer.readOverlappingMorphisms:: (second) "+ex.getLocalizedMessage());
								}	
								if (o.isNode() && morphL2iso != null)
									orig2copy.put(o, morphL2iso.getImage(o));
							} else {
								if (o.isNode() && morphL2iso != null) {
									try {
										Node n = morphL2iso.getTarget()
												.copyNode((Node) o);
										try {
											if (morphL2PACiso != null) {
												morphL2PACiso.addMapping(n, i);
												orig2copy.put(o, n);
											}
										} catch (BadMappingException bme) {
										}
									} catch (TypeException te) {
									}
								} else {
									try {
										Node src = (Node) orig2copy
												.get(((Arc) o).getSource());
										Node tar = (Node) orig2copy
												.get(((Arc) o).getTarget());
										if (src != null && tar != null
												&& morphL2iso != null) {
											Arc a = morphL2iso.getTarget()
													.copyArc((Arc) o, src, tar);
											try {
												if (morphL2PACiso != null)
													morphL2PACiso.addMapping(a,
															i);
											} catch (BadMappingException bme) {
											}
										}
									} catch (TypeException te) {
									}
								}
							}
						}
						else if (source.equals("PAC+RHS_R1")) {
							if (pacname == null || pacname.length() == 0) {
								try {
									second.addMapping(o, i);
//									System.out.println("PAC+RHS_R1:: second.addMapping   DONE");
								} catch (BadMappingException ex) {
									System.out.println("ConflictDependencyContainer.readOverlappingMorphisms:: (second) "+ex.getLocalizedMessage());
								}
							}
						}
					}
					h.close();
				}
				
				if (source.equals("NAC+LHS")
						|| source.equals("NAC+RHS")) {
					p2 = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
							morphL2iso, morphNACiso);
				} else if (source.equals("PAC+LHS")
						|| source.equals("PAC+RHS_R1")) {
					p2 = new Pair<OrdinaryMorphism, OrdinaryMorphism>(
							morphL2iso, morphL2PACiso);
				} 
				
			}
			h.close();
		}
		if (first != null && second != null) {
			p1 = new Pair<OrdinaryMorphism, OrdinaryMorphism>(first, second);
			result = new Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>(p1, p2);
		}

		return result;
	}

	
	/*
	private OrdinaryMorphism extendLeftGraph(OrdinaryMorphism isoLeft,
			OrdinaryMorphism nac) {
		if (isoLeft == null || nac == null)
			return null;
		Graph extLeft = isoLeft.getTarget();
		OrdinaryMorphism isoNAC = BaseFactory.theFactory().createMorphism(
				nac.getTarget(), extLeft);
		Hashtable<Node, Node> tmp = new Hashtable<Node, Node>(5);
		Iterator<?> e = nac.getTarget().getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			if (!nac.getInverseImage(o).hasMoreElements()) {
				try {
					Node n = extLeft.copyNode((Node) o);
					tmp.put((Node) o, n);
					isoNAC.addMapping(o, n);
				} catch (TypeException ex) {
				}
			} else {
				GraphObject oi = isoLeft.getImage(nac
						.getInverseImage(o).nextElement());
//				if (oi != null) {
					if(o.getType().isParentOf(oi.getType())) 
						isoNAC.addMapping(o, isoLeft.getImage(nac
								.getInverseImage(o).nextElement()));
					else if(o.getType().isChildOf(oi.getType())) {
//						System.out.println(o.getType().getName()+"  !=  "+oi.getType().getName());
						isoNAC.addChild2ParentMapping(o, isoLeft.getImage(nac
								.getInverseImage(o).nextElement()));
					}
//				}
			}
		}
		e = nac.getTarget().getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			if (!nac.getInverseImage(o).hasMoreElements()) {
				Node src = tmp.get(((Arc) o).getSource());
				if (src == null) {
					src = (Node) isoLeft.getImage(nac.getInverseImage(
							((Arc) o).getSource()).nextElement());
				}
				Node tar = tmp.get(((Arc) o).getTarget());
				if (tar == null) {
					tar = (Node) isoLeft.getImage(nac.getInverseImage(
							((Arc) o).getTarget()).nextElement());
				}
				if (src != null && tar != null) {
					try {					
						Arc a = extLeft.copyArc((Arc) o, src, tar);
						isoNAC.addMapping(o, a);
					} catch (TypeException ex) {}
				}
			} else {
				GraphObject oi = isoLeft.getImage(nac
						.getInverseImage(o).nextElement());
				if (oi != null) {
					isoNAC.addMapping(o, isoLeft.getImage(nac
							.getInverseImage(o).nextElement()));
				}
			}

		}
		return isoNAC;
	}
	
	
	private OrdinaryMorphism extendRightGraph(OrdinaryMorphism isoRight,
			OrdinaryMorphism nac) {
		if (isoRight == null || nac == null)
			return null;
		Graph extRight = isoRight.getTarget();
		OrdinaryMorphism isoNAC = BaseFactory.theFactory().createMorphism(
				nac.getTarget(), extRight);
		Hashtable<Node, Node> tmp = new Hashtable<Node, Node>(5);
		Iterator<?> e = nac.getTarget().getNodesSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			if (!nac.getInverseImage(o).hasMoreElements()) {
				try {
					Node n = extRight.copyNode((Node) o);
					tmp.put((Node) o, n);
					isoNAC.addMapping(o, n);
				} catch (TypeException ex) {}
			} else {
				GraphObject oi = isoRight.getImage(nac
						.getInverseImage(o).nextElement());
//				if (oi != null) {
					if(o.getType().isParentOf(oi.getType())) 
						isoNAC.addMapping(o, isoRight.getImage(nac
								.getInverseImage(o).nextElement()));
					else if(o.getType().isChildOf(oi.getType())) {
//						System.out.println(o.getType().getName()+"  !=  "+oi.getType().getName());
						isoNAC.addChild2ParentMapping(o, isoRight.getImage(nac
								.getInverseImage(o).nextElement()));
					}
//				}
			}
		}
		e = nac.getTarget().getArcsSet().iterator();
		while (e.hasNext()) {
			GraphObject o = (GraphObject) e.next();
			if (!nac.getInverseImage(o).hasMoreElements()) {
				Node src = tmp.get(((Arc) o).getSource());
				if (src == null) {
					src = (Node) isoRight.getImage(nac.getInverseImage(
							((Arc) o).getSource()).nextElement());
				}
				Node tar = tmp.get(((Arc) o).getTarget());
				if (tar == null) {
					tar = (Node) isoRight.getImage(nac.getInverseImage(
							((Arc) o).getTarget()).nextElement());
				}
				if (src != null && tar != null) {
					try {					
						Arc a = extRight.copyArc((Arc) o, src, tar);
						isoNAC.addMapping(o, a);
					} catch (TypeException ex) {}
				}
			} else {
				GraphObject oi = isoRight.getImage(nac
						.getInverseImage(o).nextElement());
				if (oi != null) {
					isoNAC.addMapping(o, isoRight.getImage(nac
							.getInverseImage(o).nextElement()));
				}
			}

		}
		return isoNAC;
	}
	*/
}
