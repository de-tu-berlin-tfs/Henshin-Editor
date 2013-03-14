/**
 * Title:        AGG<p>
 * Description:  <p>
 * Copyright:    Copyright (c) Michael Matz<p>
 * Company:      TU Berlin<p>
 * @author Michael Matz
 * @version 1.0
 */
package agg.cons;

import java.util.Enumeration;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Vector;

import agg.attribute.handler.AttrHandlerException;
import agg.attribute.handler.HandlerExpr;
import agg.attribute.handler.HandlerType;
import agg.attribute.handler.SymbolTable;
import agg.attribute.handler.impl.javaExpr.JexExpr;
import agg.attribute.impl.CondMember;
import agg.attribute.impl.CondTuple;
import agg.attribute.impl.ContextView;
import agg.attribute.impl.TupleMapping;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.attribute.parser.javaExpr.ASTExpression;
import agg.attribute.parser.javaExpr.ASTPrimaryExpression;
import agg.attribute.parser.javaExpr.SimpleNode;
import agg.util.Pair;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.BaseFactory;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.MatchHelper;
import agg.xt_basis.Node;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeException;
import agg.xt_basis.TypeSet;

public class Convert {

	private Rule rule;

	private AtomConstraint atom;
	private String error = "";
	private boolean setEnabledTG = false;
	
	public Convert(Rule r, AtomConstraint a) {
		this.rule = r;
		this.atom = a;
		if (this.atom.getSource().isEmpty()) {
			this.setEnabledTG = true;
		}
	}

	class Link {
		private Link up;

		private GraphObject o;

		public Link() {
			this.up = null;
			this.o = null;
		}

		public Link find() {
			Link l = this;
			while (l.up != null)
				l = l.up;
			Link m = this;
			while (m.up != null) {
				Link n = m.up;
				m.up = l;
				m = n;
			}
			return l;
		}

		public Link link(Link other) {
			Link po = other.find();
			Link pt = find();
			po.up = pt;
			return pt;
		}

		public GraphObject get() {
			return find().o;
		}

		public void set(GraphObject go) {
			find().o = go;
		}
	}

	/**
	 * Calculates the pushout of two morphisms. Calculates this diagram:
	 * P-----r----->G1 | | m | p2 | | v v G2---------->G3 p1 It returns the pair
	 * (p1,p2) of the two resulting morphisms. I.e. the firs in the pair is
	 * parallel to the first argument. Note, that the order of the two input
	 * morphisms only matters if the nodes are attributed. In this case the
	 * second morphism (m) is used for "setting" the free variables in r, and
	 * applying them is done by the first morphism r. This resembles the use in
	 * rules and matches, therefore the arguments are named such. Note further,
	 * that otherwise the properties of the morphisms don't matter in any way.
	 * The returned thing will be a pushout as much as possible (i.e. p2*r==p1*m
	 * in any case). m and r can be partial. They can be broken (in the sense of
	 * deleting nodes, while mapping edges), or they can identify nodes in one
	 * while not in the other.
	 */
	private Pair<OrdinaryMorphism, OrdinaryMorphism> pushout(
			final OrdinaryMorphism r,
			final OrdinaryMorphism m) {
		
		this.error = "";
		final BaseFactory bf = BaseFactory.theFactory();
		final Graph P = r.getOriginal();
		if (P != m.getOriginal()) {
			System.err
					.println("Failed!  Convert.pushout() called with morphisms from different sources");
			return null;
		}

		final Graph G3 = bf.createGraph(r.getImage().getTypeSet());
		G3.setName(r.getName());

		final OrdinaryMorphism p1 = bf.createMorphism(m.getImage(), G3);
		p1.setName("p1_morphism");

		final OrdinaryMorphism p2 = bf.createMorphism(r.getImage(), G3);
		p2.setName("p2_morphism");

		final HashMap<Object, Link> hash = new HashMap<Object, Link>();
		
		Iterator<?> en = P.getNodesSet().iterator();
		while (en.hasNext())
			hash.put(en.next(), new Link());
		en = P.getArcsSet().iterator();
		while (en.hasNext())
			hash.put(en.next(), new Link());
		
		en = r.getImage().getNodesSet().iterator();
		while (en.hasNext())
			hash.put(en.next(), new Link());
		en = r.getImage().getArcsSet().iterator();
		while (en.hasNext())
			hash.put(en.next(), new Link());
		
		en = m.getImage().getNodesSet().iterator();
		while (en.hasNext())
			hash.put(en.next(), new Link());
		en = m.getImage().getArcsSet().iterator();
		while (en.hasNext())
			hash.put(en.next(), new Link());
		
		/*
		 * Now we link together all graph objects which somehow are mapped to
		 * each other. This UNION/FIND structure enables us to quickly find
		 * nodes which must be identified.
		 */
		Link empty = new Link();
		/*
		* This is used as target for linking, for
		* deleting unmapped objects. This makes it
		* simpler to link unmapped objects, instead
		* of testing for null of return value of
		* getImage(o).
		*/		
		hash.put(null, empty);
		en = P.getNodesSet().iterator();
		while (en.hasNext()) {
			GraphObject go = (GraphObject) en.next();
			GraphObject img1 = r.getImage(go);
			GraphObject img2 = m.getImage(go);
			// img2 has to be always existent; check this ...
			if (img2 == null) {
				System.err
						.println("Argh!  Convert.pushout() FAILED! (m match is corrupt)");
				return null;
			}
			
			Link l = hash.get(go);
			/*
			 * We don't need to test for img being null here, because at that
			 * hash index the "empty" link is placed, which is the right thing
			 * here.
			 */
			l.link(hash.get(img1)).link(hash.get(img2));
			/*
			 * Hmm, if we have a mapped edge, we also need to identify both
			 * source and target nodes. This _should_ already be done, because a
			 * valid morphism only maps an edge when also mapping both end
			 * nodes.
			 */
			/*
			 * But if we delete a nodes, also all incident edges need deletion,
			 * which also should be done already, but we are not sure of this.
			 * So we simply delete all those edges here (by linking them to
			 * empty). This then also deletes all other strange edges mapped to
			 * this one by the other morphism.
			 */
			if ((img1 == null) && go.isNode()) {
				Node n = (Node) go;
				Iterator<Arc> arcs = n.getIncomingArcsSet().iterator();
				while (arcs.hasNext())
					empty.link(hash.get(arcs.next()));
				arcs = n.getOutgoingArcsSet().iterator();
				while (arcs.hasNext())
					empty.link(hash.get(arcs.next()));
			}
		}
		en = P.getArcsSet().iterator();
		while (en.hasNext()) {
			GraphObject go = (GraphObject) en.next();
			GraphObject img1 = r.getImage(go);
			GraphObject img2 = m.getImage(go);
			// img2 has to be always existent; check this ...
			if (img2 == null) {
				System.err
						.println("Argh!  Convert.pushout() FAILED! (m match is corrupt)");
				return null;
			}
			
			Link l = hash.get(go);
			/*
			 * We don't need to test for img being null here, because at that
			 * hash index the "empty" link is placed, which is the right thing
			 * here.
			 */
			l.link(hash.get(img1)).link(hash.get(img2));
			/*
			 * Hmm, if we have a mapped edge, we also need to identify both
			 * source and target nodes. This _should_ already be done, because a
			 * valid morphism only maps an edge when also mapping both end
			 * nodes.
			 */
			/*
			 * But if we delete a nodes, also all incident edges need deletion,
			 * which also should be done already, but we are not sure of this.
			 * So we simply delete all those edges here (by linking them to
			 * empty). This then also deletes all other strange edges mapped to
			 * this one by the other morphism.
			 */
		}
		
		empty = empty.find();
		
		// before to compute PO, disable Multiplicity check for min and max, too
		int checkLevelTG = G3.getTypeSet().getLevelOfTypeGraphCheck();
		if (this.setEnabledTG)
			G3.getTypeSet().setLevelOfTypeGraphCheck(TypeSet.ENABLED);
		
		/* Now we can create the objects in G3. We first create the nodes. */		
		for (int k = 0; k < 2; k++) {
			OrdinaryMorphism morph = (k == 0) ? p1 : p2;
			en = morph.getOriginal().getNodesSet().iterator();
			while (en.hasNext()) {
				Node n = (Node) en.next();
				Link l = (hash.get(n)).find();
				if (l == empty)
					continue; /* This is a to be deleted node. */
				/*
				 * Otherwise in l.get() there is the node in G3, which should
				 * this be mapped to. l.get() may still be null, which means,
				 * that the node doesn't yet exist in G3, and has to be created
				 * now (and placed into the link).
				 */
				Node n2 = (Node) l.get();
				if (n2 == null) {
					try {
						n2 = G3.copyNode(n);
						n2.setContextUsage(n.getContextUsage());
						if (checkAttributeValue(n, n2)) {
							try {
								morph.addMapping(n, n2);
							} catch (BadMappingException ex1) {
								this.error = ex1.getLocalizedMessage();
								return null;
							}
						}
						l.set(n2);
					} catch (TypeException ex) {
						this.error = ex.getLocalizedMessage();
//						System.out.println(this.getClass().getName()+".pushout: "+error);
						return null;
					}
				} else {
					if (checkAttributeValue(n, n2)) {
						try {
							morph.addMapping(n, n2);
						} catch (BadMappingException ex1) {
							this.error = ex1.getLocalizedMessage();
							return null;
						}
					}
				}
			}
		}

		/* And now the arcs. */
		for (int k = 0; k < 2; k++) {
			OrdinaryMorphism morph = (k == 0) ? p1 : p2;
			en = morph.getOriginal().getArcsSet().iterator();
			while (en.hasNext()) {
				Arc a = (Arc) en.next();
				Link l = (hash.get(a)).find();
				if (l == empty)
					continue;
				Arc a2 = (Arc) l.get();
				if (a2 == null) {
					GraphObject src = morph.getImage(a.getSource());
					GraphObject tgt = morph.getImage(a.getTarget());
					/*
					 * Be sure to only create an arc in G3 if not source and
					 * target are deleted.
					 */
					if (src == null || tgt == null)
						continue;
					try {
						a2 = G3.copyArc(a, (Node) src, (Node) tgt);
						a2.setContextUsage(a.getContextUsage());
						if (checkAttributeValue(a, a2)) {
							try {
								morph.addMapping(a, a2);
							} catch (BadMappingException ex1) {
								this.error = ex1.getLocalizedMessage();
								return null;
							}
						}
						l.set(a2);
					} catch (TypeException e) {
						this.error = e.getLocalizedMessage();
//						System.out.println(this.getClass().getName()+".pushout: "+error);
						return null;
					}
				} else {
					if (checkAttributeValue(a, a2)) {
						try {
							morph.addMapping(a, a2);
						} catch (BadMappingException ex1) {
							this.error = ex1.getLocalizedMessage();
							return null;
						}
					}
				}
			}
		}

		// enable Multiplicity check again
		G3.getTypeSet().setLevelOfTypeGraphCheck(checkLevelTG);
		
		return new Pair<OrdinaryMorphism, OrdinaryMorphism>(p1, p2);
	}

	public String getErrorMsg() {
		return this.error;
	}
	
	public Vector<Object> convert() {
		final Vector<Object> ret = new Vector<Object>();
		if (!this.atom.isValid()) {
			return ret;
		}
		
		this.atom.adoptEntriesWhereEmpty();
		renameVariables(this.rule, this.atom);

		final Graph R = this.rule.getRight();

		/* .first corresponds to this.getSource(); .second with R */
		/*
		 * Wir brauchen auch disjunkte Vereinigung. Im Falle von attributierten
		 * Graphen spielt das eine Rolle (siehe ensure_pred.ggx aus der
		 * Diplomarbeit)
		 */

		boolean leftgraph = true;
		boolean disjunion = true;
		// Create S = R + P with disjunion
		Enumeration<Pair<OrdinaryMorphism, OrdinaryMorphism>> all_s = null;		
		all_s = MatchHelper.getOverlappings(this.atom, R, leftgraph, disjunion);

		while (all_s.hasMoreElements()) {
			Pair<OrdinaryMorphism, OrdinaryMorphism> morphs = all_s.nextElement();
			OrdinaryMorphism p = morphs.first;
			OrdinaryMorphism s = morphs.second;
			if (!p.isInjective()) {
				continue;
			}
			
			Enumeration<GraphObject> e = p.getDomain();
			while (e.hasMoreElements()) {
				GraphObject o = e.nextElement();
				if (o.getAttribute() == null)
					continue;

				GraphObject img = p.getImage(o);
				ValueTuple val = (ValueTuple) o.getAttribute();
				ValueTuple valimg = (ValueTuple) img.getAttribute();
				for (int i = 0; i < val.getSize(); i++) {
					ValueMember m = val.getValueMemberAt(i);
					ValueMember mimg = valimg.getValueMemberAt(i);
					if ((mimg.getExpr() != null) && mimg.getExpr().isComplex()) {
						if (m.getExpr() != null) {
							if (m.getExpr().isVariable()
									|| m.getExpr().isConstant()) {
								mimg.setExprAsText(m.getExprAsText());
								mimg.setTransient(true);
							}
							else if (m.getExpr().isComplex())
								mimg.setExpr(null);
						} else
							mimg.setExpr(null);
					}
				}
			}

			e = s.getDomain();
			while (e.hasMoreElements()) {
				GraphObject o = e.nextElement();
				if (o.getAttribute() == null) {
					continue;
				}
				
				GraphObject img = s.getImage(o);
				ValueTuple val = (ValueTuple) o.getAttribute();
				ValueTuple valimg = (ValueTuple) img.getAttribute();
				for (int i = 0; i < val.getSize(); i++) {
					ValueMember m = val.getValueMemberAt(i);
					ValueMember mimg = valimg.getValueMemberAt(i);
					if ((mimg.getExpr() != null) && mimg.getExpr().isComplex()) {
						if (m.getExpr() != null) {
							if (m.getExpr().isVariable()
									|| m.getExpr().isConstant())
								mimg.setExprAsText(m.getExprAsText());
							else if (m.getExpr().isComplex())
								mimg.setExpr(null);
						} else
							mimg.setExpr(null);
					}
				}
			}
			
			OrdinaryMorphism 
			pmatch = BaseFactory.theFactory()
							.createMorphfromMorph(p, this.atom.getAttrContext());

			if (pmatch == null) {
				continue;
			}
			
			final Vector<Object> v = new Vector<Object>();
			EvalSet set = null;
			final Enumeration<AtomConstraint> conclusions = this.atom.getConclusions();
			while (conclusions.hasMoreElements()) {
				final AtomConstraint conclusion = conclusions.nextElement();
				adoptEntriesWhereEmpty(pmatch);
				final Pair<OrdinaryMorphism, OrdinaryMorphism> po = pushout(conclusion, pmatch);
				if (po != null) {
					final OrdinaryMorphism t = po.first;
					final OrdinaryMorphism q = po.second;
					if (t.isTotal() && q.isTotal()) {
						t.setAttrContext(conclusion.getAttrContext());
						adoptEntriesWhereEmpty(t);
						v.add(new AtomApplCond(conclusion, s, t, q));
					}
				}
			} 
			if (!v.isEmpty()) {
				set = new EvalSet(v);				
				ret.add(set);
			}
		}
				
		return ret;
	}

	private boolean checkAttributeValue(GraphObject from, GraphObject to) {
		if (from.getAttribute() == null && to.getAttribute() == null)
			return true;
		else if (from.getAttribute() != null && to.getAttribute() != null) {
			ValueTuple valueFrom = (ValueTuple) from.getAttribute();
			ValueTuple valueTo = (ValueTuple) to.getAttribute();
			for (int i = 0; i < valueFrom.getSize(); i++) {
				ValueMember mFrom = valueFrom.getValueMemberAt(i);
				ValueMember mTo = valueTo.getValueMemberAt(i);
				if (mFrom.isSet() && mTo.isSet()) {
					if ((mFrom.getExpr().isConstant() || mTo.getExpr()
							.isConstant())
							&& !mFrom.getExprAsText().equals(
									mTo.getExprAsText()))
						return false;
				}
			}
			return true;
		} else
			return false;
	}

	private void adoptEntriesWhereEmpty(OrdinaryMorphism morph) {
		Enumeration<GraphObject> e = morph.getDomain();
		while (e.hasMoreElements()) {
			GraphObject obj = e.nextElement();
			GraphObject img = morph.getImage(obj);
			if (img.getAttribute() == null)
				continue;
			ContextView context = (ContextView) morph.getAttrContext();
			Vector<TupleMapping> mappings = context.getMappingsToTarget((ValueTuple) img
					.getAttribute());
			if (mappings != null) {
				mappings.elementAt(0).adoptEntriesWhereEmpty(
						(ValueTuple) obj.getAttribute(), (ValueTuple) img
								.getAttribute());
			}
		}
	}

	private void renameVariables(Rule r, AtomConstraint atomic) {
		OrdinaryMorphism m1 = r;
		Enumeration<AtomConstraint> conclusions = atomic.getConclusions();
		if (conclusions.hasMoreElements()) {
			OrdinaryMorphism m2 = conclusions.nextElement();
			int index = 1;
			String mark = String.valueOf(index);
			VarTuple varsm1 = (VarTuple) m1.getAttrContext().getVariables();
			VarTuple varsm2 = (VarTuple) m2.getAttrContext().getVariables();
			for (int i = 0; i < varsm1.getSize(); i++) {
				VarMember vm1 = varsm1.getVarMemberAt(i);
				VarMember vm2 = varsm2.getVarMemberAt(vm1.getName());
				if ((vm2 != null)
						&& vm1.getDeclaration().getTypeName().equals(
								vm2.getDeclaration().getTypeName())) {
					String from = vm2.getName();
					String to = vm2.getName() + mark;
					while (varsm2.getVarMemberAt(to) != null) {
						mark = String.valueOf(index++);
						to = vm2.getName() + mark;
					}
					vm2.getDeclaration().setName(to);
					
					// rename variables in left/right graphs of morphs
					setAttributeVariable(from, to, varsm2,
							m2.getSource().getNodesSet().iterator());
					setAttributeVariable(from, to, varsm2,
							m2.getSource().getArcsSet().iterator());
					
					setAttributeVariable(from, to, varsm2,
							m2.getTarget().getNodesSet().iterator());
					setAttributeVariable(from, to, varsm2,
							m2.getTarget().getArcsSet().iterator());
					
					CondTuple conds = (CondTuple) m2.getAttrContext()
							.getConditions();
					renameVariableInCondition(conds, from, to);

					while (conclusions.hasMoreElements()) {
						OrdinaryMorphism mc = conclusions.nextElement();
						VarTuple varsmc = (VarTuple) mc.getAttrContext()
								.getVariables();
						
						setAttributeVariable(from, to, varsmc,
								mc.getTarget().getNodesSet().iterator());
						setAttributeVariable(from, to, varsmc,
								mc.getTarget().getArcsSet().iterator());
						
						conds = (CondTuple) mc.getAttrContext().getConditions();
						renameVariableInCondition(conds, from, to);
					}
				}
			}
		}
	}

	private void renameVariableInCondition(CondTuple conds, String from,
			String to) {
		// rename variables in conditions
		for (int j = 0; j < conds.getSize(); j++) {
			CondMember cm = conds.getCondMemberAt(j);
//			String condStr = cm.getExprAsText();
			Vector<String> v1 = cm.getAllVariables();
			if (v1.contains(from)) {
				JexExpr oldExpr = (JexExpr) cm.getExpr();
				// System.out.println("ast: "+oldExpr.getAST());
				// System.out.println("Children of ast:
				// "+oldExpr.getAST().jjtGetNumChildren());
				Vector<String> variables = new Vector<String>();
				oldExpr.getAllVariables(variables);
				/*
				 * System.out.println("Variables of ast: "+variables.size());
				 * for(int ii=0; ii<variables.size(); ii++) {
				 * System.out.println("ast variable:
				 * "+(String)variables.get(ii)); }
				 */
				findPrimaryAndReplace((SimpleNode) oldExpr.getAST(), from, to,
						null);
				// System.out.println("--------------------------");
			}
		}
	}

	private void setAttributeVariable(
			final String from, 
			final String to,
			final VarTuple vars,
			final Iterator<?> elems) {

		while (elems.hasNext()) {
			GraphObject obj = (GraphObject) elems.next();
			ValueTuple fromObj = (ValueTuple) obj.getAttribute();
			for (int i = 0; i < fromObj.getSize(); i++) {
				ValueMember fromVM = fromObj.getValueMemberAt(i);
				if (fromVM.isSet()) {
					if (fromVM.getExpr().isVariable()) {
						if (fromVM.getExprAsText().equals(from)
								&& (vars.getVarMemberAt(to) != null)) {
							fromVM.setExprAsText(to);
							// System.out.println(">>>>> set Attribute expr (is
							// Variable) : "+from+" to "+to);
						}
					} else if (fromVM.getExpr().isComplex()) {
						// System.out.println("\n--------- set Attribute (is
						// Complex) : "+fromVM.getName()+" =
						// "+fromVM.getExprAsText());
//						VarMember toVM = vars.getVarMemberAt(to);
						// System.out.println("(to) VarMember :
						// "+toVM.getName()+" "+toVM);
						JexExpr oldExpr = (JexExpr) fromVM.getExpr();
						// System.out.println("ast: "+oldExpr.getAST());
						// System.out.println("Children of ast:
						// "+oldExpr.getAST().jjtGetNumChildren());
						Vector<String> variables = new Vector<String>();
						oldExpr.getAllVariables(variables);
						/*
						 * System.out.println("Variables of ast:
						 * "+variables.size()); for(int ii=0; ii<variables.size();
						 * ii++) { System.out.println("ast variable:
						 * "+(String)variables.get(ii)); }
						 */
						findPrimaryAndReplace((SimpleNode) oldExpr.getAST(),
								from, to, vars);
						// System.out.println("------------------------------");
					}
				}
			}
		}
	}

	private void findPrimaryAndReplace(SimpleNode node, String from, String to,
			VarTuple vars) {
		// System.out.println("Convert.findPrimaryAndChange: in "+node);
		for (int j = 0; j < node.jjtGetNumChildren(); j++) {
			SimpleNode n = (SimpleNode) node.jjtGetChild(j);
			// System.out.println(j+" Child of ast: "+n+" is "+n.getString());
			if (n instanceof ASTPrimaryExpression) {
				// System.out.println("Convert.findPrimaryAndChange:
				// ASTPrimaryExpression: "+n+" NumChildren: "+
				// n.jjtGetNumChildren());
				for (int j1 = 0; j1 < n.jjtGetNumChildren(); j1++) {
					SimpleNode n1 = (SimpleNode) n.jjtGetChild(j1);
					// System.out.println("n1: "+n1);
					if (n1 instanceof ASTExpression) {
						// System.out.println("n1:
						// "+((ASTExpression)n1).getString()+"
						// "+n1.jjtGetNumChildren()+" "+n1.jjtGetChild(0));
						findPrimaryAndReplace(n1, from, to, vars);
					}
				}

//				String ident = ((ASTPrimaryExpression) n).getIdentifier();
				// System.out.println("Identifier: "+ ident+" "+
				// ((ASTPrimaryExpression) n).getString());
				if (n.getString().equals(from)) {
					// System.out.println("from is found: getString()=
					// "+n.getString()+" toString()= "+n.toString()+"
					// hasStringType()= "+n.hasStringType()+" getSymbolTable()=
					// "+n.getSymbolTable());
					SymbolTable symbs = SimpleNode.getSymbolTable();
					// System.out.println("SymbolTable: "+from+" type=
					// "+symbs.getType(from)+" expr= "+ symbs.getExpr(from));
					// System.out.println("SymbolTable: "+to+" type=
					// "+symbs.getType(to)+" expr= "+ symbs.getExpr(to));

					boolean to_found = false;
					ContextView context = (ContextView) symbs;
					VarTuple vt = (VarTuple) context.getVariables();
					for (int i = 0; i < vt.getSize(); i++) {
						VarMember vm = vt.getVarMemberAt(i);
						// System.out.println(vm.getName()+" "+vm);
						if (vm.getName().equals(to)) {
							to_found = true;
							// System.out.println(to+" exists in SymbolTable ::
							// "+vm.getName()+" "+vm);
							HandlerType t = vm.getDeclaration().getType();
							try {
								HandlerExpr expression = vm.getHandler()
										.newHandlerExpr(t, to);
								// System.out.println(expression.getAST());
								SimpleNode test = (SimpleNode) expression
										.getAST().jjtGetChild(0);
								// System.out.println(test);
								node.replaceChild(n, test);
								// System.out.println("child replaced:
								// getString()=
								// "+node.jjtGetChild(0).getString()+"
								// "+node.jjtGetChild(0).toString());
							} catch (AttrHandlerException ex) {
							}
						}
					}
					if (!to_found && (vars != null)) {
						// System.out.println("Something wrong: "+to+" NOT FOUND
						// in SymbolTable! Try to replace if variable exists in
						// VarTuple.");
						for (int i = 0; i < vars.getSize(); i++) {
							VarMember vm = vars.getVarMemberAt(i);
							// System.out.println(vm.getName()+" "+vm);
							if (vm.getName().equals(to)) {
								to_found = true;
								// System.out.println(to+" exists in vars
								// "+vm.getName()+" "+vm);
								HandlerType t = vm.getDeclaration().getType();
								try {
									HandlerExpr expression = vm.getHandler()
											.newHandlerExpr(t, to);
									// System.out.println(expression.getAST());
									SimpleNode test = (SimpleNode) expression
											.getAST().jjtGetChild(0);
									// System.out.println(test);
									node.replaceChild(n, test);
									// System.out.println("child replaced:
									// getString()=
									// "+node.jjtGetChild(0).getString()+"
									// "+node.jjtGetChild(0).toString());
								} catch (AttrHandlerException ex) {
								}
							}
						}
						if (!to_found) {
							System.out.println("Something wrong again:  " + to
									+ "  NOT FOUND! CANNOT replace.");
						}
					}
				}
			} else {
				// System.out.println("Take Child of ast: "+n);
				findPrimaryAndReplace(n, from, to, vars);
			}
		}
	}

}
