package tggeditor.util.dialogs;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import tgg.TGG;
import tggeditor.commands.create.CreateNodeCommand;
import tggeditor.dialogs.AttributeDialog;
import tggeditor.util.AttributeTypes;
import tggeditor.util.NodeTypes;
import tggeditor.util.NodeTypes.NodeGraphType;

public class DialogUtil {
	
	/**
	 * Run node creation dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param graph
	 *            the graph
	 * @return the e class
	 */
	public static EClass runNodeCreationDialog(Shell shell, CreateNodeCommand c) {
		TGG layoutModel = c.getLayoutModel();
		Graph graph = c.getGraph();
		
		List<EClass> nodeTypes = new ArrayList<EClass>();
		Module transSys = (Module) graph
				.eResource().getContents().get(0);

		if (canCreateNode(shell, graph, layoutModel, c.getNodeGraphType())) {
			
			EPackage epack = getPackage(layoutModel, c.getNodeGraphType());
			
			nodeTypes = NodeTypes.getNodeTypesOfEPackage(epack,
					graph.eContainer() != transSys);
			switch (nodeTypes.size()) {
			case 0:
				return null;
			case 1:
				return nodeTypes.get(0);
			default:
				return new SingleElementListSelectionDialog<EClass>(shell,
						new LabelProvider() {
							@Override
							public String getText(Object element) {
								return ((EClass) element).getName();
							}

//							@Override
//							public Image getImage(Object element) {
//								return IconUtil.getIcon("node18.png");
//							}
						}, nodeTypes.toArray(new EClass[nodeTypes.size()]),
						"Node Type Selection",
						"Select a EClass for the new node type:").run();
			}
		}
		return null;
	}
	
	/**
	 * Run attribute creation dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param node
	 *            the node
	 * @return the map
	 */
	public static SimpleEntry<EAttribute, String> runAttributeCreationDialog(
			Shell shell, Node node) {
		AttributeDialog dialog = new AttributeDialog(shell, SWT.NULL,
				AttributeTypes.getFreeAttributeTypes(node),null);
		dialog.open();
		return dialog.getResult();
	}
	
	/**
	 * Run edge type selection dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param eReferences
	 *            the e references
	 * @return the e reference
	 */
	public static EReference runEdgeTypeSelectionDialog(Shell shell,
			List<EReference> eReferences) {
		switch (eReferences.size()) {
		case 0:
			return null;
		case 1:
			return eReferences.get(0);
		default:
			return new SingleElementListSelectionDialog<EReference>(shell,
					new LabelProvider() {
						@Override
						public String getText(Object element) {
							return ((EReference) element).getName();
						}

//						@Override
//						public Image getImage(Object element) {
//							return IconUtil.getIcon("edge18.png");
//						}
					}, eReferences.toArray(new EReference[eReferences.size()]),
					"Edge Type Selection",
					"Select a EReference for the new edge type:").run();
		}
	}
	
	/**
	 * sucht die richtigen Packages je nach NodeGraphType raus
	 * 
	 */
	public static EPackage getPackage(TGG layoutModel, NodeGraphType type) {
		
		if (type == NodeGraphType.SOURCE) {
			return layoutModel.getSource();
		} else if (type == NodeGraphType.CORRESPONDENCE) {
			return layoutModel.getCorresp();
		} else if (type == NodeGraphType.TARGET) {
			return layoutModel.getTarget();
		}
		
		return null;
	}


	/**
	 * Can create node.
	 * 
	 * @param shell
	 *            the shell
	 * @param graph
	 *            the graph
	 * @param layoutModel
	 *            the layoutModel
	 * @param x
	 *            the position
	 *            
	 * @return true, if successful
	 */
	private static boolean canCreateNode(Shell shell, Graph graph, TGG layoutModel, 
			NodeGraphType nodeGraphType) {
		Module transSys = (Module) graph
				.eResource().getContents().get(0);

		// At least one ePackage must be imported
		if (transSys.getImports().isEmpty()) {
			MessageDialog.openError(shell, "Node Creation Error",
					"There are no model packages imported yet!");
			return false;
		}
		else if (nodeGraphType == NodeGraphType.SOURCE && layoutModel.getSource() == null) {
			MessageDialog.openError(shell, "Node Creation Error",
					"There are no SOURCE model packages imported yet!");
			return false;
		}
		else if (nodeGraphType == NodeGraphType.CORRESPONDENCE && layoutModel.getCorresp() == null) {
			MessageDialog.openError(shell, "Node Creation Error",
					"There are no CORRESPONDENCE model packages imported yet!");
			return false;
		}
		else if (nodeGraphType == NodeGraphType.TARGET && layoutModel.getTarget() == null) {
			MessageDialog.openError(shell, "Node Creation Error",
					"There are no TARGET model packages imported yet!");
			return false;
		}

		return true;
	}
	
	/**
	 * Run graph choice dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param graphen
	 *            the graphen
	 * @return the graph
	 */
	public static Graph runGraphChoiceDialog(Shell shell, List<Graph> graphen) {
		switch (graphen.size()) {
		case 0:
			return null;
		case 1:
			return graphen.get(0);
		default:
			return new SingleElementListSelectionDialog<Graph>(shell,
					new LabelProvider() {
						@Override
						public String getText(Object element) {
							return ((Graph) element).getName();
						}

//						@Override
//						public Image getImage(Object element) {
//							return IconUtil.getIcon("graph18.png");
//						}
					}, graphen.toArray(new Graph[graphen.size()]),
					"Graph Selection", "Select a Graph for transformation:")
					.run();
		}
	}
	
	/**
	 * Run rule choice dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param rules
	 *            the rules
	 * @return the rule
	 */
	public static Rule runRuleChoiceDialog(Shell shell, List<Rule> rules) {
		switch (rules.size()) {
		case 0:
			return null;
		case 1:
			return rules.get(0);
		default:
			return new SingleElementListSelectionDialog<Rule>(shell,
					new LabelProvider() {
						@Override
						public String getText(Object element) {
							return ((Rule) element).getName();
						}

//						@Override
//						public Image getImage(Object element) {
//							return IconUtil.getDescriptor("ruler16.png")
//									.createImage();
//						}
					}, rules.toArray(new Rule[rules.size()]), "Rule Selection",
					"Select a Rule for transformation:").run();
		}
	}

}
