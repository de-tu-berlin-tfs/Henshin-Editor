/*******************************************************************************
 * Copyright (c) 2012, 2014 Henshin developers.
 * All rights reserved. This program and the accompanying materials
 * are made available under the terms of the Eclipse Public License v1.0
 * which accompanies this distribution, and is available at
 * http://www.eclipse.org/legal/epl-v10.html
 *
 * Contributors:
 *     Henshin developers - initial API and implementation
 *******************************************************************************/
package de.tub.tfs.henshin.tggeditor.commands.delete;

import org.eclipse.gef.commands.CompoundCommand;

import de.tub.tfs.henshin.tgg.CritPair;
import de.tub.tfs.henshin.tgg.TGG;
import de.tub.tfs.muvitor.commands.SimpleDeleteEObjectCommand;

public class DeleteCritPairCommand extends CompoundCommand {

	private CritPair _critPair;
	private TGG _tgg;
	
	public DeleteCritPairCommand(CritPair critPair) {
		this._critPair = critPair;
	
//		_trasfo = critPair.getRule1().getTransformationSystem();
		
		add(new SimpleDeleteEObjectCommand(critPair.getRule1()));
		add(new SimpleDeleteEObjectCommand(critPair.getRule2()));
		
		add(new SimpleDeleteEObjectCommand(critPair.getOverlapping()));
		
		add(new SimpleDeleteEObjectCommand(critPair));
	}
	
	public void execute() {
		
		// remove CritPair from tgg
		_tgg.getCritPairs().remove(_critPair);		
		// remove overlapping from instances of transformation system
		_tgg.getInstances().remove(_critPair.getOverlapping());
		
		
		super.execute();
	}
}
