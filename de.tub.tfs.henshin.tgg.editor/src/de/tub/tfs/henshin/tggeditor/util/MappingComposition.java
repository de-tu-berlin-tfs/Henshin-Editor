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
package de.tub.tfs.henshin.tggeditor.util;

import org.eclipse.emf.henshin.model.Graph;
import org.eclipse.emf.henshin.model.Mapping;
import org.eclipse.emf.henshin.model.MappingList;
import org.eclipse.emf.henshin.model.Node;
import org.eclipse.emf.henshin.model.impl.MappingListImpl;

public class MappingComposition extends MappingListImpl{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = -6938318712027951218L;
	private MappingList left;
	private MappingList right;
	
	public MappingComposition(MappingList left, MappingList right, Graph graph){
		super();
		this.left = left;
		this.right = right;
		
		for (Mapping m : left){
			Node origin = m.getOrigin();
			Node imageR = right.getImage(m.getImage(), graph);
			if (imageR!=null){
				this.add(origin, imageR);
			}
		}
	}

	public MappingList getLeft() {
		return left;
	}


	public MappingList getRight() {
		return right;
	}

}
