/**
 * FlowControlInterpreter.java
 *
 * Created 02.01.2012 - 00:55:09
 */
package de.tub.tfs.henshin.editor.util.flowcontrol;

import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.ConditionalUnit;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.IndependentUnit;
import org.eclipse.emf.henshin.model.LoopUnit;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.SequentialUnit;
import org.eclipse.emf.henshin.model.TransformationUnit;

import de.tub.tfs.henshin.model.flowcontrol.Activity;
import de.tub.tfs.henshin.model.flowcontrol.CompoundActivity;
import de.tub.tfs.henshin.model.flowcontrol.ConditionalActivity;
import de.tub.tfs.henshin.model.flowcontrol.End;
import de.tub.tfs.henshin.model.flowcontrol.FlowDiagram;
import de.tub.tfs.henshin.model.flowcontrol.FlowElement;
import de.tub.tfs.henshin.model.flowcontrol.ParameterMapping;
import de.tub.tfs.henshin.model.flowcontrol.ParameterProvider;
import de.tub.tfs.henshin.model.flowcontrol.Transition;

/**
 * 
 * 
 * @author nam
 */
public class FlowControlInterpreter {

	private static class ParameterTrace extends LinkedList<TransformationUnit> {

		private static final long serialVersionUID = 1L;

		private boolean found = false;

		/**
		 * 
		 */
		public ParameterTrace() {
			super();
			// TODO Auto-generated constructor stub
		}

		/**
		 * @param c
		 */
		public ParameterTrace(Collection<? extends TransformationUnit> c) {
			super(c);
		}

		/**
		 * @return the found
		 */
		public boolean isFound() {
			return found;
		}

		/**
		 * @param found
		 *            the found to set
		 */
		public void setFound(boolean found) {
			this.found = found;
		}
	}

	private FlowDiagram diagram;

	private Map<TransformationUnit, FlowElement> generated;

	/**
	 * @param diagram
	 */
	public FlowControlInterpreter(FlowDiagram diagram) {
		super();

		this.diagram = diagram;

		generated = new HashMap<TransformationUnit, FlowElement>();
	}

	/**
	 * @return
	 */
	public TransformationUnit parse() {
		LinkedList<Object> result = new LinkedList<Object>();

		generated.clear();

		parse(diagram.getStart(), result, new ArrayList<Object>());

		SequentialUnit parsed = merge(result);

		parsed.setRollback(diagram.isRollback());
		parsed.setStrict(diagram.isStrict());

		createInputParameters(parsed);
		createOutputParameters(parsed);

		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getSrc().getProvider() == diagram
					|| m.getTarget().getProvider() == diagram) {
				continue;
			}

			Parameter src = m.getSrc().getHenshinParameter();
			ParameterTrace srcTrace = new ParameterTrace();
			Parameter target = m.getTarget().getHenshinParameter();
			ParameterTrace targetTrace = new ParameterTrace();

			buildTrace(srcTrace, m.getSrc(), parsed);
			buildTrace(targetTrace, m.getTarget(), parsed);

			ParameterTrace merged = new ParameterTrace(targetTrace);
			ParameterTrace srcList = new ParameterTrace(srcTrace);
			ParameterTrace targetList = new ParameterTrace(targetTrace);

			merged.retainAll(srcTrace);

			srcList.removeAll(targetTrace);
			targetList.removeAll(srcTrace);

			target = createHenshinParameters(targetList, false,
					target.getName());

			src = createHenshinParameters(srcList, true, src.getName());

			TransformationUnit top = merged.getLast();

			org.eclipse.emf.henshin.model.ParameterMapping newMappingSrc = HenshinFactory.eINSTANCE
					.createParameterMapping();
			org.eclipse.emf.henshin.model.ParameterMapping newMappingTarget = HenshinFactory.eINSTANCE
					.createParameterMapping();

			Parameter newParameter = HenshinFactory.eINSTANCE.createParameter();

			newParameter.setName(src.getName() + "2" + target.getName());
			newParameter.setUnit(top);

			top.getParameters().add(newParameter);

			newMappingSrc.setSource(src);
			newMappingSrc.setTarget(newParameter);

			newMappingTarget.setSource(newParameter);
			newMappingTarget.setTarget(target);

			top.getParameterMappings().add(newMappingSrc);
			top.getParameterMappings().add(newMappingTarget);
		}

		return parsed;
	}

	/**
	 * @param e
	 * @param parsed
	 * @param context
	 */
	private void parse(FlowElement e, LinkedList<Object> parsed,
			List<Object> context) {
		if (!(e instanceof End)) {
			if (e instanceof Activity) {
				parseActivity((Activity) e, parsed, context);
			} else {
				parse(e.getOut(), parsed, context);
			}
		}
	}

	/**
	 * @param t
	 * @param parsed
	 * @param context
	 */
	private void parse(Transition t, LinkedList<Object> parsed,
			List<Object> context) {
		parse(t.getNext(), parsed, context);
	}

	/**
	 * @param a
	 * @param parsed
	 * @param context
	 */
	private void parseActivity(Activity a, LinkedList<Object> parsed,
			List<Object> context) {
		if (a instanceof ConditionalActivity) {
			parseConditionalActivity((ConditionalActivity) a, parsed, context);
		} else {
			if (a instanceof CompoundActivity) {
				parseCompoundActivity((CompoundActivity) a, parsed, context);
			} else {
				NamedElement c = a.getContent();

				if (c instanceof Rule) {
					Rule newRule = (Rule) EcoreUtil.copy(c);

					generated.put(newRule, a);

					parsed.add(newRule);
				} else if (c instanceof FlowDiagram) {
					parsed.add(new FlowControlInterpreter((FlowDiagram) c)
							.parse());
				}
			}

			parse(a.getOut(), parsed, context);
		}
	}

	/**
	 * @param a
	 * @param parsed
	 * @param context
	 */
	private void parseCompoundActivity(CompoundActivity a,
			LinkedList<Object> parsed, List<Object> context) {
		List<Activity> children = a.getChildren();

		IndependentUnit u = HenshinFactory.eINSTANCE.createIndependentUnit();

		if (!children.isEmpty()) {
			for (Activity child : children) {
				LinkedList<Object> childResult = new LinkedList<Object>();

				parseActivity(child, childResult, context);

				for (Object o : childResult) {
					u.getSubUnits().add((TransformationUnit) o);
				}
			}

		}

		parsed.add(u);
	}

	/**
	 * @param a
	 * @param parsed
	 * @param context
	 */
	private void parseConditionalActivity(ConditionalActivity a,
			LinkedList<Object> parsed, List<Object> context) {
		if (!context.contains(a)) {
			LinkedList<Object> thenContent = new LinkedList<Object>();
			LinkedList<Object> elseContent = new LinkedList<Object>();

			ArrayList<Object> thenContext = new ArrayList<Object>(context);
			ArrayList<Object> elseContext = new ArrayList<Object>(context);

			thenContext.add(a);
			elseContext.add(a);

			parse(a.getOut(), thenContent, thenContext);
			parse(a.getAltOut(), elseContent, elseContext);

			TransformationUnit result = null;
			ConditionalUnit c = HenshinFactory.eINSTANCE
					.createConditionalUnit();
			NamedElement content = a.getContent();

			if (content instanceof Rule) {
				Rule newRule = EcoreUtil.copy((Rule) content);

				generated.put(newRule, a);

				c.setIf(newRule);
			} else if (content instanceof FlowDiagram) {
				TransformationUnit parsedUnit = new FlowControlInterpreter(
						(FlowDiagram) content).parse();

				generated.put(parsedUnit, a);

				c.setIf(parsedUnit);
			}

			c.setThen(merge(thenContent));

			if (thenContent.contains(a)) {
				thenContent.remove(a);

				LoopUnit loop = HenshinFactory.eINSTANCE.createLoopUnit();

				loop.setSubUnit(c);

				SequentialUnit r = HenshinFactory.eINSTANCE
						.createSequentialUnit();

				r.getSubUnits().add(loop);

				for (Object o : elseContent) {
					if (o instanceof TransformationUnit) {
						r.getSubUnits().add((TransformationUnit) o);
					}
				}

				result = r;
			} else {
				for (int i = thenContent.size() - 1; i >= 0; i--) {
					Object thenObj = thenContent.get(i);

					if (thenObj instanceof FlowElement) {
						parsed.add(thenObj);
					}
				}

				result = c;
			}

			if (result == c) {
				c.setElse(merge(elseContent));
			}

			if (elseContent.contains(a)) {
				elseContent.remove(a);

				LoopUnit loop = HenshinFactory.eINSTANCE.createLoopUnit();

				loop.setSubUnit(c);

				SequentialUnit r = HenshinFactory.eINSTANCE
						.createSequentialUnit();

				r.getSubUnits().add(loop);

				for (Object u : thenContent) {
					if (u instanceof TransformationUnit) {
						r.getSubUnits().add((TransformationUnit) u);
					}
				}

				result = r;
			} else {
				for (int i = elseContent.size() - 1; i >= 0; i--) {
					Object elseObj = elseContent.get(i);

					if (elseObj instanceof FlowElement) {
						parsed.add(elseObj);
					}
				}
			}

			parsed.add(result);
		} else {
			parsed.add(a);
		}
	}

	/**
	 * @param l
	 * @return
	 */
	private SequentialUnit merge(List<Object> l) {
		ArrayList<Object> tmp = new ArrayList<Object>(l);
		Iterator<Object> it = tmp.iterator();

		while (it.hasNext()) {
			Object object = (Object) it.next();

			if (!(object instanceof TransformationUnit)) {
				it.remove();
			}
		}

		SequentialUnit u = HenshinFactory.eINSTANCE.createSequentialUnit();

		for (Object o : tmp) {
			u.getSubUnits().add((TransformationUnit) o);
		}

		return u;
	}

	private void createInputParameters(TransformationUnit parsed) {
		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getSrc().getProvider() == diagram) {
				ParameterTrace trace = new ParameterTrace();

				buildTrace(trace, m.getTarget(), parsed);

				createHenshinParameters(trace, false, m.getTarget().getName());

				break;
			}
		}
	}

	private void createOutputParameters(TransformationUnit parsed) {
		for (ParameterMapping m : diagram.getParameterMappings()) {
			if (m.getTarget().getProvider() == diagram) {
				ParameterTrace trace = new ParameterTrace();

				buildTrace(trace, m.getSrc(), parsed);

				createHenshinParameters(trace, true, m.getSrc().getName());

				break;
			}
		}
	}

	private void buildTrace(ParameterTrace trace,
			de.tub.tfs.henshin.model.flowcontrol.Parameter p,
			TransformationUnit parsed) {
		if (parsed == null) {
			return;
		}

		ParameterProvider pProvider = p.getProvider();

		if (pProvider instanceof FlowElement) {
			if (generated.get(parsed) == p.getProvider()) {
				trace.add(parsed);
				trace.setFound(true);

				return;
			}
		}

		List<TransformationUnit> subUnits = parsed.getSubUnits(false);

		if (!subUnits.isEmpty()) {
			trace.add(parsed);

			for (TransformationUnit u : subUnits) {
				buildTrace(trace, p, u);

				if (trace.isFound()) {
					break;
				}
			}

			if (!trace.isFound()) {
				trace.removeLast();
			}
		}

	}

	private Parameter createHenshinParameters(ParameterTrace units,
			boolean isSrc, String name) {
		Parameter curr = units.getLast().getParameter(name);

		for (int i = units.size() - 2; i >= 0; i--) {
			Parameter newParameter = HenshinFactory.eINSTANCE.createParameter();
			org.eclipse.emf.henshin.model.ParameterMapping newMapping = HenshinFactory.eINSTANCE
					.createParameterMapping();
			TransformationUnit currUnit = units.get(i);

			newParameter.setName(name);

			if (isSrc) {
				newMapping.setSource(curr);
				newMapping.setTarget(newParameter);
			} else {
				newMapping.setTarget(curr);
				newMapping.setSource(newParameter);
			}

			currUnit.getParameters().add(newParameter);
			currUnit.getParameterMappings().add(newMapping);

			curr = newParameter;
		}

		return curr;
	}
}
