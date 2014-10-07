/**
 * 
 */
package agg.editor.impl;

import java.awt.Dimension;
import java.util.Enumeration;
import java.util.Hashtable;
import java.util.List;
import java.util.Vector;

import agg.util.XMLHelper;
import agg.xt_basis.Arc;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.Match;
import agg.xt_basis.NestedApplCond;
import agg.xt_basis.Node;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeException;
import agg.xt_basis.agt.AmalgamatedRule;
import agg.xt_basis.agt.MultiRule;
import agg.xt_basis.agt.RuleScheme;


/**
 * @author olga
 *
 */
public class EdRuleScheme extends EdRule {

	protected String name;
    protected EdRule itsKernelRule;
    final protected List<EdRule> itsMultiRules;
    protected EdRule itsAmalgamatedRule;
	
	
    public EdRuleScheme(final RuleScheme ruleScheme, final EdTypeSet types) {
    	super(ruleScheme, types);
    	
    	this.name = ruleScheme.getSchemeName();
    	
    	this.itsKernelRule = new EdRule(ruleScheme.getKernelRule(), types);
//      this.kernelRule.setKind(0);
	
    	this.itsMultiRules = new Vector<EdRule>();
        createMultiRules(ruleScheme.getMultiRules());  
        
        if (ruleScheme.getAmalgamatedRule() != null) {
        	makeAmalgamatedRule(ruleScheme.getAmalgamatedRule());
        }
    }
	
    public void dispose() {
    	if (this.itsAmalgamatedRule != null)
    		this.itsAmalgamatedRule.dispose();
 
    	for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		this.itsMultiRules.remove(0).dispose();
    	}
    	this.itsKernelRule.dispose();
    	this.name = "";
    	super.dispose();
    }
    
    public RuleScheme getBasisRuleScheme() {
    	return (RuleScheme) this.bRule;
    }
    
    public void setGraGra(final EdGraGra gra) {
    	super.setGraGra(gra);
    	
    	this.itsKernelRule.setGraGra(gra);
    	
    	for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		final EdRule multiRule = this.itsMultiRules.get(i);
    		multiRule.setGraGra(gra);
    	}
    	
    	if (this.itsAmalgamatedRule != null)
    		this.itsAmalgamatedRule.setGraGra(gra);
    }
    
    public void setUndoManager(EditUndoManager anUndoManager) {
    	this.undoManager = anUndoManager;
//		this.eLeft.setUndoManager(this.undoManager);
//		this.eRight.setUndoManager(this.undoManager);
		
    	this.itsKernelRule.setUndoManager(this.undoManager);
		
		for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		final EdRule multiRule = this.itsMultiRules.get(i);
    		multiRule.setUndoManager(this.undoManager);
    	}
	}
    
    public EdRule getRule(final String rulename) {
		if (this.itsKernelRule.getName().equals(rulename)
				|| rulename.equals(this.getName()+"."+this.itsKernelRule.getName()))
			return this.itsKernelRule;
		 
		for(int i=0; i<this.itsMultiRules.size(); i++) {	      	    			
			final EdRule multiRule = this.itsMultiRules.get(i);
			if (multiRule.getName().equals(rulename)
					|| rulename.equals(this.getName()+"."+multiRule.getName()))
				return multiRule;
		}
		
		return null;
	}
    
    public EdRule getKernelRule() {
    	return this.itsKernelRule;
    }
    
    public List<EdRule> getMultiRules() {
    	return this.itsMultiRules;
    }
    
    public EdRule getMultiRule(final Rule basisRule) {
    	for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		final EdRule multiRule = this.itsMultiRules.get(i);
    		if (multiRule.getBasisRule() == basisRule)
    			return multiRule;
    	}
    	return null;
    }
    
    public EdRule getMultiRule(final Graph g) {
    	for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		final EdRule multiRule = this.itsMultiRules.get(i);
    		if (multiRule.getBasisRule().getLeft() == g
    				|| multiRule.getBasisRule().getRight() == g)
    			return multiRule;
    	}
    	return null;
    }
    
    public EdRule getKernelRule(final Graph g) {
    	if (this.itsKernelRule.getBasisRule().getLeft() == g
    			|| this.itsKernelRule.getBasisRule().getRight() == g)
    		return this.itsKernelRule;
    	
    	return null;
    }
    
    public EdRule getRule(final Graph g) {
    	EdRule r = this.getKernelRule(g);
    	if (r == null) 
    		r = this.getMultiRule(g);
    	return r;
    }
    
    public boolean isRuleOfScheme(final Rule r) {
    	return ((RuleScheme)this.bRule).isRuleOfScheme(r);
    }
    
    public EdRule getAmalgamatedRule() {
    	if (this.itsAmalgamatedRule != null) {
    		return this.itsAmalgamatedRule;
    	}
    	else if (((RuleScheme)this.bRule).getAmalgamatedRule() != null) {
        	makeAmalgamatedRule(((RuleScheme)this.bRule).getAmalgamatedRule());
        	return this.itsAmalgamatedRule;
        } 
        return null;
    }
    
    public void createMultiRules(final List<Rule> multiRules) {     
    	for (int i = 0; i<multiRules.size(); i++) {
    		final MultiRule multiRule = (MultiRule) multiRules.get(i);
	        final EdRule r = new EdRule(multiRule, this.getTypeSet());
	        
	        r.setGraGra(this.eGra);
	        r.setUndoManager(this.undoManager);
	        
	        this.itsMultiRules.add(r);     
    	}
    }

    public EdRule addMultiRule(final String ruleName) {
        MultiRule mr = (MultiRule)((RuleScheme) this.getBasisRule()).addMultiRule(ruleName);
        
        EdRule r = new EdRule(mr, this.getTypeSet());
        applyLayoutOfKernelRule(r);
        
        r.setGraGra(this.eGra);
        r.setUndoManager(this.undoManager);
        
        this.itsMultiRules.add(r);
        return r;
    } 
 
    private EdNode addNodeCopy(final EdNode sourceNode, final EdGraph targetGraph) {		
		EdNode en = targetGraph.copyNode(sourceNode, sourceNode.getX(), sourceNode.getY());

//		targetGraph.addCreatedToUndo(en);
//		targetGraph.undoManagerEndEdit();
			
		return en;
	}
    
    private EdArc addArcCopy(final EdArc sourceArc, 
			final EdGraphObject targetSrc, 
			final EdGraphObject targetTar, 
			final EdGraph targetGraph) {
		
			EdArc ea = targetGraph.copyArc(sourceArc, targetSrc, targetTar);
//			targetGraph.addCreatedToUndo(ea);
//			targetGraph.undoManagerEndEdit();
			
			return ea;
	}
    
    public void propagateAddGraphObjectToMultiRule(final EdGraphObject kernObj) {
		for (int i=0; i<this.itsMultiRules.size(); i++) {
			EdRule mr = this.itsMultiRules.get(i);
			if (this.itsKernelRule.getLeft() == kernObj.getContext()) {
				if (kernObj.isNode()) {
					EdNode copy = this.addNodeCopy((EdNode) kernObj, mr.getLeft());
					if (copy != null) {
						try {
							((MultiRule)mr.getBasisRule()).addEmbeddingLeft(kernObj.getBasisObject(), copy.getBasisObject());
						
							copy.addContextUsage(String.valueOf(kernObj.hashCode()));
							copy.addContextUsage(kernObj.getContextUsage());	
						} catch (BadMappingException ex) {}
					}
				} else {
					Node bsrcObj = (Node) ((MultiRule)mr.getBasisRule())
									.getEmbeddingLeft()
									.getImage(((EdArc)kernObj).getSource().getBasisObject());
					Node btarObj = (Node) ((MultiRule)mr.getBasisRule())
								.getEmbeddingLeft()
								.getImage(((EdArc)kernObj).getTarget().getBasisObject());
					if (bsrcObj != null && btarObj != null) {
						EdNode srcObj = mr.getLeft().findNode(bsrcObj);
						EdNode tarObj = mr.getLeft().findNode(btarObj);
						if (srcObj != null && tarObj != null) {
							EdArc copy = this.addArcCopy((EdArc)kernObj, srcObj, tarObj, mr.getLeft());
							if (copy != null) {
								try {
									//TODO
									copy.addContextUsage(kernObj.getContextUsage());									
									((MultiRule)mr.getBasisRule()).addEmbeddingLeft(kernObj.getBasisObject(), copy.getBasisObject());
								} catch (BadMappingException ex) {}
							}
						}
					}
				}
			} else if (this.itsKernelRule.getRight() == kernObj.getContext()) {
				if (kernObj.isNode()) {
					EdNode copy = this.addNodeCopy((EdNode) kernObj, mr.getRight());
					if (copy != null) {
						try {
							((MultiRule)mr.getBasisRule()).addEmbeddingRight(kernObj.getBasisObject(), copy.getBasisObject());
							
							copy.addContextUsage(String.valueOf(kernObj.hashCode()));
							copy.addContextUsage(kernObj.getContextUsage());	
						} catch (BadMappingException ex) {}
					}
				} else {
					Node bsrcObj = (Node) ((MultiRule)mr.getBasisRule())
												.getEmbeddingRight()
													.getImage(((EdArc)kernObj)
														.getSource().getBasisObject());
					Node btarObj = (Node) ((MultiRule)mr.getBasisRule())
												.getEmbeddingRight()
													.getImage(((EdArc)kernObj)
														.getTarget().getBasisObject());
					if (bsrcObj != null && btarObj != null) {
						EdNode srcObj = mr.getRight().findNode(bsrcObj);
						EdNode tarObj = mr.getRight().findNode(btarObj);
						if (srcObj != null && tarObj != null) {
							EdArc copy = this.addArcCopy((EdArc)kernObj, srcObj, tarObj, mr.getRight());
							if (copy != null) {
								try {
									//TODO
									copy.addContextUsage(kernObj.getContextUsage());
									((MultiRule)mr.getBasisRule()).addEmbeddingRight(kernObj.getBasisObject(), copy.getBasisObject());
								} catch (BadMappingException ex) {}
							}
						}
					}
				}
			}
		}
	}
	
	
	public void propagateRemoveGraphObjectToMultiRule(final EdGraphObject srcObject) {
		if (srcObject.isNode()) {
			Vector<EdArc> arcs = srcObject.getContext().getIncomingArcs((EdNode) srcObject);
			for (int i=0; i<arcs.size(); i++) {
				EdArc arc = arcs.get(i);
				this.propagateRemoveGraphObjectToMultiRule(arc);				
			}
			arcs = srcObject.getContext().getOutgoingArcs((EdNode) srcObject);
			for (int i=0; i<arcs.size(); i++) {
				EdArc arc = arcs.get(i);
				this.propagateRemoveGraphObjectToMultiRule(arc);				
			}			
		}
				
		for (int i=0; i<this.itsMultiRules.size(); i++) {
			EdRule mr = this.itsMultiRules.get(i);
						
			if (this.itsKernelRule.getLeft() == srcObject.getContext()) {
				EdGraphObject tarObj = mr.getLeft().findGraphObject(
						((MultiRule)mr.getBasisRule()).getEmbeddingLeft().getImage(srcObject.getBasisObject()));

				if (srcObject.isNode()) {
					Node bNode = (Node)((MultiRule)mr.getBasisRule())
						.getEmbeddingLeft().getImage(srcObject.getBasisObject());
					if (bNode != null) {
						try {							
							((MultiRule)mr.getBasisRule()).removeEmbeddingLeft(srcObject.getBasisObject());	
							
							srcObject.addContextUsage(String.valueOf(tarObj.hashCode()));
							
							mr.getLeft().delNode(bNode);
						} catch (TypeException ex) {}					
					}
					
				} else {
					Arc bArc = (Arc)((MultiRule)mr.getBasisRule())
						.getEmbeddingLeft().getImage(srcObject.getBasisObject());
					if (bArc != null) {
						try {							
							((MultiRule)mr.getBasisRule()).removeEmbeddingLeft(srcObject.getBasisObject());
							
							srcObject.addContextUsage(String.valueOf(tarObj.hashCode()));
							
							mr.getLeft().delArc(bArc);							
						} catch (TypeException ex) {}
					}
				}
			} else if (this.itsKernelRule.getRight() == srcObject.getContext()) {
				
				EdGraphObject tarObj = mr.getRight().findGraphObject(
						((MultiRule)mr.getBasisRule()).getEmbeddingRight().getImage(srcObject.getBasisObject()));

				if (srcObject.isNode()) {
					Node bNode = (Node)((MultiRule)mr.getBasisRule())
						.getEmbeddingRight().getImage(srcObject.getBasisObject());
					if (bNode != null) {
						try {
							((MultiRule)mr.getBasisRule()).removeEmbeddingRight(srcObject.getBasisObject());
							
							srcObject.addContextUsage(String.valueOf(tarObj.hashCode()));
							
							mr.getRight().delNode(bNode);
						} catch (TypeException ex) {}					
					}
					
				} else {
					Arc bArc = (Arc)((MultiRule)mr.getBasisRule())
						.getEmbeddingRight().getImage(srcObject.getBasisObject());
					if (bArc != null) {
						try {
							((MultiRule)mr.getBasisRule()).removeEmbeddingRight(srcObject.getBasisObject());
							
							srcObject.addContextUsage(String.valueOf(tarObj.hashCode()));
							
							mr.getRight().delArc(bArc);
						} catch (TypeException ex) {}
					}
				}
			}
		}
	}
	
	
    public void propagateAddMappingToMultiRule(EdGraphObject leftgo, EdGraphObject rightgo) {
    	for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		final EdRule multiRule = this.itsMultiRules.get(i);
    		GraphObject objL = ((MultiRule)multiRule.getBasisRule()).getEmbeddingLeft()
    							.getImage(leftgo.getBasisObject());
			GraphObject objR = ((MultiRule)multiRule.getBasisRule()).getEmbeddingRight()
								.getImage(rightgo.getBasisObject());
			if (objL != null && objR != null) {
				EdGraphObject goL = multiRule.getLeft().findGraphObject(objL);
				EdGraphObject goR = multiRule.getRight().findGraphObject(objR);
				if (goL != null && goR != null) {				
					multiRule.interactRule(goL, goR);					
				}
			}    	
    	}
    }
    
    public void propagateRemoveMappingToMultiRule(EdGraphObject go, boolean left) {
    	for (int i = 0; i<this.itsMultiRules.size(); i++) {
    		final EdRule multiRule = this.itsMultiRules.get(i);
    		if (left) {
	    		GraphObject objL = ((MultiRule)multiRule.getBasisRule()).getEmbeddingLeft()
	    							.getImage(go.getBasisObject());
				if (objL != null) {
					EdGraphObject goL = multiRule.getLeft().findGraphObject(objL);					
					if (goL != null) {
						EdGraphObject goR = multiRule.getRight().findGraphObject(
								multiRule.getBasisRule().getImage(goL.getBasisObject()));
						if (goR != null) { 
							multiRule.removeRuleMapping(goL);
						}
						// remove mapping of appl conds
						// ! add kernel obj to undo !
						for (int j = 0; j < multiRule.getNestedACs().size(); j++) {
							EdNestedApplCond ac = (EdNestedApplCond)multiRule.getNestedACs().get(j);
							EdGraphObject o = ac.findGraphObject(ac.getMorphism().getImage(objL));
							if (o != null) {
								multiRule.addDeletedACMappingToUndo(go, o); //  add kernel obj to undo !
								multiRule.removeNestedACMapping(goL, (NestedApplCond)ac.getMorphism());
								multiRule.undoManagerEndEdit();
							}
						}
						for (int j = 0; j < multiRule.getNACs().size(); j++) {
							EdNAC ac = multiRule.getNACs().get(j);
							EdGraphObject o = ac.findGraphObject(ac.getMorphism().getImage(objL));
							if (o != null) {
								//TODO
								multiRule.addDeletedNACMappingToUndo(go, o); //  add kernel obj to undo !
								multiRule.removeNACMapping(goL, ac.getMorphism());
								multiRule.undoManagerEndEdit();
							}
						}
						for (int j = 0; j < multiRule.getPACs().size(); j++) {
							EdPAC ac = multiRule.getPACs().get(j);
							EdGraphObject o = ac.findGraphObject(ac.getMorphism().getImage(objL));
							if (o != null) {
								multiRule.addDeletedPACMappingToUndo(go, o); //  add kernel obj to undo !
								multiRule.removePACMapping(goL, ac.getMorphism());
								multiRule.undoManagerEndEdit();
							}
						}
					}
				}
    		} else {
    			GraphObject objR = ((MultiRule)multiRule.getBasisRule()).getEmbeddingRight()
									.getImage(go.getBasisObject());
				if (objR != null) {
					EdGraphObject goR = multiRule.getRight().findGraphObject(objR);
					if (goR != null) {
						if (multiRule.getBasisRule().getInverseImage(goR.getBasisObject()).hasMoreElements()) {
							GraphObject objL = multiRule.getBasisRule().getInverseImage(goR.getBasisObject()).nextElement();
							EdGraphObject goL = multiRule.getLeft().findGraphObject(objL);
							if (goL != null) {
								multiRule.removeRuleMapping(goL);	
							}
						}
					}
				}
    		}   	
    	}
    }
    
    private void applyLayoutOfKernelRule(final EdRule multiRule) {
    	final Enumeration<GraphObject> 
    	embLeft = ((MultiRule)multiRule.getBasisRule()).getEmbeddingLeft().getDomain();
    	while (embLeft.hasMoreElements()) {
    		GraphObject o = embLeft.nextElement();
    		GraphObject o1 = ((MultiRule)multiRule.getBasisRule()).getEmbeddingLeft().getImage(o);
    		EdGraphObject go = this.itsKernelRule.getLeft().findGraphObject(o);
    		EdGraphObject go1 = multiRule.getLeft().findGraphObject(o1);
    		if (go != null && go1 != null && go.isNode()) {
    			go1.setXY(go.getX(), go.getY());
    		}
    	}
    	
    	final Enumeration<GraphObject> 
    	embRight = ((MultiRule)multiRule.getBasisRule()).getEmbeddingRight().getDomain();
    	while (embRight.hasMoreElements()) {
    		GraphObject o = embRight.nextElement();
    		GraphObject o1 = ((MultiRule)multiRule.getBasisRule()).getEmbeddingRight().getImage(o);
    		EdGraphObject go = this.itsKernelRule.getRight().findGraphObject(o);
    		EdGraphObject go1 = multiRule.getRight().findGraphObject(o1);
    		if (go != null && go1 != null && go.isNode()) {
    			go1.setXY(go.getX(), go.getY());
    		}
    	}
    }
    										
    public void removeMultiRule(final EdRule r) {     
    	if (this.itsMultiRules.contains(r)) {
    		((RuleScheme) this.getBasisRule()).removeMultiRule(r.getBasisRule());
    		this.itsMultiRules.remove(r);
    	}
    }
      
    public void setAmalgamatedRule(final EdRule r) {
    	if (this.itsAmalgamatedRule != null)
    		this.itsAmalgamatedRule.dispose();
    	
	    this.itsAmalgamatedRule = r;
	    this.itsAmalgamatedRule.setGraGra(this.eGra);
	      
	    setXYLayoutOfAmalgamatedRule(this.itsAmalgamatedRule);
	      
	    this.itsAmalgamatedRule.getLeft().setEditable(false);
	    this.itsAmalgamatedRule.getRight().setEditable(false);
	    this.itsAmalgamatedRule.setEditable(false);
	    
	    // nur in EdRuleScheme available
	    if (this.itsAmalgamatedRule.getMatch() != null)
	    	this.eGra.getBasisGraGra().addMatch(this.itsAmalgamatedRule.getMatch());
    }

    private void makeAmalgamatedRule(final AmalgamatedRule rule) {
    	this.itsAmalgamatedRule = new EdRule(rule);
    	
    	this.itsAmalgamatedRule.setGraGra(this.eGra);
	      
	    setXYLayoutOfAmalgamatedRule(this.itsAmalgamatedRule);
	      
	    this.itsAmalgamatedRule.getLeft().setEditable(false);
	    this.itsAmalgamatedRule.getRight().setEditable(false);
	    this.itsAmalgamatedRule.setEditable(false);
    }
    
    public void setLayoutByIndexFrom(EdRuleScheme rs) {
    	this.setLayoutByIndexFrom(rs, false);
    }
    
    public void setLayoutByIndexFrom(EdRuleScheme rs, boolean inverse) {
    	if (inverse) {
	    	this.itsKernelRule.getLeft().setLayoutByIndex(
					rs.getKernelRule().getRight(), true);
	    	this.itsKernelRule.getRight().setLayoutByIndex(
	    			rs.getKernelRule().getLeft(), true);
    	}
    	else {
    		this.itsKernelRule.getLeft().setLayoutByIndex(
					rs.getKernelRule().getLeft(), true);
	    	this.itsKernelRule.getRight().setLayoutByIndex(
	    			rs.getKernelRule().getRight(), true);
    	}
    	for (int n = 0; n < this.itsKernelRule.itsACs.size(); n++) {
			EdNestedApplCond ac = (EdNestedApplCond) this.itsKernelRule.itsACs.get(n);
			if (n < rs.getKernelRule().getNestedACs().size()) {
				EdNestedApplCond ac1 = (EdNestedApplCond) rs.getKernelRule().getNestedACs().get(n);			
				ac.setLayoutByIndex(ac1, true);
			}
		}
    	for (int n = 0; n < this.itsKernelRule.getNACs().size(); n++) {
			EdNAC ac = this.itsKernelRule.getNACs().get(n);
			if (n < rs.getKernelRule().getNACs().size()) {
				EdNAC ac1 = rs.getKernelRule().getNACs().get(n);
				ac.setLayoutByIndex(ac1, true);
			}
		}
		for (int n = 0; n < this.itsKernelRule.getPACs().size(); n++) {
			EdPAC ac = this.itsKernelRule.getPACs().get(n);
			if (n < rs.getKernelRule().getPACs().size()) {
				EdPAC ac1 = rs.getKernelRule().getPACs().get(n);
				ac.setLayoutByIndex(ac1, true);
			}
		}
		
    	for (int i=0; i<this.itsMultiRules.size(); i++) {
			EdRule mr = this.itsMultiRules.get(i);	
			EdRule r = rs.getMultiRules().get(i);	
			if (inverse) {
				mr.getLeft().setLayoutByIndex(r.getRight(), true);
				mr.getRight().setLayoutByIndex(r.getLeft(), true);
			}
			else {
				mr.getLeft().setLayoutByIndex(r.getLeft(), true);
				mr.getRight().setLayoutByIndex(r.getRight(), true);
			}
			
			for (int n = 0; n < mr.getNestedACs().size(); n++) {
				EdNestedApplCond ac = (EdNestedApplCond) mr.getNestedACs().get(n);
				if (n < r.getNestedACs().size()) {
					EdNestedApplCond ac1 = (EdNestedApplCond) r.getNestedACs().get(n);
					ac.setLayoutByIndex(ac1, true);
				}
			}
			for (int n = 0; n < mr.getNACs().size(); n++) {
				EdNAC ac = mr.getNACs().get(n);
				if (n < r.getNACs().size()) {
					EdNAC ac1 = r.getNACs().get(n);
					ac.setLayoutByIndex(ac1, true);
				}
			}
			for (int n = 0; n < mr.getPACs().size(); n++) {
				EdPAC ac = mr.getPACs().get(n);
				if (n < r.getPACs().size()) {
					EdPAC ac1 = r.getPACs().get(n);
					ac.setLayoutByIndex(ac1, true);
				}
			}
    	}
    }
    
    private void setXYLayoutOfAmalgamatedRule(final EdRule r) {
    	if (r.getMatch() != null) {
    	    for (int i=0; i<r.getLeft().getNodes().size(); i++) {
    	    	final EdNode nodeL = r.getLeft().getNodes().get(i);
    	    	final Node bnodeL = (Node) r.getMatch().getImage(nodeL.getBasisObject());
    	    	final EdNode gnode = this.eGra.getGraph().findNode(bnodeL);
    	    	if (gnode != null) {
    	    		nodeL.setXY(gnode.getX(), gnode.getY());
    	    		final Node bnodeR = (Node) r.getBasisRule().getImage(nodeL.getBasisObject());
    	    		if (bnodeR != null) {
	    	    		final EdNode nodeR = r.getRight().findNode(bnodeR);
	    	    		if (nodeR != null) {
	    	    			nodeR.setXY(nodeL.getX(), nodeL.getY());
	    	    			nodeR.getLNode().setFrozen(true);
	    	    		}
	    	    	}
    	    	}
    	    }
    	    
    	    // make more layout of RHS of rule r
    	    r.getRight().layoutBasisGraph(new Dimension(400, 300));
    	}
    }
    
    /**
	 * Updates layout of the specified match.
	 */
	public void updateMatch(Match m, EdGraph eImageGraph) {
		if (m == null || eImageGraph == null)
			return;

		eImageGraph.clearMarks();

		Enumeration<GraphObject> domain = m.getDomain();
		while (domain.hasMoreElements()) {
			GraphObject bOrig = domain.nextElement();
			GraphObject bImage = m.getImage(bOrig);

			EdNode enI = eImageGraph.findNode(bImage);
			if (enI != null) {
				if (enI.isMorphismMarkEmpty())
					enI.addMorphismMark(enI.getMyKey());
			} else {
				EdArc eaI = eImageGraph.findArc(bImage);
				if (eaI != null) {
					if (eaI.isMorphismMarkEmpty())
						eaI.addMorphismMark(eaI.getMyKey());
				}
			}
		}
	}
	
    public void removeAmalgamatedRule() {
    	if (this.itsAmalgamatedRule != null) {
	    	if (this.itsAmalgamatedRule.getBasisRule() != null
	    			&& this.itsAmalgamatedRule.getBasisRule().getMatch() != null) {
	    		this.itsAmalgamatedRule.getBasisRule().getMatch().clear();
	    		this.itsAmalgamatedRule.updateMatch();
	    		this.eGra.getBasisGraGra().destroyMatch(this.itsAmalgamatedRule.getBasisRule().getMatch());    		
	    	}
	    	
	    	this.itsAmalgamatedRule.dispose();
	    	((RuleScheme)this.bRule).disposeAmalgamatedRule();
	    	this.itsAmalgamatedRule = null;
    	}
    	// test output
//    	((RuleScheme)this.bRule).showAttrContextVars();
    }
    
    public boolean deleteGraphObjectsOfType(
			final EdGraphObject tgo,
			boolean addToUndo) {
			    	
    	boolean allDone = true;		
		for(int j=0; j<this.itsMultiRules.size(); j++) {
	        EdRule r = this.itsMultiRules.get(j);
	        if (!this.delObjsOfTypeFromMultiRule(tgo, r, addToUndo))
	        	allDone = false;
		}
		
		allDone = this.delObjsOfTypeFromKernelRule(tgo, addToUndo);
		
		return allDone;
    }
    
    private boolean delObjsOfTypeFromMultiRule(
			final EdGraphObject tgo,
			final EdRule r,
			boolean addToUndo) {
		
		List<EdGraphObject> listLHS = r.eLeft.getGraphObjectsOfType(tgo);
		List<EdGraphObject> listRHS = r.eRight.getGraphObjectsOfType(tgo);
		
		if (addToUndo) {
			for (int i=0; i<listLHS.size(); i++) {
				EdGraphObject go = listLHS.get(i);
				if (!((MultiRule)r.getBasisRule()).isTargetOfEmbeddingLeft(go.getBasisObject())) {
					EdGraphObject rgo = r.getRight().findGraphObject(
							r.getBasisRule().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
			}
		}
		
		boolean allDone = true;
			
		for (int j=0; j<r.itsNACs.size(); j++) {
			EdNAC nac = r.itsNACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = nac.findGraphObject(
								nac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedNACMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
			}
			if (!nac.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}

		for (int j=0; j<r.itsPACs.size(); j++) {
			EdPAC pac = r.itsPACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = pac.findGraphObject(
								pac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedPACMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
			}
			if (!pac.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}
	
		for (int j=0; j<r.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond)r.itsACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = ac.findGraphObject(
								ac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedACMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
				ac.storeMappingOfGraphObjectsOfType(tgo, ac);
			}
			if (!ac.deleteGraphObjectsOfType(tgo, addToUndo))
				allDone = false;
		}
		
		if (addToUndo) {
			for (int i=0; i<listLHS.size(); i++) {
				EdGraphObject go = listLHS.get(i);	
				if (((MultiRule)r.getBasisRule()).isTargetOfEmbeddingLeft(go.getBasisObject())) 
					continue;
				
				if (go.isNode()) {
					if (r.eLeft.deleteNode((EdNode)go, addToUndo)) { 			
					} else {
						allDone = false;
					}
				} else {				
					if (r.eLeft.deleteArc((EdArc)go, addToUndo)) { 			
					} else {
						allDone = false;
					}
				}
			}
			
			for (int i=0; i<listRHS.size(); i++) {
				EdGraphObject go = listRHS.get(i);
				if (((MultiRule)r.getBasisRule()).isTargetOfEmbeddingRight(go.getBasisObject())) 
					continue;
				
				if (go.isNode()) {
					if (r.eRight.deleteNode((EdNode)go, addToUndo)) { 				
					} else {
						allDone = false;
					}
				} else {					
					if (r.eRight.deleteArc((EdArc)go, addToUndo)) { 			
					} else {
						allDone = false;
					}				
				}
			}
		} 
		else {
			if (!r.eLeft.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
			if (!r.eRight.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}
		
		return allDone;
	}
 
    private boolean delObjsOfTypeFromMultiRule(
			final EdType t,
			final EdRule r,
			boolean addToUndo) {
		
		List<EdGraphObject> listLHS = r.eLeft.getGraphObjectsOfType(t);
		List<EdGraphObject> listRHS = r.eRight.getGraphObjectsOfType(t);
		
		if (addToUndo) {
			for (int i=0; i<listLHS.size(); i++) {
				EdGraphObject go = listLHS.get(i);
				if (!((MultiRule)r.getBasisRule()).isTargetOfEmbeddingLeft(go.getBasisObject())) {
					EdGraphObject rgo = r.getRight().findGraphObject(
							r.getBasisRule().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
			}
		}
		
		boolean allDone = true;
			
		for (int j=0; j<r.itsNACs.size(); j++) {
			EdNAC nac = r.itsNACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = nac.findGraphObject(
								nac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedNACMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
			}
			if (!nac.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
		}

		for (int j=0; j<r.itsPACs.size(); j++) {
			EdPAC pac = r.itsPACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = pac.findGraphObject(
								pac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedPACMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
			}
			if (!pac.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
		}
	
		for (int j=0; j<r.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond)r.itsACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = ac.findGraphObject(
								ac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						r.addDeletedACMappingToUndo(go, rgo);
						r.undoManagerEndEdit();
					}
				}
				ac.storeMappingOfGraphObjectsOfType(t, ac);
			}
			if (!ac.deleteGraphObjectsOfType(t, addToUndo))
				allDone = false;
		}
		
		if (addToUndo) {
			for (int i=0; i<listLHS.size(); i++) {
				EdGraphObject go = listLHS.get(i);	
				if (((MultiRule)r.getBasisRule()).isTargetOfEmbeddingLeft(go.getBasisObject())) 
					continue;
				
				if (go.isNode()) {
					if (r.eLeft.deleteNode((EdNode)go, addToUndo)) { 			
					} else {
						allDone = false;
					}
				} else {				
					if (r.eLeft.deleteArc((EdArc)go, addToUndo)) { 			
					} else {
						allDone = false;
					}
				}
			}
			
			for (int i=0; i<listRHS.size(); i++) {
				EdGraphObject go = listRHS.get(i);
				if (((MultiRule)r.getBasisRule()).isTargetOfEmbeddingRight(go.getBasisObject())) 
					continue;
				
				if (go.isNode()) {
					if (r.eRight.deleteNode((EdNode)go, addToUndo)) { 				
					} else {
						allDone = false;
					}
				} else {					
					if (r.eRight.deleteArc((EdArc)go, addToUndo)) { 			
					} else {
						allDone = false;
					}				
				}
			}
		} 
		else {
			if (!r.eLeft.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
			if (!r.eRight.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
		}
		
		return allDone;
	}

    private boolean delObjsOfTypeFromKernelRule(
			final EdGraphObject tgo,
			boolean addToUndo) {
		
		List<EdGraphObject> listLHS = this.itsKernelRule.eLeft.getGraphObjectsOfType(tgo);
		List<EdGraphObject> listRHS = this.itsKernelRule.eRight.getGraphObjectsOfType(tgo);
		
		if (addToUndo) {
			for (int i=0; i<listLHS.size(); i++) {
				EdGraphObject go = listLHS.get(i);				
				EdGraphObject rgo = this.itsKernelRule.eRight.findGraphObject(
							this.itsKernelRule.getBasisRule().getImage(go.getBasisObject()));	
				if (rgo != null) {	
					this.propagateRemoveRuleMappingToMultiRule(go);
					this.itsKernelRule.addDeletedMappingToUndo(go, rgo);
					this.itsKernelRule.undoManagerEndEdit();						
				}														
			}
		}
		
		boolean allDone = true;
			
		for (int j=0; j<this.itsKernelRule.itsNACs.size(); j++) {
			EdNAC nac = this.itsKernelRule.itsNACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = nac.findGraphObject(
								nac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.itsKernelRule.addDeletedNACMappingToUndo(go, rgo);
						this.itsKernelRule.undoManagerEndEdit();
					}
				}
			}
			if (!nac.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}

		for (int j=0; j<this.itsKernelRule.itsPACs.size(); j++) {
			EdPAC pac = this.itsKernelRule.itsPACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = pac.findGraphObject(
								pac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {	
						this.itsKernelRule.addDeletedPACMappingToUndo(go, rgo);
						this.itsKernelRule.undoManagerEndEdit();
					}
				}
			}
			if (!pac.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}
	
		for (int j=0; j<this.itsKernelRule.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond)this.itsKernelRule.itsACs.get(j);
			if (addToUndo) {
				for (int i=0; i<listLHS.size(); i++) {
					EdGraphObject go = listLHS.get(i);
					EdGraphObject rgo = ac.findGraphObject(
								ac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.itsKernelRule.addDeletedACMappingToUndo(go, rgo);
						this.itsKernelRule.undoManagerEndEdit();
					}
				}
				ac.storeMappingOfGraphObjectsOfType(tgo, ac);
			}
			if (!ac.deleteGraphObjectsOfType(tgo, addToUndo))
				allDone = false;
		}
		
		if (addToUndo) {
			for (int i=0; i<listLHS.size(); i++) {
				EdGraphObject go = listLHS.get(i);	
				this.propagateRemoveGraphObjectToMultiRule(go);
				
				if (go.isNode()) { 
					if (!this.itsKernelRule.eLeft.deleteNode((EdNode)go, addToUndo))
						allDone = false;
				} else {
					if (!this.itsKernelRule.eLeft.deleteArc((EdArc)go, addToUndo))
						allDone = false;
				}
			}
			for (int i=0; i<listRHS.size(); i++) {
				EdGraphObject go = listRHS.get(i);	
				this.propagateRemoveGraphObjectToMultiRule(go);
				
				if (go.isNode()) { 
					if (!this.itsKernelRule.eRight.deleteNode((EdNode)go, addToUndo))
						allDone = false;
				} else {
					if (!this.itsKernelRule.eRight.deleteArc((EdArc)go, addToUndo))
						allDone = false;
				}
			}
		} 
		else {
			if (!this.itsKernelRule.eLeft.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		
			if (!this.itsKernelRule.eRight.deleteGraphObjectsOfTypeFromGraph(tgo, addToUndo))
				allDone = false;
		}
		
		return allDone;
	}
    
    
    public boolean deleteGraphObjectsOfType(
			final EdType t,
			boolean addToUndo) {
		
    	boolean allDone = true;
		for(int j=0; j<this.itsMultiRules.size(); j++) {
	        EdRule r = this.itsMultiRules.get(j);
	        if (!this.delObjsOfTypeFromMultiRule(t, r, addToUndo))
	        	allDone = false;
		}
		allDone = this.delObjsOfTypeFromKernelRule(t, addToUndo);
		return allDone;
    }
    
    private boolean delObjsOfTypeFromKernelRule(
			final EdType t,
			boolean addToUndo) {
		
		List<EdGraphObject> list = this.itsKernelRule.eLeft.getGraphObjectsOfType(t);
		if (addToUndo) {
			for (int i=0; i<list.size(); i++) {
				EdGraphObject go = list.get(i);				
				EdGraphObject rgo = this.eRight.findGraphObject(
							this.getBasisRule().getImage(go.getBasisObject()));	
				if (rgo != null) {											
					this.propagateRemoveRuleMappingToMultiRule(go);
					this.itsKernelRule.addDeletedMappingToUndo(go, rgo);
					this.itsKernelRule.undoManagerEndEdit();						
				}														
			}
		}
		
		boolean allDone = true;
			
		for (int j=0; j<this.itsKernelRule.itsNACs.size(); j++) {
			EdNAC nac = this.itsKernelRule.itsNACs.get(j);
			if (addToUndo) {
				for (int i=0; i<list.size(); i++) {
					EdGraphObject go = list.get(i);
					EdGraphObject rgo = nac.findGraphObject(
								nac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.itsKernelRule.addDeletedNACMappingToUndo(go, rgo);
						this.itsKernelRule.undoManagerEndEdit();
					}
				}
			}
			if (!nac.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
		}

		for (int j=0; j<this.itsKernelRule.itsPACs.size(); j++) {
			EdPAC pac = this.itsKernelRule.itsPACs.get(j);
			if (addToUndo) {
				for (int i=0; i<list.size(); i++) {
					EdGraphObject go = list.get(i);
					EdGraphObject rgo = pac.findGraphObject(
								pac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.itsKernelRule.addDeletedPACMappingToUndo(go, rgo);
						this.itsKernelRule.undoManagerEndEdit();
					}
				}
			}
			if (!pac.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
				allDone = false;
		}
	
		for (int j=0; j<this.itsKernelRule.itsACs.size(); j++) {
			EdNestedApplCond ac = (EdNestedApplCond)this.itsKernelRule.itsACs.get(j);
			if (addToUndo) {
				for (int i=0; i<list.size(); i++) {
					EdGraphObject go = list.get(i);
					EdGraphObject rgo = ac.findGraphObject(
								ac.getMorphism().getImage(go.getBasisObject()));			
					if (rgo != null) {			
						this.itsKernelRule.addDeletedACMappingToUndo(go, rgo);
						this.itsKernelRule.undoManagerEndEdit();
					}
				}
				ac.storeMappingOfGraphObjectsOfType(t, ac);
			}
			if (!ac.deleteGraphObjectsOfType(t, addToUndo))
				allDone = false;
		}
		
		if (!this.itsKernelRule.eLeft.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
			allDone = false;
		if (!this.itsKernelRule.eRight.deleteGraphObjectsOfTypeFromGraph(t, addToUndo))
			allDone = false;
		
		return allDone;
	}
    
	/* (non-Javadoc)
	 * @see agg.util.XMLObject#XreadObject(agg.util.XMLHelper)
	 */
	public void XreadObject(XMLHelper h) {
        h.enrichObject(this.itsKernelRule);
        this.itsKernelRule.setGraGra(this.eGra);
        
        for(int j=0; j<this.itsMultiRules.size(); j++) {
        	EdRule r = this.itsMultiRules.get(j);
        	h.enrichObject(r);      	
        	r.setGraGra(this.eGra);
        	enrichContextUsageOfEmbedTarget(r);
        }
        
        if (this.itsAmalgamatedRule != null) {
        	h.enrichObject(this.itsAmalgamatedRule);
        	this.itsAmalgamatedRule.setGraGra(this.eGra);
        }
	}

	private void enrichContextUsageOfEmbedTarget(EdRule mr) {
		List<EdNode> nodes = mr.getLeft().getNodes();
		for (int i=0; i<nodes.size(); i++) {
			EdNode n = nodes.get(i);
//			EdGraphObject kernObj = mr.getLeft().getSourceObjOfGraphEmbedding(n);
			if (((MultiRule)mr.getBasisRule()).isTargetOfEmbeddingLeft(n.getBasisObject())) {
				GraphObject kern = ((MultiRule)mr.getBasisRule())
					.getEmbeddingLeft().getInverseImage(n.getBasisObject()).nextElement();
				EdGraphObject kerngo =  this.itsKernelRule.getLeft().findGraphObject(kern);
				if (kerngo != null)
					n.addContextUsage(String.valueOf(kerngo.hashCode()));
			}
		}
		nodes = mr.getRight().getNodes();
		for (int i=0; i<nodes.size(); i++) {
			EdNode n = nodes.get(i);
//			EdGraphObject kernObj = mr.getRight().getSourceObjOfGraphEmbedding(n);
			if (((MultiRule)mr.getBasisRule()).isTargetOfEmbeddingRight(n.getBasisObject())) {
				GraphObject kern = ((MultiRule)mr.getBasisRule())
					.getEmbeddingRight().getInverseImage(n.getBasisObject()).nextElement();
				EdGraphObject kerngo =  this.itsKernelRule.getRight().findGraphObject(kern);
				if (kerngo != null)
					n.addContextUsage(String.valueOf(kerngo.hashCode()));
			}
		}
	}
	
	/* (non-Javadoc)
	 * @see agg.util.XMLObject#XwriteObject(agg.util.XMLHelper)
	 */
	public void XwriteObject(XMLHelper h) {		
	      h.addObject("", this.itsKernelRule, true);
	      
	      for (int j=0; j<this.itsMultiRules.size(); j++) {
	        h.addObject("", this.itsMultiRules.get(j), true);
	      }
	      
	      if (this.itsAmalgamatedRule != null) {
	    	  h.addObject("", this.itsAmalgamatedRule, true);
	      }
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.StateEditable#restoreState(java.util.Hashtable)
	 */
	public void restoreState(Hashtable<?, ?> state) {
		super.restoreState(state);
	}

	/* (non-Javadoc)
	 * @see javax.swing.undo.StateEditable#storeState(java.util.Hashtable)
	 */
	public void storeState(Hashtable<Object, Object> state) {
		super.restoreState(state);
	}

}
