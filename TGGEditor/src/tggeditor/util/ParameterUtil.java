package tggeditor.util;

import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Parameter;
import org.eclipse.emf.henshin.model.Rule;

public class ParameterUtil {
	/**
	 * Gets the parameter.
	 * 
	 * @param node
	 *            the node
	 * @return the parameter
	 */
	public static Parameter getParameter(Node node) {
		if (node.getName() != null && !node.getName().isEmpty()) {
			Rule rule = (Rule) node.getGraph().eContainer();
			for (Parameter parameter : rule.getParameters()) {
				if (parameter.getName().equals(node.getName())) {
					return parameter;
				}
			}
		}
		return null;
	}
}
