/**
 * 
 */
package agg.parser;

import java.util.Collection;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import agg.attribute.AttrType;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TestStep;
import agg.xt_basis.TypeException;

/**
 * This class computes critical matches of two rules at a concrete graph.
 * The two rules and a concret host graph graph are specified 
 * by the class constructor.
 * In case of rule application conflicts 
 * a vector with pairs of tables with critical matches will be computed.
 * The first table contains the match mappings from the LHS
 * of the first rule into the graph and
 * the second table contains the mappings from the LHS
 * of the second rule into the same graph.
 * @author olga
 *
 */
public class CriticalRulePairAtGraph extends ExcludePair {
	
	private Rule rule1, rule2;
	
	private Graph graph;
	
	final private Vector<Hashtable<GraphObject, GraphObject>>
	r1Matches = new Vector<Hashtable<GraphObject, GraphObject>>();
	
	final private Vector<Hashtable<GraphObject, GraphObject>>
	r2Matches = new Vector<Hashtable<GraphObject, GraphObject>>();
	
	private Hashtable<Vector<GraphObject>, Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> 
	jointlyMatches;
	
	public CriticalRulePairAtGraph(
			final Rule r1, 
			final Rule r2,
			final Graph g) {
		this.rule1 = r1;
		this.rule2 = r2;
		this.graph = g;
		
//		if (!this.rule1.isReadyToTransform())
//			System.out.println(this.rule1.getName()+"  is not ready to transform!");
//		if (!this.rule2.isReadyToTransform())
//			System.out.println(this.rule2.getName()+"  is not ready to transform!");
	}
	
	/**
	 * Computes critical matches of two rules at a concrete graph.
	 * Two rules and a concret host graph graph are specified 
	 * by the class constructor.
	 * In case of rule application conflicts 
	 * returns a vector with pairs of tables with critical matches.
	 * The first table contains the match mappings from the LHS
	 * of the first rule into the graph and
	 * the second table contains the mappings from the LHS
	 * of the second rule into the same graph.
	 * Returns null, if the specified two rules can be applied at the host graph
	 * without any conflicts.
	 * @return a vector with pairs of tables or null
	 */
	public Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> isCriticalAtGraph() {
//		System.out.println("CriticalRulePairAtGraph.isCriticalAtGraph  ");
		
		if (this.essential) {
			// disable Type Multiplicity, Graph Constraints and NACs checking
			this.disableConstraints();
		}
		
		// check global NACs of r2 before all other checks
		// if LHS of r1 satisfy a global NAC of r2,
		// where the NAC contains nodes with attrs are not set,
		// then this rule pair is non-critical!
		if (!checkGlobalNACsOfRule2(this.rule1, this.rule2)) {
//			System.out.println("CriticalRulePairAtGraph.isExclude:: a global NAC of rule2  FAILED!");
			System.out.println("*** CriticalRulePairAtGraph.getCriticalForGraph::  [ "
					+ this.rule1.getName() + ", " + this.rule2.getName()
					+ " ]  non-critical.");
			return null;
		}
				
		// Prepare and perform rule analysis
		
		// fill typesTG_L2 with types used in LHS of r2
		fillTypeSubset(this.rule2.getLeft(), this.typesTG_L2);
		
		// fill typesTG_PAC2 with types from typesTG_L2 
		// and types used in PACs of r2
		if (this.withPACs)
			getTypeSubsetLeft_PACs(this.rule2, this.typesTG_L2, this.typesTG_PAC2);
		
		// fill typesTG_NAC2 with types from typesTG_L2 
		// and types used in NACs of r2
		if (this.withNACs)
			getTypeSubsetLeft_NACs(this.rule2, this.typesTG_L2, this.typesTG_NAC2);

		// compute left context, boundary, preserved and delete of r1
		if (this.withPACs)
			computeLeftC_B_K(this.rule1, this.contextC1_L1, this.boundB1_L1, this.preservedK1_L1,
					this.delete, this.typesTG_PAC2);
		else
			computeLeftC_B_K(this.rule1, this.contextC1_L1, this.boundB1_L1, this.preservedK1_L1,
					this.delete, this.typesTG_L2);
		
		// compute right context, boundary, preserved and produce of r1
		if (this.withNACs)
			computeRightC_B_K(this.rule1, this.contextC1_R1, this.boundB1_R1, this.preservedK1_R1,
					this.produce, this.typesTG_NAC2);
		else
			computeRightC_B_K(this.rule1, this.contextC1_R1, this.boundB1_R1, this.preservedK1_R1,
					this.produce, this.typesTG_L2);
		
		findValidMatches(this.rule1, this.graph, this.r1Matches);
		findValidMatches(this.rule2, this.graph, this.r2Matches);
		
//		System.out.println("### r1Matches: "+r1Matches.size());		
//		System.out.println("### r2Matches: "+r2Matches.size());

		boolean 
		canOverlapLHS1withLHS2 = canMatchConstantAttributeLHS1intoLHS2(this.rule1, this.rule2);

		final Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>
		resultOverlappings = new Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>();

		// check delete-use conflicts
		Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>
		deleteUseOverlappings = null;
		if (!this.contextC1_L1.isEmpty() && canOverlapLHS1withLHS2 && !this.stop) {
			
			deleteUseOverlappings = getDeleteUseConflictsAtGraph(this.rule1, this.rule2);
			
			if (deleteUseOverlappings != null) {
				for (int i = 0; i < deleteUseOverlappings.size(); i++) 
					resultOverlappings.add(deleteUseOverlappings.elementAt(i));
			}
		}
		
		// check produce-forbid conflicts
		Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>
		produceForbidOverlappings = null;
		if (this.withNACs && (this.complete || resultOverlappings.isEmpty())
				&& !this.contextC1_R1.isEmpty() && !this.stop) {
			
			produceForbidOverlappings = getProduceForbidConflictsAtGraph(this.rule1, this.rule2);
		
			if (produceForbidOverlappings != null) {
				for (int i = 0; i < produceForbidOverlappings.size(); i++) 
					resultOverlappings.add(produceForbidOverlappings.elementAt(i));
			}
		}
					
		// check attribute conflicts
		Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>
		changeAttributeOverlappings = null;
		if ((this.complete || resultOverlappings.isEmpty())
				// && canOverlapLHS1withLHS2
				&& !this.stop) {
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> 
			changedAttrsL1 = new Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>>();
			// fill preservedChanged vector
			ruleChangesAttributes(this.rule1, this.rule2, this.contextC1_L1, this.boundB1_L1,
					this.preservedK1_L1, changedAttrsL1, this.typesTG_NAC2);
			
			final Vector<GraphObject> 
			preservedL2_K2 = new Vector<GraphObject>(5);
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> 
			changedAttrsL2 = new Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>>();			
			ruleChangesAttributes(this.rule2, preservedL2_K2, changedAttrsL2);					
						
			if (ruleRestrictsAttributes(true, this.rule2, changedAttrsL2, changedAttrsL1)) {			
				
				changeAttributeOverlappings = getChangeAttributeConflictsAtGraph(
						this.rule1, this.rule2, changedAttrsL1, changedAttrsL2);
				
				if (changeAttributeOverlappings != null) {					
					for (int i = 0; i < changeAttributeOverlappings.size(); i++) 
						resultOverlappings.add(changeAttributeOverlappings.elementAt(i));
				}				
			}
		}
				
		if (this.essential) {
			// enable Type Multiplicity, Graph Constraints and NACs checking
			this.enableConstraints();
		}
		
		return resultOverlappings;
	}

	private void findValidMatches(
			final Rule r, 
			final Graph g,
			final Vector<Hashtable<GraphObject, GraphObject>> ruleMatches) {
//		System.out.println("### findValidMatches: "+r.getName());
		
		Match m = BaseFactory.theFactory().createMatch(r, g);
		m.setCompletionStrategy(super.strategy, true);
		
//		super.strategy.showProperties();		
//		((VarTuple)m.getAttrContext().getVariables()).showVariables();
//		((CondTuple)m.getAttrContext().getConditions()).showConditions();
		
		while (m.nextCompletion()) {
			if (m.isValid()) {
				Hashtable<GraphObject, GraphObject> 
				mTable = new Hashtable<GraphObject, GraphObject>();
				
				Enumeration<GraphObject> dom = m.getDomain();
				while (dom.hasMoreElements()) {
					GraphObject o = dom.nextElement();
					mTable.put(o, m.getImage(o));
				}
				
				ruleMatches.add(mTable);
			} else {
//				System.out.println("### match failed: "+m.getErrorMsg());
			}
		}		
	}
	
	/*
	 * Should be used to check Delete-Use conflicts only.
	 * Key is all jointly GraphObjects,
	 * Pair contains mappings of match1 and match2.
	 */
	private Hashtable<Vector<GraphObject>, Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> getJointlyMatches(
			final Vector<Hashtable<GraphObject, GraphObject>> ruleMatches1,
			final Vector<Hashtable<GraphObject, GraphObject>> ruleMatches2) {
		
		Hashtable<Vector<GraphObject>, Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>
		result = new Hashtable<Vector<GraphObject>, Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>();
		
		for (int i=0; i<ruleMatches1.size(); i++) {
			Hashtable<GraphObject, GraphObject> m1 = ruleMatches1.get(i);
			
			for (int j=0; j<ruleMatches2.size(); j++) {
				Hashtable<GraphObject, GraphObject> m2 = ruleMatches2.get(j);
				Vector<GraphObject> jointlyObjs = new Vector<GraphObject>();
				
				Enumeration<GraphObject> keys1 = m1.keys();
				while (keys1.hasMoreElements()) {
					GraphObject o1 = keys1.nextElement();
					GraphObject i1 = m1.get(o1);
					
					Collection<GraphObject> values2 = m2.values();
					if (values2.contains(i1)) {
						jointlyObjs.add(i1);
					}
				}
				
				if (!jointlyObjs.isEmpty()) {
					Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
					p = new Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>(m1, m2);
					result.put(jointlyObjs, p);
				}
			}
		}
		
		return result;
	}

	/*
	private boolean setMapping(
			final OrdinaryMorphism morph, 
			final Hashtable<GraphObject, GraphObject> map) {
		
		Enumeration<GraphObject> keys = map.keys();
		while (keys.hasMoreElements()) {
			GraphObject o = keys.nextElement();
			GraphObject i = map.get(o);
			try {
				if (i.isArc()) {
					morph.addMapping(((Arc)o).getSource(), ((Arc)i).getSource());
					morph.addMapping(((Arc)o).getTarget(), ((Arc)i).getTarget());
				}
				morph.addMapping(o, i);
			} catch (BadMappingException ex) {
				return false;
			}
		}
		return true;
	}
	*/
	
	private boolean setMapping(
			final OrdinaryMorphism morph, 
			final OrdinaryMorphism isoG,
			final Hashtable<GraphObject, GraphObject> map) {
		
		Enumeration<GraphObject> keys = map.keys();
		while (keys.hasMoreElements()) {
			GraphObject o = keys.nextElement();
			GraphObject img = isoG.getImage(map.get(o));
			if (img != null) {				
				try {
					if (img.isArc()) {
						morph.addMapping(((Arc)o).getSource(), ((Arc)img).getSource());
						morph.addMapping(((Arc)o).getTarget(), ((Arc)img).getTarget());
					}
					morph.addMapping(o, img);
				} catch (BadMappingException ex) {
//					System.out.println(ex.getLocalizedMessage());
					return false;
				}
			}
			else 
				return false;
		}
		return true;
	}
	
	/*
	 * Returns Delete-Use conflicts of the rule pair (r1, r2).
	 */
	private Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> getDeleteUseConflictsAtGraph(
			final Rule r1, 
			final Rule r2) {
//		System.out.println("CriticalRulePairAtGraph.getDeleteUseConflictsAtGraph...("+r1.getName()+", "+r2.getName()+")");
//		System.out.println("### to delete: "+super.delete);
		
		this.jointlyMatches = getJointlyMatches(this.r1Matches, this.r2Matches);
//		System.out.println("### jointlyMatches: "+jointlyMatches.size());
		
		if (!this.jointlyMatches.isEmpty()) {
			Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> 
			result = new Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>();

			Enumeration<Vector<GraphObject>> jointly = this.jointlyMatches.keys();
			while (jointly.hasMoreElements()) {
				Vector<GraphObject> jointlyObjs = jointly.nextElement();
				
				Hashtable<GraphObject, GraphObject>
				m1 = this.jointlyMatches.get(jointlyObjs).first;
				
				Hashtable<GraphObject, GraphObject>
				m2 = this.jointlyMatches.get(jointlyObjs).second;
				
				for (int i=0; i<jointlyObjs.size(); i++) {
					GraphObject o = jointlyObjs.get(i);
					GraphObject go = getOriginalOfImage(o, m1);
										
					if (super.delete.contains(go)) {
						o.setCritical(true);
//						System.out.println("### Critical: "+o);
						
						Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
						p = new Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>(
								m1, m2);
						result.add(p);
						break;
					}
				}
			}
//			System.out.println("### result: "+result.size());			
			return result;			
		}
		return null;
	}
	
	private GraphObject getOriginalOfImage(GraphObject img, Hashtable<GraphObject, GraphObject> map) {
		Enumeration<GraphObject> keys = map.keys();
		while (keys.hasMoreElements()) {
			GraphObject o = keys.nextElement();
			GraphObject i = map.get(o);
			if (i == img)
				return o;				 
		}
		return null;
	}
	
	/*
	 * Returns Produce-Forbid conflicts of the rule pair (r1, r2).
	 */
	private Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> getProduceForbidConflictsAtGraph(
			final Rule r1, 
			final Rule r2) {		
		
		Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> 
		result = new Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>();
		
		for (int i=0; i<this.r1Matches.size(); i++) {
			OrdinaryMorphism isoG = this.graph.isomorphicCopy();
			if (isoG == null) {
				result = null;
				return null;
			}
			
			Hashtable<GraphObject, GraphObject> m1Map = this.r1Matches.get(i);
			Match m1 = BaseFactory.theFactory().createMatch(r1, isoG.getTarget());
			if (setMapping(m1, isoG, m1Map)) {				
//				make test step (r1,m1)				
				OrdinaryMorphism com1 = null;
				try {
					com1 = (OrdinaryMorphism) TestStep.execute(m1);
				} catch (TypeException ex) {
					m1.dispose();
					m1 = null;
					continue;
				}
				
				for (int j=0; j<this.r2Matches.size(); j++) {
					Hashtable<GraphObject, GraphObject> m2Map = this.r2Matches.get(j);					
					Match m2 = BaseFactory.theFactory().createMatch(r2, isoG.getTarget());
					if (setMapping(m2, isoG, m2Map)) {							
						if (m2.areTotalIdentDanglSatisfied()) {							
							final List<OrdinaryMorphism> nacs2 = r2.getNACsList();
							for (int l=0; l<nacs2.size(); l++) {
								final OrdinaryMorphism nac2 = nacs2.get(l);
								final OrdinaryMorphism nac2Star = (OrdinaryMorphism) m2.checkNAC(nac2);
								if (nac2Star != null) {
									boolean critical = false;
									final Enumeration<GraphObject> nac2StarCodom = nac2Star.getCodomain();
									while (nac2StarCodom.hasMoreElements()) {
										final GraphObject o = nac2StarCodom.nextElement();
										final Enumeration<GraphObject> preimgR1 = com1.getInverseImage(o);
										if (preimgR1.hasMoreElements()) {
											final GraphObject preimg = preimgR1.nextElement();
											if (!r1.getInverseImage(preimg).hasMoreElements()) {
												o.setCritical(true);
												critical = true;
											}
										}
										// oder so:
//										Enumeration<GraphObject> com1Codom = com1.getCodomain();
//										while (com1Codom.hasMoreElements()) {
//											GraphObject go = com1Codom.nextElement();											
//											if (go == o) {
//												GraphObject preimg = com1.getInverseImage(go).nextElement();
//												if (!r1.getInverseImage(preimg).hasMoreElements()) {
//													o.setCritical(true);
//													critical = true;
//												}
//											}
//										}						
									}
									if (critical) {
										final Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
										p = new Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>(
												m1Map, m2Map);
										result.add(p);
									}
								}
							}
						}
					} 
				}
			}
		}
		if (!result.isEmpty()) {
			result.trimToSize();
			return result;
		}
		result = null;		
		return null;
	}
	
	/*
	 * Returns Change-Use-Attribute conflict of the rule pair (r1, r2).
	 */
	private Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>> getChangeUseAttributeConflictAtGraph(
			final Rule r1, 
			final Rule r2,
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrsL1,
//			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrsL2,
			final Hashtable<GraphObject, GraphObject> m1map,
//			final OrdinaryMorphism isoG,
//			final Match m1test, 
			final OrdinaryMorphism com1test,
			final Match m2) {
		
		if (this.jointlyMatches == null)
			this.jointlyMatches = getJointlyMatches(this.r1Matches, this.r2Matches);
		
		if (!this.jointlyMatches.isEmpty()) {
//			System.out.println("### getChangeUseAttributeConflictAtGraph...");
			
			Enumeration<Vector<GraphObject>> jointly = this.jointlyMatches.keys();
			while (jointly.hasMoreElements()) {
				Vector<GraphObject> jointlyObjs = jointly.nextElement();
				
				Hashtable<GraphObject, GraphObject>
				m1Map = this.jointlyMatches.get(jointlyObjs).first;
				
				if (m1Map != m1map)
					continue;
				
				Hashtable<GraphObject, GraphObject>
				m2Map = this.jointlyMatches.get(jointlyObjs).second;
				
				boolean critical = false;
				for (int i=0; i<jointlyObjs.size(); i++) {
					GraphObject o = jointlyObjs.get(i);
					GraphObject go1 = getOriginalOfImage(o, m1Map);
										
					if (super.preservedChanged.contains(go1)) {
//						 check attrs
						GraphObject go2 = getOriginalOfImage(o, m2Map);	
						GraphObject img2 = m2.getImage(go2);
						
						GraphObject img1changed = com1test.getImage(r1.getImage(go1));
//						System.out.println("###  img2: "+img2);
//						System.out.println("###  img1changed: " +img1changed);
						
						if (!checkChangeUseAttribute(img2, img1changed, changedAttrsL1)) {
							o.setCritical(true);
//							System.out.println("### Critical host graph obj: \n"+o);
							 critical = true;
						}
					}
				}
				if (critical) {
					Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
					p = new Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>(
							m1Map, m2Map);
					return p;
				}
			}
		}
		return null;
	}

	/*
	 * Returns Change-Forbid-Attribute conflict of the rule pair (r1, r2).
	 */
	private Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>> getChangeForbidAttributeConflictAtGraph(
			final Rule r1, 
			final Rule r2,
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrsL1,
//			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrsL2,
			final Hashtable<GraphObject, GraphObject> m1map,
			final Hashtable<GraphObject, GraphObject> m2map,
//			final OrdinaryMorphism isoG,
//			final Match m1test, 
			final OrdinaryMorphism com1test,
			final Match m2) {
		
//		System.out.println("### getChangeForbidAttributeConflictAtGraph... "+r1.getName()+" && "+r2.getName());
		// now check change-forbid attribute after apply r1
		final List<OrdinaryMorphism> nacs2 = r2.getNACsList();
		for (int i=0; i<nacs2.size(); i++) {
			final OrdinaryMorphism nac2 = nacs2.get(i);
			final OrdinaryMorphism nac2Star = (OrdinaryMorphism) m2.checkNAC(nac2);
//			System.out.println("### nac: "+ nac2.getName()+"  nac2Star: "+nac2Star);
			if (nac2Star != null) {
//				System.out.println("### nac NOT satisfied! "+nac2.getName());
				boolean critical = false;
				final Enumeration<GraphObject> nac2StarCodom = nac2Star.getCodomain();
				while (nac2StarCodom.hasMoreElements()) {
					final GraphObject o = nac2StarCodom.nextElement();							
					final Enumeration<GraphObject> preimgR1 = com1test.getInverseImage(o);
					if (preimgR1.hasMoreElements()) {
						final GraphObject preimg = preimgR1.nextElement();
						final Enumeration<GraphObject> preimgL1 = r1.getInverseImage(preimg);
						if (preimgL1.hasMoreElements()) {
//							final GraphObject prepreimg1 = preimgL1.nextElement();
							// check attrs
							final GraphObject nac2go = nac2Star.getInverseImage(o).nextElement();
							
							if (checkChangeForbidAttribute(nac2go, o, changedAttrsL1)) {
								o.setCritical(true);
								critical = true;
							}
						}
					}
										
				}
				if (critical) {
//					System.out.println("### Critical NAC: "+nac2.getName());
					final Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
					p = new Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>(
							m1map, m2map);
					return p;					
				}
			}
		}
		return null;
	}
	
	/*
	 * Returns Change-Attribute conflicts of the rule pair (r1, r2).
	 */
	private Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> getChangeAttributeConflictsAtGraph(
			final Rule r1, 
			final Rule r2,
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrsL1,
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrsL2) {
		
//		System.out.println("### getChangeAttributeConflictsAtGraph... ("+r1.getName()+", "+r2.getName()+")");
//		System.out.println("### to change: "+super.preservedChanged);
		
		Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>> 
		result = new Vector<Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>>();
				
		for (int i=0; i<this.r1Matches.size(); i++) {
			OrdinaryMorphism isoG = this.graph.isomorphicCopy();
			if (isoG == null) {
				result = null;
				return null;
			}
			
			Hashtable<GraphObject, GraphObject> m1Map = this.r1Matches.get(i);
			Match m1 = BaseFactory.theFactory().createMatch(r1, isoG.getTarget());
			if (setMapping(m1, isoG, m1Map)) {				
//				 make test step (r1,m1)				
				OrdinaryMorphism com1 = null;
				try {
					com1 = (OrdinaryMorphism) TestStep.execute(m1);
				} catch (TypeException ex) {
					m1.dispose();
					m1 = null;
					continue;
				}
				
				for (int j=0; j<this.r2Matches.size(); j++) {
					Hashtable<GraphObject, GraphObject> m2Map = this.r2Matches.get(j);					
					Match m2 = BaseFactory.theFactory().createMatch(r2, isoG.getTarget());
					if (setMapping(m2, isoG, m2Map)) {							
						if (m2.areTotalIdentDanglSatisfied()) {							
							// check change-use attribute after apply r1
							Pair<Hashtable<GraphObject, GraphObject>, Hashtable<GraphObject, GraphObject>>
							p = this.getChangeUseAttributeConflictAtGraph(
									r1, r2, 
									changedAttrsL1, 
//									changedAttrsL2,
									m1Map, 
//									isoG, m1, 
									com1, m2);
							if (p != null) 
								result.add(p);
							
//							check change-forbid attribute after apply r1
							p = this.getChangeForbidAttributeConflictAtGraph(
									r1, r2, 
									changedAttrsL1, 
//									changedAttrsL2,
									m1Map, m2Map,
//									isoG, m1, 
									com1, m2);
							if (p != null) 
								result.add(p);
						}
					} 
				}
			}
		}
		if (!result.isEmpty()) {
			result.trimToSize();
			return result;
		}
		result = null;
		return null;
	}

	private boolean checkChangeForbidAttribute(
			final GraphObject other, 
			final GraphObject changed, 
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrs) {
		
		ValueTuple otherAttr = (ValueTuple) other.getAttribute();
		if (otherAttr == null)
			return true;
		
		Vector<Pair<ValueMember, ValueMember>> 
		vec = changedAttrs.get(changed.getType().getAttrType());
		if (vec == null)
			return true;
		
		for (int i=0; i<vec.size(); i++) {
			Pair<ValueMember, ValueMember> p = vec.get(i);			
			ValueMember vmLeft = p.first;
//			ValueMember vmRight = p.second;
			
			ValueMember vmChanged = ((ValueTuple) changed.getAttribute()).getValueMemberAt(vmLeft.getName());
			
			ValueMember vmOther = otherAttr.getValueMemberAt(vmLeft.getName());
			if (vmOther.isSet()) {
				if (vmOther.getExpr().isVariable()) {
					String varValue = getValueOfVariable(this.rule2, vmOther.getExprAsText());
					if (varValue != null 
							&& !varValue.equals(vmChanged.getExprAsText()))
					return false;
				}
				else if (vmOther.getExpr().isConstant()) {
					if (!vmOther.getExprAsText().equals(vmChanged.getExprAsText()))
						return false;
				}
			}
		}
		
		return true;
	}
	
	private boolean checkChangeUseAttribute(
			final GraphObject other, 
			final GraphObject changed, 
			final Hashtable<AttrType, Vector<Pair<ValueMember, ValueMember>>> changedAttrs) {
		
		ValueTuple otherAttr = (ValueTuple) other.getAttribute();
		if (otherAttr == null)
			return true;
				
		Vector<Pair<ValueMember, ValueMember>> 
		vec = changedAttrs.get(changed.getType().getAttrType());
		if (vec == null)
			return true;
				
		for (int i=0; i<vec.size(); i++) {
			Pair<ValueMember, ValueMember> p = vec.get(i);			
			ValueMember vmLeft = p.first;
//			ValueMember vmRight = p.second;
			
			ValueMember vmChanged = ((ValueTuple) changed.getAttribute()).getValueMemberAt(vmLeft.getName());
			
			ValueMember vmOther = otherAttr.getValueMemberAt(vmLeft.getName());
			if (vmOther.isSet()) {
				if (vmOther.getExpr().isVariable()) {				
					String varValue = getValueOfVariable(this.rule2, vmOther.getExprAsText());
					if (varValue != null 
							&& !varValue.equals(vmChanged.getExprAsText()))
					return false;
				}
				else if (vmOther.getExpr().isConstant()) {
					if (!vmOther.getExprAsText().equals(vmChanged.getExprAsText()))
						return false;
				}
			}
		}
		
		return true;
	}
	private String getValueOfVariable(Rule r, String varName) {
		VarTuple vars = (VarTuple) r.getAttrContext().getVariables();
		VarMember var = vars.getVarMemberAt(varName);
		if (var.isSet())
			return var.getExprAsText();
		
		return null;
	}
}
