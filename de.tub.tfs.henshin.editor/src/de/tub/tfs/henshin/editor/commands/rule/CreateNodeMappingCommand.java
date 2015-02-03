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
package de.tub.tfs.henshin.editor.commands.rule;

import java.util.List;

import org.eclipse.emf.ecore.EObject;
import org.eclipse.emf.ecore.EStructuralFeature;
import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.HenshinFactory;
import org.eclipse.emf.henshin.model.HenshinPackage;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.Module;
import org.eclipse.emf.henshin.model.NestedCondition;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.Rule;
import org.eclipse.gef.commands.Command;
import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.editor.commands.SimpleAddEObjectCommand;
import de.tub.tfs.henshin.editor.util.HenshinLayoutUtil;
import de.tub.tfs.henshin.editor.util.HenshinUtil;
import de.tub.tfs.henshin.editor.util.JavaUtil;
import de.tub.tfs.henshin.editor.util.ModelUtil;
import de.tub.tfs.henshin.model.layout.NodeLayout;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

/**
 * A {@link Command command} to create {@link Mapping mappings} between
 * {@link Node nodes} contained by {@link Graph graphs} in a {@link Rule rule} .
 * (e.g. LHS <-> RHS, LHS <-> NAC, NC <->NC, NC<->RHS...)
 * 
 * @author Johann, nam
 */
public class CreateNodeMappingCommand extends CompoundCommand {

	private Node origin;

	private Node image;

	private Mapping newMapping;

	private EObject container;

	private NodeLayout originLayout;

	private NodeLayout imageLayout;

	private EStructuralFeature mappingsFeature;

	private Graph orgGraph;

	private Graph imgGraph;

	private boolean skipCheck = false;

	/**
     * 
     */
	private void init() {
		if (JavaUtil.notNull(origin, image, newMapping, container,
				originLayout, image, mappingsFeature, orgGraph, imgGraph)) {

			int idx = -1;
			getCommands().clear();

			if (!origin.getType().isSuperTypeOf(image.getType())
					|| orgGraph == imgGraph) {
				return;
			}

			if (orgGraph.isLhs() && imgGraph.isRhs()) {
				container = imgGraph.getRule();
			}


			if (imgGraph.isNestedCondition()) {
				container = imgGraph.eContainer();
			}

			if (orgGraph.isRhs()) {
				swapOrgImg();
			}
			
			if (!skipCheck && HenshinLayoutUtil.INSTANCE.isMultiNode(origin) ){
					return;
			}
			newMapping.setOrigin(origin);
			newMapping.setImage(image);

			Module rootModel = HenshinUtil.INSTANCE
					.getTransformationSystem(origin);

			if (rootModel == null) {
				rootModel = HenshinUtil.INSTANCE.getTransformationSystem(image);
			}

			List<Mapping> currMappingsFromOrigin = ModelUtil.getReferences(
					origin, Mapping.class, rootModel,
					HenshinPackage.Literals.MAPPING__ORIGIN);

			for (Mapping m : currMappingsFromOrigin) {
				if (m.getImage() == image) {
					return;
				}

				if (m.getImage() != null && m.getImage().getGraph() == imgGraph) {
					add(new DeleteMappingCommand(m, false));
					//add(new SimpleDeleteEObjectCommand(m));
					if (m.getImage().getGraph().eContainer() instanceof Rule){
						idx = m.getImage().getGraph().getRule().getMappings().indexOf(m);
					} else {
						idx = ((NestedCondition)m.getImage().getGraph().eContainer()).getMappings().indexOf(m);
					}
				}
			}

			List<Mapping> currMappingsToTarget = ModelUtil.getReferences(image,
					Mapping.class, rootModel,
					HenshinPackage.Literals.MAPPING__IMAGE);

			for (Mapping m : currMappingsToTarget) {
				if (m.getOrigin() != null && m.getOrigin().getGraph() == orgGraph) {
					add(new DeleteMappingCommand(m, false));
					//add(new SimpleDeleteEObjectCommand(m));
					if (m.getOrigin().getGraph().eContainer() instanceof Rule){
						idx = m.getOrigin().getGraph().getRule().getMappings().indexOf(m);
					} else {
						idx = ((NestedCondition)m.getOrigin().getGraph().eContainer()).getMappings().indexOf(m);
					}
				}
			}

			//add(new CreateMappingColorCommand(originLayout, imageLayout,
			//		container));
			//System.out.println("DEBUG:_idx=_" + idx);
			
			add(new SimpleAddEObjectCommand<EObject, Mapping>(newMapping,
					mappingsFeature, container,idx));
		

			for (Rule multiRule : this.orgGraph.getRule().getMultiRules()) {
				Node multiSource = multiRule.getMultiMappings().getImage(origin, multiRule.getLhs());
				
				Node multiTarget = multiRule.getMultiMappings().getImage(image, multiRule.getRhs());
				
				CreateNodeMappingCommand c = new CreateNodeMappingCommand(multiSource,multiTarget,multiRule);
				c.skipCheck = true;
				c.init();
				add(c);
			}
		}
	}


	/**
	 * @param newMapping
	 * @param origin
	 * @param image
	 * @param container
	 */
	public CreateNodeMappingCommand(Mapping newMapping, Node origin,
			Node image, EObject container) {

		this(newMapping, origin);

		setImage(image);
		setContainer(container);

		//init();
	}

	@Override
	public boolean canExecute() {
		if (this.getCommands().isEmpty())
		 return true;
		if(!super.canExecute())
			System.out.println("No.");
		return super.canExecute();
	}


	/**
	 * Instantiates a new creates the node mapping command.
	 * 
	 * @param origin
	 *            the original
	 * @param mapping
	 *            the mapping
	 */
	public CreateNodeMappingCommand(Mapping newMapping, Node origin) {
		super("Creating Node Mapping");
		this.newMapping = newMapping;
		setOrigin(origin);
	}

	/**
	 * @param origin
	 * @param image
	 * @param container
	 */
	public CreateNodeMappingCommand(final Node origin, final Node image,
			final EObject container) {
		this(HenshinFactory.eINSTANCE.createMapping(), origin, image, container);
	}

	/**
	 * Gets the original.
	 * 
	 * @return the original
	 */
	public Node getOrigin() {
		return origin;
	}

	/**
	 * Sets the image {@link Node} for the new {@link Mapping}.
	 * 
	 * @param image
	 *            the new image {@link Node}.
	 */
	public void setImage(final Node image) {
		this.image = image;

		if (image != null) {
			imageLayout = HenshinLayoutUtil.INSTANCE.getLayout(image);
			imgGraph = image.getGraph();
		}

		//init();
	}

	/**
	 * @param imgGraph
	 *            the imgGraph to set
	 */
	public void setImgGraph(Graph imgGraph) {
		this.imgGraph = imgGraph;

		init();
	}

	/**
	 * @param origin
	 *            the origin to set
	 */
	public void setOrigin(Node origin) {
		this.origin = origin;

		if (origin != null) {
			if (origin.eContainer() != null) {
				originLayout = HenshinLayoutUtil.INSTANCE.getLayout(origin);
				orgGraph = origin.getGraph();
			}
		}

		//init();
	}

	/**
	 * @param container
	 */
	public void setContainer(final EObject container) {
		this.container = container;

		if (container != null) {
			if (container instanceof Rule) {
				mappingsFeature = HenshinPackage.Literals.RULE__MAPPINGS;
			} else if (container instanceof NestedCondition) {
				mappingsFeature = HenshinPackage.Literals.NESTED_CONDITION__MAPPINGS;
			}
		}

		init();
	}

	/**
	 * @param imageLayout
	 *            the imageLayout to set
	 */
	public void setImageLayout(NodeLayout imageLayout) {
		this.imageLayout = imageLayout;

		init();
	}

	/**
     * 
     */
	private void swapOrgImg() {
		Node tmp = origin;

		origin = image;
		image = tmp;

		imgGraph = image.getGraph();
		orgGraph = origin.getGraph();

		imageLayout = HenshinLayoutUtil.INSTANCE.getLayout(image);
		originLayout = HenshinLayoutUtil.INSTANCE.getLayout(origin);
	}
	
}
