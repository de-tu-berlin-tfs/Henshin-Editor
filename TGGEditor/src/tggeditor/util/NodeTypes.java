/**
 * 
 */
package tggeditor.util;

import java.util.Iterator;
import java.util.List;
import java.util.Vector;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Module;

import tgg.TGG;


/**
 * The Class NodeTypes.
 */
public class NodeTypes {
	
	/**
	 * Gets the node types.
	 *
	 * @param graph the graph
	 * @param withAbstract the with abstract
	 * @return the node types
	 */
	public static List<EClass> getNodeTypes(Graph graph,boolean withAbstract){
		List<EClass> eClasses = new Vector<EClass>();

		for (EPackage emodel:((Module) graph.eContainer()
				.eResource().getContents().get(0)).getImports()){
			eClasses.addAll(getNodeTypesVonEPackage(emodel,withAbstract));
		}
		return eClasses;
	}

	
	/**
	 * Gets the node types von e package.
	 *
	 * @param emodel the emodel
	 * @param withAbstract the with abstract
	 * @return the node types von e package
	 */
	public static List<EClass> getNodeTypesVonEPackage(EPackage emodel,boolean withAbstract){
		List<EClass> eClasses = new Vector<EClass>();

			Iterator<EObject> it = emodel.eAllContents();
			while (it.hasNext()) {
				EObject eO = it.next();
				if (eO instanceof EClass) {
					if (!((EClass) eO).isAbstract() || withAbstract) {
						eClasses.add((EClass) eO);
					}
				}
			}
		return eClasses;
	}
	
	/**
	 * Gets the edge types von e package.
	 *
	 * @param emodel the emodel
	 * @return the edge types von e package
	 */
	public static List<EReference> getEdgeTypesVonEPackage(EPackage emodel) {
		List<EReference> eReferences = new Vector<EReference>();
		
		Iterator<EObject> it = emodel.eAllContents();
		while (it.hasNext()) {
			EObject eO = it.next();
			if (eO instanceof EReference) {
				eReferences.add((EReference) eO);
			}
		}
		return eReferences;
	}

	public enum NodeGraphType{
		DEFAULT, SOURCE, CORRESPONDENCE, TARGET, RULE
	}
	
	/**
	 * Gets the type of node.
	 *
	 * @param node
	 * @return the node type
	 */
	public static NodeGraphType getNodeGraphType(Node node){
		TGG layoutSystem = NodeUtil.getLayoutSystem(node);
		return getNodeGraphType(layoutSystem, node);
	}

	/**
	 * Gets the type of node.
	 *
	 * @param tgg
	 * @param node
	 * @return the node type
	 */
	public static NodeGraphType getNodeGraphType(TGG tgg, Node node){
		TGG layoutSystem = tgg;
		EClass nodeClass = node.getType();
		if(layoutSystem.getSource()!=null)
		if(getNodeTypesVonEPackage(layoutSystem.getSource(),false).contains(nodeClass)) {
			return NodeGraphType.SOURCE;
		}
		if(layoutSystem.getCorresp()!=null)
		if(getNodeTypesVonEPackage(layoutSystem.getCorresp(),false).contains(nodeClass)) {
			return NodeGraphType.CORRESPONDENCE;
		}
		if(layoutSystem.getTarget()!=null)
		if(getNodeTypesVonEPackage(layoutSystem.getTarget(),false).contains(nodeClass)) {
			return NodeGraphType.TARGET;
		}
		return NodeGraphType.DEFAULT;
	}

	/**
	 * Gets the type of edge.
	 *
	 * @param edge
	 * @return the edge type
	 */
	public static NodeGraphType getEdgeGraphType(Edge edge) {
		TGG layoutSystem = NodeUtil.getLayoutSystem(edge);
		EReference edgeClass = edge.getType();
		if(getEdgeTypesVonEPackage(layoutSystem.getSource()).contains(edgeClass)) {
			return NodeGraphType.SOURCE;
		}
		if(getEdgeTypesVonEPackage(layoutSystem.getCorresp()).contains(edgeClass)) {
			return NodeGraphType.CORRESPONDENCE;
		}
		if(getEdgeTypesVonEPackage(layoutSystem.getTarget()).contains(edgeClass)) {
			return NodeGraphType.TARGET;
		}
		return NodeGraphType.DEFAULT;
	}


	/**
	 * Can containment.
	 *
	 * @param node the node
	 * @return true, if successful
	 */
	public static boolean canContainment(Node node) {
		EPackage eP = node.getType().getEPackage();
		Iterator<EObject> it = eP.eAllContents();
		while (it.hasNext()) {
			EObject eO = it.next();
			if (eO instanceof EReference) {
				EReference ref = (EReference) eO;
				if (ref.isContainment()) {
					if (ref.getEReferenceType() == node.getType() || isExtended(node.getType(), ref.getEReferenceType())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	
	public static boolean isContainment(Node node){
		for (Edge edge:node.getIncoming()){
			if (edge.getType().isContainment()){
				return true;
			}
		}
		return false;
	}
	
	public static boolean isExtended(EClass class1,EClass extendsClass){
		return class1.getEAllSuperTypes().contains(extendsClass);
	}
}
