

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.Vector;

import agg.attribute.impl.ContextView;
import agg.parser.ConflictsDependenciesContainer;
import agg.parser.DependencyPairContainer;
import agg.parser.ExcludePairContainer;
import agg.util.Pair;
import agg.util.XMLHelper;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Completion_InjCSP;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;

public class LoadCPXFile {

	private ConflictsDependenciesContainer cdc;
	private ExcludePairContainer epc;
	private DependencyPairContainer dpc;
	private String filename;
	private String newfilename;
	private static final int CONFLICT = 0;
	private static final int DEPENDENCY = 1;

	public LoadCPXFile(String filename) {
		try {
			this.cdc = new ConflictsDependenciesContainer();
			XMLHelper xmlhelper = new XMLHelper();
			if (xmlhelper.read_from_xml(filename)) {
				xmlhelper.getObjectSub(this.cdc);
				this.epc = this.cdc.getExcludePairContainer();
				this.dpc = this.cdc.getDependencyPairContainer();
				this.filename = filename;
				generateNewFileName();
			}
		} catch (Exception e) {
			System.err.println("There was an error setting up the file:  "+e.getLocalizedMessage());
//			e.printStackTrace();
		}
	}

	private void generateNewFileName() {
		String s = this.filename.substring(0, this.filename.indexOf(".cpx"));
		this.newfilename = s + "_structuremod.cpx";
	}

	public void writeToNewFile() {
		XMLHelper xmlhelper = new XMLHelper();
		xmlhelper.addObjectSub(this.cdc);
		xmlhelper.save_to_xml(this.newfilename);
	}

	public Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> getOriginalOverlapping(
			Rule reaction, Rule identity, int type, int position) {

		Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> toReturn = null;

		if (type == CONFLICT) {
			ConflictsDependenciesContainer tempcdc = new ConflictsDependenciesContainer();
			XMLHelper xmlhelper = new XMLHelper();
			xmlhelper.read_from_xml(this.filename);
			xmlhelper.getObjectSub(tempcdc);
			ExcludePairContainer tempepc = tempcdc.getExcludePairContainer();
			Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> firsthash = tempepc
					.getConflictContainer();
			Enumeration<Rule> reactionRules = firsthash.keys();
			while (reactionRules.hasMoreElements()) {
				Rule r = reactionRules.nextElement();
				if (reaction.compareTo(r)) {
					// System.out.println("found!");
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondhash = firsthash
							.get(r);
					Enumeration<Rule> identityRules = secondhash.keys();
					while (identityRules.hasMoreElements()) {
						Rule i = identityRules.nextElement();
						if (identity.compareTo(i)) {
							// System.out.println("found");
							Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> vec = secondhash
									.get(i).second;
							toReturn = vec.elementAt(position);
						}
					}
				}
			}
			// System.out.println(tempepc.getConflictContainer().contains(reaction));
			// toReturn =
			// tempepc.getConflictContainer().get(reaction).get(identity).second.elementAt(position);

		} else if (type == DEPENDENCY) {
			ConflictsDependenciesContainer tempcdc = new ConflictsDependenciesContainer();
			XMLHelper xmlhelper = new XMLHelper();
			xmlhelper.read_from_xml(this.filename);
			xmlhelper.getObjectSub(tempcdc);
			DependencyPairContainer tempdpc = tempcdc
					.getDependencyPairContainer();
			Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> firsthash = tempdpc
					.getDependencyContainer();
			Enumeration<Rule> reactionRules = firsthash.keys();
			while (reactionRules.hasMoreElements()) {
				Rule r = reactionRules.nextElement();
				if (reaction.compareTo(r)) {
					// System.out.println("found!");
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondhash = firsthash
							.get(r);
					Enumeration<Rule> identityRules = secondhash.keys();
					while (identityRules.hasMoreElements()) {
						Rule i = identityRules.nextElement();
						if (identity.compareTo(i)) {
							// System.out.println("found");
							Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> vec = secondhash
									.get(i).second;
							toReturn = vec.elementAt(position);
						}
					}
				}
			}
		}

		return toReturn;
	}

	public String getNewfilename() {
		return this.newfilename;
	}

	public ConflictsDependenciesContainer getCdc() {
		return this.cdc;
	}

	public void setCdc(ConflictsDependenciesContainer cdc) {
		this.cdc = cdc;
	}

	public ExcludePairContainer getEpc() {
		return this.epc;
	}

	public void setEpc(ExcludePairContainer epc) {
		this.epc = epc;
	}

	public DependencyPairContainer getDpc() {
		return this.dpc;
	}

	public void setDpc(DependencyPairContainer dpc) {
		this.dpc = dpc;
	}

	public void reduceStructurallyEquivalentOverlappingsOLD(
			Hashtable<Rule, java.util.Hashtable<Rule, Pair<java.lang.Boolean, java.util.Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> allOverlappingsHash,
			int type) {
		Enumeration<Rule> allReactionRules = allOverlappingsHash.keys();

		// for each reaction rule
		while (allReactionRules.hasMoreElements()) {
			Rule reactionRule = allReactionRules.nextElement();
			System.out.println(reactionRule.getName() + " overlappings:");

			// gets all the overlappings for this rule with the other identity
			// rules
			Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> tempHash = allOverlappingsHash
					.get(reactionRule);
			// the following gets the keys for the overlappings hashtable, i.e.
			// identity rules that this rule is analysed against
			Enumeration<Rule> allIdentityRules = tempHash.keys();
			while (allIdentityRules.hasMoreElements()) {
				//
				Rule identityRule = allIdentityRules.nextElement();
				System.out.print("\t" + identityRule.getName() + " : ");
				Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> criticalPairingInfo = tempHash
						.get(identityRule);
				// check if this rule pair is critical
				boolean isCritical = criticalPairingInfo.first.booleanValue();
				if (!isCritical) {
					System.out.println("Not Critical");
				} else {
					// criticalPairingInfo.second.size() gives no. of critical
					// overlappings for this rule pair
					System.out.println(" --- "
							+ criticalPairingInfo.second.size()
							+ " raw critical overlappings");
					// actually get all these overlappings
					Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
					keepers = criticalPairingInfo.second;

					// temporary array to store only non structurally equivalent pairs
					Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
					vect = new Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> (
									criticalPairingInfo.second);
					keepers.clear();
					// add the first element
					keepers.add(vect.firstElement());
					// this variable counts the no. of different pairs

					// for all remaining pairs check that the overlappings are
					// not structurally equivalent to ones already in keepers
					// i.e. match of node takes place in same place (ignoring
					// permutation of edges and peripheral nodes)
					for (int i = 1; i < vect.size(); i++) {

						OrdinaryMorphism toCheck = vect.elementAt(i).first.first
								.simplecopy();
						// BEWARE: this is returning a reference not a copy of
						// the graph!!

						Graph targetToCheck = toCheck.getTarget();

						// Graph targetToCheck = new Graph();
						// targetToCheck.graphcopy(toCheck.getTarget());

						boolean finished = false;
						while (!finished) {

							Vector<GraphObject> vectCheckObjects = new Vector<GraphObject>();
							Iterator<?> objs = targetToCheck.getNodesSet().iterator();
							while (objs.hasNext()) {
								vectCheckObjects.add((GraphObject) objs.next());
							}
							objs = targetToCheck.getArcsSet().iterator();
							while (objs.hasNext()) {
								vectCheckObjects.add((GraphObject) objs.next());
							}
							/*
							 * System.out.println("Vector size: " +
							 * vectCheckObjects.size()); try {
							 * Thread.sleep(2000); } catch (InterruptedException
							 * e1) { // e1.printStackTrace(); }
							 */
							for (int y = 0; y < vectCheckObjects.size(); y++) {
								GraphObject temp = vectCheckObjects
										.elementAt(y);
								if (y == vectCheckObjects.size() - 1) {
									finished = true;
								}

								if (temp == null) {

								} else {
									if (temp.isCritical()) {
										try {
											targetToCheck.destroyObject(temp);
											break;
										} catch (TypeException e) {
											System.err
													.println("Couldn't delete GraphObject");
											e.printStackTrace();
										}
									}
								}
							}
						}

						boolean found = false;

						// check against all matches already accepted as unique
						for (int j = 0; j < keepers.size(); j++) {

							// sixth attempt
							OrdinaryMorphism toCompare = keepers.elementAt(j).first.first
									.simplecopy();
							Graph targetToCompare = toCompare.getTarget();

							boolean finishedInner = false;
							while (!finishedInner) {

								Vector<GraphObject> vectCompareObjects = new Vector<GraphObject>();
								Iterator<?> compObjs = targetToCompare.getNodesSet().iterator();
								while (compObjs.hasNext()) {
									vectCompareObjects.add((GraphObject)compObjs.next());
								}
								compObjs = targetToCompare.getArcsSet().iterator();
								while (compObjs.hasNext()) {
									vectCompareObjects.add((GraphObject)compObjs.next());
								}
								
								for (int z = 0; z < vectCompareObjects.size(); z++) {
									GraphObject temp = vectCompareObjects
											.elementAt(z);
									if (z == vectCompareObjects.size() - 1) {
										finishedInner = true;
									}

									if (temp == null) {

									} else {
										if (temp.isCritical()) {
											try {
												targetToCompare
														.destroyObject(temp);
												break;
											} catch (TypeException e) {
												System.err
														.println("Couldn't delete GraphObject");
												e.printStackTrace();
											}
										}
									}
								}
							}
							if (targetToCheck.isIsomorphicTo(targetToCompare)) {
								found = true;
							}

						} // end of looping through existing overlappings to
						// find if duplicate

						if (!found) {
							keepers.add(vect.elementAt(i));
							//keepers.add(getOriginalOverlapping(reactionRule,identityRule, type, i));
						}

					}// end of looping through all critical overlappings

					System.out.println("\t\tNo of unique overlappings : "
							+ keepers.size());
					// System.out.println("\t\tNo of Unique overlappings recorded : "
					// + criticalPairingInfo.second.size());

				}// end of critical check

			}// end of processing this identity rule

		}// end of processing this reaction rule
	}

	/**
	 * Try to apply the rule at the target graph of the overlapping morphism.
	 * The match mapping is given by morphism mapping.
	 * 
	 * @param rule 
	 * 				the first or the second rule of a critical pair
	 * @param morph
	 * 				the first or the second overlapping morphism of a critical pair
	 * 
	 * @return
	 * 			a comatch if successful, otherwise - null
	 */
	private OrdinaryMorphism makeStep(final Rule rule, final OrdinaryMorphism morph) {
		OrdinaryMorphism comatch = null;
		
		// make isocopy of the target graph
		final OrdinaryMorphism graphIso = morph.getTarget().isomorphicCopy();
		// morphTest: morph.source -> graphIso.target,
		// the mappings are set like mappings of morph
		final OrdinaryMorphism morphTest = morph.compose(graphIso);
		// set variable context for attribute context because we do not work with a host graph
		((ContextView) morphTest.getAttrContext()).setVariableContext(true);
		// make a match from morphTest
		final Match match = BaseFactory.theFactory().makeMatch(rule, morphTest);
		if (match != null) {
			match.setCompletionStrategy(new Completion_InjCSP(), true);
			boolean isValid = match.isTotal() && match.isValid(true);
			if (isValid) {
//				final Step s = new Step();
				try {
					comatch = (OrdinaryMorphism) StaticStep.execute(match, true);
				} catch (TypeException e) {
					System.out.println("s.execute : "+e.getMessage());
				}
			}
		}
		return comatch;
	}
	
	public void reduceStructurallyEquivalentOverlappingsNEW(
			Hashtable<Rule, java.util.Hashtable<Rule, Pair<java.lang.Boolean, java.util.Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> allOverlappingsHash,
			int type) {
		Enumeration<Rule> allReactionRules = allOverlappingsHash.keys();

		// for each reaction rule
		while (allReactionRules.hasMoreElements()) {
			Rule reactionRule = allReactionRules.nextElement();
			System.out.println(reactionRule.getName() + " overlappings: ");

			// gets all the overlappings for this rule with the other identity
			// rules
			Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> tempHash = allOverlappingsHash
					.get(reactionRule);
			// the following gets the keys for the overlappings hashtable, i.e.
			// identity rules that this rule is analysed against
			Enumeration<Rule> allIdentityRules = tempHash.keys();
			while (allIdentityRules.hasMoreElements()) {
				//
				Rule identityRule = allIdentityRules.nextElement();
				System.out.print("\t" + identityRule.getName() + " : ");
				Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> criticalPairingInfo = tempHash
						.get(identityRule);
				// check if this rule pair is critical
				boolean isCritical = criticalPairingInfo.first.booleanValue();
				if (!isCritical) {
					System.out.println("Not Critical");
				} else {
					// criticalPairingInfo.second.size() gives no. of critical
					// overlappings for this rule pair
					System.out.println(" --- "
							+ criticalPairingInfo.second.size()
							+ " raw critical overlappings");
					// actually get all these overlappings
					Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
					keepers = criticalPairingInfo.second;

					// temporary array to store only non structually equivalent
					// pairs
					Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> 
					vect = new Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>();
					vect.addAll(keepers);
					if (vect.size() == 1) {
						continue;
					}
					
					keepers.clear();
					// add the first element
					keepers.add(vect.firstElement());
					// this variable counts the no. of different pairs

					// first overlapping
					OrdinaryMorphism theFirst = vect.elementAt(0).first.first;
					OrdinaryMorphism theFirstComatch = null;
					
					// second overlapping
					OrdinaryMorphism theSecond = vect.elementAt(0).first.second;
					OrdinaryMorphism theSecondComatch = null;
										
					if (type == CONFLICT) {
						// apply the first rule at the critical graph
						theFirstComatch = makeStep(reactionRule, theFirst);
						System.out.println("============> Conflict:: thefirstComatch: "+theFirstComatch);
					} else if (type == DEPENDENCY) {
						// apply the first rule (inverse!) at the critical graph
						Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
						tempFirstPair = BaseFactory.theFactory().reverseRule(reactionRule);
						if (tempFirstPair != null) {
							Rule tempFirstRule = tempFirstPair.first.first;
							
							// build up the first overlap morphism
							// here is very important! 
							// try to draw all these morphisms to understand!
							OrdinaryMorphism theFirstPrime = BaseFactory.theFactory().createMorphism(
									tempFirstRule.getLeft(), theFirst.getTarget());
							if (theFirst.completeDiagram(tempFirstPair.second.second, theFirstPrime)) {
								System.out.println("============> Dependency:: theFirstPrime: "+theFirstPrime+"  map size: "+theFirstPrime.getSize());
								
								theFirstComatch = makeStep(tempFirstRule, theFirstPrime);						
								System.out.println("============> Dependency:: (inverse) theFirstComatch: "+theFirstComatch);						
							}
						}
						else {
							// apply the second rule at the critical graph
							theSecondComatch = makeStep(identityRule, theSecond);
							System.out.println("============> Dependency:: theSecondComatch: "+ theSecondComatch);
						}
					}
					
					// for all remaining pairs check that the overlappings are
					// not structurally equivalent to ones already in keepers
					// i.e. match of node takes place in same place (ignoring
					// permutation of edges and peripheral nodes)
					for (int i = 1; i < vect.size(); i++) {
												
						if (type == CONFLICT) {
							
							// take the next overlapping of the current rule pair
							OrdinaryMorphism toCheck = vect.elementAt(i).first.first;
							
							// apply the first rule at the critical graph
							OrdinaryMorphism theCheckComatch = makeStep(reactionRule, toCheck);
												
							if (theFirstComatch != null && theCheckComatch != null) {
								Graph graphToCompare = theFirstComatch.getTarget();
								Graph graphToCheck = theCheckComatch.getTarget();
								if (graphToCheck.isIsomorphicTo(graphToCompare)) {
									System.out.println("============> Conflict::  Isomorphic critical graph found!");
								} else {
									keepers.add(vect.get(i));
								}
							}
							
						} else if (type == DEPENDENCY) {
							
							// take the next overlapping of the current rule pair
							OrdinaryMorphism toCheck = vect.elementAt(i).first.first;

							Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> 
							tempPair = BaseFactory.theFactory().reverseRule(reactionRule);
							if (tempPair != null) {
								Rule tempRule = tempPair.first.first;
								// build up the first overlap morphism
								// here is very important! 
								// try to draw all these morphisms to understand!
								OrdinaryMorphism toCheckPrime = BaseFactory.theFactory().createMorphism(
										tempRule.getLeft(), toCheck.getTarget());
								if (toCheck.completeDiagram(tempPair.second.second, toCheckPrime)) {								
									System.out.println("============> Dependency:: toCheckPrime: "+toCheckPrime+"  map size: "+toCheckPrime.getSize());												
									OrdinaryMorphism theCheckComatch = makeStep(tempRule, toCheckPrime);								
									System.out.println("============> Dependency:: theCheckComatch: "+theCheckComatch); 													
								
									if (theFirstComatch != null && theCheckComatch != null) {
										Graph graphToCompare = theFirstComatch.getTarget();
										Graph graphToCheck = theCheckComatch.getTarget();
										if (graphToCheck.isIsomorphicTo(graphToCompare)) {
											System.out.println("============> Dependency::  Isomorphic critical graph found!");
										} else {
											keepers.add(vect.get(i));
										}
									}
								}
							}
							else {
								System.out.println("DEBUG : returned reversed rule is null!!!");					
								System.out.println("Take the second rule and check!!!");
								// take the next overlapping of the current rule pair
								toCheck = vect.elementAt(i).first.second;
								// apply the second rule at the critical graph
								OrdinaryMorphism theCheckComatch = makeStep(identityRule, toCheck);
								System.out.println("============> Dependency:: theCheckComatch: "+theCheckComatch); 					
								if (theSecondComatch != null && theCheckComatch != null) {
									Graph graphToCompare = theSecondComatch.getTarget();
									Graph graphToCheck = theCheckComatch.getTarget();
									if (graphToCheck.isIsomorphicTo(graphToCompare)) {
										System.out.println("============> Dependency::  Isomorphic critical graph found!");
									} else {
										keepers.add(vect.get(i));
									}
								}
								
							}
						}						
					}					
				}				
			}
		}
	}
	
	/**
	 * @param args
	 */
	public static void main(String[] args) {

		try {
			LoadCPXFile lpf = new LoadCPXFile(args[0]);

			System.out
					.println("\nREMOVING STRUCTURALLY EQUIVALENT OVERLAPPINGS\n");

			System.out.println("Conflict Pairs:\n");
			lpf.reduceStructurallyEquivalentOverlappingsNEW(lpf.getEpc()
					.getConflictContainer(), CONFLICT);

			System.out.println("\nDependency Pairs:\n");
			lpf.reduceStructurallyEquivalentOverlappingsNEW(lpf.getDpc()
					.getDependencyContainer(), DEPENDENCY);

			System.out.println();

			try {
				System.out.println("Writing new overlappings to file \""
						+ lpf.getNewfilename() + "\"...");
				lpf.writeToNewFile();
				System.out.println("Finished!");
			} catch (Exception e) {
				System.err.println("Error writing modded pairs to file!");
			}
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err
					.println("There was an error loading your specified file - please try again!");
			System.exit(0);
		}

	}// end of main method

}// end of class
