package de.tub.tfs.henshin.tggeditor.util.dialogs;

import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.emf.ecore.EAttribute;
import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.ecore.EcorePackage;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.jface.viewers.LabelProvider;
import org.eclipse.swt.SWT;
import org.eclipse.swt.widgets.Shell;

import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.henshin.tgg.TripleComponent;
import de.tub.tfs.henshin.tgg.TripleGraph;
import de.tub.tfs.henshin.tgg.interpreter.ExceptionUtil;
import de.tub.tfs.henshin.tgg.interpreter.NodeTypes;
import de.tub.tfs.henshin.tggeditor.commands.create.CreateNodeCommand;
import de.tub.tfs.henshin.tggeditor.dialogs.AttributeDialog;
import de.tub.tfs.henshin.tggeditor.util.AttributeTypes;
import de.tub.tfs.henshin.tggeditor.util.dialogs.SingleElementListSelectionDialog.ListEntry;


public class DialogUtil {
	
	/**
	 * Run node creation dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param tripleGraph
	 *            the graph
	 * @return the e class
	 */
	public static EClass runNodeCreationDialog(Shell shell, CreateNodeCommand c) {
		TGG layoutModel = c.getLayoutModel();
		Graph graph = c.getGraph();
		
		List<EClass> nodeTypes = new ArrayList<EClass>();
		Module transSys = (Module) graph
				.eResource().getContents().get(0);

		if (canCreateNode(shell, graph, layoutModel, c.getNodeTripleComponent())) {
			
			List<EPackage> epackages = getPackages(layoutModel, c.getNodeTripleComponent());
			
			nodeTypes = NodeTypes.getNodeTypesOfEPackages(epackages,
					graph.eContainer() != transSys);
			EPackage ecorePackage = EcorePackage.eINSTANCE;
			List<EClass> nodeTypes2 = new ArrayList<EClass>();
			nodeTypes2 = NodeTypes.getNodeTypesOfEPackage(ecorePackage,
					graph.eContainer() != transSys);
			nodeTypes.addAll(nodeTypes2);
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
	 * Run node creation dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param tripleGraph
	 *            the graph
	 * @return the e class
	 */
	public static EClass runClassSelectionDialog(Shell shell, List<EClassifier> c,EClassifier source,ListEntry<EClass>... entries) {
		
		List<EClass> nodeTypes = new ArrayList<EClass>();
		
		for (EClassifier eClassifier : c) {
			if (eClassifier instanceof EClass){
				nodeTypes.add((EClass) eClassifier);
			}
		}

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
		source.getName(),
				("Select a EClass for the mapping to "+source.getName()+":"),entries).run();

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
	 * selects the packages depending on the triple component
	 * 
	 */
	public static List<EPackage> getPackages(TGG layoutModel, TripleComponent type) {
		
		if (layoutModel == null) {ExceptionUtil.error("Layout model is missing"); return null;}
		return NodeTypes.getEPackagesOfComponent(layoutModel.getImportedPkgs(),type);
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
			TripleComponent nodeTripleComponent) {
		Module module = (Module) graph
				.eResource().getContents().get(0);

		// At least one ePackage must be imported
		if (module.getImports().isEmpty()) {
			MessageDialog.openError(shell, "Node Creation Error",
					"There are no model packages imported yet!");
			return false;
		}
		else if (getPackages(layoutModel,nodeTripleComponent).isEmpty()) {
			MessageDialog.openError(shell, "Node Creation Error",
					"There are no " + nodeTripleComponent + " model packages imported yet!");
			return false;
		}

		return true;
	}
	
	/**
	 * Run graph choice dialog.
	 * 
	 * @param shell
	 *            the shell
	 * @param graphs
	 *            the graphen
	 * @return the graph
	 */
	public static Graph runGraphChoiceDialog(Shell shell, List<Graph> graphs) {
		switch (graphs.size()) {
		case 0:
			return null;
		case 1:
			return graphs.get(0);
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
					}, graphs.toArray(new Graph[graphs.size()]),
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
