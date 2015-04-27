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
 * HenshinUtil.java
 *
 * Created 20.12.2011 - 17:25:33
 */
package de.tub.tfs.henshin.editor.util;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.emf.common.util.BasicEList;
import org.eclipse.emf.common.util.ECollections;
import org.eclipse.emf.common.util.EList;
import org.eclipse.emf.ecore.EClassifier;
import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EPackage;
import org.eclipse.emf.ecore.util.EcoreUtil;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NamedElement;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.emf.henshin.model.Unit;

import de.tub.tfs.henshin.editor.HenshinTreeEditor;
import de.tub.tfs.henshin.model.flowcontrol.FlowControlSystem;
import de.tub.tfs.henshin.model.layout.LayoutSystem;
import de.tub.tfs.muvitor.ui.IDUtil;

/**
 * @author nam
 * 
 */
public final class HenshinUtil {

	/**
	 * The singleton instance.
	 */
	public static HenshinUtil INSTANCE = new HenshinUtil();

	/**
     * 
     */
	private HenshinUtil() {
	}

	/**
	 * Convenient method to the root {@link Module}.
	 * 
	 * @param eObject
	 * @return the root {@link Module}
	 */
	public Module getTransformationSystem(EObject model) {
		EObject root = EcoreUtil.getRootContainer(model);

		if (root instanceof Module) {
			return (Module) root;
		} else if (root instanceof FlowControlSystem
				|| root instanceof LayoutSystem) {
			HenshinTreeEditor editor = (HenshinTreeEditor) IDUtil
					.getHostEditor(model);

			if (editor != null) {
				return editor.getModelRoot(Module.class);
			}
		}

		return null;
	}

	/**
	 * @param model
	 * @return
	 */
	public List<NamedElement> getEPackageReferences(EPackage model,
			Module rootModel) {
		List<NamedElement> refs = new LinkedList<NamedElement>();

		boolean graphFound = false;
		boolean ruleFound = false;

		for (EClassifier type : model.getEClassifiers()) {
			if (!graphFound) {
				for (Graph g : rootModel.getInstances()) {
					if (!ModelUtil.getReferences(type, g,
							HenshinPackage.Literals.NODE__TYPE).isEmpty()) {
						refs.add(g);

						graphFound = true;
					}
				}
			}

			if (!ruleFound) {
				EList<Rule> rules = HenshinUtil.getRules(rootModel);
				for (Rule r : rules) {
					if (!ModelUtil.getReferences(type, r,
							HenshinPackage.Literals.NODE__TYPE).isEmpty()) {
						refs.add(r);

						ruleFound = true;
					}
				}
			}

			if (graphFound && ruleFound) {
				break;
			}
		}

		return refs;
	}
	
	public static EList<Rule> getRules(Module m) {
		EList<Rule> rules = new BasicEList<Rule>();
		for (Unit unit : m.getUnits()) {
			if (unit instanceof Rule) {
				rules.add((Rule) unit);
			}
		}
		return ECollections.unmodifiableEList(rules);
	}
	
}
