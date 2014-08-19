

import java.util.Enumeration;
import java.util.Hashtable;
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
import agg.xt_basis.Match;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.StaticStep;
import agg.xt_basis.TypeException;

/**
 * This class is responsible for taking structurally equivalent critical
 * overlappings and discarding all but one of them.
 * 
 * 
 * @author maz
 * 
 */

public class StructuralEquivalenceAnalysis {

	private ConflictsDependenciesContainer cdc;
	private ExcludePairContainer epc;
	private DependencyPairContainer dpc;
	private String filename;
	private String newfilename;
	private static final int CONFLICT = 0;
	private static final int DEPENDENCY = 1;

	public StructuralEquivalenceAnalysis(String filename) {
		try {
			// this loads an XML file into its representation as java objects -
			// this was formulated before Olga (AGG developer) sent an email
			// instructing me how to achieve this. Her method was slightly
			// different but essentially the same. I have decided to keep my
			// original method
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
			System.err.println("There was an error setting up the file:");
			e.printStackTrace();
			System.exit(-1);
		}
	}

	// generate a new filename, altering existing one with "structuremod"
	private void generateNewFileName() {
		String s = this.filename.substring(0, this.filename.indexOf(".cpx"));
		this.newfilename = s + "_structuremod.cpx";
	}

	public void writeToNewFile() {
		XMLHelper xmlhelper = new XMLHelper();
		xmlhelper.addObjectSub(this.cdc);
		xmlhelper.save_to_xml(this.newfilename);
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

	/**
	 * This method does all the important work for this class. A
	 * conflictContainer (field of ExcludePairContainer) or dependencyContainer
	 * (field of DependencyPairContainer) are passed in. This contains
	 * information about all the overlappings. This method then takes the
	 * critical overlappings for a rule pair, compares them and only replaces
	 * those that are different from each other (i.e. not identical up to
	 * renaming (isomorphic)).
	 * 
	 * @param allOverlappingsHash
	 * @param type
	 * 
	 */
	@SuppressWarnings("unchecked")
	public void reduceStructurallyEquivalentOverlappings(
			Hashtable<Rule, java.util.Hashtable<Rule, Pair<java.lang.Boolean, java.util.Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> allOverlappingsHash,
			int type) {
		// get an enumeration of all reaction rules
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
				Rule identityRule = allIdentityRules.nextElement();
				System.out.print("\t" + identityRule.getName() + " : ");
				// this gets all the actual overlapping info
				Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> criticalPairingInfo = tempHash
						.get(identityRule);
				// check if this rule pair is critical, if it is not, we do
				// nothing
				boolean isCritical = criticalPairingInfo.first.booleanValue();
				if (!isCritical) {
					System.out.println("Not Critical");
				} else {
					// criticalPairingInfo.second.size() gives no. of critical
					// overlappings for this rule pair
					System.out.println(" --- "
							+ criticalPairingInfo.second.size()
							+ " raw critical overlappings");
					// actually get all these overlappings as a vector "keepers"
					Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> keepers = criticalPairingInfo.second;

					// temporary array to store all the overlappings, a copy of
					// keepers
					Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> vect = (Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>) criticalPairingInfo.second
							.clone();
					// next we clear keepers, because this holds references to
					// what will be written back to file
					keepers.clear();
					// the alreadyAdded Vector will store all the graphs which
					// are the result of applying our reaction rule to the
					// delete-use-conflict graph at their individual matches
					Vector<Graph> alreadyAdded = new Vector<Graph>();
					// now we cycle through all the critical overlappings for
					// this reactionRule, identityRule pair, stored in vect
					for (int i = 0; i < vect.size(); i++) {

						// this morphism has on the LHS, a match for the
						// reaction rule, and on the RHS a delete-use-conflict
						// graph which holds information about critical graph
						// objects
						OrdinaryMorphism omToCheck = vect.elementAt(i).first.first
								.simplecopy();
						// this is the morphism which will be passed to Olga's
						// method, its value depends on whether we are looking
						// at conflict pairs or dependency pairs
						OrdinaryMorphism toCheckwithRuleApplied = null;

						if (type == CONFLICT) {
							// just apply the reaction rule with the morphism
							// directly from the overlappings vector
							toCheckwithRuleApplied = makeStep(reactionRule,
									omToCheck);

						} else if (type == DEPENDENCY) {
							// for dependencies, we need to reverse the rule
							// somehow and apply this to the
							// produce-use-dependency graph, using the match
							// information given in that graph - NOT YET
							// SOLVED!!!!

							// these 2 lines create the inverse rule - this
							// works
							Pair<Pair<Rule,Boolean>, Pair<OrdinaryMorphism, OrdinaryMorphism>> tempPair = BaseFactory
									.theFactory().reverseRule(reactionRule);
							Rule tempRule = tempPair.first.first;
							if (tempRule == null)
								System.out.println("DEBUG : returned reversed rule is null!!!");
							//OrdinaryMorphism omForMatch = omToCheck;//.invert();
							//Match toCheckMatch = BaseFactory.theFactory().makeMatch(tempRule, omToCheck);
							//OrdinaryMorphism omForMatch = omForMatchPair.first;
							//Match toCheckMatch = omForMatch.makeMatch(tempRule);
							//if (toCheckMatch == null)
							//	System.out.println("DEBUG : makematch result is null");
							
							//tempRule.setMatch(toCheckMatch);
							//Match returnedMatch = tempRule.getMatch();
							//if (returnedMatch == null)
							//	System.out.println("DEBUG : no match returned");

							/*
							 * Graph tg = temp.getLeft();
							 * Enumeration<GraphObject> goenum =
							 * tg.getElements(); int ecount = 0; while
							 * (goenum.hasMoreElements()){ GraphObject go =
							 * goenum.nextElement(); ecount++; }
							 * System.out.println("ecount = " + ecount);
							 */
							// OrdinaryMorphism newMorph = toCheck;
							toCheckwithRuleApplied = makeStep(tempRule, omToCheck);
						}

						if (toCheckwithRuleApplied != null) {
							// the resulting graph after make step
							Graph graphToCheck = toCheckwithRuleApplied.getTarget();
	
							// variable to indicate if an isomorphic result graph
							// already exists for the critical overlappings we have
							// decided to store
							boolean found = false;
	
							// check against all matches already accepted as unique
							for (int j = 0; j < alreadyAdded.size(); j++) {
	
								Graph graphToCompare = alreadyAdded.get(j);
	
								// check for isomorphism
								if (graphToCompare.isIsomorphicTo(graphToCheck)) {
									found = true;
								}
	
							} // end of looping through existing overlappings to
							// find if duplicate
	
							if (!found) {
								// if the overlapping is structurally unique, add it
								// to the keepers vector to be written back, and add
								// the resultgraph to existing graphs
								keepers.add(vect.elementAt(i));
								alreadyAdded.add(graphToCheck);
							}
						}
					}// end of looping through all critical overlappings

					System.out.println("\t\tNo of unique overlappings : "
							+ keepers.size());
					// System.out.println("\t\tNo of Unique overlappings
					// recorded : "
					// + criticalPairingInfo.second.size());

				}// end of critical check

			}// end of processing this identity rule

		}// end of processing this reaction rule
	}

	/**
	 * This method was kindly provided by Olga Runge, an original author of the
	 * AGG project. This has fixed the problem of altered information in the
	 * rewritten cpx file resulting from applying rules (see the method I
	 * devised without help in LoadCPXFile_v3.java (SVN) which used a new
	 * subclass of DefaultGraTraImpl class of AGG)
	 * 
	 * Try to apply the rule at the target graph of the overlapping morphism.
	 * The match mapping is given by morphism mapping.
	 * 
	 * @param rule
	 *            the first or the second rule of a critical pair
	 * @param morph
	 *            the first or the second overlapping morphism of a critical
	 *            pair
	 * 
	 * @return a comatch if successful, otherwise - null
	 */
	private OrdinaryMorphism makeStep(final Rule rule,
			final OrdinaryMorphism morph) {
		OrdinaryMorphism comatch = null;

		// make isocopy of the target graph
		final OrdinaryMorphism graphIso = morph.getTarget().isomorphicCopy();
		// morphTest: morph.source -> graphIso.target,
		// the mappings are set like mappings of morph
		final OrdinaryMorphism morphTest = morph.compose(graphIso);
		// set variable context for attribute context because we do not work
		// with a host graph
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
					System.out.println("s.execute : " + e.getMessage());
				}
			}
		} else {
			System.out.println("DEBUG : Not a valid match!!!!!");
		}
		return comatch;

	} // end of makeStep method

	/**
	 * this method issues all necessary instructions to process the provided cpx
	 * file's overlappings for structural equivalence
	 * 
	 */
	public void processFileForStructuralEquivalence() {
		System.out.println("\n\nREMOVING STRUCTURALLY EQUIVALENT OVERLAPPINGS\n");
		System.out.println("Conflict Pairs:\n");
		// reduce conflict pairs
		reduceStructurallyEquivalentOverlappings(getEpc()
				.getConflictContainer(), CONFLICT);

		// reduce dependency pairs
		System.out.println("\nDependency Pairs:\n");
		reduceStructurallyEquivalentOverlappings(getDpc()
				.getDependencyContainer(), DEPENDENCY);

		System.out.println();

		// write to new file
		try {
			System.out.println("Writing new overlappings to file \""
					+ getNewfilename() + "\"...");
			writeToNewFile();
			System.out.println("Finished!");
		} catch (Exception e) {
			System.err.println("Error writing modded pairs to file!");
			System.exit(-1);
		}

	}

	/**
	 * Program to be called from terminal to reduce pairs that are structurally
	 * equivalent. The filename should be passed as an argument to the command
	 * line.
	 * 
	 * @param args
	 * 
	 */
	public static void main(String[] args) {

		try {
			StructuralEquivalenceAnalysis sea = new StructuralEquivalenceAnalysis(
					args[0]);
			sea.processFileForStructuralEquivalence();
		} catch (ArrayIndexOutOfBoundsException e) {
			System.err
					.println("There was an error loading your specified file - please try again!");
			e.printStackTrace();
			System.exit(-1);
		}

	}// end of main method

}// end of class
