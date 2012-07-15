/**
 * FlowControlInterpreter.java
 *
 * Created 02.01.2012 - 00:55:09
 */
package de.tub.tfs.henshin.editor.util.flowcontrol;

import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

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
		LinkedList<TransformationUnit> result = new LinkedList<TransformationUnit>();

		generated.clear();

		parse(diagram.getStart(), result, new LinkedList<Object>());

		TransformationUnit parsed = merge(result);

		if (parsed == null || parsed instanceof Rule) {
			SequentialUnit u = HenshinFactory.eINSTANCE.createSequentialUnit();

			if (parsed instanceof Rule) {
				u.getSubUnits().add(parsed);
			}

			parsed = u;
		}

		parsed.setName(diagram.getName());

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
	private void parse(FlowElement e, LinkedList<TransformationUnit> parsed,
			LinkedList<Object> context) {
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
	private void parse(Transition t, LinkedList<TransformationUnit> parsed,
			LinkedList<Object> context) {
		if (t != null) {
			parse(t.getNext(), parsed, context);
		}
	}

	/**
	 * @param a
	 * @param parsed
	 * @param context
	 */
	private void parseActivity(Activity a,
			LinkedList<TransformationUnit> parsed, LinkedList<Object> context) {
		if (a instanceof ConditionalActivity) {
			parseConditionalActivity((ConditionalActivity) a, parsed, context);
		} else {
			if (a instanceof CompoundActivity) {
				parseCompoundActivity((CompoundActivity) a, parsed, context);
			} else {
				NamedElement c = a.getContent();

				if (c instanceof Rule) {
					Rule newRule = (Rule) (c);

					generated.put(newRule, a);

					parsed.add(newRule);
				} else if (c instanceof FlowDiagram) {
					TransformationUnit parseUnit = new FlowControlInterpreter(
							(FlowDiagram) c).parse();

					generated.put(parseUnit, a);

					parsed.add(parseUnit);
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
			LinkedList<TransformationUnit> parsed, LinkedList<Object> context) {
		List<Activity> children = a.getChildren();

		IndependentUnit u = HenshinFactory.eINSTANCE.createIndependentUnit();

		if (!children.isEmpty()) {
			for (Activity child : children) {
				LinkedList<TransformationUnit> childResult = new LinkedList<TransformationUnit>();

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
			LinkedList<TransformationUnit> parsed, LinkedList<Object> context) {
		if (context.contains(a)) {
			context.add(a);
		} else {
			LinkedList<TransformationUnit> thenContent = new LinkedList<TransformationUnit>();
			LinkedList<TransformationUnit> elseContent = new LinkedList<TransformationUnit>();

			LinkedList<Object> thenContext = new LinkedList<Object>(context);
			LinkedList<Object> elseContext = new LinkedList<Object>(context);

			thenContext.add(a);
			elseContext.add(a);

			parse(a.getOut(), thenContent, thenContext);
			parse(a.getAltOut(), elseContent, elseContext);

			thenContext.remove(a);
			elseContext.remove(a);

			ConditionalUnit conditionalUnit = HenshinFactory.eINSTANCE
					.createConditionalUnit();

			TransformationUnit thenUnit = merge(thenContent);
			TransformationUnit elseUnit = merge(elseContent);

			NamedElement content = a.getContent();

			conditionalUnit.setName(content.getName());

			TransformationUnit result = conditionalUnit;

			if (content instanceof Rule) {
				Rule newRule = (Rule) content;

				generated.put(newRule, a);

				conditionalUnit.setIf(newRule);
			} else if (content instanceof FlowDiagram) {
				TransformationUnit parsedUnit = new FlowControlInterpreter(
						(FlowDiagram) content).parse();

				generated.put(parsedUnit, a);

				conditionalUnit.setIf(parsedUnit);
			}

			if (thenContext.contains(a) || elseContext.contains(a)) {
				Rule trueRule = HenshinFactory.eINSTANCE.createRule();

				trueRule.setName("true");

				if (thenContext.contains(a) && thenUnit == null) {
					thenUnit = trueRule;
				}

				if (elseContext.contains(a) && elseUnit == null) {
					elseUnit = trueRule;
				}

				LoopUnit loopUnit = HenshinFactory.eINSTANCE.createLoopUnit();

				loopUnit.setSubUnit(conditionalUnit);

				result = loopUnit;
			}

			if (thenUnit != null) {
				conditionalUnit.setThen(thenUnit);
			}

			if (thenUnit != null) {
				conditionalUnit.setElse(elseUnit);
			}

			parsed.add(result);
		}
	}

	/**
	 * @param l
	 * @return
	 */
	private TransformationUnit merge(List<TransformationUnit> l) {
		if (l.isEmpty()) {
			return null;
		} else if (l.size() == 1) {
			return l.get(0);
		} else {
			SequentialUnit u = HenshinFactory.eINSTANCE.createSequentialUnit();

			u.getSubUnits().addAll(l);

			return u;
		}
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
		Parameter curr = units.getLast().getParameterByName(name);

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
