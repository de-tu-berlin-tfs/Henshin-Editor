package agg.parser;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

import agg.util.Pair;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.GraGra;
import agg.xt_basis.Graph;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
//import agg.cons.AtomConstraint;


public class Consistency implements Runnable {

	ExcludePairContainer excludeContainer;

	Rule rule1, rule2;

	public Consistency(ExcludePairContainer excludeContainer) {
		this.excludeContainer = excludeContainer;
		this.rule1 = null;
		this.rule2 = null;
	}

	public Consistency(ExcludePairContainer excludeContainer, Rule r1, Rule r2) {
		this(excludeContainer);
		this.rule1 = r1;
		this.rule2 = r2;
	}

	public void run() {
		// System.out.println("Consistency...");
		if (this.rule1 != null && this.rule2 != null)
			checkRulePair();
		else
			check();
	}

	public void check() {
		// System.out.println("Consistency.check()");
		try {
			Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> 
			excludeCont = this.excludeContainer
					.getContainer(CriticalPair.EXCLUDE);
			GraGra gra = this.excludeContainer.getGrammar();

			for (Enumeration<Rule> keys = excludeCont.keys(); keys.hasMoreElements();) {
				Rule r1 = keys.nextElement();
				Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = excludeCont.get(r1);
				for (Enumeration<Rule> k2 = secondPart.keys(); k2.hasMoreElements();) {
					Rule r2 = k2.nextElement();
					Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> pair = secondPart.get(r2);
					Boolean b = pair.first;
					if (b.booleanValue()) {
						// System.out.println(r1.getName()+" "+r2.getName());
						Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> v = pair.second;
						int size = v.size();

						for (int i = 0; i < size; i++) {
							Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> pi = v.elementAt(i);
							Pair<OrdinaryMorphism, OrdinaryMorphism> p = pi.first;
							Graph g = p.first.getImage();
							if (!gra.checkGraphConsistency(g)) {
								// System.out.println(r1.getName()+"
								// "+r2.getName());
								// System.out.println("critical graph
								// INCONSISTENT, remove it");
								v.removeElement(p);
								BaseFactory.theFactory().destroyMorphism(p.first);
								BaseFactory.theFactory().destroyMorphism(p.second);
								i--;
								size = v.size();
							}
						}
						if (v.size() == 0) { // move (r1,r2) from exclude- to
							// conflict free container
							this.excludeContainer
									.moveEntryFromExcludeToConflictFreeContainer(
											r1, r2);
						}
					}
				}
			}
		} catch (InvalidAlgorithmException ex) {
		}
		// System.out.println("Consistency.check() END");
	}

	public void checkRulePair() {
		try {
			// System.out.println("Consistency.checkRulePair()");
			Hashtable<Rule, Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>>> excludeCont = this.excludeContainer
					.getContainer(CriticalPair.EXCLUDE);
			GraGra gra = this.excludeContainer.getGrammar();

			for (Enumeration<Rule> keys = excludeCont.keys(); keys.hasMoreElements();) {
				Rule r1 = keys.nextElement();
				if (r1 == this.rule1) {
					Hashtable<Rule, Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>>> secondPart = excludeCont.get(r1);
					for (Enumeration<Rule> k2 = secondPart.keys(); k2
							.hasMoreElements();) {
						Rule r2 = k2.nextElement();
						if (r2 == this.rule2) {
							Pair<Boolean, Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>>> pair = secondPart.get(r2);
							Boolean b = pair.first;
							if (b.booleanValue()) {
								Vector<Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>>> v = pair.second;
								int size = v.size();
								for (int i = 0; i < size; i++) {
									Pair<Pair<OrdinaryMorphism, OrdinaryMorphism>, Pair<OrdinaryMorphism, OrdinaryMorphism>> pi = v.elementAt(i);
									Pair<OrdinaryMorphism, OrdinaryMorphism> p = pi.first;
									Graph g = p.first.getImage();
									if (!g.isEmpty()
											&& !gra.checkGraphConsistency(g)) {
										v.removeElement(p);
										p.first.dispose();
										p.second.dispose();
										i--;
										size = v.size();
									}
								}
								if (v.size() == 0) { // move (r1,r2) from
									// exclude- to conflict
									// free container
									this.excludeContainer
											.moveEntryFromExcludeToConflictFreeContainer(
													r1, r2);
								}
							}
						}
					}
				}
			}
		} catch (InvalidAlgorithmException ex) {
		}
		// System.out.println("Consistency.checkRulePair() END");
	}
}
