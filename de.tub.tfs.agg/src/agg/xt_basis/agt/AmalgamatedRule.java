/**
 * 
 */
package agg.xt_basis.agt;

import java.util.Enumeration;
import java.util.Iterator;
import java.util.Vector;

import agg.attribute.impl.DeclMember;
import agg.attribute.impl.ValueMember;
import agg.attribute.impl.ValueTuple;
import agg.attribute.impl.VarMember;
import agg.attribute.impl.VarTuple;
import agg.xt_basis.BadMappingException;
import agg.xt_basis.Graph;
import agg.xt_basis.GraphObject;
import agg.xt_basis.OrdinaryMorphism;
import agg.xt_basis.Rule;
import agg.xt_basis.TypeSet;

/**
 * @author olga
 *
 */
public class AmalgamatedRule extends Rule {

	private RuleScheme itsRuleScheme;
	
	public AmalgamatedRule(final Graph left, final Graph right) {
		super(left, right);
	}
	
	public AmalgamatedRule(final TypeSet types) {
		super(types);
	}
	
	public AmalgamatedRule(final OrdinaryMorphism h) {
		super(h.getSource(), h.getTarget());
		
		makeRuleFromMorphism(h);
		
		this.unsetInputParameter();
	}
	
	public void dispose() {
		super.dispose();
		this.itsRuleScheme = null;
	}
	
	public void setRuleScheme(final RuleScheme rs) {
		this.itsRuleScheme = rs;
	}
	
	public RuleScheme getRuleScheme() {
		return this.itsRuleScheme;
	}
	
	private void makeRuleFromMorphism(final OrdinaryMorphism h) {
		Vector<String> list = this.itsOrig.getVariableNamesOfAttributes();
		list.addAll(this.itsImag.getVariableNamesOfAttributes());
		
		// set variables
		VarTuple varsMorph = (VarTuple) h.getAttrContext().getVariables();
		final VarTuple vars = (VarTuple) getAttrContext().getVariables();
		for (int j = 0; j < varsMorph.getSize(); j++) {
			VarMember vm = varsMorph.getVarMemberAt(j);
			if (list.contains(vm.getName())) {			
				DeclMember dm = (DeclMember) vm.getDeclaration();
				if (!vars.isDeclared(dm.getTypeName(), dm.getName())) {
					vars.declare(dm.getHandler(), dm.getTypeName(), dm.getName());
					if (vm.isSet()) {
						vars.getVarMemberAt(dm.getName()).setExprAsText(vm.getExprAsText());
					}
				}
			}
		}
				
		// add object mapping
		final Enumeration<GraphObject> dom = h.getDomain();
		while (dom.hasMoreElements()) {
			GraphObject obj = dom.nextElement();
			try {
				GraphObject img = h.getImage(obj);
				this.applyAttrValue(obj, img, vars);
				addMapping(obj, img);				
			} catch (BadMappingException ex) {}
		}		
		
		// check attr. setting of graph objects of the RHS, use variable value if it is available		
		applyVarValueToAttr(vars, getRight().getNodesSet().iterator());
		applyVarValueToAttr(vars, getRight().getArcsSet().iterator());
		
		this.removeUnusedVariableOfAttrContext();
	}
	
	private void applyVarValueToAttr(final VarTuple vars, final Iterator<?> objs) {
		while (objs.hasNext()) {
			GraphObject o = (GraphObject) objs.next();
			if (o.getAttribute() == null)
				continue;
			
			ValueTuple value = (ValueTuple) o.getAttribute();
			for (int i = 0; i < value.getSize(); i++) {
				ValueMember vm = value.getValueMemberAt(i);
				if (vm.isSet()
						&& vm.getExpr().isVariable()) {
					VarMember var = vars.getVarMemberAt(vm.getExprAsText());
					if (var != null && var.isSet()) {						
						vm.setExprAsText(var.getExprAsText());
					}
				}
			}
		}
	}
	
	private void applyAttrValue(
			final GraphObject from, 
			final GraphObject to,
			final VarTuple vars) {
		
		if (from.getAttribute() == null || to.getAttribute() == null)
			return;
		
		final ValueTuple valuefrom = (ValueTuple) from.getAttribute();
		final ValueTuple value = (ValueTuple) to.getAttribute();
		for (int i = 0; i < value.getSize(); i++) {
			ValueMember vm = value.getValueMemberAt(i);
			ValueMember vmfrom = valuefrom.getValueMemberAt(vm.getName());
			if (vmfrom != null) {
				// side effect during gluing of objects
//				if (!vm.isSet() && vmfrom.isSet()) {
//					vm.setExpr(vmfrom.getExpr());
//				}
				if (vm.isSet() && vm.getExpr().isVariable()) {
					if (vmfrom.isSet() && vmfrom.getExpr().isConstant()) {
						VarMember var = vars.getVarMemberAt(vm.getExprAsText());
						if (var != null) {
							if (!var.isSet()) {
								vm.setExpr(vmfrom.getExpr());
								var.setExpr(vmfrom.getExpr());
							} else if (var.getExprAsText().equals(vmfrom.getExprAsText())) {
								vm.setExpr(vmfrom.getExpr());
							}
						}					
					}
				}
			}
		}
	}
}
