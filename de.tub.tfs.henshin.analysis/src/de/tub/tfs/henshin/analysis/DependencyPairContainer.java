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
package de.tub.tfs.henshin.analysis;

import agg.parser.ParserMessageEvent;
import agg.xt_basis.GraGra;
import agg.xt_basis.Rule;

public class DependencyPairContainer extends agg.parser.DependencyPairContainer {

	public DependencyPairContainer(GraGra gragra) {
		super(gragra);
		// TODO Auto-generated constructor stub
	}

	public void computeCriticalPair(Rule r1, Rule r2) {
		
		try{
			
	        long time0 = System.currentTimeMillis();
	        stop = false;
	        isAlive = true;
	        if(useHostGraph)
	            firePairEvent(new ParserMessageEvent(this, "Thread  -  Checking Host Graph  -  started."));
	        firePairEvent(new ParserMessageEvent(this, "Thread  - Critical pairs -  runs ..."));
	        computeCritical(r1,r2);
	        if(stop)
	        {
	            firePairEvent(new ParserMessageEvent(this, "Thread  -  Critical pairs  -  was stopped."));
	            stop = false;
	        } else
	        {
	            System.out.println((new StringBuilder("Used time: ")).append(System.currentTimeMillis() - time0).append("ms").toString());
	            firePairEvent(new ParserMessageEvent(this, -2, "Thread  -  Critical pairs  -  finished."));
	            if(useHostGraph)
	                firePairEvent(new ParserMessageEvent(this, -2, "Thread  -  Checking Host Graph  -  finished."));
	        }
	        isAlive = false;
		} catch (NullPointerException ex){
			ex.printStackTrace();
		}
		
	}
	
	
	

}
