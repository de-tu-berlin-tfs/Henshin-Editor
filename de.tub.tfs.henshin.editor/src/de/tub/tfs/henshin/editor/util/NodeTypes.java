/*******************************************************************************
 * Copyright (c) 2010-2015 Henshin developers. All rights reserved. 
 * This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     TU Berlin, University of Luxembourg, SES S.A.
 *******************************************************************************/
/**
 * 
 */
package de.tub.tfs.henshin.editor.util;

import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.Vector;

import org.eclipse.emf.ecore.EClass;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.EReference;
import org.eclipse.emf.henshin.model.Edge;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Node;

/**
 * The Class NodeTypes.
 */
public class NodeTypes {
	
	public static Set<EClass> getUsedNodeTypes(Graph graph) {
		Set<EClass> result = new HashSet<EClass>();
		
		for (Node node : graph.getNodes()) {
			result.add(node.getType());
		}
		
		return result;
	}

	/**
	 * Gets the node types.
	 * 
	 * @param graph
	 *            the graph
	 * @param withAbstract
	 *            the with abstract
	 * @return the node types
	 */
	public static List<EClass> getNodeTypes(Graph graph, boolean withAbstract) {
		List<EClass> eClasses = new Vector<EClass>();

		// ==============================================================
		// old version without filter
		/*
		for (EPackage emodel : ((Module) graph.eContainer()
				.eResource().getContents().get(0)).getImports()) {
			eClasses.addAll(getNodeTypesVonEPackage(emodel, withAbstract));
		}
		*/
		//
		// ==============================================================
		
		for (EPackage ePackage : ModelUtil.getEPackagesOfGraph(graph)) {
			eClasses.addAll(getNodeTypesOfEPackage(ePackage, withAbstract));
		}
		
		return eClasses;
	}

	/**
	 * Gets the node types von e package.
	 * 
	 * @param emodel
	 *            the emodel
	 * @param withAbstract
	 *            the with abstract
	 * @return the node types of e package
	 */
	public static List<EClass> getNodeTypesOfEPackage(EPackage emodel,
			boolean withAbstract) {
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
	 * Can containment.
	 * 
	 * @param node
	 *            the node
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
					if (ref.getEReferenceType() == node.getType()
							|| isExtended(node.getType(),
									ref.getEReferenceType())) {
						return true;
					}
				}
			}
		}
		return false;
	}

	public static boolean isContainment(Node node) {
		for (Edge edge : node.getIncoming()) {
			if (edge.getType().isContainment()) {
				return true;
			}
		}
		return false;
	}

	public static boolean isExtended(EClass class1, EClass extendsClass) {
		return class1.getEAllSuperTypes().contains(extendsClass);
	}
}
